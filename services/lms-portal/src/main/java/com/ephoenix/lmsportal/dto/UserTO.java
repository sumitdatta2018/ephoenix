package com.ephoenix.lmsportal.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.ephoenix.lmsportal.util.AttributeEncryptor;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserTO extends BaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7401562440220774535L;

	@NotEmpty
	private String userLoginId;
	private Long id;

	@Convert(converter = AttributeEncryptor.class)
	@NotEmpty
	private String password;

	@NotEmpty(message = "Name should not be empty")
	private String name;

	private String fatherName;

	private String mothername;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "IST")
	private Date dob;

	private String lastQualification;

	private String aadharNum;
	private String gender;

	private Long casteId;
	private String casteName;

	private Long religionId;
	private String religionName;

	private String city;

	private String district;

	@Email
	private String email;

	@Pattern(regexp = "^[0-9]{10}$")
	private String mobile;

	private String address;

	private Long districtId;

	private Long stateId;

	private String pin;

	private String pan;

	private String gstn;
	private String instituteName;

	private String contact;

	private Long userTypeId;
	private String userType;
	private String lasLoginTime;

	private List<RoleTo> roles;
	private List<MenuTo> menus;

	private Long classID;
	private Character isActive = 'Y';

}
