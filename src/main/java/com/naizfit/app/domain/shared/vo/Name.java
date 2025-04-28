package com.naizfit.app.domain.shared.vo;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;

public record Name(String value) {
	
	@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
	public Name {
        Objects.requireNonNull(value, "Name must not be null");
        if (value.isBlank()) throw new IllegalArgumentException("Name must not be empty");
    }


    public String value() {
        return value;
    }
	
	@Override
	public String toString() {
		return value;
	}
}
