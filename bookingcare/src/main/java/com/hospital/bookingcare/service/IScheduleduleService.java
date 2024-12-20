package com.hospital.bookingcare.service;

import com.hospital.bookingcare.model.Schedule;

public interface IScheduleduleService {
	Schedule getScheduleById(Long id);
    Schedule saveSchedule(Schedule schedule);

}
