package com.FenrisFox86.fenris_rpg.common.enchantments.logic;

import com.FenrisFox86.fenris_rpg.core.init.EnchantmentInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod.EventBusSubscriber
public class LumberjackLogic {

    public static List<PosConverter> verticalNeighbors = new ArrayList<PosConverter>(Arrays.asList(
            BlockPos::above,
            BlockPos::below,
            pos -> pos
    ));

    public static List<PosConverter> northSouthNeighbors = new ArrayList<PosConverter>(Arrays.asList(
            BlockPos::north,
            BlockPos::south,
            pos -> pos
    ));

    public static List<PosConverter> eastWestNeighbors = new ArrayList<PosConverter>(Arrays.asList(
            BlockPos::east,
            BlockPos::west,
            pos -> pos
    ));

    @SubscribeEvent
    public static void OnLumberjackUsed(BlockEvent.BreakEvent event) {
        PlayerEntity player = event.getPlayer();
        if (EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.LUMBERJACK.get(), player.getMainHandItem()) > 0) {
            if (event.getState().is(net.minecraft.tags.BlockTags.LOGS)) {
                destroyConnectedBlocks(player.level, event.getPos(), event.getState(), player, player.getMainHandItem());
            }
        }
    }

    public static void destroyConnectedBlocks(World worldIn, BlockPos posIn, BlockState stateIn, PlayerEntity playerIn, ItemStack stack) {
        for (PosConverter x: northSouthNeighbors) for (PosConverter y: verticalNeighbors)for (PosConverter z: eastWestNeighbors) {
            BlockPos pos = z.convert(y.convert(x.convert(posIn)));
            if (worldIn.getBlockState(pos).getBlock().equals(stateIn.getBlock())) {
                if (!playerIn.isCreative()) {
                    worldIn.destroyBlock(pos, true);
                    stack.hurtAndBreak(1, playerIn, playerEntity -> playerEntity.broadcastBreakEvent(Hand.MAIN_HAND));
                } else {
                    worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
                }
                destroyConnectedBlocks(worldIn, pos, stateIn, playerIn, stack);
            }
        }
    }

    public static void destroySameAbove(World worldIn, BlockPos posIn, BlockState stateIn) {
        if (worldIn.getBlockState(posIn.above()).getBlock().equals(stateIn.getBlock())) {
            destroySameAbove(worldIn, posIn.above(), stateIn);
            worldIn.destroyBlock(posIn.above(), true);
        }
    }

    public interface PosConverter {
        public BlockPos convert(BlockPos pos);
    }
}
