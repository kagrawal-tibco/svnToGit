package com.tibco.cep.studio.core.jdt;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ClasspathVariableInitializer;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import com.tibco.cep.studio.core.StudioCorePlugin;

public class BEHomeInitializer extends ClasspathVariableInitializer {
	public static final String BE_HOME = "BE_HOME"; //$NON-NLS-1$

	public BEHomeInitializer() {
	}

	@Override
	public void initialize(String variable) {
		if(variable.equals(BE_HOME) && System.getProperty(BE_HOME) != null)
			try {
				JavaCore.setClasspathVariable(BE_HOME, new Path(System.getProperty(BE_HOME)), new NullProgressMonitor());
			} catch (JavaModelException e) {
				StudioCorePlugin.log(e);
			}
	}

}
