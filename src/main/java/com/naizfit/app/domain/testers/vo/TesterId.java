package com.naizfit.app.domain.testers.vo;

import java.util.Objects;
import java.util.UUID;

public record TesterId(UUID value) {
	
    public TesterId {
        Objects.requireNonNull(value, "TesterId must not be null");
    }

    public static TesterId newId() {
        return new TesterId(UUID.randomUUID());
    }
}