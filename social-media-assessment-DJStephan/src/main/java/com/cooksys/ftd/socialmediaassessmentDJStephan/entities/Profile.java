package com.cooksys.ftd.socialmediaassessmentDJStephan.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class Profile {
	
	@Id
	@Column(name = "profile_id")
	private Long id;
	
	@Column
	private String firstName;
	
	@Column
	private String lastName;
	
	@Column(nullable = false) 
	private String email;
	
	@Column
	private String phone;
	
	@MapsId
	@OneToOne(mappedBy = "profile")
	@JoinColumn(name = "profile_id")
	private User user;
	
	public Profile() {}
	
	@Autowired
	public Profile(User user, String email, String firstName, String lastName, String phone) {
		this.email = email;
		this.user = user;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	

}
