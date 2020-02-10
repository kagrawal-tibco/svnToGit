package com.tibco.cep.webstudio.client.process.properties.tabs;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.tab.events.TabDeselectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.tibco.cep.webstudio.client.process.properties.DocumentationProperty;
import com.tibco.cep.webstudio.client.process.properties.Property;
import com.tibco.cep.webstudio.client.process.properties.general.StartEventGeneralProperty;

/**
 * This class is used to create the property tabs for start event.
 * 
 * @author dijadhav
 * 
 */
public class StartEventPropertyTabSet extends PropertyTabSet {

	public StartEventPropertyTabSet(Property property) {
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
		if (property instanceof StartEventGeneralProperty) {
			createGeneralPropertyForm((StartEventGeneralProperty) property);
		} else if (property instanceof DocumentationProperty) {
			createDocumentationPropertyForm((DocumentationProperty) property);
		}
		// TODO Add other tabs form
	}

	/**
	 * This method is used to populate the general property for association.
	 * 
	 * @param startEventGeneralProperty
	 */
	private void createGeneralPropertyForm(
			StartEventGeneralProperty startEventGeneralProperty) {
		DynamicForm generalForm = createGeneralPropertyForm();
		generalForm.setItems(getNameItem(startEventGeneralProperty.getName()),
				getLabelItem(startEventGeneralProperty.getLabel()));
		SectionStack sectionStack = PropertyCommanFieldUtil.getHelpContainer(
				startEventGeneralProperty, processMessages);
		HLayout mainHLayout = new HLayout(0);
		mainHLayout.setHeight(125);
		mainHLayout.setMembersMargin(20);
		mainHLayout.addMembers(generalForm, sectionStack);
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
}
