package com.hospital.bookingcare.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Markdown {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private String description;
	private Long doctorId;
	private Long specialtyId;
	private Long clinicId;
	
	@Lob
	@Column(columnDefinition = "TEXT")
	private String contentHTML;
	
	@Lob
	@Column(columnDefinition = "TEXT")
	private String contentMarkdown;
	
	@OneToOne
    @JoinColumn(name = "id")
    private User user;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContentHTML() {
		return contentHTML;
	}
	public void setContentHTML(String contentHTML) {
		this.contentHTML = contentHTML;
	}
	public String getContentMarkdown() {
		return contentMarkdown;
	}
	public void setContentMarkdown(String contentMarkdown) {
		this.contentMarkdown = contentMarkdown;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public Long getSpecialtyId() {
		return specialtyId;
	}
	public void setSpecialtyId(Long specialtyId) {
		this.specialtyId = specialtyId;
	}
	public Long getClinicId() {
		return clinicId;
	}
	public void setClinicId(Long clinicId) {
		this.clinicId = clinicId;
	}
	
	

}
