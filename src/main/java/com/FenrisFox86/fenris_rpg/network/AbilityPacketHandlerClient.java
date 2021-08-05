package com.FenrisFox86.fenris_rpg.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class AbilityPacketHandlerClient {

    public static void handle(AbilityPacket packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        PlayerEntity entity = Minecraft.getInstance().player;

        context.enqueueWork(() -> {
            entity.abilities.setWalkingSpeed(packet.MOVEMENT_SPEED_ability);
            System.out.println(packet.MOVEMENT_SPEED_ability);
        });

        context.setPacketHandled(true);
    }
}
