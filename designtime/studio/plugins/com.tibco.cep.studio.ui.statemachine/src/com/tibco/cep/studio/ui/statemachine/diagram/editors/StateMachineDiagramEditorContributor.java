package com.tibco.cep.studio.ui.statemachine.diagram.editors;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.actions.ActionFactory;

import com.tibco.cep.diagramming.actions.DiagramEditorContributor;
import com.tibco.cep.studio.ui.statemachine.actions.StateMachineInteractiveZoomAction;
import com.tibco.cep.studio.ui.statemachine.actions.StateMachineLinkNavigatorAction;
import com.tibco.cep.studio.ui.statemachine.actions.StateMachineMagnifyAction;
import com.tibco.cep.studio.ui.statemachine.actions.StateMachinePanAction;
import com.tibco.cep.studio.ui.statemachine.actions.StateMachineSelectAction;
import com.tibco.cep.studio.ui.statemachine.actions.StateMachineZoomAction;
import com.tibco.cep.studio.ui.statemachine.diagram.actions.StateMachineDiagramSearchAction;



/**
 * 
 * @author sasahoo
 *
 */
public class StateMachineDiagramEditorContributor extends DiagramEditorContributor{

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
		toolBarManager.add(new StateMachineSelectAction(getPage()));
		toolBarManager.add(new StateMachinePanAction(getPage()));
		toolBarManager.add(new StateMachineZoomAction(getPage()));
		toolBarManager.add(new StateMachineMagnifyAction(getPage()));
		toolBarManager.add(new StateMachineInteractiveZoomAction(getPage()));
		toolBarManager.add(new StateMachineLinkNavigatorAction(getPage()));
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.actions.DiagramEditorContributor#contributeSearchHandler(org.eclipse.jface.action.IToolBarManager)
	 */
	protected void contributeSearchHandler(IToolBarManager toolBarManager) {
		toolBarManager.add(new StateMachineDiagramSearchAction(getPage()));
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
			StateMachineEditor stateMachineEditor = (StateMachineEditor) editor;
			IActionBars actionBars = getActionBars();
			if (actionBars != null) {
				actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(),stateMachineEditor.getActionHandler(ActionFactory.COPY.getId(),stateMachineEditor));
				actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(),stateMachineEditor.getActionHandler(ActionFactory.PASTE.getId(),stateMachineEditor));
				actionBars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(),stateMachineEditor.getActionHandler(ActionFactory.SELECT_ALL.getId(),stateMachineEditor));
				actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(),stateMachineEditor.getActionHandler(ActionFactory.CUT.getId(),stateMachineEditor));
				actionBars.setGlobalActionHandler(ActionFactory.FIND.getId(),stateMachineEditor.getActionHandler(ActionFactory.FIND.getId(),stateMachineEditor));
				actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(),stateMachineEditor.getActionHandler(ActionFactory.DELETE.getId(),stateMachineEditor));
				actionBars.setGlobalActionHandler(ActionFactory.SAVE.getId(),stateMachineEditor.getActionHandler(ActionFactory.SAVE.getId(),stateMachineEditor));
				
				actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(),stateMachineEditor.getActionHandler(ActionFactory.UNDO.getId(),stateMachineEditor));
				actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(),stateMachineEditor.getActionHandler(ActionFactory.REDO.getId(),stateMachineEditor));
				
				actionBars.updateActionBars();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
