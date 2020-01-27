package com.mithrilmania.blocktopograph.block;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mithrilmania.blocktopograph.nbt.tags.CompoundTag;
import com.mithrilmania.blocktopograph.nbt.tags.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class BlockWithStatesToLegacyBlockMapper {

    private static Hashtable<String, Pair<CompoundTag, KnownBlockRepr>[]> mMapTypeToBlocks;

    static {
        mMapTypeToBlocks = new Hashtable<>();
        mMapTypeToBlocks.put("air", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_0_0_AIR
                )
        });
        mMapTypeToBlocks.put("stone", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_type", "stone").build(),
                        KnownBlockRepr.B_1_0_STONE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_type", "granite").build(),
                        KnownBlockRepr.B_1_1_STONE_GRANITE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_type", "granite_smooth").build(),
                        KnownBlockRepr.B_1_2_STONE_GRANITE_SMOOTH
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_type", "diorite").build(),
                        KnownBlockRepr.B_1_3_STONE_DIORITE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_type", "diorite_smooth").build(),
                        KnownBlockRepr.B_1_4_STONE_DIORITE_SMOOTH
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_type", "andesite").build(),
                        KnownBlockRepr.B_1_5_STONE_ANDESITE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_type", "andesite_smooth").build(),
                        KnownBlockRepr.B_1_6_STONE_ANDESITE_SMOOTH
                )
        });
        mMapTypeToBlocks.put("grass", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_2_0_GRASS
                )
        });
        mMapTypeToBlocks.put("dirt", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("dirt_type", "normal").build(),
                        KnownBlockRepr.B_3_0_DIRT
                )
        });
        mMapTypeToBlocks.put("cobblestone", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_4_0_COBBLESTONE
                )
        });
        mMapTypeToBlocks.put("planks", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "oak").build(),
                        KnownBlockRepr.B_5_0_PLANKS_OAK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "spruce").build(),
                        KnownBlockRepr.B_5_1_PLANKS_SPRUCE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "birch").build(),
                        KnownBlockRepr.B_5_2_PLANKS_BIRCH
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "jungle").build(),
                        KnownBlockRepr.B_5_3_PLANKS_JUNGLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "acacia").build(),
                        KnownBlockRepr.B_5_4_PLANKS_ACACIA
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "dark_oak").build(),
                        KnownBlockRepr.B_5_5_PLANKS_BIG_OAK
                )
        });
        mMapTypeToBlocks.put("sapling", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("sapling_type", "oak").addByte("age_bit", (byte) 0).build(),
                        KnownBlockRepr.B_6_0_SAPLING_OAK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("sapling_type", "spruce").addByte("age_bit", (byte) 0).build(),
                        KnownBlockRepr.B_6_1_SAPLING_SPRUCE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("sapling_type", "birch").addByte("age_bit", (byte) 0).build(),
                        KnownBlockRepr.B_6_2_SAPLING_BIRCH
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("sapling_type", "jungle").addByte("age_bit", (byte) 0).build(),
                        KnownBlockRepr.B_6_3_SAPLING_JUNGLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("sapling_type", "acacia").addByte("age_bit", (byte) 0).build(),
                        KnownBlockRepr.B_6_4_SAPLING_ACACIA
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("sapling_type", "dark_oak").addByte("age_bit", (byte) 0).build(),
                        KnownBlockRepr.B_6_5_SAPLING_BIG_OAK
                )
        });
        mMapTypeToBlocks.put("bedrock", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addByte("infiniburn_bit", (byte) 0).build(),
                        KnownBlockRepr.B_7_0_BEDROCK
                )
        });
        mMapTypeToBlocks.put("flowing_water", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("liquid_depth", 0).build(),
                        KnownBlockRepr.B_8_0_FLOWING_WATER
                )
        });
        mMapTypeToBlocks.put("water", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("liquid_depth", 0).build(),
                        KnownBlockRepr.B_9_0_WATER
                )
        });
        mMapTypeToBlocks.put("flowing_lava", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("liquid_depth", 0).build(),
                        KnownBlockRepr.B_10_0_FLOWING_LAVA
                )
        });
        mMapTypeToBlocks.put("lava", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("liquid_depth", 0).build(),
                        KnownBlockRepr.B_11_0_LAVA
                )
        });
        mMapTypeToBlocks.put("sand", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("sand_type", "normal").build(),
                        KnownBlockRepr.B_12_0_SAND_DEFAULT
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("sand_type", "red").build(),
                        KnownBlockRepr.B_12_1_SAND_RED
                )
        });
        mMapTypeToBlocks.put("gravel", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_13_0_GRAVEL
                )
        });
        mMapTypeToBlocks.put("gold_ore", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_14_0_GOLD_ORE
                )
        });
        mMapTypeToBlocks.put("iron_ore", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_15_0_IRON_ORE
                )
        });
        mMapTypeToBlocks.put("coal_ore", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_16_0_COAL_ORE
                )
        });
        mMapTypeToBlocks.put("log", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("old_log_type", "oak").addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_17_0_LOG_OAK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("old_log_type", "spruce").addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_17_1_LOG_SPRUCE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("old_log_type", "birch").addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_17_2_LOG_BIRCH
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("old_log_type", "jungle").addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_17_3_LOG_JUNGLE
                )
        });
        mMapTypeToBlocks.put("leaves", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("old_leaf_type", "oak").addByte("persistent_bit", (byte) 0).addByte("update_bit", (byte) 0).build(),
                        KnownBlockRepr.B_18_0_LEAVES_OAK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("old_leaf_type", "spruce").addByte("persistent_bit", (byte) 0).addByte("update_bit", (byte) 0).build(),
                        KnownBlockRepr.B_18_1_LEAVES_SPRUCE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("old_leaf_type", "birch").addByte("persistent_bit", (byte) 0).addByte("update_bit", (byte) 0).build(),
                        KnownBlockRepr.B_18_2_LEAVES_BIRCH
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("old_leaf_type", "jungle").addByte("persistent_bit", (byte) 0).addByte("update_bit", (byte) 0).build(),
                        KnownBlockRepr.B_18_3_LEAVES_JUNGLE
                )
        });
        mMapTypeToBlocks.put("sponge", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("sponge_type", "dry").build(),
                        KnownBlockRepr.B_19_0_SPONGE_DRY
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("sponge_type", "wet").build(),
                        KnownBlockRepr.B_19_1_SPONGE_WET
                )
        });
        mMapTypeToBlocks.put("glass", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_20_0_GLASS
                )
        });
        mMapTypeToBlocks.put("lapis_ore", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_21_0_LAPIS_ORE
                )
        });
        mMapTypeToBlocks.put("lapis_block", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_22_0_LAPIS_BLOCK
                )
        });
        mMapTypeToBlocks.put("dispenser", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("triggered_bit", (byte) 0).build(),
                        KnownBlockRepr.B_23_0_DISPENSER
                )
        });
        mMapTypeToBlocks.put("sandstone", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("sand_stone_type", "default").build(),
                        KnownBlockRepr.B_24_0_SANDSTONE_DEFAULT
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("sand_stone_type", "heiroglyphs").build(),
                        KnownBlockRepr.B_24_1_SANDSTONE_CHISELED
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("sand_stone_type", "cut").build(),
                        KnownBlockRepr.B_24_2_SANDSTONE_SMOOTH
                )
        });
        mMapTypeToBlocks.put("noteblock", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_25_0_NOTEBLOCK
                )
        });
        mMapTypeToBlocks.put("bed", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("head_piece_bit", (byte) 0).addByte("occupied_bit", (byte) 0).build(),
                        KnownBlockRepr.B_26_0_BED
                )
        });
        mMapTypeToBlocks.put("golden_rail", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("rail_direction", 0).addByte("rail_data_bit", (byte) 0).build(),
                        KnownBlockRepr.B_27_0_GOLDEN_RAIL
                )
        });
        mMapTypeToBlocks.put("detector_rail", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("rail_direction", 0).addByte("rail_data_bit", (byte) 0).build(),
                        KnownBlockRepr.B_28_0_DETECTOR_RAIL
                )
        });
        mMapTypeToBlocks.put("sticky_piston", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).build(),
                        KnownBlockRepr.B_29_0_STICKY_PISTON
                )
        });
        mMapTypeToBlocks.put("web", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_30_0_WEB
                )
        });
        mMapTypeToBlocks.put("tallgrass", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("tall_grass_type", "tall").build(),
                        KnownBlockRepr.B_31_1_TALLGRASS_FERN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("tall_grass_type", "fern").build(),
                        KnownBlockRepr.B_31_2_TALLGRASS_GRASS
                )
        });
        mMapTypeToBlocks.put("deadbush", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_32_0_DEADBUSH
                )
        });
        mMapTypeToBlocks.put("piston", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).build(),
                        KnownBlockRepr.B_33_0_PISTON
                )
        });
        mMapTypeToBlocks.put("pistonArmCollision", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).build(),
                        KnownBlockRepr.B_34_0_PISTONARMCOLLISION
                )
        });
        mMapTypeToBlocks.put("wool", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "white").build(),
                        KnownBlockRepr.B_35_0_WOOL_WHITE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "orange").build(),
                        KnownBlockRepr.B_35_1_WOOL_ORANGE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "magenta").build(),
                        KnownBlockRepr.B_35_2_WOOL_MAGENTA
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "light_blue").build(),
                        KnownBlockRepr.B_35_3_WOOL_LIGHT_BLUE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "yellow").build(),
                        KnownBlockRepr.B_35_4_WOOL_YELLOW
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "lime").build(),
                        KnownBlockRepr.B_35_5_WOOL_LIME
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "pink").build(),
                        KnownBlockRepr.B_35_6_WOOL_PINK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "gray").build(),
                        KnownBlockRepr.B_35_7_WOOL_GRAY
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "silver").build(),
                        KnownBlockRepr.B_35_8_WOOL_SILVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "cyan").build(),
                        KnownBlockRepr.B_35_9_WOOL_CYAN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "purple").build(),
                        KnownBlockRepr.B_35_10_WOOL_PURPLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "blue").build(),
                        KnownBlockRepr.B_35_11_WOOL_BLUE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "brown").build(),
                        KnownBlockRepr.B_35_12_WOOL_BROWN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "green").build(),
                        KnownBlockRepr.B_35_13_WOOL_GREEN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "red").build(),
                        KnownBlockRepr.B_35_14_WOOL_RED
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "black").build(),
                        KnownBlockRepr.B_35_15_WOOL_BLACK
                )
        });
        mMapTypeToBlocks.put("element_0", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_36_0_ELEMENT0
                ),
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_36_1_ELEMENT0
                ),
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_36_2_ELEMENT0
                ),
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_36_3_ELEMENT0
                ),
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_36_4_ELEMENT0
                ),
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_36_5_ELEMENT0
                ),
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_36_6_ELEMENT0
                ),
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_36_7_ELEMENT0
                ),
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_36_8_ELEMENT0
                ),
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_36_9_ELEMENT0
                ),
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_36_10_ELEMENT0
                ),
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_36_11_ELEMENT0
                ),
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_36_12_ELEMENT0
                ),
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_36_13_ELEMENT0
                ),
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_36_14_ELEMENT0
                ),
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_36_15_ELEMENT0
                )
        });
        mMapTypeToBlocks.put("yellow_flower", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_37_0_YELLOW_FLOWER
                )
        });
        mMapTypeToBlocks.put("red_flower", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("flower_type", "poppy").build(),
                        KnownBlockRepr.B_38_0_RED_FLOWER_POPPY
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("flower_type", "orchid").build(),
                        KnownBlockRepr.B_38_1_RED_FLOWER_BLUE_ORCHID
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("flower_type", "allium").build(),
                        KnownBlockRepr.B_38_2_RED_FLOWER_ALLIUM
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("flower_type", "houstonia").build(),
                        KnownBlockRepr.B_38_3_RED_FLOWER_HOUSTONIA
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("flower_type", "tulip_red").build(),
                        KnownBlockRepr.B_38_4_RED_FLOWER_TULIP_RED
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("flower_type", "tulip_orange").build(),
                        KnownBlockRepr.B_38_5_RED_FLOWER_TULIP_ORANGE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("flower_type", "tulip_white").build(),
                        KnownBlockRepr.B_38_6_RED_FLOWER_TULIP_WHITE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("flower_type", "tulip_pink").build(),
                        KnownBlockRepr.B_38_7_RED_FLOWER_TULIP_PINK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("flower_type", "oxeye").build(),
                        KnownBlockRepr.B_38_8_RED_FLOWER_OXEYE_DAISY
                )
        });
        mMapTypeToBlocks.put("brown_mushroom", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_39_0_BROWN_MUSHROOM
                )
        });
        mMapTypeToBlocks.put("red_mushroom", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_40_0_RED_MUSHROOM
                )
        });
        mMapTypeToBlocks.put("gold_block", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_41_0_GOLD_BLOCK
                )
        });
        mMapTypeToBlocks.put("iron_block", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_42_0_IRON_BLOCK
                )
        });
        mMapTypeToBlocks.put("double_stone_slab", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_slab_type", "smooth_stone").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_43_0_DOUBLE_STONE_SLAB_STONE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_slab_type", "sandstone").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_43_1_DOUBLE_STONE_SLAB_SAND
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_slab_type", "wood").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_43_2_DOUBLE_STONE_SLAB_WOOD
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_slab_type", "cobblestone").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_43_3_DOUBLE_STONE_SLAB_COBBLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_slab_type", "brick").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_43_4_DOUBLE_STONE_SLAB_BRICK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_slab_type", "stone_brick").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_43_5_DOUBLE_STONE_SLAB_SMOOTH_STONE_BRICK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_slab_type", "quartz").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_43_6_DOUBLE_STONE_SLAB_QUARTZ
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_slab_type", "nether_brick").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_43_7_DOUBLE_STONE_SLAB_NETHER_BRICK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_slab_type", "smooth_stone").addByte("top_slot_bit", (byte) 1).build(),
                        KnownBlockRepr.B_43_8_DOUBLE_STONE_SLAB_RED_SANDSTONE
                )
        });
        mMapTypeToBlocks.put("stone_slab", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_slab_type", "smooth_stone").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_44_0_STONE_SLAB_STONE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_slab_type", "sandstone").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_44_1_STONE_SLAB_SAND
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_slab_type", "wood").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_44_2_STONE_SLAB_WOOD
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_slab_type", "cobblestone").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_44_3_STONE_SLAB_COBBLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_slab_type", "brick").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_44_4_STONE_SLAB_BRICK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_slab_type", "stone_brick").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_44_5_STONE_SLAB_SMOOTH_STONE_BRICK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_slab_type", "quartz").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_44_6_STONE_SLAB_QUARTZ
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_slab_type", "nether_brick").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_44_7_STONE_SLAB_NETHER_BRICK
                )
        });
        mMapTypeToBlocks.put("brick_block", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_45_0_BRICK_BLOCK
                )
        });
        mMapTypeToBlocks.put("tnt", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addByte("allow_underwater_bit", (byte) 0).addByte("explode_bit", (byte) 0).build(),
                        KnownBlockRepr.B_46_0_TNT
                )
        });
        mMapTypeToBlocks.put("bookshelf", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_47_0_BOOKSHELF
                )
        });
        mMapTypeToBlocks.put("mossy_cobblestone", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_48_0_MOSSY_COBBLESTONE
                )
        });
        mMapTypeToBlocks.put("obsidian", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_49_0_OBSIDIAN
                )
        });
        mMapTypeToBlocks.put("torch", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("torch_facing_direction", "unknown").build(),
                        KnownBlockRepr.B_50_0_TORCH
                )
        });
        mMapTypeToBlocks.put("fire", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("age", 0).build(),
                        KnownBlockRepr.B_51_0_FIRE
                )
        });
        mMapTypeToBlocks.put("mob_spawner", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_52_0_MOB_SPAWNER
                )
        });
        mMapTypeToBlocks.put("oak_stairs", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_53_0_OAK_STAIRS
                )
        });
        mMapTypeToBlocks.put("chest", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).build(),
                        KnownBlockRepr.B_54_0_CHEST
                )
        });
        mMapTypeToBlocks.put("redstone_wire", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 0).build(),
                        KnownBlockRepr.B_55_0_REDSTONE_WIRE
                )
        });
        mMapTypeToBlocks.put("diamond_ore", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_56_0_DIAMOND_ORE
                )
        });
        mMapTypeToBlocks.put("diamond_block", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_57_0_DIAMOND_BLOCK
                )
        });
        mMapTypeToBlocks.put("crafting_table", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_58_0_CRAFTING_TABLE
                )
        });
        mMapTypeToBlocks.put("wheat", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("growth", 0).build(),
                        KnownBlockRepr.B_59_0_WHEAT
                )
        });
        mMapTypeToBlocks.put("farmland", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("moisturized_amount", 0).build(),
                        KnownBlockRepr.B_60_0_FARMLAND
                )
        });
        mMapTypeToBlocks.put("furnace", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).build(),
                        KnownBlockRepr.B_61_0_FURNACE
                )
        });
        mMapTypeToBlocks.put("lit_furnace", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).build(),
                        KnownBlockRepr.B_62_0_LIT_FURNACE
                )
        });
        mMapTypeToBlocks.put("standing_sign", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("ground_sign_direction", 0).build(),
                        KnownBlockRepr.B_63_0_STANDING_SIGN
                )
        });
        mMapTypeToBlocks.put("wooden_door", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("door_hinge_bit", (byte) 0).addByte("open_bit", (byte) 0).addByte("upper_block_bit", (byte) 0).build(),
                        KnownBlockRepr.B_64_0_WOODEN_DOOR
                )
        });
        mMapTypeToBlocks.put("ladder", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).build(),
                        KnownBlockRepr.B_65_0_LADDER
                )
        });
        mMapTypeToBlocks.put("rail", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("rail_direction", 0).build(),
                        KnownBlockRepr.B_66_0_RAIL
                )
        });
        mMapTypeToBlocks.put("stone_stairs", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_67_0_STONE_STAIRS
                )
        });
        mMapTypeToBlocks.put("wall_sign", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).build(),
                        KnownBlockRepr.B_68_0_WALL_SIGN
                )
        });
        mMapTypeToBlocks.put("lever", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("lever_direction", "down_east_west").addByte("open_bit", (byte) 0).build(),
                        KnownBlockRepr.B_69_0_LEVER
                )
        });
        mMapTypeToBlocks.put("stone_pressure_plate", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 0).build(),
                        KnownBlockRepr.B_70_0_STONE_PRESSURE_PLATE
                )
        });
        mMapTypeToBlocks.put("iron_door", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("door_hinge_bit", (byte) 0).addByte("open_bit", (byte) 0).addByte("upper_block_bit", (byte) 0).build(),
                        KnownBlockRepr.B_71_0_IRON_DOOR
                )
        });
        mMapTypeToBlocks.put("wooden_pressure_plate", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 0).build(),
                        KnownBlockRepr.B_72_0_WOODEN_PRESSURE_PLATE
                )
        });
        mMapTypeToBlocks.put("redstone_ore", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_73_0_REDSTONE_ORE
                )
        });
        mMapTypeToBlocks.put("lit_redstone_ore", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_74_0_LIT_REDSTONE_ORE
                )
        });
        mMapTypeToBlocks.put("unlit_redstone_torch", new Pair[]{

        });
        mMapTypeToBlocks.put("redstone_torch", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("torch_facing_direction", "unknown").build(),
                        KnownBlockRepr.B_76_0_REDSTONE_TORCH
                )
        });
        mMapTypeToBlocks.put("stone_button", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_77_0_STONE_BUTTON
                )
        });
        mMapTypeToBlocks.put("snow_layer", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addByte("covered_bit", (byte) 0).addInt("height", 0).build(),
                        KnownBlockRepr.B_78_0_SNOW_LAYER
                )
        });
        mMapTypeToBlocks.put("ice", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_79_0_ICE
                )
        });
        mMapTypeToBlocks.put("snow", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_80_0_SNOW
                )
        });
        mMapTypeToBlocks.put("cactus", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("age", 0).build(),
                        KnownBlockRepr.B_81_0_CACTUS
                )
        });
        mMapTypeToBlocks.put("clay", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_82_0_CLAY
                )
        });
        mMapTypeToBlocks.put("reeds", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("age", 0).build(),
                        KnownBlockRepr.B_83_0_REEDS
                )
        });
        mMapTypeToBlocks.put("jukebox", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_84_0_JUKEBOX
                )
        });
        mMapTypeToBlocks.put("fence", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "oak").build(),
                        KnownBlockRepr.B_85_0_FENCE_FENCE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "spruce").build(),
                        KnownBlockRepr.B_85_1_FENCE_SPRUCE_FENCE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "birch").build(),
                        KnownBlockRepr.B_85_2_FENCE_BIRCH_FENCE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "jungle").build(),
                        KnownBlockRepr.B_85_3_FENCE_JUNGLE_FENCE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "acacia").build(),
                        KnownBlockRepr.B_85_4_FENCE_ACACIA_FENCE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "dark_oak").build(),
                        KnownBlockRepr.B_85_5_FENCE_DARK_OAK_FENCE
                )
        });
        mMapTypeToBlocks.put("pumpkin", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).build(),
                        KnownBlockRepr.B_86_0_PUMPKIN
                )
        });
        mMapTypeToBlocks.put("netherrack", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_87_0_NETHERRACK
                )
        });
        mMapTypeToBlocks.put("soul_sand", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_88_0_SOUL_SAND
                )
        });
        mMapTypeToBlocks.put("glowstone", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_89_0_GLOWSTONE
                )
        });
        mMapTypeToBlocks.put("portal", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("portal_axis", "unknown").build(),
                        KnownBlockRepr.B_90_0_PORTAL
                )
        });
        mMapTypeToBlocks.put("lit_pumpkin", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).build(),
                        KnownBlockRepr.B_91_0_LIT_PUMPKIN
                )
        });
        mMapTypeToBlocks.put("cake", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("bite_counter", 0).build(),
                        KnownBlockRepr.B_92_0_CAKE
                )
        });
        mMapTypeToBlocks.put("unpowered_repeater", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addInt("repeater_delay", 0).build(),
                        KnownBlockRepr.B_93_0_UNPOWERED_REPEATER
                )
        });
        mMapTypeToBlocks.put("powered_repeater", new Pair[]{

        });
        mMapTypeToBlocks.put("invisibleBedrock", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_95_0_INVISIBLEBEDROCK
                )
        });
        mMapTypeToBlocks.put("trapdoor", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_96_0_TRAPDOOR
                )
        });
        mMapTypeToBlocks.put("monster_egg", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("monster_egg_stone_type", "stone").build(),
                        KnownBlockRepr.B_97_0_MONSTER_EGG_STONE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("monster_egg_stone_type", "cobblestone").build(),
                        KnownBlockRepr.B_97_1_MONSTER_EGG_COBBLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("monster_egg_stone_type", "stone_brick").build(),
                        KnownBlockRepr.B_97_2_MONSTER_EGG_BRICK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("monster_egg_stone_type", "mossy_stone_brick").build(),
                        KnownBlockRepr.B_97_3_MONSTER_EGG_MOSSYBRICK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("monster_egg_stone_type", "cracked_stone_brick").build(),
                        KnownBlockRepr.B_97_4_MONSTER_EGG_CRACKEDBRICK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("monster_egg_stone_type", "chiseled_stone_brick").build(),
                        KnownBlockRepr.B_97_5_MONSTER_EGG_CHISELEDBRICK
                )
        });
        mMapTypeToBlocks.put("stonebrick", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_brick_type", "default").build(),
                        KnownBlockRepr.B_98_0_STONEBRICK_DEFAULT
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_brick_type", "mossy").build(),
                        KnownBlockRepr.B_98_1_STONEBRICK_MOSSY
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_brick_type", "cracked").build(),
                        KnownBlockRepr.B_98_2_STONEBRICK_CRACKED
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_brick_type", "chiseled").build(),
                        KnownBlockRepr.B_98_3_STONEBRICK_CHISELED
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_brick_type", "smooth").build(),
                        KnownBlockRepr.B_98_4_STONEBRICK_SMOOTH
                )
        });
        mMapTypeToBlocks.put("brown_mushroom_block", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("huge_mushroom_bits", 0).build(),
                        KnownBlockRepr.B_99_0_BROWN_MUSHROOM_BLOCK
                )
        });
        mMapTypeToBlocks.put("red_mushroom_block", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("huge_mushroom_bits", 0).build(),
                        KnownBlockRepr.B_100_0_RED_MUSHROOM_BLOCK
                )
        });
        mMapTypeToBlocks.put("iron_bars", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_101_0_IRON_BARS
                )
        });
        mMapTypeToBlocks.put("glass_pane", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_102_0_GLASS_PANE
                )
        });
        mMapTypeToBlocks.put("melon_block", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_103_0_MELON_BLOCK
                )
        });
        mMapTypeToBlocks.put("pumpkin_stem", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("growth", 0).build(),
                        KnownBlockRepr.B_104_0_PUMPKIN_STEM
                )
        });
        mMapTypeToBlocks.put("melon_stem", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("growth", 0).build(),
                        KnownBlockRepr.B_105_0_MELON_STEM
                )
        });
        mMapTypeToBlocks.put("vine", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("vine_direction_bits", 0).build(),
                        KnownBlockRepr.B_106_0_VINE
                )
        });
        mMapTypeToBlocks.put("fence_gate", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("in_wall_bit", (byte) 0).addByte("open_bit", (byte) 0).build(),
                        KnownBlockRepr.B_107_0_FENCE_GATE
                )
        });
        mMapTypeToBlocks.put("brick_stairs", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_108_0_BRICK_STAIRS
                )
        });
        mMapTypeToBlocks.put("stone_brick_stairs", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_109_0_STONE_BRICK_STAIRS
                )
        });
        mMapTypeToBlocks.put("mycelium", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_110_0_MYCELIUM
                )
        });
        mMapTypeToBlocks.put("waterlily", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_111_0_WATERLILY
                )
        });
        mMapTypeToBlocks.put("nether_brick", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_112_0_NETHER_BRICK
                )
        });
        mMapTypeToBlocks.put("nether_brick_fence", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_113_0_NETHER_BRICK_FENCE
                )
        });
        mMapTypeToBlocks.put("nether_brick_stairs", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_114_0_NETHER_BRICK_STAIRS
                )
        });
        mMapTypeToBlocks.put("nether_wart", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("age", 0).build(),
                        KnownBlockRepr.B_115_0_NETHER_WART
                )
        });
        mMapTypeToBlocks.put("enchanting_table", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_116_0_ENCHANTING_TABLE
                )
        });
        mMapTypeToBlocks.put("brewing_stand", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addByte("brewing_stand_slot_a_bit", (byte) 0).addByte("brewing_stand_slot_b_bit", (byte) 0).addByte("brewing_stand_slot_c_bit", (byte) 0).build(),
                        KnownBlockRepr.B_117_0_BREWING_STAND
                )
        });
        mMapTypeToBlocks.put("cauldron", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("cauldron_liquid", "water").addInt("fill_level", 0).build(),
                        KnownBlockRepr.B_118_0_CAULDRON
                )
        });
        mMapTypeToBlocks.put("end_portal", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_119_0_END_PORTAL
                )
        });
        mMapTypeToBlocks.put("end_portal_frame", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("end_portal_eye_bit", (byte) 0).build(),
                        KnownBlockRepr.B_120_0_END_PORTAL_FRAME
                )
        });
        mMapTypeToBlocks.put("end_stone", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_121_0_END_STONE
                )
        });
        mMapTypeToBlocks.put("dragon_egg", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_122_0_DRAGON_EGG
                )
        });
        mMapTypeToBlocks.put("redstone_lamp", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_123_0_REDSTONE_LAMP
                )
        });
        mMapTypeToBlocks.put("lit_redstone_lamp", new Pair[]{

        });
        mMapTypeToBlocks.put("dropper", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("triggered_bit", (byte) 0).build(),
                        KnownBlockRepr.B_125_0_DROPPER
                )
        });
        mMapTypeToBlocks.put("activator_rail", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("rail_direction", 0).addByte("rail_data_bit", (byte) 0).build(),
                        KnownBlockRepr.B_126_0_ACTIVATOR_RAIL
                )
        });
        mMapTypeToBlocks.put("cocoa", new Pair[]{

        });
        mMapTypeToBlocks.put("sandstone_stairs", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_128_0_SANDSTONE_STAIRS
                )
        });
        mMapTypeToBlocks.put("emerald_ore", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_129_0_EMERALD_ORE
                )
        });
        mMapTypeToBlocks.put("ender_chest", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).build(),
                        KnownBlockRepr.B_130_0_ENDER_CHEST
                )
        });
        mMapTypeToBlocks.put("tripwire_hook", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("attached_bit", (byte) 0).addByte("powered_bit", (byte) 0).build(),
                        KnownBlockRepr.B_131_0_TRIPWIRE_HOOK
                )
        });
        mMapTypeToBlocks.put("tripWire", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addByte("attached_bit", (byte) 0).addByte("disarmed_bit", (byte) 0).addByte("powered_bit", (byte) 0).addByte("suspended_bit", (byte) 0).build(),
                        KnownBlockRepr.B_132_0_TRIPWIRE
                )
        });
        mMapTypeToBlocks.put("emerald_block", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_133_0_EMERALD_BLOCK
                )
        });
        mMapTypeToBlocks.put("spruce_stairs", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_134_0_SPRUCE_STAIRS
                )
        });
        mMapTypeToBlocks.put("birch_stairs", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_135_0_BIRCH_STAIRS
                )
        });
        mMapTypeToBlocks.put("jungle_stairs", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_136_0_JUNGLE_STAIRS
                )
        });
        mMapTypeToBlocks.put("beacon", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_138_0_BEACON
                )
        });
        mMapTypeToBlocks.put("cobblestone_wall", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("wall_block_type", "cobblestone").build(),
                        KnownBlockRepr.B_139_0_COBBLESTONE_WALL_NORMAL
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("wall_block_type", "mossy_cobblestone").build(),
                        KnownBlockRepr.B_139_1_COBBLESTONE_WALL_MOSSY
                )
        });
        mMapTypeToBlocks.put("flower_pot", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addByte("update_bit", (byte) 0).build(),
                        KnownBlockRepr.B_140_0_FLOWER_POT
                )
        });
        mMapTypeToBlocks.put("carrots", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("growth", 0).build(),
                        KnownBlockRepr.B_141_0_CARROTS
                )
        });
        mMapTypeToBlocks.put("potatoes", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("growth", 0).build(),
                        KnownBlockRepr.B_142_0_POTATOES
                )
        });
        mMapTypeToBlocks.put("wooden_button", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_143_0_WOODEN_BUTTON
                )
        });
        mMapTypeToBlocks.put("skull", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("no_drop_bit", (byte) 0).build(),
                        KnownBlockRepr.B_144_0_SKULL
                )
        });
        mMapTypeToBlocks.put("anvil", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addProperty("damage", "undamaged").build(),
                        KnownBlockRepr.B_145_0_ANVIL_INTACT
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addProperty("damage", "slightly_damaged").build(),
                        KnownBlockRepr.B_145_4_ANVIL_SLIGHTLY_DAMAGED
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addProperty("damage", "very_damaged").build(),
                        KnownBlockRepr.B_145_8_ANVIL_VERY_DAMAGED
                )
        });
        mMapTypeToBlocks.put("trapped_chest", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).build(),
                        KnownBlockRepr.B_146_0_TRAPPED_CHEST
                )
        });
        mMapTypeToBlocks.put("light_weighted_pressure_plate", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 0).build(),
                        KnownBlockRepr.B_147_0_LIGHT_WEIGHTED_PRESSURE_PLATE
                )
        });
        mMapTypeToBlocks.put("heavy_weighted_pressure_plate", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 0).build(),
                        KnownBlockRepr.B_148_0_HEAVY_WEIGHTED_PRESSURE_PLATE
                )
        });
        mMapTypeToBlocks.put("unpowered_comparator", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("output_lit_bit", (byte) 0).addByte("output_subtract_bit", (byte) 0).build(),
                        KnownBlockRepr.B_149_0_UNPOWERED_COMPARATOR
                )
        });
        mMapTypeToBlocks.put("powered_comparator", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("output_lit_bit", (byte) 0).addByte("output_subtract_bit", (byte) 0).build(),
                        KnownBlockRepr.B_150_0_POWERED_COMPARATOR
                )
        });
        mMapTypeToBlocks.put("daylight_detector", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 0).build(),
                        KnownBlockRepr.B_151_0_DAYLIGHT_DETECTOR
                )
        });
        mMapTypeToBlocks.put("redstone_block", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_152_0_REDSTONE_BLOCK
                )
        });
        mMapTypeToBlocks.put("quartz_ore", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_153_0_QUARTZ_ORE
                )
        });
        mMapTypeToBlocks.put("hopper", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("toggle_bit", (byte) 0).build(),
                        KnownBlockRepr.B_154_0_HOPPER
                )
        });
        mMapTypeToBlocks.put("quartz_block", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("chisel_type", "default").addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_155_0_QUARTZ_BLOCK_DEFAULT
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("chisel_type", "chiseled").addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_155_1_QUARTZ_BLOCK_CHISELED
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("chisel_type", "lines").addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_155_2_QUARTZ_BLOCK_LINES
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("chisel_type", "smooth").addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_155_3_QUARTZ_BLOCK_DEFAULT
                )
        });
        mMapTypeToBlocks.put("quartz_stairs", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_156_0_QUARTZ_STAIRS
                )
        });
        mMapTypeToBlocks.put("double_wooden_slab", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "oak").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_157_0_DOUBLE_WOODEN_SLAB_OAK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "spruce").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_157_1_DOUBLE_WOODEN_SLAB_SPRUCE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "birch").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_157_2_DOUBLE_WOODEN_SLAB_BIRCH
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "jungle").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_157_3_DOUBLE_WOODEN_SLAB_JUNGLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "acacia").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_157_4_DOUBLE_WOODEN_SLAB_ACACIA
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "dark_oak").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_157_5_DOUBLE_WOODEN_SLAB_BIG_OAK
                )
        });
        mMapTypeToBlocks.put("wooden_slab", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "oak").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_158_0_WOODEN_SLAB_OAK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "spruce").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_158_1_WOODEN_SLAB_SPRUCE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "birch").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_158_2_WOODEN_SLAB_BIRCH
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "jungle").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_158_3_WOODEN_SLAB_JUNGLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "acacia").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_158_4_WOODEN_SLAB_ACACIA
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("wood_type", "dark_oak").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_158_5_WOODEN_SLAB_BIG_OAK
                )
        });
        mMapTypeToBlocks.put("stained_hardened_clay", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "white").build(),
                        KnownBlockRepr.B_159_0_STAINED_HARDENED_CLAY_WHITE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "orange").build(),
                        KnownBlockRepr.B_159_1_STAINED_HARDENED_CLAY_ORANGE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "magenta").build(),
                        KnownBlockRepr.B_159_2_STAINED_HARDENED_CLAY_MAGENTA
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "light_blue").build(),
                        KnownBlockRepr.B_159_3_STAINED_HARDENED_CLAY_LIGHT_BLUE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "yellow").build(),
                        KnownBlockRepr.B_159_4_STAINED_HARDENED_CLAY_YELLOW
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "lime").build(),
                        KnownBlockRepr.B_159_5_STAINED_HARDENED_CLAY_LIME
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "pink").build(),
                        KnownBlockRepr.B_159_6_STAINED_HARDENED_CLAY_PINK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "gray").build(),
                        KnownBlockRepr.B_159_7_STAINED_HARDENED_CLAY_GRAY
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "silver").build(),
                        KnownBlockRepr.B_159_8_STAINED_HARDENED_CLAY_SILVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "cyan").build(),
                        KnownBlockRepr.B_159_9_STAINED_HARDENED_CLAY_CYAN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "purple").build(),
                        KnownBlockRepr.B_159_10_STAINED_HARDENED_CLAY_PURPLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "blue").build(),
                        KnownBlockRepr.B_159_11_STAINED_HARDENED_CLAY_BLUE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "brown").build(),
                        KnownBlockRepr.B_159_12_STAINED_HARDENED_CLAY_BROWN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "green").build(),
                        KnownBlockRepr.B_159_13_STAINED_HARDENED_CLAY_GREEN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "red").build(),
                        KnownBlockRepr.B_159_14_STAINED_HARDENED_CLAY_RED
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "black").build(),
                        KnownBlockRepr.B_159_15_STAINED_HARDENED_CLAY_BLACK
                )
        });
        mMapTypeToBlocks.put("stained_glass_pane", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "white").build(),
                        KnownBlockRepr.B_160_0_STAINED_GLASS_PANE_WHITE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "orange").build(),
                        KnownBlockRepr.B_160_1_STAINED_GLASS_PANE_ORANGE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "magenta").build(),
                        KnownBlockRepr.B_160_2_STAINED_GLASS_PANE_MAGENTA
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "light_blue").build(),
                        KnownBlockRepr.B_160_3_STAINED_GLASS_PANE_LIGHT_BLUE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "yellow").build(),
                        KnownBlockRepr.B_160_4_STAINED_GLASS_PANE_YELLOW
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "lime").build(),
                        KnownBlockRepr.B_160_5_STAINED_GLASS_PANE_LIME
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "pink").build(),
                        KnownBlockRepr.B_160_6_STAINED_GLASS_PANE_PINK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "gray").build(),
                        KnownBlockRepr.B_160_7_STAINED_GLASS_PANE_GRAY
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "silver").build(),
                        KnownBlockRepr.B_160_8_STAINED_GLASS_PANE_SILVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "cyan").build(),
                        KnownBlockRepr.B_160_9_STAINED_GLASS_PANE_CYAN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "purple").build(),
                        KnownBlockRepr.B_160_10_STAINED_GLASS_PANE_PURPLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "blue").build(),
                        KnownBlockRepr.B_160_11_STAINED_GLASS_PANE_BLUE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "brown").build(),
                        KnownBlockRepr.B_160_12_STAINED_GLASS_PANE_BROWN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "green").build(),
                        KnownBlockRepr.B_160_13_STAINED_GLASS_PANE_GREEN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "red").build(),
                        KnownBlockRepr.B_160_14_STAINED_GLASS_PANE_RED
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "black").build(),
                        KnownBlockRepr.B_160_15_STAINED_GLASS_PANE_BLACK
                )
        });
        mMapTypeToBlocks.put("leaves2", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("new_leaf_type", "acacia").addByte("persistent_bit", (byte) 0).addByte("update_bit", (byte) 0).build(),
                        KnownBlockRepr.B_161_0_LEAVES2_ACACIA
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("new_leaf_type", "dark_oak").addByte("persistent_bit", (byte) 0).addByte("update_bit", (byte) 0).build(),
                        KnownBlockRepr.B_161_1_LEAVES2_BIG_OAK
                )
        });
        mMapTypeToBlocks.put("log2", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("new_log_type", "acacia").addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_162_0_LOG2_ACACIA
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("new_log_type", "dark_oak").addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_162_1_LOG2_BIG_OAK
                )
        });
        mMapTypeToBlocks.put("acacia_stairs", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_163_0_ACACIA_STAIRS
                )
        });
        mMapTypeToBlocks.put("dark_oak_stairs", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_164_0_DARK_OAK_STAIRS
                )
        });
        mMapTypeToBlocks.put("slime", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_165_0_SLIME
                )
        });
        mMapTypeToBlocks.put("iron_trapdoor", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_167_0_IRON_TRAPDOOR
                )
        });
        mMapTypeToBlocks.put("prismarine", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("prismarine_block_type", "default").build(),
                        KnownBlockRepr.B_168_0_PRISMARINE_ROUGH
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("prismarine_block_type", "dark").build(),
                        KnownBlockRepr.B_168_1_PRISMARINE_DARK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("prismarine_block_type", "bricks").build(),
                        KnownBlockRepr.B_168_2_PRISMARINE_BRICKS
                )
        });
        mMapTypeToBlocks.put("seaLantern", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_169_0_SEALANTERN
                )
        });
        mMapTypeToBlocks.put("hay_block", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("deprecated", 0).addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_170_0_HAY_BLOCK
                )
        });
        mMapTypeToBlocks.put("carpet", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "white").build(),
                        KnownBlockRepr.B_171_0_CARPET_WHITE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "orange").build(),
                        KnownBlockRepr.B_171_1_CARPET_ORANGE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "magenta").build(),
                        KnownBlockRepr.B_171_2_CARPET_MAGENTA
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "light_blue").build(),
                        KnownBlockRepr.B_171_3_CARPET_LIGHT_BLUE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "yellow").build(),
                        KnownBlockRepr.B_171_4_CARPET_YELLOW
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "lime").build(),
                        KnownBlockRepr.B_171_5_CARPET_LIME
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "pink").build(),
                        KnownBlockRepr.B_171_6_CARPET_PINK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "gray").build(),
                        KnownBlockRepr.B_171_7_CARPET_GRAY
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "silver").build(),
                        KnownBlockRepr.B_171_8_CARPET_SILVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "cyan").build(),
                        KnownBlockRepr.B_171_9_CARPET_CYAN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "purple").build(),
                        KnownBlockRepr.B_171_10_CARPET_PURPLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "blue").build(),
                        KnownBlockRepr.B_171_11_CARPET_BLUE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "brown").build(),
                        KnownBlockRepr.B_171_12_CARPET_BROWN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "green").build(),
                        KnownBlockRepr.B_171_13_CARPET_GREEN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "red").build(),
                        KnownBlockRepr.B_171_14_CARPET_RED
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "black").build(),
                        KnownBlockRepr.B_171_15_CARPET_BLACK
                )
        });
        mMapTypeToBlocks.put("hardened_clay", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_172_0_HARDENED_CLAY
                )
        });
        mMapTypeToBlocks.put("coal_block", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_173_0_COAL_BLOCK
                )
        });
        mMapTypeToBlocks.put("packed_ice", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_174_0_PACKED_ICE
                )
        });
        mMapTypeToBlocks.put("double_plant", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("double_plant_type", "sunflower").addByte("upper_block_bit", (byte) 0).build(),
                        KnownBlockRepr.B_175_0_DOUBLE_PLANT_SUNFLOWER
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("double_plant_type", "syringa").addByte("upper_block_bit", (byte) 0).build(),
                        KnownBlockRepr.B_175_1_DOUBLE_PLANT_SYRINGA
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("double_plant_type", "grass").addByte("upper_block_bit", (byte) 0).build(),
                        KnownBlockRepr.B_175_2_DOUBLE_PLANT_GRASS
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("double_plant_type", "fern").addByte("upper_block_bit", (byte) 0).build(),
                        KnownBlockRepr.B_175_3_DOUBLE_PLANT_FERN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("double_plant_type", "rose").addByte("upper_block_bit", (byte) 0).build(),
                        KnownBlockRepr.B_175_4_DOUBLE_PLANT_ROSE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("double_plant_type", "paeonia").addByte("upper_block_bit", (byte) 0).build(),
                        KnownBlockRepr.B_175_5_DOUBLE_PLANT_PAEONIA
                )
        });
        mMapTypeToBlocks.put("banner", new Pair[]{

        });
        mMapTypeToBlocks.put("daylight_detector_inverted", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 0).build(),
                        KnownBlockRepr.B_178_0_DAYLIGHT_DETECTOR_INVERTED
                )
        });
        mMapTypeToBlocks.put("red_sandstone", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("sand_stone_type", "default").build(),
                        KnownBlockRepr.B_179_0_RED_SANDSTONE_DEFAULT
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("sand_stone_type", "heiroglyphs").build(),
                        KnownBlockRepr.B_179_1_RED_SANDSTONE_CHISELED
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("sand_stone_type", "cut").build(),
                        KnownBlockRepr.B_179_2_RED_SANDSTONE_SMOOTH
                )
        });
        mMapTypeToBlocks.put("red_sandstone_stairs", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_180_0_RED_SANDSTONE_STAIRS
                )
        });
        mMapTypeToBlocks.put("double_stone_slab2", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_slab_type_2", "red_sandstone").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_181_0_DOUBLE_STONE_SLAB2_RED_SANDSTONE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_slab_type_2", "purpur").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_181_1_DOUBLE_STONE_SLAB2_PURPUR
                )
        });
        mMapTypeToBlocks.put("stone_slab2", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_slab_type_2", "red_sandstone").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_182_0_STONE_SLAB2_RED_SANDSTONE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("stone_slab_type_2", "purpur").addByte("top_slot_bit", (byte) 0).build(),
                        KnownBlockRepr.B_182_1_STONE_SLAB2_PURPUR
                )
        });
        mMapTypeToBlocks.put("spruce_fence_gate", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("in_wall_bit", (byte) 0).addByte("open_bit", (byte) 0).build(),
                        KnownBlockRepr.B_183_0_SPRUCE_FENCE_GATE
                )
        });
        mMapTypeToBlocks.put("birch_fence_gate", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("in_wall_bit", (byte) 0).addByte("open_bit", (byte) 0).build(),
                        KnownBlockRepr.B_184_0_BIRCH_FENCE_GATE
                )
        });
        mMapTypeToBlocks.put("jungle_fence_gate", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("in_wall_bit", (byte) 0).addByte("open_bit", (byte) 0).build(),
                        KnownBlockRepr.B_185_0_JUNGLE_FENCE_GATE
                )
        });
        mMapTypeToBlocks.put("dark_oak_fence_gate", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("in_wall_bit", (byte) 0).addByte("open_bit", (byte) 0).build(),
                        KnownBlockRepr.B_186_0_DARK_OAK_FENCE_GATE
                )
        });
        mMapTypeToBlocks.put("acacia_fence_gate", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("in_wall_bit", (byte) 0).addByte("open_bit", (byte) 0).build(),
                        KnownBlockRepr.B_187_0_ACACIA_FENCE_GATE
                )
        });
        mMapTypeToBlocks.put("repeating_command_block", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("conditional_bit", (byte) 0).build(),
                        KnownBlockRepr.B_188_0_REPEATING_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 1).addByte("conditional_bit", (byte) 0).build(),
                        KnownBlockRepr.B_188_1_REPEATING_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).addByte("conditional_bit", (byte) 0).build(),
                        KnownBlockRepr.B_188_2_REPEATING_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).addByte("conditional_bit", (byte) 0).build(),
                        KnownBlockRepr.B_188_3_REPEATING_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).addByte("conditional_bit", (byte) 0).build(),
                        KnownBlockRepr.B_188_4_REPEATING_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).addByte("conditional_bit", (byte) 0).build(),
                        KnownBlockRepr.B_188_5_REPEATING_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("conditional_bit", (byte) 0).build(),
                        KnownBlockRepr.B_188_6_REPEATING_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("conditional_bit", (byte) 0).build(),
                        KnownBlockRepr.B_188_7_REPEATING_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("conditional_bit", (byte) 1).build(),
                        KnownBlockRepr.B_188_8_REPEATING_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 1).addByte("conditional_bit", (byte) 1).build(),
                        KnownBlockRepr.B_188_9_REPEATING_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).addByte("conditional_bit", (byte) 1).build(),
                        KnownBlockRepr.B_188_10_REPEATING_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).addByte("conditional_bit", (byte) 1).build(),
                        KnownBlockRepr.B_188_11_REPEATING_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).addByte("conditional_bit", (byte) 1).build(),
                        KnownBlockRepr.B_188_12_REPEATING_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).addByte("conditional_bit", (byte) 1).build(),
                        KnownBlockRepr.B_188_13_REPEATING_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("conditional_bit", (byte) 1).build(),
                        KnownBlockRepr.B_188_14_REPEATING_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("conditional_bit", (byte) 1).build(),
                        KnownBlockRepr.B_188_15_REPEATING_COMMAND_BLOCK
                )
        });
        mMapTypeToBlocks.put("chain_command_block", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("conditional_bit", (byte) 0).build(),
                        KnownBlockRepr.B_189_0_CHAIN_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 1).addByte("conditional_bit", (byte) 0).build(),
                        KnownBlockRepr.B_189_1_CHAIN_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).addByte("conditional_bit", (byte) 0).build(),
                        KnownBlockRepr.B_189_2_CHAIN_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).addByte("conditional_bit", (byte) 0).build(),
                        KnownBlockRepr.B_189_3_CHAIN_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).addByte("conditional_bit", (byte) 0).build(),
                        KnownBlockRepr.B_189_4_CHAIN_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).addByte("conditional_bit", (byte) 0).build(),
                        KnownBlockRepr.B_189_5_CHAIN_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("conditional_bit", (byte) 0).build(),
                        KnownBlockRepr.B_189_6_CHAIN_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("conditional_bit", (byte) 0).build(),
                        KnownBlockRepr.B_189_7_CHAIN_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("conditional_bit", (byte) 1).build(),
                        KnownBlockRepr.B_189_8_CHAIN_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 1).addByte("conditional_bit", (byte) 1).build(),
                        KnownBlockRepr.B_189_9_CHAIN_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).addByte("conditional_bit", (byte) 1).build(),
                        KnownBlockRepr.B_189_10_CHAIN_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).addByte("conditional_bit", (byte) 1).build(),
                        KnownBlockRepr.B_189_11_CHAIN_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).addByte("conditional_bit", (byte) 1).build(),
                        KnownBlockRepr.B_189_12_CHAIN_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).addByte("conditional_bit", (byte) 1).build(),
                        KnownBlockRepr.B_189_13_CHAIN_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("conditional_bit", (byte) 1).build(),
                        KnownBlockRepr.B_189_14_CHAIN_COMMAND_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("conditional_bit", (byte) 1).build(),
                        KnownBlockRepr.B_189_15_CHAIN_COMMAND_BLOCK
                )
        });
        mMapTypeToBlocks.put("hard_glass_pane", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_190_0_HARD_GLASS_PANE
                )
        });
        mMapTypeToBlocks.put("hard_stained_glass_pane", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "white").build(),
                        KnownBlockRepr.B_191_0_HARD_STAINED_GLASS_PANE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "orange").build(),
                        KnownBlockRepr.B_191_1_HARD_STAINED_GLASS_PANE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "magenta").build(),
                        KnownBlockRepr.B_191_2_HARD_STAINED_GLASS_PANE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "light_blue").build(),
                        KnownBlockRepr.B_191_3_HARD_STAINED_GLASS_PANE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "yellow").build(),
                        KnownBlockRepr.B_191_4_HARD_STAINED_GLASS_PANE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "lime").build(),
                        KnownBlockRepr.B_191_5_HARD_STAINED_GLASS_PANE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "pink").build(),
                        KnownBlockRepr.B_191_6_HARD_STAINED_GLASS_PANE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "gray").build(),
                        KnownBlockRepr.B_191_7_HARD_STAINED_GLASS_PANE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "silver").build(),
                        KnownBlockRepr.B_191_8_HARD_STAINED_GLASS_PANE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "cyan").build(),
                        KnownBlockRepr.B_191_9_HARD_STAINED_GLASS_PANE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "purple").build(),
                        KnownBlockRepr.B_191_10_HARD_STAINED_GLASS_PANE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "blue").build(),
                        KnownBlockRepr.B_191_11_HARD_STAINED_GLASS_PANE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "brown").build(),
                        KnownBlockRepr.B_191_12_HARD_STAINED_GLASS_PANE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "green").build(),
                        KnownBlockRepr.B_191_13_HARD_STAINED_GLASS_PANE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "red").build(),
                        KnownBlockRepr.B_191_14_HARD_STAINED_GLASS_PANE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "black").build(),
                        KnownBlockRepr.B_191_15_HARD_STAINED_GLASS_PANE
                )
        });
        mMapTypeToBlocks.put("chemical_heat", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_192_0_CHEMICAL_HEAT
                )
        });
        mMapTypeToBlocks.put("spruce_door", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("door_hinge_bit", (byte) 0).addByte("open_bit", (byte) 0).addByte("upper_block_bit", (byte) 0).build(),
                        KnownBlockRepr.B_193_0_SPRUCE_DOOR
                )
        });
        mMapTypeToBlocks.put("birch_door", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("door_hinge_bit", (byte) 0).addByte("open_bit", (byte) 0).addByte("upper_block_bit", (byte) 0).build(),
                        KnownBlockRepr.B_194_0_BIRCH_DOOR
                )
        });
        mMapTypeToBlocks.put("jungle_door", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("door_hinge_bit", (byte) 0).addByte("open_bit", (byte) 0).addByte("upper_block_bit", (byte) 0).build(),
                        KnownBlockRepr.B_195_0_JUNGLE_DOOR
                )
        });
        mMapTypeToBlocks.put("acacia_door", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("door_hinge_bit", (byte) 0).addByte("open_bit", (byte) 0).addByte("upper_block_bit", (byte) 0).build(),
                        KnownBlockRepr.B_196_0_ACACIA_DOOR
                )
        });
        mMapTypeToBlocks.put("dark_oak_door", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("door_hinge_bit", (byte) 0).addByte("open_bit", (byte) 0).addByte("upper_block_bit", (byte) 0).build(),
                        KnownBlockRepr.B_197_0_DARK_OAK_DOOR
                )
        });
        mMapTypeToBlocks.put("grass_path", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_198_0_GRASS_PATH
                )
        });
        mMapTypeToBlocks.put("frame", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).addByte("item_frame_map_bit", (byte) 0).build(),
                        KnownBlockRepr.B_199_0_FRAME
                )
        });
        mMapTypeToBlocks.put("chorus_flower", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("age", 0).build(),
                        KnownBlockRepr.B_200_0_CHORUS_FLOWER
                )
        });
        mMapTypeToBlocks.put("purpur_block", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("chisel_type", "default").addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_201_0_PURPUR_BLOCK_DEFAULT
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("chisel_type", "chiseled").addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_201_1_PURPUR_BLOCK_CHISELED
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("chisel_type", "lines").addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_201_2_PURPUR_BLOCK_LINES
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("chisel_type", "smooth").addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_201_3_PURPUR_BLOCK_DEFAULT
                )
        });
        mMapTypeToBlocks.put("purpur_stairs", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_203_0_PURPUR_STAIRS
                )
        });
        mMapTypeToBlocks.put("shulker_box", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "white").build(),
                        KnownBlockRepr.B_205_0_SHULKERBOX
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "white").build(),
                        KnownBlockRepr.B_218_0_SHULKERBOX_WHITE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "orange").build(),
                        KnownBlockRepr.B_218_1_SHULKERBOX_ORANGE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "magenta").build(),
                        KnownBlockRepr.B_218_2_SHULKERBOX_MAGENTA
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "light_blue").build(),
                        KnownBlockRepr.B_218_3_SHULKERBOX_LIGHT_BLUE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "yellow").build(),
                        KnownBlockRepr.B_218_4_SHULKERBOX_YELLOW
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "lime").build(),
                        KnownBlockRepr.B_218_5_SHULKERBOX_LIME
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "pink").build(),
                        KnownBlockRepr.B_218_6_SHULKERBOX_PINK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "gray").build(),
                        KnownBlockRepr.B_218_7_SHULKERBOX_GRAY
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "silver").build(),
                        KnownBlockRepr.B_218_8_SHULKERBOX_SILVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "cyan").build(),
                        KnownBlockRepr.B_218_9_SHULKERBOX_CYAN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "purple").build(),
                        KnownBlockRepr.B_218_10_SHULKERBOX_PURPLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "blue").build(),
                        KnownBlockRepr.B_218_11_SHULKERBOX_BLUE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "brown").build(),
                        KnownBlockRepr.B_218_12_SHULKERBOX_BROWN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "green").build(),
                        KnownBlockRepr.B_218_13_SHULKERBOX_GREEN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "red").build(),
                        KnownBlockRepr.B_218_14_SHULKERBOX_RED
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "black").build(),
                        KnownBlockRepr.B_218_15_SHULKERBOX_BLACK
                )
        });
        mMapTypeToBlocks.put("end_bricks", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_206_0_END_BRICKS
                )
        });
        mMapTypeToBlocks.put("end_rod", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).build(),
                        KnownBlockRepr.B_208_0_END_ROD
                )
        });
        mMapTypeToBlocks.put("end_gateway", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_209_0_END_GATEWAY
                )
        });
        mMapTypeToBlocks.put("210", new Pair[]{

        });
        mMapTypeToBlocks.put("211", new Pair[]{

        });
        mMapTypeToBlocks.put("212", new Pair[]{

        });
        mMapTypeToBlocks.put("magma", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_213_0_MAGMA_BLOCK
                )
        });
        mMapTypeToBlocks.put("nether_wart_block", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_214_0_NETHERWART_BLOCK
                )
        });
        mMapTypeToBlocks.put("red_nether_brick", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_215_0_RED_NETHER_BRICK
                )
        });
        mMapTypeToBlocks.put("bone_block", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("deprecated", 0).addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_216_0_BONE
                )
        });
        mMapTypeToBlocks.put("purple_glazed_terracotta", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).build(),
                        KnownBlockRepr.B_219_2_GLAZED_TERRACOTTA_PURPLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).build(),
                        KnownBlockRepr.B_219_3_GLAZED_TERRACOTTA_PURPLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).build(),
                        KnownBlockRepr.B_219_4_GLAZED_TERRACOTTA_PURPLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).build(),
                        KnownBlockRepr.B_219_5_GLAZED_TERRACOTTA_PURPLE
                )
        });
        mMapTypeToBlocks.put("white_glazed_terracotta", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).build(),
                        KnownBlockRepr.B_220_2_GLAZED_TERRACOTTA_WHITE
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).build(),
                        KnownBlockRepr.B_220_3_GLAZED_TERRACOTTA_WHITE
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).build(),
                        KnownBlockRepr.B_220_4_GLAZED_TERRACOTTA_WHITE
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).build(),
                        KnownBlockRepr.B_220_5_GLAZED_TERRACOTTA_WHITE
                )
        });
        mMapTypeToBlocks.put("orange_glazed_terracotta", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).build(),
                        KnownBlockRepr.B_221_2_GLAZED_TERRACOTTA_ORANGE
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).build(),
                        KnownBlockRepr.B_221_3_GLAZED_TERRACOTTA_ORANGE
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).build(),
                        KnownBlockRepr.B_221_4_GLAZED_TERRACOTTA_ORANGE
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).build(),
                        KnownBlockRepr.B_221_5_GLAZED_TERRACOTTA_ORANGE
                )
        });
        mMapTypeToBlocks.put("magenta_glazed_terracotta", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).build(),
                        KnownBlockRepr.B_222_2_GLAZED_TERRACOTTA_LIGHT_MAGENTA
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).build(),
                        KnownBlockRepr.B_222_3_GLAZED_TERRACOTTA_LIGHT_MAGENTA
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).build(),
                        KnownBlockRepr.B_222_4_GLAZED_TERRACOTTA_LIGHT_MAGENTA
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).build(),
                        KnownBlockRepr.B_222_5_GLAZED_TERRACOTTA_LIGHT_MAGENTA
                )
        });
        mMapTypeToBlocks.put("light_blue_glazed_terracotta", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).build(),
                        KnownBlockRepr.B_223_2_GLAZED_TERRACOTTA_LIGHT_BLUE
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).build(),
                        KnownBlockRepr.B_223_3_GLAZED_TERRACOTTA_LIGHT_BLUE
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).build(),
                        KnownBlockRepr.B_223_4_GLAZED_TERRACOTTA_LIGHT_BLUE
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).build(),
                        KnownBlockRepr.B_223_5_GLAZED_TERRACOTTA_LIGHT_BLUE
                )
        });
        mMapTypeToBlocks.put("yellow_glazed_terracotta", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).build(),
                        KnownBlockRepr.B_224_2_GLAZED_TERRACOTTA_YELLOW
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).build(),
                        KnownBlockRepr.B_224_3_GLAZED_TERRACOTTA_YELLOW
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).build(),
                        KnownBlockRepr.B_224_4_GLAZED_TERRACOTTA_YELLOW
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).build(),
                        KnownBlockRepr.B_224_5_GLAZED_TERRACOTTA_YELLOW
                )
        });
        mMapTypeToBlocks.put("lime_glazed_terracotta", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).build(),
                        KnownBlockRepr.B_225_2_GLAZED_TERRACOTTA_LIME
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).build(),
                        KnownBlockRepr.B_225_3_GLAZED_TERRACOTTA_LIME
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).build(),
                        KnownBlockRepr.B_225_4_GLAZED_TERRACOTTA_LIME
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).build(),
                        KnownBlockRepr.B_225_5_GLAZED_TERRACOTTA_LIME
                )
        });
        mMapTypeToBlocks.put("pink_glazed_terracotta", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).build(),
                        KnownBlockRepr.B_226_2_GLAZED_TERRACOTTA_PINK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).build(),
                        KnownBlockRepr.B_226_3_GLAZED_TERRACOTTA_PINK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).build(),
                        KnownBlockRepr.B_226_4_GLAZED_TERRACOTTA_PINK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).build(),
                        KnownBlockRepr.B_226_5_GLAZED_TERRACOTTA_PINK
                )
        });
        mMapTypeToBlocks.put("gray_glazed_terracotta", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).build(),
                        KnownBlockRepr.B_227_2_GLAZED_TERRACOTTA_GRAY
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).build(),
                        KnownBlockRepr.B_227_3_GLAZED_TERRACOTTA_GRAY
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).build(),
                        KnownBlockRepr.B_227_4_GLAZED_TERRACOTTA_GRAY
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).build(),
                        KnownBlockRepr.B_227_5_GLAZED_TERRACOTTA_GRAY
                )
        });
        mMapTypeToBlocks.put("silver_glazed_terracotta", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).build(),
                        KnownBlockRepr.B_228_2_GLAZED_TERRACOTTA_LIGHT_GRAY
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).build(),
                        KnownBlockRepr.B_228_3_GLAZED_TERRACOTTA_LIGHT_GRAY
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).build(),
                        KnownBlockRepr.B_228_4_GLAZED_TERRACOTTA_LIGHT_GRAY
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).build(),
                        KnownBlockRepr.B_228_5_GLAZED_TERRACOTTA_LIGHT_GRAY
                )
        });
        mMapTypeToBlocks.put("cyan_glazed_terracotta", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).build(),
                        KnownBlockRepr.B_229_2_GLAZED_TERRACOTTA_CYAN
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).build(),
                        KnownBlockRepr.B_229_3_GLAZED_TERRACOTTA_CYAN
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).build(),
                        KnownBlockRepr.B_229_4_GLAZED_TERRACOTTA_CYAN
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).build(),
                        KnownBlockRepr.B_229_5_GLAZED_TERRACOTTA_CYAN
                )
        });
        mMapTypeToBlocks.put("230", new Pair[]{

        });
        mMapTypeToBlocks.put("blue_glazed_terracotta", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).build(),
                        KnownBlockRepr.B_231_2_GLAZED_TERRACOTTA_BLUE
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).build(),
                        KnownBlockRepr.B_231_3_GLAZED_TERRACOTTA_BLUE
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).build(),
                        KnownBlockRepr.B_231_4_GLAZED_TERRACOTTA_BLUE
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).build(),
                        KnownBlockRepr.B_231_5_GLAZED_TERRACOTTA_BLUE
                )
        });
        mMapTypeToBlocks.put("brown_glazed_terracotta", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).build(),
                        KnownBlockRepr.B_232_2_GLAZED_TERRACOTTA_BROWN
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).build(),
                        KnownBlockRepr.B_232_3_GLAZED_TERRACOTTA_BROWN
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).build(),
                        KnownBlockRepr.B_232_4_GLAZED_TERRACOTTA_BROWN
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).build(),
                        KnownBlockRepr.B_232_5_GLAZED_TERRACOTTA_BROWN
                )
        });
        mMapTypeToBlocks.put("green_glazed_terracotta", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).build(),
                        KnownBlockRepr.B_233_2_GLAZED_TERRACOTTA_GREEN
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).build(),
                        KnownBlockRepr.B_233_3_GLAZED_TERRACOTTA_GREEN
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).build(),
                        KnownBlockRepr.B_233_4_GLAZED_TERRACOTTA_GREEN
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).build(),
                        KnownBlockRepr.B_233_5_GLAZED_TERRACOTTA_GREEN
                )
        });
        mMapTypeToBlocks.put("red_glazed_terracotta", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).build(),
                        KnownBlockRepr.B_234_2_GLAZED_TERRACOTTA_RED
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).build(),
                        KnownBlockRepr.B_234_3_GLAZED_TERRACOTTA_RED
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).build(),
                        KnownBlockRepr.B_234_4_GLAZED_TERRACOTTA_RED
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).build(),
                        KnownBlockRepr.B_234_5_GLAZED_TERRACOTTA_RED
                )
        });
        mMapTypeToBlocks.put("black_glazed_terracotta", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).build(),
                        KnownBlockRepr.B_235_2_GLAZED_TERRACOTTA_BLACK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).build(),
                        KnownBlockRepr.B_235_3_GLAZED_TERRACOTTA_BLACK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).build(),
                        KnownBlockRepr.B_235_4_GLAZED_TERRACOTTA_BLACK
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).build(),
                        KnownBlockRepr.B_235_5_GLAZED_TERRACOTTA_BLACK
                )
        });
        mMapTypeToBlocks.put("concrete", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "white").build(),
                        KnownBlockRepr.B_236_0_CONCRETE_WHITE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "orange").build(),
                        KnownBlockRepr.B_236_1_CONCRETE_ORANGE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "magenta").build(),
                        KnownBlockRepr.B_236_2_CONCRETE_MAGENTA
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "light_blue").build(),
                        KnownBlockRepr.B_236_3_CONCRETE_LIGHT_BLUE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "yellow").build(),
                        KnownBlockRepr.B_236_4_CONCRETE_YELLOW
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "lime").build(),
                        KnownBlockRepr.B_236_5_CONCRETE_LIME
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "pink").build(),
                        KnownBlockRepr.B_236_6_CONCRETE_PINK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "gray").build(),
                        KnownBlockRepr.B_236_7_CONCRETE_GRAY
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "silver").build(),
                        KnownBlockRepr.B_236_8_CONCRETE_SILVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "cyan").build(),
                        KnownBlockRepr.B_236_9_CONCRETE_CYAN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "purple").build(),
                        KnownBlockRepr.B_236_10_CONCRETE_PURPLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "blue").build(),
                        KnownBlockRepr.B_236_11_CONCRETE_BLUE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "brown").build(),
                        KnownBlockRepr.B_236_12_CONCRETE_BROWN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "green").build(),
                        KnownBlockRepr.B_236_13_CONCRETE_GREEN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "red").build(),
                        KnownBlockRepr.B_236_14_CONCRETE_RED
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "black").build(),
                        KnownBlockRepr.B_236_15_CONCRETE_BLACK
                )
        });
        mMapTypeToBlocks.put("concretePowder", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "white").build(),
                        KnownBlockRepr.B_237_0_CONCRETE_POWDER_WHITE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "orange").build(),
                        KnownBlockRepr.B_237_1_CONCRETE_POWDER_ORANGE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "magenta").build(),
                        KnownBlockRepr.B_237_2_CONCRETE_POWDER_MAGENTA
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "light_blue").build(),
                        KnownBlockRepr.B_237_3_CONCRETE_POWDER_LIGHT_BLUE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "yellow").build(),
                        KnownBlockRepr.B_237_4_CONCRETE_POWDER_YELLOW
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "lime").build(),
                        KnownBlockRepr.B_237_5_CONCRETE_POWDER_LIME
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "pink").build(),
                        KnownBlockRepr.B_237_6_CONCRETE_POWDER_PINK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "gray").build(),
                        KnownBlockRepr.B_237_7_CONCRETE_POWDER_GRAY
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "silver").build(),
                        KnownBlockRepr.B_237_8_CONCRETE_POWDER_SILVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "cyan").build(),
                        KnownBlockRepr.B_237_9_CONCRETE_POWDER_CYAN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "purple").build(),
                        KnownBlockRepr.B_237_10_CONCRETE_POWDER_PURPLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "blue").build(),
                        KnownBlockRepr.B_237_11_CONCRETE_POWDER_BLUE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "brown").build(),
                        KnownBlockRepr.B_237_12_CONCRETE_POWDER_BROWN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "green").build(),
                        KnownBlockRepr.B_237_13_CONCRETE_POWDER_GREEN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "red").build(),
                        KnownBlockRepr.B_237_14_CONCRETE_POWDER_RED
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "black").build(),
                        KnownBlockRepr.B_237_15_CONCRETE_POWDER_BLACK
                )
        });
        mMapTypeToBlocks.put("chemistry_table", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("chemistry_table_type", "compound_creator").addInt("direction", 0).build(),
                        KnownBlockRepr.B_238_0_CHEMISTRY_TABLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("chemistry_table_type", "compound_creator").addInt("direction", 1).build(),
                        KnownBlockRepr.B_238_1_CHEMISTRY_TABLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("chemistry_table_type", "compound_creator").addInt("direction", 2).build(),
                        KnownBlockRepr.B_238_2_CHEMISTRY_TABLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("chemistry_table_type", "compound_creator").addInt("direction", 3).build(),
                        KnownBlockRepr.B_238_3_CHEMISTRY_TABLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("chemistry_table_type", "material_reducer").addInt("direction", 0).build(),
                        KnownBlockRepr.B_238_4_CHEMISTRY_TABLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("chemistry_table_type", "material_reducer").addInt("direction", 1).build(),
                        KnownBlockRepr.B_238_5_CHEMISTRY_TABLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("chemistry_table_type", "material_reducer").addInt("direction", 2).build(),
                        KnownBlockRepr.B_238_6_CHEMISTRY_TABLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("chemistry_table_type", "material_reducer").addInt("direction", 3).build(),
                        KnownBlockRepr.B_238_7_CHEMISTRY_TABLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("chemistry_table_type", "element_constructor").addInt("direction", 0).build(),
                        KnownBlockRepr.B_238_8_CHEMISTRY_TABLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("chemistry_table_type", "element_constructor").addInt("direction", 1).build(),
                        KnownBlockRepr.B_238_9_CHEMISTRY_TABLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("chemistry_table_type", "element_constructor").addInt("direction", 2).build(),
                        KnownBlockRepr.B_238_10_CHEMISTRY_TABLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("chemistry_table_type", "element_constructor").addInt("direction", 3).build(),
                        KnownBlockRepr.B_238_11_CHEMISTRY_TABLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("chemistry_table_type", "lab_table").addInt("direction", 0).build(),
                        KnownBlockRepr.B_238_12_CHEMISTRY_TABLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("chemistry_table_type", "lab_table").addInt("direction", 1).build(),
                        KnownBlockRepr.B_238_13_CHEMISTRY_TABLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("chemistry_table_type", "lab_table").addInt("direction", 2).build(),
                        KnownBlockRepr.B_238_14_CHEMISTRY_TABLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("chemistry_table_type", "lab_table").addInt("direction", 3).build(),
                        KnownBlockRepr.B_238_15_CHEMISTRY_TABLE
                )
        });
        mMapTypeToBlocks.put("underwater_torch", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("torch_facing_direction", "unknown").build(),
                        KnownBlockRepr.B_239_0_UNDERWATER_TORCH
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("torch_facing_direction", "west").build(),
                        KnownBlockRepr.B_239_1_UNDERWATER_TORCH
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("torch_facing_direction", "east").build(),
                        KnownBlockRepr.B_239_2_UNDERWATER_TORCH
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("torch_facing_direction", "north").build(),
                        KnownBlockRepr.B_239_3_UNDERWATER_TORCH
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("torch_facing_direction", "south").build(),
                        KnownBlockRepr.B_239_4_UNDERWATER_TORCH
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("torch_facing_direction", "top").build(),
                        KnownBlockRepr.B_239_5_UNDERWATER_TORCH
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("torch_facing_direction", "unknown").build(),
                        KnownBlockRepr.B_239_6_UNDERWATER_TORCH
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("torch_facing_direction", "unknown").build(),
                        KnownBlockRepr.B_239_7_UNDERWATER_TORCH
                )
        });
        mMapTypeToBlocks.put("chorus_plant", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_240_0_CHORUS_PLANT
                )
        });
        mMapTypeToBlocks.put("stained_glass", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "white").build(),
                        KnownBlockRepr.B_241_0_STAINED_GLASS_WHITE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "orange").build(),
                        KnownBlockRepr.B_241_1_STAINED_GLASS_ORANGE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "magenta").build(),
                        KnownBlockRepr.B_241_2_STAINED_GLASS_MAGENTA
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "light_blue").build(),
                        KnownBlockRepr.B_241_3_STAINED_GLASS_LIGHT_BLUE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "yellow").build(),
                        KnownBlockRepr.B_241_4_STAINED_GLASS_YELLOW
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "lime").build(),
                        KnownBlockRepr.B_241_5_STAINED_GLASS_LIME
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "pink").build(),
                        KnownBlockRepr.B_241_6_STAINED_GLASS_PINK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "gray").build(),
                        KnownBlockRepr.B_241_7_STAINED_GLASS_GRAY
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "silver").build(),
                        KnownBlockRepr.B_241_8_STAINED_GLASS_SILVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "cyan").build(),
                        KnownBlockRepr.B_241_9_STAINED_GLASS_CYAN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "purple").build(),
                        KnownBlockRepr.B_241_10_STAINED_GLASS_PURPLE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "blue").build(),
                        KnownBlockRepr.B_241_11_STAINED_GLASS_BLUE
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "brown").build(),
                        KnownBlockRepr.B_241_12_STAINED_GLASS_BROWN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "green").build(),
                        KnownBlockRepr.B_241_13_STAINED_GLASS_GREEN
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "red").build(),
                        KnownBlockRepr.B_241_14_STAINED_GLASS_RED
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "black").build(),
                        KnownBlockRepr.B_241_15_STAINED_GLASS_BLACK
                )
        });
        mMapTypeToBlocks.put("242", new Pair[]{

        });
        mMapTypeToBlocks.put("podzol", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_243_0_PODZOL
                )
        });
        mMapTypeToBlocks.put("beetroot", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("growth", 0).build(),
                        KnownBlockRepr.B_244_0_BEETROOT
                )
        });
        mMapTypeToBlocks.put("stonecutter", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_245_0_STONECUTTER
                )
        });
        mMapTypeToBlocks.put("glowingobsidian", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_246_0_GLOWINGOBSIDIAN
                )
        });
        mMapTypeToBlocks.put("netherreactor", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_247_0_NETHERREACTOR_DEFAULT
                ),
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_247_1_NETHERREACTOR_ACTIVE
                ),
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_247_2_NETHERREACTOR_COOLED
                )
        });
        mMapTypeToBlocks.put("info_update", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_248_0_INFO_UPDATE
                )
        });
        mMapTypeToBlocks.put("info_update2", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_249_0_INFO_UPDATE2
                )
        });
        mMapTypeToBlocks.put("movingBlock", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_250_0_MOVINGBLOCK
                )
        });
        mMapTypeToBlocks.put("observer", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("powered_bit", (byte) 0).build(),
                        KnownBlockRepr.B_251_0_OBSERVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 1).addByte("powered_bit", (byte) 0).build(),
                        KnownBlockRepr.B_251_1_OBSERVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).addByte("powered_bit", (byte) 0).build(),
                        KnownBlockRepr.B_251_2_OBSERVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).addByte("powered_bit", (byte) 0).build(),
                        KnownBlockRepr.B_251_3_OBSERVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).addByte("powered_bit", (byte) 0).build(),
                        KnownBlockRepr.B_251_4_OBSERVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).addByte("powered_bit", (byte) 0).build(),
                        KnownBlockRepr.B_251_5_OBSERVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("powered_bit", (byte) 0).build(),
                        KnownBlockRepr.B_251_6_OBSERVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("powered_bit", (byte) 0).build(),
                        KnownBlockRepr.B_251_7_OBSERVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("powered_bit", (byte) 1).build(),
                        KnownBlockRepr.B_251_8_OBSERVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 1).addByte("powered_bit", (byte) 1).build(),
                        KnownBlockRepr.B_251_9_OBSERVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).addByte("powered_bit", (byte) 1).build(),
                        KnownBlockRepr.B_251_10_OBSERVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).addByte("powered_bit", (byte) 1).build(),
                        KnownBlockRepr.B_251_11_OBSERVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).addByte("powered_bit", (byte) 1).build(),
                        KnownBlockRepr.B_251_12_OBSERVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).addByte("powered_bit", (byte) 1).build(),
                        KnownBlockRepr.B_251_13_OBSERVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("powered_bit", (byte) 1).build(),
                        KnownBlockRepr.B_251_14_OBSERVER
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("powered_bit", (byte) 1).build(),
                        KnownBlockRepr.B_251_15_OBSERVER
                )
        });
        mMapTypeToBlocks.put("structure_block", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("structure_block_type", "data").build(),
                        KnownBlockRepr.B_252_0_STRUCTURE_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("structure_block_type", "save").build(),
                        KnownBlockRepr.B_252_1_STRUCTURE_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("structure_block_type", "load").build(),
                        KnownBlockRepr.B_252_2_STRUCTURE_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("structure_block_type", "corner").build(),
                        KnownBlockRepr.B_252_3_STRUCTURE_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("structure_block_type", "invalid").build(),
                        KnownBlockRepr.B_252_4_STRUCTURE_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("structure_block_type", "export").build(),
                        KnownBlockRepr.B_252_5_STRUCTURE_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("structure_block_type", "data").build(),
                        KnownBlockRepr.B_252_6_STRUCTURE_BLOCK
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("structure_block_type", "data").build(),
                        KnownBlockRepr.B_252_7_STRUCTURE_BLOCK
                )
        });
        mMapTypeToBlocks.put("hard_glass", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_253_0_HARD_GLASS
                )
        });
        mMapTypeToBlocks.put("hard_stained_glass", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "white").build(),
                        KnownBlockRepr.B_254_0_HARD_STAINED_GLASS
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "orange").build(),
                        KnownBlockRepr.B_254_1_HARD_STAINED_GLASS
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "magenta").build(),
                        KnownBlockRepr.B_254_2_HARD_STAINED_GLASS
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "light_blue").build(),
                        KnownBlockRepr.B_254_3_HARD_STAINED_GLASS
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "yellow").build(),
                        KnownBlockRepr.B_254_4_HARD_STAINED_GLASS
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "lime").build(),
                        KnownBlockRepr.B_254_5_HARD_STAINED_GLASS
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "pink").build(),
                        KnownBlockRepr.B_254_6_HARD_STAINED_GLASS
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "gray").build(),
                        KnownBlockRepr.B_254_7_HARD_STAINED_GLASS
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "silver").build(),
                        KnownBlockRepr.B_254_8_HARD_STAINED_GLASS
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "cyan").build(),
                        KnownBlockRepr.B_254_9_HARD_STAINED_GLASS
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "purple").build(),
                        KnownBlockRepr.B_254_10_HARD_STAINED_GLASS
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "blue").build(),
                        KnownBlockRepr.B_254_11_HARD_STAINED_GLASS
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "brown").build(),
                        KnownBlockRepr.B_254_12_HARD_STAINED_GLASS
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "green").build(),
                        KnownBlockRepr.B_254_13_HARD_STAINED_GLASS
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "red").build(),
                        KnownBlockRepr.B_254_14_HARD_STAINED_GLASS
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("color", "black").build(),
                        KnownBlockRepr.B_254_15_HARD_STAINED_GLASS
                )
        });
        mMapTypeToBlocks.put("reserved6", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_255_0_RESERVED6
                )
        });
        mMapTypeToBlocks.put("256", new Pair[]{

        });
        mMapTypeToBlocks.put("prismarine_stairs", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_2154_0_prismarine_stairs
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_2155_1_prismarine_stairs
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 2).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_2156_2_prismarine_stairs
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 3).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_2157_3_prismarine_stairs
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_2158_4_prismarine_stairs
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_2159_5_prismarine_stairs
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 2).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_2160_6_prismarine_stairs
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 3).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_2161_7_prismarine_stairs
                )
        });
        mMapTypeToBlocks.put("dark_prismarine_stairs", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_2162_0_dark_prismarine_stairs
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_2163_1_dark_prismarine_stairs
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 2).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_2164_2_dark_prismarine_stairs
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 3).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_2165_3_dark_prismarine_stairs
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_2166_4_dark_prismarine_stairs
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_2167_5_dark_prismarine_stairs
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 2).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_2168_6_dark_prismarine_stairs
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 3).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_2169_7_dark_prismarine_stairs
                )
        });
        mMapTypeToBlocks.put("prismarine_bricks_stairs", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_2170_0_prismarine_bricks_stairs
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_2171_1_prismarine_bricks_stairs
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 2).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_2172_2_prismarine_bricks_stairs
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 3).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_2173_3_prismarine_bricks_stairs
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_2174_4_prismarine_bricks_stairs
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_2175_5_prismarine_bricks_stairs
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 2).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_2176_6_prismarine_bricks_stairs
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("weirdo_direction", 3).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_2177_7_prismarine_bricks_stairs
                )
        });
        mMapTypeToBlocks.put("stripped_spruce_log", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_2178_0_stripped_spruce_log
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "x").build(),
                        KnownBlockRepr.B_2179_1_stripped_spruce_log
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "z").build(),
                        KnownBlockRepr.B_2180_2_stripped_spruce_log
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_2181_3_stripped_spruce_log
                )
        });
        mMapTypeToBlocks.put("stripped_birch_log", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_2182_0_stripped_birch_log
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "x").build(),
                        KnownBlockRepr.B_2183_1_stripped_birch_log
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "z").build(),
                        KnownBlockRepr.B_2184_2_stripped_birch_log
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_2185_3_stripped_birch_log
                )
        });
        mMapTypeToBlocks.put("stripped_jungle_log", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_2186_0_stripped_jungle_log
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "x").build(),
                        KnownBlockRepr.B_2187_1_stripped_jungle_log
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "z").build(),
                        KnownBlockRepr.B_2188_2_stripped_jungle_log
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_2189_3_stripped_jungle_log
                )
        });
        mMapTypeToBlocks.put("stripped_acacia_log", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_2190_0_stripped_acacia_log
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "x").build(),
                        KnownBlockRepr.B_2191_1_stripped_acacia_log
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "z").build(),
                        KnownBlockRepr.B_2192_2_stripped_acacia_log
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_2193_3_stripped_acacia_log
                )
        });
        mMapTypeToBlocks.put("stripped_dark_oak_log", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_2194_0_stripped_dark_oak_log
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "x").build(),
                        KnownBlockRepr.B_2195_1_stripped_dark_oak_log
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "z").build(),
                        KnownBlockRepr.B_2196_2_stripped_dark_oak_log
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_2197_3_stripped_dark_oak_log
                )
        });
        mMapTypeToBlocks.put("stripped_oak_log", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_2198_0_stripped_oak_log
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "x").build(),
                        KnownBlockRepr.B_2199_1_stripped_oak_log
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "z").build(),
                        KnownBlockRepr.B_2200_2_stripped_oak_log
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("pillar_axis", "y").build(),
                        KnownBlockRepr.B_2201_3_stripped_oak_log
                )
        });
        mMapTypeToBlocks.put("blue_ice", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_2202_0_blue_ice
                )
        });
        mMapTypeToBlocks.put("seagrass", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("sea_grass_type", "default").build(),
                        KnownBlockRepr.B_4091_0_seagrass
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("sea_grass_type", "double_top").build(),
                        KnownBlockRepr.B_4092_1_seagrass
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("sea_grass_type", "double_bot").build(),
                        KnownBlockRepr.B_4093_2_seagrass
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("sea_grass_type", "default").build(),
                        KnownBlockRepr.B_4094_3_seagrass
                )
        });
        mMapTypeToBlocks.put("coral", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4095_0_coral
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "pink").addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4096_1_coral
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "purple").addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4097_2_coral
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "red").addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4098_3_coral
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "yellow").addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4099_4_coral
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4100_5_coral
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4101_6_coral
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4102_7_coral
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4103_8_coral
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "pink").addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4104_9_coral
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "purple").addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4105_10_coral
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "red").addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4106_11_coral
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "yellow").addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4107_12_coral
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4108_13_coral
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4109_14_coral
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4110_15_coral
                )
        });
        mMapTypeToBlocks.put("coral_block", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4111_0_coral_block
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "pink").addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4112_1_coral_block
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "purple").addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4113_2_coral_block
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "red").addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4114_3_coral_block
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "yellow").addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4115_4_coral_block
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4116_5_coral_block
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4117_6_coral_block
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4118_7_coral_block
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4119_8_coral_block
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "pink").addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4120_9_coral_block
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "purple").addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4121_10_coral_block
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "red").addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4122_11_coral_block
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "yellow").addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4123_12_coral_block
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4124_13_coral_block
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4125_14_coral_block
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4126_15_coral_block
                )
        });
        mMapTypeToBlocks.put("coral_fan", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "pink").addInt("coral_fan_direction", 0).build(),
                        KnownBlockRepr.B_4128_1_coral_fan
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "yellow").addInt("coral_fan_direction", 0).build(),
                        KnownBlockRepr.B_4131_4_coral_fan
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addInt("coral_fan_direction", 0).build(),
                        KnownBlockRepr.B_4132_5_coral_fan
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addInt("coral_fan_direction", 0).build(),
                        KnownBlockRepr.B_4133_6_coral_fan
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addInt("coral_fan_direction", 1).build(),
                        KnownBlockRepr.B_4135_8_coral_fan
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "pink").addInt("coral_fan_direction", 1).build(),
                        KnownBlockRepr.B_4136_9_coral_fan
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "purple").addInt("coral_fan_direction", 1).build(),
                        KnownBlockRepr.B_4137_10_coral_fan
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "red").addInt("coral_fan_direction", 1).build(),
                        KnownBlockRepr.B_4138_11_coral_fan
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "yellow").addInt("coral_fan_direction", 1).build(),
                        KnownBlockRepr.B_4139_12_coral_fan
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addInt("coral_fan_direction", 1).build(),
                        KnownBlockRepr.B_4140_13_coral_fan
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addInt("coral_fan_direction", 1).build(),
                        KnownBlockRepr.B_4141_14_coral_fan
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addInt("coral_fan_direction", 1).build(),
                        KnownBlockRepr.B_4142_15_coral_fan
                )
        });
        mMapTypeToBlocks.put("coral_fan_dead", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addInt("coral_fan_direction", 0).build(),
                        KnownBlockRepr.B_4143_0_coral_fan_dead
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "pink").addInt("coral_fan_direction", 0).build(),
                        KnownBlockRepr.B_4144_1_coral_fan_dead
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "purple").addInt("coral_fan_direction", 0).build(),
                        KnownBlockRepr.B_4145_2_coral_fan_dead
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "red").addInt("coral_fan_direction", 0).build(),
                        KnownBlockRepr.B_4146_3_coral_fan_dead
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "yellow").addInt("coral_fan_direction", 0).build(),
                        KnownBlockRepr.B_4147_4_coral_fan_dead
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addInt("coral_fan_direction", 0).build(),
                        KnownBlockRepr.B_4148_5_coral_fan_dead
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addInt("coral_fan_direction", 0).build(),
                        KnownBlockRepr.B_4149_6_coral_fan_dead
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addInt("coral_fan_direction", 0).build(),
                        KnownBlockRepr.B_4150_7_coral_fan_dead
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addInt("coral_fan_direction", 0).build(),
                        KnownBlockRepr.B_4151_8_coral_fan_dead
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "pink").addInt("coral_fan_direction", 1).build(),
                        KnownBlockRepr.B_4152_9_coral_fan_dead
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "purple").addInt("coral_fan_direction", 1).build(),
                        KnownBlockRepr.B_4153_10_coral_fan_dead
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "red").addInt("coral_fan_direction", 1).build(),
                        KnownBlockRepr.B_4154_11_coral_fan_dead
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "yellow").addInt("coral_fan_direction", 1).build(),
                        KnownBlockRepr.B_4155_12_coral_fan_dead
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addInt("coral_fan_direction", 1).build(),
                        KnownBlockRepr.B_4156_13_coral_fan_dead
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addInt("coral_fan_direction", 1).build(),
                        KnownBlockRepr.B_4157_14_coral_fan_dead
                ),
                new Pair<>(
                        new BlockStateBuilder().addProperty("coral_color", "blue").addInt("coral_fan_direction", 0).build(),
                        KnownBlockRepr.B_4158_15_coral_fan_dead
                )
        });
        mMapTypeToBlocks.put("coral_fan_hang", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 0).addInt("coral_direction", 0).addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4159_0_coral_fan_hang
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 1).addInt("coral_direction", 0).addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4160_1_coral_fan_hang
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 0).addInt("coral_direction", 0).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4161_2_coral_fan_hang
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 1).addInt("coral_direction", 0).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4162_3_coral_fan_hang
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 0).addInt("coral_direction", 1).addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4163_4_coral_fan_hang
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 1).addInt("coral_direction", 1).addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4164_5_coral_fan_hang
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 0).addInt("coral_direction", 1).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4165_6_coral_fan_hang
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 0).addInt("coral_direction", 2).addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4167_8_coral_fan_hang
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 1).addInt("coral_direction", 2).addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4168_9_coral_fan_hang
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 1).addInt("coral_direction", 2).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4170_11_coral_fan_hang
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 0).addInt("coral_direction", 3).addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4171_12_coral_fan_hang
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 1).addInt("coral_direction", 3).addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4172_13_coral_fan_hang
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 0).addInt("coral_direction", 3).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4173_14_coral_fan_hang
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 1).addInt("coral_direction", 3).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4174_15_coral_fan_hang
                )
        });
        mMapTypeToBlocks.put("coral_fan_hang2", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 0).addInt("coral_direction", 0).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4177_2_coral_fan_hang2
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 1).addInt("coral_direction", 1).addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4180_5_coral_fan_hang2
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 0).addInt("coral_direction", 1).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4181_6_coral_fan_hang2
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 1).addInt("coral_direction", 1).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4182_7_coral_fan_hang2
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 0).addInt("coral_direction", 2).addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4183_8_coral_fan_hang2
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 1).addInt("coral_direction", 2).addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4184_9_coral_fan_hang2
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 0).addInt("coral_direction", 2).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4185_10_coral_fan_hang2
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 1).addInt("coral_direction", 2).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4186_11_coral_fan_hang2
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 0).addInt("coral_direction", 3).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4189_14_coral_fan_hang2
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 1).addInt("coral_direction", 3).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4190_15_coral_fan_hang2
                )
        });
        mMapTypeToBlocks.put("coral_fan_hang3", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 0).addInt("coral_direction", 0).addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4191_0_coral_fan_hang3
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 0).addInt("coral_direction", 0).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4193_2_coral_fan_hang3
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 1).addInt("coral_direction", 0).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4194_3_coral_fan_hang3
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 0).addInt("coral_direction", 1).addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4195_4_coral_fan_hang3
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 1).addInt("coral_direction", 1).addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4196_5_coral_fan_hang3
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 0).addInt("coral_direction", 1).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4197_6_coral_fan_hang3
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 1).addInt("coral_direction", 1).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4198_7_coral_fan_hang3
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 0).addInt("coral_direction", 2).addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4199_8_coral_fan_hang3
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 1).addInt("coral_direction", 2).addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4200_9_coral_fan_hang3
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 0).addInt("coral_direction", 2).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4201_10_coral_fan_hang3
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 1).addInt("coral_direction", 2).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4202_11_coral_fan_hang3
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 0).addInt("coral_direction", 3).addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4203_12_coral_fan_hang3
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 1).addInt("coral_direction", 3).addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4204_13_coral_fan_hang3
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 0).addInt("coral_direction", 3).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4205_14_coral_fan_hang3
                ),
                new Pair<>(
                        new BlockStateBuilder().addByte("coral_hang_type_bit", (byte) 1).addInt("coral_direction", 3).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4206_15_coral_fan_hang3
                )
        });
        mMapTypeToBlocks.put("kelp", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("kelp_age", 0).build(),
                        KnownBlockRepr.B_4207_0_kelp
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("kelp_age", 1).build(),
                        KnownBlockRepr.B_4208_1_kelp
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("kelp_age", 2).build(),
                        KnownBlockRepr.B_4209_2_kelp
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("kelp_age", 3).build(),
                        KnownBlockRepr.B_4210_3_kelp
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("kelp_age", 4).build(),
                        KnownBlockRepr.B_4211_4_kelp
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("kelp_age", 5).build(),
                        KnownBlockRepr.B_4212_5_kelp
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("kelp_age", 6).build(),
                        KnownBlockRepr.B_4213_6_kelp
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("kelp_age", 7).build(),
                        KnownBlockRepr.B_4214_7_kelp
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("kelp_age", 8).build(),
                        KnownBlockRepr.B_4215_8_kelp
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("kelp_age", 9).build(),
                        KnownBlockRepr.B_4216_9_kelp
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("kelp_age", 10).build(),
                        KnownBlockRepr.B_4217_10_kelp
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("kelp_age", 11).build(),
                        KnownBlockRepr.B_4218_11_kelp
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("kelp_age", 12).build(),
                        KnownBlockRepr.B_4219_12_kelp
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("kelp_age", 13).build(),
                        KnownBlockRepr.B_4220_13_kelp
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("kelp_age", 14).build(),
                        KnownBlockRepr.B_4221_14_kelp
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("kelp_age", 15).build(),
                        KnownBlockRepr.B_4222_15_kelp
                )
        });
        mMapTypeToBlocks.put("dried_kelp_block", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().build(),
                        KnownBlockRepr.B_4223_0_dried_kelp_block
                )
        });
        mMapTypeToBlocks.put("acacia_button", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4224_0_acacia_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 1).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4225_1_acacia_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4226_2_acacia_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4227_3_acacia_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4228_4_acacia_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4229_5_acacia_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4230_6_acacia_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4231_7_acacia_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4232_8_acacia_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 1).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4233_9_acacia_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4234_10_acacia_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4235_11_acacia_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4236_12_acacia_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4237_13_acacia_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4238_14_acacia_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4239_15_acacia_button
                )
        });
        mMapTypeToBlocks.put("birch_button", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4240_0_birch_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 1).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4241_1_birch_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4242_2_birch_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4243_3_birch_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4244_4_birch_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4245_5_birch_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4246_6_birch_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4247_7_birch_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4248_8_birch_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 1).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4249_9_birch_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4250_10_birch_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4251_11_birch_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4252_12_birch_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4253_13_birch_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4254_14_birch_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4255_15_birch_button
                )
        });
        mMapTypeToBlocks.put("dark_oak_button", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4256_0_dark_oak_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 1).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4257_1_dark_oak_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4258_2_dark_oak_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4259_3_dark_oak_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4260_4_dark_oak_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4261_5_dark_oak_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4262_6_dark_oak_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4263_7_dark_oak_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4264_8_dark_oak_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 1).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4265_9_dark_oak_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4266_10_dark_oak_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4267_11_dark_oak_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4268_12_dark_oak_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4269_13_dark_oak_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4270_14_dark_oak_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4271_15_dark_oak_button
                )
        });
        mMapTypeToBlocks.put("jungle_button", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4272_0_jungle_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 1).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4273_1_jungle_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4274_2_jungle_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4275_3_jungle_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4276_4_jungle_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4277_5_jungle_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4278_6_jungle_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4279_7_jungle_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4280_8_jungle_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 1).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4281_9_jungle_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4282_10_jungle_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4283_11_jungle_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4284_12_jungle_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4285_13_jungle_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4286_14_jungle_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4287_15_jungle_button
                )
        });
        mMapTypeToBlocks.put("spruce_button", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4288_0_spruce_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 1).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4289_1_spruce_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4290_2_spruce_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4291_3_spruce_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4292_4_spruce_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4293_5_spruce_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4294_6_spruce_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4295_7_spruce_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4296_8_spruce_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 1).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4297_9_spruce_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 2).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4298_10_spruce_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 3).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4299_11_spruce_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 4).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4300_12_spruce_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 5).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4301_13_spruce_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4302_14_spruce_button
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("facing_direction", 0).addByte("button_pressed_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4303_15_spruce_button
                )
        });
        mMapTypeToBlocks.put("acacia_trapdoor", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4304_0_acacia_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 1).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4305_1_acacia_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 2).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4306_2_acacia_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 3).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4307_3_acacia_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4308_4_acacia_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 1).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4309_5_acacia_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 2).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4310_6_acacia_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 3).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4311_7_acacia_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4312_8_acacia_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 1).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4313_9_acacia_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 2).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4314_10_acacia_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 3).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4315_11_acacia_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4316_12_acacia_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 1).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4317_13_acacia_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 2).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4318_14_acacia_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 3).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4319_15_acacia_trapdoor
                )
        });
        mMapTypeToBlocks.put("birch_trapdoor", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4320_0_birch_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 1).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4321_1_birch_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 2).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4322_2_birch_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 3).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4323_3_birch_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4324_4_birch_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 1).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4325_5_birch_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 2).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4326_6_birch_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 3).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4327_7_birch_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4328_8_birch_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 1).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4329_9_birch_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 2).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4330_10_birch_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 3).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4331_11_birch_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4332_12_birch_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 1).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4333_13_birch_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 2).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4334_14_birch_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 3).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4335_15_birch_trapdoor
                )
        });
        mMapTypeToBlocks.put("dark_oak_trapdoor", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4336_0_dark_oak_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 1).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4337_1_dark_oak_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 2).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4338_2_dark_oak_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 3).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4339_3_dark_oak_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4340_4_dark_oak_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 1).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4341_5_dark_oak_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 2).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4342_6_dark_oak_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 3).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4343_7_dark_oak_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4344_8_dark_oak_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 1).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4345_9_dark_oak_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 2).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4346_10_dark_oak_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 3).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4347_11_dark_oak_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4348_12_dark_oak_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 1).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4349_13_dark_oak_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 2).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4350_14_dark_oak_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 3).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4351_15_dark_oak_trapdoor
                )
        });
        mMapTypeToBlocks.put("jungle_trapdoor", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4352_0_jungle_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 1).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4353_1_jungle_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 2).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4354_2_jungle_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 3).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4355_3_jungle_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4356_4_jungle_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 1).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4357_5_jungle_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 2).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4358_6_jungle_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 3).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4359_7_jungle_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4360_8_jungle_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 1).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4361_9_jungle_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 2).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4362_10_jungle_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 3).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4363_11_jungle_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4364_12_jungle_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 1).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4365_13_jungle_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 2).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4366_14_jungle_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 3).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4367_15_jungle_trapdoor
                )
        });
        mMapTypeToBlocks.put("spruce_trapdoor", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4368_0_spruce_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 1).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4369_1_spruce_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 2).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4370_2_spruce_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 3).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4371_3_spruce_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4372_4_spruce_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 1).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4373_5_spruce_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 2).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4374_6_spruce_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 3).addByte("open_bit", (byte) 0).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4375_7_spruce_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4376_8_spruce_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 1).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4377_9_spruce_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 2).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4378_10_spruce_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 3).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4379_11_spruce_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4380_12_spruce_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 1).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4381_13_spruce_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 2).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4382_14_spruce_trapdoor
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 3).addByte("open_bit", (byte) 1).addByte("upside_down_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4383_15_spruce_trapdoor
                )
        });
        mMapTypeToBlocks.put("acacia_pressure_plate", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 0).build(),
                        KnownBlockRepr.B_4384_0_acacia_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 1).build(),
                        KnownBlockRepr.B_4385_1_acacia_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 2).build(),
                        KnownBlockRepr.B_4386_2_acacia_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 3).build(),
                        KnownBlockRepr.B_4387_3_acacia_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 4).build(),
                        KnownBlockRepr.B_4388_4_acacia_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 5).build(),
                        KnownBlockRepr.B_4389_5_acacia_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 6).build(),
                        KnownBlockRepr.B_4390_6_acacia_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 7).build(),
                        KnownBlockRepr.B_4391_7_acacia_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 8).build(),
                        KnownBlockRepr.B_4392_8_acacia_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 9).build(),
                        KnownBlockRepr.B_4393_9_acacia_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 10).build(),
                        KnownBlockRepr.B_4394_10_acacia_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 11).build(),
                        KnownBlockRepr.B_4395_11_acacia_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 12).build(),
                        KnownBlockRepr.B_4396_12_acacia_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 13).build(),
                        KnownBlockRepr.B_4397_13_acacia_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 14).build(),
                        KnownBlockRepr.B_4398_14_acacia_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 15).build(),
                        KnownBlockRepr.B_4399_15_acacia_pressure_plate
                )
        });
        mMapTypeToBlocks.put("birch_pressure_plate", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 0).build(),
                        KnownBlockRepr.B_4400_0_birch_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 1).build(),
                        KnownBlockRepr.B_4401_1_birch_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 2).build(),
                        KnownBlockRepr.B_4402_2_birch_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 3).build(),
                        KnownBlockRepr.B_4403_3_birch_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 4).build(),
                        KnownBlockRepr.B_4404_4_birch_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 5).build(),
                        KnownBlockRepr.B_4405_5_birch_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 6).build(),
                        KnownBlockRepr.B_4406_6_birch_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 7).build(),
                        KnownBlockRepr.B_4407_7_birch_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 8).build(),
                        KnownBlockRepr.B_4408_8_birch_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 9).build(),
                        KnownBlockRepr.B_4409_9_birch_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 10).build(),
                        KnownBlockRepr.B_4410_10_birch_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 11).build(),
                        KnownBlockRepr.B_4411_11_birch_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 12).build(),
                        KnownBlockRepr.B_4412_12_birch_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 13).build(),
                        KnownBlockRepr.B_4413_13_birch_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 14).build(),
                        KnownBlockRepr.B_4414_14_birch_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 15).build(),
                        KnownBlockRepr.B_4415_15_birch_pressure_plate
                )
        });
        mMapTypeToBlocks.put("dark_oak_pressure_plate", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 0).build(),
                        KnownBlockRepr.B_4416_0_dark_oak_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 1).build(),
                        KnownBlockRepr.B_4417_1_dark_oak_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 2).build(),
                        KnownBlockRepr.B_4418_2_dark_oak_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 3).build(),
                        KnownBlockRepr.B_4419_3_dark_oak_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 4).build(),
                        KnownBlockRepr.B_4420_4_dark_oak_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 5).build(),
                        KnownBlockRepr.B_4421_5_dark_oak_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 6).build(),
                        KnownBlockRepr.B_4422_6_dark_oak_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 7).build(),
                        KnownBlockRepr.B_4423_7_dark_oak_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 8).build(),
                        KnownBlockRepr.B_4424_8_dark_oak_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 9).build(),
                        KnownBlockRepr.B_4425_9_dark_oak_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 10).build(),
                        KnownBlockRepr.B_4426_10_dark_oak_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 11).build(),
                        KnownBlockRepr.B_4427_11_dark_oak_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 12).build(),
                        KnownBlockRepr.B_4428_12_dark_oak_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 13).build(),
                        KnownBlockRepr.B_4429_13_dark_oak_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 14).build(),
                        KnownBlockRepr.B_4430_14_dark_oak_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 15).build(),
                        KnownBlockRepr.B_4431_15_dark_oak_pressure_plate
                )
        });
        mMapTypeToBlocks.put("jungle_pressure_plate", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 0).build(),
                        KnownBlockRepr.B_4432_0_jungle_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 1).build(),
                        KnownBlockRepr.B_4433_1_jungle_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 2).build(),
                        KnownBlockRepr.B_4434_2_jungle_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 3).build(),
                        KnownBlockRepr.B_4435_3_jungle_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 4).build(),
                        KnownBlockRepr.B_4436_4_jungle_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 5).build(),
                        KnownBlockRepr.B_4437_5_jungle_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 6).build(),
                        KnownBlockRepr.B_4438_6_jungle_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 7).build(),
                        KnownBlockRepr.B_4439_7_jungle_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 8).build(),
                        KnownBlockRepr.B_4440_8_jungle_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 9).build(),
                        KnownBlockRepr.B_4441_9_jungle_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 10).build(),
                        KnownBlockRepr.B_4442_10_jungle_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 11).build(),
                        KnownBlockRepr.B_4443_11_jungle_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 12).build(),
                        KnownBlockRepr.B_4444_12_jungle_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 13).build(),
                        KnownBlockRepr.B_4445_13_jungle_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 14).build(),
                        KnownBlockRepr.B_4446_14_jungle_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 15).build(),
                        KnownBlockRepr.B_4447_15_jungle_pressure_plate
                )
        });
        mMapTypeToBlocks.put("spruce_pressure_plate", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 0).build(),
                        KnownBlockRepr.B_4448_0_spruce_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 1).build(),
                        KnownBlockRepr.B_4449_1_spruce_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 2).build(),
                        KnownBlockRepr.B_4450_2_spruce_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 3).build(),
                        KnownBlockRepr.B_4451_3_spruce_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 4).build(),
                        KnownBlockRepr.B_4452_4_spruce_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 5).build(),
                        KnownBlockRepr.B_4453_5_spruce_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 6).build(),
                        KnownBlockRepr.B_4454_6_spruce_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 7).build(),
                        KnownBlockRepr.B_4455_7_spruce_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 8).build(),
                        KnownBlockRepr.B_4456_8_spruce_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 9).build(),
                        KnownBlockRepr.B_4457_9_spruce_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 10).build(),
                        KnownBlockRepr.B_4458_10_spruce_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 11).build(),
                        KnownBlockRepr.B_4459_11_spruce_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 12).build(),
                        KnownBlockRepr.B_4460_12_spruce_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 13).build(),
                        KnownBlockRepr.B_4461_13_spruce_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 14).build(),
                        KnownBlockRepr.B_4462_14_spruce_pressure_plate
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("redstone_signal", 15).build(),
                        KnownBlockRepr.B_4463_15_spruce_pressure_plate
                )
        });
        mMapTypeToBlocks.put("carved_pumpkin", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 0).build(),
                        KnownBlockRepr.B_4464_0_carved_pumpkin
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 1).build(),
                        KnownBlockRepr.B_4465_1_carved_pumpkin
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 2).build(),
                        KnownBlockRepr.B_4466_2_carved_pumpkin
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("direction", 3).build(),
                        KnownBlockRepr.B_4467_3_carved_pumpkin
                )
        });
        mMapTypeToBlocks.put("sea_pickle", new Pair[]{
                new Pair<>(
                        new BlockStateBuilder().addInt("cluster_count", 0).addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4468_0_sea_pickle
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("cluster_count", 1).addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4469_1_sea_pickle
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("cluster_count", 2).addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4470_2_sea_pickle
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("cluster_count", 3).addByte("dead_bit", (byte) 0).build(),
                        KnownBlockRepr.B_4471_3_sea_pickle
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("cluster_count", 0).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4472_4_sea_pickle
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("cluster_count", 1).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4473_5_sea_pickle
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("cluster_count", 2).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4474_6_sea_pickle
                ),
                new Pair<>(
                        new BlockStateBuilder().addInt("cluster_count", 3).addByte("dead_bit", (byte) 1).build(),
                        KnownBlockRepr.B_4475_7_sea_pickle
                )
        });

    }

    @Nullable
    public static KnownBlockRepr getBestRepr(@NonNull Block block) {
        Pair<CompoundTag, KnownBlockRepr>[] list = mMapTypeToBlocks.get(block.getBlockType());
        if (list == null || list.length <= 0)
            // Already the best we can d for now.
            return null;
        String[] checks = new String[]{"color", "type", "direction"};
        ArrayList<Tag> tags = block.getStates().getValue();
        List<Pair<CompoundTag, KnownBlockRepr>> range = new ArrayList<>(list.length);
        range.addAll(Arrays.asList(list));
        for (String check : checks) {
            List<Pair<CompoundTag, KnownBlockRepr>> rangeNew = new ArrayList<>(range.size());
            for (Tag tag : tags) {
                if (tag.getName().contains(check)) {
                    for (Pair<CompoundTag, KnownBlockRepr> pair : range) {
                        if (tag.equals(pair.first)) {
                            rangeNew.add(pair);
                            break;
                        }
                    }
                }
            }
            if (rangeNew.size() > 0) range = rangeNew;
        }
        return range.get(0).second;
    }
}
