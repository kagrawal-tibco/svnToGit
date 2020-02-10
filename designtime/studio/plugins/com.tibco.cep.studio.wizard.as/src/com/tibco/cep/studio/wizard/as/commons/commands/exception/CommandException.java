package com.tibco.cep.studio.wizard.as.commons.commands.exception;

public class CommandException extends Exception {

	public CommandException() {
	}

	public CommandException(String message) {
		super(message);
	}

	public CommandException(Throwable cause) {
		super(cause);
	}

	public CommandException(String message, Throwable cause) {
		super(message, cause);
	}

}
