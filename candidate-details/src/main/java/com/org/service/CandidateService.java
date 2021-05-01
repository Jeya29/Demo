package com.org.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.org.entity.Address;
import com.org.entity.Candidate;
import com.org.repository.AddressRepository;
import com.org.repository.CandidateRepository;


@Service
public class CandidateService {
	
	
	@Autowired
	CandidateRepository candidateRepo;
	
	@Autowired
	AddressRepository addressRepo;
	
	
	
	public List<Candidate> getAllCandidates(){
		
		
		return candidateRepo.findAll();
		
		
	}
	
	public Candidate createCandidate(Candidate candidate) {
		
		
		 addressRepo.save(candidate.getAddress());
		 
		 candidate = candidateRepo.save(candidate);
		 
		return candidate;
		
	}
	
	public Candidate updateCandidate(int id , Candidate updateReq) {
		
		
       Candidate candidate = candidateRepo.findById(id).get();
       
       Address address = addressRepo.findById(candidate.getAddress().getId()).get();
       
       address.setAddress(updateReq.getAddress().getAddress());
       address.setCity(updateReq.getAddress().getCity());
       address.setPostcode(updateReq.getAddress().getPostcode());
       address.setCountry(updateReq.getAddress().getCountry());
       
       address = addressRepo.save(address);
       
       candidate.setFirstName(updateReq.getFirstName());
       candidate.setLastName(updateReq.getLastName());
       candidate.setEmail(updateReq.getEmail());
       candidate.setTechnology(updateReq.getTechnology());
       candidate.setExperience(updateReq.getExperience());
       candidate.setAddress(address);

		
       Candidate candidateUpdated = candidateRepo.save(candidate);
       
		
		return candidateUpdated;		
		
	}
	

	public String deleteCandidate(int id) {
		
		
		candidateRepo.deleteById(id);
		
		
		return "Candidate deleted successfully";
		
	}

	public List<Candidate> getByFirstName(String firstName) {
		
	 
		
		return candidateRepo.findByFirstName(firstName);
	}

	public Candidate getByFirstNameAndLastName(String firstName, String lastName) {
		
		
		/* JPQL */
		
		return candidateRepo.getByFirstAndLastName(firstName,lastName);
	}

	public List<Candidate> getByFirstNameOrLastName(String firstName, String lastName) {

		
		return candidateRepo.findByFirstNameOrLastName(firstName, lastName);
		
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


	public List<Candidate> getCandidatesByCity(String city) {
		
		return candidateRepo.getByAddressCity(city);
	}

	public Optional<Candidate> getCandidateById(int id) {
		
		
		return candidateRepo.findById(id);
	}

}
