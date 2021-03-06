package com.ephoenix.lmsportal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ephoenix.lmsportal.dto.GenericResponse;
import com.ephoenix.lmsportal.dto.UserTO;
import com.ephoenix.lmsportal.service.UserMgmtService;

@RestController
@RequestMapping(value = "/api")
public class UserMgmtController {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(UserMgmtController.class);

	@Autowired
	private UserMgmtService userMgmtService;

	/**
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody UserTO userTO) {
		UserTO createUser = userMgmtService.createUser(userTO);
		GenericResponse<UserTO> response = new GenericResponse<>(createUser);
		return new ResponseEntity<GenericResponse<UserTO>>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> fetchUserById(@PathVariable Long id) {
		UserTO user = userMgmtService.fetchUserById(id);
		GenericResponse<UserTO> response = new GenericResponse<>(user);
		return new ResponseEntity<GenericResponse<UserTO>>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUserById(@PathVariable Long id,@RequestBody UserTO userTO) {
		UserTO user = userMgmtService.updateUserById(id,userTO);
		GenericResponse<UserTO> response = new GenericResponse<>(user);
		return new ResponseEntity<GenericResponse<UserTO>>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllUser(@RequestParam(name = "isAssociatedWithSpokenEnglish", required = false) boolean isAssociatedWithSpokenEnglish,Pageable pageable) {
		Page<UserTO> users = userMgmtService.getUsers(isAssociatedWithSpokenEnglish,pageable);
		GenericResponse<Page<UserTO>> response = new GenericResponse<>(users);
		return new ResponseEntity<GenericResponse<Page<UserTO>>>(response, HttpStatus.OK);
	}
	
	
	/*@RequestMapping(value = "/test/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUserById(@PathVariable Long id) {
		UserTO user = userMgmtService.updateKeycloakName(id);
		GenericResponse<UserTO> response = new GenericResponse<>(user);
		return new ResponseEntity<GenericResponse<UserTO>>(response, HttpStatus.OK);
	}*/
	


	

}
