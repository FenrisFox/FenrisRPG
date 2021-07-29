package com.FenrisFox86.fenrisrpg.core.init;

import com.FenrisFox86.fenrisrpg.FenrisRPG;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerTypeInit {

    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(
            ForgeRegistries.CONTAINERS, FenrisRPG.MOD_ID
    );

    public static void ContainerTypeInit() {
        CONTAINER_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
