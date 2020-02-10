package com.tibco.cep.studio.ui.actions;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.saveAllEditors;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.tibco.cep.studio.core.doc.DocumentationDescriptor;
import com.tibco.cep.studio.core.doc.DocumentationWriter;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Dec 7, 2011
 */

public class GenerateDocumentationAction extends ProjectSelection implements IWorkbenchWindowActionDelegate, IObjectActionDelegate {

	protected Shell fShell;
	protected IResource fSelectedResource;

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.fWindow = targetPart.getSite().getWorkbenchWindow();
		this.fShell = this.fWindow.getShell();
	}

	@Override
	public void init(IWorkbenchWindow window) {
		this.fWindow = window;
		this.fShell = this.fWindow.getShell();
	}

	@Override
	public void run(IAction action) {
		try {
			if (fProject == null) {
				return;
			}

			if (saveAllEditors(fWindow.getActivePage(), fProject.getName(), true)) {
				boolean status = MessageDialog.openQuestion(this.fShell, "Save Editors",
						"Do you want to save all editors associated with this project \"" + fProject.getName() + "\" ?");
				if (status) {
					saveAllEditors(fWindow.getActivePage(), fProject.getName(), false);
				} else {
					return;
				}
			}

			GenerateDocumentationDialog dialog = new GenerateDocumentationDialog(fShell, fProject);
			DocumentationDescriptor docDesc = dialog.open();
			if (docDesc == null)
				return;
			DocumentationWriter docWriter = new DocumentationWriter(docDesc);
			boolean success = docWriter.write();
			if (success) {
				fProject.refreshLocal(IResource.DEPTH_INFINITE, null);
				PanelUiUtil.showInfoMessage("Documentation generation successful.");
			}
		} catch(Exception e) {
			StudioUIPlugin.log(e);
		}

	}


}
