package com.ephoenix.lmsportal.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Notice extends BaseTO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6114100856552495526L;

	private Long noticeId;

	@NotNull
	private String title;
	
	private String subTitle;
	
	@NotNull
	private String body;
	
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "IST")
	private Date startDate;
	
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "IST")
	private Date endDate;
	
	private Character isActive ='Y';
	

}
