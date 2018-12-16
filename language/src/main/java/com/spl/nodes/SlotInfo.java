package com.spl.nodes;

import com.spl.common.SplType;

public class SlotInfo {

    private SplType splType;

    public SlotInfo(SplType typeOfObject) {
        this.splType = typeOfObject;
    }

    public SplType getType() {
        return splType;
    }
}
