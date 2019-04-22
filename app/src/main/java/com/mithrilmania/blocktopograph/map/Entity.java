package com.mithrilmania.blocktopograph.map;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mithrilmania.blocktopograph.util.NamedBitmapProvider;
import com.mithrilmania.blocktopograph.util.NamedBitmapProviderHandle;

import java.io.IOException;

import androidx.annotation.NonNull;

/*
Entity enum for MCPE __ by @mithrilmania

___ Please attribute @mithrilmania for generating+updating this enum

    Format:

        ENTITY(
            numeric_id,         deprecated!
            Display Name,
            DataName,           not used?
            wiki_name,          as identifier, originally dumped as wiki-name cases.
            icon
        )

    icon is from assets/entity_wiki.png which was download from
    https://d1u5p3l4wpay3k.cloudfront.net/minecraft_gamepedia/4/40/EntityCSS.png

 */
public enum Entity implements NamedBitmapProviderHandle, NamedBitmapProvider {

    CHICKEN(10, "Chicken", new String[]{"Chicken"}, "chicken", 4),
    COW(11, "Cow", new String[]{"Cow"}, "cow", 3),
    PIG(12, "Pig", new String[]{"Pig"}, "pig", 1),
    SHEEP(13, "Sheep", new String[]{"Sheep"}, "sheep", 2),
    WOLF(14, "Wolf", new String[]{"Wolf"}, "wolf", 18),
    VILLAGER(15, "Villager", new String[]{"Villager"}, "villager", 7),
    MUSHROOM_COW(16, "Mooshroom", new String[]{"MushroomCow"}, "mooshroom", 6),
    SQUID(17, "Squid", new String[]{"Squid"}, "squid", 5),
    RABBIT(18, "Rabbit", new String[]{"Rabbit"}, "rabbit", 89),
    BAT(19, "Bat", new String[]{"Bat"}, "bat", 64),
    VILLAGER_GOLEM(20, "Iron Golem", new String[]{"VillagerGolem", "IronGolem"}, "iron_golem", 48),
    SNOW_MAN(21, "Snow Golem", new String[]{"SnowMan", "SnowGolem"}, "snow_golem", 31),
    OZELOT(22, "Ocelot", new String[]{"Ozelot", "Ocelot"}, "ozelot", 37),
    HORSE(23, "Donkey", new String[]{"EntityHorse", "Horse"}, "horse", 73),
    HORSE_DONKEY(24, "Donkey", new String[]{"EntityHorse", "Donkey"}, "donkey", 74),
    HORSE_MULE(25, "Mule", new String[]{"EntityHorse", "Mule"}, "mule", 75),
    HORSE_SKELETON(26, "Skeleten Horse", new String[]{"EntityHorse", "SkeletonHorse"}, "skeleton_horse", 76),
    HORSE_ZOMBIE(27, "Zombie Horse", new String[]{"EntityHorse", "ZombieHorse"}, "zombie_horse", 77),
    //28
    //29
    ARMOR_STAND(30, "Armor Stand", new String[]{"ArmorStand"}, "TODO", 106),//30 ; ArmorStand is not yet in the game
    //31
    ZOMBIE(32, "Zombie", new String[]{"Zombie"}, "zombie", 9),
    CREEPER(33, "Creeper", new String[]{"Creeper"}, "creeper", 14),
    SKELETON(34, "Skeleton", new String[]{"Skeleton"}, "skeleton", 10),
    SPIDER(35, "Spider", new String[]{"Spider"}, "spider", 11),
    PIG_ZOMBIE(36, "Zombie Pigman", new String[]{"PigZombie"}, "zombie_pigman", 17),
    SLIME(37, "Slime", new String[]{"Slime"}, "slime", 15),
    ENDERMAN(38, "Enderman", new String[]{"Enderman", "EnderMan"}, "enderman", 21),
    SILVERFISH(39, "Silverfish", new String[]{"Silverfish"}, "silverfish", 22),
    CAVE_SPIDER(40, "Cave Spider", new String[]{"CaveSpider"}, "cave_spider", 13),
    GHAST(41, "Ghast", new String[]{"Ghast"}, "ghast", 16),
    LAVA_SLIME(42, "Magma Cube", new String[]{"LavaSlime"}, "magma_cube", 24),
    BLAZE(43, "Blaze", new String[]{"Blaze"}, "blaze", 32),
    ZOMBIE_VILLAGER(44, "Zombie Villager", new String[]{"ZombieVillager"}, "zombie_villager", 62),
    WITCH(45, "Witch", new String[]{"Witch"}, "witch", 54),
    SKELETON_STRAY(46, "Stray", new String[]{"Skeleton", "StraySkeleton"}, "stray", 98),
    ZOMBIE_HUSK(47, "Husk", new String[]{"Zombie", "HuskZombie"}, "husk", 99),
    SKELETON_WITHER(48, "Wither Skeleton", new String[]{"Skeleton", "WitherSkeleton"}, "wither", 61),
    GUARDIAN(49, "Guardian", new String[]{"Guardian"}, "guardian", 87),
    ELDER_GAURDIAN(50, "Elder Gaurdian", new String[]{"ElderGaurdian"}, "elder_gaurdian", 88),
    NPC(51, "NPC", new String[]{"Npc"}, "npc", 100),
    WITHER_BOSS(52, "Wither Boss", new String[]{"WitherBoss"}, "blue_wither_skull", 72),
    ENDER_DRAGON(53, "Ender Dragon", new String[]{"EnderDragon"}, "ender_dragon", 29),
    SHULKER(54, "Shulker", new String[]{"Shulker"}, "shulker", 30),
    ENDERMITE(55, "Endermite", new String[]{"Endermite"}, "endermite", 86),
    LEARN_TO_CODE_MASCOT(56, "Learn To Code Mascot", new String[]{"LearnToCodeMascot"}, "learn_to_code_mascot", 108),
    GIANT(57, "Giant Zombie", new String[]{"Giant"}, "giant", 9),//53 ; Giant is not yet in the game
    //58
    //59
    //60
    //61
    //61
    CAMERA(62, "Tripod Camera", new String[]{"TripodCamera"}, "camera", 144),
    PLAYER(63, "Player", new String[]{"Player"}, "player", 8),
    ITEM(64, "Dropped Item", new String[]{"ItemEntity"}, "item", -1),//do not render items
    PRIMED_TNT(65, "Primed TNT", new String[]{"PrimedTnt"}, "primed_tnt", 49),
    FALLING_SAND(66, "Falling KnownBlock", new String[]{"FallingBlock"}, "falling_sand", 50),
    ITEM_FRAME(67, "Item Frame", new String[]{"ItemFrame"}, "empty_item_frame", 66),//67 ; ItemFrame is not yet in the game
    THROWN_EXP_BOTTLE(68, "Bottle o' Enchanting", new String[]{"ThrownExpBottle", "ExperiencePotion"}, "ThrownExpBottle", 56),
    XP_ORB(69, "Experience Orb", new String[]{"XPOrb", "ExperienceOrb"}, "experience_orb", 59),
    EYE_OF_ENDER_SIGNAL(70, "Eye of Ender", new String[]{"EyeOfEnderSignal"}, "eye_of_ender", 47),
    ENDER_CRYSTAL(71, "Ender Crystal", new String[]{"EnderCrystal"}, "ender_crystal", 52),
    //72
    //73
    TURTLE(74, "Turtle", new String[]{"Turtle"}, "turtle", 79),
    //75
    SHULKER_BULLET(76, "Shulker Bullet", new String[]{"ShulkerBullet"}, "shulker_bullet", 79),
    FISHING_HOOK(77, "Fishing Hook", new String[]{"FishingHook"}, "fishing_hook", 57),
    CHALKBOARD(78, "Chalkboard", new String[]{"Chalkboard"}, "chalkboard", 144),
    DRAGON_FIREBALL(79, "Dragon Fireball", new String[]{"DragonFireball"}, "dragon_fireball", 80),
    ARROW(80, "Arrow", new String[]{"Arrow"}, "arrow", 41),
    SNOWBALL(81, "Snowball", new String[]{"Snowball"}, "snowball", 42),
    THROWN_EGG(82, "Thrown Egg", new String[]{"ThrownEgg"}, "thrown_egg", 43),
    PAINTING(83, "Painting", new String[]{"Painting"}, "painting", 65),
    MINECART_RIDEABLE(84, "Minecart", new String[]{"MinecartRideable", "Minecart"}, "minecart", 34),
    LARGE_FIREBALL(85, "Ghast Fireball", new String[]{"Fireball", "LargeFireball"}, "fireball", 44),
    THROWN_POTION(86, "Splash Potion", new String[]{"ThrownPotion"}, "ThrownPotion", 95),
    THROWN_ENDERPEARL(87, "Ender Pearl", new String[]{"ThrownEnderpearl"}, "ender_pearl", 46),
    LEASH_KNOT(88, "Lead Knot", new String[]{"LeashKnot", "LeashFenceKnotEntity"}, "lead_knot", 94),
    WITHER_SKULL(89, "Wither Skull", new String[]{"WitherSkull"}, "wither_skull", 60),
    BOAT(90, "Boat", new String[]{"Boat"}, "boat", 33),
    //91
    //92
    LIGHTNING(93, "Lightning Bolt", new String[]{"LightningBolt"}, "lightning", 58),
    SMALL_FIREBALL(94, "Blaze Fireball", new String[]{"SmallFireball"}, "fireball", 44),
    AREA_EFFECT_CLOUD(95, "Area effect cloud", new String[]{"AreaEffectCloud"}, "area_effect_cloud", 144),
    MINECART_HOPPER(96, "Minecart with Hopper", new String[]{"MinecartHopper"}, "minecart_with_hopper", 70),
    MINECART_TNT(97, "Minecart with TNT", new String[]{"MinecartTNT"}, "minecart_with_tnt", 69),
    MINECART_CHEST(98, "Storage Minecart", new String[]{"MinecartChest"}, "minecart_chest", 35),
    LINGERING_POTION(101, "Lingering potion", new String[]{"LingeringPotion"}, "lingering_potion", 144),
    DROWNED(110, "Drowned", new String[]{"Drowned"}, "drowned", 133),

    CAT(122, "Cat", new String[]{"Cat"}, "cat", 142),//95 ; FireworksRocketEntity is not in the game yet
    PANDA(123, "Panda", new String[]{"Panda"}, "panda", 136),//95 ; FireworksRocketEntity is not in the game yet
    PARROT(1001, "Parrot", new String[]{"Parrot"}, "parrot", 109),

    //id 900+ is ignored for functions like map_filtering, these are placeholders for when the game adds more expected features.
    MINECART_SPAWNER(900, "Minecart with Spawner", new String[]{"MinecartSpawner"}, "minecart_with_spawner", 71),//99 ; MinecartSpawner is not yet in the game
    MINECART_COMMAND_BLOCK(901, "Minecart with Command KnownBlock", new String[]{"MinecartCommandBlock"}, "minecart_with_command_block", 78),//100 ; MinecartCommandBlock is not yet in the game
    MINECART_FURNACE(902, "Powered Minecart", new String[]{"MinecartFurnace"}, "minecart_furnace", 36),//101 ; MinecartFurnace is not yet in the game
    FIREWORKS_ROCKET_ENTITY(903, "Firework Rocket", new String[]{"FireworksRocketEntity"}, "fireworks_rocket", 121),//95 ; FireworksRocketEntity is not in the game yet
    UNKNOWN(999, "Unknown", new String[]{"Unknown"}, "unknown", 144);

    public final int id, sheetPos;
    public final String displayName, wikiName;
    public final String[] dataNames;
    public final String identifier;

    public Bitmap bitmap;

    Entity(int id, String displayName, String[] dataNames, String wikiName, int sheetPos) {
        this.id = id;
        this.displayName = displayName;
        this.dataNames = dataNames;
        this.wikiName = wikiName;
        this.sheetPos = sheetPos;
        this.identifier = "minecraft:" + wikiName;
    }

    @NonNull
    @Override
    public Bitmap getBitmap() {
        if (bitmap != null) return bitmap;
        if (Entity.UNKNOWN.bitmap == null) {
            Entity.UNKNOWN.bitmap = Bitmap.createBitmap(24, 24, Bitmap.Config.RGB_565);
        }
        return Entity.UNKNOWN.bitmap;
    }

    @NonNull
    @Override
    public NamedBitmapProvider getNamedBitmapProvider() {
        return this;
    }

    @NonNull
    @Override
    public String getBitmapDisplayName() {
        return this.displayName;
    }

    @NonNull
    @Override
    public String getBitmapDataName() {
        //First is for actual use, other dataNames are used for retrieval only.
        //This way, one could use the alternative name for manual input, without errors.
        return this.dataNames[0];
    }

    public static Entity getEntity(@NonNull String identifier) {
        int i = identifier.indexOf(':');
        if (i != -1) {
            if (!"minecraft".equals(identifier.substring(0, i))) return Entity.UNKNOWN;
            identifier = identifier.substring(i + 1);
        }
        for (Entity e : Entity.values()) {
            if (identifier.equals(e.wikiName)) return e;
        }
        return Entity.UNKNOWN;
    }

    public static Entity getEntity(int id) {
        for (Entity e : Entity.values()) {
            if (id == e.id) return e;
        }
        return Entity.UNKNOWN;
    }


    public static void loadEntityBitmaps(AssetManager assetManager) throws IOException {
        Bitmap sheet = BitmapFactory.decodeStream(assetManager.open("entity_wiki.png"));
        int w = sheet.getWidth();
        int tileSize = 16;
        for (Entity e : Entity.values()) {
            if (e.bitmap == null && e.sheetPos >= 0) {
                //sheetpos; first sprite has pos 1.
                int p = (e.sheetPos - 1) * tileSize;
                int x = p % w;
                int y = ((p - x) / w) * tileSize;
                //read tile from sheet, scale to 32x32
                e.bitmap = Bitmap.createScaledBitmap(Bitmap.createBitmap(sheet, x, y, tileSize, tileSize, null, false), 32, 32, false);
            }
        }
    }

}
