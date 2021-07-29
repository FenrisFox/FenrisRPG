package com.FenrisFox86.fenrisrpg.common.capabilities;

import net.minecraft.entity.player.PlayerEntity;

public class CapabilityReader {

    public static Float getFenrisState(PlayerEntity player, String key) {
        Float stateIn = player.getCapability(FenrisStateProvider.CAPABILITY).orElseThrow(() ->
                new IllegalArgumentException("LazyOptional cannot be empty! (reading LastHandUsed)"))
                .getFenrisState(key);
        return stateIn;
    }

    public static void setFenrisState(PlayerEntity player, String key, Float stateIn) {
        player.getCapability(FenrisStateProvider.CAPABILITY).orElseThrow(() ->
                new IllegalArgumentException("LazyOptional cannot be empty! (writing LastHandUsed)"))
                .setFenrisState(key, stateIn);
    }

    public static void addFenrisState(PlayerEntity player, String key, Float add) {
        setFenrisState(player, key, getFenrisState(player, key) + add);
    }
}
