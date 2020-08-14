package com.ephoenix.lmsportal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OtpTo extends BaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4963720263292070840L;

	private String otp;

	private Integer retrycount;

	private Integer retrycountLimit;

	private String userLoginId;
	
	private String trackId;

}
