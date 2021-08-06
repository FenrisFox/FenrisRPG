package com.FenrisFox86.fenris_rpg.common.tileentities;

import com.FenrisFox86.fenris_rpg.common.recipes.AbstractConversionRecipe;
import com.FenrisFox86.fenris_rpg.core.init.RecipeInit;
import com.FenrisFox86.fenris_rpg.core.init.TileEntityTypeInit;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.*;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MagmaFloorTileEntity extends TileEntity implements ITickableTileEntity {

    public int decayStage;

    public MagmaFloorTileEntity() {
        super(TileEntityTypeInit.MAGMA_FLOOR_TILE_ENTITY.get());
        this.decayStage = 100;
    }

    public void tick() {
        decayStage--;
        System.out.println(decayStage);
        if (decayStage < 1) {
            assert this.level != null;
            this.level.setBlock(this.worldPosition, Blocks.LAVA.defaultBlockState(), 0);
        }
    }
}
