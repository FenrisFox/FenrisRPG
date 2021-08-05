package com.FenrisFox86.fenris_rpg.core.init;

import com.FenrisFox86.fenris_rpg.FenrisRPG;
import com.FenrisFox86.fenris_rpg.common.containers.CrusherContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerTypeInit {

    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(
            ForgeRegistries.CONTAINERS, FenrisRPG.MOD_ID
    );

    public static final RegistryObject<ContainerType<CrusherContainer>> CRUSHER_CONTAINER_TYPE = CONTAINER_TYPES
            .register("crusher", () -> IForgeContainerType.create(CrusherContainer::new));

    public static void ContainerTypeInit() {
        CONTAINER_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
