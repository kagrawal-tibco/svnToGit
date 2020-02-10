/**
 * 
 */
package com.tibco.cep.studio.core.domain.exportHandler;

import static com.tibco.cep.studio.core.domain.importHandler.impl.DomainModelExcelImportHandler.DESCRIPTION_HEADER;
import static com.tibco.cep.studio.core.domain.importHandler.impl.DomainModelExcelImportHandler.DOMAIN_MODEL_HEADER;
import static com.tibco.cep.studio.core.domain.importHandler.impl.DomainModelExcelImportHandler.VALUE_HEADER;

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

import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.domain.Range;
import com.tibco.cep.designtime.core.model.domain.Single;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

/**
 * @author aathalye
 *
 */
public class DomainModelExcelExportHandler {
	
	private String excelFilePath;
	
	/**
	 * The Model to export
	 */
	private Domain domain;
	
	private HSSFWorkbook workbook;
	
	private HSSFSheet domainSheet;
	
	private HSSFFont sectionHeaderFont;
	
	private HSSFFont columnHeaderFont;
	
	private String projectName;
	
	/**
	 * Where the row counter is.
	 */
	private int currentRowCounter;
	
	private static final String DOMAIN_SHEET_NAME = "Domain";
	
	private static final int DESC_COLUMN_POSITION = 0;
	
	private static final int VALUE_COLUMN_POSITION = 1;
	
	/**
	 * 
	 * @param projectName
	 * @param excelFilePath
	 * @param workbook -> If not null use this workbook.
	 * @param domainSheet -> If not null, this shet will be used.
	 * @param domain
	 */
	public DomainModelExcelExportHandler(final String projectName,
										 final String excelFilePath,
										 HSSFWorkbook workbook,
										 HSSFSheet domainSheet,
			                             final Domain domain) {
		this.excelFilePath = excelFilePath;
		this.domain = domain;
		this.workbook = workbook;
		this.domainSheet = domainSheet;
		this.projectName = projectName;
		init();
	}
	
	/**
	 * Export the {@link Domain} to excel file
	 * @param saveWorkbook -> If true persist the workbook.
	 * @return {@link DomainExportData} containing position info of exported domain.
	 * @throws IOException
	 */
	public DomainExportData exportDomain(boolean saveWorkbook) throws IOException {
		HSSFSheet domainSheet = (this.domainSheet != null) ? this.domainSheet : workbook.getSheet(DOMAIN_SHEET_NAME);
		if (domainSheet == null) {
			domainSheet = workbook.createSheet(DOMAIN_SHEET_NAME);
		}
		DomainExportData domainExportData = new DomainExportData();
		domainExportData.setDomainName(domain.getName());
		createDomainHeader(domainSheet);
		createDomainSubHeaders(domainSheet, domainExportData);
		writeDomainValues(domainSheet, domainExportData);
		if (saveWorkbook) {
			write();
		}
		return domainExportData;
	}
	
	/**
	 * Write base header <b>DomainModel</b>
	 * @param domainSheet
	 */
	private void createDomainHeader(HSSFSheet domainSheet) {
		// create Domain Model Header
		HSSFRow row = domainSheet.createRow(currentRowCounter++);
		HSSFCell headerCell = row.createCell(0, CellType.STRING);
		HSSFRichTextString header = new HSSFRichTextString(DOMAIN_MODEL_HEADER);
		if (sectionHeaderFont != null) {
			header.applyFont(this.sectionHeaderFont);
		}
		headerCell.setCellValue(header);	
	}
	
	/**
	 * Write sub-headers <b>Description</b> and <b>Value</b>
	 * @param domainSheet
	 */
	private void createDomainSubHeaders(HSSFSheet domainSheet, DomainExportData domainExportData) {
		//create sub headers
		createSubHeader(domainSheet, currentRowCounter, DESC_COLUMN_POSITION, DESCRIPTION_HEADER);
		createSubHeader(domainSheet, currentRowCounter, VALUE_COLUMN_POSITION, VALUE_HEADER);
		currentRowCounter++;
		
		//The next row is start for values.
		domainExportData.setStartRow(currentRowCounter);
		domainExportData.setColumnPosition(VALUE_COLUMN_POSITION);
	}
	
	private void createSubHeader(HSSFSheet domainSheet, 
			                     int startRowNo,
			                     int columnIndex,
			                     String headerName) {
		HSSFRow row = domainSheet.getRow(startRowNo);
		if (row == null) {
			row = domainSheet.createRow(startRowNo);
		}
		HSSFCell subHeaderCell = row.createCell(columnIndex,  CellType.STRING);
		HSSFRichTextString headerString = new HSSFRichTextString(headerName);
		if (sectionHeaderFont != null) {
			headerString.applyFont(this.sectionHeaderFont);
		}
		subHeaderCell.setCellValue(headerString);
	}
	
	private void init() {
		//Only initialize new workbook if not present.
		if (workbook == null) {
			workbook = new HSSFWorkbook();
			this.sectionHeaderFont = workbook.createFont();
			this.sectionHeaderFont.setBold(true);;
			this.columnHeaderFont = workbook.createFont();
			this.columnHeaderFont.setBold(true);
			this.columnHeaderFont.setItalic(true);
		}
	}
	
	private void writeDomainValues(HSSFSheet domainSheet, DomainExportData domainExportData) {
		List<DomainEntry> domainEntries = domain.getEntries();
		writeDataToExcel(domainSheet, domainEntries, domainExportData);
		checkSuperDomainEntires(domainSheet, domain.getSuperDomainPath(), domainExportData);
	}
	
	private void writeDataToExcel(HSSFSheet domainSheet, 
			                      List<DomainEntry> domainEntries,
			                      DomainExportData domainExportData) {
		for (DomainEntry domainEntry : domainEntries) {
			//Write desc
			writeDescription(domainSheet, domainEntry);
			//Write single value
			writeSingleValue(domainSheet, domainEntry);
			//Write Range value
			writeRangeValue(domainSheet, domainEntry);
			//Increment counter
			this.currentRowCounter++;
		}
		//After all values are written update last row
		domainExportData.setEndRow(currentRowCounter);
	}
	
	private void checkSuperDomainEntires(HSSFSheet domainSheet,
			                             String domainPath,
			                             DomainExportData domainExportData) {
		Domain domain = IndexUtils.getDomain(this.projectName, domainPath);
		if (domain != null) {
			List<DomainEntry> domainEntries = domain.getEntries();
			writeDataToExcel(domainSheet, domainEntries, domainExportData);
			String superDomainPath = domain.getSuperDomainPath();
			Domain superDomain = IndexUtils.getDomain(this.projectName, superDomainPath);	
			if (superDomain != null) {
				checkSuperDomainEntires(domainSheet, superDomainPath, domainExportData);
			}
		}
	}
	
	private void writeSingleValue(HSSFSheet domainSheet,
			                      DomainEntry domainEntry) {
		if (!(domainEntry instanceof Single)) {
			return;
		}
		Single singleValue = (Single)domainEntry;
		writeCell(domainSheet, currentRowCounter, 1, singleValue.getValue().toString());
	}
	
	private void writeRangeValue(HSSFSheet domainSheet,
			                     DomainEntry domainEntry) {
		if (!(domainEntry instanceof Range)) {
			return;
		}
		Range range = (Range)domainEntry;
		String lowerBound = range.getLower();
		String upperBound = range.getUpper();
		boolean isLowerBoundIncluded = range.isLowerInclusive();
		boolean isUpperBoundIncluded = range.isUpperInclusive();								
		String rangeValue = 
			getRangeValue(lowerBound, upperBound, isLowerBoundIncluded, isUpperBoundIncluded);
		writeCell(domainSheet, currentRowCounter, 1, rangeValue);
	}
	
	private void writeDescription(HSSFSheet domainSheet,
			                      DomainEntry domainEntry) {
		String description = domainEntry.getDescription();
		writeCell(domainSheet, currentRowCounter, 0, description);
	}
	
	private void writeCell(HSSFSheet domainSheet,
			               int rowNumber,
			               int columnIndex,
			               String value) {
		HSSFRow row = domainSheet.getRow(rowNumber);
		if (row == null) {
			row = domainSheet.createRow(rowNumber);
		}
		HSSFCell cell = row.createCell(columnIndex,  CellType.STRING);
		cell.setCellValue(new HSSFRichTextString(value));
	}
	
	/**
     * return string representation of domain entry (Range value)
     * @param lowerBound
     * @param upperBound
     * @param isLowerBoundIncluded
     * @param isUpperBoundIncluded
     * @return
     */
	private String getRangeValue(String lowerBound, 
			                     String upperBound,
			                     boolean isLowerBoundIncluded,
			                     boolean isUpperBoundIncluded){
		StringBuilder sb = new StringBuilder();
		boolean isLowerBoundPresent = false;
		if (lowerBound != null && !"undefined".equalsIgnoreCase(lowerBound)) {
			sb.append(getLowerBoundOperator(isLowerBoundIncluded));
			sb.append(" ");
			sb.append(lowerBound);
			sb.append(" ");
			isLowerBoundPresent = true;
			
		}
		if (upperBound != null && !"undefined".equalsIgnoreCase(upperBound)) {
			if (isLowerBoundPresent) {
				sb.append("&& ");
			}
			sb.append(getUpperBoundOperator(isUpperBoundIncluded));
			sb.append(" ");
			sb.append(upperBound);
			sb.append(" ");
		}
		
		return sb.toString();
	}
	
	private String getLowerBoundOperator(boolean isLowerBoundIncluded) {
		if (isLowerBoundIncluded) {
			return ">=";
		} else {
			return ">";
		}
	}
	
	private String getUpperBoundOperator(boolean isUpperBoundIncluded) {
		if (isUpperBoundIncluded) {
			return "<=";
		} else {
			return "<";
		}
	}
	
	private void write() throws IOException {
		FileOutputStream fos = new FileOutputStream(excelFilePath);
		workbook.write(fos);
		fos.close();
	}
}
