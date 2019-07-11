package com.mithrilmania.blocktopograph.map;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;

import androidx.annotation.NonNull;

import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.util.ColorWrapper;
import com.mithrilmania.blocktopograph.util.NamedBitmapProvider;
import com.mithrilmania.blocktopograph.util.NamedBitmapProviderHandle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public enum Item implements NamedBitmapProviderHandle, NamedBitmapProvider {

    /*
     * ==============================
     *       Blocks
     * ==============================
     */

    I_256_0_IRON_SHOVEL("iron_shovel", null, 256, 0, "items/iron_shovel.png"),
    I_257_0_IRON_PICKAXE("iron_pickaxe", null, 257, 0, "items/iron_pickaxe.png"),
    I_258_0_IRON_AXE("iron_axe", null, 258, 0, "items/iron_axe.png"),
    I_259_0_FLINT_AND_STEEL("flint_and_steel", null, 259, 0, "items/flint_and_steel.png"),
    I_260_0_APPLE("apple", null, 260, 0, "items/apple.png"),
    I_261_0_BOW("bow", null, 261, 0, "items/bow.png"),
    I_262_0_ARROW("arrow", null, 262, 0, "items/arrow.png"),
    I_263_0_COAL_COAL("coal", "coal", 263, 0, "items/coal_coal.png"),
    I_263_1_COAL_CHARCOAL("coal", "charcoal", 263, 1, "items/coal_charcoal.png"),
    I_264_0_DIAMOND("diamond", null, 264, 0, "items/diamond.png"),
    I_265_0_IRON_INGOT("iron_ingot", null, 265, 0, "items/iron_ingot.png"),
    I_266_0_GOLD_INGOT("gold_ingot", null, 266, 0, "items/gold_ingot.png"),
    I_267_0_IRON_SWORD("iron_sword", null, 267, 0, "items/iron_sword.png"),
    I_268_0_WOODEN_SWORD("wooden_sword", null, 268, 0, "items/wooden_sword.png"),
    I_269_0_WOODEN_SHOVEL("wooden_shovel", null, 269, 0, "items/wooden_shovel.png"),
    I_270_0_WOODEN_PICKAXE("wooden_pickaxe", null, 270, 0, "items/wooden_pickaxe.png"),
    I_271_0_WOODEN_AXE("wooden_axe", null, 271, 0, "items/wooden_axe.png"),
    I_272_0_STONE_SWORD("stone_sword", null, 272, 0, "items/stone_sword.png"),
    I_273_0_STONE_SHOVEL("stone_shovel", null, 273, 0, "items/stone_shovel.png"),
    I_274_0_STONE_PICKAXE("stone_pickaxe", null, 274, 0, "items/stone_pickaxe.png"),
    I_275_0_STONE_AXE("stone_axe", null, 275, 0, "items/stone_axe.png"),
    I_276_0_DIAMOND_SWORD("diamond_sword", null, 276, 0, "items/diamond_sword.png"),
    I_277_0_DIAMOND_SHOVEL("diamond_shovel", null, 277, 0, "items/diamond_shovel.png"),
    I_278_0_DIAMOND_PICKAXE("diamond_pickaxe", null, 278, 0, "items/diamond_pickaxe.png"),
    I_279_0_DIAMOND_AXE("diamond_axe", null, 279, 0, "items/diamond_axe.png"),
    I_280_0_STICK("stick", null, 280, 0, "items/stick.png"),
    I_281_0_BOWL("bowl", null, 281, 0, "items/bowl.png"),
    I_282_0_MUSHROOM_STEW("mushroom_stew", null, 282, 0, "items/mushroom_stew.png"),
    I_283_0_GOLDEN_SWORD("golden_sword", null, 283, 0, "items/golden_sword.png"),
    I_284_0_GOLDEN_SHOVEL("golden_shovel", null, 284, 0, "items/golden_shovel.png"),
    I_285_0_GOLDEN_PICKAXE("golden_pickaxe", null, 285, 0, "items/golden_pickaxe.png"),
    I_286_0_GOLDEN_AXE("golden_axe", null, 286, 0, "items/golden_axe.png"),
    I_287_0_STRING("string", null, 287, 0, "items/string.png"),
    I_288_0_FEATHER("feather", null, 288, 0, "items/feather.png"),
    I_289_0_GUNPOWDER("gunpowder", null, 289, 0, "items/gunpowder.png"),
    I_290_0_WOODEN_HOE("wooden_hoe", null, 290, 0, "items/wooden_hoe.png"),
    I_291_0_STONE_HOE("stone_hoe", null, 291, 0, "items/stone_hoe.png"),
    I_292_0_IRON_HOE("iron_hoe", null, 292, 0, "items/iron_hoe.png"),
    I_293_0_DIAMOND_HOE("diamond_hoe", null, 293, 0, "items/diamond_hoe.png"),
    I_294_0_GOLDEN_HOE("golden_hoe", null, 294, 0, "items/golden_hoe.png"),
    I_295_0_WHEAT_SEEDS("wheat_seeds", null, 295, 0, "items/wheat_seeds.png"),
    I_296_0_WHEAT("wheat", null, 296, 0, "items/wheat.png"),
    I_297_0_BREAD("bread", null, 297, 0, "items/bread.png"),
    I_298_0_LEATHER_HELMET("leather_helmet", null, 298, 0, "items/leather_helmet.png"),
    I_299_0_LEATHER_CHESTPLATE("leather_chestplate", null, 299, 0, "items/leather_chestplate.png"),
    I_300_0_LEATHER_LEGGINGS("leather_leggings", null, 300, 0, "items/leather_leggings.png"),
    I_301_0_LEATHER_BOOTS("leather_boots", null, 301, 0, "items/leather_boots.png"),
    I_302_0_CHAINMAIL_HELMET("chainmail_helmet", null, 302, 0, "items/chainmail_helmet.png"),
    I_303_0_CHAINMAIL_CHESTPLATE("chainmail_chestplate", null, 303, 0, "items/chainmail_chestplate.png"),
    I_304_0_CHAINMAIL_LEGGINGS("chainmail_leggings", null, 304, 0, "items/chainmail_leggings.png"),
    I_305_0_CHAINMAIL_BOOTS("chainmail_boots", null, 305, 0, "items/chainmail_boots.png"),
    I_306_0_IRON_HELMET("iron_helmet", null, 306, 0, "items/iron_helmet.png"),
    I_307_0_IRON_CHESTPLATE("iron_chestplate", null, 307, 0, "items/iron_chestplate.png"),
    I_308_0_IRON_LEGGINGS("iron_leggings", null, 308, 0, "items/iron_leggings.png"),
    I_309_0_IRON_BOOTS("iron_boots", null, 309, 0, "items/iron_boots.png"),
    I_310_0_DIAMOND_HELMET("diamond_helmet", null, 310, 0, "items/diamond_helmet.png"),
    I_311_0_DIAMOND_CHESTPLATE("diamond_chestplate", null, 311, 0, "items/diamond_chestplate.png"),
    I_312_0_DIAMOND_LEGGINGS("diamond_leggings", null, 312, 0, "items/diamond_leggings.png"),
    I_313_0_DIAMOND_BOOTS("diamond_boots", null, 313, 0, "items/diamond_boots.png"),
    I_314_0_GOLDEN_HELMET("golden_helmet", null, 314, 0, "items/golden_helmet.png"),
    I_315_0_GOLDEN_CHESTPLATE("golden_chestplate", null, 315, 0, "items/golden_chestplate.png"),
    I_316_0_GOLDEN_LEGGINGS("golden_leggings", null, 316, 0, "items/golden_leggings.png"),
    I_317_0_GOLDEN_BOOTS("golden_boots", null, 317, 0, "items/golden_boots.png"),
    I_318_0_FLINT("flint", null, 318, 0, "items/flint.png"),
    I_319_0_PORKCHOP("porkchop", null, 319, 0, "items/porkchop.png"),
    I_320_0_COOKED_PORKCHOP("cooked_porkchop", null, 320, 0, "items/cooked_porkchop.png"),
    I_321_0_PAINTING("painting", null, 321, 0, "items/painting.png"),
    I_322_0_GOLDEN_APPLE("golden_apple", null, 322, 0, "items/golden_apple.png"),
    I_323_0_SIGN("sign", null, 323, 0, "items/sign.png"),
    I_324_0_WOODEN_DOOR("wooden_door", null, 324, 0, "items/wooden_door.png"),
    I_325_0_BUCKET_BUCKET("bucket", "bucket", 325, 0, "items/bucket_bucket.png"),
    I_325_1_BUCKET_MILK("bucket", "milk", 325, 1, "items/bucket_milk.png"),
    I_325_8_BUCKET_BUCKET_WATER("bucket", "bucket_water", 325, 8, "items/bucket_bucket_water.png"),
    I_325_10_BUCKET_BUCKET_LAVA("bucket", "bucket_lava", 325, 10, "items/bucket_bucket_lava.png"),
    I_328_0_MINECART("minecart", null, 328, 0, "items/minecart.png"),
    I_329_0_SADDLE("saddle", null, 329, 0, "items/saddle.png"),
    I_330_0_IRON_DOOR("iron_door", null, 330, 0, "items/iron_door.png"),
    I_331_0_REDSTONE("redstone", null, 331, 0, "items/redstone.png"),
    I_332_0_SNOWBALL("snowball", null, 332, 0, "items/snowball.png"),
    I_333_0_BOAT_OAK("boat", "oak", 333, 0, "items/boat_oak.png"),
    I_333_1_BOAT_SPRUCE("boat", "spruce", 333, 1, "items/boat_spruce.png"),
    I_333_2_BOAT_BIRCH("boat", "birch", 333, 2, "items/boat_birch.png"),
    I_333_3_BOAT_JUNGLE("boat", "jungle", 333, 3, "items/boat_jungle.png"),
    I_333_4_BOAT_ACACIA("boat", "acacia", 333, 4, "items/boat_acacia.png"),
    I_333_5_BOAT_BIG_OAK("boat", "big_oak", 333, 5, "items/boat_big_oak.png"),
    I_334_0_LEATHER("leather", null, 334, 0, "items/leather.png"),
    I_336_0_BRICK("brick", null, 336, 0, "items/brick.png"),
    I_337_0_CLAY_BALL("clay_ball", null, 337, 0, "items/clay_ball.png"),
    I_338_0_REEDS("reeds", null, 338, 0, "items/reeds.png"),
    I_339_0_PAPER("paper", null, 339, 0, "items/paper.png"),
    I_340_0_BOOK("book", null, 340, 0, "items/book.png"),
    I_341_0_SLIME_BALL("slime_ball", null, 341, 0, "items/slime_ball.png"),
    I_342_0_CHEST_MINECART("chest_minecart", null, 342, 0, "items/chest_minecart.png"),
    I_344_0_EGG("egg", null, 344, 0, "items/egg.png"),
    I_345_0_COMPASS("compass", null, 345, 0, "items/compass.png"),
    I_346_0_FISHING_ROD("fishing_rod", null, 346, 0, "items/fishing_rod.png"),
    I_347_0_CLOCK("clock", null, 347, 0, "items/clock.png"),
    I_348_0_GLOWSTONE_DUST("glowstone_dust", null, 348, 0, "items/glowstone_dust.png"),
    I_349_0_FISH("fish", null, 349, 0, "items/fish.png"),
    I_350_0_COOKED_FISH("cooked_fish", null, 350, 0, "items/cooked_fish.png"),
    I_351_0_DYE_BLACKINKSAC("dye", "black", 351, 0, "items/dye_powder_black.png"),
    I_351_1_DYE_RED("dye", "red", 351, 1, "items/dye_powder_red.png"),
    I_351_2_DYE_GREEN("dye", "green", 351, 2, "items/dye_powder_green.png"),
    I_351_3_DYE_BROWNCOCOABEANS("dye", "brown", 351, 3, "items/dye_powder_brown.png"),
    I_351_4_DYE_BLUE("dye", "blue", 351, 4, "items/dye_powder_blue.png"),
    I_351_5_DYE_PURPLE("dye", "purple", 351, 5, "items/dye_powder_purple.png"),
    I_351_6_DYE_CYAN("dye", "cyan", 351, 6, "items/dye_powder_cyan.png"),
    I_351_7_DYE_SILVER("dye", "silver", 351, 7, "items/dye_powder_silver.png"),
    I_351_8_DYE_GRAY("dye", "gray", 351, 8, "items/dye_powder_gray.png"),
    I_351_9_DYE_PINK("dye", "pink", 351, 9, "items/dye_powder_pink.png"),
    I_351_10_DYE_LIME("dye", "lime", 351, 10, "items/dye_powder_lime.png"),
    I_351_11_DYE_YELLOW("dye", "yellow", 351, 11, "items/dye_powder_yellow.png"),
    I_351_12_DYE_LIGHT_BLUE("dye", "light_blue", 351, 12, "items/dye_powder_light_blue.png"),
    I_351_13_DYE_MAGENTA("dye", "magenta", 351, 13, "items/dye_powder_magenta.png"),
    I_351_14_DYE_ORANGE("dye", "orange", 351, 14, "items/dye_powder_orange.png"),
    I_351_15_DYE_WHITEBONEMEAL("dye", "white", 351, 15, "items/dye_powder_white.png"),
    I_352_0_BONE("bone", null, 352, 0, "items/bone.png"),
    I_353_0_SUGAR("sugar", null, 353, 0, "items/sugar.png"),
    I_354_0_CAKE("cake", null, 354, 0, "items/cake.png"),
    I_355_0_BED("bed", null, 355, 0, "items/bed.png"),
    I_356_0_REPEATER("repeater", null, 356, 0, "items/repeater.png"),
    I_357_0_COOKIE("cookie", null, 357, 0, "items/cookie.png"),
    I_358_0_MAP_FILLED("map_filled", null, 358, 0, "items/map_filled.png"),
    I_359_0_SHEARS("shears", null, 359, 0, "items/shears.png"),
    I_360_0_MELON("melon", null, 360, 0, "items/melon.png"),
    I_361_0_PUMPKIN_SEEDS("pumpkin_seeds", null, 361, 0, "items/pumpkin_seeds.png"),
    I_362_0_MELON_SEEDS("melon_seeds", null, 362, 0, "items/melon_seeds.png"),
    I_363_0_BEEF("beef", null, 363, 0, "items/beef.png"),
    I_364_0_COOKED_BEEF("cooked_beef", null, 364, 0, "items/cooked_beef.png"),
    I_365_0_CHICKEN("chicken", null, 365, 0, "items/chicken.png"),
    I_366_0_COOKED_CHICKEN("cooked_chicken", null, 366, 0, "items/cooked_chicken.png"),
    I_367_0_ROTTEN_FLESH("rotten_flesh", null, 367, 0, "items/rotten_flesh.png"),
    I_368_0_ENDER_PEARL("ender_pearl", null, 368, 0, "items/ender_pearl.png"),
    I_369_0_BLAZE_ROD("blaze_rod", null, 369, 0, "items/blaze_rod.png"),
    I_370_0_GHAST_TEAR("ghast_tear", null, 370, 0, "items/ghast_tear.png"),
    I_371_0_GOLD_NUGGET("gold_nugget", null, 371, 0, "items/gold_nugget.png"),
    I_372_0_NETHER_WART("nether_wart", null, 372, 0, "items/nether_wart.png"),
    I_373_0_POTION("potion", null, 373, 0, "items/potion.png"),
    I_374_0_GLASS_BOTTLE("glass_bottle", null, 374, 0, "items/glass_bottle.png"),
    I_375_0_SPIDER_EYE("spider_eye", null, 375, 0, "items/spider_eye.png"),
    I_376_0_FERMENTED_SPIDER_EYE("fermented_spider_eye", null, 376, 0, "items/fermented_spider_eye.png"),
    I_377_0_BLAZE_POWDER("blaze_powder", null, 377, 0, "items/blaze_powder.png"),
    I_378_0_MAGMA_CREAM("magma_cream", null, 378, 0, "items/magma_cream.png"),
    I_379_0_BREWING_STAND("brewing_stand", null, 379, 0, "items/brewing_stand.png"),
    I_380_0_CAULDRON("cauldron", null, 380, 0, "items/cauldron.png"),
    I_381_0_ENDER_EYE("ender_eye", null, 381, 0, "items/ender_eye.png"),
    I_382_0_SPECKLED_MELON("speckled_melon", null, 382, 0, "items/speckled_melon.png"),
    I_383_0_SPAWN_EGG("spawn_egg", null, 383, 0, "items/spawn_egg.png"),
    I_384_0_EXPERIENCE_BOTTLE("experience_bottle", null, 384, 0, "items/experience_bottle.png"),
    I_385_0_FIREBALL("fireball", null, 385, 0, "items/fireball.png"),
    I_388_0_EMERALD("emerald", null, 388, 0, "items/emerald.png"),
    I_389_0_FRAME("frame", null, 389, 0, "items/frame.png"),
    I_390_0_FLOWER_POT("flower_pot", null, 390, 0, "items/flower_pot.png"),
    I_391_0_CARROT("carrot", null, 391, 0, "items/carrot.png"),
    I_392_0_POTATO("potato", null, 392, 0, "items/potato.png"),
    I_393_0_BAKED_POTATO("baked_potato", null, 393, 0, "items/baked_potato.png"),
    I_394_0_POISONOUS_POTATO("poisonous_potato", null, 394, 0, "items/poisonous_potato.png"),
    I_395_0_EMPTYMAP("emptyMap", null, 395, 0, "items/emptyMap.png"),
    I_396_0_GOLDEN_CARROT("golden_carrot", null, 396, 0, "items/golden_carrot.png"),
    I_397_0_SKULL_SKELETON("skull", "skeleton", 397, 0, "items/skull_skeleton.png"),
    I_397_1_SKULL_WITHER("skull", "wither", 397, 1, "items/skull_wither.png"),
    I_397_2_SKULL_ZOMBIE("skull", "zombie", 397, 2, "items/skull_zombie.png"),
    I_397_3_SKULL_PLAYER("skull", "player", 397, 3, "items/skull_player.png"),
    I_397_4_SKULL_CREEPER("skull", "creeper", 397, 4, "items/skull_creeper.png"),
    I_397_5_SKULL_DRAGON("skull", "dragon", 397, 5, "items/skull_dragon.png"),
    I_398_0_CARROTONASTICK("carrotOnAStick", null, 398, 0, "items/carrotOnAStick.png"),
    I_399_0_NETHERSTAR("netherStar", null, 399, 0, "items/netherStar.png"),
    I_400_0_PUMPKIN_PIE("pumpkin_pie", null, 400, 0, "items/pumpkin_pie.png"),
    I_403_0_ENCHANTED_BOOK("enchanted_book", null, 403, 0, "items/enchanted_book.png"),
    I_404_0_COMPARATOR("comparator", null, 404, 0, "items/comparator.png"),
    I_405_0_NETHERBRICK("netherbrick", null, 405, 0, "items/netherbrick.png"),
    I_406_0_QUARTZ("quartz", null, 406, 0, "items/quartz.png"),
    I_407_0_TNT_MINECART("tnt_minecart", null, 407, 0, "items/tnt_minecart.png"),
    I_408_0_HOPPER_MINECART("hopper_minecart", null, 408, 0, "items/hopper_minecart.png"),
    I_409_0_PRISMARINE_SHARD("prismarine_shard", null, 409, 0, "items/prismarine_shard.png"),
    I_410_0_HOPPER("hopper", null, 410, 0, "items/hopper.png"),
    I_411_0_RABBIT("rabbit", null, 411, 0, "items/rabbit.png"),
    I_412_0_COOKED_RABBIT("cooked_rabbit", null, 412, 0, "items/cooked_rabbit.png"),
    I_413_0_RABBIT_STEW("rabbit_stew", null, 413, 0, "items/rabbit_stew.png"),
    I_414_0_RABBIT_FOOT("rabbit_foot", null, 414, 0, "items/rabbit_foot.png"),
    I_415_0_RABBIT_HIDE("rabbit_hide", null, 415, 0, "items/rabbit_hide.png"),
    I_416_0_HORSEARMORLEATHER("horsearmorleather", null, 416, 0, "items/horsearmorleather.png"),
    I_417_0_HORSEARMORIRON("horsearmoriron", null, 417, 0, "items/horsearmoriron.png"),
    I_418_0_HORSEARMORGOLD("horsearmorgold", null, 418, 0, "items/horsearmorgold.png"),
    I_419_0_HORSEARMORDIAMOND("horsearmordiamond", null, 419, 0, "items/horsearmordiamond.png"),
    I_420_0_LEAD("lead", null, 420, 0, "items/lead.png"),
    I_421_0_NAMETAG("nameTag", null, 421, 0, "items/nameTag.png"),
    I_422_0_PRISMARINE_CRYSTALS("prismarine_crystals", null, 422, 0, "items/prismarine_crystals.png"),
    I_423_0_MUTTONRAW("muttonRaw", null, 423, 0, "items/muttonRaw.png"),
    I_424_0_MUTTONCOOKED("muttonCooked", null, 424, 0, "items/muttonCooked.png"),
    I_426_0_END_CRYSTAL("end_crystal", null, 426, 0, "items/end_crystal.png"),
    I_427_0_SPRUCE_DOOR("spruce_door", null, 427, 0, "items/spruce_door.png"),
    I_428_0_BIRCH_DOOR("birch_door", null, 428, 0, "items/birch_door.png"),
    I_429_0_JUNGLE_DOOR("jungle_door", null, 429, 0, "items/jungle_door.png"),
    I_430_0_ACACIA_DOOR("acacia_door", null, 430, 0, "items/acacia_door.png"),
    I_431_0_DARK_OAK_DOOR("dark_oak_door", null, 431, 0, "items/dark_oak_door.png"),
    I_432_0_CHORUS_FRUIT("chorus_fruit", null, 432, 0, "items/chorus_fruit.png"),
    I_433_0_CHORUS_FRUIT_POPPED("chorus_fruit_popped", null, 433, 0, "items/chorus_fruit_popped.png"),
    I_437_0_DRAGON_BREATH("dragon_breath", null, 437, 0, "items/dragon_breath.png"),
    I_438_0_SPLASH_POTION("splash_potion", null, 438, 0, "items/splash_potion.png"),
    I_441_0_LINGERING_POTION("lingering_potion", null, 441, 0, "items/lingering_potion.png"),
    I_444_0_ELYTRA("elytra", null, 444, 0, "items/elytra.png"),
    I_457_0_BEETROOT("beetroot", null, 457, 0, "items/beetroot.png"),
    I_458_0_BEETROOT_SEEDS("beetroot_seeds", null, 458, 0, "items/seeds_beetroot.png"),
    I_459_0_BEETROOT_SOUP("beetroot_soup", null, 459, 0, "items/beetroot_soup.png"),
    I_460_0_SALMON("salmon", null, 460, 0, "items/fish_salmon.png"),
    I_461_0_CLOWNFISH("clownfish", null, 461, 0, "items/fish_clownfish.png"),
    I_462_0_PUFFERFISH("pufferfish", null, 462, 0, "items/fish_pufferfish.png"),
    I_463_0_COOKED_SALMON("cooked_salmon", null, 463, 0, "items/fish_salmon_cooked.png"),
    I_466_0_APPLEENCHANTED("apple_enchanted", null, 466, 0, "items/apple_golden.png"),
    I_454_0_BOARD_ONE_BY_ONE("board", "one_by_one", 454, 0, "items/chalkboard_small.png"),
    I_454_1_BOARD_TWO_BY_ONE("board", "two_by_one", 454, 1, "items/chalkboard_medium.png"),
    I_454_2_BOARD_THREE_BY_TWO("board", "three_by_two", 454, 2, "items/chalkboard_large.png"),
    I_456_0_PORTFOLIO("portfolio", null, 456, 0, "items/portfolio.png"),
    I_498_0_CAMERA("camera", null, 498, 0, "items/camera.png");

    private static final Map<String, Item> byDataName = new HashMap<>();
    private static final SparseArray<SparseArray<Item>> itemMap;

    static {
        itemMap = new SparseArray<>();
        SparseArray<Item> subMap;
        for (Item b : Item.values()) {
            subMap = itemMap.get(b.id);
            if (subMap == null) {
                subMap = new SparseArray<>();
                itemMap.put(b.id, subMap);
            }
            subMap.put(b.subId, b);
            if (b.subId == 0) byDataName.put(b.str, b);
            byDataName.put(b.str + "@" + b.subName, b);
        }
    }

    public final int id, subId;
    public final String str, subName, displayName, identifier;
    public final String texPath;
    public final ColorWrapper color;
    public final boolean hasBiomeShading;
    public Bitmap bitmap;

    Item(String name, String subName, int id, int subId, String texPath) {
        this.id = id;
        this.subId = subId;
        this.str = name;
        this.subName = subName;
        this.displayName = name + " " + subName;
        this.texPath = texPath;
        this.color = null;
        this.hasBiomeShading = false;
        this.identifier = "minecraft:" + subName;
    }

    public static Item getByDataName(String dataName) {
        return byDataName.get(dataName);
    }

    public static void loadBitmaps(AssetManager assetManager) throws IOException {
        for (Item b : Item.values()) {
            if (b.bitmap == null && b.texPath != null) {
                try {
                    b.bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(assetManager.open(b.texPath)), 32, 32, false);
                } catch (FileNotFoundException e) {
                    //TODO file-paths were generated from item names; some do not actually exist...
                    //Log.w("File not found! "+b.texPath);
                } catch (Exception e) {
                    Log.d(Item.class, e);
                }
            }
        }
    }

    public static Item getItem(int id, int meta) {
        if (id < 0) return null;
        SparseArray<Item> subMap = itemMap.get(id);
        if (subMap == null) return null;
        else return subMap.get(meta);
    }

    public static Item getItemWithLegacyId(int id) {
        if (id < 0) return null;
        SparseArray<Item> subMap = itemMap.get(id);
        if (subMap == null) return null;
        return subMap.valueAt(0);
    }

    public static Item getItem(int runtimeId) {
        int id = runtimeId >>> 8;
        int data = runtimeId & 0xf;
        return getItem(id, data);
    }

    @Override
    public Bitmap getBitmap() {
        return this.bitmap;
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
        return str + "@" + subName;
    }

}
