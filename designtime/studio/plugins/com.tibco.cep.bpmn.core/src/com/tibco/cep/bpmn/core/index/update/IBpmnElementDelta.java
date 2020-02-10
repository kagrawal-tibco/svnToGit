package com.tibco.cep.bpmn.core.index.update;

import org.eclipse.emf.ecore.EObject;

public interface IBpmnElementDelta {
	public static final int ADDED = 0;
	public static final int CHANGED = 1;
	public static final int REMOVED = 2;
	
	public EObject getAffectedChild();
	
	/**
	 * Returns the type of this delta element (ADDED, CHANGED, or REMOVED)
	 * @return
	 */
	public int getType();
}
