package com.tibco.cep.rt;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import com.tibco.cep.designtime.model.AddOnRegistry;
import com.tibco.cep.designtime.model.IAddOnLoader;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.designtime.model.registry.AddOnType;

/*
@author ssailapp
@date Feb 22, 2011
 */

public class AddonUtil {

	public static final String ADDON_DATAMODELING = "data-modeling";
	public static final String ADDON_DECISIONMANAGER = "decision-manager";
	public static final String ADDON_EVENTSTREAMPROCESSING = "event-stream-processing";
	public static final String ADDON_PROCESS = "process";
	public static final String ADDON_VIEWS = "views";
	public static final String ADDON_LIVEIVEW = "live-view";
	
	public static final Map<AddOn, IAddOnLoader<?>> installedAddOns = new HashMap<AddOn, IAddOnLoader<?>>();
	public static final Map<String, Map<AddOn, Ontology>> adaptersMap = new HashMap<String, Map<AddOn,Ontology>>();
//	public static final Map<AddOn,Ontology> adapterMap = new HashMap<AddOn,Ontology>();
	
	/**
	 * @return
	 */
	public static Collection<AddOn> getInstalledAddOns() {
		try {
			synchronized (installedAddOns) {
				if (installedAddOns.isEmpty()) {
					
					IConfigurationElement[] config = Platform
							.getExtensionRegistry().getConfigurationElementsFor(
									"com.tibco.cep.rt.addonContributor");
					for (IConfigurationElement configurationElement : config) {
						final Object addonClazz = configurationElement
								.createExecutableExtension("class");
						if (addonClazz instanceof IAddOnLoader) {
							IAddOnLoader<?> loader = (IAddOnLoader<?>) addonClazz;
							AddOn addon = loader.getAddOn();
							if (addon != null) {
								installedAddOns.put(addon,loader);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return installedAddOns.keySet();
	}
	
	/**
	 * @return
	 */
	public static Map<AddOn, IAddOnLoader<?>> getInstalledAddOnMap() {
		try {
			synchronized (installedAddOns) {
				if (installedAddOns.isEmpty()) {
					getInstalledAddOns();					
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return installedAddOns;
	}
	
	public static IAddOnLoader<?> getAddOnLoader(AddOn addon){
		synchronized (installedAddOns) {
			return installedAddOns.get(addon);
		}
	}
    
    /**
     * @param projectName
     * @return
     */
    public static Map<AddOn, Ontology> getAddOnOntologyAdapters(String projectName) {
    	Map<AddOn, Ontology> map = adaptersMap.get(projectName);
    	if (map == null) {
			map = new HashMap<AddOn, Ontology>();

    		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor("com.tibco.cep.rt.addonContributor");
    		try {
    			for (IConfigurationElement configurationElement : config) {
    				final Object addonClazz = configurationElement.createExecutableExtension("class");
    				if (addonClazz instanceof IAddOnLoader) {
    					IAddOnLoader<?> loader = (IAddOnLoader<?>) addonClazz;
    					AddOn addon = loader.getAddOn();
    					Ontology addonOntologyAdapter = loader.getOntology(projectName);
    					if (addon != null) {
    						map.put(addon,addonOntologyAdapter);
    						adaptersMap.put(projectName, map);
    					}
    				}
    			}
    		} catch (Exception ex) {
    			ex.printStackTrace();
    		}
    	}
		return map;
    }
    


    public static boolean isExpressEdition() {
    	String cacheEnabled = System.getProperty("TIBCO.BE.edition.cacheEnabled");
    	if (new Boolean(cacheEnabled).booleanValue())
    		return false;

    	// Just a crude check for now
    	String beHome = System.getProperty("BE_HOME");
		String bsJarPath = beHome + "/lib/cep-backingstore.jar";
		if (new File(bsJarPath).exists())
			return false;
    	return true;
    }
    
    public static boolean isAddonInstalled(AddOnType addonId) {
    	try {
			return AddOnRegistry.getInstance().getAddOnMap().containsKey(addonId);
		} catch (Exception e) {
			Activator.log(e);
		}
    	return false;
    }
    
    public static boolean isDataModelingAddonInstalled() {
    	return (isAddonInstalled(AddOnType.DATAMODELLING));
    }
    
    public static boolean isDecisionManagerAddonInstalled() {
    	return (isAddonInstalled(AddOnType.DECISIONMANAGER));
    }
    
    public static boolean isEventStreamProcessingAddonInstalled() {
    	return (isAddonInstalled(AddOnType.EVENTSTREAMPROCESSING));
    }
    
    public static boolean isViewsAddonInstalled() {
    	return (isAddonInstalled(AddOnType.VIEWS));
    }
    
    public static boolean isProcessAddonInstalled() {
    	return (isAddonInstalled(AddOnType.PROCESS));
    }
    
    public static boolean isLiveViewAddonInstalled() {
    	return (isAddonInstalled(AddOnType.LIVEVIEW));
    }
}
