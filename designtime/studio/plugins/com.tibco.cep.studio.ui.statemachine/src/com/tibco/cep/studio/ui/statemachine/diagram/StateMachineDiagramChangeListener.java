package com.tibco.cep.studio.ui.statemachine.diagram;

import static com.tibco.cep.studio.ui.statemachine.diagram.StateMachineGraphAddHandler.handleEdgeAdd;
import static com.tibco.cep.studio.ui.statemachine.diagram.StateMachineGraphAddHandler.handleNodeAdd;
import static com.tibco.cep.studio.ui.statemachine.diagram.StateMachineGraphRemoveHandler.handleEdgeDelete;
import static com.tibco.cep.studio.ui.statemachine.diagram.StateMachineGraphRemoveHandler.handleNodeDelete;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.diagramming.drawing.DiagramChangeListener;
import com.tibco.cep.studio.ui.statemachine.diagram.tool.StateMachineReconnectEdgeTool;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.tool.TSTool;



/**
 * @author ggrigore
 * 
 * This class is a selection listener registered by the DrawingManager.
 */
@SuppressWarnings("serial")
public class StateMachineDiagramChangeListener extends DiagramChangeListener<StateMachineDiagramManager> {
	
    private Map<String, StateEntity> stateGraphPathmap = new HashMap<String, StateEntity>();

	/**
	 * @param manager
	 */
	public StateMachineDiagramChangeListener(StateMachineDiagramManager manager) {
		super(manager);
	}
	
	@Override
	protected void onNodeAdded(TSENode tsNode) {
		super.LAYOUT_CHANGE_FLAG = false;
		handleNodeAdd(tsNode, manager, this);
		super.LAYOUT_CHANGE_FLAG = true;
		resetToSelectToolAfterChange();
    }
    
    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#onEdgeAdded(com.tomsawyer.graphicaldrawing.TSEEdge)
     */
    protected void onEdgeAdded(TSEEdge tsEdge) {
    	super.onEdgeAdded(tsEdge);
    	handleEdgeAdd(tsEdge, manager, this);    
    	resetToSelectToolAfterChange();
    }
	
    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#onNodeDeleted(com.tomsawyer.graphicaldrawing.TSENode)
     */
    protected void onNodeDeleted(TSENode tsNode) {
    	super.LAYOUT_CHANGE_FLAG = false;
    	handleNodeDelete(tsNode, manager, this);
    	super.LAYOUT_CHANGE_FLAG = true;
    }
    
    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#onEdgeDeleted(com.tomsawyer.graphicaldrawing.TSEEdge)
     */
    protected void onEdgeDeleted(TSEEdge tsEdge) {
    	super.onEdgeDeleted(tsEdge);
    	handleEdgeDelete(tsEdge, manager, this);
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#onEdgeReconnected(com.tomsawyer.graphicaldrawing.TSEEdge)
     */
    protected void onEdgeReconnected(TSEEdge tsEdge)
    {
    	super.onEdgeReconnected(tsEdge);
    	TSTool tool = manager.getDrawingCanvas().getToolManager().getActiveTool();
    	if (tool instanceof StateMachineReconnectEdgeTool) {
    		StateMachineReconnectEdgeTool stateMachineReconnectEdgeTool = (StateMachineReconnectEdgeTool)tool;
    		stateMachineReconnectEdgeTool.fireModelChanged();
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
	
	public Map<String, StateEntity> getStateGraphPathmap() {
		return stateGraphPathmap;
	}
}