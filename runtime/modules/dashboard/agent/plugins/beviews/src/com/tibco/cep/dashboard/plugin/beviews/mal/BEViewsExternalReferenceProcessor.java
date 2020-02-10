package com.tibco.cep.dashboard.plugin.beviews.mal;

import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.mal.ExternalReferenceProcessor;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.model.MALExternalReference;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;

public class BEViewsExternalReferenceProcessor extends ExternalReferenceProcessor {

	@Override
	protected void init() {

	}

	@Override
	public Object resolveReference(SecurityToken token, MALExternalReference reference) {
		return reference.getExternalReference();
	}

	@Override
	public MALFieldMetaInfo resolveFieldReference(SecurityToken token, MALExternalReference reference) {
		PropertyDefinition field = (PropertyDefinition) reference.getExternalReference();
		MALSourceElement fieldOwner = MALSourceElementCache.getInstance().getMALSourceElementByFullPath(field.getOwnerPath());
		return fieldOwner.getField(field.getName());
	}


	@Override
	protected void shutdown() throws NonFatalException {
	}

}
