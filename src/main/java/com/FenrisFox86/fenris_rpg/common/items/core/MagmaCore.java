package com.FenrisFox86.fenris_rpg.common.items.core;

import com.FenrisFox86.fenris_rpg.FenrisRPG;
import com.FenrisFox86.fenris_rpg.core.util.tools.ModArmorMaterial;
import com.FenrisFox86.fenris_rpg.core.util.tools.ModItemTier;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber
public class MagmaCore extends AbstractCore {

    public MagmaCore() {
        super("magma_core", new Properties().tab(FenrisRPG.MOD_TAB).durability(256));
        this.itemTier = ModItemTier.MAGMA_CORE;
        this.armorMaterial = ModArmorMaterial.MAGMA_CORE_ARMOR;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        AbstractCore.appendHoverText(tooltip, name);
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return this.useCoreItem(worldIn, playerIn, handIn, this);
    }

    public ActionResult<ItemStack> useCoreItem(World worldIn, PlayerEntity playerIn, Hand handIn, Item item) {
        if(!playerIn.getCooldowns().isOnCooldown(item)) {
            playerIn.setSecondsOnFire(0);
            playerIn.getItemInHand(handIn).hurtAndBreak(16, playerIn, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
            return ActionResult.success(playerIn.getItemInHand(handIn));
        }
        return ActionResult.fail(playerIn.getItemInHand(handIn));
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        if(entity.isAlive()) {
            ((LivingEntity) entity).addEffect(new EffectInstance(Effects.GLOWING, 100));
            entity.setSecondsOnFire(2000);
        }
        return false;
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(worldIn.getDayTime()%10 == 0) {
            if(stack.getItem() instanceof ICoreItem && entityIn instanceof PlayerEntity && ((ICoreItem)stack.getItem()).getCore() instanceof MagmaCore) {
                if(entityIn.isOnFire()) {
                    LivingEntity living = (LivingEntity) entityIn;
                    if (living.getOffhandItem() == stack) {
                        stack.hurtAndBreak(-8, living, p -> p.broadcastBreakEvent(Hand.OFF_HAND));
                    } else if (living.getMainHandItem() == stack) {
                        stack.hurtAndBreak(-8, living, p -> p.broadcastBreakEvent(Hand.MAIN_HAND));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingArmorEquip(LivingEquipmentChangeEvent event) {
        if (!event.getEntityLiving().level.isClientSide() && event.getEntityLiving() instanceof PlayerEntity) {
            LivingEntity living = event.getEntityLiving();
            ModifiableAttributeInstance damageAttribute = living.getAttribute(Attributes.ATTACK_KNOCKBACK);
            ModifiableAttributeInstance luckAttribute = living.getAttribute(Attributes.ATTACK_SPEED);
            ModifiableAttributeInstance speedAttribute = living.getAttribute(Attributes.MOVEMENT_SPEED);

            assert damageAttribute != null;
            assert luckAttribute != null;
            assert speedAttribute != null;
            for (AttributeModifier modifier : luckAttribute.getModifiers()) {
                if (modifier.getId().equals(UUID.fromString("76FA8B01-C328-F9D4-91B2-1C2D7A871A31"))) {
                    luckAttribute.removeModifier(modifier); }}
            for (AttributeModifier modifier : damageAttribute.getModifiers()) {
                if (modifier.getId().equals(UUID.fromString("76FA8B01-C328-F9D4-92B2-1C2D7A871A3F"))) {
                    damageAttribute.removeModifier(modifier); }}
            for (AttributeModifier modifier : speedAttribute.getModifiers()) {
                if (modifier.getId().equals(UUID.fromString("76FA8B01-C328-F9D4-93B2-1C2D7A871A32"))) {
                    speedAttribute.removeModifier(modifier); }}

            for (ItemStack stack : living.getArmorSlots()) {
                if (stack.getItem() instanceof CoreArmorItem && ((CoreArmorItem)stack.getItem()).core instanceof MagmaCore) {
                    if (((CoreArmorItem)stack.getItem()).equipmentSlotType.equals(EquipmentSlotType.HEAD)) {
                        luckAttribute.addTransientModifier(
                                new AttributeModifier(UUID.fromString("76FA8B01-C328-F9D4-91B2-1C2D7A871A31"),
                                        "magma_helmet", 4.0f, AttributeModifier.Operation.MULTIPLY_TOTAL));
                    }
                    if (((CoreArmorItem)stack.getItem()).equipmentSlotType.equals(EquipmentSlotType.CHEST)) {
                        damageAttribute.addTransientModifier(
                                new AttributeModifier(UUID.fromString("76FA8B01-C328-F9D4-92B2-1C2D7A871A3F"),
                                        "magma_chestplate",4.0f, AttributeModifier.Operation.MULTIPLY_TOTAL));
                    }
                    if (((CoreArmorItem)stack.getItem()).equipmentSlotType.equals(EquipmentSlotType.LEGS)) {
                        speedAttribute.addTransientModifier(
                                new AttributeModifier(UUID.fromString("76FA8B01-C328-F9D4-93B2-1C2D7A871A32"),
                                        "magma_leggings",4.0f, AttributeModifier.Operation.MULTIPLY_TOTAL));
                    }
                }
            }
        }
    }
}
