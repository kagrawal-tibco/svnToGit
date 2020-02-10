/**
 * 
 */
package com.tibco.cep.studio.core.domain.exportHandler;

/**
 * @author aathalye
 *
 */
public class DomainExportData {
	
	private String domainName;
	
	private int startRow;
	
	private int endRow;
	
	private int columnPosition;
	
	

	/**
	 * @return the domainName
	 */
	public final String getDomainName() {
		return domainName;
	}

	/**
	 * @param domainName the domainName to set
	 */
	public final void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	/**
	 * @return the startRow
	 */
	public final int getStartRow() {
		return startRow;
	}

	/**
	 * @param startRow the startRow to set
	 */
	public final void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	/**
	 * @return the endRow
	 */
	public final int getEndRow() {
		return endRow;
	}

	/**
	 * @param endRow the endRow to set
	 */
	public final void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	/**
	 * @return the columnPosition
	 */
	public final int getColumnPosition() {
		return columnPosition;
	}

	/**
	 * @param columnPosition the columnPosition to set
	 */
	public final void setColumnPosition(int columnPosition) {
		this.columnPosition = columnPosition;
	}
}
