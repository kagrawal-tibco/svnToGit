package com.tibco.cep.webstudio.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.events.ItemClickEvent;
import com.smartgwt.client.widgets.menu.events.ItemClickHandler;
import com.tibco.cep.webstudio.client.groups.AddGroup;
import com.tibco.cep.webstudio.client.groups.ContentModelManager;
import com.tibco.cep.webstudio.client.groups.GroupContentsWindow;
import com.tibco.cep.webstudio.client.groups.MyGroups;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil;
import com.tibco.cep.webstudio.client.util.WebStudioShortcutUtil;
import com.tibco.cep.webstudio.client.widgets.WebStudioEditorPanel;
import com.tibco.cep.webstudio.client.widgets.WebStudioMenubar;
import com.tibco.cep.webstudio.client.widgets.WebStudioStatusBar;
import com.tibco.cep.webstudio.client.widgets.WebStudioToolbar;

public class WebStudioWorkspacePage extends VLayout{
	
	private GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private WebStudioMenubar applicationMenu ;
	private WebStudioToolbar applicationToolBar;
	private WebStudioEditorPanel editorPanel; 
	private MyGroups myGroups;
	private WebStudioStatusBar statusbar;
	private SectionStack groupsSectionStack;
	private ImgButton removeButton;

	private GroupContentsWindow groupContentsWindow;

	public WebStudioMenubar getApplicationMenu() {
		return applicationMenu;
	}

	public WebStudioToolbar getApplicationToolBar() {
		return applicationToolBar;
	}

	public WebStudioEditorPanel getEditorPanel() {
		return editorPanel;
	}

	public MyGroups getMyGroups() {
		return myGroups;
	}

	public SectionStack getGroupsSectionStack() {
		return groupsSectionStack;
	}


	public WebStudioStatusBar getStatusbar() {
		return statusbar;
	}

	public WebStudioWorkspacePage() {
		this.setWidth100(); 
		this.setHeight100();

		// initialize the North layout container
		HLayout northLayout = new HLayout(); 
		northLayout.setAutoHeight();
		northLayout.setShowResizeBar(true);
		northLayout.setStyleName("ws-main-background");
		northLayout.setAutoHeight();
		// create application menu
		applicationMenu = new WebStudioMenubar();
		// add the Application menu bar to the nested layout container
		northLayout.addMember(applicationMenu);

		applicationToolBar =  new WebStudioToolbar(); 
		// add the Application tool bar to the nested layout container
		northLayout.addMember(applicationToolBar);

		editorPanel = new WebStudioEditorPanel();

		// initialize the South layout container
		HLayout southLayout = new HLayout();
		southLayout.setResizeBarSize(12);

		groupsSectionStack = new SectionStack();
		groupsSectionStack.setVertical(true);
		groupsSectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		groupsSectionStack.setWidth("20%");
		groupsSectionStack.setAnimateMembers(true);
		groupsSectionStack.setShowResizeBar(true);
		
		SectionStackSection groupsSection = new SectionStackSection(globalMsgBundle.groups_sectionTitle());  

        groupsSection.setExpanded(true);  
        groupsSection.setResizeable(true);  
        groupsSectionStack.addSection(groupsSection);  

        ImgButton addButton = new ImgButton();  
        addButton.setSrc("[SKIN]actions/add.png");  
        addButton.setSize(16);  
        addButton.setShowFocused(false);  
        addButton.setShowRollOver(false);  
        addButton.setShowDown(false);
        addButton.setTooltip(globalMsgBundle.groupAdd_tooltip());
        addButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
				new AddGroup().show();
            }  
        });  
  
        removeButton = new ImgButton();  
        removeButton.setSrc("[SKIN]actions/remove.png");  
        removeButton.setSize(16);  
        removeButton.setShowFocused(false);  
        removeButton.setShowRollOver(false);  
        removeButton.setShowDown(false); 
        removeButton.setTooltip(globalMsgBundle.groupDelete_tooltip());
        removeButton.setDisabled(true);
        removeButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	myGroups.deleteSelectedGroups();
            }  
        });  
        groupsSection.setControls(addButton, removeButton);
        
        SectionStackSection resourcesSection = new SectionStackSection(globalMsgBundle.groupContents_sectionTitle());  
        resourcesSection.setExpanded(true);  
        resourcesSection.setCanCollapse(true);  
        resourcesSection.setResizeable(true);
        
        ImgButton refreshButton = new ImgButton();  
        refreshButton.setSrc(Page.getAppImgDir() + "icons/16/refresh_16x16.png");  
        refreshButton.setSize(16);  
        refreshButton.setShowFocused(false);  
        refreshButton.setShowRollOver(false);  
        refreshButton.setShowDown(false); 
        refreshButton.setTooltip(globalMsgBundle.text_refresh());
        refreshButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				List<String> slectedGrpsId = getGroupContentsWindow().getSelectedGrpsIdList();
				getMyGroups().clearSelectedGroups();
				
				List<String> groupsToRefresh = new ArrayList<String>();
				groupsToRefresh.add(ContentModelManager.BUSINESS_RULES_GROUP_ID);
				groupsToRefresh.add(ContentModelManager.DECISION_TABLES_GROUP_ID);
				groupsToRefresh.add(ContentModelManager.PROCESSES_GROUP_ID);
				groupsToRefresh.add(ContentModelManager.PROJECTS_GROUP_ID);
				
				getGroupContentsWindow().setgroupToRefreshList(groupsToRefresh);
				
				for (String grpId : slectedGrpsId) {
					getMyGroups().selectGroup(grpId);
				}
			}
		});
        
        ImgButton searchButton = new ImgButton();  
        searchButton.setSrc(Page.getAppImgDir() + "icons/16/search.png");  
        searchButton.setSize(16);  
        searchButton.setShowFocused(false);  
        searchButton.setShowRollOver(false);  
        searchButton.setShowDown(false); 
        searchButton.setTooltip(globalMsgBundle.button_search());
        searchButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				groupContentsWindow.showSearchBox();
			}
		});
		
        groupsSectionStack.addSection(resourcesSection); 

        final ImgButton showAsMenuButton = new ImgButton();  
        showAsMenuButton.setSrc(Page.getAppImgDir() + "icons/16/list_16x16.gif");  
        showAsMenuButton.setSize(16);  
        
        showAsMenuButton.setShowTitle(false);  
        showAsMenuButton.setShowFocused(false);  
        showAsMenuButton.setShowRollOver(false);  
        showAsMenuButton.setShowDown(false);  
        
        final Menu menu = new Menu();  
        menu.setShowShadow(true);  
        menu.setShadowDepth(3);  
        
        final MenuItem showAsTreeItem = new MenuItem(globalMsgBundle.groupContents_showAsTree());  
        showAsTreeItem.setChecked(true);
        showAsTreeItem.setCheckIfCondition(new MenuItemIfFunction() {
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return groupContentsWindow.isShowAsTree();
			}
		});
        final MenuItem showAsListItem = new MenuItem(globalMsgBundle.groupContents_showAsList());
        showAsListItem.setCheckIfCondition(new MenuItemIfFunction() {
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return !groupContentsWindow.isShowAsTree();
			}
		});

        menu.setItems(showAsListItem, showAsTreeItem);
        menu.addItemClickHandler(new ItemClickHandler() {
			
			@Override
			public void onItemClick(ItemClickEvent event) {
				if (event.getItem() == showAsTreeItem) {
					showAsMenuButton.setSrc(Page.getAppImgDir() + "icons/16/navigation.gif");
					showResourceAsTree(true);
					
					ProjectExplorerUtil.toggleToolbarOptions(null);
				} else {
					showAsMenuButton.setSrc(Page.getAppImgDir() + "icons/16/list_16x16.gif");
					showResourceAsTree(false);
					
					WebStudio.get().getApplicationToolBar().enableButton(WebStudioToolbar.TOOL_STRIP_REVERT_ID);
					WebStudio.get().getApplicationToolBar().enableButton(WebStudioToolbar.TOOL_STRIP_COMMIT_ID);
					WebStudio.get().getApplicationToolBar().enableButton(WebStudioToolbar.TOOL_STRIP_UPDATE_ID);
				}
				menu.hide();
			}
		});
        showAsMenuButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				int x = event.getX();
				int y = event.getY();
				if(LocaleInfo.getCurrentLocale().isRTL()){
					x = x - 150;
					y = y + 10; 
				}
				menu.moveTo(x, y);
				if (!menu.isDrawn()) {
					menu.draw();
				} else {
					menu.show();
				}
			}
		});
        resourcesSection.setControls(refreshButton, searchButton, showAsMenuButton);

        // set the Navigation Pane and Editor Area as members of the South
		// layout container
        groupContentsWindow = new GroupContentsWindow();
        
		myGroups = new MyGroups(groupContentsWindow);
		myGroups.setHeight("25%");
		groupsSection.addItem(myGroups);
		resourcesSection.addItem(groupContentsWindow);
		groupContentsWindow.setShowAsTree(WebStudio.get().getUserPreference().getItemView().equals("Tree") ? true: false);
		
		southLayout.setMembers(groupsSectionStack, editorPanel);
        //groupsSection.getSectionHeader().setStyleName("ws-main-group-section-background");

		// add the North and South layout containers to the main layout container
		this.addMember(northLayout); 
		this.addMember(southLayout);

		statusbar = new WebStudioStatusBar();
		this.setCanFocus(true);
		this.addMember(statusbar);	
		this.addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				WebStudioShortcutUtil.handleKeyPressEvent(event);			
			}
		});
		this.addDrawHandler(new DrawHandler() {

			@Override
			public void onDraw(DrawEvent event) {
				WebStudioWorkspacePage.this.focus();
				WebStudioWorkspacePage.this.setCanFocus(true);
			}
		});
	}

	public void openResource(String resourceToOpen) {
		getGroupContentsWindow().openResource(resourceToOpen);		
	}
	
	public void openResourceTree(String resourceToOpen) {
		getGroupContentsWindow().openResourceTree(resourceToOpen);		
	}

	public GroupContentsWindow getGroupContentsWindow() {
		return groupContentsWindow;
	}

	protected void showResourceAsTree(boolean asTree) {
		groupContentsWindow.setShowAsTree(asTree);
		WebStudio.get().getPortalPage().getGroupContentWindowtFromGroupPortlet().setShowAsTree(asTree);
	}
	
	public void toggleRemoveStatus(boolean enable) {
		if (enable) removeButton.enable();
		else removeButton.disable();
	}

	/**
	 * Define which groups to show as selected by default
	 */
	public void selectDefaultGroups() {
		// for now, the default groups are the Business Rules and Decision Tables groups
		getMyGroups().clearSelectedGroups();
		
		if (!groupContentsWindow.isShowAsTree()) {
			WebStudio.get().getApplicationToolBar().enableButton(WebStudioToolbar.TOOL_STRIP_COMMIT_ID);
			WebStudio.get().getApplicationToolBar().enableButton(WebStudioToolbar.TOOL_STRIP_UPDATE_ID);
			WebStudio.get().getApplicationToolBar().enableButton(WebStudioToolbar.TOOL_STRIP_REVERT_ID);
		}
		
		
		// add default groups
		getMyGroups().clearDefaultGroupsList();
		getMyGroups().addToDefaultGroups(ContentModelManager.BUSINESS_RULES_GROUP_ID);
		getMyGroups().addToDefaultGroups(ContentModelManager.DECISION_TABLES_GROUP_ID);
		getMyGroups().addToDefaultGroups(ContentModelManager.PROCESSES_GROUP_ID);
		
		List<String> groupsToRefresh = new ArrayList<String>();
		groupsToRefresh.add(ContentModelManager.BUSINESS_RULES_GROUP_ID);
		groupsToRefresh.add(ContentModelManager.DECISION_TABLES_GROUP_ID);
		groupsToRefresh.add(ContentModelManager.PROCESSES_GROUP_ID);
		groupsToRefresh.add(ContentModelManager.PROJECTS_GROUP_ID);
		groupContentsWindow.setgroupToRefreshList(groupsToRefresh);
		
		// start with the first from the list
		getMyGroups().selectGroup(ContentModelManager.BUSINESS_RULES_GROUP_ID);
	}
	
	/**
	 * Check if the explorer is shown as tree
	 * @return
	 */
	public boolean isShownAsTree() {
		return groupContentsWindow.isShowAsTree();
	}
	
	/**
	 * Hide Workspace page. 
	 */
	public void hideWorkspacePage(Rectangle rectangle) {
		final Rectangle rect = WebStudio.get().getMainLayout().getRect();

        // create an outline around the clicked button  
        final Canvas outline = new Canvas();  
        outline.setLeft(rect.getLeft());  
        outline.setTop(rect.getTop());  
        outline.setWidth(rect.getWidth());  
        outline.setHeight(rect.getHeight());  
        outline.setBorder("2px solid #8289A6");  
        outline.draw();  
        outline.bringToFront();  

		outline.animateRect(rectangle.getLeft(), rectangle.getTop(), rectangle.getWidth(), rectangle.getHeight(), new AnimationCallback() {
			
			@Override
			public void execute(boolean earlyFinish) {
				hide();
				outline.destroy();
			}
		});
	}	
}
