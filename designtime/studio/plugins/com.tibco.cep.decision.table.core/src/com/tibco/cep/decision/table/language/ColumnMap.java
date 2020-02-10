package com.tibco.cep.decision.table.language;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;

public class ColumnMap {
	//use new style of model where alias / path is stored in a Column object and referred to by id from a TableRuleVariable
	private boolean useColumnId = false;
	private Map<Object, Column> colMap = new HashMap<Object, Column>();
	
		
	public ColumnMap(TableRuleSet trs) {
		if (trs != null) {
			useColumnId = trs.getColumns() != null 
			&& trs.getColumns().getColumn() != null 
			&& trs.getColumns().getColumn().size() > 0;
			initMap(trs);
		}
	}
	
	public Column getColumn(TableRuleVariable trv) {
		if(trv == null) return null;
		if(useColumnId) {
			return colMap.get(trv.getColId());
		} else {
			return colMap.get(trv);
		}
	}
	
	
	
	private void initMap(TableRuleSet trs) {
		if(useColumnId) {
			initMapWithColumnId(trs);
		} else {
			//handle old model without Columns
			initMapWithTRV(trs);
		}
	}
	
	private void initMapWithTRV(TableRuleSet trs) {
		if(trs.getRule() == null) return;
		for(TableRule rul : trs.getRule()) {
			if(rul == null) continue;
			for(TableRuleVariable cond : rul.getCondition()) {
				if(cond == null) continue;
				//Expression exp = cond.getExpression();
				String expr = cond.getExpr();
				if(expr == null) continue;
				colMap.put(cond, columnFromTRV(cond, true));
			}
			for(TableRuleVariable action : rul.getAction()) {
				if(action == null) continue;
				//Expression exp = action.getExpression();
				String expr = action.getExpr();
				if(expr == null) continue;
				colMap.put(action, columnFromTRV(action, false));
			}
		}
	}
	
	/**
	 * Changed to TRV to get column details from there
	 * @param trv
	 * @param isCond
	 * @return
	 */
	private Column columnFromTRV(TableRuleVariable trv, boolean isCond) {
		Column col = DtmodelFactory.eINSTANCE.createColumn();
		if(isCond) {
			col.setColumnType(isCustomCondition(trv) ? ColumnType.CUSTOM_CONDITION : ColumnType.CONDITION);
		} else {
			col.setColumnType(isCustomAction(trv) ? ColumnType.CUSTOM_ACTION : ColumnType.ACTION);
		}
		col.setName(trv.getColumnName());
		col.setPropertyPath(trv.getPath());
		//col.setId
		//col.setPropertyType
		return col;
	}
	
	private void initMapWithColumnId(TableRuleSet trs) {
		Columns cols = trs.getColumns();
		if(cols != null) {
			for(Column col : cols.getColumn()) {
				if(col != null) {
					colMap.put(col.getId(), col);
				}
			}
		}
	}
	
	/**
	 * Changing it to TRV to obtain alias from there
	 * @param trv
	 * @return
	 */
	private static boolean isCustomCondition(TableRuleVariable trv) {
		return isCustom(trv, "Condition");
	}
	
	private static boolean isCustom(TableRuleVariable trv, String name) {
		String columnName = trv.getColumnName();
		if(columnName == null || columnName.equals("null") 
			|| columnName.endsWith("...") || columnName.equals("")) {
//			if(DecisionProjectLoader.LOGGER.isDebugEnabled()) {
//				String value = columnName;
//				if(value == null) value = "NULL";
//				else if(value.equals("null")) value = "\"null\"";
//				DecisionProjectLoader.LOGGER.logDebug("Custom " + name + " alias is: " + value);
//			}			
			return true;
		}
		return false;
	}
	
	/**
	 * Changing it to TRV to obtain alias from there
	 * @param trv
	 * @return
	 */
	private static boolean isCustomAction(TableRuleVariable trv) {
		return isCustom(trv, "Action");
	}
}
