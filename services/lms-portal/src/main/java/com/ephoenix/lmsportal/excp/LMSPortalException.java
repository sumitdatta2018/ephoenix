package com.ephoenix.lmsportal.excp;

public class LMSPortalException extends RuntimeException {
	
	private static final long serialVersionUID = 2153196840329558346L;

	public LMSPortalException() {
		super();
	}

	public LMSPortalException(String msg, Throwable ex) {
		super(msg, ex);
	}

	public LMSPortalException(String msg) {
		super(msg);
	}

	public LMSPortalException(Throwable ex) {
		super(ex);
	}

}
