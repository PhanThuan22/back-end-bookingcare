package com.hospital.bookingcare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.bookingcare.model.Specialty;
import com.hospital.bookingcare.repository.SpecialtyRepository;
import com.hospital.bookingcare.response.SpecialtyDTO;

@Service
public class SpecialtyService {
	
	@Autowired
    private SpecialtyRepository specialtyRepository;

	public Specialty getSpecialtyById(Long specialtyId) {
        return specialtyRepository.findById(specialtyId).orElse(null);
    }
}
