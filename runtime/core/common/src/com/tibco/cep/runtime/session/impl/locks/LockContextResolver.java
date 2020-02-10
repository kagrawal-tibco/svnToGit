package com.tibco.cep.runtime.session.impl.locks;

public abstract class LockContextResolver<CTX> {
//	protected LockContextResolver next = null;
	public abstract CTX getContext();
	public abstract String getName(CTX context);
	public abstract StackTraceElement[] getStackTrace(CTX context);
	public abstract Thread.State getThreadState(CTX context);
	
	
	/*
	 * No longer needed now that there will only be one resolver at a time
	 */
	
	/*
	public static Object getContext(LockContextResolver resolvers) {
    	for(LockContextResolver res = resolvers; res != null; res = res.next) {
    		Object ctx = res.getContext();
    		if(ctx != null) return ctx;
    	}
    	return null;
    }
    
    public static String getName(Object context, LockContextResolver resolvers) {
    	for(LockContextResolver res = resolvers; res != null; res = res.next) {
    		String name = res.getName(context);
    		if(name != null) return name;
    	}
    	return null;
    }
    
    public static StackTraceElement[] getStackTrace(Object context, LockContextResolver resolvers) {
    	for(LockContextResolver res = resolvers; res != null; res = res.next) {
    		StackTraceElement[] st = res.getStackTrace(context);
    		if(st != null) return st;
    	}
    	return null;
    }
    
    public static Thread.State getThreadState(Object context, LockContextResolver resolvers) {
    	for(LockContextResolver res = resolvers; res != null; res = res.next) {
    		Thread.State ts = res.getThreadState(context);
    		if(ts != null) return ts;
    	}
    	return null;
    }
    */
}