package com.FenrisFox86.fenris_rpg.common.items.core;

import com.FenrisFox86.fenris_rpg.common.items.Tooltips;
import com.FenrisFox86.fenris_rpg.core.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public abstract class AbstractCore extends Item{

    public IItemTier itemTier;
    public IArmorMaterial armorMaterial;
    public String name;

    public AbstractCore(String name, Properties properties) {
        super(properties);
        this.name = name;
    }

    public static boolean isCoreItem(ItemStack stack, AbstractCore core) {
        return stack.getItem() instanceof ICoreItem && ((ICoreItem) stack.getItem()).getCore().equals(core);
    }

    @Override
    public boolean isFoil(ItemStack stack) { return true; }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return new ItemStack(ItemInit.CORE_VESSEL.get(), 1);
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public static void appendHoverText(List<ITextComponent> tooltip, String toolName, String coreName) {
        if(InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
            tooltip.add(new TranslationTextComponent("tooltip.fenris_rpg."+toolName+".lore"));
            tooltip.add(new TranslationTextComponent("tooltip.fenris_rpg."+coreName+"_tool.desc"));
        } else {
            tooltip.add(Tooltips.HOLD_SHIFT);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void appendHoverText(List<ITextComponent> tooltip, String itemName) {
        if(InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
            tooltip.add(new TranslationTextComponent("tooltip.fenris_rpg."+itemName+".lore"));
            tooltip.add(new TranslationTextComponent("tooltip.fenris_rpg."+itemName+".desc"));
        } else {
            tooltip.add(Tooltips.HOLD_SHIFT);
        }
    }

    @Override
    public abstract ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand);

    public abstract ActionResult<ItemStack> useCoreItem(World world, PlayerEntity player, Hand hand, Item item);

}
