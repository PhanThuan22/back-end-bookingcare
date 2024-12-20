package com.hospital.bookingcare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.bookingcare.model.Markdown;

public interface MarkdownRepository extends JpaRepository<Markdown, Long> {

	Optional<Markdown> findByDoctorId(Long doctorId);	

}