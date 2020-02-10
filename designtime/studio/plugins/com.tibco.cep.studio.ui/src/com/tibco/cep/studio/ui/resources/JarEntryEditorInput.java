package com.tibco.cep.studio.ui.resources;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.PlatformUI;

public class JarEntryEditorInput implements IStorageEditorInput {

	private IStorage fJarEntryFile;
	private String fProjectName;

	public JarEntryEditorInput(IStorage jarEntryFile, String projectName) {
		this.fJarEntryFile = jarEntryFile;
		this.fProjectName = projectName;
	}

	public String getProjectName() {
		return fProjectName;
	}

	@Override
	public IStorage getStorage() throws CoreException {
		return fJarEntryFile;
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();
		return registry.getImageDescriptor(fJarEntryFile.getFullPath()
				.getFileExtension());
	}

	@Override
	public String getName() {
		return fJarEntryFile.getName();
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return fJarEntryFile.getFullPath().toString();
	}

	@Override
	public Object getAdapter(Class adapter) {
		if (IStorage.class.equals(adapter)) {
			try {
				return getStorage();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof JarEntryEditorInput)) {
			return false;
		}
		JarEntryEditorInput input = (JarEntryEditorInput) obj;
		if (!fProjectName.equals(input.getProjectName())) {
			return false;
		}
		
		try {
			if (!fJarEntryFile.equals(input.getStorage())) {
				return false;
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return true;
	}

}
