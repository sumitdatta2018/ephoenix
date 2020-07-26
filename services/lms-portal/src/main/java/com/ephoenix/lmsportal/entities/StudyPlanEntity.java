package com.ephoenix.lmsportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "STUDY_PLAN")
public class StudyPlanEntity extends BaseEntity {
	
	

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 900414831413175738L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "STUDY_PLAN_ID")
	private Long studyPlanId;

	@NotNull
	@Column(name = "STUDY_PLAN_TYPE_ID")
	private Long studyPlanTypeId;
	
	@Column(name = "CLASS_ID")
	private Long classId;
	
	@Column(name = "SUBJECT_ID")
	private Long subjectId;

	@Column(name = "PRICE_RATE_YEARLY")
	private Long priceRateYearly;
	
	
	@Column(name = "IS_DEFAULT")
	private Character isDefault;
	
	
	@Column(name = "STUDY_PLAN_NAME")
	private String studyPlanName;
	
	

}
