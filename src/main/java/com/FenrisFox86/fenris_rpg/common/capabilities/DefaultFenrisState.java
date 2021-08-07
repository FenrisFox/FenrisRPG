package com.FenrisFox86.fenris_rpg.common.capabilities;

import java.util.HashMap;
import java.util.Map;

public class DefaultFenrisState implements IFenrisState {
    private Map<String, Float> fenrisState = new HashMap<String, Float> () {};

    @Override
    public Float getFenrisRpgFloat(String key) {
        if (!this.fenrisState.containsKey(key)) {
            this.fenrisState.put(key, 0f);
        }
        return this.fenrisState.get(key);
    }

    @Override
    public void setFenrisRpgFloat(String key, Float stateIn) {
        if (!this.fenrisState.containsKey(key)) {
            this.fenrisState.put(key, 0f);
        }
        this.fenrisState.replace(key, stateIn);
    }
}
