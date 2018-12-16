package com.spl.common;

import com.spl.exceptions.SplException;

import java.util.Arrays;

public enum SplType {

    OBJECT("obj", true),
    INT("int", true),
    CONST("const", false),
    BOOL("bool", true),
    STRING("str", true);

    private String humaneType;
    private boolean isUpdatable;

    SplType(String humaneType, boolean isUpdatable) {
        this.humaneType = humaneType;
        this.isUpdatable = isUpdatable;
    }

    public static SplType getByHumaneType(String humaneType) {
        return Arrays.stream(SplType.values())
                .filter(splType -> splType.humaneType.equals(humaneType))
                .findAny()
                .orElseThrow(() -> new SplException("Type \"" + humaneType + " \" is not found"));
    }

    public String getHumaneType() {
        return humaneType;
    }

    public boolean isUpdatable() {
        return isUpdatable;
    }

    public boolean isInt() {
        return INT.equals(this);
    }
}
