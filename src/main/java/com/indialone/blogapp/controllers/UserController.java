package com.indialone.blogapp.controllers;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.List;
import java.util.Map;

import com.indialone.blogapp.models.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.indialone.blogapp.payloads.UserDTO;
import com.indialone.blogapp.services.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserDTO userDTO) {

        try {
            UserDTO user = userService.createUser(userDTO);
            return new ResponseEntity<>(new ApiResponse("Success", true, user), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("Failed", false, Map.of()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllUsers() {
        try {
            return new ResponseEntity<>(new ApiResponse("Success", true, userService.getUsers()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("Failed", false, Map.of()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable("userId") int id) {
        userService.deleteUser(id);
        String message = "User with ID: " + id + " deleted successfully";
        boolean status = true;
        ApiResponse apiResponse = new ApiResponse(message, status, Map.of());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateExistingUser(@Valid @RequestBody UserDTO userDTO, @PathVariable("userId") int id) {
        UserDTO updatedUser = userService.updateUser(userDTO, id);
        return new ResponseEntity<>(new ApiResponse("Success", true, updatedUser), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable("userId") int id) {
        UserDTO user = userService.getUserByID(id);
        return new ResponseEntity<>(new ApiResponse("Success", true, user), HttpStatus.OK);
    }

}
