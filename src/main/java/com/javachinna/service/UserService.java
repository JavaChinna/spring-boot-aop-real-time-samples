package com.javachinna.service;

import java.util.List;

import com.javachinna.model.UserEntity;

/**
 * @author Chinna
 * @since 26/3/18
 */
public interface UserService {

	public UserEntity save(UserEntity UserRegistrationForm);

	void deleteUsers(List<Long> userIds);
}
