package com.FenrisFox86.fenris_workshop.common.recipes;

import com.FenrisFox86.fenris_workshop.core.init.ItemInit;
import com.FenrisFox86.fenris_workshop.core.init.RecipeInit;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class StabbingRecipe extends AbstractConversionRecipe {

    public static final AbstractConversionRecipe.Serializer<CrushingRecipe> SERIALIZER = new AbstractConversionRecipe
            .Serializer<CrushingRecipe>("stabbing", CrushingRecipe::new);

    final Ingredient input;
    final int min_output;
    final int max_output;
    final float chance;
    final Item output_item;
    final ResourceLocation id;

    public StabbingRecipe(ResourceLocation id, Ingredient input, Item output_item, int min_output, int max_output, float chance) {
        super(id, input, output_item, min_output, max_output, chance);
        this.input = input;
        this.output_item = output_item;
        this.id = id;
        this.min_output = min_output;
        this.max_output = max_output;
        this.chance = chance;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() { return SERIALIZER; }

    @Override
    public IRecipeType<?> getType() { return RecipeInit.STABBING_RECIPE; }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ItemInit.DIAMOND_TOOLSET.get("DAGGER").get());
    }
}
