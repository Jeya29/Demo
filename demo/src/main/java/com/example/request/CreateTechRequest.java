package com.example.request;

public class CreateTechRequest {

	private String tech;
	
	private String experience;

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
		return "CreateTechRequest [tech=" + tech + ", experience=" + experience + "]";
	}
	
	
	
}
