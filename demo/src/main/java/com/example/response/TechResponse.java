package com.example.response;

import com.example.entity.Technology;

public class TechResponse {

	
	private int id;
	
	private String tech;
	
	private String experience;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTech() {
		return tech;
	}

	public void setTech(String tech) {
		this.tech = tech;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}
	
	
	@Override
	public String toString() {
		return "TechResponse [id=" + id + ", tech=" + tech + ", experience=" + experience + "]";
	}

	public TechResponse (Technology technology){
		
		this.id = technology.getId();
		this.tech = technology.getTech();
		this.experience = technology.getExperience();
	}
	
}
