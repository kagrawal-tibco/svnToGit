/**
 * 
 */
package com.tibco.cep.decision.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import com.tibco.be.parser.RuleError;
import com.tibco.cep.decision.table.language.DTValidator;
import com.tibco.cep.decision.table.language.ErrorListRuleErrorRecorder;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.studio.cli.studiotools.StudioCommandLineInterpreter;
import com.tibco.cep.studio.core.api.IndexOps;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * CLI interpreter to validate decision table for syntax errors.
 * @author aathalye
 *
 */
public class ValidateDecisionTableCLI extends DecisionTableCLI {
	
	private final static String OPERATION_VALIDATE_DT = "validateTable";
	
	private final static String FLAG_VALIDATE_DT_HELP = "-h"; 	// Prints help
	
	private final static String FLAG_STUDIO_PROJECT_PATH = "-studioProjPath";	// Path of new project in Studio
	
	private final static String FLAG_DECISION_TABLE_PATH = "-dtPath";	// Path of DT relative to project

	private final static String FLAG_STUDIO_PROJECT_WORKSPACE_PATH = "-wsPath";	// Path of workspace to load existing index.

	@Override
	public String[] getFlags() {
		return new String[] { 
			FLAG_STUDIO_PROJECT_PATH, 
			FLAG_DECISION_TABLE_PATH,
			FLAG_STUDIO_PROJECT_WORKSPACE_PATH
		};
	}
	
	public static void main(String[] args) throws Exception {
		if (args.length == 0 || (args.length == 1 && args[0].trim().endsWith("help"))) {
			StudioCommandLineInterpreter.printPrologue(new String[]{});
			System.out.println(new ImportExcelCLI().getHelp());
			//System.exit(-1);
			return;
		}
		String newArgs[] = new String[args.length + 2];
		newArgs[0] = DecisionTableCLI.OPERATION_CATEGORY_DT;
		newArgs[1] = OPERATION_VALIDATE_DT; 
		System.arraycopy(args, 0, newArgs, 2, args.length);
//		StudioCommandLineInterpreter.addInterpreter(new ValidateDecisionTableCLI());
		StudioCommandLineInterpreter.executeCommandLine(newArgs);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cli.studiotools.ICommandLineInterpreter#getHelp()
	 */
	@Override
	public String getHelp() {
		String helpMsg = "Usage: " + getOperationCategory() + " " + getOperationFlag() + " " + getUsageFlags() + "\n" +
			"   where, \n" +
			"	-h (optional) prints this usage \n" +
			"	-studioProjPath (required) The project path where the studio project resides. \n" +
			"	-dtPath (required) Path of the Decision Table relative to project that decision table should implement (exclude the extension). \n" +
			"	-wsPath (optional) The existing Studio workspace folder.  If specified, the existing project metadata will be loaded from this location.  If omitted, the\n" +
			"		project will be fully reloaded from disk.  For large projects, this can reduce processing time.  For smaller projects, this should be omitted to ensure\n" +
			"		the project metadata is not stale.";
		return helpMsg;
	}

	@Override
	public String getOperationFlag() {
		return OPERATION_VALIDATE_DT;
	}

	@Override
	public String getOperationName() {
		return ("Validate Decision Table");
	}
	
	public String getUsageFlags() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		stringBuilder.append(FLAG_VALIDATE_DT_HELP);
		stringBuilder.append("]");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_STUDIO_PROJECT_PATH);
		stringBuilder.append(" ");
		stringBuilder.append("<Studio Project Path>");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_DECISION_TABLE_PATH);
		stringBuilder.append(" ");
		stringBuilder.append("<Path relative to project path>");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_STUDIO_PROJECT_WORKSPACE_PATH);
		stringBuilder.append(" ");
		stringBuilder.append("<Existing Studio workspace folder>");
		return stringBuilder.toString();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cli.studiotools.ICommandLineInterpreter#runOperation(java.util.Map)
	 */
	@Override
	public boolean runOperation(Map<String, String> argsMap) throws Exception {
		if (checkIfExcludeOperation(argsMap))
			return true;
		
		System.out.println("Validating Decision Table");
		if (argsMap.containsKey(FLAG_VALIDATE_DT_HELP)) {
			System.out.println(getHelp());
			return true;
		}
		//Get all arguments
		String studioProjectPath = argsMap.get(FLAG_STUDIO_PROJECT_PATH);
		if (studioProjectPath == null) {
			System.out.println("Invalid arguments.  Missing required flag "+FLAG_STUDIO_PROJECT_PATH);
			return false;
		}
		String dtPath = argsMap.get(FLAG_DECISION_TABLE_PATH);
		if (dtPath == null) {
			System.out.println("Invalid arguments.  Missing required flag "+FLAG_DECISION_TABLE_PATH);
			return false;
		}
		String projectWorkspacePath = argsMap.get(FLAG_STUDIO_PROJECT_WORKSPACE_PATH); // optional - don't check for null
		try {
			executeOp(studioProjectPath, dtPath, projectWorkspacePath);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}
	
	/**
	 * 
	 * @param projectRootDir
	 * @param dtPath
	 * @throws Exception
	 */
	private void executeOp(String projectRootDir,
			               String dtPath,
			               String wsPath) throws Exception {
		DecisionTableCLIOpsUtils.registerEPackages();
		//Load Index
		if (wsPath != null) {
			IndexOps.loadIndex(wsPath, projectRootDir);
		} else {
			IndexOps.loadIndex(projectRootDir);
		}
		
		System.out.printf("Beginning Validation");
		File projectFile = new File(projectRootDir);
		String projectName = projectFile.getName();
		//Get DT at this location
		String dtFileAbsPath = projectRootDir + "/" + dtPath + ".rulefunctionimpl";
		System.out.println();
		System.out.printf("Loading Decision Table File at %s", dtFileAbsPath);
		System.out.println();
		InputStream inputStream = new FileInputStream(dtFileAbsPath);
		Table table = 
			(Table)CommonIndexUtils.loadEObject(dtFileAbsPath, inputStream);
		//Validate it
		ErrorListRuleErrorRecorder errorRecorder = new ErrorListRuleErrorRecorder();
		DTValidator.checkTable(table, projectName, errorRecorder);
		System.out.println();
		if (!errorRecorder.hasErrors()) {
			System.out.printf("Decision Table %s validated successfully.", dtPath);
		} else {
			for (RuleError ruleError : errorRecorder.getErrorList()) {
				System.out.printf("Decision Table Syntax Validation Error %s in source %s", 
						          ruleError.getMessage(), ruleError.getSource());
				System.out.println();
			}
		}
	}
}
