package com.ephoenix.lmsportal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ephoenix.lmsportal.dto.GenericResponse;
import com.ephoenix.lmsportal.service.LmsService;

@RestController
@RequestMapping(value = "/api")
public class RoleController {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private LmsService lmsService;

	@RequestMapping(value = "/roles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> fetchAllRole(@RequestParam String types) {
		Object masterdata = lmsService.fetchMasterData(types);
		GenericResponse<Object> response = new GenericResponse<>(masterdata);
		return new ResponseEntity<GenericResponse<Object>>(response, HttpStatus.OK);
	}

}
