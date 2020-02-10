package com.tibco.cep.webstudio.client.process.properties.tabs;

import static com.tibco.cep.webstudio.client.process.ProcessConstants.drawingViewName;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.moduleName;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.projectID;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.BlurEvent;
import com.smartgwt.client.widgets.form.fields.events.BlurHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.EditorExitEvent;
import com.smartgwt.client.widgets.form.fields.events.EditorExitHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.events.TabDeselectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.model.Operation;
import com.tibco.cep.webstudio.client.model.Port;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.model.Service;
import com.tibco.cep.webstudio.client.model.WSDLEntity;
import com.tibco.cep.webstudio.client.process.AbstractProcessEditor;
import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tibco.cep.webstudio.client.process.handler.BrowseButtonClickHandler;
import com.tibco.cep.webstudio.client.process.properties.DocumentationProperty;
import com.tibco.cep.webstudio.client.process.properties.Property;
import com.tibco.cep.webstudio.client.process.properties.general.WebserviceTaskGeneralProperty;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.client.view.TSWebModelViewCoordinators;

/**
 * This class is used to create the property tabs of web service task.
 * 
 * @author dijadhav
 * 
 */
public class WebServiceTaskPropertyTabSet extends PropertyTabSet implements HttpSuccessHandler, HttpFailureHandler {

	private WSDLEntity wsdlEntity;
	private TextItem resourceTextItem;
	private SelectItem services;
	private SelectItem ports;
	private SelectItem operations;
	private TextItem soapAction;
	
	public WebServiceTaskPropertyTabSet(Property property) {
		super(property);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.webstudio.client.process.properties.tabs.PropertyTabSet
	 * #createTabSet ()
	 */
	@Override
	public void createTabSet() {
		if (null == getGeneraltab()) {
			super.createTabSet();
		}
		if (property instanceof WebserviceTaskGeneralProperty) {
			createGeneralPropertyForm((WebserviceTaskGeneralProperty) property);
		} else if (property instanceof DocumentationProperty) {
			createDocumentationPropertyForm((DocumentationProperty) property);
		}
	}

	/**
	 * This method is used to create the general property form.
	 * 
	 * @param webserviceTaskGeneralProperty
	 */
	private void createGeneralPropertyForm(
			WebserviceTaskGeneralProperty webserviceTaskGeneralProperty) {
		
		if (webserviceTaskGeneralProperty.getResource() != null && !webserviceTaskGeneralProperty.getResource().isEmpty()) {
			fetchWSDLContents(WebStudio.get().getCurrentlySelectedProject(), webserviceTaskGeneralProperty.getResource());
		}

		CheckboxItem checkpointItem = PropertyCommanFieldUtil
				.getCheckpointItem(
						webserviceTaskGeneralProperty.isCheckPoint(),
						processMessages.processPropertyTabGeneralCheckPoint(),
						generalPropertyChangedHandler);

		resourceTextItem = PropertyCommanFieldUtil.getResourceTextItem(
				webserviceTaskGeneralProperty.getResource(),
				processMessages.processPropertyTabGeneralResource(),
				generalPropertyChangedHandler, ProcessConstants.RESOURCE);
		resourceTextItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				fetchWSDLContents(WebStudio.get().getCurrentlySelectedProject(), (String)event.getValue());
				
				services.clearValue();
				ports.clearValue();
				operations.clearValue();
				soapAction.clearValue();
			}
		});
		
		services = new SelectItem(
				processMessages.processPropertyTabGeneralService(),
				processMessages.processPropertyTabGeneralService());
		services.setWidth(150);
		services.setValue(webserviceTaskGeneralProperty.getService());
		services.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				if (wsdlEntity != null) {
					Service selectedService = wsdlEntity.getServiceByName((String)event.getValue());
					List<Port> ports = selectedService.getPorts();

					if (ports != null && ports.size() > 0) {
						LinkedHashMap<String, String> portMap = new LinkedHashMap<String, String>();
						for (Port port : ports) {
							portMap.put(port.getName(), port.getName());
						}
						WebServiceTaskPropertyTabSet.this.ports.setValueMap(portMap);
						
						// invoke command to persist value in the model
						LinkedList<String> args = new LinkedList<String>();
						args.add(services.getValueAsString());
						args.add(ProcessConstants.SERVICE);
						invokeCommand(args);
					}
				}
			}
		});
		
		ports = new SelectItem(
				processMessages.processPropertyTabGeneralPort(),
				processMessages.processPropertyTabGeneralPort());
		ports.setWidth(150);
		ports.setValue(webserviceTaskGeneralProperty.getPort());
		ports.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				if (wsdlEntity != null) {
					Service selectedService = wsdlEntity.getServiceByName(services.getValueAsString());
					Port selectedPort = selectedService.getPortByName((String)event.getValue());
					List<Operation> operations = selectedPort.getOperations();

					if (operations != null && operations.size() > 0) {
						LinkedHashMap<String, String> operationMap = new LinkedHashMap<String, String>();
						for (Operation operation : operations) {
							operationMap.put(operation.getName(), operation.getName());
						}
						WebServiceTaskPropertyTabSet.this.operations.setValueMap(operationMap);
						
						// invoke command to persist value in the model
						LinkedList<String> args = new LinkedList<String>();
						args.add(ports.getValueAsString());
						args.add(ProcessConstants.PORT);
						invokeCommand(args);
					}
				}
			}
		});
		
		operations = new SelectItem(
				processMessages.processPropertyTabGeneralOperation(),
				processMessages.processPropertyTabGeneralOperation());
		operations.setWidth(150);
		operations.setValue(webserviceTaskGeneralProperty.getOperation());
		operations.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				if (wsdlEntity != null) {
					Service selectedService = wsdlEntity.getServiceByName(services.getValueAsString());
					Port selectedPort = selectedService.getPortByName(ports.getValueAsString());
					Operation selectedOperation = selectedPort.getOperationByName((String)event.getValue());

					if (selectedOperation != null) {
						WebServiceTaskPropertyTabSet.this.soapAction.setValue(selectedOperation.getSoapAction());
						
						// invoke command to persist value in the model
						LinkedList<String> args = new LinkedList<String>();
						args.add(operations.getValueAsString());
						args.add(ProcessConstants.OPERATION);
						invokeCommand(args);
						
						// invoke command to persist value in the model
						args.clear();
						args.add(soapAction.getValueAsString());
						args.add(ProcessConstants.SOAPACTION);
						invokeCommand(args);
					}
				}
			}
		});
		
		soapAction = new TextItem(ProcessConstants.SOAPACTION,
				processMessages.processPropertyTabGeneralSoapAction());
		soapAction.setWidth(150);
		soapAction.setValue(webserviceTaskGeneralProperty.getSoapAction());
		soapAction.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				// invoke command to persist value in the model
				LinkedList<String> args = new LinkedList<String>();
				args.add(soapAction.getValueAsString());
				args.add(ProcessConstants.SOAPACTION);
				invokeCommand(args);
			}
		});
		
		final TextItem timeout = new TextItem(ProcessConstants.TIMEOUT,
				processMessages.processPropertyTabGeneralTimeOut());
		timeout.setWidth(150);
		timeout.setValue(webserviceTaskGeneralProperty.getTimeout());
		timeout.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				// invoke command to persist value in the model
				LinkedList<String> args = new LinkedList<String>();
				args.add(timeout.getValueAsString());
				args.add(ProcessConstants.TIMEOUT);
				invokeCommand(args);
			}
		});
		
		HLayout mainHLayout = new HLayout();

		DynamicForm generalForm = createGeneralPropertyForm();
		generalForm.setItems(
				getNameItem(webserviceTaskGeneralProperty.getName()),
				getLabelItem(webserviceTaskGeneralProperty.getLabel()),
				checkpointItem, resourceTextItem, services, ports, operations,
				soapAction, timeout);

		
		IButton resourceButton = new IButton(
				processMessages.processPropertyTabButtonBrowse());
		resourceButton.setWidth("100px");
		resourceButton.setHeight("23xp");
		resourceButton.setLeft(0);
		
		VLayout buttonLayout = new VLayout();
		buttonLayout.setAlign(VerticalAlignment.BOTTOM);
		buttonLayout.setMembersMargin(0);
		buttonLayout.setHeight("100px");
		buttonLayout.setWidth("100px");
		buttonLayout.addMember(resourceButton);
		resourceButton.addClickHandler(new BrowseButtonClickHandler("wsdl",
				null, resourceTextItem, null));

		SectionStack sectionStack = PropertyCommanFieldUtil.getHelpContainer(
				webserviceTaskGeneralProperty, processMessages);
		mainHLayout.setHeight(475);
		mainHLayout.setMembersMargin(20);
		mainHLayout.addMembers(generalForm, buttonLayout, sectionStack);
		getGeneraltab().setPane(mainHLayout);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.webstudio.client.process.properties.tabs.PropertyTabSet
	 * #onTabDeselected
	 * (com.smartgwt.client.widgets.tab.events.TabDeselectedEvent)
	 */
	@Override
	public void onTabDeselected(TabDeselectedEvent event) {
		// TODO Auto-generated method stub
		super.onTabDeselected(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.webstudio.client.process.properties.tabs.PropertyTabSet
	 * #onTabSelected(com.smartgwt.client.widgets.tab.events.TabSelectedEvent)
	 */
	@Override
	public void onTabSelected(TabSelectedEvent event) {
		// TODO Auto-generated method stub
		super.onTabSelected(event);
	}
	
	private void fetchWSDLContents(String projectName, String wsdlPath) {
		ArtifactUtil.addHandlers(this);
		
		HttpRequest request = new HttpRequest();
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, projectName));
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_PATH, wsdlPath));		
		request.submit(ServerEndpoints.RMS_GET_LOAD_AND_PARSE_WSDL.getURL());
	}
	
	@Override
	public void onSuccess(HttpSuccessEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_LOAD_AND_PARSE_WSDL.getURL()) != -1) {
			parseWSDLContents(event.getData());
			updateWSDLServiceFields();
			ArtifactUtil.removeHandlers(this);
		}
	}
	
	@Override
	public void onFailure(HttpFailureEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_LOAD_AND_PARSE_WSDL.getURL()) != -1) {
			ArtifactUtil.removeHandlers(this);
		}
	}
	
	private void parseWSDLContents(Element docElement) {
		wsdlEntity = new WSDLEntity();
		
		NodeList services = docElement.getElementsByTagName("service");
		for (int i = 0; i < services.getLength(); i++) {
			if (!services.item(i).toString().trim().isEmpty()) {
				Service service = new Service();
				wsdlEntity.addService(service);
				
				NodeList serviceDetailsList = services.item(i).getChildNodes();
				for (int j = 0; j < serviceDetailsList.getLength(); j++) {
					if (!serviceDetailsList.item(j).toString().trim().isEmpty()) {
						String nodeName = serviceDetailsList.item(j).getNodeName();

						if (nodeName.equals("name")) {
							service.setName(serviceDetailsList.item(j).getFirstChild().getNodeValue());
						} else if (nodeName.equals("port")) {
							Port port = new Port();
							service.addPort(port);
							
							NodeList portDetails = serviceDetailsList.item(j).getChildNodes();
							for (int k = 0; k < portDetails.getLength(); k++) {
								if (!portDetails.item(k).toString().trim().isEmpty()) {
									nodeName = portDetails.item(k).getNodeName();
									
									if (nodeName.equals("name")) {
										port.setName(portDetails.item(k).getFirstChild().getNodeValue());
									} else if (nodeName.equals("operation")) {
										Operation operation = new Operation();
										port.addOperations(operation);
										
										NodeList operationDetails = portDetails.item(k).getChildNodes();
										for (int l = 0; l < operationDetails.getLength(); l++) {
											if (!operationDetails.item(l).toString().trim().isEmpty()) {
												nodeName = operationDetails.item(l).getNodeName();
												String nodeValue = operationDetails.item(l).getFirstChild().getNodeValue();
												
												if (nodeName.equals("name")) {
													operation.setName(nodeValue);
												} else if (nodeName.equals("soapAction")) {
													operation.setSoapAction(nodeValue);
												}
											}
										}
									}
								}
							}
						}
					}
				}					
			}
		}
	}
	
	private void updateWSDLServiceFields() {
		List<Service> services = wsdlEntity.getServices();
		if (services != null && services.size() > 0) {
			LinkedHashMap<String, String> serviceMap = new LinkedHashMap<String, String>();
			for (Service service : services) {
				serviceMap.put(service.getName(), service.getName());
			}
			
			this.services.setValueMap(serviceMap);
		}
	}
	
	private void invokeCommand(LinkedList<String> args) {
		AbstractProcessEditor processEditor = (AbstractProcessEditor) WebStudio
				.get().getEditorPanel().getSelectedTab();
		if (!processEditor.isDirty()) {
			processEditor.makeDirty();
		}
		TSCustomCommand populate = new TSCustomCommand(
				projectID,
				moduleName,
				processEditor.getModelId(),
				processEditor.getDrawingViewID(),
				drawingViewName,
				"com.tibco.cep.webstudio.server.command.GeneralPropertyUpdateCommandImpl",
				args);
		TSWebModelViewCoordinators.get(processEditor.getModelId()).invokeCommandAndUpdateAll(
				populate);
	}
}
