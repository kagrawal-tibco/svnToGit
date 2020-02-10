package com.tibco.cep.studio.core.repo.emf;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.designtime.CommonOntologyAdapter;
import com.tibco.cep.designtime.CommonOntologyCache;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.repo.provider.impl.OntologyResourceProviderImpl;
import com.tibco.cep.rt.AddonUtil;

public class StudioEMFOntologyResourceProvider extends OntologyResourceProviderImpl<EMFProject> {

	Map<AddOn,Ontology> ontologyMap = new HashMap<AddOn,Ontology>();
	public StudioEMFOntologyResourceProvider(EMFProject _project) {
		super(_project);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init() throws Exception {
		this.ontologyMap = AddonUtil.getAddOnOntologyAdapters(getProject().getName());
		this.ontology =  new CommonOntologyAdapter(ontologyMap);
		CommonOntologyCache.INSTANCE.addOntology(getProject().getName(), ontology);
	}
	
	public Map<AddOn, Ontology> getOntologyMap() {
		return ontologyMap;
	}

}
