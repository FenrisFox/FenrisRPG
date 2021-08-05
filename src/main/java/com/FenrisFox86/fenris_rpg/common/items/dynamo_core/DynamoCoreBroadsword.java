package com.FenrisFox86.fenris_rpg.common.items.dynamo_core;

import com.FenrisFox86.fenris_rpg.FenrisRPG;
import com.FenrisFox86.fenris_rpg.common.items.BroadswordItem;
import com.FenrisFox86.fenris_rpg.common.items.Tooltips;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class DynamoCoreBroadsword extends BroadswordItem {

    public DynamoCoreBroadsword(IItemTier tier, int attackDamageIn, float attackMOVEMENT_SPEEDIn) {
        super(tier, attackDamageIn, attackMOVEMENT_SPEEDIn, new Properties().tab(FenrisRPG.MOD_TAB));
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
            tooltip.add(new TranslationTextComponent("tooltip.fenris_rpg.dynamo_core_broadsword.lore"));
            tooltip.add(new TranslationTextComponent("tooltip.fenris_rpg.dynamo_core_broadsword.desc"));
        } else {
            tooltip.add(Tooltips.HOLD_SHIFT);
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        if(entity.isAlive()) {
            ((LivingEntity) entity).addEffect(new EffectInstance(Effects.GLOWING, 100));
            ((LivingEntity) entity).removeEffect(Effects.DAMAGE_BOOST);
            ((LivingEntity) entity).removeEffect(Effects.MOVEMENT_SPEED);
            ((LivingEntity) entity).setSecondsOnFire(1);
        }
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(worldIn.getDayTime()%20 == 0) {
            if(stack.getItem() instanceof DynamoCoreBroadsword && entityIn instanceof PlayerEntity) {
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
