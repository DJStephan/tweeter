package com.cooksys.ftd.socialmediaassessmentDJStephan.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long>{


	Tweet[] findByAuthorInOrderByPostedDesc(Set<String> following);

	//Tweet[] findByAuthorOrderByDesc(String username);

	Tweet[] findByIdIn(Set<Long> tweetIds);

	Tweet[] findByAuthorOrderByPostedDesc(String username);

	Tweet[] findByAuthor(String username);

	Tweet[] findByDeleted(boolean b);

	Tweet findByContent(String content);

	Tweet findByIdAndDeleted(Long id, boolean b);

	Tweet findFirstByOrderByIdDesc();

	//Tweet findByIdAndActive(Long tweetId, boolean b);

	Tweet[] findByInReplyTo(Tweet tweet);

	Tweet[] findByRepostOf(Tweet tweet);

	
	
	

}
