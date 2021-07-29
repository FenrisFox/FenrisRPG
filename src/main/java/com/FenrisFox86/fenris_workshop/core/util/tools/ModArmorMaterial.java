package com.FenrisFox86.fenris_workshop.core.util.tools;

import com.FenrisFox86.fenris_workshop.core.init.ItemInit;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import java.util.function.Supplier;

public enum ModArmorMaterial implements IArmorMaterial {
    RUBY_ARMOR("ruby", 10, new int[] {3, 6, 8, 3}, ModItemTier.RUBY.getEnchantmentValue(),
            SoundEvents.ARMOR_EQUIP_IRON, 2.0f,0.0f, () -> {
        return Ingredient.of(ItemInit.RUBY.get());
    }),
    SAPPHIRE_ARMOR("sapphire", 10, new int[] {3, 6, 8, 3}, ModItemTier.SAPPHIRE.getEnchantmentValue(),
    SoundEvents.ARMOR_EQUIP_IRON, 3.0f,0.1f, () -> {
        return Ingredient.of(ItemInit.SAPPHIRE.get());
    }),
    ESSENCE_ARMOR("essence", 20, new int[] {5, 10, 16, 5}, ModItemTier.ESSENCE.getEnchantmentValue(),
    SoundEvents.ARMOR_EQUIP_IRON, 5.0f,0.2f, () -> {
        return Ingredient.of(ItemInit.ESSENCE.get());
    }),
    BRONZE_ARMOR("bronze", 2, new int[] {1, 4, 5, 1}, ModItemTier.BRONZE.getEnchantmentValue(),
            SoundEvents.ARMOR_EQUIP_IRON, 0.0f,0.0f, () -> {
                return Ingredient.of(ItemInit.BRONZE_INGOT.get());
            }),
    SILVER_ARMOR("silver", 4, new int[] {2, 4, 5, 2}, ModItemTier.SILVER.getEnchantmentValue(),
    SoundEvents.ARMOR_EQUIP_IRON, 0.0f,0.0f, () -> {
        return Ingredient.of(ItemInit.SILVER_INGOT.get());
    }),
    RUBY_CORE_ARMOR("ruby_core", 7, new int[] {2, 5, 7, 3}, ModItemTier.RUBY_CORE.getEnchantmentValue(),
            SoundEvents.ARMOR_EQUIP_IRON, 1.0f,0.0f, () -> {
        return Ingredient.of(ItemInit.RUBY.get());
    }),
    DYNAMO_CORE_ARMOR("dynamo_core", 50, new int[] {20, 20, 30, 30}, ModItemTier.DYNAMO_CORE.getEnchantmentValue(),
            SoundEvents.ARMOR_EQUIP_IRON, 5.0f,0.5f, () -> {
        return Ingredient.of(ItemInit.DYNAMO_CORE.get());
    })
    ;

    private final int[] baseDurability = {128, 144, 160, 112};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] armorVal;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    ModArmorMaterial(String name, int durabilityMultiplier, int[] armorVal, int enchantability,
                     SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.armorVal = armorVal;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }


    @Override
    public int getDurabilityForSlot(EquipmentSlotType slot) {
        return this.baseDurability[slot.getIndex()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlotType slot) {
        return this.armorVal[slot.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
