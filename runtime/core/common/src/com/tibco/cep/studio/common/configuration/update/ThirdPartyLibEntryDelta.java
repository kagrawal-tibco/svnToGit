package com.tibco.cep.studio.common.configuration.update;

import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry;

public class ThirdPartyLibEntryDelta extends JavaLibEntryDelta implements ThirdPartyLibEntry {

	public ThirdPartyLibEntryDelta(BuildPathEntry entry, int type) {
		super(entry, type);
	}

}
