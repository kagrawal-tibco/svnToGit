package com.tibco.cep.studio.tester.ui;

import java.net.URL;
import java.text.MessageFormat;
import java.util.Date;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import com.tibco.cep.studio.tester.core.utils.ManifestUtils;

public class DelegateActivator {

	public static final String PROP_STUDIO_EXTENDED_CLASSPATH 	= "studio.extended.classpath";
//	public static final String PROP_BUI_TESTER_ENGINE_LEVEL = "bui.tester.engine.feature.level";
//	public static final String ENGINE_LEVEL_MIN 			= "minimal";
//	public static final String ENGINE_LEVEL_LOCAL 			= "local";
//	public static final String ENGINE_LEVEL_FULL 			= "full";
//	public static final String ENGINE_LEVEL_DEFAULT 		= ENGINE_LEVEL_MIN;

	public static final String TESTER_DELEGATE_ID 		= "com.tibco.cep.tester.delegate";
	
	private BundleContext fParentContext;
	private Bundle fDelegateBundle;

	public DelegateActivator(BundleContext context) {
		this.fParentContext = context;
	}

	public synchronized void startDelegate(String extendedCP) {
		
		StudioTesterUIPlugin.debug("Installing delegate bundle");
		Date start = new Date();

		try {
			Bundle[] bundles = fParentContext.getBundles();
			for (Bundle bundle : bundles) {
				if (TESTER_DELEGATE_ID.equals(bundle.getSymbolicName())) {
					try {
						StudioTesterUIPlugin.debug("Uninstalling previous bundle from %1$s", bundle.getLocation());

						bundle.uninstall();
						StudioTesterUIPlugin.debug("Uninstalled previous bundle");

					} catch (Exception e) {
						StudioTesterUIPlugin.log("Unable to uninstall bundle",e);
					}
				}
			}
//			StudioConfig.init();
			
//			String testerLevel = StudioConfig.getProperty(PROP_BUI_TESTER_ENGINE_LEVEL);
//			if (testerLevel == null || testerLevel.length() == 0) {
//				StudioTesterUIPlugin.LOGGER.logDebug("{0} not set (default value is {1}).  Delegate Bundle not started", new Object[] { PROP_BUI_TESTER_ENGINE_LEVEL, ENGINE_LEVEL_DEFAULT });
//				return;
//			}
//			
//			if (ENGINE_LEVEL_MIN.equalsIgnoreCase(testerLevel)) {
//				StudioTesterUIPlugin.LOGGER.logDebug("{0} set to {1}.  Delegate Bundle not started", new Object[] { PROP_BUI_TESTER_ENGINE_LEVEL, testerLevel });
//				return;
//			}
//			
//			if (ENGINE_LEVEL_LOCAL.equalsIgnoreCase(testerLevel) || ENGINE_LEVEL_FULL.equalsIgnoreCase(testerLevel)) {
//				StudioTesterUIPlugin.LOGGER.logDebug("{0} set to {1}.  Attempting to start delegate bundle", new Object[] { PROP_BUI_TESTER_ENGINE_LEVEL, testerLevel });
//			} else {
//				StudioTesterUIPlugin.LOGGER.logDebug("Unable to determine tester engine level.  {0} set to {1}.  Delegate Bundle not started", new Object[] { PROP_BUI_TESTER_ENGINE_LEVEL, testerLevel });
//				return;
//			}
			
//			StudioProjectConfigurationManager.getInstance().getProjectConfiguration(projectName)
//			String extendedCP = getExtendedClassPath(config)//StudioConfig.getProperty(PROP_STUDIO_EXTENDED_CLASSPATH);
			if (extendedCP == null || extendedCP.length() == 0) {
				StudioTesterUIPlugin.debug(MessageFormat.format("%1$s not set.  Delegate Bundle not started", PROP_STUDIO_EXTENDED_CLASSPATH));
				return;
			}

			Date manStart = new Date();
			if (!ManifestUtils.writeDelegateManifest(extendedCP)) {
//				StudioTesterUIPlugin.LOGGER.logError(getClass().getName(), "Unable to write delegate bundle file");
//				StudioTesterUIPlugin.LOGGER.logDebug("Delegate Bundle not started");
				return;
			}
			Date manEnd = new Date();
			StudioTesterUIPlugin.debug(MessageFormat.format("Manifest written in %1$s ms", (manEnd.getTime() - manStart.getTime())));
			URL url = ManifestUtils.getDelegateBundleLocationURL();
			if (url == null) {
				StudioTesterUIPlugin.debug("Delegate bundle location not found.  Bundle not installed");
				return;
			}
			StudioTesterUIPlugin.debug(MessageFormat.format("Installing delegate bundle from %1$s", url.toExternalForm()));
			fDelegateBundle = fParentContext.installBundle(url.toExternalForm());
			fDelegateBundle.start();
			
			Date end = new Date();
			StudioTesterUIPlugin.debug(MessageFormat.format("Delegate plugin successfully started in %1$s ms", (end.getTime() - start.getTime())));
		} catch (BundleException e) {
			StudioTesterUIPlugin.log(e);
		}
	}
	
	public void stopDelegate() {
		if (fDelegateBundle != null && fDelegateBundle.getState() == Bundle.ACTIVE) {
			try {
				fDelegateBundle.stop();
			} catch (BundleException e) {
				StudioTesterUIPlugin.log(e);
			}
		}
	}
}
