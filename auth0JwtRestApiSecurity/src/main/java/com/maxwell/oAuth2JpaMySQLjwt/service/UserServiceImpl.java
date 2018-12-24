package com.maxwell.oAuth2JpaMySQLjwt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxwell.oAuth2JpaMySQLjwt.domain.AppUser;
import com.maxwell.oAuth2JpaMySQLjwt.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public AppUser getUser(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<AppUser> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public void addNewUser(AppUser newUser) {
		userRepository.save(newUser);
		
	}

	@Override
	public AppUser findById(int userId) {
		return userRepository.findById(userId).get();
	}

	@Override
	public void saveEdits(AppUser existingUser) {
		userRepository.save(existingUser);
		
	}

	@Override
	public AppUser getUser(int userId) {
		return userRepository.findById(userId).get();
	}

	@Override
	public void deleteUser(AppUser userToDel) {
		userRepository.delete(userToDel);
		
	}

}
