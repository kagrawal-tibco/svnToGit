package com.tibco.rta.impl;

import com.tibco.rta.RtaCommand;

public class RtaCommandImpl extends RtaAsyncMessageImpl implements RtaCommand {
	
	private static final long serialVersionUID = -3451561638707945084L;

	protected Command command;

    public RtaCommandImpl() {
    }

    public RtaCommandImpl(Command command) {
        this.command = command;
    }

    @Override
	public Command getCommand() {
		return command;
	}

    public void setCommand(Command command) {
        this.command = command;
    }
}
