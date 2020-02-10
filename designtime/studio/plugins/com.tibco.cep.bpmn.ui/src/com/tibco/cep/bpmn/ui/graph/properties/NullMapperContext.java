package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;

import com.tibco.be.model.functions.Predicate;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.studio.core.manager.GlobalVariablesMananger;
import com.tibco.cep.studio.core.repo.emf.StudioEMFProject;

public class NullMapperContext implements MapperContext {

	private static Map<IProject,NullMapperContext> instanceMap = new HashMap<IProject,NullMapperContext>();
	private IProject project;
	
	public NullMapperContext(IProject project) {
		this.project = project;
	}

	public static MapperContext getInstance(IProject project) {
		NullMapperContext instance = instanceMap.get(project);
		if(instance == null) {
			instance = new NullMapperContext(project);
			instanceMap.put(project, instance);
		}
		return instance;
	}
	
	@Override
	public GlobalVariablesProvider getGlobalVariables() {
		return GlobalVariablesMananger.getInstance().getProvider(getProject().getName());
	}

	@Override
	public IProject getProject() {
		return project;
	}
	
	@Override
	public BEProject getBEProject() {
		return new StudioEMFProject(getProject().getName());
	}
	
@Override
	public List<VariableDefinition> getDefinitions() {
		return new ArrayList<VariableDefinition>();
	}

	@Override
	public Predicate getFunction() {
		return null;
	}

}
