package com.tibco.cep.diagramming.menu.popup;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.menu.Menu;
import com.tibco.cep.diagramming.menu.MenuPackage;
import com.tibco.cep.diagramming.menu.util.MenuResourceFactoryImpl;

/**
 * 
 * @author sasahoo
 *
 */
public class DiagramPopupMenuUtils {

	protected static final String POPUP_MENU        = "contextMenu";
	protected static final String ATTR_RESOURCE 	= "resource";
	protected static final String ATTR_HANDLER		= "handler";
	protected static final String ATTR_EXTENSION	= "extension";
	protected static final String ATTR_FILTER	    = "filter";
	protected static final String ATTR_VALIDATOR	= "validator";
	protected static final String ATTR_I11N_GEN	= "generator";
	
	private static DiagramPopupMenuInfo[] fDiagramPopupMenuInfos;
	
	public static DiagramPopupMenuInfo[] getDiagramPopupMenuInfos() {
		return fDiagramPopupMenuInfos;
	}

	/**
	 * @return {@link DiagramPopupMenuInfo}
	 */
	public static synchronized DiagramPopupMenuInfo[] createDiagramPopupMenuInfos() {
		if (fDiagramPopupMenuInfos == null) {
			List<DiagramPopupMenuInfo> diagramPopupInfos = new ArrayList<DiagramPopupMenuInfo>();
			IConfigurationElement[] configurationElementsFor = Platform.getExtensionRegistry().getConfigurationElementsFor(DiagrammingPlugin.PLUGIN_ID, POPUP_MENU);
			for (IConfigurationElement configurationElement : configurationElementsFor) {
				String handlerAttrib = configurationElement.getAttribute(ATTR_HANDLER);
				AbstractDiagramMenuHandler handler = null;
				if (handlerAttrib != null) {
					try {
						Object executableExtension = configurationElement.createExecutableExtension(ATTR_HANDLER);
						if (executableExtension instanceof AbstractDiagramMenuHandler) {
							handler = (AbstractDiagramMenuHandler) executableExtension;
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
				String filterAttrib = configurationElement.getAttribute(ATTR_FILTER);
				AbstractDiagramMenuFilter filter = null;
				if (filterAttrib != null) {
					try {
						Object executableExtension = configurationElement.createExecutableExtension(ATTR_FILTER);
						if (executableExtension instanceof AbstractDiagramMenuFilter) {
							filter = (AbstractDiagramMenuFilter) executableExtension;
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
				
				String validatorAttrib = configurationElement.getAttribute(ATTR_VALIDATOR);
				AbstractMenuStateValidator validator = null;
				if (validatorAttrib != null) {
					try {
						Object executableExtension = configurationElement.createExecutableExtension(ATTR_VALIDATOR);
						if (executableExtension instanceof AbstractMenuStateValidator) {
							validator = (AbstractMenuStateValidator) executableExtension;
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
				AbstractDiagramI18NTextGenerator generator = null;

				IConfigurationElement[] configurationElements = configurationElement.getChildren();
				if (configurationElements[0] != null) {
					try {
						Object executableExtension = configurationElements[0].createExecutableExtension(ATTR_I11N_GEN);
						if (executableExtension instanceof AbstractDiagramI18NTextGenerator) {
							generator = (AbstractDiagramI18NTextGenerator) executableExtension;
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
				
				String resource = configurationElement.getAttribute(ATTR_RESOURCE);
				String extension = configurationElement.getAttribute(ATTR_EXTENSION);
				Menu menu = createMenu(handler.getClass(), resource);
				DiagramPopupMenuInfo info = new DiagramPopupMenuInfo(resource, extension, menu, handler, filter, validator, generator);
				diagramPopupInfos.add(info);
			}
			fDiagramPopupMenuInfos = new DiagramPopupMenuInfo[diagramPopupInfos.size()];
			return diagramPopupInfos.toArray(fDiagramPopupMenuInfos);
		}
		return fDiagramPopupMenuInfos;
	}
	
	/**
	 * @param uri
	 * @return {@link Resource}
	 */
	public static Resource getResource(URI uri) {
		Map<String, Object> packageRegistry = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
		MenuPackage.eINSTANCE.setName(MenuPackage.eNAME);
		MenuPackage.eINSTANCE.setNsPrefix(MenuPackage.eNS_PREFIX);
		packageRegistry.put(MenuPackage.eNS_PREFIX, MenuPackage.eNS_URI);
		ResourceSet resourceSet = new ResourceSetImpl();
		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put("beprocessmenu", new MenuResourceFactoryImpl());
		Resource resource = resourceSet.createResource(uri);
		return resource;
	}
	
	/**
	 * @param contribClass
	 * @param resource
	 * @return {@link Menu}
	 */
	@SuppressWarnings({ "static-access", "rawtypes" })
	public static Menu createMenu(Class contribClass, String menuResource) {
		try {
			List<URL> urlList = new LinkedList<URL>();
			Enumeration<URL> e = contribClass.getClassLoader().getSystemResources(menuResource);
			while (e.hasMoreElements()) {
				urlList.add(e.nextElement());
			}
			e = contribClass.getClassLoader().getResources(menuResource);
			while (e.hasMoreElements()) {
				urlList.add(e.nextElement());
			}
			URL url = urlList.get(0);
			URI uri = URI.createFileURI(url.getPath());
			Resource resource = getResource(uri);
			Map<Object, Object> options = new HashMap<Object, Object>();
			options.put(XMIResource.OPTION_ENCODING, "UTF-8");
			resource.load(url.openStream(),options);
			Menu menu = (Menu) resource.getContents().get(0);
			return menu;

		} catch (IOException ex) {
			DiagrammingPlugin.log(ex);
		}
		return null;
	}
}
