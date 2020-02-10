package com.tibco.cep.studio.tester.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Path;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.tester.core.model.TestDataModel;


/**
 * 
 * @author sasahoo
 *
 */
public class TestDataCsvImportHandler {

	public static final String DATE_TIME_PATTERN 		= "yyyy-MM-dd'T'HH:mm:ss";
    public static final String TEST_DATA_HEADER 		= "TEST DATA";
	public static final String TEST_DATA_PROJECT_NAME 	= "PROJECT NAME: ";
	public static final String TEST_DATA_NAME 			= "NAME: ";
	public static final String TEST_DATA_TYPE 			= "TYPE: ";
	public static final String TEST_DATA_FOLDER 		= "FOLDER: ";
	public static final String TEST_DATA_EXT_ID 		= "ExtId";
	public static final String TEST_DATA_PAYLOAD 		= "Payload";
	public static final String TEST_DATA_SELECT 		= "Use";
	
	private String projectDirPath;
	private String projectName;
	private boolean isAppend = false;
	
	//Add project name validation check
	
	private DesignerProject project;
	private List<DesignerElement> elementList;
	

	private List<File> fileList;
	
	private String outputPath;
	private String columnSeperator;
	
	public TestDataCsvImportHandler( String projectDirPath, 
									   String projectName, 
			                           String outputPath,
			                           DesignerProject project, 
			                           List<File> inputFiles,
			                           String columnSeperator,boolean isAppend) {
		this.projectDirPath = projectDirPath;
		this.projectName = projectName;
		this.outputPath = outputPath;
		this.columnSeperator = columnSeperator;
		this.project = project;
		this.fileList = inputFiles;
		this.isAppend = isAppend;
		elementList = CommonIndexUtils.getAllElements(project, new ELEMENT_TYPES[]{ELEMENT_TYPES.CONCEPT, 
				 ELEMENT_TYPES.SIMPLE_EVENT, ELEMENT_TYPES.SCORECARD});
	}
	
	private class TemplateFileData{
		public String type 		= null;
		public String name 		= null;
		public String folder 	= null;
		
		public int totalRowCount = -1;
		public int currentRow 	 = -1;
		
		public List<Integer> columns 				= new ArrayList<Integer>();//why is this a list of columns
		public List<String> columnNames 			= new ArrayList<String>(); //This is table header
		public Map<Integer, String> columnNamesMap 	= new HashMap<Integer, String>(); //Column index to column name map
		
		public Entity entity 			= null;	
		public int firstColumn 			= -1;
		public int firstRow 			= -1;
		public int actualDataRowCount 	= -1;
		//private boolean alreadyProcessed = false;
		public int currentColumn 		= -1;
		
		public List<List<String>> rowValues = new ArrayList<List<String>>();
		public List<Boolean> selectedRows 	= new ArrayList<Boolean>();
		
		private String exProjectName;
	}
	
	public boolean execute() {
		File rootProjectFile = new File(projectDirPath);
		if (!rootProjectFile.exists()) {
			return false;	
		} else {
			try {
					for(File excelFilePath : fileList){
						TemplateFileData templateFileData = new TemplateFileData();
						if (!excelFilePath.exists()) {
							return false;
						}
						FileInputStream fin = new FileInputStream(excelFilePath);
						InputStreamReader isr = new InputStreamReader(fin);
						BufferedReader reader = new BufferedReader(isr);
						String record = null;
						int rowNum = 0;
						while((record = reader.readLine())!=null){
							processRecord(record,rowNum, templateFileData);
							rowNum++;
						}
						createTestDataEntries(templateFileData,isAppend);
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
					return false;
				}
		}
		return true;
	}
	
	public void processRecord(String record, int rowNum, TemplateFileData templateFileData) {

		String[] columnsArry = record.split(columnSeperator);
		
		if(record.endsWith(columnSeperator)){
			String[] newArry = new String[columnsArry.length+1];
			System.arraycopy(columnsArry, 0, newArry, 0, columnsArry.length);
			newArry[columnsArry.length]="";
			columnsArry = newArry;
		}
		
		int columnNum = 0;
		templateFileData.currentColumn = 0 ;
		List<String> currentList 	= null;
		for (String column : columnsArry){
				String cellString = column;
//				System.out.println("Cell String--"+cellString);
				int begDataType = -1;
				if((begDataType= cellString.indexOf("("))>1){
					cellString = cellString.substring(0, begDataType-1);
				}
				
				if (TEST_DATA_HEADER == cellString.intern()) {
					//Its the test data header, nothing to do here, lets move on...
				}
				else if (cellString.startsWith(TEST_DATA_PROJECT_NAME)) {
					//Get the project Name, use this for validation if the file 
					templateFileData.exProjectName = cellString;
					
				}else if (cellString.startsWith(TEST_DATA_NAME)) {
					templateFileData.name = cellString.substring(TEST_DATA_NAME.length());
					
				}else if (cellString.startsWith(TEST_DATA_TYPE)) {
					templateFileData.type = cellString.substring(TEST_DATA_TYPE.length()).toLowerCase();
					
				}else if (cellString.startsWith(TEST_DATA_FOLDER)) {
					templateFileData.folder = cellString.substring(TEST_DATA_FOLDER.length());
					
				}else{
						//This should be the actual table of values					
						if (cellString.intern() == TEST_DATA_EXT_ID) {
							templateFileData.columns.add(columnNum);
							templateFileData.columnNamesMap.put(columnNum, TEST_DATA_EXT_ID);
							templateFileData.columnNames.add(TEST_DATA_EXT_ID);
							columnNum ++;
						}else if (cellString.intern() == TEST_DATA_PAYLOAD) {
							//Payload??
							templateFileData.columns.add(columnNum);
							templateFileData.columnNamesMap.put(columnNum, TEST_DATA_PAYLOAD);
							templateFileData.columnNames.add(TEST_DATA_PAYLOAD);
							columnNum ++;
						} else if (cellString.intern() == TEST_DATA_SELECT) {
							templateFileData.firstColumn = columnNum;
							templateFileData.firstRow = rowNum;
							templateFileData.columns.add(columnNum);
							templateFileData.columnNamesMap.put(columnNum, TEST_DATA_SELECT);
							templateFileData.columnNamesMap.put(columnNum, TEST_DATA_SELECT);
							templateFileData.columnNames.add(TEST_DATA_SELECT);
							templateFileData.actualDataRowCount = templateFileData.totalRowCount - templateFileData.firstRow;
							templateFileData.currentColumn = columnNum;
							templateFileData.currentRow = rowNum;
							columnNum++;
						} else if (rowNum == templateFileData.firstRow && templateFileData.firstColumn > -1) {
							templateFileData.columns.add(columnNum);
							templateFileData.columnNamesMap.put(columnNum, cellString);
							templateFileData.columnNames.add(cellString);
							columnNum++;
						} else if (rowNum != templateFileData.firstRow && cellString != null) {
							
							if (rowNum == templateFileData.currentRow + 1) {
								currentList = new ArrayList<String>();
							}
							if (currentList != null) {
								if (columnNum > templateFileData.currentColumn + 1) {
									currentList.add("NA");
								}
								if(cellString.equalsIgnoreCase("true")){
										currentList.add("true");
								}
								else if(cellString.equalsIgnoreCase("false")){
										currentList.add("false");
								}else{
										currentList.add(cellString.trim());
									}
								}

							templateFileData.currentRow = rowNum;
							templateFileData.currentColumn = columnNum;
							columnNum++;
						}
				}
		}
		if(currentList!=null && currentList.size() == templateFileData.columnNamesMap.size()){
			if(currentList.get(0).equals("true")){
				templateFileData.selectedRows.add(true);
			}else{
				templateFileData.selectedRows.add(false);//Select all rows
			}
			currentList.remove(0);
			templateFileData.rowValues.add(currentList);
		}
	   
	}

	
	
	private void createTestDataEntries(TemplateFileData templateFileData,boolean isAppend) {
		if (templateFileData.entity == null 
				&& templateFileData.name != null 
				&& templateFileData.type != null 
				&& templateFileData.folder != null ) {
			if(!templateFileData.rowValues.isEmpty()){
			
				for (DesignerElement element: elementList) {
					if (element instanceof EntityElement) {
						
						if(element.getElementType().toString().toLowerCase().contains(templateFileData.type)){
						templateFileData.entity = ((EntityElement)element).getEntity();
						for(List<String> list:templateFileData.rowValues){
							for(int index=0;index<list.size();index++){
								if("NA".equalsIgnoreCase(list.get(index).trim())){
									list.set(index, "");
								}
								if("\"\"\"NA\"\"\"".equalsIgnoreCase(list.get(index).trim())){
									list.set(index, "NA");
								}
							}
						}
						
						TestDataModel model = new TestDataModel(templateFileData.entity, templateFileData.columnNames, templateFileData.rowValues);
						model.setSelectRowData(templateFileData.selectedRows);
						if (templateFileData.entity.getName().equals(templateFileData.name) &&
								templateFileData.entity.getFolder().equals(templateFileData.folder) &&
								CommonIndexUtils.getFileExtension(templateFileData.entity).equals(templateFileData.type)) {
								Path outputFilePath = new Path(outputPath + templateFileData.entity.getFullPath() + CommonIndexUtils.DOT + templateFileData.type + "testdata");
								if((new File(outputFilePath.toString())).exists() && isAppend){
									model.generateVectorforCLI();
									TesterCoreUtils.processModel(model,outputFilePath.toString());
									TesterCoreUtils.exportDataToXMLDataFileAppend(outputFilePath.toString(), templateFileData.entity, model, templateFileData.columnNames, true);
									break;
								}
								File outputDir = new File(outputFilePath.removeLastSegments(1).toString());
								outputDir.mkdirs();
								TesterCoreUtils.exportDataToXMLDataFilefromCli(outputFilePath.toString(), model,elementList);
								break;
						}
					}
					}
				}
			}
		}
	}
	
}