package com.tibco.cep.dashboard.psvr.biz;


public interface BizResponse {

	public static final String SUCCESS_STATUS = "OK";

	public static final String ERROR_STATUS = "ERROR";
	
	// public static final String EXCEPTION_STATUS = "EXCEPTION";

	public String getStatus();

	public String getMessage();
	
	public void addHeader(String name, String value);

	public void removeHeader(String name);
	
	public Iterable<String> getHeaderNames();

	public String getHeader(String name);	

	public void addAttribute(String name, String value);

	public void removeAttribute(String name);
	
	public Iterable<String> getAttributeNames();

	public String getAttribute(String name);

}