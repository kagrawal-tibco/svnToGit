package com.tibco.cep.tra;


import java.lang.reflect.Method;

import org.xml.sax.InputSource;

import com.tibco.xml.tns.parse.TnsDocument;
import com.tibco.xml.tns.parse.TnsFlavor;
import com.tibco.xml.tns.parse.TnsFlavorContext;

public class IgnoredDelegatingTnsFlavor implements TnsFlavor {
	
	protected static final String FLAVOR_DELEGATE = "tnsFlavorDelegate";
	protected static final String ATTR_FLAVOR 	= "tnsFlavorClass";
	
	Object[] fFlavorConfigurationElements = null;
	private TnsFlavor fFlavor;
	
	public IgnoredDelegatingTnsFlavor() {
	}

	@Override
	public String[] getExtensions() {
		return getRegisteredFlavor().getExtensions();
	}

	@Override
	public String[] getStaticResources() {
		return getRegisteredFlavor().getStaticResources();
	}

	@Override
	public TnsDocument parse(InputSource input, TnsFlavorContext context) {
		return getRegisteredFlavor().parse(input, context);
	}
	
	private Object[] getDelegateElements() {
		try {
			if (fFlavorConfigurationElements == null) {
				Class<?> platformClass = Class.forName("org.eclipse.core.runtime.Platform");
				Method extRegMethod = platformClass.getMethod("getExtensionRegistry");
				Object extPointRegistry = extRegMethod.invoke(platformClass);
				if (fFlavorConfigurationElements == null && extPointRegistry != null) {
					Method getConfigElementsMethod = extPointRegistry.getClass().getMethod("getConfigurationElementsFor", String.class);
					fFlavorConfigurationElements = (Object[]) getConfigElementsMethod.invoke(extPointRegistry, "com.tibco.cep.tra."+FLAVOR_DELEGATE);
				}
			}
			
		} catch (Exception e) {
			fFlavorConfigurationElements = new Object[0];
		} catch (Throwable t) {
			fFlavorConfigurationElements = new Object[0];
		}
		return fFlavorConfigurationElements;

	}
	

	private TnsFlavor getRegisteredFlavor() {
		if (null == this.fFlavor) {
			try {
				Object[] elements = getDelegateElements();
				for (Object configurationElement : elements) {
					Method getAttributeMethod = configurationElement.getClass().getMethod("getAttribute", String.class);
					String appenderName = (String) getAttributeMethod.invoke(configurationElement, ATTR_FLAVOR);
					if (appenderName != null) {
						Method createExecExtMethod = configurationElement.getClass().getMethod("createExecutableExtension", String.class);
						fFlavor = (TnsFlavor) createExecExtMethod.invoke(configurationElement, ATTR_FLAVOR);
					}
				}
			} catch (Exception e) {
				// just move on, assume that the eclipse libraries are not available (i.e. 'server' mode)
			}
			if(this.fFlavor == null) {
				Class<?> flavorClass = null;
				try {
					flavorClass = (Class<?>) Class.forName("com.tibco.cep.studio.core.rdf.EMFRDFTnsFlavor");
				} catch (Throwable e) {
					try {
						flavorClass = (Class<?>) Class.forName("com.tibco.be.model.rdf.RDFTnsFlavor");
					} catch (ClassNotFoundException e1) {
						flavorClass = null;
					}
				}
				if(flavorClass != null) {
					try {
						this.fFlavor = (TnsFlavor) flavorClass.newInstance();
					} catch (Exception e) {
						e.printStackTrace();
					};
				}			
			}
		}
		return this.fFlavor;
	}

}
