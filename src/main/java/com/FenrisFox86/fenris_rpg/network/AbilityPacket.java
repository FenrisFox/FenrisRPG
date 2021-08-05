package com.FenrisFox86.fenris_rpg.network;

import net.minecraft.network.PacketBuffer;

public class AbilityPacket {
    public float MOVEMENT_SPEED_ability;

    public AbilityPacket(final PacketBuffer packetBuffer) {
        this.MOVEMENT_SPEED_ability = packetBuffer.readFloat();
    }

    public AbilityPacket(float MOVEMENT_SPEED_ability) {
        this.MOVEMENT_SPEED_ability = MOVEMENT_SPEED_ability;
    }

    void encode(final PacketBuffer packetBuffer) {
        packetBuffer.writeFloat(this.MOVEMENT_SPEED_ability);
    }
}
