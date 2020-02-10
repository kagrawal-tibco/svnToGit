package com.tibco.cep.studio.debug.core.launch;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.core.sourcelookup.containers.AbstractSourceContainer;

import com.tibco.cep.studio.core.resources.JarEntryFile;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;

public class JarEntrySourceContainer extends AbstractSourceContainer implements ISourceContainer {
	public static final String TYPE_ID = StudioDebugCorePlugin.getUniqueIdentifier() + ".containerType.projectLibrary";	 //$NON-NLS-1$

	private String projectName;
	private String jarPath;

	public JarEntrySourceContainer(String path, String projectName) {
		this.jarPath = path;
		this.projectName = projectName;
	}
	
	

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}



	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}



	/**
	 * @return the jarPath
	 */
	public String getJarPath() {
		return jarPath;
	}



	/**
	 * @param jarPath the jarPath to set
	 */
	public void setJarPath(String jarPath) {
		this.jarPath = jarPath;
	}



	@Override
	public Object[] findSourceElements(String name) throws CoreException {
		return new Object[]{new JarEntryFile(getJarPath(),name,getProjectName())};
	}

	@Override
	public String getName() {
		return getJarPath();
	}



	@Override
	public ISourceContainerType getType() {
		return getSourceContainerType(TYPE_ID);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return obj instanceof JarEntrySourceContainer &&
			((JarEntrySourceContainer)obj).getName().equals(getName());
	}



	@Override
	public boolean isComposite() {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

}
