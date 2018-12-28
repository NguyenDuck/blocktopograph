package com.mithrilmania.blocktopograph.map;

import android.util.SparseArray;

import com.mithrilmania.blocktopograph.util.Color;

/*
Biome enum for MCPE -- by @mithrilmania

--- Please attribute @mithrilmania for generating+updating this enum
 */
public enum Biome {

    OCEAN(0, "Ocean", Color.fromRGB(2, 0, 112)),
    PLAINS(1, "Plains", Color.fromRGB(140, 176, 96)),
    DESERT(2, "Desert", Color.fromRGB(251, 148, 27)),
    EXTREME_HILLS(3, "Extreme Hills", Color.fromRGB(93, 99, 93)),
    FOREST(4, "Forest", Color.fromRGB(2, 99, 32)),
    TAIGA(5, "Taiga", Color.fromRGB(9, 102, 91)),
    SWAMPLAND(6, "Swampland", Color.fromRGB(4, 200, 139)),
    RIVER(7, "River", Color.fromRGB(1, 1, 255)),
    HELL(8, "Hell", Color.fromRGB(255, 0, 1)),
    SKY(9, "Sky", Color.fromRGB(130, 129, 254)),
    FROZEN_OCEAN(10, "Frozen Ocean", Color.fromRGB(142, 141, 161)),
    FROZEN_RIVER(11, "Frozen River", Color.fromRGB(159, 163, 255)),
    ICE_PLAINS(12, "Ice Plains", Color.fromRGB(255, 254, 255)),
    ICE_MOUNTAINS(13, "Ice Mountains", Color.fromRGB(162, 157, 157)),
    MUSHROOM_ISLAND(14, "Mushroom Island", Color.fromRGB(254, 1, 255)),
    MUSHROOM_ISLAND_SHORE(15, "Mushroom Island Shore", Color.fromRGB(158, 3, 253)),
    BEACH(16, "Beach", Color.fromRGB(250, 223, 85)),
    DESERT_HILLS(17, "Desert Hills", Color.fromRGB(212, 94, 15)),
    FOREST_HILLS(18, "Forest Hills", Color.fromRGB(37, 86, 30)),
    TAIGA_HILLS(19, "Taiga Hills", Color.fromRGB(25, 54, 49)),
    EXTREME_HILLS_EDGE(20, "Extreme Hills Edge", Color.fromRGB(115, 118, 157)),
    JUNGLE(21, "Jungle", Color.fromRGB(82, 122, 7)),
    JUNGLE_HILLS(22, "Jungle Hills", Color.fromRGB(46, 64, 3)),
    JUNGLE_EDGE(23, "Jungle Edge", Color.fromRGB(99, 142, 24)),
    DEEP_OCEAN(24, "Deep Ocean", Color.fromRGB(2, 0, 47)),
    STONE_BEACH(25, "Stone Beach", Color.fromRGB(162, 164, 132)),
    COLD_BEACH(26, "Cold Beach", Color.fromRGB(250, 238, 193)),
    BIRCH_FOREST(27, "Birch Forest", Color.fromRGB(48, 117, 70)),
    BIRCH_FOREST_HILLS(28, "Birch Forest Hills", Color.fromRGB(29, 94, 51)),
    ROOFED_FOREST(29, "Roofed Forest", Color.fromRGB(66, 82, 24)),
    COLD_TAIGA(30, "Cold Taiga", Color.fromRGB(49, 85, 75)),
    COLD_TAIGA_HILLS(31, "Cold Taiga Hills", Color.fromRGB(34, 61, 52)),
    MEGA_TAIGA(32, "Mega Taiga", Color.fromRGB(92, 105, 84)),
    MEGA_TAIGA_HILLS(33, "Mega Taiga Hills", Color.fromRGB(70, 76, 59)),
    EXTREME_HILLS_PLUS(34, "Extreme Hills+", Color.fromRGB(79, 111, 81)),
    SAVANNA(35, "Savanna", Color.fromRGB(192, 180, 94)),
    SAVANNA_PLATEAU(36, "Savanna Plateau", Color.fromRGB(168, 157, 98)),
    MESA(37, "Mesa", Color.fromRGB(220, 66, 19)),
    MESA_PLATEAU_F(38, "Mesa Plateau F", Color.fromRGB(174, 152, 100)),
    MESA_PLATEAU(39, "Mesa Plateau", Color.fromRGB(202, 139, 98)),

    //fix the colors for these
    SMALL_END_ISLANDS(40, "Small End Islands",Color.fromRGB(202, 139, 98)),
    END_MIDLANDS(41, "End Midlands",Color.fromRGB(202, 139, 98)),
    END_HIGHLANDS(42, "End Highlands",Color.fromRGB(202, 139, 98)),
    END_BARRENS(43, "End Barrens",Color.fromRGB(202, 139, 98)),
    WARM_OCEAN(44, "Warm Ocean",Color.fromRGB(202, 139, 98)),
    LUKEWARM_OCEAN(45, "Lukewarm Ocean",Color.fromRGB(202, 139, 98)),
    COLD_OCEAN(46, "Cold Ocean",Color.fromRGB(202, 139, 98)),
    DEEP_WARM_OCEAN(47, "Deep Warm Ocean",Color.fromRGB(202, 139, 98)),
    DEEP_LUKEWARM_OCEAN(48, "Deep Lukewarm Ocean",Color.fromRGB(202, 139, 98)),
    DEEP_COLD_OCEAN(49, "Deep Cold Ocean",Color.fromRGB(202, 139, 98)),
    DEEP_FROZEN_OCEAN(50, "Deep Frozen Ocean",Color.fromRGB(202, 139, 98)),

    //fix the colors for the void
    THE_VOID(127, "The Void", Color.fromRGB(81, 79, 195)),

    OCEAN_M(128, "Ocean M", Color.fromRGB(81, 79, 195)),
    SUNFLOWER_PLAINS(129, "Sunflower Plains", Color.fromRGB(220, 255, 177)),
    DESERT_M(130, "Desert M", Color.fromRGB(255, 230, 101)),
    EXTREME_HILLS_M(131, "Extreme Hills M", Color.fromRGB(177, 176, 174)),
    FLOWER_FOREST(132, "Flower Forest", Color.fromRGB(82, 180, 110)),
    TAIGA_M(133, "Taiga M", Color.fromRGB(90, 182, 171)),
    SWAMPLAND_M(134, "Swampland M", Color.fromRGB(87, 255, 255)),
    RIVER_M(135, "River M", Color.fromRGB(82, 79, 255)),
    HELL_M(136, "Hell M", Color.fromRGB(255, 80, 83)),
    SKY_M(137, "Sky M", Color.fromRGB(210, 211, 255)),
    FROZEN_OCEAN_M(138, "Frozen Ocean M", Color.fromRGB(226, 224, 241)),
    FROZEN_RIVER_M(139, "Frozen River M", Color.fromRGB(239, 242, 255)),
    ICE_PLAINS_SPIKES(140, "Ice Plains Spikes", Color.fromRGB(223, 255, 255)),
    ICE_MOUNTAINS_M(141, "Ice Mountains M", Color.fromRGB(237, 237, 238)),
    MUSHROOM_ISLAND_M(142, "Mushroom Island M", Color.fromRGB(255, 82, 255)),
    MUSHROOM_ISLAND_SHORE_M(143, "Mushroom Island Shore M", Color.fromRGB(243, 82, 255)),
    BEACH_M(144, "Beach M", Color.fromRGB(255, 255, 162)),
    DESERT_HILLS_M(145, "Desert Hills M", Color.fromRGB(255, 177, 100)),
    FOREST_HILLS_M(146, "Forest Hills M", Color.fromRGB(113, 167, 109)),
    TAIGA_HILLS_M(147, "Taiga Hills M", Color.fromRGB(103, 135, 134)),
    EXTREME_HILLS_EDGE_M(148, "Extreme Hills Edge M", Color.fromRGB(196, 203, 234)),
    JUNGLE_M(149, "Jungle M", Color.fromRGB(160, 203, 92)),
    JUNGLE_HILLS_M(150, "Jungle Hills M", Color.fromRGB(127, 146, 86)),
    JUNGLE_EDGE_M(151, "Jungle Edge M", Color.fromRGB(179, 217, 105)),
    DEEP_OCEAN_M(152, "Deep Ocean M", Color.fromRGB(82, 79, 130)),
    STONE_BEACH_M(153, "Stone Beach M", Color.fromRGB(242, 243, 209)),
    COLD_BEACH_M(154, "Cold Beach M", Color.fromRGB(255, 255, 255)),
    BIRCH_FOREST_M(155, "Birch Forest M", Color.fromRGB(131, 194, 148)),
    BIRCH_FOREST_HILLS_M(156, "Birch Forest Hills M", Color.fromRGB(111, 175, 133)),
    ROOFED_FOREST_M(157, "Roofed Forest M", Color.fromRGB(143, 158, 109)),
    COLD_TAIGA_M(158, "Cold Taiga M", Color.fromRGB(132, 163, 156)),
    COLD_TAIGA_HILLS_M(159, "Cold Taiga Hills M", Color.fromRGB(113, 143, 136)),
    MEGA_SPRUCE_TAIGA(160, "Mega Spruce Taiga", Color.fromRGB(168, 180, 164)),
    REDWOOD_TAIGA_HILLS_M(161, "Redwood Taiga Hills M", Color.fromRGB(150, 158, 140)),
    EXTREME_HILLS_PLUS_M(162, "Extreme Hills+ M", Color.fromRGB(161, 194, 158)),
    SAVANNA_M(163, "Savanna M", Color.fromRGB(255, 255, 173)),
    SAVANNA_PLATEAU_M(164, "Savanna Plateau M", Color.fromRGB(247, 238, 180)),
    MESA_BRYCE(165, "Mesa (Bryce)", Color.fromRGB(255, 151, 101)),
    MESA_PLATEAU_F_M(166, "Mesa Plateau F M", Color.fromRGB(255, 234, 179)),
    MESA_PLATEAU_M(167, "Mesa Plateau M", Color.fromRGB(255, 220, 184)),
    BAMBOO_JUNGLE(168, "Bamboo Jungle", Color.fromRGB(255, 220, 184)),
    BAMBOO_JUNGLE_HILLS(169, "Bamboo Jungle Hills", Color.fromRGB(255, 220, 184));

    public final int id;
    public final String name;
    public final Color color;

    Biome(int id, String name, Color color){
        this.id = id;
        this.name = name;
        this.color = color;
    }

    private static final SparseArray<Biome> biomeMap;
    static {
        biomeMap = new SparseArray<>();
        for(Biome b : Biome.values()){
            biomeMap.put(b.id, b);
        }
    }

    public static Biome getBiome(int id){
        return biomeMap.get(id);
    }
}
