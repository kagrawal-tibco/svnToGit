package com.tibco.rta.model.command.ast;

import com.tibco.rta.model.command.CommandActions;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 28/11/12
 * Time: 10:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandFactory {

    public static Command getCommand(CommandActions commandAction) {
        switch (commandAction) {
        case CREATE :
            return new CreateCommand();
        default :
            return null;
        }
    }
}
