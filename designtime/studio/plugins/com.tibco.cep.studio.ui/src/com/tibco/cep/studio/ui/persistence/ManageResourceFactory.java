package com.tibco.cep.studio.ui.persistence;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

public class ManageResourceFactory {

	private static final String ID = "id"; //$NON-NLS-1$
	private static final String NAME = "name"; //$NON-NLS-1$
	private static final String CLASS = "class"; //$NON-NLS-1$
	private static final String DESIGNER_RESOURCE_MANAGER = "com.tibco.cep.studio.ResourceManager"; //$NON-NLS-1$

	public static IManageResource resolveManagerResource(String id) {
		IExtensionRegistry registry = Platform.getExtensionRegistry() ;
		IExtensionPoint extensionPoint = registry.getExtensionPoint(DESIGNER_RESOURCE_MANAGER) ;

		IExtension[] extensions = extensionPoint.getExtensions() ;
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements =
				extensions[i].getConfigurationElements() ;
			for (int j = 0; j < elements.length; j++) {
				if (elements[j].getAttribute(ID).equals(id)) { 
					Object obj;
					try {
						obj = elements[j].createExecutableExtension(CLASS);
						if (obj instanceof IManageResource) 
							return (IManageResource) obj ;
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return null ;
	}

	public static String[][] computeEntryNamesAndValues() {
				
		IExtensionRegistry registry = Platform.getExtensionRegistry() ;
		IExtensionPoint extensionPoint =
			registry.getExtensionPoint(DESIGNER_RESOURCE_MANAGER) ;
		IExtension[] extensions = extensionPoint.getExtensions() ;
		ArrayList<String[]> list = new ArrayList<String[]>() ;
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements =
				extensions[i].getConfigurationElements() ;
			for (int j = 0; j < elements.length; j++) {
				String[] res = new String [2] ;
				res[0] = elements[j].getAttribute(NAME) ;
				res[1] = elements[j].getAttribute(ID) ;
				list.add(res) ;
			}
		}
		
		String [][] results = new String[list.size()][2] ;
		for (int i = 0; i < list.size() ; i++) {
			results[i][0] = list.get(i)[0] ;
			results[i][1] = list.get(i)[1] ;			
		}
		
		return results ;
	}

}
