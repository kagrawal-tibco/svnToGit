/**
 * 
 */
package com.tibco.cep.webstudio.client.groups;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;

/**
 * Widget to represent a user group
 * 
 * @author Vikram Patil
 */
class Group extends VStack implements ClickHandler, MouseOverHandler, MouseOutHandler {
	
	private static GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	private String groupID;
	private String groupIcon;
	private String groupTitle;
	private boolean isSystem;
	private String fileType;
	private List<String> artifactPaths;
	private String headerIcon;
	private boolean selected;
	private Img groupImg;

	/**
	 * 
	 * @param groupIcon
	 * @param groupTitle
	 * @param extention
	 * @param isSystem
	 * @param headerIcon
	 */
	public Group(String groupIcon, String groupTitle, String extention, boolean isSystem, String headerIcon) {
		super();

		this.groupID = "group_" + groupTitle.replace(" ", "$");
		this.groupIcon = groupIcon;
		this.groupTitle = groupTitle;
		this.fileType = extention;
		this.isSystem = isSystem;
		this.headerIcon = headerIcon;

		this.init();
	}

	/**
	 * 
	 * @param groupTitle
	 * @param extention
	 * @param isSystem
	 */
	public Group(String groupTitle, String extension, boolean isSystem) {
		this(null, groupTitle, extension, isSystem, null);
	}

	/**
	 * 
	 * @param groupTitle
	 */
	public Group(String groupTitle) {
		this(groupTitle, null, false);
	}

	/**
	 * Add context menu options
	 */
	public void addContextMenu() {
		Menu contextMenu = new Menu();

		MenuItem menuItem = new MenuItem("Delete", Page.getAppImgDir() + "icons/16/delete.png");
		menuItem.addClickHandler(this);

		contextMenu.addItem(menuItem);

		this.setContextMenu(contextMenu);
	}

	public void onClick(MenuItemClickEvent event) {
		String optionName = event.getItem().getTitle();
		if (optionName.equals("Delete")) {
			ContentModelManager.getInstance().removeGroup(groupTitle);
		}
	}

	public void setGroupIcon(String icon) {
		this.groupIcon = icon;
	}

	public void setHeaderIcon(String icon) {
		this.headerIcon = icon;
	}

	public void setArtifactPaths(List<String> artifactPaths) {
		this.artifactPaths = artifactPaths;
	}

	public void addArtifactPath(String artifactPath) {
		if (this.artifactPaths == null) {
			this.artifactPaths = new ArrayList<String>();
		}
		this.artifactPaths.add(artifactPath);
	}

	/**
	 * Initialize the widget
	 */
	private void init() {
//		this.setID(this.groupID);
		this.setMembersMargin(2);
		this.setLayoutMargin(2);
		this.setCursor(Cursor.POINTER);

		this.addMouseOverHandler(this);
		this.addMouseOutHandler(this);
		
		this.setAutoWidth();
		this.setAutoHeight();
		this.setStyleName("ws-myGroups");
		
		groupImg = new Img(getGroupIcon(), 48, 48);
		groupImg.setCursor(Cursor.POINTER);
		groupImg.setAlign(Alignment.CENTER);
		groupImg.setValign(VerticalAlignment.CENTER);
		groupImg.setStyleName("ws-myGroupsImage");
		addMember(groupImg);

		Label groupTitle = new Label(getLocalizedGroupTitle(getGroupTitle()));
		/*groupTitle.setWidth(1);
		groupTitle.setMaxWidth(2);*/
		groupTitle.setCursor(Cursor.POINTER);
		groupTitle.setHeight(20);
		groupTitle.setWrap(true);
		groupTitle.setStyleName("ws-myGroups-groupTitle");
		groupTitle.setAlign(Alignment.CENTER);
		groupTitle.setValign(VerticalAlignment.CENTER);
		addMember(groupTitle);

	}

	public String getGroupIcon() {
		return this.groupIcon;
	}

	public String getGroupTitle() {
		return this.groupTitle;
	}

	public String getExtention() {
		return this.fileType;
	}

	public boolean isSystem() {
		return this.isSystem;
	}

	public String getHeaderIcon() {
		return this.headerIcon;
	}

	public List<String> getArtifactPaths() {
		return this.artifactPaths;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public void setGroupTitle(String groupTitle) {
		this.groupTitle = groupTitle;
	}

	public void setSystem(boolean isSystem) {
		this.isSystem = isSystem;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileType() {
		return this.fileType;
	}

	public boolean isSelected() {
		return this.selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		if (selected) {
			this.setStyleName("ws-myGroups-selected");
		} else {
			this.setStyleName("ws-myGroups");
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		Group grp = (Group) obj;
		return (this.getGroupID().equals(grp.getGroupID()) && this.getTitle().equals(grp.getTitle()));
	}

	/**
	 * @see MouseOutEvent
	 */
	public void onMouseOut(MouseOutEvent event) {
		if (this.isSelected()) {
			this.setStyleName("ws-myGroups-selected");
		}
		else {
			this.setStyleName("ws-myGroups");
		}
	}

	/**
	 * @see MouseOverEvent
	 */
	public void onMouseOver(MouseOverEvent event) {
		this.setStyleName("ws-myGroups-over");
	}
	
	/**
	 * Sets/Updates the image source
	 */
	public void setGroupImage() {
		groupImg.setSrc(getGroupIcon());
		groupImg.setAlign(Alignment.CENTER);
	}
	
	public String getGroupID() {
		return groupID;
	}
	
	/**
	 * Retrieve the internationalized names for default groups.
	 * 
	 * @param groupId
	 * @return
	 */
	private String getLocalizedGroupTitle(String groupId) {
		String groupTitle = groupId;
		
		if (groupId.equals(ContentModelManager.PROJECTS_GROUP_ID)) {
			groupTitle = globalMsgBundle.groups_projects_title();
		} else if (groupId.equals(ContentModelManager.BUSINESS_RULES_GROUP_ID)) {
			groupTitle = globalMsgBundle.groups_businessRules_title();
		} else if (groupId.equals(ContentModelManager.DECISION_TABLES_GROUP_ID)) {
			groupTitle = globalMsgBundle.groups_decisionTables_title();
		} else if (groupId.equals(ContentModelManager.PROCESSES_GROUP_ID)) {
			groupTitle = globalMsgBundle.groups_processes_title();
		}
		
		return groupTitle;
	}

}