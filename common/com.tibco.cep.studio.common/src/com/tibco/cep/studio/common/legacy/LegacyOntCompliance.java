/**
 * 
 */
package com.tibco.cep.studio.common.legacy;

import com.tibco.cep.decisionproject.ontology.AbstractResource;

/**
 * @author aathalye
 *
 */
public class LegacyOntCompliance {
	
	/**
	 * Status of this object
	 */
	private int status = OK;
	
	public static final int FAILED = 0x1;
	
	public static final int OK = 0x0;
	
	/**
	 * Source for error if any
	 */
	private AbstractResource source;
	
	/**
	 * Message if any
	 */
	private String message;
	
	/**
	 * Any details
	 */
	private String detail;
	
	/**
	 * Error Code if status = "Failed"
	 * @see #FAILED
	 */
	private int errorCode;
	
	public LegacyOntCompliance(final int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}

	public AbstractResource getSource() {
		return source;
	}

	public void setSource(AbstractResource source) {
		this.source = source;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the detail
	 */
	public final String getDetail() {
		return detail;
	}

	/**
	 * @param detail the detail to set
	 */
	public final void setDetail(String detail) {
		this.detail = detail;
	}
	
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof LegacyOntCompliance)) {
			return false;
		}
		LegacyOntCompliance other = (LegacyOntCompliance)object;
		if (!(status == other.getStatus())) {
			return false;
		}
		if (!source.getName().equals(other.getSource().getName())) {
			return false;
		}
		if (detail == null) {
			detail = "";
		}
		if (!detail.equals(other.getDetail())) {
			return false;
		}
		if (message == null) {
			message = "";
		}
		if (!message.equals(other.getMessage())) {
			return false;
		}
		return true;
	}
}
