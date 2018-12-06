package com.cooksys.ftd.socialmediaassessmentDJStephan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.HashTagDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.TweetDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.UserDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.Credentials;
import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.Tweet;
import com.cooksys.ftd.socialmediaassessmentDJStephan.services.TweetService;

@RestController
public class TweetController {
	
	TweetService tweetService;
	
	@Autowired
	public TweetController(TweetService tweetService) {
		this.tweetService = tweetService;
	}
	
	@GetMapping("tweets")
	public TweetDto[] getAllTweets() {
		return tweetService.getAllTweets();
	}
	
	@PostMapping("tweets")
	public TweetDto postTweet(@RequestBody String content, @RequestBody Credentials credentials) {
		return tweetService.postTweet(content, credentials);
	}
	
	@GetMapping("tweets/{id}")
	public TweetDto getTweet(@PathVariable(name= "id") Long id) {
		return tweetService.getTweet(id);
	}
	
	@DeleteMapping("tweets/{id}")
	public TweetDto deleteTweet(@PathVariable(name = "id") Long id, @RequestBody Credentials credentials) {
		return tweetService.deleteTweet(id, credentials);
	}
	
	@PostMapping("tweets/{id}/like")
	public void likeTweet(@PathVariable(name = "id") Long tweetid, @RequestBody Credentials credentials) {
		tweetService.likeTweet(tweetid, credentials);
	}
	
	@PostMapping("tweets/{id}/reply")
	public TweetDto replyTweet(@PathVariable(name = "id") Long tweetId,@RequestBody String content, @RequestBody Credentials credentials) {
		return tweetService.replyTweet(tweetId, content, credentials);
	}
	
	@PostMapping("tweets/{id}/repost")
	public TweetDto repostTweet(@PathVariable(name = "id") Long tweetId, Credentials credentials) {
		return tweetService.repostTweet(tweetId, credentials);
	}
	
	@GetMapping("tweets/{id}/tags")
	public HashTagDto[] getTweetTags(@PathVariable(name = "id") Long tweetId) {
		return tweetService.getTweetTags(tweetId);
	}
	
	@GetMapping("tweets/{id}/likes")
	public UserDto[] getUsersWhoLiked(@PathVariable(name = "id") Long tweetId) {
		return tweetService.getUsersWhoLiked(tweetId);
	}
	
	@GetMapping("tweets/{id}/replies")
	public TweetDto[] getReplies(@PathVariable(name = "id") Long tweetId) {
		boolean wantDeleted = false;
		return tweetService.getReplies(tweetId, wantDeleted);
	}
	
	@GetMapping("tweets/{id}/reposts")
	public TweetDto[] getReposts(@PathVariable(name = "id") Long tweetId) {
		return tweetService.getReposts(tweetId);
	}
	
	@GetMapping("tweets/{id}/mentions")
	public UserDto[] getMentionedUsers(@PathVariable(name = "id") Long tweetId) {
		return tweetService.getMentionedUsers(tweetId);
	}
}
