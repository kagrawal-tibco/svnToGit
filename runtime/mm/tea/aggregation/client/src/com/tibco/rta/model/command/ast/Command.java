package com.tibco.rta.model.command.ast;

import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.command.CommandLineSession;
import com.tibco.rta.model.command.CommandObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 27/11/12
 * Time: 3:11 PM
 * An abstract representation of a command.
 */
public abstract class Command {

    /**
     * The target objects affected
     */
    protected List<CommandObject> commandObjects = new ArrayList<CommandObject>();

    /**
     * The dependency using prepositions
     */
    protected Stack<CommandObject> prepositionStack = new Stack<CommandObject>();

    /**
     * Created/deleted/modified etc.
     */
    protected RtaSchema affectedSchemaObject;


    public void addCommandObject(CommandObject commandObject) {
        commandObjects.add(commandObject);
    }

    public void push(CommandObject commandObject) {
        prepositionStack.push(commandObject);
    }

    public CommandObject pop() {
        return prepositionStack.pop();
    }

    public abstract void execute(CommandLineSession session) throws Exception;
}
