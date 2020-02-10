package com.tibco.cep.studio.core.compare.handler;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.tibco.cep.studio.core.compare.impl.EntityResourceImpl;

public class ResourceFactory extends XMIResourceFactoryImpl {

	public ResourceFactory() {
		super();
	}

	@Override
	public Resource createResource(URI uri) {
		return new EntityResourceImpl(uri);
	}

}
