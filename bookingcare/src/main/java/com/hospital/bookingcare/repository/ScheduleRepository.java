package com.hospital.bookingcare.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.bookingcare.model.Allcodes;
import com.hospital.bookingcare.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	boolean existsByDoctorIdAndDateAndTimeTypes(Long doctorId, LocalDate date, Allcodes timeTypes);

	List<Schedule> findByDoctorIdAndDate(Long doctorId, LocalDate date);
	Optional<Schedule> findByTimeTypes_KeyMapAndDate(String timeTypesKeyMap, LocalDate date);
}
