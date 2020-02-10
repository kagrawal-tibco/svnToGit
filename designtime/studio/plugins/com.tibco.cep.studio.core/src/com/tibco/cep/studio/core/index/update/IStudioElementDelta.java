package com.tibco.cep.studio.core.index.update;

import com.tibco.cep.studio.core.index.model.DesignerElement;

public interface IStudioElementDelta {

	public static final int ADDED = 0;
	public static final int CHANGED = 1;
	public static final int REMOVED = 2;
	
	public DesignerElement getAffectedChild();
	
	/**
	 * Returns the type of this delta element (ADDED, CHANGED, or REMOVED)
	 * @return
	 */
	public int getType();
}
