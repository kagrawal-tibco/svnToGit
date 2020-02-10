package com.tibco.cep.webstudio.client.process.properties.tabs;

import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.events.TabDeselectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tibco.cep.webstudio.client.process.handler.BrowseButtonClickHandler;
import com.tibco.cep.webstudio.client.process.properties.DocumentationProperty;
import com.tibco.cep.webstudio.client.process.properties.Property;
import com.tibco.cep.webstudio.client.process.properties.general.CallActivityGeneralProperty;

/**
 * This class is used to create the property tabs for call activity task.
 * 
 * @author dijadhav
 * 
 */
public class CallActivityPropertyTabSet extends PropertyTabSet {

	public CallActivityPropertyTabSet(final Property property) {
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
		if(null==getGeneraltab()){
			super.createTabSet();
		}
		if (property instanceof CallActivityGeneralProperty) {
			createGeneralPropertyForm((CallActivityGeneralProperty) property);
		} else if (property instanceof DocumentationProperty) {
			createDocumentationPropertyForm((DocumentationProperty) property);
		}
	}

	/**
	 * This method is used to create the general property form.
	 * 
	 * @param callActivityGeneralProperty
	 */
	private void createGeneralPropertyForm(
			CallActivityGeneralProperty callActivityGeneralProperty) {
		CheckboxItem checkpointItem = PropertyCommanFieldUtil
				.getCheckpointItem(callActivityGeneralProperty.isCheckPoint(),
						processMessages.processPropertyTabGeneralCheckPoint(),
						generalPropertyChangedHandler);
		
		TextItem resourceTextItem = PropertyCommanFieldUtil.getResourceTextItem(
				callActivityGeneralProperty.getResource(),
				processMessages.processPropertyTabGeneralResource(),
				generalPropertyChangedHandler, ProcessConstants.RESOURCE);
		HLayout mainHLayout = new HLayout(0);

		DynamicForm generalForm = createGeneralPropertyForm();
		generalForm.setItems(
				getNameItem(callActivityGeneralProperty.getName()),
				getLabelItem(callActivityGeneralProperty.getLabel()),
				checkpointItem, resourceTextItem);


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
		resourceButton.addClickHandler(new BrowseButtonClickHandler(
				"process", null, resourceTextItem, null));
		
		SectionStack sectionStack = PropertyCommanFieldUtil.getHelpContainer(
				callActivityGeneralProperty, processMessages);
		mainHLayout.setHeight(225);
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

}
