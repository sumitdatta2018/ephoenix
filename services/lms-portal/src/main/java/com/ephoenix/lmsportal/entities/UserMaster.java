package com.ephoenix.lmsportal.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.ephoenix.lmsportal.util.AttributeEncryptor;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "USER")
public class UserMaster extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long id;

	@NotEmpty
	@Column(name = "USER_LOGIN_ID", unique = true)
	private String userLoginId;

	@Convert(converter = AttributeEncryptor.class)
	@NotEmpty
	@Column(name = "PASSWORD")
	private String password;

	@NotEmpty(message="Name should not be empty")
	@Column(name = "NAME")
	private String name;

	@Email
	@Column(name = "EMAIL_ID")
	private String email;

	@Pattern(regexp = "^[0-9]{10}$")
	@Column(name = "MOBILE_NO")
	private String mobile;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "DISTRICT_ID")
	private Long districtId;
	
	@Column(name = "STATE_ID")
	private Long stateId;

	@Column(name = "PIN_CODE")
	private String pin;

	@Column(name = "CONTACT")
	private String contact;

	@Transient
	private String annexureId;

	@Column(name = "USER_TYPE_ID")
	private Long userTypeId;
	
	@Column(name = "INSTITUTE_NAME")
	private String instituteName;
	
	@Transient
	private String userType;
	@Transient
	private String lasLoginTime;
	@Transient
	private String statusName;
	
	@Transient
	private String roleName;
	
	@Transient
	private String levelName;
	
	@Transient
	private boolean eligibleForChangePassword;
	
	@Transient
	private Long roleId;
	
	

}
