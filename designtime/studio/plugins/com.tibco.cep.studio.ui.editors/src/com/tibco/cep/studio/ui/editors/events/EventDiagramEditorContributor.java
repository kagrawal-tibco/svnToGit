package com.tibco.cep.studio.ui.editors.events;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.actions.ActionFactory;

import com.tibco.cep.diagramming.actions.DiagramEditorContributor;
import com.tibco.cep.studio.ui.diagrams.actions.RefreshDiagramAction;
import com.tibco.cep.studio.ui.editors.events.EventDiagramEditor;

public class EventDiagramEditorContributor extends DiagramEditorContributor{


	private IWorkbenchPage workbenchPage;
	private IEditorPart editor;
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.MultiPageEditorActionBarContributor#setActivePage(org.eclipse.ui.IEditorPart)
	 */
	@Override
	public void setActivePage(IEditorPart activeEditor) {
		// TODO Auto-generated method stub
		
		workbenchPage = getPage();
		if (editor == activeEditor)return;
		editor = activeEditor;
		IActionBars actionBars = getActionBars();
		if (actionBars != null) {}
		
		
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.actions.DiagramEditorContributor#contributeUtilitiesToolBar(org.eclipse.jface.action.IToolBarManager)
	 */
	protected void contributeUtilitiesToolBar(IToolBarManager toolBarManager){
		toolBarManager.add(new RefreshDiagramAction(getPage()));
		toolBarManager.add(new Separator());
	}
	
	public void setActiveEditor(IEditorPart activeEditor) {
		super.setActiveEditor(activeEditor);
		if (editor == activeEditor)return;
		editor = activeEditor;
		try{
			EventDiagramEditor EventDiagramEditor = (EventDiagramEditor) editor;
			IActionBars actionBars = getActionBars();
			if (actionBars != null) {
				actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(),EventDiagramEditor.getActionHandler(ActionFactory.DELETE.getId(),EventDiagramEditor));
				
				actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(),EventDiagramEditor.getActionHandler(ActionFactory.UNDO.getId(),EventDiagramEditor));
				actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(),EventDiagramEditor.getActionHandler(ActionFactory.REDO.getId(),EventDiagramEditor));
				

			actionBars.updateActionBars();
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
}
