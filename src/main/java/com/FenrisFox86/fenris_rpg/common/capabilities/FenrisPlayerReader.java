package com.FenrisFox86.fenris_rpg.common.capabilities;

import net.minecraft.entity.player.PlayerEntity;

public class FenrisPlayerReader {

    public static Float getFenrisRpgFloat(PlayerEntity player, String key) {
        Float stateIn = player.getCapability(FenrisStateProvider.CAPABILITY).orElse(new DefaultFenrisState())
                .getFenrisRpgFloat(key);
        return stateIn;
    }

    public static void setFenrisRpgFloat(PlayerEntity player, String key, Float stateIn) {
        player.getCapability(FenrisStateProvider.CAPABILITY).orElse(new DefaultFenrisState())
                .setFenrisRpgFloat(key, stateIn);
    }

    public static void addFenrisState(PlayerEntity player, String key, Float add) {
        setFenrisRpgFloat(player, key, getFenrisRpgFloat(player, key) + add);
    }
}
