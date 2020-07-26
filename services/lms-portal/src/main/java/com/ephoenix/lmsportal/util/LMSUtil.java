package com.ephoenix.lmsportal.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

import com.ephoenix.lmsportal.excp.LMSPortalException;
import com.ephoenix.lmsportal.generic.code.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LMSUtil {
	private static ObjectMapper mapper = new ObjectMapper();

	public static String randomUUID() {
		Random random = new SecureRandom();
		Long date = new Date().getTime();
		String id = String.format("D%s%s", date, random.nextInt(900));
		return id;
	}

	public static <T> T readValue(String content, Class<T> valueType) {
		log.debug("readValue() content : {}", content);
		if (content == null)
			throw new LMSPortalException(ErrorCode.EGB008.name());
		T targetObject = null;
		try {
			targetObject = mapper.readValue(content, valueType);
		} catch (IOException e) {
			log.error("Error readValue : {} ", e);
			throw new LMSPortalException(ErrorCode.EGB009.name());
		}
		return targetObject;
	}

	public static String checkTrailingSlash(String path) {
		return path.endsWith("/") ? path : path + "/";
	}
	
	public static String urlEncode(String url)  
    {  
              try {  
                   String encodeURL=URLEncoder.encode( url, "UTF-8" );  
                   return encodeURL;  
              } catch (UnsupportedEncodingException e) {  
                   return "Issue while encoding" +e.getMessage();  
              }  
    }

}
