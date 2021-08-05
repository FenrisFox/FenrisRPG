package com.FenrisFox86.fenris_rpg.common.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;


public class OreBlockBase extends OreBlock{
    public int xpDrop;

    public OreBlockBase(
            Material materialIn,
            float hardnessIn,
            float resistanceIn,
            int harvestLevelIn,
            ToolType toolTypeIn,
            SoundType soundTypeIn,
            @Nullable int xpDropIn
            ) {
        super(Properties.of(materialIn)
        .strength(hardnessIn, resistanceIn)
        .sound(soundTypeIn)
        .harvestLevel(harvestLevelIn)
        .harvestTool(toolTypeIn)
        .requiresCorrectToolForDrops()
        );
        xpDrop = xpDropIn;

    }

    @Override
    public int getExpDrop(BlockState state, IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
        return xpDrop;
    }

}
