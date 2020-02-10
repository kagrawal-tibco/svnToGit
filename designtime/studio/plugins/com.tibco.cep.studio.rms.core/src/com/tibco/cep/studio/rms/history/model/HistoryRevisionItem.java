package com.tibco.cep.studio.rms.history.model;


/**
 * 
 * @author sasahoo
 * @author aathalye
 *
 */
public class HistoryRevisionItem {

	private String revisionId;
	
	private String checkinActions;
	
	private String author;
	
	private String checkinTime;
	
	private String checkinComments;

	private RevisionDetailsItem[] revisionDetails;
	
	/**
	 * 
	 * @param revisionId
	 * @param checkinActions
	 * @param author
	 * @param checkinTime
	 * @param checkinComments
	 * @param revisionDetails
	 */
	public HistoryRevisionItem(String revisionId,
			                   String checkinActions,
			                   String author, 
			                   String checkinTime, 
			                   String checkinComments,
			                   RevisionDetailsItem[] revisionDetails) {
		
		this.revisionId = revisionId;
		this.checkinActions = checkinActions;
		this.author = author;
		this.checkinTime = checkinTime;
		this.checkinComments = checkinComments;
		this.revisionDetails = revisionDetails;
		
		for (RevisionDetailsItem revisionDetailsItem : revisionDetails) {
			revisionDetailsItem.setHistoryRevisionItem(this);
		}
	}


	/**
	 * @return the revision
	 */
	public final String getRevisionId() {
		return revisionId;
	}


	/**
	 * @return the action
	 */
	public final String getCheckinActions() {
		return checkinActions;
	}


	/**
	 * @return the author
	 */
	public final String getAuthor() {
		return author;
	}


	/**
	 * @return the date
	 */
	public final String getCheckinTime() {
		return checkinTime;
	}


	/**
	 * @return the message
	 */
	public final String getCheckinComments() {
		return checkinComments;
	}


	/**
	 * @param revisionId the revisionId to set
	 */
	public final void setRevisionId(String revisionId) {
		this.revisionId = revisionId;
	}


	/**
	 * @param action the action to set
	 */
	public final void setCheckinActions(String checkinActions) {
		this.checkinActions = checkinActions;
	}


	/**
	 * @param author the author to set
	 */
	public final void setAuthor(String author) {
		this.author = author;
	}


	/**
	 * @param checkinTime the checkinTime to set
	 */
	public final void setCheckinTime(String checkinTime) {
		this.checkinTime = checkinTime;
	}


	/**
	 * @param checkinComments the checkinComments to set
	 */
	public final void setCheckinComments(String checkinComments) {
		this.checkinComments = checkinComments;
	}


	/**
	 * @return the revisionDetails
	 */
	public final RevisionDetailsItem[] getRevisionDetails() {
		return revisionDetails;
	}


	/**
	 * @param revisionDetails the revisionDetails to set
	 */
	public final void setRevisionDetails(RevisionDetailsItem[] revisionDetails) {
		this.revisionDetails = revisionDetails;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return revisionId.hashCode();
	}
}
