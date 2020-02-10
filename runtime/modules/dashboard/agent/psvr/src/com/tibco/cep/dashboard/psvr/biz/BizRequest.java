package com.tibco.cep.dashboard.psvr.biz;

import java.util.Iterator;

public interface BizRequest {

	public abstract void addParameter(String name, String value);

	public abstract Iterator<String> getParameterNames();

	public abstract String getParameter(String name);

	public abstract String[] getParameterValues(String name);

	public abstract String toString();

	public abstract String toXML();

}