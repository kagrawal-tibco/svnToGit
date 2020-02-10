package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;

import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupEmfItemType;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.ui.PropertiesType;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

public abstract class AbstractNodeCommandFactory implements ICommandFactory {
	protected ModelController modelController;
	protected EClass modelType;
	protected ENamedElement extendTyped;
	protected IProject project;

	public AbstractNodeCommandFactory(IProject proj, ModelController controller,EClass type,ENamedElement extType) {
		this.project = proj;
		this.modelController = controller;
		this.modelType = type;
		this.extendTyped = extType;
	}
	
	public ModelController getModelController() {
		return modelController;
	}
	
	public EClass getModelType() {
		return modelType;
	}
	
	public ENamedElement getExtendTyped() {
		return extendTyped;
	}

	@SuppressWarnings("unchecked")
	public IGraphCommand<TSENode> getCommand(int type, Object ... args) {
		switch(type) {
		case IGraphCommand.COMMAND_ADD:
			return getAddCommand((TSEGraph)args[0],(Double)args[1],(Double)args[2]);
		case IGraphCommand.COMMAND_UPDATE:
			return getUpdateCommand((TSGraphObject)args[0],(PropertiesType)args[1],(Map<String, Object>)args[2]);
		case IGraphCommand.COMMAND_DELETE:
			return getDeleteCommand((TSEGraph)args[0],(TSENode)args[1]);
		case IGraphCommand.COMMAND_INSERT:
			return getInsertCommand((TSEGraph)args[0],(TSENode)args[1]);
		}
		return null;
	}

	public IGraphCommand<TSENode> getInsertCommand(TSEGraph graph, TSENode node) {
		// TODO Auto-generated method stub
		return null;
	}

	public IGraphCommand<TSENode> getDeleteCommand(TSEGraph graph, TSENode node) {
		// TODO Auto-generated method stub
		return null;
	}

	public IGraphCommand<TSENode> getUpdateCommand(TSGraphObject node, PropertiesType type, Map<String, Object> updateList) {
		// TODO Auto-generated method stub
		return null;
	}

	public IGraphCommand<TSENode> getAddCommand(TSEGraph graph, double x,
			double y) {
		// TODO Auto-generated method stub
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
	
	@Override
	public boolean handlesModelType(EClass mtype, ENamedElement extType) {
		if (getModelType().equals(mtype)){
			if(extendTyped == null) {
				if(extType == null) {
					return true;
				}
			} else {
				if(getExtendTyped().equals(extType)){
					return true;
				}
			}
		}
		return false;
	}


	

}
