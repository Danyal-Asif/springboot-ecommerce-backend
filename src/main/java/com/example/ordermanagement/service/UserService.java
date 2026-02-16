package com.example.ordermanagement.service;

import java.util.List;

import com.example.ordermanagement.dto.UserDto;
import com.example.ordermanagement.entity.User;

public interface UserService {
	void saveUser(UserDto userDto);
	
	User findUserByEmail(String email);

	User findUserById(Long id);
	
	List<UserDto> findAllUsers();
	
	void deleteUser(User user);
	
	void updateUser(String firstName,String lastName,String email,String password);

	

	
}
