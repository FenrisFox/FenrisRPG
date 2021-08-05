package com.FenrisFox86.fenris_rpg;

import com.FenrisFox86.fenris_rpg.common.capabilities.FenrisStateStorage;
import com.FenrisFox86.fenris_rpg.common.capabilities.DefaultFenrisState;
import com.FenrisFox86.fenris_rpg.common.capabilities.IFenrisState;
import com.FenrisFox86.fenris_rpg.core.init.*;
import com.FenrisFox86.fenris_rpg.network.AbilityPacketHandler;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("fenris_rpg")
public class FenrisRPG {
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "fenris_rpg";

    public FenrisRPG() {
        // Register the setup and doClientStuff methods for modloading
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::setup);
        bus.addListener(this::doClientStuff);
        bus.addGenericListener(IRecipeSerializer.class, RecipeInit::registerRecipes);

        ItemInit.ItemInit();
        BlockInit.BlockInit();
        EnchantmentInit.EnchantmentInit();
        TileEntityTypeInit.TileEntityTypeInit();
        ContainerTypeInit.ContainerTypeInit();
        SoundEventInit.SoundEventInit();

        MinecraftForge.EVENT_BUS.register(this);
        AbilityPacketHandler.register();
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        CapabilityManager.INSTANCE.register(IFenrisState.class, new FenrisStateStorage(), DefaultFenrisState::new);
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
        RenderTypeLookup.setRenderLayer(BlockInit.ESSENCE_BLOCK.get(), RenderType.translucent());
    }

    public static final ItemGroup MOD_TAB = new ItemGroup("basic_material_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemInit.RUBY.get());
        }
    };

}
