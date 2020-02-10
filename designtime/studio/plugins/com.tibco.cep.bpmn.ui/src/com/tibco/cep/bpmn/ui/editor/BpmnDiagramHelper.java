package com.tibco.cep.bpmn.ui.editor;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.palette.PaletteLoader;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tomsawyer.graph.TSGraphObject;

public class BpmnDiagramHelper {
	
	public static String getNodeToolTip(TSGraphObject node) {
		StringBuffer tooltip = new StringBuffer();
		
		if(node == null)
			return tooltip.toString();
		
//		Object obj = node.getUserObject();
		String description = null;
//		if(obj instanceof EObject) {
//			EObject eObj = (EObject) obj;
//			EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(eObj);
//			if(wrapper.isInstanceOf(BpmnModelClass.FLOW_NODE) ||
//					wrapper.isInstanceOf(BpmnModelClass.SEQUENCE_FLOW)) {
//				EList<EObject> listAttribute = wrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION);
//				if(listAttribute.size() > 0 ){
//					EObjectWrapper<EClass, EObject> doc = EObjectWrapper.wrap(listAttribute.get(0));
//					description = (String)doc.getAttribute(BpmnMetaModelConstants.E_ATTR_TEXT);
//					try {
//						
//						ByteArrayOutputStream baos = new ByteArrayOutputStream();
//						String xslt="<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" +
//								"<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" +
//								"<xsl:output omit-xml-declaration=\"yes\" method=\"xml\" indent=\"yes\"/>" + 
//								"<xsl:template match=\"*\"><xsl:copy><xsl:apply-templates select=\"@*\"/><xsl:apply-templates/></xsl:copy></xsl:template>" +
//								"<xsl:template match=\"@style\"/>" +
//								"<xsl:template match=\"@*\">" +
//								"<xsl:copy-of select=\".\"/>"+
//								"</xsl:template>" +
//								"</xsl:stylesheet> ";
//						javax.xml.transform.Source xmlSource = new javax.xml.transform.stream.StreamSource(new ByteArrayInputStream(description.getBytes()));
//						javax.xml.transform.Source xsltSource = new javax.xml.transform.stream.StreamSource(new ByteArrayInputStream(xslt.getBytes()));
//						javax.xml.transform.Result result = new javax.xml.transform.stream.StreamResult(baos);
//						
//						// create an instance of TransformerFactory
//						javax.xml.transform.TransformerFactory transFact =  javax.xml.transform.TransformerFactory.newInstance();
//						
//						javax.xml.transform.Transformer trans =    transFact.newTransformer(xsltSource);
//						
//						trans.transform(xmlSource, result);
//						description = baos.toString("UTF-8");
//					}catch(Throwable e) {
////						description=null;
//					}
//				}
//				
//			}
//			
//		}
//		if(description != null) {
//			tooltip.append(description);
//		} else {
			
			// task name
			String name = getname(node);
			
			if (name != null) {
				tooltip.append("<p><b>Name: </b> " + name.toString() + "</p>");
			}
			
			String label = getLabel(node);
			if (label != null) {
				tooltip.append("<p><b>Label: </b> " + label.toString() + "</p>");
			}
			
			String nType = getNodeType(node);
			if (nType != null) {
				
				tooltip.append("<p><b>Type: </b> " + nType + "</p>");
			}
			
			// description
//			String desc = (String) node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TASK_DESCRIPTION);
//			if (desc != null && !desc.trim().isEmpty()) {
//				tooltip.append("<p><b>Description: </b> "+ desc + "</p>");
//			}
//		}
		
		
//		// BE artifact to invoke
//		String task = (String) node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TASK_ACTION);
//		if (task != null) {
//			tooltip.append("<p><b>Task: </b> " + task.toString() + "</p>");
//		}
		
		// TODO do we need to show loop?(this is not valid for every node also) - manish
//		// Loop?
//		String mode = (String) node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TASK_MODE);
//		if (mode != null) {
//			tooltip.append("<p><b>Loop: </b> " + mode.toString() + "</p>");
//		}
//		else {
//			tooltip.append("<p><b>Loop: </b> " + BpmnUIConstants.NODE_ATTR_TASK_MODE_NONE + "</p>");
//		}
		
		return tooltip.toString();
		
	}
	
	private static String getname(TSGraphObject node){
		String name = null;
		EObject userObject = (EObject) node.getUserObject();

		if (userObject != null) {
			EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
					.wrap(userObject);
				name = userObjWrapper
						.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);

		}
		return name;
	}
	
	private static String getNodeType(TSGraphObject node) {
		String type = null;
		EObject userObject = (EObject) node.getUserObject();
		
		if (userObject != null) {
			EObjectWrapper<EClass, EObject> nodeWrapper = EObjectWrapper
					.wrap(userObject);
			EObjectWrapper<EClass, EObject> process = BpmnModelUtils.getProcess(userObject);
			if(process != null){
				String projectName = process.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
				BpmnPaletteModel bpmnPaletteModel = PaletteLoader.getBpmnPaletteModel(projectName);
				if (bpmnPaletteModel != null) {
					EClass nodeType = (EClass) node
							.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
					EClass nodeExtType = (EClass) node
							.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
					String id = (String) ExtensionHelper
							.getExtensionAttributeValue(nodeWrapper,
									BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID);
					BpmnPaletteGroupItem item = null;
					if (id != null && !id.isEmpty()) {
						item = bpmnPaletteModel.getPaletteItemById(id);
					} 
					if(item == null) {
						List<BpmnPaletteGroupItem> paletteToolByType = bpmnPaletteModel
								.getPaletteToolItemByType(nodeType, nodeExtType, true);
						if (paletteToolByType.size() > 0)
							item = paletteToolByType.get(0);
					}
					if (item != null)
						type = item.getTitle();
				}
			}

		}
		
		return type;
	}
	
	private static String getLabel(TSGraphObject node) {
		String lbl = null;
		EObject userObject = (EObject) node.getUserObject();

		if (userObject != null) {
			EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
					.wrap(userObject);
			if (userObjWrapper
					.containsAttribute(BpmnMetaModelConstants.E_ATTR_NAME)) {
				lbl = userObjWrapper
						.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);

			} else if (ExtensionHelper.isValidDataExtensionAttribute(
					userObjWrapper,
					BpmnMetaModelExtensionConstants.E_ATTR_LABEL)) {
				EObjectWrapper<EClass, EObject> valWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(userObjWrapper);
				if (valWrapper != null)
					lbl = valWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL);
			}
		}
		return lbl;
	}
	

}
