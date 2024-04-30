package com.smart.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.smart.Entities.Contact;
import com.smart.Entities.User;
import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;

@RestController 
public class SearchController {

	@Autowired 
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;
	
	
	
	//Search handler
	@GetMapping("/search/{query}")
	public ResponseEntity<?> Search(@PathVariable("query") String query, Principal principal){
		
	System.out.println(query);
	User user = userRepository.getUserByUserName(principal.getName());
	List<Contact> contact =	this.contactRepository.findByNameContainingAndUser(query , user);
	return ResponseEntity.ok(contact);
	}
	
}
