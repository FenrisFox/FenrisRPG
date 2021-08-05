package com.FenrisFox86.fenris_rpg.common.containers;

import com.FenrisFox86.fenris_rpg.common.containers.containerSlots.ConversionFuelSlot;
import com.FenrisFox86.fenris_rpg.common.containers.containerSlots.ConversionResultSlot;
import com.FenrisFox86.fenris_rpg.common.recipes.AbstractConversionRecipe;
import com.FenrisFox86.fenris_rpg.common.tileentities.AbstractConverterTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.ArrayUtils;

public abstract class AbstractConverterContainer<T extends AbstractConverterTileEntity> extends Container {
    private final IInventory container;
    private final IIntArray data;
    protected final World level;
    private final IRecipeType<? extends AbstractConversionRecipe> recipeType;

    protected AbstractConverterContainer(ContainerType<?> containerType, IRecipeType<? extends AbstractConversionRecipe> recipeType, int p_i241921_4_, PlayerInventory playerInv) {
        this(containerType, recipeType, p_i241921_4_, playerInv, new Inventory(15), new IntArray(4));
    }

    protected AbstractConverterContainer(ContainerType<?> containerType, IRecipeType<? extends AbstractConversionRecipe> recipeType, int p_i241922_4_, PlayerInventory playerInv, IInventory inventory, IIntArray dataAccess) {
        super(containerType, p_i241922_4_);
        this.recipeType = recipeType;
        checkContainerSize(inventory, 15);
        checkContainerDataCount(dataAccess, 4);
        this.container = inventory;
        this.data = dataAccess;
        this.level = playerInv.player.level;
        for (int s: T.INPUT_SLOT_INDEXES) { this.addSlot(new Slot(inventory, s, 8+18*(s-T.INPUT_SLOT_INDEXES[0]), 19)); }
        for (int s: T.FUEL_SLOT_INDEXES) { this.addSlot(new ConversionFuelSlot(this, inventory, s, 8+18*(s-T.FUEL_SLOT_INDEXES[0]), 55)); }
        for (int s: T.OUTPUT_SLOT_INDEXES) { int x = (s-T.OUTPUT_SLOT_INDEXES[0])%3; int y = (s-T.OUTPUT_SLOT_INDEXES[0])/3;
            this.addSlot(new ConversionResultSlot(playerInv.player, inventory, s, 116+x*18, 19+y*18));
        }


        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInv, k, 8 + k * 18, 142));
        }

        this.addDataSlots(dataAccess);
    }

    public void fillCraftSlotsStackedContents(RecipeItemHelper recipeItemHelper) {
        if (this.container instanceof IRecipeHelperPopulator) {
            ((IRecipeHelperPopulator)this.container).fillStackedContents(recipeItemHelper);
        }
    }

    public void clearCraftingContent() {
        this.container.clearContent();
    }

    public boolean recipeMatches(IRecipe<? super IInventory> recipe) {
        return recipe.matches(this.container, this.level);
    }

    @OnlyIn(Dist.CLIENT)
    public int getSize() {
        return 15;
    }

    public boolean stillValid(PlayerEntity player) {
        return this.container.stillValid(player);
    }

    //public static boolean isIn(int[] ints, int integer) { return Arrays.stream(ints).anyMatch(x -> x == integer); }

    public ItemStack quickMoveStack(PlayerEntity player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        int totalSlots = this.slots.size();
        if (slot != null && slot.hasItem()) {
            ItemStack slotItem = slot.getItem();
            newStack = slotItem.copy();
            if (ArrayUtils.contains(T.OUTPUT_SLOT_INDEXES, index)) {
                if (!this.moveItemStackTo(slotItem, totalSlots-36, totalSlots, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(slotItem, newStack);

            } else if (!ArrayUtils.contains(T.FUEL_SLOT_INDEXES, index) && !ArrayUtils.contains(T.INPUT_SLOT_INDEXES, index)) {
                boolean moved = false;

                //put smeltable Items in Input
                if (this.isFuel(slotItem)) {
                    if (!this.moveItemStackTo(slotItem, T.FUEL_SLOT_INDEXES[0],
                            T.FUEL_SLOT_INDEXES[T.FUEL_SLOT_INDEXES.length-1], false)) {
                        return ItemStack.EMPTY;
                    }

                //put fuel items in Fuel slots
                } else if (this.canSmelt(slotItem)) {
                    if (!this.moveItemStackTo(slotItem, T.INPUT_SLOT_INDEXES[0],
                            T.INPUT_SLOT_INDEXES[T.INPUT_SLOT_INDEXES.length-1], false)) {
                        return ItemStack.EMPTY;
                    }

                //put from main inventory slots into hotbar
                } else if (index >= totalSlots-36 && index < totalSlots-9) {
                    if (!this.moveItemStackTo(slotItem, totalSlots-9, totalSlots, false)) {
                        return ItemStack.EMPTY;
                    }

                //put from hotbar into main inventory
                } else if (index >= totalSlots-9 && !this.moveItemStackTo(slotItem, totalSlots-36, totalSlots-9, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotItem, totalSlots-36, totalSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (slotItem.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotItem.getCount() == newStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotItem);
        }

        return newStack;
    }

    protected boolean canSmelt(ItemStack p_217057_1_) {
        return this.level.getRecipeManager().getRecipeFor((IRecipeType)this.recipeType, new Inventory(p_217057_1_), this.level).isPresent();
    }

    public boolean isFuel(ItemStack p_217058_1_) {
        return net.minecraftforge.common.ForgeHooks.getBurnTime(p_217058_1_, this.recipeType) > 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnProgress() {
        int i = this.data.get(2);
        int j = this.data.get(3);
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getLitProgress() {
        int i = this.data.get(1);
        if (i == 0) {
            i = 200;
        }

        return this.data.get(0) * 13 / i;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isLit() {
        return this.data.get(0) > 0;
    }

}

