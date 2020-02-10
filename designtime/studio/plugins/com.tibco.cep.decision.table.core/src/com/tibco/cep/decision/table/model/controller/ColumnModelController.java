/**
 * 
 */
package com.tibco.cep.decision.table.model.controller;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.decision.table.model.notification.listener.IModelChangeListener;
import com.tibco.cep.decision.table.model.notification.listener.ModelChangeNotificationEvent;

/**
 * Controller for column model access. Other model controllers to follow suit.
 * @author aathalye
 *
 */
public class ColumnModelController extends AbstractModelController {
	
	
	/**
	 * 
	 * @param projectName
	 */
	public ColumnModelController(String projectName) {
		super(projectName);
	}

	/**
	 * 
	 * @return
	 */
	public Column createColumn() {
		return createColumnWithNotification();
	}
	
	/**
	 * 
	 * @return
	 */
	public Column createColumnWithNotification() {
		Column column = DtmodelFactory.eINSTANCE.createColumn();
		return column;
	}
	
	/**
	 * 
	 * @return
	 */
	public Columns createContainerColumn() {
		return DtmodelFactory.eINSTANCE.createColumns();
	}
	
	/**
	 * 
	 * @param containerColumns
	 * @param column
	 */
	public void addColumn(Columns containerColumns, Column column) {
		containerColumns.add(column);
	}
	
	/**
	 * 
	 * @param containerColumns
	 * @param column
	 */
	public void removeColumn(Columns containerColumns, Column column) {
		containerColumns.removeColumnById(column.getId());
	}
	
	/**
	 * 
	 * @param containerColumns
	 * @param orderIndex
	 * @param column
	 */
	public void addColumn(Columns containerColumns, int orderIndex, Column column) {
		containerColumns.add(orderIndex, column);
	}
	
	/**
	 * 
	 * @param column
	 * @param id
	 */
	public void setColumnId(Column column, String id) {
		if (column == null) {
			throw new IllegalArgumentException("Column argument cannot be null");
		}
		if (id == null) {
			throw new IllegalArgumentException("Column id cannot be null");
		}
		column.setId(id);
		firePropertyChange(column, DtmodelPackage.COLUMN__ID, id);
	}
	
	/**
	 * 
	 * @param column
	 * @param columnName
	 */
	public void setColumnName(Column column, String columnName) {
		if (column == null) {
			throw new IllegalArgumentException("Column argument cannot be null");
		}
		if (columnName == null) {
			throw new IllegalArgumentException("Column name cannot be null");
		}
		column.setName(columnName);
		firePropertyChange(column, DtmodelPackage.COLUMN__NAME, columnName);
	}
	
	/**
	 * 
	 * @param column
	 * @param columnAlias
	 */
	public void setColumnAlias(Column column, String columnAlias) {
		if (column == null) {
			throw new IllegalArgumentException("Column argument cannot be null");
		}
		if (columnAlias == null) {
			throw new IllegalArgumentException("Column alias cannot be null");
		}
		column.setAlias(columnAlias);
		firePropertyChange(column, DtmodelPackage.COLUMN__ALIAS, columnAlias);
	}
	
	/**
	 * 
	 * @param column
	 * @param defaultCellText
	 */
	public void setColumnDefaultCellText(Column column, String defaultCellText) {
		if (column == null) {
			throw new IllegalArgumentException("Column argument cannot be null");
		}
		if (defaultCellText == null) {
			throw new IllegalArgumentException("Column defaultCellText cannot be null");
		}
		column.setDefaultCellText(defaultCellText);
		firePropertyChange(column, DtmodelPackage.COLUMN__DEFAULT_CELL_TEXT, defaultCellText);
	}
	
	
	/**
	 * 
	 * @param column
	 * @param columnType
	 */
	public void setColumnType(Column column, ColumnType columnType) {
		if (column == null) {
			throw new IllegalArgumentException("Column argument cannot be null");
		}
		if (columnType == null) {
			throw new IllegalArgumentException("Column Type cannot be null");
		}
		column.setColumnType(columnType);
		firePropertyChange(column, DtmodelPackage.COLUMN_TYPE, columnType);
	}
	
	/**
	 * 
	 * @param column
	 * @param arrayProperty
	 */
	public void setArrayProperty(Column column, boolean arrayProperty) {
		if (column == null) {
			throw new IllegalArgumentException("Column argument cannot be null");
		}
		column.setArrayProperty(arrayProperty);
		firePropertyChange(column, DtmodelPackage.COLUMN__ARRAY_PROPERTY, arrayProperty);
	}
	
	/**
	 * 
	 * @param column
	 * @param substitution
	 */
	public void setSubstitution(Column column, boolean substitution) {
		if (column == null) {
			throw new IllegalArgumentException("Column argument cannot be null");
		}
		column.setSubstitution(substitution);
		firePropertyChange(column, DtmodelPackage.COLUMN__SUBSTITUTION, substitution);
	}
	
	/**
	 * 
	 * @param column
	 * @param propertyType
	 */
	public void setPropertyType(Column column, int propertyType) {
		if (column == null) {
			throw new IllegalArgumentException("Column argument cannot be null");
		}
		column.setPropertyType(propertyType);
		firePropertyChange(column, DtmodelPackage.COLUMN__PROPERTY_TYPE, propertyType);
	}
	
	/**
	 * 
	 * @param column
	 * @param propertyPath -> Will be null for custom conditions/actions
	 */
	public void setPropertyPath(Column column, String propertyPath) {
		if (column == null) {
			throw new IllegalArgumentException("Column argument cannot be null");
		}
		column.setPropertyPath(propertyPath);
		firePropertyChange(column, DtmodelPackage.COLUMN__PROPERTY_PATH, propertyPath);
	}
	
	/**
	 * 
	 * @param column
	 * @param featureId
	 * @param changedValue
	 */
	void firePropertyChange(Column column, int featureId, Object changedValue) {
		ModelChangeNotificationEvent modelChangeNotificationEvent = new ModelChangeNotificationEvent(column);
		modelChangeNotificationEvent.setFeatureId(featureId);
		modelChangeNotificationEvent.setNewValue(changedValue);
		
		for (IModelChangeListener modelChangeListener : modelNotificationsListeners) {
			modelChangeListener.notifyChanged(modelChangeNotificationEvent);
		}
	}
	
	/**
	 * 
	 * @param containerColumns
	 * @param beforeIndex
	 * @param afterIndex
	 * @return Return the {@link Column} moved.
	 */
	public Column move(Columns containerColumns, int beforeIndex, int afterIndex) {
		return containerColumns.move(afterIndex, beforeIndex);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.model.controller.AbstractModelController#adaptsClass(java.lang.Class)
	 */
	@Override
	protected boolean adaptsClass(Class<?> clazz) {
		return Column.class.isAssignableFrom(clazz);
	}
}
