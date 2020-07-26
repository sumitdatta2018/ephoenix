package com.ephoenix.lmsportal.generic.code;

public enum ErrorCode {
	UNKNOWN(""),
	UMS_ERROR_CODE001("Profile locked for 24 hours, Proceed to unlock account?"),
	UMS_ERROR_CODE002("Invalid Userid. Please try again."),
	UMS_ERROR_CODE003("Incorrect password. %s"),
	UMS_ERROR_CODE004("Account will be locked for 24 hours after %d more failed attempt(s)."),
	UMS_ERROR_CODE005("No Roles Available"),
	UMS_ERROR_CODE006("User is inactive"),
	UMS_ERROR_CODE007("Old Password does not match"),
	UMS_ERROR_CODE008("Your password match with last 5 used password"),
	UMS_ERROR_CODE009("Provided otp is not correct"),
	UMS_ERROR_CODE010("User already exsits with these UserLoginId. Please try with different UserLoginId."),
	UMS_ERROR_CODE011("User already exsits with these Mobile Number. Please try with different Mobile Number."),
	
	
	UMS_ERROR_CODE012("UserLoginId or Password did not match"),
	
	
	EGB008("Input json string can not be null."),
	EGB009("Malformed JSON request. Please try with valid JSON"),
	
	SPS_ERROR_CODE001("No Study plan available for the given combination"),
	SPS_ERROR_CODE002("Study plan already created for the given combination"),
	
	UPLOAD_ERROR_CODE001("Unable to upload file.Please try again");
	;
	
	
	private String msg;

	ErrorCode(String msg) {
		this.msg = msg;
	}

	public String message() {
		return msg;
	}

	public void setMessage(String msg) {
		this.msg = msg;
	}
}

