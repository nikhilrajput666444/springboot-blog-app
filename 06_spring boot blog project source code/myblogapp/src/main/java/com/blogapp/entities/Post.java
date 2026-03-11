package com.blogapp.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String title;
	
	private String url;
	
	@Column(nullable = false)
	private String shortDesc;
	
	@Lob
	@Column(nullable = false,columnDefinition = "LONGTEXT")
	private String content;
	
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdOn;//when post created
	
	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDateTime updatedOn;//when post updated
	
	
	@Column(nullable = false)
	private String status; //pending,approved,rejected
	
	
	private String postImageName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Category category;
	
	
	//for comments
	//one post can have multiple comments
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
	private List<PostComment> comment=new ArrayList<>();
	
	
	@ManyToOne
	private MyUser user;
	

}//
