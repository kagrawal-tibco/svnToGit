package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;

import com.tibco.cep.bpmn.ui.PropertiesType;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEGraph;

/**
 * 
 * @author majha
 *
 */
public class ProcessCommandFactory extends AbstractGraphCommandFactory {

	public ProcessCommandFactory(ModelController controller,EClass type,ENamedElement extType) {
		super(controller,type,extType);
	}
	

	@Override
	public IGraphCommand<TSEGraph> getUpdateCommand(TSGraphObject graph, PropertiesType type, Map<String, Object> updateList) {
		IGraphCommand<TSEGraph> cmd ;
		if(! (graph instanceof TSEGraph))
			return null;
		switch (type){
		case GENERAL_PROPERTIES:
			cmd = new ProcessGeneralPropertiesUpdateCommand(IGraphCommand.COMMAND_UPDATE, getModelController(), (TSEGraph)graph, updateList );
			break;
		default:
			cmd = new ProcessUpdateCommand(IGraphCommand.COMMAND_UPDATE, getModelController(), (TSEGraph)graph, updateList );
		}
		return cmd;
	}
	
}
