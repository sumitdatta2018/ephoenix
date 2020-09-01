package com.ephoenix.lmsportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.ephoenix.lmsportal.dto.UserFeedbackTo;
import com.ephoenix.lmsportal.service.UserFeedbackService;

@RestController
@RequestMapping(value = "/api")
public class UserFeedbackController {

	

	@Autowired
	private UserFeedbackService userFeedbackService;

	/**
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/userfeedback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createFeedback(@RequestBody UserFeedbackTo UserFeedbackTo) {
		UserFeedbackTo userFeedbackTo = userFeedbackService.createFeedback(UserFeedbackTo);
		GenericResponse<UserFeedbackTo> response = new GenericResponse<>(userFeedbackTo);
		return new ResponseEntity<GenericResponse<UserFeedbackTo>>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/userfeedback/{userId}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getFeedbackByUserId(@PathVariable Long userId) {
		UserFeedbackTo userFeedbackTo = userFeedbackService.getFeedbackByUserId(userId);
		GenericResponse<UserFeedbackTo> response = new GenericResponse<>(userFeedbackTo);
		return new ResponseEntity<GenericResponse<UserFeedbackTo>>(response, HttpStatus.OK);
	}
	@RequestMapping(value = "/userfeedback", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllfeedback(@RequestParam(required =false) Long rating) {
		List<UserFeedbackTo> userFeedbackTos = userFeedbackService.getAllfeedback(rating);
		GenericResponse<List<UserFeedbackTo>> response = new GenericResponse<>(userFeedbackTos);
		return new ResponseEntity<GenericResponse<List<UserFeedbackTo>>>(response, HttpStatus.OK);
	}


}
