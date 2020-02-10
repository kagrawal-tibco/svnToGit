package com.tibco.cep.dashboard.plugin.beviews.runtime;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.vizengine.ComponentRenderer;
import com.tibco.cep.dashboard.psvr.vizengine.ComponentRendererFactory;

//TODO clean up the class
public class ComponentContentProviderFactory {

	private static ComponentContentProviderFactory instance;

	public static final synchronized ComponentContentProviderFactory getInstance() {
		if (instance == null) {
			instance = new ComponentContentProviderFactory();
		}
		return instance;
	}

	private Map<String, ComponentContentProvider> contentProviderIndex;

	private ComponentContentProviderFactory() {
		contentProviderIndex = new ConcurrentHashMap<String, ComponentContentProvider>();
	}

	public ComponentContentProvider getProvider(MALComponent component) throws PluginException {
		ComponentContentProvider contentProvider = contentProviderIndex.get(component.getDefinitionType());
		if (contentProvider == null){
			contentProvider = createContentProvider(component);
			contentProviderIndex.put(component.getDefinitionType(), contentProvider);
		}
		return contentProvider;
	}

	private ComponentContentProvider createContentProvider(MALComponent component) throws PluginException {
		ComponentContentProvider contentProvider = null;
		try {
			ComponentRenderer renderer = ComponentRendererFactory.getInstance().getRenderer(component);
			String componentRenderer = renderer.getComponentRenderer();
			Class<?> clazz = Class.forName(componentRenderer);
			if (ComponentContentProvider.class.isAssignableFrom(clazz) == true){
				contentProvider = ComponentContentProvider.class.cast(clazz.newInstance());
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contentProvider;
	}

}
