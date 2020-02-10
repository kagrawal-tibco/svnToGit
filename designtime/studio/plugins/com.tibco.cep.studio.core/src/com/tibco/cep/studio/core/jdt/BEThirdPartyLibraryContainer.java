package com.tibco.cep.studio.core.jdt;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;

public class BEThirdPartyLibraryContainer implements IClasspathContainer {

	protected IPath containerPath;
	private IClasspathEntry[] fEntries;
	public static final String BE_THIRD_PARTY_LIBRARY_CONTAINER = "BE_THIRD_PARTY_LIBRARY_CONTAINER"; //$NON-NLS-1$

	public BEThirdPartyLibraryContainer(IPath containerPath, IClasspathEntry[] entries) {
		this.containerPath = containerPath;
		this.fEntries = entries;
	}

	@Override
	public IClasspathEntry[] getClasspathEntries() {
		return fEntries;
	}

	@Override
	public String getDescription() {
		return "BE Third Party Libraries";
	}

	@Override
	public int getKind() {
		return IClasspathContainer.K_APPLICATION;
	}

	@Override
	public IPath getPath() {
		return containerPath;
	}

	public boolean isValid() {
		if (containerPath.toString().equals(BE_THIRD_PARTY_LIBRARY_CONTAINER)) {
			return true;
		} else {
			return false;
		}
	}

}
