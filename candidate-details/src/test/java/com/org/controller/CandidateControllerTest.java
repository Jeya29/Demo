package com.org.controller;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.candidate.CandidateApplication;
import com.org.entity.Address;
import com.org.entity.Candidate;
import com.org.service.CandidateService;

/**
 * @author jeyalakshmi
 *
 */
@SpringBootTest(classes=CandidateApplication.class)
@WebAppConfiguration
@ExtendWith(MockitoExtension.class)
public class CandidateControllerTest {
	
	
	private MockMvc mockMvc;
	
	@InjectMocks
	private CandidateController controller;
	
	@MockBean
	private CandidateService candidateService;
	
	private static ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void testGetAllCandidates() throws Exception {
		
		List<Candidate> candidates = Stream.of(new Candidate(1,"Sid","Sriram","sid@gmail.com",". net","3 years",
				new Address(1, "3, Bourke street","Melbourne", "3057", "Australia")),
		new Candidate(2,"Edward","Stewart","edward@gmail.com","Python","2 years",
				new Address(2,"5, Kings street","Sydney","2056","Australia")))
				.collect(Collectors.toList());

		when(candidateService.getAllCandidates()).thenReturn(candidates);
	
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/candidates"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id",Matchers.is(candidates.get(0).getId())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName",Matchers.is(candidates.get(0).getFirstName())));
	
	}

	@Test
	void testCreateCandidate() throws Exception {

		Address address = new Address(1,"18,Collins street","Melbourne","3035","Australia");
		
		Candidate candidate = new Candidate(1,"Alex","Michael","alex@gmail.com","Java","3 years",address);
		
		when(candidateService.createCandidate(candidate)).thenReturn(candidate);
		
		String input = mapper.writeValueAsString(candidate);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/candidates").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
		.content(input).accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
	}

	@Test
	void testGetCandidateById() throws Exception {
			
		Address address = new Address(1,"17,Lonsdale street","Melbourne","3034","Australia");
		
		Candidate candidate = new Candidate(1,"Catherine","Tresa","catherine@gmail.com",".net","2 years",address);
		
		when(candidateService.getCandidateById(1)).thenReturn(Optional.of(candidate));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/candidates/1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is(candidate.getFirstName())));
		
	}

	@Test
	void testUpdateCandidate() throws Exception {

		Address updateAddress = new Address(1,"4, Bourke street","Sydney","2034","Australia");
		
		Candidate updateReq = new Candidate(1,"Jack","Sparrow","jack@gmail.com","Java","2 years",updateAddress);
		
		when(candidateService.updateCandidate(1, updateReq)).thenReturn(updateReq);
		
		String input = mapper.writeValueAsString(updateReq);
				
				
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/candidates").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(input).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	
	}

	@Test
	void testDeleteCandidate() throws Exception {

		String status = "Candidate deleted successfully";
	
		when(candidateService.deleteCandidate(1)).thenReturn(status);
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/v1/candidates/1"))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	
	}

	@Test
	void testGetByFirstName() throws Exception {
		
		List<Candidate> candidates = Stream.of(new Candidate(1,"Jeya","Lakshmi","jeya@gmail.com", "Java", "4 years", 
				new Address(1, "6, Clowes street","Melbourne", "3062", "Australia")))
						.collect(Collectors.toList());
		
		when(candidateService.getByFirstName("Jeya")).thenReturn(candidates);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/candidates/first-name").param("firstName", "Jeya"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].id",Matchers.is(candidates.get(0).getId())))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName",Matchers.is(candidates.get(0).getFirstName())));
	
	}

	@Test
	void testGetByFirstNameAndLastName() throws Exception {

		Candidate candidate = new Candidate(1,"Sam","Anderson","sam@gmail.com",".net","3 years",
				new Address(1,"12, Spencer street","Melbourne","3056","Australia"));
		
		when(candidateService.getByFirstNameAndLastName("Sam", "Anderson")).thenReturn(candidate);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/candidates/Sam/and/Anderson"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.id",Matchers.is(candidate.getId())))
		.andExpect(MockMvcResultMatchers.jsonPath("$.firstName",Matchers.is(candidate.getFirstName())));
	
	}

	@Test
	void testGetByFirstNameOrLastName() throws Exception {

		List<Candidate> candidates = Stream.of(new Candidate(1,"John","William","john@gmail.com", "Python", "3 years", 
				new Address(1, "3, Primrose street","Melbourne", "3062", "Australia")),
				new Candidate(2,"Harry","Louis","harry@gmail.com","Java","2 years",
				new Address(2,"5, Kings street","Sydney","2056","Australia")))
		.collect(Collectors.toList());
		
		when(candidateService.getByFirstNameOrLastName("Harry", "William")).thenReturn(candidates);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/candidates/Harry/or/William"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].id",Matchers.is(candidates.get(0).getId())))
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName",Matchers.is(candidates.get(1).getLastName())));
	
	}

	@Test
	void testGetAllByPagination() throws Exception {

		List<Candidate> candidates = Stream.of(new Candidate(1,"Sid","Sriram","sid@gmail.com",". net","3 years",
				new Address(1, "3, Bourke street","Melbourne", "3057", "Australia")),
		new Candidate(2,"Edward","Stewart","edward@gmail.com","Python","2 years",
				new Address(2,"5, Kings street","Sydney","2056","Australia")))
				.collect(Collectors.toList());
		
		when(candidateService.getAllByPagination(1, 2)).thenReturn(candidates);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/candidates/paging").param("pageNo", "1").param("pageSize","2"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].id",Matchers.is(candidates.get(0).getId())))
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].email",Matchers.is(candidates.get(1).getEmail())));
	
	}

	@Test
	void testGetAllCandidatesSorted() throws Exception {

		List<Candidate> candidates = Stream.of(new Candidate(1, "Abbie","Elliot", "abbie@gmail.com","Python","2 years",
				new Address(1,"12, Queen street","Sydney","2054","Australia")),
				new Candidate(2, "Erika","Andrews", "erika@gmail.com","Java","1 year",
				new Address(2,"12, William street","Melbourne","3096","Australia")))
					.collect(Collectors.toList());
	
		when(candidateService.getAllCandidatesSorted()).thenReturn(candidates);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/candidates/sort"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].id",Matchers.is(candidates.get(0).getId())))
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName",Matchers.is(candidates.get(1).getFirstName())));
	
	}

	@Test
	void testUpdateFirstNameById() throws Exception {

		when(candidateService.updateFirstNameById(1, "Alex")).thenReturn(1);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/v1/candidates/1/Alex"))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		
	}

	@Test
	void testGetCandidatesByCity() throws Exception {

		List<Candidate> candidates = Stream.of(new Candidate(1, "Selena","Gomez", "selena@gmail.com", ".net","3 years",
				new Address(1,"29, Clowes street","Melbourne","3089","Australia")),
				new Candidate(2, "Taylor","Swift", "taylor@gmail.com", "Python","4 years",
				new Address(2,"13, Collins street","Melbourne","3067","Australia")))
					.collect(Collectors.toList());
		
		when(candidateService.getCandidatesByCity("Melbourne")).thenReturn(candidates);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/candidates/city").param("city", "Melbourne"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].id",Matchers.is(1)))
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].id",Matchers.is(2)));
	
	
	}
	
	
	

}
