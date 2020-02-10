package com.tibco.cep.webstudio.client.process.properties.tabs;

import static com.tibco.cep.webstudio.client.process.ProcessConstants.drawingViewName;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.moduleName;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.projectID;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.EmbeddedPosition;
import com.smartgwt.client.types.RecordComponentPoolingMode;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedEvent;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.events.TabDeselectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.process.AbstractProcessEditor;
import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tibco.cep.webstudio.client.process.handler.BrowseButtonClickHandler;
import com.tibco.cep.webstudio.client.process.handler.ButtonClickHadler;
import com.tibco.cep.webstudio.client.process.properties.DocumentationProperty;
import com.tibco.cep.webstudio.client.process.properties.Property;
import com.tibco.cep.webstudio.client.process.properties.general.BusinessTaskGeneralProperty;
import com.tibco.cep.webstudio.client.process.properties.general.VRFImplementationURI;
import com.tibco.cep.webstudio.server.model.ProcessElementTypes;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.client.view.TSWebModelViewCoordinators;

/**
 * This class is used to create the property tabs of business rule task.
 * 
 * @author dijadhav
 * 
 */
public class BusinessTaskPropertyTabSet extends PropertyTabSet implements
		ChangedHandler {
	private String iconPath = Page.getAppImgDir();
	private ListGrid vrfImplementionURLs;
	private IButton vrfImplUpButton;
	private IButton vrfImplDownButton;

	public BusinessTaskPropertyTabSet(final Property property) {
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
		if (property instanceof BusinessTaskGeneralProperty) {
			createGeneralPropertyForm((BusinessTaskGeneralProperty) property);
		} else if (property instanceof DocumentationProperty) {
			createDocumentationPropertyForm((DocumentationProperty) property);
		}
	}

	/**
	 * This method is used to populate the general property for business rule..
	 * 
	 * @param associationGeneralProperty
	 */
	private void createGeneralPropertyForm(
			BusinessTaskGeneralProperty businessTaskGeneralProperty) {
		CheckboxItem checkpointItem = PropertyCommanFieldUtil
				.getCheckpointItem(businessTaskGeneralProperty.isCheckPoint(),
						processMessages.processPropertyTabGeneralCheckPoint(),
						this);

		TextItem resourceTextItem = PropertyCommanFieldUtil
				.getResourceTextItem(businessTaskGeneralProperty.getResource(),
						processMessages.processPropertyTabGeneralResource(),
						this, ProcessConstants.RESOURCE);

		HLayout mainHLayout = new HLayout(0);

		vrfImplUpButton = getButton();
		vrfImplUpButton.setIcon(iconPath + "arrow.up.png");
		vrfImplUpButton.setTop(120);
		vrfImplUpButton.setID("UP_BTN");

		vrfImplDownButton = getButton();
		vrfImplDownButton.setIcon(iconPath + "arrow.down.png");
		vrfImplDownButton.setTop(150);
		vrfImplDownButton.setID("DOWN_BTN");

		getVRFImplementationURIListGrid(businessTaskGeneralProperty);

		vrfImplUpButton.addClickHandler(new ButtonClickHadler(
				vrfImplementionURLs, vrfImplDownButton, vrfImplUpButton));

		vrfImplDownButton.addClickHandler(new ButtonClickHadler(
				vrfImplementionURLs, vrfImplDownButton, vrfImplUpButton));

		CanvasItem vrfImplCanvasItem = new CanvasItem();
		vrfImplCanvasItem.setShowTitle(false);
		vrfImplCanvasItem.setColSpan(3);
		vrfImplCanvasItem.setLeft(100);

		final HLayout listLayout = new HLayout();
		listLayout.addMember(vrfImplementionURLs);

		HLayout outerLayout = new HLayout(5);
		outerLayout.setAlign(Alignment.CENTER);
		vrfImplCanvasItem.setCanvas(listLayout);

		DynamicForm generalForm = createGeneralPropertyForm();
		generalForm.setItems(
				getNameItem(businessTaskGeneralProperty.getName()),
				getLabelItem(businessTaskGeneralProperty.getLabel()),
				checkpointItem, resourceTextItem, vrfImplCanvasItem);

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
		buttonLayout.addChild(vrfImplUpButton);
		buttonLayout.addChild(vrfImplDownButton);
		resourceButton.addClickHandler(new BrowseButtonClickHandler(
				"rulefunction", ProcessElementTypes.BusinessRuleTask.getName(),
				resourceTextItem, vrfImplementionURLs));
		
		SectionStack sectionStack = PropertyCommanFieldUtil.getHelpContainer(
				businessTaskGeneralProperty, processMessages);
		mainHLayout.setHeight(325);
		mainHLayout.setMembersMargin(20);
		mainHLayout.addMembers(generalForm, buttonLayout, sectionStack);
		getGeneraltab().setPane(mainHLayout);
	}

	/**
	 * 
	 * @param businessTaskGeneralProperty
	 * @return
	 */
	private void getVRFImplementationURIListGrid(
			BusinessTaskGeneralProperty businessTaskGeneralProperty) {
		vrfImplementionURLs = new VRFImplGrid();

		ListGridField checkBoxField = new ListGridField("checkBoxField",
				processMessages.processPropertyTabGeneralImplURI());
		vrfImplementionURLs.setFields(new ListGridField[] { checkBoxField });

		List<VRFImplementationURI> impelmentationURIList = businessTaskGeneralProperty
				.getImpelmentationURIList();
		if (null != impelmentationURIList && !impelmentationURIList.isEmpty()) {
			ListGridRecord[] records = new ListGridRecord[impelmentationURIList
					.size()];
			int index = 0;
			for (VRFImplementationURI vrfImplementationURI : impelmentationURIList) {
				if (null != vrfImplementationURI) {
					ListGridRecord listGridRecord = new ListGridRecord();
					listGridRecord.setAttribute("checked",
							vrfImplementationURI.isDeployed());
					listGridRecord.setAttribute("ruleImlURI",
							vrfImplementationURI.getUri());
					records[index] = listGridRecord;
					index++;
				}
			}
			vrfImplementionURLs.setRecords(records);
		}
	}

	/**
	 * @return
	 */
	private IButton getButton() {
		final IButton vrfImplUpButton = new IButton();
		vrfImplUpButton.setWidth("20x");
		vrfImplUpButton.setHeight("20px");
		vrfImplUpButton.setAlign(Alignment.LEFT);
		vrfImplUpButton.setDisabled(true);
		vrfImplUpButton.setLeft(10);
		return vrfImplUpButton;
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
	 * This class is used to handle when list grid selection changes.
	 * 
	 * @author dijadhav
	 * 
	 */
	final class ListGridSelectionUpdatedHandler implements
			SelectionUpdatedHandler {
		ListGridSelectionUpdatedHandler() {

		}

		@Override
		public void onSelectionUpdated(SelectionUpdatedEvent event) {

			Record[] records = vrfImplementionURLs.getRecords();
			if (null != records) {
				ListGridRecord selectedRecord = vrfImplementionURLs.getSelectedRecord();

				if (vrfImplementionURLs.getSelectedRecords().length > 1) {
					vrfImplUpButton.setDisabled(false);
					vrfImplDownButton.setDisabled(false);

				} else if (null != selectedRecord) {
					if (records.length == 1) {
						vrfImplUpButton.setDisabled(true);
						vrfImplDownButton.setDisabled(true);
					} else {
						int recordNum = vrfImplementionURLs
								.getRecordIndex(selectedRecord);
						if (0 == recordNum) {
							vrfImplUpButton.setDisabled(true);
							vrfImplDownButton.setDisabled(false);
						} else if (recordNum == (records.length - 1)) {
							vrfImplUpButton.setDisabled(false);
							vrfImplDownButton.setDisabled(true);
						} else {
							vrfImplUpButton.setDisabled(false);
							vrfImplDownButton.setDisabled(false);

						}
					}
				} else {
					vrfImplUpButton.setDisabled(true);
					vrfImplDownButton.setDisabled(true);
				}
			}
		}
	}

	/**
	 * This class represents the grids to show the vrf implementation of
	 * selected vrf in business task.
	 * 
	 * @author dijadhav
	 * 
	 */
	private class VRFImplGrid extends ListGrid {
		public VRFImplGrid() {

			setShowHeader(true);
			setShowEmptyMessage(false);
			setHeight(100);
			setWidth("75%");
			setShowAllRecords(true);
			setCellHeight(22);
			setShowSortArrow(SortArrow.CORNER);
			setShowHeaderContextMenu(false);
			setShowHeaderMenuButton(false);
			setSelectionType(SelectionStyle.SIMPLE);
			setCanResizeFields(false);
			setCanAutoFitFields(false);
			setCanDragResize(false);
			setShowRecordComponents(true);
			setShowRecordComponentsByCell(true);
			setRecordComponentPosition(EmbeddedPosition.WITHIN);
			setRecordComponentPoolingMode(RecordComponentPoolingMode.VIEWPORT);
			setShowHeaderSpanTitlesInSortEditor(false);
			setSelectionAppearance(SelectionAppearance.ROW_STYLE);
			setCanHover(true);
			setShowHover(false);
			addSelectionUpdatedHandler(new ListGridSelectionUpdatedHandler());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.smartgwt.client.widgets.grid.ListGrid#createRecordComponent(com
		 * .smartgwt.client.widgets.grid.ListGridRecord, java.lang.Integer)
		 */
		@Override
		protected Canvas createRecordComponent(final ListGridRecord record,
				Integer colNum) {
			String fieldName = this.getFieldName(colNum);
			if (fieldName.equals("checkBoxField")) {
				Canvas layout = createRecordComponent(record);
				return layout;
			}
			return super.createRecordComponent(record, colNum);
		}

		@Override
		public Canvas updateRecordComponent(ListGridRecord record,
				Integer colNum, Canvas component, boolean recordChanged) {
			String fieldName = this.getFieldName(colNum);
			if (fieldName.equals("checkBoxField")) {
				if (component instanceof DynamicForm) {
					DynamicForm form = (DynamicForm) component;
					FormItem[] formItems = form.getFields();
					if (null != formItems && formItems.length > 0) {
						CheckboxItem checkboxItem = (CheckboxItem) formItems[0];
						checkboxItem.setValue(record.getAttributeAsBoolean("checked"));
						checkboxItem.setTitle(record.getAttribute("ruleImlURI"));
						form.setFields(checkboxItem);
						return form;
					} else {
						component = createRecordComponent(record);
						return component;
					}
				} else {
					component = createRecordComponent(record);
					return component;
				}

			}
			return super.updateRecordComponent(record, colNum, component, recordChanged);
		}

		/**
		 * This method is used to create the record component.
		 * 
		 * @param record
		 * @return
		 */
		private Canvas createRecordComponent(final ListGridRecord record) {
			DynamicForm form = new DynamicForm();
			final CheckboxItem checkboxItem = createCheckBox(record);
			form.setFields(checkboxItem);
			form.setVisible(true);
			return form;
		}

		/**
		 * This method is used to create checkbox Item .
		 * 
		 * @param record
		 * 
		 * @return
		 */
		private CheckboxItem createCheckBox(final ListGridRecord record) {
			final CheckboxItem checkboxItem = new CheckboxItem("checkboxItem_"
					+ vrfImplementionURLs.getRecordIndex(record),
					record.getAttribute("ruleImlURI"));
			checkboxItem.setShowTitle(true);
			checkboxItem.setTitleAlign(Alignment.RIGHT);
			checkboxItem.setVisible(true);
			checkboxItem.setValue(record.getAttributeAsBoolean("checked"));

			checkboxItem.addChangedHandler(new ChangedHandler() {

				@Override
				public void onChanged(ChangedEvent event) {
					boolean checked = checkboxItem.getValueAsBoolean();
					record.setAttribute("checked", checked);
					invokeUpdateCommand();

				}
			});
			return checkboxItem;
		}

	}

	@Override
	public void onChanged(ChangedEvent event) {
		LinkedList<String> args = new LinkedList<String>();
		FormItem item = event.getItem();
		if (item instanceof TextItem) {
			TextItem textItem = (TextItem) item;
			String value = (String) textItem.getValue();
			if (null == value || value.trim().isEmpty()) {
				vrfImplementionURLs.setRecords(new ListGridRecord[] {});
				fireEvent(new SelectionUpdatedEvent(
						vrfImplementionURLs.getJsObj()));
			}
			args.add(value);
			args.add(ProcessConstants.RESOURCE);
		} else if (item instanceof CheckboxItem) {
			CheckboxItem checkboxItem = (CheckboxItem) item;
			args.add(checkboxItem.getValue().toString());
			args.add(ProcessConstants.CHECKPOINT);
		}

		AbstractProcessEditor processEditor = (AbstractProcessEditor) WebStudio
				.get().getEditorPanel().getSelectedTab();
		if (!processEditor.isDirty()) {
			processEditor.makeDirty();
		}
		TSCustomCommand populate = new TSCustomCommand(
				projectID,
				moduleName,
				processEditor.getModelId(),
				processEditor.getDrawingViewID(),
				drawingViewName,
				"com.tibco.cep.webstudio.server.command.GeneralPropertyUpdateCommandImpl",
				args);
		TSWebModelViewCoordinators.get(processEditor.getModelId())
				.invokeCommandAndUpdateAll(populate);

	}

	private void invokeUpdateCommand() {
		AbstractProcessEditor processEditor = (AbstractProcessEditor) WebStudio
				.get().getEditorPanel().getSelectedTab();
		if (!processEditor.isDirty()) {
			processEditor.makeDirty();
		}
		ListGridRecord[] listGridRecords = vrfImplementionURLs.getRecords();

		List<String> args = new LinkedList<String>();

		if (null != listGridRecords && listGridRecords.length > 0) {
			List<ListGridRecord> recordsList = Arrays
					.asList(listGridRecords);
			StringBuilder builder = new StringBuilder();

			for (ListGridRecord listGridRecord : recordsList) {
				int index = 0;
				StringBuilder recordBuilder = new StringBuilder();
				recordBuilder.append(listGridRecord.getAttributeAsBoolean("checked"));
				recordBuilder.append("#");
				recordBuilder.append(listGridRecord.getAttribute("ruleImlURI"));
				builder.append(recordBuilder.toString());
				if (index < (recordsList.size() - 1)) {
					builder.append(",");
				}
				index++;
			}
			args.add(builder.toString());

		} else {
			args.add("");
		}
		args.add(ProcessConstants.VRFIMPLURI);
		TSCustomCommand populate = new TSCustomCommand(
				projectID,
				moduleName,
				processEditor.getModelId(),
				"view#" + processEditor.getProcessName(),
				drawingViewName,
				"com.tibco.cep.webstudio.server.command.GeneralPropertyUpdateCommandImpl",
				args);
		TSWebModelViewCoordinators.get(processEditor.getModelId())
				.invokeCommandAndUpdateAll(populate);
	}

}
