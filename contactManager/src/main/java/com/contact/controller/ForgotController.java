package com.contact.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contact.dao.UserRepository;
import com.contact.entities.User;
import com.contact.helper.EmailService;
import com.contact.helper.Message;


@Controller
public class ForgotController {

	@Autowired
	private UserRepository userRepository; 
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; 
	
	@Autowired
	private EmailService emailService;
	
	//Forgot password page handler
	@RequestMapping("/forgot")
	public String forgotpassword() {
		return "forgotpassword";
	}
	//send OTP Handler
	@PostMapping("/sendotp")
	public String verifyotp(@RequestParam("email") String email,HttpSession session) {
		System.out.println("email>>>>>>>>>s"+email);
	
		Random random = new Random();
		
		int otp = random.nextInt(10000);
		System.out.println("OTP>>>>>>>>>>>"+otp);
		
		String subject="OTP from SCM";
		String message="<div style='border:1px solid #e2e2e2; padding:20px;'>"
				+"<h1>"
				+"Your OTP is "
				+ "<b>"
				+otp
				+"</b>"
				+ "</h1>"
				+ "</div>";
		
		boolean Emailsent = emailService.sendEmail(subject, message, email);
		if(Emailsent) {
			//store otp and email in session
			session.setAttribute("myotp", otp);
			session.setAttribute("myemail", email);
			return"sentotp";
		}else {
			session.setAttribute("message", new Message("PLease CHeck Your Mail ID","danger"));
			return "forgotpassword";
		}
		
		
	}
	
	//verify OTP
	@RequestMapping("/verifyotp")
	public String verifyotp(@RequestParam("otp") int enteredotp, HttpSession session) {
		// get otp and email from session stored at verifyotp method
		int ourotp = (int) session.getAttribute("myotp");
		String email = (String) session.getAttribute("myemail");

		System.out.println("entered OTP>>>> " + enteredotp);
		System.out.println("My OTP>>>> " + ourotp);

		if (enteredotp == ourotp) {
			// get user details from database with email reference
			User user = this.userRepository.getUserByName(email);
			System.out.println(user);
			if (user == null) {
				System.out.println("User is Null");
				session.setAttribute("message", new Message("Entered Email is not present in database", "danger"));
				return "sentotp";
			}
			return "reset_password_form";

		} else {
			System.out.println("OTP Not matching");
			session.setAttribute("message", new Message("OTP Does not match", "danger"));
			return "sentotp";
		}

	}
	//Change password process handler
	@RequestMapping("/changepass")
	public String changepass(@RequestParam("newpassword") String newpass,
			@RequestParam("confirmpassword") String confirmpass, HttpSession session) {
		System.out.println("nepass" + newpass + "\n" + "confirmpass" + confirmpass);

		if (newpass.equals(confirmpass)) {
			System.out.println("changing password");
			String email = (String) session.getAttribute("myemail");
			User user = this.userRepository.getUserByName(email);
			user.setPassword(bCryptPasswordEncoder.encode(newpass));
			this.userRepository.save(user);
		} else {
			session.setAttribute("message", new Message("confirm password Does not matching", "danger"));
			return "reset_password_form";
		}

		return "redirect:/login?change=password changed successfully !!";
	}
	
}
