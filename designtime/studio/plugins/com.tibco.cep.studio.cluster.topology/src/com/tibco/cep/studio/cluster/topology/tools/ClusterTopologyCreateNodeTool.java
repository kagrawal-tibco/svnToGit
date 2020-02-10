package com.tibco.cep.studio.cluster.topology.tools;

import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.tool.CreateNodeTool;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEAddNodeCommand;
import com.tomsawyer.interactive.command.editing.TSEInsertNodeCommand;
/**
 * @author hitesh
 *
 */
public class ClusterTopologyCreateNodeTool extends CreateNodeTool{

	public ClusterTopologyCreateNodeTool(DiagramManager diagramManager) {
		super(diagramManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected TSEAddNodeCommand newAddNodeCommand(TSEGraph arg0, double arg1,
			double arg2) {
		// TODO Auto-generated method stub
		return super.newAddNodeCommand(arg0, arg1, arg2);
	}

	@Override
	protected TSEInsertNodeCommand newInsertNodeCommand(TSEGraph arg0,
			TSENode arg1) {
		// TODO Auto-generated method stub
		return super.newInsertNodeCommand(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.CreateNodeTool#istValidGraph(com.tomsawyer.graphicaldrawing.TSEGraph)
	 */
	protected boolean isValidGraph(TSENode node, TSEGraph graph){
			return true;
		}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.CreateNodeTool#resetPaletteSelection()
	 */
	@Override
	public void resetPaletteSelection(){
		StudioUIUtils.resetPaletteSelection();
	}
}
