package com.tibco.rta.model.command;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 23/10/12
 * Time: 10:28 AM
 * To change this template use File | Settings | File Templates.
 */
public enum CommandType {

    SCHEMA("schema"),

    CUBE("cube"),

    HIERARCHY("hierarchy"),

    DIMENSION("dimension"),

    ATTRIBUTE("attribute"),

    MEASUREMENT("measurement"),

    URL("url");
    
    private String command;
    
    private CommandType(String command) {
        this.command = command;
    }
    
    public static CommandType getByLiteral(String literal) {
        CommandType[] commandTypes = CommandType.values();
        for (CommandType commandType : commandTypes) {
            if (commandType.command.equals(literal)) {
                return commandType;
            }
        }
        return null;
    }
}
