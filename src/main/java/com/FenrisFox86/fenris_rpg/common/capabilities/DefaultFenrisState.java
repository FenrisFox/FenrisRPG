package com.FenrisFox86.fenris_rpg.common.capabilities;

import java.util.HashMap;
import java.util.Map;

public class DefaultFenrisState implements IFenrisState {
    private Map<String, Float> combatState = new HashMap<String, Float> () {};

    @Override
    public Float getFenrisState(String key) {
        if (!this.combatState.containsKey(key)) {
            this.combatState.put(key, 0f);
        }
        return this.combatState.get(key);
    }

    @Override
    public void setFenrisState(String key, Float stateIn) {
        if (!this.combatState.containsKey(key)) {
            this.combatState.put(key, 0f);
        }
        this.combatState.replace(key, stateIn);
    }
}
