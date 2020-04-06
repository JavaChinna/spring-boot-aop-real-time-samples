package com.javachinna.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class EventPublishingAspect {
	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@AfterReturning(value = "execution(public * org.springframework.data.repository.Repository+.save*(..))", returning = "entity")
	public void publishEntityCreatedEvent(JoinPoint jp, Object entity) throws Throwable {
		String entityName = entity.getClass().getSimpleName();
		if (!entityName.endsWith("EntityNamesToBeExcluded")) {
			eventPublisher.publishEvent(new EntityCreatedEvent(entity));
		}
	}
}