package com.FenrisFox86.fenris_rpg.common.items.core;

import com.FenrisFox86.fenris_rpg.FenrisRPG;
import com.FenrisFox86.fenris_rpg.common.items.BroadswordItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.List;

public class CoreBroadsword extends BroadswordItem implements ICoreItem {

    public final String name;
    public final AbstractCoreItem core;

    public CoreBroadsword(AbstractCoreItem core, int attackDamageIn, float attackSpeedIn) {
        super(core.itemTier, attackDamageIn, attackSpeedIn, new Properties().tab(FenrisRPG.MOD_TAB));
        this.name = core.name + "_broadsword";
        this.core = core;
    }

    @Override
    public boolean isFoil(@Nonnull ItemStack stack) { return core.isFoil(stack); }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        AbstractCoreItem.appendPassiveHoverText(tooltip, core.name);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        return this.core.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull Entity entityIn, int itemSlot, boolean isSelected) {
        this.core.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public boolean isFireResistant() {
        return core.isFireResistant();
    }

    @Override
    public AbstractCoreItem getCore() {
        return core;
    }
}
