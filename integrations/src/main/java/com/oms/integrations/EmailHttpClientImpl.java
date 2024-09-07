
package com.oms.integrations;

import com.oms.dto.EmailRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import jakarta.mail.MessagingException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class EmailHttpClientImpl implements EmailHttpClient {
    private AtomicInteger useCnt = new AtomicInteger();

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.host}")
    private String smtpHost;

    @Value("${spring.mail.port}")
    private int smtpPort;

    @Value("${spring.mail.username}")
    private String smtpUsername;

    @Value("${spring.mail.password}")
    private String smtpPassword;

    @Override
    public String sendEmail(EmailRequestDto emailRequestDto) {
        try {
            JavaMailSenderImpl mailSender = (JavaMailSenderImpl) javaMailSender;
            mailSender.setHost(smtpHost);
            mailSender.setPort(smtpPort);
            mailSender.setUsername(smtpUsername);
            mailSender.setPassword(smtpPassword);

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");

            MimeMessageHelper helper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
            helper.setFrom(emailRequestDto.getFrom());
            helper.setTo(emailRequestDto.getTo());
            helper.setSubject(emailRequestDto.getSubject());
            helper.setText(emailRequestDto.getBody(), true);
            mailSender.send(helper.getMimeMessage());
            return String.format("SUCCESS %d", useCnt.incrementAndGet());
        } catch (MailException | MessagingException e) {
            e.printStackTrace();
            return "FAILURE";
        }
    }
}