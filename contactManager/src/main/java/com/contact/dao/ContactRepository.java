package com.contact.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contact.entities.Contact;
import com.contact.entities.User;



public interface ContactRepository extends JpaRepository<Contact, Integer> {

	
	//To implement Pagination
	//Pageble contains info of:1>current pagenumber
	//				           2>number of contact per page

	@Query("from Contact as c where c.user.id=:userId")
	public Page<Contact> findcontactByUser(@Param("userId") int userId, Pageable pagable);

	public List<Contact> findByNameContainingAndUser(String query, User user);

}
