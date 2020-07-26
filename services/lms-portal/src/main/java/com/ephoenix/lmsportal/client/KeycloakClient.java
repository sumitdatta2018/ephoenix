package com.ephoenix.lmsportal.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ephoenix.lmsportal.config.FormEncoderFeignConfiguration;

import feign.Headers;


@FeignClient(name = "keycloak-invoker",url ="http://sumit-pwc.eastus.cloudapp.azure.com:8082",configuration =FormEncoderFeignConfiguration.class)
public interface KeycloakClient {
	/**
	 * 
	 * @param mailInfoJSON
	 */
	@RequestMapping(value="/auth/realms/ephoenix/protocol/openid-connect/token",method=RequestMethod.POST,consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	@Headers("Content-Type: application/x-www-form-urlencoded")
	String getTokenDetails(@RequestBody Map<String, String> tokenRequest);
	
}
