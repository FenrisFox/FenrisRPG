package com.FenrisFox86.fenris_rpg.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class DarkContract extends Enchantment {

    public DarkContract(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    protected boolean checkCompatibility(Enchantment ench) {
        if (ench instanceof Vampiric) {
            return false;
        }
        return super.checkCompatibility(ench);
    }

    @Override
    public boolean isCurse() {
        return true;
    }
}
