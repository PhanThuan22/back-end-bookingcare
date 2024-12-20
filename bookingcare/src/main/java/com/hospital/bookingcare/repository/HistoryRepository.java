package com.hospital.bookingcare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.bookingcare.model.History;

public interface HistoryRepository extends JpaRepository<History, Long>{
	List<History> findByPatientId(Long patientId);

}
