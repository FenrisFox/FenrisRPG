package com.FenrisFox86.fenris_rpg.common.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ItemDamageHandlers {

    @SubscribeEvent
    public static void DamageHandFire(LivingHurtEvent event) {

        LivingEntity target = event.getEntityLiving();
        Entity source = event.getSource().getDirectEntity();
        if (source instanceof LivingEntity) {

            if (source instanceof PlayerEntity) {

                PlayerEntity attacker = (PlayerEntity) event.getSource().getDirectEntity();
                ItemStack stack = attacker.getMainHandItem();

                MinecraftForge.EVENT_BUS.post(new ItemDamageEvent(stack, target, attacker));

            } else {

                MinecraftForge.EVENT_BUS.post(new ItemDamageEvent(((LivingEntity)event.getSource().getDirectEntity()).getMainHandItem(),
                        (LivingEntity)source, target));
            }
        }
    }

    /*@SubscribeEvent
    public static void SetAttackHand(PlayerInteractEvent event) {
        if (event.getHand() == Hand.MAIN_HAND) {
            FenrisPlayerReader.setFenrisRpgFloat(event.getPlayer(), "offhand_used", 0f);
        } else {
            FenrisPlayerReader.setFenrisRpgFloat(event.getPlayer(), "offhand_used", 1f);
        }
    }*/
}
