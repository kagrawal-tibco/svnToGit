package com.tibco.cep.sharedresource;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * @author ssinghal
 *
 */
public class MqttSharedResourcePlugin extends AbstractUIPlugin{
	
	private static MqttSharedResourcePlugin plugin;
	
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}
	
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
	
	public static MqttSharedResourcePlugin getDefault() {
		return plugin;
	}

}
