package com.naizfit.app.domain.testers.vo;

public record SexType(Sex value) {

    public SexType {
        if (value == null) {
            throw new IllegalArgumentException("Sex cannot be null");
        }
    }

    @Override
    public String toString() {
        return value.name().toLowerCase();
    }
}
