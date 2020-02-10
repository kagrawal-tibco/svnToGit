package com.tibco.cep.studio.util.logger.problems;

import java.util.HashMap;

public class ProblemEvent {

	protected String 	errorCode;
	protected String 	message;
	protected String 	details;
	protected Object 	resource;
	protected int 	severity;
	protected int 	location = 0;
	//This will be needed henceforth
	protected String projectName;
	
	protected HashMap<String, Object> data;
	
	public static final int INFO 	= 0;
	public static final int WARNING = 1;
	public static final int ERROR 	= 2;

	private static String[] severities = { "Info", "Warning", "Error" };
	
	/**
	 * @param projectName The project in which the said resource for using this view exists
	 * @param errorCode the error code of the problem
	 * @param message the description of the problem
	 * @param details additional information about this problem
	 * @param resource the resource on which this problem occurs
	 * @param location the location/line number of the problem
	 * @param severity the severity of the problem, one of INFO, WARNING, or ERROR
	 */
	public ProblemEvent(String projectName, String errorCode, String message,
			String details, Object resource, int location, int severity) {
		this.projectName = projectName;
		this.errorCode = errorCode;
		this.message = message;
		this.details = details;
		this.resource = resource;
		this.location = location;
		this.severity = severity;
	}
	
	/**
	 * 
	 * @param errorCode the error code of the problem
	 * @param message the description of the problem
	 * @param resource the resource on which this problem occurs
	 * @param location the location/line number of the problem
	 * @param severity the severity of the problem, one of INFO, WARNING, or ERROR
	 */
	public ProblemEvent(String errorCode, String message,
			Object resource, int location, int severity) {
		this(null, errorCode, message, null, resource, location, severity);
	}
	
	/**
	 * 
	 * @param errorCode the error code of the problem
	 * @param message the description of the problem
	 * @param details additional information about this problem
	 * @param resource the resource on which this problem occurs
	 * @param severity the severity of the problem, one of INFO, WARNING, or ERROR
	 */
	public ProblemEvent(String errorCode, String message, String details,
			Object resource, int severity) {
		this(null, errorCode, message, details, resource, 0, severity);
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

	public Object getResource() {
		return resource;
	}

	public int getLocation() {
		return location;
	}

	public int getSeverity() {
		return severity;
	}
	
	

	/**
	 * @return the projectName
	 */
	public final String getProjectName() {
		return projectName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Problem Event:\r\n\t");

		builder.append("Severity: "+severities[getSeverity()] + "(" + getSeverity() + ")");
		builder.append("\r\n\t");

		builder.append("Message: "+getMessage());
		builder.append("\r\n\t");

		builder.append("Resource: "+getResource());
		builder.append("\r\n\t");

		builder.append("Location: "+getLocation());
		builder.append("\r\n\t");

		builder.append("Problem Code: "+getErrorCode());
		builder.append("\r\n\t");

		builder.append("Details: "+getDetails());
		builder.append("\r\n");
		
		return builder.toString();
	}
	
	/**
	 * Sets arbitrary data to this ProblemEvent, keyed by
	 * <code>key</code>.  This information is then accessible
	 * from the <code>getData</code> method, and can be useful 
	 * for tracking specific information regarding the problem itself
	 * @param key
	 * @param data
	 */
	public void setData(String key, Object data) {
		if (this.data == null) {
			this.data = new HashMap<String, Object>();
		}
		this.data.put(key, data);
	}
	
	/**
	 * Get the data that has been previously set
	 * via the <code>setData</code> method
	 * 
	 * @param key the key that was used in the <code>setData</code> call
	 * @return
	 */
	public Object getData(String key) {
		return data == null ? null : data.get(key);
	}
	
}
