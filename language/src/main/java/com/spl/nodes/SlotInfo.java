package com.spl.nodes;

import com.spl.common.SplType;

public class SlotInfo {

    private boolean isUpdatable;
    private SplType splType;

    public SlotInfo(boolean isUpdatable, SplType typeOfObject) {
        this.isUpdatable = isUpdatable;
        this.splType = typeOfObject;
    }

    public boolean isUpdatable() {
        return isUpdatable;
    }

    public SplType getType() {
        return splType;
    }
}
