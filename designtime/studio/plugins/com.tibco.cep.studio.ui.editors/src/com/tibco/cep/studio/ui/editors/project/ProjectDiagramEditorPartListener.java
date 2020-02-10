package com.tibco.cep.studio.ui.editors.project;

import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.diagramming.drawing.BaseDiagramManager;
import com.tibco.cep.diagramming.drawing.IDiagramManager;
import com.tibco.cep.studio.ui.diagrams.EntityDiagramEditorPartListener;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.graphicaldrawing.TSEGraph;

public class ProjectDiagramEditorPartListener extends EntityDiagramEditorPartListener{

	private ProjectDiagramEditor editor;
	public ProjectDiagramEditorPartListener(ProjectDiagramEditor editor){
		this.editor =  editor;
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.diagrams.EntityEditorPartListener#partActivated(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partActivated(IWorkbenchPart part) {
			//Default Selection of Nodes or Transitions for which is Property View display.
			IDiagramManager diagramManager = editor.getDiagramManager();
//			if((diagramManager.getSelectedNodes().size() == 1 && diagramManager.getSelectedEdges().size() == 0)
//					|| (diagramManager.getSelectedEdges().size() == 1 && diagramManager.getSelectedNodes().size() == 0)){
//				if(diagramManager.getSelectedNodes().size() == 1){
//					setWorkbenchSelection(diagramManager.getSelectedNodes().get(0), (ProjectDiagramEditor)part);
//				}
//				if(diagramManager.getSelectedEdges().size() == 1){
//					setWorkbenchSelection(diagramManager.getSelectedEdges().get(0), (ProjectDiagramEditor)part);
//				}
//			}
//			//If no Node or Edges are selected, then Root State Machine selected by default. 
//			if(diagramManager.getSelectedNodes().size()==0){
				((BaseDiagramManager)diagramManager).waitForInitComplete();
				if (diagramManager.getGraphManager() == null) {
					return;
				}
				StudioUIUtils.setWorkbenchSelection((TSEGraph)diagramManager.getGraphManager().getMainDisplayGraph(), editor);
//			}
			super.partActivated(part);
	}
}
