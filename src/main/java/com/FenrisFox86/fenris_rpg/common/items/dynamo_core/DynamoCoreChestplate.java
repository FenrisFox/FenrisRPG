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
public class DynamoCoreChestplate extends DynamoCoreArmorItem {

    public DynamoCoreChestplate(IArmorMaterial materialIn) {
        super(materialIn, EquipmentSlotType.CHEST);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if(InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
            tooltip.add(new TranslationTextComponent("tooltip.fenris_rpg.dynamo_core_chestplate.lore"));
            tooltip.add(new TranslationTextComponent("tooltip.fenris_rpg.dynamo_core_chestplate.desc"));
        } else {
            tooltip.add(Tooltips.HOLD_SHIFT);
        }
    }

    @SubscribeEvent
    public static void onLivingArmorEquip(LivingEquipmentChangeEvent event) {
        if (!event.getEntityLiving().level.isClientSide() && event.getEntityLiving() instanceof PlayerEntity) {
            LivingEntity living = event.getEntityLiving();
            living.getAttributes().getInstance(Attributes.ATTACK_DAMAGE);
            ModifiableAttributeInstance attribute = living.getAttribute(Attributes.ATTACK_DAMAGE);

            assert attribute != null;
            for (AttributeModifier modifier : attribute.getModifiers()) {
                if (modifier.getId().equals(UUID.fromString("76FA8B01-C328-F9D4-9AB2-1C2D7A871A3F"))) {
                    attribute.removeModifier(modifier);
                }
            }

            for (ItemStack stack : living.getArmorSlots()) {
                if (stack.getItem() instanceof DynamoCoreChestplate) {
                    attribute.addTransientModifier(
                            new AttributeModifier(UUID.fromString("76FA8B01-C328-F9D4-9AB2-1C2D7A871A3F"),
                                    "dynamo_chestplate", 1.0f, AttributeModifier.Operation.MULTIPLY_TOTAL));
                }
            }
        }
    }
}
