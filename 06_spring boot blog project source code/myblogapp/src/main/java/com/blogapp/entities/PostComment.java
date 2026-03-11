package com.blogapp.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostComment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	
	private String email;
	
	@Lob
	@Column(nullable = false,columnDefinition = "LONGTEXT")
	private String commentMsg;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdOn;
	
	
	//many comments belongs to one post
	
	@ManyToOne
	private Post post;
	
	

}
