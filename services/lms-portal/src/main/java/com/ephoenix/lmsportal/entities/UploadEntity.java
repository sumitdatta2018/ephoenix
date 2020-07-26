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
@Table(name = "MST_UPLOAD")
public class UploadEntity extends BaseEntity {

	private static final long serialVersionUID = -2108971121222802003L;

	@Id
	@Column(name = "UPLOAD_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uploadId;

	@Column(name = "UPLOAD_TYPE_ID")
	private Long uploadTypeId;

	@Column(name = "STUDY_PLAN_ID")
	private Long studyPlanId;

	@NotNull
	@Column(name = "FILE_NAME")
	private String fileName;

	@NotNull
	@Column(name = "FILE_PATH")
	private String filePath;

}
