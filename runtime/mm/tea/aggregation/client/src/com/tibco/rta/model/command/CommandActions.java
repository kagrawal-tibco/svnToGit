package com.tibco.rta.model.command;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 23/10/12
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */
public enum CommandActions {

    CONNECT("connect"),

    CREATE("create"),

    REMOVE("remove"),

    LINK("link"),

    SHOW("show"),

    EXPORT("export");

    private String action;

    CommandActions(String action) {
        this.action = action;
    }

    public static CommandActions getByLiteral(String literal) {
        CommandActions[] commandActions = CommandActions.values();
        for (CommandActions commandAction : commandActions) {
            if (commandAction.action.equals(literal)) {
                return commandAction;
            }
        }
        return null;
    }
}
