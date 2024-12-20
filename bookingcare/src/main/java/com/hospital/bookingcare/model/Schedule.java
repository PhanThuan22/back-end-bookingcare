package com.hospital.bookingcare.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Schedule {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long currentNumber;
	private Long maxNumber;
	private LocalDate date;
	
	@Column(name = "doctor_id")
	private Long doctorId;

	
	@ManyToOne
    @JoinColumn(name = "timeTypes", referencedColumnName = "keyMap")
    private Allcodes timeTypes;
	
	@ManyToOne
	@JoinColumn(name = "doctor_id", insertable = false, updatable = false)
    private User doctor;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCurrentNumber() {
		return currentNumber;
	}
	public void setCurrentNumber(Long currentNumber) {
		this.currentNumber = currentNumber;
	}
	public Long getMaxNumber() {
		return maxNumber;
	}
	public void setMaxNumber(Long maxNumber) {
		this.maxNumber = maxNumber;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Allcodes getTimeTypes() {
		return timeTypes;
	}
	public void setTimeTypes(Allcodes timeTypes) {
		this.timeTypes = timeTypes;
	}
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public User getDoctor() {
		return doctor;
	}
	public void setDoctor(User doctor) {
		this.doctor = doctor;
	}
	
	

}
