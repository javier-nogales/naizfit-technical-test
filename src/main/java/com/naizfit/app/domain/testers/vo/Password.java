package com.naizfit.app.domain.testers.vo;

import org.mindrot.jbcrypt.BCrypt;

public record Password(String value) {
	
	public Password {
		if (value == null || !value.matches("^\\$2[aby]\\$.{56}$")) {
            throw new IllegalArgumentException("Password must be a valid BCrypt hash");
        }
	}
	
	/**
	 * Factory from no encrypted value
	 * @param rawPassword
	 * @return
	 */
	public static Password of(String rawPassword) {
		String salt = BCrypt.gensalt(10);
		String hashed = BCrypt.hashpw(rawPassword, salt);
		
		return new Password(hashed);
	}
	
	@Override
	public String toString() {
		return "********";
	}
}
