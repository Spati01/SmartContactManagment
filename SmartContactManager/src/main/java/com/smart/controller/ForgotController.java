package com.smart.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForgotController {
	
	Random random = new Random(1000);
	//Email if form open handler
	@GetMapping("/forgot")
	public String openEmaulForm() {
		
		 return "forgot_email_form";
	}
	
	@PostMapping("/send-otp")
	public String sendOTP(@RequestParam("email") String email) {
		
		System.out.println("Email :"+email);
		
		//generating otp 4 digit
	
	int otp = random.nextInt(99999);
	System.out.println("OPT : "+otp);	
	
	//Write code for send otp to email
	
	
	
	
	
	
		 return "verify_otp";
	}
	
	
}
