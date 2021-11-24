package com.spring.test.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.spring.test.dao.ContactRepository;
import com.spring.test.dao.UserRepository;
import com.spring.test.entities.Contact;
import com.spring.test.entities.User;

@RestController
public class SearchController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;

	// search handler
	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query, Principal principle) {
		System.out.println(query);
		// get data of logged in user by name
		User user = this.userRepository.getByName(principle.getName());

		// get list of contact of logged in user from directory
		List<Contact> contactlist = this.contactRepository.findByNameContainingAndUser(query, user);
		return ResponseEntity.ok(contactlist);

	}
}