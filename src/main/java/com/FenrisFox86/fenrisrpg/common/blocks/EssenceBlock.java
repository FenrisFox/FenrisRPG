package com.FenrisFox86.fenrisrpg.common.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;


public class EssenceBlock extends Block {
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    public EssenceBlock() {
        super(Properties.of(Material.GLASS)
                .strength(4.0f, 5.0f)
                .sound(SoundType.GLASS)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
                .speedFactor(3)
                .jumpFactor(5)
        );
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 15;
    }

    @Override
    public void stepOn(World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof WitherSkeletonEntity) {
            if (worldIn instanceof ServerWorld) {
                entityIn.remove();
                EntityType.WITHER.spawn(
                        (ServerWorld)worldIn,
                        (ItemStack) null,
                        (PlayerEntity) null,
                        pos.above(),
                        SpawnReason.TRIGGERED,
                        true,
                        true);
            }
        }
    }
}
