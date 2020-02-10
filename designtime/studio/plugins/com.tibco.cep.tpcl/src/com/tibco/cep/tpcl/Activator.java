package com.tibco.cep.tpcl;

import javax.swing.UIManager;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.cep.tpcl";

	// The shared instance
	private static Activator plugin;

    // Look and Feel
	public static final String GTK_LOOK_AND_FEEL_NAME = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
	public static final String METAL_LOOK_AND_FEEL_NAME = "javax.swing.plaf.metal.MetalLookAndFeel";
	public static final String NIMBUS_LOOK_AND_FEEL_NAME = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		String prop = System.getProperty("studio.console");
		if (prop == null || prop.equalsIgnoreCase("false")) {

//			com.jidesoft.utils.Lm.verifyLicense("TIBCO Software Inc.",
//					"TIBCO BusinessEvents", "piTabGB9Ky0BLyASnMlZd7:urra403H1");

			if (!System.getProperty("os.name").startsWith("Window")) {
				// System.out.printf("ToolKit class=%s\n",
				// System.getProperty("awt.toolkit"));
				// String eqName =
				// System.getProperty("AWT.EventQueueClass","java.awt.EventQueue");
				// System.out.println("Event-Q-Name:" + eqName);

				// System.out.println("System L&F: " +
				// UIManager.getSystemLookAndFeelClassName());
				// System.out.println("Running in Linux or Unix variant, setting the default UIManager L&F");

				// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				UIManager.setLookAndFeel(UIManager
						.getCrossPlatformLookAndFeelClassName());
				/*
				 * if
				 * (UIManager.getSystemLookAndFeelClassName().equalsIgnoreCase
				 * (GTK_LOOK_AND_FEEL_NAME)) {
				 * //UIManager.setLookAndFeel(METAL_LOOK_AND_FEEL_NAME); // The
				 * following is same as Metal
				 * UIManager.setLookAndFeel(UIManager.
				 * getCrossPlatformLookAndFeelClassName());
				 * System.out.println("Setting L&F: " +
				 * UIManager.getLookAndFeel()); }
				 */
				// java.security.Security.addProvider(new
				// com.sun.crypto.provider.SunJCE());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
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
	public static Activator getDefault() {
		return plugin;
	}

}
