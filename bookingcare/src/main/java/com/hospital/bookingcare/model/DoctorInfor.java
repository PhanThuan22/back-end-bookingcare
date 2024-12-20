package com.hospital.bookingcare.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class DoctorInfor {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;	
	private Long doctorId;
	private Long specialtyId;
	private Long clinicId;
	private String priceId;
	private String provinceId;
	private String paymentId;
	private String addressClinic;
	private String nameClinic;
	private String note;
	private Long count;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getAddressClinic() {
		return addressClinic;
	}
	public void setAddressClinic(String addressClinic) {
		this.addressClinic = addressClinic;
	}
	public String getNameClinic() {
		return nameClinic;
	}
	public void setNameClinic(String nameClinic) {
		this.nameClinic = nameClinic;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public String getPriceId() {
		return priceId;
	}
	public void setPriceId(String priceId) {
		this.priceId = priceId;
	}
	
	

}
