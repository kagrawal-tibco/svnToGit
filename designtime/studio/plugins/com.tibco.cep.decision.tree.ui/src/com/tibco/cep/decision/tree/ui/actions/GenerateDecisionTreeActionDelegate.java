package com.tibco.cep.decision.tree.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.tree.ui.editor.DecisionTreeEditor;
import com.tibco.cep.decision.tree.ui.editor.DecisionTreeEditorInput;
import com.tibco.cep.studio.decision.table.editor.input.IDecisionTableEditorInput;
import com.tibco.cep.studio.decision.table.ui.editors.DefaultDecisionTableEditorInput;

public class GenerateDecisionTreeActionDelegate implements IObjectActionDelegate {
	
	private ISelection selection; 
	
	public void run(IAction action) {
		if(selection instanceof IStructuredSelection){
			final IFile file = (IFile)((IStructuredSelection)selection).getFirstElement();
			Display.getDefault().asyncExec(new Runnable(){
				public void run() {
					IDecisionTableEditorInput dtEditorInput = new DefaultDecisionTableEditorInput(file);
					dtEditorInput.loadModel();
					final Table tableEModel = dtEditorInput.getTableEModel();
						if (tableEModel == null) {
							showError("Error", "Decision Table is empty");
						} else {
							IFile dTreeFile = getDecisionTreeFile(file);
							final DecisionTreeEditorInput input = new DecisionTreeEditorInput(dTreeFile, null);

							Job translateJob = new Job("Converting Decision Table to Decision Tree") {
								@Override
								protected IStatus run(IProgressMonitor monitor) {
									monitor.beginTask("Initializing conversion...", 100);
									input.setTableEModel(tableEModel);
									monitor.worked(20);
									monitor.subTask("Converting...");
									new DecisionTableTreeAdapter(input, monitor).translateTableToTree();
									monitor.done();
									return Status.OK_STATUS;
								}
							};
							translateJob.setUser(true);
							translateJob.schedule();
							
							try {
								dTreeFile.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
							} catch (CoreException e1) {
								e1.printStackTrace();
							}
							
							try {
								boolean pageExists = false;
								IWorkbenchPage[] pages = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPages();
								for(IWorkbenchPage page : pages) {
									IEditorReference[] editors = page.getEditorReferences();
									for(IEditorReference editor : editors) {
										IEditorInput editorInput = editor.getEditorInput();
										if (editorInput instanceof DecisionTreeEditorInput) {
											DecisionTreeEditorInput decisionTreeEditorInput = (DecisionTreeEditorInput) editorInput;
											if (decisionTreeEditorInput.getTableEModel() != null && decisionTreeEditorInput.getTableEModel().getName().equals(input.getTableEModel().getName()) 
													&& decisionTreeEditorInput.getTableEModel().getPath().equals(input.getTableEModel().getPath())) {
												PlatformUI.getWorkbench().getActiveWorkbenchWindow().setActivePage(page);
												page.activate(editor.getPart(false));
												pageExists = true;
												break;
												}
											}
										}
										if(pageExists)
											break;
									}
								if( !pageExists ) 
									PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(input, DecisionTreeEditor.ID);
							} catch (PartInitException e) {
								showError("Error",  e.getMessage());
							}
						}
				}
			});
		}
	}

	private IFile getDecisionTreeFile(IFile decisionTableFile) {
		String dTreeFilePath = "/" + decisionTableFile.getProjectRelativePath().toString().replaceAll(".rulefunctionimpl", ".dtree");
		return decisionTableFile.getProject().getFile(dTreeFilePath);
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
	}
	public static void showError(String title, String message) {
		MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),title,message);
	}
}
