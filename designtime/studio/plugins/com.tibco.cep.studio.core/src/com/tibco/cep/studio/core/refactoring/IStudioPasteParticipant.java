package com.tibco.cep.studio.core.refactoring;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;

public interface IStudioPasteParticipant {

	IStatus pasteElement(String newName, IResource elementToPaste, IContainer target, boolean overwrite, IProgressMonitor pm) 
		throws CoreException, OperationCanceledException;
	
	boolean isSupportedPaste(IResource resource, IContainer target);
	IStatus validateName(IResource resource, IContainer target, String newName);
	void setProjectPaste(boolean projectPaste);
	boolean isProjectPaste();
//	String getUniqueName(IResource resource, IContainer target);
}
