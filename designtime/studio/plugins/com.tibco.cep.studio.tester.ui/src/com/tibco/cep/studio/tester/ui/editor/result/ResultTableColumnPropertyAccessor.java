/**
 * 
 */
package com.tibco.cep.studio.tester.ui.editor.result;

import java.util.List;

import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;

import com.tibco.cep.studio.tester.core.model.PropertyAttrsType;
import com.tibco.cep.studio.tester.core.model.PropertyModificationType;
import com.tibco.cep.studio.tester.core.model.PropertyType;
import com.tibco.cep.studio.tester.core.model.ValueType;

/**
 * @author mgujrath
 *
 */
public class ResultTableColumnPropertyAccessor implements IColumnPropertyAccessor<List<Object>>,IColumnObjectAccessor<String>{
	
	public enum TABLE_VALUE_TYPE {
		INITIAL,
		PREVIOUS,
		NEW
	}
	
	private String[] propertyNames; 
	private ResultDetailsPage page;
	private TABLE_VALUE_TYPE tableType;
	

	public ResultTableColumnPropertyAccessor(String[] propertyNames, ResultDetailsPage page, TABLE_VALUE_TYPE tableType) {
		this.propertyNames = propertyNames;
		this.page =  page;
		this.tableType = tableType;
	}
	

	@Override
	public Object getDataValue(List<Object> rowObject, int columnIndex) {

		Object dataObj = null;
		if (columnIndex + 1 <= rowObject.size()) {
			if (rowObject.get(columnIndex) instanceof PropertyType) {
				dataObj = ((PropertyAttrsType) rowObject.get(columnIndex)).getValue();
			} else if(rowObject.get(columnIndex) instanceof PropertyModificationType) {
				switch (tableType) {
				case INITIAL:
					ValueType initVal = ((PropertyModificationType)rowObject.get(columnIndex)).getInitialValue();
					dataObj = initVal == null ? null : initVal.getValue();
					break;

				case PREVIOUS:
					ValueType previousValue = ((PropertyModificationType)rowObject.get(columnIndex)).getPreviousValue();
					dataObj = previousValue == null ? "<no history available>" : previousValue.getValue();
					break;
					
				case NEW:
					dataObj = ((PropertyModificationType)rowObject.get(columnIndex)).getNewValue().getValue();
					break;
					
				default:
					break;
				}
			} else {
				dataObj = rowObject.get(columnIndex);
			}
		}

		return dataObj;

	}

	@Override
	public void setDataValue(List<Object> rowObject, int columnIndex, Object newValue) {
		
		Object oldValue  = getDataValue(rowObject, columnIndex);
		
		String rawNewCellValue = newValue == null ? "" : String.valueOf(newValue);
		String rawOldCellValue = oldValue == null ? "" : String.valueOf(oldValue);
		
		String rawCellValue = rawOldCellValue;
		
		boolean changed = false;
		
		if (!rawNewCellValue.equals(rawOldCellValue)) {
			rawCellValue = rawNewCellValue;
			changed = true;
			if (columnIndex + 1 <= rowObject.size()) {
				if (rowObject.get(columnIndex) instanceof PropertyType) {
					((PropertyType) rowObject.get(columnIndex)).setValue(rawCellValue);
				}
				if(rowObject.get(columnIndex) instanceof PropertyModificationType) {
					switch (tableType) {
					case INITIAL:
						((PropertyModificationType)rowObject.get(columnIndex)).getInitialValue().setValue(rawCellValue);
						break;

					case PREVIOUS:
						((PropertyModificationType)rowObject.get(columnIndex)).getPreviousValue().setValue(rawCellValue);
						break;
						
					case NEW:
						((PropertyModificationType)rowObject.get(columnIndex)).getNewValue().setValue(rawCellValue);
						break;
						
					default:
						break;
					}
				}
			}
		}
		
		if (changed) {
			for (Object o: rowObject) {
				if (o instanceof PropertyType) {
					if (getColumnObject(columnIndex).equals(((PropertyType) o).getName())) {
						page.getChangedPropertiesMap().put(((PropertyType) o).getName(), ((PropertyType) o).getValue());
						break;
					}
				}
				if (o instanceof PropertyModificationType) {
					if (getColumnObject(columnIndex).equals(((PropertyModificationType) o).getName())) {
						switch (tableType) {
						case INITIAL:
							page.getChangedPropertiesMap().put(((PropertyModificationType) o).getName(), ((PropertyModificationType) o).getInitialValue().getValue());
							break;

						case PREVIOUS:
							page.getChangedPropertiesMap().put(((PropertyModificationType) o).getName(), ((PropertyModificationType) o).getPreviousValue().getValue());
							break;
							
						case NEW:
							page.getChangedPropertiesMap().put(((PropertyModificationType) o).getName(), ((PropertyModificationType) o).getNewValue().getValue());
							break;
							
						default:
							break;
						}

						break;
					}
				}
			}
		}
	}

	@Override
	public int getColumnCount() {
		return propertyNames.length;
	}

	@Override
	public String getColumnProperty(int columnIndex) {
		// TODO Auto-generated method stub
		return "col" + columnIndex;
	}

	@Override
	public int getColumnIndex(String propertyName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getColumnObject(int columnIdx) {
		// TODO Auto-generated method stub
		return propertyNames[columnIdx];
	}

}
