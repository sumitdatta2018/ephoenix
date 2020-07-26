package com.ephoenix.lmsportal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StudyPlanTo extends BaseTO {

	private static final long serialVersionUID = 2250479798317195753L;

	private Long studyPlanId;

	private Long studyPlanTypeId;

	private Long classId;

	private Long subjectId;

	private Long priceRateYearly;

	private Character isDefault;
	
	private String studyPlanType;
	
	private String studyPlanName;
	private Character isActive;

}
