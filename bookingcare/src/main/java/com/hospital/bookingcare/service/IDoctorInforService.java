package com.hospital.bookingcare.service;

import com.hospital.bookingcare.model.DoctorInfor;

public interface IDoctorInforService {
	DoctorInfor saveDoctorInfor(DoctorInfor doctorInfor);
    DoctorInfor getDoctorInforById(Long id);

}
