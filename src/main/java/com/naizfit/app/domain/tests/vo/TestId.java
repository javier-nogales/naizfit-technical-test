package com.naizfit.app.domain.tests.vo;

import java.util.Objects;
import java.util.UUID;

public record TestId(UUID value) {
	
    public TestId {
        Objects.requireNonNull(value, "TesterId must not be null");
    }

    public static TestId newId() {
        return new TestId(UUID.randomUUID());
    }
}