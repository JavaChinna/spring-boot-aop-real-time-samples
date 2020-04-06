package com.javachinna.config;

import org.springframework.context.ApplicationEvent;

public class EntityCreatedEvent extends ApplicationEvent {

	public EntityCreatedEvent(Object source) {
		super(source);
	}
}
