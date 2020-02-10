package com.tibco.cep.studio.tester.core.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import com.tibco.cep.designtime.core.model.event.Event;
/**
 * 
 * @author sasahoo
 *
 */
public class TestDataExcelExportHandler extends AbstractTestDataExportHandler {
	
    private static final String TEST_DATA_SHEET_NAME = "TestData";
    
    //This should all be local values
  	private HSSFWorkbook 	workbook;
	private HSSFSheet 		testDataSheet;
	private HSSFFont 		sectionHeaderFont;
	private HSSFFont 		columnHeaderFont;

	private int CURRENT_ROW_COUNT	= 0;
	private int COLUMN_POSITION 	= 0;
	
	public TestDataExcelExportHandler( String projectName,
									   String projectPath,
									   String outputRootPath,
									   List<Entity> entities) {
	 super(projectName, projectPath, outputRootPath, entities);
	}
		
	private void init() {
		workbook = new HSSFWorkbook();
		this.sectionHeaderFont = workbook.createFont();
		this.sectionHeaderFont.setBold(true);
		this.columnHeaderFont = workbook.createFont();
		this.columnHeaderFont.setBold(true);;
		this.columnHeaderFont.setItalic(true);
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
	public ExportTestData exportTestData(Entity entity, List<PropertyDefinition> propertyDefitionList) throws IOException {
		
		init();
		
		HSSFSheet testDataSheet = (this.testDataSheet != null) ? this.testDataSheet : workbook.getSheet(TEST_DATA_SHEET_NAME);
		if (testDataSheet == null) {
			testDataSheet = workbook.createSheet(TEST_DATA_SHEET_NAME);
		}
		ExportTestData ExportTestData = new ExportTestData();
		ExportTestData.setTestDataName(entity.getName());
		createTestDataHeader(testDataSheet, entity);
		createTestDataSubHeaders(testDataSheet, ExportTestData, entity , propertyDefitionList);
		write(entity);
		return ExportTestData;
	}
	
	/**
	 * Write base header <b>Entity</b>
	 * @param testDataSheet
	 */
	private void createTestDataHeader(HSSFSheet testDataSheet, Entity entity) {
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
	private void createTestDataSubHeaders(HSSFSheet testDataSheet, ExportTestData ExportTestData, Entity entity, List<PropertyDefinition> propertyDefitionList) {
		CURRENT_ROW_COUNT++;
		
		createSubHeader(testDataSheet, CURRENT_ROW_COUNT, COLUMN_POSITION, TEST_DATA_SELECT+" (boolean)");
		COLUMN_POSITION++;
		
		if(entity instanceof Event){
			Event event = (Event)entity;
	//		if (event.getPayloadString() != null && !event.getPayloadString().isEmpty()) {
				createSubHeader(testDataSheet, CURRENT_ROW_COUNT, COLUMN_POSITION, TEST_DATA_PAYLOAD+" (String)");
				COLUMN_POSITION++;
	//		}
		}
		
		createSubHeader(testDataSheet, CURRENT_ROW_COUNT, COLUMN_POSITION, TEST_DATA_EXT_ID+" (String)");
		COLUMN_POSITION++;
		
		for (PropertyDefinition propDef : propertyDefitionList) {
			if(propDef.isArray()){
				createSubHeader(testDataSheet, CURRENT_ROW_COUNT, COLUMN_POSITION, propDef.getName()+ " (" +propDef.getType()+")" + " [M]");
				COLUMN_POSITION++;
			}
			else{
				createSubHeader(testDataSheet, CURRENT_ROW_COUNT, COLUMN_POSITION, propDef.getName()+ " (" +propDef.getType()+")");
				COLUMN_POSITION++;
			}
		}
		CURRENT_ROW_COUNT++;
		//The next row is start for values.
		ExportTestData.setStartRow(CURRENT_ROW_COUNT);
		ExportTestData.setColumnPosition(COLUMN_POSITION);
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
		HSSFCell cell = row.createCell(columnIndex, CellType.STRING);
		cell.setCellValue(new HSSFRichTextString(value));
	}
	
	private void write(Entity entity) throws IOException {
		String outputDirPath = rootPath + entity.getFolder();
		File dir = new File(outputDirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String path  = outputDirPath + entity.getName() + ".xls";
		File f = new File(path);
		if (!f.exists()) {
			f.createNewFile();
		} else {
			f.delete();
		}
		FileOutputStream fos = new FileOutputStream(outputDirPath + entity.getName() + ".xls");
		workbook.write(fos);
		fos.close();
	}
	
}
