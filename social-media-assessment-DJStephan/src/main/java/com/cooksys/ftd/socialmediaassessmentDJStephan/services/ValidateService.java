package com.cooksys.ftd.socialmediaassessmentDJStephan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooksys.ftd.socialmediaassessmentDJStephan.mappers.HashTagMapper;
import com.cooksys.ftd.socialmediaassessmentDJStephan.mappers.UserMapper;
import com.cooksys.ftd.socialmediaassessmentDJStephan.repositories.HashTagRepository;
import com.cooksys.ftd.socialmediaassessmentDJStephan.repositories.UserRepository;

@Service
public class ValidateService {

	UserMapper userMapper;

	HashTagMapper hashTagMapper;

	HashTagRepository hashTagRepository;

	UserRepository userRepository;

	@Autowired
	public ValidateService(UserMapper userMapper, HashTagMapper hashTagMapper, HashTagRepository hashTagRepository,
			UserRepository userRepository) {
		this.hashTagMapper = hashTagMapper;
		this.userMapper = userMapper;
		this.hashTagRepository = hashTagRepository;
		this.userRepository = userRepository;
	}

	public boolean validateTag(String label) {
		if (this.hashTagRepository.findByLabel(label) != null) {
			return true;
		}
		return false;
	}

	public boolean validateUserName(String userName) {
		if (this.userRepository.findByUsername(userName) != null) {
			return true;
		}
		return false;
	}

}
