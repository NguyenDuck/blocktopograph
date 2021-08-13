package com.mithrilmania.blocktopograph.block;

import androidx.annotation.NonNull;

import com.google.common.collect.Streams;
import com.mithrilmania.blocktopograph.block.icon.NoBlockIcon;
import com.mithrilmania.blocktopograph.block.icon.TexPathBlockIcon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class BlockTemplates {

    private static final Map<String, BlockTemplate[]> allTemplates = new HashMap<>();

    private static final BlockTemplate unknownBlockTemplate = new BlockTemplate(
            null, new Block.Builder(BlockType.UNKNOWN).build(),
            new NoBlockIcon(), 0xff0000, false);

    static {
        init();
    }

    private static void init() {
        allTemplates.put("minecraft:air", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.AIR).build(), new TexPathBlockIcon(null), 0x00000000, false)
        });
        allTemplates.put("minecraft:stone", new BlockTemplate[]{
                new BlockTemplate("stone", new Block.Builder(BlockType.STONE).setProperty("stone_type", "stone").build(), new TexPathBlockIcon("blocks/stone.png"), 0xff464646, false),
                new BlockTemplate("granite", new Block.Builder(BlockType.STONE).setProperty("stone_type", "granite").build(), new TexPathBlockIcon("blocks/stone_granite.png"), 0xff8c7167, false),
                new BlockTemplate("granite_smooth", new Block.Builder(BlockType.STONE).setProperty("stone_type", "granite_smooth").build(), new TexPathBlockIcon("blocks/stone_granite_smooth.png"), 0xff946251, false),
                new BlockTemplate("diorite", new Block.Builder(BlockType.STONE).setProperty("stone_type", "diorite").build(), new TexPathBlockIcon("blocks/stone_diorite.png"), 0xffc6c6c6, false),
                new BlockTemplate("diorite_smooth", new Block.Builder(BlockType.STONE).setProperty("stone_type", "diorite_smooth").build(), new TexPathBlockIcon("blocks/stone_diorite_smooth.png"), 0xffbebec1, false),
                new BlockTemplate("andesite", new Block.Builder(BlockType.STONE).setProperty("stone_type", "andesite").build(), new TexPathBlockIcon("blocks/stone_andesite.png"), 0xff797777, false),
                new BlockTemplate("andesite_smooth", new Block.Builder(BlockType.STONE).setProperty("stone_type", "andesite_smooth").build(), new TexPathBlockIcon("blocks/stone_andesite_smooth.png"), 0xff828382, false)
        });
        allTemplates.put("minecraft:grass", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.GRASS).build(), new TexPathBlockIcon("blocks/grass_side_carried.png"), 0xff939393, true)
        });
        allTemplates.put("minecraft:dirt", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.DIRT).setProperty("dirt_type", "normal").build(), new TexPathBlockIcon("blocks/dirt.png"), 0xff866043, true)
        });
        allTemplates.put("minecraft:cobblestone", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.COBBLESTONE).build(), new TexPathBlockIcon("blocks/cobblestone.png"), 0xff7a7a7a, false)
        });
        allTemplates.put("minecraft:planks", new BlockTemplate[]{
                new BlockTemplate("oak", new Block.Builder(BlockType.PLANKS).setProperty("wood_type", "oak").build(), new TexPathBlockIcon("blocks/planks_oak.png"), 0xff9c7f4e, false),
                new BlockTemplate("spruce", new Block.Builder(BlockType.PLANKS).setProperty("wood_type", "spruce").build(), new TexPathBlockIcon("blocks/planks_spruce.png"), 0xff5a3d0d, false),
                new BlockTemplate("birch", new Block.Builder(BlockType.PLANKS).setProperty("wood_type", "birch").build(), new TexPathBlockIcon("blocks/planks_birch.png"), 0xffdabd8d, false),
                new BlockTemplate("jungle", new Block.Builder(BlockType.PLANKS).setProperty("wood_type", "jungle").build(), new TexPathBlockIcon("blocks/planks_jungle.png"), 0xffBa7d5d, false),
                new BlockTemplate("acacia", new Block.Builder(BlockType.PLANKS).setProperty("wood_type", "acacia").build(), new TexPathBlockIcon("blocks/planks_acacia.png"), 0xff934f39, false),
                new BlockTemplate("big_oak", new Block.Builder(BlockType.PLANKS).setProperty("wood_type", "dark_oak").build(), new TexPathBlockIcon("blocks/planks_big_oak.png"), 0xff3b260f, false)
        });
        allTemplates.put("minecraft:sapling", new BlockTemplate[]{
                new BlockTemplate("oak", new Block.Builder(BlockType.SAPLING).setProperty("sapling_type", "oak").setProperty("age_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/sapling_oak.png"), 0x6b476625, false),
                new BlockTemplate("spruce", new Block.Builder(BlockType.SAPLING).setProperty("sapling_type", "spruce").setProperty("age_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/sapling_spruce.png"), 0x53333a21, false),
                new BlockTemplate("birch", new Block.Builder(BlockType.SAPLING).setProperty("sapling_type", "birch").setProperty("age_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/sapling_birch.png"), 0x6b769654, false),
                new BlockTemplate("jungle", new Block.Builder(BlockType.SAPLING).setProperty("sapling_type", "jungle").setProperty("age_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/sapling_jungle.png"), 0x55305612, false),
                new BlockTemplate("acacia", new Block.Builder(BlockType.SAPLING).setProperty("sapling_type", "acacia").setProperty("age_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/sapling_acacia.png"), 0xff718919, false),
                new BlockTemplate("big_oak", new Block.Builder(BlockType.SAPLING).setProperty("sapling_type", "dark_oak").setProperty("age_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/sapling_roofed_oak.png"), 0xff6f522d, false)
        });
        allTemplates.put("minecraft:bedrock", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.BEDROCK).setProperty("infiniburn_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/bedrock.png"), 0xff535353, false)
        });
        allTemplates.put("minecraft:flowing_water", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.FLOWING_WATER).setProperty("liquid_depth", 0).build(), new TexPathBlockIcon("blocks/water_flow.png"), 0x802e43f4, false)
        });
        allTemplates.put("minecraft:water", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.WATER).setProperty("liquid_depth", 0).build(), new TexPathBlockIcon("blocks/water_still.png"), 0x802e43f4, false)
        });
        allTemplates.put("minecraft:flowing_lava", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.FLOWING_LAVA).setProperty("liquid_depth", 0).build(), new TexPathBlockIcon("blocks/lava_flow.png"), 0xf0d45a12, false)
        });
        allTemplates.put("minecraft:lava", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.LAVA).setProperty("liquid_depth", 0).build(), new TexPathBlockIcon("blocks/lava_still.png"), 0xf0d45a12, false)
        });
        allTemplates.put("minecraft:sand", new BlockTemplate[]{
                new BlockTemplate("default", new Block.Builder(BlockType.SAND).setProperty("sand_type", "normal").build(), new TexPathBlockIcon("blocks/sand.png"), 0xffdbd3a0, false),
                new BlockTemplate("red", new Block.Builder(BlockType.SAND).setProperty("sand_type", "red").build(), new TexPathBlockIcon("blocks/red_sand.png"), 0xffa7531f, false)
        });
        allTemplates.put("minecraft:gravel", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.GRAVEL).build(), new TexPathBlockIcon("blocks/gravel.png"), 0xff7e7c7a, false)
        });
        allTemplates.put("minecraft:gold_ore", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.GOLD_ORE).build(), new TexPathBlockIcon("blocks/gold_ore.png"), 0xff8f8b7c, false)
        });
        allTemplates.put("minecraft:iron_ore", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.IRON_ORE).build(), new TexPathBlockIcon("blocks/iron_ore.png"), 0xff87827e, false)
        });
        allTemplates.put("minecraft:coal_ore", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.COAL_ORE).build(), new TexPathBlockIcon("blocks/coal_ore.png"), 0xff737373, false)
        });
        allTemplates.put("minecraft:log", new BlockTemplate[]{
                new BlockTemplate("oak", new Block.Builder(BlockType.LOG).setProperty("old_log_type", "oak").setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/log_oak.png"), 0xff9a7d4d, false),
                new BlockTemplate("spruce", new Block.Builder(BlockType.LOG).setProperty("old_log_type", "spruce").setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/log_spruce.png"), 0xff5a3d0d, false),
                new BlockTemplate("birch", new Block.Builder(BlockType.LOG).setProperty("old_log_type", "birch").setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/log_birch.png"), 0xffdabd8d, false),
                new BlockTemplate("jungle", new Block.Builder(BlockType.LOG).setProperty("old_log_type", "jungle").setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/log_jungle.png"), 0xffBa7d5d, false)
        });
        allTemplates.put("minecraft:leaves", new BlockTemplate[]{
                new BlockTemplate("oak", new Block.Builder(BlockType.LEAVES).setProperty("old_leaf_type", "oak").setProperty("persistent_bit", (byte) 0).setProperty("update_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/leaves_oak.png"), 0xff878787, true),
                new BlockTemplate("spruce", new Block.Builder(BlockType.LEAVES).setProperty("old_leaf_type", "spruce").setProperty("persistent_bit", (byte) 0).setProperty("update_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/leaves_spruce.png"), 0xff132613, true),
                new BlockTemplate("birch", new Block.Builder(BlockType.LEAVES).setProperty("old_leaf_type", "birch").setProperty("persistent_bit", (byte) 0).setProperty("update_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/leaves_birch.png"), 0xff283816, true),
                new BlockTemplate("jungle", new Block.Builder(BlockType.LEAVES).setProperty("old_leaf_type", "jungle").setProperty("persistent_bit", (byte) 0).setProperty("update_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/leaves_jungle.png"), 0xff918e86, true)
        });
        allTemplates.put("minecraft:sponge", new BlockTemplate[]{
                new BlockTemplate("dry", new Block.Builder(BlockType.SPONGE).setProperty("sponge_type", "dry").build(), new TexPathBlockIcon("blocks/sponge_dry.png"), 0xffb6b639, false),
                new BlockTemplate("wet", new Block.Builder(BlockType.SPONGE).setProperty("sponge_type", "wet").build(), new TexPathBlockIcon("blocks/sponge_wet.png"), 0xff9b9a33, false)
        });
        allTemplates.put("minecraft:glass", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.GLASS).build(), new TexPathBlockIcon("blocks/glass.png"), 0x46daf0f4, false)
        });
        allTemplates.put("minecraft:lapis_ore", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.LAPIS_ORE).build(), new TexPathBlockIcon("blocks/lapis_ore.png"), 0xff667086, false)
        });
        allTemplates.put("minecraft:lapis_block", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.LAPIS_BLOCK).build(), new TexPathBlockIcon("blocks/lapis_block.png"), 0xff1d47a5, false)
        });
        allTemplates.put("minecraft:dispenser", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.DISPENSER).setProperty("facing_direction", 0).setProperty("triggered_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/dispenser.png"), 0xff606060, false)
        });
        allTemplates.put("minecraft:sandstone", new BlockTemplate[]{
                new BlockTemplate("default", new Block.Builder(BlockType.SANDSTONE).setProperty("sand_stone_type", "default").build(), new TexPathBlockIcon("blocks/sandstone_default.png"), 0xffdad29e, false),
                new BlockTemplate("chiseled", new Block.Builder(BlockType.SANDSTONE).setProperty("sand_stone_type", "heiroglyphs").build(), new TexPathBlockIcon("blocks/sandstone_chiseled.png"), 0xffdad1a2, false),
                new BlockTemplate("smooth", new Block.Builder(BlockType.SANDSTONE).setProperty("sand_stone_type", "cut").build(), new TexPathBlockIcon("blocks/sandstone_smooth.png"), 0xffdad1a2, false)
        });
        allTemplates.put("minecraft:noteblock", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.NOTEBLOCK).build(), new TexPathBlockIcon("blocks/noteblock.png"), 0xff644332, false)
        });
        allTemplates.put("minecraft:bed", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.BED).setProperty("direction", 0).setProperty("head_piece_bit", (byte) 0).setProperty("occupied_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/bed.png"), 0xff8e1616, false)
        });
        allTemplates.put("minecraft:golden_rail", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.GOLDEN_RAIL).setProperty("rail_direction", 0).setProperty("rail_data_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/golden_rail.png"), 0xab9a6846, false)
        });
        allTemplates.put("minecraft:detector_rail", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.DETECTOR_RAIL).setProperty("rail_direction", 0).setProperty("rail_data_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/detector_rail.png"), 0x9b786559, false)
        });
        allTemplates.put("minecraft:sticky_piston", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.STICKY_PISTON).setProperty("facing_direction", 0).build(), new TexPathBlockIcon("blocks/sticky_piston.png"), 0xff8d9263, false)
        });
        allTemplates.put("minecraft:web", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.WEB).build(), new TexPathBlockIcon("blocks/web.png"), 0x68dcdcdc, false)
        });
        allTemplates.put("minecraft:tallgrass", new BlockTemplate[]{
                new BlockTemplate("fern", new Block.Builder(BlockType.TALLGRASS).setProperty("tall_grass_type", "tall").build(), new TexPathBlockIcon("blocks/tallgrass_fern.png"), 0xff747474, true),
                new BlockTemplate("grass", new Block.Builder(BlockType.TALLGRASS).setProperty("tall_grass_type", "fern").build(), new TexPathBlockIcon("blocks/tallgrass_grass.png"), 0x4e787878, true)
        });
        allTemplates.put("minecraft:deadbush", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.DEADBUSH).build(), new TexPathBlockIcon("blocks/deadbush.png"), 0x517b4f19, false)
        });
        allTemplates.put("minecraft:piston", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.PISTON).setProperty("facing_direction", 0).build(), new TexPathBlockIcon("blocks/piston.png"), 0xff998159, false)
        });
        allTemplates.put("minecraft:pistonArmCollision", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.PISTONARMCOLLISION).setProperty("facing_direction", 0).build(), new TexPathBlockIcon("blocks/pistonArmCollision.png"), 0xff9c7f4e, false)
        });
        allTemplates.put("minecraft:wool", new BlockTemplate[]{
                new BlockTemplate("white", new Block.Builder(BlockType.WOOL).setProperty("color", "white").build(), new TexPathBlockIcon("blocks/wool_colored_white.png"), 0xffdddddd, false),
                new BlockTemplate("orange", new Block.Builder(BlockType.WOOL).setProperty("color", "orange").build(), new TexPathBlockIcon("blocks/wool_colored_orange.png"), 0xffdb7d3e, false),
                new BlockTemplate("magenta", new Block.Builder(BlockType.WOOL).setProperty("color", "magenta").build(), new TexPathBlockIcon("blocks/wool_colored_magenta.png"), 0xffb350bc, false),
                new BlockTemplate("light_blue", new Block.Builder(BlockType.WOOL).setProperty("color", "light_blue").build(), new TexPathBlockIcon("blocks/wool_colored_light_blue.png"), 0xff6a8ac9, false),
                new BlockTemplate("yellow", new Block.Builder(BlockType.WOOL).setProperty("color", "yellow").build(), new TexPathBlockIcon("blocks/wool_colored_yellow.png"), 0xffb1a627, false),
                new BlockTemplate("lime", new Block.Builder(BlockType.WOOL).setProperty("color", "lime").build(), new TexPathBlockIcon("blocks/wool_colored_lime.png"), 0xff41ae38, false),
                new BlockTemplate("pink", new Block.Builder(BlockType.WOOL).setProperty("color", "pink").build(), new TexPathBlockIcon("blocks/wool_colored_pink.png"), 0xffd08499, false),
                new BlockTemplate("gray", new Block.Builder(BlockType.WOOL).setProperty("color", "gray").build(), new TexPathBlockIcon("blocks/wool_colored_gray.png"), 0xff404040, false),
                new BlockTemplate("silver", new Block.Builder(BlockType.WOOL).setProperty("color", "silver").build(), new TexPathBlockIcon("blocks/wool_colored_silver.png"), 0xff9aa1a1, false),
                new BlockTemplate("cyan", new Block.Builder(BlockType.WOOL).setProperty("color", "cyan").build(), new TexPathBlockIcon("blocks/wool_colored_cyan.png"), 0xff2e6e89, false),
                new BlockTemplate("purple", new Block.Builder(BlockType.WOOL).setProperty("color", "purple").build(), new TexPathBlockIcon("blocks/wool_colored_purple.png"), 0xff7e3db5, false),
                new BlockTemplate("blue", new Block.Builder(BlockType.WOOL).setProperty("color", "blue").build(), new TexPathBlockIcon("blocks/wool_colored_blue.png"), 0xff2e388d, false),
                new BlockTemplate("brown", new Block.Builder(BlockType.WOOL).setProperty("color", "brown").build(), new TexPathBlockIcon("blocks/wool_colored_brown.png"), 0xff4f321f, false),
                new BlockTemplate("green", new Block.Builder(BlockType.WOOL).setProperty("color", "green").build(), new TexPathBlockIcon("blocks/wool_colored_green.png"), 0xff35461b, false),
                new BlockTemplate("red", new Block.Builder(BlockType.WOOL).setProperty("color", "red").build(), new TexPathBlockIcon("blocks/wool_colored_red.png"), 0xff963430, false),
                new BlockTemplate("black", new Block.Builder(BlockType.WOOL).setProperty("color", "black").build(), new TexPathBlockIcon("blocks/wool_colored_black.png"), 0xff191616, false)
        });
        allTemplates.put("minecraft:element_0", new BlockTemplate[]{
                new BlockTemplate("unknown", new Block.Builder(BlockType.ELEMENT_0).build(), new TexPathBlockIcon("blocks/coal_ore.png"), 0xff191616, false),
                new BlockTemplate("unknown", new Block.Builder(BlockType.ELEMENT_0).build(), new TexPathBlockIcon("blocks/coal_ore.png"), 0xff191616, false),
                new BlockTemplate("unknown", new Block.Builder(BlockType.ELEMENT_0).build(), new TexPathBlockIcon("blocks/coal_ore.png"), 0xff191616, false),
                new BlockTemplate("unknown", new Block.Builder(BlockType.ELEMENT_0).build(), new TexPathBlockIcon("blocks/coal_ore.png"), 0xff191616, false),
                new BlockTemplate("unknown", new Block.Builder(BlockType.ELEMENT_0).build(), new TexPathBlockIcon("blocks/coal_ore.png"), 0xff191616, false),
                new BlockTemplate("unknown", new Block.Builder(BlockType.ELEMENT_0).build(), new TexPathBlockIcon("blocks/coal_ore.png"), 0xff191616, false),
                new BlockTemplate("unknown", new Block.Builder(BlockType.ELEMENT_0).build(), new TexPathBlockIcon("blocks/coal_ore.png"), 0xff191616, false),
                new BlockTemplate("unknown", new Block.Builder(BlockType.ELEMENT_0).build(), new TexPathBlockIcon("blocks/coal_ore.png"), 0xff191616, false),
                new BlockTemplate("unknown", new Block.Builder(BlockType.ELEMENT_0).build(), new TexPathBlockIcon("blocks/coal_ore.png"), 0xff191616, false),
                new BlockTemplate("unknown", new Block.Builder(BlockType.ELEMENT_0).build(), new TexPathBlockIcon("blocks/coal_ore.png"), 0xff191616, false),
                new BlockTemplate("unknown", new Block.Builder(BlockType.ELEMENT_0).build(), new TexPathBlockIcon("blocks/coal_ore.png"), 0xff191616, false),
                new BlockTemplate("unknown", new Block.Builder(BlockType.ELEMENT_0).build(), new TexPathBlockIcon("blocks/coal_ore.png"), 0xff191616, false),
                new BlockTemplate("unknown", new Block.Builder(BlockType.ELEMENT_0).build(), new TexPathBlockIcon("blocks/coal_ore.png"), 0xff191616, false),
                new BlockTemplate("unknown", new Block.Builder(BlockType.ELEMENT_0).build(), new TexPathBlockIcon("blocks/coal_ore.png"), 0xff191616, false),
                new BlockTemplate("unknown", new Block.Builder(BlockType.ELEMENT_0).build(), new TexPathBlockIcon("blocks/coal_ore.png"), 0xff191616, false),
                new BlockTemplate("unknown", new Block.Builder(BlockType.ELEMENT_0).build(), new TexPathBlockIcon("blocks/coal_ore.png"), 0xff191616, false)
        });
        allTemplates.put("minecraft:yellow_flower", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.YELLOW_FLOWER).build(), new TexPathBlockIcon("blocks/yellow_flower.png"), 0x1e6ca200, false)
        });
        allTemplates.put("minecraft:red_flower", new BlockTemplate[]{
                new BlockTemplate("poppy", new Block.Builder(BlockType.RED_FLOWER).setProperty("flower_type", "poppy").build(), new TexPathBlockIcon("blocks/flower_poppy.png"), 0x1d8a2b0d, false),
                new BlockTemplate("blue_orchid", new Block.Builder(BlockType.RED_FLOWER).setProperty("flower_type", "orchid").build(), new TexPathBlockIcon("blocks/flower_blue_orchid.png"), 0x1d188fd3, false),
                new BlockTemplate("allium", new Block.Builder(BlockType.RED_FLOWER).setProperty("flower_type", "allium").build(), new TexPathBlockIcon("blocks/flower_allium.png"), 0x1ddbb7f8, false),
                new BlockTemplate("houstonia", new Block.Builder(BlockType.RED_FLOWER).setProperty("flower_type", "houstonia").build(), new TexPathBlockIcon("blocks/flower_houstonia.png"), 0x1defef99, false),
                new BlockTemplate("tulip_red", new Block.Builder(BlockType.RED_FLOWER).setProperty("flower_type", "tulip_red").build(), new TexPathBlockIcon("blocks/flower_tulip_red.png"), 0x1dbd2604, false),
                new BlockTemplate("tulip_orange", new Block.Builder(BlockType.RED_FLOWER).setProperty("flower_type", "tulip_orange").build(), new TexPathBlockIcon("blocks/flower_tulip_orange.png"), 0x1dd06713, false),
                new BlockTemplate("tulip_white", new Block.Builder(BlockType.RED_FLOWER).setProperty("flower_type", "tulip_white").build(), new TexPathBlockIcon("blocks/flower_tulip_white.png"), 0x1df9f9f9, false),
                new BlockTemplate("tulip_pink", new Block.Builder(BlockType.RED_FLOWER).setProperty("flower_type", "tulip_pink").build(), new TexPathBlockIcon("blocks/flower_tulip_pink.png"), 0x1dbeb3be, false),
                new BlockTemplate("oxeye_daisy", new Block.Builder(BlockType.RED_FLOWER).setProperty("flower_type", "oxeye").build(), new TexPathBlockIcon("blocks/flower_oxeye_daisy.png"), 0x1ddadada, false)
        });
        allTemplates.put("minecraft:brown_mushroom", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.BROWN_MUSHROOM).build(), new TexPathBlockIcon("blocks/brown_mushroom.png"), 0x198a6953, false)
        });
        allTemplates.put("minecraft:red_mushroom", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.RED_MUSHROOM).build(), new TexPathBlockIcon("blocks/red_mushroom.png"), 0x21c33538, false)
        });
        allTemplates.put("minecraft:gold_block", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.GOLD_BLOCK).build(), new TexPathBlockIcon("blocks/gold_block.png"), 0xfff9ec4e, false)
        });
        allTemplates.put("minecraft:iron_block", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.IRON_BLOCK).build(), new TexPathBlockIcon("blocks/iron_block.png"), 0xffdbdbdb, false)
        });
        allTemplates.put("minecraft:double_stone_slab", new BlockTemplate[]{
                new BlockTemplate("stone", new Block.Builder(BlockType.DOUBLE_STONE_SLAB).setProperty("stone_slab_type", "smooth_stone").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/double_stone_slab_stone.png"), 0xff9f9f9f, false),
                new BlockTemplate("sand", new Block.Builder(BlockType.DOUBLE_STONE_SLAB).setProperty("stone_slab_type", "sandstone").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/double_stone_slab_sand.png"), 0xffdad29e, false),
                new BlockTemplate("wood", new Block.Builder(BlockType.DOUBLE_STONE_SLAB).setProperty("stone_slab_type", "wood").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/double_stone_slab_wood.png"), 0xff9c7f4e, false),
                new BlockTemplate("cobble", new Block.Builder(BlockType.DOUBLE_STONE_SLAB).setProperty("stone_slab_type", "cobblestone").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/double_stone_slab_cobble.png"), 0xff7a7a7a, false),
                new BlockTemplate("brick", new Block.Builder(BlockType.DOUBLE_STONE_SLAB).setProperty("stone_slab_type", "brick").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/double_stone_slab_brick.png"), 0xff926356, false),
                new BlockTemplate("smooth_stone_brick", new Block.Builder(BlockType.DOUBLE_STONE_SLAB).setProperty("stone_slab_type", "stone_brick").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/double_stone_slab_smooth_stone_brick.png"), 0xff7d7d7d, false),
                new BlockTemplate("quartz", new Block.Builder(BlockType.DOUBLE_STONE_SLAB).setProperty("stone_slab_type", "quartz").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/double_stone_slab_quartz.png"), 0xff2c161a, false),
                new BlockTemplate("nether_brick", new Block.Builder(BlockType.DOUBLE_STONE_SLAB).setProperty("stone_slab_type", "nether_brick").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/double_stone_slab_nether_brick.png"), 0xffece9e2, false),
                new BlockTemplate("red_sandstone", new Block.Builder(BlockType.DOUBLE_STONE_SLAB).setProperty("stone_slab_type", "smooth_stone").setProperty("top_slot_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/double_stone_slab_red_sandstone.png"), 0xff9f9f9f, false)
        });
        allTemplates.put("minecraft:stone_slab", new BlockTemplate[]{
                new BlockTemplate("stone", new Block.Builder(BlockType.STONE_SLAB).setProperty("stone_slab_type", "smooth_stone").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/stone_slab_side.png"), 0xff9f9f9f, false),
                new BlockTemplate("sand", new Block.Builder(BlockType.STONE_SLAB).setProperty("stone_slab_type", "sandstone").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/stone_slab_side.png"), 0xffdad29e, false),
                new BlockTemplate("wood", new Block.Builder(BlockType.STONE_SLAB).setProperty("stone_slab_type", "wood").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/stone_slab_side.png"), 0xff9c7f4e, false),
                new BlockTemplate("cobble", new Block.Builder(BlockType.STONE_SLAB).setProperty("stone_slab_type", "cobblestone").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/stone_slab_side.png"), 0xff7a7a7a, false),
                new BlockTemplate("brick", new Block.Builder(BlockType.STONE_SLAB).setProperty("stone_slab_type", "brick").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/stone_slab_side.png"), 0xff926356, false),
                new BlockTemplate("smooth_stone_brick", new Block.Builder(BlockType.STONE_SLAB).setProperty("stone_slab_type", "stone_brick").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/stone_slab_side.png"), 0xff7d7d7d, false),
                new BlockTemplate("quartz", new Block.Builder(BlockType.STONE_SLAB).setProperty("stone_slab_type", "quartz").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/stone_slab_side.png"), 0xff2c161a, false),
                new BlockTemplate("nether_brick", new Block.Builder(BlockType.STONE_SLAB).setProperty("stone_slab_type", "nether_brick").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/stone_slab_side.png"), 0xffece9e2, false)
        });
        allTemplates.put("minecraft:brick_block", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.BRICK_BLOCK).build(), new TexPathBlockIcon("blocks/brick_block.png"), 0xff926356, false)
        });
        allTemplates.put("minecraft:tnt", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.TNT).setProperty("allow_underwater_bit", (byte) 0).setProperty("explode_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/tnt.png"), 0xff82412f, false)
        });
        allTemplates.put("minecraft:bookshelf", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.BOOKSHELF).build(), new TexPathBlockIcon("blocks/bookshelf.png"), 0xff6b5839, false)
        });
        allTemplates.put("minecraft:mossy_cobblestone", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.MOSSY_COBBLESTONE).build(), new TexPathBlockIcon("blocks/mossy_cobblestone.png"), 0xff677967, false)
        });
        allTemplates.put("minecraft:obsidian", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.OBSIDIAN).build(), new TexPathBlockIcon("blocks/obsidian.png"), 0xff14121d, false)
        });
        allTemplates.put("minecraft:torch", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.TORCH).setProperty("torch_facing_direction", "unknown").build(), new TexPathBlockIcon("blocks/torch.png"), 0x13826a3a, false)
        });
        allTemplates.put("minecraft:fire", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.FIRE).setProperty("age", 0).build(), new TexPathBlockIcon("blocks/fire.png"), 0x8bd38c35, false)
        });
        allTemplates.put("minecraft:mob_spawner", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.MOB_SPAWNER).build(), new TexPathBlockIcon("blocks/mob_spawner.png"), 0x9b1a2731, false)
        });
        allTemplates.put("minecraft:oak_stairs", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.OAK_STAIRS).setProperty("weirdo_direction", 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/oak_stairs.png"), 0xff9c7f4e, false)
        });
        allTemplates.put("minecraft:chest", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.CHEST).setProperty("facing_direction", 2).build(), new TexPathBlockIcon("blocks/chest_front.png"), 0xc86f5739, false)
        });
        allTemplates.put("minecraft:redstone_wire", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.REDSTONE_WIRE).setProperty("redstone_signal", 0).build(), new TexPathBlockIcon("blocks/redstone_wire.png"), 0x80fa1010, false)
        });
        allTemplates.put("minecraft:diamond_ore", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.DIAMOND_ORE).build(), new TexPathBlockIcon("blocks/diamond_ore.png"), 0xff818c8f, false)
        });
        allTemplates.put("minecraft:diamond_block", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.DIAMOND_BLOCK).build(), new TexPathBlockIcon("blocks/diamond_block.png"), 0xff61dbd5, false)
        });
        allTemplates.put("minecraft:crafting_table", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.CRAFTING_TABLE).build(), new TexPathBlockIcon("blocks/crafting_table.png"), 0xff6b472a, false)
        });
        allTemplates.put("minecraft:wheat", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.WHEAT).setProperty("growth", 0).build(), new TexPathBlockIcon("blocks/wheat.png"), 0x0500b312, false)
        });
        allTemplates.put("minecraft:farmland", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.FARMLAND).setProperty("moisturized_amount", 0).build(), new TexPathBlockIcon("blocks/farmland.png"), 0xff734b2d, false)
        });
        allTemplates.put("minecraft:furnace", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.FURNACE).setProperty("facing_direction", 0).build(), new TexPathBlockIcon("blocks/furnace.png"), 0xff606060, false)
        });
        allTemplates.put("minecraft:lit_furnace", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.LIT_FURNACE).setProperty("facing_direction", 0).build(), new TexPathBlockIcon("blocks/lit_furnace.png"), 0xff606060, false)
        });
        allTemplates.put("minecraft:standing_sign", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.STANDING_SIGN).setProperty("ground_sign_direction", 0).build(), new TexPathBlockIcon("blocks/standing_sign.png"), 0x566f5739, false)
        });
        allTemplates.put("minecraft:wooden_door", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.WOODEN_DOOR).setProperty("direction", 0).setProperty("door_hinge_bit", (byte) 0).setProperty("open_bit", (byte) 0).setProperty("upper_block_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/wooden_door.png"), 0xcf866733, false)
        });
        allTemplates.put("minecraft:ladder", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.LADDER).setProperty("facing_direction", 0).build(), new TexPathBlockIcon("blocks/ladder.png"), 0x8f795f34, false)
        });
        allTemplates.put("minecraft:rail", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.RAIL).setProperty("rail_direction", 0).build(), new TexPathBlockIcon("blocks/rail.png"), 0x8f796c58, false)
        });
        allTemplates.put("minecraft:stone_stairs", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.STONE_STAIRS).setProperty("weirdo_direction", 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/stone_stairs.png"), 0xff7a7a7a, false)
        });
        allTemplates.put("minecraft:wall_sign", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.WALL_SIGN).setProperty("facing_direction", 0).build(), new TexPathBlockIcon("blocks/wall_sign.png"), 0x206f5739, false)
        });
        allTemplates.put("minecraft:lever", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.LEVER).setProperty("lever_direction", "down_east_west").setProperty("open_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/lever.png"), 0x136a5940, false)
        });
        allTemplates.put("minecraft:stone_pressure_plate", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.STONE_PRESSURE_PLATE).setProperty("redstone_signal", 0).build(), new TexPathBlockIcon("blocks/stone_pressure_plate.png"), 0xff7d7d7d, false)
        });
        allTemplates.put("minecraft:iron_door", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.IRON_DOOR).setProperty("direction", 0).setProperty("door_hinge_bit", (byte) 0).setProperty("open_bit", (byte) 0).setProperty("upper_block_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/iron_door.png"), 0xcfbababa, false)
        });
        allTemplates.put("minecraft:wooden_pressure_plate", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.WOODEN_PRESSURE_PLATE).setProperty("redstone_signal", 0).build(), new TexPathBlockIcon("blocks/wooden_pressure_plate.png"), 0xff9c7f4e, false)
        });
        allTemplates.put("minecraft:redstone_ore", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.REDSTONE_ORE).build(), new TexPathBlockIcon("blocks/redstone_ore.png"), 0xff846b6b, false)
        });
        allTemplates.put("minecraft:lit_redstone_ore", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.LIT_REDSTONE_ORE).build(), new TexPathBlockIcon("blocks/lit_redstone_ore.png"), 0xff846b6b, false)
        });
        allTemplates.put("minecraft:unlit_redstone_torch", new BlockTemplate[]{

        });
        allTemplates.put("minecraft:redstone_torch", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.REDSTONE_TORCH).setProperty("torch_facing_direction", "unknown").build(), new TexPathBlockIcon("blocks/redstone_torch.png"), 0x46a74b29, false)
        });
        allTemplates.put("minecraft:stone_button", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.STONE_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/stone_button.png"), 0x28565656, false)
        });
        allTemplates.put("minecraft:snow_layer", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.SNOW_LAYER).setProperty("covered_bit", (byte) 0).setProperty("height", 0).build(), new TexPathBlockIcon("blocks/snow_layer.png"), 0xffeffbfb, false)
        });
        allTemplates.put("minecraft:ice", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.ICE).build(), new TexPathBlockIcon("blocks/ice.png"), 0x9f7dadff, false)
        });
        allTemplates.put("minecraft:snow", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.SNOW).build(), new TexPathBlockIcon("blocks/snow.png"), 0xffeffbfb, false)
        });
        allTemplates.put("minecraft:cactus", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.CACTUS).setProperty("age", 0).build(), new TexPathBlockIcon("blocks/cactus.png"), 0xc30d6318, false)
        });
        allTemplates.put("minecraft:clay", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.CLAY).build(), new TexPathBlockIcon("blocks/clay.png"), 0xff9ea4b0, false)
        });
        allTemplates.put("minecraft:reeds", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.REEDS).setProperty("age", 0).build(), new TexPathBlockIcon("blocks/reeds.png"), 0x8c94c065, false)
        });
        allTemplates.put("minecraft:jukebox", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.JUKEBOX).build(), new TexPathBlockIcon("blocks/fence_birch_fence.png"), 0x8f463822, false)
        });
        allTemplates.put("minecraft:fence", new BlockTemplate[]{
                new BlockTemplate("fence", new Block.Builder(BlockType.FENCE).setProperty("wood_type", "oak").build(), new TexPathBlockIcon("blocks/fence_fence.png"), 0x8f463822, false),
                new BlockTemplate("spruce_fence", new Block.Builder(BlockType.FENCE).setProperty("wood_type", "spruce").build(), new TexPathBlockIcon("blocks/fence_spruce_fence.png"), 0x8f5a3d0d, false),
                new BlockTemplate("birch_fence", new Block.Builder(BlockType.FENCE).setProperty("wood_type", "birch").build(), new TexPathBlockIcon("blocks/fence_birch_fence.png"), 0x8fdabd8d, false),
                new BlockTemplate("jungle_fence", new Block.Builder(BlockType.FENCE).setProperty("wood_type", "jungle").build(), new TexPathBlockIcon("blocks/fence_jungle_fence.png"), 0x8fBa7d5d, false),
                new BlockTemplate("acacia_fence", new Block.Builder(BlockType.FENCE).setProperty("wood_type", "acacia").build(), new TexPathBlockIcon("blocks/fence_acacia_fence.png"), 0x8f934f39, false),
                new BlockTemplate("dark_oak_fence", new Block.Builder(BlockType.FENCE).setProperty("wood_type", "dark_oak").build(), new TexPathBlockIcon("blocks/fence_dark_oak_fence.png"), 0x8f2d2213, false)
        });
        allTemplates.put("minecraft:pumpkin", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.PUMPKIN).setProperty("direction", 0).build(), new TexPathBlockIcon("blocks/pumpkin.png"), 0xffc07615, false)
        });
        allTemplates.put("minecraft:netherrack", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.NETHERRACK).build(), new TexPathBlockIcon("blocks/netherrack.png"), 0xff6f3634, false)
        });
        allTemplates.put("minecraft:soul_sand", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.SOUL_SAND).build(), new TexPathBlockIcon("blocks/soul_sand.png"), 0xff544033, false)
        });
        allTemplates.put("minecraft:glowstone", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.GLOWSTONE).build(), new TexPathBlockIcon("blocks/glowstone.png"), 0xff8f7645, false)
        });
        allTemplates.put("minecraft:portal", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.PORTAL).setProperty("portal_axis", "unknown").build(), new TexPathBlockIcon("blocks/portal.png"), 0xc8410491, false)
        });
        allTemplates.put("minecraft:lit_pumpkin", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.LIT_PUMPKIN).setProperty("direction", 0).build(), new TexPathBlockIcon("blocks/lit_pumpkin.png"), 0xffc07615, false)
        });
        allTemplates.put("minecraft:cake", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.CAKE).setProperty("bite_counter", 0).build(), new TexPathBlockIcon("blocks/cake.png"), 0xc3e4cdce, false)
        });
        allTemplates.put("minecraft:unpowered_repeater", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.UNPOWERED_REPEATER).setProperty("direction", 0).setProperty("repeater_delay", 0).build(), new TexPathBlockIcon("blocks/unpowered_repeater.png"), 0xff979393, false)
        });
        allTemplates.put("minecraft:powered_repeater", new BlockTemplate[]{

        });
        allTemplates.put("minecraft:invisibleBedrock", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.INVISIBLEBEDROCK).build(), new TexPathBlockIcon("blocks/invisibleBedrock.png"), 0x3c282828, false)
        });
        allTemplates.put("minecraft:trapdoor", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.TRAPDOOR).setProperty("direction", 0).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/trapdoor.png"), 0xdb7e5d2d, false)
        });
        allTemplates.put("minecraft:monster_egg", new BlockTemplate[]{
                new BlockTemplate("stone", new Block.Builder(BlockType.MONSTER_EGG).setProperty("monster_egg_stone_type", "stone").build(), new TexPathBlockIcon("blocks/monster_egg_stone.png"), 0xff7d7d7d, false),
                new BlockTemplate("cobble", new Block.Builder(BlockType.MONSTER_EGG).setProperty("monster_egg_stone_type", "cobblestone").build(), new TexPathBlockIcon("blocks/monster_egg_cobble.png"), 0xff7a7a7a, false),
                new BlockTemplate("brick", new Block.Builder(BlockType.MONSTER_EGG).setProperty("monster_egg_stone_type", "stone_brick").build(), new TexPathBlockIcon("blocks/monster_egg_brick.png"), 0xff7a7a7a, false),
                new BlockTemplate("mossybrick", new Block.Builder(BlockType.MONSTER_EGG).setProperty("monster_egg_stone_type", "mossy_stone_brick").build(), new TexPathBlockIcon("blocks/monster_egg_mossybrick.png"), 0xff7b6651, false),
                new BlockTemplate("crackedbrick", new Block.Builder(BlockType.MONSTER_EGG).setProperty("monster_egg_stone_type", "cracked_stone_brick").build(), new TexPathBlockIcon("blocks/monster_egg_crackedbrick.png"), 0xff7b6651, false),
                new BlockTemplate("chiseledbrick", new Block.Builder(BlockType.MONSTER_EGG).setProperty("monster_egg_stone_type", "chiseled_stone_brick").build(), new TexPathBlockIcon("blocks/monster_egg_chiseledbrick.png"), 0xff7b6651, false)
        });
        allTemplates.put("minecraft:stonebrick", new BlockTemplate[]{
                new BlockTemplate("default", new Block.Builder(BlockType.STONEBRICK).setProperty("stone_brick_type", "default").build(), new TexPathBlockIcon("blocks/stonebrick_default.png"), 0xff7a7a7a, false),
                new BlockTemplate("mossy", new Block.Builder(BlockType.STONEBRICK).setProperty("stone_brick_type", "mossy").build(), new TexPathBlockIcon("blocks/stonebrick_mossy.png"), 0xff72776a, false),
                new BlockTemplate("cracked", new Block.Builder(BlockType.STONEBRICK).setProperty("stone_brick_type", "cracked").build(), new TexPathBlockIcon("blocks/stonebrick_cracked.png"), 0xff767676, false),
                new BlockTemplate("chiseled", new Block.Builder(BlockType.STONEBRICK).setProperty("stone_brick_type", "chiseled").build(), new TexPathBlockIcon("blocks/stonebrick_chiseled.png"), 0xff767676, false),
                new BlockTemplate("smooth", new Block.Builder(BlockType.STONEBRICK).setProperty("stone_brick_type", "smooth").build(), new TexPathBlockIcon("blocks/stonebrick_smooth.png"), 0xff767676, false)
        });
        allTemplates.put("minecraft:brown_mushroom_block", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.BROWN_MUSHROOM_BLOCK).setProperty("huge_mushroom_bits", 0).build(), new TexPathBlockIcon("blocks/brown_mushroom_block.png"), 0xff8d6a53, false)
        });
        allTemplates.put("minecraft:red_mushroom_block", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.RED_MUSHROOM_BLOCK).setProperty("huge_mushroom_bits", 0).build(), new TexPathBlockIcon("blocks/red_mushroom_block.png"), 0xffb62524, false)
        });
        allTemplates.put("minecraft:iron_bars", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.IRON_BARS).build(), new TexPathBlockIcon("blocks/iron_bars.png"), 0x736d6c6a, false)
        });
        allTemplates.put("minecraft:glass_pane", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.GLASS_PANE).build(), new TexPathBlockIcon("blocks/glass_pane.png"), 0x1fd3eff4, false)
        });
        allTemplates.put("minecraft:melon_block", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.MELON_BLOCK).build(), new TexPathBlockIcon("blocks/melon_block.png"), 0xff979924, false)
        });
        allTemplates.put("minecraft:pumpkin_stem", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.PUMPKIN_STEM).setProperty("growth", 0).build(), new TexPathBlockIcon("blocks/pumpkin_stem.png"), 0x1e87b759, false)
        });
        allTemplates.put("minecraft:melon_stem", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.MELON_STEM).setProperty("growth", 0).build(), new TexPathBlockIcon("blocks/melon_stem.png"), 0x1e87b759, false)
        });
        allTemplates.put("minecraft:vine", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.VINE).setProperty("vine_direction_bits", 0).build(), new TexPathBlockIcon("blocks/vine.png"), 0x8a6f6f6f, false)
        });
        allTemplates.put("minecraft:fence_gate", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.FENCE_GATE).setProperty("direction", 0).setProperty("in_wall_bit", (byte) 0).setProperty("open_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/fence_gate.png"), 0x7b463822, false)
        });
        allTemplates.put("minecraft:brick_stairs", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.BRICK_STAIRS).setProperty("weirdo_direction", 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/brick_stairs.png"), 0xff926356, false)
        });
        allTemplates.put("minecraft:stone_brick_stairs", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.STONE_BRICK_STAIRS).setProperty("weirdo_direction", 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/stone_brick_stairs.png"), 0xff7a7a7a, false)
        });
        allTemplates.put("minecraft:mycelium", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.MYCELIUM).build(), new TexPathBlockIcon("blocks/mycelium.png"), 0xff6f6369, false)
        });
        allTemplates.put("minecraft:waterlily", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.WATERLILY).build(), new TexPathBlockIcon("blocks/waterlily.png"), 0x93335a21, false)
        });
        allTemplates.put("minecraft:nether_brick", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.NETHER_BRICK).build(), new TexPathBlockIcon("blocks/nether_brick.png"), 0xff2c161a, false)
        });
        allTemplates.put("minecraft:nether_brick_fence", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.NETHER_BRICK_FENCE).build(), new TexPathBlockIcon("blocks/nether_brick_fence.png"), 0xff2c161a, false)
        });
        allTemplates.put("minecraft:nether_brick_stairs", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.NETHER_BRICK_STAIRS).setProperty("weirdo_direction", 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/nether_brick_stairs.png"), 0xff2c161a, false)
        });
        allTemplates.put("minecraft:nether_wart", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.NETHER_WART).setProperty("age", 0).build(), new TexPathBlockIcon("blocks/nether_wart.png"), 0x2a6a0e1e, false)
        });
        allTemplates.put("minecraft:enchanting_table", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.ENCHANTING_TABLE).build(), new TexPathBlockIcon("blocks/enchanting_table.png"), 0xff67403b, false)
        });
        allTemplates.put("minecraft:brewing_stand", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.BREWING_STAND).setProperty("brewing_stand_slot_a_bit", (byte) 0).setProperty("brewing_stand_slot_b_bit", (byte) 0).setProperty("brewing_stand_slot_c_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/brewing_stand.png"), 0x767c6751, false)
        });
        allTemplates.put("minecraft:cauldron", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.CAULDRON).setProperty("cauldron_liquid", "water").setProperty("fill_level", 0).build(), new TexPathBlockIcon("blocks/cauldron.png"), 0xff373737, false)
        });
        allTemplates.put("minecraft:end_portal", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.END_PORTAL).build(), new TexPathBlockIcon("blocks/endframe_top.png"), 0xff101010, false)
        });
        allTemplates.put("minecraft:end_portal_frame", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.END_PORTAL_FRAME).setProperty("direction", 0).setProperty("end_portal_eye_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/endframe_side.png"), 0xff597560, false)
        });
        allTemplates.put("minecraft:end_stone", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.END_STONE).build(), new TexPathBlockIcon("blocks/end_stone.png"), 0xffdddfa5, false)
        });
        allTemplates.put("minecraft:dragon_egg", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.DRAGON_EGG).build(), new TexPathBlockIcon("blocks/dragon_egg.png"), 0xff0c090f, false)
        });
        allTemplates.put("minecraft:redstone_lamp", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.REDSTONE_LAMP).build(), new TexPathBlockIcon("blocks/redstone_lamp.png"), 0xff462b1a, false)
        });
        allTemplates.put("minecraft:lit_redstone_lamp", new BlockTemplate[]{

        });
        allTemplates.put("minecraft:dropper", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.DROPPER).setProperty("facing_direction", 0).setProperty("triggered_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/dropper.png"), 0xff9c7f4e, false)
        });
        allTemplates.put("minecraft:activator_rail", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.ACTIVATOR_RAIL).setProperty("rail_direction", 0).setProperty("rail_data_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/activator_rail.png"), 0xff9c7f4e, false)
        });
        allTemplates.put("minecraft:cocoa", new BlockTemplate[]{

        });
        allTemplates.put("minecraft:sandstone_stairs", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.SANDSTONE_STAIRS).setProperty("weirdo_direction", 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/sandstone_stairs.png"), 0xffdad29e, false)
        });
        allTemplates.put("minecraft:emerald_ore", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.EMERALD_ORE).build(), new TexPathBlockIcon("blocks/emerald_ore.png"), 0xff6d8074, false)
        });
        allTemplates.put("minecraft:ender_chest", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.ENDER_CHEST).setProperty("facing_direction", 2).build(), new TexPathBlockIcon("blocks/ender_chest_front.png"), 0xc82c3e40, false)
        });
        allTemplates.put("minecraft:tripwire_hook", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.TRIPWIRE_HOOK).setProperty("direction", 0).setProperty("attached_bit", (byte) 0).setProperty("powered_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/tripwire_hook.png"), 0x2d8a8171, false)
        });
        allTemplates.put("minecraft:tripWire", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.TRIPWIRE).setProperty("attached_bit", (byte) 0).setProperty("disarmed_bit", (byte) 0).setProperty("powered_bit", (byte) 0).setProperty("suspended_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/tripWire.png"), 0x2d818181, false)
        });
        allTemplates.put("minecraft:emerald_block", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.EMERALD_BLOCK).build(), new TexPathBlockIcon("blocks/emerald_block.png"), 0xff51d975, false)
        });
        allTemplates.put("minecraft:spruce_stairs", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_STAIRS).setProperty("weirdo_direction", 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/spruce_stairs.png"), 0xff5a3d0d, false)
        });
        allTemplates.put("minecraft:birch_stairs", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_STAIRS).setProperty("weirdo_direction", 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/birch_stairs.png"), 0xffdabd8d, false)
        });
        allTemplates.put("minecraft:jungle_stairs", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_STAIRS).setProperty("weirdo_direction", 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/jungle_stairs.png"), 0xffBa7d5d, false)
        });
        allTemplates.put("minecraft:beacon", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.BEACON).build(), new TexPathBlockIcon("blocks/beacon.png"), 0xff74ddd7, false)
        });
        allTemplates.put("minecraft:cobblestone_wall", new BlockTemplate[]{
                new BlockTemplate("normal", new Block.Builder(BlockType.COBBLESTONE_WALL).setProperty("wall_block_type", "cobblestone").build(), new TexPathBlockIcon("blocks/cobblestone_wall_normal.png"), 0xff7a7a7a, false),
                new BlockTemplate("mossy", new Block.Builder(BlockType.COBBLESTONE_WALL).setProperty("wall_block_type", "mossy_cobblestone").build(), new TexPathBlockIcon("blocks/cobblestone_wall_mossy.png"), 0xff506a50, false)
        });
        allTemplates.put("minecraft:flower_pot", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.FLOWER_POT).setProperty("update_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/flower_pot.png"), 0x31764133, false)
        });
        allTemplates.put("minecraft:carrots", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.CARROTS).setProperty("growth", 0).build(), new TexPathBlockIcon("blocks/carrots.png"), 0x0901ab10, false)
        });
        allTemplates.put("minecraft:potatoes", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.POTATOES).setProperty("growth", 0).build(), new TexPathBlockIcon("blocks/potatoes.png"), 0x0901ab10, false)
        });
        allTemplates.put("minecraft:wooden_button", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.WOODEN_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/wooden_button.png"), 0x2878613e, false)
        });
        allTemplates.put("minecraft:skull", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.SKULL).setProperty("facing_direction", 0).setProperty("no_drop_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/skull.png"), 0x8c8c8c8c, false)
        });
        allTemplates.put("minecraft:anvil", new BlockTemplate[]{
                new BlockTemplate("intact", new Block.Builder(BlockType.ANVIL).setProperty("direction", 0).setProperty("damage", "undamaged").build(), new TexPathBlockIcon("blocks/anvil_intact.png"), 0x9f403c3c, false),
                new BlockTemplate("slightly_damaged", new Block.Builder(BlockType.ANVIL).setProperty("direction", 0).setProperty("damage", "slightly_damaged").build(), new TexPathBlockIcon("blocks/anvil_slightly_damaged.png"), 0x9f403c3c, false),
                new BlockTemplate("very_damaged", new Block.Builder(BlockType.ANVIL).setProperty("direction", 0).setProperty("damage", "very_damaged").build(), new TexPathBlockIcon("blocks/anvil_very_damaged.png"), 0x9f403c3c, false)
        });
        allTemplates.put("minecraft:trapped_chest", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.TRAPPED_CHEST).setProperty("facing_direction", 2).build(), new TexPathBlockIcon("blocks/chest_front.png"), 0xfe6f5739, false)
        });
        allTemplates.put("minecraft:light_weighted_pressure_plate", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.LIGHT_WEIGHTED_PRESSURE_PLATE).setProperty("redstone_signal", 0).build(), new TexPathBlockIcon("blocks/light_weighted_pressure_plate.png"), 0xc8f9ec4e, false)
        });
        allTemplates.put("minecraft:heavy_weighted_pressure_plate", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.HEAVY_WEIGHTED_PRESSURE_PLATE).setProperty("redstone_signal", 0).build(), new TexPathBlockIcon("blocks/heavy_weighted_pressure_plate.png"), 0xc8dbdbdb, false)
        });
        allTemplates.put("minecraft:unpowered_comparator", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.UNPOWERED_COMPARATOR).setProperty("direction", 0).setProperty("output_lit_bit", (byte) 0).setProperty("output_subtract_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/unpowered_comparator.png"), 0xff9c9695, false)
        });
        allTemplates.put("minecraft:powered_comparator", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.POWERED_COMPARATOR).setProperty("direction", 0).setProperty("output_lit_bit", (byte) 0).setProperty("output_subtract_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/powered_comparator.png"), 0xffa59594, false)
        });
        allTemplates.put("minecraft:daylight_detector", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.DAYLIGHT_DETECTOR).setProperty("redstone_signal", 0).build(), new TexPathBlockIcon("blocks/daylight_detector.png"), 0xff82745e, false)
        });
        allTemplates.put("minecraft:redstone_block", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.REDSTONE_BLOCK).build(), new TexPathBlockIcon("blocks/redstone_block.png"), 0xffab1b09, false)
        });
        allTemplates.put("minecraft:quartz_ore", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.QUARTZ_ORE).build(), new TexPathBlockIcon("blocks/quartz_ore.png"), 0xffd9d1c8, false)
        });
        allTemplates.put("minecraft:hopper", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.HOPPER).setProperty("facing_direction", 0).setProperty("toggle_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/hopper.png"), 0xff3e3e3e, false)
        });
        allTemplates.put("minecraft:quartz_block", new BlockTemplate[]{
                new BlockTemplate("default", new Block.Builder(BlockType.QUARTZ_BLOCK).setProperty("chisel_type", "default").setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/quartz_block_default.png"), 0xffece9e2, false),
                new BlockTemplate("chiseled", new Block.Builder(BlockType.QUARTZ_BLOCK).setProperty("chisel_type", "chiseled").setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/quartz_block_chiseled.png"), 0xffe7e4db, false),
                new BlockTemplate("lines", new Block.Builder(BlockType.QUARTZ_BLOCK).setProperty("chisel_type", "lines").setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/quartz_block_lines.png"), 0xffe8e5dd, false),
                new BlockTemplate("default", new Block.Builder(BlockType.QUARTZ_BLOCK).setProperty("chisel_type", "smooth").setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/quartz_block_default.png"), 0xffe7e3db, false)
        });
        allTemplates.put("minecraft:quartz_stairs", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.QUARTZ_STAIRS).setProperty("weirdo_direction", 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/quartz_stairs.png"), 0xffece9e2, false)
        });
        allTemplates.put("minecraft:double_wooden_slab", new BlockTemplate[]{
                new BlockTemplate("oak", new Block.Builder(BlockType.DOUBLE_WOODEN_SLAB).setProperty("wood_type", "oak").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/planks_oak.png"), 0xb4907449, false),
                new BlockTemplate("spruce", new Block.Builder(BlockType.DOUBLE_WOODEN_SLAB).setProperty("wood_type", "spruce").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/planks_spruce.png"), 0xff5a3d0d, false),
                new BlockTemplate("birch", new Block.Builder(BlockType.DOUBLE_WOODEN_SLAB).setProperty("wood_type", "birch").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/planks_birch.png"), 0xb4dabd8d, false),
                new BlockTemplate("jungle", new Block.Builder(BlockType.DOUBLE_WOODEN_SLAB).setProperty("wood_type", "jungle").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/planks_jungle.png"), 0xb4Ba7d5d, false),
                new BlockTemplate("acacia", new Block.Builder(BlockType.DOUBLE_WOODEN_SLAB).setProperty("wood_type", "acacia").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/planks_acacia.png"), 0xb4934f39, false),
                new BlockTemplate("big_oak", new Block.Builder(BlockType.DOUBLE_WOODEN_SLAB).setProperty("wood_type", "dark_oak").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/planks_big_oak.png"), 0xb4907449, false)
        });
        allTemplates.put("minecraft:wooden_slab", new BlockTemplate[]{
                new BlockTemplate("oak", new Block.Builder(BlockType.WOODEN_SLAB).setProperty("wood_type", "oak").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/wooden_slab_oak.png"), 0xff907449, false),
                new BlockTemplate("spruce", new Block.Builder(BlockType.WOODEN_SLAB).setProperty("wood_type", "spruce").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/wooden_slab_spruce.png"), 0xff5a3d0d, false),
                new BlockTemplate("birch", new Block.Builder(BlockType.WOODEN_SLAB).setProperty("wood_type", "birch").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/wooden_slab_birch.png"), 0xffdabd8d, false),
                new BlockTemplate("jungle", new Block.Builder(BlockType.WOODEN_SLAB).setProperty("wood_type", "jungle").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/wooden_slab_jungle.png"), 0xffBa7d5d, false),
                new BlockTemplate("acacia", new Block.Builder(BlockType.WOODEN_SLAB).setProperty("wood_type", "acacia").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/wooden_slab_acacia.png"), 0xff934f39, false),
                new BlockTemplate("big_oak", new Block.Builder(BlockType.WOODEN_SLAB).setProperty("wood_type", "dark_oak").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/wooden_slab_big_oak.png"), 0xff907449, false)
        });
        allTemplates.put("minecraft:stained_hardened_clay", new BlockTemplate[]{
                new BlockTemplate("white", new Block.Builder(BlockType.STAINED_HARDENED_CLAY).setProperty("color", "white").build(), new TexPathBlockIcon("blocks/hardened_clay_stained_white.png"), 0xff836f64, false),
                new BlockTemplate("orange", new Block.Builder(BlockType.STAINED_HARDENED_CLAY).setProperty("color", "orange").build(), new TexPathBlockIcon("blocks/hardened_clay_stained_orange.png"), 0xff9d5021, false),
                new BlockTemplate("magenta", new Block.Builder(BlockType.STAINED_HARDENED_CLAY).setProperty("color", "magenta").build(), new TexPathBlockIcon("blocks/hardened_clay_stained_magenta.png"), 0xff915369, false),
                new BlockTemplate("light_blue", new Block.Builder(BlockType.STAINED_HARDENED_CLAY).setProperty("color", "light_blue").build(), new TexPathBlockIcon("blocks/hardened_clay_stained_light_blue.png"), 0xff706b87, false),
                new BlockTemplate("yellow", new Block.Builder(BlockType.STAINED_HARDENED_CLAY).setProperty("color", "yellow").build(), new TexPathBlockIcon("blocks/hardened_clay_stained_yellow.png"), 0xffb5801f, false),
                new BlockTemplate("lime", new Block.Builder(BlockType.STAINED_HARDENED_CLAY).setProperty("color", "lime").build(), new TexPathBlockIcon("blocks/hardened_clay_stained_lime.png"), 0xff617030, false),
                new BlockTemplate("pink", new Block.Builder(BlockType.STAINED_HARDENED_CLAY).setProperty("color", "pink").build(), new TexPathBlockIcon("blocks/hardened_clay_stained_pink.png"), 0xff9c4848, false),
                new BlockTemplate("gray", new Block.Builder(BlockType.STAINED_HARDENED_CLAY).setProperty("color", "gray").build(), new TexPathBlockIcon("blocks/hardened_clay_stained_gray.png"), 0xff392721, false),
                new BlockTemplate("silver", new Block.Builder(BlockType.STAINED_HARDENED_CLAY).setProperty("color", "silver").build(), new TexPathBlockIcon("blocks/hardened_clay_stained_silver.png"), 0xff81655b, false),
                new BlockTemplate("cyan", new Block.Builder(BlockType.STAINED_HARDENED_CLAY).setProperty("color", "cyan").build(), new TexPathBlockIcon("blocks/hardened_clay_stained_cyan.png"), 0xff565959, false),
                new BlockTemplate("purple", new Block.Builder(BlockType.STAINED_HARDENED_CLAY).setProperty("color", "purple").build(), new TexPathBlockIcon("blocks/hardened_clay_stained_purple.png"), 0xff744555, false),
                new BlockTemplate("blue", new Block.Builder(BlockType.STAINED_HARDENED_CLAY).setProperty("color", "blue").build(), new TexPathBlockIcon("blocks/hardened_clay_stained_blue.png"), 0xff463857, false),
                new BlockTemplate("brown", new Block.Builder(BlockType.STAINED_HARDENED_CLAY).setProperty("color", "brown").build(), new TexPathBlockIcon("blocks/hardened_clay_stained_brown.png"), 0xff492e1f, false),
                new BlockTemplate("green", new Block.Builder(BlockType.STAINED_HARDENED_CLAY).setProperty("color", "green").build(), new TexPathBlockIcon("blocks/hardened_clay_stained_green.png"), 0xff484f26, false),
                new BlockTemplate("red", new Block.Builder(BlockType.STAINED_HARDENED_CLAY).setProperty("color", "red").build(), new TexPathBlockIcon("blocks/hardened_clay_stained_red.png"), 0xffff382b, false),
                new BlockTemplate("black", new Block.Builder(BlockType.STAINED_HARDENED_CLAY).setProperty("color", "black").build(), new TexPathBlockIcon("blocks/hardened_clay_stained_black.png"), 0xff21120d, false)
        });
        allTemplates.put("minecraft:stained_glass_pane", new BlockTemplate[]{
                new BlockTemplate("white", new Block.Builder(BlockType.STAINED_GLASS_PANE).setProperty("color", "white").build(), new TexPathBlockIcon("blocks/glass.png"), 0x32141414, false),
                new BlockTemplate("orange", new Block.Builder(BlockType.STAINED_GLASS_PANE).setProperty("color", "orange").build(), new TexPathBlockIcon("blocks/glass.png"), 0x209d5021, false),
                new BlockTemplate("magenta", new Block.Builder(BlockType.STAINED_GLASS_PANE).setProperty("color", "magenta").build(), new TexPathBlockIcon("blocks/glass.png"), 0x20915369, false),
                new BlockTemplate("light_blue", new Block.Builder(BlockType.STAINED_GLASS_PANE).setProperty("color", "light_blue").build(), new TexPathBlockIcon("blocks/glass.png"), 0x20706b87, false),
                new BlockTemplate("yellow", new Block.Builder(BlockType.STAINED_GLASS_PANE).setProperty("color", "yellow").build(), new TexPathBlockIcon("blocks/glass.png"), 0x20b5801f, false),
                new BlockTemplate("lime", new Block.Builder(BlockType.STAINED_GLASS_PANE).setProperty("color", "lime").build(), new TexPathBlockIcon("blocks/glass.png"), 0x20617030, false),
                new BlockTemplate("pink", new Block.Builder(BlockType.STAINED_GLASS_PANE).setProperty("color", "pink").build(), new TexPathBlockIcon("blocks/glass.png"), 0x209c4848, false),
                new BlockTemplate("gray", new Block.Builder(BlockType.STAINED_GLASS_PANE).setProperty("color", "gray").build(), new TexPathBlockIcon("blocks/glass.png"), 0x20392721, false),
                new BlockTemplate("silver", new Block.Builder(BlockType.STAINED_GLASS_PANE).setProperty("color", "silver").build(), new TexPathBlockIcon("blocks/glass.png"), 0x2081655b, false),
                new BlockTemplate("cyan", new Block.Builder(BlockType.STAINED_GLASS_PANE).setProperty("color", "cyan").build(), new TexPathBlockIcon("blocks/glass.png"), 0x20565959, false),
                new BlockTemplate("purple", new Block.Builder(BlockType.STAINED_GLASS_PANE).setProperty("color", "purple").build(), new TexPathBlockIcon("blocks/glass.png"), 0x20744555, false),
                new BlockTemplate("blue", new Block.Builder(BlockType.STAINED_GLASS_PANE).setProperty("color", "blue").build(), new TexPathBlockIcon("blocks/glass.png"), 0x20463857, false),
                new BlockTemplate("brown", new Block.Builder(BlockType.STAINED_GLASS_PANE).setProperty("color", "brown").build(), new TexPathBlockIcon("blocks/glass.png"), 0x20492e1f, false),
                new BlockTemplate("green", new Block.Builder(BlockType.STAINED_GLASS_PANE).setProperty("color", "green").build(), new TexPathBlockIcon("blocks/glass.png"), 0x20484f26, false),
                new BlockTemplate("red", new Block.Builder(BlockType.STAINED_GLASS_PANE).setProperty("color", "red").build(), new TexPathBlockIcon("blocks/glass.png"), 0x20ff382b, false),
                new BlockTemplate("black", new Block.Builder(BlockType.STAINED_GLASS_PANE).setProperty("color", "black").build(), new TexPathBlockIcon("blocks/glass.png"), 0x2021120d, false)
        });
        allTemplates.put("minecraft:leaves2", new BlockTemplate[]{
                new BlockTemplate("acacia", new Block.Builder(BlockType.LEAVES2).setProperty("new_leaf_type", "acacia").setProperty("persistent_bit", (byte) 0).setProperty("update_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/leaves2_acacia.png"), 0xff2e780c, true),
                new BlockTemplate("big_oak", new Block.Builder(BlockType.LEAVES2).setProperty("new_leaf_type", "dark_oak").setProperty("persistent_bit", (byte) 0).setProperty("update_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/leaves2_big_oak.png"), 0xff878787, true)
        });
        allTemplates.put("minecraft:log2", new BlockTemplate[]{
                new BlockTemplate("acacia", new Block.Builder(BlockType.LOG2).setProperty("new_log_type", "acacia").setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/log2_acacia.png"), 0xff934f39, false),
                new BlockTemplate("big_oak", new Block.Builder(BlockType.LOG2).setProperty("new_log_type", "dark_oak").setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/log2_big_oak.png"), 0xff2d2213, false)
        });
        allTemplates.put("minecraft:acacia_stairs", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_STAIRS).setProperty("weirdo_direction", 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/acacia_stairs.png"), 0xff934f39, false)
        });
        allTemplates.put("minecraft:dark_oak_stairs", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_STAIRS).setProperty("weirdo_direction", 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/dark_oak_stairs.png"), 0xff2d2213, false)
        });
        allTemplates.put("minecraft:slime", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.SLIME).build(), new TexPathBlockIcon("blocks/slime.png"), 0xc880b672, false)
        });
        allTemplates.put("minecraft:iron_trapdoor", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.IRON_TRAPDOOR).setProperty("direction", 0).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/iron_trapdoor.png"), 0xb4cccccc, false)
        });
        allTemplates.put("minecraft:prismarine", new BlockTemplate[]{
                new BlockTemplate("rough", new Block.Builder(BlockType.PRISMARINE).setProperty("prismarine_block_type", "default").build(), new TexPathBlockIcon("blocks/prismarine_rough.png"), 0xff79Ad7e, false),
                new BlockTemplate("dark", new Block.Builder(BlockType.PRISMARINE).setProperty("prismarine_block_type", "dark").build(), new TexPathBlockIcon("blocks/prismarine_dark.png"), 0xFF34634e, false),
                new BlockTemplate("bricks", new Block.Builder(BlockType.PRISMARINE).setProperty("prismarine_block_type", "bricks").build(), new TexPathBlockIcon("blocks/prismarine_bricks.png"), 0xff59Ad7e, false)
        });
        allTemplates.put("minecraft:seaLantern", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.SEALANTERN).build(), new TexPathBlockIcon("blocks/seaLantern.png"), 0xffe0eae4, false)
        });
        allTemplates.put("minecraft:hay_block", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.HAY_BLOCK).setProperty("deprecated", 0).setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/hay_block.png"), 0xffa3870e, false)
        });
        allTemplates.put("minecraft:carpet", new BlockTemplate[]{
                new BlockTemplate("white", new Block.Builder(BlockType.CARPET).setProperty("color", "white").build(), new TexPathBlockIcon("blocks/carpet_white.png"), 0xffdddddd, false),
                new BlockTemplate("orange", new Block.Builder(BlockType.CARPET).setProperty("color", "orange").build(), new TexPathBlockIcon("blocks/carpet_orange.png"), 0xffdb7d3e, false),
                new BlockTemplate("magenta", new Block.Builder(BlockType.CARPET).setProperty("color", "magenta").build(), new TexPathBlockIcon("blocks/carpet_magenta.png"), 0xffb350bc, false),
                new BlockTemplate("light_blue", new Block.Builder(BlockType.CARPET).setProperty("color", "light_blue").build(), new TexPathBlockIcon("blocks/carpet_light_blue.png"), 0xff6a8ac9, false),
                new BlockTemplate("yellow", new Block.Builder(BlockType.CARPET).setProperty("color", "yellow").build(), new TexPathBlockIcon("blocks/carpet_yellow.png"), 0xffb1a627, false),
                new BlockTemplate("lime", new Block.Builder(BlockType.CARPET).setProperty("color", "lime").build(), new TexPathBlockIcon("blocks/carpet_lime.png"), 0xff41ae38, false),
                new BlockTemplate("pink", new Block.Builder(BlockType.CARPET).setProperty("color", "pink").build(), new TexPathBlockIcon("blocks/carpet_pink.png"), 0xffd08499, false),
                new BlockTemplate("gray", new Block.Builder(BlockType.CARPET).setProperty("color", "gray").build(), new TexPathBlockIcon("blocks/carpet_gray.png"), 0xff404040, false),
                new BlockTemplate("silver", new Block.Builder(BlockType.CARPET).setProperty("color", "silver").build(), new TexPathBlockIcon("blocks/carpet_silver.png"), 0xff9aa1a1, false),
                new BlockTemplate("cyan", new Block.Builder(BlockType.CARPET).setProperty("color", "cyan").build(), new TexPathBlockIcon("blocks/carpet_cyan.png"), 0xff2e6e89, false),
                new BlockTemplate("purple", new Block.Builder(BlockType.CARPET).setProperty("color", "purple").build(), new TexPathBlockIcon("blocks/carpet_purple.png"), 0xff7e3db5, false),
                new BlockTemplate("blue", new Block.Builder(BlockType.CARPET).setProperty("color", "blue").build(), new TexPathBlockIcon("blocks/carpet_blue.png"), 0xff2e388d, false),
                new BlockTemplate("brown", new Block.Builder(BlockType.CARPET).setProperty("color", "brown").build(), new TexPathBlockIcon("blocks/carpet_brown.png"), 0xff4f321f, false),
                new BlockTemplate("green", new Block.Builder(BlockType.CARPET).setProperty("color", "green").build(), new TexPathBlockIcon("blocks/carpet_green.png"), 0xff35461b, false),
                new BlockTemplate("red", new Block.Builder(BlockType.CARPET).setProperty("color", "red").build(), new TexPathBlockIcon("blocks/carpet_red.png"), 0xff963430, false),
                new BlockTemplate("black", new Block.Builder(BlockType.CARPET).setProperty("color", "black").build(), new TexPathBlockIcon("blocks/carpet_black.png"), 0xff191616, false)
        });
        allTemplates.put("minecraft:hardened_clay", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.HARDENED_CLAY).build(), new TexPathBlockIcon("blocks/hardened_clay.png"), 0xff5d3828, false)
        });
        allTemplates.put("minecraft:coal_block", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.COAL_BLOCK).build(), new TexPathBlockIcon("blocks/coal_block.png"), 0xff111111, false)
        });
        allTemplates.put("minecraft:packed_ice", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.PACKED_ICE).build(), new TexPathBlockIcon("blocks/packed_ice.png"), 0xff97b3e4, false)
        });
        allTemplates.put("minecraft:double_plant", new BlockTemplate[]{
                new BlockTemplate("sunflower", new Block.Builder(BlockType.DOUBLE_PLANT).setProperty("double_plant_type", "sunflower").setProperty("upper_block_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/double_plant_sunflower.png"), 0xb4d28219, false),
                new BlockTemplate("syringa", new Block.Builder(BlockType.DOUBLE_PLANT).setProperty("double_plant_type", "syringa").setProperty("upper_block_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/double_plant_syringa.png"), 0xb4dec0e2, false),
                new BlockTemplate("grass", new Block.Builder(BlockType.DOUBLE_PLANT).setProperty("double_plant_type", "grass").setProperty("upper_block_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/double_plant_grass.png"), 0xb4334e2c, false),
                new BlockTemplate("fern", new Block.Builder(BlockType.DOUBLE_PLANT).setProperty("double_plant_type", "fern").setProperty("upper_block_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/double_plant_fern.png"), 0xb43d5d34, false),
                new BlockTemplate("rose", new Block.Builder(BlockType.DOUBLE_PLANT).setProperty("double_plant_type", "rose").setProperty("upper_block_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/double_plant_rose.png"), 0xb4d10609, false),
                new BlockTemplate("paeonia", new Block.Builder(BlockType.DOUBLE_PLANT).setProperty("double_plant_type", "paeonia").setProperty("upper_block_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/double_plant_paeonia.png"), 0xb4d6c1df, false)
        });
        allTemplates.put("minecraft:banner", new BlockTemplate[]{

        });
        allTemplates.put("minecraft:daylight_detector_inverted", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.DAYLIGHT_DETECTOR_INVERTED).setProperty("redstone_signal", 0).build(), new TexPathBlockIcon("blocks/daylight_detector_inverted.png"), 0xffd8c9b5, false)
        });
        allTemplates.put("minecraft:red_sandstone", new BlockTemplate[]{
                new BlockTemplate("default", new Block.Builder(BlockType.RED_SANDSTONE).setProperty("sand_stone_type", "default").build(), new TexPathBlockIcon("blocks/red_sandstone_default.png"), 0xffaa561e, false),
                new BlockTemplate("chiseled", new Block.Builder(BlockType.RED_SANDSTONE).setProperty("sand_stone_type", "heiroglyphs").build(), new TexPathBlockIcon("blocks/red_sandstone_chiseled.png"), 0xffa8551e, false),
                new BlockTemplate("smooth", new Block.Builder(BlockType.RED_SANDSTONE).setProperty("sand_stone_type", "cut").build(), new TexPathBlockIcon("blocks/red_sandstone_smooth.png"), 0xffcc5e16, false)
        });
        allTemplates.put("minecraft:red_sandstone_stairs", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.RED_SANDSTONE_STAIRS).setProperty("weirdo_direction", 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/red_sandstone_stairs.png"), 0xffaa561e, false)
        });
        allTemplates.put("minecraft:double_stone_slab2", new BlockTemplate[]{
                new BlockTemplate("red_sandstone", new Block.Builder(BlockType.DOUBLE_STONE_SLAB2).setProperty("stone_slab_type_2", "red_sandstone").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/double_stone_slab2_red_sandstone.png"), 0xffaa561e, false),
                new BlockTemplate("purpur", new Block.Builder(BlockType.DOUBLE_STONE_SLAB2).setProperty("stone_slab_type_2", "purpur").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/double_stone_slab2_purpur.png"), 0xffa072a0, false)
        });
        allTemplates.put("minecraft:stone_slab2", new BlockTemplate[]{
                new BlockTemplate("red_sandstone", new Block.Builder(BlockType.STONE_SLAB2).setProperty("stone_slab_type_2", "red_sandstone").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/stone_slab2_red_sandstone.png"), 0xffaa561e, false),
                new BlockTemplate("purpur", new Block.Builder(BlockType.STONE_SLAB2).setProperty("stone_slab_type_2", "purpur").setProperty("top_slot_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/stone_slab2_purpur.png"), 0xffa072a0, false)
        });
        allTemplates.put("minecraft:spruce_fence_gate", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_FENCE_GATE).setProperty("direction", 0).setProperty("in_wall_bit", (byte) 0).setProperty("open_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/spruce_fence_gate.png"), 0x8f5a3d0d, false)
        });
        allTemplates.put("minecraft:birch_fence_gate", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_FENCE_GATE).setProperty("direction", 0).setProperty("in_wall_bit", (byte) 0).setProperty("open_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/birch_fence_gate.png"), 0x8fdabd8d, false)
        });
        allTemplates.put("minecraft:jungle_fence_gate", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_FENCE_GATE).setProperty("direction", 0).setProperty("in_wall_bit", (byte) 0).setProperty("open_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/jungle_fence_gate.png"), 0x8fBa7d5d, false)
        });
        allTemplates.put("minecraft:dark_oak_fence_gate", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_FENCE_GATE).setProperty("direction", 0).setProperty("in_wall_bit", (byte) 0).setProperty("open_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/dark_oak_fence_gate.png"), 0x8f2d2213, false)
        });
        allTemplates.put("minecraft:acacia_fence_gate", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_FENCE_GATE).setProperty("direction", 0).setProperty("in_wall_bit", (byte) 0).setProperty("open_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/acacia_fence_gate.png"), 0x8f934f39, false)
        });
        allTemplates.put("minecraft:repeating_command_block", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.REPEATING_COMMAND_BLOCK).setProperty("facing_direction", 0).setProperty("conditional_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.REPEATING_COMMAND_BLOCK).setProperty("facing_direction", 1).setProperty("conditional_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.REPEATING_COMMAND_BLOCK).setProperty("facing_direction", 2).setProperty("conditional_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.REPEATING_COMMAND_BLOCK).setProperty("facing_direction", 3).setProperty("conditional_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.REPEATING_COMMAND_BLOCK).setProperty("facing_direction", 4).setProperty("conditional_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.REPEATING_COMMAND_BLOCK).setProperty("facing_direction", 5).setProperty("conditional_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.REPEATING_COMMAND_BLOCK).setProperty("facing_direction", 0).setProperty("conditional_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.REPEATING_COMMAND_BLOCK).setProperty("facing_direction", 0).setProperty("conditional_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.REPEATING_COMMAND_BLOCK).setProperty("facing_direction", 0).setProperty("conditional_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.REPEATING_COMMAND_BLOCK).setProperty("facing_direction", 1).setProperty("conditional_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.REPEATING_COMMAND_BLOCK).setProperty("facing_direction", 2).setProperty("conditional_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.REPEATING_COMMAND_BLOCK).setProperty("facing_direction", 3).setProperty("conditional_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.REPEATING_COMMAND_BLOCK).setProperty("facing_direction", 4).setProperty("conditional_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.REPEATING_COMMAND_BLOCK).setProperty("facing_direction", 5).setProperty("conditional_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.REPEATING_COMMAND_BLOCK).setProperty("facing_direction", 0).setProperty("conditional_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.REPEATING_COMMAND_BLOCK).setProperty("facing_direction", 0).setProperty("conditional_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false)
        });
        allTemplates.put("minecraft:chain_command_block", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.CHAIN_COMMAND_BLOCK).setProperty("facing_direction", 0).setProperty("conditional_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CHAIN_COMMAND_BLOCK).setProperty("facing_direction", 1).setProperty("conditional_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CHAIN_COMMAND_BLOCK).setProperty("facing_direction", 2).setProperty("conditional_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CHAIN_COMMAND_BLOCK).setProperty("facing_direction", 3).setProperty("conditional_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CHAIN_COMMAND_BLOCK).setProperty("facing_direction", 4).setProperty("conditional_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CHAIN_COMMAND_BLOCK).setProperty("facing_direction", 5).setProperty("conditional_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CHAIN_COMMAND_BLOCK).setProperty("facing_direction", 0).setProperty("conditional_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CHAIN_COMMAND_BLOCK).setProperty("facing_direction", 0).setProperty("conditional_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CHAIN_COMMAND_BLOCK).setProperty("facing_direction", 0).setProperty("conditional_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CHAIN_COMMAND_BLOCK).setProperty("facing_direction", 1).setProperty("conditional_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CHAIN_COMMAND_BLOCK).setProperty("facing_direction", 2).setProperty("conditional_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CHAIN_COMMAND_BLOCK).setProperty("facing_direction", 3).setProperty("conditional_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CHAIN_COMMAND_BLOCK).setProperty("facing_direction", 4).setProperty("conditional_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CHAIN_COMMAND_BLOCK).setProperty("facing_direction", 5).setProperty("conditional_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CHAIN_COMMAND_BLOCK).setProperty("facing_direction", 0).setProperty("conditional_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CHAIN_COMMAND_BLOCK).setProperty("facing_direction", 0).setProperty("conditional_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/command_block.png"), 0x00000000, false)
        });
        allTemplates.put("minecraft:hard_glass_pane", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.HARD_GLASS_PANE).build(), new TexPathBlockIcon("blocks/glass_pane_top.png"), 0x00000000, false)
        });
        allTemplates.put("minecraft:hard_stained_glass_pane", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.HARD_STAINED_GLASS_PANE).setProperty("color", "white").build(), new TexPathBlockIcon("blocks/glass_pane_top.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.HARD_STAINED_GLASS_PANE).setProperty("color", "orange").build(), new TexPathBlockIcon("blocks/glass_pane_top.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.HARD_STAINED_GLASS_PANE).setProperty("color", "magenta").build(), new TexPathBlockIcon("blocks/glass_pane_top.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.HARD_STAINED_GLASS_PANE).setProperty("color", "light_blue").build(), new TexPathBlockIcon("blocks/glass_pane_top.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.HARD_STAINED_GLASS_PANE).setProperty("color", "yellow").build(), new TexPathBlockIcon("blocks/glass_pane_top.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.HARD_STAINED_GLASS_PANE).setProperty("color", "lime").build(), new TexPathBlockIcon("blocks/glass_pane_top.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.HARD_STAINED_GLASS_PANE).setProperty("color", "pink").build(), new TexPathBlockIcon("blocks/glass_pane_top.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.HARD_STAINED_GLASS_PANE).setProperty("color", "gray").build(), new TexPathBlockIcon("blocks/glass_pane_top.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.HARD_STAINED_GLASS_PANE).setProperty("color", "silver").build(), new TexPathBlockIcon("blocks/glass_pane_top.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.HARD_STAINED_GLASS_PANE).setProperty("color", "cyan").build(), new TexPathBlockIcon("blocks/glass_pane_top.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.HARD_STAINED_GLASS_PANE).setProperty("color", "purple").build(), new TexPathBlockIcon("blocks/glass_pane_top.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.HARD_STAINED_GLASS_PANE).setProperty("color", "blue").build(), new TexPathBlockIcon("blocks/glass_pane_top.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.HARD_STAINED_GLASS_PANE).setProperty("color", "brown").build(), new TexPathBlockIcon("blocks/glass_pane_top.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.HARD_STAINED_GLASS_PANE).setProperty("color", "green").build(), new TexPathBlockIcon("blocks/glass_pane_top.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.HARD_STAINED_GLASS_PANE).setProperty("color", "red").build(), new TexPathBlockIcon("blocks/glass_pane_top.png"), 0x00000000, false),
                new BlockTemplate(null, new Block.Builder(BlockType.HARD_STAINED_GLASS_PANE).setProperty("color", "black").build(), new TexPathBlockIcon("blocks/glass_pane_top.png"), 0x00000000, false)
        });
        allTemplates.put("minecraft:chemical_heat", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.CHEMICAL_HEAT).build(), new TexPathBlockIcon("blocks/fire_0.png"), 0x00000000, false)
        });
        allTemplates.put("minecraft:spruce_door", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_DOOR).setProperty("direction", 0).setProperty("door_hinge_bit", (byte) 0).setProperty("open_bit", (byte) 0).setProperty("upper_block_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/spruce_door.png"), 0xff5a3d0d, false)
        });
        allTemplates.put("minecraft:birch_door", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_DOOR).setProperty("direction", 0).setProperty("door_hinge_bit", (byte) 0).setProperty("open_bit", (byte) 0).setProperty("upper_block_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/birch_door.png"), 0xffdabd8d, false)
        });
        allTemplates.put("minecraft:jungle_door", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_DOOR).setProperty("direction", 0).setProperty("door_hinge_bit", (byte) 0).setProperty("open_bit", (byte) 0).setProperty("upper_block_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/jungle_door.png"), 0xffBa7d5d, false)
        });
        allTemplates.put("minecraft:acacia_door", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_DOOR).setProperty("direction", 0).setProperty("door_hinge_bit", (byte) 0).setProperty("open_bit", (byte) 0).setProperty("upper_block_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/acacia_door.png"), 0xff934f39, false)
        });
        allTemplates.put("minecraft:dark_oak_door", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_DOOR).setProperty("direction", 0).setProperty("door_hinge_bit", (byte) 0).setProperty("open_bit", (byte) 0).setProperty("upper_block_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/dark_oak_door.png"), 0xff2d2213, false)
        });
        allTemplates.put("minecraft:grass_path", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.GRASS_PATH).build(), new TexPathBlockIcon("blocks/grass_path.png"), 0x46a0a0a0, false)
        });
        allTemplates.put("minecraft:frame", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.FRAME).setProperty("facing_direction", 5).setProperty("item_frame_map_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/frame.png"), 0xa04f3e4f, false)
        });
        allTemplates.put("minecraft:chorus_flower", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.CHORUS_FLOWER).setProperty("age", 0).build(), new TexPathBlockIcon("blocks/chorus_flower.png"), 0xa0c3b6c8, false)
        });
        allTemplates.put("minecraft:purpur_block", new BlockTemplate[]{
                new BlockTemplate("default", new Block.Builder(BlockType.PURPUR_BLOCK).setProperty("chisel_type", "default").setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/purpur_block_default.png"), 0xc095c0ff, false),
                new BlockTemplate("chiseled", new Block.Builder(BlockType.PURPUR_BLOCK).setProperty("chisel_type", "chiseled").setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/purpur_block_chiseled.png"), 0xc095c0ff, false),
                new BlockTemplate("lines", new Block.Builder(BlockType.PURPUR_BLOCK).setProperty("chisel_type", "lines").setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/purpur_block_lines.png"), 0xc095c0ff, false),
                new BlockTemplate("default", new Block.Builder(BlockType.PURPUR_BLOCK).setProperty("chisel_type", "smooth").setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/purpur_block_default.png"), 0xc095c0ff, false)
        });
        allTemplates.put("minecraft:purpur_stairs", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.PURPUR_STAIRS).setProperty("weirdo_direction", 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/purpur_stairs.png"), 0xc095c0ff, false)
        });
        allTemplates.put("minecraft:shulker_box", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.SHULKER_BOX).setProperty("color", "white").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffffff, false),
                new BlockTemplate("white", new Block.Builder(BlockType.SHULKER_BOX).setProperty("color", "white").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffffff, false),
                new BlockTemplate("orange", new Block.Builder(BlockType.SHULKER_BOX).setProperty("color", "orange").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffd030, false),
                new BlockTemplate("magenta", new Block.Builder(BlockType.SHULKER_BOX).setProperty("color", "magenta").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffef007f, false),
                new BlockTemplate("light_blue", new Block.Builder(BlockType.SHULKER_BOX).setProperty("color", "light_blue").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5588ff, false),
                new BlockTemplate("yellow", new Block.Builder(BlockType.SHULKER_BOX).setProperty("color", "yellow").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffff40, false),
                new BlockTemplate("lime", new Block.Builder(BlockType.SHULKER_BOX).setProperty("color", "lime").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff0db60e, false),
                new BlockTemplate("pink", new Block.Builder(BlockType.SHULKER_BOX).setProperty("color", "pink").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff6076, false),
                new BlockTemplate("gray", new Block.Builder(BlockType.SHULKER_BOX).setProperty("color", "gray").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff565656, false),
                new BlockTemplate("silver", new Block.Builder(BlockType.SHULKER_BOX).setProperty("color", "silver").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffa6a6a6, false),
                new BlockTemplate("cyan", new Block.Builder(BlockType.SHULKER_BOX).setProperty("color", "cyan").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff0d5656, false),
                new BlockTemplate("purple", new Block.Builder(BlockType.SHULKER_BOX).setProperty("color", "purple").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff560d56, false),
                new BlockTemplate("blue", new Block.Builder(BlockType.SHULKER_BOX).setProperty("color", "blue").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff0d0e56, false),
                new BlockTemplate("brown", new Block.Builder(BlockType.SHULKER_BOX).setProperty("color", "brown").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff804530, false),
                new BlockTemplate("green", new Block.Builder(BlockType.SHULKER_BOX).setProperty("color", "green").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff0d560e, false),
                new BlockTemplate("red", new Block.Builder(BlockType.SHULKER_BOX).setProperty("color", "red").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff2020, false),
                new BlockTemplate("black", new Block.Builder(BlockType.SHULKER_BOX).setProperty("color", "black").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff000000, false)
        });
        allTemplates.put("minecraft:end_bricks", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.END_BRICKS).build(), new TexPathBlockIcon("blocks/end_bricks.png"), 0xffe7f2af, false)
        });
        allTemplates.put("minecraft:end_rod", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.END_ROD).setProperty("facing_direction", 0).build(), new TexPathBlockIcon("blocks/end_rod.png"), 0xff6e6e6e, true)
        });
        allTemplates.put("minecraft:end_gateway", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.END_GATEWAY).build(), new TexPathBlockIcon("blocks/end_gateway.png"), 0xff171c27, false)
        });
        allTemplates.put("minecraft:210", new BlockTemplate[]{

        });
        allTemplates.put("minecraft:211", new BlockTemplate[]{

        });
        allTemplates.put("minecraft:212", new BlockTemplate[]{

        });
        allTemplates.put("minecraft:magma", new BlockTemplate[]{
                new BlockTemplate("default", new Block.Builder(BlockType.MAGMA).build(), new TexPathBlockIcon("blocks/quartz_block_default.png"), 0xffc45a12, false)
        });
        allTemplates.put("minecraft:nether_wart_block", new BlockTemplate[]{
                new BlockTemplate("default", new Block.Builder(BlockType.NETHER_WART_BLOCK).build(), new TexPathBlockIcon("blocks/quartz_block_default.png"), 0xffbf2030, false)
        });
        allTemplates.put("minecraft:red_nether_brick", new BlockTemplate[]{
                new BlockTemplate("default", new Block.Builder(BlockType.RED_NETHER_BRICK).build(), new TexPathBlockIcon("blocks/quartz_block_default.png"), 0xff7f1020, false)
        });
        allTemplates.put("minecraft:bone_block", new BlockTemplate[]{
                new BlockTemplate("default", new Block.Builder(BlockType.BONE_BLOCK).setProperty("deprecated", 0).setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/quartz_block_default.png"), 0xffefe5d2, false)
        });
        allTemplates.put("minecraft:purple_glazed_terracotta", new BlockTemplate[]{
                new BlockTemplate("purple", new Block.Builder(BlockType.PURPLE_GLAZED_TERRACOTTA).setProperty("facing_direction", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff762d76, false),
                new BlockTemplate("purple", new Block.Builder(BlockType.PURPLE_GLAZED_TERRACOTTA).setProperty("facing_direction", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff762d76, false),
                new BlockTemplate("purple", new Block.Builder(BlockType.PURPLE_GLAZED_TERRACOTTA).setProperty("facing_direction", 4).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff762d76, false),
                new BlockTemplate("purple", new Block.Builder(BlockType.PURPLE_GLAZED_TERRACOTTA).setProperty("facing_direction", 5).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff762d76, false)
        });
        allTemplates.put("minecraft:white_glazed_terracotta", new BlockTemplate[]{
                new BlockTemplate("white", new Block.Builder(BlockType.WHITE_GLAZED_TERRACOTTA).setProperty("facing_direction", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffffff, false),
                new BlockTemplate("white", new Block.Builder(BlockType.WHITE_GLAZED_TERRACOTTA).setProperty("facing_direction", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffffff, false),
                new BlockTemplate("white", new Block.Builder(BlockType.WHITE_GLAZED_TERRACOTTA).setProperty("facing_direction", 4).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffffff, false),
                new BlockTemplate("white", new Block.Builder(BlockType.WHITE_GLAZED_TERRACOTTA).setProperty("facing_direction", 5).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffffff, false)
        });
        allTemplates.put("minecraft:orange_glazed_terracotta", new BlockTemplate[]{
                new BlockTemplate("orange", new Block.Builder(BlockType.ORANGE_GLAZED_TERRACOTTA).setProperty("facing_direction", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff8030, false),
                new BlockTemplate("orange", new Block.Builder(BlockType.ORANGE_GLAZED_TERRACOTTA).setProperty("facing_direction", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff8030, false),
                new BlockTemplate("orange", new Block.Builder(BlockType.ORANGE_GLAZED_TERRACOTTA).setProperty("facing_direction", 4).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff8030, false),
                new BlockTemplate("orange", new Block.Builder(BlockType.ORANGE_GLAZED_TERRACOTTA).setProperty("facing_direction", 5).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff8030, false)
        });
        allTemplates.put("minecraft:magenta_glazed_terracotta", new BlockTemplate[]{
                new BlockTemplate("magenta", new Block.Builder(BlockType.MAGENTA_GLAZED_TERRACOTTA).setProperty("facing_direction", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff108f, false),
                new BlockTemplate("magenta", new Block.Builder(BlockType.MAGENTA_GLAZED_TERRACOTTA).setProperty("facing_direction", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff108f, false),
                new BlockTemplate("magenta", new Block.Builder(BlockType.MAGENTA_GLAZED_TERRACOTTA).setProperty("facing_direction", 4).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff108f, false),
                new BlockTemplate("magenta", new Block.Builder(BlockType.MAGENTA_GLAZED_TERRACOTTA).setProperty("facing_direction", 5).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff108f, false)
        });
        allTemplates.put("minecraft:light_blue_glazed_terracotta", new BlockTemplate[]{
                new BlockTemplate("light_blue", new Block.Builder(BlockType.LIGHT_BLUE_GLAZED_TERRACOTTA).setProperty("facing_direction", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff75a8ff, false),
                new BlockTemplate("light_blue", new Block.Builder(BlockType.LIGHT_BLUE_GLAZED_TERRACOTTA).setProperty("facing_direction", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff75a8ff, false),
                new BlockTemplate("light_blue", new Block.Builder(BlockType.LIGHT_BLUE_GLAZED_TERRACOTTA).setProperty("facing_direction", 4).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff75a8ff, false),
                new BlockTemplate("light_blue", new Block.Builder(BlockType.LIGHT_BLUE_GLAZED_TERRACOTTA).setProperty("facing_direction", 5).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff75a8ff, false)
        });
        allTemplates.put("minecraft:yellow_glazed_terracotta", new BlockTemplate[]{
                new BlockTemplate("yellow", new Block.Builder(BlockType.YELLOW_GLAZED_TERRACOTTA).setProperty("facing_direction", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffff60, false),
                new BlockTemplate("yellow", new Block.Builder(BlockType.YELLOW_GLAZED_TERRACOTTA).setProperty("facing_direction", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffff60, false),
                new BlockTemplate("yellow", new Block.Builder(BlockType.YELLOW_GLAZED_TERRACOTTA).setProperty("facing_direction", 4).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffff60, false),
                new BlockTemplate("yellow", new Block.Builder(BlockType.YELLOW_GLAZED_TERRACOTTA).setProperty("facing_direction", 5).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffff60, false)
        });
        allTemplates.put("minecraft:lime_glazed_terracotta", new BlockTemplate[]{
                new BlockTemplate("lime", new Block.Builder(BlockType.LIME_GLAZED_TERRACOTTA).setProperty("facing_direction", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2dd62e, false),
                new BlockTemplate("lime", new Block.Builder(BlockType.LIME_GLAZED_TERRACOTTA).setProperty("facing_direction", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2dd62e, false),
                new BlockTemplate("lime", new Block.Builder(BlockType.LIME_GLAZED_TERRACOTTA).setProperty("facing_direction", 4).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2dd62e, false),
                new BlockTemplate("lime", new Block.Builder(BlockType.LIME_GLAZED_TERRACOTTA).setProperty("facing_direction", 5).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2dd62e, false)
        });
        allTemplates.put("minecraft:pink_glazed_terracotta", new BlockTemplate[]{
                new BlockTemplate("pink", new Block.Builder(BlockType.PINK_GLAZED_TERRACOTTA).setProperty("facing_direction", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff8096, false),
                new BlockTemplate("pink", new Block.Builder(BlockType.PINK_GLAZED_TERRACOTTA).setProperty("facing_direction", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff8096, false),
                new BlockTemplate("pink", new Block.Builder(BlockType.PINK_GLAZED_TERRACOTTA).setProperty("facing_direction", 4).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff8096, false),
                new BlockTemplate("pink", new Block.Builder(BlockType.PINK_GLAZED_TERRACOTTA).setProperty("facing_direction", 5).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff8096, false)
        });
        allTemplates.put("minecraft:gray_glazed_terracotta", new BlockTemplate[]{
                new BlockTemplate("gray", new Block.Builder(BlockType.GRAY_GLAZED_TERRACOTTA).setProperty("facing_direction", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff767676, false),
                new BlockTemplate("gray", new Block.Builder(BlockType.GRAY_GLAZED_TERRACOTTA).setProperty("facing_direction", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff767676, false),
                new BlockTemplate("gray", new Block.Builder(BlockType.GRAY_GLAZED_TERRACOTTA).setProperty("facing_direction", 4).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff767676, false),
                new BlockTemplate("gray", new Block.Builder(BlockType.GRAY_GLAZED_TERRACOTTA).setProperty("facing_direction", 5).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff767676, false)
        });
        allTemplates.put("minecraft:silver_glazed_terracotta", new BlockTemplate[]{
                new BlockTemplate("silver", new Block.Builder(BlockType.SILVER_GLAZED_TERRACOTTA).setProperty("facing_direction", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffc6c6c6, false),
                new BlockTemplate("silver", new Block.Builder(BlockType.SILVER_GLAZED_TERRACOTTA).setProperty("facing_direction", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffc6c6c6, false),
                new BlockTemplate("silver", new Block.Builder(BlockType.SILVER_GLAZED_TERRACOTTA).setProperty("facing_direction", 4).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffc6c6c6, false),
                new BlockTemplate("silver", new Block.Builder(BlockType.SILVER_GLAZED_TERRACOTTA).setProperty("facing_direction", 5).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffc6c6c6, false)
        });
        allTemplates.put("minecraft:cyan_glazed_terracotta", new BlockTemplate[]{
                new BlockTemplate("cyan", new Block.Builder(BlockType.CYAN_GLAZED_TERRACOTTA).setProperty("facing_direction", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d7676, false),
                new BlockTemplate("cyan", new Block.Builder(BlockType.CYAN_GLAZED_TERRACOTTA).setProperty("facing_direction", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d7676, false),
                new BlockTemplate("cyan", new Block.Builder(BlockType.CYAN_GLAZED_TERRACOTTA).setProperty("facing_direction", 4).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d7676, false),
                new BlockTemplate("cyan", new Block.Builder(BlockType.CYAN_GLAZED_TERRACOTTA).setProperty("facing_direction", 5).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d7676, false)
        });
        allTemplates.put("minecraft:230", new BlockTemplate[]{

        });
        allTemplates.put("minecraft:blue_glazed_terracotta", new BlockTemplate[]{
                new BlockTemplate("blue", new Block.Builder(BlockType.BLUE_GLAZED_TERRACOTTA).setProperty("facing_direction", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2e76, false),
                new BlockTemplate("blue", new Block.Builder(BlockType.BLUE_GLAZED_TERRACOTTA).setProperty("facing_direction", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2e76, false),
                new BlockTemplate("blue", new Block.Builder(BlockType.BLUE_GLAZED_TERRACOTTA).setProperty("facing_direction", 4).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2e76, false),
                new BlockTemplate("blue", new Block.Builder(BlockType.BLUE_GLAZED_TERRACOTTA).setProperty("facing_direction", 5).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2e76, false)
        });
        allTemplates.put("minecraft:brown_glazed_terracotta", new BlockTemplate[]{
                new BlockTemplate("brown", new Block.Builder(BlockType.BROWN_GLAZED_TERRACOTTA).setProperty("facing_direction", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffa06550, false),
                new BlockTemplate("brown", new Block.Builder(BlockType.BROWN_GLAZED_TERRACOTTA).setProperty("facing_direction", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffa06550, false),
                new BlockTemplate("brown", new Block.Builder(BlockType.BROWN_GLAZED_TERRACOTTA).setProperty("facing_direction", 4).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffa06550, false),
                new BlockTemplate("brown", new Block.Builder(BlockType.BROWN_GLAZED_TERRACOTTA).setProperty("facing_direction", 5).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffa06550, false)
        });
        allTemplates.put("minecraft:green_glazed_terracotta", new BlockTemplate[]{
                new BlockTemplate("green", new Block.Builder(BlockType.GREEN_GLAZED_TERRACOTTA).setProperty("facing_direction", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d762e, false),
                new BlockTemplate("green", new Block.Builder(BlockType.GREEN_GLAZED_TERRACOTTA).setProperty("facing_direction", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d762e, false),
                new BlockTemplate("green", new Block.Builder(BlockType.GREEN_GLAZED_TERRACOTTA).setProperty("facing_direction", 4).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d762e, false),
                new BlockTemplate("green", new Block.Builder(BlockType.GREEN_GLAZED_TERRACOTTA).setProperty("facing_direction", 5).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d762e, false)
        });
        allTemplates.put("minecraft:red_glazed_terracotta", new BlockTemplate[]{
                new BlockTemplate("red", new Block.Builder(BlockType.RED_GLAZED_TERRACOTTA).setProperty("facing_direction", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff3030, false),
                new BlockTemplate("red", new Block.Builder(BlockType.RED_GLAZED_TERRACOTTA).setProperty("facing_direction", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff3030, false),
                new BlockTemplate("red", new Block.Builder(BlockType.RED_GLAZED_TERRACOTTA).setProperty("facing_direction", 4).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff3030, false),
                new BlockTemplate("red", new Block.Builder(BlockType.RED_GLAZED_TERRACOTTA).setProperty("facing_direction", 5).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff3030, false)
        });
        allTemplates.put("minecraft:black_glazed_terracotta", new BlockTemplate[]{
                new BlockTemplate("black", new Block.Builder(BlockType.BLACK_GLAZED_TERRACOTTA).setProperty("facing_direction", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff050505, false),
                new BlockTemplate("black", new Block.Builder(BlockType.BLACK_GLAZED_TERRACOTTA).setProperty("facing_direction", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff050505, false),
                new BlockTemplate("black", new Block.Builder(BlockType.BLACK_GLAZED_TERRACOTTA).setProperty("facing_direction", 4).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff050505, false),
                new BlockTemplate("black", new Block.Builder(BlockType.BLACK_GLAZED_TERRACOTTA).setProperty("facing_direction", 5).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff050505, false)
        });
        allTemplates.put("minecraft:concrete", new BlockTemplate[]{
                new BlockTemplate("orange", new Block.Builder(BlockType.CONCRETE).setProperty("color", "white").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffffff, false),
                new BlockTemplate("orange", new Block.Builder(BlockType.CONCRETE).setProperty("color", "orange").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffd030, false),
                new BlockTemplate("magenta", new Block.Builder(BlockType.CONCRETE).setProperty("color", "magenta").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffef007f, false),
                new BlockTemplate("light_blue", new Block.Builder(BlockType.CONCRETE).setProperty("color", "light_blue").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5588ff, false),
                new BlockTemplate("yellow", new Block.Builder(BlockType.CONCRETE).setProperty("color", "yellow").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffff40, false),
                new BlockTemplate("lime", new Block.Builder(BlockType.CONCRETE).setProperty("color", "lime").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff0db60e, false),
                new BlockTemplate("pink", new Block.Builder(BlockType.CONCRETE).setProperty("color", "pink").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff6076, false),
                new BlockTemplate("gray", new Block.Builder(BlockType.CONCRETE).setProperty("color", "gray").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff565656, false),
                new BlockTemplate("silver", new Block.Builder(BlockType.CONCRETE).setProperty("color", "silver").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffa6a6a6, false),
                new BlockTemplate("cyan", new Block.Builder(BlockType.CONCRETE).setProperty("color", "cyan").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff0d5656, false),
                new BlockTemplate("purple", new Block.Builder(BlockType.CONCRETE).setProperty("color", "purple").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff560d56, false),
                new BlockTemplate("blue", new Block.Builder(BlockType.CONCRETE).setProperty("color", "blue").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff0d0e56, false),
                new BlockTemplate("brown", new Block.Builder(BlockType.CONCRETE).setProperty("color", "brown").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff804530, false),
                new BlockTemplate("green", new Block.Builder(BlockType.CONCRETE).setProperty("color", "green").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff0d560e, false),
                new BlockTemplate("red", new Block.Builder(BlockType.CONCRETE).setProperty("color", "red").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff2020, false),
                new BlockTemplate("black", new Block.Builder(BlockType.CONCRETE).setProperty("color", "black").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff000000, false)
        });
        allTemplates.put("minecraft:concretePowder", new BlockTemplate[]{
                new BlockTemplate("white", new Block.Builder(BlockType.CONCRETEPOWDER).setProperty("color", "white").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffffff, false),
                new BlockTemplate("orange", new Block.Builder(BlockType.CONCRETEPOWDER).setProperty("color", "orange").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffd030, false),
                new BlockTemplate("magenta", new Block.Builder(BlockType.CONCRETEPOWDER).setProperty("color", "magenta").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffef007f, false),
                new BlockTemplate("light_blue", new Block.Builder(BlockType.CONCRETEPOWDER).setProperty("color", "light_blue").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5588ff, false),
                new BlockTemplate("yellow", new Block.Builder(BlockType.CONCRETEPOWDER).setProperty("color", "yellow").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffff40, false),
                new BlockTemplate("lime", new Block.Builder(BlockType.CONCRETEPOWDER).setProperty("color", "lime").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff0db60e, false),
                new BlockTemplate("pink", new Block.Builder(BlockType.CONCRETEPOWDER).setProperty("color", "pink").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff6076, false),
                new BlockTemplate("gray", new Block.Builder(BlockType.CONCRETEPOWDER).setProperty("color", "gray").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff565656, false),
                new BlockTemplate("silver", new Block.Builder(BlockType.CONCRETEPOWDER).setProperty("color", "silver").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffa6a6a6, false),
                new BlockTemplate("cyan", new Block.Builder(BlockType.CONCRETEPOWDER).setProperty("color", "cyan").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff0d5656, false),
                new BlockTemplate("purple", new Block.Builder(BlockType.CONCRETEPOWDER).setProperty("color", "purple").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff560d56, false),
                new BlockTemplate("blue", new Block.Builder(BlockType.CONCRETEPOWDER).setProperty("color", "blue").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff0d0e56, false),
                new BlockTemplate("brown", new Block.Builder(BlockType.CONCRETEPOWDER).setProperty("color", "brown").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff804530, false),
                new BlockTemplate("green", new Block.Builder(BlockType.CONCRETEPOWDER).setProperty("color", "green").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff0d560e, false),
                new BlockTemplate("brown", new Block.Builder(BlockType.CONCRETEPOWDER).setProperty("color", "red").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff2020, false),
                new BlockTemplate("black", new Block.Builder(BlockType.CONCRETEPOWDER).setProperty("color", "black").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff000000, false)
        });
        allTemplates.put("minecraft:chemistry_table", new BlockTemplate[]{
                new BlockTemplate("orange", new Block.Builder(BlockType.CHEMISTRY_TABLE).setProperty("chemistry_table_type", "compound_creator").setProperty("direction", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffffff, false),
                new BlockTemplate("orange", new Block.Builder(BlockType.CHEMISTRY_TABLE).setProperty("chemistry_table_type", "compound_creator").setProperty("direction", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffd030, false),
                new BlockTemplate("magenta", new Block.Builder(BlockType.CHEMISTRY_TABLE).setProperty("chemistry_table_type", "compound_creator").setProperty("direction", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffef007f, false),
                new BlockTemplate("light_blue", new Block.Builder(BlockType.CHEMISTRY_TABLE).setProperty("chemistry_table_type", "compound_creator").setProperty("direction", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5588ff, false),
                new BlockTemplate("yellow", new Block.Builder(BlockType.CHEMISTRY_TABLE).setProperty("chemistry_table_type", "material_reducer").setProperty("direction", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffff40, false),
                new BlockTemplate("lime", new Block.Builder(BlockType.CHEMISTRY_TABLE).setProperty("chemistry_table_type", "material_reducer").setProperty("direction", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff0db60e, false),
                new BlockTemplate("pink", new Block.Builder(BlockType.CHEMISTRY_TABLE).setProperty("chemistry_table_type", "material_reducer").setProperty("direction", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff6076, false),
                new BlockTemplate("gray", new Block.Builder(BlockType.CHEMISTRY_TABLE).setProperty("chemistry_table_type", "material_reducer").setProperty("direction", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff565656, false),
                new BlockTemplate("silver", new Block.Builder(BlockType.CHEMISTRY_TABLE).setProperty("chemistry_table_type", "element_constructor").setProperty("direction", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffa6a6a6, false),
                new BlockTemplate("cyan", new Block.Builder(BlockType.CHEMISTRY_TABLE).setProperty("chemistry_table_type", "element_constructor").setProperty("direction", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff0d5656, false),
                new BlockTemplate("purple", new Block.Builder(BlockType.CHEMISTRY_TABLE).setProperty("chemistry_table_type", "element_constructor").setProperty("direction", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff560d56, false),
                new BlockTemplate("blue", new Block.Builder(BlockType.CHEMISTRY_TABLE).setProperty("chemistry_table_type", "element_constructor").setProperty("direction", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff0d0e56, false),
                new BlockTemplate("brown", new Block.Builder(BlockType.CHEMISTRY_TABLE).setProperty("chemistry_table_type", "lab_table").setProperty("direction", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff804530, false),
                new BlockTemplate("green", new Block.Builder(BlockType.CHEMISTRY_TABLE).setProperty("chemistry_table_type", "lab_table").setProperty("direction", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff0d560e, false),
                new BlockTemplate("brown", new Block.Builder(BlockType.CHEMISTRY_TABLE).setProperty("chemistry_table_type", "lab_table").setProperty("direction", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff2020, false),
                new BlockTemplate("black", new Block.Builder(BlockType.CHEMISTRY_TABLE).setProperty("chemistry_table_type", "lab_table").setProperty("direction", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff000000, false)
        });
        allTemplates.put("minecraft:underwater_torch", new BlockTemplate[]{
                new BlockTemplate("orange", new Block.Builder(BlockType.UNDERWATER_TORCH).setProperty("torch_facing_direction", "unknown").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffffff, false),
                new BlockTemplate("orange", new Block.Builder(BlockType.UNDERWATER_TORCH).setProperty("torch_facing_direction", "west").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffd030, false),
                new BlockTemplate("magenta", new Block.Builder(BlockType.UNDERWATER_TORCH).setProperty("torch_facing_direction", "east").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffef007f, false),
                new BlockTemplate("light_blue", new Block.Builder(BlockType.UNDERWATER_TORCH).setProperty("torch_facing_direction", "north").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5588ff, false),
                new BlockTemplate("yellow", new Block.Builder(BlockType.UNDERWATER_TORCH).setProperty("torch_facing_direction", "south").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffffff40, false),
                new BlockTemplate("lime", new Block.Builder(BlockType.UNDERWATER_TORCH).setProperty("torch_facing_direction", "top").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff0db60e, false),
                new BlockTemplate("pink", new Block.Builder(BlockType.UNDERWATER_TORCH).setProperty("torch_facing_direction", "unknown").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffff6076, false),
                new BlockTemplate("gray", new Block.Builder(BlockType.UNDERWATER_TORCH).setProperty("torch_facing_direction", "unknown").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff565656, false)
        });
        allTemplates.put("minecraft:chorus_plant", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.CHORUS_PLANT).build(), new TexPathBlockIcon("blocks/chorus_plant.png"), 0xaa3d6e86, false)
        });
        allTemplates.put("minecraft:stained_glass", new BlockTemplate[]{
                new BlockTemplate("white", new Block.Builder(BlockType.STAINED_GLASS).setProperty("color", "white").build(), new TexPathBlockIcon("blocks/stained_glass_white.png"), 0x50836f64, false),
                new BlockTemplate("orange", new Block.Builder(BlockType.STAINED_GLASS).setProperty("color", "orange").build(), new TexPathBlockIcon("blocks/stained_glass_orange.png"), 0x509d5021, false),
                new BlockTemplate("magenta", new Block.Builder(BlockType.STAINED_GLASS).setProperty("color", "magenta").build(), new TexPathBlockIcon("blocks/stained_glass_magenta.png"), 0x50915369, false),
                new BlockTemplate("light_blue", new Block.Builder(BlockType.STAINED_GLASS).setProperty("color", "light_blue").build(), new TexPathBlockIcon("blocks/stained_glass_light_blue.png"), 0x50706b87, false),
                new BlockTemplate("yellow", new Block.Builder(BlockType.STAINED_GLASS).setProperty("color", "yellow").build(), new TexPathBlockIcon("blocks/stained_glass_yellow.png"), 0x50b5801f, false),
                new BlockTemplate("lime", new Block.Builder(BlockType.STAINED_GLASS).setProperty("color", "lime").build(), new TexPathBlockIcon("blocks/stained_glass_lime.png"), 0x50617030, false),
                new BlockTemplate("pink", new Block.Builder(BlockType.STAINED_GLASS).setProperty("color", "pink").build(), new TexPathBlockIcon("blocks/stained_glass_pink.png"), 0x509c4848, false),
                new BlockTemplate("gray", new Block.Builder(BlockType.STAINED_GLASS).setProperty("color", "gray").build(), new TexPathBlockIcon("blocks/stained_glass_gray.png"), 0x50392721, false),
                new BlockTemplate("silver", new Block.Builder(BlockType.STAINED_GLASS).setProperty("color", "silver").build(), new TexPathBlockIcon("blocks/stained_glass_silver.png"), 0x5081655b, false),
                new BlockTemplate("cyan", new Block.Builder(BlockType.STAINED_GLASS).setProperty("color", "cyan").build(), new TexPathBlockIcon("blocks/stained_glass_cyan.png"), 0x50565959, false),
                new BlockTemplate("purple", new Block.Builder(BlockType.STAINED_GLASS).setProperty("color", "purple").build(), new TexPathBlockIcon("blocks/stained_glass_purple.png"), 0x50744555, false),
                new BlockTemplate("blue", new Block.Builder(BlockType.STAINED_GLASS).setProperty("color", "blue").build(), new TexPathBlockIcon("blocks/stained_glass_blue.png"), 0x50463857, false),
                new BlockTemplate("brown", new Block.Builder(BlockType.STAINED_GLASS).setProperty("color", "brown").build(), new TexPathBlockIcon("blocks/stained_glass_brown.png"), 0x50492e1f, false),
                new BlockTemplate("green", new Block.Builder(BlockType.STAINED_GLASS).setProperty("color", "green").build(), new TexPathBlockIcon("blocks/stained_glass_green.png"), 0x50484f26, false),
                new BlockTemplate("red", new Block.Builder(BlockType.STAINED_GLASS).setProperty("color", "red").build(), new TexPathBlockIcon("blocks/stained_glass_red.png"), 0x50ff382b, false),
                new BlockTemplate("black", new Block.Builder(BlockType.STAINED_GLASS).setProperty("color", "black").build(), new TexPathBlockIcon("blocks/stained_glass_black.png"), 0x5021120d, false)
        });
        allTemplates.put("minecraft:242", new BlockTemplate[]{

        });
        allTemplates.put("minecraft:podzol", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.PODZOL).build(), new TexPathBlockIcon("blocks/podzol.png"), 0xff533a1b, true)
        });
        allTemplates.put("minecraft:beetroot", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.BEETROOT).setProperty("growth", 0).build(), new TexPathBlockIcon("blocks/beetroot.png"), 0x0901ab10, false)
        });
        allTemplates.put("minecraft:stonecutter", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.STONECUTTER).build(), new TexPathBlockIcon("blocks/stonecutter.png"), 0xff515151, false)
        });
        allTemplates.put("minecraft:glowingobsidian", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.GLOWINGOBSIDIAN).build(), new TexPathBlockIcon("blocks/glowingobsidian.png"), 0xff17060a, false)
        });
        allTemplates.put("minecraft:netherreactor", new BlockTemplate[]{
                new BlockTemplate("default", new Block.Builder(BlockType.NETHERREACTOR).build(), new TexPathBlockIcon("blocks/netherreactor_default.png"), 0xffd2d200, false),
                new BlockTemplate("active", new Block.Builder(BlockType.NETHERREACTOR).build(), new TexPathBlockIcon("blocks/netherreactor_active.png"), 0xff3d6e86, false),
                new BlockTemplate("cooled", new Block.Builder(BlockType.NETHERREACTOR).build(), new TexPathBlockIcon("blocks/netherreactor_cooled.png"), 0xff3d6e86, false)
        });
        allTemplates.put("minecraft:info_update", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.INFO_UPDATE).build(), new TexPathBlockIcon("blocks/info_update.png"), 0xff2f3218, false)
        });
        allTemplates.put("minecraft:info_update2", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.INFO_UPDATE2).build(), new TexPathBlockIcon("blocks/info_update2.png"), 0xff2f3218, false)
        });
        allTemplates.put("minecraft:movingBlock", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.MOVINGBLOCK).build(), new TexPathBlockIcon("blocks/movingBlock.png"), 0, false)
        });
        allTemplates.put("minecraft:observer", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.OBSERVER).setProperty("facing_direction", 0).setProperty("powered_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.OBSERVER).setProperty("facing_direction", 1).setProperty("powered_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.OBSERVER).setProperty("facing_direction", 2).setProperty("powered_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.OBSERVER).setProperty("facing_direction", 3).setProperty("powered_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.OBSERVER).setProperty("facing_direction", 4).setProperty("powered_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.OBSERVER).setProperty("facing_direction", 5).setProperty("powered_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.OBSERVER).setProperty("facing_direction", 0).setProperty("powered_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.OBSERVER).setProperty("facing_direction", 0).setProperty("powered_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.OBSERVER).setProperty("facing_direction", 0).setProperty("powered_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.OBSERVER).setProperty("facing_direction", 1).setProperty("powered_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.OBSERVER).setProperty("facing_direction", 2).setProperty("powered_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.OBSERVER).setProperty("facing_direction", 3).setProperty("powered_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.OBSERVER).setProperty("facing_direction", 4).setProperty("powered_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.OBSERVER).setProperty("facing_direction", 5).setProperty("powered_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.OBSERVER).setProperty("facing_direction", 0).setProperty("powered_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.OBSERVER).setProperty("facing_direction", 0).setProperty("powered_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false)
        });
        allTemplates.put("minecraft:structure_block", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.STRUCTURE_BLOCK).setProperty("structure_block_type", "data").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRUCTURE_BLOCK).setProperty("structure_block_type", "save").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRUCTURE_BLOCK).setProperty("structure_block_type", "load").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRUCTURE_BLOCK).setProperty("structure_block_type", "corner").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRUCTURE_BLOCK).setProperty("structure_block_type", "invalid").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRUCTURE_BLOCK).setProperty("structure_block_type", "export").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRUCTURE_BLOCK).setProperty("structure_block_type", "data").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRUCTURE_BLOCK).setProperty("structure_block_type", "data").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false)
        });
        allTemplates.put("minecraft:hard_glass", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.HARD_GLASS).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false)
        });
        allTemplates.put("minecraft:hard_stained_glass", new BlockTemplate[]{
                new BlockTemplate("white", new Block.Builder(BlockType.HARD_STAINED_GLASS).setProperty("color", "white").build(), new TexPathBlockIcon("blocks/stained_glass_white.png"), 0x50836f64, false),
                new BlockTemplate("orange", new Block.Builder(BlockType.HARD_STAINED_GLASS).setProperty("color", "orange").build(), new TexPathBlockIcon("blocks/stained_glass_orange.png"), 0x509d5021, false),
                new BlockTemplate("magenta", new Block.Builder(BlockType.HARD_STAINED_GLASS).setProperty("color", "magenta").build(), new TexPathBlockIcon("blocks/stained_glass_magenta.png"), 0x50915369, false),
                new BlockTemplate("light_blue", new Block.Builder(BlockType.HARD_STAINED_GLASS).setProperty("color", "light_blue").build(), new TexPathBlockIcon("blocks/stained_glass_light_blue.png"), 0x50706b87, false),
                new BlockTemplate("yellow", new Block.Builder(BlockType.HARD_STAINED_GLASS).setProperty("color", "yellow").build(), new TexPathBlockIcon("blocks/stained_glass_yellow.png"), 0x50b5801f, false),
                new BlockTemplate("lime", new Block.Builder(BlockType.HARD_STAINED_GLASS).setProperty("color", "lime").build(), new TexPathBlockIcon("blocks/stained_glass_lime.png"), 0x50617030, false),
                new BlockTemplate("pink", new Block.Builder(BlockType.HARD_STAINED_GLASS).setProperty("color", "pink").build(), new TexPathBlockIcon("blocks/stained_glass_pink.png"), 0x509c4848, false),
                new BlockTemplate("gray", new Block.Builder(BlockType.HARD_STAINED_GLASS).setProperty("color", "gray").build(), new TexPathBlockIcon("blocks/stained_glass_gray.png"), 0x50392721, false),
                new BlockTemplate("silver", new Block.Builder(BlockType.HARD_STAINED_GLASS).setProperty("color", "silver").build(), new TexPathBlockIcon("blocks/stained_glass_silver.png"), 0x5081655b, false),
                new BlockTemplate("cyan", new Block.Builder(BlockType.HARD_STAINED_GLASS).setProperty("color", "cyan").build(), new TexPathBlockIcon("blocks/stained_glass_cyan.png"), 0x50565959, false),
                new BlockTemplate("purple", new Block.Builder(BlockType.HARD_STAINED_GLASS).setProperty("color", "purple").build(), new TexPathBlockIcon("blocks/stained_glass_purple.png"), 0x50744555, false),
                new BlockTemplate("blue", new Block.Builder(BlockType.HARD_STAINED_GLASS).setProperty("color", "blue").build(), new TexPathBlockIcon("blocks/stained_glass_blue.png"), 0x50463857, false),
                new BlockTemplate("brown", new Block.Builder(BlockType.HARD_STAINED_GLASS).setProperty("color", "brown").build(), new TexPathBlockIcon("blocks/stained_glass_brown.png"), 0x50492e1f, false),
                new BlockTemplate("green", new Block.Builder(BlockType.HARD_STAINED_GLASS).setProperty("color", "green").build(), new TexPathBlockIcon("blocks/stained_glass_green.png"), 0x50484f26, false),
                new BlockTemplate("red", new Block.Builder(BlockType.HARD_STAINED_GLASS).setProperty("color", "red").build(), new TexPathBlockIcon("blocks/stained_glass_red.png"), 0x50ff382b, false),
                new BlockTemplate("black", new Block.Builder(BlockType.HARD_STAINED_GLASS).setProperty("color", "black").build(), new TexPathBlockIcon("blocks/stained_glass_black.png"), 0x5021120d, false)
        });
        allTemplates.put("minecraft:reserved6", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.RESERVED6).build(), new TexPathBlockIcon("blocks/reserved6.png"), 0xff19171a, false)
        });
        allTemplates.put("minecraft:256", new BlockTemplate[]{

        });
        allTemplates.put("minecraft:prismarine_stairs", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.PRISMARINE_STAIRS).setProperty("weirdo_direction", 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xA9498d6e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.PRISMARINE_STAIRS).setProperty("weirdo_direction", 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xA9498d6e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.PRISMARINE_STAIRS).setProperty("weirdo_direction", 2).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xA9498d6e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.PRISMARINE_STAIRS).setProperty("weirdo_direction", 3).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xA9498d6e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.PRISMARINE_STAIRS).setProperty("weirdo_direction", 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xA9498d6e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.PRISMARINE_STAIRS).setProperty("weirdo_direction", 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xA9498d6e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.PRISMARINE_STAIRS).setProperty("weirdo_direction", 2).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xA9498d6e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.PRISMARINE_STAIRS).setProperty("weirdo_direction", 3).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xA9498d6e, false)
        });
        allTemplates.put("minecraft:dark_prismarine_stairs", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_PRISMARINE_STAIRS).setProperty("weirdo_direction", 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04635e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_PRISMARINE_STAIRS).setProperty("weirdo_direction", 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04635e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_PRISMARINE_STAIRS).setProperty("weirdo_direction", 2).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04635e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_PRISMARINE_STAIRS).setProperty("weirdo_direction", 3).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04635e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_PRISMARINE_STAIRS).setProperty("weirdo_direction", 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04635e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_PRISMARINE_STAIRS).setProperty("weirdo_direction", 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04635e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_PRISMARINE_STAIRS).setProperty("weirdo_direction", 2).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04635e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_PRISMARINE_STAIRS).setProperty("weirdo_direction", 3).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04635e, false)
        });
        allTemplates.put("minecraft:prismarine_bricks_stairs", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.PRISMARINE_BRICKS_STAIRS).setProperty("weirdo_direction", 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff097d6e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.PRISMARINE_BRICKS_STAIRS).setProperty("weirdo_direction", 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff097d6e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.PRISMARINE_BRICKS_STAIRS).setProperty("weirdo_direction", 2).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff097d6e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.PRISMARINE_BRICKS_STAIRS).setProperty("weirdo_direction", 3).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff097d6e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.PRISMARINE_BRICKS_STAIRS).setProperty("weirdo_direction", 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff097d6e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.PRISMARINE_BRICKS_STAIRS).setProperty("weirdo_direction", 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff097d6e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.PRISMARINE_BRICKS_STAIRS).setProperty("weirdo_direction", 2).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff097d6e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.PRISMARINE_BRICKS_STAIRS).setProperty("weirdo_direction", 3).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff097d6e, false)
        });
        allTemplates.put("minecraft:stripped_spruce_log", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_SPRUCE_LOG).setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_SPRUCE_LOG).setProperty("pillar_axis", "x").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_SPRUCE_LOG).setProperty("pillar_axis", "z").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_SPRUCE_LOG).setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false)
        });
        allTemplates.put("minecraft:stripped_birch_log", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_BIRCH_LOG).setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_BIRCH_LOG).setProperty("pillar_axis", "x").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_BIRCH_LOG).setProperty("pillar_axis", "z").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_BIRCH_LOG).setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false)
        });
        allTemplates.put("minecraft:stripped_jungle_log", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_JUNGLE_LOG).setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_JUNGLE_LOG).setProperty("pillar_axis", "x").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_JUNGLE_LOG).setProperty("pillar_axis", "z").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_JUNGLE_LOG).setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false)
        });
        allTemplates.put("minecraft:stripped_acacia_log", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_ACACIA_LOG).setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_ACACIA_LOG).setProperty("pillar_axis", "x").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_ACACIA_LOG).setProperty("pillar_axis", "z").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_ACACIA_LOG).setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false)
        });
        allTemplates.put("minecraft:stripped_dark_oak_log", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_DARK_OAK_LOG).setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_DARK_OAK_LOG).setProperty("pillar_axis", "x").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_DARK_OAK_LOG).setProperty("pillar_axis", "z").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_DARK_OAK_LOG).setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false)
        });
        allTemplates.put("minecraft:stripped_oak_log", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_OAK_LOG).setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff9a7d4d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_OAK_LOG).setProperty("pillar_axis", "x").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff9a7d4d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_OAK_LOG).setProperty("pillar_axis", "z").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff9a7d4d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.STRIPPED_OAK_LOG).setProperty("pillar_axis", "y").build(), new TexPathBlockIcon("blocks/observer.png"), 0xff9a7d4d, false)
        });
        allTemplates.put("minecraft:blue_ice", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.BLUE_ICE).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff7dbeF6, false)
        });
        allTemplates.put("minecraft:seagrass", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.SEAGRASS).setProperty("sea_grass_type", "default").build(), new TexPathBlockIcon("blocks/observer.png"), 0x4e787878, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SEAGRASS).setProperty("sea_grass_type", "double_top").build(), new TexPathBlockIcon("blocks/observer.png"), 0x4e787878, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SEAGRASS).setProperty("sea_grass_type", "double_bot").build(), new TexPathBlockIcon("blocks/observer.png"), 0x4e787878, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SEAGRASS).setProperty("sea_grass_type", "default").build(), new TexPathBlockIcon("blocks/observer.png"), 0x4e787878, false)
        });
        allTemplates.put("minecraft:coral", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL).setProperty("coral_color", "blue").setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL).setProperty("coral_color", "pink").setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL).setProperty("coral_color", "purple").setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL).setProperty("coral_color", "red").setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL).setProperty("coral_color", "yellow").setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL).setProperty("coral_color", "blue").setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL).setProperty("coral_color", "blue").setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL).setProperty("coral_color", "blue").setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL).setProperty("coral_color", "blue").setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL).setProperty("coral_color", "pink").setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL).setProperty("coral_color", "purple").setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL).setProperty("coral_color", "red").setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL).setProperty("coral_color", "yellow").setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL).setProperty("coral_color", "blue").setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL).setProperty("coral_color", "blue").setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL).setProperty("coral_color", "blue").setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false)
        });
        allTemplates.put("minecraft:coral_block", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_BLOCK).setProperty("coral_color", "blue").setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_BLOCK).setProperty("coral_color", "pink").setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_BLOCK).setProperty("coral_color", "purple").setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_BLOCK).setProperty("coral_color", "red").setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_BLOCK).setProperty("coral_color", "yellow").setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_BLOCK).setProperty("coral_color", "blue").setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_BLOCK).setProperty("coral_color", "blue").setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_BLOCK).setProperty("coral_color", "blue").setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_BLOCK).setProperty("coral_color", "blue").setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_BLOCK).setProperty("coral_color", "pink").setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_BLOCK).setProperty("coral_color", "purple").setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_BLOCK).setProperty("coral_color", "red").setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_BLOCK).setProperty("coral_color", "yellow").setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_BLOCK).setProperty("coral_color", "blue").setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_BLOCK).setProperty("coral_color", "blue").setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_BLOCK).setProperty("coral_color", "blue").setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false)
        });
        allTemplates.put("minecraft:coral_fan", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN).setProperty("coral_color", "pink").setProperty("coral_fan_direction", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN).setProperty("coral_color", "yellow").setProperty("coral_fan_direction", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN).setProperty("coral_color", "blue").setProperty("coral_fan_direction", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN).setProperty("coral_color", "blue").setProperty("coral_fan_direction", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN).setProperty("coral_color", "blue").setProperty("coral_fan_direction", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN).setProperty("coral_color", "pink").setProperty("coral_fan_direction", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN).setProperty("coral_color", "purple").setProperty("coral_fan_direction", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN).setProperty("coral_color", "red").setProperty("coral_fan_direction", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN).setProperty("coral_color", "yellow").setProperty("coral_fan_direction", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN).setProperty("coral_color", "blue").setProperty("coral_fan_direction", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN).setProperty("coral_color", "blue").setProperty("coral_fan_direction", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN).setProperty("coral_color", "blue").setProperty("coral_fan_direction", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false)
        });
        allTemplates.put("minecraft:coral_fan_dead", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_DEAD).setProperty("coral_color", "blue").setProperty("coral_fan_direction", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xFFC8C8C8, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_DEAD).setProperty("coral_color", "pink").setProperty("coral_fan_direction", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xFFC8C8C8, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_DEAD).setProperty("coral_color", "purple").setProperty("coral_fan_direction", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xFFC8C8C8, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_DEAD).setProperty("coral_color", "red").setProperty("coral_fan_direction", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xFFC8C8C8, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_DEAD).setProperty("coral_color", "yellow").setProperty("coral_fan_direction", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xFFC8C8C8, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_DEAD).setProperty("coral_color", "blue").setProperty("coral_fan_direction", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xFFC8C8C8, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_DEAD).setProperty("coral_color", "blue").setProperty("coral_fan_direction", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xFFC8C8C8, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_DEAD).setProperty("coral_color", "blue").setProperty("coral_fan_direction", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xFFC8C8C8, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_DEAD).setProperty("coral_color", "blue").setProperty("coral_fan_direction", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xFFC8C8C8, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_DEAD).setProperty("coral_color", "pink").setProperty("coral_fan_direction", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xFFC8C8C8, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_DEAD).setProperty("coral_color", "purple").setProperty("coral_fan_direction", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xFFC8C8C8, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_DEAD).setProperty("coral_color", "red").setProperty("coral_fan_direction", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xFFC8C8C8, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_DEAD).setProperty("coral_color", "yellow").setProperty("coral_fan_direction", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xFFC8C8C8, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_DEAD).setProperty("coral_color", "blue").setProperty("coral_fan_direction", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xFFC8C8C8, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_DEAD).setProperty("coral_color", "blue").setProperty("coral_fan_direction", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xFFC8C8C8, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_DEAD).setProperty("coral_color", "blue").setProperty("coral_fan_direction", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xFFC8C8C8, false)
        });
        allTemplates.put("minecraft:coral_fan_hang", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG).setProperty("coral_hang_type_bit", (byte) 0).setProperty("coral_direction", 0).setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG).setProperty("coral_hang_type_bit", (byte) 1).setProperty("coral_direction", 0).setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG).setProperty("coral_hang_type_bit", (byte) 0).setProperty("coral_direction", 0).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG).setProperty("coral_hang_type_bit", (byte) 1).setProperty("coral_direction", 0).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG).setProperty("coral_hang_type_bit", (byte) 0).setProperty("coral_direction", 1).setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG).setProperty("coral_hang_type_bit", (byte) 1).setProperty("coral_direction", 1).setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG).setProperty("coral_hang_type_bit", (byte) 0).setProperty("coral_direction", 1).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG).setProperty("coral_hang_type_bit", (byte) 0).setProperty("coral_direction", 2).setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG).setProperty("coral_hang_type_bit", (byte) 1).setProperty("coral_direction", 2).setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG).setProperty("coral_hang_type_bit", (byte) 1).setProperty("coral_direction", 2).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG).setProperty("coral_hang_type_bit", (byte) 0).setProperty("coral_direction", 3).setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG).setProperty("coral_hang_type_bit", (byte) 1).setProperty("coral_direction", 3).setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG).setProperty("coral_hang_type_bit", (byte) 0).setProperty("coral_direction", 3).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG).setProperty("coral_hang_type_bit", (byte) 1).setProperty("coral_direction", 3).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false)
        });
        allTemplates.put("minecraft:coral_fan_hang2", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG2).setProperty("coral_hang_type_bit", (byte) 0).setProperty("coral_direction", 0).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG2).setProperty("coral_hang_type_bit", (byte) 1).setProperty("coral_direction", 1).setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG2).setProperty("coral_hang_type_bit", (byte) 0).setProperty("coral_direction", 1).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG2).setProperty("coral_hang_type_bit", (byte) 1).setProperty("coral_direction", 1).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG2).setProperty("coral_hang_type_bit", (byte) 0).setProperty("coral_direction", 2).setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG2).setProperty("coral_hang_type_bit", (byte) 1).setProperty("coral_direction", 2).setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG2).setProperty("coral_hang_type_bit", (byte) 0).setProperty("coral_direction", 2).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG2).setProperty("coral_hang_type_bit", (byte) 1).setProperty("coral_direction", 2).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG2).setProperty("coral_hang_type_bit", (byte) 0).setProperty("coral_direction", 3).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG2).setProperty("coral_hang_type_bit", (byte) 1).setProperty("coral_direction", 3).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false)
        });
        allTemplates.put("minecraft:coral_fan_hang3", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG3).setProperty("coral_hang_type_bit", (byte) 0).setProperty("coral_direction", 0).setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG3).setProperty("coral_hang_type_bit", (byte) 0).setProperty("coral_direction", 0).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG3).setProperty("coral_hang_type_bit", (byte) 1).setProperty("coral_direction", 0).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG3).setProperty("coral_hang_type_bit", (byte) 0).setProperty("coral_direction", 1).setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG3).setProperty("coral_hang_type_bit", (byte) 1).setProperty("coral_direction", 1).setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG3).setProperty("coral_hang_type_bit", (byte) 0).setProperty("coral_direction", 1).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG3).setProperty("coral_hang_type_bit", (byte) 1).setProperty("coral_direction", 1).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG3).setProperty("coral_hang_type_bit", (byte) 0).setProperty("coral_direction", 2).setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG3).setProperty("coral_hang_type_bit", (byte) 1).setProperty("coral_direction", 2).setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG3).setProperty("coral_hang_type_bit", (byte) 0).setProperty("coral_direction", 2).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG3).setProperty("coral_hang_type_bit", (byte) 1).setProperty("coral_direction", 2).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG3).setProperty("coral_hang_type_bit", (byte) 0).setProperty("coral_direction", 3).setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG3).setProperty("coral_hang_type_bit", (byte) 1).setProperty("coral_direction", 3).setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG3).setProperty("coral_hang_type_bit", (byte) 0).setProperty("coral_direction", 3).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CORAL_FAN_HANG3).setProperty("coral_hang_type_bit", (byte) 1).setProperty("coral_direction", 3).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff3d6e86, false)
        });
        allTemplates.put("minecraft:kelp", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.KELP).setProperty("kelp_age", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04833e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.KELP).setProperty("kelp_age", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04833e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.KELP).setProperty("kelp_age", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04833e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.KELP).setProperty("kelp_age", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04833e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.KELP).setProperty("kelp_age", 4).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04833e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.KELP).setProperty("kelp_age", 5).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04833e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.KELP).setProperty("kelp_age", 6).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04833e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.KELP).setProperty("kelp_age", 7).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04833e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.KELP).setProperty("kelp_age", 8).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04833e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.KELP).setProperty("kelp_age", 9).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04833e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.KELP).setProperty("kelp_age", 10).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04833e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.KELP).setProperty("kelp_age", 11).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04833e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.KELP).setProperty("kelp_age", 12).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04833e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.KELP).setProperty("kelp_age", 13).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04833e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.KELP).setProperty("kelp_age", 14).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04833e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.KELP).setProperty("kelp_age", 15).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff04833e, false)
        });
        allTemplates.put("minecraft:dried_kelp_block", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.DRIED_KELP_BLOCK).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff044b4b, false)
        });
        allTemplates.put("minecraft:acacia_button", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0x5f934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_BUTTON).setProperty("facing_direction", 1).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0x5f934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_BUTTON).setProperty("facing_direction", 2).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0x5f934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_BUTTON).setProperty("facing_direction", 3).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0x5f934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_BUTTON).setProperty("facing_direction", 4).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0x5f934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_BUTTON).setProperty("facing_direction", 5).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0x5f934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0x5f934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0x5f934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0x5f934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_BUTTON).setProperty("facing_direction", 1).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0x5f934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_BUTTON).setProperty("facing_direction", 2).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0x5f934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_BUTTON).setProperty("facing_direction", 3).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0x5f934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_BUTTON).setProperty("facing_direction", 4).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0x5f934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_BUTTON).setProperty("facing_direction", 5).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0x5f934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0x5f934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0x5f934f39, false)
        });
        allTemplates.put("minecraft:birch_button", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_BUTTON).setProperty("facing_direction", 1).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_BUTTON).setProperty("facing_direction", 2).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_BUTTON).setProperty("facing_direction", 3).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_BUTTON).setProperty("facing_direction", 4).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_BUTTON).setProperty("facing_direction", 5).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_BUTTON).setProperty("facing_direction", 1).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_BUTTON).setProperty("facing_direction", 2).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_BUTTON).setProperty("facing_direction", 3).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_BUTTON).setProperty("facing_direction", 4).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_BUTTON).setProperty("facing_direction", 5).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false)
        });
        allTemplates.put("minecraft:dark_oak_button", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_BUTTON).setProperty("facing_direction", 1).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_BUTTON).setProperty("facing_direction", 2).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_BUTTON).setProperty("facing_direction", 3).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_BUTTON).setProperty("facing_direction", 4).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_BUTTON).setProperty("facing_direction", 5).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_BUTTON).setProperty("facing_direction", 1).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_BUTTON).setProperty("facing_direction", 2).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_BUTTON).setProperty("facing_direction", 3).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_BUTTON).setProperty("facing_direction", 4).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_BUTTON).setProperty("facing_direction", 5).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false)
        });
        allTemplates.put("minecraft:jungle_button", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_BUTTON).setProperty("facing_direction", 1).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_BUTTON).setProperty("facing_direction", 2).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_BUTTON).setProperty("facing_direction", 3).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_BUTTON).setProperty("facing_direction", 4).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_BUTTON).setProperty("facing_direction", 5).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_BUTTON).setProperty("facing_direction", 1).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_BUTTON).setProperty("facing_direction", 2).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_BUTTON).setProperty("facing_direction", 3).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_BUTTON).setProperty("facing_direction", 4).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_BUTTON).setProperty("facing_direction", 5).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false)
        });
        allTemplates.put("minecraft:spruce_button", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_BUTTON).setProperty("facing_direction", 1).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_BUTTON).setProperty("facing_direction", 2).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_BUTTON).setProperty("facing_direction", 3).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_BUTTON).setProperty("facing_direction", 4).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_BUTTON).setProperty("facing_direction", 5).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_BUTTON).setProperty("facing_direction", 1).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_BUTTON).setProperty("facing_direction", 2).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_BUTTON).setProperty("facing_direction", 3).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_BUTTON).setProperty("facing_direction", 4).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_BUTTON).setProperty("facing_direction", 5).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_BUTTON).setProperty("facing_direction", 0).setProperty("button_pressed_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false)
        });
        allTemplates.put("minecraft:acacia_trapdoor", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_TRAPDOOR).setProperty("direction", 0).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_TRAPDOOR).setProperty("direction", 1).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_TRAPDOOR).setProperty("direction", 2).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_TRAPDOOR).setProperty("direction", 3).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_TRAPDOOR).setProperty("direction", 0).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_TRAPDOOR).setProperty("direction", 1).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_TRAPDOOR).setProperty("direction", 2).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_TRAPDOOR).setProperty("direction", 3).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_TRAPDOOR).setProperty("direction", 0).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_TRAPDOOR).setProperty("direction", 1).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_TRAPDOOR).setProperty("direction", 2).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_TRAPDOOR).setProperty("direction", 3).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_TRAPDOOR).setProperty("direction", 0).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_TRAPDOOR).setProperty("direction", 1).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_TRAPDOOR).setProperty("direction", 2).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_TRAPDOOR).setProperty("direction", 3).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false)
        });
        allTemplates.put("minecraft:birch_trapdoor", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_TRAPDOOR).setProperty("direction", 0).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_TRAPDOOR).setProperty("direction", 1).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_TRAPDOOR).setProperty("direction", 2).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_TRAPDOOR).setProperty("direction", 3).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_TRAPDOOR).setProperty("direction", 0).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_TRAPDOOR).setProperty("direction", 1).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_TRAPDOOR).setProperty("direction", 2).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_TRAPDOOR).setProperty("direction", 3).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_TRAPDOOR).setProperty("direction", 0).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_TRAPDOOR).setProperty("direction", 1).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_TRAPDOOR).setProperty("direction", 2).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_TRAPDOOR).setProperty("direction", 3).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_TRAPDOOR).setProperty("direction", 0).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_TRAPDOOR).setProperty("direction", 1).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_TRAPDOOR).setProperty("direction", 2).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_TRAPDOOR).setProperty("direction", 3).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false)
        });
        allTemplates.put("minecraft:dark_oak_trapdoor", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_TRAPDOOR).setProperty("direction", 0).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_TRAPDOOR).setProperty("direction", 1).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_TRAPDOOR).setProperty("direction", 2).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_TRAPDOOR).setProperty("direction", 3).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_TRAPDOOR).setProperty("direction", 0).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_TRAPDOOR).setProperty("direction", 1).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_TRAPDOOR).setProperty("direction", 2).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_TRAPDOOR).setProperty("direction", 3).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_TRAPDOOR).setProperty("direction", 0).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_TRAPDOOR).setProperty("direction", 1).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_TRAPDOOR).setProperty("direction", 2).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_TRAPDOOR).setProperty("direction", 3).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_TRAPDOOR).setProperty("direction", 0).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_TRAPDOOR).setProperty("direction", 1).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_TRAPDOOR).setProperty("direction", 2).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_TRAPDOOR).setProperty("direction", 3).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false)
        });
        allTemplates.put("minecraft:jungle_trapdoor", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_TRAPDOOR).setProperty("direction", 0).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_TRAPDOOR).setProperty("direction", 1).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_TRAPDOOR).setProperty("direction", 2).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_TRAPDOOR).setProperty("direction", 3).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_TRAPDOOR).setProperty("direction", 0).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_TRAPDOOR).setProperty("direction", 1).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_TRAPDOOR).setProperty("direction", 2).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_TRAPDOOR).setProperty("direction", 3).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_TRAPDOOR).setProperty("direction", 0).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_TRAPDOOR).setProperty("direction", 1).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_TRAPDOOR).setProperty("direction", 2).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_TRAPDOOR).setProperty("direction", 3).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_TRAPDOOR).setProperty("direction", 0).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_TRAPDOOR).setProperty("direction", 1).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_TRAPDOOR).setProperty("direction", 2).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_TRAPDOOR).setProperty("direction", 3).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false)
        });
        allTemplates.put("minecraft:spruce_trapdoor", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_TRAPDOOR).setProperty("direction", 0).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_TRAPDOOR).setProperty("direction", 1).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_TRAPDOOR).setProperty("direction", 2).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_TRAPDOOR).setProperty("direction", 3).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_TRAPDOOR).setProperty("direction", 0).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_TRAPDOOR).setProperty("direction", 1).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_TRAPDOOR).setProperty("direction", 2).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_TRAPDOOR).setProperty("direction", 3).setProperty("open_bit", (byte) 0).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_TRAPDOOR).setProperty("direction", 0).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_TRAPDOOR).setProperty("direction", 1).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_TRAPDOOR).setProperty("direction", 2).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_TRAPDOOR).setProperty("direction", 3).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_TRAPDOOR).setProperty("direction", 0).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_TRAPDOOR).setProperty("direction", 1).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_TRAPDOOR).setProperty("direction", 2).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_TRAPDOOR).setProperty("direction", 3).setProperty("open_bit", (byte) 1).setProperty("upside_down_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false)
        });
        allTemplates.put("minecraft:acacia_pressure_plate", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_PRESSURE_PLATE).setProperty("redstone_signal", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_PRESSURE_PLATE).setProperty("redstone_signal", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_PRESSURE_PLATE).setProperty("redstone_signal", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_PRESSURE_PLATE).setProperty("redstone_signal", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_PRESSURE_PLATE).setProperty("redstone_signal", 4).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_PRESSURE_PLATE).setProperty("redstone_signal", 5).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_PRESSURE_PLATE).setProperty("redstone_signal", 6).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_PRESSURE_PLATE).setProperty("redstone_signal", 7).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_PRESSURE_PLATE).setProperty("redstone_signal", 8).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_PRESSURE_PLATE).setProperty("redstone_signal", 9).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_PRESSURE_PLATE).setProperty("redstone_signal", 10).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_PRESSURE_PLATE).setProperty("redstone_signal", 11).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_PRESSURE_PLATE).setProperty("redstone_signal", 12).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_PRESSURE_PLATE).setProperty("redstone_signal", 13).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_PRESSURE_PLATE).setProperty("redstone_signal", 14).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false),
                new BlockTemplate(null, new Block.Builder(BlockType.ACACIA_PRESSURE_PLATE).setProperty("redstone_signal", 15).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff934f39, false)
        });
        allTemplates.put("minecraft:birch_pressure_plate", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_PRESSURE_PLATE).setProperty("redstone_signal", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_PRESSURE_PLATE).setProperty("redstone_signal", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_PRESSURE_PLATE).setProperty("redstone_signal", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_PRESSURE_PLATE).setProperty("redstone_signal", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_PRESSURE_PLATE).setProperty("redstone_signal", 4).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_PRESSURE_PLATE).setProperty("redstone_signal", 5).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_PRESSURE_PLATE).setProperty("redstone_signal", 6).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_PRESSURE_PLATE).setProperty("redstone_signal", 7).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_PRESSURE_PLATE).setProperty("redstone_signal", 8).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_PRESSURE_PLATE).setProperty("redstone_signal", 9).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_PRESSURE_PLATE).setProperty("redstone_signal", 10).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_PRESSURE_PLATE).setProperty("redstone_signal", 11).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_PRESSURE_PLATE).setProperty("redstone_signal", 12).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_PRESSURE_PLATE).setProperty("redstone_signal", 13).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_PRESSURE_PLATE).setProperty("redstone_signal", 14).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.BIRCH_PRESSURE_PLATE).setProperty("redstone_signal", 15).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffdabd8d, false)
        });
        allTemplates.put("minecraft:dark_oak_pressure_plate", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_PRESSURE_PLATE).setProperty("redstone_signal", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_PRESSURE_PLATE).setProperty("redstone_signal", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_PRESSURE_PLATE).setProperty("redstone_signal", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_PRESSURE_PLATE).setProperty("redstone_signal", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_PRESSURE_PLATE).setProperty("redstone_signal", 4).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_PRESSURE_PLATE).setProperty("redstone_signal", 5).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_PRESSURE_PLATE).setProperty("redstone_signal", 6).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_PRESSURE_PLATE).setProperty("redstone_signal", 7).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_PRESSURE_PLATE).setProperty("redstone_signal", 8).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_PRESSURE_PLATE).setProperty("redstone_signal", 9).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_PRESSURE_PLATE).setProperty("redstone_signal", 10).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_PRESSURE_PLATE).setProperty("redstone_signal", 11).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_PRESSURE_PLATE).setProperty("redstone_signal", 12).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_PRESSURE_PLATE).setProperty("redstone_signal", 13).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_PRESSURE_PLATE).setProperty("redstone_signal", 14).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false),
                new BlockTemplate(null, new Block.Builder(BlockType.DARK_OAK_PRESSURE_PLATE).setProperty("redstone_signal", 15).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff2d2213, false)
        });
        allTemplates.put("minecraft:jungle_pressure_plate", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_PRESSURE_PLATE).setProperty("redstone_signal", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_PRESSURE_PLATE).setProperty("redstone_signal", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_PRESSURE_PLATE).setProperty("redstone_signal", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_PRESSURE_PLATE).setProperty("redstone_signal", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_PRESSURE_PLATE).setProperty("redstone_signal", 4).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_PRESSURE_PLATE).setProperty("redstone_signal", 5).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_PRESSURE_PLATE).setProperty("redstone_signal", 6).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_PRESSURE_PLATE).setProperty("redstone_signal", 7).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_PRESSURE_PLATE).setProperty("redstone_signal", 8).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_PRESSURE_PLATE).setProperty("redstone_signal", 9).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_PRESSURE_PLATE).setProperty("redstone_signal", 10).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_PRESSURE_PLATE).setProperty("redstone_signal", 11).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_PRESSURE_PLATE).setProperty("redstone_signal", 12).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_PRESSURE_PLATE).setProperty("redstone_signal", 13).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_PRESSURE_PLATE).setProperty("redstone_signal", 14).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.JUNGLE_PRESSURE_PLATE).setProperty("redstone_signal", 15).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffBa7d5d, false)
        });
        allTemplates.put("minecraft:spruce_pressure_plate", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_PRESSURE_PLATE).setProperty("redstone_signal", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_PRESSURE_PLATE).setProperty("redstone_signal", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_PRESSURE_PLATE).setProperty("redstone_signal", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_PRESSURE_PLATE).setProperty("redstone_signal", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_PRESSURE_PLATE).setProperty("redstone_signal", 4).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_PRESSURE_PLATE).setProperty("redstone_signal", 5).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_PRESSURE_PLATE).setProperty("redstone_signal", 6).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_PRESSURE_PLATE).setProperty("redstone_signal", 7).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_PRESSURE_PLATE).setProperty("redstone_signal", 8).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_PRESSURE_PLATE).setProperty("redstone_signal", 9).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_PRESSURE_PLATE).setProperty("redstone_signal", 10).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_PRESSURE_PLATE).setProperty("redstone_signal", 11).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_PRESSURE_PLATE).setProperty("redstone_signal", 12).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_PRESSURE_PLATE).setProperty("redstone_signal", 13).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_PRESSURE_PLATE).setProperty("redstone_signal", 14).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SPRUCE_PRESSURE_PLATE).setProperty("redstone_signal", 15).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff5a3d0d, false)
        });
        allTemplates.put("minecraft:carved_pumpkin", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.CARVED_PUMPKIN).setProperty("direction", 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffc07615, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CARVED_PUMPKIN).setProperty("direction", 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffc07615, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CARVED_PUMPKIN).setProperty("direction", 2).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffc07615, false),
                new BlockTemplate(null, new Block.Builder(BlockType.CARVED_PUMPKIN).setProperty("direction", 3).build(), new TexPathBlockIcon("blocks/observer.png"), 0xffc07615, false)
        });
        allTemplates.put("minecraft:sea_pickle", new BlockTemplate[]{
                new BlockTemplate(null, new Block.Builder(BlockType.SEA_PICKLE).setProperty("cluster_count", 0).setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff10C38e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SEA_PICKLE).setProperty("cluster_count", 1).setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff10C38e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SEA_PICKLE).setProperty("cluster_count", 2).setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff10C38e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SEA_PICKLE).setProperty("cluster_count", 3).setProperty("dead_bit", (byte) 0).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff10C38e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SEA_PICKLE).setProperty("cluster_count", 0).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff10C38e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SEA_PICKLE).setProperty("cluster_count", 1).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff10C38e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SEA_PICKLE).setProperty("cluster_count", 2).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff10C38e, false),
                new BlockTemplate(null, new Block.Builder(BlockType.SEA_PICKLE).setProperty("cluster_count", 3).setProperty("dead_bit", (byte) 1).build(), new TexPathBlockIcon("blocks/observer.png"), 0xff10C38e, false)
        });
    }

    public static BlockTemplate[] getOfType(@NonNull String name) {
        if (!name.contains(":")) name = "minecraft:" + name;
        return allTemplates.get(name);
    }

    @NonNull
    public static BlockTemplate getBest(@NonNull Block block) {
        var type = block.getType();
        if (type == null) return getUnknownBlockTemplate();
        List<BlockTemplate> candidates = Arrays.asList(Objects.requireNonNull(getOfType(type.getName())));
        for (int i = 0, limit = block.getType().getKnownProperties().length; i < limit; i++) {
            List<BlockTemplate> newCandidates = new ArrayList<>();
            for (var template : candidates) {
                if (Objects.equals(template.getBlock().getKnownProperties()[i], block.getKnownProperties()[i]))
                    newCandidates.add(template);
            }
            switch (newCandidates.size()) {
                case 0:                    // give up filtering based on this property
                    continue;
                case 1:                    // no longer an option
                    return newCandidates.get(0);
                default:                    // less candidates
                    candidates = newCandidates;
            }
        }
        return candidates.size() > 0 ? candidates.get(0) : getUnknownBlockTemplate();
    }

    public static Stream<BlockTemplate> getAll() {
        var stream = Arrays.stream(new BlockTemplate[0]);
        for (var templates : allTemplates.values())
            stream = Streams.concat(stream, Arrays.stream(templates));
        return stream;
    }

    @NonNull
    public static BlockTemplate getAirTemplate() {
        return getOfType("minecraft:air")[0];
    }

    @NonNull
    public static BlockTemplate getUnknownBlockTemplate() {
        return unknownBlockTemplate;
    }
}
