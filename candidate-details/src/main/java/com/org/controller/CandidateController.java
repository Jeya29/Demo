package com.org.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.org.aop.TrackExecutionTime;
import com.org.entity.Candidate;
import com.org.exception.ResourceNotFoundException;
import com.org.response.CandidateResponse;
import com.org.service.CandidateService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
public class CandidateController{
	
	@Autowired
	CandidateService candidateService;


	@GetMapping("v1/candidates")
	@TrackExecutionTime
	public ResponseEntity<List<CandidateResponse>> getAllCandidates() throws Exception{
		
		log.info("----Get all the candidates list----");
		
		List<Candidate> getAllCandidates =  candidateService.getAllCandidates();
		
		List<CandidateResponse> candidateResponseList = new ArrayList<CandidateResponse>();
		
		if(!getAllCandidates.isEmpty()) {
			
			getAllCandidates.stream().forEach(candidate ->
			{
				candidateResponseList.add(new CandidateResponse(candidate));
				
			});		
			
		}else {
			
			throw new ResourceNotFoundException("Candidates List is Empty");
		
		}
		
		
		
		return ResponseEntity.ok().body(candidateResponseList);
	}
	
	@PostMapping("v1/candidates")
	@TrackExecutionTime
	public ResponseEntity<Candidate> createCandidate(@Valid @RequestBody Candidate candidate) throws HttpMessageNotReadableException,MethodArgumentNotValidException,Exception {
		
		log.info("-----Create Candidate----");
		
		Candidate candidateCreated = candidateService.createCandidate(candidate);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
									.buildAndExpand(candidateCreated).toUri();
		
		return ResponseEntity.created(location).body(candidateCreated);
		
		
	}
	
	@GetMapping("v1/candidates/{id}")
	@TrackExecutionTime
	public ResponseEntity<Candidate> getCandidateById (@PathVariable("id") int id )  {
		
		log.info("-----Get candidate By Id------");
		

		
		Candidate candidate = candidateService.getCandidateById(id).orElseThrow(()->
											new ResourceNotFoundException("Candidate id: " + id + " not found"));
		
		return ResponseEntity.ok().body(candidate);
		
	}
	
	@PutMapping("v1/candidates/{id}")
	@TrackExecutionTime
	public ResponseEntity<CandidateResponse> updateCandidate (@PathVariable("id") int id ,@Valid @RequestBody Candidate updateReq) throws HttpMessageNotReadableException,MethodArgumentNotValidException {
		
		log.info("-----Updating candidate details------");
		
		Candidate candidateUpdated = candidateService.updateCandidate(id,updateReq);
		
		return ResponseEntity.ok().body(new CandidateResponse(candidateUpdated));
		
	}
	
	@DeleteMapping("v1/candidates/{id}")
	@TrackExecutionTime
	public ResponseEntity<String> deleteCandidate(@PathVariable int id) throws Exception {
		
		log.info("----Deletion initiated----");
		
		String status = candidateService.deleteCandidate(id);
		
		return ResponseEntity.ok().body(status);
		
	}
	
	@GetMapping("v1/candidates/first-name")
	@TrackExecutionTime
	public ResponseEntity<List<CandidateResponse>> getByFirstName (@RequestParam String firstName) throws Exception {
		
		
		log.info("-----Fetch candidate(s) by firstname-----");
		
		List<Candidate> candidates = new ArrayList<Candidate>();
		
		List<CandidateResponse> candResponse = new ArrayList<CandidateResponse>();
		
		candidates = candidateService.getByFirstName(firstName);
		
		
		if(!candidates.isEmpty()) {
			
			candidates.stream().forEach(candidate->
			{
				candResponse.add(new CandidateResponse(candidate));
				
			});
			
		}else {
			
			throw new ResourceNotFoundException("Firstname: " + firstName + " is not found");
			
		}
		
		
		
		return ResponseEntity.ok().body(candResponse);	
		
	}
	
	@GetMapping("v1/candidates/{first-name}/and/{last-name}")
	@TrackExecutionTime
	public ResponseEntity<CandidateResponse> getByFirstNameAndLastName(@PathVariable("first-name") String firstName,@PathVariable("last-name") String LastName) throws Exception {
		
		log.info("----Fetch candidate by firstname and lastname----");
		
		Candidate candidate = new Candidate();
		
		candidate = candidateService.getByFirstNameAndLastName(firstName,LastName);
		
		CandidateResponse candResponse = null;
		
		if(candidate!=null) {
			
			candResponse = new CandidateResponse(candidate);
			
			
		} else {
			
			throw new ResourceNotFoundException("Firstname: " + firstName + " and Lastname: " +LastName+ " not found");
			
		}
		
		
		return ResponseEntity.ok().body(candResponse);	
		
	}
	
	@GetMapping("v1/candidates/{first-name}/or/{last-name}")
	@TrackExecutionTime
	public ResponseEntity<List<CandidateResponse>> getByFirstNameOrLastName(@PathVariable("first-name") String firstName,@PathVariable("last-name") String LastName) throws Exception {
		
		log.info("------Fetch candidates by first or last names---");

		List<Candidate> candidates = candidateService.getByFirstNameOrLastName(firstName,LastName);
		
		List<CandidateResponse> candResponse = new ArrayList<CandidateResponse>();
		
		if(!candidates.isEmpty()) {
			
			candidates.stream().forEach(candidate ->
			{
				candResponse.add(new CandidateResponse(candidate));
			});	
			
		}else {
			
			throw new ResourceNotFoundException("Firstname: " + firstName + " or Lastname: " +LastName+ " not found");
			
		}
		
		return ResponseEntity.ok().body(candResponse);
		
	}
	
	
	
	@GetMapping("v1/candidates/paging")
	@TrackExecutionTime
	public ResponseEntity<List<CandidateResponse>> getAllByPagination(@RequestParam int pageNo, @RequestParam int pageSize) throws Exception{
		
		log.info("----Pagination initiated----");
		
		List<Candidate> candidates = candidateService.getAllByPagination(pageNo,pageSize);
		
		List<CandidateResponse> candResponse = new ArrayList<CandidateResponse>();
		
		if(!candidates.isEmpty()) {
			
			candidates.stream().forEach(candidate ->
			{
				candResponse.add(new CandidateResponse(candidate));
			});		
			
		} else{
			
			throw new ResourceNotFoundException("No Pages to display");
			
		}
		
		return ResponseEntity.ok().body(candResponse);
		
	}
	
	@GetMapping("v1/candidates/sort")
	@TrackExecutionTime
	public ResponseEntity<List<CandidateResponse>> getAllCandidatesSorted() throws Exception{
		
		log.info("----Sort candidates----");
		
		List<Candidate> candidates = candidateService.getAllCandidatesSorted();
		
		List<CandidateResponse> candResponse = new ArrayList<CandidateResponse>();
		
		if(!candidates.isEmpty()) {
			

			candidates.stream().forEach(candidate ->
			{
				candResponse.add(new CandidateResponse(candidate));
			});		
			
		}else {
			
			throw new ResourceNotFoundException("No data to sort");
			
		}
		
		return ResponseEntity.ok().body(candResponse);
		
	}
	
	@PutMapping("v1/candidates/{id}/{firstName}")
	@TrackExecutionTime
	public ResponseEntity<String> updateFirstNameById(@PathVariable int id, @PathVariable String firstName) throws Exception {
		
			log.info("-----Update candidate's firstname by id-----" );
		
				Integer updatedValue = candidateService.updateFirstNameById(id,firstName);
				
				String updatedCandidate;
				
				if(updatedValue!=0) {
					
					updatedCandidate = updatedValue + " candidate(s) updated";
					
				} else {
					
					throw new ResourceNotFoundException("Could not find id: " +id+ " to update");
					
				}
				
				return ResponseEntity.ok().body(updatedCandidate);
		
	}
	
	
	@GetMapping("v1/candidates/city")
	@TrackExecutionTime
	public ResponseEntity<List<CandidateResponse>> getCandidatesByCity (@RequestParam String city) throws Exception{
		
		log.info("------Retrieve candidates by city-----");
		
		List<Candidate> candidates = candidateService.getCandidatesByCity(city);
		
		List<CandidateResponse> candResponse = new ArrayList<CandidateResponse>();
		
		if(!candidates.isEmpty()) {
			
			candidates.stream().forEach(candidate ->
			{
				candResponse.add(new CandidateResponse(candidate));
			});
			
		}else {
			
			throw new ResourceNotFoundException("No matches for city: " +city+ " found");
			
		}
		
		return ResponseEntity.ok().body(candResponse);
		
	}
	
}
