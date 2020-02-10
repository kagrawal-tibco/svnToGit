package com.tibco.cep.addon;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ExternalResourceInfoProviderFinder {
	
	private static final String PROPERTIES_FILE = "providers.properties";
	
	private static ExternalResourceInfoProviderFinder instance;
	
	public synchronized static final ExternalResourceInfoProviderFinder getInstance(){
		if (instance == null){
			instance = new ExternalResourceInfoProviderFinder();
		}
		return instance;
	}
	
	private Map<String,IExternalResourceInfoProvider> providers;
	 
	
	private ExternalResourceInfoProviderFinder() {
		providers = new HashMap<String, IExternalResourceInfoProvider>();
		URL resourceURL = this.getClass().getResource(PROPERTIES_FILE);
		if (resourceURL != null){
			try {
				InputStream stream = resourceURL.openStream();
				Properties props = new Properties();
				props.load(stream);
				Enumeration<Object> keys = props.keys();
				while (keys.hasMoreElements()) {
					String addOnName = (String) keys.nextElement();
					if (providers.containsKey(addOnName) == false){
						String providerClassName = props.getProperty(addOnName);
						IExternalResourceInfoProvider provider = createProvider(providerClassName);
						if (provider != null){
							providers.put(addOnName,provider);
						}
					} 
				}
			} catch (IOException ignore) {
				//do nothing
			}
		}
	}

	private IExternalResourceInfoProvider createProvider(String providerClassName) {
		try {
			Class<? extends IExternalResourceInfoProvider> clazz = Class.forName(providerClassName).asSubclass(IExternalResourceInfoProvider.class);
			return clazz.newInstance();
		} catch (ClassNotFoundException ignore) {
			//do nothing
			return null;
		} catch (InstantiationException ignore) {
			//do nothing
			return null;
		} catch (IllegalAccessException ignore) {
			//do nothing
			return null;
		}
	}
	
	public Collection<IExternalResourceInfoProvider> getRegisteredProviders(){
		return Collections.unmodifiableCollection(providers.values());
	}
	
	public Collection<String> getRegisteredAddOns(){
		return Collections.unmodifiableCollection(providers.keySet());
	}
	
	public IExternalResourceInfoProvider getProviderFor(String addOnName){
		return providers.get(addOnName);
	}

}
