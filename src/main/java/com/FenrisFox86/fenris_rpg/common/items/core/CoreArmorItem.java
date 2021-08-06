package com.FenrisFox86.fenris_rpg.common.items.core;

import com.FenrisFox86.fenris_rpg.FenrisRPG;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import java.util.List;

@Mod.EventBusSubscriber
public class CoreArmorItem extends ArmorItem implements ICoreItem {

    public final String name;
    public final AbstractCoreItem core;
    public final EquipmentSlotType equipmentSlotType;

    public CoreArmorItem(AbstractCoreItem core, EquipmentSlotType type) {
        super(core.armorMaterial, type, new Properties().tab(FenrisRPG.MOD_TAB));
        String pieceName = type.equals(EquipmentSlotType.HEAD) ? "_helmet": type.equals(EquipmentSlotType.CHEST) ? "_chestplate":
                type.equals(EquipmentSlotType.LEGS) ? "_leggings": "_boots";
        this.name = core.name + pieceName;
        this.core = core;
        this.equipmentSlotType = type;
    }

    @Override
    public boolean isFireResistant() {
        return core.isFireResistant();
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        AbstractCoreItem.appendArmorHoverText(tooltip, core.name, this.name);
    }

    @Override
    public AbstractCoreItem getCore() {
        return this.core;
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull Entity entityIn, int itemSlot, boolean isSelected) {
        this.core.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }
}
