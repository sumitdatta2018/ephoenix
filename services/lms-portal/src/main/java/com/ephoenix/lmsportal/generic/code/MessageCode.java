package com.ephoenix.lmsportal.generic.code;

public enum MessageCode {
	UNKNOWN(""),
	UM_MESSAGE_CODE001("Role Name of current user saved successfully."),
	UM_MESSAGE_CODE002("Logout Successful"),
	UM_MESSAGE_CODE003("Password Reset Successful"),
	
	UPMS_ERROR_CODE001("Document Successfully Deleted");
	
	private String msg;

	MessageCode(String msg) {
		this.msg = msg;
	}

	public String message() {
		return msg;
	}

	public void setMessage(String msg) {
		this.msg = msg;
	}
}
