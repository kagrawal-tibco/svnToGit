package com.tibco.cep.studio.tester.ui.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/**
 * @author hitesh
 */
public class ProjectTestDataAction implements IWorkbenchWindowActionDelegate {
	
	@SuppressWarnings("unused")
	private IWorkbenchWindow window = null;
	private ISelection _selection;
	private IStructuredSelection strSelection;
	private IProject project;	

//	public ProjectTestDataAction(String title) {
//		this(title, null);
//	}

//	public ProjectTestDataAction(String title, IWorkbenchWindow window) {
//		super(title);
//		this.window = window;
//		this.setId(ICommandIds.CMD_SHOW_TESTDATA);
//		this.setToolTipText(Messages.Test_Data_Tooltip);
//		this.setActionDefinitionId(ICommandIds.CMD_SHOW_TESTDATA);
//		this.setImageDescriptor(ApplicationImages
//				.getImageDescriptor(ApplicationImages.APP_IMAGE_TEST_ACTION));
//	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	@Override
	public void run(IAction action) {
//		IWorkbenchPage page = window.getActivePage();
//		boolean isTestEditorOpen = false;
//		IEditorReference[] editorReferences = page.getEditorReferences();
//		IEditorReference activeEditorReference = null;
//		if (editorReferences != null) {
//			for (IEditorReference editorReference : editorReferences) {
//				if (editorReference.getId().equals(TestDataEditor.ID)) {
//					isTestEditorOpen = true;
//					activeEditorReference = editorReference;
//					break;
//				}
//			}
//		}
//		if (!isTestEditorOpen) {
//			TestDataEditorInput input = new TestDataEditorInput(Messages.Mode_Tooltip);
//			try {
//				page.openEditor(input, TestDataEditor.ID);
//			} catch (PartInitException e) {
//				DecisionTablePlugin.LOGGER.logInfo(e.getMessage());
//			}
//		} else {
//			try {
//				page.openEditor(activeEditorReference.getEditorInput(),TestDataEditor.ID);
//			} catch (PartInitException e) {
//				e.printStackTrace();
//			}
//		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		_selection = selection;
		if (!(_selection.isEmpty()) && _selection instanceof IStructuredSelection) {
			strSelection = (IStructuredSelection)_selection;
			if(strSelection.size() == 1){
				if (!(strSelection.getFirstElement() instanceof IProject)) {
					project = StudioResourceUtils.getCurrentProject(strSelection);
				} else {
					project = (IProject)strSelection.getFirstElement();
				}
				if(project != null && project.isOpen()){
					action.setEnabled(true);
				}else{
					action.setEnabled(false);
				}
			} else {
				action.setEnabled(false);
			}	
		} else if (_selection.isEmpty()) {
			action.setEnabled(false);
		}
	}
}
