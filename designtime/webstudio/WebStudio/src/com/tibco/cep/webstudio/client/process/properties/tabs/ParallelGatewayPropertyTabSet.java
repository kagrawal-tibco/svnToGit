package com.tibco.cep.webstudio.client.process.properties.tabs;

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
import com.tibco.cep.webstudio.client.process.properties.general.ParallelGatewayGeneralProperty;

/**
 * This class is used to create the property tab set for parallel gateway.
 * 
 * @author dijadhav
 * 
 */
public class ParallelGatewayPropertyTabSet extends PropertyTabSet {

	public ParallelGatewayPropertyTabSet(Property property) {
		super(property);
		// TODO Auto-generated constructor stub
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
		if (property instanceof ParallelGatewayGeneralProperty) {
			createGeneralPropertyForm((ParallelGatewayGeneralProperty) property);
		} else if (property instanceof DocumentationProperty) {
			createDocumentationPropertyForm((DocumentationProperty) property);
		}
	}

	/**
	 * This method is used to create the general property form.
	 * 
	 * @param parallelGatewayGeneralProperty
	 */
	private void createGeneralPropertyForm(
			ParallelGatewayGeneralProperty parallelGatewayGeneralProperty) {
		String incomingSeq = parallelGatewayGeneralProperty.getIncomming();
		String outgoingSeq = parallelGatewayGeneralProperty.getOutgoing();
		String[] incomming = null;
		String[] outgoing = null;
		if (null != incomingSeq) {
			incomming = incomingSeq.split(",");
		}
		if (null != outgoingSeq) {
			outgoing = outgoingSeq.split(",");
		}

		TextItem mergeExpressionTextItem = PropertyCommanFieldUtil
				.getResourceTextItem(parallelGatewayGeneralProperty
						.getMergeExpression(), processMessages
						.processPropertyTabGeneralMergeExpression(),
						generalPropertyChangedHandler,
						ProcessConstants.MERGE_EXPRESSION);
		TextItem joinFunctionTextItem = PropertyCommanFieldUtil
				.getResourceTextItem(parallelGatewayGeneralProperty
						.getJoinfunction(), processMessages
						.processPropertyTabGeneralJoinFunction(),
						generalPropertyChangedHandler,
						ProcessConstants.JOIN_FUNCTION);
		TextItem forkFunctionTextItem = PropertyCommanFieldUtil
				.getResourceTextItem(parallelGatewayGeneralProperty
						.getForkFunction(), processMessages
						.processPropertyTabGeneralForkFunction(),
						generalPropertyChangedHandler,
						ProcessConstants.FORK_FUNCTION);
		IButton mergeExpressionButton = new IButton(
				processMessages.processPropertyTabButtonEdit());
		mergeExpressionButton.setWidth("100px");
		mergeExpressionButton.setHeight("23px");
		mergeExpressionButton.setLeft(0);

		IButton joinFunctionButton = new IButton(
				processMessages.processPropertyTabButtonEdit());
		joinFunctionButton.setWidth("100px");
		joinFunctionButton.setHeight("23px");
		joinFunctionButton.setLeft(0);

		IButton forkFunctionButton = new IButton(
				processMessages.processPropertyTabButtonEdit());
		forkFunctionButton.setWidth("100px");
		forkFunctionButton.setHeight("23px");
		forkFunctionButton.setLeft(0);
		mergeExpressionTextItem.setDisabled(true);
		mergeExpressionButton.setDisabled(true);
		if(null != incomming && incomming.length >=2){
			
			mergeExpressionButton.addClickHandler(new BrowseButtonClickHandler(
					"rulefunction", null, mergeExpressionTextItem, null));
			mergeExpressionTextItem.setValue(parallelGatewayGeneralProperty
					.getMergeExpression());
			
			joinFunctionTextItem.setDisabled(false);
			joinFunctionButton.setDisabled(false);
			joinFunctionTextItem.setValue(parallelGatewayGeneralProperty
					.getJoinfunction());			
			joinFunctionButton.addClickHandler(new BrowseButtonClickHandler(
					"rulefunction", null, joinFunctionTextItem, null));		
		
		}else{			
			joinFunctionTextItem.setDisabled(true);			
			joinFunctionButton.setDisabled(true);
		}
		
		if (null != outgoing && outgoing.length >=2) {
			forkFunctionButton.setDisabled(false);
			forkFunctionTextItem.setDisabled(false);
			forkFunctionButton.addClickHandler(new BrowseButtonClickHandler(
					"rulefunction", null, forkFunctionTextItem, null));
			forkFunctionTextItem.setValue(parallelGatewayGeneralProperty
					.getForkFunction());
		
		} else {
			forkFunctionTextItem.setDisabled(true);
			forkFunctionButton.setDisabled(true);
		}

		DynamicForm generalForm = createGeneralPropertyForm();
		generalForm.setItems(
				getNameItem(parallelGatewayGeneralProperty.getName()),
				getLabelItem(parallelGatewayGeneralProperty.getLabel()),
				mergeExpressionTextItem, joinFunctionTextItem,
				forkFunctionTextItem);

		VLayout buttonLayout = new VLayout();
		buttonLayout.setAlign(VerticalAlignment.BOTTOM);
		buttonLayout.setMembersMargin(2);
		buttonLayout.addMember(mergeExpressionButton);
		buttonLayout.addMember(joinFunctionButton);
		buttonLayout.addMember(forkFunctionButton);
		buttonLayout.setHeight("127px");
		buttonLayout.setPosition(Positioning.ABSOLUTE);
		buttonLayout.setTop(50);
		buttonLayout.setWidth("100px");
		HLayout mainHLayout = new HLayout();
		SectionStack sectionStack = PropertyCommanFieldUtil.getHelpContainer(
				parallelGatewayGeneralProperty, processMessages);
		mainHLayout.setHeight(280);
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
