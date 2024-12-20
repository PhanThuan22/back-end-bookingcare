package com.hospital.bookingcare.response;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import com.hospital.bookingcare.model.Clinic;
import com.hospital.bookingcare.model.DoctorInfor;

public class ClinicDTO {
	
	private Long id;
    private String name;
    private String descriptionHTML;
    private String descriptionMarkdown;
    private String imageBase64;
    private String address;
    private List<DoctorInfor> doctorInfors;
	
	public ClinicDTO(Clinic clinic) throws SQLException, IOException {
		this.id = clinic.getId();
        this.name = clinic.getName();
        this.descriptionHTML = clinic.getDescriptionHTML();
        this.descriptionMarkdown = clinic.getDescriptionMarkdown();
        this.address = clinic.getAddress();

        // Convert Blob to Base64 string
        Blob imageBlob = clinic.getImage();
        if (imageBlob != null) {
            InputStream inputStream = imageBlob.getBinaryStream();
            byte[] imageBytes = inputStream.readAllBytes();
            this.imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
        }
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescriptionHTML() {
		return descriptionHTML;
	}

	public void setDescriptionHTML(String descriptionHTML) {
		this.descriptionHTML = descriptionHTML;
	}

	public String getDescriptionMarkdown() {
		return descriptionMarkdown;
	}

	public void setDescriptionMarkdown(String descriptionMarkdown) {
		this.descriptionMarkdown = descriptionMarkdown;
	}

	public String getImageBase64() {
		return imageBase64;
	}

	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<DoctorInfor> getDoctorInfors() {
		return doctorInfors;
	}

	public void setDoctorInfors(List<DoctorInfor> doctorInfors) {
		this.doctorInfors = doctorInfors;
	}
	
	

}
