package com.blogapp.serviceimpl;

import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogapp.dto.PostDto;
import com.blogapp.entities.Category;
import com.blogapp.entities.MyUser;
import com.blogapp.entities.Post;
import com.blogapp.helper.Helper;
import com.blogapp.repo.CategoryRepo;
import com.blogapp.repo.PostRepository;
import com.blogapp.repo.UserRepo;
import com.blogapp.securityconfig.SecurityHelper;
import com.blogapp.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CategoryRepo catRepo;
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public PostDto savePost(PostDto pdto) {

		// convert dto to entity
		Post postEntity = modelMapper.map(pdto, Post.class);

		// set status as PENDING
		postEntity.setStatus("PENDING");

		// before saving post set catgeory to post entity
		// based on category id fetch category object/data from db and set to post
		Category category = catRepo.findById(pdto.getCategoryId())
				.orElseThrow(() -> new RuntimeException("catgery not found invalid id!!"));

		postEntity.setCategory(category);
		
		
		//we need to set currently logged in user to post
		String username = SecurityHelper.getCurrentLoggedInUsername();
		MyUser currentLoggedInUser = userRepo.findByEmail(username);
		
		
		postEntity.setUser(currentLoggedInUser);

		Post savedPost = postRepo.save(postEntity);

		// entity to dto
		return modelMapper.map(savedPost, PostDto.class);

	}

	@Override
	public Page<PostDto> getPostsForCurrentUser(int pageNo, int size) {

		Pageable pageable = PageRequest.of(pageNo, size, Sort.by("createdOn").descending());

		
		//get currently logged in username
		String username = SecurityHelper.getCurrentLoggedInUsername();
		
		//fetch loggedin user form db
		MyUser loggedInUser = userRepo.findByEmail(username);
		
		//fetch loggedin user roles
		boolean isAdmin = loggedInUser.getRoles().stream().anyMatch(role->role.getName().equals("ADMIN"));
		
		Page<Post> postPage ;
		
		if(isAdmin)
		{
			//if admin loggedin then fetching all posts
			postPage= postRepo.findAll(pageable);	
			
		}else
		{
			//if guest then fetch specific posts
			postPage=postRepo.findByUser(loggedInUser, pageable);
			
		}
		

		Page<PostDto> postPageDto = postPage.map(p -> modelMapper.map(p, PostDto.class));

		return postPageDto;
	}

	@Override
	public void updatePostStatus(int postId, String status) {

		Post existingPost = postRepo.findById(postId)
				.orElseThrow(() -> new RuntimeException("Post not found invalid id!!"));

		existingPost.setStatus(status);

		postRepo.save(existingPost);

	}

	@Override
	public PostDto getPostById(int postId) {
		Post post = postRepo.findById(postId).orElseThrow(() -> new RuntimeException("Post not found invalid id!!"));

		PostDto dto = modelMapper.map(post, PostDto.class);
		return dto;
	}

	@Override
	public void deletePostbyId(int postId) {

		Post post = postRepo.findById(postId).orElseThrow(() -> new RuntimeException("Post not found invalid id!!"));

		postRepo.delete(post);
	}

	@Override
	public PostDto updatePost(PostDto newpost) {

		Post existingpost = postRepo.findById(newpost.getId())
				.orElseThrow(() -> new RuntimeException("Post not found invalid id!!"));

		existingpost.setTitle(newpost.getTitle());
		existingpost.setShortDesc(newpost.getShortDesc());
		existingpost.setContent(newpost.getContent());
		existingpost.setPostImageName(newpost.getPostImageName());

		Category category = catRepo.findById(newpost.getCategoryId())
				.orElseThrow(() -> new RuntimeException("catgery not found invalid id!!"));
		existingpost.setCategory(category);

		// save the updated data to db

		Post entity = postRepo.save(existingpost);

		return modelMapper.map(entity, PostDto.class);

	}

	@Override
	public Page<PostDto> getAllApprovedBlogPost(Pageable pageable) {

		Page<Post> apporvedPosts = postRepo.findByStatus("APPROVED", pageable);

		Page<PostDto> approvedPostDto = apporvedPosts.map(p -> {

			PostDto dto = modelMapper.map(p, PostDto.class);
			dto.setShortDesc(Helper.htmlSanitize(dto.getShortDesc()));
			return dto;
		});

		return approvedPostDto;
	}

	@Override
	public Page<PostDto> getPostsByCategory(int catId, Pageable pageable) {

		Page<Post> postsPage = postRepo.findPostsByCategoryAndStatus(catId, "APPROVED", pageable);

		Page<PostDto> approvedPostDto = postsPage.map(p -> {

			PostDto dto = modelMapper.map(p, PostDto.class);
			dto.setShortDesc(Helper.htmlSanitize(dto.getShortDesc()));
			return dto;
		});

		return approvedPostDto;
	}

	@Override
	public Page<PostDto> searchBlogPost(String search, Pageable pageable) {

		Page<Post> postPage = postRepo.searchPost(search, "APPROVED", pageable);

		Page<PostDto> searchedPost = postPage.map(p -> {

			PostDto dto = modelMapper.map(p, PostDto.class);
			dto.setShortDesc(Helper.htmlSanitize(dto.getShortDesc()));
			return dto;
		});

		return searchedPost;
	}

	@Override
	public PostDto getPostByIdandUrl(int id, String url) {
		
		Post post = postRepo.findByIdAndUrl(id, url).orElseThrow(() -> new RuntimeException("Post not found invalid id!!"));
		
		return modelMapper.map(post, PostDto.class);

		
	}

	
	
	
	
}//
