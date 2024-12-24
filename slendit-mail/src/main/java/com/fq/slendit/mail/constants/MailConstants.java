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
	
	@Value("${email.welcome.email.template}")
	private String welcomeTemplate;
	@Value("${email.confirm.email.template}")
	private String confirmTemplate;
	@Value("${email.token.delete.url}")
	private String deleteTokenUrl;
	@Value("${email.token.get.url}")
	private String getTokenUrl;
	@Value("${email.token.verify.url}")
	private String verifyTokenUrl;
	@Value("${email.verify.url}")
	private String emailVerifyUrl;
	
	public static String SENDER_EMAIL;
	public static String CONFORMATION_FAILED_STATUS;
	public static String EMAIL_CONFORMED;
	public static String APPLICATION_NAME;
	public static String FAILED;
	public static String SUCCESS;
	
	public static String WELCOMESUBJECT;
	
	public static String WELCOME_EMAIL_TEMPLATE;
	public static String CONFIRM_EMAIL_TEMPLATE;
	
	public static String EMAIL_VERIFY_URL;
	public static String DELETE_TOKEN_URL;
	public static String GET_TOKEN_URL;
	public static String VERIFY_TOKEN_URL;

	@PostConstruct
	public void init() {
		
		SENDER_EMAIL = senderEmail;
		CONFORMATION_FAILED_STATUS = confirmationFailedStatus;
		EMAIL_CONFORMED = emailConfirmed;
		APPLICATION_NAME = applicationName;
		FAILED = failed;
		SUCCESS = success;
		
		WELCOMESUBJECT = welcomeSubject;
		
		WELCOME_EMAIL_TEMPLATE = welcomeTemplate;
		CONFIRM_EMAIL_TEMPLATE = confirmTemplate;
		DELETE_TOKEN_URL = deleteTokenUrl;
		GET_TOKEN_URL = getTokenUrl;
		VERIFY_TOKEN_URL = verifyTokenUrl;
		EMAIL_VERIFY_URL = emailVerifyUrl;
	}
}
