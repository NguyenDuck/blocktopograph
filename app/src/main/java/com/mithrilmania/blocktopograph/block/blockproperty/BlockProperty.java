package com.mithrilmania.blocktopograph.block.blockproperty;

import androidx.annotation.Nullable;

import com.mithrilmania.blocktopograph.nbt.convert.NBTConstants;

import java.util.HashMap;
import java.util.Map;

public enum BlockProperty {
    AGE_BIT("age_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    ALLOW_UNDERWATER_BIT("allow_underwater_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    ATTACHED_BIT("attached_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    BREWING_STAND_SLOT_A_BIT("brewing_stand_slot_a_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    BREWING_STAND_SLOT_B_BIT("brewing_stand_slot_b_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    BREWING_STAND_SLOT_C_BIT("brewing_stand_slot_c_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    BUTTON_PRESSED_BIT("button_pressed_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    CONDITIONAL_BIT("conditional_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    CORAL_HANG_TYPE_BIT("coral_hang_type_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    COVERED_BIT("covered_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    DEAD_BIT("dead_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    DISARMED_BIT("disarmed_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    DOOR_HINGE_BIT("door_hinge_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    END_PORTAL_EYE_BIT("end_portal_eye_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    EXPLODE_BIT("explode_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    HEAD_PIECE_BIT("head_piece_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    IN_WALL_BIT("in_wall_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    INFINIBURN_BIT("infiniburn_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    ITEM_FRAME_MAP_BIT("item_frame_map_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    NO_DROP_BIT("no_drop_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    OCCUPIED_BIT("occupied_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    OPEN_BIT("open_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    OUTPUT_LIT_BIT("output_lit_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    OUTPUT_SUBTRACT_BIT("output_subtract_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    PERSISTENT_BIT("persistent_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    POWERED_BIT("powered_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    RAIL_DATA_BIT("rail_data_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    SUSPENDED_BIT("suspended_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    TOGGLE_BIT("toggle_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    TOP_SLOT_BIT("top_slot_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    TRIGGERED_BIT("triggered_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    UPDATE_BIT("update_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    UPPER_BLOCK_BIT("upper_block_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    UPSIDE_DOWN_BIT("upside_down_bit", NBTConstants.NBTType.BYTE, new BitValuesRange()),
    AGE("age", NBTConstants.NBTType.INT, new EnumValuesRange(new Integer[]{0})),
    BITE_COUNTER("bite_counter", NBTConstants.NBTType.INT, new EnumValuesRange(new Integer[]{0})),
    CLUSTER_COUNT("cluster_count", NBTConstants.NBTType.INT, new EnumValuesRange(new Integer[]{0, 1, 2, 3})),
    CORAL_DIRECTION("coral_direction", NBTConstants.NBTType.INT, new EnumValuesRange(new Integer[]{0, 1, 2, 3})),
    CORAL_FAN_DIRECTION("coral_fan_direction", NBTConstants.NBTType.INT, new EnumValuesRange(new Integer[]{0, 1})),
    DEPRECATED("deprecated", NBTConstants.NBTType.INT, new EnumValuesRange(new Integer[]{0})),
    DIRECTION("direction", NBTConstants.NBTType.INT, new EnumValuesRange(new Integer[]{0, 1, 2, 3})),
    FACING_DIRECTION("facing_direction", NBTConstants.NBTType.INT, new EnumValuesRange(new Integer[]{0, 1, 2, 3, 4, 5})),
    FILL_LEVEL("fill_level", NBTConstants.NBTType.INT, new EnumValuesRange(new Integer[]{0})),
    GROUND_SIGN_DIRECTION("ground_sign_direction", NBTConstants.NBTType.INT, new EnumValuesRange(new Integer[]{0})),
    GROWTH("growth", NBTConstants.NBTType.INT, new EnumValuesRange(new Integer[]{0})),
    HEIGHT("height", NBTConstants.NBTType.INT, new EnumValuesRange(new Integer[]{0})),
    HUGE_MUSHROOM_BITS("huge_mushroom_bits", NBTConstants.NBTType.INT, new EnumValuesRange(new Integer[]{0})),
    KELP_AGE("kelp_age", NBTConstants.NBTType.INT, new EnumValuesRange(new Integer[]{0, 1, 10, 11, 12, 13, 14, 15, 2, 3, 4, 5, 6, 7, 8, 9})),
    LIQUID_DEPTH("liquid_depth", NBTConstants.NBTType.INT, new EnumValuesRange(new Integer[]{0})),
    MOISTURIZED_AMOUNT("moisturized_amount", NBTConstants.NBTType.INT, new EnumValuesRange(new Integer[]{0})),
    RAIL_DIRECTION("rail_direction", NBTConstants.NBTType.INT, new EnumValuesRange(new Integer[]{0})),
    REDSTONE_SIGNAL("redstone_signal", NBTConstants.NBTType.INT, new EnumValuesRange(new Integer[]{0, 1, 10, 11, 12, 13, 14, 15, 2, 3, 4, 5, 6, 7, 8, 9})),
    REPEATER_DELAY("repeater_delay", NBTConstants.NBTType.INT, new EnumValuesRange(new Integer[]{0})),
    VINE_DIRECTION_BITS("vine_direction_bits", NBTConstants.NBTType.INT, new EnumValuesRange(new Integer[]{0})),
    WEIRDO_DIRECTION("weirdo_direction", NBTConstants.NBTType.INT, new EnumValuesRange(new Integer[]{0, 1, 2, 3})),
    CAULDRON_LIQUID("cauldron_liquid", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"water"})),
    CHEMISTRY_TABLE_TYPE("chemistry_table_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"compound_creator", "element_constructor", "lab_table", "material_reducer"})),
    CHISEL_TYPE("chisel_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"chiseled", "default", "lines", "smooth"})),
    COLOR("color", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"black", "blue", "brown", "cyan", "gray", "green", "light_blue", "lime", "magenta", "orange", "pink", "purple", "red", "silver", "white", "yellow"})),
    CORAL_COLOR("coral_color", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"blue", "pink", "purple", "red", "yellow"})),
    DAMAGE("damage", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"slightly_damaged", "undamaged", "very_damaged"})),
    DIRT_TYPE("dirt_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"normal"})),
    DOUBLE_PLANT_TYPE("double_plant_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"fern", "grass", "paeonia", "rose", "sunflower", "syringa"})),
    FLOWER_TYPE("flower_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"allium", "houstonia", "orchid", "oxeye", "poppy", "tulip_orange", "tulip_pink", "tulip_red", "tulip_white"})),
    LEVER_DIRECTION("lever_direction", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"down_east_west"})),
    MONSTER_EGG_STONE_TYPE("monster_egg_stone_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"chiseled_stone_brick", "cobblestone", "cracked_stone_brick", "mossy_stone_brick", "stone", "stone_brick"})),
    NEW_LEAF_TYPE("new_leaf_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"acacia", "dark_oak"})),
    NEW_LOG_TYPE("new_log_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"acacia", "dark_oak"})),
    OLD_LEAF_TYPE("old_leaf_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"birch", "jungle", "oak", "spruce"})),
    OLD_LOG_TYPE("old_log_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"birch", "jungle", "oak", "spruce"})),
    PILLAR_AXIS("pillar_axis", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"x", "y", "z"})),
    PORTAL_AXIS("portal_axis", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"unknown"})),
    PRISMARINE_BLOCK_TYPE("prismarine_block_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"bricks", "dark", "default"})),
    SAND_STONE_TYPE("sand_stone_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"cut", "default", "heiroglyphs"})),
    SAND_TYPE("sand_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"normal", "red"})),
    SAPLING_TYPE("sapling_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"acacia", "birch", "dark_oak", "jungle", "oak", "spruce"})),
    SEA_GRASS_TYPE("sea_grass_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"default", "double_bot", "double_top"})),
    SPONGE_TYPE("sponge_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"dry", "wet"})),
    STONE_BRICK_TYPE("stone_brick_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"chiseled", "cracked", "default", "mossy", "smooth"})),
    STONE_SLAB_TYPE("stone_slab_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"brick", "cobblestone", "nether_brick", "quartz", "sandstone", "smooth_stone", "stone_brick", "wood"})),
    STONE_SLAB_TYPE_2("stone_slab_type_2", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"purpur", "red_sandstone"})),
    STONE_TYPE("stone_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"andesite", "andesite_smooth", "diorite", "diorite_smooth", "granite", "granite_smooth", "stone"})),
    STRUCTURE_BLOCK_TYPE("structure_block_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"corner", "data", "export", "invalid", "load", "save"})),
    TALL_GRASS_TYPE("tall_grass_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"fern", "tall"})),
    TORCH_FACING_DIRECTION("torch_facing_direction", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"east", "north", "south", "top", "unknown", "west"})),
    WALL_BLOCK_TYPE("wall_block_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"cobblestone", "mossy_cobblestone"})),
    WOOD_TYPE("wood_type", NBTConstants.NBTType.STRING, new EnumValuesRange(new String[]{"acacia", "birch", "dark_oak", "jungle", "oak", "spruce"}));


    private static final Map<String, BlockProperty> blockProperties = new HashMap<>();

    private final String name;

    private final NBTConstants.NBTType type;

    private final ValuesRange valuesRange;

    static {
        for (var property : BlockProperty.values()) blockProperties.put(property.name, property);
    }

    BlockProperty(String name, NBTConstants.NBTType type, ValuesRange valuesRange) {
        this.name = name;
        this.type = type;
        this.valuesRange = valuesRange;
    }

    @Nullable
    public static BlockProperty get(String name){
        return blockProperties.get(name);
    }

    public String getName() {
        return name;
    }

    public NBTConstants.NBTType getType() {
        return type;
    }

    public ValuesRange getValuesRange() {
        return valuesRange;
    }
}
