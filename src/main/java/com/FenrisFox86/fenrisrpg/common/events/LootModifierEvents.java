package com.FenrisFox86.fenrisrpg.common.events;

import com.FenrisFox86.fenrisrpg.FenrisRPG;
import com.FenrisFox86.fenrisrpg.core.util.tools.ToolLootModifier;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = FenrisRPG.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LootModifierEvents {

    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        event.getRegistry().register(new ToolLootModifier.Serializer().setRegistryName(
                new ResourceLocation(FenrisRPG.MOD_ID,"tool_modifier")));
    }
}