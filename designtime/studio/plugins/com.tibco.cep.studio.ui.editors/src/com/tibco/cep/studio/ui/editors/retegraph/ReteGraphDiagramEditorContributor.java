package com.tibco.cep.studio.ui.editors.retegraph;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IEditorPart;

import com.tibco.cep.diagramming.actions.DiagramEditorContributor;
import com.tibco.cep.studio.ui.diagrams.actions.RefreshDiagramAction;

/**
 * 
 * @author ggrigore
 *
 */
public class ReteGraphDiagramEditorContributor extends DiagramEditorContributor{

	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.MultiPageEditorActionBarContributor#setActivePage(org.eclipse.ui.IEditorPart)
	 */
	@Override
	public void setActivePage(IEditorPart activeEditor) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.actions.DiagramEditorContributor#contributeUtilitiesToolBar(org.eclipse.jface.action.IToolBarManager)
	 */
	protected void contributeUtilitiesToolBar(IToolBarManager toolBarManager){
		toolBarManager.add(new RefreshDiagramAction(getPage()));
		toolBarManager.add(new Separator());
	}
}
