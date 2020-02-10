package com.tibco.cep.sharedresource;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


/*
@author Huabin Zhang (huzhang@tibco-support.com)
@date Feb 23, 2012 09:36:03 PM
*/

public class ASConnectionSharedResourcePlugin extends AbstractUIPlugin  {

	// The shared instance
	private static ASConnectionSharedResourcePlugin plugin;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
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
	public static ASConnectionSharedResourcePlugin getDefault() {
		return plugin;
	}

}
