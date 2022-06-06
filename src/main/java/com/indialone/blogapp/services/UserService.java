package com.indialone.blogapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indialone.blogapp.models.User;
import com.indialone.blogapp.payloads.UserDTO;

public interface UserService {
	
	UserDTO createUser(UserDTO user);
	
	UserDTO updateUser(UserDTO user, Integer userId);
	
	UserDTO getUserByID(Integer userId);
	
	List<UserDTO> getUsers();
	
	void deleteUser(Integer userId);
	

}
