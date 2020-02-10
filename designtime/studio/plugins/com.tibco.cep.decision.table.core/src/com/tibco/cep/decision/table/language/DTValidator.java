package com.tibco.cep.decision.table.language;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.be.parser.RuleCompiler;
import com.tibco.be.parser.RuleError;
import com.tibco.be.parser.tree.RootNode;
import com.tibco.be.parser.tree.SourceType;
import com.tibco.cep.decision.table.codegen.DTCell;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.decisionproject.util.DTDomainUtil;
import com.tibco.cep.decisionproject.util.StudioOntology;
import com.tibco.cep.decisionprojectmodel.DecisionProject;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.Compilable;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.SharedDecisionTableElement;
import com.tibco.cep.studio.core.index.model.impl.SharedDecisionTableElementImpl;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.core.utils.ModelUtils.DecisionTableUtils.TableRuleInfo;
import com.tibco.cep.studio.core.validation.dt.DecisionTableErrorRecorder;
import com.tibco.cep.studio.util.StudioConfig;


public class DTValidator extends RuleCompiler {
	
	public static final String OPTIMIZE_VALIDATION = "bui.table.validate.optimize";
	
	protected static final List<RuleError> NO_ERRORS = new ArrayList<RuleError>(0);
	
	protected Ontology ontology = null;

	
	protected DTValidator(Compilable c, Ontology o) {
		super(c, o);
		ontology = o;
	}
	
	public static DTValidator newDTValidator(Implementation table, 
			                                 Ontology ontology, 
			                                 DecisionTableErrorRecorder errRecorder) {
		if (ontology == null) {
			RuleError err = new RuleError("Could not create DTValidator: Ontology is null.", RuleError.INTERNAL_TYPE);
			errRecorder.reportError(err);
			return null;
		}
		if (table == null) {
			RuleError err = new RuleError("Could not create DTValidator: Table is null", RuleError.INTERNAL_TYPE);
			errRecorder.reportError(err);
			return null;
		}
		Compilable rf = compilableForImpl(table, ontology);
		if (rf == null) {
			RuleError err = new RuleError("Could not create DTValidator: " + table.getImplements() + " not found in Ontology.", RuleError.INTERNAL_TYPE);
			errRecorder.reportError(err);
			return null;
		}
		return new DTValidator(rf, ontology);
	}
	
	public static Compilable compilableForImpl(final Implementation table, final Ontology ontology) {
		if (table == null || ontology == null) return null;
		String impl = table.getImplements();
		if (impl == null) return null;
		return ontology.getRuleFunction(impl);
	}
	
	public void reset(Implementation table) {
		Compilable rf = compilableForImpl(table, ontology);
		reset(rf, ontology);
	}
		
	/**
	 * Changed for optimization
	 * @param col
	 * @param expr
	 * @return
	 */
	public List<RuleError> checkConditionExpression(DTCellCompilationContext ctx) {
		ArrayList<RuleError> errorList = new ArrayList<RuleError>();
		compileConditionExpression(ctx, errorList, null);
		return errorList;
	}
	
	public void compileConditionExpression(DTCellCompilationContext ctx, 
			                               List<RuleError> errorList, 
			                               List<RootNode> rootNodes) {
		Column col = ctx.col;
		String expr = ctx.getExpr();
		if (col == null) return;
		if (col.getColumnType().isCustom()) {
			//for(String body : exp.getBodies()) {
			if (expr == null || expr.length() <=0) return;
			if (DTLanguageUtil.isStar(col.getName(), expr)) return;
			String genBody = DTLanguageUtil.generateCustomConditionBody(col.getName(), expr);
			//if just validating, skip re-validating an already validated body
			//don't check cache if the call to compile... needs to be done anyway

			RootNode rootNode = compileStandaloneExpression(new StringReader(genBody), errorList, SourceType.DT_CONDITION_CELL);
			if (rootNodes != null && rootNode != null) rootNodes.add(rootNode);
			//}
		} else {
			/**
			 * Commented this code for the time-being to avoid
			 * compilation errors resulting from domain usage.
			 */
//			String dmBody = DTLanguageUtil.generateDomainBody(ctx);
//			if (dmBody != null && dmBody.length() > 0) {
//				if (DTLanguageUtil.isStar(col.getName(), dmBody)) return;
//				RootNode rootNode = compileStandaloneExpression(new StringReader(dmBody), errorList, SourceType.DT_CONDITION_CELL);
//				if (rootNodes != null && rootNode != null) rootNodes.add(rootNode); 	
//			} else {
				//for(String body : exp.getBodies()) {
//				if (expr == null || expr.length() <=0) return;
//				if (DTLanguageUtil.isStar(col.getName(), expr)) return;
//				String genBody = DTLanguageUtil.generateNonCustomConditionBody(ctx, expr);
//				RootNode rootNode = compileStandaloneExpression(new StringReader(genBody), errorList, SourceType.DT_CONDITION_CELL);
//				if (rootNodes != null && rootNode != null) rootNodes.add(rootNode); 	
				//}
//			}
			if (expr == null || expr.length() <=0) return;
			if (DTLanguageUtil.isStar(col.getName(), expr)) return;
			String genBody = DTLanguageUtil.generateNonCustomConditionBody(ctx, expr);
			RootNode rootNode = compileStandaloneExpression(new StringReader(genBody), errorList, SourceType.DT_CONDITION_CELL);
			if (rootNodes != null && rootNode != null) rootNodes.add(rootNode);
		}
	}

	public List<RuleError> checkActionExpression(DTCellCompilationContext ctx) {
		ArrayList<RuleError> errorList = new ArrayList<RuleError>();
		compileActionExpression(ctx, errorList, null);
		return errorList;
	}
	
	/**
	 * Changed for optimization
	 * @param col
	 * @param exp
	 * @param errorList
	 * @param rootNodes
	 */
	public void compileActionExpression(DTCellCompilationContext ctx, List<RuleError> errorList, List<RootNode> rootNodes) {
		if (ctx.col == null) return;
		if (ctx.getExpr() == null || ctx.getExpr().length() <=0) return;
		if (ctx.col.getColumnType().isCustom()) {
			String genBody = DTLanguageUtil.generateCustomActionBody(ctx.getExpr());
			List<RootNode> returnedNodes = compileActionBlock(new StringReader(genBody), errorList, true);
			if (rootNodes != null) rootNodes.addAll(returnedNodes);
		} else {
			String genBody = DTLanguageUtil.generateNonCustomActionBody(ctx);
			//Checks an action statement.  Because non custom actions always start with arg = 
			//it will always parse the statement as an assignment, even though the Statement()
			//non terminal allows many other things including for loops.
			RootNode rootNode = compileStandaloneThenStatement(new StringReader(genBody), errorList, SourceType.DT_ACTION_CELL);
			if (rootNodes != null && rootNode != null) rootNodes.add(rootNode);
		}
	}
	
	public void compileDTCellCondition(String body, DTCell cell) {
		List<RuleError> errs = new ArrayList<RuleError>();
		RootNode rn = compileStandaloneExpression(new StringReader(body), errs, SourceType.DT_CONDITION_CELL);
		if (rn != null) {
			cell.setRootNodes(new ArrayList<RootNode>(1));
			cell.getRootNodes().add(rn);
		}
		if (errs.size() > 0) cell.setErrors(errs);
	}
	
	public void compileDTCellAction(String body, DTCell cell) {
		List<RuleError> errs = new ArrayList<RuleError>();
		cell.setRootNodes(compileActionBlock(new StringReader(body), errs, true));
		if (errs.size() > 0) cell.setErrors(errs);
	}

	//if bui.table.validate.optimize is true, this will return a non-null MergedTable object
	//for use by a later deployment step.
	public static MergedTable checkTable(Table table, 
			                             String projectName, 
			                             DecisionTableErrorRecorder errRecorder) {
		StudioOntology designerOntology = new StudioOntology(projectName);
		
		checkDuplicateDTInProjectLibraries(table, projectName, errRecorder);
		
		DTValidator dtv = newDTValidator(table, designerOntology.getOntology(), errRecorder);
		boolean optimizeValidation = 
			Boolean.parseBoolean(StudioConfig.getInstance().getProperty(OPTIMIZE_VALIDATION, "true"));
		if (dtv == null) {
			//newDTValidator will add errors to errRecorder if it returns null
			return null;
		} else {
			try {
				if (optimizeValidation) {
					MergedTable mt = new MergedTable();
					//TODO must specify reorderers if the MT is reused later
					mt.mergeTable(table, dtv, errRecorder, null, projectName);
					return mt;
				} else {
					ArrayList<RuleError> errors = new ArrayList<RuleError>();
					TableRuleSet dt = table.getDecisionTable();
					TableRuleSet et = table.getExceptionTable();
					if (dt != null) {
						checkTableRuleSet(dt, table, projectName, errors, dtv);
					} else {
						errRecorder.reportError(
								new RuleError("Decision Table was null for " + table.getImplements() + "/" + table.getName(), RuleError.INTERNAL_TYPE)
								, table, dt, null, null);
					}
					if (et != null) {
						checkTableRuleSet(et, table, projectName, errors, dtv);
					} else {
						errRecorder.reportError(
								new RuleError("Exception Table was null for " + table.getImplements() + "/" + table.getName(), RuleError.INTERNAL_TYPE)
								, table, et, null, null);
					}
					for (RuleError err : errors) {
						TableErrorSource tes = (TableErrorSource)err.getSource();
						TableRuleSet trs = null;
						TableRule row = null;
						TableRuleVariable col = null;
						if (tes != null) {
							trs = tes.getTrs();
							row = tes.getRow();
							col = tes.getCol();
						}
						errRecorder.reportError(err, table, trs, row, col);
					}
				}
			} catch (RuntimeException re) {
				errRecorder.reportError(new RuleError(re.getMessage(), RuleError.INTERNAL_TYPE), table, null, null, null);
			}
			return null;
		}
	}
	
	public static void checkTable(TableRule tableRule, 
			                      Table table,
			                      TableTypes tableType,
			                      String projectName,
			                      DecisionTableErrorRecorder ruleErrorRecorder) {
		StudioOntology designerOntology = new StudioOntology(projectName);
		DTValidator dtv = newDTValidator(table, designerOntology.getOntology(), ruleErrorRecorder);
		
		TableRuleSet trs = null;
		switch (tableType) {
		case DECISION_TABLE :
			trs = table.getDecisionTable();
			break;
		case EXCEPTION_TABLE :
			trs = table.getExceptionTable();
			break;
		}
		//TableRuleSet trs = tableRuleInfo.getTrs();
		ColumnMap colInfos = new ColumnMap(trs);
		HashMap<String, Map<String, DomainEntry>> dmCache = new HashMap<String, Map<String, DomainEntry>>();
		
		DTCellCompilationContext ctx = new DTCellCompilationContext(null, null, null);
		for (TableRuleVariable cond : tableRule.getCondition()) {
			if (!cond.isEnabled()) continue;
			ctx.trv = cond;
			if (ctx.getExpr() == null) continue;
			ctx.col = colInfos.getColumn(cond);
			if (ctx.col == null) continue;
			ctx.initDomainEntryMap(projectName, dmCache);
			
			List<RuleError> condErrors = dtv.checkConditionExpression(ctx);
			TableErrorSource errSrc = null;
			if (condErrors.size() > 0) {
				errSrc = new TableErrorSource(table, trs, tableRule, cond);
			} else {
				//Remove it if there is an error present on it
				ruleErrorRecorder.clearError(cond, tableRule);
			}
			for (RuleError err : condErrors) {
				err.setSource(errSrc);
				ruleErrorRecorder.reportError(err, table, trs, tableRule, cond);
			}
		}
		
		for (TableRuleVariable act : tableRule.getAction()) {
			if (!act.isEnabled()) continue;
			ctx.trv = act;
			if (ctx.getExpr() == null) continue;
			ctx.col = colInfos.getColumn(act);
			if (ctx.col == null) continue;
			ctx.initDomainEntryMap(projectName, dmCache);
			
			List<RuleError> actErrors = dtv.checkActionExpression(ctx);
			TableErrorSource errSrc = null;
			if (actErrors.size() > 0) {
				errSrc = new TableErrorSource(table, trs, tableRule, act);
			} else {
				//Remove it if there is an error present on it
				ruleErrorRecorder.clearError(act, tableRule);
			}
			for (RuleError err : actErrors) {
				err.setSource(errSrc);
				ruleErrorRecorder.reportError(err, table, trs, tableRule, act);
			}
		}
	}
	
	/**
	 * Compile contents of only single condition/action and check for validity.
	 * @param tableRuleVariable
	 * @param tableEModel
	 * @param projectName
	 * @param errRecorder
	 */
	public static void checkTableRuleVariable(TableRuleVariable tableRuleVariable, 
                                              Table tableEModel,
                                              String projectName, 
     			                              DecisionTableErrorRecorder errRecorder) {
		String ruleId = 
			ModelUtils.DecisionTableUtils.getContainingRuleId(tableRuleVariable.getId());
		String colId = tableRuleVariable.getColId();
		StudioOntology designerOntology = new StudioOntology(projectName);
		DTValidator dtv = newDTValidator(tableEModel, designerOntology.getOntology(), errRecorder);
		TableRuleInfo tableRuleInfo = 
			ModelUtils.DecisionTableUtils.getTableRuleInfoFromId(ruleId, tableEModel);
		
		TableRuleSet trs = tableRuleInfo.getTrs();
		Columns columns = trs.getColumns();
		Column column = columns.search(colId);
		switch (column.getColumnType()) {
		case CONDITION:
		case CUSTOM_CONDITION: {
			DTCellCompilationContext ctx = new DTCellCompilationContext(tableRuleVariable, column, DTDomainUtil.getDomainEntries(projectName, column));
			List<RuleError> conderrors = dtv.checkConditionExpression(ctx);
			TableErrorSource errSrc = null;
			if (conderrors.size() > 0) {
				errSrc = new TableErrorSource(tableEModel, trs, tableRuleInfo.getTableRule(), tableRuleVariable);
			} else {
				errRecorder.clearError(tableRuleVariable, tableRuleInfo.getTableRule());
			}
			for (RuleError err : conderrors) {
				err.setSource(errSrc);
				errRecorder.reportError(err, tableEModel, trs, tableRuleInfo.getTableRule(), tableRuleVariable);
			}
			break;
		}
		case ACTION:
		case CUSTOM_ACTION: {
			DTCellCompilationContext ctx = new DTCellCompilationContext(tableRuleVariable, column, DTDomainUtil.getDomainEntries(projectName, column));
			List<RuleError> acterrors = 
				dtv.checkActionExpression(ctx);
			TableErrorSource errSrc = null;
			if (acterrors.size() > 0) {
				errSrc = new TableErrorSource(tableEModel, trs, tableRuleInfo.getTableRule(), tableRuleVariable);
			} else {
				errRecorder.clearError(tableRuleVariable, tableRuleInfo.getTableRule());
			}
			for (RuleError err : acterrors) {
				err.setSource(errSrc);
				errRecorder.reportError(err, tableEModel, trs, tableRuleInfo.getTableRule(), tableRuleVariable);
			}
			break;
		}
		}
	}
	
	//The table argument is only needed for reporting errors to the Problems view and can be left as null
	protected static void checkTableRuleSet(TableRuleSet trs, 
			                                Table table, 
			                                String projectName, 
			                                List<RuleError> errors, 
			                                DTValidator dtv) {
		ColumnMap colInfos = new ColumnMap(trs);
		Map<String, Map<String, DomainEntry>> dmCache = new HashMap<String, Map<String, DomainEntry>>();
		for (TableRule rul : trs.getRule()) {
			DTCellCompilationContext ctx = new DTCellCompilationContext(null, null, null);
			for (TableRuleVariable cond : rul.getCondition()) {
				if (!cond.isEnabled()) continue;
				ctx.trv = cond;
				if (ctx.getExpr() == null) continue;
				ctx.col = colInfos.getColumn(cond);
				if (ctx.col == null) continue;
				ctx.initDomainEntryMap(projectName, dmCache);
				
				List<RuleError> condErrors = dtv.checkConditionExpression(ctx);
				TableErrorSource errSrc = null;
				if (condErrors.size() > 0) errSrc = new TableErrorSource(table, trs, rul, cond);
				for (RuleError err : condErrors) {
					err.setSource(errSrc);
					errors.add(err);
				}
			}

			for (TableRuleVariable act : rul.getAction()) {
				if (!act.isEnabled()) continue;
				ctx.trv = act;
				if (ctx.getExpr() == null) continue;
				ctx.col = colInfos.getColumn(act);
				if (ctx.col == null) continue;
				ctx.initDomainEntryMap(projectName, dmCache);
				
				List<RuleError> actErrors = dtv.checkActionExpression(ctx);
				TableErrorSource errSrc = null;
				if (actErrors.size() > 0) errSrc = new TableErrorSource(table, trs, rul, act);
				for (RuleError err : actErrors) {
					err.setSource(errSrc);
					errors.add(err);
				}
			}
		}
	}
	
	public void projectLoaded(DecisionProject dp) {
		ontology = null;
	}
	
	private static void checkDuplicateDTInProjectLibraries(Table table, String projectName, DecisionTableErrorRecorder errRecorder) {
		List<DesignerElement> tables = IndexUtils.getAllElements(projectName, ELEMENT_TYPES.DECISION_TABLE);
		
		SharedDecisionTableElement dtImpl = null;
    	Table tableImpl = null;
    	
		for(DesignerElement de : tables) {
			if (de instanceof SharedDecisionTableElement) {
				dtImpl = ((SharedDecisionTableElement)de);
				if ((dtImpl.getFolder()+dtImpl.getName()).equals(table.getPath())) {
					tableImpl = (Table) ((SharedDecisionTableElementImpl)dtImpl).getSharedImplementation();
					if (tableImpl != table && tableImpl.getImplements().equals(table.getImplements())) {
						errRecorder.reportError(new RuleError("Duplicate Decision Table '" + table.getName() + "' found.", RuleError.INTERNAL_TYPE), table, null, null, null);
						return;
					}
				}
			}
		}
	}
}