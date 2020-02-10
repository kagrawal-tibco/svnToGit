package com.tibco.cep.studio.tester.core.provider;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.GenericXMLResourceFactoryImpl;

import com.tibco.cep.studio.tester.emf.model.TesterResultsType;
import com.tibco.cep.studio.tester.emf.model.TesterRoot;
import com.tibco.cep.studio.tester.emf.model.impl.ModelPackageImpl;

public class TestResultMarshaller {


	/**
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	public static TesterResultsType deserialize(URI uri) throws Exception {
		ResourceSet resourceSet = new ResourceSetImpl();
		// add file extension to registry
    	 ModelPackageImpl.eINSTANCE.eClass();
		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put("test", new GenericXMLResourceFactoryImpl());
        Resource resource = resourceSet.createResource(uri);
        Map<Object,Object> options = new HashMap<Object,Object>();
        options.put(XMLResource.OPTION_ENCODING, "UTF-8");
        resource.load(options);
		TesterRoot dr = (TesterRoot) resource.getContents().get(0);
	    return dr.getTesterResults();
	}
	

	/**
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static TesterResultsType deserialize(File file) throws Exception {
		return deserialize(URI.createFileURI(file.getPath()));
	}
	
	public static TesterResultsType deserialize(String xmlString) throws UnsupportedEncodingException, Exception {
		return deserialize(xmlString.getBytes("UTF-8"));
	}
	

	/**
	 * @param resultsContents
	 * @return
	 * @throws Exception
	 */
	public static TesterResultsType deserialize(byte[] resultsContents) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(resultsContents);
		ResourceSet resourceSet = new ResourceSetImpl();
		// add file extension to registry
    	 ModelPackageImpl.eINSTANCE.eClass();
		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put("test", new GenericXMLResourceFactoryImpl());
		URI uri = URI.createURI("Tester");
        Resource resource = resourceSet.createResource(uri);
        Map<Object,Object> options = new HashMap<Object,Object>();
        options.put(XMLResource.OPTION_ENCODING, "UTF-8");
        resource.load(bais,options);
		TesterRoot dr = (TesterRoot) resource.getContents().get(0);
	    return dr.getTesterResults();
	}
	
	/**
	 * @param uri
	 * @param testerResultsType
	 * @throws IOException
	 */
	public static void serialize(URI uri, TesterResultsType testerResultsType) throws IOException {
		ResourceSet resourceSet = new ResourceSetImpl();
		// add file extension to registry
    	 ModelPackageImpl.eINSTANCE.eClass();
		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put("test", new GenericXMLResourceFactoryImpl());
        Resource resource = resourceSet.createResource(uri);
        Map<Object,Object> options = new HashMap<Object,Object>();
        options.put(XMLResource.OPTION_ENCODING, "UTF-8");
		resource.getContents().add(testerResultsType.eContainer());
		resource.save(options);
	}
	

	
}
