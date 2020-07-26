package com.ephoenix.lmsportal.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class UserLoginTO extends BaseTO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private String userLoginId;
	private String password;

}
