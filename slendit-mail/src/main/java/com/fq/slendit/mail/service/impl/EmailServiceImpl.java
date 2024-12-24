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
		String templatePath = MailConstants.WELCOME_EMAIL_TEMPLATE;

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

			restTemplate.delete(MailConstants.DELETE_TOKEN_URL + token);

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
		String templatePath = MailConstants.CONFIRM_EMAIL_TEMPLATE;

		Map<String, String> placeholders = new TreeMap<String, String>();
		placeholders.put("{{link}}", MailConstants.EMAIL_VERIFY_URL + token.getToken());

		try {
			mailSenderUtil.sendMail(token.getEmail(), subject, templatePath, placeholders);
			response = MailConstants.SUCCESS;
		} catch (Exception e) {
			response = MailConstants.FAILED;
		}
		return response;
	}
	
	private VerificationToken getTokenFromUser(String token) {
		return restTemplate.getForObject(MailConstants.GET_TOKEN_URL + token, VerificationToken.class);
	}

}
