package com.FenrisFox86.fenris_workshop.common.items;

import com.FenrisFox86.fenris_workshop.common.capabilities.CapabilityReader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class BroadswordItem extends SingleHandedSwordItem {

    public BroadswordItem(IItemTier tier, int attackDamageIn, float attackMOVEMENT_SPEEDIn, Properties builderIn) {
        super(tier, attackDamageIn, attackMOVEMENT_SPEEDIn, builderIn);
    }

    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.BOW;
    }

    public int getUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }


    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        return ActionResultType.FAIL;
    }

    /*@Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        return false;
    }*/

    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {

        ItemStack stack = playerIn.getItemInHand(handIn);
        if (!playerIn.getCooldowns().isOnCooldown(this)) {
            playerIn.startUsingItem(handIn);
            return ActionResult.fail(stack);
        }
        return ActionResult.fail(stack);
    }

    public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {

        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)entityLiving;

            float max_charge = 40.0f;
            float total_charge = (getUseDuration(stack) - timeLeft);
            float charge = Math.min(total_charge, max_charge)/max_charge;

            double factor = charge*10;
            Vector3d vector = player.getViewVector(1f).normalize().multiply(factor, 1, factor);
            player.setDeltaMovement(vector);
            player.getCooldowns().addCooldown(stack.getItem(), 50);
            CapabilityReader.setFenrisState(player, "dashing", ((float)(int)charge)*20 + 0.5f);
            stack.hurtAndBreak(8, player, p -> p.broadcastBreakEvent(Hand.MAIN_HAND));
        }
    }
}
