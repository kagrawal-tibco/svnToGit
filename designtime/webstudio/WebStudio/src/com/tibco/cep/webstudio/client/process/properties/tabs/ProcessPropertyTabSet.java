package com.tibco.cep.webstudio.client.process.properties.tabs;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.EmbeddedPosition;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Positioning;
import com.smartgwt.client.types.RecordComponentPoolingMode;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellContextClickHandler;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.TabDeselectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.process.AbstractProcessEditor;
import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tibco.cep.webstudio.client.process.properties.DocumentationProperty;
import com.tibco.cep.webstudio.client.process.properties.ProcessVariable;
import com.tibco.cep.webstudio.client.process.properties.ProcessVariableProperty;
import com.tibco.cep.webstudio.client.process.properties.Property;
import com.tibco.cep.webstudio.client.process.properties.general.ProcessGeneralProperty;
import com.tibco.cep.webstudio.client.process.util.ProcessCommandUtil;
import com.tibco.cep.webstudio.client.process.widgets.ProcessVariableTypeDialog;

/**
 * This class is used to create the property tabs of process.
 * 
 * @author dijadhav
 * 
 */
public class ProcessPropertyTabSet extends PropertyTabSet {

	/**
	 * Process Variable Tab
	 */
	private Tab variableTab;
	private final IButton delButton = new IButton();

	/**
	 * Parameterized constructor used to set the property
	 * 
	 * @param property
	 */
	public ProcessPropertyTabSet(final Property property) {
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

		// Create tabs only if general tab is null.

		if (null == getGeneraltab()) {
			super.createTabSet();
			variableTab = new Tab(
					processMessages.processPropertyTabTitleVariables());
			variableTab.setAttribute("height", "19px");
			//addTab(variableTab);
		}

		if (property instanceof ProcessGeneralProperty) {
			createGeneralPropertyForm((ProcessGeneralProperty) property);
		} else if (property instanceof DocumentationProperty) {
			createDocumentationPropertyForm((DocumentationProperty) property);
		} else if (property instanceof ProcessVariableProperty) {
			createProcessVariablesForm((ProcessVariableProperty) property);
		}
	}

	/**
	 * This method is used to create the general property form.
	 * 
	 * @param processGeneralProperty
	 */
	private void createGeneralPropertyForm(
			ProcessGeneralProperty processGeneralProperty) {

		TextItem processAuthorTextBox = new TextItem(
				ProcessConstants.AUTHOR,
				processMessages.processPropertyTabGeneralAuthor());
		processAuthorTextBox.setWidth(400);
		processAuthorTextBox.setValue(processGeneralProperty.getAuthor());
		processAuthorTextBox.addChangedHandler(generalPropertyChangedHandler);

		TextItem processRevisionTextBox = new TextItem(
				ProcessConstants.REVISION,
				processMessages.processPropertyTabGeneralRevision());
		processRevisionTextBox.setValue(processGeneralProperty.getRevision());

		ComboBoxItem processTypeComboBox = new ComboBoxItem(ProcessConstants.PROCESS_TYPE,
				processMessages.processPropertyTabGeneralProcessType());
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String[] processTypes = processMessages
				.processPropertyTabGeneralProcessTypes().split(",");
		if (null != processTypes && processTypes.length > 0) {
			for (String processType : processTypes) {
				if (null != processType && !processType.trim().isEmpty()) {
					map.put(processType, processType);
				}
			}
		}
		processTypeComboBox.setValueMap(map);
		processTypeComboBox.setValue(processGeneralProperty.getProcessType());
		processTypeComboBox.addChangedHandler(generalPropertyChangedHandler);

		DynamicForm generalForm = createGeneralPropertyForm();
		generalForm.setItems(getNameItem(processGeneralProperty.getName()),
				getLabelItem(processGeneralProperty.getLabel()),
				processAuthorTextBox, processRevisionTextBox);
		getGeneraltab().setPane(generalForm);
	}

	@Override
	public void onTabDeselected(TabDeselectedEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(TabSelectedEvent event) {
		if (processMessages.processPropertyTabTitleVariables().equals(
				event.getTab().getTitle())) {
			this.setSelectedTab(2);
			if (null == this.getTab(2).getPane()) {
				fetchProperty("Variables");
			}

		} else {
			super.onTabSelected(event);
		}
	}

	/**
	 * This method is used to create the variables form for process.
	 * 
	 * @param property
	 * 
	 * @return
	 */

	private void createProcessVariablesForm(
			ProcessVariableProperty processVariableProperty) {
		DynamicForm variablesForm = new DynamicForm();
		variablesForm.setLeft("0px");
		variablesForm.setWidth(650);
		variablesForm.setHeight(200);
		variablesForm.setIsGroup(false);
		VLayout mainLayout = new VLayout(0);

		HLayout buttonLayout = new HLayout(10);

		IButton addButton = new IButton();
		addButton.setTitle("Add");
		addButton.setIcon(iconPath + "add.png");

		delButton.setTitle("Delete");
		delButton.setIcon(iconPath + "remove.png");
		delButton.setDisabled(true);
		buttonLayout.setWidth100();
		buttonLayout.setHeight("25px");
		buttonLayout.setLeft("0px");
		buttonLayout.setAlign(Alignment.LEFT);
		buttonLayout.addMembers(addButton, delButton);

		Map<String, String> iconMap = getTypeFieldValueIconMap();
		final ProcessVariableGrid listGrid = new ProcessVariableGrid();
		ListGridField nameListGridField = new ListGridField("name", "Name");
		nameListGridField.setCanEdit(true);
		ListGridField pathListGridField = new ListGridField("path", "Path");
		pathListGridField.setHidden(true);

		ListGridField typeListGridField = new ListGridField("type", "Type");
		typeListGridField.setDisplayValueFromRecord(true);
		typeListGridField.setDisplayField("path");

		typeListGridField.setValueIcons(iconMap);
		ListGridField multipleListGridField = new ListGridField("multiple",
				"Multiple");
		multipleListGridField.setType(ListGridFieldType.BOOLEAN);
		listGrid.setFields(new ListGridField[] { nameListGridField,
				typeListGridField, pathListGridField, multipleListGridField });
		ListGridRecord[] listGridRecords = new ListGridRecord[processVariableProperty
				.getVariables().size()];

		int index = 0;
		for (ProcessVariable variable : processVariableProperty.getVariables()) {
			if (null != variable) {
				ListGridRecord listGridRecord = new ListGridRecord();
				listGridRecord.setAttribute("id", variable.getId());
				listGridRecord.setAttribute("name", variable.getName());
				listGridRecord.setAttribute("type", variable.getType());
				if (!("ContainedConcept".equals(variable.getType()) || "ConceptReference"
						.equals(variable.getType()))) {
					listGridRecord.setAttribute("path", variable.getType());
				} else {
					listGridRecord.setAttribute("path", variable.getPath());
				}

				listGridRecord.setAttribute("multiple", variable.isMultiple());
				listGridRecords[index] = listGridRecord;
				index++;
			}
		}
		listGrid.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {
				delButton.setDisabled(false);
			}
		});
		listGrid.setRecords(listGridRecords);

		HLayout listGridLayout = new HLayout();
		listGridLayout.setWidth100();
		listGridLayout.setTop("0px");
		mainLayout.setHeight100();
		listGridLayout.addChild(listGrid);

		mainLayout.addMembers(buttonLayout, listGridLayout);
		mainLayout.setWidth100();
		mainLayout.setHeight100();

		addButton.addClickHandler(new AddButtonClickHandler(listGrid));
		delButton.addClickHandler(new DeleteButtonClickHandler(listGrid));
		if (null != variableTab) {
			variableTab.setPane(mainLayout);
		}

	}

	/**
	 * This method is used to invoke the process grid record update command.
	 * 
	 * @param listGridRecords
	 */
	public void invokeUpdateCommand(ListGridRecord[] listGridRecords) {

		StringBuilder builder = new StringBuilder();

		if (null != listGridRecords && listGridRecords.length > 0) {
			int index = 0;
			for (ListGridRecord listGridRecord : listGridRecords) {
				if (null != listGridRecord) {
					builder.append(listGridRecord.getAttribute("id"));
					builder.append("#");
					builder.append(listGridRecord.getAttribute("name"));
					builder.append("#");
					builder.append(listGridRecord.getAttribute("type"));
					builder.append("#");
					builder.append(listGridRecord.getAttribute("path"));
					builder.append("#");
					builder.append(listGridRecord.getAttribute("multiple"));

					if (index < (listGridRecords.length - 1)) {
						builder.append(",");
					}
					index++;
				}
			}
		}
		ProcessCommandUtil.updateProcessVariable(builder.toString());
	}

	/**
	 * This method is used to fetch the properties of process for given type.
	 */
	protected void fetchProperty(String propertyType) {
		AbstractProcessEditor processEditor = (AbstractProcessEditor) WebStudio
				.get().getEditorPanel().getSelectedTab();
		processEditor
				.fetchProperties(ProcessConstants.PROCESS_ID, propertyType);
	}

	/**
	 * This method is used to check whether the passed string is numeric or not.
	 * 
	 * @param numstr
	 * @return
	 */
	private boolean isNumeric(String numstr) {
		try {
			if (!numstr.trim().equalsIgnoreCase(""))
				Integer.parseInt(numstr);
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}

	/**
	 * This method used to get the value icon map.
	 * 
	 * @return Map of Value Icon
	 */
	private Map<String, String> getTypeFieldValueIconMap() {
		Map<String, String> iconMap = new LinkedHashMap<String, String>();
		iconMap.put("String", iconPath + "iconString16.gif");
		iconMap.put("Integer", iconPath + "iconInteger16.gif");
		iconMap.put("Long", iconPath + "iconLong16.gif");
		iconMap.put("Double", iconPath + "iconReal16.gif");
		iconMap.put("Boolean", iconPath + "iconBoolean16.gif");
		iconMap.put("ContainedConcept", iconPath + "iconConcept16.gif");
		iconMap.put("ConceptReference", iconPath + "iconConceptRef16.gif");
		iconMap.put("Date", iconPath + "iconDate16.gif");
		return iconMap;
	}

	/**
	 * This is delete button click handler.
	 * 
	 * @author dijadhav
	 * 
	 */
	private final class DeleteButtonClickHandler implements ClickHandler {
		private final ProcessVariableGrid listGrid;

		private DeleteButtonClickHandler(ProcessVariableGrid listGrid) {
			this.listGrid = listGrid;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.smartgwt.client.widgets.events.ClickHandler#onClick(com.smartgwt
		 * .client.widgets.events.ClickEvent)
		 */
		@Override
		public void onClick(ClickEvent event) {
			ListGridRecord selectedRecord = listGrid.getSelectedRecord();
			listGrid.getRecordList().remove(selectedRecord);
			invokeUpdateCommand(listGrid.getRecords());
		}
	}

	/**
	 * This is add button click handler.
	 * 
	 * @author dijadhav
	 * 
	 */
	private final class AddButtonClickHandler implements ClickHandler {
		private final ProcessVariableGrid listGrid;

		private AddButtonClickHandler(ProcessVariableGrid listGrid) {
			this.listGrid = listGrid;
		}

		@Override
		public void onClick(ClickEvent event) {
			AbstractProcessEditor processEditor = (AbstractProcessEditor) WebStudio
					.get().getEditorPanel().getSelectedTab();
			String projectName = processEditor.getProjectName();
			ListGridRecord[] listGridRecords = listGrid.getRecords();
			List<ListGridRecord> records = new LinkedList<ListGridRecord>();
			String entityName = projectName + "_property_";
			int nextIndex = getNextIndex(listGridRecords, records, entityName);
			ListGridRecord listGridRecord = new ListGridRecord();
			listGridRecord.setAttribute("id", System.currentTimeMillis());
			listGridRecord.setAttribute("name", projectName + "_property_"
					+ nextIndex);
			listGridRecord.setAttribute("type", "String");
			listGridRecord.setAttribute("multiple", false);
			records.add(listGridRecord);
			listGridRecords = new ListGridRecord[records.size()];
			int index = 0;
			for (ListGridRecord record : records) {
				if (null != record) {
					listGridRecords[index] = record;
					index++;
				}
			}
			listGrid.setRecords(listGridRecords);
			invokeUpdateCommand(listGridRecords);
		}

		/**
		 * This method is sued to get next index to add new variable.
		 * 
		 * @param listGridRecords
		 * @param records
		 * @param entityName
		 * @return
		 */
		private int getNextIndex(ListGridRecord[] listGridRecords,
				List<ListGridRecord> records, String entityName) {
			int nextIndex = 0;
			if (null != listGridRecords) {
				for (ListGridRecord listGridRecord : listGridRecords) {
					if (null != listGridRecord) {
						String name = listGridRecord.getAttribute("name");
						if (null != name && !name.trim().isEmpty()
								&& name.startsWith(entityName)) {
							String subname = name.replace(entityName, "")
									.trim();
							if (isNumeric(subname)) {
								int index = Integer.parseInt(subname);
								if (index > nextIndex) {
									nextIndex = index;
								}
								nextIndex++;
							}
						}
						records.add(listGridRecord);
					}
				}
			}
			return nextIndex;
		}
	}

	/**
	 * This class is used to create the list grid for process variables.
	 * 
	 * @author dijadhav
	 * 
	 */
	private class ProcessVariableGrid extends ListGrid implements
			CellContextClickHandler, EditCompleteHandler {
		public ProcessVariableGrid() {
			setWidth100();
			setHeight100();
			setShowRecordComponents(true);
			setShowRecordComponentsByCell(true);
			setRecordComponentPosition(EmbeddedPosition.WITHIN);
			setRecordComponentPoolingMode(RecordComponentPoolingMode.VIEWPORT);
			setCanSelectCells(false);
			setSelectionType(SelectionStyle.SINGLE);
			addCellContextClickHandler(this);
			addEditCompleteHandler(this);
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
			if (fieldName.equals("type")) {
				HLayout layout = createRecordComponent(record);
				return layout;
			}
			return super.createRecordComponent(record, colNum);
		}

		/**
		 * This method is used to create the record component.
		 * 
		 * @param record
		 * @return
		 */
		private HLayout createRecordComponent(final ListGridRecord record) {
			HLayout layout = new HLayout(0);
			layout.setAlign(Alignment.RIGHT);
			layout.setHeight(22);
			layout.setWidth100();

			final IButton browseResourceBtn = createBrowseButton(record);
			layout.addMember(browseResourceBtn);
			layout.addClickHandler(new RecordComponentClickHandler(
					browseResourceBtn, record));
			return layout;
		}

		/**
		 * This method is used to create browse button.
		 * 
		 * @param record
		 * 
		 * @return
		 */
		private IButton createBrowseButton(final ListGridRecord record) {
			final IButton browseResourceBtn = new IButton();
			browseResourceBtn.setTitle("");
			browseResourceBtn.setIcon(iconPath + "browse_file_system.gif");
			browseResourceBtn.setAlign(Alignment.CENTER);
			browseResourceBtn.setWidth(20);
			browseResourceBtn.setHeight(22);
			browseResourceBtn.setPosition(Positioning.ABSOLUTE);
			browseResourceBtn.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					new ProcessVariableTypeDialog(record,
							ProcessVariableGrid.this,ProcessPropertyTabSet.this).show();
				}
			});
			browseResourceBtn.setVisible(false);
			return browseResourceBtn;
		}

		/**
		 * This class is used to handle click event on record component
		 * 
		 * @author dijadhav
		 * 
		 */
		private final class RecordComponentClickHandler implements ClickHandler {
			private final IButton browseResourceBtn;
			private final ListGridRecord record;

			private RecordComponentClickHandler(IButton browseResourceBtn,
					ListGridRecord record) {
				this.browseResourceBtn = browseResourceBtn;
				this.record = record;
			}

			@Override
			public void onClick(ClickEvent event) {
				hideButton();
				fireEvent(new CellClickEvent(record.getJsObj()) {

					@Override
					public ListGridRecord getRecord() {
						return record;
					}

					@Override
					public int getRowNum() {
						return ProcessVariableGrid.this.getRecordIndex(record);
					}

					@Override
					public int getColNum() {
						return 1;
					}

				});
				browseResourceBtn.setVisible(true);
			}

			/**
			 * This method is used to hide the browse button from other record
			 * 
			 */
			private void hideButton() {
				ListGridRecord[] records = ProcessVariableGrid.this
						.getRecords();
				if (null != records && records.length > 0) {
					int index = 0;
					for (ListGridRecord listGridRecord : records) {
						if (null != listGridRecord) {
							Canvas canvas = ProcessVariableGrid.this
									.getRecordComponent(index, 1);
							if (null != canvas && canvas instanceof HLayout) {
								HLayout layout = (HLayout) canvas;
								Canvas[] members = layout.getMembers();
								if (null != members && members.length > 0) {
									for (Canvas member : members) {
										if (null != member
												&& member instanceof IButton) {
											IButton button = (IButton) member;
											button.setVisible(false);
										}
									}
								}

							}
							index++;
						}
					}
				}
			}
		}

		@Override
		public void onCellContextClick(CellContextClickEvent event) {
			Menu menu = new Menu();
			MenuItem hangupItem = new MenuItem("Rename..");
			hangupItem
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							int column = event.getColNum();
							if (1 == column) {
								ProcessVariableGrid.this.startEditing();
							}
						}
					});

			menu.addItem(hangupItem);
			this.setContextMenu(menu);
		}

		@Override
		public void onEditComplete(EditCompleteEvent event) {
			invokeUpdateCommand(this.getRecords());
		}
	}
}