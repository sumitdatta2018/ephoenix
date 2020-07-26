package com.ephoenix.lmsportal.entities;

import java.io.Serializable;
import java.util.Date;

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

/**
 * 
 * @author arnabn639
 *
 */
@Getter
@Setter
@Entity
@Table(name = "SESSION_LOG")
public class SessionLog extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@NotNull
	@Column(name = "MST_USER_ID")
	private Long userId;

	@Column(name = "SOURCE_TYPE")
	private String sourceType;

	@Column(name = "IP_ADDRESS")
	private String ipAddress;

	@Column(name = "LOG_TIME")
	private Date logTime;

	@Column(name = "USER_ACTION")
	private String userAction;
}