package com.tibco.cep.dashboard.psvr.biz;

public interface BizSessionBindingListener {
	
	public void valueBound(BizSession bizSession,String name);
	
	public void valueUnbound(BizSession bizSession,String name);

}
