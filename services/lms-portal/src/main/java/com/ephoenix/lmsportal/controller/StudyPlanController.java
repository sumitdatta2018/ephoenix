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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ephoenix.lmsportal.dto.GenericResponse;
import com.ephoenix.lmsportal.dto.StudyPlanTo;
import com.ephoenix.lmsportal.service.StudyPlanService;

@RestController
@RequestMapping(value = "/api")
public class StudyPlanController {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(StudyPlanController.class);

	@Autowired
	private StudyPlanService studyPlanService;

	@RequestMapping(value = "/studyplans", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> fetchStudyPlans(@RequestParam(value = "clsId", required = false) Long clsId,
			@RequestParam(value = "subIds", required = false) List<Long> subIds,
			@RequestParam(value = "userId", required = false) Long userId) {
		List<StudyPlanTo> studyPlanTo = studyPlanService.fetchStudyPlans(clsId, subIds, userId);
		GenericResponse<List<StudyPlanTo>> response = new GenericResponse<>(studyPlanTo);
		return new ResponseEntity<GenericResponse<List<StudyPlanTo>>>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/studyplans", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createStudyPlans(@RequestBody List<StudyPlanTo> studyPlanTos) {
		List<StudyPlanTo> studyPlanTo = studyPlanService.createStudyPlans(studyPlanTos);
		GenericResponse<List<StudyPlanTo>> response = new GenericResponse<>(studyPlanTo);
		return new ResponseEntity<GenericResponse<List<StudyPlanTo>>>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/studyplans/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateStudyPlans(@PathVariable Long id, @RequestBody StudyPlanTo studyPlanTo) {
		StudyPlanTo updatedStudyPlanTo = studyPlanService.updateStudyPlans(id, studyPlanTo);
		GenericResponse<StudyPlanTo> response = new GenericResponse<>(updatedStudyPlanTo);
		return new ResponseEntity<GenericResponse<StudyPlanTo>>(response, HttpStatus.OK);
	}

}
