package com.tibco.cep.studio.ui.statemachine.diagram.tool;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.diagramming.tool.ReconnectEdgeTool;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramManager;
import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditor;
import com.tibco.cep.studio.ui.statemachine.diagram.rule.AnyStateToConcurrentRegionRuleImpl;
import com.tibco.cep.studio.ui.statemachine.diagram.rule.AnyStateToStartRuleImpl;
import com.tibco.cep.studio.ui.statemachine.diagram.rule.EndToEndStateRuleImpl;
import com.tibco.cep.studio.ui.statemachine.diagram.rule.EndToOthersRuleImpl;
import com.tibco.cep.studio.ui.statemachine.diagram.rule.ExitCompositeStateGraphRule;
import com.tibco.cep.studio.ui.statemachine.diagram.rule.StartToOthersRuleImpl;
import com.tibco.cep.studio.ui.statemachine.diagram.rule.StateMachineDiagramRuleset;
import com.tibco.cep.studio.ui.statemachine.diagram.rule.StateToParentStateRuleImpl;
import com.tibco.cep.studio.ui.statemachine.diagram.rule.StateToStateRuleImpl;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineImages;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author sasahoo
 *
 */
public class StateMachineReconnectEdgeTool extends ReconnectEdgeTool{

	private StateMachineDiagramRuleset ruleset;
	private StateMachineDiagramManager stateMachineDiagramManager;
	/**
	 * @param stateMachineDiagramManager
	 */
	public StateMachineReconnectEdgeTool(StateMachineDiagramManager stateMachineDiagramManager) {
		this.ruleset = new StateMachineDiagramRuleset();
		this.stateMachineDiagramManager = stateMachineDiagramManager;
		this.addAllRules();
	}
	
	protected void addAllRules() {
		this.ruleset.addRule(new StartToOthersRuleImpl(this.ruleset,this));
		this.ruleset.addRule(new AnyStateToStartRuleImpl(this.ruleset,this));
		this.ruleset.addRule(new AnyStateToConcurrentRegionRuleImpl(this.ruleset));
		this.ruleset.addRule(new StateToStateRuleImpl(this.ruleset));
		this.ruleset.addRule(new ExitCompositeStateGraphRule(this.ruleset));
		this.ruleset.addRule(new EndToEndStateRuleImpl(this.ruleset));
		this.ruleset.addRule(new StateToParentStateRuleImpl(this.ruleset));
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.CreateEdgeTool#isActionAllowed()
	 */
	public boolean isActionAllowed() {
		if(!((StateMachineEditor)stateMachineDiagramManager.getEditor()).isEnabled()){
			return false;
		}
		TSENode srcTSNode = this.getSourceNode();
		TSENode tgtTSNode = this.getTargetNode();
		//case reconnecting edge when end state becoming new source 
		TSENode newNode =  getNewNode();
		if (isReconnectingSource() && newNode != null) {
			StateMachineDiagramRuleset rulesetForDrag = new StateMachineDiagramRuleset();
			rulesetForDrag.addRule(new EndToOthersRuleImpl(rulesetForDrag));
			boolean isAllowed= rulesetForDrag.isAllowed(newNode, tgtTSNode);
			if (!isAllowed) {
				setCursorUIChange();
				return isAllowed;
			}
		}
		
 		//all reconnecting edge from addRules() check 
		boolean isAllowed = this.ruleset.isAllowed(srcTSNode, tgtTSNode);
		if (!isAllowed) {
			setCursorUIChange();
		}
		return isAllowed;
	}
	
	/**
	 * set invalid mouse cursor change
	 */
	private void setCursorUIChange() {
		try {
			Toolkit tk = Toolkit.getDefaultToolkit();
			Image image = StateMachineImages.createIcon("icons/Invalid10x10.png").getImage();
			Cursor c = tk.createCustomCursor(image, new Point(0,0),"invalid");
			this.setActionCursor(c);
			this.setCursor(c);
			this.cancelAction();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.ReconnectEdgeTool#fireModelChanged()
	 */
	public void fireModelChanged(){
		if (!EditorUtils.checkPermissions((AbstractSaveableEntityEditorPart) stateMachineDiagramManager.getEditor())) {
			((AbstractSaveableEntityEditorPart) stateMachineDiagramManager.getEditor()).doRevert();
			return;
		}
		TSENode oldNode =  getOldNode();
		TSENode newNode =  getNewNode();
		if(oldNode!=null && oldNode.getUserObject()!=null){
			State oldState = (State)oldNode.getUserObject();
			State newState = (State)newNode.getUserObject();
			StateTransition stateTransition = (StateTransition)getEdge().getUserObject();
			EditingDomain editingDomain = ((StateMachineEditor)stateMachineDiagramManager.getEditor()).getEditingDomain();
			if(isReconnectingSource()){
				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) stateMachineDiagramManager.getEditor(), 
						(SetCommand)SetCommand.create(editingDomain,stateTransition , StatesPackage.eINSTANCE.getStateTransition_FromState(),newState));
				
				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) stateMachineDiagramManager.getEditor(), 
						(RemoveCommand)RemoveCommand.create(editingDomain, oldState, StatesPackage.eINSTANCE.getStateVertex_OutgoingTransitions(),stateTransition));
				
				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) stateMachineDiagramManager.getEditor(), 
						(AddCommand)AddCommand.create(editingDomain, newState, StatesPackage.eINSTANCE.getStateVertex_IncomingTransitions(),stateTransition));
				
			}else{
				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) stateMachineDiagramManager.getEditor(), 
						(SetCommand)SetCommand.create(editingDomain,stateTransition , StatesPackage.eINSTANCE.getStateTransition_ToState(),newState));
				
				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) stateMachineDiagramManager.getEditor(), 
						(RemoveCommand)RemoveCommand.create(editingDomain, oldState, StatesPackage.eINSTANCE.getStateVertex_IncomingTransitions(),stateTransition));
				
				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) stateMachineDiagramManager.getEditor(), 
						(Command)AddCommand.create(editingDomain, newState, StatesPackage.eINSTANCE.getStateVertex_OutgoingTransitions(),stateTransition));
			}
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		stateMachineDiagramManager = null;
	}
	
	
}