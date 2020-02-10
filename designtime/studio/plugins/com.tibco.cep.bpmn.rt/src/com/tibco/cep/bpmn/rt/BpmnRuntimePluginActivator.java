package com.tibco.cep.bpmn.rt;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class BpmnRuntimePluginActivator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.cep.bpmn.rt";

	// The shared instance
	private static BpmnRuntimePluginActivator plugin;

	/**
	 * The constructor
	 */
	public BpmnRuntimePluginActivator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static BpmnRuntimePluginActivator getDefault() {
		return plugin;
	}

}