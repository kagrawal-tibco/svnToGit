package com.tibco.cep.studio.ui.editors.channels;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.addInstanceProperty;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.setIntialDataForRenderedComponents;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.ui.editors.channels.contoller.PropertyConfiguration;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.forms.AbstractDestinationDetailsPage;
import com.tibco.cep.studio.ui.util.GvField;

/**
 * 
 * @author sasahoo
 *
 */
public class ChannelDestinationsPage extends AbstractDestinationDetailsPage{

	
	/**
	 * 
	 * @param editingDomain
	 * @param viewer
	 * @param driverType
	 */
	public ChannelDestinationsPage(EditingDomain editingDomain,IProject project, TableViewer viewer,ChannelFormViewer formViewer) {
		this.editingDomain = editingDomain;
		this.viewer = viewer;
		this.delegate = formViewer.getDelegate();
		this.project = project;
		this.formViewer = formViewer;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#initialize(org.eclipse.ui.forms.IManagedForm)
	 */
	public void initialize(IManagedForm managedForm) {
		this.managedForm = managedForm;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#inputChanged(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void selectionChanged(IFormPart part, ISelection selection) {
		try{
			IStructuredSelection sel = (IStructuredSelection)selection;
			if (sel.size()==1) {
				destination = (Destination)sel.getFirstElement();
			}
			else
				destination = null;
			
			update();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#commit()
	 */
	public void commit(boolean onSave) {
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#setFocus()
	 */
	public void setFocus() {
		type = formViewer.getDriverCombo().getText();
		destinationDetailsPageBook.showPage(type);
		String projectName = project.getName().toString() ;
		projectName =  projectName.substring(projectName.lastIndexOf("/") + 1 );
		IFile file = IndexUtils.getLinkedResource(projectName, formViewer.getChannel().getFullPath()) ;
		if (file != null && IndexUtils.isProjectLibType( file ) ) {
			formViewer.getEditor().setEnabled(false);
			viewer.getTable().setEnabled(false);
			readOnlyWidgets();
		}
		
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#dispose()
	 */
	public void dispose() {
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#isDirty()
	 */
	public boolean isDirty() {
		return false;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#isStale()
	 */
	public boolean isStale() {
		return false;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#refresh()
	 */
	public void refresh() {
		update();
	}
	public boolean setFormInput(Object input) {
		return false;
	}
	
	protected void update() {
		
		type = formViewer.getDriverCombo().getText();
		destinationDetailsPageBook.showPage(type);
		
		instance = destination.getProperties();
		
		if(instance == null){
			createInstance();//creating a new Instance
		}
		EList<Entity> properties = instance.getProperties();
		for (int i=0; i<properties.size(); i++) {
			Entity entity = properties.get(i);
			String propName = entity.getName();
			
			// Set input for Kafka Streams processor topology
			if ("processor.topology".equals(propName)) {
				if (entity instanceof SimpleProperty) {
					SimpleProperty simpleProperty = (SimpleProperty) entity;
					if (controls.get(type + CommonIndexUtils.DOT + "processor.topology") != null) {
						TableViewer tableViewer = (TableViewer) controls.get(type + CommonIndexUtils.DOT +"processor.topology");
						tableViewer.setInput(simpleProperty);						
					}
				}
			}

			// Fix for old be.http.PageFlow property.
			// This will be executed only once to change the property in channel
			if ("be.http.PageFlow".equals(propName)) {
				if (entity instanceof SimpleProperty) {
					SimpleProperty simpleProperty = (SimpleProperty) entity;
					if (controls.get(type + CommonIndexUtils.DOT + "be.http.type") != null) {
						Combo combo = (Combo) controls.get(type + CommonIndexUtils.DOT + "be.http.type");

						// Set the combo box display text
						String httpType = "Default";
						if (Boolean.valueOf(simpleProperty.getValue())) {
							httpType = "PageFlow";
						}
						combo.setText(httpType);
						// Update the combo box
						controls.put(type + CommonIndexUtils.DOT + "be.http.type", combo);

						// change entity name and value
						entity.setName("be.http.type");
						((SimpleProperty) entity).setValue(httpType.toUpperCase());

						// Modify the editor
						formViewer.getEditor().modified();
					}
				}
			}

			if(controls.keySet().contains(type + CommonIndexUtils.DOT + propName) && !"processor.topology".equals(propName)){
				if (entity instanceof SimpleProperty) {
					
					SimpleProperty simpleProperty = (SimpleProperty) entity;
					
					//Setting Mandatory Property
					simpleProperty.setMandatory(isMandatory(propName));
					
					Object controlObject = controls.get(type + CommonIndexUtils.DOT + propName);
					if(controlObject instanceof GvField){
						((GvField)controlObject).getField().setData(simpleProperty);
						((GvField)controlObject).getGvText().setData(simpleProperty);
					}else{
						((Control)controlObject).setData(simpleProperty);	
					}
				}
			}
		}
		
		// update destination details layout dynamically
		updateLayout(details, delegate.getDestinationConfiguration(type).size(),type); 
		
		((ChannelFormEditor)formViewer.getEditor()).setDestinationReset(true);
		
		//populating initial model data for the destination widgets
		setIntialDataForRenderedComponents((Control)controls.get(type + CommonIndexUtils.DOT + Messages.getString("Name")), destination.getName(), null, null,null,null);
		setIntialDataForRenderedComponents((Control)controls.get(type + CommonIndexUtils.DOT + Messages.getString("Description")), destination.getDescription(), null, null,null,null);
		setIntialDataForRenderedComponents((Control)controls.get(type + CommonIndexUtils.DOT + Messages.getString("channel.destination.details.DefaultEvent")), destination.getEventURI(), null, null,null,null);
		setIntialDataForRenderedComponents((Control)controls.get(type + CommonIndexUtils.DOT + Messages.getString("channel.destination.details.SerializerDeserializer")),
				destination.getSerializerDeserializerClass(),editingDomain, destination, ChannelPackage.eINSTANCE.getDestination_SerializerDeserializerClass(), delegate.getDefaultSerializerClass(type));
		
		for (int i=0; i<properties.size(); i++) {
			Entity entity = properties.get(i);
			String propName = entity.getName();
			if ((entity instanceof SimpleProperty) && !"processor.topology".equals(propName)) {
				
				Object controlObj = controls.get(type + CommonIndexUtils.DOT + propName);
				if(controlObj instanceof GvField){
					
					GvField gvField = (GvField)controlObj; 
					String value = ((SimpleProperty) entity).getValue();
					if(!GvUtil.isGlobalVar(value)){
						if(gvField.getField() instanceof Combo){
							final List<String> displayValues = getPropertyConfiguration(propName).getDisplayedValues();
							final List<String> values = getPropertyConfiguration(propName).getValues();
							value = EditorUtils.getRenderedValue(displayValues, values, value, true);
						}
					}
					gvField.setValue(value);
				}
				else{
					
					Control control = (Control)controlObj;
					String value = ((SimpleProperty) entity).getValue();
					
					//Getting the Display Value from the actual value for Combo field only
					if(control instanceof Combo){
						final List<String> displayValues = getPropertyConfiguration(propName).getDisplayedValues();
						final List<String> values = getPropertyConfiguration(propName).getValues();
						value = EditorUtils.getRenderedValue(displayValues, values, value, true);
					}
					
					setIntialDataForRenderedComponents(control, value, null, null,null,null);
				}
				
			}
		}
		
		//This is hard coded check, to fix one CR 1-831VO3
		//{Support:TIBCO} Enhancement: defining a destination against a JMS channel
		if (controls.get(type + CommonIndexUtils.DOT +"Queue") != null){
		//	Button  buttonQueue = (Button)controls.get(type + CommonIndexUtils.DOT + "Queue");
			GvField  buttonQueueGv = (GvField)controls.get(type + CommonIndexUtils.DOT + "Queue");
			Button  buttonQueue=(Button) buttonQueueGv.getField();
			GvField  cntrlDurableSubscriberName = (GvField)controls.get(type + CommonIndexUtils.DOT +"DurableSuscriberName");
			if (buttonQueue.getSelection()) {
				cntrlDurableSubscriberName.setEnabled(false);
			} else {
				cntrlDurableSubscriberName.setEnabled(true);
			}
			
			GvField  cntrlSharedSubscriptionName = (GvField)controls.get(type + CommonIndexUtils.DOT +"SharedSubscriptionName");
			if (buttonQueue.getSelection()) {
				cntrlSharedSubscriptionName.setEnabled(false);
			} else {
				cntrlSharedSubscriptionName.setEnabled(true);
			}
			
			Control serializerDeserializerControl = (Control) controls.get(type + CommonIndexUtils.DOT + Messages.getString("channel.destination.details.SerializerDeserializer"));
			Button  isJSONPayload = (Button)controls.get(type + CommonIndexUtils.DOT + "IsJSONPayload");
			if(isJSONPayload != null) {
				String serializerDeserializerType = serializerDeserializerControl.toString();
				if (serializerDeserializerType != null 
						&& !serializerDeserializerType.isEmpty() 
						&& (serializerDeserializerType.contains("TextMessageSerializer") || serializerDeserializerType.contains("BytesMessageSerializer"))) {
					isJSONPayload.setEnabled(true);
				} else {
					isJSONPayload.setSelection(false);
					isJSONPayload.setEnabled(false);
				}
			}
		}
		
		if (controls.get(type + CommonIndexUtils.DOT +"be.http.type") != null) {
			Combo destinationType = (Combo) controls.get(type + CommonIndexUtils.DOT + "be.http.type");
//			Button  buttonPageFlow = (Button)controls.get(type + CommonIndexUtils.DOT + "be.http.PageFlow");
			Control contextPathControl = (Control) controls.get(type + CommonIndexUtils.DOT + "be.http.contextPath");
			Control pageFlowFunctionControl = (Control) controls.get(type + CommonIndexUtils.DOT + "be.http.pageFlowFunction");
			Control serializerDeserializerControl = (Control) controls.get(type + CommonIndexUtils.DOT + Messages.getString("channel.destination.details.SerializerDeserializer"));
			Control defaultEventControl = (Control) controls.get(type + CommonIndexUtils.DOT + Messages.getString("channel.destination.details.DefaultEvent"));
			Control browseControl = (Control) controls.get(type + CommonIndexUtils.DOT + Messages.getString("Browse"));
			Control browseControlProperty = (Control) controls.get(type + CommonIndexUtils.DOT + Messages.getString("Browse")+Messages.getString("Property"));
			Button  isJSONPayload = (Button)controls.get(type + CommonIndexUtils.DOT + "be.http.jsonPayload");
			if (destinationType.getText().equalsIgnoreCase("PageFlow")) {
				contextPathControl.setEnabled(true);
				pageFlowFunctionControl.setEnabled(true);
				serializerDeserializerControl.setEnabled(false);
				defaultEventControl.setEnabled(false);
				browseControl.setEnabled(false);
				browseControlProperty.setEnabled(true);
				isJSONPayload.setEnabled(false);
			
			} else {
				contextPathControl.setEnabled(false);
				pageFlowFunctionControl.setEnabled(false);
				serializerDeserializerControl.setEnabled(true);
				defaultEventControl.setEnabled(true);
				browseControl.setEnabled(true);
				browseControlProperty.setEnabled(false);
				
				String serializerDeserializerType = serializerDeserializerControl.toString();
				if (serializerDeserializerType != null && !serializerDeserializerType.isEmpty() && serializerDeserializerType.contains("SOAPMessageSerializer")) {
					if (isJSONPayload != null) {
						isJSONPayload.setEnabled(false);
					}
				} else {
					isJSONPayload.setEnabled(true);
				}
				
				Combo serializerDeserializerCombo = (Combo) serializerDeserializerControl;
				if (destinationType.getText().equalsIgnoreCase("WebSocket")) {					
					serializerDeserializerCombo.setText("com.tibco.cep.driver.http.serializer.WebSocketMessageSerializer");
				} else if (serializerDeserializerCombo.getText().contains("WebSocketMessageSerializer")) {
					serializerDeserializerCombo.setText("com.tibco.cep.driver.http.serializer.RESTMessageSerializer");
				}
			}
		}
		
		if (controls.get(type + CommonIndexUtils.DOT +"ClientType") != null) {
			Combo clientTypeControl = (Combo) controls.get(type + CommonIndexUtils.DOT + "ClientType");
			Control predicateControl = (Control) controls.get(type + CommonIndexUtils.DOT + "Predicate");
			Button bufferingControl = (Button) controls.get(type + CommonIndexUtils.DOT + "EnableBuffering");
			Control bufferSizeControl = (Control) controls.get(type + CommonIndexUtils.DOT + "BufferSize");
			Control flushIntervalControl = (Control) controls.get(type + CommonIndexUtils.DOT + "FlushInterval");
			
			if("Enqueuer".equals(clientTypeControl.getItem(clientTypeControl.getSelectionIndex()))){
				predicateControl.setEnabled(false);
				bufferingControl.setEnabled(true);
				if (bufferingControl.getSelection()) {
					bufferSizeControl.setEnabled(true);
					flushIntervalControl.setEnabled(true);
				} else {
					bufferSizeControl.setEnabled(false);
					flushIntervalControl.setEnabled(false);
				}
			} else {
				predicateControl.setEnabled(true);
				bufferingControl.setEnabled(false);
				bufferSizeControl.setEnabled(false);
				flushIntervalControl.setEnabled(false);
			}
		} 
		// hard coded for hawk destination
		if (controls.get(type + CommonIndexUtils.DOT +"MonitorType")!=null){
			Combo monitorType = (Combo)controls.get(type + CommonIndexUtils.DOT +"MonitorType");
			Control subscriptionMethodURI = (Control)controls.get(type + CommonIndexUtils.DOT +"SubscriptionMethodURI");
			Control timeInterval = (Control)controls.get(type + CommonIndexUtils.DOT +"TimeInterval");
			Control arguments = (Control)controls.get(type + CommonIndexUtils.DOT +"Arguments");
			if("MicroAgent Method Subscription".equals(monitorType.getItem(monitorType.getSelectionIndex()))){
				subscriptionMethodURI.setEnabled(true);
				timeInterval.setEnabled(true);
				arguments.setEnabled(true);
			}else{
				subscriptionMethodURI.setEnabled(false);
				timeInterval.setEnabled(false);
				arguments.setEnabled(false);
			}
		}
		// hard coded for AS destination
		if (controls.get(type + CommonIndexUtils.DOT +"ConsumptionMode")!=null){
			Combo consumptionMode = (Combo)controls.get(type + CommonIndexUtils.DOT +"ConsumptionMode");
			Control browserType = (Control)controls.get(type + CommonIndexUtils.DOT +"BrowserType");
			Control putEvent = (Control)controls.get(type + CommonIndexUtils.DOT +"PutEvent");
			Control takeEvent = (Control)controls.get(type + CommonIndexUtils.DOT +"TakeEvent");
			Control expireEvent = (Control)controls.get(type + CommonIndexUtils.DOT +"ExpireEvent");
			Control prefetch = (Control)controls.get(type + CommonIndexUtils.DOT +"Prefetch");
			if("EntryBrowser".equals(consumptionMode.getItem(consumptionMode.getSelectionIndex()))) {
				if (browserType != null) {
					browserType.setEnabled(true);
				}
				if (putEvent != null) {
					putEvent.setEnabled(false);
				}
				if (takeEvent != null) {
					takeEvent.setEnabled(false);
				}
				if (expireEvent != null) {
					expireEvent.setEnabled(false);
				}
				if (prefetch != null) {
				    prefetch.setEnabled(true);
				}
			} else if("EventListener".equals(consumptionMode.getItem(consumptionMode.getSelectionIndex()))) {
				if (browserType != null) {
					browserType.setEnabled(false);
				}
				if (putEvent != null) {
					putEvent.setEnabled(true);
				}
				if (takeEvent != null) {
					takeEvent.setEnabled(true);
				}
				if (expireEvent != null) {
					expireEvent.setEnabled(true);
				}
                if (prefetch != null) {
                    prefetch.setEnabled(false);
                }
			} else if("Router".equals(consumptionMode.getItem(consumptionMode.getSelectionIndex()))) {
				if (browserType != null) {
					browserType.setEnabled(false);
				}
				if (putEvent != null) {
					putEvent.setEnabled(false);
				}
				if (takeEvent != null) {
					takeEvent.setEnabled(false);
				}
				if (expireEvent != null) {
					expireEvent.setEnabled(false);
				}
                if (prefetch != null) {
                    prefetch.setEnabled(false);
                }
			}
		}
		
//		for(Object propConfig:instance.getProperties()){
//			PropertyInstance propInstance = (PropertyInstance)propConfig;
//			setIntialDataForRenderedComponents((Control)controls.get(propInstance.getDefinitionName()), propInstance.getValue(), null, null,null,null);
//		}
		ChannelFormEditor editor = (ChannelFormEditor)formViewer.getEditor();
		editor.setDestinationReset(true);
		
		if (!editor.isEnabled()){
			readOnlyWidgets();
		}
	}
	
	private void createInstance() {
		instance = ModelFactory.eINSTANCE.createPropertyMap();
//		instance = ElementFactory.eINSTANCE.createInstance();
		List<PropertyConfiguration> destinationProperties = delegate.getDestinationConfiguration(type);
		for(PropertyConfiguration propConfig:destinationProperties){
			addInstanceProperty(instance, propConfig);
		}
		SetCommand command=new SetCommand(((ChannelFormEditor)formViewer.getEditor()).getEditingDomain(),destination, 
				ChannelPackage.eINSTANCE.getDestination_Properties(), instance) ;			
		EditorUtils.executeCommand(formViewer.getEditor(), command);
	}
	
	/**
	 * @param propName
	 * @return
	 */
	private boolean isMandatory(String propName) {
		List<PropertyConfiguration> destinationProperties = delegate.getDestinationConfiguration(type);
		for(PropertyConfiguration propConfig:destinationProperties){
			if(propConfig.getPropertyName().equals(propName)){
				return propConfig.isMandatory();
			}
		}
		return false;
	}
	
	private boolean isGvToggle(String propName) {
		List<PropertyConfiguration> destinationProperties = delegate.getDestinationConfiguration(type);
		for(PropertyConfiguration propConfig:destinationProperties){
			if(propConfig.getPropertyName().equals(propName)){
				return propConfig.isGvToggle();
			}
		}
		return false;
	}
	
	/**
	 * @param name
	 * @return
	 */
	private PropertyConfiguration getPropertyConfiguration(String name) {
		List<PropertyConfiguration> destinationProperties = delegate.getDestinationConfiguration(type);
		for(PropertyConfiguration propConfig:destinationProperties){
			if(propConfig.getPropertyName().equalsIgnoreCase(name)){
				return propConfig;
			}
		}
		return null;
	}
	
	private void readOnlyWidgets() {
		for (Object object : controls.values()) {
			if (object instanceof Text) {
				((Text) object).setEditable(false);
			} else if (object instanceof GvField) {
				((GvField) object).setEnabled(false);
			} else if (object instanceof TableViewer) {
				//BE-24997: Disable table for kafka streams destination
				((TableViewer) object).getTable().setEnabled(false);
			} else if (object instanceof ToolItem) {
				//BE-24997: Disable toolbar items for kafka streams destination
				((ToolItem) object).setEnabled(false);
			} else {
				((Control) object).setEnabled(false);
			}
		}
	}
}