package com.tibco.be.util.config.factories;


import com.tibco.be.util.config.cdd.CddRoot;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.impl.CddPackageImpl;
import com.tibco.be.util.config.cdd.impl.ClusterConfigSerializableWrapper;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.GenericXMLResourceFactoryImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
* User: Nicolas Prade
* Date: Sep 1, 2009
* Time: 11:50:54 AM
*/


public class ClusterConfigFactory
        implements ConfigFactory {



    public ClusterConfig newConfig(String uri)
    		throws IOException {
    	if (uri.startsWith("archive:")) {
    		return newConfig(URI.createURI(uri));
    	} else {
    		return newConfig(URI.createFileURI(uri));
    	}
    }

    
    private ClusterConfig newConfig(URI uri)
    		throws IOException {
    	
    	ResourceSet resourceSet = new ResourceSetImpl();
		// add file extension to registry
    	 CddPackageImpl.eINSTANCE.eClass();
    	final Resource.Factory.Registry registry = new ResourceFactoryRegistryImpl();
    	registry.getExtensionToFactoryMap().put("*", new GenericXMLResourceFactoryImpl());
    	resourceSet.setResourceFactoryRegistry(registry);
    	
        Resource resource = resourceSet.createResource(uri);
        Map<Object,Object> options = new HashMap<Object,Object>();
        options.put(XMLResource.OPTION_ENCODING, "UTF-8");
        resource.load(options);

        final EObject cdd = resource.getContents().get(0);
        if (!(cdd instanceof CddRoot)) {
            throw new IllegalArgumentException("CDD file content is not valid, at: " + uri.path());
        }

        return new ClusterConfigSerializableWrapper(((CddRoot) cdd).getCluster());
	}


}
