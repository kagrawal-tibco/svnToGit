/**
 * 
 */
package com.tibco.cep.decision.cli;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.provider.excel.ExcelExportException;
import com.tibco.cep.decision.table.provider.excel.ExcelExportProvider;
import com.tibco.cep.decisionproject.acl.ValidationError;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.studio.cli.studiotools.BuildEarCLI;
import com.tibco.cep.studio.cli.studiotools.StudioCommandLineInterpreter;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexBuilder;
import com.tibco.cep.studio.core.repo.emf.EMFProject;

/**
 * CLI for exporting Decision Tables to Excel
 * @author vpatil
 */
public class ExportDecisionTableToExcelCLI extends DecisionTableCLI {
	private final static String OPERATION_EXPORT_TO_EXCEL = "exportToExcel";
	
	private final static String FLAG_EXPORT_EXL_HELP = "-h"; 	// Prints help
	
	private final static String FLAG_STUDIO_PROJECT_PATH = "-studioProjPath";	// Path of new project in Studio
	
	private final static String FLAG_PROJECT_EAR_PATH = "-e"; // Ear path
	
	private final static String FLAG_DECISION_TABLE_PATH = "-dtPath";	// Project relative path of the decision table, could be the decision table or folder containing DT's
	
	private final static String FLAG_EXPORT_EXCEL_PATH = "-excelPath";	// Output path where the exported Excel files will be copied
	
	private final static String FLAG_USE_COLUMN_ALIAS = "-useColumnAlias";	// Whether to use column alias in the exported excel
	
	
	public static void main(String[] args) throws Exception {
		if (args.length == 0 || (args.length == 1 && args[0].trim().endsWith("help"))) {
			StudioCommandLineInterpreter.printPrologue(new String[]{});
			System.out.println(new BuildEarCLI().getHelp());
			return;
		}
		String newArgs[] = new String[args.length+2];
		newArgs[0] = DecisionTableCLI.OPERATION_CATEGORY_DT;
		newArgs[1] = OPERATION_EXPORT_TO_EXCEL; 
		System.arraycopy(args, 0, newArgs, 2, args.length);
		StudioCommandLineInterpreter.executeCommandLine(newArgs);
	}
	
	@Override
	public String[] getFlags() {
		return new String[] { 
			FLAG_STUDIO_PROJECT_PATH, 			
			FLAG_DECISION_TABLE_PATH,
			FLAG_EXPORT_EXCEL_PATH,
			FLAG_USE_COLUMN_ALIAS,
			FLAG_PROJECT_EAR_PATH
		};
	}
	
	@Override
	public String getHelp() {
		String helpMsg = "Usage: " + getOperationCategory() + " " + getOperationFlag() + " " + getUsageFlags() + "\n" +
				"where, \n" +
				"	-h (optional) prints this usage \n" +
				"	-studioProjPath (required) The project path where the studio project resides. \n" +
				"	-dtPath (required) Project relative path of the decision table, could be a decision table (Include the extension) or folder containing DT's. \n" +				
				"	-excelPath (required) Output path where the exported Excel files will be copied. \n" +
				"	-useColumnAlias (optional) Whether to use column alias in the exported excel. \n" +
				"	-e (optional) Path of the project EAR file.";
		return helpMsg;
	}

	@Override
	public String getOperationFlag() {
		return OPERATION_EXPORT_TO_EXCEL;
	}

	@Override
	public String getOperationName() {
		return ("Export to an Excel File");
	}
	
	public String getUsageFlags() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("[");
		stringBuilder.append(FLAG_EXPORT_EXL_HELP);
		stringBuilder.append("]");
		stringBuilder.append(FLAG_STUDIO_PROJECT_PATH);
		stringBuilder.append(" ");
		stringBuilder.append("<Studio Project Path>");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_DECISION_TABLE_PATH);
		stringBuilder.append(" ");
		stringBuilder.append("<Decision Table Path(File/Folder)>");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_EXPORT_EXCEL_PATH);
		stringBuilder.append(" ");
		stringBuilder.append("<Exported Excel Path)>");		
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_USE_COLUMN_ALIAS);
		stringBuilder.append(" ");
		stringBuilder.append("<Export Column Alias>");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_PROJECT_EAR_PATH);
		stringBuilder.append(" ");
		stringBuilder.append("<Ear path>");
		
		return stringBuilder.toString();
	}
	
	@Override
	public boolean runOperation(Map<String, String> argsMap) throws Exception {
		if (checkIfExcludeOperation(argsMap))
			return true;
		
		if (argsMap.containsKey(FLAG_EXPORT_EXL_HELP)) {
			System.out.println(getHelp());
			return true;
		}
		
		String studioProjectPath = argsMap.get(FLAG_STUDIO_PROJECT_PATH);
		if (studioProjectPath == null) {
			return false;
		}
		
		studioProjectPath = new File(studioProjectPath).getAbsolutePath();
		String projectName = studioProjectPath.substring(studioProjectPath.lastIndexOf(File.separator)+1);
		
		String dtPath = argsMap.get(FLAG_DECISION_TABLE_PATH);
		if (dtPath == null) {
			return false;
		}
		
		List<File> dtFilesToExport = new ArrayList<File>();
		File dtFilePath = new File(studioProjectPath + dtPath);
		try{
			if (dtFilePath.isDirectory()) {
				lookUpDecisionTables(dtFilePath, dtFilesToExport, studioProjectPath);
			} else {
				dtFilesToExport.add(dtFilePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		
		String excelPath = argsMap.get(FLAG_EXPORT_EXCEL_PATH);
		if (excelPath == null) {
			return false;
		}
		File excelFilePath = new File(excelPath);
		if (!excelFilePath.exists()) excelFilePath.mkdirs();
		
		boolean useColumnAlias = false;
		String columnAlias = argsMap.get(FLAG_USE_COLUMN_ALIAS);
		if (columnAlias != null) {
			useColumnAlias = true;
		}
		
		String earPath = argsMap.get(FLAG_PROJECT_EAR_PATH);
		
		System.out.println("Exporting Decision Table's to Excel");

		DecisionTableCLIOpsUtils.registerEPackages();		
		loadProjectIndex(studioProjectPath, projectName, earPath);
		
		Table table = null;
		ExcelExportProvider provider = null;
		Collection<ValidationError> excelVldErrorCollection = null;
		
		InputStream is = null;
		FileInputStream fis = null;
		String excelOutputPath = null;
		
		for (File dtFile : dtFilesToExport) {
			try {
				fis = new FileInputStream(dtFile);
				is = new BufferedInputStream(fis);
				
				table = (Table) CommonIndexUtils.deserializeEObject(is);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				try {
					fis.close();
					is.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
				
			excelOutputPath = excelPath + File.separator + table.getName() + ".xls";
			provider = new ExcelExportProvider(projectName, excelOutputPath, "DecisionTable", table);
			excelVldErrorCollection = provider.getExcelVldErrorCollection();
			
			try {
				if (useColumnAlias) {
					provider.setUseColumnAlias(true);
				}
				provider.exportWorkbook();
				if (excelVldErrorCollection.size() > 0){
					StringBuffer buf = new StringBuffer();
					buf.append("Invalid Excel file for Decision Table '" + dtFile + "'(");
					int size = excelVldErrorCollection.size();
					int ctr = 1;
					buf.append(size);
					buf.append(" errors): ");
					for (ValidationError validationError : provider.getExcelVldErrorCollection()) {
						buf.append(validationError.getErrorMessage());
						if (ctr < size) {
							buf.append(", ");
						} else {
							buf.append('.');
						}
						ctr++;
					}
					System.out.println("Export Failed. The Decision Table '" + dtFile + "' is not valid:\n"+buf);
					return false;
				}
					
				System.out.println("Export Succeeded for Decision Table '" + dtFile + "'.");
			} catch (ExcelExportException e) {
				System.out.println("Export Failed for the Decision Table '" + dtFile + "' due to " + e.getMessage());
			}
		}
		
		return true;
	}
	
	/**
	 * Lookup decision tables under cascading folders. 
	 * 
	 * @param dtDirectory
	 * @param dtFiles
	 * @param projectPath
	 */
	private void lookUpDecisionTables(File dtDirectory, List<File> dtFiles, String projectPath) {
		File[] fileList = dtDirectory.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File file, String fileName) {
				if (fileName.endsWith("rulefunctionimpl") || fileName.indexOf(".") == -1) return true; 
				return false;
			}
		});
		for (File file : fileList) {
			if (file.isDirectory()) lookUpDecisionTables(file, dtFiles, projectPath);
			else dtFiles.add(file);			
		}		
	}
	
	/**
	 * Load Project Index
	 * 
	 * @param Project Path
	 * @param projectName
	 * @throws MalformedURLException
	 * @throws IOException
	 */
    private static void loadProjectIndex(String projectPath, String projectName, String archivePath) throws MalformedURLException, IOException {
    	File archiveFile = null;
    	if (archivePath != null) {
    		archiveFile = new File(archivePath);
    	}	
		if (archiveFile != null && archiveFile.exists() && archiveFile.isFile()) {
			loadProjectIndex(projectName, archiveFile);
		} else {
			IndexBuilder builder = new IndexBuilder(new File(projectPath));
			DesignerProject index = builder.loadProject();
			StudioProjectCache.getInstance().putIndex(projectName, index);
		}   	
    }
    
    /**
     * Load Project Index off Ear
     * 
     * @param projectName
     * @param archivePath Path of the EAR
     * @throws MalformedURLException
     * @throws IOException
     */
	private static void loadProjectIndex(String projectName, File archiveFile) throws MalformedURLException, IOException {

    	DesignerProject index = StudioProjectCache.getInstance().getIndex(projectName);
    	if (index == null) {
	    	try {
	    		EMFProject proj = new EMFProject(archiveFile.getAbsolutePath());
				proj.load();
		    	EObject runtimeIndex = proj.getRuntimeIndex(AddOnType.CORE);
		    	if (runtimeIndex instanceof DesignerProject) {
		    		StudioProjectCache.getInstance().putIndex(projectName, (DesignerProject) runtimeIndex);
		    		return;
		    	}
			} catch (Exception e) {
				// swallow error, attempt to load .idx file inside EAR
			}
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
