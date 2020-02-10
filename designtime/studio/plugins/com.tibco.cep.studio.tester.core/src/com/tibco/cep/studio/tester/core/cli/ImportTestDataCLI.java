package com.tibco.cep.studio.tester.core.cli;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.tibco.cep.studio.cli.studiotools.Messages;
import com.tibco.cep.studio.cli.studiotools.StudioCommandLineInterpreter;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.utils.IndexBuilder;
import com.tibco.cep.studio.tester.core.utils.TestDataCsvImportHandler;
import com.tibco.cep.studio.tester.core.utils.TestDataExcelImportHandler;
import com.tibco.cep.studio.tester.core.utils.TesterCoreUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class ImportTestDataCLI extends AbstractTesterCLI {

	private final static String OPERATION_IMPORT = "import";
	
	protected final static String FLAG_HELP 		= "-h"; 	// Prints help
	protected final static String FLAG_OVERWRITE 	= "-x"; 	
	protected final static String FLAG_APPEND 	    = "-a";// Prints overwrite
	protected final static String FLAG_INPUTPATH 	= "-i"; 	// Prints input path
	protected final static String FLAG_PROJECTPATH 	= "-p"; 	// Path of the Studio project
	protected final static String FLAG_FILETYPE 	= "-t"; 	// 
	protected final static String FLAG_SEPERATOR 	= "-s"; 	// 
	protected final static String FLAG_OUTPUTPATH	= "-o";
	
	protected final static String EXTENTION_PREFIX = ".";
	protected final static String FILETYPE_CSV = "csv";
	protected final static String FILETYPE_XLS = "xls";
	protected final static String SEPARATOR_COMMA = ",";
	protected final static String SEPARATOR_SEMICOLON = ";";
	protected final static String LISTFILE_EXTN = "xml";
	
	protected final static List<String> inputTypes 		= Arrays.asList(FILETYPE_CSV, FILETYPE_XLS); 	// List of supported output types
	protected final static List<String> columnSeperators  	= Arrays.asList(SEPARATOR_COMMA, SEPARATOR_SEMICOLON); 	// List of supported column separators for csv files
	
	List<File> inputFiles;

	public static void main(String[] args) throws Exception {
		if (args.length == 0 || 
				(args.length == 1 && args[0].trim().endsWith("help"))) {
			StudioCommandLineInterpreter.printPrologue(new String[]{});
			System.out.println(new ImportTestDataCLI().getHelp());
			//System.exit(-1);
			return;
		}
		String newArgs[] = new String[args.length+2];
		newArgs[0] = OPERATION_CATEGORY_TESTER;
		newArgs[1] = OPERATION_IMPORT; 
		System.arraycopy(args, 0, newArgs, 2, args.length);
		StudioCommandLineInterpreter.executeCommandLine(newArgs);
	}

	@Override
	public String[] getFlags() {
		return new String[] {   FLAG_HELP,
								FLAG_OVERWRITE, 
								FLAG_APPEND,
								FLAG_INPUTPATH,
								FLAG_PROJECTPATH,
								FLAG_FILETYPE,
								FLAG_SEPERATOR
								};
	}

	@Override
	public String getOperationFlag() {
		return OPERATION_IMPORT;
	}

	@Override
	public String getOperationName() {
		return ("import Test Data");
	}

	@Override
	public String getUsageFlags() {
		return ("[" + FLAG_HELP + "] " +
				FLAG_INPUTPATH + " <inputPath> "+
				FLAG_PROJECTPATH + " <projectPath> " +
				"[" +FLAG_FILETYPE + " < csv | xls >" + "] "+
				"[" +FLAG_SEPERATOR+ " < , | ; >"+"] " + 
				"[" + FLAG_OUTPUTPATH+ "] " +
				"[" + FLAG_OVERWRITE + "] " +
				"[" + FLAG_APPEND + "] ");	
	}

	@Override
	public String getHelp() {
		String helpMsg = "Usage: importTestData " + getUsageFlags() + "\n" +
				"where, \n" +
				"	-h (optional) prints this usage \n" +
				"	-i specifies the input file path \n" +
				"	-p specifies the project path \n" +
				"	-t (optional) if the input file type is a directory, please specify type of file to be processed.\n"+
				"	-s (optional) if the input file types are csv then, specify the column seperator.\n"+
				"	-o (optional) output path.\n" + 
                "	-x (optional) overwrites the specified output directory if it exists." +
				"   -a (optional) appends to existing test data \n";
        return helpMsg;
	}

	@Override
	public boolean runOperation(Map<String, String> argsMap) throws Exception {
		if (checkIfExcludeOperation(argsMap))
			return true;
		
		if (argsMap.containsKey(FLAG_HELP)) {
			System.out.println(getHelp());
			return true;
		}
		
		boolean overwrite = false;
		if (argsMap.containsKey(FLAG_OVERWRITE)) {
			overwrite = true;
		}
		
		boolean isAppend = false;
		if (argsMap.containsKey(FLAG_APPEND)) {
			isAppend = true;
		}
		
		String projectPath = argsMap.get(FLAG_PROJECTPATH);
		if (projectPath == null) {
			throw new Exception("Project path not specified.");
		}
		
		File projectDir = new File(projectPath);
		if (!projectDir.exists()) {
			throw new Exception("Project path does not exist.");
		}
		String outputStringPath = argsMap.get(FLAG_OUTPUTPATH);
		if (outputStringPath == null) {
			outputStringPath = projectPath  + "/" + "TestData";; 
		}
		
		StudioProjectConfiguration spc = IndexBuilder.getProjectConfig(projectDir);
		String projectName = spc.getName();
		
		IndexBuilder builder = new IndexBuilder(projectDir);
		DesignerProject project = builder.loadProject();
		if (project == null) {
			System.err.println(Messages.formatMessage("GenerateTestDataTemplates.Index.Error", projectPath));
			return false;
		}
		
		String inputPath = argsMap.get(FLAG_INPUTPATH);
		if (inputPath == null) {
			throw new Exception("Input path not specified.");
		}
		File inputFile = new File(inputPath);
		if (!inputFile.exists()) {
			throw new Exception("Input path does not exist.");
		}

		
		String inputType = argsMap.get(FLAG_FILETYPE);
		if(inputType==null){
			inputType = FILETYPE_CSV;
		}
		if(!inputTypes.contains(inputType)){
			System.out.println(Messages.getString("GenerateTestDataTemplates.InvalidOutputType"));
			return false;
		}
		
		String columnSeperator = null;
		if(inputType.equals(FILETYPE_CSV)){
			columnSeperator = argsMap.get(FLAG_SEPERATOR);
			if(columnSeperator==null){
				columnSeperator  = SEPARATOR_COMMA ;
			}else if (!columnSeperators.contains(columnSeperator)){
				System.out.println(Messages.getString("GenerateTestDataTemplates.InvalidColumnSeperator"));
				return false;
			}
		}
		
		if(inputFile.isDirectory()){
			//Generate Test Data files for all test data file for files
			inputFiles = TesterCoreUtils.getFilesList(inputFile,inputType);
			
		}
		else{
			Path inputFilePath = new Path(inputPath);
			if(inputFilePath.getFileExtension().equals(LISTFILE_EXTN)){
				//its a list file
				//read the list file and prepare a list of files
				inputFiles = readListFile(inputPath,inputType);
			}
			else if (inputFilePath.getFileExtension().equals(FILETYPE_XLS) || inputFilePath.getFileExtension().equals(FILETYPE_CSV)){
				inputFiles = new ArrayList<File>();
				inputFiles.add(new File(inputPath));
			}else{
				System.out.println("Invalid input file.");
				return false;
			}
				
		}
			
		File outputPath = new File(outputStringPath);
		if (outputPath.exists() && !overwrite && !isAppend) {
			System.out.println(outputStringPath + " Path already exists");
			return false;
		}
		
		if(outputPath.exists() && overwrite){
			deleteFolder(outputPath);
			outputPath.mkdir();
		}
		
		if(outputPath.exists() && isAppend){
			//Append test data
		}
		
		IWorkspace workspace= ResourcesPlugin.getWorkspace(); 
		IWorkspaceDescription wrkspacedesc= workspace.getDescription(); 
		boolean isAutoBuilding= wrkspacedesc.isAutoBuilding(); 
		if (isAutoBuilding) { 
			wrkspacedesc.setAutoBuilding(false); 
			workspace.setDescription(wrkspacedesc); 
		} 
				
		try {
			if(inputType.equals(FILETYPE_CSV)){
				TestDataCsvImportHandler handler = new TestDataCsvImportHandler(projectPath, projectName, outputStringPath, project, inputFiles, columnSeperator,isAppend);
				handler.execute();
			}else if (inputType.equals(FILETYPE_XLS)){
				TestDataExcelImportHandler handler = new TestDataExcelImportHandler(projectPath, projectName,outputStringPath, project, inputFiles, false);
				handler.execute(false);
			}else{
				System.out.println("Invalid input file");
				return true;
			}
			
		} catch (Exception e) {
			throw new Exception(Messages.getString("GenerateTestDataTemplates.Error") + " - " + e.getMessage(),e);
		}		
	
		System.out.println("Test Data generated successfully");
		return true;
	}
	public static void deleteFolder(File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	            if(f.isDirectory()) {
	                deleteFolder(f);
	            } else {
	                f.delete();
	            }
	        }
	    }
	    folder.delete();
	}
	
	public static List<File> readListFile(String file, String type){
		List<File> filesList = new ArrayList<>();
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			
			final List<String> fileNames = new ArrayList<>(); 
			
			DefaultHandler handler = new DefaultHandler() {
				boolean path = false;
				@Override
				public void startElement(String uri, String localName, String qName, Attributes attributes ){
					if(qName.equalsIgnoreCase("path")){
						path = true;
					}
				}
				@Override 
				public void characters(char ch[], int start, int length) throws SAXException{
					if(path){
						fileNames.add(new String(ch,start,length));
					}
				}
				
			};
			saxParser.parse(file,handler);
			
			
			for(String fileName : fileNames){
				fileName = fileName.trim();
				if(fileName.endsWith(type)){
					File newFile = new File(fileName);
					if(newFile.exists()){
						filesList.add(newFile);
					}
					else{
						System.out.println("File does not exist.");
					}
				}
			}
			
			
		} catch (ParserConfigurationException  | IOException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filesList;
	}
	
}


/*
 <?xml version="1.0" encoding="UTF-8"?>
  <paths filetype="csv">
	  	<!-- Consider all test data files inside the project (default behavior) -->
	  	<path>/*</path> 
	  	<!-- relative CSV file path -->
	  	<path>/concept/concept1.xls</path>
	  	<path>/event/event1.xls</path>
	  	<!-- relative directory path -->
	  	<path>/concept</path>
	  	<!-- absolute CSV file path -->
	  	<path>C:/TestData/concept/concept1.xls</path>
	  	<!-- absolute directory path -->
	  	<path>C:/TestData/concept</path>
  	<paths>    	
*/
 