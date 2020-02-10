package com.tibco.cep.studio.common.configuration.update;

import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry;

public class CustomFunctionLibEntryDelta extends JavaLibEntryDelta implements CustomFunctionLibEntry {

	public CustomFunctionLibEntryDelta(BuildPathEntry entry, int type) {
		super(entry, type);
	}

}
