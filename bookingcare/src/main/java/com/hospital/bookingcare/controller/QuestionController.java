package com.hospital.bookingcare.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.bookingcare.model.Question;
import com.hospital.bookingcare.service.QuestionService;
import com.hospital.bookingcare.service.UserService;

@RestController
@RequestMapping("/questions")
public class QuestionController {
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/ask")
	public ResponseEntity<Question> askQuestion(@RequestBody Map<String, String> request){
		String content = request.get("content");
		Long userId = Long.parseLong(request.get("userId"));
		Question question = questionService.askQuestion(content, userId);
		return ResponseEntity.ok(question);
	}
	
	@PostMapping("/answer")
	public ResponseEntity<Question> answerQuestion(@RequestBody Map<String, String> request){
		Long questionId = Long.parseLong(request.get("questionId"));
		String answer = request.get("answer");
		Long userId = Long.parseLong(request.get("userId"));
		Question question = questionService.answerQuestion(questionId, answer, userId);
		return ResponseEntity.ok(question);
	}

	@GetMapping("/unanswered")
	public ResponseEntity<List<Question>> getUnansweredQuestion(){
		List<Question> question = questionService.getUnansweredQuestion();
		return ResponseEntity.ok(question);
	}
	
	@GetMapping("/answered")
	public ResponseEntity<List<Question>> getAnsweredQuestions() {
	    List<Question> questions = questionService.getAnsweredQuestions();
	    return ResponseEntity.ok(questions);
	}

}
