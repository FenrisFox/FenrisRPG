package com.FenrisFox86.fenris_rpg.network;

import com.FenrisFox86.fenris_rpg.FenrisRPG;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Objects;
import java.util.function.Supplier;

public class ItemPacketHandler {

    private static final String PROTOCOL_VERSION = "1.0";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(FenrisRPG.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        int ID = 0;
        INSTANCE.registerMessage(++ID, AbilityPacket.class, AbilityPacket::encode,
                AbilityPacket::new, ItemPacketHandler::handle);
    }

    public static void handle(AbilityPacket packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        PlayerEntity entity = Objects.requireNonNull(context.getSender());

        if (context.getDirection().getReceptionSide() == LogicalSide.SERVER) {

            ctx.get().enqueueWork(() -> {
                entity.abilities.setWalkingSpeed(packet.MOVEMENT_SPEED_ability);
                System.out.println(packet.MOVEMENT_SPEED_ability);
            });

            ctx.get().setPacketHandled(true);
        }
    }
}
