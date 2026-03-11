package com.blogapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
	
	
	// dashboard/admin
	@GetMapping("/admin")
	public String showAdminDashboard()
	{
		return "admin/admindashboard";
	}
	
	
	// dashboard/admin
		@GetMapping("/guest")
		public String showGuestDashboard()
		{
			return "guest/guestdashboard";
		}
		
	

}
