package com.tibco.cep.studio.core.refactoring;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IRegion;
import org.eclipse.ltk.core.refactoring.TextEditBasedChangeGroup;
import org.eclipse.ltk.core.refactoring.TextEditChangeGroup;
import org.eclipse.ltk.core.refactoring.TextFileChange;

/**
 * A specialized TextFileChange that does not display a preview when the change
 * is selected in the refactoring wizard.  This class is useful for very large
 * file refactorings, such as Decision Tables with thousands of rows.
 * 
 * @author rhollom
 *
 */
public class NoPreviewTextFileChange extends TextFileChange {

	public NoPreviewTextFileChange(String name, IFile file) {
		super(name, file);
	}

	@Override
	public String getCurrentContent(IProgressMonitor pm) throws CoreException {
		return "Preview not available for this resource";
	}

	@Override
	public String getPreviewContent(IProgressMonitor pm) throws CoreException {
		return "Preview not available for this resource";
	}

	@Override
	public String getPreviewContent(TextEditBasedChangeGroup[] changeGroups,
			IRegion region, boolean expandRegionToFullLine,
			int surroundingLines, IProgressMonitor pm) throws CoreException {
		return "Preview not available for this resource";
	}

	@Override
	public String getPreviewContent(TextEditChangeGroup[] changeGroups,
			IRegion region, boolean expandRegionToFullLine,
			int surroundingLines, IProgressMonitor pm) throws CoreException {
		return "Preview not available for this resource";
	}

}
