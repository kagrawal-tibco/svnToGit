package com.tibco.cep.bpmn.ui.graph.properties;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.bpmn.core.wsdl.WsdlWrapper;
import com.tibco.cep.bpmn.core.wsdl.WsdlWrapperFactory;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.xml.schema.SmElement;

/**
 * 
 * @author majha
 * 
 */
public class GeneralServiceTaskPropertySection extends
		GeneralTaskPropertySection {

	private final String ONE_WAY_SOAP_MESSAGE="oneWayMessage";
	
	private boolean refresh;
	
	private CCombo serviceCombo;
	private CCombo portCombo;
	private CCombo operationTypeCombo;
	private Text timeoutText;
	private Text soapActionText;

	private String service;
	private String port;
	private String operation;
	private long timeout;
	private String endPointUrl;
	private String soapAction;
	private String inputSchema;
	private String outputSchema;
	
	boolean isHttp;

	WsdlWrapper wsdl;

	private WidgetListener listener;

	public GeneralServiceTaskPropertySection() {
		super();
		this.listener = new WidgetListener();
		service = "";
		port = "";
		operation = "";
		timeout = 0;
		endPointUrl = "";
		soapAction = "";
		isHttp = true;
		inputSchema = "inputSchema";
		outputSchema = "outputSchema";
	}

	@Override
	public void aboutToBeHidden() {
		// TODO Auto-generated method stub
		super.aboutToBeHidden();
		if (!composite.isDisposed()) {
			serviceCombo.removeModifyListener(this.listener);
			portCombo.removeModifyListener(this.listener);
			operationTypeCombo.removeModifyListener(this.listener);
			timeoutText.removeModifyListener(this.listener);
			resourceText.removeModifyListener(this.listener);
			soapActionText.removeModifyListener(this.listener);
		}
	}

	@Override
	public void aboutToBeShown() {
		// TODO Auto-generated method stub
		super.aboutToBeShown();
		
		if (!composite.isDisposed()) {
			serviceCombo.addModifyListener(this.listener);
			portCombo.addModifyListener(this.listener);
			operationTypeCombo.addModifyListener(this.listener);
			timeoutText.addModifyListener(this.listener);
			resourceText.addModifyListener(this.listener);
			soapActionText.addModifyListener(this.listener);
		}
	}

	@Override
	protected boolean isServiceTask() {
		return true;
	}
	
	protected void insertChildSpecificComponents() {
		super.insertChildSpecificComponents();
		
		getWidgetFactory().createLabel(composite,  BpmnMessages.getString("serviceTaskProp_serviceCombo_label"), SWT.NONE);
		serviceCombo = getWidgetFactory()
				.createCCombo(composite, SWT.READ_ONLY);
		GridData gd = new GridData();
		gd.widthHint = 300;
		gd.heightHint = 20;
		gd.horizontalIndent = 1;
		serviceCombo.setLayoutData(gd);

		getWidgetFactory().createLabel(composite, BpmnMessages.getString("serviceTaskProp_portCombo_label"), SWT.NONE);
		portCombo = getWidgetFactory().createCCombo(composite, SWT.READ_ONLY);
		gd = new GridData();
		gd.widthHint = 300;
		gd.heightHint = 20;
		gd.horizontalIndent = 1;
		portCombo.setLayoutData(gd);

		getWidgetFactory().createLabel(composite,  BpmnMessages.getString("serviceTaskProp_operationTypeCombo_label"), SWT.NONE);
		operationTypeCombo = getWidgetFactory().createCCombo(composite,
				SWT.READ_ONLY);
		gd = new GridData();
		gd.widthHint = 300;
		gd.heightHint = 20;
		gd.horizontalIndent = 1;
		operationTypeCombo.setLayoutData(gd);

		getWidgetFactory().createLabel(composite, BpmnMessages.getString("serviceTaskProp_soapActionText_label"), SWT.NONE);
		soapActionText = getWidgetFactory().createText(composite, "0");
		gd = new GridData();
		gd.widthHint = 300;
		gd.heightHint = 20;
		gd.horizontalIndent = 1;
		soapActionText.setLayoutData(gd);

		getWidgetFactory().createLabel(composite, BpmnMessages.getString("serviceTaskProp_timeoutText_label"), SWT.NONE);
		timeoutText = getWidgetFactory().createText(composite, "0");
		gd = new GridData();
		gd.widthHint = 300;
		gd.heightHint = 20;
		gd.horizontalIndent = 1;
		timeoutText.setLayoutData(gd);
		timeoutText.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9')) {
						e.doit = false;
						return;
					}
				}
			}
		}); 
		

	}

	public void refresh() {
		this.refresh = true;
		if (fTSENode != null) {
			super.refresh();
			@SuppressWarnings("unused")
			EClass nodeType = (EClass) fTSENode
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
			@SuppressWarnings("unused")
			EClass nodeExtType = (EClass) fTSENode
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
			@SuppressWarnings("unused")
			String nodeName = (String) fTSENode
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);

			EObject userObject = (EObject) fTSENode.getUserObject();
			EObjectWrapper<EClass, EObject> taskWrapper = EObjectWrapper
					.wrap(userObject);

			refreshWidgetsForResourceChange(taskWrapper);
			refreshWidgetFromModel(taskWrapper);

		}
		if (fTSEEdge != null) {
		}
		if (fTSEGraph != null) {
		}

		// System.out.println("Done with refresh.");
		this.refresh = false;
	}

	private void refreshWidgetsForResourceChange(
			EObjectWrapper<EClass, EObject> taskWrapper) {
		removeListener();
		operationTypeCombo.removeAll();
		serviceCombo.removeAll();
		portCombo.removeAll();
		soapActionText.setText("");
		operation = "";
		service = "";
		port = "";
		endPointUrl = "";
		soapAction = "";
		inputSchema = "inputSchema";
		outputSchema = "outputSchma";
		wsdl = null;
		if (taskWrapper.getEClassType().equals(BpmnModelClass.SERVICE_TASK)) {
			initializeWsdl();
			if (wsdl != null) {
				Set<String> services = wsdl.getServices();
				for (String service : services) {
					serviceCombo.add(service);
				}

				if (services.size() > 0) {
					serviceCombo.select(0);
					service = serviceCombo.getText();
					refreshWidgetForServiceChange(taskWrapper);
				}

			}

		}
		addListener();
	}

	private void initializeWsdl() {
		String text = resourceText.getText();
		if (text.isEmpty())
			return;

		IFile file = getProject().getFile(text);
		String pathWsdl=file.getLocation().toPortableString();
		if(!pathWsdl.contains( ".wsdl"))
		 pathWsdl = pathWsdl + ".wsdl";
		File file2 = new File(pathWsdl);
		if (file2.exists()) {
			wsdl = WsdlWrapperFactory.getWsdl(fProject, text);
		}
		if(!file.exists()){
			IFile fileLoc=IndexUtils.getLinkedResource(this.getDiagramManager().getProject().getName(),text);
			if(fileLoc!=null)
				file=fileLoc;	
		}
		if(file.exists())
			wsdl = WsdlWrapperFactory.getWsdl(fProject, text);
	}

	private void refreshWidgetForServiceChange(
			EObjectWrapper<EClass, EObject> taskWrapper) {
		removeListener();

		operationTypeCombo.removeAll();
		portCombo.removeAll();

		operation = "";
		port = "";
		endPointUrl = "";

		if (taskWrapper.getEClassType().equals(BpmnModelClass.SERVICE_TASK)
				&& wsdl != null) {
			Set<String> ports = wsdl.getPort(serviceCombo.getText());
			for (String string2 : ports) {
				portCombo.add(string2);
			}
			if (ports.size() > 0) {
				portCombo.select(0);
				port = portCombo.getText();
				endPointUrl = wsdl.getEndPointUrl(
						serviceCombo.getText(), portCombo.getText());
				refreshWidgetForPortChange(taskWrapper);
			}

		}
		addListener();
	}
	
	private void removeListener(){
		if (!composite.isDisposed()) {
			serviceCombo.removeModifyListener(this.listener);
			portCombo.removeModifyListener(this.listener);
			operationTypeCombo.removeModifyListener(this.listener);
			timeoutText.removeModifyListener(this.listener);
			soapActionText.removeModifyListener(this.listener);
			resourceText.removeModifyListener(this.listener);
		}
	}
	
	private void addListener(){
		if (!composite.isDisposed()) {
			serviceCombo.addModifyListener(this.listener);
			portCombo.addModifyListener(this.listener);
			operationTypeCombo.addModifyListener(this.listener);
			timeoutText.addModifyListener(this.listener);
			soapActionText.addModifyListener(this.listener);
			resourceText.addModifyListener(this.listener);
		}
	}

	private void refreshWidgetForPortChange(
			EObjectWrapper<EClass, EObject> taskWrapper) {
		removeListener();
		operationTypeCombo.removeAll();

		operation = "";

		if (taskWrapper.getEClassType().equals(BpmnModelClass.SERVICE_TASK)
				&& wsdl != null) {
			Set<String> operationTypes = wsdl.getOperationTypes(
					portCombo.getText(), serviceCombo.getText());
			for (String opType : operationTypes) {
				operationTypeCombo.add(opType);
			}

			if (operationTypes.size() > 0) {
				operationTypeCombo.select(0);
				operation = operationTypeCombo.getText();
			}
			
			refreshWidgetForOperationChange(taskWrapper);

		}
		
		addListener();
	}
	
	private void refreshWidgetForOperationChange(
			EObjectWrapper<EClass, EObject> taskWrapper){
		removeListener();
		if (taskWrapper.getEClassType().equals(BpmnModelClass.SERVICE_TASK)
				&& wsdl != null) {

			SmElement outMessgeElement = wsdl.getOutMessgeElement(operation);
			if(outMessgeElement == null){
				outputSchema = ONE_WAY_SOAP_MESSAGE;
			}else{
				outputSchema = "outputSchema";//TODO add proper output Schema
			}
			
			soapAction = wsdl.getSoapAction(port, service, operation);
			soapActionText.setText(soapAction);

		}
		
		addListener();
	}

	private void refreshWidgetFromModel(
			EObjectWrapper<EClass, EObject> taskWrapper) {
		if (taskWrapper.getEClassType().equals(BpmnModelClass.SERVICE_TASK)) {
			EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(taskWrapper);
			if (addDataExtensionValueWrapper != null) {
				service = addDataExtensionValueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SERVICE);
				port = addDataExtensionValueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PORT);
				timeout = addDataExtensionValueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMEOUT);
				endPointUrl = addDataExtensionValueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_END_POINT_URL);
				operation = addDataExtensionValueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_OPERATION);
				soapAction = addDataExtensionValueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SOAP_ACTION);
				inputSchema = addDataExtensionValueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_PAYLOAD_SCHEMA);
				outputSchema = addDataExtensionValueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_PAYLOAD_SCHEMA);
				serviceCombo.setText(service);
				portCombo.setText(port);
				timeoutText.setText(String.valueOf(timeout));
				operationTypeCombo.setText(operation);
				soapActionText.setText(soapAction);
			}

		}

	}
	
	private void webServiceResourceDeleted(Map<String, Object> updateList) {
		updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_SERVICE, "");
		updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_PORT, "");
		updateList
				.put(BpmnMetaModelExtensionConstants.E_ATTR_END_POINT_URL, "");
		updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_OPERATION, "");
		updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_SOAP_ACTION, "");
		updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_BINDING_TYPE,
				BpmnModelClass.ENUM_WS_BINDING_HTTP);
		updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_JMS_CONFIG, null);
		updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_HTTP_SSL_CONFIG, null);
		
		updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_PAYLOAD_SCHEMA, null);
		updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_PAYLOAD_SCHEMA, null);

		List<EObject> associations = new ArrayList<EObject>();
		updateList.put(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS,
				associations);

		associations = new ArrayList<EObject>();
		updateList.put(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS,
				associations);
	}

	

	private class WidgetListener implements ModifyListener {

		public void modifyText(ModifyEvent e) {
			if (refresh) {
				return;
			}
			EObject userObject = (EObject) fTSENode.getUserObject();
			EObjectWrapper<EClass, EObject> taskWrapper = EObjectWrapper
					.wrap(userObject);
			
			Map<String, Object> updateList = new HashMap<String, Object>();
			Object source = e.getSource();
			if (source == operationTypeCombo) {
				String operationTypeText = operationTypeCombo.getText();
				if (operationTypeText != null
						&& operationTypeText.trim().length() > 0) {
					if (!operation.equalsIgnoreCase(operationTypeText)) {
						operation = operationTypeText;
						refresh = true;	
						refreshWidgetForOperationChange(taskWrapper);
						refresh = false;
						
						updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_OPERATION,
											operationTypeText);
						List<EObject> associations = new ArrayList<EObject>();
						updateList.put(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS,
								associations);
						
						associations = new ArrayList<EObject>();
						updateList.put(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS,
								associations);
						
						updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_PAYLOAD_SCHEMA,
								outputSchema);
						updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_PAYLOAD_SCHEMA,
								inputSchema);
						
						if(wsdl != null){
							soapAction = wsdl.getSoapAction(port, service, operation);
							updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_SOAP_ACTION,soapAction);
							isHttp = wsdl.isHttpTransport(port, service);
							if(isHttp){
								updateList
								.put(BpmnMetaModelExtensionConstants.E_ATTR_BINDING_TYPE,
										BpmnModelClass.ENUM_WS_BINDING_HTTP);
							}else{
								updateList
								.put(BpmnMetaModelExtensionConstants.E_ATTR_BINDING_TYPE,
										BpmnModelClass.ENUM_WS_BINDING_JMS);
								EObject userObject2 = (EObject)fTSENode.getUserObject();
								if(userObject2 != null){
									EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(userObject2);
									wsdl.getJmsTransportProperties(service, port, wrap);
								}
							}
						}
					}
				}

			} else if (source == serviceCombo) {
				String serviceText = serviceCombo.getText();
				if (serviceText != null
						&& serviceText.trim().length() > 0) {
					if (!service.equalsIgnoreCase(serviceText)) {
						service = serviceText;
						refresh = true;
						
						refreshWidgetForServiceChange(taskWrapper);
						refresh = false;
						
						updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_SERVICE,
											service);
						updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_PORT,
								portCombo.getText());
						updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_END_POINT_URL,
								endPointUrl);
						updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_OPERATION,
								operation);
						List<EObject> associations = new ArrayList<EObject>();
						updateList.put(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS,
								associations);
						
						associations = new ArrayList<EObject>();
						updateList.put(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS,
								associations);
						
						updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_PAYLOAD_SCHEMA,
								outputSchema);
						updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_PAYLOAD_SCHEMA,
								inputSchema);
						
						if(wsdl != null){
							soapAction = wsdl.getSoapAction(port, service, operation);
							updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_SOAP_ACTION,soapAction);
							isHttp = wsdl.isHttpTransport(port, service);
							if(isHttp){
								updateList
								.put(BpmnMetaModelExtensionConstants.E_ATTR_BINDING_TYPE,
										BpmnModelClass.ENUM_WS_BINDING_HTTP);
							}else{
								updateList
								.put(BpmnMetaModelExtensionConstants.E_ATTR_BINDING_TYPE,
										BpmnModelClass.ENUM_WS_BINDING_JMS);
								EObject userObject2 = (EObject)fTSENode.getUserObject();
								if(userObject2 != null){
									EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(userObject2);
									wsdl.getJmsTransportProperties(service, port, wrap);
								}
							}
						}
					}
				}
			} else if (source == portCombo) {
				String portText = portCombo.getText();
				if (portText != null
						&& portText.trim().length() > 0) {
					if (!port.equalsIgnoreCase(portText)) {
						port = portText;
						refresh = true;
						refreshWidgetForPortChange(taskWrapper);
						refresh = false;
						updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_PORT,
								portCombo.getText());
						updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_END_POINT_URL,
								endPointUrl);
						updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_OPERATION,
								operation);
						List<EObject> associations = new ArrayList<EObject>();
						updateList.put(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS,
								associations);
						
						associations = new ArrayList<EObject>();
						updateList.put(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS,
								associations);
						
						updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_PAYLOAD_SCHEMA,
								outputSchema);
						updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_PAYLOAD_SCHEMA,
								inputSchema);
						
						if(wsdl != null){
							soapAction = wsdl.getSoapAction(port, service, operation);
							updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_SOAP_ACTION,soapAction);
							isHttp = wsdl.isHttpTransport(port, service);
							if(isHttp){
								updateList
								.put(BpmnMetaModelExtensionConstants.E_ATTR_BINDING_TYPE,
										BpmnModelClass.ENUM_WS_BINDING_HTTP);
							}else{
								updateList
								.put(BpmnMetaModelExtensionConstants.E_ATTR_BINDING_TYPE,
										BpmnModelClass.ENUM_WS_BINDING_JMS);
								EObject userObject2 = (EObject)fTSENode.getUserObject();
								if(userObject2 != null){
									EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(userObject2);
									wsdl.getJmsTransportProperties(service, port, wrap);
								}
							}
						}
					}
				}
			}else if (source == timeoutText) {
				String timeOutText = timeoutText.getText();
				if (timeOutText != null
						&& timeOutText.trim().length() > 0) {
					if (timeout!=Long.parseLong(timeOutText)) {
						timeout = Long.parseLong(timeOutText);
						updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_TIMEOUT,timeout);
					}
				}
			}else if (source == soapActionText) {
				String sAction= soapActionText.getText();
				if (sAction != null
						&& sAction.trim().length() > 0) {
					if (!soapAction.equals(sAction)) {
						soapAction = sAction;
						updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_SOAP_ACTION,soapAction);
					}
				}
			}else if (source == resourceText) {
				boolean empty = resourceText.getText().trim().isEmpty();
				Object res = getResource();
				if (empty || res != null) {
					String attrName = getAttrNameForTaskSelection();
					if (res instanceof String) {
						if (!((String) res).equalsIgnoreCase((String) resource)) {
							if (attrName != null) {
								updateList.put(attrName, res);
								resource = res;
								refreshWidgetsForResourceChange(taskWrapper);
								 userObject = (EObject) fTSENode.getUserObject();
									taskWrapper = EObjectWrapper
											.wrap(userObject);
								updateList
										.put(BpmnMetaModelExtensionConstants.E_ATTR_SERVICE,
												service);
								updateList 
										.put(BpmnMetaModelExtensionConstants.E_ATTR_PORT,
												portCombo.getText());
								updateList
										.put(BpmnMetaModelExtensionConstants.E_ATTR_END_POINT_URL,
												endPointUrl);
								
								updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_OPERATION,
													operation);
								List<EObject> associations = new ArrayList<EObject>();
								updateList.put(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS,
										associations);
								
								associations = new ArrayList<EObject>();
								updateList.put(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS,
										associations);
								
								updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_PAYLOAD_SCHEMA,
										outputSchema);
								updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_PAYLOAD_SCHEMA,
										inputSchema);

								if (wsdl != null) {
									soapAction = wsdl.getSoapAction(
											port, service, operation);
									updateList
											.put(BpmnMetaModelExtensionConstants.E_ATTR_SOAP_ACTION,
													soapAction);
									isHttp = wsdl.isHttpTransport(port, service);
									if(isHttp){
										updateList
										.put(BpmnMetaModelExtensionConstants.E_ATTR_BINDING_TYPE,
												BpmnModelClass.ENUM_WS_BINDING_HTTP);
									}else{
										updateList
										.put(BpmnMetaModelExtensionConstants.E_ATTR_BINDING_TYPE,
												BpmnModelClass.ENUM_WS_BINDING_JMS);
										EObject userObject2 = (EObject)fTSENode.getUserObject();
										if(userObject2 != null){
											EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(userObject2);
											EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(wrap);{
												addDataExtensionValueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMS_CONFIG, null);
											}
											wsdl.getJmsTransportProperties(service, port, wrap);
										}
									}
								}else{
									webServiceResourceDeleted(updateList);
								}
							}
						}
					}
				}

			}

			updatePropertySection(updateList);
		}

	}
	

}