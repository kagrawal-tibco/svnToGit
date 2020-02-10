package com.tibco.cep.bpmn.core.doc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

public class BpmnDocHelper {
	public static final int REPLY = 1;
	public static final int CONSUME = 2;

	public static Map<String, String> getScopeVariablesMapForInputmapper(
			EObject process, EObject flowNode) {
		Map<String, String> scopeVariables = new HashMap<String, String>();
		String inputMapperXslt = BpmnModelUtils.getInputMapperXslt(flowNode);
		if (inputMapperXslt != null && !inputMapperXslt.trim().isEmpty()) {
			String path = BpmnIndexUtils.getElementPath(process);
			path = path.replace(CommonIndexUtils.DOT
					+ CommonIndexUtils.PROCESS_EXTENSION, "");
			scopeVariables.put("$job", path);
		}
		return scopeVariables;
	}

	public static Map<String, String> getScopeVariablesMapForOutputmapper(
			EObject flowNode) {
		Map<String, String> scopeVariables = new HashMap<String, String>();
		String outputMapperXslt = BpmnModelUtils.getOutputMapperXslt(flowNode);
		if (outputMapperXslt != null && !outputMapperXslt.trim().isEmpty()) {
			Object attachedResource = BpmnModelUtils
					.getAttachedResource(flowNode);
			if (attachedResource instanceof String) {
				String path = (String) attachedResource;
				if (!path.trim().isEmpty()) {
					if (BpmnModelClass.EVENT.isSuperTypeOf(flowNode.eClass())
							|| BpmnModelClass.RECEIVE_TASK.equals(flowNode
									.eClass())) {
						String[] split = path.split("/");
						String name = split[split.length - 1];
						scopeVariables.put("$" + name, path);
					} else if (BpmnModelClass.SERVICE_TASK.equals(flowNode
							.eClass())) {
						scopeVariables.put("$message", path);
					} else if (BpmnModelClass.RULE_FUNCTION_TASK
							.equals(flowNode.eClass())) {
						scopeVariables.put("$return", path);
					}
				}
			} else if (attachedResource instanceof EObject) {
				if (BpmnModelClass.CALL_ACTIVITY.equals(flowNode.eClass())) {
					EObject obj = (EObject) attachedResource;
					String path = BpmnIndexUtils.getElementPath(obj);
					path = path.replace(CommonIndexUtils.DOT
							+ CommonIndexUtils.PROCESS_EXTENSION, "");
					scopeVariables.put("$return", path);
				}
			}

			if (BpmnModelClass.SUB_PROCESS.equals(flowNode.eClass())) {
				EObject obj = (EObject) flowNode;
				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(obj);
				String id = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				scopeVariables.put("$return", id);
			}
		}

		return scopeVariables;
	}

	public static Map<String, String> getReceivingParamsMapForInputmapper(
			EObject flowNode) {
		Map<String, String> scopeVariables = new HashMap<String, String>();
		String inputMapperXslt = BpmnModelUtils.getInputMapperXslt(flowNode);
		if (inputMapperXslt != null && !inputMapperXslt.trim().isEmpty()) {
			Object attachedResource = BpmnModelUtils
					.getAttachedResource(flowNode);
			if (attachedResource instanceof String) {
				String path = (String) attachedResource;
				if (!path.trim().isEmpty()) {
					if (BpmnModelClass.EVENT.isSuperTypeOf(flowNode.eClass())
							|| BpmnModelClass.SEND_TASK.equals(flowNode
									.eClass())
							|| BpmnModelClass.RULE_FUNCTION_TASK
									.equals(flowNode.eClass())
							|| BpmnModelClass.BUSINESS_RULE_TASK
									.equals(flowNode.eClass())) {
						String[] split = path.split("/");
						String name = split[split.length - 1];
						scopeVariables.put(name, path);
					} else if (BpmnModelClass.SERVICE_TASK.equals(flowNode
							.eClass())) {
						scopeVariables.put("message", path);
					}
				}
			} else if (attachedResource instanceof EObject) {
				if (BpmnModelClass.CALL_ACTIVITY.equals(flowNode.eClass())) {
					EObject obj = (EObject) attachedResource;
					String path = BpmnIndexUtils.getElementPath(obj);
					path = path.replace(CommonIndexUtils.DOT
							+ CommonIndexUtils.PROCESS_EXTENSION, "");
					scopeVariables.put("Input", path);
				}
			}

			if (BpmnModelClass.SUB_PROCESS.equals(flowNode.eClass())) {
				EObject obj = (EObject) flowNode;
				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(obj);
				String name = wrap
						.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
				String id = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				scopeVariables.put(name, id);
			}
		}

		if (BpmnModelClass.INFERENCE_TASK.equals(flowNode.eClass())) {
			EObject obj = (EObject) flowNode;
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(obj);
			String name = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			scopeVariables.put(name, "");
		}
		return scopeVariables;
	}

	public static Map<String, String> getReceivingParamsMapForOutputmapper(
			EObject process, EObject flowNode) {
		Map<String, String> scopeVariables = new HashMap<String, String>();
		String inputMapperXslt = BpmnModelUtils.getOutputMapperXslt(flowNode);
		if (inputMapperXslt != null && !inputMapperXslt.trim().isEmpty()) {
			String path = BpmnIndexUtils.getElementPath(process);
			path = path.replace(CommonIndexUtils.DOT
					+ CommonIndexUtils.PROCESS_EXTENSION, "");
			scopeVariables.put("job", path);
		}
		return scopeVariables;
	}

	public static List<String> getImplementationsForBusinessRuletask(
			EObject flowNode) {
		List<String> impls = new ArrayList<String>();
		if (flowNode.eClass().equals(BpmnModelClass.BUSINESS_RULE_TASK)) {
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
					.wrap(flowNode);
			if (ExtensionHelper.isValidDataExtensionAttribute(wrap,
					BpmnMetaModelExtensionConstants.E_ATTR_IMPLEMENTATIONS)) {
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(wrap);
				if (valueWrapper != null) {
					ArrayList<EObject> arrayList = new ArrayList<EObject>(
							valueWrapper
									.getListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IMPLEMENTATIONS));
					for (EObject eObject : arrayList) {
						EObjectWrapper<EClass, EObject> implementation = EObjectWrapper
								.wrap(eObject);
						String uri = implementation
								.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_URI);
						if (uri != null && !uri.trim().isEmpty()) {
							impls.add(uri);
						}
					}
				}
			}
		}
		return impls;
	}

	public static Map<String, Integer> getReplyConsumeEventList(EObject flowNode) {
		Map<String, Integer> replyConsumeMap = new HashMap<String, Integer>();
		if (BpmnModelClass.SEND_TASK.equals(flowNode.eClass())
				|| BpmnModelClass.END_EVENT.isSuperTypeOf(flowNode.eClass())) {
			EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper
					.wrap(flowNode);
			EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(flowNodeWrapper);
			if (addDataExtensionValueWrapper
					.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_STARTERS)) {

				List<EObject> listAttribute = addDataExtensionValueWrapper
						.getListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_STARTERS);
				if (listAttribute != null && listAttribute.size() > 0) {
					for (EObject eObject : listAttribute) {
						ROEObjectWrapper<EClass, EObject> wrap = ROEObjectWrapper
								.wrap(eObject);
						// here id is start event/receive task flow node
						String id = wrap
								.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_STARTER);
						boolean replyTo = (Boolean) wrap
								.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_REPLY_TO);
						boolean consume = (Boolean) wrap
								.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CONSUME);

						int mask = 0;

						mask = replyTo ? mask | REPLY : mask;
						mask = consume ? mask | CONSUME : mask;

						if (mask > 0)
							replyConsumeMap.put(id, mask);
					}
				}
			}
		}

		return replyConsumeMap;
	}
	
	public static Map<String, String> getTransportPropetiesMapForServiceTask(EObject flownode){
		Map<String, String> props = new HashMap<String, String>();
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flownode);
		if(flowNodeWrapper.getEClassType().equals(BpmnModelClass.SERVICE_TASK)){
			EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(flowNodeWrapper);
			if(valueWrapper != null){
				String service = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SERVICE);
				if(service != null && !service.trim().isEmpty())
					props.put("Service", service);
				String port = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PORT);
				if(port != null && !port.trim().isEmpty())
					props.put("Port", port);
				String operation = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_OPERATION);
				if(operation != null && !operation.trim().isEmpty())
					props.put("Operation", operation);
				Long timeout = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMEOUT);
				if(timeout != null)
					props.put("Timeout", timeout.toString());
				
				EEnumLiteral propType = valueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_BINDING_TYPE);
				if (propType == null || propType.equals(BpmnModelClass.ENUM_WS_BINDING_HTTP)){
					String endPointUrl = valueWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_END_POINT_URL);
					if(endPointUrl != null && !endPointUrl.trim().isEmpty())
						props.put("End Point Url", endPointUrl);
				}else{
					String providerUrl = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_URL);
					if(providerUrl != null && !providerUrl.trim().isEmpty())
						props.put("JNDI Context URL", providerUrl);
				}
			}
		}

		return props;
	}

}
