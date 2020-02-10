package com.tibco.cep.dashboard.psvr.context;

import com.tibco.cep.dashboard.psvr.common.query.QueryExecutor;


/**
 * @author apatil
 * 
 */
public class ServiceLocator {

	private QueryExecutor queryExecutor;

	//private PushSubscriptionManager pushSubscriptionManager;

	ServiceLocator(Context context) {
		
	}

	public final QueryExecutor getQueryExecutor() {
		return queryExecutor;
	}	

	void destroy() {
	}

	public boolean ping() {
		return true;		
	}

}