package com.hospital.bookingcare.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hospital.bookingcare.model.Clinic;
import com.hospital.bookingcare.model.DoctorInfor;
import com.hospital.bookingcare.model.Specialty;
import com.hospital.bookingcare.repository.ClinicRepository;
import com.hospital.bookingcare.response.ClinicDTO;
import com.hospital.bookingcare.service.ClinicService;
import com.hospital.bookingcare.service.DoctorInforService;


import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;

@CrossOrigin("http://localhost:5274")
@RestController
@RequestMapping("/clinic")
@RequiredArgsConstructor
public class ClinicController {
	
	private final ClinicService clinicService;
	private final DoctorInforService doctorInforService;
	
	@Autowired
	private ClinicRepository clinicRepository;
    public ClinicController(ClinicService clinicService, DoctorInforService doctorInforService) {
        this.clinicService = clinicService;
        this.doctorInforService = doctorInforService;
        
    }

	
	@PostMapping("/create-new")
	public ResponseEntity<?> createClinic(
            @RequestParam("name") String name,
            @RequestParam("descriptionHTML") String descriptionHTML,
            @RequestParam("descriptionMarkdown") String descriptionMarkdown,
            @RequestParam("address") String address,
            @RequestParam("image") MultipartFile imageFile
    ) throws SerialException, SQLException, java.io.IOException {
        try {
            // Chuyển đổi MultipartFile thành Blob
            Clinic clinic = new Clinic();
            clinic.setName(name);
            clinic.setDescriptionHTML(descriptionHTML);
            clinic.setDescriptionMarkdown(descriptionMarkdown);
            clinic.setAddress(address);
            clinic.setImage(new javax.sql.rowset.serial.SerialBlob(imageFile.getBytes()));

            // Lưu Specialty vào database
            clinicRepository.save(clinic);

            return ResponseEntity.ok("Clinic created successfully!");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to create specialty");
        }
    }
	
	@GetMapping("/all-clinic")
	public ResponseEntity<?> getAllClinic() {
	    try {
	        List<Clinic> clinics = clinicRepository.findAll();
	        List<ClinicDTO> clinicDTOs = new ArrayList<>();

	        for (Clinic clinic : clinics) {
	        	clinicDTOs.add(new ClinicDTO(clinic));
	        }

	        return ResponseEntity.ok(clinicDTOs);
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("Failed to fetch specialties");
	    }
	}
	
	@GetMapping("/detail/{clinicId}")
	public ResponseEntity<?> getDetailClinic(
	    @PathVariable Long clinicId) {
	    try {
	        // Lấy thông tin chuyên khoa
	        Clinic clinic = clinicService.getClinicById(clinicId);
	        if (clinic == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Clinic not found.");
	        }

	        List<DoctorInfor> doctorInfors;
	        // Kiểm tra giá trị của location	       
	        doctorInfors = doctorInforService.getDoctorsByClinicId(clinicId);
	       
	        // Tạo DTO và thiết lập danh sách bác sĩ
	        ClinicDTO clinicDTO = new ClinicDTO(clinic);
	        clinicDTO.setDoctorInfors(doctorInfors);
	        return ResponseEntity.ok(clinicDTO);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while fetching specialty information.");
	    }
	}
	
	@PutMapping("/update/{clinicId}")
    public ResponseEntity<?> updateClinic(
            @PathVariable Long clinicId,
            @RequestParam("name") String name,
            @RequestParam("descriptionHTML") String descriptionHTML,
            @RequestParam("descriptionMarkdown") String descriptionMarkdown,
            @RequestParam("address") String address,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) throws SerialException, SQLException, java.io.IOException {
        try {
            Clinic clinic = clinicService.getClinicById(clinicId);
            if (clinic == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Clinic not found.");
            }

            clinic.setName(name);
            clinic.setDescriptionHTML(descriptionHTML);
            clinic.setDescriptionMarkdown(descriptionMarkdown);
            clinic.setAddress(address);

            if (imageFile != null && !imageFile.isEmpty()) {
                clinic.setImage(new javax.sql.rowset.serial.SerialBlob(imageFile.getBytes()));
            }

            clinicRepository.save(clinic);

            return ResponseEntity.ok("Clinic updated successfully!");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to update specialty");
        }
    }
	
	@DeleteMapping("/delete/{clinicId}")
    public ResponseEntity<?> deleteClinic(@PathVariable Long clinicId) {
        try {
            Clinic clinic = clinicService.getClinicById(clinicId);
            if (clinic == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Clinic not found.");
            }

            clinicRepository.delete(clinic);

            return ResponseEntity.ok("Clinic deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while deleting clinic.");
        }
    }
	
}
