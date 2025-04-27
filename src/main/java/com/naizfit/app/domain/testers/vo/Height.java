package com.naizfit.app.domain.testers.vo;

public record Height(Double value) {

	public Height {
		if (value == null || value <= 0)
			throw new IllegalArgumentException("Height must be a positive number");
	}
	
	@Override
    public String toString() {
        return value.toString();
    }
}
