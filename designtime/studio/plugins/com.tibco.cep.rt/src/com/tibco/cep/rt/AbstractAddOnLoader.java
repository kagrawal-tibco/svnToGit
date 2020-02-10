package com.tibco.cep.rt;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLOptions;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.XMLResource.URIHandler;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.GenericXMLResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLOptionsImpl;
import org.osgi.framework.Bundle;

import com.tibco.cep.designtime.model.IAddOnLoader;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.designtime.model.registry.Registry;
import com.tibco.cep.designtime.model.registry.RegistryPackage;

public abstract class AbstractAddOnLoader<T extends Ontology> implements IAddOnLoader<T> {
	
	
	private static final String COM_TIBCO_CEP_RT = "com.tibco.cep.rt"; //$NON-NLS-1$
	private static final String ADDON_REGISTRY_XSD = "/addon_registry.xsd"; //$NON-NLS-1$
	private static final String ADDON_XML = "/addon.xml"; //$NON-NLS-1$

	public AddOn loadAddOn(String pluginId) throws URISyntaxException, IOException, ClassNotFoundException {
		Bundle b = Platform.getBundle(pluginId);
		URL url = b.getResource(ADDON_XML);
		Bundle rtbundle = Platform.getBundle(COM_TIBCO_CEP_RT);
		URL xsdurl = rtbundle.getResource(ADDON_REGISTRY_XSD);
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(RegistryPackage.eINSTANCE.getNsURI(), RegistryPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xml", new GenericXMLResourceFactoryImpl()); //LMN
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		// add file extension to registry

		URI uri = URI.createURI(url.toString());
		Resource resource = resourceSet.createResource(uri);
		Map<Object,Object> options = new HashMap<Object,Object>();
		options.put(XMIResource.OPTION_ENCODING, "UTF-8");			
//		options.put(XMIResource.OPTION_SCHEMA_LOCATION, URI.createPlatformPluginURI(COM_TIBCO_CEP_RT+ADDON_REGISTRY_XSD, false));
		options.put(XMIResource.OPTION_URI_HANDLER, new AddOnURIHandler("addon_registry.xsd",URI.createURI(xsdurl.toURI().toString())));
		XMLOptions xmlOpts = new XMLOptionsImpl();
        xmlOpts.setProcessSchemaLocations(false);
        options.put(XMLResource.OPTION_XML_OPTIONS, xmlOpts);
		
        try {
        	resource.load(url.openStream(),options);
        } catch (Exception exception) {
        	exception.printStackTrace();
        }
		Registry dr = (Registry) resource.getContents().get(0);
		AddOn p = dr.getAddOn();	
		return p;
	}
	
	static class AddOnURIHandler implements URIHandler {
		URI base;
		private URI schemaURI;
		private String match;
		
		public AddOnURIHandler(String match,URI schemaURI) {
			this.schemaURI = schemaURI;
			this.match = match;
			// TODO Auto-generated constructor stub
		}

		@Override
		public URI deresolve(URI arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public URI resolve(URI arg0) {
			if(arg0.toString().endsWith(match)) {
				return schemaURI;
			}
			return null;
		}

		@Override
		public void setBaseURI(URI arg0) {
			this.base = arg0;
			
		}
		
	}
	
	@Override
	public Class<?> getAddonClass(String className) throws ClassNotFoundException {
		return Class.forName(className);
	}

}
