package com.tibco.cep.studio.ui.editors.concepts;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.actions.ActionFactory;

import com.tibco.cep.diagramming.actions.DiagramEditorContributor;
import com.tibco.cep.studio.ui.diagrams.actions.RefreshDiagramAction;

public class ConceptDiagramEditorContributor extends DiagramEditorContributor{

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
			ConceptDiagramEditor conceptDiagramEditor = (ConceptDiagramEditor) editor;
			IActionBars actionBars = getActionBars();
			if (actionBars != null) {
				actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(),conceptDiagramEditor.getActionHandler(ActionFactory.DELETE.getId(),conceptDiagramEditor));
				
				actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(),conceptDiagramEditor.getActionHandler(ActionFactory.UNDO.getId(),conceptDiagramEditor));
				actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(),conceptDiagramEditor.getActionHandler(ActionFactory.REDO.getId(),conceptDiagramEditor));
				

			actionBars.updateActionBars();
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
}
