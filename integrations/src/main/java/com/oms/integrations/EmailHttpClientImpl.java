
package com.oms.integrations;

import com.oms.dto.EmailRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class EmailHttpClientImpl implements EmailHttpClient {

    @Value("${mail.smtp.host}")
    private String host;

    @Value("${mail.smtp.port}")
    private int port;

    @Value("${mail.smtp.username}")
    private String username;

    @Value("${mail.smtp.password}")
    private String password;

    @Override
    public String sendEmail(EmailRequestDto emailRequestDto) {        return "Email sending is not supported due to missing dependencies";
}
}
