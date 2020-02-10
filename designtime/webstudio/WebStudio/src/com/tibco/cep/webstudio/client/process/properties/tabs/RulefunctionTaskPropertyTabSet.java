package com.tibco.cep.webstudio.client.process.properties.tabs;

import static com.tibco.cep.webstudio.client.process.ProcessConstants.drawingViewName;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.moduleName;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.projectID;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.TabDeselectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.process.AbstractProcessEditor;
import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tibco.cep.webstudio.client.process.handler.BrowseButtonClickHandler;
import com.tibco.cep.webstudio.client.process.properties.DocumentationProperty;
import com.tibco.cep.webstudio.client.process.properties.LoopCharateristicsProperty;
import com.tibco.cep.webstudio.client.process.properties.Property;
import com.tibco.cep.webstudio.client.process.properties.general.RuleFunctionTaskGeneralProperty;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.client.view.TSWebModelViewCoordinators;

/**
 * This class is used to create the property tabs of rule function task.
 * 
 * @author dijadhav
 * 
 */
public class RulefunctionTaskPropertyTabSet extends PropertyTabSet {
	private Tab loopCharacteristicsTab;
	private ComboBoxItem loopComboBoxItem;

	public RulefunctionTaskPropertyTabSet(final Property property) {
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
			loopCharacteristicsTab = new Tab("Loop Characteristics");
			//this.addTab(loopCharacteristicsTab);
		}

		if (getProperty() instanceof RuleFunctionTaskGeneralProperty) {
			createGeneralPropertyForm((RuleFunctionTaskGeneralProperty) property);
		} else if (getProperty() instanceof DocumentationProperty) {
			createDocumentationPropertyForm((DocumentationProperty) property);
		} else {
			createLoopCharacteristicPropertyForm((LoopCharateristicsProperty) property);
		}
	}

	/**
	 * This method is used to create the general property form.
	 * 
	 * @param ruleFunctionTaskGeneralProperty
	 */
	private void createGeneralPropertyForm(
			RuleFunctionTaskGeneralProperty ruleFunctionTaskGeneralProperty) {
		CheckboxItem checkpointItem = PropertyCommanFieldUtil
				.getCheckpointItem(
						ruleFunctionTaskGeneralProperty.isCheckPoint(),
						processMessages.processPropertyTabGeneralCheckPoint(),
						generalPropertyChangedHandler);

		TextItem resourceTextItem = PropertyCommanFieldUtil
				.getResourceTextItem(
						ruleFunctionTaskGeneralProperty.getResource(),
						processMessages.processPropertyTabGeneralResource(),
						generalPropertyChangedHandler,
						ProcessConstants.RESOURCE);

		DynamicForm generalForm = createGeneralPropertyForm();
		generalForm.setItems(
				getNameItem(ruleFunctionTaskGeneralProperty.getName()),
				getLabelItem(ruleFunctionTaskGeneralProperty.getLabel()),
				checkpointItem, resourceTextItem);

		IButton resourceButton = new IButton(
				processMessages.processPropertyTabButtonBrowse());
		resourceButton.setWidth("100px");
		resourceButton.setHeight("23px");
		resourceButton.setLeft(0);

		VLayout buttonLayout = new VLayout();
		buttonLayout.setAlign(VerticalAlignment.BOTTOM);
		buttonLayout.setMembersMargin(0);
		buttonLayout.setHeight("100px");
		buttonLayout.setWidth("100px");
		buttonLayout.addMember(resourceButton);
		resourceButton.addClickHandler(new BrowseButtonClickHandler(
				"rulefunction", null, resourceTextItem, null));
		
		SectionStack sectionStack = PropertyCommanFieldUtil.getHelpContainer(ruleFunctionTaskGeneralProperty, processMessages);
		HLayout mainHLayout = new HLayout(20);
		mainHLayout.setHeight(230);
		mainHLayout.addMembers(generalForm, buttonLayout,sectionStack);	
		getGeneraltab().setPane(mainHLayout);
	}

	
	@Override
	public void onTabDeselected(TabDeselectedEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(TabSelectedEvent event) {

		if ("Loop Characteristics".equals(event.getTab().getTitle())) {
			this.setSelectedTab(2);
			if (null == this.getTab(2).getPane()) {
				fetchProperty(ProcessConstants.LOOP_CHARATERISTICS_PROPERTY);
			}

		} else {
			super.onTabSelected(event);
		}
	}

	private void createLoopCharacteristicPropertyForm(
			LoopCharateristicsProperty property) {
		DynamicForm loopCharateristicForm = new DynamicForm();
		loopCharateristicForm.setHeight(50);
		loopCharateristicForm.setTitleAlign(Alignment.LEFT);
		final VLayout mainHLayout = new VLayout(0);

		loopComboBoxItem = new ComboBoxItem(ProcessConstants.LOOP, "Loop");
		LinkedHashMap<String, String> loopTypeMap = new LinkedHashMap<String, String>();
		loopTypeMap.put(ProcessConstants.NONE, "None");
		loopTypeMap.put(ProcessConstants.STANDARD, "Standard");
		loopTypeMap.put(ProcessConstants.MULTI_INSTANCE, "Multi-Instance");
		loopComboBoxItem.setValueMap(loopTypeMap);
		loopComboBoxItem.setValue("None");
		loopComboBoxItem.setAttribute("editorType", "SelectItem");
		loopComboBoxItem.setAlign(Alignment.LEFT);
		loopComboBoxItem.setTextAlign(Alignment.LEFT);

		final DynamicForm standardForm = new DynamicForm();
		
		final HLayout hLayout = new HLayout(10);
		
		final VLayout buttonLayout = new VLayout();
		
		loopComboBoxItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				hLayout.destroy();
				if (ProcessConstants.STANDARD.equals(event.getValue())
						|| ProcessConstants.MULTI_INSTANCE.equals(event
								.getValue())) {
					standardForm.setWidth("50%");
					
					
					hLayout.setAlign(VerticalAlignment.TOP);
					hLayout.setWidth("50%");
					hLayout.setIsGroup(true);
					hLayout.setPadding(5);
					
					buttonLayout.setVertical(true);
					buttonLayout.setAlign(Alignment.CENTER);
					buttonLayout.setAlign(VerticalAlignment.CENTER);
					buttonLayout.setPadding(3);
					buttonLayout.setMembersMargin(40);
					loopComboBoxItem.setValue(event.getValue());
					
					hLayout.setGroupTitle(event.getValue().toString());
					if (ProcessConstants.STANDARD.equals(event.getValue())) {

						TextAreaItem countItem = new TextAreaItem("Count");
						countItem.setWidth(400);
						countItem.setHeight(20);

						CheckboxItem checkboxItem = new CheckboxItem("test",
								"Test Before");
						checkboxItem.setLabelAsTitle(true);
						checkboxItem.setShowTitle(true);
						checkboxItem.setWidth(400);
						checkboxItem.setHeight(20);
						TextAreaItem conditionItem = new TextAreaItem(
								"Condition");
						conditionItem.setWidth(400);
						conditionItem.setHeight(20);

						standardForm.setItems(countItem, checkboxItem,
								conditionItem);

						IButton button1 = new IButton("Edit");
						button1.setWidth(40);
						button1.setHeight(23);
						button1.setDisabled(true);
						button1.setTop(30);
						IButton button2 = new IButton("Edit");
						button2.setWidth(40);
						button2.setHeight(23);
						button2.setDisabled(true);
						buttonLayout.addMembers(button1, button2);

					} else if (ProcessConstants.MULTI_INSTANCE.equals(event
							.getValue())) {

						TextAreaItem collectionItem = new TextAreaItem(
								"Collections");
						collectionItem.setWidth(400);
						collectionItem.setHeight(20);

						CheckboxItem checkboxItem = new CheckboxItem("test",
								"Test Before");
						checkboxItem.setLabelAsTitle(true);
						checkboxItem.setShowTitle(true);
						checkboxItem.setWidth(400);
						checkboxItem.setHeight(20);
						TextAreaItem conditionItem = new TextAreaItem(
								"Condition");
						conditionItem.setWidth(400);
						conditionItem.setHeight(20);

						standardForm.setItems(collectionItem, checkboxItem,
								conditionItem);

						IButton button1 = new IButton("Edit");
						button1.setWidth(40);
						button1.setHeight(23);
						button1.setDisabled(true);
						button1.setTop(30);
						IButton button2 = new IButton("Edit");
						button2.setWidth(40);
						button2.setHeight(23);
						button2.setDisabled(true);
						buttonLayout.addMembers(button1, button2);

					}
					hLayout.addMembers(standardForm, buttonLayout);

					mainHLayout.addMember(hLayout);
				}
				invokeCommand(ProcessConstants.LOOP,
						(String) event.getValue());
			}

		});
		loopCharateristicForm.setItems(loopComboBoxItem);
		loopCharateristicForm.setWidth(40);
		loopCharateristicForm.setHeight(20);
		mainHLayout.setShowCustomScrollbars(true);
		mainHLayout.addMembers(loopCharateristicForm);
		loopCharacteristicsTab.setPane(mainHLayout);
	}

	protected void invokeCommand(String type, String value) {
		AbstractProcessEditor processEditor = (AbstractProcessEditor) WebStudio
				.get().getEditorPanel().getSelectedTab();
		if (!processEditor.isDirty()) {
			processEditor.makeDirty();
		}

		LinkedList<String> args = new LinkedList<String>();
		args.add(type);
		args.add(value);
		TSCustomCommand populate = new TSCustomCommand(
				projectID,
				moduleName,
				processEditor.getModelId(),
				"view#" + processEditor.getProcessName(),
				drawingViewName,
				"com.tibco.cep.webstudio.server.command.LoopCharacteristicsUpdateCommandImpl",
				args);
		TSWebModelViewCoordinators.get(processEditor.getModelId())
				.invokeCommandAndUpdateAll(populate);
	}
	

}
