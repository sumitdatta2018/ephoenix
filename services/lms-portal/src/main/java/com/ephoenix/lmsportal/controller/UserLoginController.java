package com.ephoenix.lmsportal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ephoenix.lmsportal.dto.ForgotPasswordTO;
import com.ephoenix.lmsportal.dto.GenericResponse;
import com.ephoenix.lmsportal.dto.KeycloakTokenDto;
import com.ephoenix.lmsportal.dto.KeycloakTokenRequestDto;
import com.ephoenix.lmsportal.dto.UserLoginTO;
import com.ephoenix.lmsportal.dto.UserTO;
import com.ephoenix.lmsportal.service.UserLoginService;

@RestController
@RequestMapping(value = "/api")
public class UserLoginController {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(UserLoginController.class);

	@Autowired
	private UserLoginService userLoginService;

	/**
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody UserLoginTO userLoginTO) {
		UserTO usetTO = userLoginService.login(userLoginTO);
		GenericResponse<UserTO> response = new GenericResponse<>(usetTO);
		return new ResponseEntity<GenericResponse<UserTO>>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/token", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public KeycloakTokenDto gettoken(@RequestBody KeycloakTokenRequestDto keycloakTokenRequestDto) {

		KeycloakTokenDto tokenDetails = userLoginService.gettoken(keycloakTokenRequestDto);
		return tokenDetails;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<String>> invokeLogout(@RequestBody String token) {

		String msg = userLoginService.logout(token);
		GenericResponse<String> response = new GenericResponse<>(msg);
		return new ResponseEntity<GenericResponse<String>>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/reset", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<String>> resetPassword(@RequestBody ForgotPasswordTO forgotPasswordTO) {

		String msg = userLoginService.resetPassword(forgotPasswordTO);
		GenericResponse<String> response = new GenericResponse<>(msg);
		return new ResponseEntity<GenericResponse<String>>(response, HttpStatus.OK);
	}

}
