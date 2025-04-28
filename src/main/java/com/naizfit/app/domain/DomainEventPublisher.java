package com.naizfit.app.domain;

public interface DomainEventPublisher {
	
	<E> void publish(E event);
}
