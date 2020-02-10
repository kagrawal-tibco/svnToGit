package com.tibco.cep.security.util;

import java.text.MessageFormat;

/*
@author ssailapp
@date Aug 23, 2011
 */

public class SecurityLoggerUtil {

	public static void debug(String className, String message, Object... args) {
		String debugMessage = MessageFormat.format(message, args);
		debug(MessageFormat.format("[{0}] {1}", className, debugMessage));
	}
	
	public static void debug(String message) {
		//if (getDefault().isDebugging()) { // run eclipse with -debug option
			System.err.println(MessageFormat.format("[{0}] {1}", "cep-security", message));
		//}
	}
	
}
