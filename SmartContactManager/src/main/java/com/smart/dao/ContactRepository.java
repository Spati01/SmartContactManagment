package com.smart.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.Entities.Contact;
import com.smart.Entities.User;



public interface ContactRepository extends JpaRepository<Contact, Integer>{

	//Pagination.....
	
	//1. currentPage-page
	//Contact per page- 5
	@Query("from Contact as c where c.user.id =:userId")
	public Page<Contact>  findContactByUser(@Param("userId") int userId,Pageable pageable);
	
	// Search Query

	public List<Contact> findByNameContainingAndUser(String name,User user);
	
	
}
