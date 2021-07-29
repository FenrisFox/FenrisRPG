package com.FenrisFox86.fenris_workshop.common.blocks;

import com.FenrisFox86.fenris_workshop.FenrisWorkshop;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class BlockItemBase extends BlockItem {
    public BlockItemBase(Block block) {
        super(block, new Item.Properties().tab(FenrisWorkshop.MOD_TAB));
    }
}
