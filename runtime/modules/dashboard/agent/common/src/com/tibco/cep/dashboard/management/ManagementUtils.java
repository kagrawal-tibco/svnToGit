package com.tibco.cep.dashboard.management;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.tibco.cep.dashboard.naming.NamingContext;
import com.tibco.cep.dashboard.naming.NamingContextFactory;

public final class ManagementUtils {
	
	public static final String BE_AGENT_DASHBOARD_KEY_PREFIX = "be.agent.dashboard";
	
	public static Context getContext() throws NamingException{
		//initialize the naming context
		Hashtable<Object, Object> ctxEnv = new Hashtable<Object, Object>();
		ctxEnv.put(NamingContext.INITIAL_CONTEXT_FACTORY, NamingContextFactory.class.getName());
		ctxEnv.put(NamingContext.TRANSPORT, "local");
		//initialize context 
		return new InitialContext(ctxEnv);
	}
	
	public static Context getContext(String transport) throws NamingException{
		//initialize the naming context
		Hashtable<Object, Object> ctxEnv = new Hashtable<Object, Object>();
		ctxEnv.put(NamingContext.INITIAL_CONTEXT_FACTORY, NamingContextFactory.class.getName());
		ctxEnv.put(NamingContext.TRANSPORT, transport);
		//initialize context 
		return new InitialContext(ctxEnv);
	}	

}
