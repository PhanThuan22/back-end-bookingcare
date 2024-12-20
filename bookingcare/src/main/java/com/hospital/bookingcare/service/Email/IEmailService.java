package com.hospital.bookingcare.service.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.hospital.bookingcare.response.DataMailDTO;

import jakarta.mail.MessagingException;


public interface IEmailService {
	void sendHtmlMail(DataMailDTO dataMail, String templateName) throws MessagingException;

}
