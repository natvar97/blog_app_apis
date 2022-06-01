package com.indialone.blogapp.impls;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indialone.blogapp.exceptions.ResourceNotFoundException;
import com.indialone.blogapp.models.User;
import com.indialone.blogapp.payloads.UserDTO;
import com.indialone.blogapp.repositories.UserRepo;
import com.indialone.blogapp.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDTO createUser(UserDTO user) {
		User newUser = dtoToUser(user);
		User savedUser = userRepo.save(newUser);
		return userToDTO(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO user, Integer userId) {
		/*
		 * User newUser = dtoToUser(user); User finalUser =
		 * userRepo.findAll().stream().filter(new Predicate<User>() {
		 * 
		 * @Override public boolean test(User streamedUser) { if (streamedUser.getId()==
		 * userId) { return true; } return false; } }).findFirst().get();
		 * 
		 * userRepo.save()
		 */
		
		User newUser = userRepo
				.findById(userId)
				.orElseThrow(
						() -> new ResourceNotFoundException("User","id",userId)
						);
		
		newUser.setName(user.getName());
		newUser.setEmail(user.getEmail());
		newUser.setAbout(user.getAbout());
		newUser.setPassword(user.getPassword());
		
		User updatedUser = userRepo.save(newUser);
		
		return userToDTO(updatedUser);
	}

	@Override
	public UserDTO getUserByID(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "ID", userId));
		return userToDTO(user);
	}

	@Override
	public List<UserDTO> getUsers() {
		List<User> users = userRepo.findAll();
		List<UserDTO> userDTOs =  users.stream().map( user -> userToDTO(user)).collect(Collectors.toList());
		return userDTOs;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "ID", userId));
		userRepo.delete(user);

	}

	private User dtoToUser(UserDTO userDTO) {
		User user = new User();
		user.setId(userDTO.getId());
		user.setName(userDTO.getName());
		user.setAbout(userDTO.getAbout());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		
		return user;
	}
	
	private UserDTO userToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setAbout(user.getAbout());
		userDTO.setEmail(user.getEmail());
		userDTO.setPassword(user.getPassword());
		
		
		return userDTO;
	}
	
}
