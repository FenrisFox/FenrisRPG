package com.FenrisFox86.fenris_workshop.common.tileentities;

import com.FenrisFox86.fenris_workshop.common.recipes.AbstractConversionRecipe;
import com.FenrisFox86.fenris_workshop.core.init.RecipeInit;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
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

public abstract class AbstractConverterTileEntity extends LockableTileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity {
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 1};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};
    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private int litTime;
    private int litDuration;
    private int cookingProgress;
    private int cookingTotalTime;
    public IRecipe<?> activeRecipe;
    protected final IIntArray dataAccess = new IIntArray() {
        public int get(int p_221476_1_) {
            switch(p_221476_1_) {
                case 0:
                    return AbstractConverterTileEntity.this.litTime;
                case 1:
                    return AbstractConverterTileEntity.this.litDuration;
                case 2:
                    return AbstractConverterTileEntity.this.cookingProgress;
                case 3:
                    return AbstractConverterTileEntity.this.cookingTotalTime;
                default:
                    return 0;
            }
        }

        public void set(int p_221477_1_, int p_221477_2_) {
            switch(p_221477_1_) {
                case 0:
                    AbstractConverterTileEntity.this.litTime = p_221477_2_;
                    break;
                case 1:
                    AbstractConverterTileEntity.this.litDuration = p_221477_2_;
                    break;
                case 2:
                    AbstractConverterTileEntity.this.cookingProgress = p_221477_2_;
                    break;
                case 3:
                    AbstractConverterTileEntity.this.cookingTotalTime = p_221477_2_;
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
        this.recipeType = recipeType;
    }

    @Deprecated
    public static Map<Item, Integer> getFuel() { return AbstractFurnaceTileEntity.getFuel(); }

    private boolean isLit() { return this.litTime > 0; }

    private IRecipe<?> getActiveRecipe() {
        for (IRecipe<?> recipe : RecipeInit.getRecipes(this.recipeType, this.level.getRecipeManager()).values()) {
            if (recipe.getIngredients().get(0).test(this.items.get(0))) {
                this.activeRecipe = recipe;
                return recipe;
            }
        }
        this.activeRecipe = null;
        return null;
    }

    private boolean getIgnition() {

        boolean justIgnited = false;
        ItemStack inputStack = this.items.get(1);
        if (!this.isLit() && this.canBurn(activeRecipe)) {
            this.litTime = this.getBurnDuration(inputStack);
            this.litDuration = this.litTime;
            if (this.isLit()) {
                justIgnited = true;
                if (inputStack.hasContainerItem()) {
                    this.items.set(1, inputStack.getContainerItem());
                } else if (!inputStack.isEmpty()) {
                    Item item = inputStack.getItem();
                    inputStack.shrink(1);
                    if (inputStack.isEmpty()) {
                        this.items.set(1, inputStack.getContainerItem());
                    }
                }
            }
        }
        return justIgnited;
    }

    public void tick() {
        boolean lit = this.isLit();
        boolean justIgnited = false;

        if (this.isLit()) { --this.litTime; }

        if (this.level != null && !this.level.isClientSide) {

            ItemStack inputStack = this.items.get(1);
            if (this.isLit() || !inputStack.isEmpty() && !this.items.get(0).isEmpty()) {

                IRecipe<?> activeRecipe = this.getActiveRecipe();
                justIgnited = getIgnition();

                if (this.isLit() && this.canBurn(activeRecipe)) {
                    ++this.cookingProgress;
                    if (this.cookingProgress == this.cookingTotalTime) {
                        this.cookingProgress = 0;
                        this.cookingTotalTime = this.getTotalCookTime();
                        this.burn(activeRecipe);
                        justIgnited = true;
                    }
                } else {
                    this.cookingProgress = 0;
                }
            } else if (!this.isLit() && this.cookingProgress > 0) {
                this.cookingProgress = MathHelper.clamp(this.cookingProgress - 2, 0, this.cookingTotalTime);
            }

            if (lit != this.isLit()) {
                justIgnited = true;
                this.level.setBlock(this.worldPosition, this.level.getBlockState(this.worldPosition).setValue(AbstractFurnaceBlock.LIT, Boolean.valueOf(this.isLit())), 3);
            }
        }

        if (justIgnited) {
            this.setChanged();
        }

    }

    protected boolean canBurn(@Nullable IRecipe<?> recipe) {
        if (!this.items.get(0).isEmpty() && recipe != null) {
            ItemStack craftingOutput = ((IRecipe<ISidedInventory>) recipe).assemble(this);
            if (craftingOutput.isEmpty() && this.activeRecipe == null) {
                return false;
            } else {
                ItemStack outputStack = this.items.get(2);
                if (outputStack.isEmpty()) {
                    return true;
                } else if (!outputStack.sameItem(craftingOutput) && !craftingOutput.isEmpty()) {
                    return false;
                } else if (outputStack.getCount() + craftingOutput.getCount() <= this.getMaxStackSize() && outputStack.getCount() + craftingOutput.getCount() <= outputStack.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                    return true;
                } else {
                    return outputStack.getCount() + craftingOutput.getCount() <= craftingOutput.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        } else {
            return false;
        }
    }

    private void burn(@Nullable IRecipe<?> p_214007_1_) {
        if (p_214007_1_ != null && this.canBurn(p_214007_1_)) {
            ItemStack itemstack = this.items.get(0);
            ItemStack itemstack1 = ((IRecipe<ISidedInventory>) p_214007_1_).assemble(this);
            ItemStack itemstack2 = this.items.get(2);
            if (itemstack2.isEmpty()) {
                this.items.set(2, itemstack1.copy());
            } else if (itemstack2.getItem() == itemstack1.getItem()) {
                itemstack2.grow(itemstack1.getCount());
            }

            if (!this.level.isClientSide) {
                this.setRecipeUsed(p_214007_1_);
            }

            if (itemstack.getItem() == Blocks.WET_SPONGE.asItem() && !this.items.get(1).isEmpty() && this.items.get(1).getItem() == Items.BUCKET) {
                this.items.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.shrink(1);
        }
    }

    protected int getBurnDuration(ItemStack stack) {
        //return AbstractConverterTileEntity.getFuel().getOrDefault(stack.getItem(), 0)*stack.getCount();
        if (stack.isEmpty()) {
            return 0;
        } else {
            Item item = stack.getItem();
            return net.minecraftforge.common.ForgeHooks.getBurnTime(stack, IRecipeType.SMELTING);
        }
    }

    protected int getTotalCookTime() {
        return (this.activeRecipe instanceof AbstractConversionRecipe) ? ((AbstractConversionRecipe)this.activeRecipe).getConversionTime() : 200;
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
            if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
                return false;
            }
        }

        return true;
    }

    public int getContainerSize() {
        return this.items.size();
    }

    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public ItemStack getItem(int p_70301_1_) {
        return this.items.get(p_70301_1_);
    }

    public ItemStack removeItem(int p_70298_1_, int p_70298_2_) {
        return ItemStackHelper.removeItem(this.items, p_70298_1_, p_70298_2_);
    }

    public ItemStack removeItemNoUpdate(int p_70304_1_) {
        return ItemStackHelper.takeItem(this.items, p_70304_1_);
    }

    public void setItem(int p_70299_1_, ItemStack stack) {
        ItemStack itemstack = this.items.get(p_70299_1_);
        boolean flag = !stack.isEmpty() && stack.sameItem(itemstack) && ItemStack.tagMatches(stack, itemstack);
        this.items.set(p_70299_1_, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        if (p_70299_1_ == 0 && !flag) {
            this.cookingTotalTime = this.getTotalCookTime();
            this.cookingProgress = 0;
            this.setChanged();
        }

    }

    public boolean stillValid(PlayerEntity player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    public boolean canPlaceItem(int p_94041_1_, ItemStack stack) {
        if (p_94041_1_ == 2) {
            return false;
        } else if (p_94041_1_ != 1) {
            return true;
        } else {
            ItemStack itemstack = this.items.get(1);
            return net.minecraftforge.common.ForgeHooks.getBurnTime(stack, this.recipeType) > 0 || stack.getItem() == Items.BUCKET && itemstack.getItem() != Items.BUCKET;
        }
    }

    public void clearContent() {
        this.items.clear();
    }

    public void setRecipeUsed(@Nullable IRecipe<?> p_193056_1_) {
        if (p_193056_1_ != null) {
            ResourceLocation resourcelocation = p_193056_1_.getId();
            this.recipesUsed.addTo(resourcelocation, 1);
        }

    }

    @Nullable
    public IRecipe<?> getRecipeUsed() {
        return null;
    }

    public void awardUsedRecipes(PlayerEntity player) { }

    public void awardUsedRecipesAndPopExperience(PlayerEntity p_235645_1_) {
        List<IRecipe<?>> list = this.getRecipesToAwardAndPopExperience(p_235645_1_.level, p_235645_1_.position());
        p_235645_1_.awardRecipes(list);
        this.recipesUsed.clear();
    }

    public List<IRecipe<?>> getRecipesToAwardAndPopExperience(World p_235640_1_, Vector3d p_235640_2_) {
        List<IRecipe<?>> list = Lists.newArrayList();

        for(Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
            p_235640_1_.getRecipeManager().byKey(entry.getKey()).ifPresent((p_235642_4_) -> {
                list.add(p_235642_4_);
                createExperience(p_235640_1_, p_235640_2_, entry.getIntValue(), ((AbstractCookingRecipe)p_235642_4_).getExperience());
            });
        }

        return list;
    }

    private static void createExperience(World p_235641_0_, Vector3d p_235641_1_, int p_235641_2_, float p_235641_3_) {
        int i = MathHelper.floor((float)p_235641_2_ * p_235641_3_);
        float f = MathHelper.frac((float)p_235641_2_ * p_235641_3_);
        if (f != 0.0F && Math.random() < (double)f) {
            ++i;
        }

        while(i > 0) {
            int j = ExperienceOrbEntity.getExperienceValue(i);
            i -= j;
            p_235641_0_.addFreshEntity(new ExperienceOrbEntity(p_235641_0_, p_235641_1_.x, p_235641_1_.y, p_235641_1_.z, j));
        }

    }

    public void fillStackedContents(RecipeItemHelper p_194018_1_) {
        for(ItemStack itemstack : this.items) {
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
        this.cookingProgress = data.getInt("CookTime");
        this.cookingTotalTime = data.getInt("CookTimeTotal");
        this.litDuration = this.getBurnDuration(this.items.get(1));
        CompoundNBT compoundnbt = data.getCompound("RecipesUsed");

        for(String s : compoundnbt.getAllKeys()) {
            this.recipesUsed.put(new ResourceLocation(s), compoundnbt.getInt(s));
        }
    }

    public CompoundNBT save(CompoundNBT data) {
        super.save(data);
        data.putInt("BurnTime", this.litTime);
        data.putInt("CookTime", this.cookingProgress);
        data.putInt("CookTimeTotal", this.cookingTotalTime);
        ItemStackHelper.saveAllItems(data, this.items);
        CompoundNBT compoundnbt = new CompoundNBT();
        this.recipesUsed.forEach((p_235643_1_, p_235643_2_) -> {
            compoundnbt.putInt(p_235643_1_.toString(), p_235643_2_);
        });
        data.put("RecipesUsed", compoundnbt);
        return data;
    }
}
