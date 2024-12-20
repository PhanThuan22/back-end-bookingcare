package com.hospital.bookingcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.bookingcare.model.Clinic;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {

}
