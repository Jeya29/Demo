package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Candidate;
import com.example.request.CreateCandidateRequest;
import com.example.request.InQueryRequest;
import com.example.request.UpdateCandidateRequest;
import com.example.response.CandidateResponse;
import com.example.service.CandidateService;

@RestController
@RequestMapping("/api/candidate")
public class CandidateController{
	
	Logger log = LoggerFactory.getLogger(CandidateController.class);
	
	
	@Autowired
	CandidateService candidateService;

	@GetMapping("/get")
	public List<CandidateResponse> getAllCandidates() {
		
		log.info("----Get all the candidates list----");
		
		List<Candidate> getAllCandidates =  candidateService.getAllCandidates();
		
		List<CandidateResponse> candidateResponseList = new ArrayList<CandidateResponse>();
		
		
		getAllCandidates.stream().forEach(candidate ->
		{
			candidateResponseList.add(new CandidateResponse(candidate));
			
		});		
		
		return candidateResponseList;
	}
	
	@PostMapping("/create")
	public CandidateResponse createCandidate(@Valid @RequestBody CreateCandidateRequest createRequest) {
		
		log.info("-----Create Candidate----");
		
		Candidate candidate = candidateService.createCandidate(createRequest);
		
		return new CandidateResponse(candidate);
		
		
	}	
	
	@PutMapping("/update")
	public CandidateResponse updateCandidate (@Valid @RequestBody UpdateCandidateRequest updateReq) {
		
		log.info("-----Updating candidate details------");
		
		Candidate candidate = candidateService.updateCandidate(updateReq);
		
		return new CandidateResponse(candidate);
		
	}
	
	@DeleteMapping("/delete")
	public String deleteCandidate(@RequestParam int id) {
		
		log.info("----Deletion initiated----");
		
		String status = candidateService.deleteCandidate(id);
		
		return status;
		
	}
	
	@GetMapping("/getByFirstName/{firstName}")
	public List<CandidateResponse> getByFirstName (@PathVariable String firstName) {
		
		log.info("-----Fetch candidate(s) by firstname-----");
		
		List<Candidate> candidates = new ArrayList<Candidate>();
		
		List<CandidateResponse> candResponse = new ArrayList<CandidateResponse>();
		
		candidates = candidateService.getByFirstName(firstName);
		
		candidates.stream().forEach(candidate->
		{
			candResponse.add(new CandidateResponse(candidate));
			
		}
		);
		
		
		return candResponse;	
		
	}
	
	@GetMapping("/getByFirstNameAndLastName/{firstName}/{LastName}")
	public CandidateResponse getByFirstNameAndLastName(@PathVariable String firstName,@PathVariable String LastName) {
		
		log.info("----Fetch candidate by firstname and lastname----");
		
		Candidate candidate = new Candidate();
		
		candidate = candidateService.getByFirstNameAndLastName(firstName,LastName);
		
		CandidateResponse candResponse = new CandidateResponse(candidate);
		
		
		return candResponse;	
		
	}
	
	@GetMapping("/getByFirstNameOrLastName/{firstName}/{LastName}")
	public List<CandidateResponse> getByFirstNameOrLastName(@PathVariable String firstName,@PathVariable String LastName) {
		
		log.info("------Fetch candidates by first or last names---");

		List<Candidate> candidates = candidateService.getByFirstNameOrLastName(firstName,LastName);
		
		List<CandidateResponse> candResponse = new ArrayList<CandidateResponse>();
		
		candidates.stream().forEach(candidate ->
		{
			candResponse.add(new CandidateResponse(candidate));
		});		
		
		
		return candResponse;
		
	}
	
	@GetMapping("/getByFirstNameIn")
	public List<CandidateResponse> getByFirstNameIn(@RequestBody InQueryRequest inQuery){
		
		log.info("------Get candidates by first name in query-----");
		log.info("----In query request----" + inQuery);
		
		List<Candidate> candidates = candidateService.getByFirstNameIn(inQuery);
		
		List<CandidateResponse> candResponse = new ArrayList<CandidateResponse>();
		
		candidates.stream().forEach(candidate ->
		{
			candResponse.add(new CandidateResponse(candidate));
		});	
		
		log.info("---In query response---" + candResponse);
		
		
		return candResponse;
	}
	
	@GetMapping("/getAllByPagination")
	public List<CandidateResponse> getAllByPagination(@RequestParam int pageNo, @RequestParam int pageSize){
		
		log.info("----Pagination initiated----");
		
		List<Candidate> candidates = candidateService.getAllByPagination(pageNo,pageSize);
		
		List<CandidateResponse> candResponse = new ArrayList<CandidateResponse>();
		
		candidates.stream().forEach(candidate ->
		{
			candResponse.add(new CandidateResponse(candidate));
		});		
		
		
		return candResponse;
		
	}
	
	@GetMapping("/getAllCandidatesSorted")
	public List<CandidateResponse> getAllCandidatesSorted(){
		
		log.info("----Sort candidates----");
		
		List<Candidate> candidates = candidateService.getAllCandidatesSorted();
		
		List<CandidateResponse> candResponse = new ArrayList<CandidateResponse>();
		
		candidates.stream().forEach(candidate ->
		{
			candResponse.add(new CandidateResponse(candidate));
		});		
		
		
		return candResponse;
		
	}
	
	@PutMapping("/updateFirstNameById/{id}/{firstName}")
	public String updateFirstNameById(@PathVariable int id, @PathVariable String firstName) {
		
		log.info("-----Update candidate's firstname by id-----" );
		
		return candidateService.updateFirstNameById(id,firstName) + " candidate(s) updated";
		
	}
	
	@DeleteMapping("/deleteByFirstName/{firstName}")
	public String deleteByFirstName(@PathVariable String firstName) {
			
		log.info("--------Delete candidate by firstname------");
		
		return candidateService.deleteByFirstName(firstName) + " candidate(s) deleted";
		
	}
	
	@GetMapping("/getCandidatesByCity/{city}")
	public List<CandidateResponse> getCandidatesByCity (@PathVariable String city){
		
		log.info("------Retrieve candidates by city-----");
		
		List<Candidate> candidates = candidateService.getCandidatesByCity(city);
		
		List<CandidateResponse> candResponse = new ArrayList<CandidateResponse>();
		
		candidates.stream().forEach(candidate ->
		{
			candResponse.add(new CandidateResponse(candidate));
		});		
		
		
		return candResponse;
		
	}
	
	
	
	
	
	
}
