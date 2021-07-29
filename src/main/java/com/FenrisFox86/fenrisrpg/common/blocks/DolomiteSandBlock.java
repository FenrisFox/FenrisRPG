package com.FenrisFox86.fenrisrpg.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.SandBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

public class DolomiteSandBlock extends FallingBlock {

    public DolomiteSandBlock() {
        super(Properties.of(Material.SAND)
        .strength(1.0f,1.0f)
        .sound(SoundType.SAND)
        .harvestLevel(1)
        .harvestTool(ToolType.SHOVEL)
        );
    }

    @Override
    public void stepOn(World worldIn, BlockPos pos, Entity entityIn) {
        if (worldIn instanceof ServerWorld) {
            super.stepOn(worldIn, pos, entityIn);
            this.tick(worldIn.getBlockState(pos), (ServerWorld) worldIn, pos, worldIn.random);
        }
    }
}
