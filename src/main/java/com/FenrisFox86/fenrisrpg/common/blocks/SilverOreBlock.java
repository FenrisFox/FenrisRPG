package com.FenrisFox86.fenrisrpg.common.blocks;

import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;


public class SilverOreBlock extends OreBlock {

    public SilverOreBlock() {
        super(Properties.of(Material.STONE)
                .strength(5.0f,6.0f)
                .sound(SoundType.STONE)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)

        );
    }
}
