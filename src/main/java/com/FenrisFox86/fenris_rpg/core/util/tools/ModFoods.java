package com.FenrisFox86.fenris_rpg.core.util.tools;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class ModFoods {
    public static final Food RAYHANS_CHEESE = (new Food.Builder()).nutrition(4).saturationMod(1.2F).effect(new EffectInstance(Effects.REGENERATION, 400, 1), 1.0F).effect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 6000, 0), 1.0F).effect(new EffectInstance(Effects.FIRE_RESISTANCE, 6000, 0), 1.0F).effect(new EffectInstance(Effects.ABSORPTION, 2400, 5), 1.0F).alwaysEat().build();

}
