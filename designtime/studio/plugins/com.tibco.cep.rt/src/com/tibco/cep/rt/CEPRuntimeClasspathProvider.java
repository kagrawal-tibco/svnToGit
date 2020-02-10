package com.tibco.cep.rt;

import java.io.File;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.URIUtil;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

import com.tibco.cep.tpcl.AbstractRuntimeBundleClasspathProvider;
import com.tibco.cep.tpcl.IRuntimeBundleClasspathProvider;

public class CEPRuntimeClasspathProvider extends AbstractRuntimeBundleClasspathProvider implements IRuntimeBundleClasspathProvider {

	final private static String[] bundleNames = { 
		"com.tibco.cep.bpmn.common", //$NON-NLS-1$
		    "org.eclipse.emf.ecore", //$NON-NLS-1$
			"org.eclipse.emf.ecore.xmi", //$NON-NLS-1$
			"org.eclipse.emf.common", //$NON-NLS-1$
			"com.tibco.cep.datamodeling.common", //$NON-NLS-1$
			"com.tibco.cep.studio.common", //$NON-NLS-1$
			"com.tibco.cep.studio.dashboard.common", //$NON-NLS-1$
			"com.tibco.cep.decision.table.common", //$NON-NLS-1$
			"com.tibco.cep.eventstreamprocessing.common" //$NON-NLS-1$
	};

	public CEPRuntimeClasspathProvider() {
	}

	@Override
	public Bundle getBundle() {
		return Activator.getDefault().getBundle();
	}

	@Override
	public File[] getClasspath() throws BundleException {
		List<File> cp = new ArrayList<File>();
		cp.addAll(Arrays.asList(super.getClasspath()));
		for (String bundleName : bundleNames) {
			Bundle bundle = Platform.getBundle(bundleName);
			if (bundle != null) {
				try {
					File file = FileLocator.getBundleFile(bundle);
					String fileStr= FileLocator.toFileURL(URIUtil.toURL(new URI(bundle.getLocation()))).getPath();
					fileStr= URLDecoder.decode(fileStr, "UTF-8"); //$NON-NLS-1$
					IPath p = new Path(fileStr);
					if(p.getFileExtension() != null && p.getFileExtension().equalsIgnoreCase("jar")) {
						
						if (file.exists()) {
							cp.add(FileLocator.getBundleFile(bundle));
						}
					} else {
						File f = new File(file,"bin");
						if(f.exists()) {
							cp.add(f);
						}
					}
				} catch (Exception e) {
					Activator.log("could not find bundle for '" + bundleName + "'", e); //$NON-NLS-1$
				}
			}
		}

		return cp.toArray(new File[cp.size()]);
	}

}
