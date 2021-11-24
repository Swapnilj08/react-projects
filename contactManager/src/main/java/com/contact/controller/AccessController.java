package com.contact.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.contact.dao.ContactRepository;
import com.contact.dao.UserRepository;
import com.contact.entities.Contact;
import com.contact.entities.User;
import com.contact.helper.Message;


@Controller
@RequestMapping("/user")
public class AccessController {

	@Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;

	// Method for adding common data to response
	@ModelAttribute
	public void addCommanData(Model model, Principal principal) {
		String name = principal.getName();
		// get the user details using user name
		User user = userRepository.getByName(name);
		model.addAttribute("user", user);
	}

	// user Home page handler
	@RequestMapping("/index")
	public String normalUser(Model model, Principal principle) {
		// To get THe Name of logged-in User
		String name = principle.getName();
		System.out.println("User:" + name);
		User user = userRepository.getByName(name);
		System.out.println(user);
		model.addAttribute("user", user);
		return "normal/normal-user";

	}

	// add contact page handler
	@GetMapping("/addContact")
	public String addContact(Model model) {
		model.addAttribute("title", "Add Contact Page");

		return "normal/addContact";
	}

	// processing add contact form
	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact, @RequestParam("imageFile") MultipartFile file,
			Principal principal, HttpSession session) {
		try {

			// process for uploading image file
			if (file.isEmpty()) {
				// if no file for upload
				System.out.println("Image not selected");
				// setting default image if no image selected
				contact.setPhotourl("Contacts_logo.png");
			} else {
				// if file exist for upload

				// to set file name in contact table
				contact.setPhotourl(file.getOriginalFilename());

				// get destination file path
				File savefile = new ClassPathResource("static/image").getFile();

				// get absolue formated path to use
				Path path = Paths.get(savefile.getAbsolutePath() + File.separator + file.getOriginalFilename());

				// to copy file to destination Folder 1. file.getInputStream():to get input
				// image stream 2. path:target folder path 3.keep both files or replace
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				System.out.println("File uploaded");

			}
			String name = principal.getName();
			// search logged in user data from user table
			User user = userRepository.getByName(name);
			// setting user entity values
			contact.setUser(user);
			// add data to contacts
			user.getContacts().add(contact);
			// Save data to database
			this.userRepository.save(user);

			session.setAttribute("message", new Message("Contact Successfully Added", "success"));

			System.out.println("DATA" + contact);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.setAttribute("message", new Message("Something went wrong !!", "danger"));
		}

		return "normal/addContact";
	}

	@GetMapping("/showContacts/{page}")
	public String showcontact(@PathVariable("page") Integer page, Model model, Principal principal) {
		model.addAttribute("title", "Show Contacts");
		// get data of logged in user
		String userName = principal.getName();
		User user = userRepository.getByName(userName);
		Pageable pageable = PageRequest.of(page, 5);

		Page<Contact> Contactlist = contactRepository.findcontactByUser(user.getId(), pageable);
		model.addAttribute("contacts", Contactlist);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", Contactlist.getTotalPages());
		return "normal/showContacts";
	}

	@RequestMapping("/contact_Details/{contactid}")
	public String showDeatails(@PathVariable("contactid") Integer contactid, Model model, Principal principal) {

		// Retrieve an entity by its id. (contactid)
		Optional<Contact> contactoptional = contactRepository.findById(contactid);
		// get all values
		Contact contact = contactoptional.get();

		// implementing security so that person A cannot see contact details of person B
		String name = principal.getName();
		User user = userRepository.getByName(name);

		if (user.getId() == contact.getUser().getId()) {
			// to send data to view
			model.addAttribute("contact", contact);
			// to display contact name on tab (title)
			model.addAttribute("title", contact.getNickname());

		}

		return "normal/contact_Details";

	}

	@RequestMapping("/delete/{contactid}")
	public String deleteContact(@PathVariable("contactid") Integer cID, Principal principal, Model model,
			HttpSession session) {
		// to search data in database
		Optional<Contact> contactoptional = contactRepository.findById(cID);
		// to retrieve and store data from database to contact type object
		Contact contact = contactoptional.get();

		// check to insure authorized user is deleting contact
		String name = principal.getName();
		User user = userRepository.getByName(name);
		if (user.getId() == contact.getUser().getId()) {

			contact.setUser(null);
			try {
				// deleting image of contact
				Contact oldcontact = this.contactRepository.findById(contact.getContactid()).get();
				File deletefile = new ClassPathResource("static/image").getFile();
				File file2 = new File(deletefile, oldcontact.getPhotourl());
				file2.delete();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this.contactRepository.delete(contact);
			session.setAttribute("message", new Message("Contact Deleted Sucessfully", "success"));

			// delete function is working fine in this application
			// but if data will not delete from database then follow bellow steps

			// to get specific user
			// User delete_user = this.userRepository.getByName(principal.getName());
			// to remove User(internally calls equals method from contact class)
			// delete_user.getContacts().remove(contact);
			// here we have to ovrride equals method in contacts entity

		}

		return "redirect:/user/showContacts/0";
	}

	// Open Update Contact Form
	@PostMapping("/update-form/{contactid}")
	public String updateContact(@PathVariable("contactid") Integer cid, Model model) {
		Contact contact = contactRepository.findById(cid).get();
		model.addAttribute("contact", contact);

		return "normal/updateContact";
	}

	// Update Contact Handler
	@PostMapping("/update-process/{contactid}")
	public String updateProcess(@ModelAttribute Contact contact, @RequestParam("imageFile") MultipartFile file,
			HttpSession session, Model model, Principal principal) {

		System.out.println(contact.getContactid());
		System.out.println(contact.getName());
		try {

			// old contact Details
			Contact oldcontact = this.contactRepository.findById(contact.getContactid()).get();
			if (!file.isEmpty()) {
				// file work

				// delete old image
				File deletefile = new ClassPathResource("static/image").getFile();
				File file2 = new File(deletefile, oldcontact.getPhotourl());
				file2.delete();

				// Update New Image
				// get destination file path
				File savefile = new ClassPathResource("static/image").getFile();
				System.out.println(savefile);
				// get absolue formated path to use
				Path path = Paths.get(savefile.getAbsolutePath() + File.separator + file.getOriginalFilename());

				// to copy file to destination Folder 1. file.getInputStream():to get input
				// image stream 2. path:target folder path 3.keep both files or replace
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				contact.setPhotourl(file.getOriginalFilename());

				session.setAttribute("message", new Message("Your contact is Updated", "success"));
				// rewrite
			} else {
				contact.setPhotourl(oldcontact.getPhotourl());
			}
			User user = this.userRepository.getByName(principal.getName());
			contact.setUser(user);
			this.contactRepository.save(contact);

		} catch (Exception e) {
			// TODO: handle exception
		}

		// return page with currosponding contactId
		return "redirect:/user/contact_Details/" + contact.getContactid();
	}

	// Profile Page Handler
	@RequestMapping("/profile")
	public String profilePage(Model model, Contact contact, User user) {
		model.addAttribute("title", "Profile");
		model.addAttribute("user", user);
		return "normal/profile";
	}

	@RequestMapping("/setting")
	public String setting() {
		return "normal/setting";
	}

	@PostMapping("/change-password")
	public String changePassword(@RequestParam("oldpassword") String oldpassword,
			@RequestParam("newPassword") String newpassword, Principal principal, HttpSession session) {
		String name = principal.getName();
		User user = userRepository.getByName(name);
		if (this.bCryptPasswordEncoder.matches(oldpassword, user.getPassword())) {
			user.setPassword(bCryptPasswordEncoder.encode(newpassword));
			userRepository.save(user);
			session.setAttribute("message", new Message("Password changed successfully", "success"));
		} else {
			session.setAttribute("message", new Message("PLease Enter correct old password", "danger"));
			return "redirect:/user/setting";
		}

		return "redirect:/user/index";
	}

}
