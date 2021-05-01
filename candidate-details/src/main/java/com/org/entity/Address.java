package com.org.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
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
@Table(name = "address")
@ToString
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
	@JsonIgnore
	private int id;
	
	@NotNull @NotEmpty(message = "Valid address required")
	@Column(name = "address")
	private String address;
	
	@NotNull @NotEmpty(message = "City required")
	@Column(name = "city")
	private String city;
	
	@NotNull @NotEmpty(message = "Postcode required")
	@Column(name = "postcode")
	private String postcode;
	
	@NotNull @NotEmpty(message = "Country required")
	@Column(name = "country")
	private String country;
	
	

}
