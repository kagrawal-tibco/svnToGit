package com.tibco.cep.studio.common.configuration.update;

import com.tibco.cep.studio.common.configuration.BuildPathEntry;

public interface IBuildPathEntryDelta {

	public static final int ADDED = 0;
	public static final int CHANGED = 1;
	public static final int REMOVED = 2;
	
	public BuildPathEntry getAffectedChild();
	
	/**
	 * Returns the type of this delta element (ADDED, CHANGED, or REMOVED)
	 * @return
	 */
	public int getType();

}
