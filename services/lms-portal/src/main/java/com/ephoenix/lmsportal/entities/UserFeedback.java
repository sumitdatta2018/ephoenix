package com.ephoenix.lmsportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "USER_FEEDBACK")
public class UserFeedback extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8503020431024524766L;
	@Id
	@Column(name = "FEEDBACK_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long feedbackID;

	@NotNull
	@Column(name = "DESCRIPTION")
	private String description;

	
	@Column(name = "SUGGESTION")
	private String suggestion;

	
	@Column(name = "RATING")
	private Integer rating;

}
