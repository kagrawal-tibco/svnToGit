package com.tibco.cep.studio.tester.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.eclipse.core.runtime.Path;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.tester.core.StudioTesterCorePlugin;
import com.tibco.cep.studio.tester.core.model.TestDataModel;


/**
 * 
 * @author sasahoo
 *
 */
public class TestDataExcelImportHandler implements HSSFListener {

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
	private boolean all;
	private SSTRecord sstRecord;
	private Map<Integer, FormatRecord> customFormatRecords = new Hashtable<Integer, FormatRecord>();
	private List<ExtendedFormatRecord> xfRecords = new ArrayList<ExtendedFormatRecord>();
	
	private String exProjectName;

	private String name = null;
	private String type = null;
	private String folder = null;
	
	private int totalRowCount = -1;
	private int currentRow = -1;
	
	private List<Integer> columns = new ArrayList<Integer>();
	private List<String> columnNames = new ArrayList<String>();
	private Map<Integer, String> columnNamesMap = new HashMap<Integer, String>(); 
	
	private DesignerProject project;
	private List<DesignerElement> elementList;
	private Entity entity = null;	
	private int firstColumn = -1;
	private int firstRow = -1;
	private int actualDataRowCount = -1;
	private boolean alreadyProcessed = false;
	private int currentColumn = -1;
	
	private List<Entity> entityList=new ArrayList<Entity>();
	private List<List<String>> rowValues = new ArrayList<List<String>>();
	private List<String> currentList = null;
	private List<Boolean> selectedRows = new ArrayList<Boolean>();
	private List<File> fileList;
	
	private String outputPath;
	
	public TestDataExcelImportHandler( String projectDirPath, 
									   String projectName, 
			                           String outputPath,
			                           DesignerProject project, 
			                           List<File> inputFiles,
			                           boolean all) {
		this.projectDirPath = projectDirPath;
		this.projectName = projectName;
		this.outputPath = outputPath;
		this.all = all;
		this.project = project;
		this.fileList = inputFiles;
		elementList = CommonIndexUtils.getAllElements(project, new ELEMENT_TYPES[]{ELEMENT_TYPES.CONCEPT, 
				 ELEMENT_TYPES.SIMPLE_EVENT, ELEMENT_TYPES.SCORECARD});
		createEntityList();
	}
	
	private void createEntityList(){
		for (DesignerElement element: elementList) {
			if (element instanceof EntityElement) {
				entity = ((EntityElement)element).getEntity();
				entityList.add(entity);
			}
		}
	}
	
	public boolean execute(boolean all) {
		File rootProjectFile = new File(projectDirPath);
		if (!rootProjectFile.exists()) {
			return false;	
		} else {
			if (all) {
				//TODO
			} else {
				try {
					
					for(File excelFilePath : fileList){

						entity=null;
						alreadyProcessed=false;
						columnNames = new ArrayList<String>();
						rowValues = new ArrayList<List<String>>();
						currentList = new ArrayList<String>();
						columnNamesMap = new HashMap<Integer, String>();
						columns = new ArrayList<Integer>();
						selectedRows = new ArrayList<Boolean>();

						if (!excelFilePath.exists()) {
							return false;
						}
						FileInputStream fin = new FileInputStream(excelFilePath);
						POIFSFileSystem poifs = new POIFSFileSystem(fin);
					
						InputStream din = poifs.createDocumentInputStream("Workbook");
						HSSFRequest request = new HSSFRequest();
						addListeners(request, this);
						HSSFWorkbook workbook = new HSSFWorkbook(poifs);
						totalRowCount = workbook.getSheetAt(0).getPhysicalNumberOfRows();
						processWorkbookEvents(fin, poifs, din, request);
						
						
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
					return false;
				}

			}
		}
		return true;
	}
	
	@Override
	public void processRecord(Record record) {
		if (alreadyProcessed) {
			createTestDataEntries();
			return;
		}
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
				int row = labelSSTRecord.getRow();
				String cellString = sstRecord.getString(labelSSTRecord.getSSTIndex()).toString();
//				System.out.println("Cell String--"+cellString);
				cellString = cellString.trim();
				int begDataType = -1;
				if((begDataType= cellString.indexOf("("))>1){
					cellString = cellString.substring(0, begDataType-1);
				}
				if (cellString.startsWith(TEST_DATA_PROJECT_NAME)) {
					exProjectName = cellString;
					break;
				}
				if (cellString.startsWith(TEST_DATA_NAME)) {
					name = cellString.substring(TEST_DATA_NAME.length());
					break;
				}
				if (cellString.startsWith(TEST_DATA_TYPE)) {
					type = cellString.substring(TEST_DATA_TYPE.length()).toLowerCase();
					break;
				}
				if (cellString.startsWith(TEST_DATA_FOLDER)) {
					folder = cellString.substring(TEST_DATA_FOLDER.length());
					break;
				}
				if (cellString.intern() == TEST_DATA_EXT_ID) {
					columns.add(column);
					columnNamesMap.put(column, TEST_DATA_EXT_ID);
					columnNames.add(TEST_DATA_EXT_ID);
					break;
				}
				if (cellString.intern() == TEST_DATA_PAYLOAD) {
					columns.add(column);
					columnNamesMap.put(column, TEST_DATA_PAYLOAD);
					columnNames.add(TEST_DATA_PAYLOAD);
					break;
				}
				if (cellString.intern() == TEST_DATA_SELECT) {
					firstColumn = column;
					firstRow = row;
					columns.add(column);
					columnNamesMap.put(column, TEST_DATA_SELECT);
					columnNames.add(TEST_DATA_SELECT);
					actualDataRowCount = totalRowCount - firstRow;
					currentColumn = column;
					currentRow = row;
					break;
				}
				if (TEST_DATA_HEADER == cellString.intern()) {
					break;
				}
				if (row == firstRow && firstColumn > -1) {
					columns.add(column);
					columnNamesMap.put(column, cellString);
					columnNames.add(cellString);
				}
				if (row != firstRow && cellString != null) {
					if (row == currentRow + 1) {
						currentList = new ArrayList<String>();
					}
					if (currentList != null) {
						if (column > currentColumn + 1) {
							currentList.add("NA");
						}
						currentList.add(cellString);
					}
					currentRow = row;
					currentColumn = column;
				}
				break;
			}
			case NumberRecord.sid : {
				NumberRecord numrec = (NumberRecord) record;
				int column = numrec.getColumn();
				int row = numrec.getRow();
				String formattedString = formatNumberDateCell(numrec, numrec.getValue(), false);
				if (row == currentRow + 1) {
					currentList = new ArrayList<String>();
				}
				if (column > currentColumn + 1) {
					currentList.add("NA");
				}
				currentList.add(formattedString);
				currentRow = row;
				currentColumn = column;
				break;
			}
			case BlankRecord.sid :
				BlankRecord blRecord = (BlankRecord) record;
				int column = blRecord.getColumn();
				int row = blRecord.getRow();
				if (row == currentRow + 1) {
					currentList = new ArrayList<String>();
				}
				currentList.add("NA");
				currentRow = row;
				currentColumn = column;
				break;
			case BoolErrRecord.sid :
				BoolErrRecord boolRecord = (BoolErrRecord)record;
				int col = boolRecord.getColumn();
				int ro = boolRecord.getRow();
				String val = "";
				if (boolRecord.getBooleanValue()) {
					val = "true";
				} else {
					val = "false";
				}
				if (ro == currentRow + 1) {
					currentList = new ArrayList<String>();
				}
				if (col > currentColumn + 1) {
					currentList.add("NA");
				}
				currentList.add(val);
				currentRow = ro;
				currentColumn = col;
				break;
	   }
	   if (!alreadyProcessed && 
			   currentList != null && 
			   currentList.size()>0 && columnNamesMap.size()>0 &&
			   	currentList.size() == columnNamesMap.size()) {
		   selectedRows.add(Boolean.parseBoolean(currentList.get(0).toString()));
		   currentList.remove(0);
		   List<String> list = new ArrayList<String>();
		   list.addAll(currentList);
		   rowValues.add(list);
		   if (currentRow == totalRowCount) {
			   alreadyProcessed = true;
		   }
	   }
	   
	   
	}

	private String formatNumberDateCell(CellValueRecordInterface cell,
			                            double value, 
			                            boolean parseInt) {
		ExtendedFormatRecord xfr = (ExtendedFormatRecord) xfRecords.get(cell.getXFIndex());
		if (xfr == null) {
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
	
	private void addListeners(HSSFRequest req, HSSFListener listener) {
		req.addListener(listener, BOFRecord.sid);
		req.addListener(listener, SSTRecord.sid);
		req.addListener(listener, LabelSSTRecord.sid);
		req.addListener(listener, NumberRecord.sid);
		req.addListener(listener, FormatRecord.sid);
		req.addListener(listener, ExtendedFormatRecord.sid);
		req.addListener(listener, BlankRecord.sid);
		req.addListener(listener, EOFRecord.sid);
		req.addListener(listener, BoolErrRecord.sid);
		req.addListener(listener, FormulaRecord.sid);
	}
	
	private void processWorkbookEvents(FileInputStream fin,
										POIFSFileSystem poifs, 
										InputStream din,
										HSSFRequest req) throws IOException {
		HSSFEventFactory factory = null;
		try {
			factory = new HSSFEventFactory();
			factory.processWorkbookEvents(req, poifs);
		} catch (Exception e) {
			StudioTesterCorePlugin.log(e);
		} finally {
			fin.close();
			din.close();
		}
	}
	
	private void createTestDataEntries() {
		if (entity == null 
				&& name != null 
				&& type != null 
				&& folder != null ) {
			for (DesignerElement element: elementList) {
				if (element instanceof EntityElement) {
					if(element.getElementType().toString().toLowerCase().contains(type)){
					entity = ((EntityElement)element).getEntity();
					/*if(entity instanceof com.tibco.cep.designtime.core.model.element.Concept){
						com.tibco.cep.designtime.core.model.element.Concept concept = (com.tibco.cep.designtime.core.model.element.Concept) entity;
						if(concept.getSuperConceptPath() !=null && !concept.getSuperConceptPath().isEmpty()){
							if(concept.getSuperConcept()==null){
								String superConceptPath=concept.getSuperConceptPath();
								Path path=new Path(superConceptPath);
								String superConceptName=path.lastSegment().toString();
								for(Entity ent:entityList){
									if(ent.getName().equals(superConceptName)){
										com.tibco.cep.designtime.core.model.element.Concept superConcept=(com.tibco.cep.designtime.core.model.element.Concept)ent;
										concept.setSuperConcept(superConcept);
										break;
									}
								}
								
							}
						}
						model = new TestDataModel(concept, columnNames, rowValues);
					}*/
					/*if(entity instanceof Event){
						model = new TestDataModel(entity, columnNames, rowValues);
					}*/
					for(List<String> list:rowValues){
						for(int index=0;index<list.size();index++){
							if("NA".equalsIgnoreCase(list.get(index).trim())){
								list.set(index, "");
							}
							if("\"NA\"".equalsIgnoreCase(list.get(index).trim())){
								list.set(index, "NA");
							}
						}
					}
					TestDataModel model = new TestDataModel(entity, columnNames, rowValues);
					model.setSelectRowData(selectedRows);
					if (entity.getName().equals(name) &&
							entity.getFolder().equals(folder) &&
							CommonIndexUtils.getFileExtension(entity).equals(type)) {
						Path outputFilePath = new Path(outputPath + entity.getFullPath() + CommonIndexUtils.DOT + type + "testdata");
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