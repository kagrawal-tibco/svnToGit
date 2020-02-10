/**
 * 
 */
package com.tibco.cep.studio.core.testdata.exportHandler;

/**
 * 
 * @author sasahoo
 *
 */
public class ExportTestData {
	
	private String testDataName;
	
	private int startRow;
	
	private int endRow;
	
	private int columnPosition;
	
	
	public final String getTestDataName() {
		return testDataName;
	}

	public final void setTestDataName(String testDataName) {
		this.testDataName = testDataName;
	}

	public final int getStartRow() {
		return startRow;
	}

	public final void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public final int getEndRow() {
		return endRow;
	}

	public final void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public final int getColumnPosition() {
		return columnPosition;
	}

	public final void setColumnPosition(int columnPosition) {
		this.columnPosition = columnPosition;
	}
	
}
