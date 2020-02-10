/**
 * 
 */
package com.tibco.cep.runtime.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.GenericXMLResourceFactoryImpl;

import com.tibco.be.util.config.cdd.CddRoot;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.impl.CddPackageImpl;
import com.tibco.be.util.config.cdd.impl.ClusterConfigSerializableWrapper;

/**
 * @author mgujrath
 *
 */
public class ClusterConfigCommonUtils {
	
	public static ClusterConfig getClusterConfig(String cddXml){
		
		ResourceSet resourceSet = new ResourceSetImpl();
		CddPackageImpl.eINSTANCE.eClass();
    	final Resource.Factory.Registry registry = new ResourceFactoryRegistryImpl();
    	registry.getExtensionToFactoryMap().put("*", new GenericXMLResourceFactoryImpl());
    	resourceSet.setResourceFactoryRegistry(registry);
    	    	
    	ByteArrayInputStream inputStream = new ByteArrayInputStream(cddXml.getBytes()); 
        Resource resource = resourceSet.createResource(URI.createURI("cdd"));
        Map<Object,Object> options = new HashMap<Object,Object>();
        options.put(XMLResource.OPTION_ENCODING, "UTF-8");
        try {
			resource.load(inputStream,options);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        final EObject cdd = resource.getContents().get(0);
        if (!(cdd instanceof CddRoot)) {
            throw new IllegalArgumentException("CDD file content is not valid");
        }

        return new ClusterConfigSerializableWrapper(((CddRoot) cdd).getCluster());
		
	}

}
