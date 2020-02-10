package com.tibco.cep.decision.cli;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang3.StringEscapeUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.be.parser.RuleError;
import com.tibco.cep.decision.table.language.DTValidator;
import com.tibco.cep.decision.table.language.ErrorListRuleErrorRecorder;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexBuilder;

/**
 * CLI interpreter to validate decision table for syntax errors.
 * @author vdhumal
 *
 */
public class WebStudioValidateDecisionTableCLI extends DecisionTableCLI {
	
	private final static String OPERATION_VALIDATE_DT = "validateWSTable";
	
	private final static String FLAG_VALIDATE_DT_HELP = "-h"; 	// Prints help
	
	private final static String FLAG_SCS_ROOT_PATH = "-r";	// Path of new project in Studio
	
	private final static String FLAG_PROJECT_NAME = "-p";	// Path of workspace required for index.
	
	private final static String FLAG_PROJECT_EAR_PATH = "-e";	// Path of DT relative to project

	private final static String FLAG_DT_TEMP_FILE_PATH = "-t";
	
	@Override
	public String[] getFlags() {
		return new String[] { 
				FLAG_SCS_ROOT_PATH, 
				FLAG_PROJECT_NAME,
				FLAG_PROJECT_EAR_PATH,
				FLAG_DT_TEMP_FILE_PATH
		};
	}
	
	public static void main(String[] args) throws Exception {
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cli.studiotools.ICommandLineInterpreter#getHelp()
	 */
	@Override
	public String getHelp() {
		String helpMsg = "Usage: " + getOperationCategory() + " " + getOperationFlag() + " " + getUsageFlags() + "\n" +
			"   where, \n" +
			"	-h (optional) prints this usage \n" +
			"	-r (required) The path where the repository resides. \n" +
			"	-p (required) Project name. \n" +
			"   -e (required) Path of the project EAR file";
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
		stringBuilder.append(FLAG_SCS_ROOT_PATH);
		stringBuilder.append(" ");
		stringBuilder.append("<Path of SCS>");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_PROJECT_NAME);
		stringBuilder.append(" ");
		stringBuilder.append("<project Name>");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_PROJECT_EAR_PATH);
		stringBuilder.append(" ");
		stringBuilder.append("<EAR archive path>");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_DT_TEMP_FILE_PATH);
		stringBuilder.append(" ");
		stringBuilder.append("<DecisionTable Temp File Path>");
		return stringBuilder.toString();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cli.studiotools.ICommandLineInterpreter#runOperation(java.util.Map)
	 */
	@Override
	public boolean runOperation(Map<String, String> argsMap) throws Exception {
		if (checkIfExcludeOperation(argsMap))
			return true;
		
		if (argsMap.containsKey(FLAG_VALIDATE_DT_HELP)) {
			System.out.println(getHelp());
			return true;
		}
		//Get all arguments
		String scsRootURL = argsMap.get(FLAG_SCS_ROOT_PATH);
		if (scsRootURL == null) {
			System.out.println(getHelp());
			return false;
		}
		String projectName = argsMap.get(FLAG_PROJECT_NAME);
		if (projectName == null) {
			System.out.println(getHelp());
			return false;
		}
		String earPath = argsMap.get(FLAG_PROJECT_EAR_PATH);
//		if (earPath == null) {
//			System.out.println(getHelp());
//			return false;
//		}
		String tempFilePath = argsMap.get(FLAG_DT_TEMP_FILE_PATH);
		if (tempFilePath == null) {
			System.out.println(getHelp());
			return false;
		}
		
		InputStream is = null;
		FileInputStream fis = null;
		try {
			File tempFile = new File(tempFilePath);
			fis = new FileInputStream(tempFile);
			is = new BufferedInputStream(fis);
			executeOp(scsRootURL, projectName, earPath, is);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				fis.close();
				is.close();
			} catch(Exception e) {
				e.printStackTrace();
				//Ignore
			}
		}
	}
	
	/**
	 * 
	 * @param projectRootDir
	 * @param workspacePath
	 * @param dtPath
	 * @throws Exception
	 */
	private void executeOp(String scsRootURL,
			               String projectName,
			               String earPath,
			               InputStream is) throws Exception {
		DecisionTableCLIOpsUtils.registerEPackages();
		//Load Index
		loadProjectIndex(scsRootURL, projectName, earPath);
		
		Table table = (Table) CommonIndexUtils.deserializeEObject(is);
							
		//Validate it
		ErrorListRuleErrorRecorder errorRecorder = new ErrorListRuleErrorRecorder();
		DTValidator.checkTable(table, projectName, errorRecorder);
		
		StringBuffer errorData = new StringBuffer();
		errorData.append("<DTValidationErrors>");
		if (errorRecorder.hasErrors()) {
			for (RuleError ruleError : errorRecorder.getErrorList()) {
				String errorMessage = StringEscapeUtils.escapeXml(ruleError.getMessage());
				errorData.append("<ruleerror message=\""+ errorMessage + "\" rowId=\""+ ruleError.getRowId()
			                          + "\" colId=\""+ ruleError.getColumnId() + "\" type=\""+ ruleError.getType() + "\"/>");
			}
		}
		errorData.append("</DTValidationErrors>");
		System.out.print(errorData);
	}

    /**
     * @param rootURL SCS root URL
     * @param projectName
     * @throws MalformedURLException
     * @throws IOException
     */
    private void loadProjectIndex(String rootURL, String projectName, String archivePath) throws MalformedURLException, IOException {
    	File archiveFile = null;
    	if (archivePath != null) {
    		archiveFile = new File(archivePath);
    	}	
		if (archiveFile != null && archiveFile.exists() && archiveFile.isFile()) {
			loadProjectIndex(projectName, archiveFile);
		} else {
			IndexBuilder builder = new IndexBuilder(new File(rootURL + File.separator + projectName));
			DesignerProject index = builder.loadProject();
			StudioProjectCache.getInstance().putIndex(projectName, index);
		}   	
    } 	
	
    /**
     * @param projectName
     * @param archivePath Path of the EAR
     * @throws MalformedURLException
     * @throws IOException
     */
	private void loadProjectIndex(String projectName, File archiveFile) throws MalformedURLException, IOException {

    	DesignerProject index = StudioProjectCache.getInstance().getIndex(projectName);
    	if (index == null) {
	    	java.net.URI earURI = archiveFile.toURI();
	    	ResourceSet resourceSet = new ResourceSetImpl();
	        final Resource resource = resourceSet.createResource(
	                URI.createURI("archive:jar:" + earURI.toString() + "!/Shared%20Archive.sar!/" + ".idx"));
	
	        final File earFile = new File (earURI);
	        final int length = (int) earFile.length();
	        final byte[] fileAsBytes = new byte[length];
	        
	        final InputStream is = earURI.toURL().openStream();
	        try {
	            for (int numRead = 0, offset = 0; (numRead >= 0) && (length > offset); offset += numRead) {
	                numRead = is.read(fileAsBytes, offset, length - offset);
	            }
	        } finally {
	            is.close();
	        }
	
	        final ByteArrayInputStream bais = new ByteArrayInputStream(fileAsBytes);
	        try {
	            final ZipInputStream earZis = new ZipInputStream(bais);
	            try {
	                for (ZipEntry earEntry = earZis.getNextEntry(); null != earEntry; earEntry = earZis.getNextEntry()) {
	                    if ("Shared Archive.sar".equals(earEntry.getName())) {
	                        final ZipInputStream sarZis = new ZipInputStream(earZis);
	                        try {
	                            for (ZipEntry sarEntry = sarZis.getNextEntry(); null != sarEntry; sarEntry = sarZis.getNextEntry()) {
	                                if (".idx".equals(sarEntry.getName())) {
	                                    resource.load(sarZis, null);
	                                    break;
	                                }
	                            }
	                        } finally {
	                            sarZis.close();
	                        }
	                        break;
	                    }
	                }
	            } finally {
	                earZis.close();
	            }
	        } finally {
	            bais.close();
	        }
		
	        DesignerProject designerProject = null;
	        if (resource.getContents().size() > 0) {
	        	designerProject = (DesignerProject) resource.getContents().get(0);
	        	StudioProjectCache.getInstance().putIndex(projectName, designerProject);
	        }     	
    	}    
    }
}
