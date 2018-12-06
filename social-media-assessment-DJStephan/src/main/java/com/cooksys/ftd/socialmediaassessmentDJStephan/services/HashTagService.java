package com.cooksys.ftd.socialmediaassessmentDJStephan.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.HashTagDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.TweetDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.HashTag;
import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.Tweet;
import com.cooksys.ftd.socialmediaassessmentDJStephan.mappers.HashTagMapper;
import com.cooksys.ftd.socialmediaassessmentDJStephan.mappers.TweetMapper;
import com.cooksys.ftd.socialmediaassessmentDJStephan.repositories.HashTagRepository;
import com.cooksys.ftd.socialmediaassessmentDJStephan.repositories.TweetRepository;
import com.cooksys.ftd.socialmediaassessmentDJStephan.repositories.UserRepository;

@Service
public class HashTagService {

	HashTagRepository hashTagRepository;

	HashTagMapper hashTagMapper;

	TweetRepository tweetRepository;

	TweetMapper tweetMapper;

	UserRepository userRepository;

	@Autowired
	public HashTagService(HashTagRepository hashTagReposity, HashTagMapper hashTagMapper,
			TweetRepository tweetRepostory, TweetMapper tweetMapper, UserRepository userRepository) {
		this.hashTagRepository = hashTagReposity;
		this.hashTagMapper = hashTagMapper;
		this.tweetRepository = tweetRepostory;
		this.tweetMapper = tweetMapper;
		this.userRepository = userRepository;
	}

	public HashTagDto[] getHashTags() {
		List<HashTag> hashTags = hashTagRepository.findAll();
		return hashTagMapper.hashTagsToHashTagDtos(hashTags);
	}

	public TweetDto[] getTaggedTweets(String label) {
		HashTag hashTag = hashTagRepository.findByLabel(label);
		Tweet[] tweets = tweetRepository.findByIdIn(hashTag.getTaggedTweets());
		Set<Tweet> tweetsToReturn = new HashSet<Tweet>();
		for(Tweet tweet : tweets) {
			if(userRepository.findByUsername(tweet.getAuthorUsername()).isActive()) {
				tweetsToReturn.add(tweet);
			}
		}
		
		return tweetMapper.tweetsToTweetDtos(tweetsToReturn.toArray(new Tweet[tweetsToReturn.size()]));
	}

}
