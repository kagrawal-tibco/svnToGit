/**
 * 
 */
package com.tibco.cep.decision.table.constraintpane;

import static com.tibco.cep.decision.table.constraintpane.Operator.AND_OP;
import static com.tibco.cep.decision.table.constraintpane.Operator.OR_OP;
import static com.tibco.cep.decision.table.language.DTLanguageUtil.COND_OPERATORS.EQUALS;

import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decisionproject.util.DTDomainUtil;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;

/**
 * @author aathalye
 *
 */
public class DecisionTableAnalyzerUtils {
	
	/**
	 * Get all domain entries for this property path.
	 * <p>
	 * Also fetches domains from super concept/event 
	 * </p>
	 * @param propertyPath
	 * @param project
	 * @return
	 */
	public static String[] getDomainValues(String propertyPath,
			                               String project,
			                               String substitutionFormat) {
		List<DomainInstance> domainInstances = 
			DTDomainUtil.getDomains(propertyPath, project);
		IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
		return DTDomainUtil.getDomainEntryStrings(domainInstances, 
				                                  propertyPath, 
				                                  project, 
												  substitutionFormat, 
												  prefStore.getBoolean(PreferenceConstants.SHOW_DOMAIN_DESCRIPTION),
												  false);
	}
	
	/**
	 * Get list of configured domains for a certain property.
	 * @param propertyPath
	 * @param project
	 * @return
	 */
	public static List<DomainInstance> getConfiguredDomains(String propertyPath,
			                                                String project) {
		List<DomainInstance> domainInstances = 
			DTDomainUtil.getDomains(propertyPath, project);
		return domainInstances;
	}
	
	/**
	 * Get the property path given a decision table column name.
	 * <p>
	 * Column name is of format &quot;&lt;alias&gt;&lt;.name&gt;
	 * </p>
	 * @param column
	 * @param table
	 * @return
	 */
	public static String getPropertyPathForColumn(String column,
			                                      DecisionTable table) {
		//This list has to be a subset of the domain list
		Cell.CellInfo cellInfo = table.getCellInfo(column);
		//Find property path
		String propertyPath = cellInfo.path;
		return propertyPath;
	}
	
	public static void highlightRows(JTable mainTable, String[] rowIndexes) {
		mainTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		ListSelectionModel selectionModel = mainTable.getSelectionModel();
		DecisionTableUIPlugin.debug(DecisionTableAnalyzerUtils.class.getName(), "Need to highlight {0} rows", rowIndexes.length);
		
		selectionModel.clearSelection();
		for (int i = 0; i < rowIndexes.length; i++) {
			int rowId = Integer.parseInt(rowIndexes[i]);
			int rowNumber = rowId;//getRowNumber((JoinTableModel) decisionTable.getModel(), rowId);
			if (rowNumber == -1) {
				// could not find the proper row number, do not select this row
				// TODO : consider writing error message
				continue;
			}
			selectionModel.addSelectionInterval(rowNumber, rowNumber);
			mainTable.getColumnModel().getSelectionModel().setSelectionInterval(0, mainTable.getColumnCount());
		}
	}
	
		
	/**
	 * Canonicalize the input expression to normalize the spaces
	 * @param expression
	 * @return
	 */
	public static String canonicalizeExpression(String expression) {
		if (expression.trim().length() == 0) {
			return "";
		}
		
		String[] rangeElements = new String[] {expression};
		//Check for ==
		String equalsString = EQUALS.opString();
		int equalsOpsIndex = 
			expression.indexOf(equalsString);
		if (equalsOpsIndex != -1) {
			rangeElements = expression.split(equalsString);
			
			StringBuilder stringBuilder = new StringBuilder();
			for (String element : rangeElements) {
				stringBuilder.append(element.trim());
			}
			return stringBuilder.toString();
		}
		int andIndex = expression.indexOf(AND_OP);
		if (andIndex != -1) {
			rangeElements = expression.split(AND_OP);
		}
		int orIndex = expression.indexOf(OR_OP);
		if (orIndex != -1) {
			String orOp = "\\|\\|";
			rangeElements = expression.split(orOp);
		}
		StringBuilder canonicalized = new StringBuilder();
		for (String rangeEle : rangeElements) {
			rangeEle = rangeEle.trim();
			char opChar = rangeEle.charAt(0);
			
			if ('>' == opChar || '<' == opChar) {
				canonicalized.append(opChar);
				//Courtesy existing code
				int index = 1;
				//Check if the next operator is = or operand
				char next = rangeEle.charAt(1);
				if ('=' == next) {
					//Concat the 2 operators
					canonicalized.append(next);
					index = 2;
				}
				String lhsOperand = rangeEle.substring(index);
				lhsOperand = lhsOperand.trim();
				//Append this with one space
				canonicalized.append(" ");
				canonicalized.append(lhsOperand);
				
			} else {
				canonicalized.append(rangeEle);
			}
			//Check if and is added
			int containsAndIndex = canonicalized.indexOf(AND_OP);
			if (andIndex != -1 && containsAndIndex == -1) {
				canonicalized.append(" ");
				canonicalized.append(AND_OP);
				canonicalized.append(" ");
			}
			//Check if || is added
			int containsORIndex = canonicalized.indexOf(OR_OP);
			if (orIndex != -1 && containsORIndex == -1) {
				canonicalized.append(" ");
				canonicalized.append(OR_OP);
				canonicalized.append(" ");
			}
		}
		return canonicalized.toString();
	}
	
	public static void setIdForQuickFix(TableRuleVariable trv, 
										String columnName,
			                            Table tableEModel, 
			                            int ruleID) {
		if (trv.getColId() == null) {
			//Get Decision Table TRS
			TableRuleSet decisionTable = tableEModel.getDecisionTable();
			//Get its columns
			Columns columns = decisionTable.getColumns();
			//Search this column
			Column column = columns.searchByName(columnName, ColumnType.CONDITION);
			if (column != null) {
				String colId = column.getId();
				String trvId = ruleID + "_" + colId;
				trv.setId(trvId);
				trv.setColId(colId);
			}
		}
	}
	
	/**
	 * <p>
	 * Searches a {@link Column} in the decision {@link TableRuleSet} for a given column Id.
	 * </p>
	 * <b>
	 * <i>
	 * To be used only from analyzer code as it searches only in decision table part.
	 * </i>
	 * </b>
	 * @param columnId
	 * @param decisionTable
	 * @return
	 */
	public static Column getColumnFromId(String columnId, TableRuleSet decisionTable) {
		//Get columns
		Columns allColumns = decisionTable.getColumns();
		//Search with this id
		return allColumns.search(columnId);
	}
}
