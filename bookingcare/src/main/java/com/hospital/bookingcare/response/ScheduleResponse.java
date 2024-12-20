package com.hospital.bookingcare.response;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScheduleResponse {
	private Long id;
	private Long currentNumber;
	private Long maxNumber;
	private LocalDate date;
	private String timeTypes;
	private Long doctorId;
	private String priceId;
	private String provinceId;
	private String paymentId;
	private String addressClinic;
	private String nameClinic;
	private String note;
	private Long count;
	private String doctorName;
	
	
	public ScheduleResponse(String priceId, String provinceId, String paymentId, String addressClinic, 
            String nameClinic, String note, Long count) {
				this.priceId = priceId;
				this.provinceId = provinceId;
				this.paymentId = paymentId;
				this.addressClinic = addressClinic;
				this.nameClinic = nameClinic;
				this.note = note;
				this.count = count;
}

	public ScheduleResponse() {
		
	}
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
	public String getTimeTypes() {
		return timeTypes;
	}
	public void setTimeTypes(String timeTypes) {
		this.timeTypes = timeTypes;
	}
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public String getPriceId() {
		return priceId;
	}
	public void setPriceId(String priceId) {
		this.priceId = priceId;
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

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	
	
	
	

}
