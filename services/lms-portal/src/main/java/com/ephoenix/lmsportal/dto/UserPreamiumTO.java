package com.ephoenix.lmsportal.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserPreamiumTO extends BaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7401562440220774535L;

	private Long userId;

	private Long studyPlanId;

	private Long tId;

}
