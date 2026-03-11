package com.blogapp.controllers;

import java.io.File;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blogapp.dto.CategoryDto;
import com.blogapp.dto.PostCommentDto;
import com.blogapp.dto.PostDto;
import com.blogapp.helper.Helper;
import com.blogapp.services.CategoryService;
import com.blogapp.services.CommentService;
import com.blogapp.services.PostService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CommentService commServ;

	// display all post page for admin / guest
	@GetMapping("/allposts")
	public String allPosts(@RequestParam(defaultValue = "0") int pageNo, Model model) {
		int size = 5;
		Page<PostDto> postPage = postService.getPostsForCurrentUser(pageNo, size);

		model.addAttribute("postPage", postPage);
		model.addAttribute("currentpage", pageNo);

		return "common/allposts";
	}

	// it display add post page
	@GetMapping("/newpost")
	public String showCreatePostPage(Model model) {

		// fetch list of category from db
		List<CategoryDto> categories = categoryService.getAllCategory();

		PostDto pdto = new PostDto();
		model.addAttribute("post", pdto);
		model.addAttribute("categories", categories);

		return "common/createpost";
	}

	@PostMapping("/savepost")
	public String savePost(@ModelAttribute("post") @Valid PostDto postDto, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, @RequestParam("image") MultipartFile imagePart, Model model) {
		// check validation error
		if (bindingResult.hasErrors()) {
			List<CategoryDto> categories = categoryService.getAllCategory();
			model.addAttribute("categories", categories);

			model.addAttribute("post", postDto);
			return "common/createpost";
		}

		// change title in hypen style
		String convertedTitle = Helper.getUrlFromTitle(postDto.getTitle());
		postDto.setUrl(convertedTitle);

		String imageName = "";

		if (!imagePart.isEmpty()) {
			imageName = StringUtils.cleanPath(Objects.requireNonNull(imagePart.getOriginalFilename()));
			postDto.setPostImageName(imageName);
		} else {
			redirectAttributes.addFlashAttribute("error", "please upload the image!!!");
			return "redirect:/post/newpost";

		}

		// save post data and get post id
		PostDto savedPostDto = postService.savePost(postDto);

		// get post id from savedpostdto
		if (savedPostDto != null) {
			String uploadDirectorypath = "postfiles/" + savedPostDto.getId();// postfiles/1 ,postfiles/2

			boolean isImageSaved = Helper.saveFile(uploadDirectorypath, imageName, imagePart);

			if (isImageSaved) {
				redirectAttributes.addFlashAttribute("msg", "PostAdded!!!");
			} else {
				redirectAttributes.addFlashAttribute("error", "soemthing went wrong image not saved!!!");
			}

		} else {
			redirectAttributes.addFlashAttribute("error", "soemthing went wrong !!!");
		}

		// save post image to folder

		// add flash attribute for UI msg

		return "redirect:/post/newpost";

	}

	// approve post
	@GetMapping("/admin/approvePost")
	public String approvePost(@RequestParam("postId") int postId) {

		// call service and service call repo
		postService.updatePostStatus(postId, "APPROVED");

		return "redirect:/post/allposts";

	}

	// reject post
	@GetMapping("/admin/rejectPost")
	public String rejectPost(@RequestParam("postId") int postId) {

		// call service and service call repo
		postService.updatePostStatus(postId, "REJECTED");

		return "redirect:/post/allposts";

	}

	// to display view post html page
	@GetMapping("/viewpostdata")
	public String viewPost(@RequestParam("postId") int postId, Model model) {
		PostDto postdto = postService.getPostById(postId);
		model.addAttribute("post", postdto);
		return "common/viewpost";
	}

	// delete post
	@GetMapping("/deletepost")
	public String deletePost(@RequestParam("postId") int postId) {
		// based on post id delete post image from postfiles dir
		String folderPath = "postfiles/" + postId;// postfiles/6

		File file = new File(folderPath);

		if (file.exists()) {
			// delete data from sub folder
			for (File f : file.listFiles()) {
				f.delete();
			}

			// delete the sub folder
			file.delete();

		}

		// delete post from db
		postService.deletePostbyId(postId);
		return "redirect:/post/allposts";

	}

	// display edit post page with existing data
	@GetMapping("/editpostdata")
	public String editpost(@RequestParam("postId") int postId, Model model) {

		// fetch list of category from db
		List<CategoryDto> categories = categoryService.getAllCategory();

		// fetch existing post from db and add to model which will show existing data to
		// edit form
		PostDto pdto = postService.getPostById(postId);
		model.addAttribute("post", pdto);
		model.addAttribute("categories", categories);
		return "common/editpost";

	}

	// update existing post
	@PostMapping("/updatepost")
	public String updatePost(@ModelAttribute("post") @Valid PostDto postDto, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, @RequestParam("image") MultipartFile imagePart, Model model) {
		// check validation error
		if (bindingResult.hasErrors()) {
			List<CategoryDto> categories = categoryService.getAllCategory();
			model.addAttribute("categories", categories);

			model.addAttribute("post", postDto);
			return "common/editpost";
		}

		// check use ruploaded the post image or not if uploaded the new image then
		// delete old image
		if (imagePart != null && !imagePart.isEmpty()) {

			// delete old image by using oldimagename
			if (postDto.getPostImageName() != null) {
				String oldImagePath = "postfiles/" + postDto.getId() + "/" + postDto.getPostImageName();// postfiles/8/5.png

				File oldImageFile = new File(oldImagePath);
				if (oldImageFile.exists()) {
					System.out.println("old image delete!!");
					oldImageFile.delete();
				}
			}

			// now after delte old image file save new image file to dir

			String newimageName = StringUtils.cleanPath(Objects.requireNonNull(imagePart.getOriginalFilename()));
			postDto.setPostImageName(newimageName);// setting new image name after delete old image

			String uploadedDirPath = "postfiles/" + postDto.getId();

			boolean isImageSaved = Helper.saveFile(uploadedDirPath, newimageName, imagePart);

			if (isImageSaved) {
				redirectAttributes.addFlashAttribute("msg", "PostAdded!!!");
			} else {
				redirectAttributes.addFlashAttribute("error", "soemthing went wrong image not saved!!!");
			}

		}

		// update post data to db
		PostDto updatedPost = postService.updatePost(postDto);

		if (updatedPost != null) {
			redirectAttributes.addFlashAttribute("msg", "Post updated!!!");
		} else {
			redirectAttributes.addFlashAttribute("error", "soemthing went wrong image not updated!!!");
		}

		return "redirect:/post/allposts";

	}
	
	
	//to display all comments only admin can see this
	@GetMapping("/admin/viewallcomments")
	public String viewAllComments(@RequestParam(defaultValue = "0") int pageNo, Model model)
	{
		int size = 5;
		Pageable pageable = PageRequest.of(pageNo, size, Sort.by("createdOn").descending());
		Page<PostCommentDto> commentPage = commServ.getAllComments(pageable);
		
		model.addAttribute("commentPage", commentPage);
		model.addAttribute("currentPage", pageNo);
		
		return "admin/allcomments";
		
	}
	
	
	//comment delete by comment id
	@GetMapping("/admin/delete-comment/{id}")
	public String deleteComment(@PathVariable("id") Long id)
	{
		commServ.deleteComment(id);
		
		
		return "redirect:/post/admin/viewallcomments";
		
		
	}
	
	
	

}//
