package com.tibco.be.util.config.sharedresources.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.tibco.be.util.config.sharedresources.id.util.IdResourceFactoryImpl;
import com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage;
import com.tibco.be.util.config.sharedresources.sharedjndiconfig.util.SharedjndiconfigResourceFactoryImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.GenericXMLResourceFactoryImpl;

import com.tibco.be.util.config.sharedresources.id.IdPackage;
import com.tibco.be.util.config.sharedresources.id.IdRoot;
import com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl;
import com.tibco.be.util.config.sharedresources.sharedhttp.SharedHttpRoot;
import com.tibco.be.util.config.sharedresources.sharedhttp.impl.SharedhttpPackageImpl;
import com.tibco.be.util.config.sharedresources.sharedjmscon.SharedJmsConRoot;
import com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage;
import com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedjmsconPackageImpl;
import com.tibco.be.util.config.sharedresources.sharedjmscon.util.SharedjmsconResourceFactoryImpl;
import com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedJndiConfigRoot;
import com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.SharedjndiconfigPackageImpl;

public class SharedResourcesHelper {

    
	public static class ExtendedMetaData
            extends BasicExtendedMetaData
    {

	    private String defaultNamespace;


        public ExtendedMetaData(
	            String defaultNamespace) {
	        super();
	        this.defaultNamespace = defaultNamespace;
	    }
	    

        @Override
        public EPackage getPackage(
                String namespace)
        {
            return super.getPackage((null == namespace) ? this.defaultNamespace : namespace);
        }


        protected boolean isFeatureNamespaceMatchingLax()
        {
          return true;
        }
    }

	
    /*
	 * Load SharedJmsConnection Doc Root
	 */
	public static SharedJmsConRoot loadSharedJmsConnectionDocRoot(String uri)
			throws IOException {

		if (uri.startsWith("archive:")) {
			return loadSharedJmsConnectionDocRoot(URI.createURI(uri));
		} else {
			return loadSharedJmsConnectionDocRoot(URI.createFileURI(uri));
		}
	}

	/*
	 * Load SharedJmsConnection Doc Root
	 */
	public static SharedJmsConRoot loadSharedJmsConnectionDocRoot(URI uri)
			throws IOException {

		final ResourceSet resourceSet = new ResourceSetImpl();
		// add file extension to registry
		SharedjmsconPackageImpl.eINSTANCE.eClass();
		final Resource.Factory.Registry registry = new ResourceFactoryRegistryImpl();
        registry.getExtensionToFactoryMap().put("*", new GenericXMLResourceFactoryImpl());
        registry.getExtensionToFactoryMap().put(SharedjmsconPackageImpl.eNAME, new SharedjmsconResourceFactoryImpl());
		resourceSet.setResourceFactoryRegistry(registry);

		final Resource resource = resourceSet.createResource(uri);
		final Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");
		options.put(XMLResource.OPTION_EXTENDED_META_DATA, new ExtendedMetaData(SharedjmsconPackage.eNS_URI));

		resource.load(options);

		return (SharedJmsConRoot) resource.getContents().get(0);
	}

	/*
	 * Load SharedJmsConnection Config Object
	 */
	public static com.tibco.be.util.config.sharedresources.sharedjmscon.Config loadSharedJmsConnectionConfig(
			String uri) throws IOException {

		SharedJmsConRoot sharedJmsConRoot = loadSharedJmsConnectionDocRoot(uri);
		return sharedJmsConRoot.getBWSharedResource().getConfig();
	}

	/*
	 * Load SharedJndi Doc Root
	 */
	public static SharedJndiConfigRoot loadSharedJndiConfigDocRoot(String uri)
			throws IOException {

		if (uri.startsWith("archive:")) {
			return loadSharedJndiConfigDocRoot(URI.createURI(uri));
		} else {
			return loadSharedJndiConfigDocRoot(URI.createFileURI(uri));
		}
	}

	/*
	 * Load SharedJndi Doc Root
	 */
	public static SharedJndiConfigRoot loadSharedJndiConfigDocRoot(URI uri)
			throws IOException {

		final ResourceSet resourceSet = new ResourceSetImpl();
		// add file extension to registry
		SharedjndiconfigPackageImpl.eINSTANCE.eClass();
		final Resource.Factory.Registry registry = new ResourceFactoryRegistryImpl();
		registry.getExtensionToFactoryMap().put("*", new GenericXMLResourceFactoryImpl());
        registry.getExtensionToFactoryMap().put(SharedjndiconfigPackage.eNAME, new SharedjndiconfigResourceFactoryImpl());
		resourceSet.setResourceFactoryRegistry(registry);

		final Resource resource = resourceSet.createResource(uri);
		final Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");

		resource.load(options);

		return (SharedJndiConfigRoot) resource.getContents().get(0);
	}

	/*
	 * Load SharedJndi Config Object
	 */
	public static com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config loadSharedJndiConfig(
			String uri) throws IOException {

		SharedJndiConfigRoot sharedJndiConfigRoot = loadSharedJndiConfigDocRoot(uri);
		return sharedJndiConfigRoot.getBWSharedResource().getConfig();
	}

	/*
	 * Load Identity Doc Root
	 */
	public static IdRoot loadIdentityDocRoot(String uri) throws IOException {

		if (uri.startsWith("archive:")) {
			return loadIdentityDocRoot(URI.createURI(uri));
		} else {
			return loadIdentityDocRoot(URI.createFileURI(uri));
		}
	}

	/*
	 * Load Identity Doc Root
	 */
	public static IdRoot loadIdentityDocRoot(URI uri) throws IOException {

		final ResourceSet resourceSet = new ResourceSetImpl();
		// add file extension to registry
		IdPackageImpl.eINSTANCE.eClass();
		final Resource.Factory.Registry registry = new ResourceFactoryRegistryImpl();
		registry.getExtensionToFactoryMap().put("*", new GenericXMLResourceFactoryImpl());
        registry.getExtensionToFactoryMap().put(IdPackageImpl.eNAME, new IdResourceFactoryImpl());
        resourceSet.setResourceFactoryRegistry(registry);

		final Resource resource = resourceSet.createResource(uri);
		final Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");
        options.put(XMLResource.OPTION_EXTENDED_META_DATA, new ExtendedMetaData(IdPackage.eNS_URI));

		resource.load(options);

		return (IdRoot) resource.getContents().get(0);
	}

	/*
	 * Load Identity Object
	 */
	public static com.tibco.be.util.config.sharedresources.id.Identity loadIdentity(
			String uri) throws IOException {

		IdRoot idRoot = loadIdentityDocRoot(uri);
		return idRoot.getRepository().getIdentity();
	}

	/*
	 * Load SharedJmsConnection Doc Root
	 */
	public static SharedHttpRoot loadSharedHttpDocRoot(String uri)
			throws IOException {

		if (uri.startsWith("archive:")) {
			return loadSharedHttpDocRoot(URI.createURI(uri));
		} else {
			return loadSharedHttpDocRoot(URI.createFileURI(uri));
		}
	}

	/*
	 * Load SharedJmsConnection Doc Root
	 */
	public static SharedHttpRoot loadSharedHttpDocRoot(URI uri)
			throws IOException {

		final ResourceSet resourceSet = new ResourceSetImpl();
		// add file extension to registry
		SharedhttpPackageImpl.eINSTANCE.eClass();
		final Resource.Factory.Registry registry = new ResourceFactoryRegistryImpl();
		registry.getExtensionToFactoryMap().put("*",
				new GenericXMLResourceFactoryImpl());
		resourceSet.setResourceFactoryRegistry(registry);

		final Resource resource = resourceSet.createResource(uri);
		final Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");

		resource.load(options);

		return (SharedHttpRoot) resource.getContents().get(0);
	}

	/*
	 * Load SharedJmsConnection Config Object
	 */
	public static com.tibco.be.util.config.sharedresources.sharedhttp.Config loadSharedHttpConfig(
			String uri) throws IOException {

		SharedHttpRoot sharedHttpRoot = loadSharedHttpDocRoot(uri);
		return sharedHttpRoot.getHttpSharedResource().getConfig();
	}



}
