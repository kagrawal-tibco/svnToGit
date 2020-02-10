package com.tibco.cep.webstudio.client.model.ruletemplate;

import java.io.Serializable;

/**
 * Model for Rule template instance commands
 * 
 * @author Vikram Patil
 */
public class CommandInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String commandType;
	private String alias;
	private String type;

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CommandInfo() {
		super();
	}

	public CommandInfo(String type, String commandType, String alias) {
		this.setType(type);
		this.setCommandType(commandType);
		this.setAlias(alias);
	}

	public String getCommandType() {
		return this.commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
	
		CommandInfo cmdInfo = (CommandInfo) obj;
		return (this.getAlias().equals(cmdInfo.getAlias()) && this.getType().equals(cmdInfo.getType()) && this
				.getCommandType().equals(cmdInfo.getCommandType()));
	}
}
