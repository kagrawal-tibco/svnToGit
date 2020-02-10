package com.tibco.cep.studio.dashboard.ui.chartcomponent;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartTypeRegistry;
import com.tibco.cep.studio.dashboard.ui.utils.ImagesLoader;

/**
 * The activator class controls the plug-in life cycle
 */
public class DashboardChartPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.cep.studio.dashboard.ui.chartcomponent";

	// The shared instance
	private static DashboardChartPlugin plugin;
	
	/**
	 * The constructor
	 */
	public DashboardChartPlugin() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		//load the chart type registry
		ChartTypeRegistry.getInstance();
	}

	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		ImagesLoader loader = new ImagesLoader(this.getBundle(),reg);
		loader.load();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static DashboardChartPlugin getDefault() {
		return plugin;
	}
	
	

}
