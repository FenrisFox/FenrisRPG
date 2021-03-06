package com.FenrisFox86.fenris_rpg.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;


public class BlockBase extends Block{

    public BlockBase(
            Material material,
            float hardness,
            float resistance,
            int harvestLevel,
            ToolType toolType,
            SoundType soundType
            ) {
        super(Properties.of(material)
        .strength(hardness, resistance)
        .sound(soundType)
        .harvestLevel(harvestLevel)
        .harvestTool(toolType)
        );

    }
}
