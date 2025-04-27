package com.naizfit.app.domain.products.vo;

import java.util.Objects;

public record Sku(String value) {
    
	public Sku {
        Objects.requireNonNull(value, "SKU nust not be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("SKU must not be empty");
        }
    }

    @Override
	public String toString() {
		return value;
	}
}
