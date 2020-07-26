package com.ephoenix.lmsportal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ephoenix.lmsportal.service.TransactionService;

@Controller
public class TransactionController {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(TransactionController.class);
	@Autowired
	private TransactionService transactionService;

	@RequestMapping(value = "/api/transactions", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String fetchtransactionDetails(@RequestBody MultiValueMap<String, Object> formParameters) {
		log.info("paramMap---->{}", formParameters);
		
		transactionService.savePreamiumTxn(formParameters);
		// GenericResponse<List<StudyPlanTo>> response = new
		// GenericResponse<>(studyPlanTo);
		return "index";
	}

}
