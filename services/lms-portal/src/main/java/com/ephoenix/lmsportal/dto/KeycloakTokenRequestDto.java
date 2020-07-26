package com.ephoenix.lmsportal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KeycloakTokenRequestDto extends BaseTO{


	private static final long serialVersionUID = 2921032431890217712L;
	private String client_id;
	private String client_secret;
	private String username;
	private String password;
	private String grant_type;
	
	

}
