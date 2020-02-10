package com.tibco.cep.studio.ui.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;

/**
 * @author rhollom
 */

public class NewDisplayModelWizard extends Wizard implements INewWizard {

	protected IProject project;
	private IWorkbench workbench;
	protected IStructuredSelection selection;
	protected NewDisplayModelNamePage dispModelNamePage;
	
	public NewDisplayModelWizard() {
		setWindowTitle(Messages.getString("new.displaymodel.wizard.title"));
	}

	@Override
	public void addPages() {
        dispModelNamePage = new NewDisplayModelNamePage(selection);
        addPage(dispModelNamePage);
	}
	
	@Override
	public boolean performFinish() {
        IFile dispFile = dispModelNamePage.createNewFile();
        if (dispFile == null)
            return false;
        
		// Open an editor on the new file.
		try {
			IWorkbenchPage workbenchPage = workbench.getActiveWorkbenchWindow().getActivePage();
			workbenchPage.openEditor
			(new FileEditorInput(dispFile),
					PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(dispFile.getLocation().toOSString()).getId());
		} catch (PartInitException pie) {
			StudioUIPlugin.log(pie);
		} 
        return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
	}

	public IProject getProject() {
		return project;
	}
	
}
