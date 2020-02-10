package com.tibco.cep.dashboard.psvr.biz;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author RGupta
 * 
 */
public abstract class BaseBizResponse implements BizResponse {

	protected String status;
	protected String message;
	protected Map<String, String> attributes;
	protected Map<String, String> headers;

	protected BaseBizResponse() {
		this(SUCCESS_STATUS, null);
	}

	protected BaseBizResponse(String status, String message) {
		this.status = status;
		this.message = message;
		this.attributes = new LinkedHashMap<String, String>();
		this.headers = new LinkedHashMap<String, String>();
	}

	public String getMessage() {
		return message;
	}

	public String getStatus() {
		return status;
	}

	public void addHeader(String name, String value) {
		headers.put(name, value);
	}

	public void removeHeader(String name) {
		headers.remove(name);
	}
	
	public Iterable<String> getHeaderNames(){
		return headers.keySet();
	}

	public String getHeader(String name) {
		return headers.get(name);
	}

	public void addAttribute(String name, String value) {
		attributes.put(name, value);
	}

	public void removeAttribute(String name) {
		attributes.remove(name);
	}
	
	public Iterable<String> getAttributeNames(){
		return attributes.keySet();
	}

	public String getAttribute(String name) {
		return attributes.get(name);
	}

}