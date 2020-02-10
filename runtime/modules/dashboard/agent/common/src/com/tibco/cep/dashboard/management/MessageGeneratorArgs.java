package com.tibco.cep.dashboard.management;

import java.util.Arrays;


public class MessageGeneratorArgs {
	
	private Object[] finalArgs;
	
	private String toString;
	
	public MessageGeneratorArgs(Throwable t,Object... args){
		this(null,null,null,null,t,args);
	}

	public MessageGeneratorArgs(String token,String userID, Object role, Object[] roles,Throwable t,Object... args){
		int argsLength = 0;
		if (args != null) {
			argsLength = args.length;
		}
		finalArgs = new Object[5+argsLength];
		finalArgs[0] = token;
		finalArgs[1] = userID;
		finalArgs[2] = role;
		finalArgs[3] = (roles != null) ? Arrays.asList(roles).toString():null;
		String throwableMsg = null;
		if (t != null) {
			throwableMsg = t.getLocalizedMessage();
	        if (throwableMsg == null || throwableMsg.trim().length() == 0){
	        	throwableMsg = t.toString();
	        }
		}
		finalArgs[4] = throwableMsg;
		if (argsLength > 0){
			System.arraycopy(args, 0, finalArgs, 5, argsLength);
		}
		StringBuilder builder = new StringBuilder("[");
		for (int i = 0; i < finalArgs.length; i++) {
			Object argument = finalArgs[i];
			if (argument != null){
				builder.append(argument);
				if (i + 1 < finalArgs.length){
					builder.append(",");	
				}
			}
		}
		builder.append("]");
		toString = builder.toString();
	}
	
	public Object[] getAsArray(){
		return finalArgs;
	}
	
	public String toString(){
		return toString;
	}
}
