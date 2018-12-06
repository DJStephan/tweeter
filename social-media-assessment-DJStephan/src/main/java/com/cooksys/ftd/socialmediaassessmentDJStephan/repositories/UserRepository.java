package com.cooksys.ftd.socialmediaassessmentDJStephan.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);

	List<User> findByIdInAndActive(Set<Long> userIds, boolean b);

}
