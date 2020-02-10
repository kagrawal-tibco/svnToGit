package com.tibco.cep.dashboard.psvr.mal;

import com.tibco.cep.dashboard.psvr.mal.model.MALElement;

/**
 * @author apatil
 *
 */
public interface ElementChangeListener {

	public enum OPERATION {
		ADD,
		UPDATE,
		DELETE,
		REPLACE
	}

	public void prepareForChange(MALElement element);

	public void preOp(String parentPath, MALElement child, MALElement replacement, OPERATION operation);

	public void postOp(String parentPath, MALElement child, MALElement replacement, OPERATION operation);

	public void changeAborted(MALElement element);

	public void changeComplete(MALElement element);

}