package com.naizfit.app.domain.products.vo;

import java.util.Objects;
import java.util.UUID;

public record ProductId(UUID value) {
	
    public ProductId {
        Objects.requireNonNull(value, "TesterId must not be null");
    }

    public static ProductId newId() {
        return new ProductId(UUID.randomUUID());
    }
}