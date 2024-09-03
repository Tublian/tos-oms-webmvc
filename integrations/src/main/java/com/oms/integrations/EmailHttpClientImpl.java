
package com.oms.integrations;

import com.oms.dto.EmailRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import javax.mail.internet.InternetAddress;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class EmailHttpClientImpl implements EmailHttpClient {
    private AtomicInteger useCnt = new AtomicInteger();

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public String sendEmail(EmailRequestDto emailRequestDto) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            JavaMailSenderImpl mailSender = (JavaMailSenderImpl) javaMailSender;
            mailSender.setHost("smtp.example.com");
            mailSender.setPort(587);
            mailSender.setUsername("your_username");
            mailSender.setPassword("your_password");

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(new InternetAddress(emailRequestDto.getFrom()));
            helper.setTo(emailRequestDto.getTo());
            helper.setSubject(emailRequestDto.getSubject());
            helper.setText(emailRequestDto.getBody(), true);
            mailSender.send(mimeMessage);
            return String.format("SUCCESS %d", useCnt.incrementAndGet());
        } catch (MessagingException e) {
            e.printStackTrace();
            return "FAILURE";
        }
    }
}