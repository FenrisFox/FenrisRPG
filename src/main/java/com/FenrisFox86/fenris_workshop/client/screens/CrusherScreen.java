package com.FenrisFox86.fenris_workshop.client.screens;

import com.FenrisFox86.fenris_workshop.FenrisWorkshop;
import com.FenrisFox86.fenris_workshop.common.containers.AbstractConverterContainer;
import com.FenrisFox86.fenris_workshop.common.containers.CrusherContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.recipebook.FurnaceRecipeGui;
import net.minecraft.client.gui.screen.inventory.AbstractFurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.FurnaceContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrusherScreen extends AbstractConverterScreen<CrusherContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(FenrisWorkshop.MOD_ID, "textures/gui/crusher.png");

    public CrusherScreen(CrusherContainer container, PlayerInventory playerInv, ITextComponent name) {
        super(container, playerInv, name, TEXTURE);
    }

}