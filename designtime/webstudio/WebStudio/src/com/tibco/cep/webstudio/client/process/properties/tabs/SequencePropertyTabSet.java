package com.tibco.cep.webstudio.client.process.properties.tabs;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.tab.events.TabDeselectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.tibco.cep.webstudio.client.process.properties.DocumentationProperty;
import com.tibco.cep.webstudio.client.process.properties.Property;
import com.tibco.cep.webstudio.client.process.properties.general.SequenceGeneralProperty;

/**
 * This class is used to create the property tabs for sequence.
 * 
 * @author dijadhav
 * 
 */
public class SequencePropertyTabSet extends PropertyTabSet {

	public SequencePropertyTabSet(Property property) {
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
		if (property instanceof SequenceGeneralProperty) {
			createGeneralPropertyForm((SequenceGeneralProperty) property);
		} else if (property instanceof DocumentationProperty) {
			createDocumentationPropertyForm((DocumentationProperty) property);
		}
		// TODO Add other tabs form
	}

	/**
	 * This method is used to populate the general property form.
	 * 
	 * @param sequenceGeneralProperty
	 */
	private void createGeneralPropertyForm(
			SequenceGeneralProperty sequenceGeneralProperty) {
		DynamicForm generalForm = createGeneralPropertyForm();
		generalForm.setItems(getNameItem(sequenceGeneralProperty.getName()),
				getLabelItem(sequenceGeneralProperty.getLabel()));
		getGeneraltab().setPane(generalForm);
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
}
