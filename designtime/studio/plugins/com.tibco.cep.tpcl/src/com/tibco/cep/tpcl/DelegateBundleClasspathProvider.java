package com.tibco.cep.tpcl;

import java.io.File;

import org.eclipse.osgi.container.ModuleLoader;
import org.eclipse.osgi.internal.framework.EquinoxBundle;
import org.eclipse.osgi.internal.loader.BundleLoader;
import org.eclipse.osgi.internal.loader.EquinoxClassLoader;
import org.eclipse.osgi.internal.loader.classpath.ClasspathEntry;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

@SuppressWarnings("restriction")
public class DelegateBundleClasspathProvider implements
		IRuntimeBundleClasspathProviderDelegate {

	@Override
	public File[] getClasspath(Bundle bundle) throws BundleException {
		ModuleLoader myOwnBundleLoader = ((EquinoxBundle)bundle).getModule().getCurrentRevision().getWiring().getModuleLoader();
		if(myOwnBundleLoader instanceof BundleLoader) {
			BundleLoader loader = (BundleLoader) myOwnBundleLoader;
			ClasspathEntry[] entries = ((EquinoxClassLoader)loader.getModuleClassLoader()).getClasspathManager().getHostClasspathEntries();
			File[] cp = new File[entries.length];
			for (int i=0; i<cp.length; i++) {
				cp[i] = entries[i].getBundleFile().getBaseFile();
			}
			return cp;
		}
		
		return null;
	}

}
