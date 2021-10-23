package com.spring.test.Controller;

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

import com.spring.test.DAO.UserRepository;
import com.spring.test.entities.User;
import com.spring.test.helper.Message;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	
	@GetMapping("/")
	public String start() {
		System.out.println("Home method called");
		return "home";
	}

	// Method for about page
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("data", "This is dynamic message" + "and here it is second message");

		return "about";
	}

	// Method for SignUp Page
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("data", "This is Signup Page");
		model.addAttribute("user", new User());
		return "signup";
	}

	// @RequestMapping(value = "/do_register", method = RequestMethod.POST)
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

			// Role always Should be "ROLE_USER/ADMIN" otherwise security will throw
			// forbidden access error
			user.setRole("User");
			user.setActivity(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			System.out.println("User" + user);
			System.out.println("Agree:" + agreement);
			User result = userRepository.save(user);
			model.addAttribute("user", new User());
			// alert-danger(bootstrap command to display message in green color search alert
			// in bootstrap site)
			session.setAttribute("message", new Message("Registration Successful !!!", "alert-success"));

		} catch (Exception e) {
			e.printStackTrace();
			// to keep old fields
			model.addAttribute("user", user);

			// alert-danger(bootstrap command to display message in red color search alert
			// in bootstrap site)
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
