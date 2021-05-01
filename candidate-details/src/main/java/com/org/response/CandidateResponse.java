package com.org.response;



import com.org.entity.Address;
import com.org.entity.Candidate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CandidateResponse {
	
	
	private int id;
	
	//@JsonProperty("firstName")
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String technology;
	
	private String experience;
	
	private Address address;
	
	

	
	public CandidateResponse(Candidate candidate) {
		this.id = candidate.getId();
		this.firstName = candidate.getFirstName();
		this.lastName = candidate.getLastName();
		this.email = candidate.getEmail();
		this.technology = candidate.getTechnology();
		this.experience = candidate.getExperience();
		
		this.address = candidate.getAddress();
		
	}
	
}
