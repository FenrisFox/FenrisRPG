package com.FenrisFox86.fenris_rpg.client.screens;

import com.FenrisFox86.fenris_rpg.FenrisRPG;
import com.FenrisFox86.fenris_rpg.common.containers.CrusherContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrusherScreen extends AbstractConverterScreen<CrusherContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(FenrisRPG.MOD_ID, "textures/gui/crusher.png");

    public CrusherScreen(CrusherContainer container, PlayerInventory playerInv, ITextComponent name) {
        super(container, playerInv, name, TEXTURE);
        this.inventoryLabelY ++;
    }

}