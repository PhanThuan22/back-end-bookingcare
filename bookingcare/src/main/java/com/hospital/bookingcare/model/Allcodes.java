package com.hospital.bookingcare.model;

import java.util.List;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Allcodes {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String type;
	@Column(name = "keyMap")
	private String keyMap;
	@Column(name = "valueEn")
	private String valueEn;
	@Column(name = "valueVi")
	private String valueVi;
	
	@OneToMany(mappedBy = "timeTypes")
    private List<Schedule> schedules;
	
	public Allcodes() {}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKeyMap() {
		return keyMap;
	}
	public void setKeyMap(String keyMap) {
		this.keyMap = keyMap;
	}
	public String getValueEn() {
		return valueEn;
	}
	public void setValueEn(String valueEn) {
		this.valueEn = valueEn;
	}
	public String getValueVi() {
		return valueVi;
	}
	public void setValueVi(String valueVi) {
		this.valueVi = valueVi;
	}
	
	

}
