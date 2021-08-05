package com.FenrisFox86.fenris_rpg.common.containers;

import com.FenrisFox86.fenris_rpg.core.init.ContainerTypeInit;
import com.FenrisFox86.fenris_rpg.core.init.RecipeInit;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;

public class CrusherContainer extends AbstractConverterContainer {
    public CrusherContainer(int windowId, PlayerInventory playerInv) {
        super(ContainerTypeInit.CRUSHER_CONTAINER_TYPE.get(), RecipeInit.CRUSHING_RECIPE, windowId, playerInv);
    }

    public CrusherContainer(final int windowId, final PlayerInventory playerInv, final PacketBuffer data) {
        super(ContainerTypeInit.CRUSHER_CONTAINER_TYPE.get(), RecipeInit.CRUSHING_RECIPE, windowId, playerInv);
    }

    public CrusherContainer(int windowId, PlayerInventory playerInv, IInventory inv, IIntArray data) {
        super(ContainerTypeInit.CRUSHER_CONTAINER_TYPE.get(), RecipeInit.CRUSHING_RECIPE, windowId, playerInv, inv, data);
    }
}