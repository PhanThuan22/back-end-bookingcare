package com.hospital.bookingcare.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.bookingcare.model.Booking;
import com.hospital.bookingcare.model.DoctorInfor;
import com.hospital.bookingcare.model.User;
import com.hospital.bookingcare.service.BookingService;
import com.hospital.bookingcare.service.DoctorInforService;
import com.hospital.bookingcare.service.IUserService;
import com.hospital.bookingcare.service.MarkdownService;
import com.hospital.bookingcare.service.ScheduleService;

import lombok.RequiredArgsConstructor;


@CrossOrigin("http://localhost:5274")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class PatientController {
	private final IUserService userService;
	private final MarkdownService markdownService;
	private final ScheduleService scheduleService;
	private final DoctorInforService doctorInforService;
	private final BookingService bookingService;
	
	@Autowired
    public PatientController(IUserService userService,MarkdownService markdownService,ScheduleService scheduleService,DoctorInforService doctorInforService,BookingService bookingService) {
        this.userService = userService;
        this.markdownService= markdownService;
        this.scheduleService = scheduleService;
        this.doctorInforService = doctorInforService;
        this.bookingService = bookingService;
       
    }
	
	@PostMapping("/patient-book-appointment")
	public ResponseEntity<?> createBookAppointment(@RequestBody Booking booking) {
	    // Kiểm tra email và tạo người dùng mới nếu chưa có, bao gồm cả firstName, gender và address
	    User user = userService.findOrCreateUserByEmail(
	        booking.getUser().getEmail(),
	        booking.getUser().getFirstName(),
	        booking.getUser().isGender(),
	        booking.getUser().getAddress()
	    );

	    // Lưu đối tượng User trước khi gán vào Booking
	    if (user.getId() == null) {
	        user = userService.save(user); // Lưu user vào cơ sở dữ liệu
	    }

	    // Gán userId cho booking
	    booking.setPatientId(user.getId());
	    booking.setUser(user); // Gán user đã lưu vào booking

	    // Lưu thông tin đặt lịch khám
	    Booking savedBooking = scheduleService.createBooking(booking);

	    // Trả về kết quả
	    return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
	}

	
	@PostMapping("/verify-book-appointment")
    public ResponseEntity<?> verifyBookAppointment(@RequestParam Long doctorId, @RequestParam String tokenBooking) {
        boolean isVerified = bookingService.verifyBooking(doctorId, tokenBooking);
        
        if (isVerified) {
            return new ResponseEntity<>("Lịch hẹn đã được xác thực thành công.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Không tìm thấy lịch hẹn hoặc token không hợp lệ.", HttpStatus.BAD_REQUEST);
        }
    }
	
	@DeleteMapping("/delete-booking/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        boolean isDeleted = bookingService.deleteBooking(id);
        if (isDeleted) {
            return ResponseEntity.ok("Xóa lịch hẹn thành công.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lịch hẹn không tồn tại.");
        }
    }
	
	@GetMapping("/list-patient-for-doctor")
    public ResponseEntity<?> getListPatientForDoctor(@RequestParam Long doctorId, 
            @RequestParam Long timestamp) { // Nhận timestamp thay vì LocalDate
        try {
            // Gọi service với doctorId và timestamp
            List<Booking> bookings = bookingService.getListPatientForDoctor(doctorId, timestamp);
           
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error occurred while fetching patient information.");
        }
    }
	
	@GetMapping("/list-booking-for-patient")
    public ResponseEntity<?> getBookingByPatientId(@RequestParam Long patientId) {
        try {
            // Gọi service để lấy danh sách booking theo doctorId
            List<Booking> bookings = bookingService.getBookingByPatient(patientId);

            // Trả về danh sách booking
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error occurred while fetching patient information.");
        }
    }
}
