package com.tibco.cep.studio.ui;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.IViewCategory;
import org.eclipse.ui.views.IViewDescriptor;
import org.eclipse.ui.views.IViewRegistry;

import com.tibco.cep.studio.common.resources.ecore.ANTLRResourceFactory;
import com.tibco.cep.studio.ui.preferences.StudioUIPreferenceConstants;

public class StudioUIPluginStartup implements IStartup {

	static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
	
	
	private final static String ONTOLOGY_INDEX_VIEW_ENABLED = "activate.index.view"; 
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IStartup#earlyStartup()
	 */
	public void earlyStartup() {
		StudioUIManager.getInstance();
		String isEnabled = System.getProperty(ONTOLOGY_INDEX_VIEW_ENABLED);
		if (isEnabled == null || !isEnabled.equals("true")) {
			deActivateIndexView();
		}
		ANTLRResourceFactory.SOURCE_BASED_PERSISTENCE = StudioUIPlugin.getDefault().getPreferenceStore().getBoolean(StudioUIPreferenceConstants.STUDIO_PERSIST_ENTITIES_AS_SOURCE);
	}
	
	@SuppressWarnings("restriction")
	private static void deActivateIndexView() {
		IExtension extension = Platform.getExtensionRegistry().getExtension("com.tibco.cep.studio.ui.index.views");
		IViewRegistry viewRegistry = PlatformUI.getWorkbench().getViewRegistry();
		for ( IViewCategory catagory : viewRegistry.getCategories()) {
			boolean isDescriptorRemoved = false;
			if (catagory.getId().equals("com.tibco.cep.studio.ui.category")) {
				for (IViewDescriptor view : catagory.getViews()) {
					if (view.getId().equals("com.tibco.cep.studio.ui.views.IndexView")) {
//						((org.eclipse.ui.internal.registry.ViewRegistry)viewRegistry).removeExtension(extension, new Object[]{view});
						isDescriptorRemoved = true;
						break;
					}
				}
				if (isDescriptorRemoved) {
					break;
				}
	        }	
		}
	}

}
