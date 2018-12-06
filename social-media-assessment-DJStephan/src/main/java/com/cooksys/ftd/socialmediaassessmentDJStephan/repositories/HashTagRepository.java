package com.cooksys.ftd.socialmediaassessmentDJStephan.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.HashTag;

public interface HashTagRepository extends JpaRepository<HashTag, Long>{

	HashTag findByLabel(String label);

	HashTag findFirstByOrderByIdDesc();

	List<HashTag> findByIdIn(Set<Long> tags);

	

}
