package com.tibco.cep.studio.common.resources.ecore;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

public class ANTLRResourceFactory implements Resource.Factory {

	// flag indicating whether to persist resource as xmi or as source
	public static boolean SOURCE_BASED_PERSISTENCE = false;
	
	private String projectName;
	public ANTLRResourceFactory(String projectName) {
		this.projectName= projectName;
	}

	public ANTLRResourceFactory() {
	}

	@Override
	public Resource createResource(URI uri) {
		return new ANTLRBasedEntityResource(uri, projectName);
//		return new XMIResourceImpl(uri);
	}

}
