package com.FenrisFox86.fenrisrpg.client.util;

import com.FenrisFox86.fenrisrpg.FenrisRPG;
import com.FenrisFox86.fenrisrpg.core.init.ContainerTypeInit;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = FenrisRPG.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBus {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
    }
}
