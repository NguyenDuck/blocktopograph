package com.mithrilmania.blocktopograph.map;

import android.util.SparseArray;

import com.mithrilmania.blocktopograph.util.ColorWrapper;


/*
Biome enum for MCPE -- by @mithrilmania
Reference link: https://minecraft.fandom.com/wiki/Biome/ID

--- Please attribute @mithrilmania for generating+updating this enum
 */
public enum Biome {

	OCEAN(0, "Ocean", ColorWrapper.fromRGB(2, 0, 112)),
	PLAINS(1, "Plains", ColorWrapper.fromRGB(140, 176, 96)),
	DESERT(2, "Desert", ColorWrapper.fromRGB(251, 148, 27)),
	EXTREME_HILLS(3, "Mountains", ColorWrapper.fromRGB(93, 99, 93)),
	FOREST(4, "Forest", ColorWrapper.fromRGB(2, 99, 32)),
	TAIGA(5, "Taiga", ColorWrapper.fromRGB(9, 102, 91)),
	SWAMPLAND(6, "Swampland", ColorWrapper.fromRGB(4, 200, 139)),
	RIVER(7, "River", ColorWrapper.fromRGB(1, 1, 255)),
	HELL(8, "Nether Wastes", ColorWrapper.fromRGB(132, 65, 65)),
	THE_END(9, "The End", ColorWrapper.fromRGB(130, 129, 254)),
	FROZEN_OCEAN(10, "Frozen Ocean", ColorWrapper.fromRGB(142, 141, 161)),
	FROZEN_RIVER(11, "Frozen River", ColorWrapper.fromRGB(159, 163, 255)),
	ICE_PLAINS(12, "Ice Plains", ColorWrapper.fromRGB(255, 254, 255)),
	ICE_MOUNTAINS(13, "Ice Mountains", ColorWrapper.fromRGB(162, 157, 157)),
	MUSHROOM_ISLAND(14, "Mushroom Fields", ColorWrapper.fromRGB(254, 1, 255)),
	MUSHROOM_ISLAND_SHORE(15, "Mushroom Fields Shore", ColorWrapper.fromRGB(158, 3, 253)),
	BEACH(16, "Beach", ColorWrapper.fromRGB(250, 223, 85)),
	DESERT_HILLS(17, "Desert Hills", ColorWrapper.fromRGB(212, 94, 15)),
	FOREST_HILLS(18, "Wooded Hills", ColorWrapper.fromRGB(37, 86, 30)),
	TAIGA_HILLS(19, "Taiga Hills", ColorWrapper.fromRGB(25, 54, 49)),
	EXTREME_HILLS_EDGE(20, "Mountain Edge", ColorWrapper.fromRGB(115, 118, 157)),
	JUNGLE(21, "Jungle", ColorWrapper.fromRGB(82, 122, 7)),
	JUNGLE_HILLS(22, "Jungle Hills", ColorWrapper.fromRGB(46, 64, 3)),
	JUNGLE_EDGE(23, "Jungle Edge", ColorWrapper.fromRGB(99, 142, 24)),
	DEEP_OCEAN(24, "Deep Ocean", ColorWrapper.fromRGB(2, 0, 47)),
	STONE_BEACH(25, "Stone Shore", ColorWrapper.fromRGB(162, 164, 132)),
	COLD_BEACH(26, "Snowy Beach", ColorWrapper.fromRGB(250, 238, 193)),
	BIRCH_FOREST(27, "Birch Forest", ColorWrapper.fromRGB(48, 117, 70)),
	BIRCH_FOREST_HILLS(28, "Birch Forest Hills", ColorWrapper.fromRGB(29, 94, 51)),
	ROOFED_FOREST(29, "Dark Forest", ColorWrapper.fromRGB(66, 82, 24)),
	COLD_TAIGA(30, "Snowy Taiga", ColorWrapper.fromRGB(49, 85, 75)),
	COLD_TAIGA_HILLS(31, "Snowy Taiga Hills", ColorWrapper.fromRGB(34, 61, 52)),
	MEGA_TAIGA(32, "Giant Tree Taiga", ColorWrapper.fromRGB(92, 105, 84)),
	MEGA_TAIGA_HILLS(33, "Giant Tree Taiga Hills", ColorWrapper.fromRGB(70, 76, 59)),
	EXTREME_HILLS_PLUS_TREE(34, "Wooded Mountains", ColorWrapper.fromRGB(79, 111, 81)),
	SAVANNA(35, "Savanna", ColorWrapper.fromRGB(192, 180, 94)),
	SAVANNA_PLATEAU(36, "Savanna Plateau", ColorWrapper.fromRGB(168, 157, 98)),
	MESA(37, "Badlands", ColorWrapper.fromRGB(220, 66, 19)),
	MESA_PLATEAU(38, "Badlands Plateau", ColorWrapper.fromRGB(174, 152, 100)),
	MESA_PLATEAU_STONE(39, "Wooded Badlands Plateau", ColorWrapper.fromRGB(202, 139, 98)),

	//fix the colors for these
	WARM_OCEAN(40, "Warm Ocean", ColorWrapper.fromRGB(202, 139, 98)),
	LUKEWARM_OCEAN(41, "Lukewarm Ocean", ColorWrapper.fromRGB(202, 139, 98)),
	COLD_OCEAN(42, "Cold Ocean", ColorWrapper.fromRGB(202, 139, 98)),
	DEEP_WARM_OCEAN(43, "Deep Warm Ocean", ColorWrapper.fromRGB(202, 139, 98)),
	DEEP_LUKEWARM_OCEAN(44, "Deep Lukewarm Ocean", ColorWrapper.fromRGB(202, 139, 98)),
	DEEP_COLD_OCEAN(45, "Deep Cold Ocean", ColorWrapper.fromRGB(202, 139, 98)),
	DEEP_FROZEN_OCEAN(46, "Deep Frozen Ocean", ColorWrapper.fromRGB(202, 139, 98)),
	LEGACY_FROZEN_OCEAN(47, "Legacy Frozen Ocean", ColorWrapper.fromRGB(202, 139, 98)),

	//  Not sure are available ( need tester )
//	OCEAN_M(128, "Ocean M", ColorWrapper.fromRGB(81, 79, 195)),

	SUNFLOWER_PLAINS(129, "Sunflower Plains", ColorWrapper.fromRGB(220, 255, 177)),
	DESERT_MUTATED(130, "Desert Lakes", ColorWrapper.fromRGB(255, 230, 101)),
	EXTREME_HILLS_MUTATED(131, "Gravelly Mountains", ColorWrapper.fromRGB(177, 176, 174)),
	FLOWER_FOREST(132, "Flower Forest", ColorWrapper.fromRGB(82, 180, 110)),
	TAIGA_MUTATED(133, "Taiga Mountains", ColorWrapper.fromRGB(90, 182, 171)),
	SWAMPLAND_MUTATED(134, "Swamp Hills", ColorWrapper.fromRGB(87, 255, 255)),

	// Not sure available ( need tester )
//	RIVER_M(135, "River M", ColorWrapper.fromRGB(82, 79, 255)),
//	HELL_M(136, "Hell M", ColorWrapper.fromRGB(255, 80, 83)),
//	SKY_M(137, "Sky M", ColorWrapper.fromRGB(210, 211, 255)),
//	FROZEN_OCEAN_M(138, "Frozen Ocean M", ColorWrapper.fromRGB(226, 224, 241)),
//	FROZEN_RIVER_M(139, "Frozen River M", ColorWrapper.fromRGB(239, 242, 255)),

	ICE_PLAINS_SPIKES(140, "Ice Spikes", ColorWrapper.fromRGB(223, 255, 255)),

	// Not sure available ( need tester )
//	ICE_MOUNTAINS_M(141, "Ice Mountains M", ColorWrapper.fromRGB(237, 237, 238)),
//	MUSHROOM_ISLAND_M(142, "Mushroom Island M", ColorWrapper.fromRGB(255, 82, 255)),
//	MUSHROOM_ISLAND_SHORE_M(143, "Mushroom Island Shore M", ColorWrapper.fromRGB(243, 82, 255)),
//	BEACH_M(144, "Beach M", ColorWrapper.fromRGB(255, 255, 162)),
//	DESERT_HILLS_M(145, "Desert Hills M", ColorWrapper.fromRGB(255, 177, 100)),
//	FOREST_HILLS_M(146, "Forest Hills M", ColorWrapper.fromRGB(113, 167, 109)),
//	TAIGA_HILLS_M(147, "Taiga Hills M", ColorWrapper.fromRGB(103, 135, 134)),
//	EXTREME_HILLS_EDGE_M(148, "Extreme Hills Edge M", ColorWrapper.fromRGB(196, 203, 234)),

	JUNGLE_MUTATED(149, "Modified Jungle", ColorWrapper.fromRGB(160, 203, 92)),
//	JUNGLE_HILLS_M(150, "Jungle Hills M", ColorWrapper.fromRGB(127, 146, 86)),
	JUNGLE_EDGE_MUTATED(151, "Modified Jungle Edge", ColorWrapper.fromRGB(179, 217, 105)),
//	DEEP_OCEAN_M(152, "Deep Ocean M", ColorWrapper.fromRGB(82, 79, 130)),
//	STONE_BEACH_M(153, "Stone Beach M", ColorWrapper.fromRGB(242, 243, 209)),
//	COLD_BEACH_M(154, "Cold Beach M", ColorWrapper.fromRGB(255, 255, 255)),
	BIRCH_FOREST_MUTATED(155, "Tall Birch Forest", ColorWrapper.fromRGB(131, 194, 148)),
	BIRCH_FOREST_HILLS_MUTATED(156, "Tall Birch Hills", ColorWrapper.fromRGB(111, 175, 133)),
	ROOFED_FOREST_MUTATED(157, "Dark Forest Hills", ColorWrapper.fromRGB(143, 158, 109)),
	COLD_TAIGA_MUTATED(158, "Snowy Taiga Mountains", ColorWrapper.fromRGB(132, 163, 156)),
//	COLD_TAIGA_HILLS_M(159, "Cold Taiga Hills M", ColorWrapper.fromRGB(113, 143, 136)),
	REDWOOD_TAIGA_MUTATED(160, "Giant Spruce Taiga", ColorWrapper.fromRGB(168, 180, 164)),
	REDWOOD_TAIGA_HILLS_MUTATED(161, "Giant Spruce Taiga Hills", ColorWrapper.fromRGB(150, 158, 140)),
	EXTREME_HILLS_PLUS_TREE_MUTATED(162, "Gravelly Mountains", ColorWrapper.fromRGB(161, 194, 158)),
	SAVANNA_MUTATED(163, "Shattered Savanna", ColorWrapper.fromRGB(255, 255, 173)),
	SAVANNA_PLATEAU_MUTATED(164, "Shattered Savanna Plateau", ColorWrapper.fromRGB(247, 238, 180)),
	MESA_BRYCE(165, "Eroded Badlands", ColorWrapper.fromRGB(255, 151, 101)),
	MESA_PLATEAU_MUTATED(166, "Modified Badlands Plateau", ColorWrapper.fromRGB(255, 234, 179)),
	MESA_PLATEAU_STONE_MUTATED(167, "Modified Wooded Badlands Plateau", ColorWrapper.fromRGB(255, 220, 184)),
	BAMBOO_JUNGLE(168, "Bamboo Jungle", ColorWrapper.fromRGB(255, 220, 184)),
	BAMBOO_JUNGLE_HILLS(169, "Bamboo Jungle Hills", ColorWrapper.fromRGB(255, 220, 184)),

	// 1.16 BIOME
	SOUL_SAND_VALLEY(178, "Soul Sand Valley", ColorWrapper.fromRGB(66, 113, 114)),
	CRIMSON_FOREST(179, "Crimson Forest", ColorWrapper.fromRGB(141, 30, 40)),
	WARPED_FOREST(180, "Warped Forest", ColorWrapper.fromRGB(22, 126, 134)),
	BASALT_DELTAS(181, "Basalt Deltas", ColorWrapper.fromRGB(75, 69, 71)),

	// 1.17 BIOME ?
	JAGGED_PEAKS(182, "Jagged Peaks", ColorWrapper.fromRGB(0, 0, 0)),
	FROZEN_PEAKS(183, "Frozen Peaks", ColorWrapper.fromRGB(0, 0, 0)),
	SNOWY_SLOPES(184, "Snowy Slopes", ColorWrapper.fromRGB(0, 0, 0)),
	GROVE(185, "Grove", ColorWrapper.fromRGB(0, 0, 0)),
	MEADOW(186, "Meadow", ColorWrapper.fromRGB(0, 0, 0)),
	LUSH_CAVES(187, "Lush Caves", ColorWrapper.fromRGB(0, 0, 0)),
	DRIPSTONE_CAVES(188, "Dripstone Caves", ColorWrapper.fromRGB(0, 0, 0)),
	STONY_PEAKS(189, "Stony Peaks", ColorWrapper.fromRGB(0, 0, 0))
	;

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
