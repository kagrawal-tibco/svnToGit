package com.tibco.cep.studio.decision.table.providers;

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;

import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;

/**
 * Data provider class to obtain the rule ID to be displayed
 * in the row header layer.
 * 
 * @author rhollom
 *
 */
public class DTRowHeaderDataProvider implements IDataProvider {
	private final TableRuleSet ruleSet;
	private final ListDataProvider<TableRule> bodyDataProvider;

	public DTRowHeaderDataProvider(TableRuleSet ruleSet,
			ListDataProvider<TableRule> bodyDataProvider) {
		this.ruleSet = ruleSet;
		this.bodyDataProvider = bodyDataProvider;
	}

	@Override
	public void setDataValue(int arg0, int arg1, Object arg2) {
	}

	@Override
	public int getRowCount() {
		return ruleSet.getRule().size();
	}

	@Override
	public Object getDataValue(int columnIndex, int rowIndex) {
		if (rowIndex < 0) {
			return null;
		}
		return bodyDataProvider.getRowObject(rowIndex).getId();
	}

	@Override
	public int getColumnCount() {
		return 1;
	}
}