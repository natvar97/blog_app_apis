package com.indialone.blogapp.impls;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indialone.blogapp.exceptions.ResourceNotFoundException;
import com.indialone.blogapp.models.User;
import com.indialone.blogapp.payloads.UserDTO;
import com.indialone.blogapp.repositories.UserRepo;
import com.indialone.blogapp.services.UserService;
import org.springframework.ui.ModelMap;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;
	
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
		return modelMapper.map(userDTO, User.class);
	}
	
	private UserDTO userToDTO(User user) {
		return modelMapper.map(user, UserDTO.class);
	}
	
}
