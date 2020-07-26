package com.ephoenix.lmsportal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectTo extends BaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8860808007875560831L;

	private Long subjectId;
	
	private Long classId;

	private String subjectName;

}
