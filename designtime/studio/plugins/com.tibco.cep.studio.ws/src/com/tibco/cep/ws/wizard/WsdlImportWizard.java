package com.tibco.cep.ws.wizard;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.ws.utils.Messages;

/**
 * @author majha
 */

public class WsdlImportWizard extends Wizard implements IImportWizard {

	protected String projName;
	private IProject project;

	private boolean initialize;
	private NewWsdlImportPage wsdlImportPage;

	public WsdlImportWizard() {
		super();
	}

	@Override
	public boolean performFinish() {
		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {

			protected void execute(IProgressMonitor monitor)
					throws CoreException, InvocationTargetException,
					InterruptedException {
				monitor.beginTask("Importing Wsdl...", 100);
				monitor.worked(10);
				try {
					String error = wsdlImportPage.performFinish(monitor);
					if(!error.trim().isEmpty()) {
						final StringBuffer errors = new StringBuffer();
						errors.append("WSDL Import completed with following:").append("\n\n");
						errors.append(error).append("\n");
						MessageDialog.openInformation(getShell(), "WSDL Import Log",errors.toString());
					}
					else{
						MessageDialog.openInformation(getShell(), "WSDL Import Log","WSDL Import completed");
					}
				} catch (Exception e) {
					String localizedMessage = e.getLocalizedMessage();
					String msg = localizedMessage != null? localizedMessage : e.toString();
					MessageDialog.openError(getShell(), "WSDL Import Error",
							"error while importing wsdl\n" + msg);

				}
				if(project != null){
					project.refreshLocal(IProject.DEPTH_INFINITE, null);
				}
				monitor.done();
			}

		};
		try {
			getContainer().run(false, true, op);
			if (project != null) {
				project.refreshLocal(IProject.DEPTH_INFINITE, null);
			}
		} catch (Exception e) {
			String localizedMessage = e.getLocalizedMessage();
			String msg = localizedMessage != null? localizedMessage : e.toString();
			MessageDialog.openError(getShell(), "Error", msg);
			return false;
		}
		return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("WSDL Import");
		try {
			project = StudioResourceUtils.getProjectForWizard(selection);
			if (project == null) {
				MessageDialog.openError(getShell(), Messages
						.getString("Project_selection_Error"), Messages
						.getString("Project_selection_Error_Message"));
				initialize = false;
				return;
			}
			this.projName = project.getName();
			setNeedsProgressMonitor(true);
			wsdlImportPage = new NewWsdlImportPage("Import WSDL",selection,project);

		} catch (Exception e) {
			String localizedMessage = e.getLocalizedMessage();
			String msg = localizedMessage != null? localizedMessage : e.toString();
			MessageDialog.openError(getShell(), "Error", "error while importing wsdl\n" + msg);
			initialize = false;
		}
		initialize = true;
	}

	public void addPages() {
		super.addPages();
		if (initialize)
			super.addPage(wsdlImportPage);
	}

	public boolean performCancel() {
		return super.performCancel();
	}
	
	public void setProject(IProject project) {
		this.project = project;
	}
}