package com.naizfit.app.domain.testers.vo;

import java.time.LocalDate;

public record Birthdate(LocalDate value) {

    public Birthdate {
        if (value == null || value.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birthdate must not be greater than today");
        }
    }

    @Override
    public String toString() {
        return value.toString(); // ISO 8601 format: uuuu-MM-dd
    }
}
