package com.naizfit.app.domain.testers.vo;

public record PasswordType(String value) {
	
	public PasswordType {
		if (value == null || !value.matches("^\\$2[aby]\\$.{56}$")) {
            throw new IllegalArgumentException("Password must be a valid BCrypt hash");
        }
	}
	
	@Override
	public String toString() {
		return "********";
	}
}
