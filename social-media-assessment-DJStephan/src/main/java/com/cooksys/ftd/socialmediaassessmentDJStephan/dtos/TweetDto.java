package com.cooksys.ftd.socialmediaassessmentDJStephan.dtos;

import java.sql.Timestamp;

import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.Tweet;
import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.User;

public class TweetDto {
	
	private Long id;
	
	private User author;
	
	private Timestamp posted;
	
	private String content;
	
	private TweetDto inReplyTo;
	
	private TweetDto repostOf;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Timestamp getPosted() {
		return posted;
	}

	public void setPosted(Timestamp posted) {
		this.posted = posted;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TweetDto getInReplyTo() {
		return inReplyTo;
	}

	public void setInReplyTo(TweetDto inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	public TweetDto getRepostOf() {
		return repostOf;
	}

	public void setRepostOf(TweetDto repostOf) {
		this.repostOf = repostOf;
	}
	
	

}
