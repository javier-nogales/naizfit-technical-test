package com.naizfit.app.domain.shared.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record Email(String value) {

	@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
	public Email {
		if (value == null || !value.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"))
			throw new IllegalArgumentException("Invalid email address");
	}
	
	@JsonValue
    public String value() {
        return value;
    }
	
	@Override
	public String toString() {
		return value;
	}
}
