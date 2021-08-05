package com.FenrisFox86.fenris_rpg.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;


public class RubyBlock extends Block {

    public RubyBlock() {
        super(Block.Properties.of(Material.STONE)
        .strength(5.0f,6.0f)
        .sound(SoundType.GLASS)
        .harvestLevel(2)
        .harvestTool(ToolType.PICKAXE)

        );
    }
}
