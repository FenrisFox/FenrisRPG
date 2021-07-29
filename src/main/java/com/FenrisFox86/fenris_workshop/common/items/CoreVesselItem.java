package com.FenrisFox86.fenris_workshop.common.items;

import com.FenrisFox86.fenris_workshop.FenrisWorkshop;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class CoreVesselItem extends Item {


    public CoreVesselItem() {
        super(new Properties().tab(FenrisWorkshop.MOD_TAB));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.fenris_workshop.core_vessel_items.lore"));
        tooltip.add(new TranslationTextComponent("tooltip.fenris_workshop.core_vessel_items.desc"));
    }
}
