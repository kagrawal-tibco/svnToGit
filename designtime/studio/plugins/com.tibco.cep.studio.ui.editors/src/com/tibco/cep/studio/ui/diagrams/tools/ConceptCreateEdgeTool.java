package com.tibco.cep.studio.ui.diagrams.tools;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.List;

import com.tibco.cep.diagramming.tool.CreateEdgeTool;
import com.tibco.cep.studio.ui.diagrams.ConceptDiagramManager;
import com.tibco.cep.studio.ui.diagrams.rule.ConceptDiagramRuleSet;
import com.tibco.cep.studio.ui.diagrams.rule.ContainmentRule;
import com.tibco.cep.studio.ui.diagrams.rule.InheritanceRule;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.EntityImages;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEAddEdgeCommand;
import com.tomsawyer.interactive.swing.viewing.tool.TSViewingToolHelper;

/**
 * 
 * @author smarathe
 *
 */
public class ConceptCreateEdgeTool extends CreateEdgeTool{

	private ConceptDiagramRuleSet ruleset;
	private ConceptDiagramRuleSet rulesetForDrag;
	private boolean isEditorEnabled = true;
	private ConceptDiagramManager conceptDiagramManager;

	public ConceptCreateEdgeTool(ConceptDiagramManager conceptDiagramManager, boolean isEditorEnabled){
		super(conceptDiagramManager);
		this.isEditorEnabled = isEditorEnabled;
		this.ruleset = new ConceptDiagramRuleSet();
		this.conceptDiagramManager = conceptDiagramManager;
		this.rulesetForDrag = new ConceptDiagramRuleSet();
		this.addAllRules();
	}

	@Override
	public void dispose() {
		super.dispose();
		conceptDiagramManager = null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.CreateEdgeTool#onMouseReleased(java.awt.event.MouseEvent)
	 */
	public void onMouseReleased(MouseEvent event) {
		if(isEditorEnabled){
			super.onMouseReleased(event);
		} else {
			if (this.conceptDiagramManager.isResetToolOnChange()) {
				getSwingCanvas().getToolManager().setActiveTool(TSViewingToolHelper.getSelectTool(getSwingCanvas().getToolManager()));
				resetPaletteSelection();
			}
		}
	}

	@Override
	protected TSEAddEdgeCommand newAddEdgeCommand(TSENode arg0, TSENode arg1,
			List arg2) {
		// TODO Auto-generated method stub
		return super.newAddEdgeCommand(arg0, arg1, arg2);
	}

	public void onMouseMoved(MouseEvent event) {
		if(isEditorEnabled){
			super.onMouseReleased(event);
		} else {
			if (this.conceptDiagramManager.isResetToolOnChange()) {
				getSwingCanvas().getToolManager().setActiveTool(TSViewingToolHelper.getSelectTool(getSwingCanvas().getToolManager()));
				resetPaletteSelection();
			}
		}
	}

	protected void addAllRules() {
		this.ruleset.addRule(new InheritanceRule(this.ruleset, this));
		this.ruleset.addRule(new ContainmentRule(this.ruleset, this));
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.CreateEdgeTool#isActionAllowed()
	 */
	public boolean isActionAllowed() {
		TSENode srcTSNode = this.getSourceNode();
		TSENode tgtTSNode = this.getTargetNode();
		boolean isAllowed = this.ruleset.isAllowed(srcTSNode, tgtTSNode);
		if (!isAllowed) {
			try {
				Toolkit tk = Toolkit.getDefaultToolkit();
				Image image =  EntityImages.createIcon("icons/Invalid10x10.png").getImage();
				Cursor c = tk.createCustomCursor(image, new Point(0,0),"invalid");
				this.setActionCursor(c);
				this.setCursor(c);
			} catch (Exception e) {
				EditorsUIPlugin.log(e);
			}
		}
		return isAllowed;
	}

	/**
	 * This method responds to the mouse being dragged. If this tool
	 * is in the process of reconnecting the edge, a paint request to
	 * the Swing canvas is issued.
	 */
	public void onMouseDragged(MouseEvent event)
	{
		super.onMouseDragged(event);

		if(getSourceNode()!=null){
			rulesetForDrag = new ConceptDiagramRuleSet();

			TSENode srcTSNode = this.getSourceNode();
			TSENode tgtTSNode = this.getTargetNode();
			boolean isAllowed = rulesetForDrag.isAllowed(srcTSNode, tgtTSNode);
			if (!isAllowed) {
				try {
					Toolkit tk = Toolkit.getDefaultToolkit();
					Image image = EntityImages.createIcon("icons/Invalid10x10.png").getImage();
					Cursor c = tk.createCustomCursor(image, new Point(0,0),"invalid");
					this.setActionCursor(c);
					this.setCursor(c);
				} catch (Exception e) {
					e.printStackTrace();
				}
				cancelAction();
			}
		}

	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.CreateNodeTool#resetPaletteSelection()
	 */
	@Override
	public void resetPaletteSelection(){
		StudioUIUtils.resetPaletteSelection();
	}
}
