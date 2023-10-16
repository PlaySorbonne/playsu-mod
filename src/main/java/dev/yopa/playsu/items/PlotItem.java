package dev.yopa.playsu.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.level.block.Block;

public class PlotItem extends BlockItem implements Equipable {
    public PlotItem(Block block, Properties prop) {
        super(block, prop);
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }
}
