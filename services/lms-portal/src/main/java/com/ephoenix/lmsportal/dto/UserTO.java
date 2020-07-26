package com.ephoenix.lmsportal.dto;

import java.util.List;

import javax.persistence.Convert;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.ephoenix.lmsportal.util.AttributeEncryptor;

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

}
