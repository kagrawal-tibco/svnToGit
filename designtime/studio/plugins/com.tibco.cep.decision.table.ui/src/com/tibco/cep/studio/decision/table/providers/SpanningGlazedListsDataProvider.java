package com.tibco.cep.studio.decision.table.providers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.data.ISpanningDataProvider;
import org.eclipse.nebula.widgets.nattable.edit.editor.IComboBoxDataProvider;
import org.eclipse.nebula.widgets.nattable.extension.glazedlists.GlazedListsDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.cell.DataCell;

import ca.odell.glazedlists.EventList;

import com.tibco.cep.decision.table.language.DTLanguageUtil;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decisionproject.util.DTDomainUtil;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;

/**
 * This class extends the base GlazedListsDataProvider implementation to
 * add cell spanning (i.e. merge rows) and domain model support (getting
 * values for the drop down list).
 * 
 * @author rhollom
 *
 * @param <T>
 */
public class SpanningGlazedListsDataProvider<T> extends
		GlazedListsDataProvider<T> implements ISpanningDataProvider, IComboBoxDataProvider {

	private boolean span = false;
	private String projectName;

	public SpanningGlazedListsDataProvider(String projectName, EventList<T> eventList,
			IColumnPropertyAccessor<T> columnPropertyAccessor) {
		super(eventList, columnPropertyAccessor);
		this.projectName = projectName;
	}

	@Override
	public DataCell getCellByPosition(int columnPosition, int rowPosition) {
		if (!span) {
			return new DataCell(columnPosition, rowPosition, 1, 1);
		}
		Object dataValue = super.getDataValue(columnPosition, rowPosition);
		int rowSpan = 1;
		while (rowPosition > 0) {
			//Compare values of all cell ABOVE this cell. If value is same go on incre rowSpan and decre rowPosition.
			Object aboveMeValue = super.getDataValue(columnPosition, rowPosition - 1);
			if (dataValue.equals(aboveMeValue)) {
				if (isRowSimilarUptoColumn(rowPosition, rowPosition - 1, columnPosition)) {
					//Continue only if the 2 rows currently in comparison are same upto current columnPosition.
					rowSpan ++; rowPosition--;
				}
				else {
					break;
				}
			} else {
				break;
			}
		}
		while (rowPosition + rowSpan < getRowCount()) {
			//Compare values of all cell BELOW this cell. If value is same go on incre rowSpan and rowPosition remains same.
			Object belowMeValue = super.getDataValue(columnPosition, rowPosition + rowSpan);
			if (dataValue.equals(belowMeValue)) {
				if (isRowSimilarUptoColumn(rowPosition, rowPosition + rowSpan, columnPosition)) {
					//Continue only if the 2 rows currently in comparison are same upto current columnPosition.
					rowSpan ++;
				}
				else {
					break;
				}
			} else {
				break;
			}
		}
		
		return new DataCell(columnPosition, rowPosition, 1, rowSpan);
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	/**
	 * Compares the two rows (denoted by rowPosition1 and rowPosition2) from 0th column upto the indicated column.<br/>
	 * Returns true if all values are found to be same.
	 * @param rowPosition1
	 * @param rowPosition2
	 * @param uptoColumnPosition
	 * @return
	 */
	private boolean isRowSimilarUptoColumn(int rowPosition1, int rowPosition2, int uptoColumnPosition) {
		for (int i = 0; i < uptoColumnPosition; i++) {
			Object value1 = super.getDataValue(i, rowPosition1);
			Object value2 = super.getDataValue(i, rowPosition2);
			if (!value1.equals(value2)) {
				return false;
			}
		}
		return true;
	}

	public void setSpan(boolean span) {
		this.span = span;
	}
	
	public boolean isSetSpan() {
		return span;
	}

	@Override
	public List<List<String>> getValues(int columnIndex, int rowIndex) {
		List<List<String>> values = new ArrayList<List<String>>();
		if (this.columnAccessor instanceof IColumnObjectAccessor) {
			Column columnObject = ((IColumnObjectAccessor<Column>) this.columnAccessor).getColumnObject(columnIndex);
			if (columnObject != null) {
				String propertyPath = columnObject.getPropertyPath();
				//Get Domain Instances. Will be empty for no domain case.
				String colName = columnObject.getName();
				String substitutionFormat = 
					(columnObject.isSubstitution()) ? 
							DTLanguageUtil.canonicalizeExpression(colName.substring(colName.indexOf(' '))) : "";

				//Fetch all display values
				IDecisionTableEditor editor = ((DTColumnPropertyAccessor)columnAccessor).getEditor();
				List<List<String>> domainValues = editor.getDomainValues(columnObject);
				if (domainValues != null && domainValues.size() > 0) {
					return domainValues;
				}
				List<DomainInstance> domainInstances = DTDomainUtil.getDomains(propertyPath, projectName);
				if (domainInstances != null && domainInstances.size() > 0) { 
					List<List<String>> values2 = DTDomainUtil.getDomainEntryDropDownStrings(domainInstances, 
							propertyPath, 
							projectName, 
							substitutionFormat,
							columnObject.getColumnType() == ColumnType.ACTION,
							false);
					List<String> valuesList = values2.get(0);
					for (int i = 0; i < valuesList.size(); i++) { //Rows
						List<String> row = new ArrayList<String>();
						for (List<String> column : values2) {
							row.add(column.get(i));
						}	 
						values.add(row);
					}		
				}
			}
		}
		return values;
	}

}
