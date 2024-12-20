package com.hospital.bookingcare.response;

public class DoctorProfileResponse {
    private UserResponse userResponse;
    private ScheduleResponse ScheduleResponse;

    // Constructor
    public DoctorProfileResponse(UserResponse userResponse, ScheduleResponse ScheduleResponse) {
        this.userResponse = userResponse;
        this.ScheduleResponse = ScheduleResponse;
    }

    // Getters và Setters
    public UserResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserResponse userResponse) {
        this.userResponse = userResponse;
    }

    public ScheduleResponse getScheduleResponse() {
        return ScheduleResponse;
    }

    public void setScheduleResponse(ScheduleResponse ScheduleResponse) {
        this.ScheduleResponse = ScheduleResponse;
    }
}

