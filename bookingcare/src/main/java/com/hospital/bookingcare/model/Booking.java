package com.hospital.bookingcare.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String statusId;
    private Long doctorId;
    
    @Column(name = "patient_id")
    private Long patientId;
    
    @Column(name = "date")
    private String date;
    
    private String timeTypes;
    
    @Column(name = "token_booking", length = 36)
    private String tokenBooking;
    
    @ManyToOne
    @JoinColumn(name = "patient_id", insertable = false, updatable = false)
    private User user;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTimeTypes() {
		return timeTypes;
	}
	public void setTimeTypes(String timeTypes) {
		this.timeTypes = timeTypes;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getTokenBooking() {
		return tokenBooking;
	}
	public void setTokenBooking(String tokenBooking) {
		this.tokenBooking = tokenBooking;
	}
    
    

}
