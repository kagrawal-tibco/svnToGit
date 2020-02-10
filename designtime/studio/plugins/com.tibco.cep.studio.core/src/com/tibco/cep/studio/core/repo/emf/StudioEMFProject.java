package com.tibco.cep.studio.core.repo.emf;


import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.model.AbstractOntologyAdapter;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.manager.GlobalVariablesMananger;
import com.tibco.cep.studio.core.repo.EMFTnsCache;

public class StudioEMFProject extends EMFProject {
	
	private static String projectPath;
	
	public StudioEMFProject(
            String projectName) {
		super(getRepoPath(projectName));
		this.projectConfig = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(projectName);
		init();
	}

	/**
	 * @param projectName
	 */
	public StudioEMFProject(
            String projectName,
            String projPath) {
		super(projPath);
		projectPath = projPath;
		StudioProjectConfigurationManager.getInstance().setProjectPath(projectPath);
		this.projectConfig = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(projectName);
		//super(getRepoPath(projectName));
		init();
	}
	
	@Override
	protected void init() {
		this.tnsCache = StudioCorePlugin.getCache(getName());//new TargetNamespaceCache(getName());
        ontologyProvider = new StudioEMFOntologyResourceProvider(this);
        providerFactory.registerProvider(ontologyProvider);
	}
	
	public void load() throws Exception {
		
		ontologyProvider.init();
		cacheSmElements();
	}
	
	@Override
	public GlobalVariables getGlobalVariables() {
		try {
			return GlobalVariablesMananger.getInstance().getProvider(getName());
		} catch (Exception e) {
			StudioCorePlugin.log(e);
			return null;
		}
	}
    

    @Override
	public EObject getRuntimeIndex(AddOnType type) {
    	Map<AddOn, Ontology> ontologyMap = ((StudioEMFOntologyResourceProvider)this.ontologyProvider).getOntologyMap();
    	for(Entry<AddOn, Ontology> entry:ontologyMap.entrySet()) {
    		AddOn addon = entry.getKey();
    		Ontology o = entry.getValue();
    		if(type.equals(addon.getType())) {
    			if(o instanceof AbstractOntologyAdapter) {
    				return ((AbstractOntologyAdapter)o).getIndex();
    			}
    		}
    	}
    	return null;
	}
	
	@Override
	public StudioProjectConfiguration getProjectConfiguration()	{
		return StudioProjectConfigurationManager.getInstance().getProjectConfiguration(getName());
	}
	
	@Override
	public String getRepoPath() {
		return getRepoPath(getName());
	}
	
	public static String getRepoPath(String name) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		if (project.getLocation() != null)
			return project.getLocation().toOSString();
		return projectPath;
	}
	
	@Override
	public EMFTnsCache getTnsCache() {
		return StudioCorePlugin.getCache(getName());
	}

}
