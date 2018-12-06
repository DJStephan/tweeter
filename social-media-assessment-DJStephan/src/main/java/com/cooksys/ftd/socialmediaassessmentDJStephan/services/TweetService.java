package com.cooksys.ftd.socialmediaassessmentDJStephan.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.HashTagDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.TweetDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.UserDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.Credentials;
import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.HashTag;
import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.Tweet;
import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.User;
import com.cooksys.ftd.socialmediaassessmentDJStephan.mappers.HashTagMapper;
import com.cooksys.ftd.socialmediaassessmentDJStephan.mappers.TweetMapper;
import com.cooksys.ftd.socialmediaassessmentDJStephan.mappers.UserMapper;
import com.cooksys.ftd.socialmediaassessmentDJStephan.repositories.CredentialsRepository;
import com.cooksys.ftd.socialmediaassessmentDJStephan.repositories.HashTagRepository;
import com.cooksys.ftd.socialmediaassessmentDJStephan.repositories.TweetRepository;
import com.cooksys.ftd.socialmediaassessmentDJStephan.repositories.UserRepository;

@Service
public class TweetService {

	TweetRepository tweetRepository;

	TweetMapper tweetMapper;

	CredentialsRepository credentialsRepository;

	HashTagRepository hashTagRepository;
	
	HashTagMapper hashTagMapper;

	UserRepository userRepository;
	
	UserMapper userMapper;

	@Autowired
	public TweetService(TweetRepository tweetRepository, TweetMapper tweetMApper,
			CredentialsRepository credentialsRepository, HashTagRepository hashTagRepository,
			UserRepository userRepository, HashTagMapper hashTagMapper, UserMapper userMApper) {
		this.tweetRepository = tweetRepository;
		this.tweetMapper = tweetMApper;
		this.credentialsRepository = credentialsRepository;
		this.hashTagRepository = hashTagRepository;
		this.userRepository = userRepository;
		this.hashTagMapper  = hashTagMapper;
		this.userMapper = userMApper;
	}

	public TweetDto[] getAllTweets() {

		return tweetMapper.tweetsToTweetDtos(tweetRepository.findByDeleted(false));
	}
	
	public void parseContent(String content, Tweet tweetWithId) {
		String[] strings = content.split("//s+");
		for (String string : strings) {
			char[] chars = string.toCharArray();
			if (chars[0] == '#') {
				HashTag hashTag = hashTagRepository.findByLabel(string.substring(1));
				if (hashTag != null) {
					hashTag.addTaggedTweet(tweetWithId.getId());
					hashTagRepository.saveAndFlush(hashTag);
					tweetWithId.addTag(hashTagRepository.findFirstByOrderByIdDesc().getId());
					tweetRepository.saveAndFlush(tweetWithId);
				} else {
					HashTag hashTagToAdd = new HashTag(string.substring(1));
					hashTagToAdd.addTaggedTweet(tweetWithId.getId());
					hashTagRepository.saveAndFlush(hashTagToAdd);
					tweetWithId.addTag(hashTagRepository.findFirstByOrderByIdDesc().getId());
					tweetRepository.saveAndFlush(tweetWithId);
				}
			}

			if (chars[0] == '@') {
				User user = userRepository.findByUsername(string.substring(1));
				if (user != null) {
					user.addMention(tweetWithId.getId());
					tweetWithId.addMention(user.getId());
					userRepository.saveAndFlush(user);
					tweetRepository.saveAndFlush(tweetWithId);
				}
			}
		}
	}

	public TweetDto postTweet(String content, Credentials credentials) {
		Credentials databaseCredentials = credentialsRepository.findByUsername(credentials.getUsername());
		if (!(credentials.equals(databaseCredentials))) {
			// error credentials don't match
		}
		Tweet tweetToAdd = new Tweet(userRepository.findByUsername(credentials.getUsername()));
		tweetToAdd.setContent(content);
		tweetRepository.saveAndFlush(tweetToAdd);
		Tweet tweetWithId = tweetRepository.findFirstByOrderByIdDesc();
		this.parseContent(content, tweetWithId);

		return tweetMapper.tweetToDto(tweetWithId);
	}

	public TweetDto getTweet(Long id) {
		Tweet tweet = tweetRepository.findByIdAndDeleted(id, false);
		if (tweet == null) {
			// error no tweet
		}
		return tweetMapper.tweetToDto(tweet);
	}

	public TweetDto deleteTweet(Long id, Credentials credentials) {
		Tweet tweet = tweetRepository.findByIdAndDeleted(id, false);
		if (tweet == null || !(userRepository.findByUsername(tweet.getAuthorUsername()).getCredentials().equals(credentials))) {
			// error no tweet
		}
		tweet.setDeleted(true);
		tweetRepository.saveAndFlush(tweet);
		return tweetMapper.tweetToDto(tweet);
	}

	public void likeTweet(Long tweetid, Credentials credentials) {
		User user = userRepository.findByUsername(credentials.getUsername());
		Tweet tweet = tweetRepository.findByIdAndDeleted(tweetid, false);
		if(user == null || !(user.getCredentials().equals(credentials)) || tweet == null) {
			//error bad credentials or no tweet
		}
		
		user.likeTweet(tweetid);
		tweet.addLike(user.getId());
		userRepository.saveAndFlush(user);
		tweetRepository.saveAndFlush(tweet);
		
	}

	public TweetDto replyTweet(Long tweetId,String content, Credentials credentials) {
		Tweet tweetReplyingTo = tweetRepository.findByIdAndDeleted(tweetId, false);
		Credentials databaseCredentials = credentialsRepository.findByUsername(credentials.getUsername());
		if(tweetReplyingTo == null || !(credentials.equals(databaseCredentials))) {
			//error
		}
		User tweetUser = userRepository.findByUsername(credentials.getUsername());
		Tweet newTweet = new Tweet(tweetUser);
		newTweet.setContent(content);
		newTweet.setInReplyTo(tweetReplyingTo);
		tweetRepository.saveAndFlush(newTweet);
		Tweet tweetWithId = tweetRepository.findFirstByOrderByIdDesc();
		this.parseContent(content, tweetWithId);
		return tweetMapper.tweetToDto(tweetWithId);
	}

	public TweetDto repostTweet(Long tweetId, Credentials credentials) {
		Tweet tweetReposting = tweetRepository.findByIdAndDeleted(tweetId, false);
		Credentials databaseCredentials = credentialsRepository.findByUsername(credentials.getUsername());
		if(tweetReposting == null || !(credentials.equals(databaseCredentials))) {
			//error
		}
		User postingUser = userRepository.findByUsername(credentials.getUsername());
		Tweet newTweet = new Tweet(postingUser);
		newTweet.setContent(tweetReposting.getContent());
		newTweet.setRepostOf(tweetReposting);
		tweetRepository.saveAndFlush(newTweet);
		Tweet tweetWithId = tweetRepository.findFirstByOrderByIdDesc();
		this.parseContent(tweetReposting.getContent(), tweetWithId);
		return tweetMapper.tweetToDto(tweetWithId);
		
	}

	public HashTagDto[] getTweetTags(Long tweetId) {
		Tweet tweet = tweetRepository.findByIdAndDeleted(tweetId, false);
		if(tweet == null) {
			//error
		}
		List<HashTag> tags = hashTagRepository.findByIdIn(tweet.getTags());
		return hashTagMapper.hashTagsToHashTagDtos(tags);
	}

	public UserDto[] getUsersWhoLiked(Long tweetId) {
		Tweet tweet = tweetRepository.findByIdAndDeleted(tweetId, false);
		if(tweet == null) {
			//error
		}
		Set<Long> userIds = tweet.getLikes();
		List<User> users = userRepository.findByIdInAndActive(userIds, true);
		return userMapper.usersToUserDtos(users);
	}

	public TweetDto[] getReplies(Long tweetId, boolean wantDeleted) {
		Optional<Tweet> tryTweet = tweetRepository.findById(tweetId);
		Tweet tweet = null;
		if(tryTweet.isPresent()) {
			tweet = tryTweet.get();
		}else {
			//error no tweet
		}
		Tweet[] replyTweets = tweetRepository.findByInReplyTo(tweet);
		List<Tweet> notDeletedReplies = new ArrayList<Tweet>();
		if(wantDeleted) {//is part of context
			return tweetMapper.tweetsToTweetDtos(replyTweets);
		}else {
			for(Tweet replyTweet : replyTweets) {
				if(!(replyTweet.isDeleted())) {
					notDeletedReplies.add(replyTweet);
				}
			}
		}
		
		
		return tweetMapper.tweetsToTweetDtos(notDeletedReplies.toArray(new Tweet[notDeletedReplies.size()]));
	}

	public TweetDto[] getReposts(Long tweetId) {
		Tweet tweet = tweetRepository.findByIdAndDeleted(tweetId, false);
		if(tweet == null) {
			//error
		}
		Tweet[] tweets = tweetRepository.findByRepostOf(tweet);
		List<Tweet> notDeletedTweets = new ArrayList<Tweet>();
		for(Tweet tweetToCheck : tweets) {
			if(!(tweetToCheck.isDeleted())) {
				notDeletedTweets.add(tweetToCheck);
			}
		}
		return tweetMapper.tweetsToTweetDtos(notDeletedTweets.toArray(new Tweet[notDeletedTweets.size()]));
	}

	public UserDto[] getMentionedUsers(Long tweetId) {
		Tweet tweet = tweetRepository.findByIdAndDeleted(tweetId, false);
		if(tweet == null) {
			//error
		}
		Set<Long> usersMentioned = tweet.getMentions();
		List<User> users = userRepository.findByIdInAndActive(usersMentioned, true);
		return userMapper.usersToUserDtos(users);
	}

}
