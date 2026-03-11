package com.blogapp.dto;

import java.util.List;

import com.blogapp.entities.MyUser;
import com.blogapp.entities.Post;
import com.blogapp.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpDto {
	
	private Long id;
	
	@NotEmpty(message = "fistname is mandatory!!")
	private String firstName;
	
	
	private String lastName;
	
	
	@NotEmpty(message = "email is mandatory!!")
	@Email(message = "Please enter valid email!!")
	private String email;
	
	@NotEmpty(message = "email is mandatory!!")
	@Size(min=6,message = "password must be at least 6 chars!!")
	private String password;
	

}
