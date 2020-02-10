package com.tibco.cep.bpmn.core;

import com.tibco.cep.bpmn.model.designtime.BpmnModelCache;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessOntologyAdapter;
import com.tibco.cep.designtime.model.IAddOnLoader;
import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.rt.AbstractAddOnLoader;

/*
@author ssailapp
@date Nov 9, 2010
 */

public class ProcessAddOnLoader extends AbstractAddOnLoader<ProcessOntologyAdapter> implements IAddOnLoader<ProcessOntologyAdapter> {

	private static final String PROCESS_PLUGIN_ID = "com.tibco.cep.bpmn.common";//$NON-NLS-N$

	public ProcessAddOnLoader() {
	}
	
	@Override
	public AddOn getAddOn() throws Exception {
		return super.loadAddOn(PROCESS_PLUGIN_ID);
	}
	
	@Override
	public ProcessOntologyAdapter getOntology(String projectName) {
		if(BpmnModelCache.getInstance().containsKey(projectName)) {
			return new ProcessOntologyAdapter(BpmnModelCache.getInstance().getIndex(projectName));
		} else {
			return new ProcessOntologyAdapter(projectName);
		}
	}

//	@Override
//	public String getName() {
//		return ADDON_PROCESS;
//	}
//
//	@Override
//	public String getAgentClass() {
//		return (AGENT_TYPE_PROCESS);
//	}

}
