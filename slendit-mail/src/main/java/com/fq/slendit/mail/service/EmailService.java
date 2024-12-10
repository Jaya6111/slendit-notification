package com.fq.slendit.mail.service;

import javax.validation.Valid;

import com.fq.slendit.mail.request.WelcomeMailRequest;
import com.fq.slendit.mail.util.VerificationToken;

public interface EmailService {

	public String sendWelcomeEmail(@Valid WelcomeMailRequest request);

	public String verifyToken(String token);

	public String confirmEmail(VerificationToken token);

}
