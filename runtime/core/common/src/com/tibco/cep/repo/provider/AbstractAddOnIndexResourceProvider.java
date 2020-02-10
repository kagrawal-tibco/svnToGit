package com.tibco.cep.repo.provider;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.repo.BETargetNamespaceCache;
import com.tibco.cep.repo.ResourceProvider;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;

/**
 * @author pdhar
 *
 */
public abstract class AbstractAddOnIndexResourceProvider<P extends BEProject> implements ResourceProvider {
	
	private StudioProjectConfiguration projectConfig;
	protected P project;

	public abstract AddOnType getAddOn();
	
	
	public AbstractAddOnIndexResourceProvider(P p) {
		this.project = p;
	}

	/**
	 * 
	 * @return
	 */
	public StudioProjectConfiguration getProjectConfig() {
		return projectConfig;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public BETargetNamespaceCache getEmfTnsCache() {
		return project.getTnsCache();
	}
	
	
	abstract public  <T extends EObject> T getIndex();
	
	public P getProject() {
		return this.project;
	}


	/**
	 * set index name field
	 * @param name
	 */
	abstract public void setName(String name);


	abstract public void loadProjectLibraries(StudioProjectConfiguration projectConfig) throws Exception;


	abstract public void postLoad();
	
	abstract public void preload();
	


}
