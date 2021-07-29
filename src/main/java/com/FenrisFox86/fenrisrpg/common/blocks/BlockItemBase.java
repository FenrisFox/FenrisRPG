package com.FenrisFox86.fenrisrpg.common.blocks;

import com.FenrisFox86.fenrisrpg.FenrisRPG;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class BlockItemBase extends BlockItem {
    public BlockItemBase(Block block) {
        super(block, new Item.Properties().tab(FenrisRPG.MOD_TAB));
    }
}
