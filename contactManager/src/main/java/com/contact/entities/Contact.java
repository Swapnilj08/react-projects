package com.contact.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Contact")
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int contactid;
	private String name;
	private String mobileno;
	private String address;
	private String pincode;
	private String photourl;
	private String nickname;
	private String relation;
	private String email;
	
	@ManyToOne
	@JsonIgnore
	private User user;
	
	public Contact(int contactid, String name, String mobileno, String address, String pincode, String photourl,
			String nickname, String relation, String email, User user) {
		super();
		this.contactid = contactid;
		this.name = name;
		this.mobileno = mobileno;
		this.address = address;
		this.pincode = pincode;
		this.photourl = photourl;
		this.nickname = nickname;
		this.relation = relation;
		this.email = email;
		this.user = user;
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

	

	
	
	public Contact() {
		super();
	}



	public int getContactid() {
		return contactid;
	}

	public void setContactid(int contactid) {
		this.contactid = contactid;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getPhotourl() {
		return photourl;
	}

	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	@Override
	public String toString() {
		return "Contact [contactid=" + contactid + ", name=" + name + ", mobileno=" + mobileno + ", address=" + address
				+ ", pincode=" + pincode + ", photourl=" + photourl + ", nickname=" + nickname + ", relation="
				+ relation + ", email=" + email + ", user=" + user + "]";
	}
	/*
	 * @Override public boolean equals(Object obj) {
	 * 
	 * return this.contactid==((Contact)obj).getContactid(); }
	 */

	
	
	
	
}
