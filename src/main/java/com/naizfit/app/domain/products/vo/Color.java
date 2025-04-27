package com.naizfit.app.domain.products.vo;

import java.util.Objects;

public record Color(String value) {
	
    public Color {
        Objects.requireNonNull(value, "Color must not be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("Color must not be empty");
        }
    }
	
	@Override
	public String toString() {
		return value;
	}
}