package com.naizfit.app.domain.testers.vo;

public record Weight(Double value) {

	public Weight {
		if (value == null || value <= 0)
			throw new IllegalArgumentException("Weight must be a positive number");
	}
	
	@Override
    public String toString() {
        return value.toString();
    }
}
