/**
 * 
 */
package com.org.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.org.entity.Address;
import com.org.entity.Candidate;
import com.org.repository.AddressRepository;
import com.org.repository.CandidateRepository;

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
	
	@Mock
	private AddressRepository addressRepo;
	
	
	
	/**
	 * Test method for creating candidate.
	 */
	@Test
	public void testCreateCandidate() {
	
		Address address = new Address(1,"18,Collins street","Melbourne","3035","Australia");
		
		Candidate candidate = new Candidate(1,"Alex","Michael","alex@gmail.com","Java","3 years",address);
		
		when(addressRepo.save(address)).thenReturn(address);
		
		when(candidateRepo.save(candidate)).thenReturn(candidate);
		
		Candidate candidateCreated = candidateService.createCandidate(candidate);
		
		assertEquals(candidate, candidateCreated);
		
		verify(candidateRepo,timeout(1)).save(candidate);
		
		
	}	

	/**
	 * Test method for fetching all the candidates list.
	 */
	@Test
	public void testGetAllCandidates() {
		
		
		List<Candidate> candidates = Stream.of(new Candidate(1,"Sid","Sriram","sid@gmail.com",". net","3 years",
													new Address(1, "3, Bourke street","Melbourne", "3057", "Australia")),
											new Candidate(2,"Edward","Stewart","edward@gmail.com","Python","2 years",
													new Address(2,"5, Kings street","Sydney","2056","Australia")))
										.collect(Collectors.toList());
		
		when(candidateRepo.findAll()).thenReturn(candidates);
		
		
		List<Candidate> candidateActual = candidateService.getAllCandidates();

		assertEquals(2, candidateActual.size());
		assertEquals(candidates.get(0).getFirstName(), candidateActual.get(0).getFirstName());
		
		verify(candidateRepo).findAll();
		
	}
	
	
	/**
	 * Test method for fetching the candidate by id.
	 */
	@Test
	public void testGetCandidateById() {
		
		Address address = new Address(1,"17,Lonsdale street","Melbourne","3034","Australia");
		
		Candidate candidate = new Candidate(1,"Catherine","Tresa","catherine@gmail.com",".net","2 years",address);
		
		when(candidateRepo.findById(1)).thenReturn(Optional.of(candidate));
		
		Optional<Candidate> candidateActual = candidateService.getCandidateById(1);
		
		assertEquals(Optional.of(candidate), candidateActual);
		
		verify(candidateRepo,times(1)).findById(1);
		
	}


	/**
	 * Test method for fetching candidates by firstName
	 */
	@Test
	public void testGetByFirstName() {

			
			List<Candidate> candidates = Stream.of(new Candidate(1,"Jeya","Lakshmi","jeya@gmail.com", "Java", "4 years", 
													new Address(1, "6, Clowes street","Melbourne", "3062", "Australia")))
										.collect(Collectors.toList());
			
			when(candidateRepo.findByFirstName("Jeya")).thenReturn(candidates);
			
			List<Candidate> candidatesActual = candidateService.getByFirstName("Jeya");
			
			assertEquals(candidates , candidatesActual);
			
			verify(candidateRepo).findByFirstName("Jeya");
			
	}



	/**
	 * Test method for fetching candidates by firstname or lastname
	 */
	@Test
	void testGetByFirstNameOrLastName() {

	
		List<Candidate> candidates = Stream.of(new Candidate(1,"John","William","john@gmail.com", "Python", "3 years", 
												new Address(1, "3, Primrose street","Melbourne", "3062", "Australia")),
												new Candidate(2,"Harry","Louis","harry@gmail.com","Java","2 years",
												new Address(2,"5, Kings street","Sydney","2056","Australia")))
										.collect(Collectors.toList());
		
		when(candidateRepo.findByFirstNameOrLastName("Harry", "William")).thenReturn(candidates);	
		
		List<Candidate> candidatesActual = candidateService.getByFirstNameOrLastName("Harry", "William");
		
		assertEquals(candidates , candidatesActual);
		
		verify(candidateRepo).findByFirstNameOrLastName("Harry", "William");
	
	}
	
	/**
	 * Test method for updating candidate.
	 */
	@Test
	public void testUpdateCandidate() {
		
		Address address = new Address(1,"4, Bourke street","Sydney","2034","Australia");
	
		Candidate candidate = new Candidate(1,"Jack","Sparrow","jack@gmail.com","Java","2 years",address);
	
		when(candidateRepo.findById(1)).thenReturn(Optional.of(candidate));
		
		when(addressRepo.findById(1)).thenReturn(Optional.of(address));
		
		when(addressRepo.save(address)).thenReturn(address);
		
		
		when(candidateRepo.save(candidate)).thenReturn(candidate);
		
		
		Candidate candidateActual = candidateService.updateCandidate(1,candidate);
		
		assertEquals(candidate, candidateActual);
		
		verify(candidateRepo,times(1)).save(candidate);
		
	}
	
	/**
	 * Test method for deleting candidate.
	 */
	@Test
	public void testDeleteCandidate() {
	
		
		candidateService.deleteCandidate(1);
		
		verify(candidateRepo,times(1)).deleteById(1);
		
		
	}
	
	/**
	 * Test method for deleting candidate.
	 */
	@Test
	public void testGetByFirstNameAndLastName() {
	
		Candidate candidate = new Candidate(1,"Sam","Anderson","sam@gmail.com",".net","3 years",
								new Address(1,"12, Spencer street","Melbourne","3056","Australia"));
		
		when(candidateRepo.getByFirstAndLastName("Sam", "Anderson")).thenReturn(candidate);
		
		Candidate candidateActual = candidateService.getByFirstNameAndLastName("Sam", "Anderson");
		
		assertEquals(candidate.getFirstName(),candidateActual.getFirstName());
		assertEquals(candidate.getLastName(),candidateActual.getLastName());
		
		verify(candidateRepo,times(1)).getByFirstAndLastName("Sam", "Anderson");
		
		
	}
	
	
	
	/**
	 * Test method for sorting candidates.
	 */
	@Test
	public void testGetAllCandidatesSorted() {
		
		Sort sort = Sort.by(Direction.ASC, "firstName");
		
		List<Candidate> candidates = Stream.of(new Candidate(1, "Erika","Andrews", "erika@gmail.com","Python","2 years",
													new Address(1,"12, Queen street","Sydney","2054","Australia")),
											new Candidate(2, "Abbie","Elliot", "abbie@gmail.com","Java","1 year",
													new Address(2,"12, William street","Melbourne","3096","Australia")))
									.collect(Collectors.toList());
		
		when(candidateRepo.findAll(sort)).thenReturn(candidates);
	
		List<Candidate> candidatesSorted = candidateService.getAllCandidatesSorted();
		
		assertEquals(candidates, candidatesSorted);
		
		verify(candidateRepo,times(1)).findAll(sort);
		
	
	}
	
	/**
	 * Test method for updating firstname of the candidate by id.
	 */
	@Test
	public void testUpdateFirstNameById() {
	
	
		
		when(candidateRepo.updateFirstNameById(1, "Christina")).thenReturn(1);
		
		
		Integer candidateActual = candidateService.updateFirstNameById(1, "Christina");
		
		
		assertEquals(1 , candidateActual);
		
		verify(candidateRepo,times(1)).updateFirstNameById(1, "Christina");
		
	}
	
	
	
	/**
	 * Test method for fetching candidates by city.
	 */
	@Test
	public void testGetCandidatesByCity() {
	
		List<Candidate> candidates = Stream.of(new Candidate(1, "Selena","Gomez", "selena@gmail.com", ".net","3 years",
													new Address(1,"29, Clowes street","Melbourne","3089","Australia")),
												new Candidate(2, "Taylor","Swift", "taylor@gmail.com", "Python","4 years",
													new Address(2,"13, Collins street","Melbourne","3067","Australia")))
									.collect(Collectors.toList());
	
	
		
		when(candidateRepo.getByAddressCity("Melbourne")).thenReturn(candidates);
		
		
		List<Candidate> candidateActual = candidateService.getCandidatesByCity("Melbourne");
		
		
		assertEquals(candidates , candidateActual);
		
		verify(candidateRepo,times(1)).getByAddressCity("Melbourne");
		
	}
	
	
	
	
	
	
	
	
	

	
}
