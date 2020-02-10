package com.tibco.cep.studio.decision.table.utils;

import static com.tibco.cep.decision.table.language.DTLanguageUtil.canonicalizeExpression;
import static com.tibco.cep.decision.table.language.DTLanguageUtil.COND_OPERATORS.AND;
import static com.tibco.cep.decision.table.language.DTLanguageUtil.COND_OPERATORS.OR;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.ACT_SUBSTITUTION_ASSIGNMENT_PATTERN;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.ACT_SUBSTITUTION_ASSIGNMENT_PATTERN_BEFORE;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.ACT_SUBSTITUTION_EQUALITY_PATTERN;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.ACT_SUBSTITUTION_EQUALITY_PATTERN_BEFORE;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.COND_SUBSTITUTION_PATTERN;
import static com.tibco.cep.decision.table.language.DTRegexPatterns.COND_SUBSTITUTION_PATTERN_BEFORE;

import java.io.File;
import java.text.Format;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.utils.DecisionConstants;
import com.tibco.cep.decision.table.utils.ExpressionBodyUtil;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.decisionproject.persistence.impl.DecisionProjectLoader;
import com.tibco.cep.decisionproject.util.DTConstants;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.utils.DecisionTableColumnIdGenerator;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.decision.table.editor.DTBodyLayerStack;
import com.tibco.cep.studio.decision.table.editor.DecisionTableDesignViewer;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
import com.tibco.cep.studio.decision.table.editor.input.IDecisionTableEditorInput;
import com.tibco.cep.studio.decision.table.generator.IDGenerator;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.util.StudioConfig;


@SuppressWarnings("restriction")
public class DecisionTableUtil {
	
	private static final String[] EMPTY_ARRAY = new String[0];
	
	public static boolean logged = false;
	
	public static boolean open = false;
	
	public static boolean IsEditorPopupMenuEnabled = true;
	
	public static IMenuManager manager = null;
	
	public static IStatusLineManager statusLineManager = null;
	
	private static final String SUBSTITUTION_DELIM = ";";
	
	public final static String[] OPERATORS = new String[]{"==", ">=", "<=", "<>", "!=", ">", "<", "="};
		
	// modified so that if methods of this class is called from command line then IllegalStateException will not come for work bench instance 
	static {
		try {
			org.eclipse.ui.internal.WorkbenchWindow window = (org.eclipse.ui.internal.WorkbenchWindow) PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			manager = window.getMenuBarManager();
			statusLineManager = window.getStatusLineManager();
		} catch (Exception e){
			
		}
	}

	/**
	 * Create a {@link TableRule} from a UI {@link DecisionRule}
	 * @param decisionTable
	 * @param decisionRule
	 * @return
	 */
	public static TableRule createEModelFromRule(TableRuleSet decisionTable, 
			                                     TableRule decisionRule) {
		TableRule tableRule = 
			DtmodelFactory.eINSTANCE.createTableRule();
		
		String id = decisionRule.getId();
		//Increment one more than size
		tableRule.setId(id);
		//Presumably columns created
		Columns columns = decisionTable.getColumns();
		//Get all conditions/actions
		
		EList<TableRuleVariable> conditions = decisionRule.getCondition();
		EList<TableRuleVariable> actions = decisionRule.getAction();
		
				
		List<TableRuleVariable> eModelConditions = 
			createEModelTRVs(conditions, 
					         columns, 
					         tableRule);
		List<TableRuleVariable> eModelActions = 
			createEModelTRVs(actions, 
			                 columns, 
			                 tableRule);
		
		tableRule.getCondition().addAll(eModelConditions);
		tableRule.getAction().addAll(eModelActions);
		return tableRule;
	}
	
	private static List<TableRuleVariable> createEModelTRVs(EList<TableRuleVariable> decisionEntries, 
			                                                Columns columns, 
			                                                TableRule tableRule) {
		List<TableRuleVariable> trvs = 
				new ArrayList<TableRuleVariable>(decisionEntries.size());
		for (TableRuleVariable decisionEntry : decisionEntries) {
			TableRuleVariable trv = 
				createEModelTRVFromDecisionEntries(decisionEntry, 
						                           columns, 
						                           tableRule);
			if (trv != null) {
				/**
				 * This case can come when you do undo after
				 * row removal upon removal of last column.
				 * Since the column is not present, the trv
				 * will be null.
				 */
				trvs.add(trv);
			}
		}
		return trvs;
	}
	
	public static TableRuleVariable createEModelTRVFromDecisionEntries( TableRuleVariable decisionEntry,
																		Columns columns,
																		TableRule tableRule) {
		String ruleId = tableRule.getId();
		
		//Get column name and such
		String columnName = decisionEntry.getColumnName();
		Column interestedColumn = null;
		
		//Check if column name has a prefix
		columnName = tokenizeColumnName(columnName);
		for (Column column : columns.getColumn()) {
			ColumnType columnType = column.getColumnType();
			int columnAreaType = 
				(columnType == ColumnType.CONDITION || columnType == ColumnType.CUSTOM_CONDITION) ? 0 : 1;
			/**
			 * Also necessary to compare column types
			 * since action and condition can have same column name.
			 */
			if (column.getName().intern() == columnName.intern() ) {
				interestedColumn = column;
				break;
			}
		}
		if (interestedColumn == null) {
			return null;
		}
		String columnId = interestedColumn.getId();
		String trvId = new StringBuilder(ruleId).append("_").append(columnId).toString();
		TableRuleVariable trv = null;
		
		Object cellValue = decisionEntry.getExpr();
		
		//Upon clicking add button
		if (cellValue instanceof String) {
			trv = DtmodelFactory.eINSTANCE.createTableRuleVariable();
			trv.setExpr((String)cellValue);
		} else {
			//Duplicating rules
			TableRuleVariable oldTrv = (TableRuleVariable)cellValue;
			//Duplicate this
			trv = (TableRuleVariable)EcoreUtil.copy(oldTrv);
		}
			
		trv.setColId(columnId);
		trv.setId(trvId);

		return trv;
	}
	
	
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
	 * Get position of a Row in the UI Row Order
	 * @param project
	 * @param tablePath
	 * @param tableType
	 * @param rowId
	 * @return
	 */
	public static int getRowPosition(String project,
			                            Table tableEModel,
			                            TableTypes tableType,
			                            String rowId) {
		
		TableRuleSet tableRuleSet = (tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable() : tableEModel.getExceptionTable();
		List<TableRule> tableRuleSetRules = tableRuleSet.getRule();
		int rowPosition = -1;
		for (int loop = 0; loop < tableRuleSetRules.size(); loop++) {
			TableRule rule = tableRuleSetRules.get(loop);
			if (rule.getId().equals(rowId)) {
				rowPosition = loop;
			}
		}
		return rowPosition;
	}	
	
	/**
	 * This class object is not intended to be instantiated by client
	 */
	private DecisionTableUtil() {
		// TODO Auto-generated constructor stub
	}
	
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
			DecisionTableUIPlugin.debug("File path is null");
		}

		return tableEModel;

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
		if ("false".equalsIgnoreCase(StudioConfig.getInstance().getProperty("decision.table.persistence.formatted", "true"))) {
			options.put(XMLResource.OPTION_FORMATTED, Boolean.FALSE);
		}
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
		DecisionTableUtil.logged = logged;
	}

	public static boolean isOpen() {
		return open;
	}

	public static void setOpen(boolean open) {
		DecisionTableUtil.open = open;
	}

	/*
	 * Search all open editors to see if the input is already open
	 */
	public static IEditorPart findOpenEditor(Table table) {

		try {
			IEditorReference[] references = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
			for (IEditorReference editorReference : references) {
				IEditorInput input = editorReference.getEditorInput();
				if (input instanceof IDecisionTableEditorInput) {
					Table editorTable = ((IDecisionTableEditorInput) input).getTableEModel();
					if (table.equals(editorTable)) {
						return editorReference.getEditor(true);
					}
				}
			}
		} catch (PartInitException e) {
		}
		return null;
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
			pattern = CellEditorUtils.massageRawString(colName.substring(colName.indexOf(' ')), column);
		}
		return pattern;
	}
	
	/**
	 * TODO this will be later addressed in a cleaner manner.
	 * @param column
	 * @param variables
	 * @return
	 */
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

	/**
	 * 
	 * @param varString
	 * @param body
	 * @return
	 */
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
	 * @param autoMerge
	 */
	public static void updateAutoMerge(boolean autoMerge) {
		IEditorReference[] references = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
    												.getActivePage().getEditorReferences();
		for (IEditorReference ref : references) {
			if (ref.getEditor(false) instanceof IDecisionTableEditor) {
				IDecisionTableEditor editor = (IDecisionTableEditor)ref.getEditor(false);
				DecisionTableDesignViewer viewer = editor.getDecisionTableDesignViewer();
				DTBodyLayerStack<TableRule> decisionTableBodyStack = (DTBodyLayerStack<TableRule>) ((GridLayer)viewer.getDecisionTable().getLayer()).getBodyLayer();	
				decisionTableBodyStack.setSpan(autoMerge);
				viewer.getDecisionTable().refresh();

				NatTable exceptionTable = viewer.getExceptionTable();
				if (exceptionTable != null) {
	                DTBodyLayerStack<TableRule> exceptionTableBodyStack = (DTBodyLayerStack<TableRule>) ((GridLayer)exceptionTable.getLayer()).getBodyLayer();  
	                exceptionTableBodyStack.setSpan(autoMerge);
	                viewer.getExceptionTable().refresh();
				}

	            
				viewer.setToggleMergeButton(autoMerge);
			}
		}
	}	
	/**
	 * @param showColumnFilter 
	 * 
	 */
	public static void updateColumnFilterVisibility(boolean showColumnFilter) {
		IEditorReference[] references = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
    												.getActivePage().getEditorReferences();
		for (IEditorReference ref : references) {
			if (ref.getEditor(false) instanceof IDecisionTableEditor) {
				IDecisionTableEditor editor = (IDecisionTableEditor)ref.getEditor(false);
				DecisionTableDesignViewer viewer = editor.getDecisionTableDesignViewer();
				viewer.setToggleColumnFilterButton(showColumnFilter);
				viewer.getDecisionTable().redraw();
				if (viewer.getExceptionTable() != null) {
				    viewer.getExceptionTable().redraw();
				}
			}
		}
	}
	public static String computeExprWithAlias(String body, TableRuleVariable trv, Table tableEModel,boolean showExpandedText) {
		
		StringTokenizer st = new StringTokenizer(body , ExpressionBodyUtil.DELIMITER);
		int tokenCounter = 1;
		int tokenCount = st.countTokens();
		String alias = getAliasFromId(trv, tableEModel);
		alias = getAliasFromSubstitution(alias);
		StringBuilder stringBuilder = new StringBuilder();
		while (st.hasMoreTokens()){
			String token = st.nextToken().trim();
			
			buildExprWithAlias(showExpandedText, alias, token, stringBuilder);
			if (tokenCounter < tokenCount){
				stringBuilder.append(ExpressionBodyUtil.DELIMITER).append(" ");
			}
			tokenCounter ++;
		}
		return stringBuilder.toString();
	}

	private static void buildExprWithAlias(boolean showExpandedText, String alias, String token,
			StringBuilder stringBuilder) {
		if (token.indexOf(AND.opString()) != -1) {
			String[] nodes = token.split(AND.opString());
			for (int loop = 0, length = nodes.length; loop < length; loop++) {
				buildExprWithAlias(showExpandedText, alias, nodes[loop], stringBuilder);
				stringBuilder.append(" ");
				if (loop != length - 1) {
					stringBuilder.append(AND.opString());
				}
		       	stringBuilder.append(" ");
			}
			return;
		} else if (token.indexOf(OR.opString()) != -1) {
			String[] nodes = token.split("\\|\\|");
			for (int loop = 0, length = nodes.length; loop < length; loop++) {
				buildExprWithAlias(showExpandedText, alias, nodes[loop], stringBuilder);
				stringBuilder.append(" ");
				if (loop != length - 1) {
					stringBuilder.append(OR.opString());
				}
		       	stringBuilder.append(" ");
			}
			return;
		}
		if (showExpandedText && !token.startsWith(alias)) {
			int parenIdx = 0;
			token = token.trim();
			for (int i=0; i < token.length(); i++) {
				if (token.charAt(i) != '(') {
					break;
				}
				parenIdx++;
			}
			int insertLocation = parenIdx + stringBuilder.length();
			if (parenIdx > 0) {
				stringBuilder.append(token.substring(0, parenIdx));
				token = token.substring(parenIdx);
			}
			String operator = getOperator(token);
			if (stringBuilder.length() == 0) {
				stringBuilder.append(alias);
			} else {
				stringBuilder.insert(insertLocation, alias);
			}
			stringBuilder.append(' ');
			if ("=".equals(operator)){
				stringBuilder.append(operator).append(token);
			}
			else {
				stringBuilder.append(token);
			}
		} else {
			token=token.replace(alias, "").trim();
			if(token.startsWith("=")){
				token=token.replace("=", "").trim();
			}
			stringBuilder.append(token);
		}
	}

	public static void updateApparance() {
		IEditorReference[] references = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		for (IEditorReference ref : references) {
			if (ref.getEditor(false) instanceof IDecisionTableEditor) {
				IDecisionTableEditor editor = (IDecisionTableEditor) ref
						.getEditor(false);
				DecisionTableDesignViewer viewer = editor
						.getDecisionTableDesignViewer();
				viewer.updateAppearance();
			}
		}
	}

	/**
	 * Utility method for invoking anything on Eclipse UI Thread
	 */
	public static void invokeOnDisplayThread(final Runnable runnable, boolean syncExec) {
		Display display = PlatformUI.getWorkbench().getDisplay();
		if (display == null) {
			display = Display.getDefault();
		}
		if (display != null && !display.isDisposed()) {
			if (display.getThread() != Thread.currentThread()) {
				if (syncExec) {
					display.syncExec(runnable);
				} else {
					display.asyncExec(runnable);
				}
			} else	{
				runnable.run();
			}
		}
	}

	/**
	 * Delegating workbench selection on Studio Elements selection
	 * @param object
	 * @param editor
	 */
	public static void setWorkbenchSelection(final Object object, final IEditorPart editor) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				try{
					editor.getSite().getWorkbenchWindow().getSelectionService().getSelection();
					ISelectionProvider selectionProvider = editor.getEditorSite().getSelectionProvider();
					ISelection currentSelection = selectionProvider.getSelection();
					if (currentSelection instanceof StructuredSelection) {
						Object obj = ((StructuredSelection)currentSelection).getFirstElement();
						if (obj != null && obj.equals(object)) {
							return;
						}
					}
					ISelection selection = object == null ? StructuredSelection.EMPTY : new StructuredSelection(object);
					selectionProvider.setSelection(selection);
				}
				catch(Exception e) {
					e.printStackTrace();
				}

			}});
	}
	
	public static int incrementID(TableRule decisionRule) {
		String id = decisionRule.getId();
		int newId = Integer.parseInt(id) + 1;
		setID(decisionRule, String.valueOf(newId));
		return newId;
	}
	
	public static void decrementID(TableRule decisionRule) {
		String id = decisionRule.getId();
		int newId = Integer.parseInt(id) - 1;
		setID(decisionRule, String.valueOf(newId));
	}
	
	public static void setID(TableRule decisionRule, String newId) {
		decisionRule.setId(newId);
		for ( TableRuleVariable trv : decisionRule.getCondition()) {
				String colId = trv.getColId();
				String newTrvId = newId + "_" + colId;
				trv.setId(newTrvId);
		}
		for (TableRuleVariable trv : decisionRule.getAction()) {
				String colId = trv.getColId();
				String newTrvId = newId + "_" + colId;
				trv.setId(newTrvId);
		}
	}
	
	public static List<TableRule> adjustRuleIDs(String start, TableRuleSet targetRuleSet) {
		List<TableRule> affectedObjects = new ArrayList<TableRule>();
		Table parentTable = (Table) targetRuleSet.eContainer();
		String project = parentTable.getOwnerProjectName();
		String tablePath = parentTable.getPath();
		EList<TableRule> rules = targetRuleSet.getRule();
		TableRule[] rulesByIndex = buildRuleArray(rules, start);
		int maxId = 0;
		for (int i = 0; i<rulesByIndex.length; i++) {
			TableRule tableRule = rulesByIndex[i];
			if (tableRule == null) {
				break;
			}
			maxId = Math.max(DecisionTableUtil.incrementID(tableRule), maxId);
			affectedObjects.add(tableRule);
		}
		IDGenerator<String> generator = RuleIDGeneratorManager.getIDGenerator(project, tablePath);
		int curId = Integer.parseInt(generator.getCurrentID());
		if (curId < maxId+1) {
			generator.setID(String.valueOf(maxId+1));
		}
		return affectedObjects;
	}
	
	public static List<TableRule> resetRuleIDs(TableRuleSet targetRuleSet, String project, String tablePath) {
		List<TableRule> affectedObjects = new ArrayList<TableRule>();

		EList<TableRule> rules = targetRuleSet.getRule();
		int maxId = rules.size()+1;
		for (int i = 0; i<rules.size(); i++) {
			TableRule tableRule = rules.get(i);
			if (tableRule == null) {
				break;
			}
			int curId = Integer.valueOf(tableRule.getId());
			if (curId != i+1) {
				DecisionTableUtil.setID(tableRule, String.valueOf(i+1));
				affectedObjects.add(tableRule);
			}
		}
		IDGenerator<String> generator = RuleIDGeneratorManager.getIDGenerator(project, tablePath);
		generator.setID(String.valueOf(maxId));
		return affectedObjects;
	}
	
	public static void resetRuleIDGenerator(TableRuleSet targetRuleSet) {
		Table parentTable = (Table) targetRuleSet.eContainer();
		String project = parentTable.getOwnerProjectName();
		String tablePath = parentTable.getPath();

		EList<TableRule> rules = targetRuleSet.getRule();
		int maxId = rules.size()+1;
		for (int i = 0; i<rules.size(); i++) {
			TableRule tableRule = rules.get(i);
			if (tableRule == null) {
				break;
			}
			maxId = Math.max(maxId, Integer.valueOf(tableRule.getId())+1);
		}
		IDGenerator<String> generator = RuleIDGeneratorManager.getIDGenerator(project, tablePath);
		generator.setID(String.valueOf(maxId));
	}
	
	private static TableRule[] buildRuleArray(EList<TableRule> rules, String start) {
		TableRule[] ruleArr = new TableRule[rules.size()+1]; // just guess the capacity
		int startIdx = 0;
		for (int i = 0; i<rules.size(); i++) {
			TableRule tableRule = rules.get(i);
			String id = tableRule.getId();
			int idx = Integer.parseInt(id)-1;
			ruleArr = ensureCapacity(idx+1, ruleArr);
			ruleArr[idx] = tableRule;
			if (id.equals(start)) {
				startIdx = idx;
			}
		}
		return Arrays.copyOfRange(ruleArr, startIdx, ruleArr.length);
	}

	private static TableRule[] ensureCapacity(int i, TableRule[] ruleArr) {
		if (ruleArr.length < i) {
			return Arrays.copyOf(ruleArr, i+1);
		}
		return ruleArr;
	}


}
