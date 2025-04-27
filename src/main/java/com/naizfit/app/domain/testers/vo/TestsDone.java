package com.naizfit.app.domain.testers.vo;

public record TestsDone(int value) {
	
	public TestsDone {
		if (value < 0) 
			throw new IllegalArgumentException("Tests done must be zero or positive");
	}
	
	@Override
    public String toString() {
        return Integer.toString(value);
    }

	public static TestsDone zero() {
		return new TestsDone(0);
	}
}
