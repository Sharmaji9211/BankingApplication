package com.shiva.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shiva.models.User;
import com.shiva.services.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("user")
public class UserController {

	private UserService userService;
	public UserController( UserService userService) {
		this.userService=userService;
	}
	
	
	@GetMapping("/login")
	public ModelAndView getLoginForm() {
		ModelAndView modelAndView=new ModelAndView("user/login/login-form");
		return modelAndView;
		
	}
	@GetMapping("/registration")
	public String getRegistrationForm() {
		return "user/registration/registration-form";
	}
	@PostMapping("/register-user")
	public ModelAndView registerUser(User user) {
		if(userService.userExist(user.getUserid())) {
			ModelAndView modelAndView=new ModelAndView("user/registration/registration-success");
			modelAndView.addObject("username", user.getFirstname()+" "+user.getLastname());	
		}
		
		int accountno=userService.createUser(user);
		ModelAndView modelAndView=new ModelAndView("user/registration/registration-success");
		modelAndView.addObject("accountno", accountno);
		modelAndView.addObject("username", user.getFirstname()+" "+user.getLastname());
		return modelAndView;
	}
	@PostMapping("/authenticate-user")
	public ModelAndView Authenticateuser(String userid, String password,HttpSession session) {
		ModelAndView modelAndView=new  ModelAndView();
		User user=userService.getUser(userid);
		if(user==null) {
			modelAndView.addObject("uid", userid);
			modelAndView.addObject("msg", "Entered userid does not exist !");
			modelAndView.setViewName("user/login/login-form");
			return modelAndView;
		}
		if(!password.equals(user.getPassword())) {
			modelAndView.addObject("uid", userid);
			modelAndView.addObject("msg", "Entered Password is Wrong !");
			modelAndView.setViewName("user/login/login-form");
			return modelAndView;
		}
		session.setAttribute("username", user.getFirstname()+" "+user.getLastname());
		int accountno=userService.getAccountNo(userid);
		session.setAttribute("accountno", accountno);
		modelAndView.setViewName("redirect:/");
		return  modelAndView;	
	}
	@GetMapping("/logout")
	public String logout(HttpSession ses, Model model) {
		model.addAttribute("username", ses.getAttribute("username"));
		ses.invalidate();
		return "user/login/logout-success";
	}
}
