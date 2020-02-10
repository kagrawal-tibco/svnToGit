package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.PropertiesType;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.graph.model.command.AbstractEdgeCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.IGraphCommand;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.interactive.command.TSCommand;


/**
 * 
 * @author majha
 *
 */
public class SequenceConditionExpressionPropertySection extends ScriptPropertySection {

	private WidgetListener widgetListener;
	private String body;

	public SequenceConditionExpressionPropertySection() {
		super();
		this.widgetListener = new WidgetListener();
		body = "";
	}
	
	@Override
	public void aboutToBeHidden() {
		if (!composite.isDisposed()) {
			condDocument.removeDocumentListener(widgetListener);
		}
	}
	
	@Override
	public void aboutToBeShown() {
		if (!composite.isDisposed()) {
			condDocument.addDocumentListener(widgetListener);
		}
	}
	
	protected void updatePropertySection(Map<String, Object> updateList) {
		fireUpdate(updateList);
	}
	
	private void fireUpdate(Map<String, Object> updateList){
		if(updateList.size() == 0)
			return ;
		
		EClass nodeType = (EClass) fTSEEdge.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		ENamedElement extType = (EClass) fTSEEdge.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);

		BpmnDiagramManager bpmnGraphDiagramManager = fPropertySheetPage.getEditor().getBpmnGraphDiagramManager();
		AbstractEdgeCommandFactory cf = (AbstractEdgeCommandFactory) bpmnGraphDiagramManager.getModelGraphFactory().getCommandFactory(nodeType,extType);
		IGraphCommand<TSEEdge> cmd = cf.getCommand(IGraphCommand.COMMAND_UPDATE, fTSEEdge, PropertiesType.GENERAL_PROPERTIES, updateList);
			
		bpmnGraphDiagramManager.executeCommand((TSCommand)cmd);
	}
	
	@Override
	public void refresh() {
		this.refresh = true;
		if (fTSENode != null){}
		if (fTSEEdge != null){ 
			String attribute = "";
			EObject userObject = (EObject) fTSEEdge.getUserObject();
			EObjectWrapper<EClass, EObject> edgeWrapper = EObjectWrapper.wrap(userObject);
			EObject expression = (EObject)edgeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_CONDITION_EXPRESSION);
			if (expression != null) {
				EObjectWrapper<EClass, EObject> expressionWrapper = EObjectWrapper.wrap(expression);
				attribute = (String)expressionWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
			}
			body = attribute;
			condDocument.set(attribute);

		}
		if (fTSEGraph != null){}
		this.refresh = false;
	}
	

	private class WidgetListener implements IDocumentListener {

		
		@Override
		public void documentAboutToBeChanged(DocumentEvent event) {
			// TODO Auto-generated method stub
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.text.IDocumentListener#documentChanged(org.eclipse.jface.text.DocumentEvent)
		 */
		@Override
		public void documentChanged(DocumentEvent event) {
			if (isRefresh()) {
				return;
			}
			Map<String, Object> updateList = new HashMap<String, Object>();
			if (condDocument != null && event.getDocument() == condDocument) {
				String text = condDocument.get();
				if (!text.equals(body)) {
					updateList.put(BpmnMetaModelConstants.E_ATTR_BODY,
							text);
				}
			}
			updatePropertySection(updateList);
		}

	}

	
}