package com.tibco.cep.studio.rms.history.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author sasahoo
 *
 */
public class RevisionDetailsItem {

	private String operation;
	
	private String artifactPath;
	
	private String artifactType;
	
	private String approvalStatus;
	
	private List<String> revisionList;
	
	/**
	 * Keep a reference to the parent
	 */
	private HistoryRevisionItem historyRevisionItem;

	public RevisionDetailsItem(String operation, 
			                   String artifactPath,
			                   String artifactType,
			                   String approvalStatus) {
		super();
		this.operation = operation;
		this.artifactPath = artifactPath;
		this.artifactType = artifactType;
		this.approvalStatus = approvalStatus;
	}

	/**
	 * @return the operation
	 */
	public final String getOperation() {
		return operation;
	}

	/**
	 * @return the artifactPath
	 */
	public final String getArtifactPath() {
		return artifactPath;
	}

	/**
	 * @return the artifactType
	 */
	public final String getArtifactType() {
		return artifactType;
	}

	/**
	 * @param operation the operation to set
	 */
	public final void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * @param artifactPath the artifactPath to set
	 */
	public final void setArtifactPath(String artifactPath) {
		this.artifactPath = artifactPath;
	}

	/**
	 * @param artifactType the artifactType to set
	 */
	public final void setArtifactType(String artifactType) {
		this.artifactType = artifactType;
	}

	/**
	 * @return the approvalStatus
	 */
	public final String getApprovalStatus() {
		return approvalStatus;
	}

	/**
	 * @param approvalStatus the approvalStatus to set
	 */
	public final void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return artifactPath.hashCode();
	}

	/**
	 * @return the historyRevisionItem
	 */
	public final HistoryRevisionItem getHistoryRevisionItem() {
		return historyRevisionItem;
	}

	/**
	 * @param historyRevisionItem the historyRevisionItem to set
	 */
	public final void setHistoryRevisionItem(HistoryRevisionItem historyRevisionItem) {
		this.historyRevisionItem = historyRevisionItem;
	}
	
	/**
	 * @param list
	 */
	public void setRevisions(List<String> list) {
		if (revisionList == null) {
			revisionList = new ArrayList<String>();
		}
		revisionList.clear();
		revisionList.addAll(list);
	}
	
	/**
	 * @return
	 */
	public String getPreviousRevision() {
		if (revisionList != null && !revisionList.isEmpty()) {
			return revisionList.get(revisionList.size() -1);
		}
		return null;
	}
	
}
