package com.hospital.bookingcare.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



import com.hospital.bookingcare.exception.PhotoRetrievalExcetion;
import com.hospital.bookingcare.exception.ResourceNotFoundException;
import com.hospital.bookingcare.model.Markdown;
import com.hospital.bookingcare.model.Schedule;
import com.hospital.bookingcare.model.User;
import com.hospital.bookingcare.repository.BookingRepository;
import com.hospital.bookingcare.response.ScheduleResponse;
import com.hospital.bookingcare.response.UserResponse;
import com.hospital.bookingcare.service.IUserService;
import com.hospital.bookingcare.service.MarkdownService;
import com.hospital.bookingcare.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@CrossOrigin("http://localhost:5274")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final IUserService userService;
	private final MarkdownService markdownService;
	private final ScheduleService scheduleService;
	
	@Autowired
	private BookingRepository bookingRepository;
    public UserController(IUserService userService,MarkdownService markdownService,ScheduleService scheduleService) {
        this.userService = userService;
        this.markdownService= markdownService;
        this.scheduleService = scheduleService;
       
    }
	
	@PostMapping("/add/new-user")
	public ResponseEntity<UserResponse> addNewUser(
			@RequestParam("photo") MultipartFile photo,
			@RequestParam("firstName") String firstName, 
			@RequestParam("lastName")String lastName, 
			@RequestParam("address")String address, 
			@RequestParam("gender")Boolean gender, 
			@RequestParam("email")String email, 
			@RequestParam("password")String password, 
			@RequestParam("roleId")String roleId, 
			@RequestParam("phoneNumber")String phoneNumber, 
			@RequestParam("positionId")String positionId)throws SerialException, SQLException, IOException{
		User savedUser = userService.addNewUser(photo,firstName, lastName, address, gender, email, password, roleId, phoneNumber, positionId);
		UserResponse response = new UserResponse(savedUser.getId(), savedUser.getFirstName(), savedUser.getLastName(), savedUser.getAddress(),
				savedUser.isGender(), savedUser.getEmail(), savedUser.getPassword(), savedUser.getRoleId(), savedUser.getPhoneNumber(), savedUser.getPositionId());
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/all-user") 
	public ResponseEntity<List<UserResponse>> getAllUsers() throws SQLException{
		List<User> users = userService.getUsers();
		List<UserResponse> userResponses = users.stream()
		        .map(user -> new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getAddress(),
						user.isGender(), user.getEmail(), user.getPassword(), user.getRoleId(), user.getPhoneNumber(), user.getPositionId())) // Giả sử UserResponse có constructor này
		        .collect(Collectors.toList());
		for(User user : users) {
			byte[] photoBytes = userService.getUserPhotoByRoomId(user.getId());
			if(photoBytes != null && photoBytes.length > 0) {
				String base64Photo = Base64.encodeBase64String(photoBytes);
				UserResponse userResponse = getUserResponse(user);
				userResponse.setPhoto(base64Photo);
				userResponses.add(userResponse);
			}
		}
		
		return ResponseEntity.ok(userResponses);
	}
	
	@GetMapping("/{email}")
	@PreAuthorize("hasRole('R1')")
	public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email){
        try{
            User theUser = userService.getUser(email);
            return ResponseEntity.ok(theUser);
        //}catch (UsernameNotFoundException e){
          //  return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user");
        }
    }
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<Optional<UserResponse>> getUserById(@PathVariable Long userId){
		Optional<User> theUser = userService.getUserById(userId);
		return theUser.map(user -> {
			UserResponse userResponse =  getUserResponse(user);
			return ResponseEntity.ok(Optional.of(userResponse));
		
		}).orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}
	
	@PutMapping("/update/{userId}")
	public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId, 
			@RequestParam(required = false) MultipartFile photo,
			@RequestParam(required = false) String firstName, 
			@RequestParam(required = false)String lastName, 
			@RequestParam(required = false)String address, 
			@RequestParam(required = false)Boolean gender, 
			@RequestParam(required = false)String email, 
			@RequestParam(required = false)String password, 
			@RequestParam(required = false)String roleId, 
			@RequestParam(required = false)String phoneNumber, 
			@RequestParam(required = false)String positionId) throws IOException, SQLException{
		byte[] photoBytes = photo != null && !photo.isEmpty()? 
				photo.getBytes() : userService.getUserPhotoByRoomId(userId);
		Blob photoBlob = photoBytes != null && photoBytes.length > 0 ? new SerialBlob(photoBytes) : null;
		User theUser = userService.updateUser(userId,photoBytes, firstName, lastName, address, gender, email, password, roleId, phoneNumber, positionId);
		theUser.setPhoto(photoBlob);
		UserResponse userResponse = getUserResponse(theUser);
		return ResponseEntity.ok(userResponse);
		
	}
	
	private UserResponse getUserResponse(User theUser) {
	    // Trích xuất thông tin từ đối tượng User
	    Long id = theUser.getId();
	    String firstName = theUser.getFirstName();
	    String lastName = theUser.getLastName();
	    String address = theUser.getAddress();
	    Boolean gender = theUser.isGender();
	    String email = theUser.getEmail();
	    String password = theUser.getPassword();
	    String roleId = theUser.getRoleId();
	    String phoneNumber = theUser.getPhoneNumber();
	    String positionId = theUser.getPositionId();
	    
	    byte[] photoBytes = null;
		Blob photoBlob = theUser.getPhoto();
		if(photoBlob != null) {
			try {
				photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
			}catch(SQLException e) {
				throw new PhotoRetrievalExcetion("Error retrieving photo");
			}
		}

	    // Tạo đối tượng UserResponse với các thông tin đã trích xuất
	    return new UserResponse(photoBytes, id, firstName, lastName, address, gender, email, password, roleId, phoneNumber, positionId);
	}


	@DeleteMapping("/delete/{userId}")
	@PreAuthorize("hasAuthority('R1')")
	public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
		userService.deleteUser(userId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	


}
