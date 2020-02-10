package com.tibco.cep.studio.common.configuration.update;

import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.impl.ProjectLibraryEntryImpl;

public class ProjectLibraryEntryDelta extends ProjectLibraryEntryImpl implements IBuildPathEntryDelta {

	private BuildPathEntry fChangedEntry;
	private int fType;

	public ProjectLibraryEntryDelta(BuildPathEntry entry, int type) {
		this.fChangedEntry = entry;
		this.fType = type;
	}

	@Override
	public BuildPathEntry getAffectedChild() {
		return fChangedEntry;
	}

	@Override
	public int getType() {
		return fType;
	}

}
