package com.contact.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contact.dao.UserRepository;
import com.contact.entities.User;
import com.contact.helper.Message;


@Controller
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// home page Handler
	@GetMapping("/")
	public String start() {

		return "home";
	}

	// about page Handler
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("data", "This is dynamic message" + "and here it is second message");

		return "about";
	}

	// SignUp Page Handler
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("data", "This is Signup Page");
		model.addAttribute("user", new User());
		return "signup";
	}

	// register handler
	@PostMapping("/do_register")
	public String register(@Valid @ModelAttribute("user") User user, BindingResult bindresult,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,
			HttpSession session) {

		try {
			if (!agreement) {
				throw new Exception("You Have Not Agreed Terms and Conditions");
			}
			if (bindresult.hasErrors()) {
				System.out.println("ERROR" + bindresult.toString());
				model.addAttribute("user", new User());

				return "signup";
			}

			// set role
			user.setRole("User");
			user.setActivity(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			System.out.println("User" + user);
			System.out.println("Agree:" + agreement);
			User result = userRepository.save(user);
			model.addAttribute("user", new User());

			session.setAttribute("message", new Message("Registration Successful !!!", "alert-success"));

		} catch (Exception e) {
			e.printStackTrace();
			// keep old fields
			model.addAttribute("user", user);

			session.setAttribute("message", new Message("Somthing went wrong !!! " + e.getMessage(), "alert-danger"));
			return "signup";
		}
		return "signup";

	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("loginpage", "This is Login Page");

		return "login";
	}

}
