package com.tibco.rta.model.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 23/10/12
 * Time: 10:27 AM
 * Representation of contents of a command
 */
public class CommandObject {

    /**
     * The actual command type like schema, cube etc.
     */
    private CommandType commandType;

    /**
     * Name value pairs of attributes
     */
    private Map<String, String> attributes = new HashMap<String, String>();

    /**
     * Maintain a reference to the actual model parent object;
     * For instance schema is parent of cube, which is a parent of hierarchy and so on
     */
    private Object parentObject;



    public void addAttribute(String name, String value) {
        attributes.put(name, value);
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public String getAttributeValue(String attributeName) {
        return attributes.get(attributeName);
    }

    public Object getParentObject() {
        return parentObject;
    }

    public void setParentObject(Object parentObject) {
        this.parentObject = parentObject;
    }
}
