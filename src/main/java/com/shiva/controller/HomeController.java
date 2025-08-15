package com.shiva.controller;

import org.springframework.stereotype.Controller;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class HomeController {
	@GetMapping
	public  String   getHomeView(HttpSession session) {
		
		if(session.getAttribute("username") ==null) {
			
			return "redirect:/user/login";
		}
	  
	  return"home/home-page";
	  
	}
}
