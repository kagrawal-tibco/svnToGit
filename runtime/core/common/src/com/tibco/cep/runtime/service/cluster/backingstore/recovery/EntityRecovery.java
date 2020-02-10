package com.tibco.cep.runtime.service.cluster.backingstore.recovery;

import com.tibco.cep.impl.common.resource.UID;

public interface EntityRecovery {
	public boolean recover() throws Exception;
	public boolean resetLoadTable(UID memberLeftId);
}
