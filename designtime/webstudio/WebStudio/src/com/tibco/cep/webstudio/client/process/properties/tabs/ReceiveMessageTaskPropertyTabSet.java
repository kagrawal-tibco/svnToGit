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
import com.tibco.cep.webstudio.client.process.properties.general.ReceiveTaskGeneralProperty;

/**
 * This class is used to create the property tabs of receive message task.
 * 
 * @author dijadhav
 * 
 */
public class ReceiveMessageTaskPropertyTabSet extends PropertyTabSet {

	public ReceiveMessageTaskPropertyTabSet(Property property) {
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
		if (property instanceof ReceiveTaskGeneralProperty) {
			createGeneralPropertyForm((ReceiveTaskGeneralProperty) property);
		} else if (property instanceof DocumentationProperty) {
			createDocumentationPropertyForm((DocumentationProperty) property);
		}
	}

	/**
	 * This method is used to create the general property form.
	 * 
	 * @param receiveTaskGeneralProperty
	 */
	private void createGeneralPropertyForm(
			ReceiveTaskGeneralProperty receiveTaskGeneralProperty) {
		CheckboxItem checkpointItem = PropertyCommanFieldUtil
				.getCheckpointItem(receiveTaskGeneralProperty.isCheckPoint(),
						processMessages.processPropertyTabGeneralCheckPoint(),
						generalPropertyChangedHandler);
		TextItem resourceTextItem = PropertyCommanFieldUtil.getResourceTextItem(
				receiveTaskGeneralProperty.getResource(),
				processMessages.processPropertyTabGeneralResource(),
				generalPropertyChangedHandler, ProcessConstants.RESOURCE);
		TextItem keyExpressionTexItem = PropertyCommanFieldUtil.getResourceTextItem(
				receiveTaskGeneralProperty.getKeyExpression(),
				processMessages.processPropertyTabGeneralKeyExpression(),
				generalPropertyChangedHandler, ProcessConstants.KEY_EXPRESSION);
		keyExpressionTexItem.setDisabled(true);
		HLayout mainHLayout = new HLayout();

		DynamicForm generalForm = createGeneralPropertyForm();
		generalForm.setItems(getNameItem(receiveTaskGeneralProperty.getName()),
				getLabelItem(receiveTaskGeneralProperty.getLabel()),
				checkpointItem, resourceTextItem,keyExpressionTexItem);


		IButton resourceButton = new IButton(
				processMessages.processPropertyTabButtonBrowse());
		resourceButton.setWidth("100px");
		resourceButton.setHeight("23xp");
		resourceButton.setLeft(0);
		IButton editButton = new IButton(
				processMessages.processPropertyTabButtonEdit());
		editButton.setWidth("100px");
		editButton.setHeight("23xp");
		editButton.setLeft(0);
		editButton.setDisabled(true);
		
		VLayout buttonLayout = new VLayout();
		buttonLayout.setAlign(VerticalAlignment.BOTTOM);
		buttonLayout.setMembersMargin(3);
		buttonLayout.setHeight("127px");
		buttonLayout.setWidth("100px");
		buttonLayout.addMember(resourceButton);
		buttonLayout.addMember(editButton);
		resourceButton.addClickHandler(new BrowseButtonClickHandler(
				"rulefunction", null, resourceTextItem, null));

		SectionStack sectionStack = PropertyCommanFieldUtil.getHelpContainer(
				receiveTaskGeneralProperty, processMessages);
		mainHLayout.setHeight(325);
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
