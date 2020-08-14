package com.ephoenix.lmsportal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordTO extends BaseTO {
	private static final long serialVersionUID = 7091870216139983259L;
	private String username;
	private String trackId;
	private String oldPassword;
	private String newPassword;
	private String otp;
}
