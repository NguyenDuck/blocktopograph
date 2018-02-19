package com.mithrilmania.blocktopograph.map.renderer;

import com.qozix.tileview.detail.DetailLevelManager;


public enum MapType implements DetailLevelManager.LevelType {

    //shows that a chunk was present, but couldn't be renderer
    ERROR(new ChessPatternRenderer(0xFF2B0000, 0xFF580000)),

    //simple xor pattern renderer
    DEBUG(new DebugRenderer()),

    //simple chess pattern renderer
    CHESS(new ChessPatternRenderer(0xFF2B2B2B, 0xFF585858)),

    //just the surface of the world, with shading for height diff
    OVERWORLD_SATELLITE(new SatelliteRenderer()),

    //cave mapping
    OVERWORLD_CAVE(new CaveRenderer()),

    OVERWORLD_SLIME_CHUNK(new SlimeChunkRenderer()),

    //render skylight value of highest block
    OVERWORLD_HEIGHTMAP(new HeightmapRenderer()),

    //render biome id as biome-unique color
    OVERWORLD_BIOME(new BiomeRenderer()),

    //render the voliage colors
    OVERWORLD_GRASS(new GrassRenderer()),

    //render only the valuable blocks to mine (diamonds, emeralds, gold, etc.)
    OVERWORLD_XRAY(new XRayRenderer()),

    //block-light renderer: from light-sources like torches etc.
    OVERWORLD_BLOCK_LIGHT(new BlockLightRenderer()),

    NETHER(new NetherRenderer()),

    NETHER_XRAY(new XRayRenderer()),

    NETHER_BLOCK_LIGHT(new BlockLightRenderer()),

    END_SATELLITE(new SatelliteRenderer()),

    END_HEIGHTMAP(new HeightmapRenderer()),

    END_BLOCK_LIGHT(new BlockLightRenderer());

    //REDSTONE //TODO redstone circuit mapping
    //TRAFFIC //TODO traffic mapping (land = green, water = blue, gravel/stone/etc. = gray, rails = yellow)


    public final MapRenderer renderer;

    MapType(MapRenderer renderer){
        this.renderer = renderer;
    }

}
