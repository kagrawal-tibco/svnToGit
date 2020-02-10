package com.tibco.cep.studio.wizard.as.commons.commands;

import com.tibco.cep.studio.wizard.as.commons.commands.exception.CommandException;

public interface ICommand {

	void execute(Object value) throws CommandException;

}
