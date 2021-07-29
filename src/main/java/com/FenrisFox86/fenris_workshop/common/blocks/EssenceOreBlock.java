package com.FenrisFox86.fenris_workshop.common.blocks;

import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;


public class EssenceOreBlock extends OreBlock {

    public EssenceOreBlock() {
        super(Properties.of(Material.STONE)
                .strength(6.0f,8.0f)
                .sound(SoundType.STONE)
                .harvestLevel(3)
                .harvestTool(ToolType.PICKAXE)
        );
    }
}
