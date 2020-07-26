package com.ephoenix.lmsportal.dto;

import java.util.Map;

import com.ephoenix.lmsportal.generic.code.MessageCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class GenericResponse<T> extends BaseTO {
	private static final long serialVersionUID = 1L;
	private boolean success;
	private ErrorResponse error;
	private T payload;
	private String code;
	private Map<Object,Object> vars;
	
	public GenericResponse() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public GenericResponse(MessageCode messageCode) {
		super();
		this.payload = (T) messageCode.message();
		this.code = messageCode.name();
		this.success = true;
	}

	public GenericResponse(T payload) {
		super();
		this.payload = payload;
		this.success = true;
	}
	

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ErrorResponse getError() {
		return error;
	}

	public void setError(ErrorResponse error) {
		this.error = error;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map<Object, Object> getVars() {
		return vars;
	}

	public void setVars(Map<Object, Object> vars) {
		this.vars = vars;
	}
}