package com.naizfit.app.domain.shared.vo;

import java.time.LocalDate;

public record CreationDate(LocalDate value) {

	public CreationDate {
		if (value == null)
			throw new IllegalArgumentException("Creation date cannot be null");
	}
	
	@Override
	public String toString() {
		return value.toString(); // ISO 8601 format: uuu-MM-dd
	}
}
