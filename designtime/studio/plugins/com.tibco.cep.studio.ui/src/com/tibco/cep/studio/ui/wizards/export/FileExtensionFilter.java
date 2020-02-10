package com.tibco.cep.studio.ui.wizards.export;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.cep.studio.core.util.CommonUtil;

class FileExtensionFilter extends ViewerFilter {

	List<String> extensions;

	FileExtensionFilter(String[] extensions) {
		this.extensions = Arrays.asList(extensions);
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IAdaptable) {
			IResource res = (IResource) ((IAdaptable) element).getAdapter(IResource.class);
			if (res instanceof IFile) {
				IFile file = (IFile) res;
				return extensions.contains(file.getFileExtension());
			}
			if (res instanceof IFolder) {
				return isVisible((IFolder) res);
			}
		}
		return false;
	}

	protected boolean isVisible(Object element) {
		Object[] object = CommonUtil.getResources((IFolder) element);
		for (Object obj : object) {
			if (obj instanceof IFolder) {
				if (isVisible(obj)) {
					return true;
				}
			}
			if (obj instanceof IFile) {
				if (extensions.contains(((IFile) obj).getFileExtension())) {
					return true;
				}
			}
		}
		return false;
	}
}