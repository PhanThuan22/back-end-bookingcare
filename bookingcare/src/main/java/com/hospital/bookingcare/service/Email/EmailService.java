package com.hospital.bookingcare.service.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.extern.slf4j.Slf4j;
import com.hospital.bookingcare.response.DataMailDTO;

@Service
@Slf4j
public class EmailService implements IEmailService {
    
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public void sendHtmlMail(DataMailDTO dataMail, String templateName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

        Context context = new Context();
        context.setVariables(dataMail.getProps());

        // Render HTML từ template Thymeleaf
        String html = templateEngine.process(templateName, context);

        helper.setTo(dataMail.getTo());
        helper.setSubject(dataMail.getSubject());
        helper.setText(html, true);  // Set HTML content

        // Đính kèm ảnh nếu có
        if (dataMail.getBase64Image() != null && !dataMail.getBase64Image().isEmpty()) {
            // Chuyển base64 thành byte array
            byte[] imageBytes = java.util.Base64.getDecoder().decode(dataMail.getBase64Image());
            // Đính kèm ảnh vào email (giả sử ảnh được gắn như file đơn thuốc)
            ByteArrayDataSource imageDataSource = new ByteArrayDataSource(imageBytes, "image/png");
            helper.addAttachment("remedy_image.png", imageDataSource);
        }

        // Gửi email
        mailSender.send(message);
    }
}
