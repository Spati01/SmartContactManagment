package com.smart.Entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="user")
public class User {
     
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotBlank(message="Name field is required!")
	@Size(min=5,max=20,message="Min 5 and  Max 20 characters are allowed!!")
	private String name;
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
	@Column(nullable = false, unique = true)
	private String email;
	@NotBlank(message="Password must be required! Min 4 and Max 8")
	private String password;
	private String role;
	@AssertTrue(message= "Must agree terms and condition")
	//private boolean enabled;
	@Column(columnDefinition = "BOOLEAN DEFAULT false")
	private Boolean agreement;
	private String imgUrl;
	@NotBlank(message="About must be required! Min 20 and Max 50 words")
	@Column(length = 500)
	private String about;
	
//  @Override
//	public String toString() {
//		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
//				+ ", enabled=" + agreement + ", imgUrl=" + imgUrl + ", about=" + about + ", contact=" + contact + "]";
//	}
@OneToMany(cascade = CascadeType.ALL,mappedBy = "user", orphanRemoval = true )
  private List<Contact> contact = new ArrayList<>();
	
	
	
	
	
	public List<Contact> getContact() {
		return contact;
	}


	public void setContact(List<Contact> contact) {
		this.contact = contact;
	}


	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
//	public boolean isEnabled() {
//		return agreement;
//	}
	
	public Boolean getAgreement() {
		return agreement;
	}


	public void setAgreement(Boolean agreement) {
		this.agreement = agreement;
	}


	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	
}
