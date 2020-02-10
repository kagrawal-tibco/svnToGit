package com.tibco.cep.bpmn.ui.editor;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeAdapterFactory;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeEvent;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeListener;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.cep.diagramming.drawing.BaseDiagramManager;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;

public class BaseWebBpmnDiagramManager extends BaseDiagramManager implements ModelChangeListener, IBpmnDiagramManager {

	protected ModelController modelController;
	protected ModelChangeAdapterFactory modelChangeAdapterFactory;
	
	public BaseWebBpmnDiagramManager() {
		super();
	}
	
	public void initializeWeb() {
		setWeb(true);
		if (modelChangeAdapterFactory == null) {
			modelChangeAdapterFactory = new ModelChangeAdapterFactory(this);
		}
		initializeModel();
	}
	
	/**
	 * initialize the model graph factory
	 */
	public void initializeModel() {
		TSEImage.setLoaderClass(this.getClass());
		if (modelController == null) {
			modelController = new ModelController(getModelChangeAdapterFactory());
		}
	}
	
	public LayoutManager getLayoutManager() {
		if (this.layoutManager == null) {
			this.layoutManager = new BpmnLayoutManager(this);
		}
		return this.layoutManager;
	}
	
	public ModelChangeAdapterFactory getModelChangeAdapterFactory() {
		return modelChangeAdapterFactory;
	}

	@Override
	public void modelChanged(ModelChangeEvent mce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ModelController getModelController() {
		return modelController;
	}

	@Override
	public boolean isPrivateProcess() {
		return false;
	}

	@Override
	public void addAdditionalModel(Map<String, Object> updateMap,
			EObject flowNode, String attachedResource) {
		// TODO Auto-generated method stub
		
	}

}
