package com.hospital.bookingcare.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.sql.rowset.serial.SerialException;

import org.springframework.web.multipart.MultipartFile;

import com.hospital.bookingcare.model.User;
import com.hospital.bookingcare.response.UserResponse;

public interface IUserService {
	User registerUser(User user);
    List<User> getUsers();
    void deleteUser(Long id);
    User getUser(String email);
    User addNewUser(MultipartFile file, String firstName, String lastName, String address, Boolean gender, String email,
			String password, String roleId, String phoneNumber, String positionId)
			throws SerialException, SQLException, IOException;
	User updateUser(Long userId, byte[] photoBytes, String firstName, String lastName, String address,
			Boolean gender, String email, String password, String roleId, String phoneNumber, String positionId);
	Optional<User> getUserById(Long userId);
	
	byte[] getUserPhotoByRoomId(Long userId) throws SQLException;
	List<User> getTop10Doctors();
	List<User> getAllDoctors();
	
	Optional<UserResponse> getDoctorDetail(Long id);
	
	 User findOrCreateUserByEmail(String email);
	User save(User user);
	User findOrCreateUserByEmail(String email, String firstName, Boolean gender, String address);

	
	

}
