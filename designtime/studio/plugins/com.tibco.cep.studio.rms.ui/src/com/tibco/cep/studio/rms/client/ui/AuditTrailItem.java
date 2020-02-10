package com.tibco.cep.studio.rms.client.ui;

public class AuditTrailItem {

	public String reveiwer;
	public String role;
	public String comment;
	public String oldStatus;
	public String newStatus;
	public String time;

	/**
	 * @param reviewer
	 * @param comment
	 * @param oldStatus
	 * @param newStatus
	 * @param time
	 */
	public AuditTrailItem(String reviewer,
			              String role,
			              String comment, 
			              String oldStatus, 
			              String newStatus, 
			              String time) {
		
		this.reveiwer = reviewer;
		this.role = role;
		this.comment = comment;
		this.comment = comment;
		this.oldStatus = oldStatus;
		this.newStatus = newStatus;
		this.time = time;
	}
	
}
