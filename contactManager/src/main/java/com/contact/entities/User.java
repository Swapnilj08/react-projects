package com.contact.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	// @Positive
	private int age;
	private String role;
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
	private String email;
	// @Pattern(regexp =
	// "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",message
	// = "PLease Fill Password as per rules")
	private String password;
	@NotBlank(message = "Name field should not be empty")
	@Size(min = 3, message = "Name should be more than 3 character")
	private String name;
	@Column(length = 5000)
	private String descriptionn;
	private boolean activity;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	List<Contact> contacts = new ArrayList<Contact>();

	public User() {
		super();
	}

	public User(int id, int age, String role, String email, String password, String name, String descriptionn,
			boolean activity, List<Contact> contacts) {
		super();
		this.id = id;
		this.age = age;
		this.role = role;
		this.email = email;
		this.password = password;
		this.name = name;
		this.descriptionn = descriptionn;
		this.activity = activity;
		this.contacts = contacts;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescriptionn() {
		return descriptionn;
	}

	public void setDescriptionn(String descriptionn) {
		this.descriptionn = descriptionn;
	}

	public boolean isActivity() {
		return activity;
	}

	public void setActivity(boolean activity) {
		this.activity = activity;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", age=" + age + ", role=" + role + ", email=" + email + ", password=" + password
				+ ", name=" + name + ", descriptionn=" + descriptionn + ", activity=" + activity + "]";
	}

}
