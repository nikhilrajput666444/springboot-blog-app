package com.blogapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.entities.PostComment;
import java.util.List;
import java.time.LocalDateTime;


public interface CommentRepo extends JpaRepository<PostComment,Long> {
	
	List<PostComment> findByPostIdOrderByCreatedOnDesc(int postId);

}
