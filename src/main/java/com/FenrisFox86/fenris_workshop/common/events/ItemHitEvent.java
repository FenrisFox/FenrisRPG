package com.FenrisFox86.fenris_workshop.common.events;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

public class ItemHitEvent extends Event {
    private ItemStack stack;
    private LivingEntity target;
    private LivingEntity entity;

    public ItemHitEvent(ItemStack stack, LivingEntity target, LivingEntity living) {
        this.stack = stack;
        this.target = target;
        this.entity = living;
    }

    public LivingEntity getTarget() { return this.target; }

    public LivingEntity getEntity() { return this.entity; }

    public ItemStack getStack() { return this.stack; }
}
