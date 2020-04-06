package com.javachinna.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

import com.javachinna.model.AbstractEntity;
import com.javachinna.model.LocalUser;
import com.javachinna.model.Role;
import com.javachinna.model.UserEntity;

@Aspect
@Configuration
public class UserAccessAspect {

	private final Logger logger = LogManager.getLogger(getClass());

	@Pointcut("execution(public * org.springframework.data.repository.Repository+.save(..))")
	public void saveMethods() {
	}

	@Pointcut("execution(public * org.springframework.data.repository.Repository+.delete(..))")
	public void deleteMethods() {
	}

	// What kind of method calls I would intercept
	// execution(* PACKAGE.*.*(..))
	// Weaving & Weaver
	@Before("saveMethods() || deleteMethods()")
	public void before(JoinPoint joinPoint) {
		// Advice
		logger.info("Check for user access");
		Object object = joinPoint.getArgs()[0];
		if (object instanceof AbstractEntity) {
			// Get the currently logged in user from the Spring Security Context
			LocalUser user = (LocalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (isAdmin(user.getUser())) {
				return; // User has admin rights
			}
			// AbstractEntity is a mapped super class with common properties like createdBy,
			// modifiedBy etc which will be extended by multiple entities
			if (object instanceof AbstractEntity) {
				AbstractEntity entity = (AbstractEntity) object;
				// Check if the currently logged in user is the owner of this entity
				if (entity.getId() != null && !isSameUser(user.getUser(), entity.getCreatedBy())) {
					throw new RuntimeException("User does not have access to modify this entity id: " + entity.getId());
				}
			}
		}
	}

	private static boolean isAdmin(UserEntity user) {
		if (user != null) {
			for (Role role : user.getRoles()) {
				return role.getName().equals(Role.ROLE_ADMIN);
			}
		}
		return false;
	}

	private static boolean isSameUser(UserEntity currentUser, UserEntity entityOwner) {
		if (currentUser != null && entityOwner != null) {
			return currentUser.getId().equals(entityOwner.getId());
		}
		return false;
	}

}