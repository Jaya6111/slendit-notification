package com.fq.slendit.mail.service.impl;

import java.util.Map;
import java.util.TreeMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fq.slendit.mail.constants.MailConstants;
import com.fq.slendit.mail.request.WelcomeMailRequest;
import com.fq.slendit.mail.service.EmailService;
import com.fq.slendit.mail.util.MailSenderUtil;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private MailSenderUtil mailSenderUtil;

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

}
