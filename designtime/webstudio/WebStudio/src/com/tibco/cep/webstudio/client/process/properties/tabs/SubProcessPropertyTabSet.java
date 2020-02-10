package com.tibco.cep.webstudio.client.process.properties.tabs;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.tab.events.TabDeselectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.tibco.cep.webstudio.client.process.properties.DocumentationProperty;
import com.tibco.cep.webstudio.client.process.properties.Property;
import com.tibco.cep.webstudio.client.process.properties.general.SubprocessGeneralProperty;

/**
 * This class is used to create the property tabs of process.
 * 
 * @author dijadhav
 * 
 */
public class SubProcessPropertyTabSet extends PropertyTabSet {
	public SubProcessPropertyTabSet(Property property) {
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
		if (property instanceof SubprocessGeneralProperty) {
			createGeneralPropertyForm((SubprocessGeneralProperty) property);
		} else if (property instanceof DocumentationProperty) {
			createDocumentationPropertyForm((DocumentationProperty) property);
		}
	}

	/**
	 * This method is used to create the general property form.
	 * 
	 * @param subprocessGeneralProperty
	 */

	private void createGeneralPropertyForm(
			SubprocessGeneralProperty subprocessGeneralProperty) {
		CheckboxItem triggerByEvent = new CheckboxItem();
		triggerByEvent.setTitle(processMessages
				.processPropertyTabGeneralTriggerByEvent());
		triggerByEvent.setCanEdit(true);
		triggerByEvent.setWidth(150);
		triggerByEvent.setVisible(true);
		triggerByEvent.setLabelAsTitle(true);
		triggerByEvent.setShowTitle(true);
		triggerByEvent.addChangedHandler(generalPropertyChangedHandler);

		CheckboxItem checkpointItem = new CheckboxItem();
		checkpointItem.setTitle(processMessages
				.processPropertyTabGeneralCheckPoint());
		checkpointItem.setCanEdit(true);
		checkpointItem.setWidth(150);
		checkpointItem.setVisible(true);
		checkpointItem.setLabelAsTitle(true);
		checkpointItem.setShowTitle(true);
		checkpointItem.addChangedHandler(generalPropertyChangedHandler);
		checkpointItem.setValue(subprocessGeneralProperty.isCheckPoint());
		triggerByEvent.setValue(subprocessGeneralProperty.isTriggerByEvent());

		DynamicForm generalForm = createGeneralPropertyForm();
		generalForm.setItems(getNameItem(subprocessGeneralProperty.getName()),
				getLabelItem(subprocessGeneralProperty.getLabel()),
				checkpointItem, triggerByEvent);
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
