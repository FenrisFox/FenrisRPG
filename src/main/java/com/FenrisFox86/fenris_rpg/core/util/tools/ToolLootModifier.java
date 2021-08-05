package com.FenrisFox86.fenris_rpg.core.util.tools;

import com.FenrisFox86.fenris_rpg.core.init.RecipeInit;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class ToolLootModifier extends LootModifier {

    @Nullable private final IRecipeType<?> recipe_type;
    @Nullable private final Integer bonus_count;
    @Nullable private final Item bonus_item;

    public ToolLootModifier(ILootCondition[] conditionsIn, @Nullable IRecipeType<?> recipe_type,
                            @Nullable Integer bonus_count, @Nullable Item bonus_item) {
        super(conditionsIn);
        this.recipe_type = recipe_type;
        this.bonus_count = bonus_count;
        this.bonus_item = bonus_item;
    }

    @Nonnull
    @Override
    public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (this.recipe_type != null) {
            ArrayList<ItemStack> old_loot = new ArrayList<ItemStack> (generatedLoot);
            for (ItemStack stack : old_loot) {
                for (IRecipe<?> recipe : RecipeInit.getRecipes(recipe_type, context.getLevel().getRecipeManager()).values()) {
                    if (recipe.getIngredients().get(0).test(stack)) {
                        int f = recipe.getResultItem().copy().getCount();
                        int count = stack.getCount();
                        int final_count = count * f;
                        Item item = recipe.getResultItem().getItem();

                        if (final_count > 0) {
                            for (int i = 0; i < final_count / item.getMaxStackSize(); i++) {
                                generatedLoot.add(new ItemStack(item, item.getMaxStackSize()));
                            }
                            generatedLoot.add(new ItemStack(item, final_count%item.getMaxStackSize()));
                        }
                        generatedLoot.remove(stack);
                    }
                }
            }
            old_loot.clear();
        }
        if (this.bonus_count != null) {
            int count = this.bonus_count;
            for (ItemStack stack : generatedLoot) {

                if (stack.getItem() == bonus_item) {
                    int add_count = Math.min(count, stack.getMaxStackSize() - stack.getCount());
                    stack.setCount(stack.getCount()+add_count);
                    count -= add_count;
                }
            }
            if (count > 0) {
                generatedLoot.add(new ItemStack(bonus_item, count));

            }
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<ToolLootModifier> {

        @Override
        public ToolLootModifier read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
            @Nullable Integer bonus_count = null;
            @Nullable Item bonus_item = null;
            @Nullable IRecipeType<?> recipe_type = null;
            JsonObject action = JSONUtils.getAsJsonObject(object, "action");
            if (action.has("recipe")) {
                JsonObject recipe = JSONUtils.getAsJsonObject(action, "recipe");
                recipe_type = Registry.RECIPE_TYPE.get(new ResourceLocation((JSONUtils.getAsString(recipe, "type"))));
            }
            if (action.has("bonus")) {
                JsonObject bonus = JSONUtils.getAsJsonObject(action, "bonus");
                bonus_count = JSONUtils.getAsInt(bonus, "count");
                bonus_item = ForgeRegistries.ITEMS.getValue(
                        new ResourceLocation(JSONUtils.getAsString(bonus, "item")));
            }
            return new ToolLootModifier(conditionsIn, recipe_type, bonus_count, bonus_item);
        }

        @Override
        public JsonObject write(ToolLootModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            JsonObject action = new JsonObject();
            if (instance.recipe_type != null) {
                JsonObject recipe = new JsonObject();
                    recipe.addProperty("type", instance.recipe_type.toString());
                action.add("recipe", recipe);
            }
            if (instance.bonus_count != null) {
                JsonObject bonus = new JsonObject();
                    bonus.addProperty("count", instance.bonus_count);
                    bonus.addProperty("item", instance.bonus_item.toString());
                action.add("bonus", bonus);
            }
            json.add("action", action);
            return json;
        }
    }
}