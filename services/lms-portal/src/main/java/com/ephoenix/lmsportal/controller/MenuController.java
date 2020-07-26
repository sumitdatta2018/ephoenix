package com.ephoenix.lmsportal.controller;

import java.util.List;

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
import com.ephoenix.lmsportal.dto.MenuTo;
import com.ephoenix.lmsportal.service.MenuService;

@RestController
@RequestMapping(value = "/api")
public class MenuController {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(MenuController.class);

	@Autowired
	private MenuService menuService;

	@RequestMapping(value = "/menus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> fetchAllMenu() {
		List<MenuTo>menus = menuService.fetchAllMenu();
		GenericResponse<Object> response = new GenericResponse<>(menus);
		return new ResponseEntity<GenericResponse<Object>>(response, HttpStatus.OK);
	}

}
