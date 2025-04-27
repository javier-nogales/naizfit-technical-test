package com.naizfit.app.domain.products.vo;

import java.util.Objects;
import java.util.UUID;

public record BrandId(UUID value) {
	
    public BrandId {
        Objects.requireNonNull(value, "TesterId must not be null");
    }

    public static BrandId newId() {
        return new BrandId(UUID.randomUUID());
    }
}