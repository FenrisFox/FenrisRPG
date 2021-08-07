package com.FenrisFox86.fenris_rpg.common.items.core;

import com.FenrisFox86.fenris_rpg.FenrisRPG;
import com.FenrisFox86.fenris_rpg.common.enchantments.logic.MagmaWalkerLogic;
import com.FenrisFox86.fenris_rpg.core.init.BlockInit;
import com.FenrisFox86.fenris_rpg.core.init.ItemInit;
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
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber
public class MagmaCore extends AbstractCoreItem {

    public MagmaCore() {
        super("magma_core", new Properties().tab(FenrisRPG.MOD_TAB).durability(256));
        this.itemTier = ModItemTier.MAGMA_CORE;
        this.armorMaterial = ModArmorMaterial.MAGMA_CORE_ARMOR;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        AbstractCoreItem.appendHoverText(tooltip, name);
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return this.useCoreItem(worldIn, playerIn, handIn, this);
    }

    public ActionResult<ItemStack> useCoreItem(World worldIn, PlayerEntity playerIn, Hand handIn, Item item) {
        if(!playerIn.getCooldowns().isOnCooldown(item)) {
            playerIn.setRemainingFireTicks(0);
            playerIn.getItemInHand(handIn).hurtAndBreak(16, playerIn, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
            return ActionResult.success(playerIn.getItemInHand(handIn));
        }
        return ActionResult.fail(playerIn.getItemInHand(handIn));
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        if(entity.isAlive()) {
            entity.setSecondsOnFire(2000);
        }
        return false;
    }

    @Override
    public boolean isFireResistant() {
        return true;
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entityIn;

            if (stack.getItem() instanceof CoreArmorItem && ((ICoreItem) stack.getItem()).getCore().equals(ItemInit.MAGMA_CORE_SET.get("CORE").get())
                    && ((CoreArmorItem) stack.getItem()).equipmentSlotType.equals(EquipmentSlotType.FEET) && living.getItemBySlot(EquipmentSlotType.FEET) == stack) {
                MagmaWalkerLogic.replaceField(BlockInit.MAGMA_FLOOR.get().defaultBlockState(), living.blockPosition().below(), worldIn, 3, 1);
            }

            if (stack.getItem() instanceof ICoreItem && ((ICoreItem) stack.getItem()).getCore().equals(ItemInit.MAGMA_CORE_SET.get("CORE").get())) {
                if (entityIn.isOnFire()) {
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
    public static void onLivingBurn(LivingDamageEvent event) {
        LivingEntity living = event.getEntityLiving();
        for (ItemStack stack: living.getArmorSlots()) {
            if (AbstractCoreItem.isCoreItem(stack, (AbstractCoreItem) ItemInit.MAGMA_CORE_SET.get("CORE").get())) {
                if (event.getSource().isFire()) event.setCanceled(true);
            }
        }
        for (ItemStack stack: living.getHandSlots()) {
            if (AbstractCoreItem.isCoreItem(stack, (AbstractCoreItem) ItemInit.MAGMA_CORE_SET.get("CORE").get())) {
                if (event.getSource().isFire()) event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingArmorEquip(LivingEquipmentChangeEvent event) {
        if (!event.getEntityLiving().level.isClientSide() && event.getEntityLiving() instanceof PlayerEntity) {
            LivingEntity living = event.getEntityLiving();
            ModifiableAttributeInstance chestplateAttribute = living.getAttribute(Attributes.ATTACK_DAMAGE);
            ModifiableAttributeInstance helmetAttribute = living.getAttribute(Attributes.MAX_HEALTH);
            ModifiableAttributeInstance leggingsAttribute = living.getAttribute(Attributes.KNOCKBACK_RESISTANCE);

            assert chestplateAttribute != null;
            assert helmetAttribute != null;
            assert leggingsAttribute != null;
            for (AttributeModifier modifier : helmetAttribute.getModifiers()) {
                if (modifier.getId().equals(UUID.fromString("78FA8B01-C328-F9D4-91B2-1C2D7A871A31"))) {
                    helmetAttribute.removeModifier(modifier); }}
            for (AttributeModifier modifier : chestplateAttribute.getModifiers()) {
                if (modifier.getId().equals(UUID.fromString("78FA8B01-C328-F9D4-92B2-1C2D7A871A3F"))) {
                    chestplateAttribute.removeModifier(modifier); }}
            for (AttributeModifier modifier : leggingsAttribute.getModifiers()) {
                if (modifier.getId().equals(UUID.fromString("78FA8B01-C328-F9D4-93B2-1C2D7A871A32"))) {
                    leggingsAttribute.removeModifier(modifier); }}

            for (ItemStack stack : living.getArmorSlots()) {
                if (stack.getItem() instanceof CoreArmorItem && ((CoreArmorItem)stack.getItem()).core instanceof MagmaCore) {
                    if (((CoreArmorItem)stack.getItem()).equipmentSlotType.equals(EquipmentSlotType.HEAD)) {
                        helmetAttribute.addTransientModifier(
                                new AttributeModifier(UUID.fromString("78FA8B01-C328-F9D4-91B2-1C2D7A871A31"),
                                        "magma_helmet", 0.5f, AttributeModifier.Operation.MULTIPLY_BASE));
                    }
                    if (((CoreArmorItem)stack.getItem()).equipmentSlotType.equals(EquipmentSlotType.CHEST)) {
                        chestplateAttribute.addTransientModifier(
                                new AttributeModifier(UUID.fromString("78FA8B01-C328-F9D4-92B2-1C2D7A871A3F"),
                                        "magma_chestplate",2.0f, AttributeModifier.Operation.MULTIPLY_TOTAL));
                    }
                    if (((CoreArmorItem)stack.getItem()).equipmentSlotType.equals(EquipmentSlotType.LEGS)) {
                        leggingsAttribute.addTransientModifier(
                                new AttributeModifier(UUID.fromString("78FA8B01-C328-F9D4-93B2-1C2D7A871A32"),
                                        "magma_leggings",10.0f, AttributeModifier.Operation.MULTIPLY_TOTAL));
                    }
                }
            }
        }
    }
}
