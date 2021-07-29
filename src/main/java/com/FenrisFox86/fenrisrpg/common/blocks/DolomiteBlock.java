package com.FenrisFox86.fenrisrpg.common.blocks;

import com.FenrisFox86.fenrisrpg.core.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class DolomiteBlock extends Block {

    public DolomiteBlock() {
        super(Properties.of(Material.STONE)
        .strength(1.0f,1.0f)
        .sound(SoundType.STONE)
        .harvestLevel(1)
        .harvestTool(ToolType.PICKAXE)
        );
    }

    @Override
    public void stepOn(World worldIn, BlockPos pos, Entity entityIn) {
        super.stepOn(worldIn, pos, entityIn);
        if (Math.random()>0.99F) {
            worldIn.setBlock(pos, BlockInit.DOLOMITE_SAND.get().defaultBlockState(), 0);
        }
    }

    @Override
    public void fallOn(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        super.fallOn(worldIn, pos, entityIn, fallDistance);
        worldIn.setBlock(pos, BlockInit.DOLOMITE_SAND.get().defaultBlockState(), 0);
    }
}
