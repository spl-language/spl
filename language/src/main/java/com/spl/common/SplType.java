package com.spl.common;

public enum SplType {

    OBJECT("obj"),
    INT("int"),
    BOOL("bool"),
    STRING("str");

    private String humaneType;

    SplType(String humaneType) {
        this.humaneType = humaneType;
    }
}
