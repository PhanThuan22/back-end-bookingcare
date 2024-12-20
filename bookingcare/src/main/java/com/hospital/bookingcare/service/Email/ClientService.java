package com.hospital.bookingcare.service.Email;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.bookingcare.response.DataMailDTO;
import com.hospital.bookingcare.utils.Const;
import com.hospital.bookingcare.utils.DataUtils;

import jakarta.mail.MessagingException;

@Service
public class ClientService implements IClientService {
	@Autowired
    private EmailService mailService;

    @Override
    public Boolean create(ClientSdi sdi) {
        try {
            DataMailDTO dataMail = new DataMailDTO();

            dataMail.setTo(sdi.getEmail());
            dataMail.setSubject(Const.SEND_MAIL_SUBJECT.CLIENT_REGISTER);
            
            Map<String, Object> props = new HashMap<>();
            props.put("name", sdi.getName());
            props.put("username", sdi.getUsername());
            props.put("password", DataUtils.generateTempPwd(6));
            props.put("verificationLink", sdi.getVerificationLink());
            dataMail.setProps(props);

            mailService.sendHtmlMail(dataMail, Const.TEMPLATE_FILE_NAME.CLIENT_REGISTER);
            return true;
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        return false;
    }
}
