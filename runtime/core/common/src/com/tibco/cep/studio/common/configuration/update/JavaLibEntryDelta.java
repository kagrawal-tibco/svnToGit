package com.tibco.cep.studio.common.configuration.update;

import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.impl.JavaLibEntryImpl;

public class JavaLibEntryDelta extends JavaLibEntryImpl implements
		IBuildPathEntryDelta {

	private BuildPathEntry fChangedEntry;
	private int fType;

	public JavaLibEntryDelta(BuildPathEntry entry, int type) {
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
