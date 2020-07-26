package com.ephoenix.lmsportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "MAP_USER_STUDY_PLAN")
public class UserStudyMap extends BaseEntity {

	private static final long serialVersionUID = -2108971121222802003L;

	@Id
	@Column(name = "USER_STUDY_PLAN_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userStudyPlanMapId;
	
	@NotEmpty
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "STUDY_PLAN_ID")
	private Long studyPlanId;

	@Column(name = "T_ID")
	private Long tId;
	
	}
