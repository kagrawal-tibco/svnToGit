package com.tibco.cep.decision.table.provider.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.cep.decision.table.model.domainmodel.Domain;
import com.tibco.cep.decision.table.model.domainmodel.DomainEntry;
import com.tibco.cep.decision.table.model.domainmodel.DomainModelFactory;
import com.tibco.cep.decision.table.model.domainmodel.EntryValue;
import com.tibco.cep.decision.table.model.domainmodel.RangeInfo;
import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.utils.DecisionTableCoreUtil;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.decision.table.core.DecisionTableCorePlugin;

public class ExcelImportProvider extends ExcelProviderConstants {
	
	private String filename;
	//private Object model;
	
	private Table tableEModel ;
	
	private TableRuleSet exceptionTable;
	private TableRuleSet decisionTable;
	private short [] conditionColumns;
	private short [] actionColumns;
	private List<RuleEntity> conditions;
	private List<RuleEntity> actions;
	private boolean parsingDecisionTable;
	private Map<String, String> aliasPathMap;
	
	
	public ExcelImportProvider(String filename) {		
		this.filename = filename;
		//this.model = model;
		this.conditions = new LinkedList<RuleEntity>();
		this.actions = new LinkedList<RuleEntity>();
		this.parsingDecisionTable = true;			
		tableEModel = DtmodelFactory.eINSTANCE.createTable();
		decisionTable = DtmodelFactory.eINSTANCE.createTableRuleSet();
		exceptionTable = DtmodelFactory.eINSTANCE.createTableRuleSet();
		aliasPathMap = new HashMap<String, String>();
	}
	


	public void importWorkbook() {
		POIFSFileSystem fs;
		HSSFWorkbook wb = null;
		
		try {
			fs = new POIFSFileSystem(new FileInputStream(this.filename));
			wb = new HSSFWorkbook(fs);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			//showError("File Not Found", e.getMessage());
			return;
		} catch (IOException e) {
			e.printStackTrace();
			//showError("Exception", e.getMessage());
			return;
		}
		
		if (wb != null) {
			this.parseModel(wb);
		}
//		else {
//			//showError("Error", "Unknown Error During Importing");
//			return;
//		}
		tableEModel.setDecisionTable(decisionTable);
		tableEModel.setExceptionTable(exceptionTable);	
		//persistDTModel(decisionTableEModel,exceptionTableEModel);
	}
	
	/*public static void showError(String title, String message) {
		MessageDialog.openError(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				title,
				message);
	}*/

	
	private void parseModel(HSSFWorkbook wb) {
		
        long startTime = System.currentTimeMillis(); 
        
		// TODO: for now we just process all the sheets, but in a wizard we should ask the user
		// which sheets to process;
		int noSheets = wb.getNumberOfSheets();
		// go through all the sheets
		for (int sheetIndex = 0; sheetIndex < noSheets; sheetIndex++) {

			HSSFSheet sheet = wb.getSheetAt(sheetIndex);
			
//			System.out.println("Sheet named " + wb.getSheetName(sheetIndex) +
//				" has " + sheet.getPhysicalNumberOfRows() + " rows.");
			
			// parse the declarations
			int rowIndex = this.parseDeclarations(sheet);
			
			// parse the decision table
			this.parsingDecisionTable = true;
			rowIndex = this.parseTable(sheet, rowIndex);
			
			if (rowIndex < 0) {
				DecisionTableCorePlugin.log(this.getClass().getName(), "Stopped parsing file due to errors in table definition.");
			}
			else {
				// System.out.println("********** Finished parsing Decision Table, now parsing Exception Table");

				// parse the exception table
				this.parsingDecisionTable = false;
				this.parseTable(sheet, rowIndex);
				// System.out.println("********** Finished parsing Exception Table, all done!");
			}
		}
		
        long endTime = System.currentTimeMillis();
        DecisionTableCorePlugin.debug(getClass().getName(), "Imported Model From Excel in: {0} sec." , (endTime - startTime)/1000.0);
	}
	
	/**
	 * @param sheet
	 * @return the row index where parsing left off
	 */
	@SuppressWarnings("deprecation")
	protected int parseDeclarations(HSSFSheet sheet) {
		boolean foundDeclarations = false;
		boolean foundNextSection = false;
		int rowIndex = 0;
		int noRows = sheet.getPhysicalNumberOfRows();
		HSSFCell cell = null;
		HSSFRow row = null;

		// go through all the rows and parse the declarations
		while (rowIndex < noRows) {
			row = sheet.getRow(rowIndex++);

			if (row != null) {
				cell = row.getCell((short) 0);

				if (cell != null) {

					if (foundDeclarations) {
						foundNextSection = this.parseDeclarationsEntity(row);
					}
					else {
						if (cell.getCellType() == CellType.STRING &&
							cell.getRichStringCellValue().getString().equalsIgnoreCase(HEADER_DECLARATIONS)) {
							// we found the header
							foundDeclarations = true;
							continue;
						}
						
						// added for domain values

					}
				}
			}
			
			if (foundNextSection) {
				break;
			}
		}
		
		if (!foundDeclarations) {
			// System.out.println("Warning, no declarations found in this sheet!");
		}
			
		return rowIndex;
	}
	
	/**
	 * @param row the row to be parsed
	 * @return whether we found the next section block (Decision Table
	 * or Exception Table header 
	 */
	@SuppressWarnings("deprecation")
	protected boolean parseDeclarationsEntity(HSSFRow row) {
		HSSFCell cell = null;
		String path = null;
		String alias = null;
		String property = null;
		String graphicalAliasPath = null;	
		String domainValues = null;
		String domainTpe = null;
		
		cell = row.getCell(INDEX_PATH);
		if (cell != null && cell.getCellType() == CellType.STRING) {
			path = cell.getRichStringCellValue().getString();
			if (path.equalsIgnoreCase(HEADER_DECLARATION_PATH)){
				return false;
				
			}
			path = path != null ? path.trim():path;
			if (path.equalsIgnoreCase(HEADER_DECISION_TABLE) ||
				path.equalsIgnoreCase(HEADER_EXCEPTION_TABLE)) {
				return true;
			}
		}
		
		cell = row.getCell(INDEX_ALIAS);
		if (cell != null && cell.getCellType() == CellType.STRING) {
			alias = cell.getRichStringCellValue().getString();
			alias = alias != null ? alias.trim():alias;
		}
		
		cell = row.getCell(INDEX_PROPERTY);
		if (cell != null && cell.getCellType() == CellType.STRING) {
			property = cell.getRichStringCellValue().getString();
			property = property != null ? property.trim():property;
		}

		cell = row.getCell(INDEX_GRAPHICAL_ALIAS_PATH);
		if (cell != null && cell.getCellType() == CellType.STRING) {
			graphicalAliasPath = cell.getRichStringCellValue().getString();
			graphicalAliasPath = graphicalAliasPath != null ? graphicalAliasPath.trim():graphicalAliasPath;
		}
		// added for Domain values

		cell = row.getCell(INDEX_DOMAIN_TYPE);
		if (cell != null && cell.getCellType() == CellType.STRING) {
			domainTpe = cell.getRichStringCellValue().getString();
			domainTpe = domainTpe != null ? domainTpe.trim():domainTpe;
		}

		cell = row.getCell(INDEX_DOMAIN_VALUES);
		if (cell != null && cell.getCellType() == CellType.STRING) {
			domainValues = cell.getRichStringCellValue().getString();
			domainValues = domainValues != null ? domainValues.trim():domainValues;
		}
		
		if (path == null || alias == null || property == null) {
			// System.out.println("Path, Alias, or Property cannot be null in a declaration entry!");
		}
		else {
			this.addDeclaration(path, alias, property, graphicalAliasPath,domainTpe,domainValues);
		}

		return false;
	}


	protected int parseTable(HSSFSheet sheet, int rowIndex) {
		
		// TODO?
//		if (rowIndex < 0) {
//			rowIndex = this.advanceToTableDefinition();
//		}

		boolean parsedColumnInformation = false;
		int noRows = sheet.getPhysicalNumberOfRows();
		
		rowIndex++;
		
		// go through all the rows and parse the DT entities
		for (HSSFCell cell = null; rowIndex < noRows; rowIndex++) {
			HSSFRow row = sheet.getRow(rowIndex);
			
			// TODO: in a wizard we should ask the user which columns (cells) contain which values,
			// for now we assume a certain format.
			if (row != null && this.hasStringData(row)) {
				
				if (!parsedColumnInformation) {
					if (this.parseColumnInformation(row)) {
						parsedColumnInformation = true;
					}
					else {
						DecisionTableCorePlugin.log(this.getClass().getName(), "Unable to find column header information.");
						rowIndex = -1;
						break;
					}
				}
				else {
					// process the row, which can have 1+ conditions and 1+ actions
					if (this.parseRuleEntity(row)) {
						// we found the beginning of the exception table, so we're done
						// with the decision table
						break;
					}
					
//					cell = row.getCell((short)0);
//
//					if (cell != null) {
//						this.parseTableEntity(cell, true);
//					}
//					else {
//						// System.out.println("null row");
//						// cell = row.createCell((short)3);
//					}
//
//					// now process action for this condition
//					cell = row.getCell((short)1);
//					if (cell != null) {
//						this.parseTableEntity(cell, false);
//					}
				}
			}
			else {
				// System.out.println("null row");
				// cell = row.createCell((short)3);
			}
		}
		
		return rowIndex;
	}
	

	/**
	 * @param row the row to be parsed
	 * @return whether we found the next section block (Exception Table header) 
	 */
	@SuppressWarnings("deprecation")
	protected boolean parseRuleEntity(HSSFRow row) {
	
		HSSFCell cell;
		String commentString;
		
		cell = row.getCell((short)0);
		if (cell != null && cell.getCellType() == CellType.STRING) {
			String contents = cell.getRichStringCellValue().getString();
			
			if (contents.equalsIgnoreCase(HEADER_DECISION_TABLE) ||
				contents.equalsIgnoreCase(HEADER_EXCEPTION_TABLE)) {
				return true;
			}
		}		
		
		this.conditions.clear();
		this.actions.clear();

// 		System.out.println("===RULE:");
		for (short conditionIndex = 0;
			conditionIndex < this.conditionColumns.length;
			conditionIndex++) {
			cell = row.getCell(this.conditionColumns[conditionIndex]);
			if (cell != null && cell.getCellType() == CellType.STRING) {
//				System.out.println("Condition: " + cell.getRichStringCellValue().getString());
				
				if (cell.getCellComment() != null) {
					commentString = cell.getCellComment().getString().getString();
				}
				else {
					commentString = null;
				}

				this.conditions.add(
					new RuleEntity(cell.getRichStringCellValue().getString(),
						commentString));
			}
		}

		for (short actionIndex = 0;
			actionIndex < this.actionColumns.length;
			actionIndex++) {
			cell = row.getCell(this.actionColumns[actionIndex]);
			if (cell != null && cell.getCellType() == CellType.STRING) {
//				System.out.println("Action:    " + cell.getRichStringCellValue().getString());
				if (cell.getCellComment() != null) {
					commentString = cell.getCellComment().getString().getString();
				}
				else {
					commentString = null;
				}

				this.actions.add(
					new RuleEntity(cell.getRichStringCellValue().getString(),
						commentString));
			}
		}

		this.updateModel(this.conditions, this.actions);
		
		return false;
	}
	
	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	protected boolean parseColumnInformation(HSSFRow row) {
		
		HSSFCell cell = null;
		LinkedList condColumnIndexes = new LinkedList();
		LinkedList actionColumnIndexes = new LinkedList();
		
		for (short columnIndex = 0;
			columnIndex < row.getPhysicalNumberOfCells();
			columnIndex++) {
			cell = row.getCell(columnIndex);
			
			if (cell != null) {
				if (cell.getCellType() == CellType.STRING) {
					if (cell.getRichStringCellValue().getString().equals(this.HEADER_CONDITION_COLUMN)) {
						// this column represents a condition
						condColumnIndexes.add(new Short(columnIndex));
					}
					else if (cell.getRichStringCellValue().getString().equals(this.HEADER_ACTION_COLUMN)) {
						// this column represents an action
						actionColumnIndexes.add(new Short(columnIndex));						
					}
					else {
						DecisionTableCorePlugin.log("Warning, unknown column header type: " +
							cell.getRichStringCellValue().getString());
						return false;
					}
				}
				else {
//					System.out.println("parseColumnInformation: found non-string cell:" + cell);
//					if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK)
//						System.out.println("found type blank");
//					if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN)
//						System.out.println("found type boolean");
//					if (cell.getCellType() == HSSFCell.CELL_TYPE_ERROR)
//						System.out.println("found type error");
//					if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA)
//						System.out.println("found type formula");
//					if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
//						System.out.println("found type numeric");
				}
			}
			else {
				DecisionTableCorePlugin.debug("ParseColumnInformation: found null cell");
			}
		}
		
		this.conditionColumns = new short[condColumnIndexes.size()];
		for (int i = 0; i < this.conditionColumns.length; i++) {
			this.conditionColumns[i] = ((Short) condColumnIndexes.get(i)).shortValue();
		}

		this.actionColumns = new short[actionColumnIndexes.size()];
		for (int i = 0; i < this.actionColumns.length; i++) {
			this.actionColumns[i] = ((Short) actionColumnIndexes.get(i)).shortValue();
		}
		
		return true;
	}

	protected boolean hasStringData(HSSFRow row) {
		HSSFCell cell = null;
		boolean foundString = false;
		
		for (short columnIndex = 0;
			columnIndex < row.getPhysicalNumberOfCells();
			columnIndex++) {
			cell = row.getCell(columnIndex);
			
			if (cell != null && (cell.getCellType() == CellType.STRING)) {
				foundString = true;
			}
		}
		
		return foundString;
	}
	
	// graphicalAliasPath may be null, others are not.
	private void addDeclaration(String path,
		String alias,
		String propertyType,
		String graphicalAliasPath, String domainType, String domainValues) {
		
		//System.setProperty("javax.xml.parsers.SAXParserFactory", "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");	
//		System.out.println("Adding declaration: " +
//			path + ", " + alias + ", " + propertyType + ", " + graphicalAliasPath+" , "+domainType+"  ,"+domainValues);
		alias = alias != null ? alias.trim():alias;
		path = path != null ? path.trim():path;
		Argument arg = DtmodelFactory.eINSTANCE.createArgument();
		arg.setDirection(propertyType);
		
		ArgumentProperty argProperty = DtmodelFactory.eINSTANCE.createArgumentProperty();
		argProperty.setAlias(alias);
		//argProperty.setDomainOverriden(true);
		argProperty.setType(domainType);
		argProperty.setPath(path);
		argProperty.setGraphicalPath(graphicalAliasPath);
		if (alias != null && path != null){
			aliasPathMap.put(alias, path);
		}		
		Domain domain = DomainModelFactory.eINSTANCE.createDomain();
		domain.setType(domainType);
		List<DomainEntry> domainEntryList = domain.getDomainEntry();
		DomainEntry domainEntry = null;
		EntryValue entryValue = null;
		
		if (domainValues != null){
		
			String[] tokens = parseDomainValues(domainValues);

			List<TokenInfo> tokenInfoList = getTokenInfoList(tokens);
			for (TokenInfo tokenInfo : tokenInfoList) {
				domainEntry = DomainModelFactory.eINSTANCE.createDomainEntry();
				entryValue = DomainModelFactory.eINSTANCE.createRangeInfo();				
				//domainEntry.setEntryValue(entryValue);
				//entryValue.setMultiple(true);
				String leftComponent = tokenInfo.getLeftComponent();
				String rightComponent = tokenInfo.getRightComponent();
				
				String leftComponentValue = getValue(leftComponent);
				String rightComponentValue = getValue(rightComponent);
				
				if (leftComponent != null && leftComponent.startsWith("<")) {			
					
					if (rightComponent != null){
						if (rightComponent.startsWith(">")){
						
						if (compareValues(leftComponent,rightComponent,domainType)){
							((RangeInfo)entryValue).setLowerBound(rightComponentValue);
							((RangeInfo)entryValue).setUpperBound(leftComponentValue);
							if (rightComponent.startsWith(">=")){
								((RangeInfo)entryValue).setLowerBoundIncluded(true);
							}
							if (leftComponent.startsWith("<=")){
								((RangeInfo)entryValue).setUpperBoundIncluded(true);
							}
						}			
						else {
							DecisionTableCorePlugin.log("Range is not valid. Enter a valid range.");
							continue;
						}
					}
					else {
						DecisionTableCorePlugin.log("Range is not valid. Enter a valid range.");
						continue;
					}
					} 
					else {
						((RangeInfo)entryValue).setLowerBound("Undefined");
						((RangeInfo)entryValue).setUpperBound(leftComponentValue);
		
						if (leftComponent.startsWith("<=")){
							((RangeInfo)entryValue).setUpperBoundIncluded(true);
						}
						
					}

					entryValue.setMultiple(true);
					domainEntry.setEntryValue(entryValue);
				}
				else if (leftComponent != null && leftComponent.startsWith(">")){					
					if (rightComponent != null ){
						if ( rightComponent.startsWith("<")){
						if (compareValues(rightComponent,leftComponent,domainType)){
							((RangeInfo)entryValue).setLowerBound(leftComponentValue);
							((RangeInfo)entryValue).setUpperBound(rightComponentValue);
							if (rightComponent.startsWith("<=")){
								((RangeInfo)entryValue).setUpperBoundIncluded(true);
							}
							if (leftComponent.startsWith(">=")){
								((RangeInfo)entryValue).setLowerBoundIncluded(true);
							}	
						}
						else {
							DecisionTableCorePlugin.debug("Range is not valid. Enter a valid range.");
							continue;
						}
					}			
					else {
						DecisionTableCorePlugin.debug("Range is not valid. Enter a valid range.");
						continue;
					}
				}
					else {
						((RangeInfo)entryValue).setLowerBound(leftComponentValue);
						((RangeInfo)entryValue).setUpperBound("Undefined");
	
						if (leftComponent.startsWith(">=")){
							((RangeInfo)entryValue).setLowerBoundIncluded(true);
						}	
					}
					entryValue.setMultiple(true);
					domainEntry.setEntryValue(entryValue);
				}
				else {					
					EntryValue singleEntryValue = DomainModelFactory.eINSTANCE.createSingleValue();
					singleEntryValue.setMultiple(false);
					singleEntryValue.setValue(leftComponent);			
				
					domainEntry.setEntryValue(singleEntryValue);
		
				}
				domainEntryList.add(domainEntry);
				
			}
	/*
		 else {

			String[] tokens = parseDomainValues(domainValues);
			for (int i = 0; i < tokens.length; ++i) {
				entryValue = DomainModelFactory.eINSTANCE
						.createSingleValue();
				entryValue.setMultiple(false);
				entryValue.setValue(tokens[i]);
				domainEntry = DomainModelFactory.eINSTANCE.createDomainEntry();
				domainEntry.setEntryValue(entryValue);
				domainEntryList.add(domainEntry);
			}

		}
		*/
		}
		
		//argProperty.setDomain(domain);
		arg.setProperty(argProperty);
		EList<Argument> argList = tableEModel.getArgument();
		//dt.getArgument().add(arg);
		
		argList.add(arg);	
		
	}
	private String getValue(String expression){
		if (expression != null){
			if (expression.indexOf(">=")!= -1 || expression.indexOf("<=")!= -1){
				return expression.substring(2);
			}
			else if (expression.indexOf(">")!= -1 || expression.indexOf("<")!= -1){
				return expression.substring(1);
			}
			else 
				return expression;
		}
		return null;
	}
	
	private boolean compareValues(String Val1,String Val2,String dataType){
		
		String value1 = getValue(Val1);
		String value2 = getValue(Val2);
		int intValue3 = 0;
		int intValue4 = 0;
		double doubleValue3 = 0;
		double doubleValue4 = 0;		
		if (dataType.equalsIgnoreCase("integer")){
			intValue3 = Integer.parseInt(value1);
			intValue4 = Integer.parseInt(value2);
		
		if (value1.equals(value2) && ((Val1.startsWith("<=") && Val2.startsWith(">=")) || 
				                       (Val1.startsWith(">=") && Val2.startsWith("<=")))){
			return true;
			
		}
		else if (intValue3 > intValue4){
			return true;
		}
		}
		else if (dataType.equalsIgnoreCase("double") || dataType.equalsIgnoreCase("Float") || dataType.equalsIgnoreCase("Decimal")){			
			doubleValue3 = Double.parseDouble(value1);			
			doubleValue4 = Double.parseDouble(value2);
			if (value1.equals(value2) && ((Val1.startsWith("<=") && Val2.startsWith(">=")) || 
                    (Val1.startsWith(">=") && Val2.startsWith("<=")))){
				return true;

			}	
			else if (doubleValue3 > doubleValue4){
				return true;
			}		
			
			
		}
		
		return false ;
		
	}
	private List<TokenInfo> getTokenInfoList(String[] tokens){
		if (tokens != null){
			List<TokenInfo> tokenInfoList = new ArrayList<TokenInfo>();
			
			for(String token : tokens){
				TokenInfo tokenInfo = populateTokenInfo(token);
				tokenInfoList.add(tokenInfo);
				
			}
			return tokenInfoList;
		}
		
		return null;
	}
	private TokenInfo populateTokenInfo(String token){
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
				
				tokenInfo = new TokenInfo(leftComponent,rightComponent);

			}
			else {
				tokenInfo = new TokenInfo(token,null);
			}
			return tokenInfo;
	    }
		
		return null;
	}
	
	private String[] parseDomainValues(String domainValues){
		StringTokenizer st = new StringTokenizer(domainValues,";");
		String[] tokens = new String[st.countTokens()];
		int tokenCount = 0;
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (token != null && !token.equals(""))
				tokens[tokenCount++] = token;
		}
		
		
		return tokens;
	}
	
	// TODO: IMPORT INTO MODEL
	// Both lists contain lists of RuleEntity objects that will have String texts
	// that will need to be parsed. The comments may be null.
	private void updateModel(List<RuleEntity> conditions, List<RuleEntity> actions) {	
		
		if (this.parsingDecisionTable) {
			// update Decision Table model				
			populateTable(decisionTable);			

		}
		else {
			// update Exception Table model			
			populateTable(exceptionTable);            
 
			
		}
		
	}
	
	private void populateTable(TableRuleSet ruleSet) {
		// TODO Auto-generated method stub
		List<TableRule> ruleList = ruleSet.getRule();
		TableRule rule = DtmodelFactory.eINSTANCE.createTableRule();
		List< TableRuleVariable> ruleConditionList = rule.getCondition();		
		//Expression expression = null;
		String expression = null;
		String body = null;
		String comment = null;
		for (Iterator<RuleEntity> it = conditions.iterator();it.hasNext();){
			RuleEntity ruleEntity = it.next();
			body = ruleEntity.getBody();				
			comment = ruleEntity.getComment();
			TableRuleVariable ruleCondition = DtmodelFactory.eINSTANCE.createTableRuleVariable();
			//expression = DtmodelFactory.eINSTANCE.createExpression();
			body = body != null ? body.trim(): body ;
			if (body != null && (!(body.trim().equals("")))){
				String operator = DecisionTableCoreUtil.getOperator(body);
				String actualValue = DecisionTableCoreUtil.getActualValue(operator, body);
				actualValue = actualValue != null ? actualValue.trim():actualValue;
				String alias = body.substring(0, body.indexOf(operator)) ;
				/**
				 * Alias in this case is something which precedes the operator.
				 * e.g:
				 * application.Name = "PeterKaBeta"
				 * then
				 * alias = application.Name 
				 */
				alias = alias != null ? alias.trim():alias;				
				String path = aliasPathMap.get(alias);				
				if (alias != null && path != null){
					ruleCondition.setColumnName(alias);
					ruleCondition.setPath(path);
				}
				//expression.setValue(actualValue);
				//expression.setOperator(operator);
			}
		
			expression = body;
			//dtRuleVariable.setExpression(expression);
			//dtRuleVarList.add(dtRuleVariable);
			ruleCondition.setExpr(expression);
			ruleCondition.setComment(comment);
			ruleConditionList.add(ruleCondition);
			
		}
		List<TableRuleVariable> ruleActionList = rule.getAction();		
		for (Iterator<RuleEntity> it = actions.iterator();it.hasNext();){
			RuleEntity ruleEntity = it.next();
			body = ruleEntity.getBody();
			comment = ruleEntity.getComment();
			TableRuleVariable ruleAction = DtmodelFactory.eINSTANCE.createTableRuleVariable();
			//expression = DtmodelFactory.eINSTANCE.createExpression();
			body = body != null ? body.trim(): body ;
			if (body != null && (!(body.trim().equals("")))){
				String operator = DecisionTableCoreUtil.getOperator(body);
				String actualValue = DecisionTableCoreUtil.getActualValue(operator, body);
				actualValue = actualValue != null ? actualValue.trim():actualValue;
				String alias = null;
				if (operator != null && body.indexOf(operator) != -1){
					alias = body.substring(0, body.indexOf(operator)) ;
					alias = alias != null ? alias.trim():alias;
				}
				String path = aliasPathMap.get(alias);
				ruleAction.setColumnName(alias);
				ruleAction.setPath(path);
				//expression.setValue(actualValue);
				//expression.setOperator(operator);
			}
			expression = body;	
			ruleAction.setComment(comment);
			ruleAction.setExpr(expression);
			ruleActionList.add(ruleAction);
			
		}	
		ruleList.add(rule);
		
		
	}


	private void persistModel(Table tableEModel){
		
		ResourceSet resourceSet = new ResourceSetImpl();
		URI fileURI = URI.createFileURI(new File("E:/tibco/decisionTable/decisionTable.dt").getAbsolutePath());
		Resource resource = resourceSet.createResource(fileURI);
		if (tableEModel != null){
			resource.getContents().add(tableEModel);
	
		}

		
		
		try {
			resource.save(ModelUtils.getPersistenceOptions()); // G11N encoding changes
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	

	/**
	 * This class contains the contents of either a condition or an action.
	 */
	class RuleEntity {
		
		private String body;
		private String comment;
		
		RuleEntity(String body, String comment) {
			this.body = body;
			this.comment = comment;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}
	}




//	protected void parseTableEntity(HSSFCell cell, boolean isCondition) {
//	HSSFRichTextString textCellContents = null;
//	double numberCellContents;
//	
//	// cell.setCellType(CellType.STRING);
//	// cell.setCellValue("test");
//	String type;
//	if (isCondition) {
//		type = "condition";
//	}
//	else {
//		type = "action";
//	}
//	
//	String debugInfo = "Parsing " + type + " ";
//	
//	if (cell.getCellType() == CellType.STRING) {
//		textCellContents = cell.getRichStringCellValue();
//		System.out.println(debugInfo + "string: " + textCellContents.getString());
//		// this.updateModel(textCellContents.getString(), cell.getCellComment().getString().getString());
//	}
//	else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//		numberCellContents = cell.getNumericCellValue();
//		System.out.println(debugInfo + "number: " + numberCellContents);
//		String doubleString = Double.toString(numberCellContents);
//		// this.updateModel(doubleString, cell.getCellComment().getString().getString());
//	}
//	else if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
//		System.out.println("Skipping formula: " + cell.getCellFormula());
//	}
//	else {
//		System.out.println("Skipping unknown type of a cell");
//	}		
//}
	
	class TokenInfo{
		String leftComponent;
		String rightComponent;
		public TokenInfo(String operator,String value) {
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

}


