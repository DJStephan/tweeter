package com.cooksys.ftd.socialmediaassessmentDJStephan.mappers;

import org.mapstruct.Mapper;

import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.TweetDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.Tweet;

@Mapper(componentModel = "spring")
public interface TweetMapper {

	TweetDto[] tweetsToTweetDtos(Tweet[] tweets);

	TweetDto tweetToDto(Tweet tweetWithId);
	
	

}
