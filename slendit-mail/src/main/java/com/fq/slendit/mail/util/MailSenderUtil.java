package com.fq.slendit.mail.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.fq.slendit.mail.constants.MailConstants;

@Service
public class MailSenderUtil {

	
	private final JavaMailSender javaMailSender;
	
	public MailSenderUtil(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendMail(String recipientEmail, String subject, String templatePath, Map<String, String> placeholders)
			throws Exception {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom(MailConstants.SENDER_EMAIL, MailConstants.APPLICATION_NAME);
		helper.setTo(recipientEmail);
		helper.setSubject(subject);

		String emailBody = processTemplate(templatePath, placeholders);

		helper.setText(emailBody, true);

		javaMailSender.send(message);
	}

	private String processTemplate(String templatePath, Map<String, String> placeholders) throws Exception {
		try (InputStream inputStream = getClass().getResourceAsStream(templatePath);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

			String body = reader.lines().collect(Collectors.joining());

			for (Map.Entry<String, String> entry : placeholders.entrySet()) {
				body = body.replace(entry.getKey(), entry.getValue());
			}

			return body;
		}
	}
}
