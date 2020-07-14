package com.mithrilmania.blocktopograph.map;

import android.util.SparseArray;

import com.mithrilmania.blocktopograph.util.ColorWrapper;



/*
Biome enum for MCPE -- by @mithrilmania

--- Please attribute @mithrilmania for generating+updating this enum
 */
public enum Biome {

    OCEAN(0, "Ocean", ColorWrapper.fromRGB(2, 0, 112)),
    PLAINS(1, "Plains", ColorWrapper.fromRGB(140, 176, 96)),
    DESERT(2, "Desert", ColorWrapper.fromRGB(251, 148, 27)),
    EXTREME_HILLS(3, "Extreme Hills", ColorWrapper.fromRGB(93, 99, 93)),
    FOREST(4, "Forest", ColorWrapper.fromRGB(2, 99, 32)),
    TAIGA(5, "Taiga", ColorWrapper.fromRGB(9, 102, 91)),
    SWAMPLAND(6, "Swampland", ColorWrapper.fromRGB(4, 200, 139)),
    RIVER(7, "River", ColorWrapper.fromRGB(1, 1, 255)),
    HELL(8, "Hell", ColorWrapper.fromRGB(255, 0, 1)),
    SKY(9, "Sky", ColorWrapper.fromRGB(130, 129, 254)),
    FROZEN_OCEAN(10, "Frozen Ocean", ColorWrapper.fromRGB(142, 141, 161)),
    FROZEN_RIVER(11, "Frozen River", ColorWrapper.fromRGB(159, 163, 255)),
    ICE_PLAINS(12, "Ice Plains", ColorWrapper.fromRGB(255, 254, 255)),
    ICE_MOUNTAINS(13, "Ice Mountains", ColorWrapper.fromRGB(162, 157, 157)),
    MUSHROOM_ISLAND(14, "Mushroom Island", ColorWrapper.fromRGB(254, 1, 255)),
    MUSHROOM_ISLAND_SHORE(15, "Mushroom Island Shore", ColorWrapper.fromRGB(158, 3, 253)),
    BEACH(16, "Beach", ColorWrapper.fromRGB(250, 223, 85)),
    DESERT_HILLS(17, "Desert Hills", ColorWrapper.fromRGB(212, 94, 15)),
    FOREST_HILLS(18, "Forest Hills", ColorWrapper.fromRGB(37, 86, 30)),
    TAIGA_HILLS(19, "Taiga Hills", ColorWrapper.fromRGB(25, 54, 49)),
    EXTREME_HILLS_EDGE(20, "Extreme Hills Edge", ColorWrapper.fromRGB(115, 118, 157)),
    JUNGLE(21, "Jungle", ColorWrapper.fromRGB(82, 122, 7)),
    JUNGLE_HILLS(22, "Jungle Hills", ColorWrapper.fromRGB(46, 64, 3)),
    JUNGLE_EDGE(23, "Jungle Edge", ColorWrapper.fromRGB(99, 142, 24)),
    DEEP_OCEAN(24, "Deep Ocean", ColorWrapper.fromRGB(2, 0, 47)),
    STONE_BEACH(25, "Stone Beach", ColorWrapper.fromRGB(162, 164, 132)),
    COLD_BEACH(26, "Cold Beach", ColorWrapper.fromRGB(250, 238, 193)),
    BIRCH_FOREST(27, "Birch Forest", ColorWrapper.fromRGB(48, 117, 70)),
    BIRCH_FOREST_HILLS(28, "Birch Forest Hills", ColorWrapper.fromRGB(29, 94, 51)),
    ROOFED_FOREST(29, "Roofed Forest", ColorWrapper.fromRGB(66, 82, 24)),
    COLD_TAIGA(30, "Cold Taiga", ColorWrapper.fromRGB(49, 85, 75)),
    COLD_TAIGA_HILLS(31, "Cold Taiga Hills", ColorWrapper.fromRGB(34, 61, 52)),
    MEGA_TAIGA(32, "Mega Taiga", ColorWrapper.fromRGB(92, 105, 84)),
    MEGA_TAIGA_HILLS(33, "Mega Taiga Hills", ColorWrapper.fromRGB(70, 76, 59)),
    EXTREME_HILLS_PLUS(34, "Extreme Hills+", ColorWrapper.fromRGB(79, 111, 81)),
    SAVANNA(35, "Savanna", ColorWrapper.fromRGB(192, 180, 94)),
    SAVANNA_PLATEAU(36, "Savanna Plateau", ColorWrapper.fromRGB(168, 157, 98)),
    MESA(37, "Mesa", ColorWrapper.fromRGB(220, 66, 19)),
    MESA_PLATEAU_F(38, "Mesa Plateau F", ColorWrapper.fromRGB(174, 152, 100)),
    MESA_PLATEAU(39, "Mesa Plateau", ColorWrapper.fromRGB(202, 139, 98)),

    //fix the colors for these
    SMALL_END_ISLANDS(40, "Small End Islands", ColorWrapper.fromRGB(202, 139, 98)),
    END_MIDLANDS(41, "End Midlands", ColorWrapper.fromRGB(202, 139, 98)),
    END_HIGHLANDS(42, "End Highlands", ColorWrapper.fromRGB(202, 139, 98)),
    END_BARRENS(43, "End Barrens", ColorWrapper.fromRGB(202, 139, 98)),
    WARM_OCEAN(44, "Warm Ocean", ColorWrapper.fromRGB(202, 139, 98)),
    LUKEWARM_OCEAN(45, "Lukewarm Ocean", ColorWrapper.fromRGB(202, 139, 98)),
    COLD_OCEAN(46, "Cold Ocean", ColorWrapper.fromRGB(202, 139, 98)),
    DEEP_WARM_OCEAN(47, "Deep Warm Ocean", ColorWrapper.fromRGB(202, 139, 98)),
    DEEP_LUKEWARM_OCEAN(48, "Deep Lukewarm Ocean", ColorWrapper.fromRGB(202, 139, 98)),
    DEEP_COLD_OCEAN(49, "Deep Cold Ocean", ColorWrapper.fromRGB(202, 139, 98)),
    DEEP_FROZEN_OCEAN(50, "Deep Frozen Ocean", ColorWrapper.fromRGB(202, 139, 98)),

    //fix the colors for the void
    THE_VOID(127, "The Void", ColorWrapper.fromRGB(81, 79, 195)),

    OCEAN_M(128, "Ocean M", ColorWrapper.fromRGB(81, 79, 195)),
    SUNFLOWER_PLAINS(129, "Sunflower Plains", ColorWrapper.fromRGB(220, 255, 177)),
    DESERT_M(130, "Desert M", ColorWrapper.fromRGB(255, 230, 101)),
    EXTREME_HILLS_M(131, "Extreme Hills M", ColorWrapper.fromRGB(177, 176, 174)),
    FLOWER_FOREST(132, "Flower Forest", ColorWrapper.fromRGB(82, 180, 110)),
    TAIGA_M(133, "Taiga M", ColorWrapper.fromRGB(90, 182, 171)),
    SWAMPLAND_M(134, "Swampland M", ColorWrapper.fromRGB(87, 255, 255)),
    RIVER_M(135, "River M", ColorWrapper.fromRGB(82, 79, 255)),
    HELL_M(136, "Hell M", ColorWrapper.fromRGB(255, 80, 83)),
    SKY_M(137, "Sky M", ColorWrapper.fromRGB(210, 211, 255)),
    FROZEN_OCEAN_M(138, "Frozen Ocean M", ColorWrapper.fromRGB(226, 224, 241)),
    FROZEN_RIVER_M(139, "Frozen River M", ColorWrapper.fromRGB(239, 242, 255)),
    ICE_PLAINS_SPIKES(140, "Ice Plains Spikes", ColorWrapper.fromRGB(223, 255, 255)),
    ICE_MOUNTAINS_M(141, "Ice Mountains M", ColorWrapper.fromRGB(237, 237, 238)),
    MUSHROOM_ISLAND_M(142, "Mushroom Island M", ColorWrapper.fromRGB(255, 82, 255)),
    MUSHROOM_ISLAND_SHORE_M(143, "Mushroom Island Shore M", ColorWrapper.fromRGB(243, 82, 255)),
    BEACH_M(144, "Beach M", ColorWrapper.fromRGB(255, 255, 162)),
    DESERT_HILLS_M(145, "Desert Hills M", ColorWrapper.fromRGB(255, 177, 100)),
    FOREST_HILLS_M(146, "Forest Hills M", ColorWrapper.fromRGB(113, 167, 109)),
    TAIGA_HILLS_M(147, "Taiga Hills M", ColorWrapper.fromRGB(103, 135, 134)),
    EXTREME_HILLS_EDGE_M(148, "Extreme Hills Edge M", ColorWrapper.fromRGB(196, 203, 234)),
    JUNGLE_M(149, "Jungle M", ColorWrapper.fromRGB(160, 203, 92)),
    JUNGLE_HILLS_M(150, "Jungle Hills M", ColorWrapper.fromRGB(127, 146, 86)),
    JUNGLE_EDGE_M(151, "Jungle Edge M", ColorWrapper.fromRGB(179, 217, 105)),
    DEEP_OCEAN_M(152, "Deep Ocean M", ColorWrapper.fromRGB(82, 79, 130)),
    STONE_BEACH_M(153, "Stone Beach M", ColorWrapper.fromRGB(242, 243, 209)),
    COLD_BEACH_M(154, "Cold Beach M", ColorWrapper.fromRGB(255, 255, 255)),
    BIRCH_FOREST_M(155, "Birch Forest M", ColorWrapper.fromRGB(131, 194, 148)),
    BIRCH_FOREST_HILLS_M(156, "Birch Forest Hills M", ColorWrapper.fromRGB(111, 175, 133)),
    ROOFED_FOREST_M(157, "Roofed Forest M", ColorWrapper.fromRGB(143, 158, 109)),
    COLD_TAIGA_M(158, "Cold Taiga M", ColorWrapper.fromRGB(132, 163, 156)),
    COLD_TAIGA_HILLS_M(159, "Cold Taiga Hills M", ColorWrapper.fromRGB(113, 143, 136)),
    MEGA_SPRUCE_TAIGA(160, "Mega Spruce Taiga", ColorWrapper.fromRGB(168, 180, 164)),
    REDWOOD_TAIGA_HILLS_M(161, "Redwood Taiga Hills M", ColorWrapper.fromRGB(150, 158, 140)),
    EXTREME_HILLS_PLUS_M(162, "Extreme Hills+ M", ColorWrapper.fromRGB(161, 194, 158)),
    SAVANNA_M(163, "Savanna M", ColorWrapper.fromRGB(255, 255, 173)),
    SAVANNA_PLATEAU_M(164, "Savanna Plateau M", ColorWrapper.fromRGB(247, 238, 180)),
    MESA_BRYCE(165, "Mesa (Bryce)", ColorWrapper.fromRGB(255, 151, 101)),
    MESA_PLATEAU_F_M(166, "Mesa Plateau F M", ColorWrapper.fromRGB(255, 234, 179)),
    MESA_PLATEAU_M(167, "Mesa Plateau M", ColorWrapper.fromRGB(255, 220, 184)),
    BAMBOO_JUNGLE(168, "Bamboo Jungle", ColorWrapper.fromRGB(255, 220, 184)),
    BAMBOO_JUNGLE_HILLS(169, "Bamboo Jungle Hills", ColorWrapper.fromRGB(255, 220, 184));
    
    //Nether biome in 1.16.0 nether update.
    NETHER_WASTES(8, "Nether Wastes", ColorWrapper.fromRGB(132, 65, 65));
    
    SOUL_SAND_VALLEY(170, "Soul Sand Valley", ColorWrapper.fromRGB(66, 113, 114));
    
    CRIMSON_FOREST(171, "Crimson Forest", ColorWrapper.fromRGB(141, 30, 40));
    
    WARPED_FOREST(172, "Warped Forest", ColorWrapper.fromRGB(22, 126, 134));
    
    BASALT_DELTAS(173, "Basalt Deltas", ColorWrapper.fromRGB(75, 69, 71));

    private static final SparseArray<Biome> biomeMap;

    static {
        biomeMap = new SparseArray<>();
        for (Biome b : Biome.values()) {
            biomeMap.put(b.id, b);
        }
    }

    public final int id;
    public final String name;
    public final ColorWrapper color;

    Biome(int id, String name, ColorWrapper color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public static Biome getBiome(int id) {
        return biomeMap.get(id);
    }


    public String getName() {
        return name;
    }
}
