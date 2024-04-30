package com.smart.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="contact")
public class Contact {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
 private int id ;

@NotBlank(message="Name field is required!")
@Size(min=3,max=50,message="Min 3 and  Max 50 characters are allowed!!")
 private String name;
@NotBlank(message="Email field is required!")
@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
 private String email;

@NotBlank(message="Last Name field is required!")
@Size(min=2,max=20,message="Min 3 and  Max 20 characters are allowed!!")
 private  String nikName;

@NotBlank(message="Work field is required!")
@Size(min=3,max=100,message="Min 3 and  Max 10 characters are allowed!!")
 private String work;
@NotBlank(message="Number field is required!")
@Size(min = 10, max = 10)
 private String phone;

 private String image;
 @Size(min=5,max=1000,message="Min 5 and  Max 1000 characters are allowed!!")
 @NotBlank(message="Description field is required!")
 @Column(length=50000)
 private String description;
 
 @JsonIgnore
 @ManyToOne
 private User user;
 
 
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
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
public String getNikName() {
	return nikName;
}
public void setNikName(String nikName) {
	this.nikName = nikName;
}
public String getWork() {
	return work;
}
public void setWork(String work) {
	this.work = work;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
@Override
public boolean equals(Object obj) {
	// TODO Auto-generated method stub
	return this.id == ((Contact)obj).getId();
}
 
 
 
 
 
 
 
 
 
}