package com.tibco.cep.webstudio.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.WebStudioWorkspacePage;
import com.tibco.cep.webstudio.client.authentication.LoginPage;
import com.tibco.cep.webstudio.client.groups.ContentModelManager;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.logging.WebStudioClientLogger;
import com.tibco.cep.webstudio.client.portal.WebStudioPortalPage;
import com.tibco.cep.webstudio.client.preferences.WebStudioPreferencesPage;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * Widget to represent Webstudio Header
 * 
 * @author Vikram Patil
 * @author Ryan
 */
public class WebStudioHeader extends VLayout implements HttpSuccessHandler, HttpFailureHandler {

	private static final int MASTHEAD_HEIGHT = 25;
	private ClickHandler portalClickHandler;
	private TabSet tabSet;
	private Tab workspaceTab, dashboardTab, preferencesTab;
	private GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	private WebStudioClientLogger logger = WebStudioClientLogger.getLogger(WebStudioHeader.class.getName());

	/**
	 * Default constructor
	 */
	public WebStudioHeader() {
		super();
		logger.info("Init WebStudio header");

		// initialize the layout container
		this.setHeight(MASTHEAD_HEIGHT);
		this.setWidth100();
		this.setStyleName("ws-Masthead");

		HLayout bodyLayout = new HLayout();
		bodyLayout.setWidth100();
		bodyLayout.addMember(this.createWestLayout());
		if(LoginPage.getCompanyLogoUrl() != null && !LoginPage.getCompanyLogoUrl().isEmpty()) {
			bodyLayout.addMember(this.createCompanyLogoLayout());
		}
		bodyLayout.addMember(this.createEastLayout());

		this.addMember(bodyLayout);
		this.addMember(this.createStrips());
	}

	/**
	 * Create West Layout to include Logo
	 * 
	 * @return
	 */
	private HLayout createWestLayout() {
		// initialize the Logo image
		Img logo = new Img(Page.getAppImgDir() + "Logo_welcome_screen_thicker.png", 436, 21);
		logo.setStyleName("ws-Masthead-Logo");
		logo.addClickHandler(this.getPortalClickHandler(logo));
		logo.setValign(VerticalAlignment.BOTTOM);
		
		// initialize the West layout container
		HLayout westLayout = new HLayout();
		westLayout.setWidth("50%");
		westLayout.addMember(logo);

		return westLayout;
	}
	
	/**
	 * Creates the Company Logo Image component shown in the header section.
	 * @return
	 */
	private Canvas createCompanyLogoLayout() {
		HTMLPane companyLogoPane = new HTMLPane();
		companyLogoPane.setContents("<div class=\"CompanyLogo\" id=\"CompanyLogo\">" +
				"<img style=\"max-height:60px;\" src='" + LoginPage.getCompanyLogoUrl() + "' alt='Company Logo'/>" +
				"</div>");
		companyLogoPane.setWidth100();
		companyLogoPane.setHeight(60);
		companyLogoPane.setOverflow(Overflow.HIDDEN);
		return companyLogoPane;
	}
	
	/**
	 * Creates the Switch to UI button shown in the header section.
	 * @return
	 */
	private Canvas createSwitchToNewUIButton() {
		HTMLPane switchButton = new HTMLPane();
		switchButton.setContents("<div class=\"ws-newUIWrapper\"><div class=\"ws-anchorAsButton\">Try the new UI</div></div>");
		switchButton.setWidth(116);
		switchButton.setHeight(50);
		switchButton.setOverflow(Overflow.HIDDEN);
		switchButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.Location.replace("new/index.html");
			}
		});
		return switchButton;
	}
	
	/**
	 * Create west layout to include loggedIn details and tabs
	 * 
	 * @return
	 */
	private HLayout createEastLayout() {
		HLayout hBox = new HLayout(5);
		hBox.setWidth("10%");
		hBox.setHeight100();
		
		hBox.addMember(this.createSwitchToNewUIButton());
		
		VLayout eastLayout = new VLayout(15);
		eastLayout.setWidth("70%");
		eastLayout.setHeight100();
		eastLayout.setLayoutTopMargin(5);
		eastLayout.setLayoutRightMargin(5);

		HLayout topLayout = new HLayout(5);
		topLayout.setWidth(270);
		topLayout.setHeight(12);
		topLayout.setAlign(VerticalAlignment.TOP);
		topLayout.setAlign(Alignment.RIGHT);
		
		String label = globalMsgBundle.text_welcome() + " <b>" + WebStudio.get().getUser().getUserName() + "</b> ";
		Label signedInUser = new Label(label);
		signedInUser.setStyleName("ws-MastHead-SignedInUser");
		signedInUser.setContents(label);		
		signedInUser.setWidth("75%");
		signedInUser.setIcon(Page.getAppImgDir() + "icons/16/person.png");
		signedInUser.setIconAlign(Alignment.RIGHT.getValue());
		if(LocaleInfo.getCurrentLocale().isRTL()){
			signedInUser.setAlign(Alignment.LEFT);
		}
		else{
			signedInUser.setAlign(Alignment.RIGHT);
		}
		topLayout.addMember(signedInUser);
		
		
		Label signoutLabel = this.getLink(globalMsgBundle.text_signout());
		signoutLabel.setAlign(Alignment.RIGHT);
		signoutLabel.setWrap(false);
		signoutLabel.setWidth("25%");
		topLayout.addMember(signoutLabel);

		eastLayout.addMember(topLayout);

		HLayout bottomLayout = new HLayout();
		bottomLayout.setWidth(275);
		bottomLayout.setHeight(20);
		bottomLayout.setAlign(Alignment.RIGHT);

		bottomLayout.addMember(this.createPageSet());
		eastLayout.addMember(bottomLayout);

		hBox.addMember(eastLayout);

		return hBox;
	}

	/**
	 * Create tab set
	 * 
	 * @return
	 */
	private TabSet createPageSet() {
		this.tabSet = new TabSet();
		this.tabSet.setTabBarPosition(Side.TOP);
		this.tabSet.setWidth100();
		this.tabSet.setHeight100();
		
		if(LocaleInfo.getCurrentLocale().isRTL()){
			this.tabSet.setTabBarAlign(Side.LEFT);
			this.tabSet.setAlign(Alignment.LEFT);
		}
		else{
			this.tabSet.setTabBarAlign(Side.RIGHT);
			this.tabSet.setAlign(Alignment.RIGHT);
		}

		this.workspaceTab = new Tab(globalMsgBundle.text_myWorkspace());
		this.workspaceTab.addTabSelectedHandler(new TabSelectedHandler() {
			public void onTabSelected(TabSelectedEvent event) {			
				WebStudio.get().showWorkSpacePage();	
				WebStudio.get().getWorkspacePage().focus();
				WebStudio.get().getWorkspacePage().setCanFocus(true);
				if(WebStudio.get().getUserPreference().isGroupingPropertyChanged()){
					List<String> slectedGrpsId = WebStudio.get().getWorkspacePage().getGroupContentsWindow().getSelectedGrpsIdList();
					WebStudio.get().getWorkspacePage().getMyGroups().clearSelectedGroups();
					List<String> groupsToRefresh = new ArrayList<String>();
					groupsToRefresh.add(ContentModelManager.BUSINESS_RULES_GROUP_ID);
					groupsToRefresh.add(ContentModelManager.DECISION_TABLES_GROUP_ID);
					groupsToRefresh.add(ContentModelManager.PROCESSES_GROUP_ID);
					groupsToRefresh.add(ContentModelManager.PROJECTS_GROUP_ID);
					WebStudio.get().getWorkspacePage().getGroupContentsWindow().setgroupToRefreshList(groupsToRefresh);
					for(String grpId : slectedGrpsId){
						WebStudio.get().getWorkspacePage().getMyGroups().selectGroup(grpId);
					}
					WebStudio.get().getUserPreference().setIsGroupingPropertyChanged(false);
				}
			}
		});
		this.dashboardTab = new Tab(globalMsgBundle.text_dashboard());
		this.dashboardTab.addTabSelectedHandler(new TabSelectedHandler() {
			public void onTabSelected(TabSelectedEvent event) {
				WebStudio.get().showDashboardPage();
				WebStudio.get().getPortalPage().focus();
				WebStudio.get().getPortalPage().setCanFocus(true);
			}
		});
		this.preferencesTab = new Tab(globalMsgBundle.text_settings());
		this.preferencesTab.addTabSelectedHandler(new TabSelectedHandler() {
			public void onTabSelected(TabSelectedEvent event) {
				WebStudio.get().showPreferencesPage();
				WebStudio.get().getPreferencesPage().focus();
				WebStudio.get().getPreferencesPage().setCanFocus(true);
			}
		});

		this.tabSet.addTab(this.dashboardTab);
		this.tabSet.addTab(this.workspaceTab);
		this.tabSet.addTab(this.preferencesTab);
	
		WebStudio.get().getPortalPage().focus();
		WebStudio.get().getPortalPage().setCanFocus(true);
	
		this.tabSet.selectTab(this.dashboardTab);
		return this.tabSet;
	}

	/**
	 * Create Empty stripes
	 * 
	 * @return
	 */
	private VLayout createStrips() {
		VLayout stripLayout = new VLayout();
		stripLayout.setWidth100();
		
		HLayout stripBelowHeader = new HLayout();
		stripBelowHeader.setWidth100();
		stripBelowHeader.setHeight(5);
		stripBelowHeader.setStyleName("stripBelowHeader");
		
		HLayout blueStrip = new HLayout();
		blueStrip.setWidth100();
		blueStrip.setHeight(5);
		blueStrip.setStyleName("ws-Masthead");

		stripLayout.addMember(stripBelowHeader);
		stripLayout.addMember(blueStrip);

		return stripLayout;
	}

	/**
	 * Handler for portal clicks
	 * 
	 * @param canvas
	 * @return
	 */
	private ClickHandler getPortalClickHandler(final Canvas canvas) {
		if (this.portalClickHandler == null) {
			this.portalClickHandler = new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					boolean isVisible;
					WebStudioPortalPage portalPage = WebStudio.get().getPortalPage();
					if(isDashboardPageSelected()) {
						isVisible = portalPage.isVisible();
						portalPage.setAnimateRectTime(500);
						if(isVisible) {
							portalPage.hidePortalPage(canvas.getRect());
						} else {
							portalPage.showPortalPage(canvas.getRect());
						}
					} else if (isWorkSpacePageSelected()) {
						WebStudioWorkspacePage workspacePage = WebStudio.get().getWorkspacePage();
						isVisible = workspacePage.isVisible();
						workspacePage.setAnimateRectTime(500);
						if(isVisible) {
							workspacePage.hideWorkspacePage(canvas.getRect());
						} else {
							portalPage.showPortalPage(canvas.getRect());
							selectDashboardPage();
						}
					} else if (isPreferencesPageSelected()){
						WebStudioPreferencesPage preferencePage = WebStudio.get().getPreferencesPage();
						isVisible = preferencePage.isVisible();
						preferencePage.setAnimateRectTime(500);
						if(isVisible) {
							preferencePage.hidePreferencesPage(canvas.getRect());
						} else {
							portalPage.showPortalPage(canvas.getRect());
							selectDashboardPage();
						}
					}
				}
			};
		}
		return this.portalClickHandler;
	}

	/**
	 * Create the logout link
	 * 
	 * @param message
	 * @param handler
	 * @return
	 */
	private Label getLink(String message) {
		Label link = new Label("[" + message + "]");
		link.setStyleName("ws-signout");
		link.setMargin(0);
			
		if (LocaleInfo.getCurrentLocale().isRTL()) {
			link.setAlign(Alignment.LEFT);
		}
		else{
			link.setAlign(Alignment.RIGHT);
		}
		
		

		link.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				WebStudioHeader.this.doLogout();
			}
		});
		return link;
	}

	/**
	 * Make the RMS logout call to remove the user from the cache as well as
	 * delete the cookie
	 */
	private void doLogout() {
		ArtifactUtil.addHandlers(this);
		HttpRequest request = new HttpRequest(ServerEndpoints.LOGOUT.getURL(), HttpMethod.GET);
		request.submit();
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.LOGOUT.getURL()) != -1) {
			ArtifactUtil.removeHandlers(this);
			LoginPage.removeUserCookies();
			Window.Location.assign(Window.Location.getHref());
		}
	}

	@Override
	public void onFailure(HttpFailureEvent event) {
	}

	/**
	 * Select Dashboard Tab
	 */
	public void selectDashboardPage() {
		if (this.tabSet.getSelectedTab() != this.dashboardTab) {
			this.tabSet.selectTab(this.dashboardTab);
		}
	}

	/**
	 * Select Workspace Tab
	 */
	public void selectWorkspacePage() {
		if (this.tabSet.getSelectedTab() != this.workspaceTab) {
			this.tabSet.selectTab(this.workspaceTab);
		}
	}
	
	/**
	 * Select Preferences Tab
	 */
	public void selectPreferencesPage() {
		if (this.tabSet.getSelectedTab() != this.preferencesTab) {
			this.tabSet.selectTab(this.preferencesTab);
		}
	}
	
	/**
	 * Check if Dashboard Page is selected
	 * @return
	 */
	public boolean isDashboardPageSelected() {
		return this.tabSet.getSelectedTab() == this.dashboardTab; 
	}
	
	/**
	 * Check if Workspace Page is selected
	 * @return
	 */
	public boolean isWorkSpacePageSelected() {
		return this.tabSet.getSelectedTab() == this.workspaceTab;
	}
	
	/**
	 * Check if Preferences Page is selected
	 * @return
	 */
	public boolean isPreferencesPageSelected() {
		return this.tabSet.getSelectedTab() == this.preferencesTab;
	}
}