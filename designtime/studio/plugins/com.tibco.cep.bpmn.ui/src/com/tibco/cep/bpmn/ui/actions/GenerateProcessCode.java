package com.tibco.cep.bpmn.ui.actions;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.saveAllEditors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.wizards.GenerateProcessWizard;

public class GenerateProcessCode extends BpmnProjectSelectionAction implements IWorkbenchWindowActionDelegate,IObjectActionDelegate,IEditorActionDelegate {
	
	protected ISelection fSelection;
	
	protected IResource fSelectedResource;
	protected Shell fShell;
	protected IWorkbenchWindow fWindow;
	private IWorkbenchPage fPage;

	@SuppressWarnings("unused")
	private IProject fProject;

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IWorkbenchWindow window) {
		this.fWindow = window;
		this.fShell = this.fWindow.getShell();

	}
	
	@Override
	public void run(IAction action) {
		try {
			if(getProject() != null) {
				// by this time the project is a valid studio project and is open
				if(fWindow == null) {
					this.fWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					this.fShell = this.fWindow.getShell();
				}
				//Save all editors if dirty for the project selected
				if(saveAllEditors(fWindow.getActivePage(), getProject().getName(), true)){
					boolean status = MessageDialog.openQuestion(this.fShell,
							Messages.getString("bpmn.build.wizard.save.editors.title"),//$NON-NLS-1$ 
							Messages.getString("bpmn.build.wizard.save.editors")); //$NON-NLS-1$
					if(status){
						saveAllEditors(fWindow.getActivePage(), getProject().getName(), false);
					}else{
						return;
					}
				}
				
				GenerateProcessWizard wizard = new GenerateProcessWizard(
						this.fWindow, Messages.getString("bpmn.build.wizard.title"), getProject());
				wizard.setNeedsProgressMonitor(true);
				WizardDialog dialog = new WizardDialog(this.fShell, wizard) {
					@Override
					protected void createButtonsForButtonBar(Composite parent) {
						super.createButtonsForButtonBar(parent);
						Button finishButton = getButton(IDialogConstants.FINISH_ID);
						finishButton.setText(IDialogConstants.OK_LABEL);
					}
				};
				// dialog.setMinimumPageSize(600,5);
				dialog.create();
				int returnCode = dialog.open();
				if (returnCode == Dialog.OK) {
				} else {
				}
			}
		} catch(Exception e) {
			BpmnUIPlugin.log(e);
		}

	}
	
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.fWindow = targetPart.getSite().getWorkbenchWindow();
		this.fShell = this.fWindow.getShell();
	}
	
	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if(targetEditor!=null) {
			fPage = targetEditor.getEditorSite().getPage();		
			this.fWindow = fPage.getWorkbenchWindow();
			this.fShell = this.fWindow.getShell();
		}
	}
	

}
