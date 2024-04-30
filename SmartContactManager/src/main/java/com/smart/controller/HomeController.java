package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.Entities.User;
import com.smart.dao.UserRepository;
import com.smart.helper.Message;
import com.smart.service.SessionService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	

	@GetMapping("/")
	public String home(Model m) {
		
		m.addAttribute("title", "Home-Smart Contact Manager");
		return "home";
	}
	@GetMapping("/about")
	public String about(Model m) {
		
		m.addAttribute("title", "About-Smart Contact Manager");
		return "about";
	}
	
	
	@GetMapping("/signup")
	public String signup(Model m) {
		
		m.addAttribute("title", "Register-Smart Contact Manager");
		m.addAttribute("user", new User());
		return "signup";
	}
	
	//Handler for registerUser
	@PostMapping("/do_register")
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result,@RequestParam(value="agreement",defaultValue = "false") Boolean agreement,Model model,HttpSession session) {
		
		try {
			if(!agreement) {
			    System.out.println("You have not agreed the terms and conditions");
				throw new Exception("You have not agreed the terms and conditions");
			}
			if(result.hasErrors()) {
				System.out.println("ERROR "+result.toString());
				model.addAttribute("user", user);
				return "signup";
			}
			
//			if(!user.getAgreement()) {
//				
//				System.out.println("You have not agreed the terms and conditions");
//				throw new Exception("You have not agreed the terms and conditions");
//				
//			}
			
//			 if(userRepository.existsByEmail(user.getEmail())) {
//		            throw new Exception("Email address already exists");
//		        }
				user.setRole("ROLE_USER");
				user.setAgreement(true);
				user.setImgUrl("Default.png");
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				
				System.out.println("Agreement" + agreement);
				System.out.println("User" + user);
				
				
				User resultUser = this.userRepository.save(user);
				
				model.addAttribute("user",new User());
				  session.setAttribute("msg", new Message("Successfully Registered", "alert-success"));
			 
				  return "redirect:/signup";
				  
			
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("msg", new Message("Something went wrong: " + e.getMessage(), "alert-danger"));
	        return "signup";
			
		}
		
	
	}
	
	
	// Handler for customLogin
	@GetMapping("/signin")
	public String login(Model model,HttpSession session) {
		
		model.addAttribute("title" , "Login Page");
		 //session.setAttribute("msg", new Message("Login Successfully", "alert-success"));
		return "login";
	}
	
}
