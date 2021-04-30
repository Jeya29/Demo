package com.org.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.org.entity.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
	
	
      List<Candidate> findByFirstName(String firstName);
      
      Candidate findByFirstNameAndLastName(String firstName, String lastName);     
      
      List<Candidate> findByFirstNameOrLastName(String firstName, String lastName);
      
      @Query("From Candidate where firstName = :firstName and lastName = :lastName")
	  Candidate getByFirstAndLastName(String firstName, String lastName);

      @Modifying
      @Transactional
      @Query("Update Candidate set firstName = :firstName where id= :id")
      Integer updateFirstNameById(int id, String firstName);

      @Query("From Candidate where address.city = :city")
      List<Candidate> getByAddressCity(String city);

      
	
	
	
      
      
	
      
	

}
