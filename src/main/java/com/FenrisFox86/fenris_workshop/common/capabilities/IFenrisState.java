package com.FenrisFox86.fenris_workshop.common.capabilities;

import net.minecraft.util.Hand;

public interface IFenrisState {
    public Float getFenrisState(String key);
    public void setFenrisState(String key, Float stateIn);
}
