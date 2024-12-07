package com.fq.slendit.mail.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WelcomeMailRequest {

    @NotBlank(message = "Recipient email is required")
    @Email(message = "Please give a valid email")
    private String recipientEmail;

    @NotBlank(message = "Recipient name is required")
    private String recipientName;

    @NotBlank(message = "Email subject is required")
    private String subject;
    
}
