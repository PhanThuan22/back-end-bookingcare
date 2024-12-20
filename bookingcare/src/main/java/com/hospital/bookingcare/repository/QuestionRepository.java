package com.hospital.bookingcare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.bookingcare.model.Question;


public interface QuestionRepository extends JpaRepository<Question,Long> {
	List<Question> findByIsAnswered(Boolean isAnswered);
	List<Question> findByIsAnsweredTrue();

}
