package com.FenrisFox86.fenris_workshop.common.events;

import com.FenrisFox86.fenris_workshop.core.util.tools.ModItemTier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.TieredItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber
public class ItemTierHandlers {

    @SubscribeEvent
    public static void itemHitTierCheck(ItemDamageEvent event) {
        if (event.getItem().getItem() instanceof TieredItem) {
            IItemTier tier = ((TieredItem) event.getItem().getItem()).getTier();
            if (tier == ModItemTier.SILVER) {
                event.getAttacker().sendMessage(new StringTextComponent("Silver"), UUID.randomUUID());
                event.getTarget().hurt(DamageSource.mobAttack(event.getAttacker()), event.getItem().getDamageValue());

            }
        }
    }
}
