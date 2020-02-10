package com.tibco.cep.decision.table.codegen;

import java.util.ArrayList;
import java.util.HashMap;

import com.tibco.cep.decision.table.language.DTCellCompilationContext;
import com.tibco.cep.decision.table.language.DTLanguageUtil;




public class BaseMergedTable<CELL_KEY, COND, ACT, ROW> {

	public static final int BASE_PRIO_DECISION_TABLE = 0;
	public static final int BASE_PRIO_EXCEPTION_TABLE = 11;

	protected HashMap<CELL_KEY, COND> conditions = new HashMap<CELL_KEY, COND>();
	protected HashMap<CELL_KEY, ACT> actions = new HashMap<CELL_KEY, ACT>();
	protected ArrayList<ROW> rows = new ArrayList<ROW>();
	
	public BaseMergedTable() {
		super();
	}

	public HashMap<CELL_KEY, COND> getConditions() {
		return conditions;
	}

	public HashMap<CELL_KEY, ACT> getActions() {
		return actions;
	}

	public ArrayList<ROW> getRows() {
		return rows;
	}

	
	//TODO move somewhere else
	public static String generateAction(DTCellCompilationContext ctx) {
		String genBody = null;
		if (ctx.col.getColumnType().isCustom()) {
			genBody = DTLanguageUtil.generateCustomActionBody(ctx.getExpr());
		} else {
			genBody = DTLanguageUtil.generateNonCustomActionBody(ctx);
		}
		return genBody;
	}

	public static String generateCondition(DTCellCompilationContext ctx) {
		String condbody = ctx.getExpr();
		if (DTLanguageUtil.isStar(ctx.col.getPropertyPath(), condbody)) {
			return null;
		} else {
			String genBody = null;
			if (ctx.col.getColumnType().isCustom()) {
				genBody = DTLanguageUtil.generateCustomConditionBody(ctx.col.getPropertyPath(), condbody);
			} else {
				/**
				 * Do we need to see if the string belongs to domain model or not.
				 * I think after the code changes made it is probably not.
				 */
//				genBody = DTLanguageUtil.generateDomainBody(ctx);
//				if (genBody == null || genBody.length() <=0) {
//					genBody = DTLanguageUtil.generateNonCustomConditionBody(ctx, condbody);
//				}
				genBody = DTLanguageUtil.generateNonCustomConditionBody(ctx, condbody);
			}
			return genBody;
		}
	}
}