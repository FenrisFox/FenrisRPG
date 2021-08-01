package com.FenrisFox86.fenris_workshop.common.containers.containerSlots;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;

public class ConversionResultSlot extends Slot {
    private final PlayerEntity player;
    private int removeCount;

    public ConversionResultSlot(PlayerEntity player, IInventory inventory, int slot, int x, int y) {
        super(inventory, slot, x, y);
        this.player = player;
    }

    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    public ItemStack remove(int count) {
        if (this.hasItem()) {
            this.removeCount += Math.min(count, this.getItem().getCount());
        }

        return super.remove(count);
    }

    public ItemStack onTake(PlayerEntity player, ItemStack stack) {
        super.onTake(player, stack);
        return stack;
    }

    protected void onQuickCraft(ItemStack stack, int count) {
        this.removeCount += count;
    }
}
