package com.hospital.bookingcare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.bookingcare.model.DoctorInfor;
import com.hospital.bookingcare.repository.DoctorInforRepository;

@Service
public class DoctorInforService implements IDoctorInforService {
	@Autowired
    private DoctorInforRepository doctorInforRepository;

    public DoctorInfor getByDoctorId(Long doctorId) {
        return doctorInforRepository.findByDoctorId(doctorId);
    }
    
    public DoctorInfor saveDoctorInfor(DoctorInfor doctorInfor) {
        return doctorInforRepository.save(doctorInfor);
    }

	@Override
	public DoctorInfor getDoctorInforById(Long id) {
		// TODO Auto-generated method stub
		return doctorInforRepository.findById(id).orElse(null);
	}

	public DoctorInfor getDoctorInforByDoctorId(Long doctorId) {
		// TODO Auto-generated method stub
		 return doctorInforRepository.findByDoctorId(doctorId);
	}

	public List<DoctorInfor> getDoctorsBySpecialtyId(Long specialtyId) {
        return doctorInforRepository.findBySpecialtyId(specialtyId);
    }

	public List<DoctorInfor> getDoctorsBySpecialtyIdAndProvinceId(Long specialtyId, String provinceId) {
        return doctorInforRepository.findBySpecialtyIdAndProvinceId(specialtyId, provinceId);
    }

	public List<DoctorInfor> getDoctorsByClinicId(Long clinicId) {
		 return doctorInforRepository.findByClinicId(clinicId);
	}
}
