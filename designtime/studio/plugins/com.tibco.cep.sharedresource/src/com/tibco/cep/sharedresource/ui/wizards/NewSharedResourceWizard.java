package com.tibco.cep.sharedresource.ui.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.studio.ui.wizards.INewSharedResourceWizard;

/*
@author ssailapp
@date Sep 21, 2009 2:17:12 PM
 */

public abstract class NewSharedResourceWizard extends Wizard implements INewSharedResourceWizard {

	protected IProject project;
	private IWorkbench workbench;
	protected IStructuredSelection selection;
	private NewSharedResourceWizardPage newSharedResourceWizardPage;	
	protected boolean allowOpenEditor;
	
	public NewSharedResourceWizard() {
		setOpenEditor(true);
	}
	
	@Override
	public abstract void addPages();
	
	@Override
	public boolean performFinish() {
		if (newSharedResourceWizardPage == null)
			return false;
		IFile resFile = newSharedResourceWizardPage.createNewFile();
        if (resFile == null)
            return false;
        
		// Open an editor on the new file.
		try {
			// this is now done by defining a content type for shared resources
//			resFile.setCharset(ModelUtils.DEFAULT_ENCODING, new NullProgressMonitor());
			resFile.refreshLocal(0, null);
			
			IWorkbenchPage workbenchPage = workbench.getActiveWorkbenchWindow().getActivePage();
			if(canOpenEditor()) {
				workbenchPage.openEditor
				(new FileEditorInput(resFile),
						PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(resFile.getLocation().toOSString()).getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return true;
	}

	public void setWizardPage(NewSharedResourceWizardPage page) {
		this.newSharedResourceWizardPage = page;
	}
	
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
	}

	public IProject getProject() {
		return project;
	}
	
	@Override
	public boolean canOpenEditor() {
		return allowOpenEditor;
	}
	
	@Override
	public void setOpenEditor(boolean allow) {
		this.allowOpenEditor = allow;
	}
	
	@Override
	public IFile getFile() {
		return null;
	}
}
