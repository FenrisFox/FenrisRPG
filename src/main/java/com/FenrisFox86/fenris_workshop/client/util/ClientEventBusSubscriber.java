package com.FenrisFox86.fenris_workshop.client.util;

import com.FenrisFox86.fenris_workshop.FenrisWorkshop;
import com.FenrisFox86.fenris_workshop.client.screens.CrusherScreen;
import com.FenrisFox86.fenris_workshop.core.init.ContainerTypeInit;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = FenrisWorkshop.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        ScreenManager.register(ContainerTypeInit.CRUSHER_CONTAINER_TYPE.get(), CrusherScreen::new);
    }
}
