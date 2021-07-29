package com.FenrisFox86.fenrisrpg.core.init;

import com.FenrisFox86.fenrisrpg.FenrisRPG;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeInit {

    public static DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(
            ForgeRegistries.TILE_ENTITIES, FenrisRPG.MOD_ID
    );

    public static void TileEntityTypeInit() {
        TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
