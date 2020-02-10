package com.tibco.cep.bpmn.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class BpmnOpenProjectWizard extends Wizard {
	
	private BpmnOpenProjectWizardPage projectPage;
	private IWorkbenchWindow window;

	public BpmnOpenProjectWizard(IWorkbenchWindow window) {
		setWindowTitle(Messages.getString("OPEN_XPDL_WIZARD_WINDOW_TITLE"));
		this.window = window;
	}
	public BpmnOpenProjectWizard(IWorkbenchWindow window,String title) {
		setWindowTitle(title);
		this.window = window;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		projectPage = new BpmnOpenProjectWizardPage();
		addPage(projectPage);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performCancel()
	 */
	@Override
	public boolean performCancel() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		final String fullFilename = projectPage.getFilePathText().getText();
		@SuppressWarnings("unused")
		final String filename = projectPage.getGraphNameText().getText();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
				try {
					monitor.beginTask(Messages.getString("OPEN_GRAPH"), 100);
					monitor.worked(10);
					if (fullFilename != null) {
						@SuppressWarnings("unused")
						IWorkbenchPage page = window.getActivePage();
						@SuppressWarnings("unused")
						IEditorInput input = null;
//						try {
//							input = new DecisionGraphEditorInput("xpdl", filename, fullFilename , monitor);
//							IEditorPart editor = page.openEditor(input,	DecisionGraphEditor.ID);
//							if (editor instanceof DecisionGraphEditor){
//								DecisionGraphEditorInput dtInput = (DecisionGraphEditorInput)((DecisionGraphEditor)editor).getEditorInput();
//								dtInput.setextension1Name(filename);
//								((DecisionGraphEditor)editor).inputChanged();
//							}
//						} catch (PartInitException e) {
//							e.printStackTrace();
//						}
					}

				} catch (Exception e) {
					monitor.setTaskName(Messages.getString("OPEN_XPDL_ERROR"));
					monitor.done();
					throw new RuntimeException(e);
				}
			}
		};
		try {
			getContainer().run(false, true, op);
		} catch (InvocationTargetException e) {
			BpmnUIPlugin.log(e);
		} catch (InterruptedException e) {
			BpmnUIPlugin.log(e);
		}
		return true;
	}

	public BpmnOpenProjectWizardPage getProjectPage() {
		return projectPage;
	}

	public void setProjectPage(BpmnOpenProjectWizardPage projectPage) {
		this.projectPage = projectPage;
	}

}
