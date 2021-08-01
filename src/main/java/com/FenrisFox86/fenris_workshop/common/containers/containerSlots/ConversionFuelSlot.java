package com.FenrisFox86.fenris_workshop.common.containers.containerSlots;

import com.FenrisFox86.fenris_workshop.common.containers.AbstractConverterContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ConversionFuelSlot extends Slot {
    private final AbstractConverterContainer menu;

    public ConversionFuelSlot(AbstractConverterContainer container, IInventory inventory, int p_i50084_3_, int p_i50084_4_, int p_i50084_5_) {
        super(inventory, p_i50084_3_, p_i50084_4_, p_i50084_5_);
        this.menu = container;
    }

    public boolean mayPlace(ItemStack p_75214_1_) {
        return this.menu.isFuel(p_75214_1_) || isBucket(p_75214_1_);
    }

    public int getMaxStackSize(ItemStack p_178170_1_) {
        return isBucket(p_178170_1_) ? 1 : super.getMaxStackSize(p_178170_1_);
    }

    public static boolean isBucket(ItemStack stack) {
        return stack.getItem() == Items.BUCKET;
    }
}