package com.FenrisFox86.fenris_workshop.common.items.dynamo_core;

import com.FenrisFox86.fenris_workshop.FenrisWorkshop;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public abstract class DynamoCoreArmorItem extends ArmorItem {

    public DynamoCoreArmorItem(IArmorMaterial materialIn, EquipmentSlotType type) {
        super(materialIn, type, new Properties().tab(FenrisWorkshop.MOD_TAB));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(worldIn.getDayTime()%20 == 0) {
            if(stack.getItem() instanceof DynamoCoreLeggings && entityIn instanceof PlayerEntity) {
                if(entityIn.isSprinting()) {
                    if (stack.getItem() instanceof DynamoCoreArmorItem && stack.getEquipmentSlot() != null) {
                        stack.hurtAndBreak(-8, (LivingEntity) entityIn, p -> p.broadcastBreakEvent(stack.getEquipmentSlot()));
                    }
                }
            }
        }
    }

    /*
    @SubscribeEvent
    public static void onLivingArmorEquip(LivingEquipmentChangeEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            LivingEntity living = event.getEntityLiving();
            living.getAttributes().getInstance(Attributes.MOVEMENT_SPEED);
            ModifiableAttributeInstance attribute = living.getAttribute(Attributes.MOVEMENT_SPEED);

            for (AttributeModifier modifier : attribute.getModifiers()) {
                if (modifier.getName().equals("dynamo_MOVEMENT_SPEED")) {
                    attribute.removeModifier(modifier);
                }
            }

            for (ItemStack stack : living.getArmorSlots()) {
                if (stack.getItem() instanceof DynamoCoreArmorItem) {
                    attribute.addTransientModifier(
                            new AttributeModifier("dynamo_MOVEMENT_SPEED", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL));
                }
            }
        }
    }*/
}
