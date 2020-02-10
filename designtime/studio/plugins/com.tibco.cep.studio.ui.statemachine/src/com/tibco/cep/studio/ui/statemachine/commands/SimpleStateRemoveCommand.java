package com.tibco.cep.studio.ui.statemachine.commands;

import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.createTooltip;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.is_UNDO;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.setDefaultExtendedProperties;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateSimple;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramManager;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

public class SimpleStateRemoveCommand extends AbstractNodeRemoveCommand {
	private Object value;

	public SimpleStateRemoveCommand(StateMachineDiagramManager manager,
			EditingDomain domain, EList<?> list, Object value) {
		super(manager, domain, list, value);
		this.value=value;
		runForChange(manager);
	}
	
	@Override
	public void runForChange(StateMachineDiagramManager manager) {
		StateSimple state = null;
		state=(StateSimple)value;
		if(is_UNDO){
			
		//SimpleStateNodeCreator nodeCreator=new SimpleStateNodeCreator(manager.getLayoutManager());
		//TSENode tsNode=nodeCreator.addNode(manager.getGraphManager().selectedGraph());
		TSENode tsNode=manager.createSimpleState((TSEGraph)manager.getGraphManager().getMainDisplayGraph(),
				StateMachineDiagramManager.getStateNodeUI(),(State)value);
		//StateMachine stateMachine = manager.getStateMachine();
			/*TSEAddNodeCommand command = new TSEAddNodeCommand();
			TSCommandManager commandManager = getSwingCanvas().getCommandManager();
			commandManager.transmit(command);
			TSENode tsNode=command.getNode();*/
		//String projectName = stateMachine.getOwnerProjectName();
		
		/*if(tsNode.getUserObject() == null){
			state = StatesFactory.eINSTANCE.createStateSimple();
			state.setName(tsNode.getText());
			state.setGUID(getGUID(GUIDGenerator.getGUID(), manager.getGUIDGraphMap(), tsNode));//setting generated GUID
			addStateRule(stateMachine, state, projectName);
			tsNode.setUserObject(state);
			tsNode.setTooltipText(createTooltip(state));
		} else*/ {
			//state = (StateSimple)tsNode.getUserObject();
			
			System.out.println(state.getName());
			state = (StateSimple)EcoreUtil.copy(state);
			tsNode.setUserObject(state);
			tsNode.setTooltipText(createTooltip(state));
		}
		setDefaultExtendedProperties(state);
		
	}
	}
}
