package com.FenrisFox86.fenris_rpg.common.blocks;

import com.FenrisFox86.fenris_rpg.FenrisRPG;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class BlockItemBase extends BlockItem {
    public BlockItemBase(Block block) {
        super(block, new Item.Properties().tab(FenrisRPG.MOD_TAB));
    }
}
