package com.ephoenix.lmsportal.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ephoenix.lmsportal.dto.GenericResponse;
import com.ephoenix.lmsportal.dto.Notice;
import com.ephoenix.lmsportal.dto.StudyPlanTo;
import com.ephoenix.lmsportal.service.NoticeService;

@RestController
@RequestMapping(value = "/api")
public class NoticeController {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(NoticeController.class);

	@Autowired
	private NoticeService noticeService;

	@RequestMapping(value = "/notice", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> fetchNotice() {
		List<Notice> noticeTos = noticeService.fetchNotice();
		GenericResponse<List<Notice>> response = new GenericResponse<>(noticeTos);
		return new ResponseEntity<GenericResponse<List<Notice>>>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/notice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createNotice(@RequestBody Notice notice) {
		Notice savedNotice = noticeService.createNotice(notice);
		GenericResponse<Notice> response = new GenericResponse<>(savedNotice);
		return new ResponseEntity<GenericResponse<Notice>>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/notice/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateNotice(@PathVariable Long id, @RequestBody Notice notice) {
		Notice updatedNotice = noticeService.updateNotice(notice, id);
		GenericResponse<Notice> response = new GenericResponse<>(updatedNotice);
		return new ResponseEntity<GenericResponse<Notice>>(response, HttpStatus.OK);
	}

}
