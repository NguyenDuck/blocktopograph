"""python script generating Minecraft BE block list from Wiki.
"""
from bs4 import BeautifulSoup as Soup
from base64 import b64encode, b64decode
from io import BytesIO
from PIL import Image, ImageDraw
import re
import requests as req
import json
import wikitextparser as wtp
import numpy as np

Wiki_html = ".\\blocksWiki.html"
Sav = ".\\blocksWiki.json"
Sav2 = ".\\blocksWiki2.json"
Url_host = "https://minecraft.gamepedia.com"
Headers = {
    "cache-control": "max-age=0",
    "user-agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36",
    "accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
    "cookie": "crfgL0cSt0r=true; Geo=^{^%^22region^%^22:^%^22CA^%^22^%^2C^%^22country^%^22:^%^22US^%^22^%^2C^%^22continent^%^22:^%^22NA^%^22^}; _gid=GA1.2.1134239218.1579079756; usprivacy=1NNN; wikia_beacon_id=Uvlr8mGCp1; tracking_session_id=osGf6QyudA; VEE=wikitext; pv_number=4; pv_number_global=4"
}


def parse_color(tag, color):
    try:
        style = tag.attrs['style']
        color = re.match(
            r'.*[^-]?color\s*:\s*([#0-9a-zA-Z]+)\s*', style).group(1)
    except (IndexError, KeyError):
        pass
    for ch in tag.contents:
        if isinstance(ch, str):
            try:
                val = int(ch.strip())
            except ValueError:
                val = None
            return [val, color]
        else:
            val, color = parse_color(ch, color)
            if val is not None:
                return [val, color]
    return [None, color]


def parse_name(tag):
    name = ''
    for ch in tag.contents:
        if isinstance(ch, str):
            name += ch
        elif ch.name == 'sup':
            continue
        else:
            name += parse_name(ch)
    return name


def parse_list():
    """Copy & Paste the 5 tables containing Bedrock version blocks
        from wiki into a text file then parse it using this function.
    """
    List = []
    doc = Soup(open(Wiki_html).read(), features="lxml")
    cnt = 0
    for row in doc.select('tbody>tr'):
        cols = row.select('td')
        if len(cols) > 4:
            entry = {}
            cnt += 1
            # 1. icon
            imgs = cols[0].select('img')
            if len(imgs) > 1:
                raise Error()
            elif len(imgs) == 1:
                for img in imgs:
                    entry['icon_src'] = img.attrs['src']
                    # input(entry['icon'])
                    break
            else:
                entry['icon_src'] = '[]'
            # 2. id and its color
            entry['id'], entry['id_color'] = parse_color(cols[1], None)
            # 3. identifier
            entry['identifier'] = cols[3].text.strip()
            # 4. notations
            notations = ''
            for part in cols[4].select('sup'):
                notations += part.text
            entry['note_i'] = True if 'I' in notations else False
            entry['note_d'] = True if 'D' in notations else False
            entry['note_s'] = True if 'S' in notations else False
            entry['note_b'] = True if 'B' in notations else False
            entry['note_n'] = True if 'N' in notations else False
            entry['note_e'] = True if 'E' in notations else False
            # 5. page links
            if '(unused)' not in cols[4].text:
                url = cols[4].select('td>a')[0].attrs['href']
                if '#' in url:
                    url = url[0: url.index('#')]
                entry['page'] = url
            else:
                entry['page'] = '[]'
            # 6. name
            block['name'] = re.sub(r'\([^)]*\)', '',
                                   re.sub(r'[\r\n]', '', parse_name(cols[4]).strip()))
            List.append(entry)
    open(Sav, 'w').write(json.dumps(List))
    print(f"Retrieved {cnt} blocks.")
    # for i in range(477):
    #     found = False
    #     for entry in List:
    #         if entry['id'] == i:
    #             found = True
    #             break
    #     if not found:
    #         input(i)


def upgrade_list_from_page_link_to_edit_link():
    """Get wiki's edit page link from each wiki page."""
    List = json.loads(open(Sav).read())
    for block in List:
        edit_url = block.get('edit_url', '[]')
        if not (edit_url.startswith('[') and edit_url.endswith(']')) or block['page'] == '[]':
            continue
        try:
            resp = req.get(
                Url_host + block['page'], headers=Headers, verify=False)
            doc = Soup(resp.text, features="lxml")
            edit_url = doc.select("a[accesskey='e']")[0].attrs['href']
            # input(edit_url)
        except (IndexError, KeyError):
            input(
                f'failed {block["identifier"]}, {resp.text}.')
            edit_url = '[1]'
        except:
            input(f'failed {block["identifier"]}.')
            edit_url = '[2]'
        block['edit_url'] = edit_url
    open(Sav, 'w').write(json.dumps(List))


def upgrade_list_get_wikitext():
    """retrieve wikitext from wiki edit pages."""
    List = json.loads(open(Sav).read())
    for block in List:
        edit_url = block['edit_url']
        wikitext = block.get('wikitext', '[]')
        if not (wikitext.startswith('[') and wikitext.endswith(']')):
            continue
        if edit_url.startswith('[') and edit_url.endswith(']'):
            block['wikitext'] = '[3]'
            continue
        try:
            resp = req.get(
                Url_host + edit_url, headers=Headers, verify=False)
            doc = Soup(resp.text, features="lxml")
            wikitext = doc.select("textarea#wpTextbox1")[0].text
            # input(wikitext)
        except (IndexError):
            input(
                f'failed {block["identifier"]}, {resp.text}.')
            wikitext = '[1]'
        except:
            input(
                f'failed {block["identifier"]}.')
            wikitext = '[2]'
        block['wikitext'] = wikitext
    open(Sav, 'w').write(json.dumps(List, indent=4))


def upgrade_list_get_block_states_wikitext():
    """Get the missing wikitext for block states from seperated pages."""
    List = json.loads(open(Sav).read())
    for block in List:
        wikitext = block['wikitext']
        bsholder = '{{/BS}}'
        if bsholder in wikitext:
            url = Url_host + block['edit_url'].replace("&action", "/BS&action")
            try:
                resp = req.get(
                    url, headers=Headers, verify=False)
                doc = Soup(resp.text, features="lxml")
                bstext = doc.select("textarea#wpTextbox1")[0].text
                block['wikitext'] = wikitext.replace(bsholder, bstext)
                # input(wikitext)
            except (IndexError):
                input(
                    f'failed {block["identifier"]}, {resp.text}.')
            except:
                input(
                    f'failed {block["identifier"]}.')
        # if len(wikitext) < 5:
        #     input(f"skipping {block['identifier']}: no wikitext.")
        #     continue
        # doc = wtp.parse(wikitext)
        # section = None
        # for sec in doc.sections:
        #     if 'data values' in sec.title.lower():
        #         section = sec
        #         break
        # if section is None:
        #     input(f"skipping {block['identifier']}: no data values.")
        #     continue
        # input(block['identifier'])
        # input(section.string)
    open(Sav, 'w').write(json.dumps(List, indent=4))


def upgrade_list_get_icons():
    """Get icons according to their url and store in base64 format within the
        list. CurseCdn is really faster than gamepedia."""
    List = json.loads(open(Sav).read())
    for block in List:
        if block.get('icon') is None:
            src = block.get('icon_src')
            if src is None:
                input(block['identifier'])
            elif 'http' not in src:
                input(f"fuck{block['identifier']}")
            else:
                if block.get('identifier', '').endswith('_sign'):
                    repl = '/84px'
                else:
                    repl = '/120px'
                src = re.sub(r'/[0-9]+px', repl, src)
                try:
                    block['icon'] = b64encode(
                        req.get(src, headers=Headers, verify=False).content).decode('ascii')
                except:
                    input(f"suck{block['identifier']}")  # 606484120126
    open(Sav, 'w').write(json.dumps(List, indent=4))


def update_list_generate_icons():
    """Generate tile png for all icons and store x and y offsets for icons.
        In addition, average colors for non-fully transparent parts of each
        blocks are calculated and saved within the list. A color tile map
        is also generated for preview."""
    List = json.loads(open(Sav).read())
    icons_cnt = 0
    cols_cnt = 20
    size_per_icon = 120
    width = size_per_icon * cols_cnt
    for block in List:
        if block.get('icon') is not None:
            icons_cnt += 1
    height = size_per_icon * ((icons_cnt - 1) // cols_cnt + 1)
    icons = Image.new('RGBA', (width, height))
    colors = Image.new('RGB', (width, height))
    colors_draw = ImageDraw.Draw(colors)
    x = y = 0
    for block in List:
        icon = block.get('icon')
        if icon is None:
            continue
        icon = Image.open(BytesIO(b64decode(icon.encode('ascii'))))
        if icon.width != size_per_icon or icon.height != size_per_icon:
            # input(f"{block['identifier']}:{icon.width}x{icon.height}")
            pass
        if icon.mode != 'RGBA':
            icon = icon.convert('RGBA')
        actualx = x + 18 if icon.width <= 100 else x
        icons.paste(icon, (actualx, y))
        arr = np.array(icon)
        color = np.average(arr[:, :, :3], (0, 1), np.repeat(
            arr[:, :, 3:] > 0, 3, 2)).astype('uint8').tolist()
        colors_draw.rectangle(
            (x, y, x + size_per_icon, y + size_per_icon), tuple(color))
        block['icon_r'], block['icon_g'], block['icon_b'] = color
        block['icon_x'], block['icon_y'] = x, y
        x += size_per_icon
        if x >= width:
            x = 0
            y += size_per_icon
    del colors_draw
    colors.save('block_colors.png')
    icons.save('block_icons.png')
    open(Sav, 'w').write(json.dumps(List, indent=4))


def generate_listing_blocks_enum():
    List = json.loads(open(Sav).read())
    with open('listing_blocks.txt', 'w') as hole:
        for i in range(len(List)):
            block = List[i]
            if block['identifier'].startswith('element_') or i == len(List) - 1:
                hole.write('//')
            hole.write(
                f'B_{str(block.get("id","XX"))}_{block["identifier"].upper()}('
            )
            hole.write(
                f'"minecraft:{block["identifier"]}", {block.get("id",-1)}, ')
            hole.write(f'"{block.get("name","<Unknown>")}", ')
            hole.write(f'{block.get("icon_x",-1)}, {block.get("icon_y",0)}, ')
            hole.write(f'{block.get("icon_r",-1)}, {block.get("icon_g",0)}, ')
            hole.write(f'{block.get("icon_b",0)})')
            hole.write(';'if i == len(List) - 2 else',')
            hole.write('\n')


def __main__():
    # upgrade_list_get_icons()
    # update_list_generate_icons()
    generate_listing_blocks_enum()


if __name__ == '__main__':
    __main__()
