package com.ephoenix.lmsportal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ephoenix.lmsportal.client.KeycloakClient;
import com.ephoenix.lmsportal.dto.BaseTO;
import com.ephoenix.lmsportal.dto.KeycloakTokenDto;

import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping(value = "/api")
public class TestController {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(TestController.class);
	
	
	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String test() {

		CMS cms = new CMS();
		
		Status status = new Status();
		status.setCode("200");
		status.setMsg("ok");
		cms.setStatus(status);
		Result result1 = new Result();
		result1.set_oid("59cc438ae4b00c976c22df59");
		result1.set_repo("cmsdbtest");
		result1.set_type("NodeServer");
		result1.setResourceId("10.176.5.77");
		
		Result result2 = new Result();
		result2.set_oid("59cc438ae4b00c976c22df59");
		result2.set_repo("cmsdbtest");
		result2.set_type("NodeServer");
		result2.setResourceId("10.176.5.76");
		
		List<Result> result = new ArrayList<>();
		result.add(result1);
		result.add(result2);
		cms.setResult(result);
		
		
		return cms.toString();
	}
	
	

}





@Getter
@Setter
class Status {
	private String code;
	private String msg;
	private String stackTrace;
}

@Getter
@Setter
class CMS extends BaseTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1194544494845618572L;
	private Status status;
	private int dbTimeCost;
	private int totalTimeCost;
	private boolean hasmore;
	private int count;
	List<Result> result;

}

@Getter
@Setter
class Result {

	private String _type;
	private String resourceId;
	private String _oid;
	private String _repo;

}
