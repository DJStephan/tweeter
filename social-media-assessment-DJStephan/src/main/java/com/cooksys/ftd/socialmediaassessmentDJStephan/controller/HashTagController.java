package com.cooksys.ftd.socialmediaassessmentDJStephan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.HashTagDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.TweetDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.services.HashTagService;
@RestController
public class HashTagController {
	
	HashTagService hashTagService;
	
	@Autowired
	public HashTagController(HashTagService hashTagService) {
		this.hashTagService = hashTagService;
	}

	public HashTagService getHashTagService() {
		return hashTagService;
	}

	public void setHashTagService(HashTagService hashTagService) {
		this.hashTagService = hashTagService;
	}
	
	@GetMapping("tags")
	public HashTagDto[] getHashTags() {
		return hashTagService.getHashTags();
	}
	
	@GetMapping("tags/{label}")
	public TweetDto[] getTaggedTweets(@PathVariable(name = "label") String label) {
		return hashTagService.getTaggedTweets(label);
	}
	
	

}
