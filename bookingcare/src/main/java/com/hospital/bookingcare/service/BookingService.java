package com.hospital.bookingcare.service;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.bookingcare.model.Booking;
import com.hospital.bookingcare.repository.BookingRepository;

@Service
public class BookingService {
	
	@Autowired
    private BookingRepository bookingRepository;

    public boolean verifyBooking(Long doctorId, String tokenBooking) {
     
        Booking appointment = bookingRepository.findByDoctorIdAndTokenBooking(doctorId, tokenBooking);
        if (appointment != null) {
            // Kiểm tra nếu token hợp lệ và cập nhật trạng thái
            appointment.setStatusId("S2");
            bookingRepository.save(appointment);
            return true;
        }
        return false;
    }
    
    public List<Booking> getListPatientForDoctor(Long doctorId, Long timestamp) {
        // Chuyển đổi timestamp thành LocalDate
    	 String date = String.valueOf(timestamp);
        // Lấy danh sách booking dựa trên doctorId và ngày
        return bookingRepository.findByStatusIdAndDoctorIdAndDate("S2", doctorId, date);
    }
    
    public Optional<Booking> findBooking(Long doctorId, Long patientId, String timeTypes, String statusId) {
        return bookingRepository.findByDoctorIdAndPatientIdAndTimeTypesAndStatusId(doctorId, patientId, timeTypes, statusId);
    }
    
    public Optional<Booking> findAndUpdateBooking(Long doctorId, Long patientId, String timeTypes, String statusId) {
        Optional<Booking> bookingOpt = bookingRepository.findByDoctorIdAndPatientIdAndTimeTypesAndStatusId(
            doctorId, patientId, timeTypes, statusId
        );

        // Kiểm tra xem booking có tồn tại hay không
        if (bookingOpt.isPresent()) {
            // Nếu tìm thấy, cập nhật statusId và lưu lại
            Booking booking = bookingOpt.get();
            booking.setStatusId("S3");
            bookingRepository.save(booking);
        } else {
            
            System.out.println("No booking found for the given parameters.");
        }

        return bookingOpt;
    }
    
    public boolean deleteBooking(Long bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isPresent()) {
            bookingRepository.deleteById(bookingId);
            return true;
        } else {
            System.out.println("Booking with ID " + bookingId + " not found.");
            return false;
        }
    }

    
    public List<Booking> getBookingByPatient(Long patientId) {
        // Gọi repository để lấy danh sách booking theo doctorId
        return bookingRepository.findByPatientId(patientId);
    }

}
