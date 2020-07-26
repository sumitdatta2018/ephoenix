package com.ephoenix.lmsportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mds012
 *
 */
@Getter
@Setter
@Entity
@Table(name = "MST_SUBJECT")
public class SubjectMaster extends BaseEntity {

	private static final long serialVersionUID = -2108971121222802003L;

	@Id
	@Column(name = "SUBJECT_ID")
	private Long subjectId;
	
	@Column(name = "CLASS_ID")
	private Long classId;

	@NotNull
	@Column(name = "SUBJECT_NAME")
	private String subjectName;

}
