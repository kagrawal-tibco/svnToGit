package com.tibco.cep.studio.dashboard.ui.utils;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.XMIResource;

public class BEViewsURIHandler implements XMIResource.URIHandler {
	
	private URI baseURI;
	
	@Override
	public URI deresolve(URI uri) {
		if (uri.isRelative() == false && baseURI != null){
			uri = uri.deresolve(baseURI);
		}
		return uri;
	}

	@Override
	public URI resolve(URI uri) {
		throw new UnsupportedOperationException("resolve");
	}

	@Override
	public void setBaseURI(URI uri) {
		baseURI = URI.createFileURI(uri.trimSegments(1).toFileString()+"/");
	}

}