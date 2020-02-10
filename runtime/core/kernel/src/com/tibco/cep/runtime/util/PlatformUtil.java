package com.tibco.cep.runtime.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class PlatformUtil {
	
	public static enum PLATFORM_MODE {
		STUDIO,
		WEBSTUDIO,
		RUNTIME
	}
	
	public static final PlatformUtil INSTANCE = new PlatformUtil();
	private PLATFORM_MODE mode = null;
	
	private PlatformUtil() {
		mode = getMode();
	}
	
	public PLATFORM_MODE getMode() {
		if(mode == null) {
			if(checkWebStudioRunning()) {
				mode = PLATFORM_MODE.WEBSTUDIO;
			} else if(checkStudioRunning()) {
				mode = PLATFORM_MODE.STUDIO;
			} else {
				mode = PLATFORM_MODE.RUNTIME;
			}
		}
		return mode;
	}
	
	
	public boolean isStudioPlatform() {
		return mode == PLATFORM_MODE.STUDIO;
	}
	
	private boolean checkStudioRunning() {
		Class platform = null;
		boolean isRunning = false;
    	try {
			platform = Class.forName("org.eclipse.core.runtime.Platform");
			if(platform != null) {
				Method m_isRunning = platform.getDeclaredMethod("isRunning");
				isRunning = (Boolean) m_isRunning.invoke(null);
			}
		} catch (ClassNotFoundException e) {
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
		return isRunning;
		
	}
	
	public  boolean isWebStudioPlatform() {
		return mode == PLATFORM_MODE.WEBSTUDIO;
	}
	
	private  boolean checkWebStudioRunning() {
		try {
			// Obtain our environment naming context
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			return envCtx != null;
		} catch (NamingException ex) {
			return false;
		}
	}
	
	public boolean isRuntimePlatform() {
		return mode == PLATFORM_MODE.RUNTIME;
	}

}
