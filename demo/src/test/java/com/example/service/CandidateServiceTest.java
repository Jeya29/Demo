/**
 * 
 */
package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.entity.Candidate;
import com.example.repository.CandidateRepository;
import com.example.request.CreateCandidateRequest;
import com.example.request.CreateTechRequest;

/**
 * @author Jeyalakshmi
 *
 */

@ExtendWith(MockitoExtension.class)
class CandidateServiceTest {
	
	@InjectMocks
	private CandidateService candidateService;
	
	@Mock
	private CandidateRepository candidateRepo;
	

	@BeforeEach
	void init() {
	
	}
	

	/**
	 * Test method for fetching all the candidates list.
	 */
	@Test
	public void testGetAllCandidates() {
		
		CreateTechRequest techReq1 = new CreateTechRequest();
		techReq1.setTech("Java");
		techReq1.setExperience("4 years");
		List<CreateTechRequest> technologies1 = new ArrayList<CreateTechRequest>();
		technologies1.add(techReq1);
		
		CreateCandidateRequest createReq1 = new CreateCandidateRequest();
		createReq1.setFirstName("Sid");
		createReq1.setLastName("Sriram");
		createReq1.setEmail("sid@gmail.com");
		createReq1.setStreet("Bourke street");
		createReq1.setCity("Melbourne");
		createReq1.setTechnologies(technologies1);
		
		Candidate candidate1 = new Candidate(createReq1);
		
		CreateTechRequest techReq2 = new CreateTechRequest();
		techReq2.setTech("Python");
		techReq2.setExperience("2 years");
		List<CreateTechRequest> technologies2 = new ArrayList<CreateTechRequest>();
		technologies2.add(techReq2);
		
		CreateCandidateRequest createReq2 = new CreateCandidateRequest();
		createReq2.setFirstName("Edward");
		createReq2.setLastName("Stewart");
		createReq2.setEmail("edward@gmail.com");
		createReq2.setStreet("Kings street");
		createReq2.setCity("Sydney");
		createReq2.setTechnologies(technologies2);
		
		Candidate candidate2 = new Candidate(createReq1);
		
		List<Candidate> candidates = new ArrayList<Candidate>();
		candidates.add(candidate1);
		candidates.add(candidate2);
		
		Mockito.when(candidateRepo.findAll()).thenReturn(candidates);
		
		List<Candidate> candidateActual = candidateService.getAllCandidates();

		assertEquals(candidates, candidateActual);
		
		Mockito.verify(candidateRepo).findAll();
		
	}

		


	/**
	 * Test method for fetching candidates by firstname
	 */
	@Test
	public void testGetByFirstName() {

			Candidate candidate = new Candidate();
			candidate.setId(1);
			candidate.setfirstName("Jeya");
			candidate.setlastName("Lakshmi");
			candidate.setEmail("jeya@gmail.com");
			
			List<Candidate> candidates = new ArrayList<Candidate>();
			candidates.add(candidate);
			
			Mockito.when(candidateRepo.findByFirstName("Jeya")).thenReturn(candidates);	
			
			List<Candidate> candidatesActual = candidateService.getByFirstName("Jeya");
			
			assertEquals(candidates , candidatesActual);
			
			Mockito.verify(candidateRepo).findByFirstName("Jeya");
			
	}



	/**
	 * Test method for fetching candidates by firstname or lastname
	 */
	@Test
	void testGetByFirstNameOrLastName() {

		Candidate candidate = new Candidate();
		candidate.setId(1);
		candidate.setfirstName("Jeya");
		candidate.setlastName("Lakshmi");
		candidate.setEmail("jeya@gmail.com");
		
		List<Candidate> candidates = new ArrayList<Candidate>();
		candidates.add(candidate);
		
		Mockito.when(candidateRepo.findByFirstNameOrLastName("Jeya", "Lakshmi")).thenReturn(candidates);	
		
		List<Candidate> candidatesActual = candidateService.getByFirstNameOrLastName("Jeya", "Lakshmi");
		
		assertEquals(candidates , candidatesActual);
		
		Mockito.verify(candidateRepo).findByFirstNameOrLastName("Jeya", "Lakshmi");
	
	}


	
}
