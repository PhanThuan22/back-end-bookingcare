package com.hospital.bookingcare.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.bookingcare.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	Booking findByDoctorIdAndTokenBooking(Long doctorId, String tokenBooking);
	List<Booking> findByStatusIdAndDoctorIdAndDate(String statusId, Long doctorId,String date);
	void deleteByPatientId(Long userId);
	void deleteById(Long bookingId);
	Optional<Booking> findByDoctorIdAndPatientIdAndTimeTypesAndStatusId(
	        Long doctorId, Long patientId, String timeTypes, String statusId);
	 List<Booking> findByPatientId(Long patientId);
	
}
