package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.List;

import org.eclipse.core.resources.IProject;

import com.tibco.be.model.functions.Predicate;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;

public interface MapperContext {

	IProject getProject();
	
	BEProject getBEProject();

	GlobalVariablesProvider getGlobalVariables();

	List<VariableDefinition> getDefinitions();

	Predicate getFunction();

}
