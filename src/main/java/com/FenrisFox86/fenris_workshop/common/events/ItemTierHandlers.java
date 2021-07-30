package com.FenrisFox86.fenris_workshop.common.events;

import com.FenrisFox86.fenris_workshop.FenrisWorkshop;
import com.FenrisFox86.fenris_workshop.core.util.tools.ModItemTier;
import net.minecraft.entity.Entity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.TieredItem;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class ItemTierHandlers {

    @SubscribeEvent
    public static void itemHitTierCheck(ItemDamageEvent event) {
        if (event.getItem().getItem() instanceof TieredItem) {
            IItemTier tier = ((TieredItem) event.getItem().getItem()).getTier();
            if (tier == ModItemTier.SILVER) {
                //Check for Tag and deal additional damage
            }
            EntityTypeTags
        }
    }
}
