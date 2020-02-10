package com.tibco.cep.studio.core.compare.handler;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.tibco.cep.studio.core.compare.impl.TableResourceImpl;

public class TableResourceFactory extends XMIResourceFactoryImpl {

	public TableResourceFactory() {
		super();
	}

	@Override
	public Resource createResource(URI uri) {
		return new TableResourceImpl(uri);
	}

}
