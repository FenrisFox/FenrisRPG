package com.FenrisFox86.fenris_rpg.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Random;

public class MagmaFloor extends Block {
    public static final IntegerProperty DECAY_STAGE = BlockStateProperties.STAGE;

    public MagmaFloor(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState blockState, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random random) {
        double x = (double)pos.getX() + 0.5D;
        double y = (double)pos.getY() + 1.02D;
        double z = (double)pos.getZ() + 0.5D;
        double randX = random.nextDouble()-0.5D;
        double randZ = random.nextDouble()-0.5D;;
        world.addParticle(ParticleTypes.SMOKE, x + randX, y, z + randZ, 0.0D, -0.0D, 0.0D);
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateContainerIn) {
        stateContainerIn.add(DECAY_STAGE);
    }

    @Nonnull
    public BlockRenderType getRenderShape(@Nonnull BlockState stateIn) {
        return BlockRenderType.MODEL;
    }

    public BlockState getStateForPlacement(@Nonnull BlockItemUseContext context) {
        return this.defaultBlockState().setValue(DECAY_STAGE, 10);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(@Nonnull BlockState stateIn, @Nonnull ServerWorld worldIn, @Nonnull BlockPos posIn, @Nonnull Random random) {
        super.tick(stateIn, worldIn, posIn, random);
        stateIn.setValue(DECAY_STAGE, stateIn.getValue(DECAY_STAGE)-1);
        if (stateIn.getValue(DECAY_STAGE) < 1) {
            worldIn.setBlock(posIn, Blocks.AIR.defaultBlockState(), 0);
        }
    }
}