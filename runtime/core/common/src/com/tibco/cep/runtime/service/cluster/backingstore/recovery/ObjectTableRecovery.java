package com.tibco.cep.runtime.service.cluster.backingstore.recovery;

import com.tibco.cep.impl.common.resource.UID;

public interface ObjectTableRecovery {
	public boolean recover() throws Exception;
	public boolean resetRecoveryTable(UID memberLeftId);
}
