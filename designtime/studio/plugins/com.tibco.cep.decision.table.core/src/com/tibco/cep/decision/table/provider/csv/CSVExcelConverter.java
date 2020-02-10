/**
 * 
 */
package com.tibco.cep.decision.table.provider.csv;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;


/**
 * @author aathalye
 *
 */
public class CSVExcelConverter implements CSVListener {
	
	private String xlFilePath;
	
	private String xlSheetName;
	
	/**
	 * Keep track of the current row number in XL.
	 */
	private int currentRowNo;

	private HSSFFont sectionHeaderFont;
	
	private HSSFFont columnHeaderFont;
	
	private HSSFWorkbook workbook;
	
	private HSSFSheet sheet;

	public CSVExcelConverter(String xlFilePath, 
			                 String sheetname) {
		this.xlFilePath = xlFilePath;
		
		this.xlSheetName = sheetname;

	}

	/**
	 * 
	 */	
	public void wordEvent(CSVWordEvent wordEvent) {
		Object value = wordEvent.getSource();
		int rowNumber = wordEvent.getRowNumber();
		int columnPosition = wordEvent.getColumnCposition();
		writeWord(value, rowNumber, columnPosition);
	}

	protected void writeWord(Object value, 
			                 int rowNumber, 
			                 int columnPosition) {
		HSSFRow row = null;
		//Compare current row number with this row
		if (currentRowNo != rowNumber || currentRowNo == 0) {
			//Create a new row
			currentRowNo = rowNumber;
			row = sheet.createRow(currentRowNo);
		} else {
			//Use same row
			row = sheet.getRow(currentRowNo);
		}
		CellType cellType = CellType._NONE;
		if (value instanceof String) {
			cellType = CellType.STRING;
		} else if (value instanceof Double) {
			cellType =CellType.NUMERIC;
		}
		@SuppressWarnings("deprecation")
		HSSFCell cell = row.createCell((short) columnPosition, cellType);
		HSSFRichTextString header = new HSSFRichTextString(value.toString().trim());
		header.applyFont(this.sectionHeaderFont);
		cell.setCellValue(header);
	}

	protected void writeColumnHeader(HSSFRow row, short index, String value) {
		@SuppressWarnings("deprecation")
		HSSFCell cell = row.createCell(index, CellType.STRING);
		HSSFRichTextString valueString = new HSSFRichTextString(value);
		valueString.applyFont(this.columnHeaderFont);
		cell.setCellValue(valueString);
	}
	
	/**
	 * Initialize workbook and other XL related initialization.
	 * @throws Exception
	 */
	public void initializeWorkbook() throws Exception {
				
		try {
			workbook = new HSSFWorkbook();

			if (workbook != null) {
				this.sectionHeaderFont = workbook.createFont();
				this.sectionHeaderFont.setBold(true);
				this.columnHeaderFont = workbook.createFont();
				this.columnHeaderFont.setBold(true);
				this.columnHeaderFont.setItalic(true);

				sheet = workbook.getSheet(this.xlSheetName);

				if (sheet == null) {
					sheet = workbook.createSheet(this.xlSheetName);
				}
			} else {
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Save this workbook to the output file.
	 * @throws IOException
	 */
	public void saveWorkbook() throws IOException {
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(this.xlFilePath);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		} finally {
			if (fileOut != null) {
				fileOut.close();
			}
		}
	}
	
	public static void convert(String csvFilePath, String xlsFilePath, String sheetName, String columnSeparator) throws Exception {
		CSVExcelConverter excelConverter = new CSVExcelConverter(xlsFilePath, sheetName);
		excelConverter.initializeWorkbook();
		FileInputStream fis = new FileInputStream(csvFilePath);
		CSVParser parser = new CSVParser(columnSeparator);
		parser.addListener(excelConverter);
		parser.parse(fis);
		excelConverter.saveWorkbook();
	}
}
