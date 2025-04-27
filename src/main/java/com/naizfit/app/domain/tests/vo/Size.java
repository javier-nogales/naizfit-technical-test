package com.naizfit.app.domain.tests.vo;

import java.util.Objects;

public record Size(String value) {
	
    public Size {
        Objects.requireNonNull(value, "Size must not be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("Size must not be empty");
        }
    }
    
    @Override
	public String toString() {
		return value;
	}
}