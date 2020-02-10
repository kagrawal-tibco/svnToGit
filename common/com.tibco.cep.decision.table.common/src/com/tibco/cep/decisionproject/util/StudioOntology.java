package com.tibco.cep.decisionproject.util;

import com.tibco.cep.decisionprojectmodel.DecisionProject;
import com.tibco.cep.designtime.CommonOntologyAdapter;
import com.tibco.cep.designtime.model.Ontology;

public class StudioOntology {
	
	private Ontology ontology = null;
	
	/**
	 * The project name
	 */
	private String projectName;
	
		
	public StudioOntology(String projectName) {
		this.projectName = projectName;
	}
	
	public Ontology getOntology() {
		try {
			ontology = new CommonOntologyAdapter(projectName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ontology;
	}

	public void projectLoaded(DecisionProject dp) {
		ontology = null;
	}
	
	/***
	 * added for command line class generation so that Ontology can be set
	 * @param ontology
	 */
	public void setOntology(Ontology ontology) {
		this.ontology = ontology;
	}
	
}
