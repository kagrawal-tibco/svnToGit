package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;

import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupEmfItemType;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.ui.PropertiesType;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author majha
 *
 */
public abstract class AbstractConnectorCommandFactory implements ICommandFactory {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 2642620343425881486L;
	protected ModelController modelController;
	protected ENamedElement extendTyped;

	public AbstractConnectorCommandFactory(ModelController controller,ENamedElement extType) {
		this.modelController = controller;
		this.extendTyped = extType;
	}
	
	public ModelController getModelController() {
		return modelController;
	}
	
	public ENamedElement getExtendTyped() {
		return extendTyped;
	}

	@SuppressWarnings("unchecked")
	public IGraphCommand<TSEConnector> getCommand(int type, Object ... args) {
		switch(type) {
		case IGraphCommand.COMMAND_UPDATE:
			return getUpdateCommand((TSEConnector)args[0],(PropertiesType)args[1],(Map<String, Object>)args[2]);
		case IGraphCommand.COMMAND_DELETE:
			return getDeleteCommand((TSENode)args[0],(TSEConnector)args[1]);
		case IGraphCommand.COMMAND_INSERT:
			return getInsertCommand((TSENode)args[0], (String)args[1], (String)args[2]);
		}
		return null;
	}
	
	protected EClass getNodeType(BpmnPaletteGroupItem item) {
		BpmnCommonPaletteGroupEmfItemType itemType = (BpmnCommonPaletteGroupEmfItemType) item
				.getItemType();
		ExpandedName extClassSpec = itemType.getEmfType();
		EClass mType = BpmnMetaModel.getInstance().getEClass(extClassSpec);
		return mType;
	}
	
	protected EClass getExtendedNodeType(BpmnPaletteGroupItem item) {
		BpmnCommonPaletteGroupEmfItemType itemType = (BpmnCommonPaletteGroupEmfItemType) item
				.getItemType();
		ExpandedName extClassSpec = itemType.getExtendedType();
		EClass eClass = BpmnMetaModel.getInstance().getEClass(extClassSpec);
		return eClass;
	}



	abstract public IGraphCommand<TSEConnector> getDeleteCommand(TSENode parentNode,TSEConnector connector);
	
	abstract public IGraphCommand<TSEConnector> getUpdateCommand(TSEConnector connector,
			PropertiesType type, Map<String, Object> updateList);
		

	abstract public IGraphCommand<TSEConnector> getInsertCommand(TSENode parent, String attachedRes, String toolId);
	

	
	@Override
	abstract public boolean handlesModelType(EClass mtype, ENamedElement extType);


	

}
