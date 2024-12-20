package com.hospital.bookingcare.service;

import java.util.List;

import com.hospital.bookingcare.model.Allcodes;

public interface ICodeService {
	List<Allcodes> getAllcodes();
	List<Allcodes> getType(String type);

}
