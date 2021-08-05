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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBase extends Item {

    private boolean isFoil = false;
    private final List<String> lore = new ArrayList<String>(){};
    private boolean shiftLore = false;

    public ItemBase() {
        super(new Item.Properties().tab(FenrisRPG.MOD_TAB));
    }

    public ItemBase(Item.Properties properties) {
        super(properties);
    }

    public ItemBase foilEffect() {
        this.isFoil = true;
        return this;
    }

    public ItemBase shiftForLore() {
        this.shiftLore = true;
        return this;
    }

    public ItemBase description(String desc) {
        this.lore.add(desc);
        return this;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isFoil;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if(InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT) || !this.shiftLore) {
            for (String desc: lore) { tooltip.add(new TranslationTextComponent(desc)); }
        } else {
            tooltip.add(Tooltips.HOLD_SHIFT);
        }
    }
}
