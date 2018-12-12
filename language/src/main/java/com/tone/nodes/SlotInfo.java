package com.tone.nodes;

public class SlotInfo {

    private boolean isUpdatable;
    private Class<?> typeOfObject;

    public SlotInfo(boolean isUpdatable, Class<?> typeOfObject) {
        this.isUpdatable = isUpdatable;
        this.typeOfObject = typeOfObject;
    }

    public boolean isUpdatable() {
        return isUpdatable;
    }

    public Class<?> getTypeOfObject() {
        return typeOfObject;
    }
}
