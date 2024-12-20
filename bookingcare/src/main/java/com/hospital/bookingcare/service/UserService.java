package com.hospital.bookingcare.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.hospital.bookingcare.exception.InternalServerException;
import com.hospital.bookingcare.exception.ResourceNotFoundException;
import com.hospital.bookingcare.exception.UserAlreadyExistsException;
import com.hospital.bookingcare.model.User;
import com.hospital.bookingcare.repository.MarkdownRepository;
import com.hospital.bookingcare.repository.UserRepository;
import com.hospital.bookingcare.response.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
	private final UserRepository userRepository;
	private final MarkdownRepository markdownRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
    public UserService(UserRepository userRepository,MarkdownRepository markdownRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.markdownRepository= markdownRepository;
        this.passwordEncoder = passwordEncoder;
      
    }
	
	@Override
	public User addNewUser(MultipartFile file,String firstName, String lastName, String address,
			Boolean gender, String email, String password, String roleId, String phoneNumber, String positionId) throws SerialException, SQLException, IOException {
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setAddress(address);
		user.setGender(gender);
		user.setEmail(email);
		user.setPassword(password);
		user.setRoleId(roleId);
		user.setPhoneNumber(phoneNumber);
		user.setPositionId(positionId);
		if(!file.isEmpty()) {
			byte[] photoBytes = file.getBytes();
			Blob photoBlob = new SerialBlob(photoBytes);
			user.setPhoto(photoBlob);
		}
		return userRepository.save(user);
	}

	@Override
	public User registerUser(User user) {
		 if (userRepository.existsByEmail(user.getEmail())){
	            throw new UserAlreadyExistsException(user.getEmail() + " already exists");
	        }
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        user.setRoleId("R3");
	        return userRepository.save(user);
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public void deleteUser(Long userId) {
		Optional<User> theRoom = userRepository.findById(userId);
		if(theRoom.isPresent()) {
			userRepository.deleteById(userId);
		}
		
	}
	
	public User updateUser(Long userId,byte[] photoBytes, String firstName, String lastName, String address,
			Boolean gender, String email, String password, String roleId, String phoneNumber, String positionId) {
		User user  = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		if(firstName != null) user.setFirstName(firstName);;
		if(lastName != null) user.setLastName(lastName);;
		if(address != null) user.setAddress(address);;
		if(gender != null) user.setGender(gender);;
		if(email != null) user.setEmail(email);;
		if(password != null) user.setPassword(password);;
		if(roleId != null) user.setRoleId(roleId);;
		if(phoneNumber != null) user.setPhoneNumber(phoneNumber);;
		if(positionId != null) user.setPositionId(positionId);;
		if(photoBytes != null && photoBytes.length > 0) {
			try {
				user.setPhoto(new SerialBlob(photoBytes));
			}catch(SQLException ex) {
				throw new InternalServerException("Error updating user");
			}
		}
		return userRepository.save(user);
	}

	@Override
	public User getUser(String email) {
		return userRepository.findByEmail(email);
                
	}

	@Override
	public Optional<User> getUserById(Long userId) {
		// TODO Auto-generated method stub
		return Optional.of(userRepository.findById(userId).get());
	}

	@Override
	public byte[] getUserPhotoByRoomId(Long userId) throws SQLException {
		Optional<User> theUser = userRepository.findById(userId);
		if(theUser.isEmpty()) {
			throw new ResourceNotFoundException("Sorry, Room not found!");
		}
		Blob photoBlob = theUser.get().getPhoto();
		if(photoBlob != null) {
			return photoBlob.getBytes(1, (int) photoBlob.length());
		}
		return null;
	}

	@Override
	public List<User> getTop10Doctors() {
		return userRepository.findTop10ByRoleId("R2");
	}

	@Override
	public List<User> getAllDoctors() {
		// TODO Auto-generated method stub
		return userRepository.findByRoleId("R2");
	}

	@Override
	public Optional<UserResponse> getDoctorDetail(Long id) {
	    return userRepository.findById(id)
	        .map(user -> {
	            // Lấy thông tin Markdown
	            var markdown = markdownRepository.findByDoctorId(user.getId());
	            byte[] photoBytes = null;
	            try {
	                Blob photoBlob = user.getPhoto();
	                if (photoBlob != null) {
	                    photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
	                }
	            } catch (SQLException e) {
	                throw new InternalServerException("Error retrieving user photo");
	            }

	            // Chuyển đổi đối tượng User sang UserResponse
	            if (markdown.isPresent()) {
	                return new UserResponse(
	                    photoBytes,
	                    user.getId(),
	                    user.getFirstName(),
	                    user.getLastName(),
	                    user.getAddress(),
	                    user.isGender(),
	                    user.getEmail(),
	                    user.getPassword(),
	                    user.getRoleId(),
	                    user.getPhoneNumber(),
	                    user.getPositionId(),
	                    markdown.get().getContentHTML(),
	                    markdown.get().getContentMarkdown(),
	                    markdown.get().getDescription()
	                );
	            } else {
	                // Trường hợp không có thông tin Markdown
	                return new UserResponse(
	                    photoBytes, // Bổ sung photoBytes
	                    user.getId(),
	                    user.getFirstName(),
	                    user.getLastName(),
	                    user.getAddress(),
	                    user.isGender(),
	                    user.getEmail(),
	                    user.getPassword(),
	                    user.getRoleId(),
	                    user.getPhoneNumber(),
	                    user.getPositionId()
	                );
	            }
	        });
	}


	@Override
	public User findOrCreateUserByEmail(String email, String firstName, Boolean gender, String address) {
	    User user = userRepository.findByEmail(email);

	    // Nếu người dùng đã tồn tại, cập nhật thông tin mới
	    if (user != null) {
	        if (firstName != null) {
	            user.setFirstName(firstName);
	        }
	        if (gender != null) {
	            user.setGender(gender);
	        }
	        if (address != null) {
	            user.setAddress(address);
	        }
	        return userRepository.save(user); // Cập nhật và lưu lại người dùng
	    }

	    // Nếu người dùng không tồn tại, tạo người dùng mới
	    User newUser = new User();
	    newUser.setEmail(email);
	    newUser.setRoleId("R3"); 
	    newUser.setFirstName(firstName);
	    newUser.setGender(gender);
	    newUser.setAddress(address);

	    // Lưu người dùng mới vào database
	    return userRepository.save(newUser);
	}


	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public User findOrCreateUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
