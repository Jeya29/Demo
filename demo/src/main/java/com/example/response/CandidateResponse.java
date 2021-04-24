package com.example.response;


import java.util.ArrayList;
import java.util.List;

import com.example.entity.Candidate;
import com.example.entity.Technology;



public class CandidateResponse {
	
	
	//@JsonIgnore
	private int id;
	
	//@JsonProperty("firstName")
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String street;
	
	private String city;
	
	private List<TechResponse> technologies;
	

	
	public int getId() {
		return id;
	}


	public void setId(int id) {
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
	


	public List<TechResponse> getTechnologies() {
		return technologies;
	}


	public void setTechnologies(List<TechResponse> technologies) {
		this.technologies = technologies;
	}
	

	@Override
	public String toString() {
		return "CandidateResponse [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", street=" + street + ", city=" + city + ", technologies=" + technologies + "]";
	}


	public CandidateResponse(Candidate candidate) {
		this.id = candidate.getId();
		this.firstName = candidate.getfirstName();
		this.lastName = candidate.getlastName();
		this.email = candidate.getEmail();
		
		this.street = candidate.getAddress().getStreet();
		this.city = candidate.getAddress().getCity();
		
		if(candidate.getTechnologies()!=null) {
			
			technologies = new ArrayList<TechResponse>();
			
			for(Technology technology : candidate.getTechnologies()) {
				
				technologies.add(new TechResponse(technology));
				
			}
		}
	}
	
}
