package com.FenrisFox86.fenrisrpg.common.items;

import com.FenrisFox86.fenrisrpg.FenrisRPG;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class CoreVesselItem extends Item {


    public CoreVesselItem() {
        super(new Properties().tab(FenrisRPG.MOD_TAB));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.fenrisrpg.core_vessel_items.lore"));
        tooltip.add(new TranslationTextComponent("tooltip.fenrisrpg.core_vessel_items.desc"));
    }
}
