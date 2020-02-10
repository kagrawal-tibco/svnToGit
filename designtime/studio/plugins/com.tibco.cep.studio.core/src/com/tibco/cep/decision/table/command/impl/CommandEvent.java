package com.tibco.cep.decision.table.command.impl;

import java.util.EventObject;

import com.tibco.cep.decision.table.command.IExecutableCommand;

public class CommandEvent<C extends IExecutableCommand> extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean isError;
	
	
	/**
	 * @param source
	 */
	public CommandEvent(C source) {
		super(source);
	}


	/**
	 * @return the isError
	 */
	public final boolean isError() {
		return isError;
	}


	/**
	 * @param isError the isError to set
	 */
	public final void setError(boolean isError) {
		this.isError = isError;
	}
	
	
}
