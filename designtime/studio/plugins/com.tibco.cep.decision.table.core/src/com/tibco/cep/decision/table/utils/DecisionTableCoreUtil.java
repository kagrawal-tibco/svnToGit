package com.tibco.cep.decision.table.utils;


import static com.tibco.cep.decision.table.language.DTLanguageUtil.OPERATORS;
import static com.tibco.cep.decision.table.language.DTLanguageUtil.canonicalizeExpression;
import static com.tibco.cep.decision.table.language.DTLanguageUtil.COND_OPERATORS.OR;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.ACT_SUBSTITUTION_ASSIGNMENT_PATTERN;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.ACT_SUBSTITUTION_ASSIGNMENT_PATTERN_BEFORE;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.ACT_SUBSTITUTION_EQUALITY_PATTERN;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.ACT_SUBSTITUTION_EQUALITY_PATTERN_BEFORE;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.COND_SUBSTITUTION_PATTERN;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.COND_SUBSTITUTION_PATTERN_BEFORE;
import static com.tibco.cep.decision.table.utils.CellEditorUtils.massageRawString;

import java.io.File;
import java.text.Format;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;

import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.model.dtmodel.impl.TableImpl;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.decisionproject.persistence.impl.DecisionProjectLoader;
import com.tibco.cep.decisionproject.util.DTConstants;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.impl.SharedDecisionTableElementImpl;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.DecisionTableColumnIdGenerator;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.decision.table.core.DecisionTableCorePlugin;


public class DecisionTableCoreUtil {
	
	private static final String[] EMPTY_ARRAY = new String[0];
	
	public static boolean logged = false;
	public static boolean open = false;
	public static boolean IsEditorPopupMenuEnabled = true;
	
	private static final String SUBSTITUTION_DELIM = ";";
	
	/**
	 * Get position of a column in the UI Column Order
	 * @param project
	 * @param tablePath
	 * @param tableType
	 * @param columnName
	 * @return
	 */
	public static int getColumnPosition(String project,
			                            Table tableEModel,
			                            TableTypes tableType,
			                            String columnName,
			                            String trvId) {
		
		TableRuleSet tableRuleSet = (tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable() : tableEModel.getExceptionTable();
		List<Column> tableRuleSetColumns = tableRuleSet.getColumns().getColumn();
		int pos = trvId.indexOf("_") + 1;
		String trvColdId = trvId.substring(pos);
		for (int loop = 0; loop < tableRuleSetColumns.size(); loop++) {
			Column orderedColumn = tableRuleSetColumns.get(loop);
			String colId = orderedColumn.getId();
			if (orderedColumn.getName().equals(columnName) == true 
					&& trvColdId.equals(colId) == true) {
				return loop;
			}
		}
		return -1;
	}

	/**
	 * This class object is not intended to be instantiated by client
	 */
	private DecisionTableCoreUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Create a {@link TableRule} from a UI {@link DecisionRule}
	 * @param decisionTable
	 * @param decisionRule
	 * @return
	 */
//	public static TableRule createEModelFromRule(DecisionDataModel decisionDataModel,
//												 TableRuleSet decisionTable, 
//			                                     DecisionRule decisionRule) {
//		TableRule tableRule = 
//			DtmodelFactory.eINSTANCE.createTableRule();
//		
//		String id = Integer.toString(decisionRule.getId());
//		//Increment one more than size
//		tableRule.setId(id);
//		//Presumably columns created
//		Columns columns = decisionTable.getColumns();
//		//Get all conditions/actions
//		List<DecisionEntry> conditions = decisionRule.getConditions();
//		List<DecisionEntry> actions = decisionRule.getActions();
//		
//				
//		List<TableRuleVariable> eModelConditions = 
//			createEModelTRVs(conditions, 
//					         columns, 
//					         tableRule, 
//					         decisionDataModel);
//		List<TableRuleVariable> eModelActions = 
//			createEModelTRVs(actions, 
//			                 columns, 
//			                 tableRule, 
//			                 decisionDataModel);
//		
//		tableRule.getCondition().addAll(eModelConditions);
//		tableRule.getAction().addAll(eModelActions);
//		return tableRule;
//	}
	
//	private static List<TableRuleVariable> createEModelTRVs(List<DecisionEntry> decisionEntries, 
//			                                                Columns columns, 
//			                                                TableRule tableRule, 
//			                                                DecisionDataModel decisionDataModel) {
//		List<TableRuleVariable> trvs = 
//				new ArrayList<TableRuleVariable>(decisionEntries.size());
//		for (DecisionEntry decisionEntry : decisionEntries) {
//			TableRuleVariable trv = 
//				createEModelTRVFromDecisionEntries(decisionEntry, 
//						                           columns, 
//						                           tableRule, 
//						                           decisionDataModel);
//			if (trv != null) {
//				/**
//				 * This case can come when you do undo after
//				 * row removal upon removal of last column.
//				 * Since the column is not present, the trv
//				 * will be null.
//				 */
//				trvs.add(trv);
//			}
//		}
//		return trvs;
//	}
	
//	public static TableRuleVariable createEModelTRVFromDecisionEntries(DecisionEntry decisionEntry, 
//			                                                           Columns columns,
//			                                                           TableRule tableRule,
//			                                                           DecisionDataModel decisionDataModel) {
//		DecisionField decisionField = decisionEntry.getDecisionField();
//		//Get its area type
//		int areaType = decisionField.getAreaType();
//		String ruleId = tableRule.getId();
//		
//		//Get column name and such
//		String columnName = decisionField.getName();
//		Column interestedColumn = null;
//		
//		//Check if column name has a prefix
//		columnName = tokenizeColumnName(columnName);
//		for (Column column : columns.getColumn()) {
//			ColumnType columnType = column.getColumnType();
//			int columnAreaType = 
//				(columnType == ColumnType.CONDITION || columnType == ColumnType.CUSTOM_CONDITION) ? 0 : 1;
//			/**
//			 * Also necessary to compare column types
//			 * since action and condition can have same column name.
//			 */
//			if (column.getName().intern() == columnName.intern() && areaType == columnAreaType) {
//				interestedColumn = column;
//				break;
//			}
//		}
//		if (interestedColumn == null) {
//			return null;
//		}
//		String columnId = interestedColumn.getId();
//		String trvId = new StringBuilder(ruleId).append("_").append(columnId).toString();
//		TableRuleVariable trv = null;
//		
//		Object cellValue = decisionEntry.getValue();
//		
//		//Upon clicking add button
//		if (cellValue instanceof String) {
//			trv = DtmodelFactory.eINSTANCE.createTableRuleVariable();
//			trv.setExpr((String)cellValue);
//		} else {
//			//Duplicating rules
//			TableRuleVariable oldTrv = (TableRuleVariable)cellValue;
//			//Duplicate this
//			trv = (TableRuleVariable)EcoreUtil.copy(oldTrv);
//		}
//			
//		trv.setColId(columnId);
//		trv.setId(trvId);
//
//		return trv;
//	}
	
	/**
	 * Tokenize column name to remove an prefixes
	 * @param columnName
	 * @return
	 */
	public static String tokenizeColumnName(String columnName) {
		//Check if column name has a prefix
		if (columnName.startsWith(DTConstants.PREFIX_IN)) {
			int prefixLength = DTConstants.PREFIX_IN.length();
			columnName = columnName.substring(prefixLength, columnName.length());
		}
		if (columnName.startsWith(DTConstants.PREFIX_OUT)) {
			int prefixLength = DTConstants.PREFIX_OUT.length();
			columnName = columnName.substring(prefixLength, columnName.length());
		}
		return columnName;
	}

	public static Table loadModel(String filePath) {
		Table tableEModel = null;
		if (filePath != null) {
			ResourceSet resourceSet = new ResourceSetImpl();
			URI fileURI = URI.createFileURI(new File(filePath)
					.getAbsolutePath());
			Resource dtResource = resourceSet.getResource(fileURI, true);
			if (dtResource != null) {
				tableEModel = (Table) dtResource.getContents().get(0);
			}

		} else {
			// logger
			DecisionTableCorePlugin.debug("File path is null");
		}

		return tableEModel;

	}

	
	// The following two methods were moved to ExpressionBodyUtil so that
	// they would be visible to the code generation code in the
	// decision project plugin.
	public static String getOperator(String cellValue) {
		return ExpressionBodyUtil.getOperator(cellValue);
	}

	public static String getActualValue(String operator, String cellValue) {
		return ExpressionBodyUtil.getActualValue(operator, cellValue);
	}
	
	/**
	 * 
	 * @param tableEModel
	 * @return
	 */
	public static TableModel getDeclarationTableModel(Table tableEModel) {
		return getDeclarationTableModel(tableEModel, null);
	}
	
	/**
	 * 
	 * @param tableEModel
	 * @param rf
	 * @return
	 */
	public static TableModel getDeclarationTableModel(Table tableEModel,
			                                          RuleFunction rf) {
		List<Argument> argumentList = (rf != null) ? rf.getArguments().getArgument() : tableEModel.getArgument();
		return getDeclarationTableModel(argumentList);
	}
	
	private static TableModel getDeclarationTableModel(List<Argument> argumentList) {
		TableModel tableModel = null;
		Vector<String> columnIdentifiers = new Vector<String>(4);
		columnIdentifiers.add(Messages.getString("DT_Declaration_columns_name"));
		columnIdentifiers.add(Messages.getString("DT_Declaration_columns_alias"));
		columnIdentifiers.add(Messages.getString("DT_Declaration_columns_array"));
		// columnIdentifiers.add(Messages.DT_Declaration_columns_legend);
		if (argumentList != null) {
			Vector<Vector<String>> dataVector = new Vector<Vector<String>>(argumentList.size());
			for (Iterator<Argument> it = argumentList.iterator(); it.hasNext();) {
				Argument argument = it.next();
				if (argument != null) {
					ArgumentProperty argProperty = argument.getProperty();
					if (argProperty != null) {
						String alias = argProperty.getAlias();
//						String legend = argProperty.getGraphicalPath();
						boolean isArray = argProperty.isArray();
						String path = argProperty.getPath();
						// Domain domain = argProperty.getDomain();
						Vector<String> rowVector = new Vector<String>(4);
						rowVector.add(path);
						rowVector.add(alias);
						rowVector.add(Boolean.toString(isArray));
//						rowVector.add(legend);
						dataVector.add(rowVector);
					}
				}
			}
			tableModel = new DefaultTableModel(dataVector, columnIdentifiers);
		} else {
			tableModel = new DefaultTableModel(columnIdentifiers, 0);
		}
		return tableModel;
	}

	
	/**
	 * Save a {@link Table} as an {@link IFile}
	 * 
	 * @param absoluteFilePath -> The absolute path of DTable.
	 * @param table -> The {@link Table} resource to save
	 */
	public static void saveIFileImpl(final String absoluteFilePath,
			                         final Table table) throws Exception {
	
		//Create a URI
		URI uri = URI.createFileURI(absoluteFilePath);
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(table);
		
		Map<String, Object> options = new HashMap<String, Object>();
		options.put(XMLResource.OPTION_FORMATTED, Boolean.FALSE);
		options.put(XMIResource.OPTION_ENCODING, ModelUtils.DEFAULT_ENCODING); // G11N encoding changes

		resource.save(options);
	}


	public static boolean isVirtual(RuleFunction rf) {
		EList<com.tibco.cep.decision.table.model.dtmodel.Property> property = rf
				.getHeader().getProperty();
		for (com.tibco.cep.decision.table.model.dtmodel.Property prop : property) {
			if (prop.getName().equalsIgnoreCase("virtual")
					&& prop.getType().equalsIgnoreCase("boolean")
					&& prop.getValue().equalsIgnoreCase("true")) {
				return true;
			}
		}
		return false;
	}

	
	public static boolean islogged() {
		return logged;
	}

	public static void setlogged(boolean logged) {
		DecisionTableCoreUtil.logged = logged;
	}

	public static boolean isOpen() {
		return open;
	}

	public static void setOpen(boolean open) {
		DecisionTableCoreUtil.open = open;
	}

	
	public static Table reloadTable(Table table, String projectName) {
		return reloadTable(table, projectName, false);
	}

	public static Table loadTemporaryTable(Table table, String projectName) {
		// commented because there is no need to get VRF for resource path , by
		// mistake it was
		// retrieved initially
		// AbstractResource template = getTemplate(table, ontology);
		// load resource from file system
		DecisionProjectLoader dpl = DecisionProjectLoader.getInstance();
		String baseDir = dpl.getBaseDirectory();
		String resPath = null;
		/*
		 * if (baseDir != null && template != null) { resPath = baseDir +
		 * template.getFolder() + table.getName() + ".rulefunctionimpl"; }
		 */
		if (baseDir != null) {
			resPath = baseDir + table.getFolder() + table.getName()
					+ ".rulefunctionimpl";
		}
		if (resPath != null) {
			ResourceSet resourceSet = new ResourceSetImpl();
			URI resURI = URI.createFileURI(resPath);
			Resource resource = resourceSet.getResource(resURI, true);
			if (resource.getContents().get(0) != null) {
				EObject eObject = resource.getContents().get(0);
				if (eObject instanceof Table) {
					Table tbl = (Table) eObject;
					tbl.getDecisionTable();
					return tbl;
				}
			}
		}
		return null;
	}

	public static Table reloadTable(Table table, String projectName, boolean loadTableRuleSet) {
//		TRACE.logDebug(DecisionTableUtil.class.getName(),
//				"Reloading table {0}", table.getName());
		Table tbl = loadTemporaryTable(table, projectName);
		if (tbl == null) {
			// could not reload, return passed in table
//			TRACE.logDebug(DecisionTableUtil.class.getName(),
//					"Unable to reload table {0}, returning original table",
//					table.getName());
			return table;
		}
		if (tbl.getDecisionTable() != null) {
			table.setDecisionTable(tbl.getDecisionTable());
		}
		if (tbl.getExceptionTable() != null) {
			table.setExceptionTable(tbl.getExceptionTable());
		}
		if (tbl.getMd() != null) {
			table.setMd(tbl.getMd());
		}
		table.setVersion(tbl.getVersion());
		table.setDirty(false);
		return table;
	}

	
	
	
	/**
	 * @param type
	 * @return boolean
	 */
	public static boolean checkDateType(int type) {

		switch(type) {
		case PropertyDefinition.PROPERTY_TYPE_DATETIME:
			return true;
		default:
			return false;
		}
	}
	
	
	
	/**
	 * 
	 * @param varString
	 * @param body
	 * @return
	 */

	
	
	/**
	 * Check if the expr is a substitution pattern
	 * @param value the expression
	 * @return boolean
	 */
	public static boolean isVarString (String value, int areaType) {
		boolean bool = false;
		if (DecisionConstants.AREA_CONDITION == areaType) {
			bool = isConditionVarString(value);
		} else if (DecisionConstants.AREA_ACTION == areaType) {
			bool = isActionVarString(value);
		}
		return bool;
	}
	
	/**
	 * Check if the expr is a substitution pattern
	 * @param value the expression
	 * @param columnType
	 * @return boolean
	 */
	public static boolean isVarString (String value, ColumnType columnType) {
		int areaType = (columnType == ColumnType.CONDITION || columnType == ColumnType.CUSTOM_CONDITION) ? DecisionConstants.AREA_CONDITION : DecisionConstants.AREA_ACTION;
		return isVarString(value, areaType);
	}
	
	
	
	/**
	 * Check if the expr is a substitution pattern for condition column
	 * @param value the expression
	 * @return boolean
	 */
	public static boolean isConditionVarString (String value) {
		if (value == null)
			return false;
		return COND_SUBSTITUTION_PATTERN.matcher(value).matches() || COND_SUBSTITUTION_PATTERN_BEFORE.matcher(value).matches();
	}
	
	/**
	 * Check if the expr is a substitution pattern for action column.
	 * <p>
	 * Also check that it is not an equality expression.
	 * </p>
	 * @param value the expression
	 * @return boolean
	 */
	public static boolean isActionVarString (String value) {
		if (value == null)
			return false;
		return (ACT_SUBSTITUTION_ASSIGNMENT_PATTERN.matcher(value).matches() 
				|| ACT_SUBSTITUTION_ASSIGNMENT_PATTERN_BEFORE.matcher(value).matches())
					&& !ACT_SUBSTITUTION_EQUALITY_PATTERN.matcher(value).matches() && !ACT_SUBSTITUTION_EQUALITY_PATTERN_BEFORE.matcher(value).matches();
	}
	
	
	/**
	 * Checks if the rule containing the TRV has enabled conditions other than the trv itself. 
	 * @param trvId the id of the table rule variable
	 * @param tableEModel the table model
	 * @param colType column type
	 * @return 
	 * 		true 	if any other condition is present or the column is not a condition type
	 * 		false	column is of condition type and no other enabled conditions are found in that rule
	 */
	public static boolean checkRuleForDefaultInvocation(String trvId, Table tableEModel, ColumnType colType) {
		String trvRuleId = getContainingRuleId(trvId);
		boolean defaultInvoked = true;
		if (ColumnType.CONDITION == colType
				|| ColumnType.CUSTOM_CONDITION == colType) {
			List<TableRule> rules = tableEModel.getDecisionTable().getRule();
			if (rules.isEmpty())
				return false;
			for (TableRule rule : rules) {
				if (false == rule.getId().equals(trvRuleId))
					continue;
				for (TableRuleVariable cond : rule.getCondition()) {
					if (false == cond.getId().equals(trvId) 
							&& true == cond.isEnabled()
							&& !cond.getExpr().trim().isEmpty())
						defaultInvoked = false;
				}
			}
		} else {
			defaultInvoked = false;
		}
		return defaultInvoked;
	}
	
	/**
	 * Gets the containing rule id of a given trv id
	 * @param trvId
	 * @return
	 */
	public static String getContainingRuleId(String trvId) {
		String ruleId = null;
		ruleId = trvId.substring(0, trvId.indexOf('_'));
		return ruleId;
	}
	
	public static String computeExprWithAlias(String body, TableRuleVariable trv, Table tableEModel) {
		
		String cellValue = "";
		StringTokenizer st = new StringTokenizer(body , ExpressionBodyUtil.DELIMITER);
		int tokenCounter = 1;
		int tokenCount = st.countTokens();
		String alias = getAliasFromId(trv, tableEModel);
		alias = getAliasFromSubstitution(alias);
		while (st.hasMoreTokens()){
			String token = st.nextToken().trim();
			String operator = getOperator(token);

			if (!token.startsWith(alias)) {
				if ("=".equals(operator)){
					cellValue = cellValue + alias + operator + token;
				}
				else {
					cellValue = cellValue + alias + token;
				}
			} else {
				cellValue = cellValue + token;
			}
			if (tokenCounter < tokenCount){
				cellValue = cellValue + ExpressionBodyUtil.DELIMITER + " ";
			}
			tokenCounter ++;
		}
		return cellValue;
	}
	
	public static String getAliasFromSubstitution(String alias) {

		boolean isSubstitution = (isVarString(alias, DecisionConstants.AREA_ACTION) 
				|| isVarString(alias, DecisionConstants.AREA_CONDITION));
		
		if (isSubstitution) {
        	
        	int idx = alias.length();
        	if (alias.contains(" ") && alias.indexOf(' ') < idx) {
    			idx = alias.indexOf(' ');
    		}
        	
        	for (String op : OPERATORS) {
        		if (alias.contains(op) && alias.indexOf(op) < idx) {
        			idx = alias.indexOf(op);
        		}
        	}
        	alias = alias.substring(0, idx);
        }
		
		return alias;
	}
	
	public static String getAliasFromId(TableRuleVariable trv, Table tableEModel) {

		String alias = "";
		String trvId = trv.getColId();
		Columns columns = tableEModel.getDecisionTable().getColumns();
		List<Column> cols = columns.getColumn();
		for (Column col : cols) {
			if (col.getId().equals(trvId)) {
				alias = col.getName();
			}
		}
		
		return alias;
	}
	
	public static ColumnType getColumnTypeFromId(TableRuleVariable trv, Table tableEModel) {

		ColumnType type = null;
		String trvId = trv.getColId();
		Columns columns = tableEModel.getDecisionTable().getColumns();
		List<Column> cols = columns.getColumn();
		for (Column col : cols) {
			if (col.getId().equals(trvId)) {
				type = col.getColumnType();
			}
		}
		
		return type;
	}
	
	public static String getUnusedColumnID(TableTypes tableType, DecisionTableColumnIdGenerator columnIdGen, Columns columns) {
		
		boolean found = false;
		String newColumnId = "";
		while (!found) {
			newColumnId = columnIdGen.getCoulmnId(tableType);
			List<Column> cols = columns.getColumn();
			for (Column col : cols) {
				if (col.getId().equals(newColumnId)) {
					newColumnId = "";
				}
			}
			found = (newColumnId.isEmpty()) ? false : true;
		}
		
		return newColumnId;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String getPattern(Column column) {
		String pattern = "";
		String colName = column.getName().trim();
		if (column.getColumnType() == ColumnType.CUSTOM_ACTION 
				|| column.getColumnType() == ColumnType.CUSTOM_CONDITION) {
			pattern = colName;
		} else {
			pattern = massageRawString(colName.substring(colName.indexOf(' ')), column);
		}
		return pattern;
	}
	
	public static String getFormattedString(Column column, String variables) {
		String text = getPattern(column); // get the actual expr
		String variableStringOrig = variables;
		//Canonicalize to remove redundant spaces or add few where needed.
		variables = canonicalizeExpression(variables);
		
		if (text.isEmpty() == true || variables.isEmpty() == true)
			return "";
		
		StringTokenizer st = new StringTokenizer(variables, OR.opString()); // tokenize on "||"
		StringBuilder sb = new StringBuilder();
		boolean matchesPattern = true;
		while (st.hasMoreTokens()) {
			String nextToken = st.nextToken();
			Object[] arguments = getArguments(text, canonicalizeExpression(nextToken)); // get the substitutes
			if (arguments.length == 0) {
				continue;
			}
			try {
				MessageFormat mf = new MessageFormat(text);
				Format[] formats = mf.getFormats();
				if (arguments.length == formats.length) {
					String formattedExpr = MessageFormat.format(text, arguments);
					sb.append((sb.length() == 0) ? formattedExpr : " || " + formattedExpr);
				} else {
					matchesPattern = false;
					break;
				}
			} catch(IllegalArgumentException e) {
				continue;
			}
		}
		if (!matchesPattern) {
			//If formats do not match no substitution to be done.
			sb.append(variableStringOrig);
		}
		return sb.toString();
	}
	
	private static Object[] getArguments(String varString, String body) {
		List<String> args = new ArrayList<String>();
		
		try {
			MessageFormat mf = new MessageFormat(varString);
			Object[] existingVars = mf.parse(body);
			if (existingVars.length > 0) {
				for (Object var : existingVars) {
					if (var == null) {
						return EMPTY_ARRAY;
					} 
					//Not sure why the logic below
//					else if (var.toString().trim().contains(" ") == true) {
//						return EMPTY_ARRAY;
//					}
				}
				return existingVars;
			}
		} catch (ParseException pe) {
			StringTokenizer st = new StringTokenizer(body, SUBSTITUTION_DELIM);
			while (st.hasMoreTokens()) {
				String token = st.nextToken().trim();
//				if (token.contains(" ") == false) {
					args.add(token.trim());
//				}
			}
			String[] arguments = new String[args.size()];
			args.toArray(arguments);
			return arguments;
		}
		
		return EMPTY_ARRAY;
	}
	
	public static boolean isDuplicateDTInProjectLibrary(String projectName, String vrfName, String newfqpDT, StringBuilder duplicateFile) {
		boolean duplicateFound = false;
		
		List<DesignerElement> dts = IndexUtils.getAllElements(projectName, ELEMENT_TYPES.DECISION_TABLE);
		for (DesignerElement de : dts) {
			if (de instanceof SharedDecisionTableElementImpl) {
				SharedDecisionTableElementImpl dtImpl = ((SharedDecisionTableElementImpl)de);
				TableImpl table = (TableImpl)dtImpl.getSharedImplementation();
				if ((dtImpl.getFolder() + dtImpl.getName()).equals(newfqpDT) && table.getImplements().equals(vrfName)) {
					duplicateFile.append(dtImpl.getName());
					duplicateFound = true;
					break;
				}
			}
		}
		
		return duplicateFound;
	}
	
}
