package com.tibco.cep.thirdparty;

import java.net.URL;
import java.text.MessageFormat;
import java.util.Date;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import com.tibco.cep.studio.util.StudioConfig;

public class DelegateActivator { 
	
	static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
	

	public static final String PROP_STUDIO_EXTENDED_CLASSPATH 	= "studio.extended.classpath";
//	public static final String PROP_BUI_TESTER_ENGINE_LEVEL = "bui.tester.engine.feature.level";
//	public static final String ENGINE_LEVEL_MIN 			= "minimal";
//	public static final String ENGINE_LEVEL_LOCAL 			= "local";
//	public static final String ENGINE_LEVEL_FULL 			= "full";
//	public static final String ENGINE_LEVEL_DEFAULT 		= ENGINE_LEVEL_MIN;

	public static final String THIRDPARTY_DELEGATE_ID 		= "com.tibco.cep.thirdparty.delegate";
	
	private BundleContext fParentContext;
	private Bundle fDelegateBundle;

	public DelegateActivator(BundleContext context) {
		this.fParentContext = context;
	}

	public synchronized void startDelegate() {
		
		DelegatePlugin.debug("Installing delegate bundle");
		Date start = new Date();

		try {
			Bundle[] bundles = fParentContext.getBundles();
			for (Bundle bundle : bundles) {
				if (THIRDPARTY_DELEGATE_ID.equals(bundle.getSymbolicName())) {
					try {
						DelegatePlugin.debug("Uninstalling previous bundle from "+bundle.getLocation());
						bundle.uninstall();
						DelegatePlugin.debug("Uninstalled previous bundle");
					} catch (Exception e) {
						DelegatePlugin.log("Unable to uninstall bundle",e);
					}
				}
			}
//			StudioConfig.init();
			
//			String testerLevel = StudioConfig.getProperty(PROP_BUI_TESTER_ENGINE_LEVEL);
//			if (testerLevel == null || testerLevel.length() == 0) {
//				DelegatePlugin.LOGGER.logDebug("{0} not set (default value is {1}).  Delegate Bundle not started", new Object[] { PROP_BUI_TESTER_ENGINE_LEVEL, ENGINE_LEVEL_DEFAULT });
//				return;
//			}
//			
//			if (ENGINE_LEVEL_MIN.equalsIgnoreCase(testerLevel)) {
//				DelegatePlugin.LOGGER.logDebug("{0} set to {1}.  Delegate Bundle not started", new Object[] { PROP_BUI_TESTER_ENGINE_LEVEL, testerLevel });
//				return;
//			}
//			
//			if (ENGINE_LEVEL_LOCAL.equalsIgnoreCase(testerLevel) || ENGINE_LEVEL_FULL.equalsIgnoreCase(testerLevel)) {
//				DelegatePlugin.LOGGER.logDebug("{0} set to {1}.  Attempting to start delegate bundle", new Object[] { PROP_BUI_TESTER_ENGINE_LEVEL, testerLevel });
//			} else {
//				DelegatePlugin.LOGGER.logDebug("Unable to determine tester engine level.  {0} set to {1}.  Delegate Bundle not started", new Object[] { PROP_BUI_TESTER_ENGINE_LEVEL, testerLevel });
//				return;
//			}
			String extendedCP = StudioConfig.getInstance().getProperty(PROP_STUDIO_EXTENDED_CLASSPATH);
			if (extendedCP == null || extendedCP.length() == 0) {
				extendedCP = StudioConfig.substituteEnvVars(PROP_STUDIO_EXTENDED_CLASSPATH);
				if (extendedCP == null || extendedCP.length() == 0) {
					DelegatePlugin.debug(MessageFormat.format("{0} not set.  Delegate Bundle not started", new Object[] { PROP_STUDIO_EXTENDED_CLASSPATH }));
					return;
				}
			} 

			Date manStart = new Date();
			if (!ManifestUtil.writeDelegateManifest(extendedCP)) {
//				DelegatePlugin.LOGGER.logError(getClass().getName(), "Unable to write delegate bundle file");
//				DelegatePlugin.LOGGER.logDebug("Delegate Bundle not started");
				return;
			}
			Date manEnd = new Date();
			DelegatePlugin.debug(MessageFormat.format("Manifest written in {0} ms", new Object[] {(manEnd.getTime() - manStart.getTime())}));
			URL url = ManifestUtil.getDelegateBundleLocationURL();
			if (url == null) {
				DelegatePlugin.log("Delegate bundle location not found.  Bundle not installed");
				return;
			}
			DelegatePlugin.debug(MessageFormat.format("Installing delegate bundle from {0}", new Object[] {url.toExternalForm()}));
			fDelegateBundle = fParentContext.installBundle(url.toExternalForm());
			
			fDelegateBundle.start();
			
			Date end = new Date();
			DelegatePlugin.debug(MessageFormat.format("Delegate plugin successfully started in {0} ms", new Object[] {(end.getTime() - start.getTime())}));
		} catch (BundleException e) {
			DelegatePlugin.log(e);
		}
	}
	
	public Bundle getDelegateBundle() {
		return fDelegateBundle;
	}
	
	public void stopDelegate() {
		
		if (fDelegateBundle != null && fDelegateBundle.getState() == Bundle.ACTIVE) {
			try {
				fDelegateBundle.stop();
			} catch (BundleException e) {
				DelegatePlugin.log(e);
			}
		}
		
	}
	
}
