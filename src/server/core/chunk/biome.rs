/**
 * Copyright © 2025 NguyenDuck
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
////////////////////////////////////////////////////////////////////////
use std::io::Error;

macro_rules! define_biome_id {
    (
        $(#[$meta:meta])*
        pub enum $name:ident {
            $($variant:ident = $id:literal,)*
        }
    ) => {
        $(#[$meta])*
        pub enum $name {
            $($variant,)*
        }

        impl $name {
            const ALL: &'static [$name] = &[$($name::$variant,)*];

            pub fn iter() -> impl Iterator<Item = &'static $name> {
                Self::ALL.iter()
            }

            pub fn to_i32(&self) -> i32 {
                match self {
                    $($name::$variant => $id,)*
                }
            }

            pub fn from_i32(id: i32) -> Result<Self, Error> {
                match id {
                    $($id => Ok($name::$variant),)*
                    _ => Err(Error::new(std::io::ErrorKind::InvalidData, format!("Unknown biome id: {}", id))),
                }
            }
        }
    };
}

define_biome_id! {
    #[derive(Debug, Clone, Copy, PartialEq, Eq, Hash)]
    pub enum BiomeId {
        Ocean = 0,
        Plains = 1,
        Desert = 2,
        ExtremeHills = 3,
        Forest = 4,
        Taiga = 5,
        Swampland = 6,
        River = 7,
        Hell = 8,
        TheEnd = 9,
        LegacyFrozenOcean = 10,
        FrozenRiver = 11,
        IcePlains = 12,
        IceMountains = 13,
        MushroomIsland = 14,
        MushroomIslandShore = 15,
        Beach = 16,
        DesertHills = 17,
        ForestHills = 18,
        TaigaHills = 19,
        ExtremeHillsEdge = 20,
        Jungle = 21,
        JungleHills = 22,
        JungleEdge = 23,
        DeepOcean = 24,
        StoneBeach = 25,
        ColdBeach = 26,
        BirchForest = 27,
        BirchForestHills = 28,
        RoofedForest = 29,
        ColdTaiga = 30,
        ColdTaigaHills = 31,
        MegaTaiga = 32,
        MegaTaigaHills = 33,
        ExtremeHillsPlusTrees = 34,
        Savanna = 35,
        SavannaPlateau = 36,
        Mesa = 37,
        MesaPlateauStone = 38,
        MesaPlateau = 39,
        WarmOcean = 40,
        DeepWarmOcean = 41,
        LukewarmOcean = 42,
        DeepLukewarmOcean = 43,
        ColdOcean = 44,
        DeepColdOcean = 45,
        FrozenOcean = 46,
        DeepFrozenOcean = 47,
        BambooJungle = 48,
        BambooJungleHills = 49,
        SunflowerPlains = 129,
        DesertMutated = 130,
        ExtremeHillsMutated = 131,
        FlowerForest = 132,
        TaigaMutated = 133,
        SwamplandMutated = 134,
        IcePlainsSpikes = 140,
        JungleMutated = 149,
        JungleEdgeMutated = 151,
        BirchForestMutated = 155,
        BirchForestHillsMutated = 156,
        RoofedForestMutated = 157,
        ColdTaigaMutated = 158,
        RedwoodTaigaMutated = 160,
        RedwoodTaigaHillsMutated = 161,
        ExtremeHillsPlusTreesMutated = 162,
        SavannaMutated = 163,
        SavannaPlateauMutated = 164,
        MesaBryce = 165,
        MesaPlateauStoneMutated = 166,
        MesaPlateauMutated = 167,
        SoulsandValley = 178,
        CrimsonForest = 179,
        WarpedForest = 180,
        BasaltDeltas = 181,
        JaggedPeaks = 182,
        FrozenPeaks = 183,
        SnowySlopes = 184,
        Grove = 185,
        Meadow = 186,
        LushCaves = 187,
        DripstoneCaves = 188,
        StonyPeaks = 189,
        DeepDark = 190,
        MangroveSwamp = 191,
        CherryGrove = 192,
        PaleGarden = 193,
    }
}

#[derive(Debug, Clone, PartialEq)]
pub struct Biome {
    id: BiomeId,
    temperature: f32,
    humidity: f32,
    resource_location: &'static str,
}

impl Biome {
    const fn new(
        id: BiomeId,
        temperature: f32,
        humidity: f32,
        resource_location: &'static str,
    ) -> Self {
        Self {
            id,
            temperature,
            humidity,
            resource_location,
        }
    }

    pub const ALL: &'static [Biome] = &[
        Self::new(BiomeId::BambooJungle, 0.9, 0.9, "bamboo_jungle"),
        Self::new(BiomeId::BambooJungleHills, 0.9, 0.9, "bamboo_jungle_hills"),
        Self::new(BiomeId::BasaltDeltas, 2.0, 0.0, "basalt_deltas"),
        Self::new(BiomeId::Beach, 0.8, 0.4, "beach"),
        Self::new(BiomeId::BirchForest, 0.6, 0.6, "birch_forest"),
        Self::new(BiomeId::BirchForestHills, 0.6, 0.6, "birch_forest_hills"),
        Self::new(
            BiomeId::BirchForestHillsMutated,
            0.7,
            0.8,
            "birch_forest_hills_mutated",
        ),
        Self::new(
            BiomeId::BirchForestMutated,
            0.6,
            0.6,
            "birch_forest_mutated",
        ),
        Self::new(BiomeId::CherryGrove, 0.3, 0.8, "cherry_grove"),
        Self::new(BiomeId::ColdBeach, 0.1, 0.3, "cold_beach"),
        Self::new(BiomeId::ColdOcean, 0.5, 0.5, "cold_ocean"),
        Self::new(BiomeId::ColdTaiga, -0.5, 0.4, "cold_taiga"),
        Self::new(BiomeId::ColdTaigaHills, -0.5, 0.4, "cold_taiga_hills"),
        Self::new(BiomeId::ColdTaigaMutated, -0.5, 0.4, "cold_taiga_mutated"),
        Self::new(BiomeId::CrimsonForest, 2.0, 0.0, "crimson_forest"),
        Self::new(BiomeId::DeepColdOcean, 0.5, 0.5, "deep_cold_ocean"),
        Self::new(BiomeId::DeepDark, 0.8, 0.4, "deep_dark"),
        Self::new(BiomeId::DeepFrozenOcean, 0.0, 0.5, "deep_frozen_ocean"),
        Self::new(BiomeId::DeepLukewarmOcean, 0.5, 0.5, "deep_lukewarm_ocean"),
        Self::new(BiomeId::DeepOcean, 0.5, 0.5, "deep_ocean"),
        Self::new(BiomeId::DeepWarmOcean, 0.5, 0.5, "deep_warm_ocean"),
        Self::new(BiomeId::Desert, 2.0, 0.0, "desert"),
        Self::new(BiomeId::DesertHills, 2.0, 0.0, "desert_hills"),
        Self::new(BiomeId::DesertMutated, 2.0, 0.0, "desert_mutated"),
        Self::new(BiomeId::DripstoneCaves, 0.2, 0.0, "dripstone_caves"),
        Self::new(BiomeId::ExtremeHills, 0.2, 0.3, "extreme_hills"),
        Self::new(BiomeId::ExtremeHillsEdge, 0.2, 0.3, "extreme_hills_edge"),
        Self::new(
            BiomeId::ExtremeHillsMutated,
            0.2,
            0.3,
            "extreme_hills_mutated",
        ),
        Self::new(
            BiomeId::ExtremeHillsPlusTrees,
            0.2,
            0.3,
            "extreme_hills_plus_trees",
        ),
        Self::new(
            BiomeId::ExtremeHillsPlusTreesMutated,
            0.2,
            0.3,
            "extreme_hills_plus_trees_mutated",
        ),
        Self::new(BiomeId::FlowerForest, 0.7, 0.8, "flower_forest"),
        Self::new(BiomeId::Forest, 0.7, 0.8, "forest"),
        Self::new(BiomeId::ForestHills, 0.7, 0.8, "forest_hills"),
        Self::new(BiomeId::FrozenOcean, 0.0, 0.5, "frozen_ocean"),
        Self::new(BiomeId::FrozenPeaks, -0.7, 0.9, "frozen_peaks"),
        Self::new(BiomeId::FrozenRiver, 0.0, 0.5, "frozen_river"),
        Self::new(BiomeId::Grove, -0.2, 0.8, "grove"),
        Self::new(BiomeId::Hell, 2.0, 0.0, "hell"),
        Self::new(BiomeId::IceMountains, 0.0, 0.5, "ice_mountains"),
        Self::new(BiomeId::IcePlains, 0.0, 0.5, "ice_plains"),
        Self::new(BiomeId::IcePlainsSpikes, 0.0, 1.0, "ice_plains_spikes"),
        Self::new(BiomeId::JaggedPeaks, -0.7, 0.9, "jagged_peaks"),
        Self::new(BiomeId::Jungle, 0.9, 0.9, "jungle"),
        Self::new(BiomeId::JungleEdge, 0.9, 0.8, "jungle_edge"),
        Self::new(BiomeId::JungleEdgeMutated, 0.9, 0.8, "jungle_edge_mutated"),
        Self::new(BiomeId::JungleHills, 0.9, 0.9, "jungle_hills"),
        Self::new(BiomeId::JungleMutated, 0.9, 0.9, "jungle_mutated"),
        Self::new(BiomeId::LegacyFrozenOcean, 0.0, 0.5, "legacy_frozen_ocean"),
        Self::new(BiomeId::LukewarmOcean, 0.5, 0.5, "lukewarm_ocean"),
        Self::new(BiomeId::LushCaves, 0.9, 0.0, "lush_caves"),
        Self::new(BiomeId::MangroveSwamp, 0.8, 0.9, "mangrove_swamp"),
        Self::new(BiomeId::Meadow, 0.3, 0.8, "meadow"),
        Self::new(BiomeId::MegaTaiga, 0.3, 0.8, "mega_taiga"),
        Self::new(BiomeId::MegaTaigaHills, 0.3, 0.8, "mega_taiga_hills"),
        Self::new(BiomeId::Mesa, 2.0, 0.0, "mesa"),
        Self::new(BiomeId::MesaBryce, 2.0, 0.0, "mesa_bryce"),
        Self::new(BiomeId::MesaPlateau, 2.0, 0.0, "mesa_plateau"),
        Self::new(
            BiomeId::MesaPlateauMutated,
            2.0,
            0.0,
            "mesa_plateau_mutated",
        ),
        Self::new(BiomeId::MesaPlateauStone, 2.0, 0.0, "mesa_plateau_stone"),
        Self::new(
            BiomeId::MesaPlateauStoneMutated,
            2.0,
            0.0,
            "mesa_plateau_stone_mutated",
        ),
        Self::new(BiomeId::MushroomIsland, 0.9, 1.0, "mushroom_island"),
        Self::new(
            BiomeId::MushroomIslandShore,
            0.9,
            1.0,
            "mushroom_island_shore",
        ),
        Self::new(BiomeId::Ocean, 0.5, 0.5, "ocean"),
        Self::new(BiomeId::PaleGarden, 0.7, 0.8, "pale_garden"),
        Self::new(BiomeId::Plains, 0.8, 0.4, "plains"),
        Self::new(
            BiomeId::RedwoodTaigaHillsMutated,
            0.3,
            0.8,
            "redwood_taiga_hills_mutated",
        ),
        Self::new(
            BiomeId::RedwoodTaigaMutated,
            0.3,
            0.8,
            "redwood_taiga_mutated",
        ),
        Self::new(BiomeId::River, 0.5, 0.5, "river"),
        Self::new(BiomeId::RoofedForest, 0.7, 0.8, "roofed_forest"),
        Self::new(
            BiomeId::RoofedForestMutated,
            0.7,
            0.8,
            "roofed_forest_mutated",
        ),
        Self::new(BiomeId::Savanna, 1.2, 0.0, "savanna"),
        Self::new(BiomeId::SavannaMutated, 2.0, 0.0, "savanna_mutated"),
        Self::new(BiomeId::SavannaPlateau, 1.0, 0.0, "savanna_plateau"),
        Self::new(
            BiomeId::SavannaPlateauMutated,
            1.0,
            0.5,
            "savanna_plateau_mutated",
        ),
        Self::new(BiomeId::SnowySlopes, -0.3, 0.9, "snowy_slopes"),
        Self::new(BiomeId::SoulsandValley, 2.0, 0.0, "soulsand_valley"),
        Self::new(BiomeId::StoneBeach, 0.2, 0.3, "stone_beach"),
        Self::new(BiomeId::StonyPeaks, 1.0, 0.3, "stony_peaks"),
        Self::new(BiomeId::SunflowerPlains, 0.8, 0.4, "sunflower_plains"),
        Self::new(BiomeId::Swampland, 0.8, 0.9, "swampland"),
        Self::new(BiomeId::SwamplandMutated, 0.8, 0.5, "swampland_mutated"),
        Self::new(BiomeId::Taiga, 0.3, 0.8, "taiga"),
        Self::new(BiomeId::TaigaHills, 0.3, 0.8, "taiga_hills"),
        Self::new(BiomeId::TaigaMutated, 0.3, 0.8, "taiga_mutated"),
        Self::new(BiomeId::TheEnd, 0.5, 0.5, "the_end"),
        Self::new(BiomeId::WarmOcean, 0.5, 0.5, "warm_ocean"),
        Self::new(BiomeId::WarpedForest, 2.0, 0.0, "warped_forest"),
    ];

    pub fn from_id(id: i32) -> Result<Self, Error> {
        Self::ALL
            .iter()
            .find(|biome| biome.id.to_i32() == id)
            .ok_or(Error::new(
                std::io::ErrorKind::InvalidData,
                format!("Unknown biome id: {}", id),
            ))
            .cloned()
    }

    pub fn id(&self) -> BiomeId {
        self.id
    }

    pub fn numeric_id(&self) -> i32 {
        self.id.to_i32()
    }

    pub fn temperature(&self) -> f32 {
        self.temperature
    }

    pub fn humidity(&self) -> f32 {
        self.humidity
    }

    pub fn resource_location(&self) -> &'static str {
        self.resource_location
    }
}

impl Default for Biome {
    fn default() -> Self {
        Self::from_id(1).unwrap()
    }
}

macro_rules! cast_biome_id_from_i32 {
    ($($t:ty),*) => {
        $(
            impl Into<$t> for Biome {
                fn into(self) -> $t {
                    self.numeric_id() as $t
                }
            }
        )*
    };
}

cast_biome_id_from_i32!(u8, i8, u16, i16, u32, i32, u64, i64);
