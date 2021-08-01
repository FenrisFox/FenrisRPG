package com.FenrisFox86.fenris_workshop.core.init;

import com.FenrisFox86.fenris_workshop.FenrisWorkshop;
import com.FenrisFox86.fenris_workshop.common.tileentities.CrusherTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeInit {

    public static DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(
            ForgeRegistries.TILE_ENTITIES, FenrisWorkshop.MOD_ID
    );

    public static final RegistryObject<TileEntityType<CrusherTileEntity>> CRUSHER_TILE_ENTITY_TYPE = TILE_ENTITY_TYPES
            .register("crusher", () -> TileEntityType.Builder.of(CrusherTileEntity::new, BlockInit.CRUSHER_BLOCK.get()).build(
                    null));

    public static void TileEntityTypeInit() {
        TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
