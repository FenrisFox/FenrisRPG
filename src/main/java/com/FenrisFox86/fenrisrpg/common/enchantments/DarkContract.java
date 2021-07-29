package com.FenrisFox86.fenrisrpg.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;

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
