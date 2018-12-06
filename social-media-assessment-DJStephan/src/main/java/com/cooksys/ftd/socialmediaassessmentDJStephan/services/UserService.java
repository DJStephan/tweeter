package com.cooksys.ftd.socialmediaassessmentDJStephan.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.CredentialsDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.ProfileDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.TweetDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.UserDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.Credentials;
import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.Profile;
import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.Tweet;
import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.User;
import com.cooksys.ftd.socialmediaassessmentDJStephan.mappers.CredentialsMapper;
import com.cooksys.ftd.socialmediaassessmentDJStephan.mappers.ProfileMapper;
import com.cooksys.ftd.socialmediaassessmentDJStephan.mappers.TweetMapper;
import com.cooksys.ftd.socialmediaassessmentDJStephan.mappers.UserMapper;
import com.cooksys.ftd.socialmediaassessmentDJStephan.repositories.CredentialsRepository;
import com.cooksys.ftd.socialmediaassessmentDJStephan.repositories.ProfileRepository;
import com.cooksys.ftd.socialmediaassessmentDJStephan.repositories.TweetRepository;
import com.cooksys.ftd.socialmediaassessmentDJStephan.repositories.UserRepository;
@Service
public class UserService {
	
	private UserRepository userRepository;
	
	private CredentialsRepository credendentialsRrepository;
	
	private ProfileRepository profileRepository;
	
	private TweetRepository tweetRepository;
	
	private UserMapper usermapper;
	
	private TweetMapper tweetMapper;
	
	private CredentialsMapper credentialsmapper;
	
	private ProfileMapper profileMapper;
	
	@Autowired
	public UserService(UserRepository userRepository, UserMapper  userMapper, CredentialsMapper credentialsMapper, ProfileMapper profileMapper ) {
		this.userRepository = userRepository;
		this.usermapper = userMapper;
		this.credentialsmapper = credentialsMapper;
		this.profileMapper = profileMapper;
	}

	public UserDto[] getUsers() {
		List<User> users = this.userRepository.findAll();
		return this.usermapper.usersToUserDtos(users);
		 
	}

	public UserDto createUser(CredentialsDto credentialsDto, ProfileDto profileDto) {// throws usernameExists
		Credentials credentials = this.credentialsmapper.credentialsDtoToCredentials(credentialsDto);
		Profile profile = this.profileMapper.profileDtoToProfile(profileDto);
		Credentials databaseCredentials = this.credendentialsRrepository.findByUsername(credentials.getUsername());
		
		if(databaseCredentials != null && databaseCredentials.getPassword().equals(credentials.getPassword())) {
			User user = this.userRepository.findByUsername(credentials.getUsername());
			if(user.isActive()) {
				//throw error, user already exists
			}else {
				user.setActive(true);
				this.userRepository.saveAndFlush(user);
				UserDto userDto = this.usermapper.userToUserDto(user);
				return userDto;
			}
		}else if(databaseCredentials != null) {
			//throw error, user already exists
		}
		
		User newUser = new User(credentials, profile);
		userRepository.saveAndFlush(newUser);
		profileRepository.saveAndFlush(profile);
		credendentialsRrepository.saveAndFlush(credentials);
		
		
		
		return this.usermapper.userToUserDto(newUser);
	}
	
	public UserDto getUser(String username) {//throws usernotfound
		User user = userRepository.findByUsername(username);
		if(user == null || !(user.isActive())) {
			//throw error, usernotfouns
		}
		return this.usermapper.userToUserDto(user);
	}
	
	public UserDto updateUserProfile(String username, CredentialsDto credentialsDto, ProfileDto profileDto) {
		if(!(username.equals(credentialsDto.getUserName()))) {
			//throw error usernames don't match
		}
		Credentials databaseCredentials = credendentialsRrepository.findByUsername(credentialsDto.getUserName());
		Credentials credentials = credentialsmapper.credentialsDtoToCredentials(credentialsDto);
		if(!(databaseCredentials.equals(credentials))) {
			//throw error credentials don't match
		}
		User user = userRepository.findByUsername(username);
		if(!(user.isActive())) {
			//throw error cantfinduser
		}
		Profile profile = profileMapper.profileDtoToProfile(profileDto);
		user.setProfile(profile);
		userRepository.saveAndFlush(user);
		return usermapper.userToUserDto(user);
	}

	public UserDto deleteUser(String username, CredentialsDto credentialsDto) {
		if(!(username.equals(credentialsDto.getUserName()))) {
			// error credentials don't match
		}
		Credentials credentials = credentialsmapper.credentialsDtoToCredentials(credentialsDto);
		Credentials databaseCredentials = credendentialsRrepository.findByUsername(username);
		if(databaseCredentials == null || !(credentials.equals(databaseCredentials))) {
			//error credentials don't match
		}
		User user = userRepository.findByUsername(username);
		if(!(user.isActive())){
			//error user doesn't exist
		}
		user.setActive(false);
		userRepository.saveAndFlush(user);
		Tweet[] userTweets = tweetRepository.findByAuthor(username);
		for(Tweet tweet: userTweets) {
			tweet.setDeleted(true);
			tweetRepository.saveAndFlush(tweet);
		}
		return usermapper.userToUserDto(user);
	}
	
	public void follow(String usernameToFollow, CredentialsDto credentialsDto) {
		Credentials credentials = credentialsmapper.credentialsDtoToCredentials(credentialsDto);
		Credentials databaseCredentials = credendentialsRrepository.findByUsername(credentials.getUsername());
		
		if(!(credentials.equals(databaseCredentials)) || this.userRepository.findByUsername(usernameToFollow) == null) {
			//error
		}
		
		User user = userRepository.findByUsername(credentials.getUsername());
		User userToFollow =  userRepository.findByUsername(usernameToFollow);
		if(user.getFollowing().contains(usernameToFollow)) {
			//error already following
		}
		user.addFollow(usernameToFollow);
		userToFollow.addFollower(user.getUsername());
		userRepository.saveAndFlush(userToFollow);
		userRepository.saveAndFlush(user);
		
	}
	
	public void unfollow(String usernameToUnfollow, CredentialsDto credentialsDto) {
		Credentials credentials = credentialsmapper.credentialsDtoToCredentials(credentialsDto);
		Credentials databaseCredentials = credendentialsRrepository.findByUsername(credentials.getUsername());
		
		if(!(credentials.equals(databaseCredentials)) || this.userRepository.findByUsername(usernameToUnfollow) == null) {
			//error
		}
		
		User user = userRepository.findByUsername(credentials.getUsername());
		if(!(user.getFollowing().contains(usernameToUnfollow))) {
			//error notfollowing
		}
		user.removeFollow(usernameToUnfollow);
		userRepository.saveAndFlush(user);
		
	}
	
	public TweetDto[] getFeed(String username) {
		User user = this.userRepository.findByUsername(username);
		if(user == null || !(user.isActive())) {
			//error no user exists
		}
		Set<String> following = user.getFollowing();
		following.add(username);
		Set<String> usersOnFeed = new HashSet<String>();
		for(String userFollowed : following) {
			if((userRepository.findByUsername(userFollowed).isActive())) {
				usersOnFeed.add(userFollowed);
			}
		}
		Tweet[] tweets = tweetRepository.findByAuthorInOrderByPostedDesc(usersOnFeed);
		return tweetMapper.tweetsToTweetDtos(tweets);
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserMapper getUsermapper() {
		return usermapper;
	}

	public void setUsermapper(UserMapper usermapper) {
		this.usermapper = usermapper;
	}

	public CredentialsMapper getCredentialsmapper() {
		return credentialsmapper;
	}

	public void setCredentialsmapper(CredentialsMapper credentialsmapper) {
		this.credentialsmapper = credentialsmapper;
	}

	public ProfileMapper getProfileMapper() {
		return profileMapper;
	}

	public void setProfileMapper(ProfileMapper profileMapper) {
		this.profileMapper = profileMapper;
	}

	public TweetDto[] getTweets(String username) {
		User user = userRepository.findByUsername(username);
		if(!(user.isActive())) {
			//error user doesn't exist
		}
		Tweet[] tweets = tweetRepository.findByAuthorOrderByPostedDesc(username);
		return tweetMapper.tweetsToTweetDtos(tweets);
	}

	public TweetDto[] getMentions(String username) {
		User user = userRepository.findByUsername(username);
		if(user == null || !(user.isActive())) {
			//error user does not exist
		}
		
		Tweet[] tweets = tweetRepository.findByIdIn(user.getMentions());
		return tweetMapper.tweetsToTweetDtos(tweets);
	}

	public UserDto[] getFollowers(String username) {
		User user = userRepository.findByUsername(username);
		if(user == null || !(user.isActive())) {
			//error no user
		}
		Set<String> followers = user.getFollowers();
		List<User> followersToReturn = new ArrayList<User>();
		for(String userFollowing : followers) {
			User userToAdd = userRepository.findByUsername(userFollowing);
			if(userToAdd.isActive()) {
				followersToReturn.add(userToAdd);
			}
		}
		
		return usermapper.usersToUserDtos(followersToReturn);
	}

	public UserDto[] getFollowing(String username) {
		User user = userRepository.findByUsername(username);
		if(user == null || !(user.isActive())) {
			//error no user
		}
		Set<String> following = user.getFollowing();
		List<User> followersToReturn = new ArrayList<User>();
		for(String userFollowing : following) {
			User userToAdd = userRepository.findByUsername(userFollowing);
			if(userToAdd.isActive()) {
				followersToReturn.add(userToAdd);
			}
		}
		
		return usermapper.usersToUserDtos(followersToReturn);
		
	}
	

}
