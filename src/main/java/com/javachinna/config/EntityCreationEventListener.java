package com.javachinna.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EntityCreationEventListener implements ApplicationListener<EntityCreatedEvent> {
	private final Logger logger = LogManager.getLogger(getClass());

	@Async
	@Override
	public void onApplicationEvent(EntityCreatedEvent event) {
		logger.info("Created instance: " + event.getSource().toString());
		// Logic goes here to notify the interested parties
	}
}