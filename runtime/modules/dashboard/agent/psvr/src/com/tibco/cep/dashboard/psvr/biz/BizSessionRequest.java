package com.tibco.cep.dashboard.psvr.biz;

import java.util.Iterator;
import java.util.Map;

public interface BizSessionRequest extends BizRequest {
	
	public BizSession getSession();
	
	public BizSession getSession(boolean create);
	
    public void setAttribute(String key, Object value);
    
    public Object getAttribute(String key);
    
    public Iterator<String> getAttributeNames();

	public String getContextPath();

	public Map getParameterMap();

}
