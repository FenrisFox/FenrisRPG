package com.FenrisFox86.fenris_rpg.common.blocks;

import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolType;


public class EssenceOreBlock extends OreBlock {

    public EssenceOreBlock() {
        super(Properties.of(Material.STONE)
                .strength(6.0F, 1200.0F)
                .sound(SoundType.STONE)
                .harvestLevel(3)
                .harvestTool(ToolType.PICKAXE)
                .requiresCorrectToolForDrops()
        );
    }
}
