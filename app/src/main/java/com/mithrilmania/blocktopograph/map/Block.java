package com.mithrilmania.blocktopograph.map;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.util.NamedBitmapProvider;
import com.mithrilmania.blocktopograph.util.NamedBitmapProviderHandle;
import com.mithrilmania.blocktopograph.util.Color;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mithrilmania
 * <p>
 * ==========================
 * POCKET EDITION BLOCKS ONLY
 * ==========================
 * <p>
 * uvs are up to date with MCPE 0.14.0
 * <p>
 * --- Please attribute @mithrilmania for generating+updating this enum
 */
public enum Block implements NamedBitmapProviderHandle, NamedBitmapProvider {

    /*
     * ==============================
     *       Blocks
     * ==============================
     */

    B_0_0_AIR("air", null, 0, 0, null, 0x00000000, false, null, false),
    B_1_0_STONE("stone", "stone", 1, 0, "blocks/stone.png", 0xff464646, false, null, false),
    B_1_1_STONE_GRANITE("stone", "granite", 1, 1, "blocks/stone_granite.png", 0xff8c7167, false, null, false),
    B_1_2_STONE_GRANITE_SMOOTH("stone", "granite_smooth", 1, 2, "blocks/stone_granite_smooth.png", 0xff946251, false, null, false),
    B_1_3_STONE_DIORITE("stone", "diorite", 1, 3, "blocks/stone_diorite.png", 0xffc6c6c6, false, null, false),
    B_1_4_STONE_DIORITE_SMOOTH("stone", "diorite_smooth", 1, 4, "blocks/stone_diorite_smooth.png", 0xffbebec1, false, null, false),
    B_1_5_STONE_ANDESITE("stone", "andesite", 1, 5, "blocks/stone_andesite.png", 0xff797777, false, null, false),
    B_1_6_STONE_ANDESITE_SMOOTH("stone", "andesite_smooth", 1, 6, "blocks/stone_andesite_smooth.png", 0xff828382, false, null, false),
    B_2_0_GRASS("grass", null, 2, 0, "blocks/grass_side_carried.png", 0xff939393, true, null, false),
    B_3_0_DIRT("dirt", null, 3, 0, "blocks/dirt.png", 0xff866043, true, null, false),
    B_4_0_COBBLESTONE("cobblestone", null, 4, 0, "blocks/cobblestone.png", 0xff7a7a7a, false, null, false),
    B_5_0_PLANKS_OAK("planks", "oak", 5, 0, "blocks/planks_oak.png", 0xff9c7f4e, false, null, false),
    B_5_1_PLANKS_SPRUCE("planks", "spruce", 5, 1, "blocks/planks_spruce.png", 0xff674d2e, false, null, false),
    B_5_2_PLANKS_BIRCH("planks", "birch", 5, 2, "blocks/planks_birch.png", 0xffc3b37b, false, null, false),
    B_5_3_PLANKS_JUNGLE("planks", "jungle", 5, 3, "blocks/planks_jungle.png", 0xff9a6e4d, false, null, false),
    B_5_4_PLANKS_ACACIA("planks", "acacia", 5, 4, "blocks/planks_acacia.png", 0xffaa5a2f, false, null, false),
    B_5_5_PLANKS_BIG_OAK("planks", "big_oak", 5, 5, "blocks/planks_big_oak.png", 0xff3b260f, false, null, false),
    B_6_0_SAPLING_OAK("sapling", "oak", 6, 0, "blocks/sapling_oak.png", 0x6b476625, false, null, false),
    B_6_1_SAPLING_SPRUCE("sapling", "spruce", 6, 1, "blocks/sapling_spruce.png", 0x53333a21, false, null, false),
    B_6_2_SAPLING_BIRCH("sapling", "birch", 6, 2, "blocks/sapling_birch.png", 0x6b769654, false, null, false),
    B_6_3_SAPLING_JUNGLE("sapling", "jungle", 6, 3, "blocks/sapling_jungle.png", 0x55305612, false, null, false),
    B_6_4_SAPLING_ACACIA("sapling", "acacia", 6, 4, "blocks/sapling_acacia.png", 0xff718919, false, null, false),
    B_6_5_SAPLING_BIG_OAK("sapling", "big_oak", 6, 5, "blocks/sapling_roofed_oak.png", 0xff6f522d, false, null, false),
    B_7_0_BEDROCK("bedrock", null, 7, 0, "blocks/bedrock.png", 0xff535353, false, null, false),
    B_8_0_FLOWING_WATER("flowing_water", null, 8, 0, "blocks/water_flow.png", 0x802e43f4, false, null, false),
    B_9_0_WATER("water", null, 9, 0, "blocks/water_still.png", 0x802e43f4, false, null, false),
    B_10_0_FLOWING_LAVA("flowing_lava", null, 10, 0, "blocks/lava_flow.png", 0xf0d45a12, false, null, false),
    B_11_0_LAVA("lava", null, 11, 0, "blocks/lava_still.png", 0xf0d45a12, false, null, false),
    B_12_0_SAND_DEFAULT("sand", "default", 12, 0, "blocks/sand.png", 0xffdbd3a0, false, null, false),
    B_12_1_SAND_RED("sand", "red", 12, 1, "blocks/red_sand.png", 0xffa7531f, false, null, false),
    B_13_0_GRAVEL("gravel", null, 13, 0, "blocks/gravel.png", 0xff7e7c7a, false, null, false),
    B_14_0_GOLD_ORE("gold_ore", null, 14, 0, "blocks/gold_ore.png", 0xff8f8b7c, false, null, false),
    B_15_0_IRON_ORE("iron_ore", null, 15, 0, "blocks/iron_ore.png", 0xff87827e, false, null, false),
    B_16_0_COAL_ORE("coal_ore", null, 16, 0, "blocks/coal_ore.png", 0xff737373, false, null, false),
    B_17_0_LOG_OAK("log", "oak", 17, 0, "blocks/log_oak.png", 0xff9a7d4d, false, null, false),
    B_17_1_LOG_SPRUCE("log", "spruce", 17, 1, "blocks/log_spruce.png", 0xff9a7d4d, false, null, false),
    B_17_2_LOG_BIRCH("log", "birch", 17, 2, "blocks/log_birch.png", 0xff9a7d4d, false, null, false),
    B_17_3_LOG_JUNGLE("log", "jungle", 17, 3, "blocks/log_jungle.png", 0xff9a7d4d, false, null, false),
    B_18_0_LEAVES_OAK("leaves", "oak", 18, 0, "blocks/leaves_oak.png", 0xff878787, true, null, false),
    B_18_1_LEAVES_SPRUCE("leaves", "spruce", 18, 1, "blocks/leaves_spruce.png", 0xff132613, true, null, false),
    B_18_2_LEAVES_BIRCH("leaves", "birch", 18, 2, "blocks/leaves_birch.png", 0xff283816, true, null, false),
    B_18_3_LEAVES_JUNGLE("leaves", "jungle", 18, 3, "blocks/leaves_jungle.png", 0xff918e86, true, null, false),
    B_19_0_SPONGE_DRY("sponge", "dry", 19, 0, "blocks/sponge_dry.png", 0xffb6b639, false, null, false),
    B_19_1_SPONGE_WET("sponge", "wet", 19, 1, "blocks/sponge_wet.png", 0xff9b9a33, false, null, false),
    B_20_0_GLASS("glass", null, 20, 0, "blocks/glass.png", 0x46daf0f4, false, null, false),
    B_21_0_LAPIS_ORE("lapis_ore", null, 21, 0, "blocks/lapis_ore.png", 0xff667086, false, null, false),
    B_22_0_LAPIS_BLOCK("lapis_block", null, 22, 0, "blocks/lapis_block.png", 0xff1d47a5, false, null, false),
    B_23_0_DISPENSER("dispenser", null, 23, 0, "blocks/dispenser.png", 0xff606060, false, null, false),
    B_24_0_SANDSTONE_DEFAULT("sandstone", "default", 24, 0, "blocks/sandstone_default.png", 0xffdad29e, false, null, false),
    B_24_1_SANDSTONE_CHISELED("sandstone", "chiseled", 24, 1, "blocks/sandstone_chiseled.png", 0xffdad1a2, false, null, false),
    B_24_2_SANDSTONE_SMOOTH("sandstone", "smooth", 24, 2, "blocks/sandstone_smooth.png", 0xffdad1a2, false, null, false),
    B_25_0_NOTEBLOCK("noteblock", null, 25, 0, "blocks/noteblock.png", 0xff644332, false, null, false),
    B_26_0_BED("bed", null, 26, 0, "blocks/bed.png", 0xff8e1616, false, null, false),
    B_27_0_GOLDEN_RAIL("golden_rail", null, 27, 0, "blocks/golden_rail.png", 0xab9a6846, false, null, false),
    B_28_0_DETECTOR_RAIL("detector_rail", null, 28, 0, "blocks/detector_rail.png", 0x9b786559, false, null, false),
    B_29_0_STICKY_PISTON("sticky_piston", null, 29, 0, "blocks/sticky_piston.png", 0xff8d9263, false, null, false),
    B_30_0_WEB("web", null, 30, 0, "blocks/web.png", 0x68dcdcdc, false, null, false),
    B_31_1_TALLGRASS_FERN("tallgrass", "fern", 31, 1, "blocks/tallgrass_fern.png", 0xff747474, true, null, false),
    B_31_2_TALLGRASS_GRASS("tallgrass", "grass", 31, 2, "blocks/tallgrass_grass.png", 0x4e787878, true, null, false),
    B_32_0_DEADBUSH("deadbush", null, 32, 0, "blocks/deadbush.png", 0x517b4f19, false, null, false),
    B_33_0_PISTON("piston", null, 33, 0, "blocks/piston.png", 0xff998159, false, null, false),
    B_34_0_PISTONARMCOLLISION("pistonArmCollision", null, 34, 0, "blocks/pistonArmCollision.png", 0xff9c7f4e, false, null, false),
    B_35_0_WOOL_WHITE("wool", "white", 35, 0, "blocks/wool_colored_white.png", 0xffdddddd, false, null, false),
    B_35_1_WOOL_ORANGE("wool", "orange", 35, 1, "blocks/wool_colored_orange.png", 0xffdb7d3e, false, null, false),
    B_35_2_WOOL_MAGENTA("wool", "magenta", 35, 2, "blocks/wool_colored_magenta.png", 0xffb350bc, false, null, false),
    B_35_3_WOOL_LIGHT_BLUE("wool", "light_blue", 35, 3, "blocks/wool_colored_light_blue.png", 0xff6a8ac9, false, null, false),
    B_35_4_WOOL_YELLOW("wool", "yellow", 35, 4, "blocks/wool_colored_yellow.png", 0xffb1a627, false, null, false),
    B_35_5_WOOL_LIME("wool", "lime", 35, 5, "blocks/wool_colored_lime.png", 0xff41ae38, false, null, false),
    B_35_6_WOOL_PINK("wool", "pink", 35, 6, "blocks/wool_colored_pink.png", 0xffd08499, false, null, false),
    B_35_7_WOOL_GRAY("wool", "gray", 35, 7, "blocks/wool_colored_gray.png", 0xff404040, false, null, false),
    B_35_8_WOOL_SILVER("wool", "silver", 35, 8, "blocks/wool_colored_silver.png", 0xff9aa1a1, false, null, false),
    B_35_9_WOOL_CYAN("wool", "cyan", 35, 9, "blocks/wool_colored_cyan.png", 0xff2e6e89, false, null, false),
    B_35_10_WOOL_PURPLE("wool", "purple", 35, 10, "blocks/wool_colored_purple.png", 0xff7e3db5, false, null, false),
    B_35_11_WOOL_BLUE("wool", "blue", 35, 11, "blocks/wool_colored_blue.png", 0xff2e388d, false, null, false),
    B_35_12_WOOL_BROWN("wool", "brown", 35, 12, "blocks/wool_colored_brown.png", 0xff4f321f, false, null, false),
    B_35_13_WOOL_GREEN("wool", "green", 35, 13, "blocks/wool_colored_green.png", 0xff35461b, false, null, false),
    B_35_14_WOOL_RED("wool", "red", 35, 14, "blocks/wool_colored_red.png", 0xff963430, false, null, false),
    B_35_15_WOOL_BLACK("wool", "black", 35, 15, "blocks/wool_colored_black.png", 0xff191616, false, null, false),
    B_37_0_YELLOW_FLOWER("yellow_flower", null, 37, 0, "blocks/yellow_flower.png", 0x1e6ca200, false, null, false),
    B_38_0_RED_FLOWER_POPPY("red_flower", "poppy", 38, 0, "blocks/flower_poppy.png", 0x1d8a2b0d, false, null, false),
    B_38_1_RED_FLOWER_BLUE_ORCHID("red_flower", "blue_orchid", 38, 1, "blocks/flower_blue_orchid.png", 0x1d188fd3, false, null, false),
    B_38_2_RED_FLOWER_ALLIUM("red_flower", "allium", 38, 2, "blocks/flower_allium.png", 0x1ddbb7f8, false, null, false),
    B_38_3_RED_FLOWER_HOUSTONIA("red_flower", "houstonia", 38, 3, "blocks/flower_houstonia.png", 0x1defef99, false, null, false),
    B_38_4_RED_FLOWER_TULIP_RED("red_flower", "tulip_red", 38, 4, "blocks/flower_tulip_red.png", 0x1dbd2604, false, null, false),
    B_38_5_RED_FLOWER_TULIP_ORANGE("red_flower", "tulip_orange", 38, 5, "blocks/flower_tulip_orange.png", 0x1dd06713, false, null, false),
    B_38_6_RED_FLOWER_TULIP_WHITE("red_flower", "tulip_white", 38, 6, "blocks/flower_tulip_white.png", 0x1df9f9f9, false, null, false),
    B_38_7_RED_FLOWER_TULIP_PINK("red_flower", "tulip_pink", 38, 7, "blocks/flower_tulip_pink.png", 0x1dbeb3be, false, null, false),
    B_38_8_RED_FLOWER_OXEYE_DAISY("red_flower", "oxeye_daisy", 38, 8, "blocks/flower_oxeye_daisy.png", 0x1ddadada, false, null, false),
    B_39_0_BROWN_MUSHROOM("brown_mushroom", null, 39, 0, "blocks/brown_mushroom.png", 0x198a6953, false, null, false),
    B_40_0_RED_MUSHROOM("red_mushroom", null, 40, 0, "blocks/red_mushroom.png", 0x21c33538, false, null, false),
    B_41_0_GOLD_BLOCK("gold_block", null, 41, 0, "blocks/gold_block.png", 0xfff9ec4e, false, null, false),
    B_42_0_IRON_BLOCK("iron_block", null, 42, 0, "blocks/iron_block.png", 0xffdbdbdb, false, null, false),
    B_43_0_DOUBLE_STONE_SLAB_STONE("double_stone_slab", "stone", 43, 0, "blocks/double_stone_slab_stone.png", 0xff9f9f9f, false, null, false),
    B_43_1_DOUBLE_STONE_SLAB_SAND("double_stone_slab", "sand", 43, 1, "blocks/double_stone_slab_sand.png", 0xffdad29e, false, null, false),
    B_43_2_DOUBLE_STONE_SLAB_WOOD("double_stone_slab", "wood", 43, 2, "blocks/double_stone_slab_wood.png", 0xff9c7f4e, false, null, false),
    B_43_3_DOUBLE_STONE_SLAB_COBBLE("double_stone_slab", "cobble", 43, 3, "blocks/double_stone_slab_cobble.png", 0xff7a7a7a, false, null, false),
    B_43_4_DOUBLE_STONE_SLAB_BRICK("double_stone_slab", "brick", 43, 4, "blocks/double_stone_slab_brick.png", 0xff926356, false, null, false),
    B_43_5_DOUBLE_STONE_SLAB_SMOOTH_STONE_BRICK("double_stone_slab", "smooth_stone_brick", 43, 5, "blocks/double_stone_slab_smooth_stone_brick.png", 0xff7d7d7d, false, null, false),
    B_43_6_DOUBLE_STONE_SLAB_QUARTZ("double_stone_slab", "quartz", 43, 6, "blocks/double_stone_slab_quartz.png", 0xff2c161a, false, null, false),
    B_43_7_DOUBLE_STONE_SLAB_NETHER_BRICK("double_stone_slab", "nether_brick", 43, 7, "blocks/double_stone_slab_nether_brick.png", 0xffece9e2, false, null, false),
    B_43_8_DOUBLE_STONE_SLAB_RED_SANDSTONE("double_stone_slab", "red_sandstone", 43, 8, "blocks/double_stone_slab_red_sandstone.png", 0xff9f9f9f, false, null, false),
    B_44_0_STONE_SLAB_STONE("stone_slab", "stone", 44, 0, "blocks/stone_slab_side.png", 0xff9f9f9f, false, null, false),
    B_44_1_STONE_SLAB_SAND("stone_slab", "sand", 44, 1, "blocks/stone_slab_side.png", 0xffdad29e, false, null, false),
    B_44_2_STONE_SLAB_WOOD("stone_slab", "wood", 44, 2, "blocks/stone_slab_side.png", 0xff9c7f4e, false, null, false),
    B_44_3_STONE_SLAB_COBBLE("stone_slab", "cobble", 44, 3, "blocks/stone_slab_side.png", 0xff7a7a7a, false, null, false),
    B_44_4_STONE_SLAB_BRICK("stone_slab", "brick", 44, 4, "blocks/stone_slab_side.png", 0xff926356, false, null, false),
    B_44_5_STONE_SLAB_SMOOTH_STONE_BRICK("stone_slab", "smooth_stone_brick", 44, 5, "blocks/stone_slab_side.png", 0xff7d7d7d, false, null, false),
    B_44_6_STONE_SLAB_QUARTZ("stone_slab", "quartz", 44, 6, "blocks/stone_slab_side.png", 0xff2c161a, false, null, false),
    B_44_7_STONE_SLAB_NETHER_BRICK("stone_slab", "nether_brick", 44, 7, "blocks/stone_slab_side.png", 0xffece9e2, false, null, false),
    B_45_0_BRICK_BLOCK("brick_block", null, 45, 0, "blocks/brick_block.png", 0xff926356, false, null, false),
    B_46_0_TNT("tnt", null, 46, 0, "blocks/tnt.png", 0xff82412f, false, null, false),
    B_47_0_BOOKSHELF("bookshelf", null, 47, 0, "blocks/bookshelf.png", 0xff6b5839, false, null, false),
    B_48_0_MOSSY_COBBLESTONE("mossy_cobblestone", null, 48, 0, "blocks/mossy_cobblestone.png", 0xff677967, false, null, false),
    B_49_0_OBSIDIAN("obsidian", null, 49, 0, "blocks/obsidian.png", 0xff14121d, false, null, false),
    B_50_0_TORCH("torch", null, 50, 0, "blocks/torch.png", 0x13826a3a, false, null, false),
    B_51_0_FIRE("fire", null, 51, 0, "blocks/fire.png", 0x8bd38c35, false, null, false),
    B_52_0_MOB_SPAWNER("mob_spawner", null, 52, 0, "blocks/mob_spawner.png", 0x9b1a2731, false, null, false),
    B_53_0_OAK_STAIRS("oak_stairs", null, 53, 0, "blocks/oak_stairs.png", 0xff9c7f4e, false, null, false),
    B_54_0_CHEST("chest", null, 54, 0, "blocks/chest_front.png", 0xc86f5739, false, null, false),
    B_55_0_REDSTONE_WIRE("redstone_wire", null, 55, 0, "blocks/redstone_wire.png", 0x80fa1010, false, null, false),
    B_56_0_DIAMOND_ORE("diamond_ore", null, 56, 0, "blocks/diamond_ore.png", 0xff818c8f, false, null, false),
    B_57_0_DIAMOND_BLOCK("diamond_block", null, 57, 0, "blocks/diamond_block.png", 0xff61dbd5, false, null, false),
    B_58_0_CRAFTING_TABLE("crafting_table", null, 58, 0, "blocks/crafting_table.png", 0xff6b472a, false, null, false),
    B_59_0_WHEAT("wheat", null, 59, 0, "blocks/wheat.png", 0x0500b312, false, null, false),
    B_60_0_FARMLAND("farmland", null, 60, 0, "blocks/farmland.png", 0xff734b2d, false, null, false),
    B_61_0_FURNACE("furnace", null, 61, 0, "blocks/furnace.png", 0xff606060, false, null, false),
    B_62_0_LIT_FURNACE("lit_furnace", null, 62, 0, "blocks/lit_furnace.png", 0xff606060, false, null, false),
    B_63_0_STANDING_SIGN("standing_sign", null, 63, 0, "blocks/standing_sign.png", 0x566f5739, false, null, false),
    B_64_0_WOODEN_DOOR("wooden_door", null, 64, 0, "blocks/wooden_door.png", 0xcf866733, false, null, false),
    B_65_0_LADDER("ladder", null, 65, 0, "blocks/ladder.png", 0x8f795f34, false, null, false),
    B_66_0_RAIL("rail", null, 66, 0, "blocks/rail.png", 0x8f796c58, false, null, false),
    B_67_0_STONE_STAIRS("stone_stairs", null, 67, 0, "blocks/stone_stairs.png", 0xff7a7a7a, false, null, false),
    B_68_0_WALL_SIGN("wall_sign", null, 68, 0, "blocks/wall_sign.png", 0x206f5739, false, null, false),
    B_69_0_LEVER("lever", null, 69, 0, "blocks/lever.png", 0x136a5940, false, null, false),
    B_70_0_STONE_PRESSURE_PLATE("stone_pressure_plate", null, 70, 0, "blocks/stone_pressure_plate.png", 0xff7d7d7d, false, null, false),
    B_71_0_IRON_DOOR("iron_door", null, 71, 0, "blocks/iron_door.png", 0xcfbababa, false, null, false),
    B_72_0_WOODEN_PRESSURE_PLATE("wooden_pressure_plate", null, 72, 0, "blocks/wooden_pressure_plate.png", 0xff9c7f4e, false, null, false),
    B_73_0_REDSTONE_ORE("redstone_ore", null, 73, 0, "blocks/redstone_ore.png", 0xff846b6b, false, null, false),
    B_74_0_LIT_REDSTONE_ORE("lit_redstone_ore", null, 74, 0, "blocks/lit_redstone_ore.png", 0xff846b6b, false, null, false),
    B_75_0_UNLIT_REDSTONE_TORCH("unlit_redstone_torch", null, 75, 0, "blocks/unlit_redstone_torch.png", 0x465d3e26, false, null, false),
    B_76_0_REDSTONE_TORCH("redstone_torch", null, 76, 0, "blocks/redstone_torch.png", 0x46a74b29, false, null, false),
    B_77_0_STONE_BUTTON("stone_button", null, 77, 0, "blocks/stone_button.png", 0x28565656, false, null, false),
    B_78_0_SNOW_LAYER("snow_layer", null, 78, 0, "blocks/snow_layer.png", 0xffeffbfb, false, null, false),
    B_79_0_ICE("ice", null, 79, 0, "blocks/ice.png", 0x9f7dadff, false, null, false),
    B_80_0_SNOW("snow", null, 80, 0, "blocks/snow.png", 0xffeffbfb, false, null, false),
    B_81_0_CACTUS("cactus", null, 81, 0, "blocks/cactus.png", 0xc30d6318, false, null, false),
    B_82_0_CLAY("clay", null, 82, 0, "blocks/clay.png", 0xff9ea4b0, false, null, false),
    B_83_0_REEDS("reeds", null, 83, 0, "blocks/reeds.png", 0x8c94c065, false, null, false),

    B_84_0_JUKEBOX("jukebox", null, 84, 0, "blocks/fence_birch_fence.png", 0x8f463822, false, null, false),

    B_85_0_FENCE_FENCE("fence", "fence", 85, 0, "blocks/fence_fence.png", 0x8f463822, false, null, false),
    B_85_1_FENCE_SPRUCE_FENCE("fence", "spruce_fence", 85, 1, "blocks/fence_spruce_fence.png", 0x8f463822, false, null, false),
    B_85_2_FENCE_BIRCH_FENCE("fence", "birch_fence", 85, 2, "blocks/fence_birch_fence.png", 0x8f463822, false, null, false),
    B_85_3_FENCE_JUNGLE_FENCE("fence", "jungle_fence", 85, 3, "blocks/fence_jungle_fence.png", 0x8f463822, false, null, false),
    B_85_4_FENCE_ACACIA_FENCE("fence", "acacia_fence", 85, 4, "blocks/fence_acacia_fence.png", 0x8f463822, false, null, false),
    B_85_5_FENCE_DARK_OAK_FENCE("fence", "dark_oak_fence", 85, 5, "blocks/fence_dark_oak_fence.png", 0x8f463822, false, null, false),
    B_86_0_PUMPKIN("pumpkin", null, 86, 0, "blocks/pumpkin.png", 0xffc07615, false, null, false),
    B_87_0_NETHERRACK("netherrack", null, 87, 0, "blocks/netherrack.png", 0xff6f3634, false, null, false),
    B_88_0_SOUL_SAND("soul_sand", null, 88, 0, "blocks/soul_sand.png", 0xff544033, false, null, false),
    B_89_0_GLOWSTONE("glowstone", null, 89, 0, "blocks/glowstone.png", 0xff8f7645, false, null, false),
    B_90_0_PORTAL("portal", null, 90, 0, "blocks/portal.png", 0xc8410491, false, null, false),
    B_91_0_LIT_PUMPKIN("lit_pumpkin", null, 91, 0, "blocks/lit_pumpkin.png", 0xffc07615, false, null, false),
    B_92_0_CAKE("cake", null, 92, 0, "blocks/cake.png", 0xc3e4cdce, false, null, false),
    B_93_0_UNPOWERED_REPEATER("unpowered_repeater", null, 93, 0, "blocks/unpowered_repeater.png", 0xff979393, false, null, false),
    B_94_0_POWERED_REPEATER("powered_repeater", null, 94, 0, "blocks/powered_repeater.png", 0xffa09393, false, null, false),
    B_95_0_INVISIBLEBEDROCK("invisibleBedrock", null, 95, 0, "blocks/invisibleBedrock.png", 0x3c282828, false, null, false),
    B_96_0_TRAPDOOR("trapdoor", null, 96, 0, "blocks/trapdoor.png", 0xdb7e5d2d, false, null, false),
    B_97_0_MONSTER_EGG_STONE("monster_egg", "stone", 97, 0, "blocks/monster_egg_stone.png", 0xff7d7d7d, false, null, false),
    B_97_1_MONSTER_EGG_COBBLE("monster_egg", "cobble", 97, 1, "blocks/monster_egg_cobble.png", 0xff7a7a7a, false, null, false),
    B_97_2_MONSTER_EGG_BRICK("monster_egg", "brick", 97, 2, "blocks/monster_egg_brick.png", 0xff7a7a7a, false, null, false),
    B_97_3_MONSTER_EGG_MOSSYBRICK("monster_egg", "mossybrick", 97, 3, "blocks/monster_egg_mossybrick.png", 0xff7b6651, false, null, false),
    B_97_4_MONSTER_EGG_CRACKEDBRICK("monster_egg", "crackedbrick", 97, 4, "blocks/monster_egg_crackedbrick.png", 0xff7b6651, false, null, false),
    B_97_5_MONSTER_EGG_CHISELEDBRICK("monster_egg", "chiseledbrick", 97, 5, "blocks/monster_egg_chiseledbrick.png", 0xff7b6651, false, null, false),
    B_98_0_STONEBRICK_DEFAULT("stonebrick", "default", 98, 0, "blocks/stonebrick_default.png", 0xff7a7a7a, false, null, false),
    B_98_1_STONEBRICK_MOSSY("stonebrick", "mossy", 98, 1, "blocks/stonebrick_mossy.png", 0xff72776a, false, null, false),
    B_98_2_STONEBRICK_CRACKED("stonebrick", "cracked", 98, 2, "blocks/stonebrick_cracked.png", 0xff767676, false, null, false),
    B_98_3_STONEBRICK_CHISELED("stonebrick", "chiseled", 98, 3, "blocks/stonebrick_chiseled.png", 0xff767676, false, null, false),
    B_98_4_STONEBRICK_SMOOTH("stonebrick", "smooth", 98, 4, "blocks/stonebrick_smooth.png", 0xff767676, false, null, false),
    B_99_0_BROWN_MUSHROOM_BLOCK("brown_mushroom_block", null, 99, 0, "blocks/brown_mushroom_block.png", 0xff8d6a53, false, null, false),
    B_100_0_RED_MUSHROOM_BLOCK("red_mushroom_block", null, 100, 0, "blocks/red_mushroom_block.png", 0xffb62524, false, null, false),
    B_101_0_IRON_BARS("iron_bars", null, 101, 0, "blocks/iron_bars.png", 0x736d6c6a, false, null, false),
    B_102_0_GLASS_PANE("glass_pane", null, 102, 0, "blocks/glass_pane.png", 0x1fd3eff4, false, null, false),
    B_103_0_MELON_BLOCK("melon_block", null, 103, 0, "blocks/melon_block.png", 0xff979924, false, null, false),
    B_104_0_PUMPKIN_STEM("pumpkin_stem", null, 104, 0, "blocks/pumpkin_stem.png", 0x1e87b759, false, null, false),
    B_105_0_MELON_STEM("melon_stem", null, 105, 0, "blocks/melon_stem.png", 0x1e87b759, false, null, false),
    B_106_0_VINE("vine", null, 106, 0, "blocks/vine.png", 0x8a6f6f6f, false, null, false),
    B_107_0_FENCE_GATE("fence_gate", null, 107, 0, "blocks/fence_gate.png", 0x7b463822, false, null, false),
    B_108_0_BRICK_STAIRS("brick_stairs", null, 108, 0, "blocks/brick_stairs.png", 0xff926356, false, null, false),
    B_109_0_STONE_BRICK_STAIRS("stone_brick_stairs", null, 109, 0, "blocks/stone_brick_stairs.png", 0xff7a7a7a, false, null, false),
    B_110_0_MYCELIUM("mycelium", null, 110, 0, "blocks/mycelium.png", 0xff6f6369, false, null, false),
    B_111_0_WATERLILY("waterlily", null, 111, 0, "blocks/waterlily.png", 0x93335a21, false, null, false),
    B_112_0_NETHER_BRICK("nether_brick", null, 112, 0, "blocks/nether_brick.png", 0xff2c161a, false, null, false),
    B_113_0_NETHER_BRICK_FENCE("nether_brick_fence", null, 113, 0, "blocks/nether_brick_fence.png", 0xff2c161a, false, null, false),
    B_114_0_NETHER_BRICK_STAIRS("nether_brick_stairs", null, 114, 0, "blocks/nether_brick_stairs.png", 0xff2c161a, false, null, false),
    B_115_0_NETHER_WART("nether_wart", null, 115, 0, "blocks/nether_wart.png", 0x2a6a0e1e, false, null, false),
    B_116_0_ENCHANTING_TABLE("enchanting_table", null, 116, 0, "blocks/enchanting_table.png", 0xff67403b, false, null, false),
    B_117_0_BREWING_STAND("brewing_stand", null, 117, 0, "blocks/brewing_stand.png", 0x767c6751, false, null, false),
    B_118_0_CAULDRON("cauldron", null, 118, 0, "blocks/cauldron.png", 0xff373737, false, null, false),
    B_119_0_END_PORTAL("end_portal", null, 119, 0, "blocks/endframe_top.png", 0xff101010, false, null, false),
    B_120_0_END_PORTAL_FRAME("end_portal_frame", null, 120, 0, "blocks/endframe_side.png", 0xff597560, false, null, false),
    B_121_0_END_STONE("end_stone", null, 121, 0, "blocks/end_stone.png", 0xffdddfa5, false, null, false),
    B_122_0_DRAGON_EGG("dragon_egg", null, 122, 0, "blocks/dragon_egg.png", 0xff0c090f, false, null, false),
    B_123_0_REDSTONE_LAMP("redstone_lamp", null, 123, 0, "blocks/redstone_lamp.png", 0xff462b1a, false, null, false),
    B_124_0_LIT_REDSTONE_LAMP("lit_redstone_lamp", null, 124, 0, "blocks/lit_redstone_lamp.png", 0xff775937, false, null, false),
    B_125_0_DROPPER("dropper", null, 125, 0, "blocks/dropper.png", 0xff9c7f4e, false, null, false),
    B_126_0_ACTIVATOR_RAIL("activator_rail", null, 126, 0, "blocks/activator_rail.png", 0xff9c7f4e, false, null, false),
    B_127_0_COCOA("cocoa", null, 127, 0, "blocks/cocoa.png", 0x2e8a8c40, false, null, false),
    B_128_0_SANDSTONE_STAIRS("sandstone_stairs", null, 128, 0, "blocks/sandstone_stairs.png", 0xffdad29e, false, null, false),
    B_129_0_EMERALD_ORE("emerald_ore", null, 129, 0, "blocks/emerald_ore.png", 0xff6d8074, false, null, false),
    B_130_0_ENDER_CHEST("ender_chest", null, 130, 0, "blocks/ender_chest_front.png", 0xc82c3e40, false, null, false),
    B_131_0_TRIPWIRE_HOOK("tripwire_hook", null, 131, 0, "blocks/tripwire_hook.png", 0x2d8a8171, false, null, false),
    B_132_0_TRIPWIRE("tripWire", null, 132, 0, "blocks/tripWire.png", 0x2d818181, false, null, false),
    B_133_0_EMERALD_BLOCK("emerald_block", null, 133, 0, "blocks/emerald_block.png", 0xff51d975, false, null, false),
    B_134_0_SPRUCE_STAIRS("spruce_stairs", null, 134, 0, "blocks/spruce_stairs.png", 0xff674d2e, false, null, false),
    B_135_0_BIRCH_STAIRS("birch_stairs", null, 135, 0, "blocks/birch_stairs.png", 0xffc3b37b, false, null, false),
    B_136_0_JUNGLE_STAIRS("jungle_stairs", null, 136, 0, "blocks/jungle_stairs.png", 0xff9a6e4d, false, null, false),
    B_138_0_BEACON("beacon", null, 138, 0, "blocks/beacon.png", 0xff74ddd7, false, null, false),
    B_137_0_IMPULSE_COMMAND_BLOCK("impulse_command_block", null, 137, 0, "blocks/beacon.png", 0xff9a6e4d, false, null, false),
    B_137_4_IMPULSE_COMMAND_BLOCK("impulse_command_block", null, 137, 4, "blocks/beacon.png", 0xff9a6e4d, false, null, false),
    B_139_0_COBBLESTONE_WALL_NORMAL("cobblestone_wall", "normal", 139, 0, "blocks/cobblestone_wall_normal.png", 0xff7a7a7a, false, null, false),
    B_139_1_COBBLESTONE_WALL_MOSSY("cobblestone_wall", "mossy", 139, 1, "blocks/cobblestone_wall_mossy.png", 0xff506a50, false, null, false),
    B_140_0_FLOWER_POT("flower_pot", null, 140, 0, "blocks/flower_pot.png", 0x31764133, false, null, false),
    B_141_0_CARROTS("carrots", null, 141, 0, "blocks/carrots.png", 0x0901ab10, false, null, false),
    B_142_0_POTATOES("potatoes", null, 142, 0, "blocks/potatoes.png", 0x0901ab10, false, null, false),
    B_143_0_WOODEN_BUTTON("wooden_button", null, 143, 0, "blocks/wooden_button.png", 0x2878613e, false, null, false),
    B_144_0_SKULL("skull", null, 144, 0, "blocks/skull.png", 0x8c8c8c8c, false, null, false),
    B_145_0_ANVIL_INTACT("anvil", "intact", 145, 0, "blocks/anvil_intact.png", 0x9f403c3c, false, null, false),
    B_145_4_ANVIL_SLIGHTLY_DAMAGED("anvil", "slightly_damaged", 145, 4, "blocks/anvil_slightly_damaged.png", 0x9f403c3c, false, null, false),
    B_145_8_ANVIL_VERY_DAMAGED("anvil", "very_damaged", 145, 8, "blocks/anvil_very_damaged.png", 0x9f403c3c, false, null, false),
    B_146_0_TRAPPED_CHEST("trapped_chest", null, 146, 0, "blocks/chest_front.png", 0xfe6f5739, false, null, false),
    B_147_0_LIGHT_WEIGHTED_PRESSURE_PLATE("light_weighted_pressure_plate", null, 147, 0, "blocks/light_weighted_pressure_plate.png", 0xc8f9ec4e, false, null, false),
    B_148_0_HEAVY_WEIGHTED_PRESSURE_PLATE("heavy_weighted_pressure_plate", null, 148, 0, "blocks/heavy_weighted_pressure_plate.png", 0xc8dbdbdb, false, null, false),
    B_149_0_UNPOWERED_COMPARATOR("unpowered_comparator", null, 149, 0, "blocks/unpowered_comparator.png", 0xff9c9695, false, null, false),
    B_150_0_POWERED_COMPARATOR("powered_comparator", null, 150, 0, "blocks/powered_comparator.png", 0xffa59594, false, null, false),
    B_151_0_DAYLIGHT_DETECTOR("daylight_detector", null, 151, 0, "blocks/daylight_detector.png", 0xff82745e, false, null, false),
    B_152_0_REDSTONE_BLOCK("redstone_block", null, 152, 0, "blocks/redstone_block.png", 0xffab1b09, false, null, false),
    B_153_0_QUARTZ_ORE("quartz_ore", null, 153, 0, "blocks/quartz_ore.png", 0xffd9d1c8, false, null, false),
    B_154_0_HOPPER("hopper", null, 154, 0, "blocks/hopper.png", 0xff3e3e3e, false, null, false),
    B_155_0_QUARTZ_BLOCK_DEFAULT("quartz_block", "default", 155, 0, "blocks/quartz_block_default.png", 0xffece9e2, false, null, false),
    B_155_1_QUARTZ_BLOCK_CHISELED("quartz_block", "chiseled", 155, 1, "blocks/quartz_block_chiseled.png", 0xffe7e4db, false, null, false),
    B_155_2_QUARTZ_BLOCK_LINES("quartz_block", "lines", 155, 2, "blocks/quartz_block_lines.png", 0xffe8e5dd, false, null, false),
    B_155_3_QUARTZ_BLOCK_DEFAULT("quartz_block", "default", 155, 3, "blocks/quartz_block_default.png", 0xffe7e3db, false, null, false),
    B_156_0_QUARTZ_STAIRS("quartz_stairs", null, 156, 0, "blocks/quartz_stairs.png", 0xffece9e2, false, null, false),
    B_157_0_DOUBLE_WOODEN_SLAB_OAK("double_wooden_slab", "oak", 157, 0, "blocks/planks_oak.png", 0xb4907449, false, null, false),
    B_157_1_DOUBLE_WOODEN_SLAB_SPRUCE("double_wooden_slab", "spruce", 157, 1, "blocks/planks_spruce.png", 0xb4907449, false, null, false),
    B_157_2_DOUBLE_WOODEN_SLAB_BIRCH("double_wooden_slab", "birch", 157, 2, "blocks/planks_birch.png", 0xb4907449, false, null, false),
    B_157_3_DOUBLE_WOODEN_SLAB_JUNGLE("double_wooden_slab", "jungle", 157, 3, "blocks/planks_jungle.png", 0xb4907449, false, null, false),
    B_157_4_DOUBLE_WOODEN_SLAB_ACACIA("double_wooden_slab", "acacia", 157, 4, "blocks/planks_acacia.png", 0xb4907449, false, null, false),
    B_157_5_DOUBLE_WOODEN_SLAB_BIG_OAK("double_wooden_slab", "big_oak", 157, 5, "blocks/planks_big_oak.png", 0xb4907449, false, null, false),
    B_158_0_WOODEN_SLAB_OAK("wooden_slab", "oak", 158, 0, "blocks/wooden_slab_oak.png", 0xff907449, false, null, false),
    B_158_1_WOODEN_SLAB_SPRUCE("wooden_slab", "spruce", 158, 1, "blocks/wooden_slab_spruce.png", 0xff907449, false, null, false),
    B_158_2_WOODEN_SLAB_BIRCH("wooden_slab", "birch", 158, 2, "blocks/wooden_slab_birch.png", 0xff907449, false, null, false),
    B_158_3_WOODEN_SLAB_JUNGLE("wooden_slab", "jungle", 158, 3, "blocks/wooden_slab_jungle.png", 0xff907449, false, null, false),
    B_158_4_WOODEN_SLAB_ACACIA("wooden_slab", "acacia", 158, 4, "blocks/wooden_slab_acacia.png", 0xff907449, false, null, false),
    B_158_5_WOODEN_SLAB_BIG_OAK("wooden_slab", "big_oak", 158, 5, "blocks/wooden_slab_big_oak.png", 0xff907449, false, null, false),
    B_159_0_STAINED_HARDENED_CLAY_WHITE("stained_hardened_clay", "white", 159, 0, "blocks/hardened_clay_stained_white.png", 0xff836f64, false, null, false),
    B_159_1_STAINED_HARDENED_CLAY_ORANGE("stained_hardened_clay", "orange", 159, 1, "blocks/hardened_clay_stained_orange.png", 0xff9d5021, false, null, false),
    B_159_2_STAINED_HARDENED_CLAY_MAGENTA("stained_hardened_clay", "magenta", 159, 2, "blocks/hardened_clay_stained_magenta.png", 0xff915369, false, null, false),
    B_159_3_STAINED_HARDENED_CLAY_LIGHT_BLUE("stained_hardened_clay", "light_blue", 159, 3, "blocks/hardened_clay_stained_light_blue.png", 0xff706b87, false, null, false),
    B_159_4_STAINED_HARDENED_CLAY_YELLOW("stained_hardened_clay", "yellow", 159, 4, "blocks/hardened_clay_stained_yellow.png", 0xffb5801f, false, null, false),
    B_159_5_STAINED_HARDENED_CLAY_LIME("stained_hardened_clay", "lime", 159, 5, "blocks/hardened_clay_stained_lime.png", 0xff617030, false, null, false),
    B_159_6_STAINED_HARDENED_CLAY_PINK("stained_hardened_clay", "pink", 159, 6, "blocks/hardened_clay_stained_pink.png", 0xff9c4848, false, null, false),
    B_159_7_STAINED_HARDENED_CLAY_GRAY("stained_hardened_clay", "gray", 159, 7, "blocks/hardened_clay_stained_gray.png", 0xff392721, false, null, false),
    B_159_8_STAINED_HARDENED_CLAY_SILVER("stained_hardened_clay", "silver", 159, 8, "blocks/hardened_clay_stained_silver.png", 0xff81655b, false, null, false),
    B_159_9_STAINED_HARDENED_CLAY_CYAN("stained_hardened_clay", "cyan", 159, 9, "blocks/hardened_clay_stained_cyan.png", 0xff565959, false, null, false),
    B_159_10_STAINED_HARDENED_CLAY_PURPLE("stained_hardened_clay", "purple", 159, 10, "blocks/hardened_clay_stained_purple.png", 0xff744555, false, null, false),
    B_159_11_STAINED_HARDENED_CLAY_BLUE("stained_hardened_clay", "blue", 159, 11, "blocks/hardened_clay_stained_blue.png", 0xff463857, false, null, false),
    B_159_12_STAINED_HARDENED_CLAY_BROWN("stained_hardened_clay", "brown", 159, 12, "blocks/hardened_clay_stained_brown.png", 0xff492e1f, false, null, false),
    B_159_13_STAINED_HARDENED_CLAY_GREEN("stained_hardened_clay", "green", 159, 13, "blocks/hardened_clay_stained_green.png", 0xff484f26, false, null, false),
    B_159_14_STAINED_HARDENED_CLAY_RED("stained_hardened_clay", "red", 159, 14, "blocks/hardened_clay_stained_red.png", 0xffff382b, false, null, false),
    B_159_15_STAINED_HARDENED_CLAY_BLACK("stained_hardened_clay", "black", 159, 15, "blocks/hardened_clay_stained_black.png", 0xff21120d, false, null, false),
    B_160_0_STAINED_GLASS_PANE_WHITE("stained_glass_pane", "white", 160, 0, "blocks/glass.png", 0x32141414, false, null, false),
    B_160_1_STAINED_GLASS_PANE_ORANGE("stained_glass_pane", "orange", 160, 1, "blocks/glass.png", 0x209d5021, false, null, false),
    B_160_2_STAINED_GLASS_PANE_MAGENTA("stained_glass_pane", "magenta", 160, 2, "blocks/glass.png", 0x20915369, false, null, false),
    B_160_3_STAINED_GLASS_PANE_LIGHT_BLUE("stained_glass_pane", "light_blue", 160, 3, "blocks/glass.png", 0x20706b87, false, null, false),
    B_160_4_STAINED_GLASS_PANE_YELLOW("stained_glass_pane", "yellow", 160, 4, "blocks/glass.png", 0x20b5801f, false, null, false),
    B_160_5_STAINED_GLASS_PANE_LIME("stained_glass_pane", "lime", 160, 5, "blocks/glass.png", 0x20617030, false, null, false),
    B_160_6_STAINED_GLASS_PANE_PINK("stained_glass_pane", "pink", 160, 6, "blocks/glass.png", 0x209c4848, false, null, false),
    B_160_7_STAINED_GLASS_PANE_GRAY("stained_glass_pane", "gray", 160, 7, "blocks/glass.png", 0x20392721, false, null, false),
    B_160_8_STAINED_GLASS_PANE_SILVER("stained_glass_pane", "silver", 160, 8, "blocks/glass.png", 0x2081655b, false, null, false),
    B_160_9_STAINED_GLASS_PANE_CYAN("stained_glass_pane", "cyan", 160, 9, "blocks/glass.png", 0x20565959, false, null, false),
    B_160_10_STAINED_GLASS_PANE_PURPLE("stained_glass_pane", "purple", 160, 10, "blocks/glass.png", 0x20744555, false, null, false),
    B_160_11_STAINED_GLASS_PANE_BLUE("stained_glass_pane", "blue", 160, 11, "blocks/glass.png", 0x20463857, false, null, false),
    B_160_12_STAINED_GLASS_PANE_BROWN("stained_glass_pane", "brown", 160, 12, "blocks/glass.png", 0x20492e1f, false, null, false),
    B_160_13_STAINED_GLASS_PANE_GREEN("stained_glass_pane", "green", 160, 13, "blocks/glass.png", 0x20484f26, false, null, false),
    B_160_14_STAINED_GLASS_PANE_RED("stained_glass_pane", "red", 160, 14, "blocks/glass.png", 0x20ff382b, false, null, false),
    B_160_15_STAINED_GLASS_PANE_BLACK("stained_glass_pane", "black", 160, 15, "blocks/glass.png", 0x2021120d, false, null, false),
    B_161_0_LEAVES2_ACACIA("leaves2", "acacia", 161, 0, "blocks/leaves2_acacia.png", 0xff2e780c, true, null, false),
    B_161_1_LEAVES2_BIG_OAK("leaves2", "big_oak", 161, 1, "blocks/leaves2_big_oak.png", 0xff878787, true, null, false),
    B_162_0_LOG2_ACACIA("log2", "acacia", 162, 0, "blocks/log2_acacia.png", 0xff433f39, false, null, false),
    B_162_1_LOG2_BIG_OAK("log2", "big_oak", 162, 1, "blocks/log2_big_oak.png", 0xff2d2213, false, null, false),
    B_163_0_ACACIA_STAIRS("acacia_stairs", null, 163, 0, "blocks/acacia_stairs.png", 0xffa95c33, false, null, false),
    B_164_0_DARK_OAK_STAIRS("dark_oak_stairs", null, 164, 0, "blocks/dark_oak_stairs.png", 0xff3f2913, false, null, false),
    B_165_0_SLIME("slime", null, 165, 0, "blocks/slime.png", 0xc880b672, false, null, false),
    B_167_0_IRON_TRAPDOOR("iron_trapdoor", null, 167, 0, "blocks/iron_trapdoor.png", 0xb4cccccc, false, null, false),
    B_168_0_PRISMARINE_ROUGH("prismarine", "rough", 168, 0, "blocks/prismarine_rough.png", 0xff426a64, false, null, false),
    B_168_1_PRISMARINE_DARK("prismarine", "dark", 168, 1, "blocks/prismarine_dark.png", 0xff577a73, false, null, false),
    B_168_2_PRISMARINE_BRICKS("prismarine", "bricks", 168, 2, "blocks/prismarine_bricks.png", 0xff2f483e, false, null, false),
    B_169_0_SEALANTERN("seaLantern", null, 169, 0, "blocks/seaLantern.png", 0xffe0eae4, false, null, false),
    B_170_0_HAY_BLOCK("hay_block", null, 170, 0, "blocks/hay_block.png", 0xffa3870e, false, null, false),
    B_171_0_CARPET_WHITE("carpet", "white", 171, 0, "blocks/carpet_white.png", 0xffdddddd, false, null, false),
    B_171_1_CARPET_ORANGE("carpet", "orange", 171, 1, "blocks/carpet_orange.png", 0xffdb7d3e, false, null, false),
    B_171_2_CARPET_MAGENTA("carpet", "magenta", 171, 2, "blocks/carpet_magenta.png", 0xffb350bc, false, null, false),
    B_171_3_CARPET_LIGHT_BLUE("carpet", "light_blue", 171, 3, "blocks/carpet_light_blue.png", 0xff6a8ac9, false, null, false),
    B_171_4_CARPET_YELLOW("carpet", "yellow", 171, 4, "blocks/carpet_yellow.png", 0xffb1a627, false, null, false),
    B_171_5_CARPET_LIME("carpet", "lime", 171, 5, "blocks/carpet_lime.png", 0xff41ae38, false, null, false),
    B_171_6_CARPET_PINK("carpet", "pink", 171, 6, "blocks/carpet_pink.png", 0xffd08499, false, null, false),
    B_171_7_CARPET_GRAY("carpet", "gray", 171, 7, "blocks/carpet_gray.png", 0xff404040, false, null, false),
    B_171_8_CARPET_SILVER("carpet", "silver", 171, 8, "blocks/carpet_silver.png", 0xff9aa1a1, false, null, false),
    B_171_9_CARPET_CYAN("carpet", "cyan", 171, 9, "blocks/carpet_cyan.png", 0xff2e6e89, false, null, false),
    B_171_10_CARPET_PURPLE("carpet", "purple", 171, 10, "blocks/carpet_purple.png", 0xff7e3db5, false, null, false),
    B_171_11_CARPET_BLUE("carpet", "blue", 171, 11, "blocks/carpet_blue.png", 0xff2e388d, false, null, false),
    B_171_12_CARPET_BROWN("carpet", "brown", 171, 12, "blocks/carpet_brown.png", 0xff4f321f, false, null, false),
    B_171_13_CARPET_GREEN("carpet", "green", 171, 13, "blocks/carpet_green.png", 0xff35461b, false, null, false),
    B_171_14_CARPET_RED("carpet", "red", 171, 14, "blocks/carpet_red.png", 0xff963430, false, null, false),
    B_171_15_CARPET_BLACK("carpet", "black", 171, 15, "blocks/carpet_black.png", 0xff191616, false, null, false),
    B_172_0_HARDENED_CLAY("hardened_clay", null, 172, 0, "blocks/hardened_clay.png", 0xff5d3828, false, null, false),
    B_173_0_COAL_BLOCK("coal_block", null, 173, 0, "blocks/coal_block.png", 0xff111111, false, null, false),
    B_174_0_PACKED_ICE("packed_ice", null, 174, 0, "blocks/packed_ice.png", 0xff97b3e4, false, null, false),
    B_175_0_DOUBLE_PLANT_SUNFLOWER("double_plant", "sunflower", 175, 0, "blocks/double_plant_sunflower.png", 0xb4d28219, false, null, false),
    B_175_1_DOUBLE_PLANT_SYRINGA("double_plant", "syringa", 175, 1, "blocks/double_plant_syringa.png", 0xb4dec0e2, false, null, false),
    B_175_2_DOUBLE_PLANT_GRASS("double_plant", "grass", 175, 2, "blocks/double_plant_grass.png", 0xb4334e2c, false, null, false),
    B_175_3_DOUBLE_PLANT_FERN("double_plant", "fern", 175, 3, "blocks/double_plant_fern.png", 0xb43d5d34, false, null, false),
    B_175_4_DOUBLE_PLANT_ROSE("double_plant", "rose", 175, 4, "blocks/double_plant_rose.png", 0xb4d10609, false, null, false),
    B_175_5_DOUBLE_PLANT_PAEONIA("double_plant", "paeonia", 175, 5, "blocks/double_plant_paeonia.png", 0xb4d6c1df, false, null, false),

    B_176_0_BANNER("banner", null, 176, 0, "blocks/double_plant_paeonia.png", 0xff9aa1a1, false, null, false),
    B_176_8_BANNER("banner", null, 176, 8, "blocks/double_plant_paeonia.png", 0xff9aa1a1, false, null, false),
    B_176_12_BANNER("banner", null, 176, 12, "blocks/double_plant_paeonia.png", 0xff9aa1a1, false, null, false),

    B_178_0_DAYLIGHT_DETECTOR_INVERTED("daylight_detector_inverted", null, 178, 0, "blocks/daylight_detector_inverted.png", 0xffd8c9b5, false, null, false),
    B_179_0_RED_SANDSTONE_DEFAULT("red_sandstone", "default", 179, 0, "blocks/red_sandstone_default.png", 0xffaa561e, false, null, false),
    B_179_1_RED_SANDSTONE_CHISELED("red_sandstone", "chiseled", 179, 1, "blocks/red_sandstone_chiseled.png", 0xffa8551e, false, null, false),
    B_179_2_RED_SANDSTONE_SMOOTH("red_sandstone", "smooth", 179, 2, "blocks/red_sandstone_smooth.png", 0xffcc5e16, false, null, false),
    B_180_0_RED_SANDSTONE_STAIRS("red_sandstone_stairs", null, 180, 0, "blocks/red_sandstone_stairs.png", 0xffaa561e, false, null, false),
    B_181_0_DOUBLE_STONE_SLAB2_RED_SANDSTONE("double_stone_slab2", "red_sandstone", 181, 0, "blocks/double_stone_slab2_red_sandstone.png", 0xffaa561e, false, null, false),
    B_181_1_DOUBLE_STONE_SLAB2_PURPUR("double_stone_slab2", "purpur", 181, 1, "blocks/double_stone_slab2_purpur.png", 0xffa072a0, false, null, false),
    B_182_0_STONE_SLAB2_RED_SANDSTONE("stone_slab2", "red_sandstone", 182, 0, "blocks/stone_slab2_red_sandstone.png", 0xffaa561e, false, null, false),
    B_182_1_STONE_SLAB2_PURPUR("stone_slab2", "purpur", 182, 1, "blocks/stone_slab2_purpur.png", 0xffa072a0, false, null, false),
    B_183_0_SPRUCE_FENCE_GATE("spruce_fence_gate", null, 183, 0, "blocks/spruce_fence_gate.png", 0x00000000, false, null, false),
    B_184_0_BIRCH_FENCE_GATE("birch_fence_gate", null, 184, 0, "blocks/birch_fence_gate.png", 0x00000000, false, null, false),
    B_185_0_JUNGLE_FENCE_GATE("jungle_fence_gate", null, 185, 0, "blocks/jungle_fence_gate.png", 0x00000000, false, null, false),
    B_186_0_DARK_OAK_FENCE_GATE("dark_oak_fence_gate", null, 186, 0, "blocks/dark_oak_fence_gate.png", 0x00000000, false, null, false),
    B_187_0_ACACIA_FENCE_GATE("acacia_fence_gate", null, 187, 0, "blocks/acacia_fence_gate.png", 0x00000000, false, null, false),
    B_193_0_SPRUCE_DOOR("spruce_door", null, 193, 0, "blocks/spruce_door.png", 0x00000000, false, null, false),
    B_194_0_BIRCH_DOOR("birch_door", null, 194, 0, "blocks/birch_door.png", 0x00000000, false, null, false),
    B_195_0_JUNGLE_DOOR("jungle_door", null, 195, 0, "blocks/jungle_door.png", 0x00000000, false, null, false),
    B_196_0_ACACIA_DOOR("acacia_door", null, 196, 0, "blocks/acacia_door.png", 0x00000000, false, null, false),
    B_197_0_DARK_OAK_DOOR("dark_oak_door", null, 197, 0, "blocks/dark_oak_door.png", 0x00000000, false, null, false),
    B_198_0_GRASS_PATH("grass_path", null, 198, 0, "blocks/grass_path.png", 0x46a0a0a0, false, null, false),
    B_199_0_FRAME("frame", null, 199, 0, "blocks/frame.png", 0xa04f3e4f, false, null, false),
    B_200_0_CHORUS_FLOWER("chorus_flower", null, 200, 0, "blocks/chorus_flower.png", 0xa0c3b6c8, false, null, false),
    B_201_0_PURPUR_BLOCK_DEFAULT("purpur_block", "default", 201, 0, "blocks/purpur_block_default.png", 0xffa577a5, false, null, false),
    B_201_1_PURPUR_BLOCK_CHISELED("purpur_block", "chiseled", 201, 1, "blocks/purpur_block_chiseled.png", 0xffa570a5, false, null, false),
    B_201_2_PURPUR_BLOCK_LINES("purpur_block", "lines", 201, 2, "blocks/purpur_block_lines.png", 0xffa070a5, false, null, false),
    B_201_3_PURPUR_BLOCK_DEFAULT("purpur_block", "default", 201, 3, "blocks/purpur_block_default.png", 0xffa577a5, false, null, false),
    B_202_0_COLORED_TORCH_DEFAULT("colored_torch_rg", "default", 202, 0, "blocks/redstone_torch_on.png", 0xffa070a5, false, null, false),
    B_202_9_COLORED_TORCH_DEFAULT("colored_torch_rg", "default", 202, 9, "blocks/redstone_torch_on.png", 0xffa070a5, false, null, false),
    B_202_13_COLORED_TORCH_DEFAULT("colored_torch_rg", "default", 202, 13, "blocks/redstone_torch_on.png", 0xffa070a5, false, null, false),
    B_203_0_PURPUR_STAIRS("purpur_stairs", null, 203, 0, "blocks/purpur_stairs.png", 0xffa577a5, false, null, false),
    B_206_0_END_BRICKS("end_bricks", null, 206, 0, "blocks/end_bricks.png", 0xffe7f2af, false, null, false),
    B_208_0_END_ROD("end_rod", null, 208, 0, "blocks/end_rod.png", 0xff6e6e6e, true, null, false),
    B_209_0_END_GATEWAY("end_gateway", null, 209, 0, "blocks/end_gateway.png", 0xff171c27, false, null, false),

    B_205_0_SHULKERBOX("shulker_box", null, 205, 0, "blocks/observer.png", 0xffffffff, false, null, false),
    B_218_0_SHULKERBOX_WHITE("shulker_box", "white", 218, 0, "blocks/observer.png", 0xffffffff, false, null, false),
    B_218_1_SHULKERBOX_ORANGE("shulker_box", "orange", 218, 1, "blocks/observer.png", 0xffffd030, false, null, false),
    B_218_2_SHULKERBOX_MAGENTA("shulker_box", "magenta", 218, 2, "blocks/observer.png", 0xffef007f, false, null, false),
    B_218_3_SHULKERBOX_LIGHT_BLUE("shulker_box", "light_blue", 218, 3, "blocks/observer.png", 0xff5588ff, false, null, false),
    B_218_4_SHULKERBOX_YELLOW("shulker_box", "yellow", 218, 4, "blocks/observer.png", 0xffffff40, false, null, false),
    B_218_5_SHULKERBOX_LIME("shulker_box", "lime", 218, 5, "blocks/observer.png", 0xff0db60e, false, null, false),
    B_218_6_SHULKERBOX_PINK("shulker_box", "pink", 218, 6, "blocks/observer.png", 0xffff6076, false, null, false),
    B_218_7_SHULKERBOX_GRAY("shulker_box", "gray", 218, 7, "blocks/observer.png", 0xff565656, false, null, false),
    B_218_8_SHULKERBOX_SILVER("shulker_box", "silver", 218, 8, "blocks/observer.png", 0xffa6a6a6, false, null, false),
    B_218_9_SHULKERBOX_CYAN("shulker_box", "cyan", 218, 9, "blocks/observer.png", 0xff0d5656, false, null, false),
    B_218_10_SHULKERBOX_PURPLE("shulker_box", "purple", 218, 10, "blocks/observer.png", 0xff560d56, false, null, false),
    B_218_11_SHULKERBOX_BLUE("shulker_box", "blue", 218, 11, "blocks/observer.png", 0xff0d0e56, false, null, false),
    B_218_12_SHULKERBOX_BROWN("shulker_box", "brown", 218, 12, "blocks/observer.png", 0xff804530, false, null, false),
    B_218_13_SHULKERBOX_GREEN("shulker_box", "green", 218, 13, "blocks/observer.png", 0xff0d560e, false, null, false),
    B_218_14_SHULKERBOX_RED("shulker_box", "red", 218, 14, "blocks/observer.png", 0xffff2020, false, null, false),
    B_218_15_SHULKERBOX_BLACK("shulker_box", "black", 218, 15, "blocks/observer.png", 0xff000000, false, null, false),


    B_240_0_CHORUS_PLANT("chorus_plant", null, 240, 0, "blocks/chorus_plant.png", 0xaa3d6e86, false, null, false),
    B_241_0_STAINED_GLASS_WHITE("stained_glass", "white", 241, 0, "blocks/stained_glass_white.png", 0x50836f64, false, null, false),
    B_241_1_STAINED_GLASS_ORANGE("stained_glass", "orange", 241, 1, "blocks/stained_glass_orange.png", 0x509d5021, false, null, false),
    B_241_2_STAINED_GLASS_MAGENTA("stained_glass", "magenta", 241, 2, "blocks/stained_glass_magenta.png", 0x50915369, false, null, false),
    B_241_3_STAINED_GLASS_LIGHT_BLUE("stained_glass", "light_blue", 241, 3, "blocks/stained_glass_light_blue.png", 0x50706b87, false, null, false),
    B_241_4_STAINED_GLASS_YELLOW("stained_glass", "yellow", 241, 4, "blocks/stained_glass_yellow.png", 0x50b5801f, false, null, false),
    B_241_5_STAINED_GLASS_LIME("stained_glass", "lime", 241, 5, "blocks/stained_glass_lime.png", 0x50617030, false, null, false),
    B_241_6_STAINED_GLASS_PINK("stained_glass", "pink", 241, 6, "blocks/stained_glass_pink.png", 0x509c4848, false, null, false),
    B_241_7_STAINED_GLASS_GRAY("stained_glass", "gray", 241, 7, "blocks/stained_glass_gray.png", 0x50392721, false, null, false),
    B_241_8_STAINED_GLASS_SILVER("stained_glass", "silver", 241, 8, "blocks/stained_glass_silver.png", 0x5081655b, false, null, false),
    B_241_9_STAINED_GLASS_CYAN("stained_glass", "cyan", 241, 9, "blocks/stained_glass_cyan.png", 0x50565959, false, null, false),
    B_241_10_STAINED_GLASS_PURPLE("stained_glass", "purple", 241, 10, "blocks/stained_glass_purple.png", 0x50744555, false, null, false),
    B_241_11_STAINED_GLASS_BLUE("stained_glass", "blue", 241, 11, "blocks/stained_glass_blue.png", 0x50463857, false, null, false),
    B_241_12_STAINED_GLASS_BROWN("stained_glass", "brown", 241, 12, "blocks/stained_glass_brown.png", 0x50492e1f, false, null, false),
    B_241_13_STAINED_GLASS_GREEN("stained_glass", "green", 241, 13, "blocks/stained_glass_green.png", 0x50484f26, false, null, false),
    B_241_14_STAINED_GLASS_RED("stained_glass", "red", 241, 14, "blocks/stained_glass_red.png", 0x50ff382b, false, null, false),
    B_241_15_STAINED_GLASS_BLACK("stained_glass", "black", 241, 15, "blocks/stained_glass_black.png", 0x5021120d, false, null, false),
    B_243_0_PODZOL("podzol", null, 243, 0, "blocks/podzol.png", 0xff533a1b, true, null, false),
    B_244_0_BEETROOT("beetroot", null, 244, 0, "blocks/beetroot.png", 0x0901ab10, false, null, false),
    B_245_0_STONECUTTER("stonecutter", null, 245, 0, "blocks/stonecutter.png", 0xff515151, false, null, false),
    B_246_0_GLOWINGOBSIDIAN("glowingobsidian", null, 246, 0, "blocks/glowingobsidian.png", 0xff17060a, false, null, false),
    B_247_0_NETHERREACTOR_DEFAULT("netherreactor", "default", 247, 0, "blocks/netherreactor_default.png", 0xffd2d200, false, null, false),
    B_247_1_NETHERREACTOR_ACTIVE("netherreactor", "active", 247, 1, "blocks/netherreactor_active.png", 0xff3d6e86, false, null, false),
    B_247_2_NETHERREACTOR_COOLED("netherreactor", "cooled", 247, 2, "blocks/netherreactor_cooled.png", 0xff3d6e86, false, null, false),
    B_248_0_INFO_UPDATE("info_update", null, 248, 0, "blocks/info_update.png", 0xff2f3218, false, null, false),
    B_249_0_INFO_UPDATE2("info_update2", null, 249, 0, "blocks/info_update2.png", 0xff2f3218, false, null, false),
    B_250_0_MOVINGBLOCK("movingBlock", null, 250, 0, "blocks/movingBlock.png", 0, false, null, false),
    B_251_0_OBSERVER("observer", null, 251, 0, "blocks/observer.png", 0xff3d6e86, false, null, false),


    B_236_0_CONCRETE_WHITE("concrete", "orange", 236, 0, "blocks/observer.png", 0xffffffff, false, null, false),
    B_236_1_CONCRETE_ORANGE("concrete", "orange", 236, 1, "blocks/observer.png", 0xffffd030, false, null, false),
    B_236_2_CONCRETE_MAGENTA("concrete", "magenta", 236, 2, "blocks/observer.png", 0xffef007f, false, null, false),
    B_236_3_CONCRETE_LIGHT_BLUE("concrete", "light_blue", 236, 3, "blocks/observer.png", 0xff5588ff, false, null, false),
    B_236_4_CONCRETE_YELLOW("concrete", "yellow", 236, 4, "blocks/observer.png", 0xffffff40, false, null, false),
    B_236_5_CONCRETE_LIME("concrete", "lime", 236, 5, "blocks/observer.png", 0xff0db60e, false, null, false),
    B_236_6_CONCRETE_PINK("concrete", "pink", 236, 6, "blocks/observer.png", 0xffff6076, false, null, false),
    B_236_7_CONCRETE_GRAY("concrete", "gray", 236, 7, "blocks/observer.png", 0xff565656, false, null, false),
    B_236_8_CONCRETE_SILVER("concrete", "silver", 236, 8, "blocks/observer.png", 0xffa6a6a6, false, null, false),
    B_236_9_CONCRETE_CYAN("concrete", "cyan", 236, 9, "blocks/observer.png", 0xff0d5656, false, null, false),
    B_236_10_CONCRETE_PURPLE("concrete", "purple", 236, 10, "blocks/observer.png", 0xff560d56, false, null, false),
    B_236_11_CONCRETE_BLUE("concrete", "blue", 236, 11, "blocks/observer.png", 0xff0d0e56, false, null, false),
    B_236_12_CONCRETE_BROWN("concrete", "brown", 236, 12, "blocks/observer.png", 0xff804530, false, null, false),
    B_236_13_CONCRETE_GREEN("concrete", "green", 236, 13, "blocks/observer.png", 0xff0d560e, false, null, false),
    B_236_14_CONCRETE_RED("concrete", "red", 236, 14, "blocks/observer.png", 0xffff2020, false, null, false),
    B_236_15_CONCRETE_BLACK("concrete", "black", 236, 15, "blocks/observer.png", 0xff000000, false, null, false),


    B_237_0_CONCRETE_POWDER_WHITE("concrete_powder", "orange", 237, 0, "blocks/observer.png", 0xffffffff, false, null, false),
    B_237_1_CONCRETE_POWDER_ORANGE("concrete_powder", "orange", 237, 1, "blocks/observer.png", 0xffffd030, false, null, false),
    B_237_2_CONCRETE_POWDER_MAGENTA("concrete_powder", "magenta", 237, 2, "blocks/observer.png", 0xffef007f, false, null, false),
    B_237_3_CONCRETE_POWDER_LIGHT_BLUE("concrete_powder", "light_blue", 237, 3, "blocks/observer.png", 0xff5588ff, false, null, false),
    B_237_4_CONCRETE_POWDER_YELLOW("concrete_powder", "yellow", 237, 4, "blocks/observer.png", 0xffffff40, false, null, false),
    B_237_5_CONCRETE_POWDER_LIME("concrete_powder", "lime", 237, 5, "blocks/observer.png", 0xff0db60e, false, null, false),
    B_237_6_CONCRETE_POWDER_PINK("concrete_powder", "pink", 237, 6, "blocks/observer.png", 0xffff6076, false, null, false),
    B_237_7_CONCRETE_POWDER_GRAY("concrete_powder", "gray", 237, 7, "blocks/observer.png", 0xff565656, false, null, false),
    B_237_8_CONCRETE_POWDER_SILVER("concrete_powder", "silver", 237, 8, "blocks/observer.png", 0xffa6a6a6, false, null, false),
    B_237_9_CONCRETE_POWDER_CYAN("concrete_powder", "cyan", 237, 9, "blocks/observer.png", 0xff0d5656, false, null, false),
    B_237_10_CONCRETE_POWDER_PURPLE("concrete_powder", "purple", 237, 10, "blocks/observer.png", 0xff560d56, false, null, false),
    B_237_11_CONCRETE_POWDER_BLUE("concrete_powder", "blue", 237, 11, "blocks/observer.png", 0xff0d0e56, false, null, false),
    B_237_12_CONCRETE_POWDER_BROWN("concrete_powder", "brown", 237, 12, "blocks/observer.png", 0xff804530, false, null, false),
    B_237_13_CONCRETE_POWDER_GREEN("concrete_powder", "green", 237, 13, "blocks/observer.png", 0xff0d560e, false, null, false),
    B_237_14_CONCRETE_POWDER_RED("concrete_powder", "brown", 237, 14, "blocks/observer.png", 0xffff2020, false, null, false),
    B_237_15_CONCRETE_POWDER_BLACK("concrete_powder", "black", 237, 15, "blocks/observer.png", 0xff000000, false, null, false),


    B_219_2_GLAZED_TERRACOTTA_PURPLE("glazed_terracotta", "purple", 219, 2, "blocks/observer.png", 0xff762d76, false, null, false),
    B_219_3_GLAZED_TERRACOTTA_PURPLE("glazed_terracotta", "purple", 219, 3, "blocks/observer.png", 0xff762d76, false, null, false),
    B_219_4_GLAZED_TERRACOTTA_PURPLE("glazed_terracotta", "purple", 219, 4, "blocks/observer.png", 0xff762d76, false, null, false),
    B_219_5_GLAZED_TERRACOTTA_PURPLE("glazed_terracotta", "purple", 219, 5, "blocks/observer.png", 0xff762d76, false, null, false),
    B_220_2_GLAZED_TERRACOTTA_WHITE("glazed_terracotta", "white", 220, 2, "blocks/observer.png", 0xffffffff, false, null, false),
    B_220_3_GLAZED_TERRACOTTA_WHITE("glazed_terracotta", "white", 220, 3, "blocks/observer.png", 0xffffffff, false, null, false),
    B_220_4_GLAZED_TERRACOTTA_WHITE("glazed_terracotta", "white", 220, 4, "blocks/observer.png", 0xffffffff, false, null, false),
    B_220_5_GLAZED_TERRACOTTA_WHITE("glazed_terracotta", "white", 220, 5, "blocks/observer.png", 0xffffffff, false, null, false),
    B_221_2_GLAZED_TERRACOTTA_ORANGE("glazed_terracotta", "orange", 221, 2, "blocks/observer.png", 0xffff8030, false, null, false),
    B_221_3_GLAZED_TERRACOTTA_ORANGE("glazed_terracotta", "orange", 221, 3, "blocks/observer.png", 0xffff8030, false, null, false),
    B_221_4_GLAZED_TERRACOTTA_ORANGE("glazed_terracotta", "orange", 221, 4, "blocks/observer.png", 0xffff8030, false, null, false),
    B_221_5_GLAZED_TERRACOTTA_ORANGE("glazed_terracotta", "orange", 221, 5, "blocks/observer.png", 0xffff8030, false, null, false),
    B_222_2_GLAZED_TERRACOTTA_LIGHT_MAGENTA("glazed_terracotta", "magenta", 222, 2, "blocks/observer.png", 0xffff108f, false, null, false),
    B_222_3_GLAZED_TERRACOTTA_LIGHT_MAGENTA("glazed_terracotta", "magenta", 222, 3, "blocks/observer.png", 0xffff108f, false, null, false),
    B_222_4_GLAZED_TERRACOTTA_LIGHT_MAGENTA("glazed_terracotta", "magenta", 222, 4, "blocks/observer.png", 0xffff108f, false, null, false),
    B_222_5_GLAZED_TERRACOTTA_LIGHT_MAGENTA("glazed_terracotta", "magenta", 222, 5, "blocks/observer.png", 0xffff108f, false, null, false),
    B_223_2_GLAZED_TERRACOTTA_LIGHT_BLUE("glazed_terracotta", "light_blue", 223, 2, "blocks/observer.png", 0xff75a8ff, false, null, false),
    B_223_3_GLAZED_TERRACOTTA_LIGHT_BLUE("glazed_terracotta", "light_blue", 223, 3, "blocks/observer.png", 0xff75a8ff, false, null, false),
    B_223_4_GLAZED_TERRACOTTA_LIGHT_BLUE("glazed_terracotta", "light_blue", 223, 4, "blocks/observer.png", 0xff75a8ff, false, null, false),
    B_223_5_GLAZED_TERRACOTTA_LIGHT_BLUE("glazed_terracotta", "light_blue", 223, 5, "blocks/observer.png", 0xff75a8ff, false, null, false),
    B_224_0_GLAZED_TERRACOTTA_YELLOW("glazed_terracotta", "yellow", 224, 0, "blocks/observer.png", 0xffffff60, false, null, false),
    B_224_2_GLAZED_TERRACOTTA_YELLOW("glazed_terracotta", "yellow", 224, 2, "blocks/observer.png", 0xffffff60, false, null, false),
    B_224_3_GLAZED_TERRACOTTA_YELLOW("glazed_terracotta", "yellow", 224, 3, "blocks/observer.png", 0xffffff60, false, null, false),
    B_224_4_GLAZED_TERRACOTTA_YELLOW("glazed_terracotta", "yellow", 224, 4, "blocks/observer.png", 0xffffff60, false, null, false),
    B_224_5_GLAZED_TERRACOTTA_YELLOW("glazed_terracotta", "yellow", 224, 5, "blocks/observer.png", 0xffffff60, false, null, false),
    B_225_0_GLAZED_TERRACOTTA_LIME("glazed_terracotta", "lime", 225, 0, "blocks/observer.png", 0xff2dd62e, false, null, false),
    B_225_2_GLAZED_TERRACOTTA_LIME("glazed_terracotta", "lime", 225, 2, "blocks/observer.png", 0xff2dd62e, false, null, false),
    B_225_3_GLAZED_TERRACOTTA_LIME("glazed_terracotta", "lime", 225, 3, "blocks/observer.png", 0xff2dd62e, false, null, false),
    B_225_4_GLAZED_TERRACOTTA_LIME("glazed_terracotta", "lime", 225, 4, "blocks/observer.png", 0xff2dd62e, false, null, false),
    B_225_5_GLAZED_TERRACOTTA_LIME("glazed_terracotta", "lime", 225, 5, "blocks/observer.png", 0xff2dd62e, false, null, false),
    B_226_0_GLAZED_TERRACOTTA_PINK("glazed_terracotta", "pink", 226, 0, "blocks/observer.png", 0xffff8096, false, null, false),
    B_226_2_GLAZED_TERRACOTTA_PINK("glazed_terracotta", "pink", 226, 2, "blocks/observer.png", 0xffff8096, false, null, false),
    B_226_3_GLAZED_TERRACOTTA_PINK("glazed_terracotta", "pink", 226, 3, "blocks/observer.png", 0xffff8096, false, null, false),
    B_226_4_GLAZED_TERRACOTTA_PINK("glazed_terracotta", "pink", 226, 4, "blocks/observer.png", 0xffff8096, false, null, false),
    B_226_5_GLAZED_TERRACOTTA_PINK("glazed_terracotta", "pink", 226, 5, "blocks/observer.png", 0xffff8096, false, null, false),
    B_226_6_GLAZED_TERRACOTTA_PINK("glazed_terracotta", "pink", 226, 6, "blocks/observer.png", 0xffff8096, false, null, false),
    B_226_7_GLAZED_TERRACOTTA_PINK("glazed_terracotta", "pink", 226, 7, "blocks/observer.png", 0xffff8096, false, null, false),
    B_227_2_GLAZED_TERRACOTTA_GRAY("glazed_terracotta", "gray", 227, 2, "blocks/observer.png", 0xff767676, false, null, false),
    B_227_3_GLAZED_TERRACOTTA_GRAY("glazed_terracotta", "gray", 227, 3, "blocks/observer.png", 0xff767676, false, null, false),
    B_227_4_GLAZED_TERRACOTTA_GRAY("glazed_terracotta", "gray", 227, 4, "blocks/observer.png", 0xff767676, false, null, false),
    B_227_5_GLAZED_TERRACOTTA_GRAY("glazed_terracotta", "gray", 227, 5, "blocks/observer.png", 0xff767676, false, null, false),
    B_228_2_GLAZED_TERRACOTTA_LIGHT_GRAY("glazed_terracotta", "light_gray", 228, 2, "blocks/observer.png", 0xffc6c6c6, false, null, false),
    B_228_3_GLAZED_TERRACOTTA_LIGHT_GRAY("glazed_terracotta", "light_gray", 228, 3, "blocks/observer.png", 0xffc6c6c6, false, null, false),
    B_228_4_GLAZED_TERRACOTTA_LIGHT_GRAY("glazed_terracotta", "light_gray", 228, 4, "blocks/observer.png", 0xffc6c6c6, false, null, false),
    B_228_5_GLAZED_TERRACOTTA_LIGHT_GRAY("glazed_terracotta", "light_gray", 228, 5, "blocks/observer.png", 0xffc6c6c6, false, null, false),
    B_229_2_GLAZED_TERRACOTTA_CYAN("glazed_terracotta", "cyan", 229, 2, "blocks/observer.png", 0xff2d7676, false, null, false),
    B_229_3_GLAZED_TERRACOTTA_CYAN("glazed_terracotta", "cyan", 229, 3, "blocks/observer.png", 0xff2d7676, false, null, false),
    B_229_4_GLAZED_TERRACOTTA_CYAN("glazed_terracotta", "cyan", 229, 4, "blocks/observer.png", 0xff2d7676, false, null, false),
    B_229_5_GLAZED_TERRACOTTA_CYAN("glazed_terracotta", "cyan", 229, 5, "blocks/observer.png", 0xff2d7676, false, null, false),
    B_231_2_GLAZED_TERRACOTTA_BLUE("glazed_terracotta", "blue", 231, 2, "blocks/observer.png", 0xff2d2e76, false, null, false),
    B_231_3_GLAZED_TERRACOTTA_BLUE("glazed_terracotta", "blue", 231, 3, "blocks/observer.png", 0xff2d2e76, false, null, false),
    B_231_4_GLAZED_TERRACOTTA_BLUE("glazed_terracotta", "blue", 231, 4, "blocks/observer.png", 0xff2d2e76, false, null, false),
    B_231_5_GLAZED_TERRACOTTA_BLUE("glazed_terracotta", "blue", 231, 5, "blocks/observer.png", 0xff2d2e76, false, null, false),
    B_232_2_GLAZED_TERRACOTTA_BROWN("glazed_terracotta", "brown", 232, 2, "blocks/observer.png", 0xffa06550, false, null, false),
    B_232_3_GLAZED_TERRACOTTA_BROWN("glazed_terracotta", "brown", 232, 3, "blocks/observer.png", 0xffa06550, false, null, false),
    B_232_4_GLAZED_TERRACOTTA_BROWN("glazed_terracotta", "brown", 232, 4, "blocks/observer.png", 0xffa06550, false, null, false),
    B_232_5_GLAZED_TERRACOTTA_BROWN("glazed_terracotta", "brown", 232, 5, "blocks/observer.png", 0xffa06550, false, null, false),
    B_233_0_GLAZED_TERRACOTTA_GREEN("glazed_terracotta", "green", 233, 0, "blocks/observer.png", 0xff2d762e, false, null, false),
    B_233_2_GLAZED_TERRACOTTA_GREEN("glazed_terracotta", "green", 233, 2, "blocks/observer.png", 0xff2d762e, false, null, false),
    B_233_3_GLAZED_TERRACOTTA_GREEN("glazed_terracotta", "green", 233, 3, "blocks/observer.png", 0xff2d762e, false, null, false),
    B_233_4_GLAZED_TERRACOTTA_GREEN("glazed_terracotta", "green", 233, 4, "blocks/observer.png", 0xff2d762e, false, null, false),
    B_233_5_GLAZED_TERRACOTTA_GREEN("glazed_terracotta", "green", 233, 5, "blocks/observer.png", 0xff2d762e, false, null, false),
    B_234_2_GLAZED_TERRACOTTA_RED("glazed_terracotta", "red", 234, 2, "blocks/observer.png", 0xffff3030, false, null, false),
    B_234_3_GLAZED_TERRACOTTA_RED("glazed_terracotta", "red", 234, 3, "blocks/observer.png", 0xffff3030, false, null, false),
    B_234_4_GLAZED_TERRACOTTA_RED("glazed_terracotta", "red", 234, 4, "blocks/observer.png", 0xffff3030, false, null, false),
    B_234_5_GLAZED_TERRACOTTA_RED("glazed_terracotta", "red", 234, 5, "blocks/observer.png", 0xffff3030, false, null, false),
    B_235_2_GLAZED_TERRACOTTA_BLACK("glazed_terracotta", "black", 235, 2, "blocks/observer.png", 0xff050505, false, null, false),
    B_235_3_GLAZED_TERRACOTTA_BLACK("glazed_terracotta", "black", 235, 3, "blocks/observer.png", 0xff050505, false, null, false),
    B_235_4_GLAZED_TERRACOTTA_BLACK("glazed_terracotta", "black", 235, 4, "blocks/observer.png", 0xff050505, false, null, false),
    B_235_5_GLAZED_TERRACOTTA_BLACK("glazed_terracotta", "black", 235, 5, "blocks/observer.png", 0xff050505, false, null, false),

    B_213_0_MAGMA_BLOCK("magma_block", "default", 213, 0, "blocks/quartz_block_default.png", 0xffc45a12, false, null, false),

    B_214_0_NETHERWART_BLOCK("netherwart_block", "default", 214, 0, "blocks/quartz_block_default.png", 0xffbf2030, false, null, false),

    B_215_0_RED_NETHER_BRICK("red_nether_brick", "default", 215, 0, "blocks/quartz_block_default.png", 0xff7f1020, false, null, false),

    B_216_0_BONE("quartz_block", "default", 216, 0, "blocks/quartz_block_default.png", 0xffefe5d2, false, null, false),

    B_255_0_RESERVED6("reserved6", null, 255, 0, "blocks/reserved6.png", 0xff19171a, false, null, false),
    B_210_0_ALLOW("allow", null, 210, 0, "blocks/allow.png", 0xff634aba, false, null, false),
    B_211_0_DENY("deny", null, 211, 0, "blocks/deny.png", 0xff6ca28a, false, null, false),
    B_212_0_BORDER_BLOCK("border_block", null, 212, 0, "blocks/border_block.png", 0xff76a7fc, false, null, false),
    B_230_0_CHALKBOARD("chalkboard", null, 230, 0, "blocks/chalkboard.png", 0xff3d6e86, false, null, false),
    B_242_0_CAMERA("camera", null, 242, 0, "blocks/camera.png", 0xff3d6e86, false, null, false);

    private static final Map<String, Block> byDataName = new HashMap<>();
    private static final SparseArray<SparseArray<Block>> blockMap;
    private static final Map<String, Integer> nameService;

    static {
        blockMap = new SparseArray<>();
        nameService = new HashMap<>(256);
        SparseArray<Block> subMap;
        for (Block b : Block.values()) {
            subMap = blockMap.get(b.id);
            if (subMap == null) {
                subMap = new SparseArray<>();
                blockMap.put(b.id, subMap);
            }
            subMap.put(b.subId, b);
            if (b.subId == 0) byDataName.put(b.str, b);
            byDataName.put(b.str + "@" + b.subName, b);
            nameService.put(b.str, b.id);
        }
    }

    public final int id, subId;
    public final String str, subName, displayName, identifier;
    public final String texPath;
    public final Color color;
    public final boolean hasBiomeShading;
    public Bitmap bitmap;

    Block(String name, String subName, int id, int subId, String texPath, int color, boolean hasBiomeShading, String minVer, boolean deprecated) {
        this.id = id;
        this.subId = subId;
        this.str = name;
        this.subName = subName;
        this.displayName = name + " " + subName;
        this.texPath = texPath;
        this.color = Color.fromARGB(color);
        this.hasBiomeShading = hasBiomeShading;
        this.identifier = "minecraft:" + subName;
    }

    public static Block getByDataName(String dataName) {
        return byDataName.get(dataName);
    }

    public static void loadBitmaps(AssetManager assetManager) throws IOException {
        for (Block b : Block.values()) {
            if (b.bitmap == null && b.texPath != null) {
                try {
                    b.bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(assetManager.open(b.texPath)), 32, 32, false);
                } catch (FileNotFoundException e) {
                    //TODO file-paths were generated from block names; some do not actually exist...
                    //Log.w("File not found! "+b.texPath);
                } catch (Exception e) {
                    Log.d(Block.class, e);
                }
            }
        }
    }

    @NonNull
    public static Block getBestBlock(int id, int meta) {
        Block ret = getBlock(id, meta);
        if (ret != null) return ret;
        if (id >= 0) {
            SparseArray<Block> subMap = blockMap.get(id);
            if (subMap != null) ret = subMap.valueAt(0);
        }
        return (ret == null) ? Block.B_0_0_AIR : ret;
    }

    @Nullable
    public static Block getBlock(int id, int meta) {
        if (id < 0) return null;
        SparseArray<Block> subMap = blockMap.get(id);
        if (subMap == null) return null;
        else return subMap.get(meta);
    }

    @Nullable
    public static Block getBlockWithLegacyId(int id) {
        if (id < 0) return null;
        SparseArray<Block> subMap = blockMap.get(id);
        if (subMap == null) return null;
        return subMap.valueAt(0);
    }

    public static Block getBlock(int runtimeId) {
        int id = runtimeId >>> 8;
        int data = runtimeId & 0xf;
        return getBlock(id, data);
    }

    public static int resolve(@NonNull String identifier) {
        int dotPos = identifier.indexOf(':');
        if (dotPos != -1) {
            if (!identifier.substring(0, dotPos).equals("minecraft")) return 0;
            identifier = identifier.substring(dotPos + 1);
        }
        Integer i = nameService.get(identifier);
        if (i == null) return 0;
        return i;
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
