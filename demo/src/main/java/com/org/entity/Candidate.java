package com.org.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "candidate")
public class Candidate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="candidate_id")
	@JsonIgnore
	private int id;
	
	@NotNull @NotEmpty(message = "Candidate's Firstname required")
	@Column(name="first_name")
	private String firstName;
	
	@NotNull @NotEmpty(message = "Candidate's Lastname required")
	@Column(name="last_name")
	private String lastName;
	
	@NotNull @NotEmpty(message = "Candidate's email id required")
	@Column(name="email")
	private String email;

	@NotNull @NotEmpty(message = "Candidate's technology required")
	@Column(name="technology")
	private String technology;
	
	@NotNull @NotEmpty(message = "Candidate's experience required")
	@Column(name="experience")
	private String experience;
	
	@NotNull (message = "Candidate's address required")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address address;
	


	
}
