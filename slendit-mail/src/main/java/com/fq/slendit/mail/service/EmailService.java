package com.fq.slendit.mail.service;

import javax.validation.Valid;

import com.fq.slendit.mail.request.WelcomeMailRequest;

public interface EmailService {

	public String sendWelcomeEmail(@Valid WelcomeMailRequest request);

}
