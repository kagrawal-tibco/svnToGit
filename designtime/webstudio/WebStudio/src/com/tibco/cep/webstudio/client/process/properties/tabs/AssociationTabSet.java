package com.tibco.cep.webstudio.client.process.properties.tabs;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.tab.events.TabDeselectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.tibco.cep.webstudio.client.process.properties.DocumentationProperty;
import com.tibco.cep.webstudio.client.process.properties.Property;
import com.tibco.cep.webstudio.client.process.properties.general.AssociationGeneralProperty;

/**
 * This class is used to create the property tabs for association.
 * 
 * @author dijadhav
 * 
 */
public class AssociationTabSet extends PropertyTabSet {
	public AssociationTabSet(Property property) {
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

		if (property instanceof AssociationGeneralProperty) {
			createGeneralPropertyForm((AssociationGeneralProperty) property);
		} else if (property instanceof DocumentationProperty) {
			createDocumentationPropertyForm((DocumentationProperty) property);
		}
	}

	/**
	 * This method is used to populate the general property for association.
	 * 
	 * @param associationGeneralProperty
	 */
	private void createGeneralPropertyForm(
			AssociationGeneralProperty associationGeneralProperty) {
		DynamicForm generalForm = createGeneralPropertyForm();
		generalForm.setItems(getNameItem(associationGeneralProperty.getName()),
				getLabelItem(associationGeneralProperty.getLabel()));
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
