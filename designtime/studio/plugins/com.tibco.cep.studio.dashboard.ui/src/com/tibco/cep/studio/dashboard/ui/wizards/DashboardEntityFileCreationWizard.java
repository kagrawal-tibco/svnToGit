package com.tibco.cep.studio.dashboard.ui.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;

import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard;

/**
 * @author anpatil
 *
 */
public class DashboardEntityFileCreationWizard extends EntityFileCreationWizard {

	private String typeName;

	private boolean hideTypeDesc;

	public DashboardEntityFileCreationWizard(String pageName, IStructuredSelection selection, String type, String typeName) {
		this(pageName, selection, type, typeName, false);
	}

	public DashboardEntityFileCreationWizard(String pageName, IStructuredSelection selection, String type, String typeName, boolean hideTypeDesc) {
		super(pageName, selection, type);
		this.typeName = typeName;
		this.hideTypeDesc = hideTypeDesc;
	}

	@Override
	protected String getNewFileLabel() {
		return Messages.getString("Wizard.BE.FileName.title",typeName);
	}


	@Override
	protected void createTypeDescControl(Composite parent) {
		if (hideTypeDesc == true){
			//we do not want to show the type description
			return;
		}
		super.createTypeDescControl(parent);
	}

	@Override
	public String getTypeDesc() {
		if (hideTypeDesc == true){
			//we are not showing the type description, do description is blank
			return "";
		}
		return super.getTypeDesc();
	}

	@Override
    public IFile getModelFile() {
		IPath fileNameWithPath = getContainerFullPath().append(getFileName()+"."+BEViewsElementNames.getExtension(_type));
		return ResourcesPlugin.getWorkspace().getRoot().getFile(fileNameWithPath);
    }

}