package com.tibco.cep.decision.tree.ui.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

import com.tibco.cep.decision.tree.ui.util.Messages;;

/*
 @author ssailapp
 @date Sep 13, 2011
 */

public class DecisionTreeNewWizard extends Wizard implements INewWizard {

	private DecisionTreeMainWizardPage mainPage;
	private IWorkbench workbench;
	private IStructuredSelection selection;

	public DecisionTreeNewWizard() {
		setWindowTitle(Messages.getString("new.decisiontree.wizard.title"));
	}
	
	public void addPages() {
		mainPage = new DecisionTreeMainWizardPage(workbench, selection);
		addPage(mainPage);
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
	}

	public boolean performFinish() {
		IFile newFile = mainPage.createNewFile();
		if (newFile == null)
			return false;
		
		// open newly created file in the editor
		IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
		if (page != null) {
			try {
				IDE.openEditor(page, newFile, true);
			} catch (PartInitException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
}
