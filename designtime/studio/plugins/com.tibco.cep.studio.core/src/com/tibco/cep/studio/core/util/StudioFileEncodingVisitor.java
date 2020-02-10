package com.tibco.cep.studio.core.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class StudioFileEncodingVisitor implements IResourceVisitor {

	@Override
	public boolean visit(IResource resource) throws CoreException {
		// set the encoding of all appropriate files as UTF-8
		if (!(resource instanceof IFile)) {
			return true;
		}
		IFile file = (IFile) resource;
		IndexUtils.isSupportedType(file);
		file.setCharset("UTF-8", new NullProgressMonitor());
		return false;
	}

}
