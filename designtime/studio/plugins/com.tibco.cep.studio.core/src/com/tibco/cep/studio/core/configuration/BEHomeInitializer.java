package com.tibco.cep.studio.core.configuration;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;

import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.StudioCorePlugin;

/**
 * 
 * @author majha
 *
 */
public class BEHomeInitializer extends PathVariableInitializer {
	public static final String BE_HOME = "BE_HOME"; //$NON-NLS-1$
	public static final String JUNIT_HOME = "JUNIT_HOME"; //$NON-NLS-1$

	public BEHomeInitializer() {
	}

	@Override
	public void initialize(String variable) {
		if (variable.equals(BE_HOME)) {

			String property = System.getProperty(BE_HOME);
			if (property != null) {
				StudioCore.setClasspathVariable(BE_HOME, new Path(System
						.getProperty(BE_HOME)), new NullProgressMonitor());
			} else {
				IProduct product = Platform.getProduct();
				if (product != null && !product.getId().contains("streambase")) {
					// don't log this error in a streambase shared context
					StudioCorePlugin.log("system property for 'BE_HOME' not set");
				}
			}
		} else if (variable.equals(JUNIT_HOME)) {
	        // add a new entry using the path to the container
			IPath property = JavaCore.getClasspathVariable(JUNIT_HOME);
			if (property != null) {
				StudioCore.setClasspathVariable(JUNIT_HOME, property, new NullProgressMonitor());
			} else {
				IProduct product = Platform.getProduct();
				if (product != null && !product.getId().contains("streambase")) {
					// don't log this error in a streambase shared context
					StudioCorePlugin.log("system property for 'JUNIT_HOME' not set");
				}
			}
		}

	}

}
