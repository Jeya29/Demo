package com.example.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
	
	
      List<Candidate> findByFirstName(String firstName);
      
      Candidate findByFirstNameAndLastName(String firstName, String lastName);     
      
      List<Candidate> findByFirstNameOrLastName(String firstName, String lastName);
      
      List<Candidate> findByFirstNameIn (List<String> firstNames);

      @Query("From Candidate where firstName = :firstName and lastName = :lastName")
	  Candidate getByFirstAndLastName(String firstName, String lastName);

      @Modifying
      @Transactional
      @Query("Update Candidate set firstName = :firstName where id= :id")
      Integer updateFirstNameById(int id, String firstName);

      @Modifying
      @Transactional
      @Query("Delete from Candidate where firstName = :firstName")
      Integer deleteByFirstName(String firstName);

      List<Candidate> findByAddressCity(String city);

      @Query("From Candidate where address.city = :city")
      List<Candidate> getByAddressCity(String city);

      
	
	
	
      
      
	
      
	

}
