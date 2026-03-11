package com.blogapp.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.blogapp.dto.PostDto;

public interface PostService {
	
	//save
	PostDto savePost(PostDto pdto);
	
 Page<PostDto>	getPostsForCurrentUser(int pageNo,int size);//used to get all posts based on guest or admin role
 
 //to update post status
 void updatePostStatus(int postId,String status);
 
 //get post by id
 PostDto getPostById(int postId);
 
 //delete post
 void deletePostbyId(int postId);
 
 PostDto updatePost(PostDto postdto);
 
 
 //to get all approved posts
 Page<PostDto> getAllApprovedBlogPost(Pageable pageable);

 Page<PostDto> getPostsByCategory(int catId,Pageable pageable);
 
 
 Page<PostDto> searchBlogPost(String search,Pageable pageable);
 
 PostDto getPostByIdandUrl(int id,String url);
 
 
 
 
}//
