package com.tibco.cep.studio.cluster.topology.editors;

/**
 * 
 * @author hitesh
 *
 */

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.actions.ActionFactory;

import com.tibco.cep.diagramming.actions.DiagramEditorContributor;
import com.tibco.cep.studio.cluster.topology.actions.ClusterTopologyInteractiveZoomAction;
import com.tibco.cep.studio.cluster.topology.actions.ClusterTopologyLinkNavigatorAction;
import com.tibco.cep.studio.cluster.topology.actions.ClusterTopologyMagnifyAction;
import com.tibco.cep.studio.cluster.topology.actions.ClusterTopologyPanAction;
import com.tibco.cep.studio.cluster.topology.actions.ClusterTopologySelectAction;
import com.tibco.cep.studio.cluster.topology.actions.ClusterTopologyZoomAction;

public class ClusterTopologyEditorContributor extends DiagramEditorContributor {

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
	 * @see com.tibco.cep.diagramming.actions.DiagramEditorContributor#contributeUtilitiesToolBar(org.eclipse.jface.action.IToolBarManager)
	 */
	protected void contributeUtilitiesToolBar(IToolBarManager toolBarManager){
		
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.actions.DiagramEditorContributor#contributeInteractiveToolBar(org.eclipse.jface.action.IToolBarManager)
	 */
	@Override
	protected void contributeInteractiveToolBar(IToolBarManager toolBarManager) {
		toolBarManager.add(new ClusterTopologySelectAction(getPage()));
		toolBarManager.add(new ClusterTopologyPanAction(getPage()));
		toolBarManager.add(new ClusterTopologyZoomAction(getPage()));
		toolBarManager.add(new ClusterTopologyMagnifyAction(getPage()));
		toolBarManager.add(new ClusterTopologyInteractiveZoomAction(getPage()));
		toolBarManager.add(new ClusterTopologyLinkNavigatorAction(getPage()));
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
			ClusterTopologyEditor clusterTopologyEditor = (ClusterTopologyEditor) editor;
			IActionBars actionBars = getActionBars();
			if (actionBars != null) {
				actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(),clusterTopologyEditor.getActionHandler(ActionFactory.COPY.getId(),clusterTopologyEditor));
				actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(),clusterTopologyEditor.getActionHandler(ActionFactory.PASTE.getId(),clusterTopologyEditor));
				actionBars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(),clusterTopologyEditor.getActionHandler(ActionFactory.SELECT_ALL.getId(),clusterTopologyEditor));
				actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(),clusterTopologyEditor.getActionHandler(ActionFactory.CUT.getId(),clusterTopologyEditor));
				actionBars.setGlobalActionHandler(ActionFactory.FIND.getId(),clusterTopologyEditor.getActionHandler(ActionFactory.FIND.getId(),clusterTopologyEditor));
				actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(),clusterTopologyEditor.getActionHandler(ActionFactory.DELETE.getId(),clusterTopologyEditor));
				actionBars.setGlobalActionHandler(ActionFactory.SAVE.getId(),clusterTopologyEditor.getActionHandler(ActionFactory.SAVE.getId(),clusterTopologyEditor));
				actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(),clusterTopologyEditor.getActionHandler(ActionFactory.UNDO.getId(),clusterTopologyEditor));
				actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(),clusterTopologyEditor.getActionHandler(ActionFactory.REDO.getId(),clusterTopologyEditor));
				
				actionBars.updateActionBars();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
