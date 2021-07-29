package com.FenrisFox86.fenris_workshop.common.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class FenrisStateStorage implements Capability.IStorage<IFenrisState> {

    @Nullable
    @Override
    public INBT writeNBT(Capability capability, IFenrisState instance, Direction side) {
        CompoundNBT data = new CompoundNBT();
        data.putFloat("offhand_used", instance.getFenrisState("offhand_used"));
        data.putFloat("dashing", instance.getFenrisState("dashing"));
        data.putFloat("MOVEMENT_SPEED_add", instance.getFenrisState("MOVEMENT_SPEED_add"));
        return data;
    }

    @Override
    public void readNBT(Capability capability, IFenrisState instance, Direction side, INBT nbt) {
        CompoundNBT nbt_data = (CompoundNBT) nbt;

        if (nbt_data.contains("offhand_used")) {
            instance.setFenrisState("offhand_used", nbt_data.getFloat("offhand_used"));
        } else { instance.setFenrisState("offhand_used", 0f); }

        if (nbt_data.contains("dashing")) {
            instance.setFenrisState("dashing", nbt_data.getFloat("dashing"));
        } else { instance.setFenrisState("dashing", 0f); }

        if (nbt_data.contains("MOVEMENT_SPEED_add")) {
            instance.setFenrisState("MOVEMENT_SPEED_add", nbt_data.getFloat("MOVEMENT_SPEED_add"));
        } else { instance.setFenrisState("MOVEMENT_SPEED_add", 0f); }
    }
}
