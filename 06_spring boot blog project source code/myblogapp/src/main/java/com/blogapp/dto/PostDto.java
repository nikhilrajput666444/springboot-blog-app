package com.blogapp.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

	private int id;
	
	@NotEmpty(message="Title cannot be empty")
	@Size(min=5,max=100,message="Title must be between 5 and 100 characters")
	private String title;

	private String url;

	@NotEmpty(message="short desc cannot be empty")
	private String shortDesc;

	@NotEmpty(message="content cannot be empty")
	private String content;

	private LocalDateTime createdOn;

	private LocalDateTime updatedOn;

	private String status;

	private String postImageName;
	
	
	@NotNull(message="Please select the category!!!")
	private Long  categoryId;

}//
