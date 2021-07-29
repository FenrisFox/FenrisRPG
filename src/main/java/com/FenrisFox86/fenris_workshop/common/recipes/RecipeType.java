package com.FenrisFox86.fenris_workshop.common.recipes;

import com.FenrisFox86.fenris_workshop.FenrisWorkshop;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;

public class RecipeType<T extends IRecipe<IInventory>> implements IRecipeType<T> {

    private String name;

    public RecipeType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return FenrisWorkshop.MOD_ID + ":" + name;
    }
}
