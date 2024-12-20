package com.hospital.bookingcare.controller;

import java.sql.Blob;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.hospital.bookingcare.exception.PhotoRetrievalExcetion;
import com.hospital.bookingcare.model.Booking;
import com.hospital.bookingcare.model.DoctorInfor;
import com.hospital.bookingcare.model.History;
import com.hospital.bookingcare.model.Markdown;
import com.hospital.bookingcare.model.Schedule;
import com.hospital.bookingcare.model.User;
import com.hospital.bookingcare.response.DataMailDTO;
import com.hospital.bookingcare.response.DoctorProfileResponse;
import com.hospital.bookingcare.response.ScheduleResponse;
import com.hospital.bookingcare.response.UserResponse;
import com.hospital.bookingcare.service.BookingService;
import com.hospital.bookingcare.service.DoctorInforService;
import com.hospital.bookingcare.service.HistoryService;
import com.hospital.bookingcare.service.IUserService;
import com.hospital.bookingcare.service.MarkdownService;
import com.hospital.bookingcare.service.ScheduleService;
import com.hospital.bookingcare.service.Email.EmailService;

import lombok.RequiredArgsConstructor;

@CrossOrigin("http://localhost:5274")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class DoctorController {
	
	private final IUserService userService;
	private final MarkdownService markdownService;
	private final ScheduleService scheduleService;
	private final DoctorInforService doctorInforService;
	private final BookingService bookingService;
	private final EmailService emailService;
	private final HistoryService historyService;
	
	@Autowired
    public DoctorController(IUserService userService,MarkdownService markdownService,ScheduleService scheduleService,
    		DoctorInforService doctorInforService,BookingService bookingService,EmailService emailService,HistoryService historyService) {
        this.userService = userService;
        this.markdownService= markdownService;
        this.scheduleService = scheduleService;
        this.doctorInforService = doctorInforService;
        this.bookingService = bookingService;
        this.emailService = emailService;
        this.historyService = historyService;
    }
	
	@GetMapping("/top-10-doctors")
    public ResponseEntity<List<UserResponse>> getTop10Doctors() throws SQLException {
        List<User> doctors = userService.getTop10Doctors();
        List<UserResponse> userResponses = doctors.stream()
		        .map(user -> new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getAddress(),
						user.isGender(), user.getEmail(), user.getPassword(), user.getRoleId(), user.getPhoneNumber(), user.getPositionId())) // Giả sử UserResponse có constructor này
		        .collect(Collectors.toList());
        for(User user : doctors) {
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
	
	@GetMapping("/all-doctors")
	public ResponseEntity<List<UserResponse>> getAllDoctors() throws SQLException {
        List<User> doctors = userService.getAllDoctors();
        List<UserResponse> userResponses = doctors.stream()
		        .map(user -> new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getAddress(),
						user.isGender(), user.getEmail(), user.getPassword(), user.getRoleId(), user.getPhoneNumber(), user.getPositionId())) // Giả sử UserResponse có constructor này
		        .collect(Collectors.toList());
        for(User user : doctors) {
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
	
	// Lưu Mardown
	@PostMapping("/save-infor-doctor")
    public ResponseEntity<Markdown> saveMarkdown(@RequestBody Markdown markdown) {
        Markdown savedMarkdown = markdownService.saveMarkdown(markdown);
        return new ResponseEntity<>(savedMarkdown, HttpStatus.CREATED);
    }
	
	@PutMapping("/update-infor-doctor/{doctorId}")
    public ResponseEntity<?> updateMarkdown(@PathVariable Long doctorId, @RequestBody Markdown markdown) {
        Markdown existingMarkdown = markdownService.getMarkdownByDoctorId(doctorId);
        if (existingMarkdown == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Markdown not found.");
        }

        // Cập nhật các thuộc tính của Markdown
        existingMarkdown.setContentMarkdown(markdown.getContentMarkdown());
        existingMarkdown.setContentHTML(markdown.getContentHTML());
        existingMarkdown.setDescription(markdown.getDescription());
        existingMarkdown.setSpecialtyId(markdown.getSpecialtyId());
        existingMarkdown.setClinicId(markdown.getClinicId());

        Markdown updatedMarkdown = markdownService.saveMarkdown(existingMarkdown);

        return ResponseEntity.ok(updatedMarkdown);
    }
	
	@GetMapping("/markdown/{doctorId}")
    public ResponseEntity<?> getMarkdownByDoctorId(@PathVariable Long doctorId) {
        try {
            Markdown markdown = markdownService.getMarkdownByDoctorId(doctorId);
            if (markdown == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Markdown not found.");
            }
            return ResponseEntity.ok(markdown);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while retrieving markdown.");
        }
    }
	
	// Lấy thông tin chi tiết bác sĩ
	@GetMapping("doctor/{id}")
    public ResponseEntity<UserResponse> getDoctorDetail(@PathVariable Long id) {
        return userService.getDoctorDetail(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
	
	@PostMapping("/create-schedule")
	public ResponseEntity<?> createSchedules(@RequestBody List<Schedule> schedules) {
	    try {
	        List<Schedule> createdSchedules = scheduleService.createSchedules(schedules);
	        
	        if (createdSchedules.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No schedules were created.");
	        }
	        
	        List<ScheduleResponse> response = createdSchedules.stream()
	                .map(schedule -> {
	                    ScheduleResponse scheduleResponse = new ScheduleResponse();
	                    scheduleResponse.setId(schedule.getId());
	                    scheduleResponse.setCurrentNumber(schedule.getCurrentNumber());
	                    scheduleResponse.setMaxNumber(schedule.getMaxNumber());
	                    scheduleResponse.setDate(schedule.getDate());
	                    if (schedule.getTimeTypes() != null) {
	                        scheduleResponse.setTimeTypes(schedule.getTimeTypes().getValueVi());  // Chuyển Allcodes sang String
	                    }
	                    scheduleResponse.setDoctorId(schedule.getDoctorId());
	                    return scheduleResponse;
	                })
	                .collect(Collectors.toList());

	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        // Log the exception
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while creating schedules.");
	    }
	}
	
	@PutMapping("/update-schedule")
	public ResponseEntity<?> updateSchedules(@RequestBody List<Schedule> schedules) {
		try {
	        // Gọi service để cập nhật currentNumber
	        List<Schedule> updatedSchedules = scheduleService.updateSchedules(schedules);

	        if (updatedSchedules.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No schedules were updated.");
	        }

	        // Tạo danh sách response trả về
	        List<ScheduleResponse> response = updatedSchedules.stream()
	                .map(schedule -> {
	                    ScheduleResponse scheduleResponse = new ScheduleResponse();
	                    scheduleResponse.setId(schedule.getId());
	                    scheduleResponse.setCurrentNumber(schedule.getCurrentNumber());
	                    scheduleResponse.setMaxNumber(schedule.getMaxNumber()); // Giữ nguyên giá trị cũ
	                    scheduleResponse.setDate(schedule.getDate()); // Giữ nguyên giá trị cũ
	                    if (schedule.getTimeTypes() != null) {
	                        scheduleResponse.setTimeTypes(schedule.getTimeTypes().getValueVi());
	                    }
	                    scheduleResponse.setDoctorId(schedule.getDoctorId()); // Giữ nguyên giá trị cũ
	                    return scheduleResponse;
	                })
	                .collect(Collectors.toList());

	        return ResponseEntity.ok(response);
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating schedules.");
	    }
	}
	
	@PutMapping("/update-current-number")
    public ResponseEntity<?> updateCurrentNumber(@RequestParam String timeTypes,
                                                 @RequestParam String date,
                                                 @RequestParam Long newCurrentNumber) {
        try {
            // Chuyển đổi date từ String sang LocalDate
            LocalDate parsedDate = LocalDate.parse(date);

            // Gọi service để cập nhật currentNumber
            Schedule updatedSchedule = scheduleService.updateCurrentNumber(timeTypes, parsedDate, newCurrentNumber);

            // Trả về kết quả
            return ResponseEntity.ok(updatedSchedule);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

	@DeleteMapping("/delete-schedules")
	public ResponseEntity<?> deleteSchedules(@RequestBody List<Long> scheduleIds) {
	    try {
	        boolean isDeleted = scheduleService.deleteSchedulesByIds(scheduleIds);
	        if (isDeleted) {
	            return ResponseEntity.ok("Schedules deleted successfully.");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Some or all schedules not found.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while deleting schedules.");
	    }
	}

	
	@PostMapping("/create-schedule-extra")
	public ResponseEntity<?> createDoctorInfor(@RequestBody DoctorInfor doctorInfor) {
	    try {
	        DoctorInfor savedDoctorInfor = doctorInforService.saveDoctorInfor(doctorInfor);

	        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctorInfor);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while creating doctor information.");
	    }
	}
	
	@PutMapping("/update-schedule-extra/{doctorId}")
    public ResponseEntity<?> updateDoctorInfor(@PathVariable Long doctorId, @RequestBody DoctorInfor doctorInfor) {
        try {
            DoctorInfor existingDoctorInfor = doctorInforService.getDoctorInforByDoctorId(doctorId);
            if (existingDoctorInfor == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor information not found.");
            }

            // Cập nhật các thuộc tính của DoctorInfor
            
            existingDoctorInfor.setPriceId(doctorInfor.getPriceId());
            existingDoctorInfor.setProvinceId(doctorInfor.getProvinceId());
            existingDoctorInfor.setPaymentId(doctorInfor.getPaymentId());
            existingDoctorInfor.setSpecialtyId(doctorInfor.getSpecialtyId());
            existingDoctorInfor.setClinicId(doctorInfor.getClinicId());
            existingDoctorInfor.setAddressClinic(doctorInfor.getAddressClinic());
            existingDoctorInfor.setNameClinic(doctorInfor.getNameClinic());
            existingDoctorInfor.setNote(doctorInfor.getNote());
            existingDoctorInfor.setCount(doctorInfor.getCount());

            DoctorInfor updatedDoctorInfor = doctorInforService.saveDoctorInfor(existingDoctorInfor);

            return ResponseEntity.ok(updatedDoctorInfor);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while updating doctor information.");
        }
    }
	
	@GetMapping("/schedule-extra/{doctorId}")
    public ResponseEntity<?> getDoctorInforById(@PathVariable Long doctorId) {
        try {
            DoctorInfor doctorInfor = doctorInforService.getDoctorInforByDoctorId(doctorId);
            if (doctorInfor == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor information not found.");
            }
            return ResponseEntity.ok(doctorInfor);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while fetching doctor information.");
        }
    }

	
	@GetMapping("/schedule-doctor-by-date")
	public ResponseEntity<List<ScheduleResponse>> getScheduleByDate(
	        @RequestParam Long doctorId, 
	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
	    try {
	        List<Schedule> schedules = scheduleService.getSchedulesByDoctorAndDate(doctorId, date);
	        
	        if (schedules.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	        }

	        List<ScheduleResponse> response = schedules.stream()
	            .map(schedule -> {
	                ScheduleResponse scheduleResponse = new ScheduleResponse();
	                scheduleResponse.setId(schedule.getId());
	                scheduleResponse.setCurrentNumber(schedule.getCurrentNumber());
	                scheduleResponse.setMaxNumber(schedule.getMaxNumber());
	                scheduleResponse.setDate(schedule.getDate());
	                
	                // Lấy thông tin bác sĩ từ đối tượng User
	                if (schedule.getDoctor() != null) {
	                    User doctor = schedule.getDoctor();
	                    scheduleResponse.setDoctorId(doctor.getId());
	                    scheduleResponse.setDoctorName(doctor.getFirstName() + " " + doctor.getLastName()); // Lấy tên bác sĩ
	                }

	                if (schedule.getTimeTypes() != null) {
	                    scheduleResponse.setTimeTypes(schedule.getTimeTypes().getValueVi());  // Map giá trị valueVi
	                }
	                return scheduleResponse;
	            })
	            .collect(Collectors.toList());

	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}


	@GetMapping("/profile-doctor/{doctorId}")
	public ResponseEntity<?> getProfileByDoctor(@PathVariable Long doctorId) {
	    try {
	        // Lấy thông tin bác sĩ từ UserService
	        Optional<UserResponse> userResponseOptional = userService.getDoctorDetail(doctorId);
	        if (!userResponseOptional.isPresent()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found.");
	        }
	        UserResponse userResponse = userResponseOptional.get();

	        // Lấy thông tin từ bảng DoctorInfor
	        DoctorInfor doctorInfor = doctorInforService.getDoctorInforByDoctorId(doctorId);
	        if (doctorInfor == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor information not found.");
	        }

	        // Tạo đối tượng chứa thông tin DoctorInfor
	        ScheduleResponse doctorInforResponse = new ScheduleResponse(
	            doctorInfor.getPriceId(),
	            doctorInfor.getProvinceId(),
	            doctorInfor.getPaymentId(),
	            doctorInfor.getAddressClinic(),
	            doctorInfor.getNameClinic(),
	            doctorInfor.getNote(),
	            doctorInfor.getCount()
	        );

	        // Tạo đối tượng kết hợp để trả về
	        DoctorProfileResponse doctorProfileResponse = new DoctorProfileResponse(userResponse, doctorInforResponse);

	        return ResponseEntity.ok(doctorProfileResponse);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while retrieving doctor profile.");
	    }
	}

	@PostMapping("/send-remedy")
	public ResponseEntity<?> sendRemedy(@RequestBody History request) {
	    Optional<Booking> bookingOpt = bookingService.findAndUpdateBooking(
	        request.getDoctorId(),
	        request.getPatientId(),
	        request.getTimeTypes(),
	        "S2"
	    );

	    if (bookingOpt.isPresent()) {
	        Booking booking = bookingOpt.get();

	        try {
	            String base64Image = request.getFileBase64();

	            // Chuyển đổi timestamp thành ngày định dạng "dd/MM/yyyy"
	            String formattedDate = LocalDateTime.ofInstant(
	                Instant.ofEpochMilli(Long.parseLong(booking.getDate())),
	                ZoneId.systemDefault()
	            ).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

	            // Lưu thông tin đơn thuốc
	            History remedy = new History();
	            remedy.setDoctorId(request.getDoctorId());
	            remedy.setPatientId(request.getPatientId());
	            remedy.setDescription(request.getDescription()); // Thêm phần mô tả
	            remedy.setFileBase64(base64Image);
	            historyService.save(remedy); // Gọi service lưu thông tin

	            // Tạo DTO cho email
	            DataMailDTO dataMail = new DataMailDTO();
	            dataMail.setTo(request.getEmail());
	            dataMail.setSubject("Đơn thuốc từ bác sĩ");

	            Map<String, Object> props = new HashMap<>();
	            props.put("patientName", request.getFirstName());
	            props.put("doctorName", request.getDoctorId());
	            props.put("bookingDate", formattedDate);
	            dataMail.setProps(props);

	            dataMail.setBase64Image(base64Image);

	            // Gửi email
	            emailService.sendHtmlMail(dataMail, "remedyTemplate");

	            return ResponseEntity.ok("Remedy sent, saved, and Booking status updated to S3.");
	        } catch (Exception e) {
	            return ResponseEntity.status(500).body("Error sending email or saving remedy: " + e.getMessage());
	        }
	    } else {
	        return ResponseEntity.status(404).body("Booking not found or status is not S2.");
	    }
	}
	
	@GetMapping("/history-patient/{patientId}")
    public ResponseEntity<?> getPatientHistory(@PathVariable Long patientId) {
        List<History> historyList = historyService.getHistoryByPatientId(patientId);
        if (!historyList.isEmpty()) {
            return ResponseEntity.ok(historyList);
        } else {
            return ResponseEntity.status(404).body("No history found for this patient.");
        }
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
}
