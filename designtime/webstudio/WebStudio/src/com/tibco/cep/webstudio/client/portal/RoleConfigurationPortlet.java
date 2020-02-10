package com.tibco.cep.webstudio.client.portal;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.MaximizeClickEvent;
import com.smartgwt.client.widgets.events.MaximizeClickHandler;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.grid.events.RowEditorExitEvent;
import com.smartgwt.client.widgets.grid.events.RowEditorExitHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.tibco.cep.webstudio.client.PortalLayout;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.AclData;
import com.tibco.cep.webstudio.client.model.Entry;
import com.tibco.cep.webstudio.client.model.Permission;
import com.tibco.cep.webstudio.client.model.Resources;
import com.tibco.cep.webstudio.client.model.UserData;
import com.tibco.cep.webstudio.client.model.UserPermission;
import com.tibco.cep.webstudio.client.model.WebUser;
import com.tibco.cep.webstudio.client.preferences.RoleConfigurationHelper;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * Class for ACL Settings portlet
 * 
 * @author Rohini Jadhav
 */
public class RoleConfigurationPortlet implements HttpSuccessHandler, HttpFailureHandler {

	private static GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private static IButton applyButton;
	private static VerticalPanel firstCheckBoxGroup;
	private static VerticalPanel secondCheckBoxGroup;
	private ListGrid userGrid;
	private static AclData aclFileData = new AclData();
	private static UserData userFileData = WebStudio.get().getUserData();
	private static UserPermission userPermission = new UserPermission();
	private static RoleConfigurationPortlet instance;
	private static VerticalPanel resourcesLayout;
	private static Label selectedArrow;
	private static Label selectedFieldTypeLabel = null;
	private static String selectedFieldType = null;
	HashMap<String, ArrayList<String>> resourceRelatedPermissions = new HashMap<String, ArrayList<String>>();
	public ArrayList<Permission> permissionList = new ArrayList<Permission>();
	private ArrayList<String> projectList = new ArrayList<String>();
	public static String roleName;
	public static String[] resourceSelectedRecord = new String[10];
	public static String selectedrole;
	public String selectedRecord;
	protected boolean setFlag;
	protected Record selectedUserRecord;
	protected Boolean setRoleFlag = false;
	protected Record selectedRoleRecord;
	private String selectedProjectFieldType;
	private Label selectedProjectArrow;
	private Label selectedProjectFieldTypeLabel;
	private static ListGrid roleGrid;
	private static VerticalPanel projectListLayout;

	public Canvas getCanvas(){
    	return createConfigurationCanvas();
    }
	
	public static RoleConfigurationPortlet getInstance() {
		if (instance == null) {
			instance = new RoleConfigurationPortlet();
		}
		return instance;
	}

	public void getPermissionPreference(String selectedProject) {
		ArtifactUtil.addHandlers(this);
		HttpRequest request = new HttpRequest();
		request.setMethod(HttpMethod.GET);
		request.submit(ServerEndpoints.RMS_GET_ACL_DATA.getURL("projects", selectedProject));

	}

	public void getProjectList() {
		ArtifactUtil.addHandlers(this);
		HttpRequest requestProjectList = new HttpRequest();
		requestProjectList.setMethod(HttpMethod.GET);
		requestProjectList.submit(ServerEndpoints.RMS_GET_ALL_PROJECTS.getURL());
	}

	/**
	 * Method to create the portlet canvas.
	 * 
	 * @return
	 */
	private Canvas createConfigurationCanvas() {
		RoleConfigurationPortlet.getInstance().getProjectList();

		VLayout stack = new VLayout();
		stack.setWidth100();
		stack.setHeight100();
		stack.setStyleName("ws-app-pref-portlet");

		HLayout gridsLayout = new HLayout();
		gridsLayout.setStyleName("ws-app-role-config-outerLayout");
		gridsLayout.setShowHover(true);
		gridsLayout.setHeight("100%");
		gridsLayout.setOverflow(Overflow.HIDDEN);

		if (userFileData.getAuthType().equals("file")) {
			createRoleLayout(gridsLayout);
			createUserLayout(gridsLayout);

		} else {
			createRoleLayout(gridsLayout);
		}
		createManagePermissionLayout(gridsLayout);

		HLayout buttonContainer = new HLayout();
		buttonContainer.setStyleName("ws-app-pref-button");
		buttonContainer.setHeight("10%");
		buttonContainer.setAlign(Alignment.RIGHT);
		applyButton = new IButton(globalMsgBundle.button_apply());
		buttonContainer.addMember(applyButton);
		applyButton.disable();
		applyButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				WebStudio.get().setAclData(aclFileData);
				WebStudio.get().setUserData(userFileData);

				if (userFileData.getAuthType().equals("file"))
					RoleConfigurationHelper.getInstance().updateUsersData(userFileData);
				else
					RoleConfigurationHelper.getInstance().updateAclData(aclFileData);
			}
		});

		stack.addMember(gridsLayout);
		stack.addMember(buttonContainer);

		return stack;
	}

	/**
	 * Method to create Roles Grid Layout.
	 * 
	 * @param parent
	 * @return
	 */
	private void createUserLayout(HLayout parent) {
		VLayout stack = new VLayout();
		parent.addMember(stack);
		stack.setBorder("1px solid #ABABAB");
		stack.setStyleName("ws-role-config-user");
		
		Label fieldTypeLabel = new Label();
		fieldTypeLabel.setContents(globalMsgBundle.aclUser_grid_title());
		fieldTypeLabel.setAlign(Alignment.CENTER);
		fieldTypeLabel.setStyleName("ws-role-header");
		fieldTypeLabel.setBackgroundColor("#DEDEDE");
		fieldTypeLabel.setAutoHeight();
		fieldTypeLabel.setPadding(5);
		fieldTypeLabel.setWidth("195%");

		userGrid = new ListGrid();
		userGrid.setWidth("190%");
		userGrid.setAutoFitData(Autofit.VERTICAL);
		userGrid.setCanEdit(true);
		userGrid.setStyleName("ws-role-config");
		userGrid.setEditEvent(ListGridEditEvent.DOUBLECLICK);
		//userGrid.disable();
		userGrid.setAlternateRecordStyles(false);
		userGrid.setShowHeader(false);

		ListGridField imageField = new ListGridField("userimage", " ", 50);
		imageField.setAlign(Alignment.CENTER);
		imageField.setCanEdit(false);
		imageField.setType(ListGridFieldType.ICON);
		imageField.setIcon(Page.getAppImgDir() + "icons/16/person.png");
		ListGridField userNameField = new ListGridField("name", "User Name");
		ListGridField passwordField = new ListGridField("password", "PassWord");
		PasswordItem item = new PasswordItem();
		passwordField.setEditorType(item);
		passwordField.setCellFormatter(new CellFormatter() {
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return "******";
			}
		});

		userGrid.setFields(imageField, userNameField, passwordField);

		if (selectedrole != null) {
			for (int i = 0; i < WebStudio.get().getUserData().getUserList().size(); i++) {
				if (userFileData.getAuthType().equals("file")
						&& WebStudio.get().getUserData().getUserList().get(i).getRole().contains(",")) {
					String[] roles = WebStudio.get().getUserData().getUserList().get(i).getRole().split(",");
					for (int j = 0; j < roles.length; j++) {
						roles[j] = roles[j].trim();
						if (selectedrole.equals(roles[j])) {
							userGrid.addData(
									createUserRecord(WebStudio.get().getUserData().getUserList().get(i).getUserName(),
											WebStudio.get().getUserData().getUserList().get(i).getPassword()));
						}
					}
				} else {
					if (selectedrole.equals(WebStudio.get().getUserData().getUserList().get(i).getRole())) {
						userGrid.addData(
								createUserRecord(WebStudio.get().getUserData().getUserList().get(i).getUserName(),
										WebStudio.get().getUserData().getUserList().get(i).getPassword()));
					}
				}
			}
		}

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth(50);
		toolStrip.setTop(2);
		toolStrip.setHeight(24);
		toolStrip.setStyleName("ws-user-button-toolbar");

		Canvas canvas = new Canvas();
		ImgButton addButton = new ImgButton();
		addButton.setSize(16);
		addButton.setShowRollOver(false);
		addButton.setTooltip(globalMsgBundle.portlet_adduser_tooltip());
		addButton.setShowDown(false);
		addButton.setSrc("[SKIN]actions/add.png");
		addButton.setActionType(SelectionType.BUTTON);
		toolStrip.addMember(addButton);
		addButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setFlag = true;
				userGrid.startEditingNew();
				applyButton.enable();

			}

		});

		ImgButton removeButton = new ImgButton();
		removeButton.setSize(16);
		removeButton.setShowRollOver(false);
		removeButton.setShowDown(false);
		removeButton.setStyleName("ws-role-config-remove");
		removeButton.setTooltip(globalMsgBundle.portlet_removeuser_tooltip());
		removeButton.setSrc("[SKIN]actions/remove.png");
		toolStrip.addMember(removeButton);
		removeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setFlag = false;
				applyButton.enable();

				ArrayList<WebUser> userList = new ArrayList<WebUser>();
				userList = userFileData.getUserList();
				for (int i = 0; i < userList.size(); i++) {
					if (WebStudio.get().getUserData().getUserList().get(i).getUserName()
							.equals(userGrid.getSelectedRecord().getAttribute("name"))
							&& WebStudio.get().getUserData().getUserList().get(i).getRole().contains(",")) {
						String newRole = null;
						String[] roles = WebStudio.get().getUserData().getUserList().get(i).getRole().split(",");
						for (int j = 0; j < roles.length; j++) {
							roles[j] = roles[j].trim();
							if (!roles[j].equals(selectedrole) && j != 0) {
								newRole = newRole + "," + roles[j];
							} else if (j == 0)
								newRole = roles[j];

						}
						userList.get(i).setRole(newRole);
					} else if (WebStudio.get().getUserData().getUserList().get(i).getUserName()
							.equals(userGrid.getSelectedRecord().getAttribute("name"))) {
						userList.remove(i);
					}

				}
				userGrid.removeSelectedData();
				userFileData.setUserList(userList);
			}

		});

		userGrid.addRowEditorExitHandler(new RowEditorExitHandler() {
			@Override
			public void onRowEditorExit(final RowEditorExitEvent event) {
				if (setFlag) {
					Boolean isExist = false;
					ArrayList<WebUser> userList = new ArrayList<WebUser>();
					userList = userFileData.getUserList();
					for (int i = 0; i < userList.size(); i++) {
						if (userList.get(i).getUserName().equals((String) event.getNewValues().get("name"))) {
							isExist = true;
							String newRole = userList.get(i).getRole() + "," + selectedrole;
							userList.get(i).setRole(newRole);
						}
					}

					if (!isExist) {
						WebUser newUser = new WebUser();
						newUser.setPassword((String) event.getNewValues().get("password"));
						newUser.setUserName((String) event.getNewValues().get("name"));
						newUser.setRole(selectedrole);
						userList.add(newUser);
					}

					userFileData.setUserList(userList);

				} else {
					ArrayList<WebUser> userList = new ArrayList<WebUser>();
					userList = userFileData.getUserList();

					if (((String) event.getNewValues().get("name")) != null
							&& !((String) event.getNewValues().get("name"))
									.equals(selectedUserRecord.getAttribute("name"))) {
						for (int i = 0; i < userList.size(); i++) {
							if (userList.get(i).getUserName().equals(selectedUserRecord.getAttribute("name"))) {
								userList.get(i).setUserName((String) event.getNewValues().get("name"));
							}
						}
					}

					if (((String) event.getNewValues().get("password")) != null
							&& !((String) event.getNewValues().get("password"))
									.equals(selectedUserRecord.getAttribute("password"))) {
						for (int i = 0; i < userList.size(); i++) {
							if (userList.get(i).getPassword().equals(selectedUserRecord.getAttribute("password"))) {
								userList.get(i).setPassword((String) event.getNewValues().get("password"));
							}
						}
					}
					userFileData.setUserList(userList);
				}
                applyButton.enable();
			}
		});
		userGrid.addRecordClickHandler(new RecordClickHandler() {
			// @SuppressWarnings("deprecation")
			@Override
			public void onRecordClick(RecordClickEvent event) {
				selectedUserRecord = event.getRecord();
				//applyButton.enable();
			}
		});

		userGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				setFlag = false;
			}

		});

		canvas.addChild(fieldTypeLabel);
		canvas.addChild(toolStrip);

		stack.addMember(canvas);
		stack.addMember(userGrid);
	}

	/**
	 * Method to create permissions layout.
	 * 
	 * @param parent
	 * @return
	 */
	private void createManagePermissionLayout(HLayout parent) {

		HLayout stack = new HLayout();
		stack.setStyleName("ws-role-config-commandop");
		stack.setBorder("1px solid #ABABAB");
		stack.setHeight("100%");
		parent.addMember(stack);

		VLayout checkBoxLayout = new VLayout();
		checkBoxLayout.setBorder("1px solid #ABABAB");
		checkBoxLayout.setWidth("25%");
		checkBoxLayout.setLeft("42%");
		firstCheckBoxGroup = new VerticalPanel();
		firstCheckBoxGroup.setSpacing(3);
		secondCheckBoxGroup = new VerticalPanel();
		secondCheckBoxGroup.setSpacing(3);

		createCheckBoxPanel();

		HLayout hLayout = new HLayout();
		hLayout.setStyleName("ws-role-config-permissions");
		hLayout.addMember(firstCheckBoxGroup);
		hLayout.addMember(secondCheckBoxGroup);
		checkBoxLayout.addMember(hLayout);

		projectListLayout = new VerticalPanel();

		Label fieldTypeProjectLabel = new Label();
		fieldTypeProjectLabel.setContents(globalMsgBundle.portlet_projectlist_title());
		fieldTypeProjectLabel.setStyleName("ws-role-config-header-label");
		fieldTypeProjectLabel.setAlign(Alignment.LEFT);
		fieldTypeProjectLabel.setBackgroundColor("#DEDEDE");
		fieldTypeProjectLabel.setAutoHeight();
		fieldTypeProjectLabel.setPadding(10);
		fieldTypeProjectLabel.setWidth("90%");
		Canvas canvas = new Canvas();
		canvas.setWidth("30%");
		canvas.setHeight("5%");
		canvas.addChild(fieldTypeProjectLabel);
		projectListLayout.add(canvas);

		resourcesLayout = new VerticalPanel();

		Label fieldTypeLabel = new Label();
		fieldTypeLabel.setContents(globalMsgBundle.portlet_resources_title());
		fieldTypeLabel.setStyleName("ws-role-config-header-label");
		fieldTypeLabel.setAlign(Alignment.LEFT);
		fieldTypeLabel.setBackgroundColor("#DEDEDE");
		fieldTypeLabel.setAutoHeight();
		fieldTypeLabel.setPadding(10);
		fieldTypeLabel.setWidth("90%");
		Canvas resourceCanvas = new Canvas();
		resourceCanvas.setWidth("30%");
		resourceCanvas.setHeight("5%");
		resourceCanvas.addChild(fieldTypeLabel);
		resourcesLayout.add(resourceCanvas);

		String[] resourceList = { "PROJECT", "CONCEPT", "SCORECARD", "DOMAIN", "RULE", "EVENT", "CHANNEL",
				"RULETEMPLATE", "RULETEMPLATEVIEW", "RULETEMPLATEINSTANCE", "RULEFUNCTIONIMPL", "BEPROCESS", "PROPERTY",
				"RULEFUNCTION" };
		int resourceCount = 0;

		for (final String fieldType : resourceList) {
			final HLayout fieldLayout = new HLayout();
			fieldLayout.setStyleName("ws-role-config-hlayout");
			fieldLayout.setAutoWidth();
			fieldLayout.setAutoHeight();
			final Label fieldLabel = new Label();
			fieldLabel.setAutoHeight();
			fieldLabel.setContents(fieldType);
			fieldLabel.setPadding(6);
			fieldLabel.setAlign(Alignment.LEFT);

			final HLayout arrowLayout = new HLayout();
			arrowLayout.setAlign(Alignment.RIGHT);
			arrowLayout.setWidth("10%");
			final Label arrowLabel = new Label();
			arrowLabel.setAutoHeight();
			arrowLabel.setAutoWidth();
			arrowLabel.setContents("&#9654;");
			arrowLabel.hide();
			arrowLabel.setStyleName("ws-app-pref-arrow");
			arrowLayout.addMember(arrowLabel);

			fieldLayout.addMember(fieldLabel);
			fieldLayout.addMember(arrowLayout);

			if (resourceCount == 0) {
				selectedArrow = arrowLabel;
				selectedFieldType = fieldType;
				selectedArrow = arrowLabel;
			} else {
				fieldLabel.setStyleName("ws-app-pref-fieldtype");
			}
			fieldLabel.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (selectedFieldTypeLabel != null) {
						selectedFieldTypeLabel.setStyleName("ws-app-pref-fieldtype");
					}

					if (!fieldLabel.equals(selectedFieldTypeLabel)) {
						selectedArrow.hide();
					}
					arrowLabel.show();
					selectedArrow = arrowLabel;
					fieldLabel.setStyleName("ws-app-pref-fieldtype-onclick");
					selectedFieldTypeLabel = fieldLabel;
					selectedFieldType = fieldType;
					ArrayList<Entry> listOfEntries = new ArrayList<Entry>();
					listOfEntries = aclFileData.getEntries();
					if (listOfEntries != null && selectedrole != null) {
						for (int i = 0; i < listOfEntries.size(); i++) {
							if (selectedrole.equals(listOfEntries.get(i).roleName)) {
								permissionList = listOfEntries.get(i).permission;
							}
						}
						managePermissions(fieldType);
					}
				}
			});
			fieldLabel.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					if (!fieldLabel.equals(selectedFieldTypeLabel)) {
						fieldLabel.setStyleName("ws-app-pref-fieldtype-onHover");
					}
				}
			});

			fieldLabel.addMouseOutHandler(new MouseOutHandler() {

				@Override
				public void onMouseOut(MouseOutEvent event) {
					if (!fieldLabel.equals(selectedFieldTypeLabel)) {
						fieldLabel.setStyleName("ws-app-pref-fieldtype");
					}
				}
			});

			resourcesLayout.add(fieldLayout);
			resourceCount++;
		}

		stack.addMember(projectListLayout);
		stack.addMember(resourcesLayout);
		stack.addMember(hLayout);

	}

	/**
	 * Method to create Role Grid layout.
	 * 
	 * @param gridsLayout
	 * 
	 * @param parent
	 * @return
	 */
	private void createRoleLayout(HLayout parent) {
		VLayout stack = new VLayout();
		parent.addMember(stack);
		stack.setHeight("70%");

		Label fieldTypeLabel = new Label();
		fieldTypeLabel.setContents(globalMsgBundle.portlet_roles_grid_title());
		fieldTypeLabel.setAlign(Alignment.CENTER);
		fieldTypeLabel.setStyleName("ws-role-header");
		fieldTypeLabel.setBackgroundColor("#DEDEDE");
		fieldTypeLabel.setAutoHeight();
		fieldTypeLabel.setPadding(5);
		fieldTypeLabel.setWidth("108%");

		roleGrid = new ListGrid();
		roleGrid.setWidth("107%");
		roleGrid.setCanEdit(true);
		roleGrid.setAutoFitData(Autofit.VERTICAL);
		roleGrid.setStyleName("ws-role-config");
		roleGrid.setEditEvent(ListGridEditEvent.DOUBLECLICK);
		roleGrid.setShowHeader(false);
		roleGrid.setAlternateRecordStyles(false);

		ListGridField imageField = new ListGridField("userimage", " ", 20);
		imageField.setAlign(Alignment.LEFT);
		imageField.setCanEdit(false);
		imageField.setType(ListGridFieldType.ICON);
		imageField.setIcon(Page.getAppImgDir() + "icons/16/person.png");
		final ListGridField roleField = new ListGridField("role", "");

		for (int i = 0; i < WebStudio.get().getUserData().getUserList().size(); i++) {
			ListGridRecord[] allRecords = roleGrid.getRecords();
			ArrayList<String> roleList = new ArrayList<String>();
			for (int t = 0; t < allRecords.length; t++) {
				roleList.add(allRecords[t].getAttribute("role"));
			}
			if (!roleList.contains(WebStudio.get().getUserData().getUserList().get(i).getRole())) {
				if (userFileData.getAuthType().equals("file")
						&& WebStudio.get().getUserData().getUserList().get(i).getRole().contains(",")) {
					Boolean isExist = false;
					String[] roles = WebStudio.get().getUserData().getUserList().get(i).getRole().split(",");
					for (int j = 0; j < roles.length; j++) {
						roles[j] = roles[j].trim();
						for (int k = 0; k < roleGrid.getRecords().length; k++) {
							if (roleGrid.getRecord(k).getAttribute("role").equals(roles[j]))
								isExist = true;
						}

						if (!isExist)
							roleGrid.addData(createRoleRecord(roles[j]));
					}
				} else
					roleGrid.addData(createRoleRecord(WebStudio.get().getUserData().getUserList().get(i).getRole()));
			}
		}
		roleGrid.selectRecord(0);
		selectedrole = WebStudio.get().getUserData().getUserList().get(0).getRole();

		roleGrid.setFields(new ListGridField[] { imageField, roleField });
		roleGrid.setEditByCell(true);

		roleGrid.addRecordClickHandler(new RecordClickHandler() {
			@Override
			public void onRecordClick(RecordClickEvent event) {
				//applyButton.enable();
				if (userFileData.getAuthType().equals("file")) {
					userGrid.enable();
					userGrid.selectAllRecords();
					userGrid.removeSelectedData();
					selectedrole = event.getRecord().getAttribute("role");
					for (int i = 0; i < WebStudio.get().getUserData().getUserList().size(); i++) {
						if (userFileData.getAuthType().equals("file")
								&& WebStudio.get().getUserData().getUserList().get(i).getRole().contains(",")) {
							String[] roles = WebStudio.get().getUserData().getUserList().get(i).getRole().split(",");
							for (int j = 0; j < roles.length; j++) {
								roles[j] = roles[j].trim();
								if (event.getRecord().getAttribute("role").equals(roles[j])) {
									userGrid.addData(createUserRecord(
											WebStudio.get().getUserData().getUserList().get(i).getUserName(),
											WebStudio.get().getUserData().getUserList().get(i).getPassword()));
								}
							}
						} else {
							if (event.getRecord().getAttribute("role")
									.equals(WebStudio.get().getUserData().getUserList().get(i).getRole())) {
								userGrid.addData(createUserRecord(
										WebStudio.get().getUserData().getUserList().get(i).getUserName(),
										WebStudio.get().getUserData().getUserList().get(i).getPassword()));
							}
						}
					}
				}

				ArrayList<Entry> listOfEntries = new ArrayList<Entry>();
				listOfEntries = aclFileData.getEntries();
				selectedrole = event.getRecord().getAttribute("role");
				if (listOfEntries != null) {
					for (int i = 0; i < listOfEntries.size(); i++) {
						if (selectedrole.equals(listOfEntries.get(i).roleName)) {
							permissionList = listOfEntries.get(i).permission;
						}
					}
				}
				if (selectedFieldTypeLabel != null)
					managePermissions(selectedFieldTypeLabel.getTitle());

			}

		});

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth(50);
		toolStrip.setTop(2);
		toolStrip.setLeft("85%");
		toolStrip.setHeight(24);

		if (userFileData.getAuthType().equals("file")) {
			stack.setStyleName("ws-role-config-roles");
			toolStrip.setStyleName("ws-role-config-button-toolbar");
		} else {
			stack.setStyleName("ws-role-config-LDAP-roles");
			toolStrip.setStyleName("ws-ldap-button-toolbar");
		}

		Canvas canvas = new Canvas();
		ImgButton addButton = new ImgButton();
		addButton.setSize(16);
		addButton.setShowRollOver(false);
		addButton.setTooltip(globalMsgBundle.portlet_addrole_tooltip());
		addButton.setShowDown(false);
		addButton.setSrc("[SKIN]actions/add.png");
		addButton.setActionType(SelectionType.BUTTON);
		toolStrip.addMember(addButton);
		addButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				roleGrid.startEditingNew();
				applyButton.enable();
				setRoleFlag = true;
			}

		});

		ImgButton removeButton = new ImgButton();
		removeButton.setSize(16);
		removeButton.setShowRollOver(false);
		removeButton.setShowDown(false);
		removeButton.setTooltip(globalMsgBundle.portlet_removerole_tooltip());
		removeButton.setSrc("[SKIN]actions/remove.png");
		toolStrip.addMember(removeButton);
		removeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setRoleFlag = false;
				roleGrid.removeSelectedData();
				applyButton.enable();
				if (userFileData.getAuthType().equals("file")) {
					ArrayList<WebUser> userList = new ArrayList<WebUser>();
					userList = userFileData.getUserList();
					for (int j = 0; j < userList.size(); j++) {
						if (userList.get(j).getRole().equals(selectedrole)) {
							userList.remove(j);
						}
					}
					userGrid.selectAllRecords();
					userGrid.removeSelectedData();
					userFileData.setUserList(userList);
				}

				ArrayList<Entry> listOfEntries = new ArrayList<Entry>();
				listOfEntries = aclFileData.getEntries();
				for (int i = 0; i < listOfEntries.size(); i++) {
					if (listOfEntries.get(i).getRoleName().equals(selectedrole))
						listOfEntries.remove(i);
				}
				aclFileData.setEntries(listOfEntries);
				firstCheckBoxGroup.clear();
				secondCheckBoxGroup.clear();
				createCheckBoxPanel();
				if (selectedFieldTypeLabel != null) {
					selectedFieldTypeLabel.setStyleName("ws-app-pref-fieldtype");
					selectedFieldTypeLabel = null;
					selectedArrow.hide();
					selectedrole = null;
				}
			}

		});
		roleGrid.addRowEditorExitHandler(new RowEditorExitHandler() {
			@Override
			public void onRowEditorExit(RowEditorExitEvent event) {
				if (!setRoleFlag) {
					ArrayList<WebUser> userList = new ArrayList<WebUser>();
					userList = userFileData.getUserList();

					if ((selectedrole) != null && (selectedrole).equals(selectedRoleRecord.getAttribute("role"))) {
						ArrayList<Entry> entryList = aclFileData.getEntries();
						if (entryList != null) {
							for (int k = 0; k < entryList.size(); k++) {
								if (selectedRoleRecord.getAttribute("role").equals(entryList.get(k).roleName)) {
									entryList.get(k).setRoleName((String) event.getNewValues().get("role"));
								}
							}
						}
						userFileData.setUserList(userList);
					}
				} else if (setRoleFlag) {
					ArrayList<Entry> entryList = new ArrayList<Entry>();
					entryList = aclFileData.getEntries();

					if (entryList != null) {
						Entry newEntry = new Entry();
						newEntry.setRoleName((String) event.getNewValues().get("role"));
						newEntry.setPermission(new ArrayList<Permission>());
						entryList.add(newEntry);
						aclFileData.setEntries(entryList);
					}

				}
              applyButton.enable();
			}

		});

		roleGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				setRoleFlag = false;
			}

		});

		roleGrid.addRecordClickHandler(new RecordClickHandler() {
			// @SuppressWarnings("deprecation")
			@Override
			public void onRecordClick(RecordClickEvent event) {
				selectedRoleRecord = event.getRecord();
				//applyButton.enable();
			}
		});

		canvas.setWidth100();
		canvas.addChild(fieldTypeLabel);
		canvas.addChild(toolStrip);

		stack.addMember(canvas);
		stack.addMember(roleGrid);
	}

	private Record createRoleRecord(String role) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute("role", role);
		return record;

	}

	public ListGridRecord createUserRecord(String userName, String password) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute("name", userName);
		record.setAttribute("password", password);
		return record;
	}

	public ListGridRecord createResourceIDRecord(String resourceid) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute("resourceId", resourceid);
		return record;
	}

	private void createCheckBoxPanel() {
		final String[] checkBoxItems = { "Read", "Delete", "Approve",
				"Commit", "Add Impl", "Delete Impl", "CheckOut", "Generate Deploy", "Add Instance",
				"Delete Instance", "Manage Locks" };

		for (int j = 0; j < 7; j++) {
			final CheckBox checkBox = new CheckBox(checkBoxItems[j]);
			checkBox.setEnabled(false);
			firstCheckBoxGroup.add(checkBox);
		}

		for (int k = 7; k < 11; k++) {
			final CheckBox checkBox = new CheckBox(checkBoxItems[k]);
			checkBox.setEnabled(false);
			secondCheckBoxGroup.add(checkBox);
		}

	}

	public static void disableApplyButton() {
		applyButton.disable();
	}

	private void createResourceRelatedPermission() {
		ArrayList<String> projectPermissions = new ArrayList<String>();
		String[] permissionsOfProject = { "checkout", "gen_deploy", "commit", "approval", "manage_locks" };
		for (int i = 0; i < permissionsOfProject.length; i++) {
			projectPermissions.add(permissionsOfProject[i]);
		}
		resourceRelatedPermissions.put("PROJECT", projectPermissions);

		ArrayList<String> readPermissions = new ArrayList<String>();
		readPermissions.add("read");
		resourceRelatedPermissions.put("CHANNEL", readPermissions);
		resourceRelatedPermissions.put("CONCEPT", readPermissions);
		resourceRelatedPermissions.put("EVENT", readPermissions);
		resourceRelatedPermissions.put("RULE", readPermissions);
		resourceRelatedPermissions.put("WSDL", readPermissions);
		resourceRelatedPermissions.put("XSD", readPermissions);
		resourceRelatedPermissions.put("RULETEMPLATEINSTANCE", readPermissions);
		resourceRelatedPermissions.put("RULETEMPLATEVIEW", readPermissions);
		resourceRelatedPermissions.put("PROPERTY", readPermissions);
		resourceRelatedPermissions.put("SCORECARD", readPermissions);

		ArrayList<String> domainPermissions = new ArrayList<String>();
		domainPermissions.add("read");
		resourceRelatedPermissions.put("DOMAIN", domainPermissions);

		ArrayList<String> ruleFunctionPermissions = new ArrayList<String>();
		ruleFunctionPermissions.add("read");
		ruleFunctionPermissions.add("add_impl");
		ruleFunctionPermissions.add("del_impl");
		resourceRelatedPermissions.put("RULEFUNCTION", ruleFunctionPermissions);

		ArrayList<String> ruleFunctionImplPermissions = new ArrayList<String>();
		ruleFunctionImplPermissions.add("read");
		resourceRelatedPermissions.put("RULEFUNCTIONIMPL", ruleFunctionImplPermissions);

		ArrayList<String> ruleFunctionInstancePermissions = new ArrayList<String>();
		ruleFunctionInstancePermissions.add("read");
		ruleFunctionInstancePermissions.add("add_inst");
		ruleFunctionInstancePermissions.add("del_inst");
		resourceRelatedPermissions.put("RULETEMPLATE", ruleFunctionInstancePermissions);

		ArrayList<String> BEProcessPermissions = new ArrayList<String>();
		BEProcessPermissions.add("read");
		BEProcessPermissions.add("delete");
		resourceRelatedPermissions.put("BEPROCESS", BEProcessPermissions);

	}

	public void createPermission(CheckBox checkBox, String selectedRecord) {
		@SuppressWarnings("deprecation")
		Boolean isChecked = checkBox.isChecked();
		Boolean flag = false;
		for (int i = 0; i < permissionList.size(); i++) {
			if (selectedRecord.equals(permissionList.get(i).resourceRef)) {
				if (checkBox.getText().equals(permissionList.get(i).actionType)) {
					flag = true;
					if (isChecked) {
						permissionList.get(i).setActionValue("ALLOW");
					} else {
						permissionList.get(i).setActionValue("DENY");
					}
				}
			}

		}
		if (!flag) {
			Permission newPermission = new Permission();
			newPermission.setActionType(checkBox.getText());
			newPermission.setResourceRef(selectedRecord);
			if (isChecked) {
				newPermission.setActionValue("ALLOW");
			} else {
				newPermission.setActionValue("DENY");
			}
			permissionList.add(newPermission);
		}
		ArrayList<Entry> entryList = aclFileData.getEntries();

		if (entryList != null) {
			for (int i = 0; i < entryList.size(); i++) {
				if (roleGrid.getSelectedRecord().getAttribute("role").equals(entryList.get(i).roleName)) {

					entryList.get(i).setPermission(permissionList);
					aclFileData.setEntries(entryList);
				}
			}
		}
	}

	protected void managePermissions(String selectedResourceRecord) {
		final String[] checkBoxItems = { "Read", "Delete", "Approve",
				"Commit", "Add Impl", "Delete Impl", "CheckOut", "Generate Deploy", "Add Instance",
				"Delete Instance", "Manage Locks" };
		final String[] checkBoxStrings = { "read", "delete",
				"approval", "commit", "add_impl", "del_impl", "checkout", "gen_deploy", "add_inst", "del_inst",
				"manage_locks" };
		firstCheckBoxGroup.clear();
		secondCheckBoxGroup.clear();
		createResourceRelatedPermission();
		if (permissionList.size() == 0 && permissionList != null) {
			for (int i = 0; i < aclFileData.getEntries().size(); i++) {
				if (selectedrole.equals(aclFileData.getEntries().get(i).roleName)) {
					permissionList = aclFileData.getEntries().get(i).permission;
					roleName = aclFileData.getEntries().get(i).roleName;
				}
			}
		}

		for (int i = 0; i < aclFileData.getEntries().size(); i++) {
			if (selectedrole.equals(aclFileData.getEntries().get(i).roleName)) {
				resourceSelectedRecord[i] = selectedResourceRecord;
			}
		}

		ArrayList<String> resourcePemissions = resourceRelatedPermissions.get(selectedResourceRecord);
		for (int i = 0; i < aclFileData.getResources().size(); i++) {
			if (aclFileData.getResources().get(i).getResourceType().equals(selectedResourceRecord)) {
				selectedRecord = aclFileData.getResources().get(i).getResourceID();
				break;
			}
		}

		for (int j = 0; j < 7; j++) {
			Boolean isEnable = false;
			final CheckBox checkBox = new CheckBox(checkBoxItems[j]);
			for (int k = 0; k < resourcePemissions.size(); k++) {
				if (checkBoxStrings[j].equals(resourcePemissions.get(k))) {
					for (int i = 0; i < permissionList.size(); i++) {
						isEnable = true;
						if (selectedRecord.equals(permissionList.get(i).resourceRef)) {
							if (checkBoxStrings[j].equals(permissionList.get(i).actionType)
									&& permissionList.get(i).actionValue.equals("ALLOW")) {
								checkBox.setChecked(true);
							}
						}
					}
					if (permissionList.size() == 0) {
						isEnable = true;
					}

				}

			}

			if (isEnable != true)
				checkBox.setEnabled(false);

			checkBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					String selectedInstruction = null;
					for (int i = 0; i < checkBoxItems.length; i++) {
						if (checkBoxItems[i].equals(checkBox.getText())) {
							selectedInstruction = checkBoxItems[i];
							checkBox.setText(checkBoxStrings[i]);
						}
					}
					createPermission(checkBox, selectedRecord);
					checkBox.setText(selectedInstruction);
					applyButton.enable();
				}
			});
			firstCheckBoxGroup.add(checkBox);
		}

		for (int k = 7; k < checkBoxItems.length; k++) {
			Boolean isEnable = false;
			final CheckBox checkBox = new CheckBox(checkBoxItems[k]);
			for (int j = 0; j < resourcePemissions.size(); j++) {
				if (checkBoxStrings[k].equals(resourcePemissions.get(j))) {
					for (int i = 0; i < permissionList.size(); i++) {
						isEnable = true;
						if (selectedRecord.equals(permissionList.get(i).resourceRef)) {
							if (checkBoxStrings[k].equals(permissionList.get(i).actionType)
									&& permissionList.get(i).actionValue.equals("ALLOW")) {
								checkBox.setChecked(true);
							}
						}
					}
					if (permissionList.size() == 0) {
						isEnable = true;
					}

				}

			}
			if (isEnable != true)
				checkBox.setEnabled(false);

			checkBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					String selectedInstruction = null;
					for (int i = 0; i < checkBoxItems.length; i++) {
						if (checkBoxItems[i].equals(checkBox.getText())) {
							selectedInstruction = checkBoxItems[i];
							checkBox.setText(checkBoxStrings[i]);
						}
					}
					createPermission(checkBox, selectedRecord);
					checkBox.setText(selectedInstruction);
					applyButton.enable();
				}
			});
			secondCheckBoxGroup.add(checkBox);
		}
	}

	@Override
	public void onFailure(HttpFailureEvent event) {
		boolean validEvent = false;
		if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_MANAGED_PROJECTS.getURL()) != -1) {
			validEvent = true;
		} else if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_ACL_DATA.getURL()) != -1) {
			validEvent = true;
		}

		if (validEvent) {
			ArtifactUtil.removeHandlers(this);
		}
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		ArtifactUtil.removeHandlers(this);

		if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_ALL_PROJECTS.getURL()) != -1) {
			projectList.clear();
			Element docElement = event.getData();
			NodeList recordList = docElement.getElementsByTagName("record");
			for (int i = 0; i < recordList.getLength(); i++) {
				NodeList recordChildList = recordList.item(i).getChildNodes();
				for (int j = 0; j < recordChildList.getLength(); j++) {
					String childNodeName = recordChildList.item(j).getNodeName();
					if (childNodeName.equals("projectName")) {
						projectList.add(recordChildList.item(j).getFirstChild().getNodeValue());
					}
				}
			}

			int count = 0;
			for (final String fieldTypeProject : projectList) {
				final HLayout fieldProjectLayout = new HLayout();
				fieldProjectLayout.setStyleName("ws-role-config-hlayout");
				fieldProjectLayout.setAutoWidth();
				fieldProjectLayout.setAutoHeight();
				final Label fieldProjectLabel = new Label();
				fieldProjectLabel.setStyleName("ws-role-config-fieldProjectLabel");
				fieldProjectLabel.setAutoHeight();
				fieldProjectLabel.setContents(fieldTypeProject);
				fieldProjectLabel.setPadding(6);
				fieldProjectLabel.setAlign(Alignment.LEFT);

				final HLayout arrowProjectLayout = new HLayout();
				arrowProjectLayout.setAlign(Alignment.RIGHT);
				arrowProjectLayout.setAutoWidth();
				final Label arrowProjectLabel = new Label();
				arrowProjectLabel.setAutoHeight();
				arrowProjectLabel.setAutoWidth();
				arrowProjectLabel.setContents("&#9654;");
				arrowProjectLabel.hide();
				arrowProjectLabel.setStyleName("ws-app-pref-arrow");
				arrowProjectLabel.setAlign(Alignment.LEFT);
				arrowProjectLayout.addMember(arrowProjectLabel);

				fieldProjectLayout.addMember(fieldProjectLabel);
				fieldProjectLayout.addMember(arrowProjectLayout);

				if (count == 0) {
					fieldProjectLabel.setStyleName("ws-app-pref-fieldtype-onclick");
					selectedProjectFieldType = fieldTypeProject;
					aclFileData.setProjectName(fieldTypeProject);
					selectedProjectArrow = arrowProjectLabel;
					selectedProjectArrow.show();
					selectedProjectFieldTypeLabel = fieldProjectLabel;
					RoleConfigurationPortlet.getInstance().getPermissionPreference(fieldTypeProject);
				} else {
					fieldProjectLabel.setStyleName("ws-app-pref-fieldtype");
				}
				fieldProjectLabel.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						if (selectedProjectFieldTypeLabel != null) {
							selectedProjectFieldTypeLabel.setStyleName("ws-app-pref-fieldtype");
							//selectedFieldTypeLabel.setStyleName("ws-app-pref-fieldtype");
						}
						arrowProjectLabel.show();
						if (!fieldProjectLabel.equals(selectedProjectFieldTypeLabel)) {
							selectedProjectArrow.hide();
						}
						selectedProjectArrow = arrowProjectLabel;
						fieldProjectLabel.setStyleName("ws-app-pref-fieldtype-onclick");
						selectedProjectFieldTypeLabel = fieldProjectLabel;
						selectedProjectFieldType = fieldTypeProject;
						selectedArrow.hide();
						RoleConfigurationPortlet.getInstance().getPermissionPreference(fieldTypeProject);
						firstCheckBoxGroup.clear();
						secondCheckBoxGroup.clear();
						createCheckBoxPanel();
						//applyButton.enable();
						aclFileData.setProjectName(fieldTypeProject);
					}
				});
				fieldProjectLabel.addMouseOverHandler(new MouseOverHandler() {
					@Override
					public void onMouseOver(MouseOverEvent event) {
						if (!fieldProjectLabel.equals(selectedProjectFieldTypeLabel)) {
							fieldProjectLabel.setStyleName("ws-app-pref-fieldtype-onHover");
						}
					}
				});

				fieldProjectLabel.addMouseOutHandler(new MouseOutHandler() {

					@Override
					public void onMouseOut(MouseOutEvent event) {
						if (!fieldProjectLabel.equals(selectedProjectFieldTypeLabel)) {
							fieldProjectLabel.setStyleName("ws-app-pref-fieldtype");
						}
					}
				});

				projectListLayout.add(fieldProjectLayout);
				count++;
			}

		} else {

			Element docElement = event.getData();
			ArrayList<Resources> listOfResources = new ArrayList<Resources>();

			NodeList recordList = docElement.getElementsByTagName("resources");

			for (int i = 0; i < recordList.getLength(); i++) {
				if (!recordList.item(i).toString().trim().isEmpty()) {
					NodeList preferenceDetails = recordList.item(i).getChildNodes();

					for (int k = 0; k < preferenceDetails.getLength(); k++) {
						if (!preferenceDetails.item(k).toString().trim().isEmpty()) {
							NodeList childNodes = preferenceDetails.item(k).getChildNodes();
							Resources resourcesList = new Resources();
							for (int j = 0; j < childNodes.getLength(); j++) {
								String childNodeName = childNodes.item(j).getNodeName();

								if (childNodeName.equals("rid"))
									resourcesList.setResourceID(childNodes.item(j).getFirstChild().getNodeValue());
								else if (childNodeName.equals("type")) {
									resourcesList.setResourceType(childNodes.item(j).getFirstChild().getNodeValue());
								}
							}
							listOfResources.add(resourcesList);
						}
					}
				}
			}
			aclFileData.setResources(listOfResources);
			NodeList entryList = docElement.getElementsByTagName("entry");
			ArrayList<String> resourcetype = new ArrayList<String>();
			ArrayList<String> resourcevalue = new ArrayList<String>();
			ArrayList<Entry> listOfEntries = new ArrayList<Entry>();
			String role = null;
			for (int i = 0; i < entryList.getLength(); i++) {
				NodeList entryChild = entryList.item(i).getChildNodes();
				Entry entryObject = new Entry();
				ArrayList<Permission> permissionObjectArray = new ArrayList<Permission>();
				for (int j = 0; j < entryChild.getLength(); j++) {
					String entryChildNodeName = entryChild.item(j).getNodeName();

					if (entryChildNodeName.equals("role")) {
						NodeList roleList = entryChild.item(j).getChildNodes();
						for (int k = 0; k < roleList.getLength(); k++) {
							String roleName = roleList.item(k).getNodeName();
							if (roleName.equals("name")) {
								role = roleList.item(k).getFirstChild().getNodeValue();
								entryObject.setRoleName(roleList.item(k).getFirstChild().getNodeValue());
							}
						}
					} else if (entryChildNodeName.equals("permissions")) {
						NodeList permissions = entryChild.item(j).getChildNodes();
						for (int k = 0; k < permissions.getLength(); k++) {
							NodeList listOfPermissions = permissions.item(k).getChildNodes();
							Permission permissionObject = new Permission();
							for (int l = 0; l < listOfPermissions.getLength(); l++) {
								String permissionChildNodeName = listOfPermissions.item(l).getNodeName();
								if (permissionChildNodeName.equals("action")) {
									NodeList actionChilds = listOfPermissions.item(l).getChildNodes();
									for (int h = 0; h < actionChilds.getLength(); h++) {
										String actionChildNodeName = actionChilds.item(h).getNodeName();
										if (actionChildNodeName.equals("type")) {
											resourcetype.add(actionChilds.item(h).getFirstChild().getNodeValue());
											permissionObject
													.setActionType(actionChilds.item(h).getFirstChild().getNodeValue());
										} else if (actionChildNodeName.equals("value")) {
											resourcevalue.add(actionChilds.item(h).getFirstChild().getNodeValue());
											permissionObject.setActionValue(
													actionChilds.item(h).getFirstChild().getNodeValue());
										}
									}

								} else if (permissionChildNodeName.equals("resourceref")) {
									permissionObject
											.setResourceRef(listOfPermissions.item(l).getFirstChild().getNodeValue());
									permissionObjectArray.add(permissionObject);

								}

							}

						}

					}
					if (!entryChildNodeName.equals("#text") && role != null) {
						entryObject.setPermission(permissionObjectArray);
					}
				}
				listOfEntries.add(entryObject);
			}

			if (roleGrid.getRecords().length > listOfEntries.size()) {
				for (int i = listOfEntries.size(); i < roleGrid.getRecords().length; i++) {
					Entry newEntry = new Entry();
					newEntry.setRoleName(roleGrid.getRecord(i).getAttribute("role"));
					newEntry.setPermission(new ArrayList<Permission>());
					listOfEntries.add(newEntry);
				}
			}

			aclFileData.setEntries(listOfEntries);
			userPermission.setAclData(aclFileData);
			WebStudio.get().setAclData(aclFileData);

		}

	}
}
