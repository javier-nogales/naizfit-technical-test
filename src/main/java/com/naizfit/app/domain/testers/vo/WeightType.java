package com.naizfit.app.domain.testers.vo;

public record WeightType(Double value) {

	public WeightType {
		if (value == null || value <= 0)
			throw new IllegalArgumentException("Weight must be a positive number");
	}
	
	@Override
    public String toString() {
        return value.toString();
    }
}
