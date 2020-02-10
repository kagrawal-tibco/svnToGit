/**
 *
 */
package com.tibco.cep.studio.core.repo.emf.providers;


import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.CommonOntologyAdapter;
import com.tibco.cep.designtime.CommonOntologyCache;
import com.tibco.cep.designtime.model.AbstractOntologyAdapter;
import com.tibco.cep.designtime.model.AddOnRegistry;
import com.tibco.cep.designtime.model.IAddOnLoader;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.repo.Project;
import com.tibco.cep.repo.ResourceProvider;
import com.tibco.cep.repo.provider.AbstractAddOnIndexResourceProvider;
import com.tibco.cep.repo.provider.impl.OntologyResourceProviderImpl;
import com.tibco.cep.studio.core.repo.emf.EMFProject;
import com.tibco.objectrepo.vfile.VFileStream;

/**
 * We will provide this class to maintain the standard way of getting resources
 * through providers.
 * @author aathalye
 *
 */
public class EMFOntologyResourceProvider
        extends OntologyResourceProviderImpl<EMFProject> {


	/**
	 * Runtime index
	 */
	protected Map<AddOnType, AbstractAddOnIndexResourceProvider> indexMap;

	/**
	 *
	 * @param project Project
	 */
	public EMFOntologyResourceProvider(EMFProject project) {
		super(project);
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.repo.ResourceProvider#init()
	 */
	@Override
	public void init() throws Exception {
		this.indexMap = getProject().getIndexResourceProviderMap();
//		this.ontology = (this.indexMap == null) ?
//				       new CommonOntologyAdapter(getProject().getName()) : new CommonOntologyAdapter(indexMap);
		Map<AddOn,Ontology> ontologyMap = new HashMap<AddOn,Ontology>();
		for(AddOn addon: AddOnRegistry.getInstance().getAddons()) {
			String ontologyAdapterClassName = addon.getOntologyAdapterClass();
			Class<?> ontologyAdapterClass = null;
			try {
				ontologyAdapterClass = Class.forName(ontologyAdapterClassName);
			} catch(ClassNotFoundException e) {
				Map<AddOnType,IAddOnLoader> addonmap = AddOnRegistry.getInstance().getInstalledAddOnMap();
				ontologyAdapterClass = addonmap.get(addon.getType()).getAddonClass(ontologyAdapterClassName);
			}
			Class<?> paramType = getGenericParameterizedType(ontologyAdapterClass);
			if (paramType == null) {
				paramType = EObject.class;
			}
//			if(ontologyAdapterClass.getGenericSuperclass() != null && ontologyAdapterClass.getGenericSuperclass() instanceof ParameterizedType) {
//				Type[] pTypes = ((ParameterizedType)ontologyAdapterClass.getGenericSuperclass()).getActualTypeArguments();
//				if(pTypes.length > 0) {
//					if(pTypes[0] instanceof Class) {
//						paramType = (Class<?>) pTypes[0];
//					}
//				}
//			}
			Constructor<?> constructor = ontologyAdapterClass.getConstructor(paramType);
			EObject index = indexMap.get(addon.getType()).getIndex();
			AbstractOntologyAdapter<EObject> o=  (AbstractOntologyAdapter<EObject>) constructor.newInstance(index);
			ontologyMap.put(addon, o);
		}
		this.ontology = (this.indexMap == null) ?
							new CommonOntologyAdapter(project.getName()):
								new CommonOntologyAdapter(ontologyMap);
		CommonOntologyCache.INSTANCE.addOntology(project.getName(),this.ontology);
	}

	private Class<?> getGenericParameterizedType(Class<?> ontologyAdapterClass) {
		if(ontologyAdapterClass.getGenericSuperclass() != null && ontologyAdapterClass.getGenericSuperclass() instanceof ParameterizedType) {
			Type[] pTypes = ((ParameterizedType)ontologyAdapterClass.getGenericSuperclass()).getActualTypeArguments();
			if(pTypes.length > 0) {
				if(pTypes[0] instanceof Class) {
					return (Class<?>) pTypes[0];
				}
			}
		}
		return getGenericParameterizedType(ontologyAdapterClass.getSuperclass());
	}


	@Override
	public int deserializeResource(String URI, InputStream is, Project project,
			VFileStream stream) throws Exception {
		return ResourceProvider.SUCCESS_CONTINUE;
	}

	@Override
	public boolean supportsResource(String streamName) {
		return false;
	}




}
