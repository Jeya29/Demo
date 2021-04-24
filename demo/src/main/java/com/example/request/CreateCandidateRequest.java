package com.example.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

public class CreateCandidateRequest {
	
	@NotBlank(message = "First Name is required!")
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String street;
	
	private String city;
	
	private List<CreateTechRequest> technologies;
	
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
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public List<CreateTechRequest> getTechnologies() {
		return technologies;
	}
	public void setTechnologies(List<CreateTechRequest> technologies) {
		this.technologies = technologies;
	}
	@Override
	public String toString() {
		return "CreateCandidateRequest [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", street=" + street + ", city=" + city + ", technologies=" + technologies + "]";
	}
	
	

}
