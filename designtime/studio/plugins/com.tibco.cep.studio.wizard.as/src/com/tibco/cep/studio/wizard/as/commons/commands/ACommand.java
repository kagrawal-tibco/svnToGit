package com.tibco.cep.studio.wizard.as.commons.commands;

import com.tibco.cep.studio.wizard.as.commons.utils.AIdentifiable;

public abstract class ACommand extends AIdentifiable implements ICommand {

	protected ACommand(String id) {
		super(id);
	}

}
