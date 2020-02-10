package com.tibco.cep.decision.tree.ui.tool;

import java.util.List;

import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.interactive.command.editing.TSEMoveGroupCommand;
import com.tomsawyer.interactive.swing.editing.tool.TSEMoveSelectedTool;

public class DecisionTreeMoveSelectedTool extends TSEMoveSelectedTool {

	/* (non-Javadoc)
	 * @see com.tomsawyer.interactive.swing.editing.tool.TSEMoveSelectedTool#init()
	 */
	@Override
	protected void init() {
		super.init();
		this.getMoveControl().draggedNodeLabels().clear();
		this.getMoveControl().draggedEdgeLabels().clear();
		this.getMoveControl().draggedConnectorLabels().clear();
	}

	/* (non-Javadoc)
	 * @see com.tomsawyer.interactive.swing.editing.tool.TSEMoveSelectedTool#newMoveCommand(java.util.List, java.util.List, java.util.List, java.util.List, java.util.List, java.util.List, java.util.List, com.tomsawyer.drawing.geometry.shared.TSConstPoint, com.tomsawyer.drawing.geometry.shared.TSConstPoint)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected TSEMoveGroupCommand newMoveCommand(List graphs,
			List nodeList,
			List connectorList,
			List pathNodeList,
			List edgeLabelList,
			List nodeLabelList,
			List connectorLabelList,
			TSConstPoint startPoint,
			TSConstPoint endPoint) {
			return super.newMoveCommand(graphs,
				nodeList,
				connectorList,
				pathNodeList,
				null,
				null,
				null,
				startPoint,
				endPoint);
	}

}
