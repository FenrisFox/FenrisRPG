package com.FenrisFox86.fenris_rpg.common.blocks;

import com.FenrisFox86.fenris_rpg.common.tileentities.AbstractConverterTileEntity;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public abstract class AbstractConverterBlock extends ContainerBlock {
    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    protected AbstractConverterBlock(AbstractBlock.Properties p_i50000_1_) {
        super(p_i50000_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, Boolean.valueOf(false)));
    }

    @Nonnull
    public ActionResultType use(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos blockPos, @Nonnull PlayerEntity player,
                                @Nonnull Hand hand, @Nonnull BlockRayTraceResult rayTraceResult) {
        if (world.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            this.openContainer(world, blockPos, player);
            return ActionResultType.CONSUME;
        }
    }

    protected abstract void openContainer(World p_220089_1_, BlockPos p_220089_2_, PlayerEntity p_220089_3_);

    public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
        return this.defaultBlockState().setValue(FACING, p_196258_1_.getHorizontalDirection().getOpposite());
    }

    public void setPlacedBy(World p_180633_1_, BlockPos p_180633_2_, BlockState p_180633_3_, LivingEntity p_180633_4_, ItemStack p_180633_5_) {
        if (p_180633_5_.hasCustomHoverName()) {
            TileEntity tileentity = p_180633_1_.getBlockEntity(p_180633_2_);
            if (tileentity instanceof AbstractConverterTileEntity) {
                ((AbstractConverterTileEntity) tileentity).setCustomName(p_180633_5_.getHoverName());
            }
        }
    }

    public void onRemove(BlockState state, @Nonnull World world, @Nonnull BlockPos pos, BlockState newState, boolean p_196243_5_) {
        if (!state.is(newState.getBlock())) {
            TileEntity tileentity = world.getBlockEntity(pos);
            if (tileentity instanceof AbstractConverterTileEntity) {
                InventoryHelper.dropContents(world, pos, (AbstractConverterTileEntity) tileentity);
                ((AbstractConverterTileEntity) tileentity).getRecipesToAwardAndPopExperience(world, Vector3d.atCenterOf(pos));
                world.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, world, pos, newState, p_196243_5_);
        }
    }

    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    public int getAnalogOutputSignal(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos) {
        return Container.getRedstoneSignalFromBlockEntity(world.getBlockEntity(pos));
    }

    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
        return p_185499_1_.setValue(FACING, p_185499_2_.rotate(p_185499_1_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
        return p_185471_1_.rotate(p_185471_2_.getRotation(p_185471_1_.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(FACING, LIT);
    }
}

