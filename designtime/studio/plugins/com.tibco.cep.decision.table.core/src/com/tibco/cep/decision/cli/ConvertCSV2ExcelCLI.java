/**
 * 
 */
package com.tibco.cep.decision.cli;

import java.util.Map;

import com.tibco.cep.decision.table.provider.csv.CSVExcelConverter;
import com.tibco.cep.studio.cli.studiotools.StudioCommandLineInterpreter;

/**
 * @author schelwa
 *
 */
public class ConvertCSV2ExcelCLI extends DecisionTableCLI {
	
	private final static String OPERATION_CONVERT_CSV2EXCEL = "convertCSV2Excel";
	
	private final static String FLAG_HELP = "-h"; 	// Prints help
	
	private final static String FLAG_CONVERT_CSV_FILE = "-csvPath";	// Absolute Path of the csv file

	private final static String FLAG_CONVERT_EXCEL_FILE = "-excelPath";	// Absolute Path of the excel file to convert
	
	private final static String FLAG_CSV_SEPERATOR = "-s";	// The column separator that is used in the csvFile. If not specified (,) is used as default
	
	private final static String DEFAULT_EXCEL_SHEET_NAME = "DecisionTable";	// Default Excel Sheet Name.

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cli.studiotools.ICommandLineInterpreter#getFlags()
	 */
	@Override
	public String[] getFlags() {
		return new String[] {
				FLAG_CONVERT_CSV_FILE,
				FLAG_CONVERT_EXCEL_FILE,
				FLAG_CSV_SEPERATOR
		};
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cli.studiotools.ICommandLineInterpreter#getHelp()
	 */
	@Override
	public String getHelp() {
		String helpMsg = "Usage: convertCSV2Excel " + getUsageFlags() + "\n" +
			"where, \n" +
			"	-h (optional) prints this usage \n" +
			"	-csvPath (required) Absolute Path of the csv file used as source. \n" +
			"	-excelPath (required) Absolute path of Excel file to convert. \n" +
			"	-s (optional) The column separator that is used in the csvFile. If not specified (,) is used as default." ;
		return helpMsg;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cli.studiotools.ICommandLineInterpreter#getOperationFlag()
	 */
	@Override
	public String getOperationFlag() {
		return OPERATION_CONVERT_CSV2EXCEL;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cli.studiotools.ICommandLineInterpreter#getOperationName()
	 */
	@Override
	public String getOperationName() {
		return ("Convert CSV File To Excel File");
	}
	
	@Override
	public String getUsageFlags() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		stringBuilder.append(FLAG_HELP);
		stringBuilder.append("]");
		stringBuilder.append(FLAG_CONVERT_CSV_FILE);
		stringBuilder.append(" ");
		stringBuilder.append("<CSV File Path>");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_CONVERT_EXCEL_FILE);
		stringBuilder.append(" ");
		stringBuilder.append("<Excel File Path>");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_CSV_SEPERATOR);
		stringBuilder.append(" ");
		stringBuilder.append("<Column Separator>");
		stringBuilder.append(" ");
		return stringBuilder.toString();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cli.studiotools.ICommandLineInterpreter#runOperation(java.util.Map)
	 */
	@Override
	public boolean runOperation(Map<String, String> argsMap) throws Exception {
		if (checkIfExcludeOperation(argsMap))
			return true;
		
		if (argsMap.containsKey(FLAG_HELP)) {
			System.out.println(getHelp());
			return true;
		}
		System.out.println("Converting CSV to Excel");
		//Get all arguments
		String csvFilePath = argsMap.get(FLAG_CONVERT_CSV_FILE);
		if (csvFilePath == null) {
			System.out.println(getHelp());
			return false;
		}
		String xlsFilePath = argsMap.get(FLAG_CONVERT_EXCEL_FILE);
		if (xlsFilePath == null) {
			System.out.println(getHelp());
			return false;
		}
		
		String columnSeparator = argsMap.get(FLAG_CSV_SEPERATOR);
		
		try {
			executeOp(csvFilePath, xlsFilePath, DEFAULT_EXCEL_SHEET_NAME,columnSeparator);
			System.out.printf("Generated Excel File at location %s", xlsFilePath);
			System.out.println();
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 
	 * @param studioProjectPath
	 * @param workspacePath
	 * @param xlFilePath
	 * @param dtName
	 * @param virtualFuncPath
	 * @param dtFolderPath
	 */
	private void executeOp(String csvFilePath, 
			               String xlsFilePath,
			               String sheetName,
			               String columnSeparator) throws Exception {
		DecisionTableCLIOpsUtils.registerEPackages();
		
		CSVExcelConverter.convert(csvFilePath, xlsFilePath, sheetName, columnSeparator);
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		if (args.length == 0 || (args.length == 1 && args[0].trim().endsWith("help"))) {
			StudioCommandLineInterpreter.printPrologue(new String[]{});
			System.out.println((new ConvertCSV2ExcelCLI()).getHelp());
			return;
		}
		String newArgs[] = new String[args.length + 2];
		newArgs[0] = DecisionTableCLI.OPERATION_CATEGORY_DT;
		newArgs[1] = OPERATION_CONVERT_CSV2EXCEL; 
		System.arraycopy(args, 0, newArgs, 2, args.length);
		StudioCommandLineInterpreter.executeCommandLine(newArgs);
	}
}
