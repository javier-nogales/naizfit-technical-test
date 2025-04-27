package com.naizfit.app.domain.testers.vo;

public record TestsDoneType(int value) {
	
	public TestsDoneType {
		if (value < 0) 
			throw new IllegalArgumentException("Tests done must be zero or positive");
	}
	
	@Override
    public String toString() {
        return Integer.toString(value);
    }

	public static TestsDoneType zero() {
		return new TestsDoneType(0);
	}
}
