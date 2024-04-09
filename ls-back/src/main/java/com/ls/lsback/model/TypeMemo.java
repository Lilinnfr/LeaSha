package com.ls.lsback.model;

public enum TypeMemo {
    CARTE("CARTE"),
    LISTE("LISTE");

    private String value;

    TypeMemo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
