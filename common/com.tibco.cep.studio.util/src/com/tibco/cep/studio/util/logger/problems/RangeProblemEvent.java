/**
 * 
 */
package com.tibco.cep.studio.util.logger.problems;

/**
 * Represents range related problems in decision table
 * @author aathalye
 *
 */
public class RangeProblemEvent extends ProblemEvent implements IMarkableProblemEvent, IAutofixableProblemEvent {
	
	private Object min;
	
	private Object max;
	
	private RANGE_TYPE_PROBLEM rangeTypeProblem;
	
	/**
	 * The name of the column in the table causing the problem
	 */
	private String guiltyColumnName;
	
	/**
	 * @param projectName
	 * @param errorCode
	 * @param message
	 * @param details
	 * @param resource
	 * @param location
	 * @param severity
	 */
	public RangeProblemEvent(String projectName, String errorCode,
			String message, String details, Object resource, int location,
			int severity) {
		super(projectName, errorCode, message, details, resource, location,
				severity);
		// TODO Auto-generated constructor stub
	}
	
	public RangeProblemEvent(String projectName, 
			                 String errorCode,
			                 String message, 
			                 String details, 
			                 Object resource, 
			                 Object max2,
			                 Object min2,
			                 String guiltyColumnName,
			                 RANGE_TYPE_PROBLEM rangeTypeProblem,
			                 int location,
			                 int severity) {
		super(projectName, errorCode, message, details, resource, location,
				severity);
		this.min = max2;
		this.max = min2;
		this.guiltyColumnName = guiltyColumnName;
		this.rangeTypeProblem = rangeTypeProblem;
	}

	/**
	 * @param errorCode
	 * @param message
	 * @param resource
	 * @param location
	 * @param severity
	 */
	public RangeProblemEvent(String errorCode, String message, Object resource,
			int location, int severity) {
		super(errorCode, message, resource, location, severity);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorCode
	 * @param message
	 * @param details
	 * @param resource
	 * @param severity
	 */
	public RangeProblemEvent(String errorCode, String message, String details,
			Object resource, int severity) {
		super(errorCode, message, details, resource, severity);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the min
	 */
	public final Object getMin() {
		return min;
	}

	/**
	 * @return the max
	 */
	public final Object getMax() {
		return max;
	}

	/**
	 * @return the rangeTypeProblem
	 */
	public final RANGE_TYPE_PROBLEM getRangeTypeProblem() {
		return rangeTypeProblem;
	}

	/**
	 * @return the guiltyColumnName
	 */
	public final String getGuiltyColumnName() {
		return guiltyColumnName;
	}
}
