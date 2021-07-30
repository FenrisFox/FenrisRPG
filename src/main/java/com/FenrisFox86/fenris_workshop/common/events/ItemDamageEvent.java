package com.FenrisFox86.fenris_workshop.common.events;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

public class ItemDamageEvent extends Event {
    private ItemStack item;
    private LivingEntity target;
    private LivingEntity attacker;

    public ItemDamageEvent(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        this.item = stack;
        this.target = target;
        this.attacker = attacker;
    }

    public LivingEntity getTarget() { return this.target; }

    public LivingEntity getAttacker() { return this.attacker; }

    public ItemStack getItem() { return this.item; }
}
