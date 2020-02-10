package com.tibco.cep.decision.table.language;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.tibco.be.parser.RuleError;
import com.tibco.cep.decision.table.codegen.BaseMergedTable;
import com.tibco.cep.decision.table.codegen.DTCell;
import com.tibco.cep.decision.table.codegen.DTRow;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.utils.Messages;
import com.tibco.cep.decisionproject.util.DTModelUtil;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.studio.core.validation.dt.RuleErrorRecorder;

public class MergedTable extends BaseMergedTable<String, DTCell, DTCell, DTRow> {
	protected boolean hasErrors = false;

	public MergedTable(){}
	
	public void mergeTable(Table table, String projectName) throws IllegalArgumentException {
		mergeTable(table, null, null, null, projectName);
	}
	
	//Non-null dtv means to validate the table while merging
	//and store RuleErrors and RootNodes in the DTCell objects
	//Non-null errRecorder will report any RuleErrors from validation
	//to the errRecorder
	public void mergeTable(Table table, DTValidator dtv, RuleErrorRecorder errRecorder, RowReorderer[] reorderers, String projectName) throws IllegalArgumentException {
		if (table == null) throw new IllegalArgumentException("Table was null");
		ColumnMap dtColInfos = new ColumnMap(table.getDecisionTable()); 
		ColumnMap expColInfos = new ColumnMap(table.getExceptionTable());
		Map<String, Map<String, DomainEntry>> dmCache = new HashMap<String, Map<String, DomainEntry>>();
		
		TableRuleSet trs = table.getDecisionTable();
		//TODO separate exception table and decision table actions and conditions via table chaining or something similar
//		if (trs == null) throw new IllegalArgumentException("Decision Table was null for " + table.getImplements() + "/" + table.getName());
		if (trs != null) mergeTrs(trs, table, dtColInfos, dmCache, BASE_PRIO_DECISION_TABLE, dtv, errRecorder, reorderers != null ? reorderers[0] : null, projectName);

		trs = table.getExceptionTable();
//		if (trs == null) throw new IllegalArgumentException("Exception Table was null for " + table.getImplements() + "/" + table.getName());
		if (trs != null) mergeTrs(trs, table, expColInfos, dmCache, BASE_PRIO_EXCEPTION_TABLE, dtv, errRecorder, reorderers != null ? reorderers[1] : null, projectName);
	}
	
	
	/**
	 * For optimization this has been changed
	 * @param trs
	 * @param table
	 * @param colInfos
	 * @param dm
	 * @param basePriority
	 * @param dtv
	 * @param errRecorder
	 */
	private void mergeTrs(TableRuleSet trs, Table table, ColumnMap colInfos, Map<String, Map<String, DomainEntry>> dmCache, int basePriority, 
			DTValidator dtv, RuleErrorRecorder errRecorder, RowReorderer reorder, String projectName) {
		//TODO eliminate conditions that are only used on rows w/ have no actions
		//TODO way to avoid generating conditions to use as hash map keys?
		for (TableRule tr : trs.getRule()) {
			ArrayList<DTCell> rowConditions = new ArrayList<DTCell>();
			ArrayList<DTCell> rowActions = new ArrayList<DTCell>();
			Collection<TableRuleVariable> conds = reorder == null ? tr.getCondition() : reorder.reorderConditions(tr.getCondition());
			DTCellCompilationContext ctx = new DTCellCompilationContext(null,null,null);
			
			for (TableRuleVariable cond : conds) {
				if (cond != null && cond.isEnabled()) {
					ctx.trv = cond;
					if (ctx.getExpr() == null) continue;
					ctx.col = colInfos.getColumn(cond);
					if (ctx.col == null) continue;
					if (ctx.getExpr() == null || ctx.getExpr().length() <= 0) continue;
					ctx.initDomainEntryMap(projectName, dmCache);
					
					String condBody = BaseMergedTable.generateCondition(ctx);
					if (condBody == null) continue;
					condBody = condBody.trim();
					DTCell condCell = conditions.get(condBody);
					if (condCell == null) {
						condCell = new DTCell();
						conditions.put(condBody, condCell);
						validateCondition(dtv, condBody, condCell, table, tr);
					}
					rowConditions.add(condCell);
					postProblems(condCell, table, trs, tr, cond, errRecorder);
				}
			}
			
			for (TableRuleVariable action : reorder == null ? tr.getAction() : reorder.reorderActions(tr.getAction())) {
				if (action != null && action.isEnabled()) {
					ctx.trv = action;
					if (ctx.getExpr() == null) continue;
					ctx.col = colInfos.getColumn(action);
					if (ctx.col == null) continue;
					if (ctx.getExpr() == null || ctx.getExpr().length() <= 0) continue;
					ctx.initDomainEntryMap(projectName, dmCache);
					
					String actBody = BaseMergedTable.generateAction(ctx).trim();
					DTCell actCell = actions.get(actBody);
					if (actCell == null) {
						actCell = new DTCell();
						actions.put(actBody, actCell);
						validateAction(dtv, actBody, actCell, table, tr);
					}
					rowActions.add(actCell);
					postProblems(actCell, table, trs, tr, action, errRecorder);
				}
			}
			if (rowActions.size() > 0) {
				DTRow dtrow = new DTRow(rowConditions, rowActions, DTModelUtil.getRowPriority(tr)+basePriority, tr.getId());
				rows.add(dtrow);
			}
		}
	}
	
	private void validateCondition(DTValidator dtv, String body, DTCell cell, Table table, TableRule row) {
		if (dtv != null) {
			dtv.compileDTCellCondition(body, cell);
			hasErrors = hasErrors || (cell.getErrors() != null && cell.getErrors().size() > 0); 
		}
	}
	
	private void validateAction(DTValidator dtv, 
			                    String body,
			                    DTCell cell,
			                    Table table,
			                    TableRule row) {
		if (dtv != null) {
			dtv.compileDTCellAction(body, cell);
			hasErrors = hasErrors || (cell.getErrors() != null && cell.getErrors().size() > 0); 
		}
	}
	
	private void postProblems(DTCell cell, Table table, TableRuleSet trs,
			TableRule row, TableRuleVariable col, RuleErrorRecorder errRecorder) {
		if (cell.getErrors() != null && cell.getErrors().size() > 0) {
			if (errRecorder != null) {
				for (RuleError err : cell.getErrors()) {
					if (err.getMessage().contains("\"instanceof\" expected")) {
						RuleError error = null;
						if (trs.getColumns().search(col.getColId()).getPropertyType() == PROPERTY_TYPES.INTEGER_VALUE
								|| trs.getColumns().search(col.getColId()).getPropertyType() == PROPERTY_TYPES.LONG_VALUE) {
							error = new RuleError(
									Messages.getString("Number_Instance_Expected"),
									err.getPointOfFailure(), err
											.getBeginExtent(), err
											.getEndExtent(), err.getType());
						} else if (trs.getColumns().search(col.getColId()).getPropertyType() == PROPERTY_TYPES.DOUBLE_VALUE) {
							error = new RuleError(
									Messages.getString("Double_Instance_Expected"),
									err.getPointOfFailure(), err
											.getBeginExtent(), err
											.getEndExtent(), err.getType());
						} else if (trs.getColumns().search(col.getColId()).getPropertyType() == PROPERTY_TYPES.STRING_VALUE) {
							error = new RuleError(
									Messages.getString("String_Instance_Expected"),
									err.getPointOfFailure(), err
											.getBeginExtent(), err
											.getEndExtent(), err.getType());
						} else {
							
							//Get the correct property type.
							int propertyType = trs.getColumns().search(col.getColId()).getPropertyType();
							PROPERTY_TYPES propType = PROPERTY_TYPES.get(propertyType);
							String name="";
							if (null != propType) {
								name = propType.getName();
							}
							
							error = new RuleError(
									Messages.getString("Other_Instance_Expected", name),
									err.getPointOfFailure(),err.
									getBeginExtent(), err.
									getEndExtent(),	err.getType());
						}
						err = error;
					}

					errRecorder.reportError(err, table, trs, row, col);

				}
			}
		}
	}

	public boolean hasErrors() {
		return hasErrors;
	}
}