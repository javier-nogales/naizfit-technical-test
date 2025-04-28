package com.naizfit.app.domain.shared.exception;

public class DomainException extends RuntimeException{

	private static final long serialVersionUID = -3032966679355709601L;

	public DomainException(final String errorMessage) {
		super(errorMessage);
	}
	
	public DomainException(final String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
}
