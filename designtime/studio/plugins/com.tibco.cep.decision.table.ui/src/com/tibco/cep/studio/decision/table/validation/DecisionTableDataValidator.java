package com.tibco.cep.studio.decision.table.validation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.data.validate.DataValidator;
import org.eclipse.nebula.widgets.nattable.data.validate.ValidationFailedException;

import com.tibco.be.parser.RuleError;
import com.tibco.cep.decision.table.language.DTCellCompilationContext;
import com.tibco.cep.decision.table.language.DTValidator;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decisionproject.util.DTDomainUtil;
import com.tibco.cep.decisionproject.util.StudioOntology;
import com.tibco.cep.studio.core.validation.dt.DecisionTableErrorRecorder;
import com.tibco.cep.studio.core.validation.dt.DecisionTableSyntaxProblemParticipant;

/**
 * Data validator class responsible for validating individual cell/expressions
 * as they are edited, and reports an error when one is found.
 * 
 * @author rhollom
 *
 */
public final class DecisionTableDataValidator extends DataValidator {
	final class DTValidatorErrorRecorder implements DecisionTableErrorRecorder {
		List<RuleError> errors = new ArrayList<RuleError>();

		@Override
		public void reportError(RuleError rulErr) {
			this.errors.add(rulErr);
		}

		@Override
		public void reportError(RuleError rulErr, Table table, TableRuleSet trs,
				TableRule row, TableRuleVariable col) {
			this.errors.add(rulErr);
		}

		@Override
		public void reportAllErrors() {
			// TODO Auto-generated method stub

		}

		@Override
		public void clear() {
			// TODO Auto-generated method stub

		}

		@Override
		public void clearError(TableRuleVariable problemTableRuleVar,
				TableRule containingRule) {
			// TODO Auto-generated method stub

		}

		@Override
		public void clearError(
				DecisionTableSyntaxProblemParticipant problemParticipant) {
			// TODO Auto-generated method stub

		}

		public List<RuleError> getErrors() {
			return errors;
		}
	}

	private final ListDataProvider<TableRule> bodyDataProvider;
	private Table table;
	private TableRuleSet ruleSet;
	private DTValidator dtValidator;
	private DTValidatorErrorRecorder errorRecorder;

	public DecisionTableDataValidator(
			Table table, TableRuleSet ruleSet, ListDataProvider<TableRule> bodyDataProvider) {
		this.table = table;
		this.ruleSet = ruleSet;
		this.bodyDataProvider = bodyDataProvider;
	}

	public boolean validate(int columnIndex, int rowIndex, Object newValue) {
		if (newValue == null){
			return true;
		}
		TableRule rule = bodyDataProvider.getRowObject(rowIndex);
		Column column = ruleSet.getColumns().getColumn().get(columnIndex);
		EList<TableRuleVariable> trvs = null;
		if (column.getColumnType() == ColumnType.CONDITION || column.getColumnType() == ColumnType.CUSTOM_CONDITION) {
			trvs = rule.getCondition();
		} else {
			trvs = rule.getAction();
		}
		for (TableRuleVariable tableRuleVariable : trvs) {
			if (tableRuleVariable.getColId().equals(column.getId())) {
				List<RuleError> validateTableRuleVariable = validateTableRuleVariable(column, tableRuleVariable);
				if (validateTableRuleVariable.size() > 0) {
					// throwing errors during validation does not allow users to commit the value
					// we want to allow invalid values, so instead use custom labels to show errors
					throw new ValidationFailedException(validateTableRuleVariable.get(0).toString());
				}

				break;
			}
		}
		return true;
	}

	private DTValidatorErrorRecorder getErrorRecorder() {
		if (errorRecorder == null) {
			errorRecorder = new DTValidatorErrorRecorder();
		}
		return errorRecorder;
	}

	private List<RuleError> validateTableRuleVariable(Column column, TableRuleVariable tableRuleVariable) {
		String projectName = table.getOwnerProjectName();
		DTValidator dtv = getDTValidator(tableRuleVariable, projectName, getErrorRecorder());

		switch (column.getColumnType()) {
		case CONDITION:
		case CUSTOM_CONDITION: {
			DTCellCompilationContext ctx = new DTCellCompilationContext(tableRuleVariable, column, DTDomainUtil.getDomainEntries(projectName, column));
			List<RuleError> conderrors = dtv.checkConditionExpression(ctx);
			return conderrors;
		}
		case ACTION:
		case CUSTOM_ACTION: {
			DTCellCompilationContext ctx = new DTCellCompilationContext(tableRuleVariable, column, DTDomainUtil.getDomainEntries(projectName, column));
			List<RuleError> acterrors = 
					dtv.checkActionExpression(ctx);
			return acterrors;
		}
		}
		return null;
	}

	private DTValidator getDTValidator(TableRuleVariable tableRuleVariable, String projectName, DTValidatorErrorRecorder errRecorder) {
		if (dtValidator == null) {
			StudioOntology designerOntology = new StudioOntology(projectName);
			dtValidator = DTValidator.newDTValidator(table, designerOntology.getOntology(), errRecorder);
		}
		return dtValidator;
	}

}