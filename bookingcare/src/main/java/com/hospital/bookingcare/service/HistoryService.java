package com.hospital.bookingcare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.bookingcare.model.History;
import com.hospital.bookingcare.repository.HistoryRepository;

@Service
public class HistoryService {
	@Autowired
	private HistoryRepository historyRepository;
	
	public History save(History history) {
		return historyRepository.save(history);
	}
	
	 public List<History> getHistoryByPatientId(Long patientId) {
	        return historyRepository.findByPatientId(patientId);
	    }
}
