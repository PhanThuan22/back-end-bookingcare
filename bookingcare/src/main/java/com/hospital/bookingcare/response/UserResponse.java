package com.hospital.bookingcare.response;

import java.sql.Blob;

import org.apache.tomcat.util.codec.binary.Base64;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
	private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private Boolean gender;
    private String email;
    private String password; 
    private String roleId;
    private String phoneNumber;
    private String positionId;
    private String photo;
    
    private String description;
    private String contentHTML;
    private String contentMarkdown;
    
    
    public UserResponse( Long id, String firstName, String lastName, String address, Boolean gender, String email,
			String password, String roleId, String phoneNumber, String positionId) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.gender = gender;
		this.email = email;
		this.password = password;
		this.roleId = roleId;
		this.phoneNumber = phoneNumber;
		this.positionId = positionId;
	}
    
    
	public UserResponse(byte[] photoBytes, Long id, String firstName, String lastName, String address, Boolean gender, String email,
			String password, String roleId, String phoneNumber, String positionId) {
		super();
		this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes) : null;
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.gender = gender;
		this.email = email;
		this.password = password;
		this.roleId = roleId;
		this.phoneNumber = phoneNumber;
		this.positionId = positionId;
	}
	
	public UserResponse(byte[] photoBytes,Long id, String firstName, String lastName, String address, Boolean gender, String email,
            String password, String roleId, String phoneNumber, String positionId, String contentHTML,
            String contentMarkdown, String description) {
		this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes) : null;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
        this.phoneNumber = phoneNumber;
        this.positionId = positionId;
        this.contentHTML = contentHTML;
        this.contentMarkdown = contentMarkdown;
        this.description = description;
    }


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Boolean isGender() {
		return gender;
	}


	public void setGender(Boolean gender) {
		this.gender = gender;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getRoleId() {
		return roleId;
	}


	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getPositionId() {
		return positionId;
	}


	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}


	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
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
	
	
	
	
	
    
    

}
