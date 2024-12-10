package com.fq.slendit.mail.util;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VerificationToken {

	private String token;
	private String email;
	private LocalDateTime expiryDate;

}
