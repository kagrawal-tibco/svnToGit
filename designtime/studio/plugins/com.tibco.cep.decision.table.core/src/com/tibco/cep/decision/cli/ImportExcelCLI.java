package com.tibco.cep.decision.cli;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.spi.ExcelConversionOps;
import com.tibco.cep.decision.table.utils.DecisionTableCoreUtil;
import com.tibco.cep.decisionproject.acl.ValidationError;
import com.tibco.cep.studio.cli.studiotools.StudioCommandLineInterpreter;

/*
@author ssailapp
@date Jan 17, 2010 2:03:52 PM
 */

public class ImportExcelCLI extends DecisionTableCLI {

	private final static String OPERATION_IMPORT_EXCEL = "importExcel";
	
	private final static String FLAG_IMPORTXL_HELP = "-h"; 	// Prints help
	
	private final static String FLAG_STUDIO_PROJECT_PATH = "-studioProjPath";	// Path of new project in Studio
	
	private final static String FLAG_IMPORT_EXCEL_FILE = "-excelPath";	// Absolute Path of the excel file to import
	
	private final static String FLAG_DECISION_TABLE_NAME = "-dtName";	// Name of new Decision Table
	
	private final static String FLAG_DECISION_TABLE_FOLDER_PATH = "-folderPath";	// Path of new project in Studio
	
	private final static String FLAG_VIRTUAL_RULE_FUNCTION_PATH = "-vrfPath";	// Virtual Rule Function Path
	
	private final static String FLAG_STUDIO_PROJECT_WORKSPACE_PATH = "-wsPath";	// Path of workspace to load existing index.

	@Override
	public String[] getFlags() {
		return new String[] { 
			FLAG_STUDIO_PROJECT_PATH, 
			FLAG_IMPORT_EXCEL_FILE, 
			FLAG_DECISION_TABLE_NAME,
			FLAG_DECISION_TABLE_FOLDER_PATH,
			FLAG_VIRTUAL_RULE_FUNCTION_PATH,
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
		newArgs[1] = OPERATION_IMPORT_EXCEL; 
		System.arraycopy(args, 0, newArgs, 2, args.length);
//		StudioCommandLineInterpreter.addInterpreter(new ImportExcelCLI());
		StudioCommandLineInterpreter.executeCommandLine(newArgs);
	}

	@Override
	public String getHelp() {
		String helpMsg = "Usage: " + getOperationCategory() + " " + getOperationFlag() + " " + getUsageFlags() + "\n" +
				"where, \n" +
				"	-h (optional) prints this usage \n" +
				"	-studioProjPath (required) The project path where the studio project resides. \n" +
				"	-excelPath (required) Absolute path of Excel file to import. \n" +				
				"	-dtName (required) Name of the Decision Table to create. \n" +
				"	-folderPath (required) Folder path relative to project path for creating Decision Table. \n" +
				"	-vrfPath (required) Path of the virtual Rule function relative to project that decision table should implement (exclude the extension). \n" +
				"	-wsPath (optional) The existing Studio workspace folder.  If specified, the existing project metadata will be loaded from this location.  If omitted, the\n" +
				"		project will be fully reloaded from disk.  For large projects, this can reduce processing time.  For smaller projects, this should be omitted to ensure\n" +
				"		the project metadata is not stale.";
		return helpMsg;
	}

	@Override
	public String getOperationFlag() {
		return OPERATION_IMPORT_EXCEL;
	}

	@Override
	public String getOperationName() {
		return ("Import Excel File");
	}
	
	public String getUsageFlags() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		stringBuilder.append(FLAG_IMPORTXL_HELP);
		stringBuilder.append("]");
		stringBuilder.append(FLAG_STUDIO_PROJECT_PATH);
		stringBuilder.append(" ");
		stringBuilder.append("<Studio Project Path>");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_IMPORT_EXCEL_FILE);
		stringBuilder.append(" ");
		stringBuilder.append("<Excel File Path>");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_DECISION_TABLE_NAME);
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_DECISION_TABLE_FOLDER_PATH);
		stringBuilder.append(" ");
		stringBuilder.append("<Path relative to project path>");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_VIRTUAL_RULE_FUNCTION_PATH);
		stringBuilder.append(" ");
		stringBuilder.append("<Path relative to project path>");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_STUDIO_PROJECT_WORKSPACE_PATH);
		stringBuilder.append(" ");
		stringBuilder.append("<Existing Studio workspace folder>");
		return stringBuilder.toString();
	}

	@Override
	public boolean runOperation(Map<String, String> argsMap) throws Exception {
		if (checkIfExcludeOperation(argsMap))
			return true;
		
		if (argsMap.containsKey(FLAG_IMPORTXL_HELP)) {
			System.out.println(getHelp());
			return true;
		}
		System.out.println("Importing Excel");
		//Get all arguments
		String studioProjectPath = argsMap.get(FLAG_STUDIO_PROJECT_PATH);
		if (studioProjectPath == null) {
			System.out.println("Invalid arguments.  Missing required flag "+FLAG_STUDIO_PROJECT_PATH);
			return false;
		}
		
		String xlFilePath = argsMap.get(FLAG_IMPORT_EXCEL_FILE);
		if (xlFilePath == null) {
			System.out.println("Invalid arguments.  Missing required flag "+FLAG_IMPORT_EXCEL_FILE);
			return false;
		}
		String dtName = argsMap.get(FLAG_DECISION_TABLE_NAME);
		if (dtName == null) {
			System.out.println("Invalid arguments.  Missing required flag "+FLAG_DECISION_TABLE_NAME);
			return false;
		}
		String vrfPath = argsMap.get(FLAG_VIRTUAL_RULE_FUNCTION_PATH);
		if (vrfPath == null) {
			System.out.println("Invalid arguments.  Missing required flag "+FLAG_VIRTUAL_RULE_FUNCTION_PATH);
			return false;
		}
		String dtFolder = argsMap.get(FLAG_DECISION_TABLE_FOLDER_PATH);
		if (dtFolder == null) {
			System.out.println("Invalid arguments.  Missing required flag "+FLAG_DECISION_TABLE_FOLDER_PATH);
			return false;
		}
		String projectWorkspacePath = argsMap.get(FLAG_STUDIO_PROJECT_WORKSPACE_PATH); // optional - don't check for null
		try {
			executeOp(studioProjectPath, 
					  projectWorkspacePath,   
					  xlFilePath, 
					  dtName, 
					  vrfPath, 
					  dtFolder);
			System.out.printf("Generated Decision Table %s at location %s", dtName, studioProjectPath + dtFolder);
			System.out.println();
			return true;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	/**
	 * 
	 * @param studioProjectPath
	 * @param xlFilePath
	 * @param dtName
	 * @param virtualFuncPath
	 * @param dtFolderPath
	 */
	private void executeOp(String studioProjectPath, 
			               String workspacePath,
			               String xlFilePath,
			               String dtName,
			               String virtualFuncPath,
			               String dtFolderPath) throws Exception {
		DecisionTableCLIOpsUtils.registerEPackages();
		
		List<ValidationError> validationErrors = new ArrayList<ValidationError>();
		Table tableEModel = ExcelConversionOps.convertToDecisionTable(studioProjectPath,
													 workspacePath,
													 virtualFuncPath,
													 xlFilePath,
													 dtFolderPath,
													 dtName,
													 validationErrors);
		
		if (validationErrors.isEmpty()) {
			String decisionTableAbsolutePath = 
				new StringBuilder(studioProjectPath).append(File.separatorChar)
				.append(tableEModel.getFolder()).append(dtName).append(".rulefunctionimpl").toString();
			//Write it out
			DecisionTableCoreUtil.saveIFileImpl(decisionTableAbsolutePath, tableEModel);
		} else {
			//Print errors.
			for (ValidationError validationError : validationErrors) {
				System.err.printf("Excel Validation Error %s", validationError.getErrorMessage());
			}
		}
	}
}
