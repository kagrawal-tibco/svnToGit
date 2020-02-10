package com.tibco.cep.studio.ui.wizards.export;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.studio.core.doc.DocumentationDescriptor;
import com.tibco.cep.studio.core.doc.DocumentationWriter;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/*
@author ssailapp
@date Jan 19, 2012
 */

public class GenerateDocumentationWizard extends Wizard implements IExportWizard {

	private static final String EXPORT_GENERATE_DOCUMENTATION_SETTINGS = "GenerateDocumentation"; //$NON-NLS-1$
	
	protected IStructuredSelection selection;
	protected IWorkbenchWindow workbenchWindow;
	
	protected GenerateDocumentationWizardMainPage mainPage;
	private IProject project;
	private DocumentationDescriptor docDesc;
	
	public GenerateDocumentationWizard() {
		setWindowTitle(Messages.getString("doc.generator.export.window.title")); //$NON-NLS-1$
		setHelpAvailable(false);
		StudioUIPlugin plugin = StudioUIPlugin.getDefault();
		IDialogSettings workbenchSettings = plugin.getDialogSettings();
		IDialogSettings settings = workbenchSettings.getSection(EXPORT_GENERATE_DOCUMENTATION_SETTINGS);
		if (settings == null) {
			settings = workbenchSettings.addNewSection(EXPORT_GENERATE_DOCUMENTATION_SETTINGS);
		}
		setDialogSettings(settings);
	}

	private void initDocumentationDescriptor() {
		docDesc = new DocumentationDescriptor();
		docDesc.project = project;
		docDesc.location = project.getLocation().toString() + "/doc";
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		Object resource = selection.getFirstElement();
		this.selection = selection;
		this.workbenchWindow = workbench.getActiveWorkbenchWindow();
		if(resource instanceof IResource ) {
			project = StudioResourceUtils.getCurrentProject(selection);
			initDocumentationDescriptor();
			mainPage = new GenerateDocumentationWizardMainPage(Messages.getString("doc.generator.export.window.title"), docDesc);
		}
	}

	@Override
	public void addPages() {
		if (selection.isEmpty() || mainPage == null) {
			MessageDialog.openError(workbenchWindow.getShell(), Messages.getString("doc.generator.export.window.title"), 
					Messages.getString("doc.generator.export.error.invalid.project"));
			dispose();
			return;
		} else {
			addPage(mainPage);
		}
	}
	
	@Override
	public boolean performFinish() {
		DocumentationWriter docWriter = new DocumentationWriter(docDesc);
		boolean success = docWriter.write();
		if (success) {
			try {
				project.refreshLocal(IResource.DEPTH_INFINITE, null);
				return true;
			} catch (CoreException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
}
