package com.hospital.bookingcare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.bookingcare.service.Email.ClientSdi;
import com.hospital.bookingcare.service.Email.ClientService;
import com.hospital.bookingcare.service.Email.EmailService;
import com.hospital.bookingcare.service.Email.IClientService;

@CrossOrigin("http://localhost:5274")
@RestController
@RequestMapping("/api/mail")
public class EmailController {
	@Autowired
    private IClientService clientService;

    @PostMapping(value = "create")
    public ResponseEntity<Boolean> create(
            @RequestBody ClientSdi sdi
    ) {
        return ResponseEntity.ok(clientService.create(sdi));
    }
    
    public class BookingResponse {
        private Long id;
        private String tokenBooking;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getTokenBooking() {
			return tokenBooking;
		}
		public void setTokenBooking(String tokenBooking) {
			this.tokenBooking = tokenBooking;
		}
        
    }
}
