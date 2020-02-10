package com.tibco.cep.webstudio.client.process.properties;

import com.google.gwt.i18n.client.LocaleInfo;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.layout.HLayout;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.ProcessMessages;
import com.tibco.cep.webstudio.client.process.AbstractProcessEditor;
import com.tibco.cep.webstudio.client.process.properties.tabs.AssociationTabSet;
import com.tibco.cep.webstudio.client.process.properties.tabs.BusinessTaskPropertyTabSet;
import com.tibco.cep.webstudio.client.process.properties.tabs.CallActivityPropertyTabSet;
import com.tibco.cep.webstudio.client.process.properties.tabs.EndEventPropertyTabSet;
import com.tibco.cep.webstudio.client.process.properties.tabs.ErrorEndEventPropertyTabSet;
import com.tibco.cep.webstudio.client.process.properties.tabs.ExclusiveGatewayPropertyTabSet;
import com.tibco.cep.webstudio.client.process.properties.tabs.InferenceTaskPropertyTabSet;
import com.tibco.cep.webstudio.client.process.properties.tabs.MessageEndEventPropertyTabSet;
import com.tibco.cep.webstudio.client.process.properties.tabs.MessageStartEventPropertyTabSet;
import com.tibco.cep.webstudio.client.process.properties.tabs.ParallelGatewayPropertyTabSet;
import com.tibco.cep.webstudio.client.process.properties.tabs.ProcessPropertyTabSet;
import com.tibco.cep.webstudio.client.process.properties.tabs.PropertyTabSet;
import com.tibco.cep.webstudio.client.process.properties.tabs.ReceiveMessageTaskPropertyTabSet;
import com.tibco.cep.webstudio.client.process.properties.tabs.RulefunctionTaskPropertyTabSet;
import com.tibco.cep.webstudio.client.process.properties.tabs.SendMessageTaskPropertyTabSet;
import com.tibco.cep.webstudio.client.process.properties.tabs.SequencePropertyTabSet;
import com.tibco.cep.webstudio.client.process.properties.tabs.SignalEndEventPropertyTabSet;
import com.tibco.cep.webstudio.client.process.properties.tabs.SignalStartPropertyTabSet;
import com.tibco.cep.webstudio.client.process.properties.tabs.StartEventPropertyTabSet;
import com.tibco.cep.webstudio.client.process.properties.tabs.TextAnnotationPropertyTabSet;
import com.tibco.cep.webstudio.client.process.properties.tabs.TimerStartEventPropertyTabSet;
import com.tibco.cep.webstudio.client.process.properties.tabs.WebServiceTaskPropertyTabSet;

/**
 * This class holds the properties of process and its components.
 * 
 * @author sasahoo
 * 
 */
public class ProcessPropertyTab extends HLayout {
	private PropertyTabSet propertyTabSet;
	public static final String COMMA = ",";
	protected ProcessMessages processMessages = (ProcessMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.PROCESS_MESSAGES);

	public ProcessPropertyTab() {
		setWidth100();
		setHeight100();
		setMembersMargin(5);
	}

	public void refresh(AbstractProcessEditor editor, final Property property) {
		populatePropery(property);
	}

	private void populatePropery(Property property) {
		if (property instanceof ProcessProperty) {
			propertyTabSet = new ProcessPropertyTabSet(property);
		} else if (property instanceof RuleFunctionTaskProperty) {
			propertyTabSet = new RulefunctionTaskPropertyTabSet(property);
		} else if (property instanceof BusinessTaskProperty) {
			propertyTabSet = new BusinessTaskPropertyTabSet(property);
		} else if (property instanceof SendTaskProperty) {
			propertyTabSet = new SendMessageTaskPropertyTabSet(property);
		} else if (property instanceof ReceiveTaskProperty) {
			propertyTabSet = new ReceiveMessageTaskPropertyTabSet(property);
		} else if (property instanceof WebserviceProperty) {
			propertyTabSet = new WebServiceTaskPropertyTabSet(property);
		} else if (property instanceof InferenceTaskProperty) {
			propertyTabSet = new InferenceTaskPropertyTabSet(property);
		} else if (property instanceof CallActivityProperty) {
			propertyTabSet = new CallActivityPropertyTabSet(property);
		} else if (property instanceof ExclusiveGatewayProperty) {
			propertyTabSet = new ExclusiveGatewayPropertyTabSet(property);
		} else if (property instanceof ParallelGatewayProperty) {
			propertyTabSet = new ParallelGatewayPropertyTabSet(property);
		} else if (property instanceof MessageStartEventProperty) {
			propertyTabSet = new MessageStartEventPropertyTabSet(property);
		} else if (property instanceof SignalStartEventProperty) {
			propertyTabSet = new SignalStartPropertyTabSet(property);
		} else if (property instanceof TimerStartEventProperty) {
			propertyTabSet = new TimerStartEventPropertyTabSet(property);
		} else if (property instanceof MessageEndEventProperty) {
			propertyTabSet = new MessageEndEventPropertyTabSet(property);
		} else if (property instanceof SignalEndEventProperty) {
			propertyTabSet = new SignalEndEventPropertyTabSet(property);
		} else if (property instanceof ErrorEndEventProperty) {
			propertyTabSet = new ErrorEndEventPropertyTabSet(property);
		} else if (property instanceof StartEventProperty) {
			propertyTabSet = new StartEventPropertyTabSet(property);
		} else if (property instanceof EndEventProperty) {
			propertyTabSet = new EndEventPropertyTabSet(property);
		} else if (property instanceof TextAnnotationProperty) {
			propertyTabSet = new TextAnnotationPropertyTabSet(property);
		} else if (property instanceof SequenceProperty) {
			propertyTabSet = new SequencePropertyTabSet(property);
		} else if (property instanceof AssociationProperty) {
			propertyTabSet = new AssociationTabSet(property);
		}else if(property instanceof DocumentationProperty){
			propertyTabSet.setProperty(property);
		}else if(property instanceof ProcessVariableProperty){
			propertyTabSet.setProperty(property);
		}
		else if(property instanceof LoopCharateristicsProperty){
			propertyTabSet.setProperty(property);
		}
		if(LocaleInfo.getCurrentLocale().isRTL()){
			propertyTabSet.setAlign(Alignment.RIGHT);
		}
		else{
			propertyTabSet.setAlign(Alignment.LEFT);
		}
		
		propertyTabSet.createTabSet();
		setMembers(propertyTabSet);	
	}

	/**
	 * @return the propertyTabSet
	 */
	public PropertyTabSet getPropertyTabSet() {
		return propertyTabSet;
	}

	/**
	 * @param propertyTabSet
	 *            the propertyTabSet to set
	 */
	public void setPropertyTabSet(PropertyTabSet propertyTabSet) {
		this.propertyTabSet = propertyTabSet;
	}

}
