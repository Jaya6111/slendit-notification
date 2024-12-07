package com.fq.slendit.mail.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fq.slendit.mail.request.WelcomeMailRequest;
import com.fq.slendit.mail.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

	@Autowired
	private EmailService emailService;
	
	@PostMapping("/welcome")
	public String welcomeMail(@RequestBody @Valid WelcomeMailRequest request) {
		return emailService.sendWelcomeEmail(request);
	}
}
