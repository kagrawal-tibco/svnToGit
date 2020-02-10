package com.tibco.cep.webstudio.client.decisiontable;

import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.getColumn;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.getDataSourceField;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceSequenceField;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.tibco.cep.webstudio.client.decisiontable.model.PROPERTY_TYPES;
import com.tibco.cep.webstudio.client.decisiontable.model.TableColumn;

/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTableDataSource extends DataSource {
	
	
	/**
	 * @param columns
	 * @param listFields
	 */
	public DecisionTableDataSource(List<TableColumn> columns, ListGridField[] listFields) {
		super();
		List<DataSourceField> dataSourceFields = new ArrayList<DataSourceField>();
		DataSourceSequenceField idField = new DataSourceSequenceField("id", "ID");
		idField.setPrimaryKey(true);
		idField.setCanEdit(false);
		dataSourceFields.add(idField);
		
		for (int i = 1; i < listFields.length ; i++) {
			ListGridField field = listFields[i];
			if (field == null) {
				continue;
			}
			String columnName  = field.getName();
			TableColumn column = getColumn(columns, columnName);
			if (column != null) {
				PROPERTY_TYPES type = PROPERTY_TYPES.get(Integer.parseInt(column.getPropertyType()));
				boolean isArray = column.isArrayProperty();
				DataSourceField dsfield = getDataSourceField(type, columnName, field.getTitle(), isArray);
				dsfield.setCanFilter(true);
//				dsfield.setCanSortClientOnly(true);
//				dsfield.setRequired(true);
				dataSourceFields.add(dsfield);
			}
		}
		setFields(dataSourceFields.toArray(new DataSourceField[dataSourceFields.size()]));
		setCanMultiSort(true);
		setClientOnly(true);
	}
	

}
