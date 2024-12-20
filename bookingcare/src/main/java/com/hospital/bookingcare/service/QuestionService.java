package com.hospital.bookingcare.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.bookingcare.model.Question;
import com.hospital.bookingcare.model.User;
import com.hospital.bookingcare.repository.QuestionRepository;
import com.hospital.bookingcare.repository.UserRepository;

@Service
public class QuestionService {
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Question askQuestion(String content, Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		Question question = new Question(content, user);
		return questionRepository.save(question);
	}
	
	public Question answerQuestion(Long questionId, String answer, Long userId) {
		Question question = questionRepository.findById(questionId)
				.orElseThrow(() -> new RuntimeException("Question not found"));
		if (question.getIsAnswered()) {
			throw new RuntimeException("Question already answered");
		}
		
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		question.setAnswer(answer);
		question.setAnsweredBy(user);
		question.setIsAnswered(true);
		question.setAnsweredDate(LocalDateTime.now());
		return questionRepository.save(question);
	}
	
	public List<Question> getUnansweredQuestion(){
		return questionRepository.findByIsAnswered(false);
	}

	public List<Question> getAnsweredQuestions() {
	    return questionRepository.findByIsAnsweredTrue();
	}

}
