package com.FenrisFox86.fenris_rpg.common.items.dynamo_core;

import com.FenrisFox86.fenris_rpg.common.items.Tooltips;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber
public class DynamoCoreLeggings extends DynamoCoreArmorItem {

    public DynamoCoreLeggings(IArmorMaterial materialIn) {
        super(materialIn, EquipmentSlotType.LEGS);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if(InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
            tooltip.add(new TranslationTextComponent("tooltip.fenris_rpg.dynamo_core_leggings.lore"));
            tooltip.add(new TranslationTextComponent("tooltip.fenris_rpg.dynamo_core_leggings.desc"));
        } else {
            tooltip.add(Tooltips.HOLD_SHIFT);
        }
    }

    @SubscribeEvent
    public static void onLivingArmorEquip(LivingEquipmentChangeEvent event) {
        if (!event.getEntityLiving().level.isClientSide() && event.getEntityLiving() instanceof PlayerEntity) {
            LivingEntity living = event.getEntityLiving();
            living.getAttributes().getInstance(Attributes.MOVEMENT_SPEED);
            ModifiableAttributeInstance attribute = living.getAttribute(Attributes.MOVEMENT_SPEED);

            assert attribute != null;
            for (AttributeModifier modifier : attribute.getModifiers()) {
                if (modifier.getId().equals(UUID.fromString("60228c5b-7776-438f-97c7-69b09a860b80"))) {
                    attribute.removeModifier(modifier);
                }
            }

            for (ItemStack stack : living.getArmorSlots()) {
                if (stack.getItem() instanceof DynamoCoreLeggings) {
                    attribute.addTransientModifier(
                            new AttributeModifier(UUID.fromString("60228c5b-7776-438f-97c7-69b09a860b80"),
                                    "dynamo_leggings", 1.0f, AttributeModifier.Operation.MULTIPLY_TOTAL));
                }
            }
        }
    }
}
