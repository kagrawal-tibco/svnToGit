package com.tibco.cep.webstudio.client.widgets;

import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.MouseMoveEvent;
import com.smartgwt.client.widgets.events.MouseMoveHandler;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableEditor;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableEditorFactory;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.editor.EditorFactory;
import com.tibco.cep.webstudio.client.editor.IEditorFactory;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory.RuleTemplateInstanceEditor;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.RMSMessages;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.util.ArtifactLockHelper;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil;

/**
 * Application Menu with various actions supported, additionally support for handling each of these actions.
 * 
 * @author Vikram Patil
 */
public class WebStudioMenubar extends HLayout implements com.smartgwt.client.widgets.menu.events.ClickHandler {

	private static final int APPLICATION_MENU_HEIGHT = 25;
	private static final int DEFAULT_SHADOW_DEPTH = 10;
	private static final int DEFAULT_MENU_WIDTH = 45;

	public static final String SEPARATOR = "separator";
	public static final String ICON_PREFIX =  Page.getAppImgDir() + "icons/16/";
	public static final String ICON_SUFFIX = ".png";

	//	private MenuBar menuBar ;
	private Menu mainMenu ;
	
	// Menu Option Indexes
	// New Menu Options
	public static final int NEW_STUDIO_PROJECT = 0;
	public static final int NEW_RESOURCE_CONCEPT = 0;
	public static final int NEW_RESOURCE_EVENT = 1;
	public static final int NEW_RESOURCE_CHANNEL = 2;
	public static final int NEW_RESOURCE_SCORECARD = 3;
	public static final int NEW_RESOURCE_RULE = 4;
	public static final int NEW_RESOURCE_RULE_FUNCTION = 5;
	// Edit Menu Options
	public static final int EDIT_CUT = 0;
	public static final int EDIT_COPY = 1;
	public static final int EDIT_PASTE = 2;
	public static final int EDIT_DELETE = 3;
	// Import Menu Options
	public static final int IMPORT_STUDIO_PROJECT = 0;
	public static final int IMPORT_DECISION_TABLE = 1;
	//Project Menu Options
	public static final int PROJECT_VALIDATE = 0;
	public static final int PROJECT_ANALYZE = 1;
	public static final int PROJECT_VIEW = 3;
	//Table Menu Options
	public static final int TABLE_OPEN = 0;
	public static final int TABLE_ANALYSE = 1;
	// Rule Menu Options
	public static final int RULE_ANALYSE = 0;
	// Process Menu Options
	public static final int PROCESS_GENERATE_PROCESSCODE = 0;
	
	// Tibbr Menu Options
	public static final int TIBBR_LOGIN = 0;
	public static final int TIBBR_SENT_TO_SUBJECT = 1;
	
	// Menu ID's
	public static final String MENU_NEW_ID = "id_new";
	public static final String MENU_NEW_RESOURCE_ID = "id_new_resource";
	public static final String MENU_EDIT_ID = "id_edit";
	public static final String MENU_IMPORT_ID = "id_import";
	public static final String MENU_PROJECT_ID = "id_project";
	public static final String MENU_TABLE_ID = "id_table";
	public static final String MENU_RULE_ID = "id_rule";
	public static final String MENU_PROCESS_ID = "id_process";
	public static final String MENU_RMS_ID = "id_rms";
	public static final String MENU_TIBBR_ID = "id_tibbr";
	public static final String MENU_HELP_ID = "id_help";
	
	//RMS MenuItem Ids 
	private static final String MENUITEM_RMS_SHOW_DIFF_ID = "id_rms_showDiff";
	
	private RMSMessages rmsMsgBundle = (RMSMessages) I18nRegistry.getResourceBundle(I18nRegistry.RMS_MESSAGES);
	private GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	/**
	 * Default Constructor
	 */
	public WebStudioMenubar() {
		super();
		this.setHeight(APPLICATION_MENU_HEIGHT);
		this.setWidth("300px");
		// Create Menu Bar and add it layout container
		mainMenu = new Menu();
		mainMenu.setWidth("140px");
		mainMenu.setAutoHeight();
		Canvas padding = new Canvas();
		padding.setWidth(10);
		this.addMember(padding);
		this.setBorder("0px");
		final Label mainMenuButton = new Label("WebStudio");
        mainMenuButton.setIcon(Page.getAppImgDir() + "down_arrow.png");  
        mainMenuButton.setIconWidth(11);
        mainMenuButton.setIconHeight(7);
        mainMenuButton.setIconOrientation("right");  
        mainMenuButton.setWidth("140px");
        mainMenuButton.setStyleName("ws-MainMenu");
        mainMenuButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
				int x = mainMenuButton.getAbsoluteLeft();//event.getX();
				int y = mainMenuButton.getAbsoluteTop()+mainMenuButton.getHeight();//event.getY();
            	mainMenu.moveTo(x, y);
            	if (mainMenu.isDrawn()) {
            		mainMenu.redraw();
            		mainMenu.show();
            	} else {
            		mainMenu.draw();
            	}
            }  
        }); 
        mainMenuButton.addMouseMoveHandler(new MouseMoveHandler() {  
            public void onMouseMove(MouseMoveEvent event) {  
                mainMenuButton.setStyleName("ws-MainMenu-rollover");
                mainMenuButton.markForRedraw();
            }  
        });  
  
        mainMenuButton.addMouseOutHandler(new MouseOutHandler() {  
            public void onMouseOut(MouseOutEvent event) {  
                mainMenuButton.setStyleName("ws-MainMenu");
                mainMenuButton.setOpacity(100);
            }  
        });  

        this.addMember(mainMenuButton);
        createMenu();
	}
		
	/**
	 * Initialize and create various Menu/SubMenu Options
	 * 
	 * @return
	 */
	private Menu createMenu(){
		//RMS Menu
		Menu[] rmsMenu = addMenu(globalMsgBundle.menu_rms(),MENU_RMS_ID,null);
		rmsMenu[0].addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				WebStudio.get().setRmsOptionViaContextMenu(false);
			}
		});

		addRMSMenuOptions(rmsMenu[0], false, false, false, false);
		
		MenuItem item = new MenuItem(globalMsgBundle.menu_rms());
		item.setSubmenu(rmsMenu[0]);
		mainMenu.addItem(item);
		
		// Help Menu
		Menu[] helpMenu = addMenu(globalMsgBundle.menu_help(), MENU_HELP_ID, null);
		addMenuOptions(helpMenu[0], globalMsgBundle.menu_helpOnthisPage(), "helponthispage.png");
		addMenuOptions(helpMenu[0], globalMsgBundle.menu_helpContents(), "contents.png");
		addMenuOptions(helpMenu[0], SEPARATOR, null);
		addMenuOptions(helpMenu[0], globalMsgBundle.menu_helpAbout(), "aboutwebstudio.png");
		
		item = new MenuItem(globalMsgBundle.menu_help());
		item.setSubmenu(helpMenu[0]);
		mainMenu.addItem(item);

		return mainMenu;
	}	
	
	/**
	 * Create Top level menu
	 * 
	 * @param menuName
	 * @param width
	 */
	private Menu[] addMenu(String menuName, String id, Integer width) {
		width = (width == null || width <= 0) ? DEFAULT_MENU_WIDTH : width.intValue();
	
		Menu menu = new Menu();
		menu.setTitle(menuName);
		menu.setShowShadow(true);  
		menu.setShadowDepth(DEFAULT_SHADOW_DEPTH); 
		menu.setWidth(width);
		menu.setID(id);

		Menu[] menus = new Menu[1]; 
		menus[0] = menu;
				
		return menus;
	}
	
	/**
	 * Create Menu Option
	 * 
	 * @param parentMenu
	 * @param menuOptionName
	 * @param menuOptionIndex
	 * @param icon
	 */
	private MenuItem addMenuOptions(Menu parentMenu, String menuOptionName, String icon){
		MenuItem menuItem = null;
		if (menuOptionName.equals(SEPARATOR)){
			menuItem = new MenuItemSeparator();
		} else {
			menuItem = new MenuItem(menuOptionName, getIcon(menuOptionName,icon));
			menuItem.addClickHandler(this);
		}
		
		parentMenu.addItem(menuItem);
		return menuItem;
	}
	
	/**
	 * Create SubMenu
	 * 
	 * @param parentMenuId
	 * @param menuOptionIndex
	 * @param subMenuName
	 * @param subMenuId
	 * @param width
	 */
	private Menu addSubMenu(Menu parentMenu, String subMenuName, String subMenuId, Integer width){
		width = (width == null || width <= 0)?DEFAULT_MENU_WIDTH:width.intValue();
		
		MenuItem menuItem = new MenuItem(subMenuName);
		
		Menu subMenu = new Menu(); 
		subMenu.setShowShadow(true);  
		subMenu.setShadowDepth(DEFAULT_SHADOW_DEPTH); 
		subMenu.setWidth(width);
		subMenu.setID(subMenuId);
				
		menuItem.setSubmenu(subMenu);
		parentMenu.addItem(menuItem);
		
		return subMenu;
	}

	/**
	 * Add RMS menu options which can be reused within other widgets
	 */
	public void addRMSMenuOptions(Menu parentMenu, boolean showDiffOption, boolean showLockOption, boolean isLocked, boolean showHistoryOption) {
		if (showDiffOption) {
			MenuItem showDiffMenuItem = addMenuOptions(parentMenu, globalMsgBundle.menu_rmsDiff(), "diff.png");
			showDiffMenuItem.setAttribute("id", MENUITEM_RMS_SHOW_DIFF_ID);
		}
		addMenuOptions(parentMenu, globalMsgBundle.menu_rmsCheckout(), "checkout.png");
		addMenuOptions(parentMenu, globalMsgBundle.menu_rmsCommit(), "commit.png");
		addMenuOptions(parentMenu, globalMsgBundle.menu_rmsUpdate(), "synchronize.png");
		addMenuOptions(parentMenu, globalMsgBundle.menu_rmsRevert(), "revert.png");
		addMenuOptions(parentMenu, globalMsgBundle.menu_rmsSyncToRepository(), "synchronize.png");
		addMenuOptions(parentMenu, SEPARATOR, null);
		addMenuOptions(parentMenu, globalMsgBundle.menu_rmsWorkList(), "worklist.png");
		if (showHistoryOption) {
			addMenuOptions(parentMenu, globalMsgBundle.menu_rmsHistory(), "history.png");
		}
		addMenuOptions(parentMenu, SEPARATOR, null);
		addMenuOptions(parentMenu, globalMsgBundle.menu_rmsDeployable(), "generatedeployable.gif");
		addMenuOptions(parentMenu, SEPARATOR, null);
		if (showLockOption) {
			if (isLocked) {
				addMenuOptions(parentMenu, globalMsgBundle.menu_rmsUnLock(), "unlockedstate.gif");
			} else {
				addMenuOptions(parentMenu, globalMsgBundle.menu_rmsLock(), "lockedstate.gif");
			}
		}
		addMenuOptions(parentMenu, globalMsgBundle.menu_rmsManageLocks(), "lockedstate.gif");
	}
	
	/**
	 * Get Icon Path
	 * 
	 * @param optionName
	 * @param iconPath
	 * @return
	 */
	private String getIcon(String optionName, String iconPath) {
		String icon = ICON_PREFIX;
		
		if (iconPath == null){
			icon += optionName.toLowerCase().replaceAll("\\W", "") + ICON_SUFFIX;
		} else {
			icon += iconPath;
		}
		
		return icon;
	}
	
	/**
	 * Enable/Disable a menu option
	 * 
	 * @param menuID
	 * @param menuOption
	 * @param enable
	 */
	@SuppressWarnings("static-access")
	public void enableMenuOption(String menuID, int menuOption, boolean enable) {
		Menu menu = (Menu) mainMenu.getById(menuID);
		MenuItem menuItem = menu.getItem(menuOption);
		menuItem.setEnabled(enable);
	}
	
	/**
	 * Returns NavigatorResource of artifact identified by adtifactId and projectName
	 * @param artifactId
	 * @param projectName
	 * @return
	 */
	private NavigatorResource getResource(String artifactId, String projectName) {
		String artifactFileExtn = artifactId.substring(artifactId.lastIndexOf('.') + 1);
		String artifactName = artifactId.substring(artifactId.lastIndexOf("$") + 1, artifactId.length());
		String artifactParent = artifactId.substring(0, artifactId.lastIndexOf("$"));

		NavigatorResource resource = ProjectExplorerUtil.createProjectResource(
				artifactName, artifactParent, artifactFileExtn, artifactId, false);
		return resource;
	}
	
	@Override
	public void onClick(MenuItemClickEvent event) {
		String applicationName = event.getItem().getTitle();
		String applicationId = event.getItem().getAttribute("id");
		
		if (MENUITEM_RMS_SHOW_DIFF_ID.equals(applicationId)) {
			if (WebStudio.get().getCurrentlySelectedArtifact() == null) {
				CustomSC.say("Please select an artifact to view diff.");
			} else {
				NavigatorResource selectedResource = getResource(WebStudio.get().getCurrentlySelectedArtifact(), WebStudio.get().getCurrentlySelectedProject());
				if (selectedResource != null && (selectedResource.getType().equals("ruletemplateinstance") || selectedResource.getType().equals("rulefunctionimpl"))) {
					String tabTitle = selectedResource.getName().substring(0, selectedResource.getName().indexOf('.')) + "(" + globalMsgBundle.menu_rmsDiff() + ")";
					Tab[] openTabs = WebStudio.get().getEditorPanel().getTabs();
					for (Tab tab : openTabs) {
						if (tab instanceof AbstractEditor) {
							if (tabTitle.equals(tab.getTitle())) {
								WebStudio.get().showWorkSpacePage();
								WebStudio.get().getEditorPanel().selectTab(tab);
								return;
							}
						}
					}
					IEditorFactory editorFactory = EditorFactory.getArtifactEditorFactory(selectedResource);
					Tab editor = null;
					if (editorFactory instanceof RuleTemplateInstanceEditorFactory) {
						RuleTemplateInstanceEditorFactory rtiEditorFactory = (RuleTemplateInstanceEditorFactory) editorFactory;
						RuleTemplateInstanceEditor rtiEditor = (RuleTemplateInstanceEditor) rtiEditorFactory.createEditor(selectedResource, true, null, true, null);
						editor = rtiEditor;
					} else if (editorFactory instanceof DecisionTableEditorFactory) {
						DecisionTableEditorFactory dtEditorFactory = (DecisionTableEditorFactory) editorFactory;
						DecisionTableEditor dtEditor = (DecisionTableEditor) dtEditorFactory.createEditor(selectedResource, true, null, true, null);
						editor = dtEditor;
					}
					if (editor != null) {
						editor.setTitle(tabTitle);
						WebStudio.get().showWorkSpacePage();
						WebStudio.get().getEditorPanel().addTab(editor);
						WebStudio.get().getEditorPanel().selectTab(editor);
					}
				}
			}
			return;
		} 
		
		if (globalMsgBundle.menu_rmsCheckout().equals(applicationName.trim())) {
			new RMSCheckoutDialog().show();
			return;
		}
		
		if (globalMsgBundle.menu_rmsSyncToRepository().equals(applicationName.trim())) {
			new RMSSyncToRepository().show();
			return;
		}
		
		if (globalMsgBundle.menu_rmsCheckout().equals(applicationName.trim())) {
			new RMSCheckoutDialog().show();
			return;
		}
				
		if (globalMsgBundle.menu_rmsWorkList().equals(applicationName.trim())) {
			new RMSWorklistDialog().show();
			return;
		}
		if (globalMsgBundle.menu_rmsUpdate().equals(applicationName.trim())) {
			if ((WebStudio.get().getCurrentlySelectedProject() == null || WebStudio.get().getCurrentlySelectedProject().isEmpty())) {
				CustomSC.say(rmsMsgBundle.rms_selectProjectToUpdate_message());
			} else {
				String[] selectedArtifacts = new String[]{WebStudio.get().getCurrentlySelectedArtifact()};
				if (!WebStudio.get().isRmsOptionViaContextMenu()) {
					selectedArtifacts = null;
				} else {
					WebStudio.get().setRmsOptionViaContextMenu(false);
				}
				new RMSUpdateDialog(selectedArtifacts).show();
			}
			return;
		}
		if (globalMsgBundle.menu_rmsCommit().equals(applicationName.trim())) {
			if (WebStudio.get().getCurrentlySelectedProject() == null || WebStudio.get().getCurrentlySelectedProject().isEmpty()){
				CustomSC.say(rmsMsgBundle.rms_selectProjectToCommit_message());
			} else {
				String[] selectedArtifacts = new String[]{WebStudio.get().getCurrentlySelectedArtifact()};
				if (!WebStudio.get().isRmsOptionViaContextMenu()) {
					selectedArtifacts = null;
				} else {
					WebStudio.get().setRmsOptionViaContextMenu(false);
				}
				new RMSCommitDialog(selectedArtifacts).show();
			}
			return;
		}
		if (globalMsgBundle.menu_rmsRevert().equals(applicationName.trim())) {
			if (WebStudio.get().getCurrentlySelectedProject() == null || WebStudio.get().getCurrentlySelectedProject().isEmpty()){
				CustomSC.say(rmsMsgBundle.rms_selectProjectToRevert_message());
			} else {
				String[] selectedArtifacts = new String[]{WebStudio.get().getCurrentlySelectedArtifact()};
				if (!WebStudio.get().isRmsOptionViaContextMenu()) {
					selectedArtifacts = null;
				} else {
					WebStudio.get().setRmsOptionViaContextMenu(false);
				}
				new RMSRevertDialog(selectedArtifacts).show();
			}
			return;
		}
		if (globalMsgBundle.menu_rmsHistory().equals(applicationName.trim())) {
			// TODO - Should only apply to leaf (non folder) nodes
			if (WebStudio.get().getCurrentlySelectedArtifact() == null){
				CustomSC.say(rmsMsgBundle.rms_selectArtifactToViewHistory_message());
			} else {
				new RMSHistoryDialog().show();
			}
			return;
		}
		if (globalMsgBundle.menu_rmsDeployable().equals(applicationName.trim())){
			String selectedArtifact = WebStudio.get().getCurrentlySelectedArtifact();
			if (WebStudio.get().isRmsOptionViaContextMenu()
					&& selectedArtifact != null
					&& selectedArtifact.indexOf(".") != -1
					&& !selectedArtifact.endsWith(".ruletemplateinstance")
					&& !selectedArtifact.endsWith(".rulefunctionimpl")) {
				CustomSC.say(rmsMsgBundle.rms_cannotDeployArtifact_message());
			} else {
				String[] selectedArtifacts = new String[]{selectedArtifact};
				if (!WebStudio.get().isRmsOptionViaContextMenu()) {
					selectedArtifacts = null;
				}
				new RMSDeployArtifactDialog(selectedArtifacts).show();
			}
			return;
		}
		if (globalMsgBundle.menu_helpAbout().equals(applicationName.trim())) {
			new WebStudioAboutDialog().show();
			return;
		}
		if (globalMsgBundle.menu_rmsLock().equals(applicationName.trim())) {
			NavigatorResource selectedResource = WebStudio.get().getWorkspacePage().getGroupContentsWindow().getArtifactTreeGrid().getClickHandler().getSelectedResource();
			//NavigatorResource selectedResource = getResource(WebStudio.get().getCurrentlySelectedArtifact(), WebStudio.get().getCurrentlySelectedProject());
			ArtifactLockHelper.getInstance().lockArtifacts(new NavigatorResource[]{selectedResource});
			return;
		}
		if (globalMsgBundle.menu_rmsUnLock().equals(applicationName.trim())) {
			NavigatorResource selectedResource = WebStudio.get().getWorkspacePage().getGroupContentsWindow().getArtifactTreeGrid().getClickHandler().getSelectedResource();
			//NavigatorResource selectedResource = getResource(WebStudio.get().getCurrentlySelectedArtifact(), WebStudio.get().getCurrentlySelectedProject());
			ArtifactLockHelper.getInstance().unLockArtifacts(new NavigatorResource[]{selectedResource});
			return;
		}
		if (globalMsgBundle.menu_rmsManageLocks().equals(applicationName.trim())) {
			new RMSManageProjectLocksDialog().show();
			return;
		}
		
		CustomSC.say(globalMsgBundle.message_help_notImplemented() + " " + applicationName);
	}
}