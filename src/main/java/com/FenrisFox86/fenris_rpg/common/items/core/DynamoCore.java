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
import java.util.Objects;
import java.util.UUID;

@Mod.EventBusSubscriber
public class DynamoCore extends AbstractCoreItem {

    public DynamoCore() {
        super("dynamo_core", new Properties().tab(FenrisRPG.MOD_TAB).durability(256));
        this.itemTier = ModItemTier.DYNAMO_CORE;
        this.armorMaterial = ModArmorMaterial.DYNAMO_CORE_ARMOR;
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
            playerIn.addEffect(new EffectInstance(Effects.JUMP, 500, 1));
            playerIn.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 500, 4));
            playerIn.addEffect(new EffectInstance(Effects.DIG_SPEED, 500, 4));
            playerIn.getCooldowns().addCooldown(item,60);
            playerIn.getItemInHand(handIn).hurtAndBreak(16, playerIn, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
            return ActionResult.success(playerIn.getItemInHand(handIn));
        }
        return ActionResult.fail(playerIn.getItemInHand(handIn));
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        if(entity.isAlive()) {
            ((LivingEntity) entity).addEffect(new EffectInstance(Effects.GLOWING, 100));
            ((LivingEntity) entity).removeEffect(Effects.MOVEMENT_SPEED);
            ((LivingEntity) entity).removeEffect(Effects.JUMP);
            ((LivingEntity) entity).removeEffect(Effects.DIG_SPEED);
            //entity.setSecondsOnFire(3);
        }
        return false;
    }

    @Override
    public boolean isFireResistant() {
        return true;
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(worldIn.getDayTime()%20 == 0) {
            if(stack.getItem() instanceof ICoreItem && entityIn instanceof PlayerEntity && ((ICoreItem)stack.getItem()).getCore() instanceof DynamoCore) {
                if(entityIn.isSprinting()) {
                    LivingEntity living = (LivingEntity) entityIn;
                    stack.hurtAndBreak(-8, living, p ->
                            p.broadcastBreakEvent(Objects.requireNonNull(stack.getEquipmentSlot())));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingArmorEquip(LivingEquipmentChangeEvent event) {
        if (!event.getEntityLiving().level.isClientSide() && event.getEntityLiving() instanceof PlayerEntity) {
            LivingEntity living = event.getEntityLiving();
            ModifiableAttributeInstance knockbackAttribute = living.getAttribute(Attributes.ATTACK_KNOCKBACK);
            ModifiableAttributeInstance attackSpeedAttribute = living.getAttribute(Attributes.ATTACK_SPEED);
            ModifiableAttributeInstance speedAttribute = living.getAttribute(Attributes.MOVEMENT_SPEED);

            assert knockbackAttribute != null;
            assert attackSpeedAttribute != null;
            assert speedAttribute != null;
            for (AttributeModifier modifier : attackSpeedAttribute.getModifiers()) {
                if (modifier.getId().equals(UUID.fromString("76FA8B01-C328-F9D4-9AB2-1C2D7A871A31"))) {
                    attackSpeedAttribute.removeModifier(modifier); }}
            for (AttributeModifier modifier : knockbackAttribute.getModifiers()) {
                if (modifier.getId().equals(UUID.fromString("76FA8B01-C328-F9D4-9AB2-1C2D7A871A3F"))) {
                    knockbackAttribute.removeModifier(modifier); }}
            for (AttributeModifier modifier : speedAttribute.getModifiers()) {
                if (modifier.getId().equals(UUID.fromString("76FA8B01-C328-F9D4-9AB2-1C2D7A871A32"))) {
                    speedAttribute.removeModifier(modifier); }}

            for (ItemStack stack : living.getArmorSlots()) {
                if (stack.getItem() instanceof CoreArmorItem && ((CoreArmorItem)stack.getItem()).core instanceof DynamoCore) {
                    if (((CoreArmorItem)stack.getItem()).equipmentSlotType.equals(EquipmentSlotType.HEAD)) {
                        attackSpeedAttribute.addTransientModifier(
                                new AttributeModifier(UUID.fromString("76FA8B01-C328-F9D4-9AB2-1C2D7A871A31"),
                                        "dynamo_helmet", 3.0f, AttributeModifier.Operation.MULTIPLY_TOTAL));
                    }
                    if (((CoreArmorItem)stack.getItem()).equipmentSlotType.equals(EquipmentSlotType.CHEST)) {
                        knockbackAttribute.addTransientModifier(
                                new AttributeModifier(UUID.fromString("76FA8B01-C328-F9D4-9AB2-1C2D7A871A3F"),
                                        "dynamo_chestplate",3.0f, AttributeModifier.Operation.MULTIPLY_TOTAL));
                    }
                    if (((CoreArmorItem)stack.getItem()).equipmentSlotType.equals(EquipmentSlotType.LEGS)) {
                        speedAttribute.addTransientModifier(
                                new AttributeModifier(UUID.fromString("76FA8B01-C328-F9D4-9AB2-1C2D7A871A32"),
                                        "dynamo_leggings",2.0f, AttributeModifier.Operation.MULTIPLY_TOTAL));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void OnLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity living = event.getEntityLiving();
        Item boots = living.getItemBySlot(EquipmentSlotType.FEET).getItem();

        if (boots instanceof CoreArmorItem && ((CoreArmorItem)boots).core instanceof DynamoCore) {
            Vector3d vector = living.getDeltaMovement().multiply(1, 2, 1);
            living.setDeltaMovement(vector);

        }
    }

    @SubscribeEvent
    public static void OnLivingFall(LivingFallEvent event) {
        LivingEntity living = event.getEntityLiving();
        Item boots = living.getItemBySlot(EquipmentSlotType.FEET).getItem();

        if (boots instanceof CoreArmorItem && ((CoreArmorItem)boots).core instanceof DynamoCore) {
            event.setCanceled(true);
        }
    }
}
