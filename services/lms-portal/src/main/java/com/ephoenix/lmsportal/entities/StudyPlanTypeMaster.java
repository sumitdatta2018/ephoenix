package com.ephoenix.lmsportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "MST_STUDY_PLAN_TYPE")
public class StudyPlanTypeMaster extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1471970575882331601L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "STUDY_PLAN_TYPE_ID")
	private Long studyPlanTypeId;

	@Column(name = "STUDY_PLAN_TYPE")
	private String studyPlanType;

}
