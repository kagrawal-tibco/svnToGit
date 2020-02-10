package com.tibco.cep.decision.table.emf;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

public class DecisionTableResourceFactory extends XMIResourceFactoryImpl {

	public DecisionTableResourceFactory() {
		super();
	}

	@Override
	public Resource createResource(URI uri) {
		return new DecisionTableResourceImpl(uri);
	}

}
