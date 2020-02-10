package com.tibco.cep.decision.tree.ui.tool;

import java.awt.event.MouseEvent;
import java.util.List;

import com.tibco.cep.decision.tree.ui.editor.DecisionTreeDiagramManager;
import com.tibco.cep.diagramming.tool.CreateEdgeTool;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEAddEdgeCommand;
import com.tomsawyer.interactive.swing.viewing.tool.TSViewingToolHelper;

public class DecisionTreeCreateEdgeTool extends CreateEdgeTool {

	@SuppressWarnings("rawtypes")
	@Override
	protected TSEAddEdgeCommand newAddEdgeCommand(TSENode arg0, TSENode arg1,
			List arg2) {
		return super.newAddEdgeCommand(arg0, arg1, arg2);
	}

	/*TODO - 
	private DecisionTreeDiagramRuleset ruleset;
	private DecisionTreeDiagramRuleset rulesetForDrag;
	*/
	private boolean isEditorEnabled = true;
	private DecisionTreeDiagramManager decisionTreeDiagramManager;

	
	public DecisionTreeCreateEdgeTool(DecisionTreeDiagramManager decisionTreeDiagramManager, boolean isEditorEnabled){
		super(decisionTreeDiagramManager);
		this.isEditorEnabled = isEditorEnabled;
		/* TODO - 
		this.ruleset = new DecisionTreeDiagramRuleset();
		this.decisionTreeDiagramManager = decisionTreeDiagramManager;
		
		this.addAllRules();
		*/
	}
	
	@Override
	public void dispose() {
		super.dispose();
		decisionTreeDiagramManager = null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.CreateEdgeTool#onMouseReleased(java.awt.event.MouseEvent)
	 */
	public void onMouseReleased(MouseEvent event) {
		if(isEditorEnabled){
			super.onMouseReleased(event);
		} else {
			if (decisionTreeDiagramManager != null && decisionTreeDiagramManager.isResetToolOnChange()) {
				getSwingCanvas().getToolManager().setActiveTool(TSViewingToolHelper.getSelectTool(getSwingCanvas().getToolManager()));
				resetPaletteSelection();
			}
		}
	}
	
	protected void addAllRules() {
		/*TODO - 
		this.ruleset.addRule(new StartToOthersRuleImpl(this.ruleset,this));
		this.ruleset.addRule(new AnyStateToStartRuleImpl(this.ruleset,this));
		this.ruleset.addRule(new AnyStateToConcurrentRegionRuleImpl(this.ruleset));
		this.ruleset.addRule(new StateToStateRuleImpl(this.ruleset));
		this.ruleset.addRule(new ExitCompositeStateGraphRule(this.ruleset));
		this.ruleset.addRule(new StateToParentStateRuleImpl(this.ruleset));
		this.ruleset.addRule(new EndToEndStateRuleImpl(this.ruleset));
		this.ruleset.addRule(new EndToOthersRuleImpl(this.ruleset));
		*/
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.CreateEdgeTool#isActionAllowed()
	 */
	public boolean isActionAllowed() {
		/*TODO - 
		TSENode srcTSNode = this.getSourceNode();
		TSENode tgtTSNode = this.getTargetNode();
		boolean isAllowed = this.ruleset.isAllowed(srcTSNode, tgtTSNode);
		if (!isAllowed) {
			try {
				Toolkit tk = Toolkit.getDefaultToolkit();
				Image image = DecisionTreeImages.createIcon("icons/Invalid10x10.png").getImage();
				Cursor c = tk.createCustomCursor(image, new Point(0,0),"invalid");
				this.setActionCursor(c);
				this.setCursor(c);
			} catch (Exception e) {
				DecisionTreePlugin.log(e);
			}
		}
		return isAllowed;
		*/
		return true;
	}
	
	/**
	 * This method responds to the mouse being dragged. If this tool
	 * is in the process of reconnecting the edge, a paint request to
	 * the Swing canvas is issued.
	 */
	public void onMouseDragged(MouseEvent event)
	{
		super.onMouseDragged(event);
		
		/*TODO - 
		if(getSourceNode()!=null){
			rulesetForDrag = new DecisionTreeDiagramRuleset();
			rulesetForDrag.addRule(new EndToOthersRuleImpl(rulesetForDrag));
			rulesetForDrag.addRule(new ConcurrentRegionToAnyStateRuleImpl(rulesetForDrag));
			TSENode srcTSNode = this.getSourceNode();
			TSENode tgtTSNode = this.getTargetNode();
			boolean isAllowed = rulesetForDrag.isAllowed(srcTSNode, tgtTSNode);
			if (!isAllowed) {
				try {
					Toolkit tk = Toolkit.getDefaultToolkit();
					Image image = DecisionTreeImages.createIcon("icons/Invalid10x10.png").getImage();
					Cursor c = tk.createCustomCursor(image, new Point(0,0),"invalid");
					this.setActionCursor(c);
					this.setCursor(c);
				} catch (Exception e) {
					e.printStackTrace();
				}
				cancelAction();
			}
		}
		*/
		
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.CreateNodeTool#resetPaletteSelection()
	 */
	@Override
	public void resetPaletteSelection(){
		StudioUIUtils.resetPaletteSelection();
	}
	
}
