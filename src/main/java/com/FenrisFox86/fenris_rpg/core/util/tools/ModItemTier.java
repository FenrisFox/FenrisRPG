package com.FenrisFox86.fenris_rpg.core.util.tools;

import com.FenrisFox86.fenris_rpg.core.init.ItemInit;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModItemTier implements IItemTier {

    RUBY(3, 2003, 6.0F, 7.0F, 36, () -> {
        return Ingredient.of(ItemInit.RUBY.get());
    }),

    SAPPHIRE(3, 2003, 8.0F, 5.0F, 36, () -> {
        return Ingredient.of(ItemInit.SAPPHIRE.get());
    }),

    ESSENCE(4, 2135, 7.0F, 10.0F, 36, () -> {
        return Ingredient.of(ItemInit.ESSENCE.get());
    }),

    BRONZE(2, 132, 4.0F, 3.0F, 12, () -> {
        return Ingredient.of(ItemInit.BRONZE_INGOT.get());
    }),

    SILVER(2, 264, 5.0F, 3.0F, 24, () -> {
        return Ingredient.of(ItemInit.SILVER_INGOT.get());
    }),

    RUBY_CORE(3, 1000, 4.0F, 5.0F, 28, () -> {
        return Ingredient.of(ItemInit.RUBY.get());
    }),

    DYNAMO_CORE(5, 2604, 10.0F, 28.0F, 48, () -> {
        return Ingredient.of(ItemInit.DYNAMO_CORE_SET.get("CORE").get());
    }),

    MAGMA_CORE(5, 2604, 10.0F, 28.0F, 48, () -> {
        return Ingredient.of(ItemInit.MAGMA_CORE_SET.get("CORE").get());
    });

    private final int harvestlevel;
    private final int maxUses;
    private final float speed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairMaterial;

    ModItemTier(int harvestlevel,
                int maxUses,
                float speed,
                float attackDamage,
                int enchantability,
                Supplier<Ingredient> repairMaterial) {

        this.harvestlevel = harvestlevel;
        this.maxUses = maxUses;
        this.speed = speed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;

    }

    @Override
    public int getUses() {
        return maxUses;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return attackDamage;
    }

    @Override
    public int getLevel() {
        return harvestlevel;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairMaterial.get();
    }
}
