package com.indialone.blogapp.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
	
	private int id;

	@NotEmpty
	@Size(min = 4,message = "username must be greater than 4 characters")
	private String name;

	@Email(message = "email id is not valid")
	private String email;

	@NotEmpty
	@Size(min = 5, max = 10, message = "password must be between 5-10 characters")
	private String password;

	@NotEmpty
	private String about;
	

}
