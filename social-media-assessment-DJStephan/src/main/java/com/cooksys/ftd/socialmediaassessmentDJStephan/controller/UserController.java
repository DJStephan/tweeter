package com.cooksys.ftd.socialmediaassessmentDJStephan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.CredentialsDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.ProfileDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.TweetDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.UserDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.User;
import com.cooksys.ftd.socialmediaassessmentDJStephan.repositories.UserRepository;
import com.cooksys.ftd.socialmediaassessmentDJStephan.services.UserService;

@RestController
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("users")
	public UserDto[] getUsers() {
		return userService.getUsers();
	}

	@PostMapping("users")
	public UserDto createUser(@RequestBody CredentialsDto credentialsDto, @RequestBody ProfileDto profileDto) {
		return this.userService.createUser(credentialsDto, profileDto);
	}

	@GetMapping("users/@{username}")
	public UserDto getUser(@PathVariable(name = "username") String username) {
		return this.userService.getUser(username);
	}

	@PatchMapping("users/@{username}")
	public UserDto updateUserProfile(@PathVariable(name = "username") String username,
			@RequestBody CredentialsDto credentialsDto, @RequestBody ProfileDto profileDto) {
		return this.userService.updateUserProfile(username, credentialsDto, profileDto);
	}

	@DeleteMapping("users/@{username}")
	public UserDto deleteUser(@PathVariable(name = "username") String username,
			@RequestBody CredentialsDto credentialsDto) {
		return this.userService.deleteUser(username, credentialsDto);
	}

	@PostMapping("users/@{username}/follow")
	public void follow(@PathVariable(name = "username") String usernameToFollow,
			@RequestBody CredentialsDto credentials) {
		userService.follow(usernameToFollow, credentials);

	}

	@PostMapping("users/@{username}/unfollow")
	public void unfollow(@PathVariable(name = "username") String usernameToUnfollow, CredentialsDto credentialsDto) {
		userService.unfollow(usernameToUnfollow, credentialsDto);
	}

	@GetMapping("users/@{username}/feed")
	public TweetDto[] getFeed(@PathVariable(name = "username") String username) {
		return userService.getFeed(username);
	}
	
	@GetMapping("users/@{username}/tweets")
	public TweetDto[] getTweets(@PathVariable(name = "username") String username) {
		return userService.getTweets(username);
	}
	
	@GetMapping("users/@{username}/mentions")
	public TweetDto[] getMentions(@PathVariable(name = "username") String username) {
		return userService.getMentions(username);
	}
	
	@GetMapping("users/@{username}/followers")
	public UserDto[] getFollowers(@PathVariable(name = "username") String username) {
		return userService.getFollowers(username);
	}
	
	@GetMapping("users/@{username}/following")
	public UserDto[] getFollowing(@PathVariable(name = "username") String username) {
		return userService.getFollowing(username);
	}
}
