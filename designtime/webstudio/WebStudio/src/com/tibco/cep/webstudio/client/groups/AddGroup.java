/**
 * 
 */
package com.tibco.cep.webstudio.client.groups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedEvent;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.GroupDataItem;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.SerializeArtifact;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tibco.cep.webstudio.client.widgets.AbstractWebStudioDialog;

/**
 * Dialog for allowing the user to add new Groups.
 * 
 * @author Vikram Patil
 */
public class AddGroup extends AbstractWebStudioDialog implements HttpSuccessHandler, HttpFailureHandler {

	private GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private ListGrid artifactGrid, filterGrid;
	private TextItem groupName;
	private IButton addButton;
	private IButton deleteButton;

	/**
	 * Default constructor
	 */
	public AddGroup() {
		super();

		this.setDialogWidth(500);
		this.setDialogHeight(240);
		this.setDialogTitle(globalMsgBundle.groupAddNew_title());
		this.setHeaderIcon(Page.getAppImgDir() + "icons/16/customgroup.png");

		this.initialize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.webstudio.client.widgets.AbstractWebStudioDialog#getWidgetList
	 * ()
	 */
	@Override
	public List<Widget> getWidgetList() {
		List<Widget> widgetList = new ArrayList<Widget>();

		widgetList.add(this.createAddGroupForm());
		widgetList.add(this.createArtifactLabel());
		widgetList.add(this.createGroupArtifactSelection());

		return widgetList;
	}

	/**
	 * Creates form elements for the dialog
	 * 
	 * @return
	 */
	private Widget createAddGroupForm() {
		DynamicForm addGroupForm = new DynamicForm();
		addGroupForm.setWidth100();
		addGroupForm.setNumCols(2);
		addGroupForm.setColWidths("8%", "*");

		this.groupName = new TextItem();
		this.groupName.setTitle(globalMsgBundle.groupAdd_nameText());
		this.groupName.setWrapTitle(false);
		this.groupName.setWidth(200);
		this.groupName.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				if (groupName.getValueAsString() != null && !groupName.getValueAsString().trim().isEmpty()) {
					AddGroup.this.okButton.enable();
				} else {
					AddGroup.this.okButton.disable();
				}
			}
		});

		addGroupForm.setItems(this.groupName);

		return addGroupForm;
	}

	/**
	 * Creates a label for artifacs
	 * 
	 * @return
	 */
	private Widget createArtifactLabel() {
		HLayout titleContainer = new HLayout();
		titleContainer.setWidth100();
		titleContainer.setLayoutBottomMargin(-10);

		Label title = new Label("(" + globalMsgBundle.text_optional() + ") " + globalMsgBundle.groupAdd_selectArtifacts());
		title.setHeight(3);
		title.setWidth(200);
		titleContainer.addMember(title);

		return titleContainer;
	}

	/**
	 * Creates a artifact Selection widget for Add Group
	 * 
	 * @return
	 */
	private Layout createGroupArtifactSelection() {
		 
		VLayout artifactSelectionContainer = new VLayout(5);
		artifactSelectionContainer.setWidth100();
		artifactSelectionContainer.setLayoutMargin(10);
		artifactSelectionContainer.setHeight(224);
		artifactSelectionContainer.setBorder("1px solid grey");
		
		VLayout filterGridContainer = new VLayout(4);
		filterGridContainer.setWidth("40%");
		
		DynamicForm addFilterForm = new DynamicForm();
		addFilterForm.setAutoWidth();
		
		final SelectItem groupType = new SelectItem();
		groupType.setTitle(globalMsgBundle.groupAdd_typeText());
		groupType.setValueMap(this.getGroupTypes());
		groupType.setWrapTitle(false);
		groupType.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				if (groupType.getValueAsString() != null) {
					if (!groupType.getValueAsString().equals("empty")) {
						AddGroup.this.loadArtifacts(groupType.getValueAsString());
					}
				}
			}
		});
		
		addFilterForm.setItems(groupType);
		artifactSelectionContainer.addMember(addFilterForm);
		
		this.filterGrid = new ListGrid();
		this.filterGrid.setWidth100();
		this.filterGrid.setHeight(215);
		this.filterGrid.setShowAllRecords(true);
		this.filterGrid.setShowEmptyMessage(true);
		this.filterGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		this.filterGrid.setEmptyMessage(this.globalMsgBundle.message_noData());
		this.filterGrid.setShowHeaderContextMenu(false);
		this.filterGrid.setShowHeaderMenuButton(false);
		this.filterGrid.addSelectionUpdatedHandler(new SelectionUpdatedHandler() {
			public void onSelectionUpdated(SelectionUpdatedEvent event) {
				if (filterGrid.getSelectedRecords().length > 0) {
					addButton.enable();
				} else {
					addButton.disable();
				}
			}
		});
		
		this.filterGrid.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				return formatCell(value);
			}
		});

		ListGridField avaliableArtifacts = new ListGridField("artifact");
		avaliableArtifacts.setHidden(true);
		
		ListGridField avaliableArtifactsDisplayName = new ListGridField("displayArtifact", globalMsgBundle.groupAdd_availableArtifacts());
		avaliableArtifactsDisplayName.setWidth(325);
		avaliableArtifactsDisplayName.setType(ListGridFieldType.TEXT);
				
		this.filterGrid.setFields(avaliableArtifacts, avaliableArtifactsDisplayName);
		filterGridContainer.addMember(this.filterGrid);

		HLayout artifactListsHLayout = new HLayout(5);
		artifactListsHLayout.setWidth100();
		artifactListsHLayout.setHeight100();
		artifactListsHLayout.addMember(filterGridContainer);

		VLayout selectionButtonContainer = new VLayout(5);
		selectionButtonContainer.setHeight(200);
		selectionButtonContainer.setWidth("15%");
		selectionButtonContainer.setAlign(Alignment.CENTER);

		addButton = new IButton(globalMsgBundle.button_add());
		addButton.setTooltip(globalMsgBundle.button_add());
		addButton.setWidth(70);
		addButton.setShowRollOver(true);
		addButton.setShowDown(true);
		addButton.setDisabled(true);
		addButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				for (ListGridRecord record : AddGroup.this.filterGrid.getSelectedRecords()) {
					if (!exists(record)) {
						AddGroup.this.artifactGrid.addData(record);
					}
				}
			}
		});
		selectionButtonContainer.addMember(addButton);
		deleteButton = new IButton(globalMsgBundle.button_remove());
		deleteButton.setTooltip(globalMsgBundle.button_remove());
		deleteButton.setWidth(70);
		deleteButton.setShowRollOver(true);
		deleteButton.setShowDown(true);
		deleteButton.setDisabled(true);
		deleteButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				for (ListGridRecord record : AddGroup.this.artifactGrid.getSelectedRecords()) {
					if (exists(record)) {
						AddGroup.this.artifactGrid.deselectRecord(record);
						AddGroup.this.artifactGrid.removeData(record);
					}
				}
				deleteButton.disable();
				
			}
		});
		selectionButtonContainer.addMember(deleteButton);
		
		artifactListsHLayout.addMember(selectionButtonContainer);

		VLayout artifactGridContainer = new VLayout();
//		artifactGridContainer.setHeight100();
		artifactGridContainer.setWidth("40%");
		artifactGridContainer.setAlign(VerticalAlignment.TOP);
		
		this.artifactGrid = new ListGrid();
		this.artifactGrid.setWidth100();
		this.artifactGrid.setHeight(215);
		this.artifactGrid.setShowAllRecords(true);
		this.artifactGrid.setShowEmptyMessage(true);
		this.artifactGrid.setEmptyMessage(this.globalMsgBundle.message_noData());
		this.artifactGrid.setShowHeaderContextMenu(false);
		this.artifactGrid.setShowHeaderMenuButton(false);
		
		this.artifactGrid.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				return formatCell(value);
			}
		});
		this.artifactGrid.addSelectionUpdatedHandler(new SelectionUpdatedHandler() {
			public void onSelectionUpdated(SelectionUpdatedEvent event) {
				if (artifactGrid.getSelectedRecords().length > 0) {
					deleteButton.enable();
				} 
			}
		});

		ListGridField selectedArtifact = new ListGridField("artifact");
		selectedArtifact.setHidden(true);
		
		ListGridField selectedArtifactDisplayName = new ListGridField("displayArtifact", globalMsgBundle.groupAdd_selectedArtifacts());
		selectedArtifactDisplayName.setWidth(325);
		selectedArtifactDisplayName.setType(ListGridFieldType.TEXT);
		
		this.artifactGrid.setFields(selectedArtifact, selectedArtifactDisplayName);

		artifactGridContainer.addMember(this.artifactGrid);

		artifactListsHLayout.addMember(artifactGridContainer);
		artifactSelectionContainer.addMember(artifactListsHLayout);

		return artifactSelectionContainer;
	}
	
	/**
	 * Format cell to display artifact path
	 * 
	 * @param value
	 * @return
	 */
	private String formatCell(Object value) {
		if (value instanceof String) {
			String artifactPath = (String) value;

			String path = artifactPath.substring(artifactPath.indexOf("/"),
					artifactPath.indexOf("."));
			return path;
		}
		return value.toString();
	}

	/**
	 * Get a list of supported artifact types
	 * 
	 * @return
	 */
	private LinkedHashMap<String, String> getGroupTypes() {
		LinkedHashMap<String, String> groupTypes = new LinkedHashMap<String, String>();
		groupTypes.put("ruletemplateinstance", "Business Rules");
		groupTypes.put("rulefunctionimpl", "Decision Tables");

		return groupTypes;
	}

	/**
	 * Load Artifacts based on artifact type
	 * 
	 * @param groupType
	 */
	private void loadArtifacts(String groupType) {
		String[] groupTypes = new String[] { groupType };
		
		String[] matchingArtifacts = WebStudio.get().getWorkspacePage().getGroupContentsWindow().getArtifactsByType(groupTypes);
		
		List<ListGridRecord> artifacts = new ArrayList<ListGridRecord>();
		for (String artifact : matchingArtifacts) {
			ListGridRecord record = new ListGridRecord();
			record.setAttribute("artifact", artifact.replace("$", "/"));
			record.setAttribute("displayArtifact", artifact.replace("$", "/"));
			artifacts.add(record);
		}

		if (artifacts.size() > 0) {
			this.filterGrid.setData(artifacts.toArray(new ListGridRecord[artifacts.size()]));
		} else {
			this.filterGrid.selectAllRecords();
			this.filterGrid.removeSelectedData();
			this.filterGrid.deselectAllRecords();
			this.filterGrid.setEmptyMessage(globalMsgBundle.noMatchingArtifactsFound_Message());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.webstudio.client.widgets.AbstractWebStudioDialog#onAction()
	 */
	@Override
	public void onAction() {
		if (!groupExists(this.groupName.getValueAsString().trim())) {
			okButton.disable();
			ContentGroup newGroup = new ContentGroup(this.groupName.getValueAsString().trim());
			newGroup.setGroupIcon(Page.getAppImgDir() + "icons/48/customgroup.png");
			newGroup.setHeaderIcon(Page.getAppImgDir() + "icons/16/customgroup.png");
			newGroup.setSystem(false);

			ContentModelManager.getInstance().addGroup(newGroup);

			this.addGroup();
		} else {
			CustomSC.say(globalMsgBundle.group_alreadyExists_message(this.groupName.getValueAsString().trim()));
		}
	}
	
	/**
	 * Case Insensitive check to verify if duplicate group exists
	 * 
	 * @param groupId
	 * @return
	 */
	private boolean groupExists(String groupId) {
		List<ContentGroup> groupList = ContentModelManager.getInstance().getGroups();

		for (ContentGroup group : groupList) {
			if (group.getGroupId().toLowerCase().equals(groupId.toLowerCase())) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Add's a new Group
	 */
	@SuppressWarnings("rawtypes")
	private void addGroup() {
		HttpRequest request = new HttpRequest();
		Map<String, Object> requestParameters = new HashMap<String, Object>();

		requestParameters.put(RequestParameter.REQUEST_GROUP_NAME, this.groupName.getValueAsString());
		GroupDataItem groupDataItem = new GroupDataItem();

		if (this.artifactGrid.getRecords().length > 0) {
			List<String> artifactList = new ArrayList<String>();
			for (ListGridRecord record : this.artifactGrid.getRecords()) {
				artifactList.add(record.getAttribute("artifact"));
			}
			groupDataItem.setArtifactList(artifactList);
		}
		requestParameters.put(RequestParameter.REQUEST_ARTIFACT_DATA_ITEM, groupDataItem);

		@SuppressWarnings("unchecked")
		String xmlData = new SerializeArtifact().generateXML(ServerEndpoints.RMS_ADD_GROUP, requestParameters);

		request.clearParameters();
		request.setPostData(xmlData);
		request.setMethod(HttpMethod.POST);

		ArtifactUtil.addHandlers(this);
		request.submit(ServerEndpoints.RMS_ADD_GROUP.getURL("groups", this.groupName.getValueAsString()));
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_ADD_GROUP.getURL("groups", this.groupName.getValueAsString())) != -1) {
			ArtifactUtil.removeHandlers(this);
			AddGroup.this.destroy();
		}
	}

	@Override
	public void onFailure(HttpFailureEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_ADD_GROUP.getURL("groups", this.groupName.getValueAsString())) != -1) {
			Element docElement = event.getData();
			
			String errorMsg = "Error creating user group";
			String errorCode = docElement.getElementsByTagName("errorCode").item(0).getFirstChild().getNodeValue();
			if (errorCode.equals("ERR_1111")) {
				errorMsg = docElement.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
			}
			
			CustomSC.say(errorMsg);
			okButton.enable();
			
			ArtifactUtil.removeHandlers(this);
		}
	}
	
	/**
	 * Check if a selected records exists in the artifact list
	 * 
	 * @param selectedRecord
	 * @return
	 */
	private boolean exists(ListGridRecord selectedRecord) {
		for (ListGridRecord record : artifactGrid.getRecords()) {
			if (record.getAttributeAsString("artifact").equals(selectedRecord.getAttributeAsString("artifact"))) {
				return true;
			}
		}
		return false;
	}
}
