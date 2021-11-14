package com.uhn.patientmanagementservice.entity;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Patient {
	
	@Id
	private Long id;
	private String firstName;
	private String lastName;
	private Date dob;
	
	protected Patient() {

	}
	
	public Patient(Long id, String firstName, String lastName, Date dob) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
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
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	@Override
	public String toString() {
		return "Patient [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob + "]";
	}


}
