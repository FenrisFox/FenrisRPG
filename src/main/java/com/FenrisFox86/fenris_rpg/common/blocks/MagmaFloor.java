package com.FenrisFox86.fenris_rpg.common.blocks;

import com.FenrisFox86.fenris_rpg.common.tileentities.AbstractConverterTileEntity;
import com.FenrisFox86.fenris_rpg.common.tileentities.MagmaFloorTileEntity;
import com.FenrisFox86.fenris_rpg.core.init.BlockInit;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Random;

public class MagmaFloor extends Block  implements ITileEntityProvider {

    public MagmaFloor() {
        super(Properties.of(Material.STONE));
    }

    public TileEntity newBlockEntity(IBlockReader blockReader) {
        return new MagmaFloorTileEntity();
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

    @Nonnull
    public BlockRenderType getRenderShape(@Nonnull BlockState stateIn) {
        return BlockRenderType.MODEL;
    }

    public void onRemove(BlockState state, @Nonnull World world, @Nonnull BlockPos pos, BlockState newState, boolean p_196243_5_) {
        if (!state.is(newState.getBlock())) {
            TileEntity tileentity = world.getBlockEntity(pos);
            super.onRemove(state, world, pos, newState, p_196243_5_);
        }
    }
}
