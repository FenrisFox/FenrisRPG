package com.FenrisFox86.fenris_rpg.common.recipes;

import com.FenrisFox86.fenris_rpg.FenrisRPG;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public abstract class AbstractConversionRecipe implements IRecipe<IInventory> {

    final Ingredient input;
    final int min_output;
    final int max_output;
    final float chance;
    final Item output_item;
    final ResourceLocation id;

    public AbstractConversionRecipe(ResourceLocation id, Ingredient input, Item output_item, int min_output, int max_output, float chance) {
        this.input = input;
        this.output_item = output_item;
        this.id = id;
        this.min_output = min_output;
        this.max_output = max_output;
        this.chance = chance;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return input.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(IInventory inv) {
        return getResultItem();
    }

    public int getConversionTime() {
        return 200;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        if (Math.random()<this.chance){
            int count = ((int) (Math.random() * (this.max_output-this.min_output))) + this.min_output;
            return new ItemStack(output_item, count);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.input);
        return nonnulllist;
    }

    public static class Serializer<T extends AbstractConversionRecipe>
            extends ForgeRegistryEntry<IRecipeSerializer<?>>
            implements IRecipeSerializer<AbstractConversionRecipe> {

        public final Serializer.IFactory<T> factory;

        public Serializer(String name, Serializer.IFactory<T> factory) {
            this.setRegistryName(FenrisRPG.MOD_ID, name);
            this.factory = factory;
        }

        @Override
        public T fromJson(ResourceLocation recipeId, JsonObject json) {

            JsonElement InputElement = JSONUtils.isArrayNode(json, "input") ?
                    JSONUtils.getAsJsonArray(json, "input") :
                    JSONUtils.getAsJsonObject(json, "input");
            final Ingredient input = Ingredient.fromJson(InputElement);
            //final ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getAsJsonObject(json, "output"));
            final JsonObject output = JSONUtils.getAsJsonObject(json, "output");
            int min_output = 1;
            int max_output = 1;
            float chance = 1f;
            final Item output_item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(JSONUtils.getAsString(output, "item")));
            if (output.has("count")) {
                min_output = JSONUtils.getAsInt(output, "count");
                max_output = JSONUtils.getAsInt(output, "count");
            } else if (output.has("max_count")) {
                max_output = JSONUtils.getAsInt(output, "max_count");
                if (output.has("min_count")) {
                    min_output = JSONUtils.getAsInt(output, "min_count");
                }
            }
            if (output.has("chance")) {
                chance = JSONUtils.getAsFloat(output, "chance");
            }

            return this.factory.create(recipeId, input, output_item, min_output, max_output, chance);
        }

        @Nullable
        @Override
        public T fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            final Ingredient input = Ingredient.fromNetwork(buffer);
            final CompoundNBT output = buffer.readNbt();
            assert output != null;
            final Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(output.getString("item")));
            final int min_output = output.getInt("min_count");
            final int max_output = output.getInt("max_count");
            final float chance = output.getFloat("chance");

            return this.factory.create(recipeId, input, item, min_output, max_output, chance);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, AbstractConversionRecipe recipe) {
            recipe.input.toNetwork(buffer);
            CompoundNBT output = new CompoundNBT();
            output.putString("item", recipe.output_item.toString());
            output.putInt("min_count", recipe.min_output);
            output.putInt("max_count", recipe.max_output);
            output.putFloat("chance", recipe.chance);

            buffer.writeNbt(output);
            //buffer.writeItemStack(recipe.output_item);
        }

        interface IFactory<T extends AbstractConversionRecipe> {
            T create(ResourceLocation recipeId, Ingredient input, Item output, int min, int max, float chance);
        }
    }
}
