package com.tibco.cep.studio.dashboard.ui.statemachinecomponent;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.cep.studio.dashboard.ui.utils.ImagesLoader;

/**
 * The activator class controls the plug-in life cycle
 */
public class DashboardStateMachineComponentPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.cep.studio.dashboard.ui.statemachinecomponent";

	// The shared instance
	private static DashboardStateMachineComponentPlugin instance;

	public DashboardStateMachineComponentPlugin() {
	}
	
	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		ImagesLoader imagesLoader = new ImagesLoader(getBundle(),reg);
		imagesLoader.load();		
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		instance = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		instance = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * @return the shared instance
	 */
	public static DashboardStateMachineComponentPlugin getInstance() {
		return instance;
	}

}
