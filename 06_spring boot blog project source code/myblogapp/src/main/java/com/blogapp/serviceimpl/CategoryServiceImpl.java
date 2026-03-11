package com.blogapp.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.dto.CategoryDto;
import com.blogapp.entities.Category;
import com.blogapp.repo.CategoryRepo;
import com.blogapp.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepo categroyRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	

	@Override
	public CategoryDto createCategory(CategoryDto cdto) {
		
		//first fetch the existing category from db by its name
		if(categroyRepo.existsByNameIgnoringCase(cdto.getName()))
		{
			throw new RuntimeException("Category already exists !!");
		}
		
	    //convert dto into entity
		Category category = modelMapper.map(cdto, Category.class);
		
		Category savedCategory = categroyRepo.save(category);
		
		
		
		return modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		
		List<Category> categories = categroyRepo.findAll();
		
		List<CategoryDto> catDtolist = categories.stream().map(c->modelMapper.map(c, CategoryDto.class)).collect(Collectors.toList());
		
		
		return catDtolist;
	}
	
	

}
