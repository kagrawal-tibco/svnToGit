package com.tibco.cep.studio.tester.ui.editor.result;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter;

/**
 * 
 * @author sasahoo
 *
 */
public class TestResultFileInclusionFilter extends OnlyFileInclusionFilter{

	private Set<String> results;
	
	/**
	 * @param inclusions
	 * @param results
	 * @param showSubNode
	 */
	public TestResultFileInclusionFilter(Set<String> inclusions, 
			                             Set<String> results,
			                             boolean showSubNode) {
		super(inclusions);
		this.results = results;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IAdaptable) {
			IResource res = (IResource) ((IAdaptable) element).getAdapter(IResource.class);
			if (res instanceof IFile) {
				IFile file = (IFile) res;
				String path = IndexUtils.getFullPath(file);
				return isEntityFile(file) && results.contains(path);
			}
			if (res instanceof IFolder){
			    IFolder folder = (IFolder)res;
			    visible = false;
                return isVisible(folder);
			}
		}
		return true;
	}
}
