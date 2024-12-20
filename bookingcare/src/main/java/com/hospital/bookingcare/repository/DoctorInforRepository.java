package com.hospital.bookingcare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.bookingcare.model.DoctorInfor;

public interface DoctorInforRepository extends JpaRepository<DoctorInfor, Long> {

	DoctorInfor findByDoctorId(Long doctorId);

	List<DoctorInfor> findBySpecialtyId(Long specialtyId);

	List<DoctorInfor> findBySpecialtyIdAndProvinceId(Long specialtyId, String provinceId);

	List<DoctorInfor> findByClinicId(Long clinicId);
}
