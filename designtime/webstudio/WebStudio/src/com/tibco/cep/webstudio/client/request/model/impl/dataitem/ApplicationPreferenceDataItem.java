package com.tibco.cep.webstudio.client.request.model.impl.dataitem;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Text;
import com.tibco.cep.webstudio.client.model.ApplicationPreferences;
import com.tibco.cep.webstudio.client.model.OperatorPreferences;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.XMLRequestBuilderConstants;
import com.tibco.cep.webstudio.model.rule.instance.operators.IBuilderOperator;

/**
 * XML representation of Application Preferences
 * 
 * @author Aparajita Sharma
 */
public class ApplicationPreferenceDataItem implements IRequestDataItem {

	private ApplicationPreferences applicationPreferences;
	
	public ApplicationPreferences getApplicationPreferences() {
		return applicationPreferences;
	}

	public void setApplicationPreferences(
			ApplicationPreferences applicationPreferences) {
		this.applicationPreferences = applicationPreferences;
	}

	public ApplicationPreferenceDataItem(ApplicationPreferences applicationPreferences) {
		this.applicationPreferences = applicationPreferences;
	}

	public void serialize(Document rootDocument, Node rootNode) {
		Node appPreferenceItemElement = rootDocument
				.createElement(XMLRequestBuilderConstants.APPLICATION_PREFERENCE_ITEM_ELEMENT);
		rootNode.appendChild(appPreferenceItemElement);
		
		
		for(OperatorPreferences opPreferences : applicationPreferences.getOperatorPreferences()) {
			Node operatorPreference = rootDocument
					.createElement(XMLRequestBuilderConstants.APPLICATION_PREFERENCE_OPERATOR_PREFERNCE_ELEMENT);
			appPreferenceItemElement.appendChild(operatorPreference);
			
			Node fieldType = rootDocument
					.createElement(XMLRequestBuilderConstants.APPLICATION_PREFERENCE_FIELD_TYPE_ELEMENT);
			operatorPreference.appendChild(fieldType);

			Text fieldTypeText = rootDocument.createTextNode(String.valueOf(opPreferences
					.getFieldType()));
			fieldType.appendChild(fieldTypeText);
			
			for(IBuilderOperator filterOperator : opPreferences.getFilterOperators()) {
				Node filterOperators = rootDocument
						.createElement(XMLRequestBuilderConstants.APPLICATION_PREFERENCE_FILTER_OPERATORS_ELEMENT);
				operatorPreference.appendChild(filterOperators);

				Text filterOperatorText = rootDocument.createTextNode(filterOperator.getValue());
				filterOperators.appendChild(filterOperatorText);
			}
			
			for(IBuilderOperator commandOperator : opPreferences.getCommandOperators()) {
				Node commandOperators = rootDocument
						.createElement(XMLRequestBuilderConstants.APPLICATION_PREFERENCE_COMMAND_OPERATORS_ELEMENT);
				operatorPreference.appendChild(commandOperators);

				Text commandOperatorText = rootDocument.createTextNode(commandOperator.getValue());
				commandOperators.appendChild(commandOperatorText);
			}
			
		}
	}
}
