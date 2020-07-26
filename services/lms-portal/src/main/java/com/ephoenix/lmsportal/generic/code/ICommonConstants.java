package com.ephoenix.lmsportal.generic.code;

/**
 * 
 * @author arnabn639
 *
 */
public interface ICommonConstants {
	Long DEFAULT_USER = 1l;
	String SMS_BASE_URL = "notification.sms.base.url";
	String SPRING_PROFILE_ACTIVE = "spring.profiles.active";
	String EMAIL_SUCCESS_MESSAGE = "Email has been sent successfully";
	String SMS_SUCCESS_MESSAGE = "SMS has been sent successfully";
	String BLANK = "";
	String SPACE = " ";
	String CREATE_VALUE = "CREATE";
	String UPDATE_VALUE = "UPDATE";
	String DEFAULT_ROLE = "CITIZEN";
	String USER_CREATION = "user_creation";	
	String PDF = "PDF";
	String OFFICE_MAP= "office_map";
	String OFFICE_LIST = "office_list";	
	String COMMA_SEPARATOR = ",";
	String DASH_SEPARATOR = "-";
	String CAN_NOT_BE_BLANK = " can not be blank";
	String UPLOADED_SUCCESSFULLY = " record(s) have been uploaded successfully";
	String FAILED_VALIDATION = " record(s) have failed validation";
	String NO_USER_DATA_TO_UPLOAD = "There is no user data to upload";
	String FAILED_RECORDS = "Failed Records";
	Long MIN_AGE = 1L;
	Long MAX_AGE = 100L;
	String OTP_JSON_FOR_USERLOGINID = "{\"userLoginId\":\"%s\"}";
	String USERNAME_KEY = "_u_";
	String PASSWORD_KEY = "_p_";
	String DEVICE_KEY = "_m_";
	String CLIENT_ID_KEY = "_ci_";
	String CLIENT_SECRET_KEY = "_cs_";
	String PRE_LOGIN_MAP = "preLoginTokens";
	String LOGIN_ATTEMPT_MAP = "loginAttempts";
	String USER_CREDENTIALS_MAP = "userCredentialsMap";
	String LOGIN_WITH_OTP = "_o_";
	String SCHEME_TYPE = "scheme";
	String USERID_TYPE = "userid";
	String USER_AGENT = "User-Agent";
	String X_FORWARDED_FOR = "X-Forwarded-For";
	String ANONYMOUS_USER = "anonymous";
	String SUB_KEY = "sub";
	String USER_CURRENT_ROLE_MAP = "userCurrentRoles";
	String AUDIT_USER_MAP = "auditUsers";
	String USER_CREATION_MAIL_TEMPLATE_KEY = "fdms_user_creation_mail_template";
	String USER_CREATION_SMS_TEMPLATE_KEY = "fdms_user_creation_sms_template";
	String USER_BULK_UPLOAD_MAIL_TEMPLATE_KEY = "fdms_user_bulk_upload_mail_template";
	String USER_BULK_UPLOAD_SMS_TEMPLATE_KEY = "fdms_user_bulk_upload_sms_template";
	String USER_BULK_UPLOAD_FAILED_RECORDS_FILENAME = "UserBulkUploadFailedRecords.xlsx";
	String HIERARCHY_BULK_UPLOAD_FAILED_RECORDS_FILENAME = "HierarchyBulkUploadFailedRecords.xlsx";
	String LAST_LOGIN_DATE_FORMAT = "dd MMM yyyy HH:mm:ss";
	String UNBLOCK_FLAG = "unblock";
	String DEFAULT_EMAIL_ID = "arnab.n@pwc.com";
	String DEFAULT_MOBILE_NO = "9038055562";
	String DEFAULT_RECIPIENT_NAME = "Arnab Nath";
	String LOGGED_IN_USER_INFO_MAP = "logged_in_user_info_map";
	String EMAIL_ID = "email_id";
	String MOBILE_NO = "mobile_no";
	String FULL_NAME = "full_name";
	String OFFICE_NAME = "office_name";
	String OFFICE_ID = "office_id";
	String OFFICE_TYPE_ID = "office_type_id";
	String USER_ID = "user_id";
	String HIERARCHY_ID = "hierarchy_id";
	String USER_TYPE_CONSULTANT = "CONSULTANT";
	String USER_TYPE_USER="GOVERNMENT";
	String USER_TYPE_BANK="BANK";
	String ROLE_UPDATION_SUCCESS_MESSAGE = "Role has been updated successfully";
	String OTP_SUCCESS_MESSAGE = "Your otp has been sent to to your registered mobile number __mobilenum__ and in __email__";
	String EXCEL_SAVE_DATE_FORMAT = "MM/dd/yy";
	String SECONDFACTORAUTHNAME = "NONE";
    String USER_UPDATION_SUCCESS_SUBJECT="User Updated"; 
    String USER_CREATION_SUCCESS_SUBJECT="User Created"; 
    String ACTION_CREATE="Create";
	String ACTION_SUBMIT_APPROVED = "Approved";
	String ACTION_SUBMIT_RESUBMITTED = "Resubmitted";
			
}
