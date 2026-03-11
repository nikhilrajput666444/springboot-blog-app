package com.blogapp.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blogapp.entities.MyUser;
import com.blogapp.entities.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
	//it will fetch all post form db based on there status
	Page<Post> findByStatus(String status,Pageable pageable);
	
	@Query("select p FROM Post p Where p.category.id=:categoryId AND p.status =:status")
	Page<Post> findPostsByCategoryAndStatus(@Param("categoryId") int categoryId, @Param("status") String status ,Pageable pageable);

   //user pass search string we need to search that string in title as well as short desc
	
	
	@Query("select p FROM Post p where (p.title LIKE concat('%',:search,'%') or  p.shortDesc LIKE concat('%',:search,'%')) and p.status =:status")
	Page<Post> searchPost(@Param("search") String search, @Param("status") String status ,Pageable pageable);




	
	 Optional<Post> findByIdAndUrl(int id,String url);

	 
	 //fetch posts based on logged in user
	 
	 Page<Post> findByUser(MyUser user,Pageable pageable);

}
