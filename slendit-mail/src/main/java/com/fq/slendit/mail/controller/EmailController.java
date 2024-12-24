package com.fq.slendit.mail.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fq.slendit.mail.request.WelcomeMailRequest;
import com.fq.slendit.mail.service.EmailService;
import com.fq.slendit.mail.util.VerificationToken;

@RestController
@RequestMapping("/slendit/email")
public class EmailController {

	@Autowired
	private EmailService emailService;
	
	@PostMapping("/welcome")
	public String welcomeMail(@RequestBody @Valid WelcomeMailRequest request) {
		return emailService.sendWelcomeEmail(request);
	}
	
	@PostMapping("/confirm-email")
	public ResponseEntity<String> confirmEmail(@RequestBody VerificationToken token) {
		String response = emailService.confirmEmail(token);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/verify-email/{token}")
	public ResponseEntity<String> verifyEmail(@PathVariable String token) {
		String response = emailService.verifyToken(token);
		return ResponseEntity.ok(response);
	}
}
