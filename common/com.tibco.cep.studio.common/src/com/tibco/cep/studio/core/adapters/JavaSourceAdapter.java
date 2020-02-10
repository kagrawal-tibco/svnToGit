package com.tibco.cep.studio.core.adapters;

import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.java.JavaSource;

public class JavaSourceAdapter extends EntityAdapter<com.tibco.cep.designtime.core.model.java.JavaSource> implements JavaSource, ICacheableAdapter {

	public JavaSourceAdapter(com.tibco.cep.designtime.core.model.java.JavaSource adapted, Ontology emfOntology) {
		super(adapted, emfOntology);
	}

	protected com.tibco.cep.designtime.core.model.java.JavaSource getAdapted() {
		return adapted;
	}

	@Override
	public String getPackageName() {
		return adapted.getPackageName();
	}

	@Override
	public byte[] getSource() {
		return adapted.getFullSourceText();
	}
	
	

}
