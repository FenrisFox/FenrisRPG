package com.FenrisFox86.fenris_rpg.common.tileentities;

import com.FenrisFox86.fenris_rpg.common.recipes.AbstractConversionRecipe;
import com.FenrisFox86.fenris_rpg.core.init.RecipeInit;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
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
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public abstract class AbstractConverterTileEntity<T extends AbstractConverterTileEntity> extends LockableTileEntity implements ISidedInventory, IRecipeHelperPopulator, ITickableTileEntity {
    public static int[] INPUT_SLOT_INDEXES = {0, 1, 2};
    public static int[] FUEL_SLOT_INDEXES = {3, 4, 5};
    public static int[] OUTPUT_SLOT_INDEXES = {6, 7, 8, 9, 10, 11, 12, 13, 14};
    List<ItemStack> INPUT_SLOTS;
    List<ItemStack> FUEL_SLOTS;
    List<ItemStack> OUTPUT_SLOTS;
    int[] SLOTS_FOR_UP;
    int[] SLOTS_FOR_DOWN;
    int[] SLOTS_FOR_SIDES;
    protected NonNullList<ItemStack> items = NonNullList.withSize(15, ItemStack.EMPTY);
    private int litTime;
    private int litDuration;
    private int conversionPregress;
    private int conversionTotalTime;

    public IRecipe<?> activeRecipe;
    public ItemStack activeInputStack = ItemStack.EMPTY;

    public boolean convertAnything;
    public ItemStack replacementOutput;

    protected final IIntArray dataAccess = new IIntArray() {
        public int get(int c) {
            switch (c) {
                case 0:
                    return AbstractConverterTileEntity.this.litTime;
                case 1:
                    return AbstractConverterTileEntity.this.litDuration;
                case 2:
                    return AbstractConverterTileEntity.this.conversionPregress;
                case 3:
                    return AbstractConverterTileEntity.this.conversionTotalTime;
                default:
                    return 0;
            }
        }

        public void set(int c, int value) {
            switch (c) {
                case 0:
                    AbstractConverterTileEntity.this.litTime = value;
                    break;
                case 1:
                    AbstractConverterTileEntity.this.litDuration = value;
                    break;
                case 2:
                    AbstractConverterTileEntity.this.conversionPregress = value;
                    break;
                case 3:
                    AbstractConverterTileEntity.this.conversionTotalTime = value;
            }

        }

        public int getCount() {
            return 4;
        }
    };
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    protected final IRecipeType<? extends AbstractConversionRecipe> recipeType;

    protected AbstractConverterTileEntity(TileEntityType<?> tileEntityType, IRecipeType<? extends AbstractConversionRecipe> recipeType) {
        super(tileEntityType);
        this.updateStacks();
        this.recipeType = recipeType;
        this.convertAnything = false;
        this.replacementOutput = ItemStack.EMPTY;
    }

    public void updateStacks() {
        this.INPUT_SLOTS = this.getStackArray(AbstractConverterTileEntity.INPUT_SLOT_INDEXES);
        this.FUEL_SLOTS = this.getStackArray(AbstractConverterTileEntity.FUEL_SLOT_INDEXES);
        this.OUTPUT_SLOTS = this.getStackArray(AbstractConverterTileEntity.OUTPUT_SLOT_INDEXES);
        this.SLOTS_FOR_UP = T.INPUT_SLOT_INDEXES;
        this.SLOTS_FOR_SIDES = T.FUEL_SLOT_INDEXES;
        this.SLOTS_FOR_DOWN = T.OUTPUT_SLOT_INDEXES; //ArrayUtils.addAll(T.FUEL_SLOT_INDEXES.clone(), T.OUTPUT_SLOT_INDEXES);
    }

    public List<ItemStack> getStackArray(int[]... intArray) {
        ArrayList<ItemStack> stackArray = new ArrayList<ItemStack>() {
        };
        for (int[] array : intArray) {
            for (int index : array) {
                stackArray.add(this.items.get(index));
            }
        }
        return stackArray;
    }

    @Deprecated
    public static Map<Item, Integer> getFuel() {
        return AbstractFurnaceTileEntity.getFuel();
    }

    private boolean isLit() {
        return this.litTime > 0;
    }

    public static ItemStack getFirstStack(List<ItemStack> stacks) {
        for (ItemStack stack : stacks) {
            if (!stack.isEmpty()) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    public ItemStack getFirstEmptyStack(List<ItemStack> stacks) {
        return getFirstEmptyStackIndex(stacks) != null ? this.items.get(getFirstEmptyStackIndex(stacks)): stacks.get(0);
        /*for (ItemStack stack : stacks) {
            if (stack.isEmpty()) {
                return stack;
            }
        }
        return stacks.get(0);*/
    }

    public Integer getFirstEmptyStackIndex(List<ItemStack> stacks) {
        for (int index = 0;index<stacks.size(); index ++) {
            if (this.items.get(index).isEmpty()) {
                return index;
            }
        }
        return null;
    }

    public ItemStack getActiveInputStack() {
        for (ItemStack slot : this.INPUT_SLOTS) {
            if ((this.activeRecipe != null && this.activeRecipe.getIngredients().get(0).test(slot)) ||
                    (this.activeRecipe == null && !slot.isEmpty())) {
                return slot;
            }
            if (this.activeRecipe != null) {
                this.getActiveRecipe();
            }
        }
        return ItemStack.EMPTY;
    }


    private IRecipe<?> getActiveRecipe() {
        if (!this.activeInputStack.isEmpty() && this.activeRecipe.getIngredients().get(0).test(this.activeInputStack)) {
            return this.activeRecipe;
        }
        for (ItemStack stack : this.INPUT_SLOTS) {
            assert this.level != null;
            for (IRecipe<?> recipe : RecipeInit.getRecipes(this.recipeType, this.level.getRecipeManager()).values()) {
                if (recipe.getIngredients().get(0).test(stack)) {
                    this.activeInputStack = stack;
                    this.activeRecipe = recipe;
                    return recipe;
                }
            }
        }
        this.activeRecipe = null;
        return null;
    }

    public boolean canFitInSlotArray(List<ItemStack> slots, ItemStack stack) {
        return fittingSlot(slots, stack) != null;
    }

    public ItemStack fittingSlot(List<ItemStack> slots, ItemStack stack) {
        for (ItemStack slot : slots) {
            if ((slot.sameItem(stack) && slot.getCount() + stack.getCount() <= stack.getMaxStackSize()) || slot.isEmpty()) {
                return slot;
            }
        }
        return null;
    }

    private boolean igniteIfNecessary() {

        boolean justIgnited = false;
        if (!this.isLit() && this.canProcessInput()) {
            ItemStack fuelStack = AbstractConverterTileEntity.getFirstStack(this.FUEL_SLOTS);

            this.litTime = this.getBurnDuration(fuelStack);
            this.litDuration = this.litTime;
            if (this.isLit()) {
                justIgnited = true;
                if (fuelStack.hasContainerItem()) {
                    this.items.set(getFirstEmptyStackIndex(this.FUEL_SLOTS), fuelStack.getContainerItem());
                } else if (!fuelStack.isEmpty()) {
                    fuelStack.shrink(1);
                    if (fuelStack.isEmpty()) {
                        this.items.set(getFirstEmptyStackIndex(this.FUEL_SLOTS), fuelStack.getContainerItem());
                    }
                }
            }
        }
        return justIgnited;
    }

    public void tick() {
        this.updateStacks();
        boolean lit = this.isLit();
        boolean justIgnited = false;

        if (this.isLit()) {
            --this.litTime;
        }
        if (this.level != null && !this.level.isClientSide) {
            this.activeRecipe = this.getActiveRecipe();
            justIgnited = this.igniteIfNecessary();

            if (this.isLit()) {
                if (this.canProcessInput()) {
                    ++this.conversionPregress;

                    if (this.conversionPregress == this.conversionTotalTime) {
                        this.conversionPregress = 0;
                        this.conversionTotalTime = this.getTotalConversionTime();
                        this.finishProcess();
                    }
                } else {
                    this.conversionPregress = 0;
                }
            } else if (this.conversionPregress > 0) {
                this.conversionPregress = MathHelper.clamp(this.conversionPregress - 2, 0, this.conversionTotalTime);
            }

            if (lit != this.isLit()) {
                this.level.setBlock(this.worldPosition, this.level.getBlockState(this.worldPosition).setValue(AbstractFurnaceBlock.LIT, this.isLit()), 3);
            }
        }

        if (justIgnited) {
            this.setChanged();
        }
    }

    protected boolean canProcessInput() {
        if (!getActiveInputStack().isEmpty() && (this.activeRecipe != null || this.convertAnything)) {
            ItemStack craftingOutput = activeRecipe != null ? ((IRecipe<AbstractConverterTileEntity>) activeRecipe).assemble(this) : ItemStack.EMPTY;
            if (this.convertAnything && !getActiveInputStack().isEmpty()) {
                return true;
            } else if (this.activeRecipe == null && !this.convertAnything) {
                return false;
            } else {
                ItemStack outputStack = this.getFirstEmptyStack(this.OUTPUT_SLOTS);
                return this.canFitInSlotArray(this.OUTPUT_SLOTS, craftingOutput);
            }
        } else {
            return false;
        }
    }

    private void finishProcess() {

        ItemStack inputStack = this.getActiveInputStack();
        ItemStack assemblyOutput = activeRecipe != null ? ((IRecipe<ISidedInventory>) activeRecipe).assemble(this) : ItemStack.EMPTY;
        ItemStack outputSlotStack = this.fittingSlot(this.OUTPUT_SLOTS, assemblyOutput);

        if (this.canProcessInput() || (this.convertAnything && !inputStack.isEmpty())) {

            if (outputSlotStack.isEmpty()) {
                this.items.set(this.OUTPUT_SLOTS.indexOf(outputSlotStack)+OUTPUT_SLOT_INDEXES[0], assemblyOutput.copy());
            } else if (outputSlotStack.getItem() == assemblyOutput.getItem()) {
                outputSlotStack.grow(Math.min(outputSlotStack.getMaxStackSize() - outputSlotStack.getCount(), assemblyOutput.getCount()));
            }

            if (inputStack.getItem() == Blocks.WET_SPONGE.asItem() && !this.items.get(1).isEmpty() && this.items.get(1).getItem() == Items.BUCKET) {
                this.items.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            inputStack.shrink(1);
        }
    }

    protected int getBurnDuration(ItemStack stack) {
        //return AbstractConverterTileEntity.getFuel().getOrDefault(stack.getItem(), 0)*stack.getCount();
        if (stack.isEmpty()) {
            return 0;
        } else {
            Item item = stack.getItem();
            return net.minecraftforge.common.ForgeHooks.getBurnTime(stack, RecipeInit.CRUSHING_RECIPE);
        }
    }

    protected int getTotalConversionTime() {
        return (this.activeRecipe instanceof AbstractConversionRecipe) ? ((AbstractConversionRecipe) this.activeRecipe).getConversionTime() : 200;
    }

    public static boolean isFuel(ItemStack stack) {
        return net.minecraftforge.common.ForgeHooks.getBurnTime(stack, null) > 0;
    }

    public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return direction == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    public boolean canPlaceItemThroughFace(int p_180462_1_, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(p_180462_1_, stack);
    }

    public boolean canTakeItemThroughFace(int p_180461_1_, ItemStack p_180461_2_, Direction p_180461_3_) {
        if (p_180461_3_ == Direction.DOWN && p_180461_1_ == 1) {
            Item item = p_180461_2_.getItem();
            return item == Items.WATER_BUCKET || item == Items.BUCKET;
        }

        return true;
    }

    public int getContainerSize() {
        return this.items.size();
    }

    public boolean isEmpty() {
        for (ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public ItemStack getItem(int index) {
        return this.items.get(index);
    }

    public ItemStack removeItem(int p_70298_1_, int p_70298_2_) {
        return ItemStackHelper.removeItem(this.items, p_70298_1_, p_70298_2_);
    }

    public ItemStack removeItemNoUpdate(int p_70304_1_) {
        return ItemStackHelper.takeItem(this.items, p_70304_1_);
    }

    public void setItem(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && stack.sameItem(itemstack) && ItemStack.tagMatches(stack, itemstack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        if (index == 0 && !flag) {
            this.conversionTotalTime = this.getTotalConversionTime();
            this.conversionPregress = 0;
            this.setChanged();
        }

    }

    public boolean stillValid(PlayerEntity player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    public boolean canPlaceItem(int slot, ItemStack stack) {
        for (int i : T.OUTPUT_SLOT_INDEXES) {
            if (slot == i) {
                return false;
            }
        }
        for (int i : T.INPUT_SLOT_INDEXES) {
            if (slot == i) {
                return true;
            }
        }
        return net.minecraftforge.common.ForgeHooks.getBurnTime(stack, this.recipeType) > 0;
    }

    public void clearContent() {
        this.items.clear();
    }

    public void awardUsedRecipes(PlayerEntity player) {
    }

    public void awardUsedRecipesAndPopExperience(PlayerEntity p_235645_1_) {
        List<IRecipe<?>> list = this.getRecipesToAwardAndPopExperience(p_235645_1_.level, p_235645_1_.position());
        p_235645_1_.awardRecipes(list);
        this.recipesUsed.clear();
    }

    public List<IRecipe<?>> getRecipesToAwardAndPopExperience(World p_235640_1_, Vector3d p_235640_2_) {
        List<IRecipe<?>> list = Lists.newArrayList();

        for (Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
            p_235640_1_.getRecipeManager().byKey(entry.getKey()).ifPresent((p_235642_4_) -> {
                list.add(p_235642_4_);
                //createExperience(p_235640_1_, p_235640_2_, entry.getIntValue(), ((AbstractConversionRecipe)p_235642_4_).getExperience());
            });
        }

        return list;
    }

    private static void createExperience(World p_235641_0_, Vector3d p_235641_1_, int p_235641_2_, float p_235641_3_) {
        int i = MathHelper.floor((float) p_235641_2_ * p_235641_3_);
        float f = MathHelper.frac((float) p_235641_2_ * p_235641_3_);
        if (f != 0.0F && Math.random() < (double) f) {
            ++i;
        }

        while (i > 0) {
            int j = ExperienceOrbEntity.getExperienceValue(i);
            i -= j;
            p_235641_0_.addFreshEntity(new ExperienceOrbEntity(p_235641_0_, p_235641_1_.x, p_235641_1_.y, p_235641_1_.z, j));
        }

    }

    public void fillStackedContents(RecipeItemHelper p_194018_1_) {
        for (ItemStack itemstack : this.items) {
            p_194018_1_.accountStack(itemstack);
        }

    }

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == Direction.UP)
                return handlers[0].cast();
            else if (facing == Direction.DOWN)
                return handlers[1].cast();
            else
                return handlers[2].cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        for (int x = 0; x < handlers.length; x++)
            handlers[x].invalidate();
    }

    public void load(BlockState blockState, CompoundNBT data) {
        super.load(blockState, data);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(data, this.items);
        this.litTime = data.getInt("BurnTime");
        this.conversionPregress = data.getInt("CookTime");
        this.conversionTotalTime = data.getInt("CookTimeTotal");
        this.litDuration = this.getBurnDuration(AbstractConverterTileEntity.getFirstStack(this.FUEL_SLOTS));
        CompoundNBT compoundnbt = data.getCompound("RecipesUsed");

        for (String s : compoundnbt.getAllKeys()) {
            this.recipesUsed.put(new ResourceLocation(s), compoundnbt.getInt(s));
        }
    }

    public CompoundNBT save(CompoundNBT data) {
        super.save(data);
        data.putInt("BurnTime", this.litTime);
        data.putInt("CookTime", this.conversionPregress);
        data.putInt("CookTimeTotal", this.conversionTotalTime);
        ItemStackHelper.saveAllItems(data, this.items);
        CompoundNBT compoundnbt = new CompoundNBT();
        this.recipesUsed.forEach((p_235643_1_, p_235643_2_) -> {
            compoundnbt.putInt(p_235643_1_.toString(), p_235643_2_);
        });
        data.put("RecipesUsed", compoundnbt);
        return data;
    }
}
