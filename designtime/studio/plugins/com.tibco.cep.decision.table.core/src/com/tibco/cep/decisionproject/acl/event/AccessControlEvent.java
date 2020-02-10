/**
 *
 */
package com.tibco.cep.decisionproject.acl.event;

import java.util.EventObject;

import com.tibco.cep.decisionproject.acl.AccessControlCellData;

/**
 * @author aathalye
 *
 */
public class AccessControlEvent extends EventObject {

	private static final long serialVersionUID = -123637138716L;

	private Object source;

	private String uid;

	/**
	 * @param source
	 */
	public AccessControlEvent(final Object source) {
		super(source);
		if (!(source instanceof AccessControlCellData)) {
			throw new IllegalArgumentException("Event source should be a AccessControlCellData");
		}
		AccessControlCellData acd = (AccessControlCellData)source;
		this.source = acd;
		if (!acd.getTrv().isModified()) {
			throw new IllegalArgumentException("TableRuleVariable has not been modified");
		}
	}

	/**
	 * @return the source
	 */
	public final Object getSource() {
		return source;
	}

	/**
	 * @return the uid
	 */
	public final String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public final void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @param source the source to set
	 */
	public final void setSource(Object source) {
		this.source = source;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AccessControlEvent)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		AccessControlEvent other = (AccessControlEvent)obj;
		if (this.source.equals(other.getSource())) {
			return true;
		}
		return false;
	}
}
