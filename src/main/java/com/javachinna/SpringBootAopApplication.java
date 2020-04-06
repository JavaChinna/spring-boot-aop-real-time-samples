package com.javachinna;

import java.util.HashSet;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.javachinna.model.ExampleEntity;
import com.javachinna.model.LocalUser;
import com.javachinna.model.Role;
import com.javachinna.model.UserEntity;
import com.javachinna.repo.ExampleRepository;
import com.javachinna.repo.RoleRepository;
import com.javachinna.service.UserService;

@SpringBootApplication
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class SpringBootAopApplication implements CommandLineRunner {

	private final Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ExampleRepository exampleRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAopApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		// Create new user role and persist it
		Role role = new Role();
		role.setRoleId(1);
		role.setName(Role.ROLE_USER);
		var userRole = roleRepository.save(role);

		final HashSet<Role> roles = new HashSet<Role>();
		roles.add(userRole);

		// Publishing events + logging + Performance Monitoring example

		// Create and persist user1
		UserEntity user1 = new UserEntity("JavaChinna", "secret");
		user1.setRoles(roles);
		// Time taken for executing by the service method will be logged
		// Repository method entry and exit will be logged
		// Entity created event will be triggered
		userService.save(user1);

		// Set user1 as the logged in user in the Spring's Security Context
		authenticateUser(LocalUser.create(user1));

		// Create and persist user2
		UserEntity user2 = new UserEntity("Chinna", "secret");
		user2.setRoles(roles);
		userService.save(user2);

		// User access restriction example
		ExampleEntity example = new ExampleEntity();
		example.setText("some text");
		// Set user2 as the owner of this entity. So that currently logged in user
		// (user1) cannot modify this entity
		example.setCreatedBy(user2);
		exampleRepository.save(example);

		// Try to modify this entity
		example.setText("some other text");

		try {
			exampleRepository.save(example); // Throws access denied exception
		} catch (Exception e) {
		}

		// Transaction Management
		userService.deleteUsers(List.of(1L));
	}

	private void authenticateUser(UserDetails userDetails) {
		logger.debug("Logging in principal: {}", userDetails);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		logger.info("User: {} has been logged in.", userDetails);
	}

}
