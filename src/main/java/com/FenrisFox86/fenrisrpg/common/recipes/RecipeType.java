package com.FenrisFox86.fenrisrpg.common.recipes;

import com.FenrisFox86.fenrisrpg.FenrisRPG;
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
        return FenrisRPG.MOD_ID + ":" + name;
    }
}
