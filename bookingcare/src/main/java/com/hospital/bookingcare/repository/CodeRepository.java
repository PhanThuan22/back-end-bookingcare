package com.hospital.bookingcare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.bookingcare.model.Allcodes;


public interface CodeRepository extends JpaRepository<Allcodes, Long> {
	 List<Allcodes> findByType(String type);

	Allcodes findByKeyMap(String keyMap);
	Allcodes findByValueVi(String valueVi);

}
