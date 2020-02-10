package com.tibco.cep.dashboard.psvr.mal;

import com.tibco.cep.dashboard.psvr.mal.model.MALExternalReference;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandler;
import com.tibco.cep.dashboard.security.SecurityToken;

public abstract class ExternalReferenceProcessor extends AbstractHandler {

	public abstract Object resolveReference(SecurityToken token, MALExternalReference reference);

	public abstract MALFieldMetaInfo resolveFieldReference(SecurityToken token, MALExternalReference reference);

}