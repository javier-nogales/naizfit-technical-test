package com.naizfit.app.domain.shared.exception;

public class NotFoundException 
  	 extends DomainException {

	private static final long serialVersionUID = -2819697599967951160L;

	public NotFoundException(final String entity, final Object key) {
		super(entity + " id=" + key + " not found");
	}

}
