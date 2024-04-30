package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.Entities.Contact;
import com.smart.Entities.User;
import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class userController {
 
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Autowired
	private UserRepository userRepository;
	
      @Autowired 
      private ContactRepository contactRepository;
	
	//Adding common Data
	
	@ModelAttribute
	public void addCommonData(Model model,Principal principal) {
		String userName = principal.getName();
		System.out.println("UserName : "+userName);
		User user = userRepository.getUserByUserName(userName);
		
		System.out.println("User : "+user);
		model.addAttribute("user", user);
		
	}
	
	//dashboard Home
	
	@GetMapping("/index")
	public String dashboard(Model model,Principal principal) {
		
		model.addAttribute("title", "User Dashboard");
		return "normal/user_dashboard";
	}
  
	
  //Open add form Handler
	
	@GetMapping("/add-contact")
	public String openAddContactForm(Model model) {
		
		model.addAttribute("title", "Add Contsct");
		model.addAttribute("contact", new Contact());
		
		return "normal/add_contact_form";
	}
	
	
	
	//process-contact add from
		@PostMapping("/process-contact")
		public String processContact(@Valid
				@ModelAttribute("contact") Contact contact,
				BindingResult result,
				@RequestParam("image") MultipartFile image,
				Principal principal,
				HttpSession session) {
		 
			
			try {
			
				 if(!result.hasErrors()) {
			            // Handle validation errors
					 System.out.print("Error : "+result.toString());
					// model.addAttribute("contact", contact);
						return "normal/add_contact_form";
				 }
				 
				
				
		        String name = principal.getName();
		        User user = this.userRepository.getUserByUserName(name);
		        
		        //Processing and Uploading file
		        
		        if(image.isEmpty()) {
		        	//if the file is empty

			        System.out.println("File is Empty");
			        contact.setImage("contacts.jpg");
		        }else {
		        	
		        	//Upload the file in folder the name to contact
		        	
		        	contact.setImage(image.getOriginalFilename());
		        	
		        File saveFile = new ClassPathResource("static/img").getFile();
		        	Path path =  Paths.get(saveFile.getAbsolutePath()+File.separator+image.getOriginalFilename());
		        	Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		        	contact.setImage(path.toString());
		        System.out.println("Image is uploded Seccessfully");
		        
		        
		        }
		        
		        
		        user.getContact().add(contact);
		        contact.setUser(user);
		        
		        
		        this.userRepository.save(user);
		        System.out.println("Added to Database");
		        session.setAttribute("msg", new Message("Contact added Successfully!! Add more", "alert-success"));
		       
		        //return "redirect:/normal/add_contact_form";
			
			
			
			
			}catch (Exception e) {
				System.out.println("Error: "+e.getMessage());
				e.printStackTrace();
				 session.setAttribute("msg", new Message("Something went wrong! Plase try agin!", "alert-danger"));
				 
			
			}
		  return "normal/add_contact_form";
			 
		}

		/* View Contact */
		//par page =5[n]
          //current page = 0[page]
		
		
		@GetMapping("/show-contact/{page}")
		public String viewContact(@PathVariable("page") Integer page,Model model,Principal principal) {
			model.addAttribute("title", "Show User Contact");
			
		
		String userName = principal.getName();
		User user  = this.userRepository.getUserByUserName(userName);
		
		Pageable pageable = PageRequest.of(page, 3);
	    Page<Contact> contact = this.contactRepository.findContactByUser(user.getId(),pageable);
	
	    model.addAttribute("contact", contact);
	    model.addAttribute("currentpage", page);
	    model.addAttribute("totalPages", contact.getTotalPages());
		return "normal/show_contact";
		
		
		}
		
		//Showing perticular contact details 
		@GetMapping("/{id}/contact")
		public String showContactDetails(@PathVariable("id") Integer id,Model model,Principal principal) {
			
			System.out.println("ID : " + id);
			
		Optional<Contact> contactOptional = this.contactRepository.findById(id);
			
			Contact contact = contactOptional.get();
			
			
			
			String userName = principal.getName();
			
			User user = this.userRepository.getUserByUserName(userName);
			
			if(user.getId() == contact.getUser().getId()) { 
				model.addAttribute("contact", contact);
			   model.addAttribute("title", contact.getName());
			
			}
			
			
			return "normal/contact_details";
		}
		
		
		
		//Deleted Contact
		@GetMapping("/delete/{id}")
		public String deleteContact(@PathVariable("id")Integer id,Model model,Principal principal,HttpSession session) {
			

             Contact contact = this.contactRepository.findById(id).get();
			
           
			
			User user = this.userRepository.getUserByUserName(principal.getName());
			
			if(user.getId() == contact.getUser().getId()) { 
				model.addAttribute("contact", contact);
			   model.addAttribute("title", contact.getName());
			
			}
			
		
		 
		user.getContact().remove(contact);			
			
			this.userRepository.save(user);
			
			session.setAttribute("msg", new Message("Contact Deleted Successfully...", "success"));
			
			return "redirect:/user/show-contact/0";
		}
		
		//Update Contact
		@PostMapping("/update-contact/{id}")
		public String updateContact(@PathVariable("id") Integer id,Model m) {
			
			m.addAttribute("title", "update-contact");
			
		Contact  contact  = this.contactRepository.findById(id).get();
			m.addAttribute("contact", contact);
			
			return "normal/update_contact";
		}
		
		
		//Update Handler
		@PostMapping("/process-update")
		public String updateHandler(@Valid @ModelAttribute Contact contact,
				BindingResult result,
				@RequestParam("image") MultipartFile image,
				Principal principal,
				Model model,
				HttpSession session) {
			
			try {
				
				//old contact details
				if(!result.hasErrors()) {
		            // Handle validation errors
				 System.out.print("Error : "+result.toString());
				// model.addAttribute("contact", contact);
				 return "normal/update_contact";
			 }
			 
			Contact old	= this.contactRepository.findById(contact.getId()).get();
				
				
			//Image
				if(!image.isEmpty()) {
					
					//Delete old Photo
					 File deleteFile = new ClassPathResource("static/img").getFile();
			        	File file1 = new File(deleteFile,old.getImage());
			        	file1.delete();
					
					
					//update new Photo
					 File saveFile = new ClassPathResource("static/img").getFile();
			        	Path path =  Paths.get(saveFile.getAbsolutePath()+File.separator+image.getOriginalFilename());
			        	Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				          contact.setImage(image.getOriginalFilename());
				
				}else {
					
					contact.setImage(old.getImage());
				}
					
				User user = this.userRepository.getUserByUserName(principal.getName());
				contact.setUser(user);
				this.contactRepository.save(contact);
				
				
				
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
			
			System.out.print("Contact Name : "+contact.getName());
			System.out.print("Contact Name : "+contact.getId());
			
			return "redirect:/user/" + contact.getId() + "/contact";
		}
		
		
		//Your Profile 
		@GetMapping("/profile")
		public String yourProfile(Model model) {
			model.addAttribute("title", "profile page");
			
			
			return "normal/profile";
		}
		
		
		
		//Setting 
		@GetMapping("/setting")
		public String openSetting() {
			
			return "normal/setting";
		}
		
		
	// Change Password
		@PostMapping("/change-password")
		public String changePassword(@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword,Principal principal,HttpSession session) {
			
			System.out.println("OLD :"+oldPassword);
			System.out.println("NEW : "+newPassword);
			
			User user = this.userRepository.getUserByUserName(principal.getName());
			  if(this.bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
				  // Change the Password
				  user.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
			    this.userRepository.save(user);
			    session.setAttribute("msg", new Message("Password Changed Successfully...", "success"));
			  }else {
				  //error
				  session.setAttribute("msg", new Message(" Wrong old password! Plase try agin!", "alert-danger"));
					return "redirect:/user/setting";
			  }
			
			return "redirect:/user/index";
		}
		
		
}
