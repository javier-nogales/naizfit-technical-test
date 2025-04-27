package com.naizfit.app.domain.shared.vo;

public record Email(String value) {

	public Email {
		if (value == null || !value.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"))
			throw new IllegalArgumentException("Invalid email address");
	}
	
	@Override
	public String toString() {
		return value;
	}
}
