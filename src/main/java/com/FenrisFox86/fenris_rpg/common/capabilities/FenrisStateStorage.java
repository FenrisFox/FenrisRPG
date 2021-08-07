package com.FenrisFox86.fenris_rpg.common.capabilities;

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
        data.putFloat("offhand_used", instance.getFenrisRpgFloat("offhand_used"));
        data.putFloat("dashing", instance.getFenrisRpgFloat("dashing"));
        data.putFloat("numberLeaps", instance.getFenrisRpgFloat("numberLeaps"));
        return data;
    }

    @Override
    public void readNBT(Capability capability, IFenrisState instance, Direction side, INBT nbt) {
        CompoundNBT nbt_data = (CompoundNBT) nbt;

        if (nbt_data.contains("offhand_used")) {
            instance.setFenrisRpgFloat("offhand_used", nbt_data.getFloat("offhand_used"));
        } else { instance.setFenrisRpgFloat("offhand_used", 0f); }

        if (nbt_data.contains("dashing")) {
            instance.setFenrisRpgFloat("dashing", nbt_data.getFloat("dashing"));
        } else { instance.setFenrisRpgFloat("dashing", 0f); }

        if (nbt_data.contains("numberLeaps")) {
            instance.setFenrisRpgFloat("numberLeaps", nbt_data.getFloat("numberLeaps"));
        } else { instance.setFenrisRpgFloat("numberLeaps", 0f); }
    }
}
