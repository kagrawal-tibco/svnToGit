/**
 * 
 */
package com.tibco.cep.studio.core.domain.importHandler.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.CellValueRecordInterface;
import org.apache.poi.hssf.record.EOFRecord;
import org.apache.poi.hssf.record.ExtendedFormatRecord;
import org.apache.poi.hssf.record.FormatRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.domain.importHandler.AbstractDomainModelImportHandler;
import com.tibco.cep.studio.core.domain.importHandler.DomainConfiguration;
import com.tibco.cep.studio.core.domain.importHandler.DomainModelImportException;
import com.tibco.cep.studio.core.domain.importHandler.IDomainModelImportHandler;
import com.tibco.cep.studio.core.domain.importSource.impl.DomainModelExcelDataSource;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.validation.ValidationError;


/**
 * @author aathalye
 *
 */
public class DomainModelExcelImportHandler extends AbstractDomainModelImportHandler<String, DomainModelExcelDataSource> implements HSSFListener, IDomainModelImportHandler<String, DomainModelExcelDataSource> {
	
	public static final String DOMAIN_MODEL_HEADER = "DomainModel";
	
	public static final String DESCRIPTION_HEADER = "Description";
	
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
	
	public static final String VALUE_HEADER = "Value";
	
	
	/**
	 * The column number containing descriptions
	 */
	private int descriptionColumn;
	
	/**
	 * The column number containing values
	 */
	private int valueColumn;
	
	/**
	 * Keep a reference to this since the actual
	 * {@link DomainEntry} is decided by value
	 */
	private String rowDescription;
	
		
	//Maintain a reference to this
	private SSTRecord sstRecord;
	
	/**
	 * Courtesy old code
	 */
	private Map<Integer, FormatRecord> customFormatRecords = new Hashtable<Integer, FormatRecord>();
	
	/**
	 * Courtesy old code
	 */
	private List<ExtendedFormatRecord> xfRecords = new ArrayList<ExtendedFormatRecord>();

	public DomainModelExcelImportHandler(List<ValidationError> errors, DomainModelExcelDataSource dataSource) {
		super(dataSource, errors);
	}

	/* (non-Javadoc)
	 * @see org.apache.poi.hssf.eventusermodel.HSSFListener#processRecord(org.apache.poi.hssf.record.Record)
	 */
	@Override
	public void processRecord(Record record) {
		switch (record.getSid()) {
		
		case SSTRecord.sid :
			sstRecord = (SSTRecord)record;
		    break;
		case FormatRecord.sid :
			FormatRecord fr = (FormatRecord) record;

			customFormatRecords.put(new Integer(fr.getIndexCode()), fr);
			break;
		case ExtendedFormatRecord.sid :
			ExtendedFormatRecord xr = (ExtendedFormatRecord) record;
			xfRecords.add(xr);
			break;
		
		case LabelSSTRecord.sid : {
			LabelSSTRecord labelSSTRecord = (LabelSSTRecord)record;
			int column = labelSSTRecord.getColumn();
			//Get the concerned row
			int row = labelSSTRecord.getRow();
			String cellString = 
				sstRecord.getString(labelSSTRecord.getSSTIndex()).toString();
			if (DESCRIPTION_HEADER == cellString.intern()) {
				//This is description column
				descriptionColumn = column;
				break;
			}
			if (VALUE_HEADER == cellString.intern()) {
				//This is description column
				valueColumn = column;
				break;
			}
			if (DOMAIN_MODEL_HEADER == cellString.intern()) {
				break;
			}
			//The first non-null string after the header strings
			if (cellString != null) {
				createDomainEntries(cellString, column, row);
			}
			break;
		}
		
		case NumberRecord.sid : {
			NumberRecord numrec = (NumberRecord) record;

			int column = numrec.getColumn();
			//Get the concerned row
			int row = numrec.getRow();
			
			//If date
			String formattedString = 
				formatNumberDateCell(numrec, numrec.getValue(), false);
			createDomainEntries(formattedString, column, row);
		}
		}
	}
	
	
	
	private void createDomainEntries(String cellString, 
			                         int column,
			                         int row) {
		createDomainModel();
		DomainEntry domainEntry = null;
		if (column == descriptionColumn) {
			rowDescription = cellString;
		}
		if (column == valueColumn) {
			domainEntry = 
				CommonUtil.parseValuesToDomainEntry(cellString, dataType);
			domainEntry.setDescription(rowDescription);
			//Reset description
			rowDescription = null;
			List<DomainEntry> entries = domain.getEntries();
			if (contains(entries, domainEntry)) {
				validationErrors.add(new ValidationError(cellString, "Duplicate Entry"));
				return;
			}
			entries.add(domainEntry);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.domain.importHandler.IDomainModelImportHandler#importDomain(com.tibco.cep.studio.core.domain.importHandler.IDomainImportSource, com.tibco.cep.studio.core.domain.importHandler.DomainConfiguration)
	 */
	public void importDomain(DomainConfiguration domainConfiguration) throws DomainModelImportException {
		
		String projectDirectoryPath = domainConfiguration.getProjectDirectoryPath();
		String domainName = domainConfiguration.getDomainName();
		dataType = domainConfiguration.getDomainDataType();
		String excelFilePath = domainImportDataSource.getSource();
		String domainFolderPath = domainConfiguration.getDomainFolderPath();
		String domainDescription = domainConfiguration.getDomainDescription();
		
		File rootProjectFile = new File(projectDirectoryPath);
		String projectName = rootProjectFile.getName();
		//This should be absolute path
		try {
			FileInputStream fin = new FileInputStream(excelFilePath);
			POIFSFileSystem poifs = new POIFSFileSystem(fin);
			InputStream din = poifs.createDocumentInputStream("Workbook");
			// Request object is need for event model system
			HSSFRequest request = new HSSFRequest();
			//Add Listeners for various events
			addListeners(request, this);
			processWorkbookEvents(fin, poifs, din, request);

			domain.setName(domainName);
			domain.setDescription(domainDescription);
			domain.setDataType(dataType);
			domain.setFolder(domainFolderPath);
			domain.setOwnerProjectName(projectName);
		} catch (IOException ioe) {
			throw new DomainModelImportException(ioe);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.domain.importHandler.IDomainModelImportHandler#getImportedDomain()
	 */
	

	private void addListeners(HSSFRequest req, 
			                  HSSFListener listener) {
		req.addListener(listener, BOFRecord.sid);
		req.addListener(listener, SSTRecord.sid);
		req.addListener(listener,
				LabelSSTRecord.sid);
		req.addListener(listener,
				NumberRecord.sid);
		req.addListener(listener,
				FormatRecord.sid);
		req.addListener(listener,
				ExtendedFormatRecord.sid);
		req.addListener(listener,
				BlankRecord.sid);
		req.addListener(listener, EOFRecord.sid);
		req.addListener(listener,
				BoolErrRecord.sid);
		req.addListener(listener,
				FormulaRecord.sid);
	}
	
	/**
	 * Courtesy Old code
	 * @param cell
	 * @param value
	 * @param parseInt
	 * @return
	 */
	private String formatNumberDateCell(CellValueRecordInterface cell,
			                            double value, 
			                            boolean parseInt) {
		// Get the built in format, if there is one
		ExtendedFormatRecord xfr = (ExtendedFormatRecord) xfRecords.get(cell
				.getXFIndex());
		if (xfr == null) {

			System.err.println("Cell " + cell.getRow() + "," + cell.getColumn()
					+ " uses XF with index " + cell.getXFIndex()
					+ ", but we don't have that");
			int intVal = (int)value;
			if (value == intVal){
				return "" + intVal;
				
			}
			return Double.toString(value);
		} else {

			int formatIndex = xfr.getFormatIndex();

			String format;

			if (formatIndex >= HSSFDataFormat
					.getNumberOfBuiltinBuiltinFormats()) {
				FormatRecord tfr = (FormatRecord) customFormatRecords
						.get(new Integer(formatIndex));
				format = tfr.getFormatString();
			} else {

				format = HSSFDataFormat.getBuiltinFormat(xfr.getFormatIndex());
			}

			// Is it a date?

			if (HSSFDateUtil.isADateFormat(formatIndex, format)
					&& HSSFDateUtil.isValidExcelDate(value)) {
				// Java wants M not m for month

				format = format.replace('m', 'M');
				// Change \- into -, if it's there
				format = format.replaceAll("\\\\-", "-");

				// Format as a date

				Date d = HSSFDateUtil.getJavaDate(value, false);
				//DateFormat df = new SimpleDateFormat(format);
				// format it to BE Date time format
				DateFormat df = new SimpleDateFormat(DATE_TIME_PATTERN);
				String formattedDate = df.format(d);
				//return df.format(d);
				return formattedDate;
			} else {
				if (format == "General") {
					// Some sort of wierd default
					int intVal = (int)value;
					if (value == intVal) {
						return "" + intVal;
						
					}
					return Double.toString(value);
				}

				// Format as a number
				DecimalFormat df = new DecimalFormat(format);
				if (parseInt) {
					return DecimalFormat.getIntegerInstance().format(value);
				}
				else {
					return df.format(value);
				}
			}
		}
	}
	
	/**
	 * @param fin
	 * @param poifs
	 * @param din
	 * @param req
	 * @throws IOException
	 */
	private void processWorkbookEvents(FileInputStream fin,
			                           POIFSFileSystem poifs, 
			                           InputStream din,
			                           HSSFRequest req) throws IOException {
		HSSFEventFactory factory = null;
		try {
			factory = new HSSFEventFactory();
			/*
			 * process our events based on the document input
			 * stream
			 */
			factory.processWorkbookEvents(req, poifs);
		} catch (Exception e) {
			StudioCorePlugin.log(e);
		} finally {
			/*
			 * once all the events are processed close our file
			 * input stream
			 */
			fin.close();
			/*
			 * and our document input stream (don't want to leak
			 * these!)
			 */
			din.close();
		}
	}
}
