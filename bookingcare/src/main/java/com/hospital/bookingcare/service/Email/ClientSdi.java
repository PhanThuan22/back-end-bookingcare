package com.hospital.bookingcare.service.Email;

import lombok.Data;

@Data
public class ClientSdi {
    private String name;
    private String username;
    private String email;
    private String tokenBooking;
    private Long doctorId;
    private String verificationLink;
    
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTokenBooking() {
		return tokenBooking;
	}
	public void setTokenBooking(String tokenBooking) {
		this.tokenBooking = tokenBooking;
	}
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public String getVerificationLink() {
		return verificationLink;
	}
	public void setVerificationLink(String verificationLink) {
		this.verificationLink = verificationLink;
	}
    
    
}
