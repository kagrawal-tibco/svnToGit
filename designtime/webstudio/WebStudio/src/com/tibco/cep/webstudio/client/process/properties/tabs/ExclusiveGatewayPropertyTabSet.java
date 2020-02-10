package com.tibco.cep.webstudio.client.process.properties.tabs;

import static com.tibco.cep.webstudio.client.process.ProcessConstants.drawingViewName;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.moduleName;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.projectID;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
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
import com.tibco.cep.webstudio.client.process.handler.ButtonClickHadler;
import com.tibco.cep.webstudio.client.process.properties.DocumentationProperty;
import com.tibco.cep.webstudio.client.process.properties.Property;
import com.tibco.cep.webstudio.client.process.properties.general.ExclusiveGatewayGeneralProperty;
import com.tibco.cep.webstudio.server.model.ProcessElementTypes;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.client.view.TSWebModelViewCoordinators;

/**
 * This class is used to create the property tab set for parallel gateway.
 * 
 * @author dijadhav
 * 
 */
public class ExclusiveGatewayPropertyTabSet extends PropertyTabSet {

	private String iconPath = Page.getAppImgDir();
	private int listGridSize = 0;
	private ComboBoxItem defaultSeqComboItem;

	public ExclusiveGatewayPropertyTabSet(Property property) {
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
		if (property instanceof ExclusiveGatewayGeneralProperty) {
			createGeneralPropertyForm((ExclusiveGatewayGeneralProperty) property);
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
			ExclusiveGatewayGeneralProperty exclusiveGatewayGeneralProperty) {

		final IButton upButton = getButton();
		upButton.setIcon(iconPath + "arrow.up.png");
		upButton.setTop(120);
		upButton.setID("UP_BTN");

		final IButton downButton = getButton();
		downButton.setIcon(iconPath + "arrow.down.png");
		downButton.setTop(150);
		upButton.setID("DOWN_BTN");

		VLayout buttonLayout = new VLayout();
		buttonLayout.setAlign(VerticalAlignment.BOTTOM);
		buttonLayout.setMembersMargin(0);
		buttonLayout.setHeight("100px");
		buttonLayout.setWidth("25px");
		buttonLayout.addChild(upButton);
		buttonLayout.addChild(downButton);

		String outgoingSeq = exclusiveGatewayGeneralProperty.getOutgoing();
		String[] outgoings = null;

		if (null != outgoingSeq) {
			outgoings = outgoingSeq.split(",");
		}

		LinkedHashMap<String, String> outgoingMap = new LinkedHashMap<String, String>();
		LinkedHashMap<String, String> seqMap = new LinkedHashMap<String, String>();
		defaultSeqComboItem = new ComboBoxItem(ProcessConstants.DEFAULT_SEQUENCE,
				processMessages.processPropertyTabGeneralDefaultSequence());
		String defaultSequenceId = exclusiveGatewayGeneralProperty
				.getDefaultSequenceId();
		if (null != outgoings) {
			for (String outgoing : outgoings) {
				if (null != outgoing && !outgoing.equals(defaultSequenceId)) {
					outgoingMap.put(outgoing, outgoing);
				}
				seqMap.put(outgoing, outgoing);
			}
			defaultSeqComboItem.setValueMap(outgoings);
			listGridSize = outgoingMap.size();
		}

		defaultSeqComboItem.setShowAllOptions(true);
		defaultSeqComboItem.setValue(defaultSequenceId);
		ListGrid listGrid = new ListGrid();
		ListGridField outgoingField = new ListGridField("outgoing");
		outgoingField.setShowTitle(false);
		listGrid.setFields(new ListGridField[] { outgoingField });

		createListGridRecord(outgoings, defaultSequenceId, listGrid,false);
		defaultSeqComboItem.setWidth(190);
		defaultSeqComboItem.addChangeHandler(new DefaultSequenceChangeHandler(
				outgoings, listGrid));
		listGrid.addSelectionUpdatedHandler(new ListGridSelectionUpdatedHandler(
				upButton, downButton, listGrid));
		upButton.addClickHandler(new ButtonClickHadler(listGrid, downButton,
				upButton));
		downButton.addClickHandler(new ButtonClickHadler(listGrid, downButton,
				upButton));

		CanvasItem sequenceCanvasItem = new CanvasItem();
		sequenceCanvasItem.setShowTitle(false);
		sequenceCanvasItem.setColSpan(3);
		sequenceCanvasItem.setLeft(100);

		final HLayout listLayout = new HLayout();
		listLayout.addMember(listGrid);

		HLayout outerLayout = new HLayout(5);
		outerLayout.setAlign(Alignment.CENTER);
		sequenceCanvasItem.setCanvas(listLayout);

		DynamicForm generalForm = createGeneralPropertyForm();

		if (null == outgoings || outgoings.length==0) {
			generalForm.setItems(
					getNameItem(exclusiveGatewayGeneralProperty.getName()),
					getLabelItem(exclusiveGatewayGeneralProperty.getLabel()),
					defaultSeqComboItem);
		} else {
			generalForm.setItems(
					getNameItem(exclusiveGatewayGeneralProperty.getName()),
					getLabelItem(exclusiveGatewayGeneralProperty.getLabel()),
					defaultSeqComboItem, sequenceCanvasItem);
		}

		HLayout mainHLayout = new HLayout();
		SectionStack sectionStack = PropertyCommanFieldUtil.getHelpContainer(
				exclusiveGatewayGeneralProperty, processMessages);
		mainHLayout.setHeight(200);
		mainHLayout.setMembersMargin(20);
		if (null == outgoings || outgoings.length==0) {
			mainHLayout.addMembers(generalForm, sectionStack);
		}else{
			mainHLayout.addMembers(generalForm,buttonLayout, sectionStack);
		}
		getGeneraltab().setPane(mainHLayout);

	}

	/**
	 * @param outgoings
	 * @param defaultSequenceId
	 * @param listGrid
	 * @param isDefaultSequenceChanged 
	 */
	private void createListGridRecord(String[] outgoings,
			String defaultSequenceId, ListGrid listGrid, boolean isDefaultSequenceChanged) {
		if (null != outgoings && outgoings.length > 0) {
			ListGridRecord[] listGridRecords = new ListGridRecord[listGridSize];
			int index = 0;
			for (String outgoing : outgoings) {
				if (null != outgoing && !outgoing.equals(defaultSequenceId)) {
					ListGridRecord listGridRecord = new ListGridRecord();
					listGridRecord.setAttribute("outgoing", outgoing);
					listGridRecords[index] = listGridRecord;
					index++;
				}
			}
			listGrid.setRecords(listGridRecords);
			invokeCommand(outgoings, defaultSequenceId,isDefaultSequenceChanged);
		}
	}

	private void invokeCommand(String[] outgoings, String defaultSequenceId, boolean isDefaultSequenceChanged) {
		LinkedList<String> args = new LinkedList<String>();
		args.add(defaultSequenceId);
		StringBuilder outgoingSeqIds = new StringBuilder();
		if (null != outgoings && outgoings.length > 0) {
			int index = 0;
			for (String outgoing : outgoings) {
				outgoingSeqIds.append(outgoing);
				if (index < (outgoings.length - 1)) {
					outgoingSeqIds.append(",");
				}
				index++;
			}
		}
		args.add(ProcessElementTypes.ExclusiveGateway.getName());
		args.add(outgoingSeqIds.toString());

		AbstractProcessEditor processEditor = (AbstractProcessEditor) WebStudio
				.get().getEditorPanel().getSelectedTab();
		if (isDefaultSequenceChanged && !processEditor.isDirty()) {
			processEditor.makeDirty();
		}
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

	/**
	 * @return
	 */
	private IButton getButton() {
		final IButton button = new IButton();
		button.setWidth("20x");
		button.setHeight("20px");
		button.setAlign(Alignment.LEFT);
		button.setDisabled(true);
		button.setLeft(10);
		return button;
	}

	/**
	 * This class is used to handle when list grid selection changes.
	 * 
	 * @author dijadhav
	 * 
	 */
	final class ListGridSelectionUpdatedHandler implements
			SelectionUpdatedHandler {
		private ListGrid listgrid;
		private IButton upButton;
		private IButton downButton;

		ListGridSelectionUpdatedHandler(IButton upButton, IButton downButton,
				ListGrid listgrid) {
			this.upButton = upButton;
			this.downButton = downButton;
			this.listgrid = listgrid;
		}

		@Override
		public void onSelectionUpdated(SelectionUpdatedEvent event) {
			Record[] records = listgrid.getRecords();
			if (null != records) {
				ListGridRecord selectedRecord = listgrid.getSelectedRecord();

				if (listgrid.getSelectedRecords().length > 1) {
					upButton.setDisabled(false);
					downButton.setDisabled(false);

				} else if (null != selectedRecord) {
					if(records.length ==1){
						upButton.setDisabled(true);
						downButton.setDisabled(true);
					} else {
						int recordNum = listgrid.getRecordIndex(selectedRecord);
						if (0 == recordNum) {
							upButton.setDisabled(true);
							downButton.setDisabled(false);
						} else if (recordNum <= (records.length - 1)) {
							upButton.setDisabled(false);
							downButton.setDisabled(true);
						}
					}
				} else {
					upButton.setDisabled(true);
					downButton.setDisabled(true);
				}
			}

		}
	}

	/**
	 * This is change handler for default sequence.
	 */

	private final class DefaultSequenceChangeHandler implements ChangeHandler {
		private String[] outgoings;
		private ListGrid listGrid;

		public DefaultSequenceChangeHandler(String[] outgoings,
				ListGrid listGrid) {
			this.outgoings = outgoings;
			this.listGrid = listGrid;

		}

		@Override
		public void onChange(ChangeEvent event) {
			ListGridRecord[] records = listGrid.getRecords();
			if (null != records && records.length > 0) {
				listGrid.removeData(listGrid.getRecord(0));
			}
			String oldValue=(String) event.getOldValue();
			String selected = (String) event.getValue();
			defaultSeqComboItem.setValue(selected);
			if(!oldValue.equalsIgnoreCase(selected))
			createListGridRecord(outgoings, selected, listGrid,true);
		}
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
