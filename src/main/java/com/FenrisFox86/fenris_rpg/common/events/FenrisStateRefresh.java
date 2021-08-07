package com.FenrisFox86.fenris_rpg.common.events;

import com.FenrisFox86.fenris_rpg.FenrisRPG;
import com.FenrisFox86.fenris_rpg.common.capabilities.FenrisPlayerReader;
import com.FenrisFox86.fenris_rpg.common.capabilities.FenrisStateProvider;
import com.FenrisFox86.fenris_rpg.common.items.BroadswordItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class FenrisStateRefresh {
    public static final ResourceLocation COMBAT_STATE_CAP = new ResourceLocation(FenrisRPG.MOD_ID, "combat_state");

    @SubscribeEvent
    public static void OnCapabilityAttach(final AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(COMBAT_STATE_CAP, new FenrisStateProvider());
        }
    }

    @SubscribeEvent
    public static void OnPlayerTick(final TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;

        if (event.side.isServer()) {

            float dashing = FenrisPlayerReader.getFenrisRpgFloat(player, "dashing");
            if (dashing > 0) {
                FenrisPlayerReader.setFenrisRpgFloat(player, "dashing", dashing - 1);
                World world = player.level;
                double
                        x = player.getX(),
                        y = player.getY(),
                        z = player.getZ(),
                        radius = 2.0;
                List<Entity> entityList = world.getEntities(
                        player, new AxisAlignedBB(
                                x, y, z, x, y, z).inflate(radius));
                for (Entity entity : entityList) {
                    Item mainItem = player.getMainHandItem().getItem();
                    if (mainItem instanceof BroadswordItem && entity != player && entity instanceof LivingEntity) {
                        entity.hurt(DamageSource.playerAttack(player),
                                (((BroadswordItem) mainItem).getAttackDamageBonus()));
                        mainItem.hurtEnemy(player.getOffhandItem(), (LivingEntity)entity, player);
                    }
                }
            } else if (dashing < 0) {
                FenrisPlayerReader.setFenrisRpgFloat(player, "dashing", 0.0f);
            }
        }
    }
}
