package com.tibco.cep.obfuscator;

import java.lang.reflect.Method;

public class ObfuscateHelper {

	public static void main(String [] args) {
		try {
	        com.jidesoft.utils.Lm.verifyLicense("TIBCO Software Inc.","TIBCO BusinessEvents", "piTabGB9Ky0BLyASnMlZd7:urra403H1");
			System.out.println("RetroGuard " + makeString(args));
			Class retroClass = Class.forName("RetroGuard");
			Method main = retroClass.getMethod("main", new Class[]{args.getClass()});
			main.invoke(null, new Object[]{args});
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private static String makeString(String[] args) {
		StringBuffer buf = new StringBuffer();
		for (int i=0; i < args.length; i++) {
			buf.append(args[i]).append(" ");
		}
		return buf.toString();
	}
}
