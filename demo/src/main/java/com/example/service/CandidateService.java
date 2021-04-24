package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.entity.Address;
import com.example.entity.Candidate;
import com.example.entity.Technology;
import com.example.repository.AddressRepository;
import com.example.repository.CandidateRepository;
import com.example.repository.TechRepository;
import com.example.request.CreateCandidateRequest;
import com.example.request.CreateTechRequest;
import com.example.request.InQueryRequest;
import com.example.request.UpdateCandidateRequest;

import jdk.internal.org.jline.utils.Log;

@Service
public class CandidateService {
	
	Logger log = LoggerFactory.getLogger(CandidateService.class);
	
	
	@Autowired
	CandidateRepository candidateRepo;
	
	@Autowired
	AddressRepository addressRepo;
	
	@Autowired
	TechRepository techRepo;
	
	
	
	public List<Candidate> getAllCandidates(){
		
		
		return candidateRepo.findAll();
		
		
	}
	
	public Candidate createCandidate(CreateCandidateRequest createRequest) {
		
		/*Candidate candidate = new Candidate(createRequest);
		
 		candidateRepo.save(candidate); */
		
		/*OneToOne*/
		
		Address address = new Address();
		address.setStreet(createRequest.getStreet());
		address.setCity(createRequest.getCity());
		
		address = addressRepo.save(address);
		
		Candidate candidate = new Candidate(createRequest);
		
		candidate.setAddress(address);
		
		/*OneToMany*/
		
		List<Technology> technologies = new ArrayList<Technology>();
		
		if(createRequest.getTechnologies()!=null) {
		for(CreateTechRequest techRequests: createRequest.getTechnologies()) {
			Technology technology = new Technology();
			technology.setTech(techRequests.getTech());
			technology.setExperience(techRequests.getExperience());
			technology.setCandidate(candidate);
			
			technologies.add(technology);		
			
			}
		
		}
		 candidate.setTechnologies(technologies);
		 
		 candidate = candidateRepo.save(candidate);
		
		techRepo.saveAll(technologies);
		
	   
		
		return candidate;
		
	}
	
	public Candidate updateCandidate(UpdateCandidateRequest updateReq) {
		
		
       Candidate candidate = candidateRepo.findById(updateReq.getId()).get();
       
       if(updateReq.getFirstName()!=null && !updateReq.getFirstName().isEmpty()) {
    	   
    	   candidate.setfirstName(updateReq.getFirstName());
    	   
       } else if(updateReq.getLastName()!=null && !updateReq.getLastName().isEmpty()) {
    	   
	    	   candidate.setlastName(updateReq.getLastName());
	    	   
	       }else if(updateReq.getEmail()!=null && !updateReq.getEmail().isEmpty()) {
	    	   
	    	   candidate.setEmail(updateReq.getEmail());
	    	   
	       }else {
	    	   
	    	   return candidate;
	       }
		
       candidate = candidateRepo.save(candidate);
       
		
		return candidate;		
		
	}
	
	public String deleteCandidate(int id) {
		
		
		candidateRepo.deleteById(id);
		
		
		return "Candidate deleted successfully";
		
	}

	public List<Candidate> getByFirstName(String firstName) {
		
	 
		
		return candidateRepo.findByFirstName(firstName);
	}

	public Candidate getByFirstNameAndLastName(String firstName, String lastName) {
		
		
		//candidateRepo.findByFirstNameAndLastName(firstName, lastName);
		/* JPQL */
		return candidateRepo.getByFirstAndLastName(firstName,lastName);
	}

	public List<Candidate> getByFirstNameOrLastName(String firstName, String lastName) {

		
		return candidateRepo.findByFirstNameOrLastName(firstName, lastName);
		
	}

	public List<Candidate> getByFirstNameIn(InQueryRequest inQuery) {
		
		return candidateRepo.findByFirstNameIn(inQuery.getFirstNames());
	}

	public List<Candidate> getAllByPagination(int pageNo, int pageSize) {
		
		Pageable paging = PageRequest.of(pageNo-1, pageSize);
		
		return candidateRepo.findAll(paging).getContent();
	}

	public List<Candidate> getAllCandidatesSorted() {
		
		Sort sort = Sort.by(Direction.ASC, "firstName");		
		
		return candidateRepo.findAll(sort);
	}

	public Integer updateFirstNameById(int id, String firstName) {

		return candidateRepo.updateFirstNameById(id , firstName);
		
	}

	public Integer deleteByFirstName(String firstName) {
		
		return candidateRepo.deleteByFirstName(firstName);
	}

	public List<Candidate> getCandidatesByCity(String city) {
		
		//return candidateRepo.findByAddressCity(city);
		return candidateRepo.getByAddressCity(city);
	}

}
