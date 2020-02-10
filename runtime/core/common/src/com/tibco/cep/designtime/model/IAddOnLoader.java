package com.tibco.cep.designtime.model;

import com.tibco.cep.designtime.model.registry.AddOn;

/*
@author ssailapp
@date Feb 10, 2011
 */

public interface IAddOnLoader<T extends Ontology> {
	
	public AddOn getAddOn() throws Exception;
	
	public T getOntology(String projectName);
	
	Class<?> getAddonClass(String className) throws ClassNotFoundException;
	
}
