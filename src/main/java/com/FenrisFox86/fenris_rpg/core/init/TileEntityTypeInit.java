package com.FenrisFox86.fenris_rpg.core.init;

import com.FenrisFox86.fenris_rpg.FenrisRPG;
import com.FenrisFox86.fenris_rpg.common.tileentities.CrusherTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeInit {

    public static DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(
            ForgeRegistries.TILE_ENTITIES, FenrisRPG.MOD_ID
    );

    public static final RegistryObject<TileEntityType<CrusherTileEntity>> CRUSHER_TILE_ENTITY_TYPE = TILE_ENTITY_TYPES
            .register("crusher", () -> TileEntityType.Builder.of(CrusherTileEntity::new, BlockInit.CRUSHER_BLOCK.get()).build(
                    null));

    public static void TileEntityTypeInit() {
        TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
