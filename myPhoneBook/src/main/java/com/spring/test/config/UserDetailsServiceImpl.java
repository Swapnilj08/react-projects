package com.spring.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.spring.test.dao.UserRepository;
import com.spring.test.entities.User;

public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//fetching user from database
		User user = userRepository.getUserByName(username);
		
		System.out.println("UserDetailsServiceImpl class"+user);
		if (user==null) {
			throw new UsernameNotFoundException("Could not Found User");
		}
		 CostumUserDetails userDetails = new CostumUserDetails(user);
		return userDetails;
	}

}
