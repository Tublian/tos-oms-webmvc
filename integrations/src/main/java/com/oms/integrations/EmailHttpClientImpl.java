
package com.oms.integrations;

import com.oms.dto.EmailRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

@Component
public class EmailHttpClientImpl implements EmailHttpClient {
    private AtomicInteger useCnt = new AtomicInteger();

    @Value("${smtp.host}")
    private String smtpHost;

    @Value("${smtp.port}")
    private String smtpPort;

    @Value("${smtp.username}")
    private String smtpUsername;

    @Value("${smtp.password}")
    private String smtpPassword;

    @Override
    public String sendEmail(EmailRequestDto emailRequestDto) {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpUsername, smtpPassword);
            }
        });

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setSubject(emailRequestDto.getMessageTitle());
            mimeMessage.setText(emailRequestDto.getMessageBody());
            Transport.send(mimeMessage);
            int count = useCnt.incrementAndGet();
            return String.format("SUCCESS %d", count);
        } catch (MessagingException e) {
            return "ERROR: " + e.getMessage();
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }
}