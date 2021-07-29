package com.FenrisFox86.fenris_workshop.network;

import com.FenrisFox86.fenris_workshop.FenrisWorkshop;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class AbilityPacketHandler {

    private static final String PROTOCOL_VERSION = "1.0";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(FenrisWorkshop.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        int ID = 0;
        INSTANCE.registerMessage(++ID, AbilityPacket.class, AbilityPacket::encode,
                AbilityPacket::new, AbilityPacketHandler::handle);
    }

    public static void handle(AbilityPacket packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();

        if (context.getDirection().getReceptionSide() != LogicalSide.SERVER) {
            ctx.get().enqueueWork(() ->
                    // Make sure it's only executed on the physical client
                    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> AbilityPacketHandlerClient
                            .handle(packet, ctx))
            );
        }
        //context.setPacketHandled(true);
    }
}
