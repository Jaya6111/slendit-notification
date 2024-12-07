package com.fq.slendit.mail.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MailConstants {
	

	@Value("${spring.mail.smtp.username}")
	private String senderEmail;

	@Value("${email.conformation.failed}")
	private String confirmationFailedStatus;

	@Value("${email.conformation}")
	private String emailConfirmed;

	@Value("${application.name}")
	private String applicationName;

	@Value("${email.status.failed}")
	private String failed;

	@Value("${email.status.success}")
	private String success;
	
	@Value("${email.subject.welcome}")
	private String welcomeSubject;
	
	public static String SENDER_EMAIL;
	public static String CONFORMATION_FAILED_STATUS;
	public static String EMAIL_CONFORMED;
	public static String APPLICATION_NAME;
	public static String FAILED;
	public static String SUCCESS;
	
	public static String WELCOMESUBJECT;

	@PostConstruct
	public void init() {
		
		SENDER_EMAIL = senderEmail;
		CONFORMATION_FAILED_STATUS = confirmationFailedStatus;
		EMAIL_CONFORMED = emailConfirmed;
		APPLICATION_NAME = applicationName;
		FAILED = failed;
		SUCCESS = success;
		
		WELCOMESUBJECT = welcomeSubject;
	}
}
