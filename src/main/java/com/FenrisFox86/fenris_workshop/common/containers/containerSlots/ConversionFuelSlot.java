package com.FenrisFox86.fenris_workshop.common.containers.containerSlots;

import com.FenrisFox86.fenris_workshop.common.containers.AbstractConverterContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ConversionFuelSlot extends Slot {
    private final AbstractConverterContainer menu;

    public ConversionFuelSlot(AbstractConverterContainer container, IInventory inventory, int slot, int x, int y) {
        super(inventory, slot, x, y);
        this.menu = container;
    }

    public boolean mayPlace(ItemStack stack) {
        return this.menu.isFuel(stack) || isBucket(stack);
    }

    public int getMaxStackSize(ItemStack p_178170_1_) {
        return isBucket(p_178170_1_) ? 1 : super.getMaxStackSize(p_178170_1_);
    }

    public static boolean isBucket(ItemStack stack) {
        return stack.getItem() == Items.BUCKET;
    }
}