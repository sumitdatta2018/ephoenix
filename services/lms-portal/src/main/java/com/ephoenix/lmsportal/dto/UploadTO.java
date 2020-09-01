package com.ephoenix.lmsportal.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadTO extends BaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4159902773954114504L;

	private Long uploadId;
	private Long uploadTypeId;
	private Long studyPlanId;
	private String fileName;
	private String filePath;
	private String fileContent;
	private String completeUploadPath;
	private String thumbnailURL;
	private String className;
	private String subjectName;
	
	
	
	
	
	

}
