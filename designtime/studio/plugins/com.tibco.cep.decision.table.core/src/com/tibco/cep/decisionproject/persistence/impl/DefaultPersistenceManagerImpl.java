package com.tibco.cep.decisionproject.persistence.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import com.tibco.cep.decisionproject.persistence.PersistenceManager;
import com.tibco.cep.studio.core.utils.ModelUtils;
/**
 * @author rmishra
 */
public class DefaultPersistenceManagerImpl implements PersistenceManager {
	private ResourceSet rs;
    public DefaultPersistenceManagerImpl(){
    	rs = new ResourceSetImpl();
    	
    }

	public Object loadModel(String uriString, Map<?, ?> options) {
		if (uriString != null){
			if (options == null || options.size() == 0){				
				URI fileURI = URI.createFileURI(uriString);
				Resource resource = rs.getResource(fileURI, true);
				if (resource != null && resource.getContents().size() != 0){
					return resource.getContents().get(0);
				}
			}
			else {
				throw new IllegalArgumentException("URI can not be null");
				
			}
		}

		return null;
	}

	public Object loadModel(File fileLocation, Map<?, ?> options)throws Exception {
		if (fileLocation != null){
			String location = fileLocation.getAbsolutePath();
			return loadModel(location, options);
		}
		else {
			throw new IllegalArgumentException(" File can not be null");
		}
		
	}

	public void persistModel(String targetURI, Object modelObject,
			Map<?, ?> options)throws Exception {		
		if (modelObject == null || targetURI == null){
			throw new IllegalArgumentException(" target URI and Model object can not be null");
		}
		if (modelObject instanceof EObject){	
				if (options == null || options.size() == 0){ // why are we checking for null/size 0 here?				
					URI fileURI = URI.createFileURI(targetURI);
					Resource resource = rs.createResource(fileURI);
					if (resource != null){
						resource.getContents().add((EObject)modelObject);
						try {
							resource.save(ModelUtils.getPersistenceOptions());  // G11N encoding changes
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
	
				}
				else {
					
				}
		
		}
		else {
			
		}
		}
	



	public void persistModel(File targetFile, Object modelObject,
			Map<?, ?> options) throws Exception{
		if (targetFile != null){
			String targetLocation = targetFile.getAbsolutePath();
			persistModel(targetLocation, modelObject, options);

		}
		else {
			throw new IllegalArgumentException("Target file can not be null");
		}

	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decisionproject.persistence.PersistenceManager#loadModel(java.io.InputStream, java.util.Map)
	 */
	public EObject loadModel(final InputStream is, 
			                 final Map<?, ?> options)
			throws Exception {
		if (is == null) {
			throw new IllegalArgumentException("Stream cannot be null");
		}
		Resource resource = new ResourceImpl();
		resource.load(is, options);
		EList<EObject> contents = resource.getContents();
		if (!contents.isEmpty()) {
			return contents.get(0);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decisionproject.persistence.PersistenceManager#saveModel(org.eclipse.emf.ecore.EObject, java.io.OutputStream, java.util.Map)
	 */
	public void saveModel(final EObject object, 
			              final OutputStream os,
			              Map<Object, Object> options) throws Exception {
		if (object == null || os == null) {
			throw new IllegalArgumentException("Provided arguments cannot be null");
		}
		if (options == null) {
			options = new HashMap<Object, Object>();
		}
		options.put(XMLResource.OPTION_RESOURCE_HANDLER, DecisionTableResourceHandler.INSTANCE);
		Resource resource;
		if (object.eResource() != null && object.eResource().getURI() != null) {
    		Factory factory = Resource.Factory.Registry.INSTANCE.getFactory(object.eResource().getURI());
    		resource = factory.createResource(object.eResource().getURI());
		} else {
			resource = new XMIResourceImpl();
		}
		resource.getContents().add(object);
		resource.save(os, options);
	}
}
