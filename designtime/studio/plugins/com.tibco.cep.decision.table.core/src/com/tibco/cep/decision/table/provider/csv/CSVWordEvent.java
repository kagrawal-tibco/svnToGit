/**
 * 
 */
package com.tibco.cep.decision.table.provider.csv;

import java.util.EventObject;

/**
 * @author aathalye
 *
 */
public class CSVWordEvent extends EventObject {
	
	private int columnCposition;
	
	private int rowNumber;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2548943062349087930L;

	/**
	 * @param source
	 */
	public CSVWordEvent(Object source, 
			            int rowNumber, 
			            int columnPosition) {
		super(source);
		this.rowNumber = rowNumber;
		this.columnCposition = columnPosition;
	}

	public int getColumnCposition() {
		return columnCposition;
	}

	public int getRowNumber() {
		return rowNumber;
	}
}
