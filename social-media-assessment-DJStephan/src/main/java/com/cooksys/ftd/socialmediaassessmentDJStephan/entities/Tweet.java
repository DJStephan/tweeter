package com.cooksys.ftd.socialmediaassessmentDJStephan.entities;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Tweet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
	private User author;

	@Column
	private String authorUsername;

	@Column(nullable = false)
	private Timestamp posted;

	@Column
	private String content;


	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_tweet_id")
	private Tweet inReplyTo;

	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repost_tweet_id")
	private Tweet repostOf;

	@Column
	private boolean deleted;
	
	@Column
	@ElementCollection(targetClass=Long.class)
	private Set<Long> tags = new HashSet<Long>();
	
	@Column
	@ElementCollection(targetClass=Long.class)
	private Set<Long> mentions = new HashSet<Long>();
	
	@Column
	@ElementCollection(targetClass=Long.class)
	private Set<Long> likes = new HashSet<Long>();

	public Tweet() {}

	public Tweet(User author) {
		this.author = author;
		this.authorUsername = this.author.getUsername();
		this.deleted = false;
	}

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

	public Tweet getInReplyTo() {
		return inReplyTo;
	}

	public void setInReplyTo(Tweet inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	public Tweet getRepostOf() {
		return repostOf;
	}

	public void setRepostOf(Tweet repostOf) {
		this.repostOf = repostOf;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getAuthorUsername() {
		return authorUsername;
	}

	public void setAuthorUsername(String authorUsername) {
		this.authorUsername = authorUsername;
	}

	public Set<Long> getTags() {
		return tags;
	}

	public void setTags(Set<Long> tags) {
		this.tags = tags;
	}

	public Set<Long> getMentions() {
		return mentions;
	}

	public void setMentions(Set<Long> mentions) {
		this.mentions = mentions;
	}

	public void addTag(long hashTagId) {
		this.tags.add(hashTagId);
		
	}
	
	public void addMention(long userId) {
		this.mentions.add(userId);
		
	}

	public Set<Long> getLikes() {
		return likes;
	}

	public void setLikes(Set<Long> likes) {
		this.likes = likes;
	}
	
	public void addLike(Long userId) {
		this.likes.add(userId);
	}
	
	
	
	

}
