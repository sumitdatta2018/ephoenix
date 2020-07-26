package com.ephoenix.lmsportal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KeycloakTokenDto extends BaseTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2921032431890217712L;
	
	private String access_token;
	private String refresh_token;
	private String token_type;
	private String session_state;
	private String scope;
	private Long expires_in;
	private Long refresh_expires_in;
	
	
	

}
