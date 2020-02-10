package com.tibco.cep.studio.core.adapters;

import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.java.JavaResource;

public class JavaResourceAdapter extends EntityAdapter<com.tibco.cep.designtime.core.model.java.JavaResource> implements JavaResource, ICacheableAdapter {

	public JavaResourceAdapter(com.tibco.cep.designtime.core.model.java.JavaResource adapted, Ontology emfOntology) {
		super(adapted, emfOntology);
	}

	protected com.tibco.cep.designtime.core.model.java.JavaResource getAdapted() {
		return adapted;
	}

	@Override
	public String getPackageName() {
		return adapted.getPackageName();
	}

	@Override
	public byte[] getContent() {
		return adapted.getContent();
	}
	
	@Override
	public String getExtension() {
		return adapted.getExtension();
	}

}
