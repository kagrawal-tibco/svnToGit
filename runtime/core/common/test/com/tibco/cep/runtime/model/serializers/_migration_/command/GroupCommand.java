package com.tibco.cep.runtime.model.serializers._migration_.command;

import com.tibco.cep.runtime.model.serializers._migration_.ConversionScratchpad;

/*
* Author: Ashwin Jayaprakash Date: Jan 21, 2009 Time: 6:50:37 PM
*/
public class GroupCommand implements Command {
    protected Command[] commands;

    public GroupCommand() {
    }

    public Command[] getCommands() {
        return commands;
    }

    public void setCommands(Command[] commands) {
        this.commands = commands;
    }

    //----------

    public void execute(ConversionScratchpad scratchpad) throws Exception {
        for (Command command : commands) {
            try {
                command.execute(scratchpad);
            }
            catch (NonFatalException e) {
                //todo;
            }
            catch (Exception e) {
                //todo

                break;
            }
        }
    }
}
