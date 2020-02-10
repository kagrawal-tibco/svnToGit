package com.tibco.cep.decision.table.provider.excel;

import static com.tibco.cep.decision.table.provider.excel.ExcelProviderConstants.COLUMN_ALIAS_ID;
import static com.tibco.cep.decision.table.provider.excel.ExcelProviderConstants.HEADER_GENERAL_PROPERTIES;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
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
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellAddress;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.decision.table.language.DTLanguageUtil;
import com.tibco.cep.decision.table.language.DTRegexPatterns;
import com.tibco.cep.decision.table.model.controller.ColumnModelController;
import com.tibco.cep.decision.table.model.domainmodel.Domain;
import com.tibco.cep.decision.table.model.domainmodel.DomainEntry;
import com.tibco.cep.decision.table.model.domainmodel.DomainModelFactory;
import com.tibco.cep.decision.table.model.domainmodel.EntryValue;
import com.tibco.cep.decision.table.model.domainmodel.RangeInfo;
import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decision.table.model.dtmodel.Property;
import com.tibco.cep.decision.table.model.dtmodel.ResourceType;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.utils.DecisionConstants;
import com.tibco.cep.decision.table.utils.DecisionTableCoreUtil;
import com.tibco.cep.decision.table.utils.LegacyDecisionTableCoreUtil;
import com.tibco.cep.decision.table.utils.LegacyDecisionTableCoreUtil.PropertyPathWithDataType;
import com.tibco.cep.decision.table.utils.Messages;
import com.tibco.cep.decisionproject.acl.ValidationError;
import com.tibco.cep.decisionproject.acl.ValidationErrorImpl;
import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.decisionproject.util.DTConstants;
import com.tibco.cep.studio.core.utils.DecisionTableColumnIdGenerator;
import com.tibco.cep.util.PlatformUtil;
import com.tibco.decision.table.core.DecisionTableCorePlugin;

/**
 * Do not add Eclipse UI code here at all.
 * TODO Move EMF/model code elsewhere
 */
public class ExcelImportListener implements HSSFListener {
	
	private int sheetCount = 0;
	private static final String DECLARATION_SECTION = "Declarations";
	private static final String DECISION_TABLE_SECTION = "DecisionTable";
	private static final String EXCEPTION_TABLE_SECTION = "ExceptionTable";
	private static final String Excel_VERSION = "Version";
	private boolean foundDeclSection;
	private boolean foundGenPropsSection = false;
	private boolean foundDTSection;
	private boolean foundETSection;
	private int PATH_COLUMN_COUNT = -1;
	private int ALIAS_COLUMN_COUNT = -1;
	private int PROPERTY_COLUMN_COUNT = -1;
	private int LEGEND_COLUMN_COUNT = -1;
	private int DOMAIN_TYPE_COLUMN_COUNT = -1;
	private int DOMAIN_VALUES_COLUMN_COUNT = -1;
	private int DECL_HEADER_ROW_COUNT = -1;
	private int DECL_STRT_ROW_COUNT = -1;
	private int DECL_END_ROW_COUNT = -1;
	private int DT_HEADER_ROW = -1;
	private int DT_STRT_ROW = -1;
	private int DT_END_ROW = -1;
	private int ET_STRT_ROW = -1;
	private int ET_HEADER_ROW = -1;
	private static final String PATH = "path";
	private static final String ALIAS = "alias";
	private static final String PROPERTY = "property";
	private static final String LEGEND = "legend";
	private static final String DOMAINTYPE = "domainType";
	private static final String DOMAINVALUES = "domainValues";
	private static final String CONDITION = "condition";
	private static final String CUSTOM_CONDITION = "CustomCondition";
	private static final String ACTION = "action";
	private static final String CUSTOM_ACTION = "CustomAction";
	private static final String DESCRIPTION = "description";
	private static final String PRIORITY = "priority";
	private static final String ID = "Id";
	private int DT_DES_COLUMN_COUNT = -1;
	private int ET_DES_COLUMN_COUNT = -1;
	private int DT_ID_COLUMN_COUNT = -1;
	private int ET_ID_COLUMN_COUNT = -1;
	private int DT_PRI_COLUMN_COUNT = -1;
	private int ET_PRI_COLUMN_COUNT = -1;
	private String path;
	private String alias;
	private String property;
	private String legend;
	private String domainType;
	private String domainValues;
	private List<RuleParticipantInfo> dtconditionColumnList;
	private List<RuleParticipantInfo> dtactionColumnList;
	private List<RuleParticipantInfo> etconditionColumnList;
	private List<RuleParticipantInfo> etactionColumnList;
	private List<RuleParticipantInfo> conditionList;
	private List<RuleParticipantInfo> actionList;
	private Set<String> dtConditioncolumns ;
	private Set<String> dtActioncolumns ;
	private Set<String> etConditioncolumns ;
	private Set<String> etActioncolumns ;
	private int rowCount;
	private int RULE_PARTICIPANT_CUSTOM = 1;
	private int RULE_PARTICIPANT_NON_CUSTOM = 0;
	// Records we pick up as we process
	private SSTRecord sstRecord;
	private Map<Integer, FormatRecord> customFormatRecords = new Hashtable<Integer, FormatRecord>();
	private List<ExtendedFormatRecord> xfRecords = new ArrayList<ExtendedFormatRecord>();
	private AbstractResource template;
	
	private static final String REGEX_SUFFIX = "[\\s\\(\\d+\\)]*$";
	private static final String CONDITION_REGEX = "(?i)^" + CONDITION + REGEX_SUFFIX;
	private static final String CUSTOM_CONDITION_REGEX = "(?i)^" + CUSTOM_CONDITION + REGEX_SUFFIX;
	private static final String ACTION_REGEX = "(?i)^" + ACTION + REGEX_SUFFIX;
	private static final String CUSTOM_ACTION_REGEX = "(?i)^" + CUSTOM_ACTION + REGEX_SUFFIX;
	
	// Call back method

	private Table tableEModel;
	private TableRuleSet exceptionTable;
	private TableRuleSet decisionTable;
	private Map<String, String> aliasPathMap;
	private Map<String, PropertyPathWithDataType> propPathAndDataTypeMap;
	private String description;
	private String id;
	private String priority;
	private String effectiveDate = null;
	private String expiryDate = null;
	private String genPriority = null;
	private String atMostOneRow = null;
	
	private final static int Excel_VALIDATION_ERROR_CODE = 0x500;
	
	private Collection<ValidationError> excelVldErrorCollection;
	
	private List<String> etCustomConditionIdList;
	private List<String> etCustomActionIdList;
	private Map<String, String> etColNameIdMap;
	private List<String> dtCustomConditionIdList;
	private List<String> dtCustomActionIdList;
	private Map<String, String> dtColNameIdMap;
	private DecisionTableColumnIdGenerator columnIdGenerator;
	private int currentDTRuleId = 0;
	private int currentETRuleId = 0;
	private List<Object> ruleIdCache;
	private boolean useExistingId;
	private boolean foundDTHeaderRow;
	private boolean foundETHeaderRow;
	private boolean foundVersionNo;
	private boolean foundVersionHeader;
	private double versionNo = -1;
	private ExcelParser xlParser;
	private int versionRowNo = -1;
	private int versionHeaderColumnNo = -1;
	private boolean foundRegularDTCondiiton = false;
	private boolean foundRegularETCondiiton = false;
	// added boolean if dt/dm sheet is found
	
	private boolean isDTAvailable;
	
	private boolean foundDTSheet;
	
	private boolean foundDMSheet;
	
	private boolean useExistingIDsAtImport;
	
	private Map<Integer, String> sheetIndexMap ;
	
	private int sheetIndex = 0;
		
	private int customConditionCounter;
	
	private int customActionCounter;
	 
	private int errorReturnCode = 1;
	
	
	private HSSFWorkbook workBook;
	
	/**
	 * Keep a reference to this
	 */
	private ColumnModelController columnModelController;
	private int GEN_PROP_START_ROW = -1;
	private MetaData metaData = DtmodelFactory.eINSTANCE.createMetaData();
	private ArrayList<Property> propertyList = new ArrayList<Property>();
	
	/**
	 * 
	 * @param projectName -> This could either be only a name or entire path of project as on file system
	 * @param template
	 * @param validationErrors
	 */
	public ExcelImportListener(String projectName, 
			                   AbstractResource template,
			                   Collection<ValidationError> validationErrors,
			                   boolean useExistingColumnIDs, HSSFWorkbook workBook) {
		this.template = template;
		
		this.workBook = workBook;
		dtconditionColumnList = new ArrayList<RuleParticipantInfo>();
		dtactionColumnList = new ArrayList<RuleParticipantInfo>();
		etconditionColumnList = new ArrayList<RuleParticipantInfo>();
		etactionColumnList = new ArrayList<RuleParticipantInfo>();
		conditionList = new ArrayList<RuleParticipantInfo>();
		actionList = new ArrayList<RuleParticipantInfo>();
		tableEModel = DtmodelFactory.eINSTANCE.createTable();
		//Set since value
		tableEModel.setSince(DTConstants.SINCE_BE_40);
		decisionTable = DtmodelFactory.eINSTANCE.createTableRuleSet();
		tableEModel.setDecisionTable(decisionTable);
		exceptionTable = DtmodelFactory.eINSTANCE.createTableRuleSet();
		tableEModel.setExceptionTable(exceptionTable);
		aliasPathMap = new HashMap<String, String>();
		propPathAndDataTypeMap = 
			LegacyDecisionTableCoreUtil.getPropertyPathWithDataTypeMap(tableEModel, template, projectName);
		excelVldErrorCollection = (validationErrors != null) ? validationErrors : new ArrayList<ValidationError>();
		etCustomConditionIdList = new ArrayList<String>();
		etCustomActionIdList = new ArrayList<String>();
		etColNameIdMap = new HashMap<String, String>();
		dtColNameIdMap = new HashMap<String, String>();
		dtCustomConditionIdList = new ArrayList<String>();
		dtCustomActionIdList = new ArrayList<String>();
		// initialize columnn id generator
		columnIdGenerator = new DecisionTableColumnIdGenerator();
		columnIdGenerator.initializeCounter(0, 0);
		ruleIdCache = new ArrayList<Object>();
		useExistingId = true;
		useExistingIDsAtImport = useExistingColumnIDs;
		sheetIndexMap = new HashMap<Integer, String>();
		dtConditioncolumns = new HashSet<String>();
		dtActioncolumns = new HashSet<String>();
		etConditioncolumns = new HashSet<String>();
		etActioncolumns = new HashSet<String>();
		columnModelController = new ColumnModelController(projectName);
		MetaData metaData = DtmodelFactory.eINSTANCE.createMetaData();
		ArrayList<Property> propertyList = new ArrayList<Property>();
	}
	
	/**
	 * 
	 * @param projectName
	 * @param template
	 * @param importDM
	 */
	public ExcelImportListener(String projectName, 
			                   AbstractResource template, 
			                   boolean importDM,
			                   boolean useExistingIDsAtImport, HSSFWorkbook workBook) {
		this(projectName, template, new ArrayList<ValidationError>(), useExistingIDsAtImport, workBook);
	}

	public void processRecord(Record record) {
		
		int thisRow = -1;
		int thisColumn = -1;
		String thisStr = null;

		switch (record.getSid()) {

		// the BOFRecord can represent either the beginning of a sheet or the

		// workbook

		case BOFRecord.sid:

			BOFRecord bof = (BOFRecord) record;			

			if (bof.getType() == BOFRecord.TYPE_WORKBOOK) {
				if (PlatformUtil.INSTANCE.isStudioPlatform()) {
					DecisionTableCorePlugin.debug("Encountered workbook");
				}
				// assigned to the class level member
			} else if (bof.getType() == BOFRecord.TYPE_WORKSHEET) {
				// commented shifted to BoundSheetRecord.sid
				//sheetCount++;
				String sheetName = sheetIndexMap.get(sheetCount);
				if (PlatformUtil.INSTANCE.isStudioPlatform()) {
					DecisionTableCorePlugin.debug("Encountered sheet reference {0}" , sheetName);
				}
				if (DTConstants.SHEET_DECISION_TABLE.equalsIgnoreCase(sheetName)){
					foundDMSheet = false;
					foundDTSheet = true;
					isDTAvailable = true;
					//populateDTArguments();
				} else if (DTConstants.SHEET_DOMAIN_MODEL.equalsIgnoreCase(sheetName)){
					foundDTSheet = false;
					foundDMSheet = true;
				} else {
					foundDTSheet = false;
					foundDMSheet = false;
					if (PlatformUtil.INSTANCE.isStudioPlatform()) {
						DecisionTableCorePlugin.debug("Ignoring worksheet named {0}", sheetName);
					}
				}
				sheetCount ++;
			}
			break;

		case SSTRecord.sid:	
			sstRecord = (SSTRecord) record;
			break;

		case FormatRecord.sid:
			FormatRecord fr = (FormatRecord) record;

			customFormatRecords.put(new Integer(fr.getIndexCode()), fr);
			break;

		case ExtendedFormatRecord.sid:
			ExtendedFormatRecord xr = (ExtendedFormatRecord) record;
			xfRecords.add(xr);
			break;

		case BlankRecord.sid:
			// Do not process sheet if it's not DecisionTable sheet or DomainModel sheet
			if (!(foundDTSheet || foundDMSheet)){
				return;
			}
			BlankRecord brec = (BlankRecord) record;
			thisRow = brec.getRow();
			thisColumn = brec.getColumn();
			thisStr = "";
			if (foundDTSheet){
				updateModel(thisRow, thisColumn, thisStr, null, true);
			} else {
				// process domain model here
				// blank values are not put in domain model
		
			}
			break;

		case BoolErrRecord.sid:
			// Do not process sheet if it's not DecisionTable sheet or DomainModel sheet
			if (!(foundDTSheet || foundDMSheet)){
				return;
			}
			BoolErrRecord berec = (BoolErrRecord) record;

			thisRow = berec.getRow();
			thisColumn = berec.getColumn();
			if (berec.getBooleanValue())
				thisStr = "true";
			else
				thisStr = "false";
			if (foundDTSheet){
				updateModel(thisRow, thisColumn, thisStr, null, true);
			} else {
				// process Domain Model here
//				if (importDM){
//					dmImporter.processValues(thisRow, thisColumn, thisStr);
//				}
				
			}
			break;

		case LabelSSTRecord.sid:
			// Do not process sheet if it's not DecisionTable sheet or DomainModel sheet
			if (!(foundDTSheet || foundDMSheet)){
				return;
			}
			LabelSSTRecord lsrec = (LabelSSTRecord) record;		
			thisRow = lsrec.getRow();	
			thisColumn = lsrec.getColumn();
			
			int startIdx = 0;
			int endIdx = 0;
			String colId = "";
			int dtColumn = -1;
			String comment = null;
			boolean isEnabled = true;
            
			if (sstRecord == null) {
				thisStr = '"' + "(No SST Record, can't identify string)" + '"';
			} else {
				thisStr = sstRecord.getString(lsrec.getSSTIndex()).toString();
				if(workBook != null) {
					HSSFSheet sheet = workBook.getSheet("DecisionTable");
					if (sheet.getCellComment(new CellAddress(thisRow, thisColumn)) != null){
						comment = sheet.getCellComment(new CellAddress(thisRow, thisColumn))
								.getString().getString();
						
					}
					if(sheet.getRow(thisRow) != null && sheet.getRow(thisRow).getCell(thisColumn) != null) {
						isEnabled = !sheet.getRow(thisRow).getCell(thisColumn).getCellStyle().getLocked();
					}else {
						isEnabled = true;
					}
				}
				thisStr = thisStr != null ? thisStr.trim() : null;
				if (thisStr != null) {
					if (foundDMSheet){										
						// process domain model here
//						if (importDM){
//							dmImporter.processValues(thisRow, thisColumn, thisStr);
//						}
						break;
					}
					// check if version information is present inside Excel
					if (!foundVersionHeader) {
						// remove all blank spaces
						if (thisStr.equalsIgnoreCase(Excel_VERSION)) {
							foundVersionHeader = true;
							versionRowNo = thisRow;
							versionHeaderColumnNo = thisColumn;
						}

					}
					if ((!foundVersionNo) && foundVersionHeader
							&& (thisRow == versionRowNo)
							&& (thisColumn != versionHeaderColumnNo)) {
						try {
							versionNo = Double.parseDouble(thisStr);
							foundVersionNo = true;
						} catch (Exception e) {
							// version no is not a valid format
						}
					}
					if (!foundDeclSection
							&& thisStr.equalsIgnoreCase(DECLARATION_SECTION)) {
						// check if version information is found , if not then
						// throw exception
						try {
							verifyVersionInformation();
						} catch (ExcelImportException e) {
							throw new RuntimeException(e);
						}
						foundDeclSection = true;
					} else if (!foundGenPropsSection && thisStr.equalsIgnoreCase(HEADER_GENERAL_PROPERTIES)) {
						DECL_END_ROW_COUNT = thisRow - 1;
						GEN_PROP_START_ROW  = thisRow;
						foundGenPropsSection = true;
					} else if (!foundDTSection
							&& thisStr.equalsIgnoreCase(DECISION_TABLE_SECTION)) {
						if (DECL_END_ROW_COUNT == -1) {// Case when General Properties Section is not Available in the xls file.
							DECL_END_ROW_COUNT = thisRow - 1;
						}
						foundDTSection = true;
					} else if (!foundETSection
							&& thisStr
									.equalsIgnoreCase(EXCEPTION_TABLE_SECTION)) {
						DT_END_ROW = thisRow - 1;
						foundETSection = true;
					}
					if (foundDeclSection
							&& (DECL_END_ROW_COUNT == -1 || thisRow <= DECL_END_ROW_COUNT)) {
						if (DECL_HEADER_ROW_COUNT == -1
								|| thisRow == DECL_HEADER_ROW_COUNT) {
							if (PATH_COLUMN_COUNT == -1
									&& thisStr.equalsIgnoreCase(PATH)) {
								PATH_COLUMN_COUNT = thisColumn;
								DECL_STRT_ROW_COUNT = thisRow + 1;
								rowCount = DECL_STRT_ROW_COUNT;
								DECL_HEADER_ROW_COUNT = thisRow;
							} else if (ALIAS_COLUMN_COUNT == -1
									&& thisStr.equalsIgnoreCase(ALIAS)) {
								ALIAS_COLUMN_COUNT = thisColumn;
								DECL_HEADER_ROW_COUNT = thisRow;
							} else if (PROPERTY_COLUMN_COUNT == -1
									&& thisStr.equalsIgnoreCase(PROPERTY)) {
								PROPERTY_COLUMN_COUNT = thisColumn;
								DECL_HEADER_ROW_COUNT = thisRow;
							} else if (LEGEND_COLUMN_COUNT == -1
									&& thisStr.equalsIgnoreCase(LEGEND)) {
								LEGEND_COLUMN_COUNT = thisColumn;
								DECL_HEADER_ROW_COUNT = thisRow;
							} else if (DOMAIN_TYPE_COLUMN_COUNT == -1
									&& thisStr.equalsIgnoreCase(DOMAINTYPE)) {
								DOMAIN_TYPE_COLUMN_COUNT = thisColumn;
								DECL_HEADER_ROW_COUNT = thisRow;
							} else if (DOMAIN_VALUES_COLUMN_COUNT == -1
									&& thisStr.equalsIgnoreCase(DOMAINVALUES)) {
								DOMAIN_VALUES_COLUMN_COUNT = thisColumn;
								DECL_HEADER_ROW_COUNT = thisRow;
							}

						}
					} else if (foundDTSection
							&& (DT_END_ROW == -1 || thisRow <= DT_END_ROW)) {
						if (DT_HEADER_ROW == -1 || thisRow == DT_HEADER_ROW) {
							if (thisStr.matches(CONDITION_REGEX)) {
								DT_STRT_ROW = thisRow + 1;
								DT_HEADER_ROW = thisRow;
								rowCount = DT_STRT_ROW;
								
								if (useExistingIDsAtImport) {
									try {
										startIdx = thisStr.indexOf("(");
										endIdx = thisStr.indexOf(")", startIdx);
										colId = thisStr.substring(startIdx + 1, endIdx);
										dtColumn = Integer.parseInt(colId);
									} catch (NumberFormatException nfe) {
										if (PlatformUtil.INSTANCE.isStudioPlatform()) {
											DecisionTableCorePlugin.log("DT import error: No column identifier specified for column.");
										}
	//									dtColumn = thisColumn;
	//									throw nfe;
									} catch (StringIndexOutOfBoundsException siob) {
										dtColumn = -1;
									}
								}
								
								RuleParticipantInfo info = new RuleParticipantInfo(
										thisColumn, RULE_PARTICIPANT_NON_CUSTOM, dtColumn);
								info.setEnabled(isEnabled);
								info.setComment(comment);
								dtconditionColumnList.add(info);
								foundRegularDTCondiiton = true;
							} else if (thisStr.matches(CUSTOM_CONDITION_REGEX)) {
								DT_STRT_ROW = thisRow + 1;
								DT_HEADER_ROW = thisRow;
								rowCount = DT_STRT_ROW;
								
								try {
									startIdx = thisStr.indexOf("(");
									endIdx = thisStr.indexOf(")", startIdx);
									colId = thisStr.substring(startIdx + 1, endIdx);
									dtColumn = Integer.parseInt(colId);
								} catch (NumberFormatException nfe) {
									if (PlatformUtil.INSTANCE.isStudioPlatform()) {
										DecisionTableCorePlugin.log("DT import error: No column identifier specified for column.");
									}
//									dtColumn = thisColumn;
//									throw nfe;
								} catch (StringIndexOutOfBoundsException siob) {
									dtColumn = -1;
								}
								
								RuleParticipantInfo info = new RuleParticipantInfo(
										thisColumn, RULE_PARTICIPANT_CUSTOM, dtColumn);
								info.setEnabled(isEnabled);
								info.setComment(comment);
								dtconditionColumnList.add(info);
								// noOfDTCutomConditions++;
							} else if (thisStr.matches(ACTION_REGEX)) {
								DT_HEADER_ROW = thisRow;
								
								if (useExistingIDsAtImport) {
									try {
										startIdx = thisStr.indexOf("(");
										endIdx = thisStr.indexOf(")", startIdx);
										colId = thisStr.substring(startIdx+1, endIdx);
										dtColumn = Integer.parseInt(colId);
									} catch (NumberFormatException nfe) {
										if (PlatformUtil.INSTANCE.isStudioPlatform()) {
											DecisionTableCorePlugin.log("DT import error: No column identifier specified for column.");
										}
	//									dtColumn = thisColumn;
	//									throw nfe;
									} catch (StringIndexOutOfBoundsException siob) {
										dtColumn = -1;
									}
								}
								
								RuleParticipantInfo info = new RuleParticipantInfo(
										thisColumn, RULE_PARTICIPANT_NON_CUSTOM, dtColumn);
								info.setEnabled(isEnabled);
								info.setComment(comment);
								dtactionColumnList.add(info);
								foundRegularDTCondiiton = true;
							} else if (thisStr.matches(CUSTOM_ACTION_REGEX)) {
								DT_HEADER_ROW = thisRow;
								
								if (useExistingIDsAtImport) {
									try {
										startIdx = thisStr.indexOf("(");
										endIdx = thisStr.indexOf(")", startIdx);
										colId = thisStr.substring(startIdx + 1, endIdx);
										dtColumn = Integer.parseInt(colId);
									} catch (NumberFormatException nfe) {
										if (PlatformUtil.INSTANCE.isStudioPlatform()) {
											DecisionTableCorePlugin.log("DT import error: No column identifier specified for column.");
										}
	//									dtColumn = thisColumn;
	//									throw nfe;
									} catch (StringIndexOutOfBoundsException siob) {
										dtColumn = -1;
									}
								}
								RuleParticipantInfo info = new RuleParticipantInfo(
										thisColumn, RULE_PARTICIPANT_CUSTOM, dtColumn);
								info.setEnabled(isEnabled);
								info.setComment(comment);
								dtactionColumnList.add(info);
								// noOfDTCustomActions++;
							} else if (thisStr.equalsIgnoreCase(DESCRIPTION)) {
								DT_DES_COLUMN_COUNT = thisColumn;
							} else if (thisStr.equalsIgnoreCase(PRIORITY)) {
								DT_PRI_COLUMN_COUNT = thisColumn;
							} else if (thisStr.equalsIgnoreCase(ID)) {
								DT_ID_COLUMN_COUNT = thisColumn;
							}
						}
					} else if (foundETSection) {
						if (ET_HEADER_ROW == -1 || thisRow == ET_HEADER_ROW) {
							if (thisStr.matches(CONDITION_REGEX)) {
								ET_HEADER_ROW = thisRow;
								ET_STRT_ROW = thisRow + 1;
								rowCount = ET_STRT_ROW;
								
								if (useExistingIDsAtImport) {
									try {
										startIdx = thisStr.indexOf("(");
										endIdx = thisStr.indexOf(")", startIdx);
										colId = thisStr.substring(startIdx + 1, endIdx);
										dtColumn = Integer.parseInt(colId);
									} catch (NumberFormatException nfe) {
										if (PlatformUtil.INSTANCE.isStudioPlatform()) {
											DecisionTableCorePlugin.log("DT import error: No column identifier specified for column.");
										}
	//									dtColumn = thisColumn;
	//									throw nfe;
									} catch (StringIndexOutOfBoundsException siob) {
										dtColumn = -1;
									}
								}
								
								RuleParticipantInfo info = new RuleParticipantInfo(
										thisColumn, RULE_PARTICIPANT_NON_CUSTOM, dtColumn);
								info.setEnabled(isEnabled);
								info.setComment(comment);
								etconditionColumnList.add(info);
								foundRegularETCondiiton = true;
							} else if (thisStr.matches(CUSTOM_CONDITION_REGEX)) {
								ET_HEADER_ROW = thisRow;
								ET_STRT_ROW = thisRow + 1;
								rowCount = ET_STRT_ROW;
								
								if (useExistingIDsAtImport) {
									try {
										startIdx = thisStr.indexOf("(");
										endIdx = thisStr.indexOf(")", startIdx);
										colId = thisStr.substring(startIdx + 1, endIdx);
										dtColumn = Integer.parseInt(colId);
									} catch (NumberFormatException nfe) {
										if (PlatformUtil.INSTANCE.isStudioPlatform()) {
											DecisionTableCorePlugin.log("DT import error: No column identifier specified for column.");
										}
	//									dtColumn = thisColumn;
	//									throw nfe;
									} catch (StringIndexOutOfBoundsException siob) {
										dtColumn = -1;
									}
								}
								
								RuleParticipantInfo info = new RuleParticipantInfo(
										thisColumn, RULE_PARTICIPANT_CUSTOM, dtColumn);
								info.setEnabled(isEnabled);
								info.setComment(comment);
								etconditionColumnList.add(info);
								// noOfETCutomConditions++;
							} else if (thisStr.matches(ACTION_REGEX)) {
								ET_HEADER_ROW = thisRow;
								
								if (useExistingIDsAtImport) {
									try {
										startIdx = thisStr.indexOf("(");
										endIdx = thisStr.indexOf(")", startIdx);
										colId = thisStr.substring(startIdx + 1, endIdx);
										dtColumn = Integer.parseInt(colId);
									} catch (NumberFormatException nfe) {
										if (PlatformUtil.INSTANCE.isStudioPlatform()) {
											DecisionTableCorePlugin.log("DT import error: No column identifier specified for column.");
										}
	//									dtColumn = thisColumn;
	//									throw nfe;
									} catch (StringIndexOutOfBoundsException siob) {
										dtColumn = -1;
									}
								}
								
								RuleParticipantInfo info = new RuleParticipantInfo(
										thisColumn, RULE_PARTICIPANT_NON_CUSTOM, dtColumn);
								info.setEnabled(isEnabled);
								info.setComment(comment);
								etactionColumnList.add(info);
								foundRegularETCondiiton = true;
							} else if (thisStr.matches(CUSTOM_ACTION_REGEX)) {
								ET_HEADER_ROW = thisRow;
								
								if (useExistingIDsAtImport) {
									try {
										startIdx = thisStr.indexOf("(");
										endIdx = thisStr.indexOf(")", startIdx);
										colId = thisStr.substring(startIdx + 1, endIdx);
										dtColumn = Integer.parseInt(colId);
									} catch (NumberFormatException nfe) {
										if (PlatformUtil.INSTANCE.isStudioPlatform()) {
											DecisionTableCorePlugin.log("DT import error: No column identifier specified for column.");
										}
	//									dtColumn = thisColumn;
	//									throw nfe;
									} catch (StringIndexOutOfBoundsException siob) {
										dtColumn = -1;
									}
								}
								
								RuleParticipantInfo info = new RuleParticipantInfo(
										thisColumn, RULE_PARTICIPANT_CUSTOM, dtColumn);
								info.setEnabled(isEnabled);
								info.setComment(comment);
								etactionColumnList.add(info);
								// noOfETCustomActions++;
							} else if (thisStr.equalsIgnoreCase(DESCRIPTION)) {
								ET_DES_COLUMN_COUNT = thisColumn;
							} else if (thisStr.equalsIgnoreCase(PRIORITY)) {
								ET_PRI_COLUMN_COUNT = thisColumn;
							} else if (thisStr.equalsIgnoreCase(ID)) {
								ET_ID_COLUMN_COUNT = thisColumn;
							}
						}
					} else {

					}
					
					updateModel(thisRow, thisColumn, thisStr, comment, isEnabled);
				}
			}
			break;

		case NumberRecord.sid:
			// Do not process sheet if it's not DecisionTable sheet or DomainModel sheet
			if (!(foundDTSheet || foundDMSheet)){
				return;
			}
			NumberRecord numrec = (NumberRecord) record;

			thisRow = numrec.getRow();
			thisColumn = numrec.getColumn();
			
			if (foundDMSheet){
				// process domain model
//				if (importDM){
//					thisStr = formatNumberDateCell(numrec, numrec.getValue(), false);
//					dmImporter.processValues(thisRow, thisColumn, thisStr);
//				}
				break;
			}
			// check if current row is version header row
			if ((!foundVersionNo) && foundVersionHeader
					&& thisRow == versionRowNo
					&& (thisColumn != versionHeaderColumnNo)) {
				versionNo = numrec.getValue();
				foundVersionNo = true;
			}

			// Format
			if (thisColumn == DT_PRI_COLUMN_COUNT
					|| thisColumn == ET_PRI_COLUMN_COUNT) {
				// thisStr = formatNumberDateCell(numrec, numrec.getValue(),
				// true);
				thisStr = "" + numrec.getValue();
			} else {
				thisStr = formatNumberDateCell(numrec, numrec.getValue(), false);
			}
			if (thisStr != null) {
				updateModel(thisRow, thisColumn, thisStr, null, true);
			}
			break;

		case FormulaRecord.sid:
			// Do not process sheet if it's not DecisionTable sheet or DomainModel sheet
			if (!(foundDTSheet || foundDMSheet)){
				return;
			}
			FormulaRecord fr1 = (FormulaRecord) record;
			// fr1.getColumn();
			thisRow = fr1.getRow();
			thisColumn = fr1.getColumn();

			double value = fr1.getValue();

			int intVal = (int) value;
			if (intVal == value) {
				thisStr = Integer.toString(intVal);
			} else {
				thisStr = Double.toString(value);
			}
			if (foundDTSheet){
				updateModel(thisRow, thisColumn, thisStr, null, true);
			} else {
				// process domain model here
//				if (importDM){
//					dmImporter.processValues(thisRow, thisColumn, thisStr);
//				}
			}
			break;
			
			// record added to get Sheet Name
			
		case BoundSheetRecord.sid:
		
			BoundSheetRecord bsRecord = (BoundSheetRecord) record;
			String name = bsRecord.getSheetname();
			// cache index and sheet name
			sheetIndexMap.put(sheetIndex++, name);
			
			break;
			

		case EOFRecord.sid:
			// Do not process sheet if it's not DecisionTable sheet or DomainModel sheet
			if (!(foundDTSheet || foundDMSheet)){
				return;
			}
			
			if (foundDMSheet){
				// do nothing if it's DM sheet
				break;
			}
			if (template == null || !(template instanceof RuleFunction)) {
				if (foundDeclSection && !foundDTSection) {
					addDeclaration(path, alias, property, legend, domainType,
							domainValues);
				}
			}
			try {
				if (foundDTSection && !foundETSection) {
					populateTable(decisionTable, TableTypes.DECISION_TABLE);
				}
				if (foundETSection) {
					populateTable(exceptionTable, TableTypes.EXCEPTION_TABLE);
				}
			} catch (ExcelImportException e) {
				throw new RuntimeException(e);
			}
			break;
		default:
			break;
		}

	}

	private void verifyVersionInformation() throws ExcelImportException {
		if (foundVersionNo) {
			if (!(versionNo == 1 || versionNo == 2)) {
				if (PlatformUtil.INSTANCE.isStudioPlatform()) {
					DecisionTableCorePlugin.log(this.getClass().getName(),
							Messages.getString("ImportExcel_validationerror_InvalidVersionNo"));
				}
				throw new ExcelImportException(
						Messages.getString("ImportExcel_validationerror_InvalidVersionNo"));
			} else {
				initializeExcelParser();
			}
		} else {
			versionNo = 1;
			initializeExcelParser();
		}
	}

	private void initializeExcelParser() {
		if (versionNo == 1) {
			xlParser = new ExcelVersion1Parser();
		} else if (versionNo == 2) {
			xlParser = new ExcelVersion2Parser();
		}
	}

	
	private void updateModel(int thisRow, int thisColumn, String thisStr, String comment, boolean isEnabled) {

		//if (template == null || !(template instanceof RuleFunction)) {
			if (foundDeclSection
					&& (DECL_END_ROW_COUNT == -1 || (thisRow <= (DECL_END_ROW_COUNT + 1)))) {
				if (DECL_STRT_ROW_COUNT != -1 && thisRow >= DECL_STRT_ROW_COUNT) {
					/*if (rowCount != thisRow) {
						if ((path == null || path.equals(""))
								|| (alias == null || alias.equals(""))) {
							return;
						}
						addDeclaration(path, alias, property, legend,
								domainType, domainValues);
						// after adding make variables null
						path = null;
						alias = null;
						property = null;
						legend = null;
						domainType = null;
						domainValues = null;
						rowCount = thisRow;

					}*/
					if (DECL_END_ROW_COUNT == -1
							|| thisRow <= (DECL_END_ROW_COUNT)) {
						if (thisColumn == PATH_COLUMN_COUNT) {
							path = thisStr;
							List<Argument> args = tableEModel.getArgument();
							boolean pathValid = false;
							for (Argument arg : args) {
								ArgumentProperty argProperty = arg.getProperty();
								pathValid = argProperty.getPath().equals(path);
								if (pathValid)
									break;
							}
							if (pathValid == false) {
//								DecisionTableCorePlugin.logErrorMessage("Invalid argument path : "+path);
								ValidationError vldError = new ValidationErrorImpl(
										Excel_VALIDATION_ERROR_CODE, "Invalid argument path : " + path,
										"DecisionTable");
								excelVldErrorCollection.add(vldError);
							}
						} else if (thisColumn == ALIAS_COLUMN_COUNT) {
							alias = thisStr;
							List<Argument> args = tableEModel.getArgument();
							boolean aliasValid = false;
							for (Argument arg : args) {
								ArgumentProperty argProperty = arg.getProperty();
								aliasValid = argProperty.getAlias().equals(alias);
								if (aliasValid) {
									break;
								}
							}
							if (aliasValid == false) {
//								DecisionTableCorePlugin.logErrorMessage("Invalid argument alias : "+alias);
								ValidationError vldError = new ValidationErrorImpl(
										Excel_VALIDATION_ERROR_CODE, "Invalid argument alias : " + alias,
										"DecisionTable");
								excelVldErrorCollection.add(vldError);
							}
						} /*else if (thisColumn == PROPERTY_COLUMN_COUNT) {
							property = thisStr;
						} else if (thisColumn == LEGEND_COLUMN_COUNT) {
							legend = thisStr;
						} else if (thisColumn == DOMAIN_TYPE_COLUMN_COUNT) {
							domainType = thisStr;
						} else if (thisColumn == DOMAIN_VALUES_COLUMN_COUNT) {
							domainValues = thisStr;
						} else {

						}*/
					}
				}
			//}
		} else if (foundGenPropsSection && GEN_PROP_START_ROW != -1 && thisRow == GEN_PROP_START_ROW + 3) {
			switch (thisColumn) {
			case 0:
				effectiveDate = thisStr;
				if (effectiveDate != null) {
					Property property = DtmodelFactory.eINSTANCE.createProperty();
					property.setName("EffectiveDate");
					property.setType("String");
					property.setValue(effectiveDate);
					propertyList.add(property);
				}
				break;
				
			case 1:
				expiryDate = thisStr;
				if (expiryDate != null) {
					Property property = DtmodelFactory.eINSTANCE.createProperty();
					property.setName("ExpiryDate");
					property.setType("String");
					property.setValue(expiryDate);
					propertyList.add(property);
				}
				break;
				
			case 2:
				atMostOneRow = thisStr;
				if (atMostOneRow != null) {
					Property property = DtmodelFactory.eINSTANCE.createProperty();
					property.setName("SingleRowExecution");
					property.setType("Boolean");
					property.setValue(atMostOneRow);
					propertyList.add(property);
				}
				break;
				
			case 3:
				genPriority = thisStr;
				if (genPriority != null) {
					Property property = DtmodelFactory.eINSTANCE.createProperty();
					property.setName("Priority");
					property.setType("Integer");
					property.setValue(genPriority);
					propertyList.add(property);
				}
				for (Property property : propertyList) {
					metaData.getProp().add(property);				
				}
				tableEModel.setMd(metaData);
				break;
				
			default:
				break;
			}
		} else if (foundDTSection
				&& (DT_END_ROW == -1 || (thisRow <= (DT_END_ROW + 1)))) {
			if (DT_STRT_ROW != -1 && thisRow >= DT_STRT_ROW) {
				if (rowCount != thisRow) {
					rowCount = thisRow;
					try {
						populateTable(decisionTable, TableTypes.DECISION_TABLE);
					} catch (ExcelImportException e) {
						throw new RuntimeException(e);
					}
					conditionList.clear();
					actionList.clear();
				}
				if (DT_END_ROW == -1 || thisRow <= (DT_END_ROW)) {
					RuleParticipantInfo rpi = getRuleParticipantInfo(
							thisColumn, dtconditionColumnList);
					if (rpi != null) {
						if (!thisStr.equals("")) {
							rpi.setExpression(thisStr);
							rpi.setEnabled(isEnabled);
							rpi.setComment(comment);
							conditionList.add(rpi);
						}
					} else {
						rpi = getRuleParticipantInfo(thisColumn,
								dtactionColumnList);
						if (rpi != null) {
							if (!thisStr.equals("")) {
								rpi.setExpression(thisStr);
								rpi.setEnabled(isEnabled);
								rpi.setComment(comment);
								actionList.add(rpi);
							}
						}

					}
					if (rpi == null) {
						if (DT_DES_COLUMN_COUNT != -1
								&& thisColumn == DT_DES_COLUMN_COUNT) {
							description = thisStr;
						} else if (DT_PRI_COLUMN_COUNT != -1
								&& thisColumn == DT_PRI_COLUMN_COUNT) {
							priority = thisStr;
							if (priority.contains(".")) {
								priority = discardDecimal(priority);
							}
						} else if (DT_ID_COLUMN_COUNT != -1
								&& thisColumn == DT_ID_COLUMN_COUNT) {
							id = thisStr;
							/*
							 * if (id.contains(".")) { id = discardDecimal(id); }
							 */
						}
					}

				}

			}

		} else if (foundETSection) {

			if (ET_STRT_ROW != -1 && thisRow >= ET_STRT_ROW) {
				if (rowCount != thisRow) {
					rowCount = thisRow;
					try {
						populateTable(exceptionTable, TableTypes.EXCEPTION_TABLE);
					} catch (ExcelImportException e) {
						throw new RuntimeException(e);
					}
					conditionList.clear();
					actionList.clear();
				}
				RuleParticipantInfo rpi = getRuleParticipantInfo(thisColumn,
						etconditionColumnList);
				if (rpi != null) {
					if (!thisStr.equals("")) {
						rpi.setExpression(thisStr);
						rpi.setComment(comment);
						rpi.setEnabled(isEnabled);
						conditionList.add(rpi);
					}
				} else {
					rpi = getRuleParticipantInfo(thisColumn, etactionColumnList);
					if (rpi != null) {
						if (!thisStr.equals("")) {
							rpi.setExpression(thisStr);
							rpi.setComment(comment);
							rpi.setEnabled(isEnabled);
							actionList.add(rpi);
						}
					}

				}

				if (rpi == null) {
					if (ET_DES_COLUMN_COUNT != -1
							&& thisColumn == ET_DES_COLUMN_COUNT) {
						description = thisStr;
					} else if (ET_PRI_COLUMN_COUNT != -1
							&& thisColumn == ET_PRI_COLUMN_COUNT) {
						priority = thisStr;
						if (priority.contains(".")) {
							priority = discardDecimal(priority);
						}
					} else if (ET_ID_COLUMN_COUNT != -1
							&& thisColumn == ET_ID_COLUMN_COUNT) {
						id = thisStr;
						/*
						 * if (id.contains(".")) { id = discardDecimal(id); }
						 */
					}
				}

			}
		}
	}

	private String discardDecimal(String tempString) {
		int priority = 5;
		try {
			double tempPriority = Double.parseDouble(tempString);
			priority = (int) tempPriority;
		} catch (Exception e) {
			// format is not right
		}
		return Integer.toString(priority);
	}

	private RuleParticipantInfo getRuleParticipantInfo(int thisColumn,
			List<RuleParticipantInfo> expressionList) {
		for (RuleParticipantInfo rpi : expressionList) {
			int index = rpi.getIndex();
			if (thisColumn == index)
				return rpi;

		}
		return null;
	}

	private void populateTable(TableRuleSet ruleSet, TableTypes tableType) throws ExcelImportException {
		// TODO Auto-generated method stub
		if (xlParser != null) {
			xlParser.populateTable(ruleSet, tableType);
		} else {
			throw new ExcelImportException(Messages.getString("ImportExcel_validationerror_InvalidVersionNo"));
		}

	}



	private void populateCustomColumnHeaderInformation(TableTypes tableType,
			Columns columns) {

		List<RuleParticipantInfo> conditionTRVColumnList = null;
		List<RuleParticipantInfo> actionTRVColumnList = null;
		if (TableTypes.DECISION_TABLE == tableType) {
			conditionTRVColumnList = dtconditionColumnList;
			actionTRVColumnList = dtactionColumnList;
		} else {
			conditionTRVColumnList = etconditionColumnList;
			actionTRVColumnList = etactionColumnList;
		}
		// iterate through condition list
		for (RuleParticipantInfo rpi : conditionTRVColumnList) {
			// generate id and create new column
			String propertyName = getPropertyName(rpi, conditionList);
			setColumnWithRuleParticipant(columns, rpi, propertyName, "", tableType,
					ColumnType.CUSTOM_CONDITION, false);

		}
		for (RuleParticipantInfo rpi : actionTRVColumnList) {
			// generate id and create new column
			String propertyName = getPropertyName(rpi, actionList);
			setColumnWithRuleParticipant(columns, rpi, propertyName, "", tableType,
					ColumnType.CUSTOM_ACTION, false);

		}

	}
	
	private String[] getPropertyAlias(String propertyName) {
		
		if (propertyName == null || (propertyName != null && propertyName.isEmpty())) {
			return new String[] {"", ""};
		}
		
		String alias = "";
		String newPropertyName = "";
		if (propertyName.contains("("+COLUMN_ALIAS_ID)) {
			int i = propertyName.indexOf("("+COLUMN_ALIAS_ID);
			int j = propertyName.indexOf(")", i);
			alias = propertyName.substring(i+COLUMN_ALIAS_ID.length()+1, j);
			newPropertyName = propertyName.substring(0, i-1);
		}
		
		return new String[] {newPropertyName.trim(), alias.trim()};
	}

	private void populateColumnHeaderInformation(TableTypes tableType, Columns columns) {
		String source = null;
		List<RuleParticipantInfo> conditionTRVColumnList = null;
		List<RuleParticipantInfo> actionTRVColumnList = null;
		if (TableTypes.DECISION_TABLE == tableType) {
			source = DECISION_TABLE_SECTION;
			conditionTRVColumnList = dtconditionColumnList;
			actionTRVColumnList = dtactionColumnList;
		} else {
			source = EXCEPTION_TABLE_SECTION;
			conditionTRVColumnList = etconditionColumnList;
			actionTRVColumnList = etactionColumnList;
		}
		
		// iterate through condition list
		for (RuleParticipantInfo rpi : conditionTRVColumnList) {
			// get corresponding property name
			int trvType = rpi.getRuleParticipantType();
			String propertyName = getPropertyName(rpi, conditionList);
			
			String columnAlias = "";
			String[] splits = getPropertyAlias(propertyName);
			
			if (!splits[1].isEmpty()) {
				propertyName = splits[0];
				columnAlias = splits[1];
			}
			
			
			// check if property is part of VRF argument
			if (propertyName != null) {
				boolean isAdded = false;
				//String mappedPropertyName = processPropertyName(propertyName);
				if(TableTypes.DECISION_TABLE == tableType) {
					isAdded = dtConditioncolumns.add(propertyName);
				}else{
					isAdded = etConditioncolumns.add(propertyName);	
				}
				
				//If returns false, the property is duplicated
				if(!isAdded){
					catchDupicatePropertyError(propertyName, ColumnType.CONDITION, tableType);
				}
				
				String mappedPropertyName = propertyName;
				boolean isSubstitution = DecisionTableCoreUtil.isVarString(propertyName, DecisionConstants.AREA_CONDITION);
				if (isSubstitution) {
					int idx = DTLanguageUtil.firstIndexOfOp(mappedPropertyName);
					if (idx != -1) {
						mappedPropertyName = mappedPropertyName.substring(0, idx).trim();
					}
				}
				if (propPathAndDataTypeMap.containsKey(mappedPropertyName)) {
					setColumnWithRuleParticipant(columns, rpi, propertyName, columnAlias,
							tableType, ColumnType.CONDITION, isSubstitution);
				} else if (trvType == RULE_PARTICIPANT_CUSTOM){
					setColumnWithRuleParticipant(columns, rpi, propertyName, columnAlias,
							tableType, ColumnType.CUSTOM_CONDITION, isSubstitution);
				} else{
					catchNoValidPropertyError(mappedPropertyName);
				}
			} else if (DTRegexPatterns.CUSTOM_HEADER_PATTERN.matcher(String.valueOf(propertyName)).matches()) {
				setColumnWithRuleParticipant(columns, rpi, propertyName, columnAlias,
						tableType, ColumnType.CUSTOM_CONDITION, false);
			} else {
				if (RULE_PARTICIPANT_NON_CUSTOM == trvType) {
					// Non Custom TRV should be associated with a specific
					// property
					ValidationError vldError = new ValidationErrorImpl(
							Excel_VALIDATION_ERROR_CODE, propertyName
									+ Messages.getString("General_Error"), source);
					if (!excelVldErrorCollection.contains(vldError)) {
						excelVldErrorCollection.add(vldError);
					}
					// continue processing
					continue;
				}
				// it's a custom TRV
				// generate id and create new column
				String columnName =
					DTConstants.CUSTOM_CONDITION_PREFIX + ++customConditionCounter;
				setColumnWithRuleParticipant(columns, rpi, columnName, columnAlias, 
						tableType, ColumnType.CUSTOM_CONDITION, false);
			}
		}

		// iterate through action list
		for (RuleParticipantInfo rpi : actionTRVColumnList) {
			// get corresponding property name
			int trvType = rpi.getRuleParticipantType();
			String propertyName = getPropertyName(rpi, actionList);
			// check if property is part of VRF argument
			
			String columnAlias = "";
			String[] splits = getPropertyAlias(propertyName);
			
			if (!splits[1].isEmpty()) {
				propertyName = splits[0];
				columnAlias = splits[1];
			}

			if (propertyName != null) {
				boolean isAdded = false;
				//String mappedPropertyName = processPropertyName(propertyName);
				if(TableTypes.DECISION_TABLE == tableType) {
					isAdded = dtActioncolumns.add(propertyName);
				}else{
					isAdded = etActioncolumns.add(propertyName);	
				}
				
				//String mappedPropertyName = processPropertyName(propertyName);
				String mappedPropertyName = propertyName;
				
				//If returns false, the property is duplicated
				if(!isAdded){
					catchDupicatePropertyError(mappedPropertyName, ColumnType.ACTION, tableType);
				}
				
				boolean isSubstitution = DecisionTableCoreUtil.isVarString(propertyName, DecisionConstants.AREA_ACTION);
				if (isSubstitution) {
					int idx = DTLanguageUtil.firstIndexOfOp(mappedPropertyName);
					if (idx != -1) {
						mappedPropertyName = mappedPropertyName.substring(0, idx).trim();
					}
				}
				if (propPathAndDataTypeMap.containsKey(mappedPropertyName)) {
					setColumnWithRuleParticipant(columns, rpi, propertyName, columnAlias,
							tableType, ColumnType.ACTION, isSubstitution);
				} else if (trvType == RULE_PARTICIPANT_CUSTOM){
					setColumnWithRuleParticipant(columns, rpi, propertyName, columnAlias,
							tableType, ColumnType.CUSTOM_ACTION, isSubstitution);
				}else{
					catchNoValidPropertyError(mappedPropertyName);
				}
			} else if (DTRegexPatterns.CUSTOM_HEADER_PATTERN.matcher(String.valueOf(propertyName)).matches()) {
				setColumnWithRuleParticipant(columns, rpi, propertyName, columnAlias,
						tableType, ColumnType.CUSTOM_CONDITION, false);
			} else {
				if (RULE_PARTICIPANT_NON_CUSTOM == trvType) {
					// Non Custom TRV should be associated with a specific
					// property
					ValidationError vldError = new ValidationErrorImpl(
							Excel_VALIDATION_ERROR_CODE, propertyName
									+ Messages.getString("General_Error"), source);
					if (!excelVldErrorCollection.contains(vldError)) {
						excelVldErrorCollection.add(vldError);
					}
					// continue processing
					continue;
				}

				// it's a custom TRV
				// generate id and create new column
				String columnName =
					DTConstants.CUSTOM_ACTION_PREFIX + ++customActionCounter;
				setColumnWithRuleParticipant(columns, rpi, columnName, columnAlias, 
						tableType, ColumnType.CUSTOM_ACTION, false);

			}
		}
	}
	
	
	private void setColumnWithRuleParticipant(Columns columns,
			                                  RuleParticipantInfo rpi, 
			                                  String propertyName,
			                                  String columnAlias,
			                                  TableTypes tableType,
			                                  ColumnType columnType,
			                                  boolean substitution) {
		
		// generate id and create new column
		String id = "";
		Integer actualIndex = rpi.getActualIndex();
		if (actualIndex == -1) {
			id = columnIdGenerator.getCoulmnId(tableType);
		} else {
			id = actualIndex.toString();
		}
		
		Column column = columnModelController.createColumn();
		
		//Call setters
		columnModelController.setColumnId(column, id);
		columnModelController.setColumnName(column, propertyName);
		columnModelController.setColumnAlias(column, columnAlias);
		columnModelController.setColumnType(column, columnType);
		columnModelController.setSubstitution(column, substitution);
		
		if ((ColumnType.CONDITION == columnType) || (ColumnType.ACTION == columnType)) {
			String mappedPropertyName = propertyName ;//processPropertyName(propertyName);
			//Again check if substitution
			boolean isSubstitution = DecisionTableCoreUtil.isVarString(mappedPropertyName, columnType);
			if (isSubstitution) {
				int idx = DTLanguageUtil.firstIndexOfOp(mappedPropertyName);
				if (idx != -1) {
					mappedPropertyName = mappedPropertyName.substring(0, idx).trim();
				}
			}
			PropertyPathWithDataType propertyPathWithDataType = propPathAndDataTypeMap.get(mappedPropertyName);
			String propertyPath = propertyPathWithDataType.getPropertyPath();

			columnModelController.setPropertyPath(column, propertyPath);
			//Also set property data type
			int dataType = propertyPathWithDataType.getPropertyDataType();
			columnModelController.setPropertyType(column, dataType);
		}
		rpi.setColumn(column);
		columnModelController.addColumn(columns, column);
	}

	private String getPropertyName(RuleParticipantInfo rpi,
			List<RuleParticipantInfo> trvList) {
		if (trvList.contains(rpi)) {
			return rpi.getExpression();
		}

		return null;
	}
	
	private void setCurrentid(final TableRule rule, String id, TableTypes tableType) {
		if (id == null) {
			catchNoIdError();
			return;
		}
		// first parse it to double
		if(ruleIdCache.contains(id)){
			catchDuplicateIdError(tableType.getValue(), id);
			return;
		}
		try {
			double doubleValue = Double.parseDouble(id);
			int intValue = (int) doubleValue;
			if (intValue == doubleValue) {
				if (TableTypes.DECISION_TABLE == tableType) {
					if (intValue > currentDTRuleId) {
						currentDTRuleId = intValue;

					}
				} else {
					if (intValue > currentETRuleId) {
						currentETRuleId = intValue;

					}
				}
				rule.setId("" + intValue);
			} else {
				rule.setId(id);
			}
			ruleIdCache.add(id);
		} catch (Exception e) {
			// if exception then format is not a number
			rule.setId(id);
			ruleIdCache.add(id);
		}

	}
	
	private void catchDuplicateIdError(String source, String currentId) {		
		// it's a duplicate ID
		ValidationError vldError = new ValidationErrorImpl(
				Excel_VALIDATION_ERROR_CODE, "Duplicate Id :" + currentId + " in " + source,
				"DecsionTable");
		excelVldErrorCollection.add(vldError);

	}
	
	private void catchNoIdError() {		
			ValidationError vldError = new ValidationErrorImpl(
					Excel_VALIDATION_ERROR_CODE, "No Id available for rule." ,
					"DecsionTable");
			excelVldErrorCollection.add(vldError);		
	}

	
	private void catchNoValidPropertyError(String property) {		
		ValidationError vldError = new ValidationErrorImpl(
				Excel_VALIDATION_ERROR_CODE, "Not a valid property :" + property,
				"DecsionTable");
		excelVldErrorCollection.add(vldError);		
	}
	
	private void catchDupicatePropertyError(String property, ColumnType columnType, TableTypes tableType) {		
		ValidationError vldError = new ValidationErrorImpl(
				Excel_VALIDATION_ERROR_CODE, "Duplicate property in "+ tableType + " in "+ columnType + " : " + property,
				"DecsionTable");
		excelVldErrorCollection.add(vldError);		
	}
	
	private void addColumn(Columns columns, 
			               TableRuleVariable trv,
			               String trvType, 
			               String colName,
			               Map<String, String> colNameIdMap,
			               TableTypes tableType) {
		
		String colId = colNameIdMap.get(trvType + colName);
		if (colId != null) {
			trv.setColId(colId);
		} else {
			// colId = UUID.randomUUID().toString();
			colId = columnIdGenerator.getCoulmnId(tableType);
			Column column = columnModelController.createColumn();
			columnModelController.setColumnId(column, colId);
			columnModelController.setColumnName(column, colName);
			
			PropertyPathWithDataType propertyPathWithDataType = propPathAndDataTypeMap.get(colName);
			if (propertyPathWithDataType != null) {
				String propertyPath = propertyPathWithDataType.getPropertyPath();
				columnModelController.setPropertyPath(column, propertyPath);
				if (DTConstants.PREFIX_IN.equals(trvType)) {
					columnModelController.setColumnType(column, ColumnType.CONDITION);
				} else {
					columnModelController.setColumnType(column, ColumnType.ACTION);
				}
				columnModelController.addColumn(columns, column);
				colNameIdMap.put(trvType + colName, colId);
				trv.setColId(colId);
			}
		}
	}

	private void addColumn(Columns columns, 
			               TableRuleVariable trv,
			               String trvType, 
			               List<String> trvIDList,
			               List<String> usedCustTrvIdList, 
			               TableTypes tableType) {
		
		if (trvIDList.isEmpty()) {
			// generate unique id
			// String colId = UUID.randomUUID().toString();
			
			String colId = columnIdGenerator.getCoulmnId(tableType);

			Column column = columnModelController.createColumn();
			columnModelController.setColumnId(column, colId);
			String columnName;
			if (DTConstants.PREFIX_IN.equals(trvType)) {
				columnName = 
					DTConstants.CUSTOM_CONDITION_PREFIX + ++customConditionCounter;
				columnModelController.setColumnType(column, ColumnType.CUSTOM_CONDITION);
			} else {
				columnName = 
					DTConstants.CUSTOM_ACTION_PREFIX + ++customActionCounter;
				columnModelController.setColumnType(column, ColumnType.CUSTOM_ACTION);
			}
			columnModelController.setColumnName(column, columnName);
			columnModelController.addColumn(columns, column);
			trvIDList.add(colId);
			usedCustTrvIdList.add(colId);
			trv.setColId(colId);
		} else {
			String colId = null;
			for (String id : trvIDList) {
				if (!usedCustTrvIdList.contains(id)) {
					colId = id;
					break;
				}
			}
			if (colId != null) {
				usedCustTrvIdList.add(colId);
			} else {
				// create a new column
				// generate unique id
				// colId = UUID.randomUUID().toString();
				colId = columnIdGenerator.getCoulmnId(tableType);
				Column column = columnModelController.createColumn();
				columnModelController.setColumnId(column, colId);
				columnModelController.setColumnName(column, "");
				if (DTConstants.PREFIX_IN.equals(trvType)) {
					columnModelController.setColumnType(column, ColumnType.CUSTOM_CONDITION);
				} else {
					columnModelController.setColumnType(column, ColumnType.CUSTOM_ACTION);
				}
				columnModelController.addColumn(columns, column);
				trvIDList.add(colId);
				usedCustTrvIdList.add(colId);
			}
			trv.setColId(colId);
		}
	}

	private String parseExcelCellValue(String body, StringBuffer expression,
			TableTypes tableType) {
		String source = null;
		if (tableType == TableTypes.DECISION_TABLE) {
			source = DECISION_TABLE_SECTION;
		} else {
			source = EXCEPTION_TABLE_SECTION;
		}
		// get all expressions of body
		StringTokenizer st = new StringTokenizer(body, ";");
		StringBuffer sb = new StringBuffer("");
		int count = 0;
		String validAliasForColumn = null;
		String path = null;
		while (st.hasMoreTokens()) {
			String expToken = st.nextToken();
			if (expToken != null && !(expToken.trim().equals(""))) {
				expToken = expToken.trim();
				//String operator = DecisionTableCoreUtil.getOperator(expToken);
				String operator = getOperator(expToken);
				String actualValue = null;
				String alias = null;
				if (operator != null) {
					if (expToken.indexOf(operator) == -1) {
						ValidationError vldError = new ValidationErrorImpl(
								Excel_VALIDATION_ERROR_CODE, expToken
										+ "  is not a valid Expression ",
								source);
						if (!excelVldErrorCollection.contains(vldError)) {
							excelVldErrorCollection.add(vldError);
						}
						continue;
					}
					alias = expToken.substring(0, expToken.indexOf(operator));

					if (validAliasForColumn != null) {
						if (alias != null && !alias.equals(validAliasForColumn)) {
							ValidationError vldError = new ValidationErrorImpl(
									Excel_VALIDATION_ERROR_CODE, expToken
											+ " only " + validAliasForColumn
											+ " belongs to this column", source);
							if (!excelVldErrorCollection.contains(vldError)) {
								excelVldErrorCollection.add(vldError);
							}
							continue;
						}
					}

					actualValue = DecisionTableCoreUtil.getActualValue(operator,
							expToken);

					if (template == null || !(template instanceof RuleFunction)) {
						path = aliasPathMap.get(alias);
					} else {
						actualValue = actualValue != null ? actualValue.trim()
								: actualValue;

						// put actual value in string buffer
						String expVal = "";
						if (!operator.equals("=") && !operator.equals("==")) {
							expVal = operator + actualValue;
						} else {
							expVal = actualValue;
						}
						if (count == 0) {
							sb.append(expVal);
						} else {
							sb.append(";" + expVal);
						}
						alias = alias != null ? alias.trim() : alias;
						PropertyPathWithDataType propertyPathWithDataType = propPathAndDataTypeMap.get(alias);
						path = propertyPathWithDataType.getPropertyPath();

						if (path == null) {

							ValidationError vldError = new ValidationErrorImpl(
									Excel_VALIDATION_ERROR_CODE,
									alias
											+ " is not part of argument of selected Virtual Rule Function ",
									source);
							if (!excelVldErrorCollection.contains(vldError)) {
								excelVldErrorCollection.add(vldError);
							}
							continue;
						} else {
							validAliasForColumn = alias;
						}
						/*
						 * if (tableType == DT_TYPE) { if
						 * (!FieldMapUtil.containsKey(dtPropPathMap, alias)) {
						 * FieldMapUtil.put(dtPropPathMap, alias, path); }
						 * 
						 * if (!(dtPropPathMap.containsKey(alias))) {
						 * dtPropPathMap.put(alias, path); } } else if
						 * (tableType == ET_TYPE) {
						 * 
						 * if (!(etPropPathMap.containsKey(alias))) {
						 * etPropPathMap.put(alias, path); }
						 * 
						 * if (!FieldMapUtil.containsKey(etPropPathMap, alias)) {
						 * FieldMapUtil.put(etPropPathMap, alias, path); } }
						 */
					}
				} else {
					ValidationError vldError = new ValidationErrorImpl(
							Excel_VALIDATION_ERROR_CODE,
							expToken
									+ " should be part of custom condition or action",
							source);
					if (!excelVldErrorCollection.contains(vldError)) {
						excelVldErrorCollection.add(vldError);
					}
					continue;
				}

			}
			count++;
		}
		String columnName = null;
		if (validAliasForColumn != null && path != null) {
			// now alias and path is removed from expression
			// expression.setAlias(validAliasForColumn);
			// expression.setPath(path);
			columnName = validAliasForColumn;
			validAliasForColumn = null;
		}
		
		expression.append(sb.toString());
		return columnName;
	}
	private String getOperator(String cellValue){		
		int leastIndex = Integer.MAX_VALUE;
		String op = null;
		for (String _op : DTLanguageUtil.OPERATORS) {	
			int opIndex = cellValue.indexOf(_op);
			if (opIndex != -1 && (opIndex < leastIndex || op == null)) {
				op = _op;
				leastIndex = opIndex;
			}		
	
		}
		
		if(op != null) return op;
		/*
		if ((cellValue.indexOf("(") != -1) && (cellValue.indexOf(")") != -1)) {
			return null;
		}
		*/
	
		return "=";
	
	}

	private void addDeclaration(String path, String alias, String propertyType,
			String graphicalAliasPath, String domainType, String domainValues) {

		// System.setProperty("javax.xml.parsers.SAXParserFactory",
		// "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");
		// System.out.println("Adding declaration: " + path + ", " + alias + ",
		// "
		// + propertyType + ", " + graphicalAliasPath + " , " + domainType
		// + " ," + domainValues);
		alias = alias != null ? alias.trim() : alias;
		path = path != null ? path.trim() : path;
		Argument arg = DtmodelFactory.eINSTANCE.createArgument();
		arg.setDirection(propertyType);

		ArgumentProperty argProperty = DtmodelFactory.eINSTANCE
				.createArgumentProperty();

		argProperty.setAlias(alias);
		// argProperty.setDomainOverriden(true);
		argProperty.setType(domainType);
		argProperty.setPath(path);
		argProperty.setGraphicalPath(graphicalAliasPath);
		argProperty.setResourceType(ResourceType.PROPERTY);

		if (alias != null && path != null) {
			aliasPathMap.put(alias, path);
		}
		Domain domain = DomainModelFactory.eINSTANCE.createDomain();
		domain.setType(domainType);
		List<DomainEntry> domainEntryList = domain.getDomainEntry();
		DomainEntry domainEntry = null;
		EntryValue entryValue = null;

		if (domainValues != null) {

			String[] tokens = parseDomainValues(domainValues);

			List<TokenInfo> tokenInfoList = getTokenInfoList(tokens);
			for (TokenInfo tokenInfo : tokenInfoList) {
				domainEntry = DomainModelFactory.eINSTANCE.createDomainEntry();
				entryValue = DomainModelFactory.eINSTANCE.createRangeInfo();
				String leftComponent = tokenInfo.getLeftComponent();
				String rightComponent = tokenInfo.getRightComponent();

				String leftComponentValue = getValue(leftComponent);
				String rightComponentValue = getValue(rightComponent);

				if (leftComponent != null && leftComponent.startsWith("<")) {

					if (rightComponent != null) {
						if (rightComponent.startsWith(">")) {

							if (compareValues(leftComponent, rightComponent,
									domainType)) {
								((RangeInfo) entryValue)
										.setLowerBound(rightComponentValue);
								((RangeInfo) entryValue)
										.setUpperBound(leftComponentValue);
								if (rightComponent.startsWith(">=")) {
									((RangeInfo) entryValue)
											.setLowerBoundIncluded(true);
								}
								if (leftComponent.startsWith("<=")) {
									((RangeInfo) entryValue)
											.setUpperBoundIncluded(true);
								}
							} else {
								System.out
										.println("********Range is not valid ::enter a valid range*****");
								continue;
							}
						} else {
							System.out
									.println("******Range is not valid ::enter a valid range*******");
							continue;
						}
					} else {
						((RangeInfo) entryValue).setLowerBound("Undefined");
						((RangeInfo) entryValue)
								.setUpperBound(leftComponentValue);

						if (leftComponent.startsWith("<=")) {
							((RangeInfo) entryValue)
									.setUpperBoundIncluded(true);
						}

					}

					entryValue.setMultiple(true);
					domainEntry.setEntryValue(entryValue);
				} else if (leftComponent != null
						&& leftComponent.startsWith(">")) {
					if (rightComponent != null) {
						if (rightComponent.startsWith("<")) {
							if (compareValues(rightComponent, leftComponent,
									domainType)) {
								((RangeInfo) entryValue)
										.setLowerBound(leftComponentValue);
								((RangeInfo) entryValue)
										.setUpperBound(rightComponentValue);
								if (rightComponent.startsWith("<=")) {
									((RangeInfo) entryValue)
											.setUpperBoundIncluded(true);
								}
								if (leftComponent.startsWith(">=")) {
									((RangeInfo) entryValue)
											.setLowerBoundIncluded(true);
								}
							} else {
								System.out
										.println("*******Range is not valid ::enter a valid range");
								continue;
							}
						} else {
							System.out
									.println("*******Range is not valid ::enter a valid range");
							continue;
						}
					} else {
						((RangeInfo) entryValue)
								.setLowerBound(leftComponentValue);
						((RangeInfo) entryValue).setUpperBound("Undefined");

						if (leftComponent.startsWith(">=")) {
							((RangeInfo) entryValue)
									.setLowerBoundIncluded(true);
						}
					}
					entryValue.setMultiple(true);
					domainEntry.setEntryValue(entryValue);
				} else {
					EntryValue singleEntryValue = DomainModelFactory.eINSTANCE
							.createSingleValue();
					singleEntryValue.setMultiple(false);
					singleEntryValue.setValue(leftComponent);

					domainEntry.setEntryValue(singleEntryValue);

				}
				domainEntryList.add(domainEntry);

			}

		}

		// argProperty.setDomain(domain);

		arg.setProperty(argProperty);
		EList<Argument> argList = tableEModel.getArgument();
		// dt.getArgument().add(arg);

		argList.add(arg);

	}

	private String getValue(String expression) {
		if (expression != null) {
			if (expression.indexOf(">=") != -1
					|| expression.indexOf("<=") != -1) {
				return expression.substring(2);
			} else if (expression.indexOf(">") != -1
					|| expression.indexOf("<") != -1) {
				return expression.substring(1);
			} else
				return expression;
		}
		return null;
	}

	private boolean compareValues(String Val1, String Val2, String dataType) {

		String value1 = getValue(Val1);
		String value2 = getValue(Val2);
		int intValue3 = 0;
		int intValue4 = 0;
		double doubleValue3 = 0;
		double doubleValue4 = 0;
		if (dataType.equalsIgnoreCase("integer")) {
			intValue3 = Integer.parseInt(value1);
			intValue4 = Integer.parseInt(value2);

			if (value1.equals(value2)
					&& ((Val1.startsWith("<=") && Val2.startsWith(">=")) || (Val1
							.startsWith(">=") && Val2.startsWith("<=")))) {
				return true;

			} else if (intValue3 > intValue4) {
				return true;
			}
		} else if (dataType.equalsIgnoreCase("double")
				|| dataType.equalsIgnoreCase("Float")
				|| dataType.equalsIgnoreCase("Decimal")) {
			doubleValue3 = Double.parseDouble(value1);
			doubleValue4 = Double.parseDouble(value2);
			if (value1.equals(value2)
					&& ((Val1.startsWith("<=") && Val2.startsWith(">=")) || (Val1
							.startsWith(">=") && Val2.startsWith("<=")))) {
				return true;

			} else if (doubleValue3 > doubleValue4) {
				return true;
			}

		}

		return false;

	}

	private List<TokenInfo> getTokenInfoList(String[] tokens) {
		if (tokens != null) {
			List<TokenInfo> tokenInfoList = new ArrayList<TokenInfo>();

			for (String token : tokens) {
				TokenInfo tokenInfo = populateTokenInfo(token);
				tokenInfoList.add(tokenInfo);

			}
			return tokenInfoList;
		}

		return null;
	}

	private TokenInfo populateTokenInfo(String token) {
		if (token != null) {
			token = token.trim();
			TokenInfo tokenInfo = null;
			if (token.indexOf("&") != -1) {
				String leftComponent = token.substring(0, token.indexOf('&'));
				if (leftComponent != null)
					leftComponent = leftComponent.trim();
				String rightComponent = token
						.substring(token.lastIndexOf('&') + 1);
				if (rightComponent != null)
					rightComponent = rightComponent.trim();

				tokenInfo = new TokenInfo(leftComponent, rightComponent);

			} else {
				tokenInfo = new TokenInfo(token, null);
			}
			return tokenInfo;
		}

		return null;
	}

	private String[] parseDomainValues(String domainValues) {
		StringTokenizer st = new StringTokenizer(domainValues, ";");
		String[] tokens = new String[st.countTokens()];
		int tokenCount = 0;
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (token != null && !token.equals(""))
				tokens[tokenCount++] = token;
		}

		return tokens;
	}

	/**
	 * Formats a number or date cell, be that a real number, or the answer to a
	 * formula
	 */
	private String formatNumberDateCell(CellValueRecordInterface cell,
			double value, boolean parseInt) {
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
				DateFormat df = new SimpleDateFormat(DTConstants.DATE_TIME_PATTERN);
				String formattedDate = df.format(d);
				//return df.format(d);
				return formattedDate;
			} else {
				if (format == "General") {
					// Some sort of wierd default
					int intVal = (int)value;
					if (value == intVal){
						return "" + intVal;
						
					}
					return Double.toString(value);
				}

				// Format as a number
				DecimalFormat df = new DecimalFormat(format);
				if (parseInt)
					return DecimalFormat.getIntegerInstance().format(value);
				else
					return df.format(value);
			}
		}
	}

	/*
	 * public DecisionTableEModelWrapper getDecisionTableEModelWrapper() {
	 * 
	 * return new DecisionTableEModelWrapper(decisionTableEModel,
	 * exceptionTableEModel); }
	 */

	class TokenInfo {
		String leftComponent;
		String rightComponent;

		public TokenInfo(String operator, String value) {
			// TODO Auto-generated constructor stub
			this.leftComponent = operator;
			this.rightComponent = value;
		}

		public String getLeftComponent() {
			return leftComponent;
		}

		public void setLeftComponent(String leftComponent) {
			this.leftComponent = leftComponent;
		}

		public String getRightComponent() {
			return rightComponent;
		}

		public void setRightComponent(String rightComponent) {
			this.rightComponent = rightComponent;
		}

	}

	public Table getTableEModel() {
		return tableEModel;
	}

	private static class RuleParticipantInfo {
		int index;
		int ruleParticipantType;
		String expression;
		Column column;
		int actualIndex = -1;
		String comment;
		boolean isEnabled;
		
		public RuleParticipantInfo(int index, int ruleParticipantType, int actualIndex) {
			this.index = index;
			this.ruleParticipantType = ruleParticipantType;
			this.actualIndex = actualIndex;
		}
		
		

		public int getIndex() {
			return index;
		}

		public int getRuleParticipantType() {
			return ruleParticipantType;
		}

		public String getExpression() {
			return expression;
		}

		public void setExpression(String expression) {
			this.expression = expression;
		}

		public Column getColumn() {
			return column;
		}

		public void setColumn(Column column) {
			this.column = column;
		}

		public int getActualIndex(){
			return actualIndex;
		}
		
		
		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		public boolean isEnabled() {
			return isEnabled;
		}

		public void setEnabled(boolean isEnabled) {
			this.isEnabled = isEnabled;
		}
		
		
	}

	private interface ExcelParser {
		/**
		 * it parses Excel DT section and ET section and populate EMF model
		 * 
		 * @param ruleSet
		 * @param tableType
		 */
		void populateTable(TableRuleSet ruleSet, TableTypes tableType) throws ExcelImportException;
	}

	/**
	 * Version 1 Excel parser Implementation Format Example : person.Gender =
	 * male
	 * 
	 * @author rmishra
	 * 
	 */
	private class ExcelVersion1Parser implements ExcelParser {

		public void populateTable(TableRuleSet ruleSet, TableTypes tableType) {
			if (conditionList.size() == 0 && actionList.size() == 0) {
				return;
			}
			// add columns
			Columns columns = null;
			if (ruleSet.getColumns() == null) {
				columns = DtmodelFactory.eINSTANCE.createColumns();
				ruleSet.setColumns(columns);
			} else {
				columns = ruleSet.getColumns();
			}
			List<TableRule> ruleList = ruleSet.getRule();
			TableRule rule = DtmodelFactory.eINSTANCE.createTableRule();
			MetaData metaData = DtmodelFactory.eINSTANCE.createMetaData();
			Property property = null;
			if (description != null) {
				property = DtmodelFactory.eINSTANCE.createProperty();
				metaData.getProp().add(property);
				property.setName("Description");
				property.setType("String");
				property.setValue(description);
			}
			if (priority != null) {
				property = DtmodelFactory.eINSTANCE.createProperty();
				metaData.getProp().add(property);
				property.setName("Priority");
				property.setType("Integer");
				property.setValue(priority);
			}
			if (id != null) {
				// rule.setId(id);
				// added to set lat integer id encountered
				setCurrentid(rule, id, tableType);
			}
			rule.setMd(metaData);
			List<TableRuleVariable> ruleConditionList = rule.getCondition();
			//Expression expression = null;
			String expression = null;
			String body = null;
			List<String> usedCustCondIdList = new ArrayList<String>();
			for (Iterator<RuleParticipantInfo> it = conditionList.iterator(); it
					.hasNext();) {
				RuleParticipantInfo rpi = it.next();
				int rpType = rpi.getRuleParticipantType();
				body = rpi.getExpression();
				TableRuleVariable ruleCondition = DtmodelFactory.eINSTANCE
						.createTableRuleVariable();

				//expression = DtmodelFactory.eINSTANCE.createExpression();
				body = body != null ? body.trim() : body;
				if (body != null && (!(body.trim().equals("")))) {
					if (RULE_PARTICIPANT_CUSTOM == rpType) {
						expression = body;
						if (tableType == TableTypes.DECISION_TABLE) {
							addColumn(columns, ruleCondition,
									DTConstants.PREFIX_IN,
									dtCustomConditionIdList,
									usedCustCondIdList,
									TableTypes.DECISION_TABLE);
						} else {
							addColumn(columns, ruleCondition,
									DTConstants.PREFIX_IN,
									etCustomConditionIdList,
									usedCustCondIdList,
									TableTypes.EXCEPTION_TABLE);
						}
					} else {
						// get all expressions of body
						StringBuffer expr = new StringBuffer();
						String columnName = parseExcelCellValue(body, expr, tableType);
						expression = expr.toString();
						if (tableType == TableTypes.DECISION_TABLE) {
							addColumn(columns, ruleCondition,
									  DTConstants.PREFIX_IN,
									  columnName, dtColNameIdMap,
									  TableTypes.DECISION_TABLE);
						} else {
							addColumn(columns, ruleCondition,
									  DTConstants.PREFIX_IN,
									  columnName, etColNameIdMap,
									  TableTypes.EXCEPTION_TABLE);
						}
					}
					String columnId = ruleCondition.getColId();
					String ruleId = rule.getId();
					String rvId = ruleId + "_" + columnId;
					ruleCondition.setExpr(expression);
					ruleCondition.setComment(rpi.getComment());
					ruleCondition.setEnabled(rpi.isEnabled);
					//ruleCondition.setModified(true);
					// ruleCondition.setId(UUID.randomUUID().toString());
					ruleCondition.setId(rvId);
					ruleConditionList.add(ruleCondition);
					
				}
			}
			List<TableRuleVariable> ruleActionList = rule.getAction();
			List<String> usedCustActionIdList = new ArrayList<String>();
			for (Iterator<RuleParticipantInfo> it = actionList.iterator(); it
					.hasNext();) {
				RuleParticipantInfo rpi = it.next();
				body = rpi.getExpression();
				int rpType = rpi.getRuleParticipantType();
				TableRuleVariable ruleAction = DtmodelFactory.eINSTANCE
						.createTableRuleVariable();

				//expression = DtmodelFactory.eINSTANCE.createExpression();
				body = body != null ? body.trim() : body;
				if (body != null && (!(body.trim().equals("")))) {
					if (RULE_PARTICIPANT_CUSTOM == rpType) {
						expression = body;
						if (tableType == TableTypes.DECISION_TABLE) {
							addColumn(columns, ruleAction,
									DTConstants.PREFIX_OUT,
									dtCustomActionIdList, usedCustActionIdList,
									tableType);
						} else {
							addColumn(columns, ruleAction,
									DTConstants.PREFIX_OUT,
									etCustomActionIdList, usedCustActionIdList,
									tableType);
						}
					} else {
						StringBuffer expr = new StringBuffer();
						String columnName = parseExcelCellValue(body, expr, tableType);
						expression = expr.toString();
						if (tableType == TableTypes.DECISION_TABLE) {
							addColumn(columns, ruleAction,
									  DTConstants.PREFIX_OUT,
									  columnName, dtColNameIdMap,
									  tableType);
						} else {
							addColumn(columns, ruleAction,
									  DTConstants.PREFIX_OUT,
									  columnName, etColNameIdMap,
									  tableType);
						}
					}

					String columnId = ruleAction.getColId();
					String ruleId = rule.getId();
					String rvId = ruleId + "_" + columnId;

					ruleAction.setExpr(expression);
					ruleAction.setComment(rpi.getComment());
					ruleAction.setEnabled(rpi.isEnabled());
					//ruleAction.setModified(true);
					// ruleAction.setId(UUID.randomUUID().toString());
					ruleAction.setId(rvId);
					ruleActionList.add(ruleAction);
					
				}
				
			}
			ruleList.add(rule);
			usedCustActionIdList.clear();
			usedCustCondIdList.clear();
			// after adding one rule reset id , description and priority
			id = null;
			priority = null;
			description = null;
			

		}
	}

	/**
	 * Version 2 Excel parser implementation Condition/Action header row is
	 * added Example : person.Name person.Gender person.Income name1 male 1000
	 * 
	 * @author rmishra
	 * 
	 */
	private class ExcelVersion2Parser implements ExcelParser {
		public void populateTable(TableRuleSet ruleSet, TableTypes tableType) throws ExcelImportException {
			if (conditionList.size() == 0 && actionList.size() == 0) {
				return;
			}
			// add columns
			Columns columns = null;
			if (ruleSet.getColumns() == null) {
				columns = DtmodelFactory.eINSTANCE.createColumns();
				ruleSet.setColumns(columns);
			} else {
				columns = ruleSet.getColumns();
			}

			// check whether DT/ET header row is found or not

			if (TableTypes.DECISION_TABLE == tableType) {
				// if DT has only custom conditions and actions
				if (foundRegularDTCondiiton) {
					if (!foundDTHeaderRow) {
						populateColumnHeaderInformation(tableType, columns);
						// check if there is any validation error
//						checkValidationError();
						foundDTHeaderRow = true;
						return;
					}
				} else {
					if (!foundDTHeaderRow) {
						populateCustomColumnHeaderInformation(tableType,
								columns);
						foundDTHeaderRow = true;
						return;
					}
				}

			} else {
				// if DT has only custom conditions and actions
				if (foundRegularETCondiiton) {
					if (!foundETHeaderRow) {
						populateColumnHeaderInformation(tableType, columns);
						// check if there is any validation error
//						checkValidationError();
						foundETHeaderRow = true;
						return;
					}
				} else {
					if (!foundETHeaderRow) {
						populateCustomColumnHeaderInformation(tableType,
								columns);
						foundETHeaderRow = true;
						return;
					}
				}
			}

			List<TableRule> ruleList = ruleSet.getRule();
			TableRule rule = DtmodelFactory.eINSTANCE.createTableRule();
			MetaData metaData = DtmodelFactory.eINSTANCE.createMetaData();
			Property property = null;
			if (description != null) {
				property = DtmodelFactory.eINSTANCE.createProperty();
				metaData.getProp().add(property);
				property.setName("Description");
				property.setType("String");
				property.setValue(description);
			}
			if (priority != null) {
				property = DtmodelFactory.eINSTANCE.createProperty();
				metaData.getProp().add(property);
				property.setName("Priority");
				property.setType("Integer");
				property.setValue(priority);
			}
			
			// rule.setId(id);
			// added to set lat integer id encountered
			setCurrentid(rule, id, tableType);
			
			rule.setMd(metaData);
			List<TableRuleVariable> ruleConditionList = rule.getCondition();
			//Expression expression = null;
			String expression = null;
			String body = null;
			
			List<String> usedCustCondIdList = new ArrayList<String>();
			for (Iterator<RuleParticipantInfo> it = conditionList.iterator(); it
					.hasNext();) {
				RuleParticipantInfo rpi = it.next();
				body = rpi.getExpression();
				TableRuleVariable ruleCondition = DtmodelFactory.eINSTANCE
						.createTableRuleVariable();
				//expression = DtmodelFactory.eINSTANCE.createExpression();
				body = body != null ? body.trim() : body;
				if (body != null && (!(body.trim().equals("")))) {
					if (body.startsWith("==")) {
						body = body.substring(2);
					} else if (body.startsWith("=")) {
						body = body.substring(1);
					}
					Column column = rpi.getColumn();
					if(column != null){
						if (column.isSubstitution()) {
							expression = DecisionTableCoreUtil.getFormattedString(column, body);
						} else {
							expression = body;
						}
						ruleCondition.setColId(column.getId());
						String columnId = ruleCondition.getColId();
						String ruleId = rule.getId();
						String rvId = ruleId + "_" + columnId;
						
						ruleCondition.setExpr(expression);
						ruleCondition.setComment(rpi.getComment());
						ruleCondition.setEnabled(rpi.isEnabled());
						//ruleCondition.setModified(true);
						// ruleCondition.setId(UUID.randomUUID().toString());
						ruleCondition.setId(rvId);
						ruleConditionList.add(ruleCondition);
					}
				}
			}
			List<TableRuleVariable> ruleActionList = rule.getAction();
			List<String> usedCustActionIdList = new ArrayList<String>();
			for (Iterator<RuleParticipantInfo> it = actionList.iterator(); it
					.hasNext();) {
				RuleParticipantInfo rpi = it.next();
				body = rpi.getExpression();
				// int rpType = rpi.getRuleParticipantType();
				TableRuleVariable ruleAction = DtmodelFactory.eINSTANCE
						.createTableRuleVariable();
				//expression = DtmodelFactory.eINSTANCE.createExpression();
				body = body != null ? body.trim() : body;
				if (body != null && (!(body.trim().equals("")))) {
					if (body.startsWith("==")) {
						body = body.substring(2);
					} else if (body.startsWith("=")) {
						body = body.substring(1);
					}
					Column column = rpi.getColumn();
					if(column != null){
						if (column.isSubstitution()) {
							//TODO Fix this
							//expression.setVarString(body);
							expression = DecisionTableCoreUtil.getFormattedString(column, body);
						} else {
							expression = body;
						}
						ruleAction.setColId(column.getId());
						String columnId = ruleAction.getColId();
						String ruleId = rule.getId();
						String rvId = ruleId + "_" + columnId;
						
						ruleAction.setExpr(expression);
						ruleAction.setComment(rpi.getComment());
						ruleAction.setEnabled(rpi.isEnabled());
						//ruleAction.setModified(true);
						// ruleAction.setId(UUID.randomUUID().toString());
						ruleAction.setId(rvId);
						ruleActionList.add(ruleAction);
					}
				}
			}
			ruleList.add(rule);
			usedCustActionIdList.clear();
			usedCustCondIdList.clear();
			// after adding one rule reset id , description and priority
			id = null;
			priority = null;
			description = null;

		}
	}

	public Collection<ValidationError> getExcelVldErrorCollection() {
		if(!isDTAvailable){
			ValidationError vldError = new ValidationErrorImpl(
					Excel_VALIDATION_ERROR_CODE, "No Decision Table available in the given sheet.",
					"DecisionTable");
			excelVldErrorCollection.add(vldError);
		}
		return excelVldErrorCollection;
	}

	public DecisionTableColumnIdGenerator getColumnIdGenerator() {
		return columnIdGenerator;
	}

	public int getCurrentDTRuleId() {
		return currentDTRuleId;
	}

	public int getCurrentETRuleId() {
		return currentETRuleId;
	}

	

	/**
	 * @return the errorReturnCode
	 */
	public int getErrorReturnCode() {
		return this.errorReturnCode;
	}

}
