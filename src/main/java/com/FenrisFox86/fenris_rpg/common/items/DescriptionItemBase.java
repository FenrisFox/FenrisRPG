package com.FenrisFox86.fenris_rpg.common.items;

import com.FenrisFox86.fenris_rpg.FenrisRPG;
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

import java.util.Arrays;
import java.util.List;

public class DescriptionItemBase extends Item {

    private final TranslationTextComponent[] translationComponents;
    private final String[] text_info;

    public DescriptionItemBase(TranslationTextComponent... translation_text_Components) {
        super(new Properties().tab(FenrisRPG.MOD_TAB));
        this.translationComponents = translation_text_Components;
        this.text_info = null;
    }

    public DescriptionItemBase(String... text_info) {
        super(new Properties().tab(FenrisRPG.MOD_TAB));
        this.text_info = text_info;
        this.translationComponents = null;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if(InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
            if (translationComponents != null) {tooltip.addAll(Arrays.asList(translationComponents));}
            if (text_info != null) {for (String desc: text_info) { tooltip.add(new TranslationTextComponent(desc)); }}
        } else {
            tooltip.add(Tooltips.HOLD_SHIFT);
        }
    }
}
