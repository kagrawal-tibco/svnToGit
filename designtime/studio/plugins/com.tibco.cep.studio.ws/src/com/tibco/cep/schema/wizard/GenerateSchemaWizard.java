package com.tibco.cep.schema.wizard;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ws.WsPlugin;
import com.tibco.cep.ws.utils.Messages;

/**
 * @author majha
 */

public class GenerateSchemaWizard extends Wizard implements IImportWizard {

	private String projName;
	private IProject project;

	private boolean initialize;
	private GenerateSchemaPage generateSchemaPage;

	public GenerateSchemaWizard() {
		super();
	}

	@Override
	public boolean performFinish() {
		IRunnableWithProgress runnable = new IRunnableWithProgress() {

			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				monitor.beginTask("Generating Schema...", 100);
				monitor.worked(10);
				try {
					generateSchemaPage.performFinish(monitor);
				} catch (Exception e) {
					String localizedMessage = e.getLocalizedMessage();
					String msg = localizedMessage != null? localizedMessage : e.toString();
					String errorMessage = "error while generating schema \n" + msg;
					MessageDialog.openError(getShell(), "Error",
							errorMessage);
					WsPlugin.log(errorMessage, e);
				}
				monitor.done();
			}

		};
		try {
			getContainer().run(false, true, runnable);

		} catch (Exception e) {
			String localizedMessage = e.getLocalizedMessage();
			String msg = localizedMessage != null? localizedMessage : e.toString();
			MessageDialog.openError(getShell(), Messages
					.getString("DBConcept_error"), msg);
			return false;
		}
		return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("Generate Schema");
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
			generateSchemaPage = new GenerateSchemaPage(projName);

		} catch (Exception e) {
			String localizedMessage = e.getLocalizedMessage();
			String msg = localizedMessage != null? localizedMessage : e.toString();
			MessageDialog.openError(getShell(), "Error",
					"error while generating schema \n" + msg);
			initialize = false;
		}
		initialize = true;
	}

	public void addPages() {
		super.addPages();
		if (initialize)
			super.addPage(generateSchemaPage);
	}

	public boolean performCancel() {
		return super.performCancel();
	}
}