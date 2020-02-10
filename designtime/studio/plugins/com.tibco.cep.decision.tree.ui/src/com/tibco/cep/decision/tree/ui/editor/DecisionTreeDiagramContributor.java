package com.tibco.cep.decision.tree.ui.editor;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.actions.ActionFactory;

import com.tibco.cep.decision.tree.ui.actions.DecisionTreeDiagramSearchAction;
import com.tibco.cep.decision.tree.ui.actions.DecisionTreeInteractiveZoomAction;
import com.tibco.cep.decision.tree.ui.actions.DecisionTreeLinkNavigatorAction;
import com.tibco.cep.decision.tree.ui.actions.DecisionTreeMagnifyAction;
import com.tibco.cep.decision.tree.ui.actions.DecisionTreePanAction;
import com.tibco.cep.decision.tree.ui.actions.DecisionTreeSelectAction;
import com.tibco.cep.decision.tree.ui.actions.DecisionTreeZoomAction;
import com.tibco.cep.diagramming.actions.DiagramEditorContributor;

public class DecisionTreeDiagramContributor extends DiagramEditorContributor {

	@SuppressWarnings("unused")
	private IWorkbenchPage workbenchPage;
	private IEditorPart editor;
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.MultiPageEditorActionBarContributor#setActivePage(org.eclipse.ui.IEditorPart)
	 */
	@Override
	public void setActivePage(IEditorPart activeEditor) {
		workbenchPage = getPage();
		if (editor == activeEditor)return;
		editor = activeEditor;
		IActionBars actionBars = getActionBars();
		if (actionBars != null) {}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToMenu(org.eclipse.jface.action.IMenuManager)
	 */
	public void contributeToMenu(IMenuManager menuManager) {
		super.contributeToMenu(menuManager);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.actions.DiagramEditorContributor#contributeInteractiveToolBar(org.eclipse.jface.action.IToolBarManager)
	 */
	protected void contributeInteractiveToolBar(IToolBarManager toolBarManager){
		toolBarManager.add(new DecisionTreeSelectAction(getPage()));
		toolBarManager.add(new DecisionTreePanAction(getPage()));
		toolBarManager.add(new DecisionTreeZoomAction(getPage()));
		toolBarManager.add(new DecisionTreeMagnifyAction(getPage()));
		toolBarManager.add(new DecisionTreeInteractiveZoomAction(getPage()));
		toolBarManager.add(new DecisionTreeLinkNavigatorAction(getPage()));
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.actions.DiagramEditorContributor#contributeSearchHandler(org.eclipse.jface.action.IToolBarManager)
	 */
	protected void contributeSearchHandler(IToolBarManager toolBarManager) {
		toolBarManager.add(new DecisionTreeDiagramSearchAction(getPage()));
		toolBarManager.add(new Separator());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.MultiPageEditorActionBarContributor#setActiveEditor(org.eclipse.ui.IEditorPart)
	 */
	@Override
	public void setActiveEditor(IEditorPart activeEditor) {
		super.setActiveEditor(activeEditor);
		if (editor == activeEditor)return;
		editor = activeEditor;
		try{
			DecisionTreeEditor decisionTreeEditor = (DecisionTreeEditor) editor;
			IActionBars actionBars = getActionBars();
			if (actionBars != null) {
				actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(),decisionTreeEditor.getActionHandler(ActionFactory.COPY.getId(),decisionTreeEditor));
				actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(),decisionTreeEditor.getActionHandler(ActionFactory.PASTE.getId(),decisionTreeEditor));
				actionBars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(),decisionTreeEditor.getActionHandler(ActionFactory.SELECT_ALL.getId(),decisionTreeEditor));
				actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(),decisionTreeEditor.getActionHandler(ActionFactory.CUT.getId(),decisionTreeEditor));
				actionBars.setGlobalActionHandler(ActionFactory.FIND.getId(),decisionTreeEditor.getActionHandler(ActionFactory.FIND.getId(),decisionTreeEditor));
				actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(),decisionTreeEditor.getActionHandler(ActionFactory.DELETE.getId(),decisionTreeEditor));
				actionBars.setGlobalActionHandler(ActionFactory.SAVE.getId(),decisionTreeEditor.getActionHandler(ActionFactory.SAVE.getId(),decisionTreeEditor));

				actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(),decisionTreeEditor.getActionHandler(ActionFactory.UNDO.getId(),decisionTreeEditor));
				actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(),decisionTreeEditor.getActionHandler(ActionFactory.REDO.getId(),decisionTreeEditor));

				actionBars.updateActionBars();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
