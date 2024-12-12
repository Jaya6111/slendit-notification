package com.fq.slendit.mail.service.impl;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fq.slendit.mail.constants.MailConstants;
import com.fq.slendit.mail.request.WelcomeMailRequest;
import com.fq.slendit.mail.service.EmailService;
import com.fq.slendit.mail.util.MailSenderUtil;
import com.fq.slendit.mail.util.VerificationToken;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private MailSenderUtil mailSenderUtil;

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public String sendWelcomeEmail(@Valid WelcomeMailRequest request) {

		String response = null;
		String subject = MailConstants.WELCOMESUBJECT;
		String templatePath = "/templates/WelcomeMail.html";

		Map<String, String> placeholders = new TreeMap<String, String>();
		placeholders.put("{{username}}", request.getRecipientName());

		try {
			mailSenderUtil.sendMail(request.getRecipientEmail(), subject, templatePath, placeholders);
			response = MailConstants.SUCCESS;
		} catch (Exception e) {
			response = MailConstants.FAILED;
		}
		return response;
	}

	@Override
	public String verifyToken(String token) {

		try {
			VerificationToken userToken = getTokenFromUser(token);
			
			if (userToken == null || !userToken.getToken().equals(token)) {
				return "Invalid token";
			}

			if (userToken.getExpiryDate() == null || userToken.getExpiryDate().isBefore(LocalDateTime.now())) {
				return "Token expired.";
			}

			restTemplate.delete("http://localhost:8081/user/delete-token/" + token);

			return "Email successfully verified";
			
		} catch (HttpClientErrorException e) {
			return "Token not found";
		} catch (Exception e) {
			return "An error occurred during token verification";
		}
	}

	@Override
	public String confirmEmail(VerificationToken token) {

		String response = null;
		String subject = "Confirm Email";
		String templatePath = "/templates/ConfirmEmail.html";

		Map<String, String> placeholders = new TreeMap<String, String>();
		placeholders.put("{{link}}", "http://localhost:8083/email/verify-email" + token.getToken());

		try {
			mailSenderUtil.sendMail(token.getEmail(), subject, templatePath, placeholders);
			response = MailConstants.SUCCESS;
		} catch (Exception e) {
			response = MailConstants.FAILED;
		}
		return response;
	}
	
	private VerificationToken getTokenFromUser(String token) {
		return restTemplate.getForObject("http://localhost:8081/user/get-token/" + token,
				VerificationToken.class);
	}

}
