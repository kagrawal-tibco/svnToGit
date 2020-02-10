package com.tibco.cep.bpmn.ui.graph.properties;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnEditor;
import com.tibco.cep.bpmn.ui.editor.BpmnEditorInput;
import com.tibco.cep.bpmn.ui.editor.IGraphSelection;
import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

public class PropertyLabelProvider extends LabelProvider {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		String text = null;
		Object selElement = null;
		if (element instanceof IGraphSelection) {
			IGraphSelection selection = (IGraphSelection) element;
			selElement = selection.getGraphObject();
		}
		if (selElement instanceof TSENode) {
			TSENode node = (TSENode)selElement;
			EObject userObject = (EObject)node.getUserObject();
			text = "";
			if(userObject != null){
				EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(userObject);
				if (userObjWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_NAME)) {
					text =userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);

				}else if (ExtensionHelper.isValidDataExtensionAttribute(
						userObjWrapper,
						BpmnMetaModelExtensionConstants.E_ATTR_LABEL)){
					EObjectWrapper<EClass, EObject> valWrapper = ExtensionHelper
							.getAddDataExtensionValueWrapper(userObjWrapper);
					if (valWrapper != null)
						text = valWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL);
				}
			}
			if(text == null){
				EClass nodeType = (EClass)node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
				if(nodeType != null)
					text = nodeType.getName();
				else
					text = "";
			}
			
			return text;
		}
		if (selElement instanceof TSEConnector) {
			TSEConnector node = (TSEConnector)selElement;
			EObject userObject = (EObject)node.getUserObject();
			text = "";
			if(userObject != null){
				EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(userObject);
				if (userObjWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_NAME)) {
					text =userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);

				}else if (ExtensionHelper.isValidDataExtensionAttribute(
						userObjWrapper,
						BpmnMetaModelExtensionConstants.E_ATTR_LABEL)){
					EObjectWrapper<EClass, EObject> valWrapper = ExtensionHelper
							.getAddDataExtensionValueWrapper(userObjWrapper);
					if (valWrapper != null)
						text = valWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL);
				}
			}
			if(text == null){
				EClass nodeType = (EClass)node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
				if(nodeType != null)
					text = nodeType.getName();
				else
					text = "";
			}
			
			return text;
		}
		if (selElement instanceof TSEGraph) {
			BpmnEditor editor = (BpmnEditor)PlatformUI.getWorkbench().
				getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			return "BPMN Process: " +
			((BpmnEditorInput)editor.getEditorInput()).getFile().getName();
			// ((BpmnGraphEditorInput)editor.getEditorInput()).getFile().getProject().getName();
		}
		if (selElement instanceof TSEEdge) {   
			TSEdge edge = (TSEdge)selElement;
			return edge.getText()!= null && !edge.getText().trim().equals("") ? edge.getText().trim():"Link";
		}
		return text != null ? text : ""/*super.getText(element)*/;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		Image img = null;
		
		if (element instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) element;
			element = selection.getFirstElement();
		}
		if (element instanceof TSENode) {
			@SuppressWarnings("unused")
			TSENode node = (TSENode)element;
			return BpmnUIPlugin.getDefault().getImage("icons/bpmn/Activity.gif");
		}
		if (element instanceof TSEdge) {   
			return BpmnUIPlugin.getDefault().getImage("icons/bpmn/SequenceFlow.gif");
		}
		if (element instanceof TSEGraph) {
			return BpmnUIPlugin.getDefault().getImage("icons/appicon16x16.gif");
		}
		if (element instanceof TSEConnector) {
			@SuppressWarnings("unused")
			TSEConnector node = (TSEConnector)element;
			return BpmnUIPlugin.getDefault().getImage("icons/bpmn/Activity.gif");
		}
		
		return img != null ? img : super.getImage(element);
	}

}
