package com.tibco.cep.decision.tree.ui.editor;

import com.tibco.cep.decision.tree.ui.actions.DecisionTreeEditHandler;
import com.tibco.cep.decision.tree.ui.tool.DecisionTreeReconnectEdgeTool;
import com.tibco.cep.diagramming.drawing.DiagramChangeListener;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.tool.TSTool;


/*
@author ssailapp
@date Sep 14, 2011
 */

@SuppressWarnings("serial")
public class DecisionTreeDiagramChangeListener extends DiagramChangeListener<DecisionTreeDiagramManager> {
	
    /**
	 * @param manager
	 */
	public DecisionTreeDiagramChangeListener(DecisionTreeDiagramManager manager) {
		super(manager);
	}
	
	@Override
	protected void onNodeAdded(TSENode tsNode) {
		super.LAYOUT_CHANGE_FLAG = false;
		DecisionTreeEditHandler.handleNodeAdd(tsNode, manager, this);
		super.LAYOUT_CHANGE_FLAG = true;
    }
    
    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#onEdgeAdded(com.tomsawyer.graphicaldrawing.TSEEdge)
     */
    protected void onEdgeAdded(TSEEdge tsEdge) {
    	super.onEdgeAdded(tsEdge);
    	DecisionTreeEditHandler.handleEdgeAdd(tsEdge, manager, this);    
    }
	
    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#onNodeDeleted(com.tomsawyer.graphicaldrawing.TSENode)
     */
    protected void onNodeDeleted(TSENode tsNode) {
    	super.LAYOUT_CHANGE_FLAG = false;
    	DecisionTreeEditHandler.handleNodeDelete(tsNode, manager, this);
    	super.LAYOUT_CHANGE_FLAG = true;
    }
    
    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#onEdgeDeleted(com.tomsawyer.graphicaldrawing.TSEEdge)
     */
    protected void onEdgeDeleted(TSEEdge tsEdge) {
    	super.onEdgeDeleted(tsEdge);
    	DecisionTreeEditHandler.handleEdgeDelete(tsEdge, manager, this);
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#onEdgeReconnected(com.tomsawyer.graphicaldrawing.TSEEdge)
     */
    protected void onEdgeReconnected(TSEEdge tsEdge) {
    	super.onEdgeReconnected(tsEdge);
    	TSTool tool = manager.getDrawingCanvas().getToolManager().getActiveTool();
    	if (tool instanceof DecisionTreeReconnectEdgeTool) {
    		DecisionTreeReconnectEdgeTool decisionTreeReconnectEdgeTool = (DecisionTreeReconnectEdgeTool)tool;
    		decisionTreeReconnectEdgeTool.fireModelChanged();
    	}
    }

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#resetToSelectToolAfterChange()
	 */
	@Override
	public void resetToSelectToolAfterChange() {
		if (manager.isResetToolOnChange()) {
			super.resetToSelectToolAfterChange();
			StudioUIUtils.resetPaletteSelection();
		}
	}
}
