package com.cooksys.ftd.socialmediaassessmentDJStephan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.Credentials;

public interface CredentialsRepository extends JpaRepository<Credentials, Long>{

	Credentials findByUsername(String userName);

	
	
	

}
