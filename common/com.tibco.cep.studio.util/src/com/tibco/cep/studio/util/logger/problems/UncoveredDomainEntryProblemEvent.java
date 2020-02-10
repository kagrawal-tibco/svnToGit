/**
 * 
 */
package com.tibco.cep.studio.util.logger.problems;

/**
 * @author aathalye
 *
 */
public class UncoveredDomainEntryProblemEvent extends ProblemEvent implements IMarkableProblemEvent, IAutofixableProblemEvent {
	
	private String uncoveredDomainValue;
	
	/**
	 * The name of the column in the table
	 */
	private String columnName;
	/**
	 * @param errorCode
	 * @param message
	 * @param resource
	 * @param location
	 * @param severity
	 */
	public UncoveredDomainEntryProblemEvent(String errorCode, String message,
			Object resource, String uncoveredDomainValue, String columnName, int severity) {
		super(errorCode, message, resource, -1, severity);
		this.uncoveredDomainValue = uncoveredDomainValue;
		this.columnName = columnName;
	}

	/**
	 * @param errorCode
	 * @param message
	 * @param details
	 * @param resource
	 * @param severity
	 */
	public UncoveredDomainEntryProblemEvent(String errorCode, String message,
			String details, Object resource, int severity) {
		super(errorCode, message, details, resource, severity);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param projectName
	 * @param errorCode
	 * @param message
	 * @param details
	 * @param resource
	 * @param location
	 * @param severity
	 */
	public UncoveredDomainEntryProblemEvent(String projectName,
			String errorCode, String message, String details, Object resource,
			int location, int severity) {
		super(projectName, errorCode, message, details, resource, location, severity);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the uncoveredDomainValue
	 */
	public final String getUncoveredDomainValue() {
		return uncoveredDomainValue;
	}

	/**
	 * @return the columnName
	 */
	public final String getColumnName() {
		return columnName;
	}
	
	
}
