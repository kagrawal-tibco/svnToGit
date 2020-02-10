package com.tibco.cep.designtime.model;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLOptions;
import org.eclipse.emf.ecore.xmi.impl.GenericXMLResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLOptionsImpl;

import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.designtime.model.registry.Registry;
import com.tibco.cep.designtime.model.registry.RegistryPackage;
import com.tibco.cep.util.PlatformUtil;

/**
 * @author pdhar Singleton Addon registry
 * 
 */
public class AddOnRegistry {
	private static final String ONTOLOGY_PROVIDER = "addon.xml"; //$NON-NLS-1$
	private static AddOnRegistry instance;
	private static Map<AddOnType,AddOn> providerMap = new HashMap<AddOnType,AddOn>();
	public static final Map<AddOnType,IAddOnLoader> installedAddOns = new HashMap<AddOnType,IAddOnLoader>();
	

	private AddOnRegistry() throws Exception {
		init();
	}
	
	/**
	 * Look in the classpath for "ontology.provider" resource file, every addon which is 
	 * contributing towards the Ontology should have one.
	 * @throws Exception
	 */
	private  void init() throws Exception {
		Set<URL> urlList = new HashSet<URL>();
		RegistryPackage.eINSTANCE.eClass();
		if(!PlatformUtil.INSTANCE.isStudioPlatform()) {
			Enumeration<URL> e = ClassLoader.getSystemResources(ONTOLOGY_PROVIDER);
			while (e.hasMoreElements()) {
				urlList.add(e.nextElement());
			}
			e = this.getClass().getClassLoader().getResources(ONTOLOGY_PROVIDER);
			while (e.hasMoreElements()) {
				urlList.add(e.nextElement());
			}

			URL url = AddOnRegistry.class.getResource("/addon_registry.xsd");
			for(URL u:urlList) {
				
				RegistryPackage.eINSTANCE.eClass();
				ResourceSet resourceSet = new ResourceSetImpl();
				// add file extension to registry
				ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put("xml", new GenericXMLResourceFactoryImpl());
				URI uri = URI.createURI(u.toString());
				Resource resource = resourceSet.createResource(uri);
				Map<String,URI> schemaLocations = new HashMap<String,URI>();
				schemaLocations.put("addon_registry.xsd",URI.createURI(url.toURI().toString()));
				XMLOptions xmlops = new XMLOptionsImpl();
				xmlops.setExternalSchemaLocations(schemaLocations);
				
				Map<Object,Object> options = new HashMap<Object,Object>();
				options.put(XMIResource.OPTION_ENCODING, "UTF-8");
				options.put(XMIResource.OPTION_XML_OPTIONS, xmlops);
				options.put(XMIResource.OPTION_SCHEMA_LOCATION, URI.createURI(url.toURI().toString()));
				resource.load(u.openStream(),options);
				Registry dr = (Registry) resource.getContents().get(0);
				AddOn p = dr.getAddOn();	
				register(p);
				
			}
			
		} else {
			// AddOnUtil class is exposed from the same plugin as AddonRegistry
			Class<?> util = Class.forName("com.tibco.cep.rt.AddonUtil"); //$NON-NLS-1$
			Method m = util.getDeclaredMethod("getInstalledAddOnMap");
			Map<AddOn,IAddOnLoader> addons = (Map<AddOn,IAddOnLoader>) m.invoke(null);
			for(Entry<AddOn, IAddOnLoader> entry:addons.entrySet()) {
				register(entry.getKey());
				registerLoader(entry.getKey(),entry.getValue());
			}
		}
		

		
	}

	private void registerLoader(AddOn addOn, IAddOnLoader l) {
		if(!installedAddOns.containsKey(addOn.getType())) {
			installedAddOns.put(addOn.getType(), l);		
		}
		
	}

	/**
	 * @return
	 */
	public static AddOnRegistry getInstance() throws Exception {
		if (instance == null) {
			instance = new AddOnRegistry();
			
		}
		return instance;
	}

	/**
	 * @param provider
	 */
	public void register(AddOn p) throws Exception {
		if(!providerMap.containsKey(p.getType())) {
			providerMap.put(p.getType(), p);		
		}
	}
	
	
	public Map<AddOnType, AddOn> getAddOnMap() {
		return providerMap;
	}

	public Collection<AddOn> getAddons() {		
		return providerMap.values();
	}
	
	public static Map<AddOnType, IAddOnLoader> getInstalledAddOnMap() {
		if (!PlatformUtil.INSTANCE.isStudioPlatform()) {
			throw new UnsupportedOperationException(
					"This method should be called from Studio only.");
		}

		return installedAddOns;
	}
	

}
