package com.hospital.bookingcare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.bookingcare.model.Clinic;
import com.hospital.bookingcare.repository.ClinicRepository;


@Service
public class ClinicService {
	@Autowired
    private ClinicRepository clinicRepository;

	public Clinic getClinicById(Long clinicId) {
        return clinicRepository.findById(clinicId).orElse(null);
    }
}
