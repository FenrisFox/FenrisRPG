package com.FenrisFox86.fenrisrpg.common.recipes;

import com.FenrisFox86.fenrisrpg.FenrisRPG;
import com.FenrisFox86.fenrisrpg.core.init.ItemInit;
import com.FenrisFox86.fenrisrpg.core.init.RecipeInit;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class StabbingRecipe extends ConversionRecipe {

    public static final ConversionRecipe.Serializer<StabbingRecipe> SERIALIZER = new ConversionRecipe
            .Serializer<StabbingRecipe>("stabbing", StabbingRecipe::new);

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
