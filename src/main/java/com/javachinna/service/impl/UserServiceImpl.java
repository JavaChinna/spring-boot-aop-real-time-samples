package com.javachinna.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javachinna.model.UserEntity;
import com.javachinna.repo.UserRepository;
import com.javachinna.service.UserService;

/**
 * @author Chinna
 * @since 26/3/18
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserEntity save(UserEntity user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteUsers(List<Long> userIds) {
		userRepository.deleteUsers(userIds);
	}
}