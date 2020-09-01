package com.ephoenix.lmsportal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserFeedbackTo extends BaseTO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8945369294672519948L;

	private Long feedbackID;

	private String description;

	private String suggestion;

	private Integer rating;
	
	private Long userId;

}
