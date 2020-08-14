package com.ephoenix.lmsportal.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "NOTICE")
public class NoticeEntity extends BaseEntity {

	private static final long serialVersionUID = -2108971121222802003L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long noticeId;

	@NotNull
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "SUB_TITLE")
	private String subTitle;
	
	@NotNull
	@Column(name = "BODY")
	private String body;
	
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "IST")
	private Date startDate;
	
	@NotNull
	@Column(name = "END_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "IST")
	private Date endDate;
	
	

}
