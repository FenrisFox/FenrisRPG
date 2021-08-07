package com.FenrisFox86.fenris_rpg.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class Lumberjack extends Enchantment {

    public Lumberjack(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    protected boolean checkCompatibility(Enchantment ench) {
        if (ench instanceof Crushing || ench instanceof Smelting) return false;
        return super.checkCompatibility(ench);
    }
}
