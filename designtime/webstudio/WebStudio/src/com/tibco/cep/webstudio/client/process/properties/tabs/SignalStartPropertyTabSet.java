package com.tibco.cep.webstudio.client.process.properties.tabs;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Positioning;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
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
import com.tibco.cep.webstudio.client.process.properties.general.SignalStartEventGeneralProperty;

/**
 * This class is used to create the property tabs of signal start event.
 * 
 * @author dijadhav
 * 
 */
public class SignalStartPropertyTabSet extends PropertyTabSet {

	public SignalStartPropertyTabSet(Property property) {
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
		if (property instanceof SignalStartEventGeneralProperty) {
			createGeneralPropertyForm((SignalStartEventGeneralProperty) property);
		} else if (property instanceof DocumentationProperty) {
			createDocumentationPropertyForm((DocumentationProperty) property);
		}
	}

	/**
	 * This method is used to create the general property form.
	 * 
	 * @param signalStartEventGeneralProperty
	 */
	private void createGeneralPropertyForm(
			SignalStartEventGeneralProperty signalStartEventGeneralProperty) {

		TextItem resourceTextItem = PropertyCommanFieldUtil.getResourceTextItem(
				signalStartEventGeneralProperty.getResource(),
				processMessages.processPropertyTabGeneralResource(),
				generalPropertyChangedHandler, ProcessConstants.RESOURCE);
		HLayout mainHLayout = new HLayout(0);

		DynamicForm generalForm = createGeneralPropertyForm();
		generalForm.setItems(
				getNameItem(signalStartEventGeneralProperty.getName()),
				getLabelItem(signalStartEventGeneralProperty.getLabel()),
				resourceTextItem);

	
		IButton resourceButton = new IButton(
				processMessages.processPropertyTabButtonBrowse());
		resourceButton.setWidth("100px");
		resourceButton.setHeight("25xp");
		resourceButton.setLeft(0);

		VLayout buttonLayout = new VLayout();
		buttonLayout.setHeight("130px");
		buttonLayout.setWidth("100px");
		buttonLayout.setAlign(VerticalAlignment.BOTTOM);
		buttonLayout.setAlign(Alignment.CENTER);
		buttonLayout.setPosition(Positioning.ABSOLUTE);
		buttonLayout.setMembersMargin(0);
		buttonLayout.addMember(resourceButton);
		resourceButton.addClickHandler(new BrowseButtonClickHandler("event",
				null, resourceTextItem, null));

		SectionStack sectionStack = PropertyCommanFieldUtil.getHelpContainer(
				signalStartEventGeneralProperty, processMessages);
		mainHLayout.setHeight(175);
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
