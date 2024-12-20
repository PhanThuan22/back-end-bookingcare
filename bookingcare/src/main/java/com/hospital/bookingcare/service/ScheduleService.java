package com.hospital.bookingcare.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.bookingcare.model.Allcodes;
import com.hospital.bookingcare.model.Booking;
import com.hospital.bookingcare.model.Schedule;
import com.hospital.bookingcare.repository.BookingRepository;
import com.hospital.bookingcare.repository.CodeRepository;
import com.hospital.bookingcare.repository.ScheduleRepository;


@Service
public class ScheduleService implements IScheduleduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private CodeRepository allcodesRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> createSchedules(List<Schedule> schedules) {
        // Lọc các lịch trình đã tồn tại và chưa tồn tại
        List<Schedule> newSchedules = schedules.stream()
                .filter(schedule -> !isScheduleExists(schedule.getDoctorId(), schedule.getDate(), schedule.getTimeTypes()))
                .map(schedule -> {
                    // Kiểm tra và lấy timeTypes từ cơ sở dữ liệu
                    Allcodes timeTypes = allcodesRepository.findByKeyMap(schedule.getTimeTypes().getKeyMap());
                    if (timeTypes == null) {
                        throw new RuntimeException("Time type not found");
                    }
                    schedule.setTimeTypes(timeTypes); // Gán timeTypes đã lấy từ database
                    return schedule;
                })
                .collect(Collectors.toList());

        // Lưu các lịch trình mới vào cơ sở dữ liệu
        return scheduleRepository.saveAll(newSchedules);
    }

    public List<Schedule> updateSchedules(List<Schedule> schedules) {
        // Lấy danh sách các id từ các schedule cần cập nhật
        List<Long> scheduleIds = schedules.stream()
                .map(Schedule::getId)
                .collect(Collectors.toList());

        // Tìm các schedule hiện có trong cơ sở dữ liệu theo id
        List<Schedule> existingSchedules = scheduleRepository.findAllById(scheduleIds);

        // Ánh xạ currentNumber từ các lịch trình đầu vào sang các lịch trình trong cơ sở dữ liệu
        Map<Long, Long> currentNumberUpdates = schedules.stream()
                .collect(Collectors.toMap(Schedule::getId, Schedule::getCurrentNumber));

        // Cập nhật currentNumber cho các schedule trong cơ sở dữ liệu
        existingSchedules.forEach(existingSchedule -> {
            Long newCurrentNumber = currentNumberUpdates.get(existingSchedule.getId());
            if (newCurrentNumber != null) {
                existingSchedule.setCurrentNumber(newCurrentNumber);
            }
        });

        // Lưu các lịch trình đã được cập nhật
        return scheduleRepository.saveAll(existingSchedules);
    }

    public Schedule updateCurrentNumber(String timeTypeKeyMap, LocalDate date, Long newCurrentNumber) {
        // Tìm schedule dựa trên timeType và date
        Schedule schedule = scheduleRepository.findByTimeTypes_KeyMapAndDate(timeTypeKeyMap, date)
                .orElseThrow(() -> new RuntimeException("Schedule not found for timeType: " + timeTypeKeyMap + " and date: " + date));

        // Cập nhật currentNumber
        schedule.setCurrentNumber(newCurrentNumber);

        // Lưu schedule đã cập nhật
        return scheduleRepository.save(schedule);
    }

    public boolean deleteSchedulesByIds(List<Long> scheduleIds) {
        List<Schedule> schedules = scheduleRepository.findAllById(scheduleIds);
        if (!schedules.isEmpty()) {
            scheduleRepository.deleteAll(schedules);
            return true;
        }
        return false; // Không tìm thấy lịch nào để xóa
    }
    
    public boolean isScheduleExists(Long doctorId, LocalDate date, Allcodes timeTypes) {
        return scheduleRepository.existsByDoctorIdAndDateAndTimeTypes(doctorId, date, timeTypes);
    }

	public List<Schedule> getSchedulesByDoctorAndDate(Long doctorId, LocalDate date) {
		// TODO Auto-generated method stub
		return scheduleRepository.findByDoctorIdAndDate(doctorId, date);
	}

	@Override
	public Schedule getScheduleById(Long id) {
		// TODO Auto-generated method stub
		return scheduleRepository.findById(id).orElse(null);
	}

	@Override
	public Schedule saveSchedule(Schedule schedule) {
		// TODO Auto-generated method stub
		 return scheduleRepository.save(schedule);
	}
	
	
	public Booking createBooking(Booking booking) {
		booking.setTokenBooking(UUID.randomUUID().toString());
        return bookingRepository.save(booking);
    }
}
