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

/*
@author ssailapp
@date Sep 21, 2009 2:17:12 PM
 */

public class NewClusterConfigurationWizard extends Wizard implements INewWizard {

	protected IProject project;
	private IWorkbench workbench;
	protected IStructuredSelection selection;
	protected NewClusterConfigNamePage cddNamePage;
	protected NewClusterConfigTemplatePage cddTemplatePage;
	
	public NewClusterConfigurationWizard() {
		setWindowTitle(Messages.getString("new.clusterconfiguration.wizard.title"));
	}

	@Override
	public void addPages() {
        cddNamePage = new NewClusterConfigNamePage(selection);
        addPage(cddNamePage);
        
        cddTemplatePage = new NewClusterConfigTemplatePage("cddTemplate");
        addPage(cddTemplatePage);
        //addPage()
	}
	
	@Override
	public boolean performFinish() {
        IFile cddFile = cddNamePage.createNewFile();
        if (cddFile == null)
            return false;
        
		// Open an editor on the new file.
		try {
			// this is now done by defining a content type for shared resources
//			cddFile.setCharset(ModelUtils.DEFAULT_ENCODING, new NullProgressMonitor());
			IWorkbenchPage workbenchPage = workbench.getActiveWorkbenchWindow().getActivePage();
			workbenchPage.openEditor
			(new FileEditorInput(cddFile),
					PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(cddFile.getLocation().toOSString()).getId());
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
