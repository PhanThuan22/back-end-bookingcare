package com.hospital.bookingcare.controller;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hospital.bookingcare.model.DoctorInfor;
import com.hospital.bookingcare.model.Specialty;
import com.hospital.bookingcare.repository.SpecialtyRepository;
import com.hospital.bookingcare.response.SpecialtyDTO;
import com.hospital.bookingcare.service.DoctorInforService;
import com.hospital.bookingcare.service.SpecialtyService;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;

@CrossOrigin("http://localhost:5274")
@RestController
@RequestMapping("/specialty")
@RequiredArgsConstructor
public class SpecialtyController {
	
	private final SpecialtyService specialtyService;
	private final DoctorInforService doctorInforService;
	
	@Autowired
    private SpecialtyRepository specialtyRepository;
    public SpecialtyController(SpecialtyService specialtyService, DoctorInforService doctorInforService) {
        this.specialtyService = specialtyService;
        this.doctorInforService = doctorInforService;
        
    }
	
	@PostMapping("/create-new")
	public ResponseEntity<?> createSpecialty(
            @RequestParam("name") String name,
            @RequestParam("descriptionHTML") String descriptionHTML,
            @RequestParam("descriptionMarkdown") String descriptionMarkdown,
            @RequestParam("image") MultipartFile imageFile
    ) throws SerialException, SQLException, java.io.IOException {
        try {
            // Chuyển đổi MultipartFile thành Blob
            Specialty specialty = new Specialty();
            specialty.setName(name);
            specialty.setDescriptionHTML(descriptionHTML);
            specialty.setDescriptionMarkdown(descriptionMarkdown);
            specialty.setImage(new javax.sql.rowset.serial.SerialBlob(imageFile.getBytes()));

            // Lưu Specialty vào database
            specialtyRepository.save(specialty);

            return ResponseEntity.ok("Specialty created successfully!");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to create specialty");
        }
    }
	
	@GetMapping("/all-specialty")
	public ResponseEntity<?> getAllSpecialty() {
	    try {
	        List<Specialty> specialties = specialtyRepository.findAll();
	        List<SpecialtyDTO> specialtyDTOs = new ArrayList<>();

	        for (Specialty specialty : specialties) {
	            specialtyDTOs.add(new SpecialtyDTO(specialty));
	        }

	        return ResponseEntity.ok(specialtyDTOs);
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("Failed to fetch specialties");
	    }
	}
	@GetMapping("/detail/{specialtyId}")
	public ResponseEntity<?> getDetailSpecialty(
	    @PathVariable Long specialtyId,
	    @RequestParam(value = "location", defaultValue = "ALL") String location) {
	    try {
	        // Lấy thông tin chuyên khoa
	        Specialty specialty = specialtyService.getSpecialtyById(specialtyId);
	        if (specialty == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Specialty not found.");
	        }

	        List<DoctorInfor> doctorInfors;

	        // Kiểm tra giá trị của location
	        if ("ALL".equalsIgnoreCase(location)) {
	            // Lấy tất cả bác sĩ liên quan đến chuyên khoa
	            doctorInfors = doctorInforService.getDoctorsBySpecialtyId(specialtyId);
	        } else {
	            // Lấy bác sĩ theo provinceId (location)
	            doctorInfors = doctorInforService.getDoctorsBySpecialtyIdAndProvinceId(specialtyId, location);
	        }

	        // Tạo DTO và thiết lập danh sách bác sĩ
	        SpecialtyDTO specialtyDTO = new SpecialtyDTO(specialty);
	        specialtyDTO.setDoctorInfors(doctorInfors);

	        return ResponseEntity.ok(specialtyDTO);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while fetching specialty information.");
	    }
	}
	
	@PutMapping("/update/{specialtyId}")
	public ResponseEntity<?> updateSpecialty(
			@PathVariable Long specialtyId,
			@RequestParam("name") String name,
			@RequestParam("descriptionHTML") String descriptionHTML,
            @RequestParam("descriptionMarkdown") String descriptionMarkdown,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) throws SerialException, SQLException, java.io.IOException{
		try {
			Optional<Specialty> optionalSpecialty = specialtyRepository.findById(specialtyId);
			if(!optionalSpecialty.isPresent()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Specialty not found.");
			}
			
			Specialty specialty = optionalSpecialty.get();
			specialty.setName(name);
			specialty.setDescriptionHTML(descriptionHTML);
			specialty.setDescriptionMarkdown(descriptionMarkdown);
			
			if(imageFile != null && !imageFile.isEmpty()) {
				specialty.setImage(new javax.sql.rowset.serial.SerialBlob(imageFile.getBytes()));
			}
			
			specialtyRepository.save(specialty);
			return ResponseEntity.ok("Specialty updated successfully!");
		}catch(IOException e) {
			return ResponseEntity.badRequest().body("Failed to update specialty.");
		}
	}
	
	@DeleteMapping("/delete/{specialtyId}")
    public ResponseEntity<?> deleteSpecialty(@PathVariable Long specialtyId) {
        try {
            Optional<Specialty> optionalSpecialty = specialtyRepository.findById(specialtyId);
            if (!optionalSpecialty.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Specialty not found.");
            }

            specialtyRepository.deleteById(specialtyId);

            return ResponseEntity.ok("Specialty deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete specialty.");
        }
    }

}
