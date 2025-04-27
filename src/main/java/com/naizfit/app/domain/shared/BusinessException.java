package com.naizfit.app.domain.shared;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -6242139586840848374L;

	public BusinessException(final String errorMessage) {
		super(errorMessage);
	}
	
	public BusinessException(final String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
	
}
