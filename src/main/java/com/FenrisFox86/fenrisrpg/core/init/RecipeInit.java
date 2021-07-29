package com.FenrisFox86.fenrisrpg.core.init;

import com.FenrisFox86.fenrisrpg.common.recipes.CrushingRecipe;
import com.FenrisFox86.fenrisrpg.common.recipes.RecipeType;
import com.FenrisFox86.fenrisrpg.common.recipes.SlashingRecipe;
import com.FenrisFox86.fenrisrpg.common.recipes.StabbingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.HashMap;
import java.util.Map;

public class RecipeInit {

    public static final IRecipeType<CrushingRecipe> CRUSHING_RECIPE = new RecipeType<CrushingRecipe>("crushing");
    public static final IRecipeType<SlashingRecipe> SLASHING_RECIPE = new RecipeType<SlashingRecipe>("slashing");
    public static final IRecipeType<StabbingRecipe> STABBING_RECIPE = new RecipeType<StabbingRecipe>("stabbing");

    public static final Map<IRecipeSerializer<?>, RecipeType<?>> RECIPE_MAP = new HashMap<IRecipeSerializer<?>, RecipeType<?>>() {};

    public static void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> event) {

        RECIPE_MAP.put(CrushingRecipe.SERIALIZER, (RecipeType<?>) CRUSHING_RECIPE);
        RECIPE_MAP.put(SlashingRecipe.SERIALIZER, (RecipeType<?>) SLASHING_RECIPE);
        RECIPE_MAP.put(StabbingRecipe.SERIALIZER, (RecipeType<?>) STABBING_RECIPE);

        registerRecipe(event, CRUSHING_RECIPE, CrushingRecipe.SERIALIZER);
        registerRecipe(event, SLASHING_RECIPE, SlashingRecipe.SERIALIZER);
        registerRecipe(event, STABBING_RECIPE, StabbingRecipe.SERIALIZER);
    }

    private static void registerRecipe(
            RegistryEvent.Register<IRecipeSerializer<?>> event, IRecipeType<?> type, IRecipeSerializer<?> serializer)  {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(type.toString()), type);
        event.getRegistry().register(serializer);
    }

    public static Map<ResourceLocation, IRecipe<?>> getRecipes(IRecipeType<?> type, RecipeManager manager) {
        final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipes = ObfuscationReflectionHelper
                .getPrivateValue(RecipeManager.class, manager, "field_199522_d");
        return recipes.get(type);
    }
}
