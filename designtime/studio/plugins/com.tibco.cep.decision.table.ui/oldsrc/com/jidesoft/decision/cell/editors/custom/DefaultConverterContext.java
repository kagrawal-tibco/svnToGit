package com.jidesoft.decision.cell.editors.custom;

import com.jidesoft.converter.ConverterContext;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;

/**
 * 
 * @author sasahoo
 *
 */
public class DefaultConverterContext extends ConverterContext {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] values;
	private String[] descriptions;
	
	protected Object[] displayValues;
	protected Object[] expressionValues;
	protected TableTypes tableType;
		
	boolean dropdown;
	
	protected boolean actionColumn = false;
	
	
	public boolean isActionColumn() {
		return actionColumn;
	}

	/**
	 * @param name
	 * @param object
	 * @param _actionColumn
	 */
	public DefaultConverterContext(String name, 
			                       Object object, 
			                       boolean _actionColumn,
			                       TableTypes tableType) {
		super(name, object);
		this.actionColumn = _actionColumn;
		this.tableType = tableType;
	}

	/**
	 * @param name
	 */
	public DefaultConverterContext(String name) {
		super(name);
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public String[] getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String[] desc) {
		this.descriptions = desc;
	}

	public void setDisplayValues(Object[] displayValues) {
		this.displayValues = displayValues;
	}

	public Object[] getExpressionValues() {
		return expressionValues;
	}

	public void setExpressionValues(Object[] expressionValues) {
		this.expressionValues = expressionValues;
	}
	
    public Object[] getDisplayValues() {
		return displayValues;
	}

	public TableTypes getTableType() {
		return tableType;
	}

	public void setTableTypes(TableTypes tableType) {
		this.tableType = tableType;
	}
    
    
}
