package com.naizfit.app.domain.shared.vo;

public record NameType(String value) {
	
	public NameType {
		if (value == null || value.isBlank())
			throw new IllegalArgumentException("Name must not be empty");
	}
	
	@Override
	public String toString() {
		return value;
	}
}
