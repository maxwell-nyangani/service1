package com.maxwell.oAuth2JpaMySQLjwt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.maxwell.oAuth2JpaMySQLjwt.domain.AppUser;

public interface UserService {
	AppUser getUser(String username);
	//List<User> getUsers(int pageNumner, int itemsPerPage);
	List<AppUser> getUsers();
	void addNewUser(AppUser newUser);
	AppUser findById(int userId);
	void saveEdits(AppUser existingUser);
	AppUser getUser(int userId);
	void deleteUser(AppUser userToDel);
}
