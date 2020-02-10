package com.tibco.cep.studio.ui.diagrams.tools;

import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.tool.CreateEdgeTool;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

public class EntityCreateEdgeTool extends CreateEdgeTool {
	
	public EntityCreateEdgeTool(DiagramManager diagramManager) {
		super(diagramManager);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.CreateEdgeTool#isActionAllowed()
	 */
	public boolean isActionAllowed() {
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