package com.tibco.cep.decisionproject.persistence;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
/**
 * @author rmishra
 */
public interface PersistenceManager {
	/**
	 * persist model to the target location
	 * @param targetLocation
	 * @param modelObject
	 * @param options 
	 */
	public void persistModel(String targetLocation, Object modelObject, Map<?, ?> options)throws Exception;
	
	
	/**
	 * load model from file system
	 * @param fileLocation
	 * @param options
	 * @return
	 */
	public Object loadModel(String fileLocation, Map<?, ?> options)throws Exception;
	
	/**
	 * 
	 * @param targetLocation
	 * @param modelObject
	 * @param options
	 */
	
	public void persistModel(File targetLocation, Object modelObject, Map<?, ?> options)throws Exception;
	
	/**
	 * 
	 * @param fileLocation
	 * @param options
	 * @return
	 */
	
	public Object loadModel(File fileLocation, Map<?, ?> options)throws Exception;	
	
	/**
	 * <p>
	 * Meant for loading an <b>EMF</b> model.
	 * </p>
	 * @param is: The <tt>InputStream</tt> to load the resource from
	 * @param options: The options meant for loading.
	 * @return the <tt>EObject</tt>
	 * @throws Exception if there was some problem while loading
	 */
	public EObject loadModel(InputStream is, Map<?, ?> options) throws Exception;
	
	/**
	 * <p>
	 * Meant for persisting an <b>EMF</b> model.
	 * </p>
	 * @param object to persist
	 * @param os:The <tt>OutputStream</tt> to save the resource to
	 * @param options: The options meant for persistence.
	 * @throws Exception if there was some problem while persisting
	 */
	public void saveModel(EObject object, OutputStream os, Map<Object, Object> options) throws Exception;

}
