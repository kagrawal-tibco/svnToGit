package com.tibco.cep.studio.application;

/**
 * Interface defining the application's command IDs. Key bindings can be defined
 * for specific commands. To associate an action with a command, use
 * IAction.setActionDefinitionId(commandId).
 * 
 * @see org.eclipse.jface.action.IAction#setActionDefinitionId(String)
 */
public interface ICommandIds {

	public static final String CMD_SHOW_CONSOLE = "com.tibco.cep.studio.application.console";
	public static final String CMD_PREFERENCES = "com.tibco.cep.studio.application.preferences";
	public static final String CMD_NAVIGATION = "com.tibco.cep.studio.application.navigation";
	public static final String CMD_SHOWVIEW = "com.tibco.cep.studio.application.showview";
	public static final String CMD_KEY_ASSIST = "com.tibco.cep.studio.application.keyassist";
	public static final String CMD_SHOW_PROPERTIES = "com.tibco.cep.studio.application.properties";
	public static final String CMD_SHOW_PALETTE = "com.tibco.cep.studio.application.palette";
	public static final String CMD_SHOW_OVERVIEW = "com.tibco.cep.studio.application.overview";
	public static final String CMD_SHOW_PROJECT_EXPLORER = "com.tibco.cep.studio.application.project.explorer";
}
