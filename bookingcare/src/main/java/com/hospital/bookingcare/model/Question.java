package com.hospital.bookingcare.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String content;
	private String answer;
	private Boolean isAnswered = false;
	
	@ManyToOne
	@JoinColumn(name = "asked_by_id")
	private User askedBy;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "answered_by_id")
	private User answeredBy;
	
	private LocalDateTime createdDate = LocalDateTime.now();
	private LocalDateTime answeredDate;
	
	public Question() {}
	
	public Question(String content, User askedBy) {
		this.content = content;
		this.askedBy = askedBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Boolean getIsAnswered() {
		return isAnswered;
	}

	public void setIsAnswered(Boolean isAnswered) {
		this.isAnswered = isAnswered;
	}

	public User getAskedBy() {
		return askedBy;
	}

	public void setAskedBy(User askedBy) {
		this.askedBy = askedBy;
	}

	public User getAnsweredBy() {
		return answeredBy;
	}

	public void setAnsweredBy(User answeredBy) {
		this.answeredBy = answeredBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getAnsweredDate() {
		return answeredDate;
	}

	public void setAnsweredDate(LocalDateTime answeredDate) {
		this.answeredDate = answeredDate;
	}

	
}
