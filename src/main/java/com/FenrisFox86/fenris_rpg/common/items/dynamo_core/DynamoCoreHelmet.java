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

@Mod.EventBusSubscriber
public class DynamoCoreHelmet extends DynamoCoreArmorItem {

    public DynamoCoreHelmet(IArmorMaterial materialIn) {
        super(materialIn, EquipmentSlotType.HEAD);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if(InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
            tooltip.add(new TranslationTextComponent("tooltip.fenris_rpg.dynamo_core_helmet.lore"));
            tooltip.add(new TranslationTextComponent("tooltip.fenris_rpg.dynamo_core_helmet.desc"));
        } else {
            tooltip.add(Tooltips.HOLD_SHIFT);
        }
    }

    @SubscribeEvent
    public static void onLivingArmorEquip(LivingEquipmentChangeEvent event) {
        if (!event.getEntityLiving().level.isClientSide() && event.getEntityLiving() instanceof PlayerEntity) {
            LivingEntity living = event.getEntityLiving();
            ModifiableAttributeInstance attribute = living.getAttributes().getInstance(Attributes.LUCK);

            if (attribute != null) {
                for (AttributeModifier modifier : attribute.getModifiers()) {
                    if (modifier.getName().equals("dynamo_helmet")) {
                        attribute.removeModifier(modifier);
                    }
                }
            }

            for (ItemStack stack : living.getArmorSlots()) {
                if (stack.getItem() instanceof DynamoCoreHelmet) {
                    attribute.addTransientModifier(
                            new AttributeModifier("dynamo_helmet", 4.0f, AttributeModifier.Operation.MULTIPLY_TOTAL));
                }
            }
        }
    }
}
