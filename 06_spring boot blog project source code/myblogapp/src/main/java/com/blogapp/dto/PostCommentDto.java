package com.blogapp.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentDto {

	private Long id;

	@NotBlank(message = "Name is required!!")
	private String name;

	@NotBlank(message = "Email is required!!")
	@Email(message = "Please provide valid email!!")
	private String email;

	@NotBlank(message = "comment is required!!")
	@Size(min=10,message="Comment must be at least 10 character")
	private String commentMsg;

	private LocalDateTime createdOn;

	private int postId;
	
	private String postTitle;
	
	private String url;
	
}
