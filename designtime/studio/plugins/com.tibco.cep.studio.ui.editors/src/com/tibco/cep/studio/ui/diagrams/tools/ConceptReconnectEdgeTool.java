package com.tibco.cep.studio.ui.diagrams.tools;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.diagramming.tool.ReconnectEdgeTool;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.diagrams.ConceptDiagramManager;
import com.tibco.cep.studio.ui.diagrams.rule.ConceptDiagramRuleSet;
import com.tibco.cep.studio.ui.diagrams.rule.ContainmentRule;
import com.tibco.cep.studio.ui.diagrams.rule.InheritanceRule;
import com.tibco.cep.studio.ui.editors.concepts.ConceptDiagramEditor;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.EntityImages;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author smarathe
 *
 */
public class ConceptReconnectEdgeTool extends ReconnectEdgeTool {

	private ConceptDiagramRuleSet ruleset;
	private ConceptDiagramManager conceptDiagramManager;
	/**
	 * @param conceptDiagramManager
	 */
	public ConceptReconnectEdgeTool(ConceptDiagramManager ConceptDiagramManager) {
		this.ruleset = new ConceptDiagramRuleSet();
		this.conceptDiagramManager = ConceptDiagramManager;
		this.addAllRules();
	}
	
	protected void addAllRules() {
		this.ruleset.addRule(new InheritanceRule(this.ruleset, this));
		this.ruleset.addRule(new ContainmentRule(this.ruleset, this));
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.CreateEdgeTool#isActionAllowed()
	 */
	public boolean isActionAllowed() {
		if(!((ConceptDiagramEditor)conceptDiagramManager.getEditor()).isEnabled()){
			return false;
		}
		TSENode srcTSNode = this.getSourceNode();
		TSENode tgtTSNode = this.getTargetNode();
		//case reconnecting edge when end state becoming new source 
		TSENode newNode =  getNewNode();
		if (isReconnectingSource() && newNode != null) {
			ConceptDiagramRuleSet rulesetForDrag = new ConceptDiagramRuleSet();
		
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
			Image image = EntityImages.createIcon("icons/Invalid10x10.png").getImage();
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
		if (!EditorUtils.checkPermissions((AbstractSaveableEntityEditorPart) conceptDiagramManager.getEditor())) {
			((AbstractSaveableEntityEditorPart) conceptDiagramManager.getEditor()).doRevert();
			return;
		}
		TSENode temp;
		TSENode oldNode =  getOldNode();
		TSENode newNode =  getNewNode();
		if(oldNode!=null && oldNode.getUserObject()!=null){
			State oldState = (State)oldNode.getUserObject();
			State newState = (State)newNode.getUserObject();
			StateTransition stateTransition = (StateTransition)getEdge().getUserObject();
			EditingDomain editingDomain = null;// = ((ConceptDiagramEditor)conceptDiagramManager.getEditor()).getEditingDomain();
			if(isReconnectingSource()){
				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) conceptDiagramManager.getEditor(), 
						(SetCommand)SetCommand.create(editingDomain,stateTransition , StatesPackage.eINSTANCE.getStateTransition_FromState(),newState));
				
				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) conceptDiagramManager.getEditor(), 
						(RemoveCommand)RemoveCommand.create(editingDomain, oldState, StatesPackage.eINSTANCE.getStateVertex_OutgoingTransitions(),stateTransition));
				
				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) conceptDiagramManager.getEditor(), 
						(AddCommand)AddCommand.create(editingDomain, newState, StatesPackage.eINSTANCE.getStateVertex_IncomingTransitions(),stateTransition));
				
			}else{
				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) conceptDiagramManager.getEditor(), 
						(SetCommand)SetCommand.create(editingDomain,stateTransition , StatesPackage.eINSTANCE.getStateTransition_ToState(),newState));
				
				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) conceptDiagramManager.getEditor(), 
						(RemoveCommand)RemoveCommand.create(editingDomain, oldState, StatesPackage.eINSTANCE.getStateVertex_IncomingTransitions(),stateTransition));
				
				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) conceptDiagramManager.getEditor(), 
						(AddCommand)AddCommand.create(editingDomain, newState, StatesPackage.eINSTANCE.getStateVertex_OutgoingTransitions(),stateTransition));
			}
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		conceptDiagramManager = null;
	}
}
