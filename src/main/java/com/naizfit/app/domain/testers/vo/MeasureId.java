package com.naizfit.app.domain.testers.vo;

import java.util.Objects;
import java.util.UUID;

public record MeasureId(UUID value) {
	
    public MeasureId {
        Objects.requireNonNull(value, "TesterId must not be null");
    }

    public static MeasureId newId() {
        return new MeasureId(UUID.randomUUID());
    }
}
