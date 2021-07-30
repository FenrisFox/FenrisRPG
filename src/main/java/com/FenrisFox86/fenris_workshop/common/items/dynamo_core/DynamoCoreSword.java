package com.FenrisFox86.fenris_workshop.common.items.dynamo_core;

import com.FenrisFox86.fenris_workshop.FenrisWorkshop;
import com.FenrisFox86.fenris_workshop.common.items.Tooltips;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class DynamoCoreSword extends SwordItem implements IDynamoCoreItem {

    public DynamoCoreSword(IItemTier tier, int attackDamageIn, float attackMOVEMENT_SPEEDIn) {
        super(tier, attackDamageIn, attackMOVEMENT_SPEEDIn, new Properties().tab(FenrisWorkshop.MOD_TAB));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if(InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
            tooltip.add(new TranslationTextComponent("tooltip.fenris_workshop.dynamo_core_sword.lore"));
            tooltip.add(new TranslationTextComponent("tooltip.fenris_workshop.dynamo_core_sword.desc"));
        } else {
            tooltip.add(Tooltips.HOLD_SHIFT);
        }
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(!playerIn.getCooldowns().isOnCooldown(this)) {
            playerIn.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 500, 3));
            playerIn.addEffect(new EffectInstance(Effects.REGENERATION, 500, 3));
            playerIn.getCooldowns().addCooldown(this,60);
            playerIn.getItemInHand(handIn).hurtAndBreak(8, playerIn, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
            return ActionResult.success(playerIn.getItemInHand(handIn));
        }
        return ActionResult.fail(playerIn.getItemInHand(handIn));
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        if(entity.isAlive()) {
            ((LivingEntity) entity).addEffect(new EffectInstance(Effects.GLOWING, 200));
            ((LivingEntity) entity).removeEffect(Effects.DAMAGE_BOOST);
            ((LivingEntity) entity).removeEffect(Effects.REGENERATION);
            ((LivingEntity) entity).setSecondsOnFire(1);
        }
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(worldIn.getDayTime()%20 == 0) {
            if(stack.getItem() instanceof DynamoCoreSword && entityIn instanceof PlayerEntity) {
                if(entityIn.isSprinting()) {
                    LivingEntity living = (LivingEntity) entityIn;
                    if (living.getOffhandItem() == stack) {
                        stack.hurtAndBreak(-1, living, p -> p.broadcastBreakEvent(Hand.OFF_HAND));
                    } else if (living.getMainHandItem() == stack) {
                        stack.hurtAndBreak(-1, living, p -> p.broadcastBreakEvent(Hand.MAIN_HAND));
                    }
                }
            }
        }
    }
}
