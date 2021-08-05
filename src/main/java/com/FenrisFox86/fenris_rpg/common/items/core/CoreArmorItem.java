package com.FenrisFox86.fenris_rpg.common.items.core;

import com.FenrisFox86.fenris_rpg.FenrisRPG;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
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
    public final AbstractCore core;
    public final EquipmentSlotType equipmentSlotType;

    public CoreArmorItem(AbstractCore core, EquipmentSlotType type) {
        super(core.armorMaterial, type, new Properties().tab(FenrisRPG.MOD_TAB));
        String pieceName = type.equals(EquipmentSlotType.HEAD) ? "_helmet": type.equals(EquipmentSlotType.CHEST) ? "_chestplate":
                type.equals(EquipmentSlotType.LEGS) ? "_leggings": "_boots";
        this.name = core.name + pieceName;
        this.core = core;
        this.equipmentSlotType = type;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        AbstractCore.appendHoverText(tooltip, this.name);
    }

    @Override
    public AbstractCore getCore() {
        return this.core;
    }
}
