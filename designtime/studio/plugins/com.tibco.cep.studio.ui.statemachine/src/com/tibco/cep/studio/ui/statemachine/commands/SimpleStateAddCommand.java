package com.tibco.cep.studio.ui.statemachine.commands;

import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.addStateRule;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.createTooltip;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.getGUID;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.is_REDO;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.is_UNDO;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.setDefaultExtendedProperties;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateSimple;
import com.tibco.cep.designtime.core.model.states.StatesFactory;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramManager;
import com.tibco.cep.studio.ui.statemachine.diagram.ui.SimpleStateNodeCreator;
import com.tomsawyer.graphicaldrawing.TSENode;

public class SimpleStateAddCommand extends AbstractNodeAddCommand{

	@SuppressWarnings("unused")
	private Object value;

	public SimpleStateAddCommand(StateMachineDiagramManager manager,
			EditingDomain domain, EList<?> list, Object value) {
		super(manager, domain, list, value);
		this.value=value;
		runForChange(manager);
	}

	@Override
	public void runForChange (StateMachineDiagramManager manager) {
		// TODO Auto-generated method stub
		if(is_UNDO){
				manager.delete();
		}else if(is_REDO){
			SimpleStateNodeCreator nodeCreator=new SimpleStateNodeCreator(manager.getLayoutManager());
			TSENode tsNode=nodeCreator.addNode(manager.getGraphManager().selectedGraph());
			StateMachine stateMachine = manager.getStateMachine();
			String projectName = stateMachine.getOwnerProjectName();
			StateSimple state = null;
			if(tsNode.getUserObject() == null){
				state = StatesFactory.eINSTANCE.createStateSimple();
				state.setName(tsNode.getText());
				state.setGUID(getGUID(GUIDGenerator.getGUID(), manager.getGUIDGraphMap(), tsNode));//setting generated GUID
				addStateRule(stateMachine, state, projectName);
				tsNode.setUserObject(state);
				tsNode.setTooltipText(createTooltip(state));
			} else {
				state = (StateSimple)tsNode.getUserObject();
				state = (StateSimple)EcoreUtil.copy(state);
				tsNode.setUserObject(state);
				tsNode.setTooltipText(createTooltip(state));
			}
			setDefaultExtendedProperties(state);
			
		}
		
	}

}
