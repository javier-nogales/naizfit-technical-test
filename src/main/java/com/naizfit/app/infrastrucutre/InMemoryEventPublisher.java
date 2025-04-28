package com.naizfit.app.infrastrucutre;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.inject.Singleton;
import com.naizfit.app.domain.DomainEventPublisher;

@Singleton
public class InMemoryEventPublisher 
  implements DomainEventPublisher {
	
	// BEWARE! concurrency danger -> use CopyOnWriteArrayList -> for consistent writes and reads
	// https://docs.oracle.com/javase/8/docs/api////index.html?java/util/concurrent/CopyOnWriteArrayList.html
	private final List<Object> events = new CopyOnWriteArrayList<>();

	@Override
	public <E> void publish(E event) {
		events.add(event);
		
		// invoque listeners?
	}
	
	public List<Object> getPublishedEvents() {
		return List.copyOf(events);
	}
	
	public void clear() {
		events.clear();
	}

}
