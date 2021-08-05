package com.FenrisFox86.fenris_rpg.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;


public class BronzeBlock extends Block{

    public BronzeBlock() {
        super(Block.Properties.of(Material.METAL)
        .strength(4.0f,5.0f)
        .sound(SoundType.METAL)
        .harvestLevel(1)
        .harvestTool(ToolType.PICKAXE)

        );
    }
}
