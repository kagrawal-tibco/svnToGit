package com.tibco.cep.studio.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.rule.RuleSet;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.repo.ResourceProvider;
import com.tibco.cep.repo.Workspace;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.repo.provider.SharedArchiveResourceProvider;
import com.tibco.cep.repo.provider.impl.BEArchiveResourceProviderImpl;
import com.tibco.cep.repo.provider.impl.GlobalVariablesProviderImpl;
import com.tibco.cep.repo.provider.impl.JavaArchiveResourceProviderImpl;
import com.tibco.cep.repo.provider.impl.SharedArchiveResourceProviderImpl;

/**
 * 
 * @author sasahoo
 *
 */
public class EARResourceUtil {

	@SuppressWarnings("unchecked")
	public static Entity getEntity(String earFilepath, String parenEntitytPath,
			String entityPath) throws Exception {

		if (earFilepath == null) {
			throw new IllegalArgumentException(
					"ear file path can not be null::");
		}
		com.tibco.cep.designtime.model.Ontology beOntology = getBEProject(
				earFilepath).getOntology();
		if (beOntology == null) {
			return null;
		}
		RuleSet ruleSet = null;
		Collection<Entity> entityCollection = beOntology.getEntities();
		for (Object obj : entityCollection) {
			if (obj != null && obj instanceof Entity) {
				Entity entity = (Entity) obj;
				if (entity.getFullPath().equals(parenEntitytPath)) {
					ruleSet = (RuleSet) entity;
					break;
				}
			}
		}
		if (ruleSet != null) {
			List<Object> rules = ruleSet.getRules();
			for (Object obj : rules) {
				if (obj != null && obj instanceof Entity) {
					Entity entity = (Entity) obj;
					if (entity.getFullPath().equals(entityPath)) {
						return entity;
					}
				}
			}
		}
		return null;
	}

	public static BEProject getBEProject(String earFilepath) throws Exception {

//        final Workspace ws = EMFWorkspace.getInstance();
        final Workspace<?> ws = (Workspace<?>) Class.forName("com.tibco.cep.studio.core.repo.emf.EMFWorkspace")
                .getMethod("getInstance").invoke(null);

		// Clear project Hash Map
		if (ws.getProjects() != null) {
			ws.getProjects().clear();
		}
		System
				.setProperty("javax.xml.parsers.DocumentBuilderFactory",
						"com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
		List<ResourceProvider> providers = new ArrayList<ResourceProvider>(4);
		providers.add(new JavaArchiveResourceProviderImpl());
		providers.add(new SharedArchiveResourceProviderImpl());
		providers.add(new BEArchiveResourceProviderImpl());
		// added Global Variable Provider for resolving global variables for populating domain values from DB concept
		providers.add(new GlobalVariablesProviderImpl());
		BEProject project = (BEProject) ws.loadProject(providers, earFilepath);
		return project;
	}
	/**
	 * return shared archive resource provider
	 * @param beProject
	 * @return
	 */
	public static SharedArchiveResourceProvider getSharedArchiveResourceProvider(BEProject beProject){
		if (beProject == null) return null;
		LinkedHashSet<?> resourceProviers = beProject.getProviderFactory().getProviders();
		if (resourceProviers == null) return null;
		for (Object resProvider : resourceProviers){
			if (resProvider != null && resProvider.getClass() == SharedArchiveResourceProvider.class){
				return (SharedArchiveResourceProvider)resProvider;
			}
		}
		return null;
		
	}
	public static GlobalVariablesProvider getGlobalVariablesProvider(BEProject beProject){
		if (beProject == null) return null;
		LinkedHashSet<?> resourceProviders = beProject.getProviderFactory().getProviders();
		if (resourceProviders != null){
			Iterator<?> it = resourceProviders.iterator();
			while (it.hasNext()){
				Object resourceProvider = it.next();
				if (resourceProvider != null && resourceProvider.getClass() == GlobalVariablesProvider.class){
					return (GlobalVariablesProvider)resourceProvider;
				}
			}
		}
		return null;
		
	}
}
