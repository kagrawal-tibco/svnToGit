package com.tibco.cep.studio.ui.diagrams.tools;

import static com.tibco.cep.diagramming.utils.DiagramUtils.refreshDiagram;
import static com.tibco.cep.studio.ui.diagrams.EntityNodeData.calculateConceptNodeSizes;
import static com.tibco.cep.studio.ui.overview.OverviewUtils.refreshOverview;

import java.util.List;

import org.eclipse.core.resources.IFile;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tibco.cep.diagramming.tool.CreateNodeTool;
import com.tibco.cep.studio.ui.diagrams.EntityDiagramManager;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.cep.studio.ui.wizards.IDiagramEntitySelection;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.swing.viewing.tool.TSViewingToolHelper;

/**
 * 
 * @author sasahoo
 *
 */
public class EntityCreateNodeTool extends CreateNodeTool implements IDiagramEntitySelection{

	public EntityCreateNodeTool(DiagramManager diagramManager) {
		super(diagramManager);
		// TODO Auto-generated constructor stub
	}

	protected IFile file;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.CreateNodeTool#resetPaletteSelection()
	 */
	@Override
	public void resetPaletteSelection(){
		StudioUIUtils.resetPaletteSelection();
	}
	
	/**
	 * @param diagramManager
	 * @param nodes
	 * @param drawingCanvas
	 */
	protected void refresh(EntityDiagramManager<? extends Entity> diagramManager, 
			               List<TSENode> nodes, 
			               DrawingCanvas drawingCanvas){
		calculateConceptNodeSizes(nodes, drawingCanvas);
		refreshDiagram(diagramManager);
		refreshOverview(diagramManager.getEditor().getEditorSite(), true, true);
		cancelAction();
		getSwingCanvas().getToolManager().setActiveTool(TSViewingToolHelper.getSelectTool(getSwingCanvas().getToolManager()));
		resetPaletteSelection();
	}
	

	@Override
	public IFile getEntityFile() {
		return file;
	}

	@Override
	public void setEntityFile(IFile file) {
	      this.file = file; 
	}
}
