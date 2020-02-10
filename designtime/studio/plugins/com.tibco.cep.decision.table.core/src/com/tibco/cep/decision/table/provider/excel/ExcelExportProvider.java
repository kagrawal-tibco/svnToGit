package com.tibco.cep.decision.table.provider.excel;

import static com.tibco.cep.decision.table.provider.excel.ExcelProviderConstants.COLUMN_ALIAS_ID;
import static com.tibco.cep.decision.table.provider.excel.ExcelProviderConstants.EXCEL_VERSION;
import static com.tibco.cep.decision.table.provider.excel.ExcelProviderConstants.HEADER_ACTION_COLUMN;
import static com.tibco.cep.decision.table.provider.excel.ExcelProviderConstants.HEADER_CONDITION_COLUMN;
import static com.tibco.cep.decision.table.provider.excel.ExcelProviderConstants.HEADER_CUSTOM_ACTION_COLUMN;
import static com.tibco.cep.decision.table.provider.excel.ExcelProviderConstants.HEADER_CUSTOM_CONDITION_COLUMN;
import static com.tibco.cep.decision.table.provider.excel.ExcelProviderConstants.HEADER_DECISION_TABLE;
import static com.tibco.cep.decision.table.provider.excel.ExcelProviderConstants.HEADER_DECLARATIONS;
import static com.tibco.cep.decision.table.provider.excel.ExcelProviderConstants.HEADER_DECLARATION_ALIAS;
import static com.tibco.cep.decision.table.provider.excel.ExcelProviderConstants.HEADER_DECLARATION_PATH;
import static com.tibco.cep.decision.table.provider.excel.ExcelProviderConstants.HEADER_DECLARATION_PROPERTY;
import static com.tibco.cep.decision.table.provider.excel.ExcelProviderConstants.HEADER_DESCRIPTION_COLUMN;
import static com.tibco.cep.decision.table.provider.excel.ExcelProviderConstants.HEADER_EXCEPTION_TABLE;
import static com.tibco.cep.decision.table.provider.excel.ExcelProviderConstants.HEADER_GENERAL_PROPERTIES;
import static com.tibco.cep.decision.table.provider.excel.ExcelProviderConstants.HEADER_ID_COLUMN;
import static com.tibco.cep.decision.table.provider.excel.ExcelProviderConstants.HEADER_PRIORITY_COLUMN;
import static com.tibco.cep.decision.table.provider.excel.ExcelProviderConstants.INDEX_ALIAS;
import static com.tibco.cep.decision.table.provider.excel.ExcelProviderConstants.INDEX_PATH;
import static com.tibco.cep.decision.table.provider.excel.ExcelProviderConstants.INDEX_PROPERTY;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.decision.table.language.DTLanguageUtil;
import com.tibco.cep.decision.table.metadata.DecisionTableMetadataFeature;
import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decision.table.model.dtmodel.Property;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.utils.Messages;
import com.tibco.cep.decisionproject.acl.ValidationError;
import com.tibco.cep.decisionproject.acl.ValidationErrorImpl;
import com.tibco.cep.decisionproject.ontology.OntologyFactory;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.decisionproject.util.DTConstants;
import com.tibco.cep.decisionproject.util.DTDomainUtil;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.studio.common.legacy.adapters.IModelTransformer;
import com.tibco.cep.studio.common.legacy.adapters.RuleFunctionModelTransformer;
import com.tibco.cep.studio.core.domain.exportHandler.DomainExportData;
import com.tibco.cep.studio.core.domain.exportHandler.DomainModelExcelExportHandler;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.decision.table.core.DecisionTableCorePlugin;


public class ExcelExportProvider {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

	private String filename;
	
	private String sheetname;
	
	private Table tableEModel;
	
	private boolean useColumnAlias = false;
	
	private HSSFWorkbook workbook;
	
	private Collection<ValidationError> excelVldErrorCollection = new ArrayList<ValidationError>();
	
	/**
	 * The project which has the table to be exported
	 */
	private String projectName;

	private HSSFFont sectionHeaderFont;
	
	private HSSFFont columnHeaderFont;

	private HSSFPatriarch patr;

	private int currentRowNo = 0;

	private Map<String, Short> dtIDColumnMap;
	
	private Map<String, Short> etIDColumnMap;
	
	private short dtPriorityColumn = -1;
	
	private short etPriorityColumn = -1;
	
	private short dtDescriptionColumn = -1;
	
	private short etDescriptionColumn = -1;
	
	private final static int Excel_VALIDATION_ERROR_CODE = 0x500;
	
	public ExcelExportProvider(String filename, String projectName) {
		this(projectName, filename, null, null);
	}

	public ExcelExportProvider(String projectName,
			                   String filename, 
			                   String sheetname,
			                   Table tableEModel) {

		this.filename = filename;
		
		this.sheetname = sheetname;
		
		this.projectName = projectName;
		
		this.tableEModel = tableEModel;
				
		dtIDColumnMap = new HashMap<String, Short>();
		
		etIDColumnMap = new HashMap<String, Short>();
	}
	
	/**
	 * 
	 * @throws ExcelExportException
	 */
	public void exportWorkbook() throws ExcelExportException {
		FileOutputStream fileOut = null;		
		long startTime = 0;
		try {

			fileOut = new FileOutputStream(this.filename);
			workbook = new HSSFWorkbook();

			if (workbook != null) {

				this.sectionHeaderFont = workbook.createFont();

				this.sectionHeaderFont.setBold(true);

				this.columnHeaderFont = workbook.createFont();

				this.columnHeaderFont.setBold(true);

				this.columnHeaderFont.setItalic(true);

				HSSFSheet sheet = workbook.getSheet(this.sheetname);

				if (sheet == null) {

					sheet = workbook.createSheet(this.sheetname);

				}

				
				startTime = System.currentTimeMillis();
				// write domain model to different sheet 
				/**
				 * for dynamic list write domain model to different sheet
				 * un comment once integrated with POI 3.5 beta release
				 */
				
				this.parseModel(sheet);

			} else {
				throw new ExcelExportException("Unknown Error During Exporting");
			}

			workbook.write(fileOut);
			long endTime = System.currentTimeMillis();
			DecisionTableCorePlugin.debug(getClass().getName(), "Exported Model To Excel in: {0} sec."
					, (endTime - startTime) / 1000.0 );

			fileOut.close();

		} catch (Exception e) {
			DecisionTableCorePlugin.log(e);
			throw new ExcelExportException(e);
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					throw new ExcelExportException(e);
				}
			}
		}
	}
	
	/**
	 * @param stream Output stream to write the excel contents to
	 * @throws ExcelExportException
	 */
	public void exportWorkbook(OutputStream stream) throws ExcelExportException {
		try {	
			workbook = new HSSFWorkbook();
			if (workbook != null) {
				this.sectionHeaderFont = workbook.createFont();
				this.sectionHeaderFont.setBold(true);
				this.columnHeaderFont = workbook.createFont();
				this.columnHeaderFont.setBold(true);
				this.columnHeaderFont.setItalic(true);
				HSSFSheet sheet = workbook.getSheet(this.sheetname);
				if (sheet == null) {
					sheet = workbook.createSheet(this.sheetname);
				}					
				this.parseModel(sheet);
			} else {
				throw new ExcelExportException("Unknown Error During Exporting");
			}
			workbook.write(stream);
		} catch (Exception e) {
			DecisionTableCorePlugin.log(e);
			throw new ExcelExportException(e);
		} 		
	}	

	private void parseModel(HSSFSheet sheet) throws ExcelExportException {
		// write Version Information		
		this.writeVersionInfo(sheet);
		this.parseDeclarations(sheet);	
		//this.parseDomainModel(sheet);
		this.parseTable(sheet);	
	}
	
	@SuppressWarnings("deprecation")
	private void writeVersionInfo(HSSFSheet sheet) {
		HSSFRow row = sheet.createRow(this.currentRowNo++);
		HSSFCell cell = row.createCell((short) 0, CellType.NUMERIC);
		HSSFRichTextString header = new HSSFRichTextString(
				EXCEL_VERSION);
		header.applyFont(this.sectionHeaderFont);
		cell.setCellValue(header);		
		cell = row.createCell((short) 1, CellType.NUMERIC);
		//header = new HSSFRichTextString("2");
		//header.applyFont(this.sectionHeaderFont);
		cell.setCellValue(2);		
		// add a blank row for esthetics
		sheet.createRow(this.currentRowNo++);
	}
	
	private DomainExportData writeDomainModelToDifferentSheet(HSSFWorkbook wb, Domain domain) {
		//Create Sheet with this name if one does not exist
		HSSFSheet domainSheet = wb.getSheet(domain.getName());
		if (domainSheet == null) {
			domainSheet = wb.createSheet(domain.getName());
		}
		DomainModelExcelExportHandler domainModelExcelExportHandler = 
			new DomainModelExcelExportHandler(projectName, filename, wb, domainSheet, domain);
		try {
			DomainExportData domainExportData = domainModelExcelExportHandler.exportDomain(false);
			return domainExportData;
		} catch (IOException e) {
			DecisionTableCorePlugin.log(e);
		}
		return null;
	}

	private void parseDeclarations(HSSFSheet sheet) {

		// write the section header
		this.writeSectionHeader(sheet, HEADER_DECLARATIONS);

		// now also write the column headers
		HSSFRow row = sheet.createRow(this.currentRowNo++);

		this.writeColumnHeader(row, INDEX_PATH,
				HEADER_DECLARATION_PATH);
		this.writeColumnHeader(row, INDEX_ALIAS,
				HEADER_DECLARATION_ALIAS);
		this.writeColumnHeader(row, INDEX_PROPERTY,
				HEADER_DECLARATION_PROPERTY);
		
		String declPath;
		String declAlias;
		String declType;
		
		EList<Argument> argList = tableEModel.getArgument();
	
		EList<Argument> rfArgs = null;
		
			String implementedRes = tableEModel.getImplements();
			//Get new model VRF
			com.tibco.cep.designtime.core.model.rule.RuleFunction newVRF = 
				(com.tibco.cep.designtime.core.model.rule.RuleFunction)IndexUtils.getRule(projectName, implementedRes, ELEMENT_TYPES.RULE_FUNCTION);
			RuleFunction oldVRF = OntologyFactory.eINSTANCE.createRuleFunction();
			//Transform this to the legacy object
			IModelTransformer<RuleFunction, com.tibco.cep.designtime.core.model.rule.RuleFunction> transformer =
				new RuleFunctionModelTransformer();
			transformer.transform(newVRF, oldVRF);
			 rfArgs = oldVRF.getArguments().getArgument();
			if(rfArgs.size() != argList.size()){
				ValidationError vldError = new ValidationErrorImpl(
						Excel_VALIDATION_ERROR_CODE, "VRF and DT Arguments don't match ", "DecisionTable");
						excelVldErrorCollection.add(vldError);
			}
			
		for (Argument arg : argList) {
			ArgumentProperty argProperty = arg.getProperty();
			String alias = argProperty.getAlias();
			boolean isEqual = false; 
			for(Argument rfarg: rfArgs) {				
				ArgumentProperty rfargProperty = rfarg.getProperty();
				if (rfargProperty.getAlias().equals(alias)) {
					isEqual = true;
					break;					
				}
			}
			
			if(!isEqual){
				ValidationError vldError = new ValidationErrorImpl(
				Excel_VALIDATION_ERROR_CODE, "Invalid argument alias : " + alias,
				"DecisionTable");
				excelVldErrorCollection.add(vldError);
			}
			
			declPath = argProperty.getPath();
			
			declAlias = argProperty.getAlias();
			// declType = argProperty.getType();
			declType = arg.getDirection();
			
			// now that we have all the fields we need, create the row
			row = sheet.createRow(this.currentRowNo++);
			this.writeCell(sheet, row, INDEX_PATH, declPath, null);
			this.writeCell(sheet, row, INDEX_ALIAS, declAlias, null);
			this.writeCell(sheet, row, INDEX_PROPERTY, declType, null);
		}
		// add one blank row for esthetics
		sheet.createRow(this.currentRowNo++);
	}
	
	public Collection<ValidationError> getExcelVldErrorCollection() {
			return excelVldErrorCollection;
	}
	/**
	 * 
	 * @param sheet
	 * @param tableType
	 */
	private void writeColumnHeaders(HSSFSheet sheet, TableTypes tableType) {
		// write all the "Condition" headers
		// check if DT is opened in editor
		Columns columnsModel = null;
		columnsModel =
			(tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable().getColumns()
					: tableEModel.getExceptionTable().getColumns();
		List<Column> orderedColumnList = (columnsModel != null) ? columnsModel.getColumn() : new ArrayList<Column>(0);
		// Store column id as key and Excel column no as value inside a map
		short index = 1;
		HSSFRow columnHeaderRow = sheet.createRow(this.currentRowNo++);
		HSSFRow columnNameRow = sheet.createRow(this.currentRowNo++);
		Map<String, Short> idColumnMap = null;
		TableRuleSet trs = null;
		if (tableType == TableTypes.DECISION_TABLE) {
			trs = tableEModel.getDecisionTable();
			idColumnMap = dtIDColumnMap;
			// write description column as well as priority column
			int trvListSize = orderedColumnList.size();
			dtDescriptionColumn = (short) (trvListSize + 1);
			dtPriorityColumn = (short) (trvListSize + 2);
			this.writeColumnHeader(columnHeaderRow, dtDescriptionColumn,
					HEADER_DESCRIPTION_COLUMN);
			this.writeColumnHeader(columnHeaderRow, dtPriorityColumn,
					HEADER_PRIORITY_COLUMN);
		} else if (tableType == TableTypes.EXCEPTION_TABLE) {
			trs = tableEModel.getExceptionTable();
			idColumnMap = etIDColumnMap;
			int trvListSize = orderedColumnList.size();
			etDescriptionColumn = (short) (trvListSize + 1);
			etPriorityColumn = (short) (trvListSize + 2);
			this.writeColumnHeader(columnHeaderRow, etDescriptionColumn,
					HEADER_DESCRIPTION_COLUMN);
			this.writeColumnHeader(columnHeaderRow, etPriorityColumn,
					HEADER_PRIORITY_COLUMN);
		}
		// write Id header
		this.writeColumnHeader(columnHeaderRow, (short) 0, HEADER_ID_COLUMN);
		for (Column column : orderedColumnList) {
			String columnName = column.getName();
			String propertyPath = column.getPropertyPath();
			//Find any domains 
			//Get Applicable domains
			List<DomainInstance> applicableDomainInstances = DTDomainUtil.getDomains(propertyPath, projectName);
			if (applicableDomainInstances == null) {
				applicableDomainInstances = new ArrayList<DomainInstance>();
			}
			DomainExportData domainExportData[] = new DomainExportData[applicableDomainInstances.size()];
			//Write each domain to apporpriate sheet
			int counter = 0;
			for (DomainInstance domainInstance : applicableDomainInstances) {
				String resourcePath = domainInstance.getResourcePath();
				//Load domain from index
				Domain domain = IndexUtils.getDomain(projectName, resourcePath);
				domainExportData[counter++] = writeDomainModelToDifferentSheet(workbook, domain);
			}
			String substitutionFormat = 
				(column.isSubstitution()) ? 
						DTLanguageUtil.canonicalizeExpression(columnName.substring(columnName.indexOf(' '))) : "";
			String[] allDomainEntries = 
				DTDomainUtil.getDomainEntryStrings(applicableDomainInstances,
						                           propertyPath, 
						                           projectName, 
						                           substitutionFormat, 
						                           false, 
						                           column.getColumnType() == ColumnType.ACTION);
			String trvType = null;
			ColumnType columnType = column.getColumnType();
			if (ColumnType.CONDITION.equals(columnType)) {
				trvType = HEADER_CONDITION_COLUMN;
			} else if (ColumnType.ACTION.equals(columnType)) {
				trvType = HEADER_ACTION_COLUMN;
			} else if (ColumnType.CUSTOM_CONDITION.equals(columnType)) {
				trvType = HEADER_CUSTOM_CONDITION_COLUMN;
			} else if (ColumnType.CUSTOM_ACTION.equals(columnType)) {
				trvType = HEADER_CUSTOM_ACTION_COLUMN;
			}
			
			String colId = column.getId();
			this.writeColumnHeader(columnHeaderRow, index, trvType + " (" + colId + ")");
			if (column.isSubstitution()) {
				// TODO handle substitution for custom action/condition.
				if (useColumnAlias) {
					String columnAlias = column.getAlias();
					if (null != columnAlias) {
						if (!columnAlias.isEmpty()) {
							columnName = columnName.concat(" (" + COLUMN_ALIAS_ID + columnAlias + ")");
						}
					}
				}
				this.writeColumnHeader(columnNameRow, index, columnName);
			} else if (isColumnHeaderExportable(columnType, columnName)) {
				// donot write column header for custom action/condition
				if (useColumnAlias) {
					String columnAlias = column.getAlias();
					if (null != columnAlias) {
						if (!columnAlias.isEmpty()) {
							columnName = columnName.concat(" (" + COLUMN_ALIAS_ID + columnAlias + ")");
						}
					}
				}
				this.writeColumnHeader(columnNameRow, index, columnName);
			}
			
			if (trs != null && trs.getRule().size() > 0){
				//populateDomainValues(sheet,column.getPropertyPath(),trs.getRule().size(),columnNameRow.getRowNum(),index );
				/**
				 * use this method for dynamic list feature
				 */
				populateDomainValuesAsDynamicList(sheet,
						                          column.getPropertyPath(),
						                          domainExportData,
						                          allDomainEntries,
						                          trs.getRule().size(),
						                          columnNameRow.getRowNum(),
						                          index);
			}
			idColumnMap.put(colId, index);
			index++;
		}
	}
	
	private boolean isColumnHeaderExportable(ColumnType columnType, String columnName) {
		boolean flag = false;
		if (ColumnType.CONDITION.equals(columnType) 
				|| ColumnType.ACTION.equals(columnType)
				/*!DTRegexPatterns.CUSTOM_HEADER_PATTERN.matcher(columnName).matches()*/
				|| ColumnType.CUSTOM_CONDITION.equals(columnType) 
				|| ColumnType.CUSTOM_ACTION.equals(columnType)) {
			flag = true;
		}
		return flag;
	}

	
	private void parseTable(HSSFSheet sheet) throws ExcelExportException {
		this.writeGeneralProperties(sheet);
		
		// write the DT section header
		this.writeSectionHeader(sheet, HEADER_DECISION_TABLE);
		
		// now also write the column headers
		// HSSFRow row = sheet.createRow(this.currentRowNo++);
		writeColumnHeaders(sheet, TableTypes.DECISION_TABLE);
		
		// parse the actual DT model
		TableRuleSet decisionTable = tableEModel.getDecisionTable();
		List<TableRule> dtRuleList = decisionTable.getRule();
		parseTableEntries(sheet, decisionTable, dtRuleList, dtIDColumnMap,
				dtDescriptionColumn, dtPriorityColumn ,DTConstants.DECISION_TABLE);

		// add a blank row between DT and ET
		sheet.createRow(this.currentRowNo++);

		// write the ET section header
		this.writeSectionHeader(sheet, HEADER_EXCEPTION_TABLE);

		writeColumnHeaders(sheet, TableTypes.EXCEPTION_TABLE);

		TableRuleSet exceptionTable = tableEModel.getExceptionTable();

		List<TableRule> etRuleList = exceptionTable.getRule();
		parseTableEntries(sheet, exceptionTable, etRuleList, etIDColumnMap,
				etDescriptionColumn, etPriorityColumn , DTConstants.EXCEPTION_TABLE);

	}
	
	/**
	 * Create "General Properties" section and write the properties such as Effective/Expiry Dates, Single Row Execution, Table Priority.
	 * 
	 * @param sheet
	 * @param decisionTable
	 */
	private void writeGeneralProperties(HSSFSheet sheet) {
		this.writeSectionHeader(sheet, HEADER_GENERAL_PROPERTIES);
		
		HSSFRow columnHeaderRow = sheet.createRow(this.currentRowNo++);
		this.writeColumnHeader(columnHeaderRow, (short)0, ExcelProviderConstants.GEN_PROP_EFFECTIVE_DATE_TEXT);
		this.writeColumnHeader(columnHeaderRow, (short)1, ExcelProviderConstants.GEN_PROP_EXPIRY_DATE_TEXT);
		this.writeColumnHeader(columnHeaderRow, (short)2, ExcelProviderConstants.GEN_PROP_SINGLE_ROW_TEXT);
		this.writeColumnHeader(columnHeaderRow, (short)3, ExcelProviderConstants.GEN_PROP_TABLE_PRIORITY_TEXT);
		
		HSSFRow row = sheet.createRow(this.currentRowNo++);
		MetaData metaData = tableEModel.getMd();
		String effectiveDate = null;
		String expiryDate = null;
		String atMostOneRow = null;
		String priority = null;
		if (metaData != null) {
			for (Property prop : metaData.getProp()) {
				if (DecisionTableMetadataFeature.EFFECTIVE_DATE.getFeatureName().equalsIgnoreCase(prop.getName())) {
					effectiveDate = prop.getValue();
				} else if (DecisionTableMetadataFeature.EXPIRY_DATE.getFeatureName().equalsIgnoreCase(prop.getName())) {
					expiryDate = prop.getValue();
				} else if (DecisionTableMetadataFeature.SINGLE_ROW_EXECUTION.getFeatureName().equalsIgnoreCase(prop.getName())) {
					atMostOneRow = prop.getValue();
				} else if (DecisionTableMetadataFeature.PRIORITY.getFeatureName().equalsIgnoreCase(prop.getName())) {
					priority = prop.getValue();
				}
			}
		}
		this.writeCell(sheet, row, (short) 0, effectiveDate, null);
		this.writeCell(sheet, row, (short) 1, expiryDate, null);
		this.writeCell(sheet, row, (short) 2, atMostOneRow, null);
		this.writeCell(sheet, row, (short) 3, priority, null);
		sheet.createRow(this.currentRowNo++);
		
	}

	private void parseTableEntries(HSSFSheet sheet, TableRuleSet ruleSet, List<TableRule> ruleList,
			Map<String, Short> idColumnMap, short descriptionColumn,
			short priorityColumn ,int tableType) throws ExcelExportException {
		HSSFRow row = null;
		
		Columns columns = ruleSet.getColumns();
		for (TableRule rule : ruleList) {
			String priority = null;
			String description = null;
			String id = null;
			List<TableRuleVariable> ruleConditionList = rule.getCondition();
			List<TableRuleVariable> ruleActionList = rule.getAction();
			row = sheet.createRow(this.currentRowNo++);
			for (TableRuleVariable cond : ruleConditionList) {
				String condComment = cond.getComment();
				boolean isEnabled = cond.isEnabled();
				//Expression expr = cond.getExpression();
				String expr = cond.getExpr();
				String entireCondString = expr;
				String colId = cond.getColId();
				if (colId == null)
					continue;
				if (idColumnMap.get(colId) == null) continue;
				short columnIndex = idColumnMap.get(colId);
				Column column = columns.search(colId);
				if (column == null) {
					continue;
				}
				if (column.isSubstitution()) {
					//TODO Fix this
					//entireCondString = expr.getVarString();
				}
				
				//populateDomainValues(sheet, colId, trs, columnIndex);
				
				this.writeCellWithMetaData(sheet, row, columnIndex, entireCondString,
						condComment, isEnabled);

			}

			for (TableRuleVariable action : ruleActionList) {
				String actionComment = action.getComment();
				//Expression expr = action.getExpression();
				String expr = action.getExpr();
				boolean isEnabled = action.isEnabled();
				
				String entireActionString = expr;
				String colId = action.getColId();
				if (colId == null) continue;
				if (idColumnMap.get(colId) == null) continue;
				short columnIndex = idColumnMap.get(colId);		
				Column column = columns.search(colId);
				if (column == null) {
					continue;
				}
				if (column.isSubstitution()) {
					//TODO Fix this
					//entireActionString = expr.getVarString();
				}
				//populateDomainValues(sheet, colId, trs, columnIndex);
				this.writeCellWithMetaData(sheet, row, columnIndex, entireActionString,
						actionComment, isEnabled);

			}

			MetaData metaData = rule.getMd();
			if (metaData != null) {
				for (Property prop : metaData.getProp()) {
					if (prop.getName() != null
							&& prop.getName().equalsIgnoreCase(
									HEADER_DESCRIPTION_COLUMN)) {
						description = prop.getValue();
					} else if (prop.getName() != null
							&& prop.getName().equalsIgnoreCase(
									HEADER_PRIORITY_COLUMN)) {
						priority = prop.getValue();
					}
				}
			}

			id = rule.getId();
			if (description != null && !(description.equals(""))) {
				this.writeCell(sheet, row, descriptionColumn, description,
								null);
			}
			if (priority != null && !(priority.equals(""))) {
				this.writeCellInNumberFormat(sheet, row, priorityColumn, priority, null);
			}
			if (id != null && !(id.trim().equals(""))) {
				this.writeCell(sheet, row, (short) 0, id, null);
			}

		}
	}
	
	/**
	 * Method to add cell validation to multiple cells in a column.
	 * @param sheet
	 * @param propPath
	 * @param domainExportDatas
	 * @param allDomainEntries
	 * @param ruleSize
	 * @param rowNo
	 * @param columnIndex
	 */
	private void populateDomainValuesAsDynamicList(HSSFSheet sheet, 
			                                       String propPath,
			                                       DomainExportData[] domainExportDatas, 
			                                       String[] allDomainEntries,
											       int ruleSize,
											       int rowNo,
											       int columnIndex) {	
		CellRangeAddressList rangeAddrList = new CellRangeAddressList();	
		for (DomainExportData domainExportData : domainExportDatas) {
			String domainName = domainExportData.getDomainName();
			int startRow = domainExportData.getStartRow();
			int endRow = domainExportData.getEndRow();
			int colNo = domainExportData.getColumnPosition();
			String strFormula = getNameFormula(domainName, colNo, startRow + 1, endRow + 1);
			String name = getValidName(propPath);
			int index = workbook.getNameIndex(name);
			HSSFName nameRange = null;
			if (index == -1) {
				nameRange = workbook.createName();
				nameRange.setNameName(name);
				index = workbook.getNameIndex(name);
				nameRange.setSheetIndex(0);
				nameRange.setRefersToFormula(strFormula);
			} else {
				nameRange = workbook.getNameAt(index);
				nameRange.setNameName(name);
				nameRange.setRefersToFormula(strFormula);
				nameRange.setSheetIndex(0);
			}
			
			CellRangeAddress cellRangeAddress = new CellRangeAddress(rowNo + 1, rowNo + ruleSize , columnIndex, columnIndex);
			rangeAddrList.addCellRangeAddress(cellRangeAddress);
			//  nameRange.setReference(strFormula);
			DVConstraint dvConstraint = DVConstraint.createFormulaListConstraint(strFormula);
			HSSFDataValidation data_validation = new HSSFDataValidation(rangeAddrList, dvConstraint);			 
	        data_validation.setSuppressDropDownArrow(false); 
			data_validation.setEmptyCellAllowed(true);
			data_validation.setShowPromptBox(true);
			data_validation.createErrorBox(Messages.getString("INVALID_EXCEL_INPUT"),
					Messages.getString("INVALID_EXCEL_INPUT_ERROR"));
			sheet.addValidationData(data_validation);
		}
	}

	/**
	 * get valid name from property path (remove all segment separator to '.')
	 *  @param str
	 */	
	private String getValidName(String str) {
		// replace all 
		if (str == null) return null;
		while (str.startsWith("/")) {
			str = str.substring(1);
		}
		String replacesStr = str.replace('/', '.');
		return replacesStr;
	}
	
	/**
	 * get formula for Dynamic List
	 * @param domainName
	 * @param columnPosition
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	private String getNameFormula(String domainName,
			                        int columnPosition,
			                        int startRow,
			                        int endRow){
		int asciiA = 'A';		
		String prefix ="";
		int dividened = columnPosition/26;
		int remainder = columnPosition % 26;
		if (dividened > 0 && dividened < 26){
			prefix = ""+(char)(asciiA + dividened-1);
		} else {
			// @TODO if required
		
		}
		
		StringBuilder columnIdentifier = new StringBuilder("");
		char ch = (char)(remainder + asciiA);
//		columnIdentifier.append("OFFSET(");
		columnIdentifier.append(domainName);
		columnIdentifier.append("!$");
		columnIdentifier.append(prefix);
		columnIdentifier.append(ch);
		columnIdentifier.append("$");
		columnIdentifier.append(startRow);
		columnIdentifier.append(":$");
		columnIdentifier.append(ch);
		columnIdentifier.append("$");
		columnIdentifier.append(endRow);
		//System.out.println(strValue);
		return columnIdentifier.toString();
	}
	
	@SuppressWarnings("deprecation")
	private void writeColumnHeader(HSSFRow row, short index, String value) {
		HSSFCell cell = row.createCell(index, CellType.STRING);
		HSSFRichTextString valueString = new HSSFRichTextString(value);
		valueString.applyFont(this.columnHeaderFont);
		cell.setCellValue(valueString);
	}

	@SuppressWarnings("deprecation")
	private void writeCell(HSSFSheet sheet, HSSFRow row, short index,
			String value, String comment) {

		HSSFCell cell = row.createCell(index, CellType.STRING);
		cell.setCellValue(new HSSFRichTextString(value));

 		if (comment != null && (!comment.trim().equals("")) && sheet != null) {
			if (patr == null) {
				patr = sheet.createDrawingPatriarch();
			}
			HSSFComment cellComment = patr.createComment(new HSSFClientAnchor(
					0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
			cellComment.setString(new HSSFRichTextString(comment));
			cell.setCellComment(cellComment);
		  
		}

	}
	@SuppressWarnings("deprecation")
	private void writeCellWithMetaData(HSSFSheet sheet, HSSFRow row, short index,
			String value, String comment, boolean isEnabled) {

		HSSFCell cell = row.createCell(index, CellType.STRING);
		cell.setCellValue(new HSSFRichTextString(value));

 		if (comment != null && (!comment.trim().equals("")) && sheet != null) {
			if (patr == null) {
				patr = sheet.createDrawingPatriarch();
			}
			HSSFComment cellComment = patr.createComment(new HSSFClientAnchor(
					0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
			cellComment.setString(new HSSFRichTextString(comment));
			cell.setCellComment(cellComment);		
		}

 		// Set the style to make sure the enable/disable behaviour is exported
		HSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		if (isEnabled) {
			cellStyle.cloneStyleFrom(cell.getCellStyle());
			cellStyle.setLocked(false);
		} else {
			cellStyle.cloneStyleFrom(cell.getCellStyle());
			cellStyle.setLocked(true);
		}
		cell.setCellStyle(cellStyle);
	}
	
	@SuppressWarnings("deprecation")
	private void writeCellInNumberFormat(HSSFSheet sheet, HSSFRow row, short index,
			String value, String comment) throws ExcelExportException {

		HSSFCell cell = row.createCell(index, CellType.NUMERIC);
		double priority = 5;
		try {
			priority = Double.parseDouble(value);
		} catch (Exception e){
			throw new ExcelExportException(e);
		}
		cell.setCellValue(priority);

	}

	@SuppressWarnings("deprecation")
	private void writeSectionHeader(HSSFSheet sheet, String value) {
		HSSFRow row = sheet.createRow(this.currentRowNo++);
		HSSFCell cell = row.createCell((short) 0, CellType.STRING);
		HSSFRichTextString header = new HSSFRichTextString(value);
		header.applyFont(this.sectionHeaderFont);
		cell.setCellValue(header);
		// add a blank row for esthetics
		sheet.createRow(this.currentRowNo++);
	}

	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getSheetname() {
		return sheetname;
	}

	public void setSheetname(String sheetname) {
		this.sheetname = sheetname;
	}

	public Table getTableEModel() {
		return tableEModel;
	}
	
	public HSSFWorkbook getWorkBook() {
		return workbook;
	}
	
	public boolean getUseColumnAlias() {
		return useColumnAlias;
	}

	public void setTableEModel(Table tableEModel) {
		this.tableEModel = tableEModel;
	}
	
	public void setUseColumnAlias(boolean useColumnAlias) {
		this.useColumnAlias = useColumnAlias;
	}
}
