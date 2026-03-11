package com.blogapp.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BlogAppGlobalExceptionHandler {
	
	@ExceptionHandler(RuntimeException.class)
	public String handlerRunTimeException(RuntimeException ex,Model model)
	{
		model.addAttribute("error",ex.getMessage());
		
		return "error-page";
		
	}
	

}
