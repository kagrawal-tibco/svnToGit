package com.tibco.cep.studio.decision.table.providers;

import java.util.ArrayList;

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.studio.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;

/** 
 * Data provider class to obtain the names of the columns in the
 * column header.
 * 
 * @author rhollom
 *
 */
public class DTHeaderDataProvider implements IDataProvider {

	private TableRuleSet ruleSet;

	private boolean showColumnAlias = DecisionTableUIPlugin.getDefault()
			.getPreferenceStore().getBoolean(PreferenceConstants.SHOW_COLUMN_ALIAS);
	
	public DTHeaderDataProvider(TableRuleSet ruleSet) {
		this.ruleSet = ruleSet;
	}

	@Override
	public int getColumnCount() {
		Columns columns = ruleSet.getColumns();
		if (columns != null) {
			return columns.getColumn().size();
		}
		return 0;
	}

	@Override
	public Object getDataValue(int col, int row) {
		Columns columns = ruleSet.getColumns();
		Column currColumn = columns.getColumn().get(col);
		if (columns != null) {
			if (showColumnAlias) {
				if (currColumn.getAlias() != null && !currColumn.getAlias().isEmpty()) {
					return currColumn.getAlias();
				}
				return currColumn.getName();
			}
			else {
				return currColumn.getName();	
			}
		}
		return "";
	}

	@Override
	public int getRowCount() {
		return 1;
	}

	@Override
	public void setDataValue(int arg0, int arg1, Object arg2) {
		throw new UnsupportedOperationException();
	}

	public ArrayList<String> getColumnNames() {
		ArrayList<String> columnNames = new ArrayList<>();
		Columns columns = ruleSet.getColumns();
		for (int colNum = 0; colNum < getColumnCount(); colNum++) {
			Column currColumn = columns.getColumn().get(colNum);
			columnNames.add(currColumn.getName());
		}
		return columnNames;
	}

	/**
	 * @param col index
	 * @return Column
	 */
	public Column getColumn(int col) {
		Columns columns = ruleSet.getColumns();
		return columns.getColumn().get(col);
	}
}