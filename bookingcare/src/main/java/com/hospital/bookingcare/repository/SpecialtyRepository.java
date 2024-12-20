package com.hospital.bookingcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.bookingcare.model.Specialty;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {

}
