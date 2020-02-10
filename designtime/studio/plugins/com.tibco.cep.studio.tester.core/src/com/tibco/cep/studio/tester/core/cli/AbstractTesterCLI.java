package com.tibco.cep.studio.tester.core.cli;

import com.tibco.cep.studio.cli.studiotools.AbstractCLI;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractTesterCLI extends AbstractCLI{

	public static final String OPERATION_CATEGORY_TESTER = "-tester";
	
	/*protected final static String FLAG_HELP = "-h"; 	    // Prints help
	protected final static String FLAG_OVERWRITE = "-x"; 	// Overwrite output files if is exists
	protected final static String FLAG_HTML = "-html"; 	    // convert output format to html format
	protected final static String FLAG_XSLTFILE_PATH = "-xs";// specifies the xslt file path
	protected final static String FLAG_OUTPUTPATH = "-o"; 	// Output Directory Path  
	protected final static String FLAG_PROJECTPATH = "-p"; 	// Path of the Studio project
	protected final static String FLAG_PROJECTNAME = "-n"; 	// Name of the Studio project
	protected final static String FLAG_FILEPATH = "-f"; 	// Test Data Excel File
	protected final static String FLAG_INPUTDIRPATH = "-d"; // Path of the Test Data Excel Files Directory
	
	protected final static String FLAG_EAR_PATH = "-r"; // Tester EAR file path
	protected final static String FLAG_PROCESSING_UNIT = "-u"; //Tester Processing unit
	protected final static String FLAG_EXTENDED_CP = "-cp"; // extended classpath
	protected final static String FLAG_CDD_PATH = "-c"; // Tester cdd file path
	protected final static String FLAG_WORKING_DIR = "-w"; // Tester working directory*/

	
	@Override
	public String getOperationCategory() {
		return OPERATION_CATEGORY_TESTER;
	}
}
