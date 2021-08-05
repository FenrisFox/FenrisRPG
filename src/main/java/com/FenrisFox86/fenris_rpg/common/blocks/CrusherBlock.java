package com.FenrisFox86.fenris_rpg.common.blocks;

import java.util.Random;

import com.FenrisFox86.fenris_rpg.common.tileentities.CrusherTileEntity;
import com.FenrisFox86.fenris_rpg.core.init.SoundEventInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class CrusherBlock extends AbstractConverterBlock {
    public CrusherBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    public TileEntity newBlockEntity(IBlockReader blockReader) {
        return new CrusherTileEntity();
    }

    protected void openContainer(World world, BlockPos pos, PlayerEntity player) {
        TileEntity tileentity = world.getBlockEntity(pos);
        if (tileentity instanceof CrusherTileEntity) {
            player.openMenu((INamedContainerProvider)tileentity);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState blockState, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random random) {
        if (blockState.getValue(LIT)) {
            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY();
            double d2 = (double)pos.getZ() + 0.5D;
            if (random.nextDouble() < 0.5D) {
                world.playLocalSound(d0, d1, d2, SoundEventInit.CRUSHER_CRUSH.get(), SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = blockState.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d3 = 0.52D;
            double d4 = random.nextDouble() * 0.6D - 0.3D;
            double d5 = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * d3 : d4;
            double d6 = random.nextDouble() * 6.0D / 16.0D;
            double d7 = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * d3 : d4;
            world.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, -0.3D, 0.0D);
            world.addParticle(ParticleTypes.ASH, d0 + d5, d1 + d6, d2 + d7, 0.0D, -0.3D, 0.0D);
        }
    }
}