package com.tibco.cep.decision.table.actions;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.studio.ui.compare.EMFCompareEditorInput;
import com.tibco.cep.studio.ui.compare.model.AbstractResourceNode;

public class TableCompareEditorInput extends EMFCompareEditorInput {

	private Implementation implementation;

	/**
	 * @param implementation
	 */
	public TableCompareEditorInput(Implementation implementation) {
		super();
		this.implementation = implementation;
	}
	
	/**
	 * @param configuration
	 * @param implementation
	 */
	public TableCompareEditorInput(CompareConfiguration configuration, Implementation implementation) {
		super(configuration);
		this.implementation = implementation;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.history.compare.ICompareEditorInput#getLeftInput(org.eclipse.compare.CompareConfiguration)
	 */
	@Override
	public AbstractResourceNode getLeftInput(CompareConfiguration configuration) throws Exception {
		return createResourceNode(getSelectedObject(true), true);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.history.compare.ICompareEditorInput#getRightInput(org.eclipse.compare.CompareConfiguration)
	 */
	@Override
	public AbstractResourceNode getRightInput(CompareConfiguration configuration) throws Exception {
		return createResourceNode(getSelectedObject(false), false);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.history.compare.EMFCompareEditorInput#getSelectedObject(boolean)
	 */
	protected Object getSelectedObject(boolean isLeft) throws Exception {
		CompareConfiguration compareConfiguration = getCompareConfiguration();
		IStructuredSelection structuredSelection = getSelection();
		if (isLeft) {
			String name = implementation.getName();
			String extension = "rulefunctionImpl";
			compareConfiguration.setProperty(PROP_COMPARE_REVISION_ID_LEFT, "0");
			compareConfiguration.setProperty(PROP_COMPARE_IS_LEFT_REMOTE, true);
			compareConfiguration.setProperty(PROP_COMPARE_EXTN_LEFT, extension);
			compareConfiguration.setProperty(PROP_COMPARE_NAME_LEFT, name);
			return implementation;
		}
		if (structuredSelection != null) {
			if (structuredSelection.size() == 1 && structuredSelection.getFirstElement() instanceof IFile) {
				IFile file = (IFile)structuredSelection.getFirstElement();
				compareConfiguration.setProperty(PROP_COMPARE_IS_RIGHT_REMOTE, false);
				compareConfiguration.setProperty(PROP_COMPARE_EXTN_RIGHT, file.getFileExtension());
				compareConfiguration.setProperty(PROP_COMPARE_NAME_RIGHT, file.getName());
				compareConfiguration.setProperty(PROP_COMPARE_ABS_PATH_RIGHT, file.getLocation().toString());
				return file;
			}
		}
		return super.getSelectedObject(isLeft);
	}
}