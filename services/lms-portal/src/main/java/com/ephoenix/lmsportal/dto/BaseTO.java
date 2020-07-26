package com.ephoenix.lmsportal.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(value = Include.NON_NULL)
public class BaseTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String obj = null;
		try {
			obj = new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			obj = super.toString();
		}
		return obj;
	}

}
