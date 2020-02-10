package com.tibco.cep.studio.ui.editors.dependency;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import com.tibco.cep.studio.ui.editors.DiagramEditorInput;

/**
 * 
 * @author ggrigore
 *
 */
public class DependencyDiagramEditorInput extends DiagramEditorInput {
	
	/**
	 * @param rule
	 * @param project
	 */
	public DependencyDiagramEditorInput(IFile rule, IProject project) {
		super(rule, project);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.editors.DiagramEditorInput#getToolTipText()
	 */
	@Override
	public String getToolTipText() {
		return getFile().getName()+"dependencyview";
	}
}
