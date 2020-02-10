package com.tibco.cep.webstudio.client.process.properties.tabs;

import static com.tibco.cep.webstudio.client.process.ProcessConstants.drawingViewName;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.moduleName;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.projectID;

import java.util.LinkedList;
import java.util.List;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Positioning;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.events.TabDeselectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.process.AbstractProcessEditor;
import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tibco.cep.webstudio.client.process.handler.BrowseButtonClickHandler;
import com.tibco.cep.webstudio.client.process.properties.DocumentationProperty;
import com.tibco.cep.webstudio.client.process.properties.Property;
import com.tibco.cep.webstudio.client.process.properties.general.InferenceTaskGeneralProperty;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.client.view.TSWebModelViewCoordinators;

/**
 * This class is used to create the property tabs of inference task.
 * 
 * @author dijadhav
 * 
 */
public class InferenceTaskPropertyTabSet extends PropertyTabSet{

	public InferenceTaskPropertyTabSet(final Property property) {
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
		if (property instanceof InferenceTaskGeneralProperty) {
			createGeneralPropertyForm((InferenceTaskGeneralProperty) property);
		} else if (property instanceof DocumentationProperty) {
			createDocumentationPropertyForm((DocumentationProperty) property);
		}
	}

	/**
	 * This method is used to create the general property form.
	 * 
	 * @param inferenceTaskGeneralProperty
	 */
	private void createGeneralPropertyForm(
			InferenceTaskGeneralProperty inferenceTaskGeneralProperty) {

		CheckboxItem checkpointItem = PropertyCommanFieldUtil
				.getCheckpointItem(inferenceTaskGeneralProperty.isCheckPoint(),
						processMessages.processPropertyTabGeneralCheckPoint(),
						generalPropertyChangedHandler);

		HLayout outerLayout = new HLayout(5);
		outerLayout.setPosition(Positioning.ABSOLUTE);

		VLayout buttonVLayout = new VLayout(5);
		buttonVLayout.setHeight(100);
		buttonVLayout.setVertical(true);
		buttonVLayout.setLeft("650px");
		buttonVLayout.setAlign(VerticalAlignment.BOTTOM);
		buttonVLayout.setTop("150px");
		IButton resourceButton = new IButton(
				processMessages.processPropertyTabButtonBrowse());
		resourceButton.setSize("100px", "23xp");
		resourceButton.setValign(VerticalAlignment.CENTER);
		resourceButton.setPosition(Positioning.ABSOLUTE);
		final IButton ruleRemoveButton = new IButton(processMessages.processPropertyTabButtonRemove());
		ruleRemoveButton.setSize("100px", "23xp");
		ruleRemoveButton.setValign(VerticalAlignment.CENTER);
		ruleRemoveButton.setAlign(Alignment.CENTER);
		ruleRemoveButton.setDisabled(true);

		final RuleGrid inferenceTaskRulesListGrid = new RuleGrid();
		ListGridRecord[] records = new ListGridRecord[inferenceTaskGeneralProperty
				.getResources().size()];
		int index = 0;
		for (String rules : inferenceTaskGeneralProperty.getResources()) {
			ListGridRecord gridRecord = new ListGridRecord();
			gridRecord.setAttribute("rulePath", rules);
			records[index] = gridRecord;
			index++;
		}
		inferenceTaskRulesListGrid.setRecords(records);
		final ListGridField listGridField = new ListGridField("rulePath",
				"Rules");
		inferenceTaskRulesListGrid
				.setFields(new ListGridField[] { listGridField });

		inferenceTaskRulesListGrid
				.addRecordClickHandler(new RecordClickHandler() {

					@Override
					public void onRecordClick(RecordClickEvent event) {
						ruleRemoveButton.setDisabled(false);

					}
				});

		ruleRemoveButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord selectedRecord = inferenceTaskRulesListGrid
						.getSelectedRecord();
				if (null != selectedRecord) {
					inferenceTaskRulesListGrid.removeData(selectedRecord);
					invokeUpdateCommand(inferenceTaskRulesListGrid.getRecords());
				}
			}
		});

		CanvasItem inferenceTaskRulesCanvasItem = new CanvasItem();
		inferenceTaskRulesCanvasItem.setShowTitle(false);

		final HLayout listLayout = new HLayout();
		listLayout.addMember(inferenceTaskRulesListGrid);
		outerLayout.addMember(listLayout);

		resourceButton.addClickHandler(new BrowseButtonClickHandler("rule",
				inferenceTaskGeneralProperty.getType(), null,
				inferenceTaskRulesListGrid));
		resourceButton.setSize("100px", "23xp");
		buttonVLayout.setAlign(VerticalAlignment.BOTTOM);
		buttonVLayout.setAlign(Alignment.RIGHT);
		buttonVLayout.setHeight("140px");
		buttonVLayout.setWidth("100px");
		buttonVLayout.setLeft("20px");
		
		buttonVLayout.addMembers(resourceButton, ruleRemoveButton);

		outerLayout.addChild(listLayout);

		inferenceTaskRulesCanvasItem.setCanvas(outerLayout);
		inferenceTaskRulesCanvasItem.setAlign(Alignment.RIGHT);
		inferenceTaskRulesCanvasItem.setColSpan(2);

		HLayout mainHLayout = new HLayout();

		DynamicForm generalForm = createGeneralPropertyForm();
		generalForm.setItems(
				getNameItem(inferenceTaskGeneralProperty.getName()),
				getLabelItem(inferenceTaskGeneralProperty.getLabel()),
				checkpointItem, inferenceTaskRulesCanvasItem);

		SectionStack sectionStack = PropertyCommanFieldUtil.getHelpContainer(
				inferenceTaskGeneralProperty, processMessages);
		mainHLayout.setHeight(225);
		mainHLayout.setMembersMargin(20);
		mainHLayout.addMembers(generalForm, buttonVLayout, sectionStack);
		getGeneraltab().setPane(mainHLayout);
	}

	/**
	 * This method is used to invoke the command to update the resource path of
	 * inference task.
	 * 
	 * @param records
	 */
	protected void invokeUpdateCommand(ListGridRecord[] records) {
		if (null != records && records.length > 0) {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < records.length; i++) {
				builder.append(records[i].getAttribute("rulePath"));
				if (i < (records.length - 1)) {
					builder.append(",");
				}
			}

			AbstractProcessEditor processEditor = (AbstractProcessEditor) WebStudio
					.get().getEditorPanel().getSelectedTab();
			if (!processEditor.isDirty()) {
				processEditor.makeDirty();
			}

			List<String> args = new LinkedList<String>();

			args.add(builder.toString());
			args.add(ProcessConstants.RESOURCE);
			TSCustomCommand populate = new TSCustomCommand(
					projectID,
					moduleName,
					processEditor.getModelId(),
					"view#" + processEditor.getProcessName(),
					drawingViewName,
					"com.tibco.cep.webstudio.server.command.GeneralPropertyUpdateCommandImpl",
					args);
			TSWebModelViewCoordinators.get(processEditor.getModelId()).invokeCommandAndUpdateAll(
					populate);

		}

	}

	@Override
	public void onTabDeselected(TabDeselectedEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(TabSelectedEvent event) {
		super.onTabSelected(event);
	}

	/**
	 * This class represents the grids to show the resource of inference task.
	 * 
	 * @author dijadhav
	 * 
	 */
	private class RuleGrid extends ListGrid {
		public RuleGrid() {
			setShowHeader(false);
			setShowEmptyMessage(false);
			setHeight(100);
			setShowAllRecords(true);
			setCellHeight(22);
			setShowSortArrow(SortArrow.NONE);
			setShowHeaderContextMenu(false);
			setShowHeaderMenuButton(true);
			setSelectionType(SelectionStyle.SIMPLE);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.smartgwt.client.widgets.grid.ListGrid#setRecords(com.smartgwt
		 * .client.widgets.grid.ListGridRecord[])
		 */
		@Override
		public void setRecords(ListGridRecord[] records) {
			super.setRecords(records);
			InferenceTaskPropertyTabSet.this.invokeUpdateCommand(records);
		}
	}
}
