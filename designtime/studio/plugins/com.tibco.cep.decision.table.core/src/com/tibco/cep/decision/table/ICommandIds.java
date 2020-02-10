package com.tibco.cep.decision.table;

/**
 * Interface defining the application's command IDs.
 * Key bindings can be defined for specific commands.
 * To associate an action with a command, use IAction.setActionDefinitionId(commandId).
 *
 * @see org.eclipse.jface.action.IAction#setActionDefinitionId(String)
 */
public interface ICommandIds {


    public static final String CMD_DT_OPEN = "com.tibco.cep.decision.table.dt.open";
    public static final String CMD_DT_SAVE = "com.tibco.cep.decision.table.dt.save";
    public static final String CMD_DT_DELETE = "com.tibco.cep.decision.table.dt.delete";
    public static final String CMD_DT_RENAME = "com.tibco.cep.decision.table.dt.rename";
    public static final String CMD_DT_NEW = "com.tibco.cep.decision.table.dt.new";
    public static final String CMD_DT_SAVE_AS = "com.tibco.cep.decision.table.dt.saveas";
    public static final String CMD_DT_DEPLOY = "com.tibco.cep.decision.table.dt.deploy";
    public static final String CMD_DT_GENERATE_CLASS = "com.tibco.cep.decision.table.dt.genClass";
    public static final String CMD_DT_DEPLOY_ALL = "com.tibco.cep.decision.table.dt.deployall";
    public static final String CMD_DT_UNDEPLOY_ALL = "com.tibco.cep.decision.table.dt.undeployall";
    public static final String CMD_DT_SHOW_ALL = "com.tibco.cep.decision.table.dt.showall";
    public static final String CMD_DT_MERGE_ROWS = "com.tibco.cep.decision.table.dt.merge.rows";
    
    public static final String CMD_IMPORT_EXCEL = "com.tibco.cep.decision.table.import.excel";
    public static final String CMD_EXPORT_EXCEL = "com.tibco.cep.decision.table.export.excel";
    public static final String CMD_VALIDATE = "com.tibco.cep.decision.table.validate";
    public static final String CMD_PRINTPREVIEW = "com.tibco.cep.decision.table.printpreview";
    
    public static final String CMD_KEY_ASSIST = "com.tibco.cep.decision.table.key.assist";
    
    public static final String CMD_OPEN_MESSAGE = "com.tibco.cep.decision.table.openMessage";    
    public static final String CMD_FILE_CLOSE = "com.tibco.cep.decision.table.file.close";
    public static final String CMD_FILE_CLOSE_ALL = "com.tibco.cep.decision.table.file.close.all";
    public static final String CMD_FILE_SAVE_ALL = "com.tibco.cep.decision.table.file.save.all";
    public static final String CMD_CONVERT_LINE_DELIMITERS = "com.tibco.cep.decision.table.file.convert.delimiters";
	public static final String CMD_PREVIOUS_EDITOR = "com.tibco.cep.decision.table.previous.editor";
	public static final String CMD_NEXT_EDITOR = "com.tibco.cep.decision.table.next.editor";
	public static final String CMD_ACTIVATE_EDITOR = "com.tibco.cep.decision.table.activate.editor";
	public static final String CMD_MAX_EDITOR = "com.tibco.cep.decision.table.maximize.editor";
	public static final String CMD_MIN_EDITOR = "com.tibco.cep.decision.table.minimize.editor";
	public static final String CMD_SWITCH_EDITOR = "com.tibco.cep.decision.table.switch.editor";
    
    public static final String CMD_SHOW_WORKLIST = "com.tibco.cep.decision.table.dt.worklist";
    public static final String CMD_SHOW_EMPTY_WORKLIST = "com.tibco.cep.decision.table.dt.emptyworklist";
    
    public static final String CMD_SHOW_MY_REQUEST = "com.tibco.cep.decision.table.dt.myrequest";
    public static final String CMD_CHECKOUT_PROJECT = "com.tibco.cep.decision.table.decision.table.checkout.project";
    public static final String CMD_SHOW_TESTDATA = "com.tibco.cep.decision.table.dt.testdata";
    public static final String CMD_MODE_NORMAL = "com.tibco.cep.decision.table.dt.mode.normal";
    public static final String CMD_MODE_TEST = "com.tibco.cep.decision.table.dt.mode.test";
    public static final String CMD_ANALYZE_TABLE = "com.tibco.cep.decision.table.analyze.table";
	public static final String CMD_VALIDATE_TABLE = "com.tibco.cep.decision.table.validate.table";
	public static final String CMD_SHOW_TABLE_PROPERTY = "com.tibco.cep.decision.table.property";


}
