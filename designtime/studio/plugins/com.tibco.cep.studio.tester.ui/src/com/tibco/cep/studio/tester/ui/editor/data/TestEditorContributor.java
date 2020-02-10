package com.tibco.cep.studio.tester.ui.editor.data;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;
import org.eclipse.ui.texteditor.ITextEditor;


public class TestEditorContributor extends MultiPageEditorActionBarContributor {

	@SuppressWarnings("unused")
	private IEditorPart activeEditorPart;

	public TestEditorContributor() {
		super();
	}

	protected IAction getAction(ITextEditor editor, String actionID) {
		return (editor == null ? null : editor.getAction(actionID));
	}

	@Override
	public void setActivePage(IEditorPart activeEditor) {
//		IEditorPart editorPart = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//		if (editorPart != null && editorPart instanceof TestDataEditor) {
//			DecisionTableUtil.disableTableActions();
//			DecisionTableUtil.disableDeleteRenameAction();
//			// DecisionTableUtil.disableEditMenu();
//		}
//		if (activeEditorPart == activeEditor)
//			return;
	}

	public void init() {
//		IWorkbenchPage page = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage();
//		IPerspectiveDescriptor descriptor = page.getPerspective();
//		if (descriptor != null) {
//			DecisionTableUtil.disableTableActions();
//			DecisionTableUtil.disableDeleteRenameAction();
//		}
	}
}
