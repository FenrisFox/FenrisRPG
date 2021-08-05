package com.FenrisFox86.fenris_workshop.common.tileentities;

import com.FenrisFox86.fenris_workshop.common.containers.CrusherContainer;
import com.FenrisFox86.fenris_workshop.core.init.RecipeInit;
import com.FenrisFox86.fenris_workshop.core.init.TileEntityTypeInit;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class CrusherTileEntity extends AbstractConverterTileEntity<CrusherTileEntity> {
    public CrusherTileEntity() {
        super(TileEntityTypeInit.CRUSHER_TILE_ENTITY_TYPE.get(), RecipeInit.CRUSHING_RECIPE);
        this.convertAnything = true;
    }

    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.fenris_workshop.crusher");
    }

    protected Container createMenu(int p_213906_1_, PlayerInventory playerInv) {
        return new CrusherContainer(p_213906_1_, playerInv, this, this.dataAccess);
    }
}