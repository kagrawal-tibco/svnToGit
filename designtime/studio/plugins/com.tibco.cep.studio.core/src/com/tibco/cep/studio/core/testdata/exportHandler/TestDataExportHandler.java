/**
 * 
 */
package com.tibco.cep.studio.core.testdata.exportHandler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;


/**
 * @author mgujrath
 *
 */
public class TestDataExportHandler {
	
private static final String TEST_DATA_SHEET_NAME = "TestData";
    
	public static final String TEST_DATA_HEADER 		= "TEST DATA";
	public static final String TEST_DATA_PROJECT_NAME 	= "PROJECT NAME: ";
	public static final String TEST_DATA_NAME 			= "NAME: ";
	public static final String TEST_DATA_TYPE 			= "TYPE: ";
	public static final String TEST_DATA_FOLDER 		= "FOLDER: ";
	public static final String TEST_DATA_EXT_ID 		= "ExtId";
	public static final String TEST_DATA_PAYLOAD 		= "Payload";
	public static final String TEST_DATA_SELECT 		= "Use";
	protected String 		rootPath = null;
	protected String 		projectName;
	protected String 		projectPath;

    //This should all be local values
  	private HSSFWorkbook 	workbook;
	private HSSFSheet 		testDataSheet;
	private HSSFFont 		sectionHeaderFont;
	private HSSFFont 		columnHeaderFont;

	private int CURRENT_ROW_COUNT	= 0;
	private int COLUMN_POSITION 	= 0;
	List<PropertyDefinition> list = null;
	private String excelFilePath;
	private Entity entity;
	private String entityType;
	private String testDataFilePath;
	
	public TestDataExportHandler(final String projectName,
			final String excelFilePath, HSSFWorkbook workbook,
			HSSFSheet testDataSheet, final Entity entity,final String testDataFilePath) {
		
		this.excelFilePath = excelFilePath;
		this.entity = entity;
		this.workbook = workbook;
		this.testDataSheet = testDataSheet;
		this.projectName = projectName;
		this.testDataFilePath=testDataFilePath;
		init();
	}
		
	private void init() {
		
		if (entity instanceof Scorecard) {
			list = ((Scorecard) entity).getAllProperties();
			entityType = "Scorecard";
		} else if (entity instanceof Event) {
			list = ((Event) entity).getAllUserProperties();
			entityType = "Event";
		} else if (entity instanceof Concept) {
			list = ((Concept) entity).getAllProperties();
			entityType = "Concept";

		}
		if (workbook == null) {
			workbook = new HSSFWorkbook();
			this.sectionHeaderFont = workbook.createFont();
			this.sectionHeaderFont.setBold(true);
			//this.sectionHeaderFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			this.columnHeaderFont = workbook.createFont();
			//this.columnHeaderFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			this.columnHeaderFont.setBold(true);
			this.columnHeaderFont.setItalic(true);
		}
		testDataSheet = null;
		CURRENT_ROW_COUNT = 0;
		COLUMN_POSITION = 0;
	}
	
	/**
	 * Export the {@link Entity} to excel file
	 * @param saveWorkbook -> If true persist the workbook.
	 * @return {@link ExportTestData} containing position info of exported test data.
	 * @throws IOException
	 */
	public ExportTestData exportTestData(boolean saveWorkbook) throws IOException {
		
		HSSFSheet testDataSheet = (this.testDataSheet != null) ? this.testDataSheet : workbook.getSheet(TEST_DATA_SHEET_NAME);
		if (testDataSheet == null) {
			testDataSheet = workbook.createSheet(TEST_DATA_SHEET_NAME);
		}
		ExportTestData exportTestData = new ExportTestData();
		exportTestData.setTestDataName(entity.getName());
		createTestDataHeader(testDataSheet);
		createTestDataSubHeaders(testDataSheet, exportTestData);
		writeTestdataValuesValues(testDataSheet, exportTestData);
		if (saveWorkbook) {
			write();
		}
		return exportTestData;

	}
	
	/**
	 * Write base header <b>Entity</b>
	 * @param testDataSheet
	 */
	private void createTestDataHeader(HSSFSheet testDataSheet) {
		HSSFRow row = testDataSheet.createRow(CURRENT_ROW_COUNT++);
		HSSFCell headerCell = row.createCell(0, CellType.STRING);
		HSSFRichTextString header = new HSSFRichTextString(TEST_DATA_HEADER);
		if (sectionHeaderFont != null) {
			header.applyFont(this.sectionHeaderFont);
		}
		headerCell.setCellValue(header);
		
		row = testDataSheet.createRow(CURRENT_ROW_COUNT++);
		String cellVal = TEST_DATA_PROJECT_NAME + projectName;
		headerCell = row.createCell(0, CellType.STRING);
		header = new HSSFRichTextString(cellVal);
		if (sectionHeaderFont != null) {
			header.applyFont(this.sectionHeaderFont);
		}
		headerCell.setCellValue(header);
		
		row = testDataSheet.createRow(CURRENT_ROW_COUNT++);
		cellVal = TEST_DATA_NAME + entity.getName();
		headerCell = row.createCell(0, CellType.STRING);
		header = new HSSFRichTextString(cellVal);
		if (sectionHeaderFont != null) {
			header.applyFont(this.sectionHeaderFont);
		}
		headerCell.setCellValue(header);
		
		row = testDataSheet.createRow(CURRENT_ROW_COUNT++);
		cellVal = TEST_DATA_TYPE;
		if (entity instanceof Concept) {
			if (((Concept)entity).isScorecard()){
				cellVal = cellVal + "Scorecard";
			}
			else{
				cellVal = cellVal + "Concept";
			}
		}
		else if (entity instanceof Event) {
			cellVal = cellVal + "Event";
		}
		headerCell = row.createCell(0, CellType.STRING);
		header = new HSSFRichTextString(cellVal);
		if (sectionHeaderFont != null) {
			header.applyFont(this.sectionHeaderFont);
		}
		headerCell.setCellValue(header);
		
		row = testDataSheet.createRow(CURRENT_ROW_COUNT++);
		cellVal = TEST_DATA_FOLDER + entity.getFolder();
		headerCell = row.createCell(0, CellType.STRING);
		header = new HSSFRichTextString(cellVal);
		if (sectionHeaderFont != null) {
			header.applyFont(this.sectionHeaderFont);
		}
		headerCell.setCellValue(header);
		
	}
	
	/**
	 * Write sub-headers <b>Description</b> and <b>Value</b>
	 * @param testDataSheet
	 */
	private void createTestDataSubHeaders(HSSFSheet testDataSheet,
			ExportTestData exportTestData) {
		CURRENT_ROW_COUNT++;

		createSubHeader(testDataSheet, CURRENT_ROW_COUNT, COLUMN_POSITION,
				TEST_DATA_SELECT + " (boolean)");
		COLUMN_POSITION++;

		if (entity instanceof Event) {
			Event event = (Event) entity;
			createSubHeader(testDataSheet, CURRENT_ROW_COUNT,
						COLUMN_POSITION, TEST_DATA_PAYLOAD+ " (String)");
				COLUMN_POSITION++;
			
		}
		createSubHeader(testDataSheet, CURRENT_ROW_COUNT, COLUMN_POSITION,
				TEST_DATA_EXT_ID + " (String)");
		COLUMN_POSITION++;

		for (PropertyDefinition propDef : list) {
			createSubHeader(testDataSheet, CURRENT_ROW_COUNT, COLUMN_POSITION,
					propDef.getName() + " (" + propDef.getType() + ")");
			COLUMN_POSITION++;
		}
		CURRENT_ROW_COUNT++;
		// The next row is start for values.

	}

	private void createSubHeader(HSSFSheet testDataSheet, 
			                     int startRowNo,
			                     int columnIndex,
			                     String headerName) {
		HSSFRow row = testDataSheet.getRow(startRowNo);
		if (row == null) {
			row = testDataSheet.createRow(startRowNo);
		}
		HSSFCell subHeaderCell = row.createCell(columnIndex, CellType.STRING);
		HSSFRichTextString headerString = new HSSFRichTextString(headerName);
		if (sectionHeaderFont != null) {
			headerString.applyFont(this.sectionHeaderFont);
		}
		subHeaderCell.setCellValue(headerString);
	}
	
	@SuppressWarnings("unused")
	private void writeCell(HSSFSheet testDataSheet,
			               int rowNumber,
			               int columnIndex,
			               String value) {
		HSSFRow row = testDataSheet.getRow(rowNumber);
		if (row == null) {
			row = testDataSheet.createRow(rowNumber);
		}
		if(columnIndex==0){
			HSSFCell cell = row.createCell(columnIndex, CellType.STRING);
			cell.setCellValue(Boolean.parseBoolean(value));
		}
		else{
			HSSFCell cell = row.createCell(columnIndex, CellType.STRING);
			cell.setCellValue(new HSSFRichTextString(value));
		}
		
	}
	
		
	private void write() throws IOException {
		FileOutputStream fos = new FileOutputStream(excelFilePath);
		workbook.write(fos);
		fos.close();
	}
	
	private void writeDataToExcel(HSSFSheet testDatasheet,
			List<ArrayList<String>> testDataValues, ExportTestData exportTestData) {
		for (List<String> list : testDataValues) {
			
			writeTestData(testDatasheet,list);
			this.CURRENT_ROW_COUNT++;
		}
		// After all values are written update last row
		exportTestData.setEndRow(CURRENT_ROW_COUNT);
	}
	
	private void writeTestData(HSSFSheet testDataSheet,List<String> list) {
		
			for(int index=0;index<list.size();index++){
				writeCell(testDataSheet, CURRENT_ROW_COUNT, index, list.get(index));
			}
	}
	
	private void writeTestdataValuesValues(HSSFSheet testDataSheet,
			ExportTestData exportTestData) {

		TestData model = null;
		List<ArrayList<String>> testDataValues=new ArrayList<ArrayList<String>>();
		ArrayList<String> tableColumnNames = new ArrayList<String>();
		tableColumnNames.add("Use");
		if (entity instanceof Event) {
			tableColumnNames.add("Payload");
		}
		tableColumnNames.add("ExtId");
		for (PropertyDefinition pd : list) {
			tableColumnNames.add(pd.getName());
		}
		try {
			model = TesterImportExportUtils.getDataFromXML(testDataFilePath,
					entityType, entity, tableColumnNames);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int index=0;index<model.getSelectRowData().size();index++){
			
			ArrayList<String> list=new ArrayList<String>();
			list.add(model.getSelectRowData().get(index).toString());
			list.addAll(model.getTestData().get(index));
			testDataValues.add(list);
			
		}
		writeDataToExcel(testDataSheet, testDataValues, exportTestData);

	}

}
