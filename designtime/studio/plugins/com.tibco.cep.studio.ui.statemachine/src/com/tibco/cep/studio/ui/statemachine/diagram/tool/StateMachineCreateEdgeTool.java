package com.tibco.cep.studio.ui.statemachine.diagram.tool;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.List;

import com.tibco.cep.diagramming.tool.CreateEdgeTool;
import com.tibco.cep.studio.ui.statemachine.StateMachinePlugin;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramManager;
import com.tibco.cep.studio.ui.statemachine.diagram.rule.AnyStateToConcurrentRegionRuleImpl;
import com.tibco.cep.studio.ui.statemachine.diagram.rule.AnyStateToStartRuleImpl;
import com.tibco.cep.studio.ui.statemachine.diagram.rule.ConcurrentRegionToAnyStateRuleImpl;
import com.tibco.cep.studio.ui.statemachine.diagram.rule.EndToEndStateRuleImpl;
import com.tibco.cep.studio.ui.statemachine.diagram.rule.EndToOthersRuleImpl;
import com.tibco.cep.studio.ui.statemachine.diagram.rule.ExitCompositeStateGraphRule;
import com.tibco.cep.studio.ui.statemachine.diagram.rule.StartToOthersRuleImpl;
import com.tibco.cep.studio.ui.statemachine.diagram.rule.StateMachineDiagramRuleset;
import com.tibco.cep.studio.ui.statemachine.diagram.rule.StateToParentStateRuleImpl;
import com.tibco.cep.studio.ui.statemachine.diagram.rule.StateToStateRuleImpl;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineImages;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEAddEdgeCommand;
import com.tomsawyer.interactive.swing.viewing.tool.TSViewingToolHelper;

public class StateMachineCreateEdgeTool extends CreateEdgeTool {

	@SuppressWarnings("rawtypes")
	@Override
	protected TSEAddEdgeCommand newAddEdgeCommand(TSENode arg0, TSENode arg1,
			List arg2) {
		// TODO Auto-generated method stub
		return super.newAddEdgeCommand(arg0, arg1, arg2);
	}

	private StateMachineDiagramRuleset ruleset;
	private StateMachineDiagramRuleset rulesetForDrag;
	private boolean isEditorEnabled = true;
	private StateMachineDiagramManager stateMachineDiagramManager;
	
	public StateMachineCreateEdgeTool(StateMachineDiagramManager stateMachineDiagramManager, boolean isEditorEnabled){
		super(stateMachineDiagramManager);
		this.isEditorEnabled = isEditorEnabled;
		this.ruleset = new StateMachineDiagramRuleset();
		this.stateMachineDiagramManager = stateMachineDiagramManager;
		
		this.addAllRules();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		stateMachineDiagramManager = null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.CreateEdgeTool#onMouseReleased(java.awt.event.MouseEvent)
	 */
	public void onMouseReleased(MouseEvent event) {
		if(isEditorEnabled){
			super.onMouseReleased(event);
		} else {
			if (this.stateMachineDiagramManager.isResetToolOnChange()) {
				getSwingCanvas().getToolManager().setActiveTool(TSViewingToolHelper.getSelectTool(getSwingCanvas().getToolManager()));
				resetPaletteSelection();
			}
		}
	}
	
	protected void addAllRules() {
		this.ruleset.addRule(new StartToOthersRuleImpl(this.ruleset,this));
		this.ruleset.addRule(new AnyStateToStartRuleImpl(this.ruleset,this));
		this.ruleset.addRule(new AnyStateToConcurrentRegionRuleImpl(this.ruleset));
		this.ruleset.addRule(new StateToStateRuleImpl(this.ruleset));
		this.ruleset.addRule(new ExitCompositeStateGraphRule(this.ruleset));
		this.ruleset.addRule(new StateToParentStateRuleImpl(this.ruleset));
		this.ruleset.addRule(new EndToEndStateRuleImpl(this.ruleset));
		this.ruleset.addRule(new EndToOthersRuleImpl(this.ruleset));
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
				Image image = StateMachineImages.createIcon("icons/Invalid10x10.png").getImage();
				Cursor c = tk.createCustomCursor(image, new Point(0,0),"invalid");
				this.setActionCursor(c);
				this.setCursor(c);
			} catch (Exception e) {
				StateMachinePlugin.log(e);
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
			rulesetForDrag = new StateMachineDiagramRuleset();
			rulesetForDrag.addRule(new EndToOthersRuleImpl(rulesetForDrag));
			rulesetForDrag.addRule(new ConcurrentRegionToAnyStateRuleImpl(rulesetForDrag));
			TSENode srcTSNode = this.getSourceNode();
			TSENode tgtTSNode = this.getTargetNode();
			boolean isAllowed = rulesetForDrag.isAllowed(srcTSNode, tgtTSNode);
			if (!isAllowed) {
				try {
					Toolkit tk = Toolkit.getDefaultToolkit();
					Image image = StateMachineImages.createIcon("icons/Invalid10x10.png").getImage();
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
