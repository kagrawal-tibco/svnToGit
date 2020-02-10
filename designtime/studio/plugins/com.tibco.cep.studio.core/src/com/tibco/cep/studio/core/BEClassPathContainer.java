package com.tibco.cep.studio.core;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;

public class BEClassPathContainer implements IClasspathContainer {

	protected IPath containerPath;
	private IClasspathEntry[] fEntries;
	public static final String BE_CLASSPATH_CONTAINER = "BE_CLASSPATH_CONTAINER"; //$NON-NLS-1$
	
	public BEClassPathContainer(IPath containerPath, IClasspathEntry[] entries){
		this.containerPath = containerPath;
		this.fEntries = entries;
	}
	
	@Override
	public IClasspathEntry[] getClasspathEntries() {
		return fEntries;
	}

	@Override
	public String getDescription() {
			return "BE Core Libraries";	
	}

	@Override
	public int getKind() {
		return IClasspathContainer.K_APPLICATION;
	}

	@Override
	public IPath getPath() {
		return containerPath;
	}
	
	public boolean isValid(){
		if (containerPath.toString().equals(BE_CLASSPATH_CONTAINER)){
			return true;
		}
		else{
			return false;
		}
	}

}
