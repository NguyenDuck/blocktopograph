import os

dir_root = '.'
dir_any = 'drawable-anydpi'
dirs_bmp = ['drawable-mdpi', 'drawable-hdpi', 'drawable-xhdpi', 'drawable-xxhdpi', 'drawable-xxxhdpi']
files_unused = ['ic_action_compass', 'ic_action_home_b', 'ic_action_map_b', 'ic_add_location', 'ic_developer_mode', 'ic_emoticon_b', 'ic_folder', 'ic_inventory', 'ic_layers', 'ic_nature_people', 'ic_person_pin_circle', 'ic_player', 'ic_priority_high', 'ic_refresh']

def getFilesOfDir(dir):
	return [file for file in os.listdir(dir) if os.path.isfile(os.path.join(dir, file))]

def main():
	vects = getFilesOfDir(os.path.join(dir_root, dir_any))
	vects = [name.split('.')[0] for name in vects]
	#print(vects)
	for dir in dirs_bmp:
		dir = os.path.join(dir_root, dir)
		if os.path.isfile(dir): continue
		for bmp in getFilesOfDir(dir):
			if bmp.split('.')[0] in vects or bmp.split('.')[0] in files_unused:
				bmp = os.path.join(dir, bmp)
				os.remove(bmp)
				print('Deleted ' + bmp)

main()
	