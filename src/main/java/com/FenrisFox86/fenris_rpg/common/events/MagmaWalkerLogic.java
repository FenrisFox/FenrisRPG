package com.FenrisFox86.fenris_rpg.common.events;

import com.FenrisFox86.fenris_rpg.common.tileentities.MagmaFloorTileEntity;
import com.FenrisFox86.fenris_rpg.core.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;


@Mod.EventBusSubscriber
public class MagmaWalkerLogic {

    public static void replaceNorthSouth(BlockState state, BlockPos cursor, World worldIn, int amount) {
        BlockPos cursorNorth = cursor;
        BlockPos cursorSouth = cursor;

        replaceBlockIfType(worldIn, cursor, BlockInit.MAGMA_FLOOR.get().defaultBlockState(), Blocks.LAVA);
        for (int i = 0; i < amount; i++) {
            cursorNorth = cursorNorth.north();
            cursorSouth = cursorSouth.south();

            replaceBlockIfType(worldIn, cursorNorth, BlockInit.MAGMA_FLOOR.get().defaultBlockState(), Blocks.LAVA);
            replaceBlockIfType(worldIn, cursorSouth, BlockInit.MAGMA_FLOOR.get().defaultBlockState(), Blocks.LAVA);
        }
    }

    public static void replaceField(BlockState state, BlockPos cursor, World worldIn, int amount, int edge) {
        BlockPos cursorEast = cursor;
        BlockPos cursorWest = cursor;
        replaceNorthSouth(BlockInit.MAGMA_FLOOR.get().defaultBlockState(), cursor, worldIn, amount);
        int edgeOff = 0;
        for (int i = 0; i < amount; i++) {
            if (amount-i <= edge) edgeOff += 1;
            cursorEast = cursorEast.east();
            cursorWest = cursorWest.west();

            replaceNorthSouth(BlockInit.MAGMA_FLOOR.get().defaultBlockState(), cursorEast, worldIn, amount-edgeOff);
            replaceNorthSouth(BlockInit.MAGMA_FLOOR.get().defaultBlockState(), cursorWest, worldIn, amount-edgeOff);
        }
    }

    public static void replaceField(BlockState state, BlockPos cursor, World worldIn, int amount) {
        replaceField(state, cursor, worldIn, amount, 0);
    }

    public static void replaceBlockIfType(World worldIn, BlockPos pos, BlockState state, Block requiredBlock) {
        if (worldIn.getBlockState(pos).getBlock().equals(requiredBlock) || worldIn.getBlockState(pos).getBlock().equals(state.getBlock())) {
            if (worldIn.getBlockEntity(pos) != null && worldIn.getBlockEntity(pos) instanceof MagmaFloorTileEntity) {
                ((MagmaFloorTileEntity) Objects.requireNonNull(worldIn.getBlockEntity(pos))).resetDecay();
            }
            worldIn.setBlock(pos, state, 0);
        }
    }
}
