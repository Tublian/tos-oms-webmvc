
package com.oms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequestDto {

    private String to;
    private String subject;
    private String body;
    private String from;

    public EmailRequestDto(String to, String subject, String body, String from) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.from = from;
    }
}